package ma.youcode.covoiturage.feature.trip.mapper;

import ma.youcode.covoiturage.feature.car.mapper.CarMapper;
import ma.youcode.covoiturage.feature.driver.mapper.DriverMapper;
import ma.youcode.covoiturage.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.covoiturage.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.covoiturage.feature.trip.model.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WaypointMapper.class, DriverMapper.class, CarMapper.class})
public interface TripMapper {
    TripResponseDTO toResponseDto(Trip trip);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "waypoints", ignore = true)
    Trip toEntity(CreateTripRequestDTO createTripRequestDTO);
}
