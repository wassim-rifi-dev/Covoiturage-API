package ma.youcode.covoiturage.feature.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.request.DriverSearchCriteria;
import ma.youcode.covoiturage.feature.driver.dto.request.UpdateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.covoiturage.feature.driver.service.DriverService;
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
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(
        name = "Driver",
        description = "Driver management endpoints."
)
public class DriverController {
    private final DriverService driverService;

    @Operation(
            summary = "Create a driver",
            description = "Registers a new driver account."
    )
    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(
            @Valid @RequestBody CreateDriverRequestDTO requestDTO
    ) {
        log.debug("POST /api/drivers");

        DriverResponseDTO responseDTO = driverService.createDriver(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(
            summary = "Get a driver by ID",
            description = "Returns the details of the driver identified by the given ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable Long id) {
        log.debug("Get /api/drivers/{}" , id);

        DriverResponseDTO responseDTO = driverService.getDriverById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Update a driver",
            description = "Updates the information of an existing driver identified by its ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(
            @PathVariable Long id ,
            @Valid @RequestBody UpdateDriverRequestDTO requestDTO
    ) {
        log.debug("PUT /api/drivers/{}", id);

        DriverResponseDTO responseDTO = driverService.updateDriver(id , requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Search drivers",
            description = "Returns a paginated list of drivers matching the optional search criteria."
    )
    @GetMapping
    public ResponseEntity<PagedModel<DriverResponseDTO>> searchDriver(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String licenseNumber,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/drivers page={} size={} search", pageable.getPageNumber(), pageable.getPageSize());

        DriverSearchCriteria criteria = new DriverSearchCriteria(fullName, email, phone, licenseNumber);

        return ResponseEntity.ok(new PagedModel<>(driverService.searchDrivers(criteria, pageable)));
    }
}
