package com.familytree.mainapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = { "com.familytree.*" })
public class SpringBootRestApplication {
	Logger logger = LoggerFactory.getLogger(SpringBootRestApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApplication.class, args);
	}

	@GetMapping("/")
	@ResponseBody
	String test() throws Exception {
		logger.info("Rest call to welcome API");
		 return "welcome to FamilyTreeService Spring boot Application";
	}
}

