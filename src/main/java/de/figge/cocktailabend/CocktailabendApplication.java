package de.figge.cocktailabend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//http://www.thymeleaf.org/doc/articles/layouts.html
@SpringBootApplication
public class CocktailabendApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CocktailabendApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
	}

}