package com.indra.eventossostenibles;

import com.indra.eventossostenibles.Entities.*;
import com.indra.eventossostenibles.Services.MergeInscripciones;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeInscripcionesTest {
    private MergeInscripciones merger;
    private List<Evento> eventos;
    private List<Usuario> usuarios;
    private List<Inscripcion> inscripciones;
    private List<Organizador> organizadores;

    @BeforeEach
    void setUp() {
        merger = new MergeInscripciones();
        eventos = new ArrayList<>();
        usuarios = new ArrayList<>();
        inscripciones = new ArrayList<>();
        organizadores = new ArrayList<>();
        
        // Crear datos de prueba
        Organizador org1 = new Organizador(1L, "Test Org 1", "test1@org.com", 123456789, "pass123");
        Organizador org2 = new Organizador(2L, "Test Org 2", "test2@org.com", 123456788, "pass456");
        organizadores.add(org1);
        organizadores.add(org2);
        
        Evento evento1 = new Evento(1L, Categoria.CONFERENCIA, "Test Event 1", 
                new Ubicacion("Location 1"), LocalDate.now(), 2.0, org1);
        Evento evento2 = new Evento(2L, Categoria.TALLER, "Test Event 2", 
                new Ubicacion("Location 2"), LocalDate.now().plusDays(1), 3.0, org2);
        eventos.add(evento1);
        eventos.add(evento2);
        
        Usuario usuario1 = new Usuario(1L, "Test User 1", "test1@user.com", 987654321, "pass123");
        Usuario usuario2 = new Usuario(2L, "Test User 2", "test2@user.com", 987654322, "pass456");
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        
        inscripciones.add(new Inscripcion(1L, usuario1, evento1));
        inscripciones.add(new Inscripcion(2L, usuario2, evento2));
    }

    @Test
    void añadirEventosAUsuarios_DeberiaAsignarEventosCorrectamente() {
        // Probar con eventosPresentes null
        usuarios.forEach(u -> u.setEventosPresentes(null));
        
        merger.añadirEventosAUsuarios(inscripciones, usuarios);
        // Verificar que cada usuario tiene el evento correcto
        assertEquals(1, usuarios.get(0).getEventosPresentes().size());
        assertEquals(1, usuarios.get(1).getEventosPresentes().size());
        assertEquals(eventos.get(0).getIdEvento(), 
                usuarios.get(0).getEventosPresentes().get(0).getIdEvento());
        assertEquals(eventos.get(1).getIdEvento(), 
                usuarios.get(1).getEventosPresentes().get(0).getIdEvento());
    }

    @Test
    void añadirEventosAOrganizadores_DeberiaAsignarEventosCorrectamente() {
        // Probar con eventosCreados null
        organizadores.forEach(o -> o.setEventosCreados(null));
        
        merger.añadirEventosAOrganizadores(eventos, organizadores);
        
        // Verificar que cada organizador tiene sus eventos asignados
        assertEquals(1, organizadores.get(0).getEventosCreados().size());
        assertEquals(1, organizadores.get(1).getEventosCreados().size());
        assertEquals(eventos.get(0).getIdEvento(), 
                organizadores.get(0).getEventosCreados().get(0).getIdEvento());
        assertEquals(eventos.get(1).getIdEvento(), 
                organizadores.get(1).getEventosCreados().get(0).getIdEvento());
    }

    @Test
    void añadirUsuariosAEventos_DeberiaAsignarUsuariosCorrectamente() {
        // Configurar los eventos presentes de los usuarios
        usuarios.forEach(u -> u.setEventosPresentes(new ArrayList<>()));
        usuarios.get(0).getEventosPresentes().add(eventos.get(0));
        usuarios.get(1).getEventosPresentes().add(eventos.get(1));
        
        // Probar con usuarios null
        eventos.forEach(e -> e.setUsuarios(null));
        
        merger.añadirUsuariosAEventos(eventos, usuarios);
        
        // Verificar que cada evento tiene el usuario correcto
        assertEquals(1, eventos.get(0).getUsuarios().size());
        assertEquals(1, eventos.get(1).getUsuarios().size());
        assertEquals(usuarios.get(0).getIdUsuario(), 
                eventos.get(0).getUsuarios().get(0).getIdUsuario());
        assertEquals(usuarios.get(1).getIdUsuario(), 
                eventos.get(1).getUsuarios().get(0).getIdUsuario());
    }

    @Test
    void añadirEventosAUsuarios_ListaVacia_NoDeberiaLanzarExcepcion() {
        List<Inscripcion> inscripcionesVacias = new ArrayList<>();
        assertDoesNotThrow(() -> 
            merger.añadirEventosAUsuarios(inscripcionesVacias, usuarios)
        );
    }

    @Test
    void añadirEventosAOrganizadores_ListaVacia_NoDeberiaLanzarExcepcion() {
        List<Evento> eventosVacios = new ArrayList<>();
        assertDoesNotThrow(() -> 
            merger.añadirEventosAOrganizadores(eventosVacios, organizadores)
        );
    }

    @Test
    void añadirUsuariosAEventos_ListaVacia_NoDeberiaLanzarExcepcion() {
        List<Usuario> usuariosVacios = new ArrayList<>();
        assertDoesNotThrow(() -> 
            merger.añadirUsuariosAEventos(eventos, usuariosVacios)
        );
    }
}