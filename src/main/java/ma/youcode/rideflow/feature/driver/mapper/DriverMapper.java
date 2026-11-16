package ma.youcode.rideflow.feature.driver.mapper;

import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponseDTO toResponseDto(Driver driver);

    @Mapping(target = "id", ignore = true)
    Driver toEntity(CreateDriverRequestDTO createDriverRequestDTO);
}
