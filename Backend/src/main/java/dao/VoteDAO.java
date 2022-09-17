package dao;

import model.Vote;

public interface VoteDAO {

	/**
	 * Saves a vote in the database
	 * @param vote: The vote to be saved
	 * @return True if the vote was saved successfully and false otherwise
	 */
	public boolean saveVote(Vote vote);
	
}
