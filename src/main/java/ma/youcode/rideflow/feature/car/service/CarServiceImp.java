package ma.youcode.rideflow.feature.car.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.common.exception.CarNotFoundException;
import ma.youcode.rideflow.common.exception.LicensePlateAlreadyExistsException;
import ma.youcode.rideflow.common.exception.UserNotFoundException;
import ma.youcode.rideflow.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.rideflow.feature.car.dto.request.UpdateCarRequestDTO;
import ma.youcode.rideflow.feature.car.dto.response.CarResponseDTO;
import ma.youcode.rideflow.feature.car.mapper.CarMapper;
import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.car.repository.CarRepository;
import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import ma.youcode.rideflow.feature.driver.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImp implements CarService {
    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final DriverRepository driverRepository;

    @Override
    @Transactional
    public CarResponseDTO createCar(Long driverId, CreateCarRequestDTO requestDTO) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new UserNotFoundException("Driver not found with id=" + driverId));

        carExistByLicensePlate(requestDTO.licensePlate());

        Car car = carMapper.toEntity(requestDTO);
        car.setDriver(driver);

        Car createdCar = carRepository.save(car);

        return carMapper.toResponseDto(createdCar);
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDTO getCarById(Long id) {
        Car car = findCarById(id);

        return carMapper.toResponseDto(car);
    }

    @Override
    @Transactional
    public CarResponseDTO updateCar(Long id, UpdateCarRequestDTO requestDTO) {
        Car car = findCarById(id);

        if (carRepository.existsByLicensePlateAndIdNot(requestDTO.licensePlate(), id)) {
            throw new LicensePlateAlreadyExistsException("License plate already exists.");
        }

        carMapper.updateEntity(requestDTO, car);

        Car updatedCar = carRepository.save(car);

        return carMapper.toResponseDto(updatedCar);
    }

    private Car findCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id=" + id));
    }

    private void carExistByLicensePlate(String licensePlate) {
        if (carRepository.existsByLicensePlate(licensePlate)) {
            throw new LicensePlateAlreadyExistsException("License plate already exists.");
        }
    }
}
