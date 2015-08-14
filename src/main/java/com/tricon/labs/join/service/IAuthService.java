package com.tricon.labs.join.service;

import com.tricon.labs.join.entity.JoinSession;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.exceptions.AuthenticationException;


public interface IAuthService {
	
	public JoinSession validateUser() throws ApplicationException;

}
