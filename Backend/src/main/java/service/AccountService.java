package service;

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
	
	public Account addNewAccount(Account account) {
	    String salt = hasher.getRandomSalt();
	    String hashedPasswordBase64 = hasher.hashPassword(salt, account.getPassword());
	    account.setPassword(hashedPasswordBase64);
	    account.setSalt(salt);
		boolean success = dao.saveAccount(account);
		return success ? dao.getAccount(account.getEmail()) : null;
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
