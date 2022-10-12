package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import model.Account;

public class AccountDAOImpl implements AccountDAO{

	private EntityManager em;
	
	public AccountDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public boolean saveAccount(Account account) {
  	    em.getTransaction().begin();
  	    try {
			em.persist(account);
			return true;
		} catch (EntityExistsException e) {
			return false;
		} finally {
		  em.getTransaction().commit();
		}
	}
	
	@Override
	public Account getAccount(String email) {
		Account account = em.find(Account.class, email);
        try {
          if(account != null) {
            em.refresh(account); //Gets the updated object
          }
          return account;
        }catch(EntityNotFoundException e) {
          return null;
        }
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
	    em.getTransaction().begin();
	    try {
			em.remove(em.merge(account));
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		} finally {
		  em.getTransaction().commit();
		}
	}
}
