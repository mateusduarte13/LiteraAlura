package com.example.LiteraAlura;

import com.example.LiteraAlura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraAluraApplication implements CommandLineRunner {

	@Autowired
	private Principal principal;  // Instância de Principal será injetada pelo Spring

	public static void main(String[] args) {
		SpringApplication.run(LiteraAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Chame o método exibirMenu na instância do Principal
		principal.exibirMenu();
	}
}
