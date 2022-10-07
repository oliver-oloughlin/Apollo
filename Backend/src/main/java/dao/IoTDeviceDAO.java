package dao;

import model.IoTDevice;

public interface IoTDeviceDAO {

	/**
	 * Creates a new device in the db
	 * @param device: The device that is to be saved in the db
	 * @return True if the device was created was and false if device already existed in the db
	 */
	public boolean saveDevice(IoTDevice device);
	
	/**
	 * Gets a device from db
	 * @param token: Token for the wanted device
	 * @return The device from db or NULL if it does not exist
	 */
	public IoTDevice getDevice(long token);

	/**
	 * Updates an device
	 * @param Device: The device-object with the updated information
	 * @return The managed updated device
	 */
	public IoTDevice updateDevice(IoTDevice device);

	/**
	 * Deletes a device from db
	 * @param device: The device that is going to be deleted
	 * @return True if deletion was successful and false otherwise
	 */
	public boolean deleteDevice(IoTDevice device);
}
