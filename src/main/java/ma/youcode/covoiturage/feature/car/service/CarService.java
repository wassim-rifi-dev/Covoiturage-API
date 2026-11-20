package ma.youcode.covoiturage.feature.car.service;

import ma.youcode.covoiturage.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.covoiturage.feature.car.dto.request.UpdateCarRequestDTO;
import ma.youcode.covoiturage.feature.car.dto.response.CarResponseDTO;

import ma.youcode.covoiturage.feature.driver.dto.response.DriverCarsResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CarService {
    public CarResponseDTO createCar(Long driverId , CreateCarRequestDTO requestDTO);

    public CarResponseDTO getCarById(Long id);

    public CarResponseDTO updateCar(Long driverId, Long carId, UpdateCarRequestDTO requestDTO);

    public DriverCarsResponseDTO getCarsByDriverId(Long driverId, Pageable pageable);

    public void deleteCar(Long id);
}
