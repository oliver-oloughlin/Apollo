package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.IoTDevice;

public class IoTDeviceDAOImpl implements IoTDeviceDAO {

private EntityManager em;
	
	public IoTDeviceDAOImpl() {
		em = Persistence.createEntityManagerFactory("ApolloPU").createEntityManager();
	}

	@Override
	public boolean saveDevice(IoTDevice device) {
		try {
			em.getTransaction().begin();
			em.persist(device);
			em.getTransaction().commit();
			return true;
		} catch (EntityExistsException e) {
			return false;
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
		try {
			em.getTransaction().begin();
			em.remove(em.merge(device));
			em.getTransaction().commit();
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}
}
