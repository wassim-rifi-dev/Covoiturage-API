package ma.youcode.rideflow.feature.trip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.trip.model.enums.TripStatus;
import ma.youcode.rideflow.feature.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripServiceImp implements TripService {
    private final TripRepository tripRepository;

    @Override
    public boolean tripExistByCarAndStatuNotIn(Car car) {
        return tripRepository.existsByCarIdAndStatusNotIn(
                car,
                List.of(TripStatus.COMPLETED, TripStatus.CANCELLED)
        );
    }
}
