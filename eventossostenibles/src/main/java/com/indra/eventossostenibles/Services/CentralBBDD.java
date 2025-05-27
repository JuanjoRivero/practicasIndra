package com.indra.eventossostenibles.Services;

import com.indra.eventossostenibles.Entities.*;

import java.util.List;

public class CentralBBDD {
    // ---> BASE DE DATOS <--- \\
    private Almacen almacen = new Almacen();
    private GeneradorIds generadorIds = new GeneradorIds();
    private MergeInscripciones mergeInscripciones = new MergeInscripciones();


    // ---> CONTRUCTOR <--- \\
    public CentralBBDD() {
        init();
    }


    // ---> INICIALIZAR BBDD <--- \\
    private void init() {
        List<Evento> listaEventos = almacen.getEventos();
        List<Organizador> listaOrganizadores = almacen.getOrganizadores();
        List<Usuario> listaUsuarios = almacen.getUsuarios();
        List<Inscripcion> listaInscripciones = almacen.getInscripciones();

        generadorIds.actualizarId(listaEventos, listaOrganizadores, listaUsuarios, listaInscripciones);
        completarListadosDeRelaciones(listaInscripciones, listaEventos, listaUsuarios, listaOrganizadores);

        System.out.println("BBDD inicializada correctamente.");
    }


    // ---> METODO AUXILIAR PARA init() Y PARA ACTUALIZAR INSCRIPCIONES <--- \\
    public void completarListadosDeRelaciones(List<Inscripcion> listaInscripciones, List<Evento> listaEventos, List<Usuario> listaUsuarios, List<Organizador> listaOrganizadores) {
        List<Inscripcion> inscripcionesValidas = listaInscripciones.stream()
                .filter(inscripcion -> {
                    boolean eventoExiste = listaEventos.stream()
                            .anyMatch(e -> e.getIdEvento().equals(inscripcion.getEvento().getIdEvento()));
                    boolean usuarioExiste = listaUsuarios.stream()
                            .anyMatch(u -> u.getIdUsuario().equals(inscripcion.getUsuario().getIdUsuario()));

                    if (!eventoExiste || !usuarioExiste) {
                        System.out.println("⚠️ Inscripción ignorada - " +
                                (!eventoExiste ? "Evento no existe: " + inscripcion.getEvento().getNombre() : "") +
                                (!usuarioExiste ? "Usuario no existe: " + inscripcion.getUsuario().getNombre() : ""));
                    }

                    return eventoExiste && usuarioExiste;
                })
                .toList();


        mergeInscripciones.añadirEventosAUsuarios(inscripcionesValidas, listaUsuarios);
        mergeInscripciones.añadirEventosAOrganizadores(listaEventos, listaOrganizadores);
        mergeInscripciones.añadirUsuariosAEventos(listaEventos, listaUsuarios);
    }


    // ---> CERRAR BBDD <--- \\
    public void close(){
        almacen.close();
        System.out.println("BBDD cerrada correctamente.");
    }


    // ---> GENERADORES DE ID <--- \\
    public Long generarIdEvento() {return generadorIds.generarIdEvento();}
    public Long generarIdOrganizador() {return generadorIds.generarIdOrganizador();}
    public Long generarIdUsuario() {return generadorIds.generarIdUsuario();}
    public Long generarIdInscripcion() {return generadorIds.generarIdInscripcion();}


    // ---> GETTERS DE LAS LISTAS <--- \\
    public List<Evento> getEventos(){return almacen.getEventos();}
    public List<Organizador> getOrganizadores(){return almacen.getOrganizadores();}
    public List<Usuario> getUsuarios(){return almacen.getUsuarios();}
    public List<Inscripcion> getInscripciones(){return almacen.getInscripciones();}


    // ---> INSERTAR EN BBDD <--- \\
    public boolean insertarUsuario(Usuario usuario) {
        return almacen.agregarUsuario(usuario);
    }
    public boolean insertarOrganizador(Organizador organizador) {
        return almacen.agregarOrganizador(organizador);
    }
    public boolean insertarEvento(Evento evento) {
        return almacen.agregarEvento(evento);
    }
    public boolean insertarInscripcion(Inscripcion inscripcion) {
        return almacen.agregarInscripcion(inscripcion);
    }


    // ---> ELIMINAR EN BBDD <--- \\
    public boolean eliminarEvento(Long id) {return almacen.eliminarEvento(id);}
    public boolean eliminarOrganizador(Long id) {return almacen.eliminarOrganizador(id);}
    public boolean eliminarUsuario(Long id) {return almacen.eliminarUsuario(id);}
    public boolean eliminarInscripcion(Long id) {return almacen.eliminarInscripcion(id);}


    // ---> ACTUALIZAR EN BBDD <--- \\
    public boolean actualizarEvento(Evento evento) {
        return almacen.actualizarEvento(evento);
    }
    public boolean actualizarOrganizador(Organizador organizador) {
        return almacen.actualizarOrganizador(organizador);
    }
    public boolean actualizarUsuario(Usuario usuario) {
        return almacen.actualizarUsuario(usuario);
    }
    public boolean actualizarInscripcion(Inscripcion inscripcion) {
        return almacen.actualizarInscripcion(inscripcion);
    }
}
