package utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

public class PasswordHasher {

    public String getRandomSalt() {
      RandomNumberGenerator rng = new SecureRandomNumberGenerator();
      return rng.nextBytes().toBase64();
    }
    
    public String hashPassword(String salt, String password) {
      return new Sha256Hash(password, salt, 1024).toBase64();
    }
    
    
}
