package ma.youcode.rideflow.feature.driver.service;

import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.request.UpdateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {
    DriverResponseDTO createDriver(CreateDriverRequestDTO requestDTO);

    Page<DriverResponseDTO> getAllDrivers(Pageable pageable);

    DriverResponseDTO getDriverById(Long id);

    DriverResponseDTO updateDriver(Long id, UpdateDriverRequestDTO requestDTO);
}
