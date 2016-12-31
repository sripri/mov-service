package com.ram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.apache.catalina.filters.RequestDumperFilter;
import javax.servlet.Filter;
@SpringBootApplication

public class ApplicationConfig {


	Logger log=LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
	
	@Bean
	public FilterRegistrationBean requestDumperFilter() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    Filter requestDumperFilter = new RequestDumperFilter();
	    registration.setFilter(requestDumperFilter);
	    registration.addUrlPatterns("/*");
	    return registration;
	}
}
