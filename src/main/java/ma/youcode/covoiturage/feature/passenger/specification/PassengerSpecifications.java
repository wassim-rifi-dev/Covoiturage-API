package ma.youcode.covoiturage.feature.passenger.specification;

import ma.youcode.covoiturage.feature.passenger.model.entity.Passenger;
import org.springframework.data.jpa.domain.Specification;

public final class PassengerSpecifications {

    private PassengerSpecifications() {
    }

    public static Specification<Passenger> hasFullName(String fullName) {
        return (root, query, cb) -> {
            if (fullName == null || fullName.isBlank()) {
                return cb.conjunction();
            }

            String pattern = "%" + fullName.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern),
                    cb.like(
                            cb.lower(cb.concat(cb.concat(root.get("firstName"), " "), root.get("lastName"))),
                            pattern
                    )
            );
        };
    }

    public static Specification<Passenger> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("email")), "%" + email.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Passenger> hasPhone(String phone) {
        return (root, query, cb) -> {
            if (phone == null || phone.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(root.get("phone"), "%" + phone.trim() + "%");
        };
    }
}
