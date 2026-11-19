package ma.youcode.rideflow.feature.trip.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import ma.youcode.rideflow.feature.booking.model.enums.BookingStatus;
import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import ma.youcode.rideflow.feature.trip.exception.InvalidTripStatusException;
import ma.youcode.rideflow.feature.trip.exception.WaypointNotFoundException;
import ma.youcode.rideflow.feature.trip.model.enums.TripStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order ASC")
    @Builder.Default
    private List<Waypoint> waypoints = new ArrayList<>();

    @OneToMany(mappedBy = "trip")
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
        waypoint.setTrip(this);
    }

    public void removeWaypoint(Waypoint waypoint) {
        waypoints.remove(waypoint);
        waypoint.setTrip(null);
    }

    public int getRemainingSeats() {
        int bookedSeats = bookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        return proposedSeats - bookedSeats;
    }

    public boolean isBookable() {
        return status == TripStatus.PUBLISHED
                && departureDateTime.isAfter(LocalDateTime.now())
                && getRemainingSeats() > 0;
    }

    public Waypoint findWaypoint(String city) {
        return waypoints.stream()
                .filter(waypoint -> waypoint.getCity().equalsIgnoreCase(city.trim()))
                .findFirst()
                .orElseThrow(() -> new WaypointNotFoundException(
                        "No stop point found in city '" + city + "' for trip " + id
                ));
    }

    public void complete() {
        if (status != TripStatus.PUBLISHED && status != TripStatus.FULL) {
            throw new InvalidTripStatusException(
                    "Trip " + id + " cannot be completed from status " + status
            );
        }

        status = TripStatus.COMPLETED;
    }

    public void cancel() {
        if (status == TripStatus.COMPLETED || status == TripStatus.CANCELLED) {
            throw new InvalidTripStatusException(
                    "Trip " + id + " cannot be cancelled from status " + status
            );
        }

        status = TripStatus.CANCELLED;

        bookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED)
                .forEach(booking -> booking.setStatus(BookingStatus.CANCELLED));
    }
}
