package com.indra.eventossostenibles;

import com.indra.eventossostenibles.Entities.*;
import com.indra.eventossostenibles.Services.GeneradorIds;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorIdsTest {
    private GeneradorIds generador;

    @BeforeEach
    void setUp() {
        generador = new GeneradorIds();
    }

    @Test
    void generarIdEvento_DebeSerSecuencial() {
        Long id1 = generador.generarIdEvento();
        Long id2 = generador.generarIdEvento();
        assertEquals(id1 + 1, id2);
    }

    @Test
    void actualizarId_DebeActualizarContadoresCorrectamente() {
        List<Evento> eventos = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Organizador> organizadores = new ArrayList<>();
        List<Inscripcion> inscripciones = new ArrayList<>();
        
        eventos.add(new Evento(5L, null, null, null, LocalDate.now(), null, null));
        usuarios.add(new Usuario(6L, null, null, null, null));
        organizadores.add(new Organizador(7L, null, null, null, null));
        inscripciones.add(new Inscripcion(8L, null, null));
        
        generador.actualizarId(eventos, organizadores, usuarios, inscripciones);

        assertTrue(generador.generarIdEvento() == 6);
        assertTrue(generador.generarIdUsuario() == 7);
        assertTrue(generador.generarIdOrganizador() == 8);
        assertTrue(generador.generarIdInscripcion() == 9);
    }
}