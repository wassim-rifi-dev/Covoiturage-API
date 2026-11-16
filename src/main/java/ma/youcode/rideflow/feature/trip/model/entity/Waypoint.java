package ma.youcode.rideflow.feature.trip.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "waypoints")
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Min(0)
    @Column(nullable = false)
    private int order;

    @Column(name = "passage_time", nullable = false)
    private LocalDateTime passageTime;

    @Column(name = "price_from_start", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceFromStart;
}
