package ma.youcode.rideflow.feature.driver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.rideflow.feature.driver.service.DriverService;
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


    @GetMapping
    public ResponseEntity<PagedModel<DriverResponseDTO>> getAllDrivers(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/driver page={} size={}", pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(new PagedModel<>(driverService.getAllDrivers(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable Long id) {
        log.debug("Get /api/driver/{}" , id);

        DriverResponseDTO responseDTO = driverService.getDriverById(id);

        return ResponseEntity.ok(responseDTO);
    }
}
