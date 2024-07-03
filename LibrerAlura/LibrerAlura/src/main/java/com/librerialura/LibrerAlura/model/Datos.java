package com.librerialura.LibrerAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("results") List<DatosLibro> libros,
        @JsonAlias("count") Double total
) {
}
