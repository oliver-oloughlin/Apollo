package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Account;

public class VoterDAOImpl implements VoterDAO{

	private EntityManager em;
	
	public VoterDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public Account getVoter(String username, String password) {
		Account voter = em.find(Account.class, username);
		if(voter != null) {
			return voter.getPassword().equals(password) ? voter : null;
		}
		return null;
	}

	@Override
	public Account updateVoter(Account voter) {
		em.getTransaction().begin();
		Account managedVoter = em.merge(voter);
		em.getTransaction().commit();
		return managedVoter;
	}

	@Override
	public boolean deleteVoter(Account voter) {
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
	public boolean saveVoter(Account voter) {
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
