package com.little.helpers;

import com.little.helpers.models.Users;
import com.little.helpers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("findAll():");
		for (Users user : repository.findAll()) {
			System.out.println(user);
		}
		System.out.println();

		System.out.println("findByEmail('user1@gmail.com'):");
		System.out.println(repository.findByEmail("user1@gmail.com"));

	}

}
