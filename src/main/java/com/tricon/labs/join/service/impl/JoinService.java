package com.tricon.labs.join.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tricon.labs.join.entity.Email;
import com.tricon.labs.join.entity.Message;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.service.IJoinService;
import com.tricon.labs.join.service.IMailService;
import com.tricon.labs.join.service.IMessageService;
import com.tricon.labs.join.util.CallUtil;

@Service
public class JoinService extends BaseService<Message> implements IJoinService{
	
	/**
	 * 
	 */
	@Resource(name = "messageService")
	private IMessageService messageService;
	
	/**
	 * 
	 */
	@Resource(name = "mailService")
	private IMailService mailService;

	@Override
	public void sendMessage(Message message) throws ApplicationException {
		this.messageService.sendMessage(message);
	}

	@Override
	public void call(String phoneNumber, String url) throws ApplicationException {
		CallUtil.callNumber(phoneNumber, url);
	}

	@Override
	public void sendMail(Email email) throws ApplicationException {
		this.mailService.sendMail(email);
	}
	
}
