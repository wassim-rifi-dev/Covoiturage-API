package ma.youcode.rideflow.feature.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.common.exception.TripNotBookableException;
import ma.youcode.rideflow.common.exception.UserNotFoundException;
import ma.youcode.rideflow.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.rideflow.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.rideflow.feature.booking.exception.BookingCancellationDeadlineExceededException;
import ma.youcode.rideflow.feature.booking.exception.BookingDoesNotBelongToPassengerException;
import ma.youcode.rideflow.feature.booking.exception.BookingNotFoundException;
import ma.youcode.rideflow.feature.booking.exception.NotEnoughAvailableSeatsException;
import ma.youcode.rideflow.feature.booking.mapper.BookingMapper;
import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import ma.youcode.rideflow.feature.booking.repository.BookingRepository;
import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import ma.youcode.rideflow.feature.passenger.repository.PassengerRepository;
import ma.youcode.rideflow.feature.trip.exception.TripNotFoundException;
import ma.youcode.rideflow.feature.trip.exception.WaypointNotFoundException;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import ma.youcode.rideflow.feature.trip.model.entity.Waypoint;
import ma.youcode.rideflow.feature.trip.model.enums.TripStatus;
import ma.youcode.rideflow.feature.trip.repository.TripRepository;
import ma.youcode.rideflow.feature.trip.repository.WaypointRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final PassengerRepository passengerRepository;

    private final TripRepository tripRepository;

    private final WaypointRepository waypointRepository;

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

        Booking booking = bookingMapper.toEntity(requestDTO);
        booking.setPassenger(passenger);
        booking.setTrip(trip);
        booking.setPickupPoint(pickupPoint);
        booking.setDropoffPoint(dropoffPoint);

        if (trip.getRemainingSeats() == 0) {
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

        if (!booking.isCancellable()) {
            throw new BookingCancellationDeadlineExceededException("You can't cancel your booking because deadline of cancel is finish.");
        }

        booking.cancel();

        if (booking.getTrip().getStatus() == TripStatus.FULL) {
            booking.getTrip().setStatus(TripStatus.PUBLISHED);
        }

        booking.getTrip().setProposedSeats(booking.getTrip().getProposedSeats() - booking.getNumberOfSeats());

        tripRepository.save(booking.getTrip());

        bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingResponseDTO> getPassengerBooking(Long passengerId, Pageable pageable) {
        passengerExist(passengerId);

        return bookingRepository.findAllByPassengerId(passengerId, pageable)
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
}
