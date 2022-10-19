package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import model.IoTDevice;
import model.Question;

public class IoTDeviceDAOImpl implements IoTDeviceDAO {

private EntityManager em;
	
	public IoTDeviceDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}

	@Override
	public boolean saveDevice(IoTDevice device) {
	    EntityTransaction tx = em.getTransaction();
        tx.begin();
	    try {
	        Question question = device.getQuestion();
	        question.addDevice(device);
	        em.merge(device);
			em.persist(device);
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
	public IoTDevice getDevice(long token) {
		IoTDevice device = em.find(IoTDevice.class, token);
        try {
          if(device != null) {
            em.refresh(device); //Gets the updated object
          }
          return device;
        }catch(EntityNotFoundException e) {
          return null;
        }
	}

	@Override
	public IoTDevice updateDevice(IoTDevice device) {
		em.getTransaction().begin();
		IoTDevice managedDevice = em.merge(device);
		em.getTransaction().commit();
		return managedDevice;
	}

	@Override
	public boolean deleteDevice(IoTDevice device) {
	    em.getTransaction().begin();
	    try {
	        Question question = device.getQuestion();
	        question.removeDevice(device);
	        em.merge(question);
			em.remove(em.merge(device));
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		} finally {
          em.getTransaction().commit();
        }
	}
}
