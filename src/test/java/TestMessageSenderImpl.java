import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestMessageSenderImpl {

    @ParameterizedTest
    @MethodSource()
    public void testSend(String ip, String expected) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-real-ip", ip);
        Location location;
        if (ip.equals("172.")) {
            location = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        } else {
            location = new Location("New York", Country.USA, " 10th Avenue", 32);
        }
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(headers.get("x-real-ip")))
                .thenReturn(location);
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenCallRealMethod();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        String result = messageSender.send(headers);
        System.out.println();
        Assertions.assertEquals(expected, result);
    }

    static Stream<Arguments> testSend() {
        return Stream.of(
                arguments("172.", "Добро пожаловать"),
                arguments("96.", "Welcome")
        );
    }
}
