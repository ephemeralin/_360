package com.ephemeralin.z360.util;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DatesHelper {
    public static LocalDate getCurrentBeginningOfYear() {
        return LocalDate.of(LocalDate.now().getYear(), 1, 1);
    }

    public static LocalDate getCurrentEndOfYear() {
        return LocalDate.of(LocalDate.now().getYear(), 12, 31);
    }

    public static LocalDateTime parseVestiDateTime(String s) {
        //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
//        "EEE, dd MMM yyyy HH:mm:ss Z"
        ZonedDateTime parsed;
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
        try {
            parsed = ZonedDateTime.parse(s, dtf);
        } catch (DateTimeParseException dtpe) {
            log.error(dtpe);
            parsed = ZonedDateTime.now();
        }
        return parsed.toLocalDateTime();
    }
}
