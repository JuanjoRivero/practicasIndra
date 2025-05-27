package com.indra.eventossostenibles.Services;

import com.indra.eventossostenibles.Entities.Evento;
import com.indra.eventossostenibles.Entities.Inscripcion;
import com.indra.eventossostenibles.Entities.Organizador;
import com.indra.eventossostenibles.Entities.Usuario;

import java.util.List;

public class Dao {
    // ---> INSTACIACIÓN DE LA BASE DE DATOS <--- \\
    CentralBBDD centralBBDD;


    // ---> CONSTRUCTOR <--- \\
    public Dao() {
        centralBBDD = new CentralBBDD();
    }


    // ---> FINALIZAR BBDD <--- \\
    public void close(){
        centralBBDD.close();
    }


    // ---> METODOS DE LA INTERFAZ DAO PARA EVENTO <--- \\
    public Evento getByIdEvento(Long id) {
        return centralBBDD.getEventos().stream().filter(
                evento -> evento.getIdEvento().equals(id)).findFirst().orElse(null);
    }
    public List<Evento> getAllEventos() {
        return (centralBBDD.getEventos());
    }
    public boolean insertEvento(Evento evento) {
        return centralBBDD.insertarEvento(evento);
    }
    public boolean updateByIdEvento(Evento evento) {
        return centralBBDD.actualizarEvento(evento);
    }
    public boolean deleteByIdEvento(Long id) {
        return centralBBDD.eliminarEvento(id);
    }
    public long sizeEventos() {
        return centralBBDD.getEventos().size();
    }


    // ---> METODO DE LA INTERFAZ DAO PARA USUARIO <--- \\
    public Usuario getByIdUsuario(Long id) {
        return centralBBDD.getUsuarios().stream().filter(
                usuario -> usuario.getIdUsuario().equals(id)).findFirst().orElse(null);
    }
    public List<Usuario> getAllUsuarios() {
        return (centralBBDD.getUsuarios());
    }
    public boolean insertUsuario(Usuario usuario) {
        return centralBBDD.insertarUsuario(usuario);
    }
    public boolean updateByIdUsuario(Usuario usuario) {
        return centralBBDD.actualizarUsuario(usuario);
    }
    public boolean deleteByIdUsuario(Long id) {
        return centralBBDD.eliminarUsuario(id);
    }
    public long sizeUsuarios() {
        return centralBBDD.getUsuarios().size();
    }


    // ---> METODO DE LA INTERFAZ DAO PARA ORGANIZADOR <--- \\
    public Organizador getByIdOrganizador(Long id) {
        return centralBBDD.getOrganizadores().stream().filter(
                organizador -> organizador.getIdOrganizador().equals(id)).findFirst().orElse(null);
    }
    public List<Organizador> getAllOrganizadores() {return (centralBBDD.getOrganizadores());}
    public boolean insertOrganizador(Organizador organizador) {return centralBBDD.insertarOrganizador(organizador);}
    public boolean updateByIdOrganizador(Organizador organizador) {return centralBBDD.actualizarOrganizador(organizador);}
    public boolean deleteByIdOrganizador(Long id) {return centralBBDD.eliminarOrganizador(id);}
    public long sizeOrganizadores() {return centralBBDD.getOrganizadores().size();}


    // ---> METODO DE LA INTERFAZ DAO PARA INSCRIPCION <--- \\
    public Inscripcion getByIdInscripcion(Long id) {
        return centralBBDD.getInscripciones().stream().filter(
                inscripcion -> inscripcion.getIdInscripcion().equals(id)).findFirst().orElse(null);
    }
    public List<Inscripcion> getAllInscripciones() {return (centralBBDD.getInscripciones());}
    public boolean insertInscripcion(Inscripcion inscripcion) {
        boolean exito = centralBBDD.insertarInscripcion(inscripcion);
        if (exito) {centralBBDD.completarListadosDeRelaciones(centralBBDD.getInscripciones(), centralBBDD.getEventos(), centralBBDD.getUsuarios(), centralBBDD.getOrganizadores());}
        return exito;
    }
    public boolean updateByIdInscripcion(Inscripcion inscripcion) {return centralBBDD.actualizarInscripcion(inscripcion);}
    public boolean deleteByIdInscripcion(Long id) {
        boolean exito = centralBBDD.eliminarInscripcion(id);
        if (exito) {centralBBDD.completarListadosDeRelaciones(centralBBDD.getInscripciones(), centralBBDD.getEventos(), centralBBDD.getUsuarios(), centralBBDD.getOrganizadores());}
        return exito;}
    public long sizeInscripciones() {return centralBBDD.getInscripciones().size();}


    // ---> METODO DE LA INTERFAZ DAO PARA GENERADOR DE ID <--- \\
    public Long generarIdEvento() {return centralBBDD.generarIdEvento();}
    public Long generarIdUsuario() {return centralBBDD.generarIdUsuario();}
    public Long generarIdOrganizador() {return centralBBDD.generarIdOrganizador();}
    public Long generarIdInscripcion() {return centralBBDD.generarIdInscripcion();}
}
