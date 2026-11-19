package ma.youcode.rideflow.feature.booking.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.rideflow.feature.booking.exception.BookingAlreadyCancelledException;
import ma.youcode.rideflow.feature.booking.model.enums.BookingStatus;
import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import ma.youcode.rideflow.feature.trip.model.entity.Waypoint;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class Booking {
    public static final long CANCELLATION_DEADLINE_HOURS = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pickup_point_id", nullable = false)
    private Waypoint pickupPoint;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dropoff_point_id", nullable = false)
    private Waypoint dropoffPoint;

    @PrePersist
    protected void onCreate() {
        this.bookingDate = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
        this.totalAmount = calculateTotalAmount();

    }

    public BigDecimal getSegmentPrice() {
        return dropoffPoint.getPriceFromStart().subtract(pickupPoint.getPriceFromStart());
    }

    public BigDecimal calculateTotalAmount() {
        return getSegmentPrice().multiply(BigDecimal.valueOf(numberOfSeats));
    }

    public boolean isCancellable() {
        return status == BookingStatus.CONFIRMED
                && LocalDateTime.now().isBefore(pickupPoint.getPassageTime().minusHours(CANCELLATION_DEADLINE_HOURS));
    }

    public void cancel() {
        if (status == BookingStatus.CANCELLED) {
            throw new BookingAlreadyCancelledException("Booking is already cancelled");
        }

        this.status = BookingStatus.CANCELLED;
    }
}
