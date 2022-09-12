package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import database.Voter;
import interfaces.VoterDAO;

public class VoterDAOImpl implements VoterDAO{

	private EntityManager em;
	
	public VoterDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public Voter getVoter(String username, String password) {
		Voter voter = em.find(Voter.class, username);
		if(voter != null) {
			return voter.getPassword().equals(password) ? voter : null;
		}
		return null;
	}

	@Override
	public Voter updateVoter(Voter voter) {
		em.getTransaction().begin();
		Voter managedVoter = em.merge(voter);
		em.getTransaction().commit();
		return managedVoter;
	}

	@Override
	public boolean deleteVoter(Voter voter) {
		try {
			em.getTransaction().begin();
			em.remove(em.merge(voter));
			em.getTransaction().commit();
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public boolean createVoter(Voter voter) {
		try {
			em.getTransaction().begin();
			em.persist(voter);
			em.getTransaction().commit();
			return true;
		} catch (EntityExistsException e) {
			return false;
		}
	}
}
