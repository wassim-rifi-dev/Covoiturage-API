package ma.youcode.rideflow.feature.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.common.exception.SeatsExceedTripCapacityException;
import ma.youcode.rideflow.common.exception.UserNotFoundException;
import ma.youcode.rideflow.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.rideflow.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.rideflow.feature.booking.mapper.BookingMapper;
import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import ma.youcode.rideflow.feature.booking.repository.BookingRepository;
import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import ma.youcode.rideflow.feature.passenger.repository.PassengerRepository;
import ma.youcode.rideflow.feature.trip.exception.TripNotFoundException;
import ma.youcode.rideflow.feature.trip.exception.WaypointNotFoundException;
import ma.youcode.rideflow.feature.trip.mapper.WaypointMapper;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import ma.youcode.rideflow.feature.trip.model.entity.Waypoint;
import ma.youcode.rideflow.feature.trip.repository.TripRepository;
import ma.youcode.rideflow.feature.trip.repository.WaypointRepository;
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
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new UserNotFoundException("Passenger not exist with id=" + passengerId));

        Trip trip = tripRepository.findById(requestDTO.tripId())
                .orElseThrow(() -> new TripNotFoundException("Trip not exist with id=" + requestDTO.tripId()));

        Waypoint pickupPoint = waypointRepository.findById(requestDTO.pickupPointId())
                .orElseThrow(() -> new WaypointNotFoundException("Waypoint not exist with id=" + requestDTO.pickupPointId()));

        Waypoint dropoffPoint = waypointRepository.findById(requestDTO.dropoffPointId())
                .orElseThrow(() -> new WaypointNotFoundException("Waypoint not exist with id=" + requestDTO.dropoffPointId()));

        hasEnoughAvailableSeats(trip , requestDTO.numberOfSeats());

        Booking booking = bookingMapper.toEntity(requestDTO);
        booking.setPassenger(passenger);
        booking.setTrip(trip);
        booking.setPickupPoint(pickupPoint);
        booking.setDropoffPoint(dropoffPoint);

        Booking createdBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDto(createdBooking);
    }

    private void hasEnoughAvailableSeats(Trip trip , int numberOfSeats) {
        int availableSeatsCount = trip.getRemainingSeats();

        if (availableSeatsCount < numberOfSeats) {
            throw new SeatsExceedTripCapacityException("Number of seat is bigger than available seats in the trip");
        }
    }
}
