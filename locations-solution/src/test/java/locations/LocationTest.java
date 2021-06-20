package locations;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location;

    @BeforeEach
    void init() {
        location = new LocationParser().parse("Budapest,47.497912,19.040235");
        System.out.println(location);
    }

    @DisplayName("Sztring location-né konvertálása.")
    @Test
    void testParse() {
        assertEquals("Budapest", location.getName());
        assertEquals(47.497912, location.getLat());
        assertEquals(19.040235, location.getLon());
    }

    @DisplayName("Igen, az egyenlítőn van.")
    @Test
    void isOnEquatorYes() {
        location.setLat(0);
        assertTrue(location.isOnEquator());
    }

    @DisplayName("Nem, nincs az egyenlítőn.")
    @Test
    void isOnEquatorNo() {
        assertFalse(location.isOnEquator());
    }

    @DisplayName("Igen, a meridiánon van.")
    @Test
    void isOnPrimeMeridiánYes() {
        location.setLon(0);
        assertTrue(location.isOnPrimeMeridian());
    }

    @DisplayName("Nem, nincs a meridiánon.")
    @Test
    void isOnPrimeMeridiánNo() {
        assertFalse(location.isOnPrimeMeridian());
    }

    @ParameterizedTest(name = "Koordináta helyességének ellenőrzései {0} , {1} -> kivételt dob: {2}")
    @MethodSource("coordinateGenerator")
    void locationValidityTest(double lat, double lon, boolean doThrow) {
        if (doThrow) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Location(null, lat, lon));
        } else {
            assertDoesNotThrow(
                    () -> new Location(null, lat, lon));
        }
    }

    static Stream<Arguments> coordinateGenerator() {
        return Stream.of(
                Arguments.arguments(-90.1, 0, true),
                Arguments.arguments(-90, 0, false),
                Arguments.arguments(90, 0, false),
                Arguments.arguments(90.1, 0, true),
                Arguments.arguments(0, -180.1, true),
                Arguments.arguments(0, -180, false),
                Arguments.arguments(0, 180, false),
                Arguments.arguments(0, 180.1, true)
        );
    }

    @RepeatedTest(value = 6, name = "Egyenlítőn van-e ismétléses teszttel {currentRepetition}/{totalRepetitions}")
    void equatorIsmétlésesTeszt(RepetitionInfo repetitionInfo) {
        int round = repetitionInfo.getCurrentRepetition() - 1;
        assertEquals(values[round][2],
                new Location("", (double) values[round][0], (double) values[round][1]).isOnEquator());
    }

    private Object[][] values = {
            {47.497912, 19.040235, false},
            {47.497912, 0., false},
            {0., 19.040235, true},
            {0., -19.040235, true},
            {0., 0., true},
            {-47.497912, 0., false}};

    @Disabled
    @ParameterizedTest
    @CsvFileSource(resources = "/coordinates.csv")
    void distanceFromTest(double lat1, double lon1, double lat2, double lon2, double distance) {
        assertEquals(distance,
                new Location("", lat1, lon1).distanceFrom(new Location("", lat2, lon2)));
    }

    @TestFactory
    Stream<DynamicTest> dinamikusTeszt() {
        return Stream.of(values)
                .map(value -> DynamicTest.dynamicTest(
                        "egyenlítőn levést tesztelő esetek",
                        () -> assertEquals(value[2],
                                new Location("", (double) value[0], (double) value[1]).isOnEquator())
                ));
    }


    Condition<Location> anyCoordinateEqualsToZero = new Condition<>(
            l -> l.isOnEquator() || l.isOnPrimeMeridian(),
            "Nem nulla az egyik koordináta!");

    @Test
    void nonZeroCoordinateTest() {
        assertThat(location).isNot(anyCoordinateEqualsToZero);
    }

    @RepeatedTest(value = 5)
    void zeroCoordinateTest(RepetitionInfo repetitionInfo) {
        int round = repetitionInfo.getCurrentRepetition() ;
        location = new Location("", (double) values[round][0], (double) values[round][1]);

        assertThat(location).is(anyCoordinateEqualsToZero);
    }


}
