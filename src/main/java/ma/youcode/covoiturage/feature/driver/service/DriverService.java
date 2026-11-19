package ma.youcode.covoiturage.feature.driver.service;

import ma.youcode.covoiturage.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.request.DriverSearchCriteria;
import ma.youcode.covoiturage.feature.driver.dto.request.UpdateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.response.DriverResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {
    DriverResponseDTO createDriver(CreateDriverRequestDTO requestDTO);

    Page<DriverResponseDTO> getAllDrivers(Pageable pageable);

    DriverResponseDTO getDriverById(Long id);

    DriverResponseDTO updateDriver(Long id, UpdateDriverRequestDTO requestDTO);

    Page<DriverResponseDTO> searchDrivers(DriverSearchCriteria criteria, Pageable pageable);
}
