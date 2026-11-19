package ma.youcode.covoiturage.feature.driver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.EmailAlreadyExistsException;
import ma.youcode.covoiturage.common.exception.LicenseNumberAlreadyExistsException;
import ma.youcode.covoiturage.common.exception.UserNotFoundException;
import ma.youcode.covoiturage.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.request.DriverSearchCriteria;
import ma.youcode.covoiturage.feature.driver.dto.request.UpdateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.covoiturage.feature.driver.mapper.DriverMapper;
import ma.youcode.covoiturage.feature.driver.model.entity.Driver;
import ma.youcode.covoiturage.feature.driver.repository.DriverRepository;
import ma.youcode.covoiturage.feature.driver.specification.DriverSpecifications;
import ma.youcode.covoiturage.feature.user.repository.UserRepository;
import ma.youcode.covoiturage.feature.user.service.UserService;
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
    private final UserService userService;

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    @Transactional
    @Override
    public DriverResponseDTO createDriver(CreateDriverRequestDTO requestDTO) {
        userService.userExistByEmail(requestDTO.email());
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

        userService.userIdNotAndExistByEmail(id, requestDTO.email());
        driverIdNotAndExistByLisenceNumber(id, requestDTO.licenseNumber());

        driverMapper.updateEntity(requestDTO , driver);

        Driver upatedDriver = driverRepository.save(driver);

        return driverMapper.toResponseDto(upatedDriver);
    }

    @Override
    public Page<DriverResponseDTO> searchDrivers(DriverSearchCriteria criteria, Pageable pageable) {
        Specification<Driver> spec = Specification
                .where(DriverSpecifications.hasFullName(criteria.fullName()))
                .and(DriverSpecifications.hasEmail(criteria.email()))
                .and(DriverSpecifications.hasPhone(criteria.phone()))
                .and(DriverSpecifications.hasLicenseNumber(criteria.licenseNumber()));

        return driverRepository.findAll(Specification.allOf(spec), pageable)
                .map(driverMapper::toResponseDto);
    }

    private void driverExisteByLicenseNumber(String licenseNumber) {
        if (driverRepository.existsByLicenseNumber(licenseNumber)) {
            throw new LicenseNumberAlreadyExistsException("License number already existe.");
        }
    }

    private void driverIdNotAndExistByLisenceNumber(Long id , String licenseNumber) {
        if (driverRepository.existsByLicenseNumberAndIdNot(licenseNumber, id)) {
            throw new LicenseNumberAlreadyExistsException("License number already existe.");
        }
    }
}
