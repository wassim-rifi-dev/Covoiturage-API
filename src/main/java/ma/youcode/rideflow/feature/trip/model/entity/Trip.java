package ma.youcode.rideflow.feature.trip.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.rideflow.feature.trip.model.enums.TripStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_city", nullable = false)
    private String departureCity;

    @Column(name = "arrival_city", nullable = false)
    private String arrivalCity;

    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "proposed_seats", nullable = false)
    private int proposedSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;
}
