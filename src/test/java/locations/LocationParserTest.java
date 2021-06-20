package locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationParserTest {
    Location location = new LocationParser().parse("Budapest,47.497912,19.040235");

    @DisplayName("Két új példány jön létre?")
    @Test
    void parseNewTeszt() {
        assertNotSame(new LocationParser().parse("Budapest,47.497912,19.040235"),
                new LocationParser().parse("Budapest,47.497912,19.040235"));
    }

    @DisplayName("Mindent egyszerrre ellenőriz.")
    @Test
    void parseAttributesTeszt() {
        assertAll(()->assertEquals("Budapest",location.getName()),
                ()->assertEquals(47.497912,location.getLat(),0.000001),
                ()->assertEquals(19.040235,location.getLon(),0.000001));
    }


}