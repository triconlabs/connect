package com.tricon.labs.join.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tricon.labs.join.entity.JoinSession;
import com.tricon.labs.join.entity.User;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.exceptions.AuthenticationException;
import com.tricon.labs.join.service.IAuthService;
import com.tricon.labs.join.service.IUserService;

/**
 * 
 * @author Priyesh
 *
 */
@Service
public class AuthService implements IAuthService {
	
	/**
	 * 
	 */
	@Resource(name = "userService")
	private IUserService userService;
	
	@Override
	public JoinSession validateUser() throws AuthenticationException, ApplicationException {
		JoinSession joinSession = getCurrentUser();
		if (joinSession == null)
			throw new AuthenticationException("session not found");
		return joinSession;
		
	}

	private JoinSession getCurrentUser() throws ApplicationException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		String currentUserId = request.getHeader("userId");
		if(session.getAttribute("user") != null)
			return (JoinSession) session.getAttribute("user");
		return createUserSession(session, currentUserId);
	}

	private JoinSession createUserSession(HttpSession session, String currentUserId) throws ApplicationException {
		User user = this.userService.get(currentUserId, User.class);
		JoinSession joinSession = new JoinSession(user);
		if(user == null)
			throw new AuthenticationException("not allowed");
		session.setAttribute("user", joinSession);
		session.setAttribute("userId", currentUserId);
		return joinSession;
		
	}
	
}
