package ma.youcode.covoiturage.feature.trip.mapper;

import ma.youcode.covoiturage.feature.trip.dto.request.WaypointRequestDTO;
import ma.youcode.covoiturage.feature.trip.dto.response.WaypointResponseDTO;
import ma.youcode.covoiturage.feature.trip.model.entity.Waypoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WaypointMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Waypoint toEntity(WaypointRequestDTO waypointRequestDTO);

    List<Waypoint> toEntityList(List<WaypointRequestDTO> waypointRequestDTOs);

    WaypointResponseDTO toResponseDto(Waypoint waypoint);

    List<WaypointResponseDTO> toResponseDtoList(List<Waypoint> waypoints);
}
