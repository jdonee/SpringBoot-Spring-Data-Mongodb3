package org.jdonee.cooking;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.DispatcherType;

import org.jdonee.cooking.entity.Customer;
import org.jdonee.cooking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import com.github.dandelion.core.web.DandelionFilter;
import com.github.dandelion.core.web.DandelionServlet;
import com.github.dandelion.datatables.thymeleaf.dialect.DataTablesDialect;
import com.github.dandelion.thymeleaf.dialect.DandelionDialect;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Jdonee
 *
 */
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
// spring boot 现有配置不支持mongodb3x的默认验证方式，直接排除
@Slf4j
public class Boot implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	@Bean
	public DandelionDialect dandelionDialect() {
		return new DandelionDialect();
	}

	@Bean
	public DataTablesDialect dataTablesDialect() {
		return new DataTablesDialect();
	}

	@Bean
	@Order(1)
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		DandelionFilter dandelionFilter = new DandelionFilter();
		registrationBean.setFilter(dandelionFilter);
		List<String> urlPatterns = Lists.newArrayList();
	    urlPatterns.add("/*");
	    registrationBean.setUrlPatterns(urlPatterns);
	    registrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR);
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean dandelionServletRegistrationBean() {
		return new ServletRegistrationBean(new DandelionServlet(), "/dandelion-assets/*");
	}

	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.debug("开始运行...");
		this.repository.deleteAll();

		List<String> lastNames = Lists.newArrayList("曾", "沈", "刘", "李", "童", "习", "彭");
		List<String> firstNames = Lists.newArrayList("家用", "钱钱", "富贵", "千金", "万贯", "戡乱", "轮回", "天下", "明智", "碧君");

		// save a couple of customers
		this.repository.save(Customer.builder().firstName("大大").lastName("习").created(new Date()).build());
		this.repository.save(Customer.builder().firstName("麻麻").lastName("彭").created(new Date()).build());
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			Customer customer = Customer.builder().firstName(firstNames.get(random.nextInt(firstNames.size())))
					.lastName(lastNames.get(random.nextInt(lastNames.size()))).created(new Date()).build();
			this.repository.save(customer);
		}

		// fetch all customers
		log.debug("使用findAll()查找所有的用户:");
		log.debug("-------------------------------");
		for (Customer customer : this.repository.findAll()) {
			log.debug(customer.toString());
		}
		System.out.println();

		// fetch an individual customer
		log.debug("使用findByFirstName('大大')查找:");
		log.debug("--------------------------------");
		log.debug(this.repository.findByFirstName("大大").toString());

		log.debug("使用findByLastName('彭')查找:");
		log.debug("--------------------------------");
		for (Customer customer : this.repository.findByLastName("彭")) {
			log.debug(customer.toString());
		}
	}
}
