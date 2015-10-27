package org.jdonee.cooking;

import org.jdonee.cooking.customer.Customer;
import org.jdonee.cooking.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoDataAutoConfiguration;

/**
 * 
 * @author Jdonee
 *
 */
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
// spring boot 现有配置不支持mongodb3x的默认验证方式，直接排除
public class Boot implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Boot.class);

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.debug("开始运行...");
		this.repository.deleteAll();

		// save a couple of customers
		this.repository.save(Customer.builder().firstName("大大").lastName("习").build());
		this.repository.save(Customer.builder().firstName("麻麻").lastName("彭").build());

		// fetch all customers
		LOGGER.debug("使用findAll()查找所有的用户:");
		LOGGER.debug("-------------------------------");
		for (Customer customer : this.repository.findAll()) {
			LOGGER.debug(customer.toString());
		}
		System.out.println();

		// fetch an individual customer
		LOGGER.debug("使用findByFirstName('大大')查找:");
		LOGGER.debug("--------------------------------");
		LOGGER.debug(this.repository.findByFirstName("大大").toString());

		LOGGER.debug("使用findByLastName('彭')查找:");
		LOGGER.debug("--------------------------------");
		for (Customer customer : this.repository.findByLastName("彭")) {
			LOGGER.debug(customer.toString());
		}
	}
}
