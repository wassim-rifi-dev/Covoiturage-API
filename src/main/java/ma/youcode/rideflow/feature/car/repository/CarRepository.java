package ma.youcode.rideflow.feature.car.repository;

import ma.youcode.rideflow.feature.car.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
