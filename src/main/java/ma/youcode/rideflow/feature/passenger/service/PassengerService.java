package ma.youcode.rideflow.feature.passenger.service;

import ma.youcode.rideflow.feature.passenger.dto.request.CreatePassengeRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.request.UpdatePassengerRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.response.PassengerResponseDTO;

public interface PassengerService {
    public PassengerResponseDTO createPassenger(CreatePassengeRequestDTO requestDTO);

    PassengerResponseDTO updatePassenger(Long id, UpdatePassengerRequestDTO requestDTO);
}
