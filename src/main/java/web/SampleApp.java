package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SampleApp {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SampleApp.class, args);
		System.out.println(ctx.getDisplayName());

		System.out.println("This is my first Spring Boot Example");

	}

}
