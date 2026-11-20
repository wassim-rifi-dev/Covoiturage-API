package ma.youcode.covoiturage.feature.trip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.*;
import ma.youcode.covoiturage.feature.car.model.entity.Car;
import ma.youcode.covoiturage.feature.car.repository.CarRepository;
import ma.youcode.covoiturage.feature.driver.model.entity.Driver;
import ma.youcode.covoiturage.feature.driver.repository.DriverRepository;
import ma.youcode.covoiturage.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.covoiturage.feature.trip.dto.request.TripSearchCriteria;
import ma.youcode.covoiturage.feature.trip.dto.request.WaypointRequestDTO;
import ma.youcode.covoiturage.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.covoiturage.feature.trip.exception.*;
import ma.youcode.covoiturage.feature.trip.mapper.TripMapper;
import ma.youcode.covoiturage.feature.trip.mapper.WaypointMapper;
import ma.youcode.covoiturage.feature.trip.model.entity.Trip;
import ma.youcode.covoiturage.feature.trip.model.entity.Waypoint;
import ma.youcode.covoiturage.feature.trip.model.enums.TripStatus;
import ma.youcode.covoiturage.feature.trip.repository.TripRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        carIsAvailableDuringTrip(car, requestDTO.departureDateTime(), requestDTO.arrivalDateTime());
        waypointService.waypointsWithinTrip(requestDTO);

        Trip trip = tripMapper.toEntity(requestDTO);
        trip.setDriver(driver);
        trip.setCar(car);
        trip.setStatus(TripStatus.PUBLISHED);

        buildRoute(trip, requestDTO);

        if (!trip.getDepartureDateTime().isAfter(LocalDateTime.now())) {
            throw new DepartureDateTimeMustBeInFutureException("Departure date and time must be in the future.");
        }

        if ((long) trip.getWaypoints().size() < 2) {
            throw new InsufficientWaypointsException("At least 2 waypoints are required to create a trip.");
        }

        citiesAreUnique(trip.getWaypoints());
        pricesStartAtZeroAndStrictlyIncrease(trip.getWaypoints());

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
                .orElseThrow(() -> new TripNotFoundException("Trip not exist with id=" + tripId));

        if (!trip.getDriver().getId().equals(driver.getId())) {
            throw new DriverNotAuthorizedForTripException("This driver don't have permission to trip with id=" + tripId);
        }

        trip.cancel();

        tripRepository.save(trip);
    }

    @Override
    @Transactional
    public void completTrip(Long driverId, Long tripId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new UserNotFoundException("Driver not exist with id=" + driverId));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not exist with id=" + tripId));

        if (trip.getDriver().getId().equals(driver.getId())) {
            throw new DriverNotAuthorizedForTripException("This driver don't have permission to trip with id=" + tripId);
        }

        trip.complete();

        tripRepository.save(trip);
    }

    private void carBelongsToDriver(Car car, Long driverId) {
        if (!car.getDriver().getId().equals(driverId)) {
            throw new CarDoesNotBelongToDriverException("This car does not belong to driver with id=" + driverId);
        }
    }

    private void carIsAvailableDuringTrip(Car car, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        boolean overlaps = tripRepository.existsOverlappingTripForCar(
                car.getId(),
                departureDateTime,
                arrivalDateTime,
                List.of(TripStatus.COMPLETED, TripStatus.CANCELLED)
        );

        if (overlaps) {
            throw new CarAlreadyHasTripInTimeRangeException(
                    "Car with id=" + car.getId() + " already has a trip between "
                            + departureDateTime + " and " + arrivalDateTime + "."
            );
        }
    }

    private void seatsWithinCarCapacity(int proposedSeats, Car car) {
        if (proposedSeats > car.getPassengerCapacity()) {
            throw new SeatsExceedCarCapacityException(
                    "Proposed seats (" + proposedSeats + ") exceed car capacity (" + car.getPassengerCapacity() + ")."
            );
        }
    }

    private void citiesAreUnique(List<Waypoint> waypoints) {
        Set<String> cities = new HashSet<>();

        for (Waypoint waypoint : waypoints) {
            String city = waypoint.getCity().trim().toLowerCase();

            if (!cities.add(city)) {
                throw new DuplicateWaypointCityException(
                        "City '" + waypoint.getCity().trim() + "' appears more than once in the trip route."
                );
            }
        }
    }

    private void pricesStartAtZeroAndStrictlyIncrease(List<Waypoint> waypoints) {
        BigDecimal previousPrice = null;

        for (Waypoint waypoint : waypoints) {
            BigDecimal price = waypoint.getPriceFromStart();

            if (price == null) {
                throw new InvalidWaypointPriceException(
                        "Price is missing for city '" + waypoint.getCity() + "'."
                );
            }

            if (previousPrice == null) {
                if (price.compareTo(BigDecimal.ZERO) != 0) {
                    throw new InvalidWaypointPriceException("Price at departure must be 0.");
                }
            } else if (price.compareTo(previousPrice) <= 0) {
                throw new InvalidWaypointPriceException(
                        "Price at '" + waypoint.getCity() + "' (" + price
                                + ") must be strictly greater than the previous waypoint price (" + previousPrice + ")."
                );
            }

            previousPrice = price;
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
