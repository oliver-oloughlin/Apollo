package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class PasswordHashing {

    /**
     * Hashes a password before saving in db
     * @param password to be hashed
     * @return a hashed password as a byte array
     */
    public byte[] hashing(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[25];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
            return hashedPassword;
        } catch (NoSuchAlgorithmException nsa) {
            throw new RuntimeException(nsa);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }


}
