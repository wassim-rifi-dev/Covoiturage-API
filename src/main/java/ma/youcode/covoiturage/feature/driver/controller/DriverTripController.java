package ma.youcode.covoiturage.feature.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.covoiturage.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.covoiturage.feature.trip.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(
        name = "Driver Trips",
        description = "Endpoints for drivers to create and manage their trips."
)
public class DriverTripController {
    private final TripService tripService;

    @Operation(
            summary = "Create a trip",
            description = "Creates a new trip published by the given driver."
    )
    @PostMapping("/{driverId}/trip")
    public ResponseEntity<TripResponseDTO> createTrip(
            @PathVariable Long driverId,
            @RequestBody CreateTripRequestDTO requestDTO
    ) {
        log.debug("POST /api/trips/{}", driverId);

        TripResponseDTO responseDTO = tripService.createTrip(driverId, requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/trips/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(
            summary = "Cancel a trip",
            description = "Cancels a trip belonging to the given driver."
    )
    @PatchMapping("/{driverId}/trips/{tripId}/cancel")
    public ResponseEntity<Void> cancelTrip(
            @PathVariable Long driverId,
            @PathVariable Long tripId
    ) {

        tripService.cancelTrip(driverId, tripId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Complete a trip",
            description = "Marks a trip belonging to the given driver as completed."
    )
    @PatchMapping("/{driverId}/trips/{tripId}/complet")
    public ResponseEntity<Void> completTrip(
            @PathVariable Long driverId,
            @PathVariable Long tripId
    ) {

        tripService.completTrip(driverId, tripId);

        return ResponseEntity.noContent().build();
    }
}
