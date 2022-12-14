package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import model.Account;
import model.Question;
import model.Vote;

public class VoteDAOImpl implements VoteDAO {

  private EntityManager em;

  public VoteDAOImpl() {
    em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
  }

  @Override
  public boolean saveVote(Vote vote) {

    Account voter = vote.getVoter();
    Question question = vote.getQuestion();

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {

      em.persist(vote);

      if (question != null) {
        question.addVote(vote);
        em.merge(question);
      }

      if (voter != null) {
        voter.addVote(vote);
        em.merge(voter);
      }
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
  public Vote getVote(long id) {
    Vote vote = em.find(Vote.class, id);
    try {
      if (vote != null) {
        em.refresh(vote); // Gets the updated object
      }
      return vote;
    } catch (EntityNotFoundException e) {
      return null;
    }
  }
}
