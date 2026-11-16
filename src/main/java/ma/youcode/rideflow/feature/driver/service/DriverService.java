package ma.youcode.rideflow.feature.driver.service;

import lombok.RequiredArgsConstructor;
import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.rideflow.feature.driver.mapper.DriverMapper;
import ma.youcode.rideflow.feature.driver.repository.DriverRepository;
import ma.youcode.rideflow.feature.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final UserRepository userRepository;

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    public DriverResponseDTO createDriver(CreateDriverRequestDTO createDriverRequestDTO) {}
}
