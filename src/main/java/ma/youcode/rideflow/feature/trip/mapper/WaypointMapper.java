package ma.youcode.rideflow.feature.trip.mapper;

import ma.youcode.rideflow.feature.trip.dto.request.WaypointRequestDTO;
import ma.youcode.rideflow.feature.trip.model.entity.Waypoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WaypointMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Waypoint toEntity(WaypointRequestDTO waypointRequestDTO);

    List<Waypoint> toEntityList(List<WaypointRequestDTO> waypointRequestDTOs);
}
