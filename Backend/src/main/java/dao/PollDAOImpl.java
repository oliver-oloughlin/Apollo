package dao;

import model.Poll;

import javax.persistence.*;
import java.util.List;

public class PollDAOImpl implements PollDAO {

    @PersistenceContext(unitName = "ApolloPU")
    private EntityManager em;

    @Override
    public boolean savePoll(Poll poll) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(poll);
            transaction.commit();
            return true;
        } catch (EntityExistsException e) {
            return false;
        }
    }
    
    @Override
    public Poll getPoll(long id) {
        try {
            return em.find(Poll.class, id);
        } catch(EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public Poll updatePoll(Poll poll) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Poll p = em.merge(poll);
            transaction.commit();
            return p; 
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
	public boolean deletePoll(Poll poll) {
    	try {
			em.getTransaction().begin();
			em.remove(em.merge(poll));
			em.getTransaction().commit();
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}

    @Override
    public List<Poll> getAllPolls() {
        return em.createQuery("SELECT p FROM Poll p", Poll.class).getResultList();
    }
}
