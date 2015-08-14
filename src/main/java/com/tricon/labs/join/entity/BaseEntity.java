package com.tricon.labs.join.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6477319144031043456L;

	private Map<String, Object> extraInfo;
	
	private AuditInfo auditInfo;
	
	public BaseEntity() {
		this.extraInfo = new HashMap<String, Object>();
		this.auditInfo = new AuditInfo();
	}

	public Map<String, Object> getExtraInfo() {
		return extraInfo;
	}
	
	public Object getExtraInfo(String key) {
		return extraInfo.get(key);
	}

	public void setExtraInfo(Map<String, Object> extraInfo) {
		this.extraInfo = extraInfo;
	}
	
	public void addExtraInfo(String key, Object value) {
		this.extraInfo.put(key, value);
	}
	
	public AuditInfo auditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	
	public void delete() {
		this.auditInfo.setIsDeleted(true);
		this.auditInfo.update();
	}
	
}
