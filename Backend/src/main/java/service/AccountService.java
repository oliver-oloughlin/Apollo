package service;

import org.springframework.stereotype.Service;

import dao.AccountDAO;
import dao.AccountDAOImpl;
import model.Account;

@Service
public class AccountService {

	AccountDAO dao;
	
	public AccountService() {
		dao = new AccountDAOImpl();
	}
	
	public Account addNewAccount(Account account) {
		boolean success = dao.saveAccount(account);
		return success ? account : null;
	}
	
	public Account getAccountWithPassword(String email, String password) {
		Account account = dao.getAccount(email);
		if (account != null && account.getPassword().equals(password)) {
			return account;
		}
		return null;
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
