package ma.youcode.rideflow.feature.passenger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.passenger.dto.request.CreatePassengeRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.request.PassengerSearchCriteria;
import ma.youcode.rideflow.feature.passenger.dto.request.UpdatePassengerRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.response.PassengerResponseDTO;
import ma.youcode.rideflow.feature.passenger.service.PassengerService;
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
@RequestMapping("/api/passenger")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponseDTO> createPassenger(
            @RequestBody CreatePassengeRequestDTO requestDTO
    ) {
        log.debug("POST /api/passenger");

        PassengerResponseDTO responseDTO = passengerService.createPassenger(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePassengerRequestDTO requestDTO
    ) {
        log.debug("PUT /api/passenger/{}", id);

        PassengerResponseDTO responseDTO = passengerService.updatePassenger(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<PagedModel<PassengerResponseDTO>> searchPassengers(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/passenger page={} size={} search", pageable.getPageNumber(), pageable.getPageSize());

        PassengerSearchCriteria criteria = new PassengerSearchCriteria(fullName, email, phone);

        return ResponseEntity.ok(new PagedModel<>(passengerService.searchPassengers(criteria, pageable)));
    }
}
