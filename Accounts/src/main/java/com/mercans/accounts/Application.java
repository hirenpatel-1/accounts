package com.mercans.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.mercans.accounts.common.JsonUtility;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner{
	
	@Autowired
	JsonUtility utilService;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void process() {
		utilService.process();
	}
	@Override
	public void run(String... args) throws Exception {
		process();
	}
}
