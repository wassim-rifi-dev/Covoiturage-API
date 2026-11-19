package ma.youcode.covoiturage.feature.trip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.feature.trip.dto.request.TripSearchCriteria;
import ma.youcode.covoiturage.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.covoiturage.feature.trip.service.TripService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;

    @GetMapping
    public ResponseEntity<PagedModel<TripResponseDTO>> searchTrips(
            @RequestParam(required = false) String departureCity,
            @RequestParam(required = false) String arrivalCity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minSeats,
            @RequestParam(required = false) String waypointCity,
            @PageableDefault(size = 10, sort = "departureDateTime", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/trips page={} size={} search", pageable.getPageNumber(), pageable.getPageSize());

        TripSearchCriteria criteria = new TripSearchCriteria(
                departureCity, arrivalCity, departureDate, maxPrice, minSeats, waypointCity
        );

        return ResponseEntity.ok(new PagedModel<>(tripService.searchTrips(criteria, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDTO> getTripById(@PathVariable Long id) {
        log.warn("GET /api/trips/{}", id);

        TripResponseDTO responseDTO = tripService.getTripById(id);

        return ResponseEntity.ok(responseDTO);
    }
}
