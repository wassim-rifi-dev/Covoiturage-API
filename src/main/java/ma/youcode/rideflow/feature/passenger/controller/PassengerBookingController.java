package ma.youcode.rideflow.feature.passenger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.rideflow.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.rideflow.feature.booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerBookingController {
    private final BookingService bookingService;

    @PostMapping("/{passengerId}/booking")
    public ResponseEntity<BookingResponseDTO> createBooking(
        @PathVariable Long passengerId,
        @Valid @RequestBody CreateBookingRequestDTO requestDTO
    ) {
        log.warn("POST /api/passenger/{}/booking", passengerId);

        BookingResponseDTO responseDTO = bookingService.createBooking(passengerId, requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/booking/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
