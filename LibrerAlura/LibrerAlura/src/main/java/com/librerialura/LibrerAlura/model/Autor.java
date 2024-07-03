package com.librerialura.LibrerAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer añoDeNacimiento;
    private Integer añoDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.añoDeNacimiento = datosAutor.añoDeNacimiento();
        this.añoDeFallecimiento = datosAutor.añoDeFallecimiento();
    }

    @Override
    public String toString() {

        StringBuilder libroStrn = new StringBuilder();
        libroStrn.append("Libros: ");
        for (int i = 0; i < libros.size(); i++) {
            libroStrn.append(libros.get(i).getTitulo());
            if(i < libros.size()-1){
                libroStrn.append(", ");
            }
        }

        return String.format("*****AUTOR*****%nNombre:" +
        " %s%n%s%nFecha de Nacimiento: %s%nFecha de Fallecimiento:" +
                " %s%n******%n",nombre,libroStrn.toString(),añoDeNacimiento,añoDeFallecimiento);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAñoDeNacimiento() {
        return añoDeNacimiento;
    }

    public void setAñoDeNacimiento(Integer añoDeNacimiento) {
        this.añoDeNacimiento = añoDeNacimiento;
    }

    public Integer getAñoDeFallecimiento() {
        return añoDeFallecimiento;
    }

    public void setAñoDeFallecimiento(Integer añoDeFallecimiento) {
        this.añoDeFallecimiento = añoDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
