package com.tricon.labs.join.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tricon.labs.join.entity.JoinSession;
import com.tricon.labs.join.entity.Message;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.service.IAuthService;
import com.tricon.labs.join.service.IMailService;
import com.tricon.labs.join.service.IMessageService;
import com.tricon.labs.join.util.GenUtil;

@Controller
@RequestMapping("v1/util")
public class JoinUtilController {
	
	
	@Autowired
	private Environment environment;
	
	/**
	 * 
	 */
	@Resource(name = "authService")
	private IAuthService authService;
	
	/**
	 * 
	 */
	@Resource(name = "mailService")
	private IMailService mailService;
	
	/**
	 * 
	 */
	@Resource(name = "messageService")
	private IMessageService messageService;
	
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	private ModelAndView crashReport(@RequestParam("report") String report,
			@RequestParam("os") String os, @RequestParam("build") String build,
			@RequestParam("name") String name, @RequestParam("identifier") String identifier,
			@RequestParam("api") String api, @RequestParam("env") String env,
			@RequestParam(value = "type", required = false) String type,
			HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			final JoinSession joinSession = this.authService.validateUser();
			String code = RandomStringUtils.randomNumeric(3);
			String subject = code + " crash report from " + name + " on " + env + " due to " + type;
			this.mailService.sendSupportMail(subject,  "OS : " + os + "\nBiuld : " + build + "\nIdentifier : " + identifier + "\nAPI : " + api + "\nReport : " + report);
			/*String identifiers = GenUtil.getEnvProperty("cherry.support.identifiers", true);
			String []contacts = identifiers.split(",");
			for (String contact : contacts) {
				this.messageService.sendMessage(new Message(joinSession.getUser(), "Issue " + code + " has been logged for cherry support", contact));
			}*/
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/message", method = RequestMethod.PUT)
	private ModelAndView updateStatus(@RequestParam("report") String report,
			@RequestParam("os") String os, @RequestParam("build") String build,
			@RequestParam("name") String name, @RequestParam("identifier") String identifier,
			@RequestParam("api") String api, @RequestParam("env") String env,
			@RequestParam(value = "type", required = false) String type,
			HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			final JoinSession joinSession = this.authService.validateUser();
			String code = RandomStringUtils.randomNumeric(3);
			String subject = code + " crash report from " + name + " on " + env + " due to " + type;
			this.mailService.sendSupportMail(subject,  "OS : " + os + "\nBiuld : " + build + "\nIdentifier : " + identifier + "\nAPI : " + api + "\nReport : " + report);
			String identifiers = GenUtil.getEnvProperty("cherry.support.identifiers", true);
			String []contacts = identifiers.split(",");
			for (String contact : contacts) {
				this.messageService.sendMessage(new Message(joinSession.getUser(), "Issue " + code + " has been logged for cherry support", contact));
			}
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}

}
