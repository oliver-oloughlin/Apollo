package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.IoTDevice;
import model.Question;

public class IoTDeviceDAOImpl implements IoTDeviceDAO {

private EntityManager em;
	
	public IoTDeviceDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}

	@Override
	public boolean saveDevice(IoTDevice device) {
	    em.getTransaction().begin();
	    Question question = device.getQuestion();
	    question.addDevice(device);
	    try {
	        em.merge(device);
			em.persist(device);
			return true;
		} catch (EntityExistsException e) {
			return false;
		} finally {
		  em.getTransaction().commit();
		}
	}

	@Override
	public IoTDevice getDevice(long token) {
		return em.find(IoTDevice.class, token);
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
			em.remove(em.merge(device));
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		} finally {
          em.getTransaction().commit();
        }
	}
}
