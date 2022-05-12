package ru.netology.geo;

import junit.framework.TestCase;
import org.junit.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GeoServiceImplTest{

    @Test
    public void testByIpRussia() {
        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp("172.x.x.x");
        Country message = location.getCountry();
        assertEquals(Country.RUSSIA, message);
    }

    @Test
    public void testByIpUs() {
        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp("96.x.x.x");
        Country message = location.getCountry();
        assertDoesNotThrow(location::getCountry);
        assertEquals(Country.USA, message);
    }

    @Test //In Case of other IP, method throws NullPointerException
    public void testByIpOther() {
        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp("0.5.x.x");
//        Country message =  location.getCountry();
        assertDoesNotThrow(location::getCountry);

    }
}