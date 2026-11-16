package ma.youcode.rideflow.feature.driver.repository;

import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver , Long> {
}
