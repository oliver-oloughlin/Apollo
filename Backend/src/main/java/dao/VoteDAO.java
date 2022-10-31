package dao;

import model.Vote;

public interface VoteDAO {

  /**
   * Saves a vote in the db
   * 
   * @param vote: The vote to be saved
   * @return true if vote was persisted and false otherwise
   */
  public boolean saveVote(Vote vote);

  /**
   * Gets a vote from the db
   * 
   * @param id: The id of the vote to be fetched
   * @return the fetched vote
   */
  public Vote getVote(long id);

}
