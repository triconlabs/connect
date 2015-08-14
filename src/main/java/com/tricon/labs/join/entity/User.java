package com.tricon.labs.join.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6082169559631852443L;

	@Id
	private String id;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String identifier;
	
	public static enum UniqueIdentifierType {
		PHONE, EMAIL, USERNAME
	}
	
	public User() {
		
	}
	
	public User(String id) {
		this.id = id;
	}

	public User(String name, String identifier) {
		this.name = name;
		this.identifier = identifier;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdntifier() {
		return identifier;
	}

	public void setIdntifier(String Idntifier) {
		this.identifier = Idntifier;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
