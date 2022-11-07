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

    Account admin = new Account("anders@gmail.com", null, new HashSet<Poll>(), new HashSet<Vote>());
    admin.setAdmin(true);
    admin.setSalt("Yyk+ifw2G+Qx5kWYKkyPRw==");
    admin.setPassword("fO3TaGQG+ypORCH+Hjbcz+xo03muG+NPWFTt2SK/ysw=");
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

  public boolean deleteAccount(Account account) {
    return dao.deleteAccount(account);
  }
}
