package com.danduran.flavor_finder;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.danduran.flavor_finder.model.Role;
import java.util.List;

import com.danduran.flavor_finder.model.UserEntity;
import com.danduran.flavor_finder.repository.UserRepository;

@SpringBootApplication
public class FlavorFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlavorFinderApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args -> {
			Role roleAdmin = Role.builder()
			.name("ADMIN")
			.build();

			UserEntity userAdmin = UserEntity.builder()
			.userName("admin")
			.password(new BCryptPasswordEncoder().encode("1234"))
			.email("admin@mail.com")
			.isEnabled(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialNoExpired(true)
			.roles(Set.of(roleAdmin))
			.build();

			userRepository.saveAll(List.of(userAdmin));
		};
	}

}
