package service;

import java.util.HashSet;

import javax.persistence.EntityExistsException;

import dao.AccountDAO;
import model.Account;
import model.Poll;
import model.Vote;
import utils.PasswordHasher;

public class AccountService {

  AccountDAO dao;
  PasswordHasher hasher;

  public AccountService(AccountDAO dao) {
    this.dao = dao;
    this.hasher = new PasswordHasher();

    // Should be put in DB
    Account admin = new Account("anders@gmail.com", "", new HashSet<Poll>(), new HashSet<Vote>());
    admin.setAdmin(true);
    hasher.hashAndSaltPassword(admin, "pass123");
    dao.saveAccount(admin);
  }

  public boolean addNewAccount(Account account) throws EntityExistsException {

    if (dao.getAccount(account.getEmail()) != null) {
      throw new EntityExistsException();
    }

    hasher.hashAndSaltPassword(account, account.getPassword());

    return dao.saveAccount(account);
  }

  public Account getAccount(String email) {
    return dao.getAccount(email);
  }

  public Account updateAccount(Account newAccount) {

    Account oldAccount = dao.getAccount(newAccount.getEmail());

    if (oldAccount == null) {
      return null;
    }

    hasher.hashAndSaltPassword(oldAccount, newAccount.getPassword());
    oldAccount.setAdmin(newAccount.isAdmin());

    return dao.updateAccount(oldAccount);
  }

  public Account deleteAccount(Account account) {
    boolean success = dao.deleteAccount(account);
    return success ? account : null;
  }

}
