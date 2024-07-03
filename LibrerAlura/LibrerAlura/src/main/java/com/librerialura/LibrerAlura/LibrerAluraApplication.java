package com.librerialura.LibrerAlura;

import com.librerialura.LibrerAlura.principal.Principal;
import com.librerialura.LibrerAlura.repository.AutorRepository;
import com.librerialura.LibrerAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrerAluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibrerAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository,autorRepository);
		principal.menu();
	}
}
