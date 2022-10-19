package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import model.Account;

public class AccountDAOImpl implements AccountDAO{

	private EntityManager em;
	
	public AccountDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}
	
	@Override
	public boolean saveAccount(Account account) {
  	    
	    EntityTransaction tx = em.getTransaction();
	    tx.begin();
  	    try {
			em.persist(account);
			tx.commit();
			return true;
		} catch (RollbackException e) {
			return false;
		} finally {
  		    if(tx.isActive()) {
  		      tx.commit();
  		    }
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
