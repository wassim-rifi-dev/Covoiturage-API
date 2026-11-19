package ma.youcode.rideflow.feature.driver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.rideflow.feature.trip.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverTripController {
    private final TripService tripService;

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
}
