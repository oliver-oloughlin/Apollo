package dao;

import model.Account;
import model.Poll;

import javax.persistence.*;
import java.util.List;

public class PollDAOImpl implements PollDAO {

    private EntityManager em;

    public PollDAOImpl() {
      em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
    }
    
    @Override
    public boolean savePoll(Poll poll) {
        em.getTransaction().begin();
        Account account = poll.getOwner();
        account.addPoll(poll);
        try {
            em.persist(poll);
            em.merge(account);
            return true;
        } catch (EntityExistsException e) {
            return false;
        } finally {
          em.getTransaction().commit();
        }
    }
    
    @Override
    public Poll getPoll(long code) {
        return em.find(Poll.class, code);
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
