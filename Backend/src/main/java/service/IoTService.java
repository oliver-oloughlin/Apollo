package service;

import javax.persistence.EntityExistsException;

import org.apache.shiro.authz.AuthorizationException;

import dao.IoTDeviceDAO;
import model.IoTDevice;
import security.AccessControl;

public class IoTService {

	IoTDeviceDAO dao;
	
	public IoTService(IoTDeviceDAO dao) {
		this.dao = dao;
	}
	
	public boolean addNewDevice(IoTDevice device, AccessControl accessControl)
	    throws EntityExistsException, AuthorizationException {
	    
  	    if(dao.getDevice(device.getToken()) != null) {
          throw new EntityExistsException();
        }
      
        if(accessControl.accessToDevice(device)) {
          return dao.saveDevice(device);
        }
        throw new AuthorizationException();
	}
	
	public IoTDevice getDeviceFromString(String tokenString) {
		try {
			long token  = Long.parseLong(tokenString);
			return dao.getDevice(token);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	public IoTDevice getDevice(long token) {
	  return dao.getDevice(token);
	}
	
	public IoTDevice updateDevice(IoTDevice device) {
		return dao.updateDevice(device);
	}
	
	public IoTDevice deleteDevice(IoTDevice device) {
		boolean success = dao.deleteDevice(device);
		return success ? device : null;
	}
}
