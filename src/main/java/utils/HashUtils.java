package utils;

import java.security.MessageDigest;

/**
 * Helper functions to deal with strings and hashes.
 */
public class HashUtils {
    public static String toHexString(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (byte byteDatum : byteData) {
            sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String hash(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte[] hashData = md.digest();
            return toHexString(hashData);
        } catch (Exception e) {
            System.err.println("Util.hash(" + s + ") FAILED: " + e);
        }
        return null;
    }

    public static String generateChallenge(int digits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            char c = (char) (((int) ' ') + ((int) (Math.random() * 80)));
            sb.append(c);
        }
        return sb.toString();
    }
}
