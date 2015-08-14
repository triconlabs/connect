package com.tricon.labs.join.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tricon.labs.join.entity.User;
import com.tricon.labs.join.entity.User.UniqueIdentifierType;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.service.IAuthService;
import com.tricon.labs.join.service.IUserService;

@Controller
@RequestMapping("v1/user")
public class UserController {
	
	@Autowired
	private Environment environment;
	
	/**
	 * 
	 */
	@Resource(name = "userService")
	private IUserService userService;
	
	/**
	 * 
	 */
	@Resource(name = "authService")
	private IAuthService authService;
	
	@RequestMapping(method = RequestMethod.POST)
	public final ModelAndView registerUser(@RequestParam("identifier") String identifier,
			@RequestParam("identifierType") String identifierType,
			@RequestParam("name") String name,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			if(UniqueIdentifierType.PHONE.name().equals(identifierType) && !identifier.startsWith("+"))
				identifier = "+" + identifier.trim();
			User user = this.userService.getUserByIdentifier(identifier);
			if(user == null) {
				user = new User(name, identifier);
				this.userService.add(user);
			}
			mav.addObject("userId", user.getId());
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}
	
/*	@RequestMapping(method = RequestMethod.DELETE)
	public final ModelAndView UnRegisterUser(@RequestParam("identifier") String identifier,
			@RequestParam("idntifierType") String idntifierType,
			@RequestParam("name") String name,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		try {
			JoinSession session = this.authService.validateUser();
			if(UniqueIdentifierType.PHONE.name().equals(idntifierType) && !identifier.startsWith("+"))
				identifier = "+" + identifier.trim();
			User user = session.getUser();
			this.userService.delete(user);
		} catch (ApplicationException e) {
			response.setStatus(e.getResponseCode());
			mav.addObject("err", e.getError());
		}
		return mav;
	}*/
	
}
