package org.hua.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class FilesystemApplication {

	private static final Logger log = LoggerFactory.getLogger(FilesystemApplication.class);
	
	public static void main(String[] args) {
//		System.setProperty("spring.data.mongodb.database", "filesystem");
//		System.setProperty("spring.data.mongodb.host", "192.168.1.5");
//		System.setProperty("spring.data.mongodb.username", "root");
//		System.setProperty("spring.data.mongodb.password", "example");
//		System.setProperty("spring.data.mongodb.authentication-database", "admin");
		
		boolean hasConfig = asserNotNull();
		if(!hasConfig) {
			return;
		}
		SpringApplication.run(FilesystemApplication.class, args);
	}
	
	public static boolean asserNotNull() {
		String database = System.getProperty("spring.data.mongodb.database");
		if(StringUtils.isEmpty(database)) {
			log.error("spring.data.mongodb.database >> is null");
			return false;
		}
		String host = System.getProperty("spring.data.mongodb.host");
		if(StringUtils.isEmpty(host)) {
			log.error("spring.data.mongodb.host >> is null");
			return false;
		}
		String username = System.getProperty("spring.data.mongodb.username");
		if(StringUtils.isEmpty(username)) {
			log.error("spring.data.mongodb.username >> is null");
			return false;
		}
		String password = System.getProperty("spring.data.mongodb.password");
		if(StringUtils.isEmpty(password)) {
			log.error("spring.data.mongodb.password >> is null");
			return false;
		}
		String authentication = System.getProperty("spring.data.mongodb.authentication-database");
		if(StringUtils.isEmpty(authentication)) {
			log.error("spring.data.mongodb.authentication-database >> is null");
			return false;
		}
		return true;
	}
}
