package com.es.phoneshop.model.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public final class PriceFormatUtils {
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("USD");

    public static String format(BigDecimal price, Locale locale, Currency currency) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setCurrency(currency);
        return formatter.format(price);
    }

    public static String format(BigDecimal price, Locale locale) {
        return format(price, locale, DEFAULT_CURRENCY);
    }
}
