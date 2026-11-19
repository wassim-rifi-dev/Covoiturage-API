package ma.youcode.covoiturage.feature.booking;

import ma.youcode.covoiturage.feature.booking.mapper.BookingMapper;
import ma.youcode.covoiturage.feature.booking.repository.BookingRepository;
import ma.youcode.covoiturage.feature.booking.service.BookingServiceImp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImpTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImp bookingService;
}
