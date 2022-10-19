package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import model.Account;
import model.Poll;

public class PollDAOImpl implements PollDAO {

  private EntityManager em;

  public PollDAOImpl() {
    em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
  }

  @Override
  public boolean savePoll(Poll poll) {

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      Account account = poll.getOwner();
      account.addPoll(poll);
      em.persist(poll);
      em.merge(account);
      tx.commit();
      return true;
    } catch (RollbackException e) {
      return false;
    } finally {
      if (tx.isActive()) {
        tx.commit();
      }
    }
  }

  @Override
  public Poll getPoll(long code) {
    Poll poll = em.find(Poll.class, code);
    try {
      if (poll != null) {
        em.refresh(poll); // Gets the updated object
      }
      return poll;
    } catch (EntityNotFoundException e) {
      return null;
    }
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
      Account account = poll.getOwner();
      account.removePoll(poll);
      em.merge(account);
      em.remove(em.merge(poll));
      return true;
    } catch (IllegalArgumentException e) {
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
