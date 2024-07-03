package com.librerialura.LibrerAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private DatosIdioma idiomas;
    private Double numeroDeDescargas;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;


    public Libro() {}


    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.idiomas = datosLibro.idioma().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    @Override
    public String toString() {
        StringBuilder strngB = new StringBuilder();
        strngB.append("***** Libro *****\n");
        strngB.append("Título: ").append(titulo).append("\n");
        strngB.append("Autor: ").append(autor != null ? autor.getNombre() : "Desconocido").append("\n");
        strngB.append("Idioma: ").append(idiomas).append("\n");
        strngB.append("Número de descargas: ").append(numeroDeDescargas).append("\n");
        strngB.append("*****\n");

        return strngB.toString();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public DatosIdioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(DatosIdioma idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
