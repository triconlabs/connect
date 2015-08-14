package com.tricon.labs.join.entity;


public class JoinSession {
	
	private User user;
	
	private String userId;
	private String name;
	
	private String identifier;
	
	public JoinSession() {
		super();
	}
	
	public JoinSession(User user) {
		this.user = user;
	}

	public JoinSession(User user, String identifier) {
		super();
		this.identifier = identifier;
		if (user != null) {
			this.name = user.getName();
			this.userId = user.getId();
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProfileId() {
		return userId;
	}
	public void setProfileId(String profileId) {
		this.userId = profileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	


}
