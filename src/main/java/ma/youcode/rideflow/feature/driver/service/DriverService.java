package ma.youcode.rideflow.feature.driver.service;

import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;

public interface DriverService {
    DriverResponseDTO createDriver(CreateDriverRequestDTO requestDTO);
}
