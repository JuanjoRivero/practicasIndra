package com.indra.eventossostenibles;

import com.indra.eventossostenibles.Entities.*;
import com.indra.eventossostenibles.Services.CentralBBDD;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CentralBBDDTest {
    private CentralBBDD bbdd;
    private Evento eventoTest;
    private Usuario usuarioTest;
    private Organizador organizadorTest;
    private Inscripcion inscripcionTest;

    @BeforeEach
    void setUp() {
        bbdd = new CentralBBDD();
        
        // Crear datos de prueba
        organizadorTest = new Organizador(
            bbdd.generarIdOrganizador(), 
            "Test Org", 
            "test@org.com", 
            123456789, 
            "pass123"
        );
        
        eventoTest = new Evento(
            bbdd.generarIdEvento(),
            Categoria.CONFERENCIA,
            "Evento Test",
            new Ubicacion("Test Location"),
            LocalDate.now(),
            2.0,
            organizadorTest
        );
        
        usuarioTest = new Usuario(
            bbdd.generarIdUsuario(),
            "Usuario Test",
            "test@user.com",
            987654321,
            "pass123"
        );

        inscripcionTest = new Inscripcion(bbdd.generarIdInscripcion(), usuarioTest, eventoTest);
    }

    @Test
    void generarIds_DeberianSerUnicos() {
        // Probar generación de IDs para todas las entidades
        Long eventoId1 = bbdd.generarIdEvento();
        Long eventoId2 = bbdd.generarIdEvento();
        Long orgId1 = bbdd.generarIdOrganizador();
        Long orgId2 = bbdd.generarIdOrganizador();
        Long userId1 = bbdd.generarIdUsuario();
        Long userId2 = bbdd.generarIdUsuario();
        Long inscripcionId1 = bbdd.generarIdInscripcion();
        Long inscripcionId2 = bbdd.generarIdInscripcion();

        assertAll(
            () -> assertNotEquals(eventoId1, eventoId2),
            () -> assertNotEquals(orgId1, orgId2),
            () -> assertNotEquals(userId1, userId2),
            () -> assertNotEquals(inscripcionId1, inscripcionId2)
        );
    }

    @Test
    void operacionesCRUD_Evento_DeberianFuncionar() {
        // Create
        assertTrue(bbdd.insertarEvento(eventoTest));
        
        // Read
        List<Evento> eventos = bbdd.getEventos();
        assertTrue(eventos.contains(eventoTest));
        
        // Update
        eventoTest.setNombre("Evento Actualizado");
        assertTrue(bbdd.actualizarEvento(eventoTest));
        
        // Delete
        assertTrue(bbdd.eliminarEvento(eventoTest.getIdEvento()));
        assertFalse(bbdd.getEventos().contains(eventoTest));
    }

    @Test
    void operacionesCRUD_Usuario_DeberianFuncionar() {
        // Create
        assertTrue(bbdd.insertarUsuario(usuarioTest));
        
        // Read
        List<Usuario> usuarios = bbdd.getUsuarios();
        assertTrue(usuarios.contains(usuarioTest));
        
        // Update
        usuarioTest.setNombre("Usuario Actualizado");
        assertTrue(bbdd.actualizarUsuario(usuarioTest));
        
        // Delete
        assertTrue(bbdd.eliminarUsuario(usuarioTest.getIdUsuario()));
        assertFalse(bbdd.getUsuarios().contains(usuarioTest));
    }

    @Test
    void operacionesCRUD_Organizador_DeberianFuncionar() {
        // Create
        assertTrue(bbdd.insertarOrganizador(organizadorTest));
        
        // Read
        List<Organizador> organizadores = bbdd.getOrganizadores();
        assertTrue(organizadores.contains(organizadorTest));
        
        // Update
        organizadorTest.setNombre("Organizador Actualizado");
        assertTrue(bbdd.actualizarOrganizador(organizadorTest));
        
        // Delete
        assertTrue(bbdd.eliminarOrganizador(organizadorTest.getIdOrganizador()));
        assertFalse(bbdd.getOrganizadores().contains(organizadorTest));
    }

    @Test
    void operacionesCRUD_Inscripcion_DeberianFuncionar() {
        // Preparar dependencias
        bbdd.insertarEvento(eventoTest);
        bbdd.insertarUsuario(usuarioTest);
        
        // Create
        assertTrue(bbdd.insertarInscripcion(inscripcionTest));
        
        // Read
        List<Inscripcion> inscripciones = bbdd.getInscripciones();
        assertTrue(inscripciones.contains(inscripcionTest));
        
        // Update
        assertTrue(bbdd.actualizarInscripcion(inscripcionTest));
        
        // Delete
        assertTrue(bbdd.eliminarInscripcion(inscripcionTest.getIdInscripcion()));
        assertFalse(bbdd.getInscripciones().contains(inscripcionTest));
    }

    @Test
    void completarListadosDeRelaciones_DeberiaEstablecerRelacionesCorrectas() {
        // Preparar datos
        bbdd.insertarOrganizador(organizadorTest);
        bbdd.insertarEvento(eventoTest);
        bbdd.insertarUsuario(usuarioTest);
        bbdd.insertarInscripcion(inscripcionTest);

        // Actualizar relaciones
        bbdd.completarListadosDeRelaciones(
            bbdd.getInscripciones(),
            bbdd.getEventos(),
            bbdd.getUsuarios(),
            bbdd.getOrganizadores()
        );

        // Verificar relaciones
        assertTrue(usuarioTest.getEventosPresentes().contains(eventoTest));
        assertTrue(eventoTest.getUsuarios().contains(usuarioTest));
        assertTrue(organizadorTest.getEventosCreados().contains(eventoTest));
    }

    @AfterEach
    void tearDown() {
        bbdd.eliminarEvento(eventoTest.getIdEvento());
        bbdd.eliminarUsuario(usuarioTest.getIdUsuario());
        bbdd.eliminarOrganizador(organizadorTest.getIdOrganizador());
        bbdd.eliminarInscripcion(inscripcionTest.getIdInscripcion());
        bbdd.close();
    }
}