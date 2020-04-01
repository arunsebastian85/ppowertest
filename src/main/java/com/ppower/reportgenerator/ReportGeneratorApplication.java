package com.ppower.reportgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ppower"})
public class ReportGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportGeneratorApplication.class, args);
	}

}
