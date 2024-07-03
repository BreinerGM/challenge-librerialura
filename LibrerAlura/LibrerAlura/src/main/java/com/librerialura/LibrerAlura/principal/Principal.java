package com.librerialura.LibrerAlura.principal;

import com.librerialura.LibrerAlura.model.*;
import com.librerialura.LibrerAlura.repository.AutorRepository;
import com.librerialura.LibrerAlura.repository.LibroRepository;
import com.librerialura.LibrerAlura.service.ConsumoAPI;
import com.librerialura.LibrerAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<Autor> autores;
    private List<Libro> libros;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    @Autowired
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void menu(){
            try {
                var opcion = -1;
                while (opcion != 0) {
                    var menu = """
                    1 - Buscar libro por titulo.
                    2 - Listar libros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos en un determinado año.
                    5 - Listar libros por idioma.
                                  
                    0 - Salir
                    """;
                    System.out.println(menu);
                    opcion = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion) {
                        case 1:
                            buscarLibroXTitulo();
                            break;
                        case 2:
                            listarLosLibros();
                            break;
                        case 3:
                            listarAutoresRegistrados();
                            break;
                        case 4:
                            listadoDeterminadoAño();
                            break;
                        case 5:
                            listadoLibrosXIdioma();
                            break;
                        case 0:
                            System.out.println("Cerrando la aplicación...");
                            break;
                        default:
                            System.out.println("Opción inválida");
                    }
            }
        }catch (Exception e){
                System.out.println("INGRESE UNA OPCION VALIDA ...");
            }
    }

    private void listadoLibrosXIdioma() {
        String menuIdioma = """
                ********************************************
                Ingrese el idioma para buscar en los libros: 
                es --- Español.
                en --- Ingles.
                fr --- Frances.
                pt --- Portugues.
                ********************************************
                """;
        System.out.println(menuIdioma);
        String idiomaBuscado = teclado.nextLine();
        DatosIdioma idiomaSelec = null;
        switch (idiomaBuscado){
            case "es":
                idiomaSelec = DatosIdioma.valueOf("ESPAÑOL");
                break;
            case "en":
                idiomaSelec = DatosIdioma.valueOf("INGLES");
                break;
            case "fr":
                idiomaSelec = DatosIdioma.valueOf("FRANCES");
                break;
            case "pt":
                idiomaSelec = DatosIdioma.valueOf("PORTUGUES");
                break;
            default:
                System.out.println("Entrada no correcta !...");
                return;
        }
        buscarPorIdioma(idiomaSelec);
    }

    private void buscarPorIdioma(DatosIdioma idiomaSelec) {
        libros = libroRepository.findByIdiomas(idiomaSelec);
        if(libros.isEmpty()){
            System.out.println("No hay ningun libro registrado con ese idioma");
        }else{
            libros.stream()
                    .forEach(System.out::println);
        }
    }

    private void listadoDeterminadoAño() {
        System.out.println("Ingrese el año para listar los autores que estaban vivos");
        var año = teclado.nextInt();
        autores = autorRepository.listaAutores(año);
        System.out.println("***** LISTADO *****");
        autores.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();
        System.out.println("***** LISTADO *****");
        autores.stream()
                .forEach(System.out::println);
    }

    private void listarLosLibros() {
        libros = libroRepository.findAll();
        System.out.println("******* LISTADO *******");
        libros.forEach(l -> System.out.println("***********************"+ "\n" +
                l.toString()));
    }

    private void buscarLibroXTitulo() {
        Datos datos = getDataLibro();
        if (datos != null && !datos.libros().isEmpty()){
            DatosLibro librouno = datos.libros().get(0);
            Libro libro = new Libro(librouno);

            Optional<Libro> libroExistente = libroRepository.findByTitulo(libro.getTitulo());
            if(libroExistente.isPresent()){
                System.out.println("El libro ya se encuentra registrado");
            }else {
                if (!librouno.autor().isEmpty()) {
                    DatosAutor autor = librouno.autor().get(0);
                    Autor autorprimero = new Autor(autor);
                    Optional<Autor> autorOPT = autorRepository.findByNombre(autorprimero.getNombre());

                    if (autorOPT.isPresent()) {
                        Autor autorExiste = autorOPT.get();
                        libro.setAutor(autorExiste);
                        libroRepository.save(libro);
                    } else {
                        Autor autorNuevo = autorRepository.save(autorprimero);
                        libro.setAutor(autorNuevo);
                        libroRepository.save(libro);
                    }

                    Double numDescargas = libro.getNumeroDeDescargas() != null ? libro.getNumeroDeDescargas() : 0;
                    System.out.println("***** Libro *****");
                    System.out.printf("Titulo: %s%nAutor: %s%nIdioma: %s%nNumero de Descargas: %s%n",
                            libro.getTitulo(), autorprimero.getNombre(), libro.getIdiomas(), libro.getNumeroDeDescargas());
                    System.out.println("*******\n");
                } else {
                    System.out.println("No hay autor");
                }
            }

        }else {
            System.out.println("Lo sentimo el libro no fue encontrado");
        }
    }

    private Datos getDataLibro() {
        System.out.println("Escriba el nombre del libro a buscar: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+tituloLibro.replace(" ","%20"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        return datos;
    }


}
