package ma.youcode.covoiturage.feature.driver.dto.response;

import ma.youcode.covoiturage.feature.car.dto.response.CarSummaryResponseDTO;
import org.springframework.data.web.PagedModel;

import java.util.List;

public record DriverCarsResponseDTO(
        DriverResponseDTO driver,

        List<CarSummaryResponseDTO> cars,

        PagedModel.PageMetadata page
) {}
