package ma.youcode.rideflow.feature.driver.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drivers")
@PrimaryKeyJoinColumn(name = "id")
public class Driver {
    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;
}
