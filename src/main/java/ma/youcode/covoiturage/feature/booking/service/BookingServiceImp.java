package ma.youcode.covoiturage.feature.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.TripNotBookableException;
import ma.youcode.covoiturage.common.exception.UserNotFoundException;
import ma.youcode.covoiturage.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.covoiturage.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.covoiturage.feature.booking.exception.*;
import ma.youcode.covoiturage.feature.booking.mapper.BookingMapper;
import ma.youcode.covoiturage.feature.booking.model.entity.Booking;
import ma.youcode.covoiturage.feature.booking.model.enums.BookingStatus;
import ma.youcode.covoiturage.feature.booking.repository.BookingRepository;
import ma.youcode.covoiturage.feature.driver.model.entity.Driver;
import ma.youcode.covoiturage.feature.driver.repository.DriverRepository;
import ma.youcode.covoiturage.feature.passenger.model.entity.Passenger;
import ma.youcode.covoiturage.feature.passenger.repository.PassengerRepository;
import ma.youcode.covoiturage.feature.trip.exception.*;
import ma.youcode.covoiturage.feature.trip.model.entity.Trip;
import ma.youcode.covoiturage.feature.trip.model.entity.Waypoint;
import ma.youcode.covoiturage.feature.trip.model.enums.TripStatus;
import ma.youcode.covoiturage.feature.trip.repository.TripRepository;
import ma.youcode.covoiturage.feature.trip.repository.WaypointRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final PassengerRepository passengerRepository;

    private final TripRepository tripRepository;

    private final WaypointRepository waypointRepository;

    private final DriverRepository driverRepository;

    @Override
    @Transactional
    public BookingResponseDTO createBooking(Long passengerId, CreateBookingRequestDTO requestDTO) {
        Passenger passenger = passengerExist(passengerId);

        Trip trip = tripRepository.findById(requestDTO.tripId())
                .orElseThrow(() -> new TripNotFoundException("Trip not exist with id=" + requestDTO.tripId()));

        Waypoint pickupPoint = waypointRepository.findById(requestDTO.pickupPointId())
                .orElseThrow(() -> new WaypointNotFoundException("Waypoint not exist with id=" + requestDTO.pickupPointId()));

        Waypoint dropoffPoint = waypointRepository.findById(requestDTO.dropoffPointId())
                .orElseThrow(() -> new WaypointNotFoundException("Waypoint not exist with id=" + requestDTO.dropoffPointId()));

        tripIsBookable(trip);

        if (trip.getRemainingSeats() < requestDTO.numberOfSeats()) {
            throw new NotEnoughAvailableSeatsException("Not enough available seats for this trip.");
        }

        if (!trip.getWaypoints().contains(pickupPoint) && !trip.getWaypoints().contains(dropoffPoint)) {
            throw new WaypointNotBelongToTripException("Pickup point or dropoff point does not belong to this trip");
        }

        if (dropoffPoint.getOrder() < pickupPoint.getOrder()) {
            throw new InvalidWaypointOrderException("Dropoff point must be after pickup point");
        }

        Booking booking = bookingMapper.toEntity(requestDTO);
        booking.setPassenger(passenger);
        booking.setTrip(trip);
        booking.setPickupPoint(pickupPoint);
        booking.setDropoffPoint(dropoffPoint);

        if (trip.getRemainingSeats() - requestDTO.numberOfSeats() == 0) {
            trip.setStatus(TripStatus.FULL);

            tripRepository.save(trip);
        }

        Booking createdBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDto(createdBooking);
    }

    @Override
    @Transactional
    public void annulerBooking(Long passengerId , Long bookingId) {
        passengerExist(passengerId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not exist with id=" + bookingId));

        if (!booking.getPassenger().getId().equals(passengerId)) {
            throw new BookingDoesNotBelongToPassengerException("This booking not accesible for this passenger.");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingAlreadyCancelledException("Booking is already cancelled");
        }

        if (!booking.isCancellable()) {
            throw new BookingCancellationDeadlineExceededException("You can't cancel your booking because deadline of cancel is finish.");
        }

        booking.cancel();

        if (booking.getTrip().getStatus() == TripStatus.FULL) {
            booking.getTrip().setStatus(TripStatus.PUBLISHED);
        }

        tripRepository.save(booking.getTrip());

        bookingRepository.save(booking);
    }

    @Override
    public Page<BookingResponseDTO> getPassengerBooking(Long passengerId, Pageable pageable) {
        passengerExist(passengerId);

        return bookingRepository.findAllByPassengerId(passengerId, pageable)
                .map(bookingMapper::toResponseDto);
    }

    @Override
    public Page<BookingResponseDTO> getDriverBooking(Long driverId, Pageable pageable) {
        driverExist(driverId);

        return bookingRepository.findAllByTripDriverId(driverId, pageable)
                .map(bookingMapper::toResponseDto);
    }

    @Override
    public Page<BookingResponseDTO> getDriverTripBookings(Long driverId, Long tripId, Pageable pageable) {
        driverExist(driverId);

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not exist with id=" + tripId));

        if (!trip.getDriver().getId().equals(driverId)) {
            throw new DriverNotAuthorizedForTripException("This driver don't have permission to trip with id=" + tripId);
        }

        return bookingRepository.findAllByTripId(tripId, pageable)
                .map(bookingMapper::toResponseDto);
    }

    private void tripIsBookable(Trip trip) {
        if (!trip.isBookable()) {
            throw new TripNotBookableException("This trip is bookable.");
        }
    }

    private Passenger passengerExist(Long passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new UserNotFoundException("Passenger not exist with id=" + passengerId));
    }

    private Driver driverExist(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new UserNotFoundException("Driver not exist with id=" + driverId));
    }
}
