package interfaces;

import database.Vote;
import database.Voter;

public interface VoterDAO {
	
	public Voter getVoter(String username, String password);
	public boolean addVote(Vote vote);
	public Voter updateVoter(Voter voter);
	public boolean deleteVoter(Voter voter);
}
