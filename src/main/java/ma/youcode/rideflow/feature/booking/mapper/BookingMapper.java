package ma.youcode.rideflow.feature.booking.mapper;

import ma.youcode.rideflow.feature.booking.dto.request.CreateBookingRequestDTO;
import ma.youcode.rideflow.feature.booking.dto.response.BookingResponseDTO;
import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import ma.youcode.rideflow.feature.passenger.mapper.PassengerMapper;
import ma.youcode.rideflow.feature.trip.mapper.TripMapper;
import ma.youcode.rideflow.feature.trip.mapper.WaypointMapper;
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
