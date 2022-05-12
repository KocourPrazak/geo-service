package ru.netology.sender;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImplMock;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.i18n.LocalizationServiceImplMock;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MessageSenderImplTest {
    @Test
    public void testSendStandAlone() {
        GeoService geoService = new GeoServiceImplMock();
        LocalizationService localizationService = new LocalizationServiceImplMock();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        String message = messageSender.send(headers);
        String expected = "Russia";
        Assertions.assertEquals(message, expected);
    }

    @Test
    public void testSendWithLocalizationServiceIpRu() {
        GeoService geoService = new GeoServiceImplMock();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        String message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[а-яА-Я ]+", message));
    }

    @Test
    public void testSendWithLocalizationServiceIpUs() {
        GeoService geoService = new GeoServiceImplMock();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.2.1");
        String message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", message));
    }

    @Test //Error NullPointerException at location.getCountry
    public void testSendWithLocalizationServiceIpOther() {
        GeoService geoService = new GeoServiceImplMock();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "0.2.2.9");
        String message = messageSender.send(headers);
        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", message));
    }
}