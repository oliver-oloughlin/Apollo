package database;

import java.security.SecureRandom;


public class PasswordHashing {

    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
}
