package ma.youcode.rideflow.feature.user.service;

import lombok.RequiredArgsConstructor;
import ma.youcode.rideflow.common.exception.EmailAlreadyExistsException;
import ma.youcode.rideflow.feature.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    @Override
    public void userExistByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already existe.");
        }
    }

    @Override
    public void userIdNotAndExistByEmail(Long id, String email) {
        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new EmailAlreadyExistsException("Email already existe.");
        }
    }
}
