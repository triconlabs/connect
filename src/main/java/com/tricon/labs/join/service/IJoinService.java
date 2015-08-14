package com.tricon.labs.join.service;

import com.tricon.labs.join.entity.Email;
import com.tricon.labs.join.entity.Message;
import com.tricon.labs.join.exceptions.ApplicationException;

public interface IJoinService extends IBaseService<Message> {
	
	public void sendMessage(Message message) throws ApplicationException;
	
	public void call(String phoneNumber, String url) throws ApplicationException;

	public void sendMail(Email email) throws ApplicationException;

}
