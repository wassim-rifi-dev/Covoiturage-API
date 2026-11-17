package ma.youcode.rideflow.feature.passenger.mapper;

import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.passenger.dto.response.PassengerResponseDTO;
import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerResponseDTO toResponseDto(Passenger passenger);

    @Mapping(target = "id", ignore = true)
    Passenger toEntity(CreateDriverRequestDTO createDriverRequestDTO);
}
