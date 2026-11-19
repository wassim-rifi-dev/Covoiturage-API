package ma.youcode.rideflow.feature.trip.service;

import ma.youcode.rideflow.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.rideflow.feature.trip.dto.request.WaypointRequestDTO;

import java.util.List;

public interface WaypointService {
    public List<WaypointRequestDTO> sortIntermediateWaypoints(CreateTripRequestDTO requestDTO);

    public void waypointsWithinTrip(CreateTripRequestDTO requestDTO);
}
