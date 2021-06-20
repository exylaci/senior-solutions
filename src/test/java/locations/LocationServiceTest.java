package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {


    @TempDir
    Path tempDir;

    @Test
    void writeLocations() throws IOException {
        Path path = tempDir.resolve("locations.csv");
        new LocationService().writeLocations(path, new LocationOperatorsTest().locations);

        List<String> result = Files.readAllLines(path, StandardCharsets.UTF_8);

        assertEquals( "Északon,0,100000,-19,040235",result.get(1));
    }
}