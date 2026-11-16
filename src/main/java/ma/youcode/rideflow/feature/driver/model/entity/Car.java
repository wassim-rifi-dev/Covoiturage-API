package ma.youcode.rideflow.feature.driver.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    private String color;

    @Min(1)
    @Max(8)
    @Column(name = "passenger_capacity")
    private int passengerCapacity;
}
