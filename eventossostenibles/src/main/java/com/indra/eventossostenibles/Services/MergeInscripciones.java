package com.indra.eventossostenibles.Services;

import com.indra.eventossostenibles.Entities.Evento;
import com.indra.eventossostenibles.Entities.Inscripcion;
import com.indra.eventossostenibles.Entities.Organizador;
import com.indra.eventossostenibles.Entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MergeInscripciones {
    public void añadirEventosAUsuarios(List<Inscripcion> inscripciones, List<Usuario> usuarios){
        usuarios.forEach(usuario -> {
            if (usuario.getEventosPresentes() == null) {
                usuario.setEventosPresentes(new ArrayList<>());
            }
        });

        if (inscripciones != null) {
            inscripciones.forEach(inscripcion -> {
                Usuario usuario = inscripcion.getUsuario();
                Evento evento = inscripcion.getEvento();

                usuarios.stream().filter(usuarioAux ->
                        usuarioAux.getIdUsuario().equals(usuario.getIdUsuario())).findFirst().ifPresent(usuarioAAñadirEvento -> {
                    usuarioAAñadirEvento.agregarEvento(evento);
                });
            });
        }
    }

    public void añadirEventosAOrganizadores(List<Evento> eventos, List<Organizador> organizadores){
        organizadores.forEach(organizador -> {
            if ((organizador.getEventosCreados() == null)) {
                organizador.setEventosCreados(new ArrayList<>());
            }
        });

        eventos.forEach(evento -> {
            Organizador organizador = evento.getOrganizador();
            organizadores.stream().filter(
                    organizadorAux -> organizadorAux.getIdOrganizador().equals(organizador.getIdOrganizador()))
                    .findFirst().ifPresent(org -> org.agregarEvento(evento));
        });
    }

    public void añadirUsuariosAEventos(List<Evento> eventos, List<Usuario> usuarios) {
        eventos.forEach(evento -> {
            if (evento.getUsuarios() == null) {
                evento.setUsuarios(new ArrayList<>());
            }
        });

        usuarios.forEach(usuario -> {
            List<Evento> eventoList = usuario.getEventosPresentes();
            eventos.forEach(evento -> {
                eventoList.stream().filter(eventoAux ->
                        eventoAux.getIdEvento().equals(evento.getIdEvento())).findFirst().ifPresent(
                        eventoAux -> evento.getUsuarios().add(usuario));
            });
        });
    }
}
