package ma.youcode.rideflow.feature.driver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.rideflow.feature.car.dto.response.CarResponseDTO;
import ma.youcode.rideflow.feature.car.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverCarController {
    private final CarService carService;

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
}
