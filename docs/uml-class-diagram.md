# RideFlow — Diagramme de classes UML (modèle de données)

Généré à partir des entités JPA de `src/main/java/ma/youcode/rideflow/feature/**/model`.

```mermaid
classDiagram
    direction LR

    class User {
        <<abstract>>
        -Long id
        -String firstName
        -String lastName
        -String email  «unique»
        -String phone  «unique»
    }

    class Driver {
        -String licenseNumber  «unique»
    }

    class Passenger

    class Car {
        -Long id
        -String brand
        -String model
        -String licensePlate  «unique»
        -String color
        -int passengerCapacity  [1..8]
    }

    class Trip {
        -Long id
        -String departureCity
        -String arrivalCity
        -LocalDateTime departureDateTime
        -int proposedSeats
        -TripStatus status
        +addWaypoint(Waypoint) void
        +removeWaypoint(Waypoint) void
    }

    class Waypoint {
        -Long id
        -String city
        -int order  [>= 0]
        -LocalDateTime passageTime
        -BigDecimal priceFromStart
    }

    class Booking {
        -Long id
        -int numberOfSeats
        -BigDecimal totalAmount
        -LocalDateTime bookingDate
        -BookingStatus status
    }

    class TripStatus {
        <<enumeration>>
        PUBLISHED
        FULL
        COMPLETED
        CANCELLED
    }

    class BookingStatus {
        <<enumeration>>
        CONFIRMED
        CANCELLED
    }

    User <|-- Driver
    User <|-- Passenger

    Driver "1" <-- "0..*" Car : driver
    Trip "1" *-- "0..*" Waypoint : waypoints (ordonnés par order)
    Passenger "1" <-- "0..*" Booking : passenger
    Waypoint "1" <-- "0..*" Booking : pickupPoint
    Waypoint "1" <-- "0..*" Booking : dropoffPoint

    Trip ..> TripStatus
    Booking ..> BookingStatus
```

## Tables (héritage JOINED)

| Entité | Table | Clés étrangères |
|---|---|---|
| User (abstract) | `users` | — |
| Driver | `drivers` | `id` → `users.id` |
| Passenger | `passengers` | `id` → `users.id` |
| Car | `cars` | `driver_id` → `drivers.id` |
| Trip | `trips` | — |
| Waypoint | `waypoints` | `trip_id` → `trips.id` |
| Booking | `bookings` | `passenger_id`, `pickup_point_id`, `dropoff_point_id` |
