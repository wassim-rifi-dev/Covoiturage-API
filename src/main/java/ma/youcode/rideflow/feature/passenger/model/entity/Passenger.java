package ma.youcode.rideflow.feature.passenger.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.youcode.rideflow.feature.user.model.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passengers")
@PrimaryKeyJoinColumn(name = "id")
public class Passenger extends User {
}
