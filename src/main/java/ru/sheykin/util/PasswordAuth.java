package ru.sheykin.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;

public class PasswordAuth {

    private static final int ITERATIONS = 1000;
    private static final int SALTEN = 32;
    private static final int DESIRED_KEY_LEN = 256;

    /**
     * Computes a salted PBKDF2 hash of given plaintext password
     *      suitable for storing in a database.
     *      Empty passwords are not supported.
     * @param password
     * @return
     */
    public static String getSaltedHash(String password) {
        byte[] salt;
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALTEN);
            return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("No algorithm found");
        }
    }

    /**
     * Checks whether given plaintext password corresponds
     *      to a stored salted hash of the password.
     * @param password
     * @param stored
     * @return
     */
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

    /**
     *
     * @param password
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */

    private static String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LEN)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }
}