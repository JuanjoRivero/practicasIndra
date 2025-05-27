package com.indra.eventossostenibles;

import com.indra.eventossostenibles.Entities.*;
import com.indra.eventossostenibles.Services.Almacen;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AlmacenTest {
    private Almacen almacen;
    private Evento eventoTest;
    private Usuario usuarioTest;
    private Organizador organizadorTest;
    private Inscripcion inscripcionTest;

    // IDs para testing
    private static final Long TEST_ID = 99999999L;

    @BeforeEach
    public void setUp() {
        almacen = new Almacen();

        // Crear datos de prueba con IDs altos
        organizadorTest = new Organizador(TEST_ID, "Test Org", "test@org.com", 123456789, "pass123");
        eventoTest = new Evento(TEST_ID, Categoria.CONFERENCIA, "Evento Test",
                new Ubicacion("Test Location"), LocalDate.of(2003,12,12), 2.0, organizadorTest);
        usuarioTest = new Usuario(TEST_ID, "Usuario Test", "test@user.com", 987654321, "pass123");
        inscripcionTest = new Inscripcion(TEST_ID, usuarioTest, eventoTest);
    }

    // Tests para Eventos
    @Test
    public void agregarEvento_DeberiaAgregarEventoCorrectamente() {
        assertTrue(almacen.agregarEvento(eventoTest));
        assertTrue(almacen.getEventos().contains(eventoTest));
    }

    @Test
    public void eliminarEvento_DeberiaEliminarEventoCorrectamente() {
        almacen.agregarEvento(eventoTest);
        assertTrue(almacen.eliminarEvento(TEST_ID));
    }

    @Test
    public void actualizarEvento_DeberiaActualizarEventoCorrectamente() {
        almacen.agregarEvento(eventoTest);
        eventoTest.setNombre("Nuevo Nombre");
        assertTrue(almacen.actualizarEvento(eventoTest));
        assertEquals("Nuevo Nombre", almacen.getEventos().get(almacen.getEventos().size() - 1).getNombre());
    }

    @Test
    public void actualizarEvento_DeberiaFallarSiNoExiste() {
        Evento eventoInexistente = new Evento(999L, Categoria.CONFERENCIA, "No Existe",
                new Ubicacion("Location"), LocalDate.now(), 2.0, organizadorTest);
        assertFalse(almacen.actualizarEvento(eventoInexistente));
    }

    // Tests para Usuarios
    @Test
    public void agregarUsuario_DeberiaAgregarUsuarioCorrectamente() {
        assertTrue(almacen.agregarUsuario(usuarioTest));
        assertTrue(almacen.getUsuarios().contains(usuarioTest));
    }

    @Test
    public void eliminarUsuario_DeberiaEliminarUsuarioCorrectamente() {
        almacen.agregarUsuario(usuarioTest);
        assertTrue(almacen.eliminarUsuario(TEST_ID));
    }

    @Test
    public void actualizarUsuario_DeberiaActualizarUsuarioCorrectamente() {
        almacen.agregarUsuario(usuarioTest);
        usuarioTest.setNombre("Nuevo Nombre Usuario");
        assertTrue(almacen.actualizarUsuario(usuarioTest));
        assertEquals("Nuevo Nombre Usuario", almacen.getUsuarios().get(almacen.getUsuarios().size() - 1).getNombre());
    }

    // Tests para Organizadores
    @Test
    public void agregarOrganizador_DeberiaAgregarOrganizadorCorrectamente() {
        assertTrue(almacen.agregarOrganizador(organizadorTest));
        assertTrue(almacen.getOrganizadores().contains(organizadorTest));
    }

    @Test
    public void eliminarOrganizador_DeberiaEliminarOrganizadorCorrectamente() {
        almacen.agregarOrganizador(organizadorTest);
        assertTrue(almacen.eliminarOrganizador(TEST_ID));
    }

    @Test
    public void actualizarOrganizador_DeberiaActualizarOrganizadorCorrectamente() {
        almacen.agregarOrganizador(organizadorTest);
        organizadorTest.setNombre("Nuevo Nombre Organizador");
        assertTrue(almacen.actualizarOrganizador(organizadorTest));
        assertEquals("Nuevo Nombre Organizador", almacen.getOrganizadores().get(almacen.getOrganizadores().size() - 1).getNombre());
    }

    // Tests para Inscripciones
    @Test
    public void agregarInscripcion_DeberiaAgregarInscripcionCorrectamente() {
        assertTrue(almacen.agregarInscripcion(inscripcionTest));
        assertTrue(almacen.getInscripciones().contains(inscripcionTest));
    }

    @Test
    public void eliminarInscripcion_DeberiaEliminarInscripcionCorrectamente() {
        almacen.agregarInscripcion(inscripcionTest);
        assertTrue(almacen.eliminarInscripcion(TEST_ID));
    }

    @Test
    public void actualizarInscripcion_DeberiaActualizarInscripcionCorrectamente() {
        almacen.agregarInscripcion(inscripcionTest);
        Usuario nuevoUsuario = new Usuario(2L, "Nuevo Usuario", "nuevo@test.com", 111222333, "pass456");
        inscripcionTest.setUsuario(nuevoUsuario);
        assertTrue(almacen.actualizarInscripcion(inscripcionTest));
        assertEquals(nuevoUsuario, almacen.getInscripciones().get(almacen.getInscripciones().size() - 1).getUsuario());
    }

    // Test para verificar la persistencia
    @Test
    public void close_DeberiaPersistirDatosCorrectamente() {
        // Agregar datos de prueba
        almacen.agregarEvento(eventoTest);
        almacen.agregarUsuario(usuarioTest);
        almacen.agregarOrganizador(organizadorTest);
        almacen.agregarInscripcion(inscripcionTest);

        // Cerrar el almacén
        almacen.close();

        // Crear nuevo almacén para verificar la carga
        Almacen nuevoAlmacen = new Almacen();

        // Verificar que los datos se cargaron correctamente
        assertTrue(nuevoAlmacen.getEventos().stream()
                .anyMatch(e -> e.getIdEvento().equals(TEST_ID)));
        assertTrue(nuevoAlmacen.getUsuarios().stream()
                .anyMatch(u -> u.getIdUsuario().equals(TEST_ID)));
        assertTrue(nuevoAlmacen.getOrganizadores().stream()
                .anyMatch(o -> o.getIdOrganizador().equals(TEST_ID)));
        assertTrue(nuevoAlmacen.getInscripciones().stream()
                .anyMatch(i -> i.getIdInscripcion().equals(TEST_ID)));

        // Limpiar los datos de prueba
        nuevoAlmacen.eliminarEvento(TEST_ID);
        nuevoAlmacen.eliminarUsuario(TEST_ID);
        nuevoAlmacen.eliminarOrganizador(TEST_ID);
        nuevoAlmacen.eliminarInscripcion(TEST_ID);
        nuevoAlmacen.close();
    }

    @AfterEach
    public void tearDown() {
        // Eliminar solo los elementos con ID de test
        almacen.eliminarEvento(TEST_ID);
        almacen.eliminarUsuario(TEST_ID);
        almacen.eliminarOrganizador(TEST_ID);
        almacen.eliminarInscripcion(TEST_ID);

        almacen.close();
    }
}
