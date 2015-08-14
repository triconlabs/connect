package com.tricon.labs.join.service.impl;

import org.springframework.stereotype.Service;

import com.tricon.labs.join.entity.User;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.service.IUserService;

@Service
public class UserService extends BaseService<User> implements IUserService{

	@Override
	public User getUserByIdentifier(String identifier) throws ApplicationException {
		return this.getByParam("identifier", identifier, User.class);
	}

}
