package com.tricon.labs.join.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tricon.labs.join.entity.Email;
import com.tricon.labs.join.entity.JoinSession;
import com.tricon.labs.join.entity.Message;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.service.IAuthService;
import com.tricon.labs.join.service.IJoinService;
import com.tricon.labs.join.util.GenUtil;


@Controller
@RequestMapping("v1/join")
public class JoinController {
	
	@Autowired
	private Environment environment;
	
	/**
	 * 
	 */
	@Resource(name = "joinService")
	private IJoinService joinService;
	
	/**
	 * 
	 */
	@Resource(name = "authService")
	private IAuthService authService;
	
	@RequestMapping(value = "/message/{contact}", method = RequestMethod.POST)
	public final ModelAndView message(@PathVariable("contact") String contact,
			@RequestParam("text") String text,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			JoinSession session = this.authService.validateUser();
			if(!contact.startsWith("+"))
				contact = "+" + contact.trim();
			Message message = new Message(session.getUser(), text, contact);
			this.joinService.sendMessage(message);
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}
	
	@RequestMapping(value = "/call/{contact}", method = RequestMethod.POST)
	public final ModelAndView call(@PathVariable("contact") String contact,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			if(!contact.startsWith("+"))
				contact = "+" + contact.trim();
			String url = new String();
			this.joinService.call(contact, url);
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}
	
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public final ModelAndView mail(@RequestParam("to") String to, @RequestParam("from") String from,
			@RequestParam("subject") String subject, @RequestParam("body") String body,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			JoinSession session = this.authService.validateUser();
			Email email = new Email(to, from, subject, body, session.getUser());
			this.joinService.sendMail(email);
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/report/", method = RequestMethod.POST)
	public final ModelAndView sendReportForCherry(@RequestParam("text") String text,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			JoinSession session = this.authService.validateUser();
//			if(!contact.startsWith("+"))
//				contact = "+" + contact.trim();
//			Message message = new Message(session.getUser(), text, contact);
//			this.joinService.sendMessage(message);
			Email email = new Email(GenUtil.getEnvProperty("", true), GenUtil.getEnvProperty("", true), "subject", text, session.getUser());
			this.joinService.sendMail(email);
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}
	
}
