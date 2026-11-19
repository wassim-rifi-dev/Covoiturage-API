package ma.youcode.rideflow.feature.passenger.mapper;

import ma.youcode.rideflow.feature.passenger.dto.request.CreatePassengeRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.request.UpdatePassengerRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.response.PassengerResponseDTO;
import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerResponseDTO toResponseDto(Passenger passenger);

    @Mapping(target = "id", ignore = true)
    Passenger toEntity(CreatePassengeRequestDTO createDriverRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdatePassengerRequestDTO updatePassengerRequestDTO, @MappingTarget Passenger passenger);
}
