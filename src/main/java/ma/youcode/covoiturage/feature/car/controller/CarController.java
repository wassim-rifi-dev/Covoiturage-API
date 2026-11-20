package ma.youcode.covoiturage.feature.car.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.car.dto.request.UpdateCarRequestDTO;
import ma.youcode.covoiturage.feature.car.dto.response.CarResponseDTO;
import ma.youcode.covoiturage.feature.car.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(
        name = "Car",
        description = "Car management endpoints."
)
public class CarController {
    private final CarService carService;

    @Operation(
            summary = "Get a car by ID",
            description = "Returns the details of the car identified by the given ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable Long id) {
        log.debug("GET /api/cars/{}", id);

        CarResponseDTO responseDTO = carService.getCarById(id);

        return ResponseEntity.ok(responseDTO);
    }
}
