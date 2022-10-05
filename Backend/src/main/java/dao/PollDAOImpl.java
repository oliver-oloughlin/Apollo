package dao;

import model.Poll;

import javax.persistence.*;
import java.util.List;

public class PollDAOImpl implements PollDAO {

    @PersistenceContext(unitName = "ApolloPU")
    private EntityManager em;

    @Override
    public boolean savePoll(Poll poll) {
        em.getTransaction().begin();
        try {
            em.persist(poll);
            return true;
        } catch (EntityExistsException e) {
            return false;
        } finally {
          em.getTransaction().commit();
        }
    }
    
    @Override
    public Poll getPoll(long id) {
        return em.find(Poll.class, id);
    }

    @Override
    public Poll updatePoll(Poll poll) {
        em.getTransaction().begin();
        Poll managedPoll = em.merge(poll);
        em.getTransaction().commit();
        return managedPoll; 
    }
    
    @Override
	public boolean deletePoll(Poll poll) {
        em.getTransaction().begin();
        try {
            em.remove(em.merge(poll));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        } finally {
          em.getTransaction().commit();
        }
	}

    @Override
    public List<Poll> getAllPolls() {
        return em.createQuery("SELECT p FROM Poll p", Poll.class).getResultList();
    }
}
