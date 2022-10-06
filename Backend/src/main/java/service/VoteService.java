package service;

import dao.VoteDAO;
import model.Vote;

public class VoteService {

	VoteDAO dao;
	
	public VoteService(VoteDAO dao) {
		this.dao = dao;
	}
	
	public Vote addNewVote(Vote vote) {
		boolean success = dao.saveVote(vote);
		return success ? vote : null;
	}
}
