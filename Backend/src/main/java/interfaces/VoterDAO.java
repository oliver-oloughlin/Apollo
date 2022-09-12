package interfaces;

import database.Voter;

public interface VoterDAO {

	/**
	 * Gets a voter from db
	 * @param username: PK in db, can not be changed by user
	 * @param password: Hashed in db
	 * @return The voter from db or NULL if it does not exist
	 */
	public Voter getVoter(String username, String password);

	/**
	 * Lets the voter update his account
	 * @param voter: The voter-object with the updated information
	 * @return The updated voter
	 */
	public Voter updateVoter(Voter voter);

	/**
	 * Deletes a voter from db
	 * @param voter: The voter that is going to be deleted
	 * @return True if deletion was successful and false otherwise
	 */
	public boolean deleteVoter(Voter voter);
	
	/**
	 * Creates a voter in the db
	 * @param voter: The voter that is to be saved in the db
	 * @return True if user was created was and false if user already existed in the db
	 */
	public boolean createVoter(Voter voter);
}
