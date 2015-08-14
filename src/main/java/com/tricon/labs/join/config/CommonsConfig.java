package com.tricon.labs.join.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.tricon.labs.join.util.GenUtil;

@Configuration
public class CommonsConfig {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	public void setEnvironment() {
		GenUtil.applicationEnvironment = environment;
	}

}
