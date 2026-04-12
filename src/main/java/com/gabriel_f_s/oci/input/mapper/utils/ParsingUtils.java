package com.gabriel_f_s.oci.input.mapper.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParsingUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank() || "00000000".equals(dateStr) || "0".equals(dateStr)) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static Integer parseInteger(String intStr) {
        if (intStr == null) return null;
        return Integer.parseInt(intStr);
    }

    public static String stringTreatment(String str) {
        if (str == null) return null;
        int limit = 255;
        String treatedString = str.trim();
        return treatedString.length() <= limit ? treatedString : treatedString.substring(0, limit);
    }
}
