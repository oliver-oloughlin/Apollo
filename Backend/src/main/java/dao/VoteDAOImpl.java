package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Question;
import model.Vote;
import model.Account;

public class VoteDAOImpl implements VoteDAO{

	private EntityManager em;
	
	public VoteDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public boolean saveVote(Vote vote) {
		
		Account voter = vote.getVoter();
		voter.addVote(vote);
		Question question = vote.getQuestion();
		question.addVote(vote);
		
		try {
			em.getTransaction().begin();
			em.persist(vote);
			if(voter != null) {
				em.merge(voter);
			}
			em.merge(question);
			em.getTransaction().commit();
			return true;
		} catch(EntityExistsException | IllegalArgumentException e) {
			return false;
		}
	}
}
