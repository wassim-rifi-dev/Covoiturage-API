package ma.youcode.covoiturage.feature.passenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.passenger.dto.request.CreatePassengeRequestDTO;
import ma.youcode.covoiturage.feature.passenger.dto.request.PassengerSearchCriteria;
import ma.youcode.covoiturage.feature.passenger.dto.request.UpdatePassengerRequestDTO;
import ma.youcode.covoiturage.feature.passenger.dto.response.PassengerResponseDTO;
import ma.youcode.covoiturage.feature.passenger.service.PassengerService;
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
@Tag(
        name = "Passenger",
        description = "Passenger management endpoints."
)
public class PassengerController {
    private final PassengerService passengerService;

    @Operation(
            summary = "Create a passenger",
            description = "Registers a new passenger account."
    )
    @PostMapping
    public ResponseEntity<PassengerResponseDTO> createPassenger(
            @RequestBody CreatePassengeRequestDTO requestDTO
    ) {
        log.debug("POST /api/passengers");

        PassengerResponseDTO responseDTO = passengerService.createPassenger(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(
            summary = "Update a passenger",
            description = "Updates the information of an existing passenger identified by its ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePassengerRequestDTO requestDTO
    ) {
        log.debug("PUT /api/passengers/{}", id);

        PassengerResponseDTO responseDTO = passengerService.updatePassenger(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Search passengers",
            description = "Returns a paginated list of passengers matching the optional search criteria."
    )
    @GetMapping
    public ResponseEntity<PagedModel<PassengerResponseDTO>> searchPassengers(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/passengers page={} size={} search", pageable.getPageNumber(), pageable.getPageSize());

        PassengerSearchCriteria criteria = new PassengerSearchCriteria(fullName, email, phone);

        return ResponseEntity.ok(new PagedModel<>(passengerService.searchPassengers(criteria, pageable)));
    }
}
