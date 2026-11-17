package ma.youcode.rideflow.feature.user.service;

public interface UserService {
    public void userExistByEmail(String email);

    public void userIdNotAndExistByEmail(Long id, String email);
}
