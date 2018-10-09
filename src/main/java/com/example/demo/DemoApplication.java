package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping(value = "/")
	public String hello() {
		return "Go to ./exchangerate and try this service."
		+ "<br>Parameter name is the target currency. "
		+ "<br>Parameter value is the total TWD value you wanna exchange to USD.";
	}
}
