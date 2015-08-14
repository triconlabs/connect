package com.tricon.labs.join.service.impl;

import org.springframework.stereotype.Service;

import com.tricon.labs.join.entity.Message;
import com.tricon.labs.join.entity.Message.Api;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.service.IMessageService;
import com.tricon.labs.join.util.MessageUtil;

@Service
public class MessageService extends BaseService<Message> implements IMessageService{

	@Override
	public void sendMessage(Message message) throws ApplicationException {
		if(message.getTo().startsWith("+91")){
			message.setApi(Api.PLIVO);
			MessageUtil.sendMessageInIndia(message.getTo(), message.getText());
			add(message);
			return;
		}
		message.setApi(Api.TWILIO);
		MessageUtil.sendMessage(message.getTo(), message.getText());
		add(message);
	}

}
