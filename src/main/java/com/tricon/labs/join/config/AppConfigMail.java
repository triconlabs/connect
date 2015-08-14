package com.tricon.labs.join.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.util.GenUtil;

/**
 * Configuration file.
 * 
 * @author shailesh
 * 
 */
@Configuration
@EnableAsync
public class AppConfigMail {
	
	private static final String EMAIL_ADDRESS = "mail.config.address";
	private static final String PASSWORD = "mail.config.password";

	/**
	 * 
	 * @return
	 * @throws ApplicationException 
	 */
	@Bean
	public JavaMailSender mailSender() throws ApplicationException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(GenUtil.getEnvProperty(EMAIL_ADDRESS, true));
		mailSender.setPassword(GenUtil.getEnvProperty(PASSWORD, true));
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

}