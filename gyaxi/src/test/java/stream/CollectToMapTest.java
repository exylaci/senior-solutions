package stream;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class CollectToMapTest {

    List<String> strings = List.of("hello", "szia", "hali");

    @Test
    void original() {
        assertEquals(Map.of("h", "hellohali", "s", "szia"), new CollectToMap().original(strings));
    }

    @Test
    void simplified() {
        assertEquals(Map.of("h", "hellohali", "s", "szia"), new CollectToMap().simplified(strings));
    }

    @Test
    void withCollect() {
        assertEquals(Map.of("h", "hellohali", "s", "szia"), new CollectToMap().withCollect(strings));
    }
}