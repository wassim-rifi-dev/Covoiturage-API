package ma.youcode.rideflow.feature.user.repository;

import ma.youcode.rideflow.feature.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email , Long id);
}
