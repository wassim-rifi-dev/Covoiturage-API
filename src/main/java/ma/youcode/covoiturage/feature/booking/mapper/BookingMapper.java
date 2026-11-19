package ma.youcode.covoiturage.feature.booking.mapper;

import ma.youcode.covoiturage.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.covoiturage.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.covoiturage.feature.booking.model.entity.Booking;
import ma.youcode.covoiturage.feature.passenger.mapper.PassengerMapper;
import ma.youcode.covoiturage.feature.trip.mapper.TripMapper;
import ma.youcode.covoiturage.feature.trip.mapper.WaypointMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TripMapper.class , PassengerMapper.class , WaypointMapper.class})
public interface BookingMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "bookingDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "pickupPoint", ignore = true)
    @Mapping(target = "dropoffPoint", ignore = true)
    Booking toEntity(CreateBookingRequestDTO requestDTO);

    BookingResponseDTO toResponseDto(Booking booking);
}
