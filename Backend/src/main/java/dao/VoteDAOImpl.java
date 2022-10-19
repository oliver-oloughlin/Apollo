package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

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

		EntityTransaction tx = em.getTransaction();
        tx.begin();
		try {
		    question.addVote(vote);
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
			tx.commit();
			return true;
		} catch(RollbackException e) {
			return false;
		} finally {
		    if(tx.isActive()) {
		        tx.commit();
		    }
		}
	}
	
	@Override
	public Vote getVote(long id) {
	  Vote vote = em.find(Vote.class, id);
      try {
        if(vote != null) {
          em.refresh(vote); //Gets the updated object
        }
        return vote;
      }catch(EntityNotFoundException e) {
        return null;
      }
	}
}
