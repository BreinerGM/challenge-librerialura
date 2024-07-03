package com.librerialura.LibrerAlura.repository;

import com.librerialura.LibrerAlura.model.Autor;
import com.librerialura.LibrerAlura.model.DatosAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.añoDeNacimiento <= :año AND a.añoDeFallecimiento >= :año")
    List<Autor> listaAutores(Integer año);


}
