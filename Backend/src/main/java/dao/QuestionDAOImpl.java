package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Question;

public class QuestionDAOImpl implements QuestionDAO{

private EntityManager em;
	
	public QuestionDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	@Override
    public boolean saveQuestion(Question question) {
        em.getTransaction().begin();
        try {
            em.persist(question);
            return true;
        } catch (EntityExistsException e) {
            return false;
        } finally {
          em.getTransaction().commit();
        }
    }	
	@Override
    public Question getQuestion(long id) {
	    return em.find(Question.class, id);
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
