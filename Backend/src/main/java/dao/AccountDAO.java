package dao;

import model.Account;

public interface AccountDAO {

	/**
	 * Creates a new account in the db
	 * @param account: The account that is to be saved in the db
	 * @return True if user was created was and false if user already existed in the db
	 */
	public boolean saveAccount(Account account);
	
	/**
	 * Gets a account from db
	 * @param username: PK in db, can not be changed by user
	 * @return The account from db or NULL if it does not exist
	 */
	public Account getAccount(String email);

	/**
	 * Updates an account
	 * @param account: The account-object with the updated information
	 * @return The managed updated account
	 */
	public Account updateAccount(Account account);

	/**
	 * Deletes a account from db
	 * @param account: The account that is going to be deleted
	 * @return True if deletion was successful and false otherwise
	 */
	public boolean deleteAccount(Account account);
}
