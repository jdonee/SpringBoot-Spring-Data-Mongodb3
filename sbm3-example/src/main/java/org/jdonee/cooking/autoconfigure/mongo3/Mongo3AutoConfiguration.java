/**
 * 
 */
package org.jdonee.cooking.autoconfigure.mongo3;

import java.net.UnknownHostException;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

/**
 * NOTE:使用MongoClient替代Mongo旧接口
 * 
 * @author Jdonee
 *
 */
@Configuration
@ConditionalOnClass(MongoClient.class)
@EnableConfigurationProperties(Mongo3Properties.class)
@ConditionalOnMissingBean(type = "org.springframework.data.mongodb.MongoDbFactory")
public class Mongo3AutoConfiguration {
	@Autowired
	private Mongo3Properties properties;

	@Autowired(required = false)
	private MongoClientOptions options;

	private MongoClient mongoClient;

	@PreDestroy
	public void close() {
		if (this.mongoClient != null) {
			this.mongoClient.close();
		}
	}

	@Bean
	@ConditionalOnMissingBean
	public MongoClient mongo() throws UnknownHostException {
		this.mongoClient = this.properties.createMongoClient(this.options);
		return this.mongoClient;
	}

}
