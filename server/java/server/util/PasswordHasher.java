package server.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordHasher {

    private static final int SALT_BYTES = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordHasher() {
    }

 
    public static String hash(String password) {
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        byte[] hash = sha256(salt, password);
        Base64.Encoder b64 = Base64.getEncoder();
        return b64.encodeToString(salt) + ":" + b64.encodeToString(hash);
    }

    public static boolean verify(String password, String stored) {
        String[] parts = stored.split(":");
        if (parts.length != 2) {
            return false;
        }
        byte[] salt;
        byte[] expected;
        try {
            Base64.Decoder b64 = Base64.getDecoder();
            salt = b64.decode(parts[0]);
            expected = b64.decode(parts[1]);
        } catch (IllegalArgumentException e) {
            return false; 
        }
        byte[] actual = sha256(salt, password);
        return MessageDigest.isEqual(expected, actual);
    }

    private static byte[] sha256(byte[] salt, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            return digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 недоступен", e);
        }
    }
}
