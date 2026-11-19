package ma.youcode.covoiturage.feature.car.mapper;

import ma.youcode.covoiturage.feature.car.dto.request.CreateCarRequestDTO;
import ma.youcode.covoiturage.feature.car.dto.request.UpdateCarRequestDTO;
import ma.youcode.covoiturage.feature.car.dto.response.CarResponseDTO;
import ma.youcode.covoiturage.feature.car.dto.response.CarSummaryResponseDTO;
import ma.youcode.covoiturage.feature.car.model.entity.Car;
import ma.youcode.covoiturage.feature.driver.mapper.DriverMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = DriverMapper.class)
public interface CarMapper {
    CarResponseDTO toResponseDto(Car car);

    CarSummaryResponseDTO toSummaryDto(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    Car toEntity(CreateCarRequestDTO createCarRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    void updateEntity(UpdateCarRequestDTO updateCarRequestDTO, @MappingTarget Car car);
}
