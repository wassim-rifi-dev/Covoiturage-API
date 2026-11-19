package ma.youcode.rideflow.feature.driver.dto.response;

import ma.youcode.rideflow.feature.car.dto.response.CarSummaryResponseDTO;
import org.springframework.data.web.PagedModel;

import java.util.List;

public record DriverCarsResponseDTO(
        DriverResponseDTO driver,

        List<CarSummaryResponseDTO> cars,

        PagedModel.PageMetadata page
) {}
