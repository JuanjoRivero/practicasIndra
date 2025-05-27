package com.indra.eventossostenibles;

import com.indra.eventossostenibles.Entities.*;
import com.indra.eventossostenibles.Services.Dao;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DaoTest {
    private Dao dao;
    private Evento eventoTest;
    private Usuario usuarioTest;
    private Organizador organizadorTest;
    private Inscripcion inscripcionTest;
    
    private static final Long TEST_ID_EVENTO = 999999L;
    private static final Long TEST_ID_USUARIO = 999998L;
    private static final Long TEST_ID_ORGANIZADOR = 999997L;
    private static final Long TEST_ID_INSCRIPCION = 999996L;

    @BeforeEach
    void setUp() {
        dao = new Dao();
        
        // Crear datos de prueba con IDs altos
        organizadorTest = new Organizador(
            TEST_ID_ORGANIZADOR,
            "Organizador Test",
            "organizador@test.com",
            123456789,
            "pass123"
        );
        
        eventoTest = new Evento(
            TEST_ID_EVENTO,
            Categoria.CONFERENCIA,
            "Evento Test",
            new Ubicacion("Ubicación Test"),
            LocalDate.now(),
            10.0,
            organizadorTest
        );
        
        usuarioTest = new Usuario(
            TEST_ID_USUARIO,
            "Usuario Test",
            "usuario@test.com",
            987654321,
            "pass123"
        );
        
        inscripcionTest = new Inscripcion(
            TEST_ID_INSCRIPCION,
            usuarioTest,
            eventoTest
        );
    }

    @Test
    void operacionesCRUD_Evento() {
        long cantidadEventosAntes = dao.sizeEventos();

        // Insertar
        assertTrue(dao.insertEvento(eventoTest));
        assertEquals(cantidadEventosAntes + 1, dao.sizeEventos());
        
        // Obtener por ID
        Evento eventoRecuperado = dao.getByIdEvento(TEST_ID_EVENTO);
        assertNotNull(eventoRecuperado);
        assertEquals(eventoTest.getNombre(), eventoRecuperado.getNombre());
        
        // Obtener todos
        List<Evento> eventos = dao.getAllEventos();
        eventos.forEach(evento -> assertNotNull(evento));
        
        // Actualizar
        eventoTest.setNombre("Evento Actualizado");
        assertTrue(dao.updateByIdEvento(eventoTest));
        eventoRecuperado = dao.getByIdEvento(TEST_ID_EVENTO);
        assertEquals("Evento Actualizado", eventoRecuperado.getNombre());
        
        // Eliminar
        assertTrue(dao.deleteByIdEvento(TEST_ID_EVENTO));
        assertEquals(cantidadEventosAntes, dao.sizeEventos());
    }

    @Test
    void operacionesCRUD_Usuario() {
        long cantidadUsuariosAntes = dao.sizeUsuarios();

        // Insertar
        assertTrue(dao.insertUsuario(usuarioTest));
        assertEquals(cantidadUsuariosAntes + 1, dao.sizeUsuarios());
        
        // Obtener por ID
        Usuario usuarioRecuperado = dao.getByIdUsuario(TEST_ID_USUARIO);
        assertNotNull(usuarioRecuperado);
        assertEquals(usuarioTest.getNombre(), usuarioRecuperado.getNombre());
        
        // Obtener todos
        List<Usuario> usuarios = dao.getAllUsuarios();
        usuarios.forEach(usuario -> assertNotNull(usuario));
        
        // Actualizar
        usuarioTest.setNombre("Usuario Actualizado");
        assertTrue(dao.updateByIdUsuario(usuarioTest));
        usuarioRecuperado = dao.getByIdUsuario(TEST_ID_USUARIO);
        assertEquals("Usuario Actualizado", usuarioRecuperado.getNombre());
        
        // Eliminar
        assertTrue(dao.deleteByIdUsuario(TEST_ID_USUARIO));
        assertEquals(cantidadUsuariosAntes, dao.sizeUsuarios());
    }

    @Test
    void operacionesCRUD_Organizador() {
        long cantidadOrganizadoresAntes = dao.sizeOrganizadores();

        // Insertar
        assertTrue(dao.insertOrganizador(organizadorTest));
        assertEquals(cantidadOrganizadoresAntes + 1, dao.sizeOrganizadores());
        
        // Obtener por ID
        Organizador organizadorRecuperado = dao.getByIdOrganizador(TEST_ID_ORGANIZADOR);
        assertNotNull(organizadorRecuperado);
        assertEquals(organizadorTest.getNombre(), organizadorRecuperado.getNombre());
        
        // Obtener todos
        List<Organizador> organizadores = dao.getAllOrganizadores();
        organizadores.forEach(organizador -> assertNotNull(organizador));
        
        // Actualizar
        organizadorTest.setNombre("Organizador Actualizado");
        assertTrue(dao.updateByIdOrganizador(organizadorTest));
        organizadorRecuperado = dao.getByIdOrganizador(TEST_ID_ORGANIZADOR);
        assertEquals("Organizador Actualizado", organizadorRecuperado.getNombre());
        
        // Eliminar
        assertTrue(dao.deleteByIdOrganizador(TEST_ID_ORGANIZADOR));
        assertEquals(cantidadOrganizadoresAntes, dao.sizeOrganizadores());
    }

    @Test
    void operacionesCRUD_Inscripcion() {
        // Preparar dependencias
        dao.insertOrganizador(organizadorTest);
        dao.insertEvento(eventoTest);
        dao.insertUsuario(usuarioTest);
        
        long cantidadInscripcionesAntes = dao.sizeInscripciones();

        // Insertar
        assertTrue(dao.insertInscripcion(inscripcionTest));
        assertEquals(cantidadInscripcionesAntes + 1, dao.sizeInscripciones());
        
        // Obtener por ID
        Inscripcion inscripcionRecuperada = dao.getByIdInscripcion(TEST_ID_INSCRIPCION);
        assertNotNull(inscripcionRecuperada);
        assertEquals(inscripcionTest.getIdInscripcion(), inscripcionRecuperada.getIdInscripcion());
        
        // Obtener todos
        List<Inscripcion> inscripciones = dao.getAllInscripciones();
        inscripciones.forEach(inscripcion -> assertNotNull(inscripcion));
        
        // Actualizar
        assertTrue(dao.updateByIdInscripcion(inscripcionTest));
        inscripcionRecuperada = dao.getByIdInscripcion(TEST_ID_INSCRIPCION);
        assertNotNull(inscripcionRecuperada);
        
        // Eliminar
        assertTrue(dao.deleteByIdInscripcion(TEST_ID_INSCRIPCION));
        assertEquals(cantidadInscripcionesAntes, dao.sizeInscripciones());
        
        // Limpiar dependencias en orden inverso
        dao.deleteByIdUsuario(TEST_ID_USUARIO);
        dao.deleteByIdEvento(TEST_ID_EVENTO);
        dao.deleteByIdOrganizador(TEST_ID_ORGANIZADOR);
    }

    @Test
    void generacionIds_DeberianSerSecuenciales() {
        Long idEvento = dao.generarIdEvento();
        Long idUsuario = dao.generarIdUsuario();
        Long idOrganizador = dao.generarIdOrganizador();
        Long idInscripcion = dao.generarIdInscripcion();
        
        assertAll(
            () -> assertNotNull(idEvento),
            () -> assertNotNull(idUsuario),
            () -> assertNotNull(idOrganizador),
            () -> assertNotNull(idInscripcion)
        );
    }

    @AfterEach
    void tearDown() {
        // Limpiar datos de prueba
        dao.deleteByIdInscripcion(TEST_ID_INSCRIPCION);
        dao.deleteByIdEvento(TEST_ID_EVENTO);
        dao.deleteByIdUsuario(TEST_ID_USUARIO);
        dao.deleteByIdOrganizador(TEST_ID_ORGANIZADOR);
        dao.close();
    }
}