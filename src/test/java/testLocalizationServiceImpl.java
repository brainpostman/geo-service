import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;


public class testLocalizationServiceImpl {

    @ParameterizedTest
    @MethodSource()
    public void testLocale(Country country, String expected) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String result = localizationService.locale(country);
        Assertions.assertEquals(expected, result);
    }

    static Stream<Arguments> testLocale() {
        return Stream.of(
                arguments(Country.RUSSIA, "Добро пожаловать"),
                arguments(Country.USA, "Welcome"));
    }

}