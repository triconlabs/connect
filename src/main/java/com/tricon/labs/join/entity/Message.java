package com.tricon.labs.join.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6914437680910393760L;

	@Id
	private String id;
	
	@Indexed
	private String sid;
	
	@DBRef
	private User user;
	
	private String text;
	
	private String to;
	
	private Status status;
	
	private Api api;
	
	public enum Status {
		NEW, QUEUED, SENT, DELIVERED, FAILED, UNDELIVERED
	}
	
	public enum Api {
		TWILIO, PLIVO, SMSGATEWAYHUB
	}
	
	public Message() {
		
	}
	
	public Message(String id) {
		this.id = id;
	}
	
	public Message(String id, User user, String text, String to) {
		this.id = id;
		this.user = user;
		this.text = text;
		this.to = to;
	}
	
	public Message(User user, String text, String to) {
		this.user = user;
		this.text = text;
		this.to = to;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

}
