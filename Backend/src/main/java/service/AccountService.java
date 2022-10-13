package service;

import dao.AccountDAO;
import model.Account;

public class AccountService {

	AccountDAO dao;
	
	public AccountService(AccountDAO dao) {
		this.dao = dao;
	}
	
	public Account addNewAccount(Account account) {
		boolean success = dao.saveAccount(account);
		return success ? dao.getAccount(account.getEmail()) : null;
	}
	
	public Account getAccount(String email) {
		return dao.getAccount(email);
	}
	
	public Account updateAccount(Account account) {
		return dao.updateAccount(account);
	}
	
	public Account deleteAccount(String email) {
		Account account = getAccount(email);
		boolean success = dao.deleteAccount(account);
		return success ? account : null;
	}
}
