package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import model.Poll;
import model.Question;

public class QuestionDAOImpl implements QuestionDAO {

  private EntityManager em;

  public QuestionDAOImpl() {
    em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
  }

  @Override
  public boolean saveQuestion(Question question) {

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      Poll poll = question.getPoll();
      poll.addQuestion(question);
      em.persist(question);
      em.merge(poll);
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
  public Question getQuestion(long id) {
    Question question = em.find(Question.class, id);
    try {
      if (question != null) {
        em.refresh(question); // Gets the updated object
      }
      return question;
    } catch (EntityNotFoundException e) {
      return null;
    }
  }

  @Override
  public boolean deleteQuestion(Question question) {
    em.getTransaction().begin();
    try {
      Poll poll = question.getPoll();
      poll.removeQuestion(question);
      em.merge(poll);
      em.remove(em.merge(question));
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } finally {
      em.getTransaction().commit();
    }
  }
}
