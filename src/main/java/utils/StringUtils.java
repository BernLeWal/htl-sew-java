package utils;

public class StringUtils {
    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isNullOrEmpty(String param) {
        return isNull(param) || param.trim().length() == 0;
    }
}
