package interfaces;

import database.Vote;
import database.Voter;

public interface VoterDAO {

	/**
	 * Creates a voter
	 * @param username PK in db, can not be changed by user
	 * @param password Hashed in db
	 * @return
	 */
	public Voter getVoter(String username, String password);

	/**
	 * Gives the ability of voting to a voter
	 * @param vote The vote casted by the voter, by default red or green
	 * @return true or false
	 */
	public boolean addVote(Vote vote);

	/**
	 * lets the Voter update the User account
	 * @param voter the Voter that
	 * @return
	 */
	public Voter updateVoter(Voter voter);
	public boolean deleteVoter(Voter voter);
}
