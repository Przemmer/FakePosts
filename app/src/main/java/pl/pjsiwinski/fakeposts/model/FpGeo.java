package pl.pjsiwinski.fakeposts.model;

public class FpGeo {

    private double lat, lng;

    public FpGeo(String lat, String lng) {
        this.lat = Double.parseDouble(lat);
        this.lng = Double.parseDouble(lng);
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
