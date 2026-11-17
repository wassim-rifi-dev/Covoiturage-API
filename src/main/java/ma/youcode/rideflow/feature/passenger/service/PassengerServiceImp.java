package ma.youcode.rideflow.feature.passenger.service;

import lombok.RequiredArgsConstructor;
import ma.youcode.rideflow.feature.passenger.mapper.PassengerMapper;
import ma.youcode.rideflow.feature.passenger.repository.PassengerRepository;
import ma.youcode.rideflow.feature.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceImp implements PassengerService {
    private final UserService userService;

    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;
}
