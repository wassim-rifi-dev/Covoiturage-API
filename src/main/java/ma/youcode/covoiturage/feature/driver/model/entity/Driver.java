package ma.youcode.covoiturage.feature.driver.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.covoiturage.feature.user.model.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "drivers")
@PrimaryKeyJoinColumn(name = "id")
public class Driver extends User {
    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;
}
