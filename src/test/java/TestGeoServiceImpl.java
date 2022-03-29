import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestGeoServiceImpl {

    @ParameterizedTest
    @MethodSource()
    public void testByIp(String ip, Country expected) {
        GeoService geoService = new GeoServiceImpl();
        Location result = geoService.byIp(ip);
        Assertions.assertEquals(expected, result.getCountry());
    }
    static Stream<Arguments> testByIp() {
        return Stream.of(
                arguments("127.0.0.1", null),
                arguments("172.0.32.11", Country.RUSSIA),
                arguments("96.44.183.149", Country.USA),
                arguments("172.", Country.RUSSIA),
                arguments("96.", Country.USA));
    }
}
