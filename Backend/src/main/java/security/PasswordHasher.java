package security;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

import model.Account;

public class PasswordHasher {

  public String getRandomSalt() {
    RandomNumberGenerator rng = new SecureRandomNumberGenerator();
    return rng.nextBytes().toBase64();
  }

  public String hashPassword(String salt, String password) {
    return new Sha256Hash(password, salt, 1024).toBase64();
  }

  public void hashAndSaltPassword(Account account) {
    String salt = getRandomSalt();
    String hashedPasswordBase64 = hashPassword(salt, account.getPassword());

    account.setPassword(hashedPasswordBase64);
    account.setSalt(salt);
  }
}
