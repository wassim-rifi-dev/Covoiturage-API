package ma.youcode.covoiturage.feature.passenger.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ma.youcode.covoiturage.feature.user.model.entity.User;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "passengers")
@PrimaryKeyJoinColumn(name = "id")
public class Passenger extends User {
}
