package ma.youcode.covoiturage.feature.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.covoiturage.feature.car.dto.response.CarResponseDTO;
import ma.youcode.covoiturage.feature.car.service.CarService;
import ma.youcode.covoiturage.feature.driver.dto.response.DriverCarsResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(
        name = "Driver Cars",
        description = "Endpoints for drivers to register and list their cars."
)
public class DriverCarController {
    private final CarService carService;

    @Operation(
            summary = "Register a car for a driver",
            description = "Creates a new car and associates it with the given driver."
    )
    @PostMapping("/{driverId}/car")
    public ResponseEntity<CarResponseDTO> createCar(
            @PathVariable Long driverId,
            @Valid @RequestBody CreateCarRequestDTO requestDTO
    ) {
        CarResponseDTO responseDTO = carService.createCar(driverId, requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/car/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(
            summary = "List a driver's cars",
            description = "Returns a paginated list of the cars owned by the given driver."
    )
    @GetMapping("/{driverId}/car")
    public ResponseEntity<DriverCarsResponseDTO> getCarsByDriverId(
            @PathVariable Long driverId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/drivers/{}/car page={} size={}", driverId, pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(carService.getCarsByDriverId(driverId, pageable));
    }
}
