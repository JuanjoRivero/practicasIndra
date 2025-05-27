package com.indra.eventossostenibles;

import com.indra.eventossostenibles.Entities.*;
import com.indra.eventossostenibles.Services.Controlador;
import com.indra.eventossostenibles.Services.Dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Controlador controlador = new Controlador();
    private static final Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual = null;
    private static Organizador organizadorActual = null;

    public static void main(String[] args) {
        iniciarSesion();
        terminarOperacion();
        boolean logueado = true;

        if (usuarioActual != null) {
            while (logueado) {logueado = usuarioMenu();}
        } else if (organizadorActual != null) {
            while (logueado) {logueado = organizadorMenu();}
        }
    }

    private static void iniciarSesion(){
        int opcion = 0;

        System.out.println("================================");
        System.out.println("BIENVENIDO A EVENTOS SOSTENIBLES");
        System.out.println("================================");

        do {
            System.out.println("\nSELECCIONA UNA OPCIÓN: ");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");

            try{
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("\nINTRODUCE EL NUMERO CORRESPONDIENTE A LA OPCION");
                scanner.nextLine();
            }
        }while (opcion < 1 || opcion > 2);


        if (opcion == 1) {
            Object logueado = controlador.login();
            if (logueado instanceof Usuario) {
                usuarioActual = (Usuario) logueado;
            } else if (logueado instanceof Organizador) {
                organizadorActual = (Organizador) logueado;
            }
        }
        if (opcion == 2) {
            Object registered = controlador.register();
            if (registered instanceof Usuario) {
                usuarioActual = (Usuario) registered;
            } else if (registered instanceof Organizador) {
                organizadorActual = (Organizador) registered;
            }
        }
    }
    private static boolean usuarioMenu(){
        int opcion = 0;

        System.out.println("=====================");
        System.out.println("   MENÚ DE USUARIO   ");
        System.out.println("=====================");

        System.out.println("1. Ver mi perfil");
        System.out.println("2. Editar perfil");
        System.out.println("3. Ver eventos disponibles por fecha");
        System.out.println("4. Ver eventos disponibles por categoría");
        System.out.println("5. Ver mis eventos");
        System.out.println("6. Apuntarme a un evento");
        System.out.println("7. Desapuntarme de un evento");
        System.out.println("8. Borrar mi cuenta");
        System.out.println("9. Salir");

        do {
            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("\nINTRODUCE EL NUMERO CORRESPONDIENTE A LA OPCION");
                terminarOperacion();
            }
        } while (opcion < 1 || opcion > 9);
        switch (opcion){
            case 1:
                Usuario usuario = controlador.getByIdUsuario(usuarioActual.getIdUsuario());
                System.out.println(usuario);
                terminarOperacion();
                break;
            case 2:
                if (controlador.actualizarUsuario(usuarioActual)){
                    System.out.println("ACTUALIZADO CON EXITO");
                    terminarOperacion();
                } else {
                    System.out.println("NO SE PUDO ACTUALIZAR");
                    terminarOperacion();
                }
                break;
            case 3:
                List<Evento> eventoListFecha = controlador.getEventosPorFecha();
                if (eventoListFecha.isEmpty()) {
                    System.out.println("NO TIENES EVENTOS DISPONIBLES");
                    terminarOperacion();
                } else {
                    eventoListFecha.forEach(System.out::println);
                    terminarOperacion();
                }
                break;
            case 4:
                List<Evento> eventosListCategoria = controlador.getEventosPorCategoria();
                if (eventosListCategoria.isEmpty()) {
                    System.out.println("NO TIENES EVENTOS DISPONIBLES");
                    terminarOperacion();
                } else {
                    eventosListCategoria.forEach(System.out::println);
                    terminarOperacion();
                }
                break;
            case 5:
                List<Evento> eventosListMios = controlador.getMyEvents(usuarioActual);
                if (eventosListMios.isEmpty()) {
                    System.out.println("NO ESTÁS REGISTRADO EN NINGÚN EVENTO");
                    terminarOperacion();
                } else {
                    eventosListMios.forEach(System.out::println);
                    terminarOperacion();
                }
                break;
            case 6:
                if (controlador.inscribirseAEvento(usuarioActual)){
                    System.out.println("TE INSCRBISTE CON EXITO");
                    terminarOperacion();
                } else {
                    System.out.println("NO TE PUDISTE INSCRIBIR");
                    terminarOperacion();
                }
                break;
            case 7:
                if (controlador.desapuntarseEvento(usuarioActual)){
                    System.out.println("TE DESAPUNTASTE CON EXITO");
                    terminarOperacion();
                } else {
                    System.out.println("NO TE PUDISTE DESAPUNTAR");
                    terminarOperacion();
                }
                break;
            case 8:
                if (controlador.borrarUsuario(usuarioActual)){
                    System.out.println("BORRADO CON EXITO");
                } else {
                    System.out.println("NO SE PUDO BORRAR");
                    terminarOperacion();
                    break;
                }
            case 9:
                controlador.closeDao();
                System.out.println("Saliendo...");
                System.exit(0);
        }
        return true;
    }
    private static boolean organizadorMenu(){
        int opcion = 0;

        System.out.println("=========================");
        System.out.println("   MENÚ DE ORGANIZADOR   ");
        System.out.println("=========================");

        System.out.println("1. Ver mi perfil");
        System.out.println("2. Editar perfil");
        System.out.println("3. Crear un evento");
        System.out.println("4. Borrar un evento");
        System.out.println("5. Ver eventos creados");
        System.out.println("6. Borrar mi cuenta");
        System.out.println("7. Salir");

        do {
            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("\nINTRODUCE EL NUMERO CORRESPONDIENTE A LA OPCION");
                terminarOperacion();
            }
        } while (opcion < 1 || opcion > 7);

        switch (opcion){
            case 1:
                Organizador organizador = controlador.getByIdOrganizador(organizadorActual.getIdOrganizador());
                System.out.println(organizador);
                break;
            case 2:
                if (controlador.actualizarOrganizador(organizadorActual)){
                    System.out.println("ACTUALIZADO CON EXITO");
                    terminarOperacion();
                } else {
                    System.out.println("NO SE PUDO ACTUALIZAR");
                    terminarOperacion();
                }
                break;
            case 3:
                if (controlador.crearEvento(organizadorActual)){
                    System.out.println("CREADO CON EXITO");
                    terminarOperacion();
                } else {
                    System.out.println("NO SE PUDO CREAR");
                    terminarOperacion();
                }
                break;
            case 4:
                if (controlador.deleteByIdEvento(organizadorActual)){
                    System.out.println("BORRADO CON EXITO");
                    terminarOperacion();
                } else {
                    System.out.println("NO SE PUDO BORRAR");
                    terminarOperacion();
                }
                break;
            case 5:
                List<Evento> eventos = controlador.getMyEvents(organizadorActual);
                if (eventos.isEmpty()) {
                    System.out.println("NO TIENES EVENTOS CREADOS");
                    terminarOperacion();
                } else {
                    eventos.forEach(System.out::println);
                    terminarOperacion();
                }
                break;
            case 6:
                if (controlador.borrarOrganizador(organizadorActual)){
                    System.out.println("BORRADO CON EXITO");
                } else {
                    System.out.println("NO SE PUDO BORRAR");
                    terminarOperacion();
                    break;
                }
            case 7:
                controlador.closeDao();
                System.out.println("Saliendo...");
                System.exit(0);
        }
        return true;
    }

    private static void terminarOperacion() {
        System.out.println("\n\n======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================\n\n");
    }
}