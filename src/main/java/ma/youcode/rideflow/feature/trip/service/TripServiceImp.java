package ma.youcode.rideflow.feature.trip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.common.exception.*;
import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.car.repository.CarRepository;
import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import ma.youcode.rideflow.feature.driver.repository.DriverRepository;
import ma.youcode.rideflow.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.request.TripSearchCriteria;
import ma.youcode.rideflow.feature.trip.dto.request.WaypointRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.rideflow.feature.trip.exception.*;
import ma.youcode.rideflow.feature.trip.mapper.TripMapper;
import ma.youcode.rideflow.feature.trip.mapper.WaypointMapper;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import ma.youcode.rideflow.feature.trip.model.entity.Waypoint;
import ma.youcode.rideflow.feature.trip.model.enums.TripStatus;
import ma.youcode.rideflow.feature.trip.repository.TripRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripServiceImp implements TripService {
    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    private final WaypointMapper waypointMapper;

    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    private final WaypointService waypointService;

    @Override
    @Transactional
    public TripResponseDTO createTrip(Long driverId, CreateTripRequestDTO requestDTO) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new UserNotFoundException("Driver not found with id=" + driverId));

        Car car = carRepository.findById(requestDTO.carId())
                .orElseThrow(() -> new CarNotFoundException("Car not found with id=" + requestDTO.carId()));

        carBelongsToDriver(car, driverId);
        seatsWithinCarCapacity(requestDTO.proposedSeats(), car);
        waypointService.waypointsWithinTrip(requestDTO);

        Trip trip = tripMapper.toEntity(requestDTO);
        trip.setDriver(driver);
        trip.setCar(car);
        trip.setStatus(TripStatus.PUBLISHED);

        buildRoute(trip, requestDTO);

        Trip createdTrip = tripRepository.save(trip);

        return tripMapper.toResponseDto(createdTrip);
    }

    @Override
    public boolean tripExistByCarAndStatuNotIn(Car car) {
        return tripRepository.existsByCarIdAndStatusNotIn(
                car,
                List.of(TripStatus.COMPLETED, TripStatus.CANCELLED)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TripResponseDTO> searchTrips(TripSearchCriteria criteria, Pageable pageable) {
        LocalDate date = criteria.departureDate();

        return tripRepository.searchTrips(
                        toLikePattern(criteria.departureCity()),
                        toLikePattern(criteria.arrivalCity()),
                        date != null ? date.atStartOfDay() : null,
                        date != null ? date.plusDays(1).atStartOfDay() : null,
                        criteria.maxPrice(),
                        criteria.minSeats(),
                        toLikePattern(criteria.waypointCity()),
                        pageable
                )
                .map(tripMapper::toResponseDto);
    }

    private String toLikePattern(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return "%" + value.trim().toLowerCase() + "%";
    }

    @Override
    public TripResponseDTO getTripById(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip not existe with id=" + id));

        return tripMapper.toResponseDto(trip);
    }

    @Override
    @Transactional
    public void cancelTrip(Long driverId , Long tripId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new UserNotFoundException("Driver not exist with id=" + driverId));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not existe with id=" + tripId));

        if (trip.getDriver().getId().equals(driver.getId())) {
            throw new DriverNotAuthorizedForTripException("This driver don't have permission to trip with id=" + tripId);
        }

        trip.cancel();

        tripRepository.save(trip);
    }

    private void carBelongsToDriver(Car car, Long driverId) {
        if (!car.getDriver().getId().equals(driverId)) {
            throw new CarDoesNotBelongToDriverException("This car does not belong to driver with id=" + driverId);
        }
    }

    private void seatsWithinCarCapacity(int proposedSeats, Car car) {
        if (proposedSeats > car.getPassengerCapacity()) {
            throw new SeatsExceedCarCapacityException(
                    "Proposed seats (" + proposedSeats + ") exceed car capacity (" + car.getPassengerCapacity() + ")."
            );
        }
    }

    private void buildRoute(Trip trip, CreateTripRequestDTO requestDTO) {
        trip.addWaypoint(Waypoint.builder()
                .city(requestDTO.departureCity())
                .order(0)
                .passageTime(requestDTO.departureDateTime())
                .priceFromStart(BigDecimal.ZERO)
                .build());

        List<Waypoint> waypointsIntermediates = waypointMapper.toEntityList(waypointService.sortIntermediateWaypoints(requestDTO));

        int order = 1;
        for (Waypoint waypoint : waypointsIntermediates) {
            waypoint.setOrder(order++);
            trip.addWaypoint(waypoint);
        }

        trip.addWaypoint(Waypoint.builder()
                .city(requestDTO.arrivalCity())
                .order(order)
                .passageTime(requestDTO.arrivalDateTime())
                .priceFromStart(requestDTO.price())
                .build());
    }
}
