package ma.youcode.rideflow.feature.trip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.request.WaypointRequestDTO;
import ma.youcode.rideflow.feature.trip.exception.InvalidTripPriceException;
import ma.youcode.rideflow.feature.trip.exception.InvalidTripTimeException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaypointServiceImp implements WaypointService {

    @Override
    public List<WaypointRequestDTO> sortIntermediateWaypoints(CreateTripRequestDTO requestDTO) {
        if (requestDTO.waypoints() == null) {
            return List.of();
        }

        return requestDTO.waypoints().stream()
                .sorted(Comparator.comparing(WaypointRequestDTO::passageTime))
                .toList();
    }

    @Override
    public void waypointsWithinTrip(CreateTripRequestDTO requestDTO) {
        for (WaypointRequestDTO waypoint : sortIntermediateWaypoints(requestDTO)) {
            if (
                    !waypoint.passageTime().isAfter(requestDTO.departureDateTime())
            ) {
                throw new InvalidTripTimeException(
                        "Waypoint '" + waypoint.city() + "' passage time must be between departure and arrival."
                );
            }

            if (waypoint.priceFromStart().compareTo(requestDTO.price()) > 0) {
                throw new InvalidTripPriceException(
                        "Waypoint '" + waypoint.city() + "' price must not exceed the trip price."
                );
            }
        }
    }
}
