package com.tricon.labs.join.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.scheduling.annotation.EnableAsync;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoOptions;
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
public class AppConfigMongo {
	
	/**
	 * Factory bean that creates the com.mongodb.Mongo instance
	 * 
	 * @return mongo factory bean.
	 * @throws Exception 
	 */
	@Bean
	public MongoFactoryBean mongo() throws Exception {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost(GenUtil.getEnvProperty("OPENSHIFT_MONGODB_DB_HOST", true));
		mongo.setPort(Integer.valueOf(GenUtil.getEnvProperty("OPENSHIFT_MONGODB_DB_PORT", true)));
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
		MongoOptions mongoOptions = new MongoOptions(mongoClientOptions);
		mongo.setMongoOptions(mongoOptions);
		return mongo;
	}
	
	@Bean
	public MappingMongoConverter mongoConverter() throws ApplicationException, Exception {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext = new MongoMappingContext();
		MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
		mappingMongoConverter.setMapKeyDotReplacement("\\+");
		return mappingMongoConverter;
	}

	private MongoDbFactory mongoDbFactory() throws Exception {
		String username = null;	String password = null;
		try {
			username = GenUtil.getEnvProperty("OPENSHIFT_MONGODB_DB_USERNAME", true);
			password = GenUtil.getEnvProperty("OPENSHIFT_MONGODB_DB_PASSWORD", true);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
		}
		if (username != null && password != null)
			return new SimpleMongoDbFactory(mongo().getObject(), GenUtil.getEnvProperty("mongo.database", true), new UserCredentials(username, password));
		return new SimpleMongoDbFactory(mongo().getObject(), GenUtil.getEnvProperty("mongo.database", true));
	}

	/**
	 * @return MongoTemplate instance of mongo template.
	 * @throws Exception
	 */
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory(), mongoConverter());
	}
	
}