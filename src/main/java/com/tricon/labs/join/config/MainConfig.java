package com.tricon.labs.join.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 
 * @author Shailesh
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.tricon.labs"}, excludeFilters = { @Filter(Configuration.class) })
@PropertySource({"classpath:application.properties","classpath:env.properties","classpath:connect/connect.properties"})
@EnableAsync
public class MainConfig {

	@Inject
	private Environment environment;
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
