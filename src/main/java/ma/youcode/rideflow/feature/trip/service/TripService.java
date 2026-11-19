package ma.youcode.rideflow.feature.trip.service;

import ma.youcode.rideflow.feature.car.model.entity.Car;

public interface TripService {
    public boolean tripExistByCarAndStatuNotIn(Car car);
}
