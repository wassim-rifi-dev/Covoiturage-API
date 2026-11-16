package ma.youcode.rideflow.feature.driver;

import ma.youcode.rideflow.common.exception.EmailAlreadyExistsException;
import ma.youcode.rideflow.common.exception.LicenseNumberAlreadyExistsException;
import ma.youcode.rideflow.feature.driver.dto.request.CreateDriverRequestDTO;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.rideflow.feature.driver.mapper.DriverMapper;
import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import ma.youcode.rideflow.feature.driver.repository.DriverRepository;
import ma.youcode.rideflow.feature.driver.service.DriverServiceImpl;
import ma.youcode.rideflow.feature.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {
    @Mock
    private DriverRepository driverRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DriverMapper driverMapper;

    @InjectMocks
    private DriverServiceImpl driverService;

    @Test
    void createDriver_shouldReturnResponseDTO_whenEmailDoesNotExistAndLicenseNumberNotExist() {

        // Arrange
        CreateDriverRequestDTO requestDTO = new CreateDriverRequestDTO(
                "test",
                "app",
                "test@gmail.com",
                "+212611223344",
                "145263"
        );

        Driver driver = new Driver();

        DriverResponseDTO responseDTO = DriverResponseDTO.builder()
                .id(1L)
                .firstName("test")
                .lastName("app")
                .email("test@gmail.com")
                .phone("+212611223344")
                .licenseNumber("145263")
                .build();

        when(userRepository.existsByEmail(requestDTO.email())).thenReturn(false);
        when(driverRepository.existsByLicenseNumber(requestDTO.licenseNumber())).thenReturn(false);
        when(driverMapper.toEntity(requestDTO)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(driverMapper.toResponseDto(driver)).thenReturn(responseDTO);

        // Act
        DriverResponseDTO result = driverService.createDriver(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(responseDTO , result);

        // Verify
        verify(userRepository).existsByEmail(requestDTO.email());
        verify(driverRepository).existsByLicenseNumber(requestDTO.licenseNumber());
        verify(driverMapper).toEntity(requestDTO);
        verify(driverRepository).save(driver);
        verify(driverMapper).toResponseDto(driver);
    }

    @Test
    void createDriver_shouldThrowEmailAlreadyExistsException_whenEmailExist() {

        // Arrange
        CreateDriverRequestDTO requestDTO = new CreateDriverRequestDTO(
                "test",
                "app",
                "test@gmail.com",
                "+212611223344",
                "145263"
        );

        when(userRepository.existsByEmail(requestDTO.email())).thenReturn(true);

        // Act
        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> driverService.createDriver(requestDTO)
        );

        // Assert
        assertEquals("Email already existe.", exception.getMessage());

        // Verify
        verify(userRepository).existsByEmail(requestDTO.email());
        verify(driverRepository, never()).existsByLicenseNumber(any());
        verify(driverMapper, never()).toEntity(any());
        verify(driverRepository, never()).save(any());
        verify(driverMapper, never()).toResponseDto(any());
    }

    @Test
    void createDriver_shouldThrowLicenseNumberAlreadyExistsException_whenLicenseNumberExist() {

        // Arrange
        CreateDriverRequestDTO requestDTO = new CreateDriverRequestDTO(
                "test",
                "app",
                "test@gmail.com",
                "+212611223344",
                "145263"
        );

        when(userRepository.existsByEmail(requestDTO.email())).thenReturn(false);
        when(driverRepository.existsByLicenseNumber(requestDTO.licenseNumber())).thenReturn(true);

        // Act
        LicenseNumberAlreadyExistsException exception = assertThrows(
                LicenseNumberAlreadyExistsException.class,
                () -> driverService.createDriver(requestDTO)
        );

        // Assert
        assertEquals("License number already existe.", exception.getMessage());

        // Verify
        verify(userRepository).existsByEmail(requestDTO.email());
        verify(driverRepository).existsByLicenseNumber(requestDTO.licenseNumber());
        verify(driverMapper, never()).toEntity(any());
        verify(driverRepository, never()).save(any());
        verify(driverMapper, never()).toResponseDto(any());
    }
}
