package com.librerialura.LibrerAlura.repository;

import com.librerialura.LibrerAlura.model.Autor;
import com.librerialura.LibrerAlura.model.DatosIdioma;
import com.librerialura.LibrerAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdiomas(DatosIdioma idioma);

    Optional<Libro> findByTitulo(String titulo);

}
