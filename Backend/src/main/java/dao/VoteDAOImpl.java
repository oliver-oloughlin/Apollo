package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Question;
import model.Vote;
import model.Account;
import model.IoTDevice;

public class VoteDAOImpl implements VoteDAO{

	private EntityManager em;
	
	public VoteDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public boolean saveVote(Vote vote) {

		Account voter = vote.getVoter();
		IoTDevice device = vote.getDevice();
		Question question = vote.getQuestion();
		
		question.addVote(vote);
		
		em.getTransaction().begin();
		try {
			em.persist(vote);
			em.merge(question);
			
			if(voter != null) {
				voter.addVote(vote);
				em.merge(voter);
			}
			else if(device != null) {
				device.addVote(vote);
				em.merge(device);
			}
			return true;
		} catch(EntityExistsException | IllegalArgumentException e) {
			return false;
		} finally {
		  em.getTransaction().commit();
		}
	}
	
	@Override
	public Vote getVote(long id) {
	  return em.find(Vote.class, id);
	}
}
