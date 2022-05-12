package ru.netology.i18n;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.netology.entity.Country;

import java.util.regex.Pattern;

public class LocalizationServiceImpTest {
    @Test
    public void testLocaleNotRussia() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", localizationService.locale(Country.BRAZIL)));
        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", localizationService.locale(Country.USA)));
        Assertions.assertTrue(Pattern.matches("[a-zA-Z ]+", localizationService.locale(Country.GERMANY)));
    }

    @Test
    public void testLocaleRussia() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        Assertions.assertTrue(Pattern.matches("[а-яА-Я ]+", localizationService.locale(Country.RUSSIA)));
    }
}