package ru.sheykin.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;

/**
 * Password salty hashing with PBKDF2 algorithm
 */

public class PasswordAuth {

    private static final int ITERATIONS = 1000;
    private static final int SALTEN = 32;
    private static final int DESIRED_KEY_LEN = 256;

    public static String getSaltedHash(String password) {
        byte[] salt;
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALTEN);
            return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("No algorithm found");
        }
    }

    public static boolean check(String password, String stored) {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = null;
        try {
            hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hashOfInput.equals(saltAndPass[1]);
    }

    private static String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException(
                    "Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LEN)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }
}