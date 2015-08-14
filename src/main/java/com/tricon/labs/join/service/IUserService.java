package com.tricon.labs.join.service;

import com.tricon.labs.join.entity.User;
import com.tricon.labs.join.exceptions.ApplicationException;

public interface IUserService extends IBaseService<User> {
	
	public User getUserByIdentifier(String identifier) throws ApplicationException;

}
