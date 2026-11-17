package ma.youcode.rideflow.feature.driver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.common.exception.EmailAlreadyExistsException;
import ma.youcode.rideflow.common.exception.LicenseNumberAlreadyExistsException;
import ma.youcode.rideflow.common.exception.UserNotFoundException;
import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.request.DriverSearchCriteria;
import ma.youcode.rideflow.feature.driver.dto.request.UpdateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.rideflow.feature.driver.mapper.DriverMapper;
import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import ma.youcode.rideflow.feature.driver.repository.DriverRepository;
import ma.youcode.rideflow.feature.driver.specification.DriverSpecifications;
import ma.youcode.rideflow.feature.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final UserRepository userRepository;

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    @Transactional
    @Override
    public DriverResponseDTO createDriver(CreateDriverRequestDTO requestDTO) {
        driverExisteByEmail(requestDTO.email());
        driverExisteByLicenseNumber(requestDTO.licenseNumber());

        Driver driver = driverMapper.toEntity(requestDTO);

        Driver createdDriver = driverRepository.save(driver);

        log.info("Driver user created: driver_id= {}" , createdDriver.getId());

        return driverMapper.toResponseDto(createdDriver);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DriverResponseDTO> getAllDrivers(Pageable pageable) {
        return driverRepository.findAll(pageable)
                .map(driverMapper::toResponseDto);
    }

    @Override
    public DriverResponseDTO getDriverById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Driver not found with id=" + id));

        return driverMapper.toResponseDto(driver);
    }

    @Transactional
    @Override
    public DriverResponseDTO updateDriver(Long id, UpdateDriverRequestDTO requestDTO) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Driver not found with id=" + id));

        if (userRepository.existsByEmailAndIdNot(requestDTO.email(), id)) {
            throw new EmailAlreadyExistsException("Email already existe.");
        }

        if (driverRepository.existsByLicenseNumberAndIdNot(requestDTO.licenseNumber(), id)) {
            throw new LicenseNumberAlreadyExistsException("License number already existe.");
        }

        driverMapper.updateEntity(requestDTO , driver);

        Driver upatedDriver = driverRepository.save(driver);

        return driverMapper.toResponseDto(upatedDriver);
    }

    @Override
    public Page<DriverResponseDTO> searchDrivers(DriverSearchCriteria criteria, Pageable pageable) {
        List<Specification<Driver>> specs = new ArrayList<>();

        if (criteria.fullName() != null) {
            specs.add(DriverSpecifications.hasFullName(criteria.fullName()))
        }

        if (criteria.email() != null) {
            specs.add(DriverSpecifications.hasEmail(criteria.email()));
        }

        if (criteria.phone() != null) {
            specs.add(DriverSpecifications.hasPhone(criteria.phone()));
        }

        if (criteria.licenseNumber() != null) {
            specs.add(DriverSpecifications.hasLicenseNumber(criteria.licenseNumber()));
        }

        return driverRepository.findAll(Specification.allOf(specs), pageable)
                .map(driverMapper::toResponseDto);
    }

    private void driverExisteByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already existe.");
        }
    }

    private void driverExisteByLicenseNumber(String licenseNumber) {
        if (driverRepository.existsByLicenseNumber(licenseNumber)) {
            throw new LicenseNumberAlreadyExistsException("License number already existe.");
        }
    }
}
