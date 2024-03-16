package com.chrisp1985.footballodds.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static String getDatePlusHours(int hoursAhead) {
        String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(datePattern);
        return europeanDateFormatter.format(LocalDateTime.now().plusHours(hoursAhead).atOffset(ZoneOffset.UTC)) + ".000Z";
    }
}