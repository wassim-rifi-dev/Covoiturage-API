package ma.youcode.covoiturage.feature.booking.service;

import ma.youcode.covoiturage.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.covoiturage.feature.booking.dto.response.BookingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    public BookingResponseDTO createBooking(Long passengerId , CreateBookingRequestDTO requestDTO);

    public void annulerBooking(Long passengerId, Long bookingId);

    public Page<BookingResponseDTO> getPassengerBooking(Long passengerId , Pageable pageable);

    public Page<BookingResponseDTO> getDriverBooking(Long driverId , Pageable pageable);

    public Page<BookingResponseDTO> getDriverTripBookings(Long driverId, Long tripId, Pageable pageable);
}
