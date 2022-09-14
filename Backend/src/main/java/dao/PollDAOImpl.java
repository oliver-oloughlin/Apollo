package dao;

import model.Poll;
import utils.VoteCount;

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
    public boolean updatePoll(Poll poll) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Poll p = em.merge(poll);
            transaction.commit();
            return p != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Poll getPollById(int id) {
        try {
            return em.find(Poll.class, id);
        } catch(EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<Poll> getAllPolls() {
        Query q = em.createNativeQuery("SELECT * FROM Poll", Poll.class);
        return q.getResultList();
    }

    @Override
    public VoteCount countVotesByPollId(int id) {
        // TODO: Create query strings to count all green and red votes for a given poll
        Query redQuery = em.createNativeQuery("", Integer.class);
        Query greenQuery = em.createNativeQuery("", Integer.class);
        int redCount = redQuery.getFirstResult();
        int greenCount = greenQuery.getFirstResult();
        return new VoteCount(greenCount, redCount);
    }

}
