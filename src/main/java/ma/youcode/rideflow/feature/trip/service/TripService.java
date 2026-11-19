package ma.youcode.rideflow.feature.trip.service;

import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.request.TripSearchCriteria;
import ma.youcode.rideflow.feature.trip.dto.response.TripResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {
    public TripResponseDTO createTrip(Long driverId, CreateTripRequestDTO requestDTO);

    public boolean tripExistByCarAndStatuNotIn(Car car);

    public Page<TripResponseDTO> searchTrips(TripSearchCriteria criteria, Pageable pageable);
}
