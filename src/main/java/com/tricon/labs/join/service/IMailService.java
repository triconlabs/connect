package com.tricon.labs.join.service;

import com.tricon.labs.join.entity.Email;
import com.tricon.labs.join.exceptions.ApplicationException;


public interface IMailService extends IBaseService<Email> {
	
	void sendMail(String from, String to, String subject, String msg) throws ApplicationException;
	
	void sendMail(String from, String to, String subject, String msg, String[] cc, String[] bcc) throws ApplicationException;
	
	void sendMail(Email email) throws ApplicationException;

	void sendSupportMail(String subject, String report) throws ApplicationException;

}
