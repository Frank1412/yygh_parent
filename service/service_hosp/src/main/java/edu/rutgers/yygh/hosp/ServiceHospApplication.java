package edu.rutgers.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Shouzhi Fang(frank)
 * @create: 2022-01-15 22:07
 * @description:
 */

@SpringBootApplication
@ComponentScan(basePackages = "edu.rutgers")
public class ServiceHospApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceHospApplication.class, args);
	}
}
