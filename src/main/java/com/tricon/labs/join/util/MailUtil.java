package com.tricon.labs.join.util;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import com.tricon.labs.join.entity.Email;

/**
 * 
 * @author Priyesh
 *
 */

public class MailUtil {
	
	
	/**
	 * 
	 */
	public static JavaMailSender mailSender;
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 */
	@Async
	public static void sendTextMail(String from, String to, String subject, String msg) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(msg);
			mailSender.send(message);
		} catch (MailException e) {
			System.out.println("exception in sendTextMail :: " + e.getMessage());
		} catch (Throwable t) {
			System.out.println("exception in sendTextMail :: " + t.getMessage());
		}
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 */
	@Async
	public static void sendTextMail(String from, String to, String subject, 
			String msg, String[] cc, String[] bcc) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(to);
			if (cc != null)
				message.setCc(cc);
			if (bcc != null)
				message.setBcc(bcc);
			message.setSubject(subject);
			message.setText(msg);
			mailSender.send(message);
		} catch (MailException e) {
			System.out.println("exception in sendTextMail :: " + e.getMessage());
		} catch (Throwable t) {
			System.out.println("exception in sendTextMail :: " + t.getMessage());
		}
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 */
	@Async
	public static void sendTextMail(Email email) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(email.getFrom());
			message.setTo(email.getTo());
			if (email.getCc() != null)
				message.setCc(email.getCc());
			if (email.getBcc() != null)
				message.setBcc(email.getBcc());
			message.setSubject(email.getSubject());
			message.setText(email.getBody());
			mailSender.send(message);
		} catch (MailException e) {
			System.out.println("exception in sendTextMail :: " + e.getMessage());
		} catch (Throwable t) {
			System.out.println("exception in sendTextMail :: " + t.getMessage());
		}
	}

}
