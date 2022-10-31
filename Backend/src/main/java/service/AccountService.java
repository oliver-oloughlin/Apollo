package service;

import javax.persistence.EntityExistsException;

import dao.AccountDAO;
import model.Account;
import utils.PasswordHasher;

public class AccountService {

  AccountDAO dao;
  PasswordHasher hasher;

  public AccountService(AccountDAO dao) {
    this.dao = dao;
    this.hasher = new PasswordHasher();
  }

  public boolean addNewAccount(Account account) throws EntityExistsException {

    if (dao.getAccount(account.getEmail()) != null) {
      throw new EntityExistsException();
    }

    String salt = hasher.getRandomSalt();
    String hashedPasswordBase64 = hasher.hashPassword(salt, account.getPassword());

    account.setPassword(hashedPasswordBase64);
    account.setSalt(salt);

    return dao.saveAccount(account);
  }

  public Account getAccount(String email) {
    return dao.getAccount(email);
  }

  public Account updateAccount(Account account) {
    return dao.updateAccount(account);
  }

  public Account deleteAccount(Account account) {
    boolean success = dao.deleteAccount(account);
    return success ? account : null;
  }
}
