package ru.netology.geo;

import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceImplMock implements GeoService {
    @Override
    public Location byIp(String ip) {
        if (ip.startsWith("172.")) {
            return new Location("Moscow", Country.RUSSIA, "Street", 0);
        } else if (ip.startsWith("96")) {
            return new Location("NY", Country.USA, "Street", 0);
        }

        return null;
    }

    @Override
    public Location byCoordinates(double latitude, double longitude) {
        return null;
    }
}
