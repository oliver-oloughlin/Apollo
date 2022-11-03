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
    Account admin = new Account("anders@gmail.com", "pass123", new HashSet<Poll>(), new HashSet<Vote>());
    admin.setAdmin(true);
    hasher.hashAndSaltPassword(admin);
    dao.saveAccount(admin);
  }

  public boolean addNewAccount(Account account) throws EntityExistsException {

    if (dao.getAccount(account.getEmail()) != null) {
      throw new EntityExistsException();
    }

    hasher.hashAndSaltPassword(account);

    return dao.saveAccount(account);
  }

  public Account getAccount(String email) {
    return dao.getAccount(email);
  }

  public Account updateAccount(Account account) {

    Account oldAccount = dao.getAccount(account.getEmail());

    if (oldAccount == null) {
      return null;
    }

    account.setPolls(oldAccount.getPolls());
    account.setVotes(oldAccount.getVotes());

    hasher.hashAndSaltPassword(account);

    return dao.updateAccount(account);
  }

  public Account deleteAccount(Account account) {
    boolean success = dao.deleteAccount(account);
    return success ? account : null;
  }
}
