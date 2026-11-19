package ma.youcode.covoiturage.feature.booking;

import ma.youcode.covoiturage.feature.booking.service.BookingServiceImp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImpTest {
    @InjectMocks
    private BookingServiceImp bookingService;
}
