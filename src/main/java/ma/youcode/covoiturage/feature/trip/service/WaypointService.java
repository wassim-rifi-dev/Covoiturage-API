package ma.youcode.covoiturage.feature.trip.service;

import ma.youcode.covoiturage.feature.trip.dto.request.CreateTripRequestDTO;
import ma.youcode.covoiturage.feature.trip.dto.request.WaypointRequestDTO;

import java.util.List;

public interface WaypointService {
    public List<WaypointRequestDTO> sortIntermediateWaypoints(CreateTripRequestDTO requestDTO);

    public void waypointsWithinTrip(CreateTripRequestDTO requestDTO);
}
