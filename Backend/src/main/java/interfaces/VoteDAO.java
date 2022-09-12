package interfaces;

import database.Vote;

public interface VoteDAO {

	public boolean saveVote(Vote vote);
	
}
