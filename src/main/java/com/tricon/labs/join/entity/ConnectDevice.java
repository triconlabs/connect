package com.tricon.labs.join.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * TODO device id only should be the primary key, separate id is not required.
 * @author Priyesh
 *
 */
@Document
public class ConnectDevice extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2963240577884537120L;
	
	@Id
	private String deviceId;
	
	private DeviceType deviceType;
	
	private Boolean isActive = true;
	
	private Boolean notificationEnabled;
	
	public enum DeviceType {
		ANDROID, IOS
	}
	
	public ConnectDevice() {
		super();
	}
	
	public ConnectDevice(String deviceId, DeviceType deviceType) {
		super();
		this.deviceId = deviceId;
		this.deviceType = deviceType;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getNotificationEnabled() {
		return notificationEnabled;
	}

	public void setNotificationEnabled(Boolean notificationEnabled) {
		this.notificationEnabled = notificationEnabled;
	}
}
