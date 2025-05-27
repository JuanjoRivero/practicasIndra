package com.indra.eventossostenibles.Services;

import com.indra.eventossostenibles.Entities.Evento;
import com.indra.eventossostenibles.Entities.Inscripcion;
import com.indra.eventossostenibles.Entities.Organizador;
import com.indra.eventossostenibles.Entities.Usuario;

import java.util.List;

public class GeneradorIds {
    // ---> PRÓXIMAS ID GENERADAS <--- \\
    private Long proximoIdEvento = 0L;
    private Long proximoIdUsuario = 0L;
    private Long proximoIdOrganizador = 0L;
    private Long proximoIdInscripcion = 0L;


    // ---> ACTUALIZAR IDs <--- \\

    public void actualizarId(List<Evento> eventos, List<Organizador> organizadores, List<Usuario> usuarios, List<Inscripcion> inscripciones) {
        proximoIdEvento = eventos.isEmpty() ? 0L : eventos.get(eventos.size() - 1).getIdEvento() + 1L;
        proximoIdUsuario = usuarios.isEmpty() ? 0L : usuarios.get(usuarios.size() - 1).getIdUsuario() + 1L;
        proximoIdOrganizador = organizadores.isEmpty() ? 0L : organizadores.get(organizadores.size() - 1).getIdOrganizador() + 1L;
        proximoIdInscripcion = inscripciones.isEmpty() ? 0L : inscripciones.get(inscripciones.size() - 1).getIdInscripcion() + 1L;
    }

    // ---> GENERADOR DE ID <--- \\
    public Long generarIdEvento() {
        return proximoIdEvento++;
    }

    public Long generarIdUsuario() {
        return proximoIdUsuario++;
    }

    public Long generarIdOrganizador() {
        return proximoIdOrganizador++;
    }

    public Long generarIdInscripcion() {
        return proximoIdInscripcion++;
    }
}
