package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import database.Question;
import database.Vote;
import database.Voter;
import interfaces.VoteDAO;

public class VoteDAOImpl implements VoteDAO{

	private EntityManager em;
	
	public VoteDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public boolean saveVote(Vote vote) {
		
		Voter voter = vote.getVoter();
		voter.addVote(vote);
		Question question = vote.getQuestion();
		question.addVote(vote);
		
		
	}

}
