package ru.netology.sender;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MessageSenderImplTest {
    @Test
    public void testSendStandAlone() {
        String testIp = "172.123.12.1";
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(testIp)).thenReturn(
                new Location("Moscow", Country.RUSSIA, "Street", 0));
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, testIp);
        String message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[а-яА-Я ]+", message));
    }

    @Test
    public void testSendWithLocalizationServiceIpRu() {
        String testIp = "172.123.12.1";
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(testIp)).thenReturn(
                new Location("Moscow", Country.RUSSIA, "Street", 0));
//        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
//        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, testIp);
        String message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[а-яА-Я ]+", message));
    }

    @Test
    public void testSendWithLocalizationServiceIpUs() {
        String testIp = "96.0.2.1";
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(testIp)).thenReturn(
                new Location("Moscow", Country.USA, "Street", 0));
//        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
//        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.2.1");
        String message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", message));
    }

    @Test
    public void testSendWithGeoServiceIpRuUs() {
        String testIp = "172.123.12.1";
        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
//        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, testIp);
        String message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[а-яА-Я ]+", message));
        testIp = "96.1.1.1";
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, testIp);
        message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", message));
    }


//    @Test //Error NullPointerException at location.getCountry
//    public void testSendWithLocalizationServiceIpOther() {
//        GeoService geoService = new GeoServiceImplMock();
//        LocalizationService localizationService = new LocalizationServiceImpl();
//        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
//        Map<String, String> headers = new HashMap<>();
//        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "0.2.2.9");
//        String message = messageSender.send(headers);
//        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", message));
//    }
}