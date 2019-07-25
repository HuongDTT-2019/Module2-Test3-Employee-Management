package com.codegym.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class StringToLocalDateConverter implements Converter<String,LocalDate> {

    private String datePattern;

    public StringToLocalDateConverter(String datePattern) {
        this.datePattern = datePattern;
    }
    @Override
    public LocalDate convert(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern(datePattern));
        } catch (DateTimeParseException e) {
            // the error message will be displayed in <form:errors>
            throw new IllegalArgumentException("invalid date format. Please use this pattern\""
                    + datePattern + "\"");
        }
    }
}
