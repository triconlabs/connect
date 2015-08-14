package com.tricon.labs.join.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Component;

import com.tricon.labs.join.util.GenUtil;

/**
 * 
 * @author shailesh
 *
 */
@Component
public class AuditInfo implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7083550918679458700L;

	/**
	 * 
	 */
	@CreatedDate
	private Date createdOn;
	
	/**
	 * 
	 */
	@LastModifiedDate
	private Date modifiedOn;
	
	/**
	 * 
	 */
	@Indexed
	private String createdBy;
	
	/**
	 * 
	 */
	@Indexed
	private String modifiedBy;
	
	/**
	 * 
	 */
	private Boolean isDeleted = false;
	
	/**
	 * 
	 */
	public AuditInfo() {
		if (this.createdOn == null)
			this.createdOn = new Date();
		this.modifiedOn = new Date();
		this.isDeleted = false;
		String modifiedBy = GenUtil.getCurrentUser();
		if (createdBy == null)
			this.createdBy = modifiedBy;
		this.modifiedBy = modifiedBy;
		this.isDeleted = false;
	}
	
	public AuditInfo update() {
		if (this.createdOn == null)
			this.createdOn = new Date();
		this.modifiedOn = new Date();
		String modifiedBy = GenUtil.getCurrentUser();
		if (createdBy == null)
			this.createdBy = modifiedBy;
		this.modifiedBy = modifiedBy;
		return this;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
