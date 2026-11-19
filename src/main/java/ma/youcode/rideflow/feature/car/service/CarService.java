package ma.youcode.rideflow.feature.car.service;

import ma.youcode.rideflow.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.rideflow.feature.car.dto.request.UpdateCarRequestDTO;
import ma.youcode.rideflow.feature.car.dto.response.CarResponseDTO;

import ma.youcode.rideflow.feature.driver.dto.response.DriverCarsResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CarService {
    public CarResponseDTO createCar(Long driverId , CreateCarRequestDTO requestDTO);

    public CarResponseDTO getCarById(Long id);

    public CarResponseDTO updateCar(Long id, UpdateCarRequestDTO requestDTO);

    public DriverCarsResponseDTO getCarsByDriverId(Long driverId, Pageable pageable);

    public void deleteCar(Long id);
}
