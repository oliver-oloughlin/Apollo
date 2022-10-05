package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import model.Question;

public class QuestionDAOImpl implements QuestionDAO{

private EntityManager em;
	
	public QuestionDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	@Override
	public boolean saveQuestion(Question question) {
		try {
			if(!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.persist(question);
			em.getTransaction().commit();
			return true;
		} catch(EntityExistsException e) {
			return false;
		}
	}
	
	@Override
    public Question getQuestion(long id) {
        try {
            return em.find(Question.class, id);
        } catch(EntityNotFoundException e) {
            return null;
        }
    }
	
	@Override
	public boolean deleteQuestion(Question question) {
		try {
			if(!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.remove(em.merge(question));
			em.getTransaction().commit();
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}
}
