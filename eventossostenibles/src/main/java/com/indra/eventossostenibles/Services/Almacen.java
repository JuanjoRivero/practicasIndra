package com.indra.eventossostenibles.Services;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.indra.eventossostenibles.Entities.Evento;
import com.indra.eventossostenibles.Entities.Inscripcion;
import com.indra.eventossostenibles.Entities.Organizador;
import com.indra.eventossostenibles.Entities.Usuario;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Almacen {
    // ---> ALMACENES <--- \\
    private List<Evento> eventos = new ArrayList<>();
    private List<Organizador> organizadores = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Inscripcion> inscripciones = new ArrayList<>();


    // ---> RUTAS DE LOS ALMACENES <--- \\
    private final String URL_EVENTOS = "src/main/resources/Eventos.json";
    private final String URL_ORGANIZADORES = "src/main/resources/Organizadores.json";
    private final String URL_USUARIOS = "src/main/resources/Usuarios.json";
    private final String URL_INSCRIPCIONES = "src/main/resources/Inscripciones.json";


    // ---> CONSTRUCTOR <--- \\
    public Almacen() {
        eventos = new ArrayList<>();
        organizadores = new ArrayList<>();
        usuarios = new ArrayList<>();
        inscripciones = new ArrayList<>();

        cargarAlmacenes();
    }


    // ---> METODO close() DEL ALMACEN <--- \\
    public void close(){
        int correctos = 0;

        if (guardarEventos()) {correctos++;}
        if (guardarOrganizadores()) {correctos++;}
        if (guardarUsuarios()) {correctos++;}
        if (guardarInscripciones()) {correctos++;}

        if (correctos == 4) { System.out.println("Archivos guardados correctamente."); }
        else { System.out.println("No se han podido guardar todos los archivos."); }
    }


    // ---> CARGAR ALMACENES <--- \\
    private void cargarAlmacenes() {
        cargarEventos();
        cargarOrganizadores();
        cargarUsuarios();
        cargarInscripciones();
    }

    private void cargarEventos() {
        try (BufferedReader br = new BufferedReader(new FileReader(URL_EVENTOS))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            String json = br.lines().collect(Collectors.joining());

            Type eventoListType = new TypeToken<List<Evento>>(){}.getType();
            eventos = gson.fromJson(json, eventoListType);
            if (eventos == null) {eventos = new ArrayList<>();}
            else eventos.forEach(Evento::actualizarEstado);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + URL_EVENTOS, e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + URL_EVENTOS, e);
        }
    }

    private void cargarOrganizadores(){
        try (BufferedReader br = new BufferedReader(new FileReader(URL_ORGANIZADORES))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            String json = br.lines().collect(Collectors.joining());

            Type organizadorListType = new TypeToken<List<Organizador>>(){}.getType();
            organizadores = gson.fromJson(json, organizadorListType);
            if (organizadores == null) {organizadores = new ArrayList<>();}
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + URL_ORGANIZADORES, e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + URL_ORGANIZADORES, e);
        }
    }

    private void cargarUsuarios(){
        try (BufferedReader br = new BufferedReader(new FileReader(URL_USUARIOS))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            String json = br.lines().collect(Collectors.joining());

            Type usuarioListType = new TypeToken<List<Usuario>>(){}.getType();
            usuarios = gson.fromJson(json, usuarioListType);
            if (usuarios == null) {usuarios = new ArrayList<>();}
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + URL_USUARIOS, e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + URL_USUARIOS, e);
        }
    }

    private void cargarInscripciones(){
        try (BufferedReader br = new BufferedReader(new FileReader(URL_INSCRIPCIONES))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            String json = br.lines().collect(Collectors.joining());

            Type inscripcionListType = new TypeToken<List<Inscripcion>>(){}.getType();
            inscripciones = gson.fromJson(json, inscripcionListType);
            if (inscripciones == null) {inscripciones = new ArrayList<>();}
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + URL_INSCRIPCIONES, e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + URL_INSCRIPCIONES, e);
        }
    }


    // ---> GUARDAR ALMACENES <--- \\
    private boolean guardarEventos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(URL_EVENTOS))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            eventos.sort(Comparator.comparing(Evento::getIdEvento));
            String json = gson.toJson(eventos);
            bw.write(json);
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean guardarOrganizadores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(URL_ORGANIZADORES))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            organizadores.sort(Comparator.comparing(Organizador::getIdOrganizador));
            String json = gson.toJson(organizadores);
            bw.write(json);
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean guardarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(URL_USUARIOS))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            usuarios.sort(Comparator.comparing(Usuario::getIdUsuario));
            String json = gson.toJson(usuarios);
            bw.write(json);
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private boolean guardarInscripciones() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(URL_INSCRIPCIONES))) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd")
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            List<Inscripcion> inscripcionesFiltradas = inscripciones.stream()
                    .filter(inscripcion -> inscripcion != null && inscripcion.getIdInscripcion() != null)
                    .collect(Collectors.toList());

            inscripcionesFiltradas.sort(Comparator.comparing(Inscripcion::getIdInscripcion));

            String json = gson.toJson(inscripcionesFiltradas);
            bw.write(json);
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    // ---> METODOS DEL ALMACEN DE EVENTOS <--- \\
    public List<Evento> getEventos() {return eventos;}
    public boolean agregarEvento(Evento evento) {return eventos.add(evento);}
    public boolean eliminarEvento(Long id) {return eventos.removeIf(evento -> evento.getIdEvento().equals(id));}
    public boolean actualizarEvento(Evento evento) {
        int index = -1;
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getIdEvento().equals(evento.getIdEvento())) {
                index = i;
                break;
            }
        }

        if (index == -1) return false;

        eventos.set(index, evento);
        return true;
    }


    // ---> METODOS DEL ALMACEN DE USUARIOS <--- \\
    public List<Usuario> getUsuarios() {return usuarios;}
    public boolean agregarUsuario(Usuario usuario) {return usuarios.add(usuario);}
    public boolean eliminarUsuario(Long id) {return usuarios.removeIf(usuario -> usuario.getIdUsuario().equals(id));}
    public boolean actualizarUsuario(Usuario usuario) {
        int index = -1;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario().equals(usuario.getIdUsuario())) {
                index = i;
                break;
            }
        }

        if (index == -1) return false;

        usuarios.set(index, usuario);
        return true;
    }


    // ---> METODOS DEL ALMACEN DE INSCRIPCIONES <--- \\
    public List<Inscripcion> getInscripciones() {return inscripciones;}
    public boolean agregarInscripcion(Inscripcion inscripcion) {return inscripciones.add(inscripcion);}
    public boolean eliminarInscripcion(Long id) {return inscripciones.removeIf(inscripcion -> inscripcion.getIdInscripcion().equals(id));}
    public boolean actualizarInscripcion(Inscripcion inscripcion) {
        int index = -1;
        for (int i = 0; i < inscripciones.size(); i++) {
            if (inscripciones.get(i).getIdInscripcion().equals(inscripcion.getIdInscripcion())) {
                index = i;
                break;
            }
        }

        if (index == -1) return false;

        inscripciones.set(index, inscripcion);
        return true;
    }


    // ---> METODOS DEL ALMACEN DE ORGANIZADORES <--- \\
    public List<Organizador> getOrganizadores() {return organizadores;}
    public boolean agregarOrganizador(Organizador organizador) {return organizadores.add(organizador);}
    public boolean eliminarOrganizador(Long id) {return organizadores.removeIf(organizador -> organizador.getIdOrganizador().equals(id));}
    public boolean actualizarOrganizador(Organizador organizador) {
        int index = -1;
        for (int i = 0; i < organizadores.size(); i++) {
            if (organizadores.get(i).getIdOrganizador().equals(organizador.getIdOrganizador())) {
                index = i;
                break;
            }
        }

        if (index == -1) return false;

        organizadores.set(index, organizador);
        return true;
    }
}


