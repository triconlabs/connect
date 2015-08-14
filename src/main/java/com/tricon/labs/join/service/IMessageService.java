package com.tricon.labs.join.service;

import com.tricon.labs.join.entity.Message;
import com.tricon.labs.join.exceptions.ApplicationException;

public interface IMessageService {

	void sendMessage(Message message) throws ApplicationException;

}
