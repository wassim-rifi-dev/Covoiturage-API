package ma.youcode.rideflow.feature.driver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.rideflow.feature.driver.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
