package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EcommerceBackendApplication {

	@Bean
	CommandLineRunner createAdmin(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder) {

		return args -> {

			if (!userRepository.existsByEmail("admin@gmail.com")) {

				User admin = new User();

				admin.setFirstName("Admin");
				admin.setLastName("User");
				admin.setEmail("admin@gmail.com");
				admin.setPhone("9999999999");

				admin.setPassword(
						passwordEncoder.encode("admin123")
				);

				admin.setRole("ADMIN");

				userRepository.save(admin);

				System.out.println("Admin user created successfully");
			}
		};
	}

	public static void main(String[] args) {

		SpringApplication.run(EcommerceBackendApplication.class, args);


	}

}
