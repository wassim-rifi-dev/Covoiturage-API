package ma.youcode.covoiturage.feature.passenger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.covoiturage.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.covoiturage.feature.booking.service.BookingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
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

    @PatchMapping("/{passengerId}/bookings/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long passengerId , @PathVariable Long bookingId) {
        log.warn("PATCH /api/passengers/{}/bookings/{}/cancel", passengerId, bookingId);

        bookingService.annulerBooking(passengerId, bookingId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{passengerId}/bookings")
    public ResponseEntity<PagedModel<BookingResponseDTO>> getPassengerBookings(
            @PathVariable Long passengerId,
            @PageableDefault(size = 10, sort = "bookingDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.debug("GET /api/passengers/{}/bookings page={} size={}", passengerId, pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(new PagedModel<>(bookingService.getPassengerBooking(passengerId, pageable)));
    }
}
