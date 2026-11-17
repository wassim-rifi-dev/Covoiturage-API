package ma.youcode.rideflow.feature.car.mapper;

import ma.youcode.rideflow.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.rideflow.feature.car.dto.response.CarResponseDTO;
import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.driver.mapper.DriverMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DriverMapper.class)
public interface CarMapper {
    CarResponseDTO toResponseDto(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    Car toEntity(CreateCarRequestDTO createCarRequestDTO);
}
