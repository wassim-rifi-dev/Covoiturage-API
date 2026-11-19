package ma.youcode.covoiturage.feature.passenger.mapper;

import ma.youcode.covoiturage.feature.passenger.dto.request.CreatePassengeRequestDTO;
import ma.youcode.covoiturage.feature.passenger.dto.request.UpdatePassengerRequestDTO;
import ma.youcode.covoiturage.feature.passenger.dto.response.PassengerResponseDTO;
import ma.youcode.covoiturage.feature.passenger.model.entity.Passenger;
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
