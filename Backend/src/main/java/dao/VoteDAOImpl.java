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
		
		try {
			em.getTransaction().begin();
			em.persist(vote);
			
			if(voter != null) {
				voter.addVote(vote);
				em.merge(voter);
			}
			else if(device != null) {
				device.addVote(vote);
				em.merge(device);
			}
			
			em.merge(question);
			em.getTransaction().commit();
			return true;
		} catch(EntityExistsException | IllegalArgumentException e) {
			return false;
		}
	}
}
