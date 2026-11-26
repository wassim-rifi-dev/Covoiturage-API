package ma.youcode.covoiturage.feature.driver.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.covoiturage.feature.user.model.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "drivers")
public class Driver {
    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;

    @OneToOne
    @JoinColumn(name = "user_id" , nullable = false , unique = true)
    private User user;
}
