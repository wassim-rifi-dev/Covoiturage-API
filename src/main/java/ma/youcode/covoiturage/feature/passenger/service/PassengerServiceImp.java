package ma.youcode.covoiturage.feature.passenger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.UserNotFoundException;
import ma.youcode.covoiturage.feature.passenger.dto.request.CreatePassengeRequestDTO;
import ma.youcode.covoiturage.feature.passenger.dto.request.PassengerSearchCriteria;
import ma.youcode.covoiturage.feature.passenger.dto.request.UpdatePassengerRequestDTO;
import ma.youcode.covoiturage.feature.passenger.specification.PassengerSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ma.youcode.covoiturage.feature.passenger.dto.response.PassengerResponseDTO;
import ma.youcode.covoiturage.feature.passenger.mapper.PassengerMapper;
import ma.youcode.covoiturage.feature.passenger.model.entity.Passenger;
import ma.youcode.covoiturage.feature.passenger.repository.PassengerRepository;
import ma.youcode.covoiturage.feature.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImp implements PassengerService {
    private final UserService userService;

    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;

    @Transactional
    @Override
    public PassengerResponseDTO createPassenger(CreatePassengeRequestDTO requestDTO) {
        userService.userExistByEmail(requestDTO.email());

        Passenger passenger = passengerMapper.toEntity(requestDTO);

        Passenger createdPassenger = passengerRepository.save(passenger);

        log.info("Passenger user created: passenger_id= {}" , createdPassenger.getId());

        return passengerMapper.toResponseDto(createdPassenger);
    }

    @Transactional
    @Override
    public PassengerResponseDTO updatePassenger(Long id, UpdatePassengerRequestDTO requestDTO) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Passenger not found with id=" + id));

        userService.userIdNotAndExistByEmail(id, requestDTO.email());

        passengerMapper.updateEntity(requestDTO, passenger);

        Passenger updatedPassenger = passengerRepository.save(passenger);

        log.info("Passenger user updated: passenger_id= {}", updatedPassenger.getId());

        return passengerMapper.toResponseDto(updatedPassenger);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PassengerResponseDTO> searchPassengers(PassengerSearchCriteria criteria, Pageable pageable) {
        Specification<Passenger> spec = Specification.allOf(
                PassengerSpecifications.hasFullName(criteria.fullName()),
                PassengerSpecifications.hasEmail(criteria.email()),
                PassengerSpecifications.hasPhone(criteria.phone())
        );

        return passengerRepository.findAll(spec, pageable)
                .map(passengerMapper::toResponseDto);
    }
}
