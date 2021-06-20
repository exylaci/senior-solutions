package locations;

public class Location {
    private String name;
    private double lat;
    private double lon;

    public Location() {
    }

    public Location(String name, double lat, double lon) {
        if(Math.abs(lat)>90 || Math.abs(lon)>180){
            throw new IllegalArgumentException("Invalis coordinate!");
        }
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public boolean isOnEquator() {
        return lat == 0;
    }

    public boolean isOnPrimeMeridian() {
        return lon == 0;
    }

    public  double distanceFrom(Location otherLocation) {
        return distance(lat,otherLocation.lat,lon ,otherLocation.lon , 0, 0);
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return String.format("%s,%f,%f",name,lat,lon);
    }
}
