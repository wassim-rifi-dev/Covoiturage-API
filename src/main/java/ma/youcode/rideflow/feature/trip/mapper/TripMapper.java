package ma.youcode.rideflow.feature.trip.mapper;

import ma.youcode.rideflow.feature.car.mapper.CarMapper;
import ma.youcode.rideflow.feature.driver.mapper.DriverMapper;
import ma.youcode.rideflow.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {WaypointMapper.class, DriverMapper.class, CarMapper.class})
public interface TripMapper {
    TripResponseDTO toResponseDto(Trip trip);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "waypoints", defaultExpression = "java(new java.util.ArrayList<>())")
    Trip toEntity(CreateTripRequestDTO createTripRequestDTO);

    @AfterMapping
    default void linkWaypoints(@MappingTarget Trip trip) {
        trip.getWaypoints().forEach(waypoint -> waypoint.setTrip(trip));
    }
}
