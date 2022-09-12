package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import database.Question;
import interfaces.QuestionDAO;

public class QuestionDAOImpl implements QuestionDAO{

private EntityManager em;
	
	public QuestionDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	@Override
	public boolean saveQuestion(Question question) {
		try {
			em.getTransaction().begin();
			em.persist(question);
			em.getTransaction().commit();
			return true;
		} catch(EntityExistsException e) {
			return false;
		}
	}
}
