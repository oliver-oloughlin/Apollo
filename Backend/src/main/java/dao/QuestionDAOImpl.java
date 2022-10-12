package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import model.Poll;
import model.Question;

public class QuestionDAOImpl implements QuestionDAO{

private EntityManager em;
	
	public QuestionDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	@Override
    public boolean saveQuestion(Question question) {
        em.getTransaction().begin();
        Poll poll = question.getPoll();
        poll.addQuestion(question);
        try {
            em.persist(question);
            em.merge(poll);
            return true;
        } catch (EntityExistsException e) {
            e.printStackTrace();
            return false;
        } finally {
          em.getTransaction().commit();
        }
    }	
	@Override
    public Question getQuestion(long id) {
	    Question question = em.find(Question.class, id);
        try {
          if(question != null) {
            em.refresh(question); //Gets the updated object
          }
          return question;
        }catch(EntityNotFoundException e) {
          return null;
        }
    }
	
	@Override
    public boolean deleteQuestion(Question question) {
        em.getTransaction().begin();
        try {
            em.remove(em.merge(question));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        } finally {
          em.getTransaction().commit();
        }
    }
}
