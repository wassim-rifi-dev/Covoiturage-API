package ma.youcode.covoiturage.feature.booking;

import ma.youcode.covoiturage.common.exception.UserNotFoundException;
import ma.youcode.covoiturage.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.covoiturage.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.covoiturage.feature.booking.mapper.BookingMapper;
import ma.youcode.covoiturage.feature.booking.model.entity.Booking;
import ma.youcode.covoiturage.feature.booking.model.enums.BookingStatus;
import ma.youcode.covoiturage.feature.booking.repository.BookingRepository;
import ma.youcode.covoiturage.feature.booking.service.BookingServiceImp;
import ma.youcode.covoiturage.feature.driver.repository.DriverRepository;
import ma.youcode.covoiturage.feature.passenger.model.entity.Passenger;
import ma.youcode.covoiturage.feature.passenger.repository.PassengerRepository;
import ma.youcode.covoiturage.feature.trip.exception.TripNotFoundException;
import ma.youcode.covoiturage.feature.trip.exception.WaypointNotFoundException;
import ma.youcode.covoiturage.feature.trip.model.entity.Trip;
import ma.youcode.covoiturage.feature.trip.model.entity.Waypoint;
import ma.youcode.covoiturage.feature.trip.model.enums.TripStatus;
import ma.youcode.covoiturage.feature.trip.repository.TripRepository;
import ma.youcode.covoiturage.feature.trip.repository.WaypointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImpTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private WaypointRepository waypointRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private BookingServiceImp bookingService;

    @Test
    @DisplayName("Should create a booking successfully when all data are valid")
    void createBooking_shouldReturnBookingResponse_whenRequestIsValid() {
        // Arrange
        Long passengerId = 1L;
        Long tripId = 10L;
        Long pickupPointId = 100L;
        Long dropoffPointId = 200L;
        int numberOfSeats = 2;

        CreateBookingRequestDTO requestDTO = new CreateBookingRequestDTO(tripId, numberOfSeats, pickupPointId, dropoffPointId);

        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        Trip trip = Trip.builder()
                .id(tripId)
                .proposedSeats(4)
                .status(TripStatus.PUBLISHED)
                .departureDateTime(LocalDateTime.now().plusDays(2))
                .build();

        Waypoint pickupPoint = Waypoint.builder()
                .id(pickupPointId)
                .city("Casablanca")
                .order(0)
                .priceFromStart(BigDecimal.ZERO)
                .passageTime(LocalDateTime.now().plusDays(2))
                .build();

        Waypoint dropoffPoint = Waypoint.builder()
                .id(dropoffPointId)
                .city("Rabat")
                .order(1)
                .priceFromStart(BigDecimal.valueOf(50))
                .passageTime(LocalDateTime.now().plusDays(2).plusHours(1))
                .build();

        Booking booking = Booking.builder()
                .numberOfSeats(numberOfSeats)
                .build();

        Booking savedBooking = Booking.builder()
                .id(1L)
                .numberOfSeats(numberOfSeats)
                .totalAmount(BigDecimal.valueOf(100))
                .status(BookingStatus.CONFIRMED)
                .bookingDate(LocalDateTime.now())
                .passenger(passenger)
                .trip(trip)
                .pickupPoint(pickupPoint)
                .dropoffPoint(dropoffPoint)
                .build();

        BookingResponseDTO expectedResponse = BookingResponseDTO.builder()
                .id(1L)
                .numberOfSeats(numberOfSeats)
                .totalAmount(BigDecimal.valueOf(100))
                .status(BookingStatus.CONFIRMED)
                .build();

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(waypointRepository.findById(pickupPointId)).thenReturn(Optional.of(pickupPoint));
        when(waypointRepository.findById(dropoffPointId)).thenReturn(Optional.of(dropoffPoint));
        when(bookingMapper.toEntity(requestDTO)).thenReturn(booking);
        when(bookingRepository.save(booking)).thenReturn(savedBooking);
        when(bookingMapper.toResponseDto(savedBooking)).thenReturn(expectedResponse);

        // Act
        BookingResponseDTO result = bookingService.createBooking(passengerId, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(result , expectedResponse);
        assertEquals(1L , result.id());
        assertEquals(numberOfSeats , result.numberOfSeats());
        assertEquals(BookingStatus.CONFIRMED , result.status());

        assertEquals(passenger, booking.getPassenger());
        assertEquals(trip, booking.getTrip());
        assertEquals(pickupPoint, booking.getPickupPoint());
        assertEquals(dropoffPoint, booking.getDropoffPoint());

        // Verify
        verify(passengerRepository).findById(passengerId);
        verify(tripRepository).findById(tripId);
        verify(waypointRepository).findById(pickupPointId);
        verify(waypointRepository).findById(dropoffPointId);
        verify(bookingMapper).toEntity(requestDTO);
        verify(bookingRepository).save(booking);
        verify(bookingMapper).toResponseDto(savedBooking);
        verify(tripRepository, never()).save(any(Trip.class));
    }

    @Test
    @DisplayName("Should throw an UserNotFoundException when passenger not exist")
    void createBooking_shouldThrowUserNotFoundException_whenPassengerNotFound() {
        // Arrange
        Long passengerId = 999L;

        CreateBookingRequestDTO requestDTO = new CreateBookingRequestDTO(10L, 2, 100L, 200L);

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.empty());

        // Act
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> bookingService.createBooking(passengerId , requestDTO)
        );

        // Assert
        assertNotNull(exception);
        assertEquals("Passenger not exist with id=" + passengerId , exception.getMessage());

        // Verify
        verify(passengerRepository).findById(passengerId);
        verifyNoInteractions(tripRepository, waypointRepository, bookingMapper, bookingRepository);
    }

    @Test
    @DisplayName("Should throw a TripNotFoundException when trip not exist")
    void createBooking_shouldThrowTripNotFoundException_whenTripNotFound() {
        // Arrange
        Long passengerId = 1L;
        Long tripId = 999L;

        CreateBookingRequestDTO requestDTO = new CreateBookingRequestDTO(tripId, 2, 100L, 200L);

        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        // Act
        TripNotFoundException exception = assertThrows(
                TripNotFoundException.class,
                () -> bookingService.createBooking(passengerId , requestDTO)
        );

        // Assert
        assertNotNull(exception);
        assertEquals("Trip not exist with id=" + tripId , exception.getMessage());

        // Verify
        verify(passengerRepository).findById(passengerId);
        verify(tripRepository).findById(tripId);
        verify(tripRepository, never()).save(any(Trip.class));
        verifyNoInteractions(waypointRepository, bookingMapper, bookingRepository);
    }

    @Test
    @DisplayName("Should throw a WaypointNotFoundException when pickup point not exist")
    void createBooking_shouldThrowWaypointNotFoundException_whenPickupPointNotFound() {
        // Arrange
        Long passengerId = 1L;
        Long tripId = 999L;
        Long pickupPointId = 999L;

        CreateBookingRequestDTO requestDTO = new CreateBookingRequestDTO(tripId, 2, pickupPointId, 200L);

        Passenger passenger = new Passenger();
        passenger.setId(passengerId);

        Trip trip = Trip.builder()
                .id(tripId)
                .proposedSeats(4)
                .status(TripStatus.PUBLISHED)
                .departureDateTime(LocalDateTime.now().plusDays(2))
                .build();

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(waypointRepository.findById(pickupPointId)).thenReturn(Optional.empty());

        // Act
        WaypointNotFoundException exception = assertThrows(
                WaypointNotFoundException.class,
                () -> bookingService.createBooking(passengerId , requestDTO)
        );

        // Assert
        assertNotNull(exception);
        assertEquals("Waypoint not exist with id=" + pickupPointId , exception.getMessage());

        // Verify
        verify(passengerRepository).findById(passengerId);
        verify(tripRepository).findById(tripId);
        verify(waypointRepository).findById(pickupPointId);
        verify(waypointRepository, never()).findById(200L);
        verify(tripRepository, never()).save(any(Trip.class));
        verifyNoInteractions(bookingMapper, bookingRepository);
    }
}
