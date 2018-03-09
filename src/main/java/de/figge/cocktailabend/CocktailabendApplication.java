package de.figge.cocktailabend;

import de.figge.cocktailabend.entities.UserAccount;
import de.figge.cocktailabend.repositories.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CocktailabendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocktailabendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(final UserAccountRepository accountRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... arg0) throws Exception {
				accountRepository.save(new UserAccount(arg0[0], arg0[1]));
			}
		};
	}
}
