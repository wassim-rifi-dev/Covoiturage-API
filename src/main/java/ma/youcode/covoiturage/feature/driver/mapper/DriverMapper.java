package ma.youcode.covoiturage.feature.driver.mapper;

import ma.youcode.covoiturage.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.request.UpdateDriverRequestDTO;
import ma.youcode.covoiturage.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.covoiturage.feature.driver.model.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponseDTO toResponseDto(Driver driver);

    @Mapping(target = "id", ignore = true)
    Driver toEntity(CreateDriverRequestDTO createDriverRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateDriverRequestDTO updateDriverRequestDTO, @MappingTarget Driver driver);
}
