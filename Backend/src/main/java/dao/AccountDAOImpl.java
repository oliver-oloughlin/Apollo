package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Account;

public class AccountDAOImpl implements AccountDAO{

	private EntityManager em;
	
	public AccountDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public boolean saveAccount(Account account) {
		try {
			em.getTransaction().begin();
			em.persist(account);
			em.getTransaction().commit();
			return true;
		} catch (EntityExistsException e) {
			return false;
		}
	}
	
	@Override
	public Account getAccount(String email) {
		return em.find(Account.class, email);
	}

	@Override
	public Account updateAccount(Account account) {
		em.getTransaction().begin();
		Account managedAccount = em.merge(account);
		em.getTransaction().commit();
		return managedAccount;
	}

	@Override
	public boolean deleteAccount(Account account) {
		try {
			em.getTransaction().begin();
			em.remove(em.merge(account));
			em.getTransaction().commit();
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}
}
