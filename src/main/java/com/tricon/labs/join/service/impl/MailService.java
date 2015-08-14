package com.tricon.labs.join.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tricon.labs.join.entity.Email;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.service.IMailService;
import com.tricon.labs.join.util.GenUtil;
import com.tricon.labs.join.util.MailUtil;

@Service
public class MailService extends BaseService<Email> implements IMailService{
	
	private static final String SUPPORT_MAIL_SENDER = "cherry.support.mail.sender";
	private static final String SUPPORT_MAIL_RECEIVER = "cherry.support.mail.receiver";
	
	/**
	 * 
	 * @param mailSender
	 */
	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		MailUtil.mailSender = mailSender;
	}

	@Override
	public void sendMail(String from, String to, String subject, String msg) throws ApplicationException {
		MailUtil.sendTextMail(from, to, subject, msg);
	}
	
	@Override
	public void sendMail(String from, String to, String subject, String msg, String[] cc, String[] bcc) throws ApplicationException {
		MailUtil.sendTextMail(from, to, subject, msg, cc, bcc);
	}

	@Override
	public void sendMail(Email email) throws ApplicationException {
		add(email);
		MailUtil.sendTextMail(email);
	}

	@Override
	public void sendSupportMail(String subject, String msgBody) throws ApplicationException {
		String from = GenUtil.getEnvProperty(SUPPORT_MAIL_SENDER, true);
		String to = GenUtil.getEnvProperty(SUPPORT_MAIL_RECEIVER, true);
		MailUtil.sendTextMail(from , to, subject, msgBody);
		
	}

}
