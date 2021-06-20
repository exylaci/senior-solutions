package locations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class LocationService {
    public void writeLocations(Path file, List<Location> locations) {
        try {
            Files.write(file,
                    locations
                            .stream()
                            .map(Location::toString)
                            .toList(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new IllegalStateException("Can't write file!");
        }
    }
}
