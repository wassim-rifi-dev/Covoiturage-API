package ma.youcode.rideflow.feature.booking.service;

import ma.youcode.rideflow.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.rideflow.feature.booking.dto.response.BookingResponseDTO;

public interface BookingService {
    public BookingResponseDTO createBooking(Long passengerId , CreateBookingRequestDTO requestDTO);

    public void annulerBooking(Long passengerId, Long bookingId);
}
