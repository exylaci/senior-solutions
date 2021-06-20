package locations;

public class LocationParser {

    public static final String SEPARATOR = ",";

    public Location parse(String text) {
        String[] parts = text.split(SEPARATOR);
        return new Location(
                parts[0],
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]));
    }
}
