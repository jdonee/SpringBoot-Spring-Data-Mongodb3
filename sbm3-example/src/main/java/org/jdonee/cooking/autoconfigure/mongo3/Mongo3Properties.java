/**
 * 
 */
package org.jdonee.cooking.autoconfigure.mongo3;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Mongodb3配置信息
 * 
 * @author Jdonee
 *
 */
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class Mongo3Properties {

	/**
	 * Mongo database URI. When set, host and port are ignored.
	 */
	private String uri = "mongodb://localhost:27017/test";

	/**
	 * Authentication database name.
	 */
	private String authenticationDatabase = "admin";

	/**
	 * GridFS database name.
	 */
	private String gridFsDatabase;

	public String getAuthenticationDatabase() {
		return this.authenticationDatabase;
	}

	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getGridFsDatabase() {
		return this.gridFsDatabase;
	}

	public void setGridFsDatabase(String gridFsDatabase) {
		this.gridFsDatabase = gridFsDatabase;
	}

	public String getMongoClientDatabase() {
		return new MongoClientURI(this.uri).getDatabase();
	}

	public MongoClient createMongoClient(MongoClientOptions options) throws UnknownHostException {
		try {
			MongoClientURI clientURI = new MongoClientURI(this.uri, builder(options));
			if (options == null) {
				options = MongoClientOptions.builder().build();
			}
			List<String> hosts = clientURI.getHosts();
			System.out.println("当前所有的主机和端口数据：" + hosts.toString());
			List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
			for (String host : hosts) {
				String[] hostArr = host.split(":");
				serverAddressList.add(new ServerAddress(hostArr[0], Integer.valueOf(hostArr[1])));// 主机和端口号
			}
			String username = clientURI.getUsername();
			char[] password = clientURI.getPassword();
			if (username != null && password != null) {
				List<MongoCredential> credentials = Arrays.asList(MongoCredential.createScramSha1Credential(username,
						authenticationDatabase, password));// 启用Mongodb3的默认的登录验证
				return new MongoClient(serverAddressList, credentials, options);
			}
			return new MongoClient(serverAddressList, options);
		} finally {

		}
	}

	private Builder builder(MongoClientOptions options) {
		Builder builder = MongoClientOptions.builder();
		if (options != null) {
			builder = MongoClientOptions.builder(options);
		}
		return builder;
	}
}