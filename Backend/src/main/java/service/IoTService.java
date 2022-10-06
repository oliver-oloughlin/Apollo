package service;

import dao.IoTDeviceDAO;
import model.IoTDevice;

public class IoTService {

	IoTDeviceDAO dao;
	
	public IoTService(IoTDeviceDAO dao) {
		this.dao = dao;
	}
	
	public IoTDevice addNewDevice(IoTDevice device) {
		boolean success = dao.saveDevice(device);
		return success ? device : null;
	}
	
	public IoTDevice getDevice(String tokenString) {
		try {
			long token  = Long.parseLong(tokenString);
			return dao.getDevice(token);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	public IoTDevice updateDevice(IoTDevice device) {
		return dao.updateDevice(device);
	}
	
	public IoTDevice deleteDevice(String token) {
		IoTDevice device = getDevice(token);
		boolean success = dao.deleteDevice(device);
		return success ? device : null;
	}
}
