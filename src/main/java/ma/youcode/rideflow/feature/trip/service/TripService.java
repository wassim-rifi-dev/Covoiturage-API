package ma.youcode.rideflow.feature.trip.service;

import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.response.TripResponseDTO;

public interface TripService {
    public TripResponseDTO createTrip(Long driverId, CreateTripRequestDTO requestDTO);

    public boolean tripExistByCarAndStatuNotIn(Car car);
}
