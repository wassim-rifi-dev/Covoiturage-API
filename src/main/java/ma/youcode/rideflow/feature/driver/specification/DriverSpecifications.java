package ma.youcode.rideflow.feature.driver.specification;

import ma.youcode.rideflow.feature.driver.model.entity.Driver;
import org.springframework.data.jpa.domain.Specification;

public final class DriverSpecifications {

    public DriverSpecifications() {
    }

    public static Specification<Driver> hasFullName(String fullName) {
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

    public static Specification<Driver> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("email")), "%" + email.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Driver> hasPhone(String phone) {
        return (root, query, cb) -> {
            if (phone == null || phone.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(root.get("phone"), "%" + phone.trim() + "%");
        };
    }

    public static Specification<Driver> hasLicenseNumber(String licenseNumber) {
        return (root, query, cb) -> {
            if (licenseNumber == null || licenseNumber.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("licenseNumber")), "%" + licenseNumber.trim().toLowerCase() + "%");
        };
    }

}
