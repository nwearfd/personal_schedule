package kr.co.personal.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PersonalScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalScheduleApplication.class, args);
	}
}
