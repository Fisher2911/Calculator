package io.github.fisher2911.util;

public class Utils {

    public static boolean isInt(String s) {
        return tryParseInt(s) != null;
    }

    public static boolean isDouble(String s) {
        return tryParseDouble(s) != null;
    }

    public static Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
