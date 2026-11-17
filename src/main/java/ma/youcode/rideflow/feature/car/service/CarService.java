package ma.youcode.rideflow.feature.car.service;

import ma.youcode.rideflow.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.rideflow.feature.car.dto.response.CarResponseDTO;

public interface CarService {
    public CarResponseDTO createCar(Long driverId , CreateCarRequestDTO requestDTO);
}
