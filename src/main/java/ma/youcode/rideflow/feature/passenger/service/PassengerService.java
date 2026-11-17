package ma.youcode.rideflow.feature.passenger.service;

import ma.youcode.rideflow.feature.passenger.dto.request.CreatePassengeRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.request.PassengerSearchCriteria;
import ma.youcode.rideflow.feature.passenger.dto.request.UpdatePassengerRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.response.PassengerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassengerService {
    public PassengerResponseDTO createPassenger(CreatePassengeRequestDTO requestDTO);

    PassengerResponseDTO updatePassenger(Long id, UpdatePassengerRequestDTO requestDTO);

    Page<PassengerResponseDTO> searchPassengers(PassengerSearchCriteria criteria, Pageable pageable);
}
