package ma.youcode.covoiturage.feature.driver.controller;

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
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(
            @Valid @RequestBody CreateDriverRequestDTO requestDTO
    ) {
        log.debug("POST /api/driver");

        DriverResponseDTO responseDTO = driverService.createDriver(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable Long id) {
        log.debug("Get /api/driver/{}" , id);

        DriverResponseDTO responseDTO = driverService.getDriverById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(
            @PathVariable Long id ,
            @Valid @RequestBody UpdateDriverRequestDTO requestDTO
    ) {
        log.debug("PUT /api/driver/{}", id);

        DriverResponseDTO responseDTO = driverService.updateDriver(id , requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<PagedModel<DriverResponseDTO>> searchDriver(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String licenseNumber,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/driver page={} size={} search", pageable.getPageNumber(), pageable.getPageSize());

        DriverSearchCriteria criteria = new DriverSearchCriteria(fullName, email, phone, licenseNumber);

        return ResponseEntity.ok(new PagedModel<>(driverService.searchDrivers(criteria, pageable)));
    }
}
