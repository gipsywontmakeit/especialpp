package edu.estg.route;

import edu.maen.core.interfaces.IGeographicCoordinates;

public class GeographicCoordinates implements IGeographicCoordinates {

    private double latitude;
    private double longitude;

    public GeographicCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public String toString() {
        return "GeographicCoordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
