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
import com.mongodb.ReadPreference;

/**
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
		this.mongoClient.setReadPreference(ReadPreference.secondaryPreferred());// 优先从secondary节点进行读取操作，secondary节点不可用时从主节点读取数据
		return this.mongoClient;
	}

}
