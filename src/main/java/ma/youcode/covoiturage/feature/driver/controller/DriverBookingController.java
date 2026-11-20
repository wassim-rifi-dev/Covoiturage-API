package ma.youcode.covoiturage.feature.driver.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.covoiturage.feature.booking.service.BookingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(
        name = "Driver Bookings",
        description = "Endpoints for drivers to view bookings on their trips."
)
public class DriverBookingController {
    private final BookingService bookingService;

    @GetMapping("/{driverId}/bookings")
    public ResponseEntity<PagedModel<BookingResponseDTO>> getDriverBooking(
        @PathVariable Long driverId,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.warn("GET /api/drivers/{}/bookings", driverId);

        return ResponseEntity.ok(new PagedModel<>(bookingService.getDriverBooking(driverId , pageable)));
    }

    @GetMapping("/{driverId}/trips/{tripId}/bookings")
    public ResponseEntity<PagedModel<BookingResponseDTO>> getDriverTripBookings(
        @PathVariable Long driverId,
        @PathVariable Long tripId,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/drivers/{}/trips/{}/bookings", driverId, tripId);

        return ResponseEntity.ok(new PagedModel<>(bookingService.getDriverTripBookings(driverId, tripId, pageable)));
    }
}
