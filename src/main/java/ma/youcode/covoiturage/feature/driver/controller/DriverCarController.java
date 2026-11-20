package ma.youcode.covoiturage.feature.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.covoiturage.feature.car.dto.request.UpdateCarRequestDTO;
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
public class DriverCarController {
    private final CarService carService;

    @PostMapping("/{driverId}/cars")
    public ResponseEntity<CarResponseDTO> createCar(
            @PathVariable Long driverId,
            @Valid @RequestBody CreateCarRequestDTO requestDTO
    ) {
        CarResponseDTO responseDTO = carService.createCar(driverId, requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/cars/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(
            summary = "Update a car",
            description = "Updates the information of an existing car identified by its ID."
    )
    @PutMapping("/{driverId}/cars/{carId}")
    public ResponseEntity<CarResponseDTO> updateCar(
            @PathVariable Long driverId,
            @PathVariable Long carId,
            @Valid @RequestBody UpdateCarRequestDTO requestDTO
    ) {
        log.debug("PUT /api/cars/{}/cars/{}", driverId, carId);

        CarResponseDTO responseDTO = carService.updateCar(driverId, carId , requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{driverId}/cars")
    public ResponseEntity<DriverCarsResponseDTO> getCarsByDriverId(
            @PathVariable Long driverId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/drivers/{}/cars page={} size={}", driverId, pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(carService.getCarsByDriverId(driverId, pageable));
    }
}
