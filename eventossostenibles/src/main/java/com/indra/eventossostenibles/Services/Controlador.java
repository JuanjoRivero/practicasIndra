package com.indra.eventossostenibles.Services;

import com.indra.eventossostenibles.Entities.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Controlador {
    // ---> INSTANCIACIÓN DE LA BASE DE DATOS Y SCANNER <--- \\
    private Dao dao;
    private Scanner scanner;


    // ---> CONSTRUCTOR Y FINALIZAR BBDD <--- \\
    public Controlador() {
        dao = new Dao();
        scanner = new Scanner(System.in);
    }
    public void closeDao() {
        dao.close();
        scanner.close();
    }


    // ---> METODOS DE LA INTERFAZ DAO PARA EVENTO <--- \\
    public Evento getByIdEvento(Long id) {return dao.getByIdEvento(id);}
    public List<Evento> getAllEventos() {
        List<Evento> eventos = dao.getAllEventos();
        eventos.sort(Comparator.comparing(Evento::getIdEvento));
        return List.copyOf(eventos);
    }
    public List<Evento> getEventosPorCategoria() {
        List<Evento> eventosSinFiltrar = dao.getAllEventos();
        List<Evento> eventos = new ArrayList<>();

        eventosSinFiltrar.forEach(evento -> {if (evento.getEstado() == Estado.ACTIVO) eventos.add(evento);});

        eventos.sort(Comparator.comparing(Evento::getCategoria));
        return List.copyOf(eventos);
    }
    public List<Evento> getEventosPorFecha(){
        List<Evento> eventosSinFiltrar = dao.getAllEventos();
        List<Evento> eventos = new ArrayList<>();

        eventosSinFiltrar.forEach(evento -> {if (evento.getEstado() == Estado.ACTIVO) eventos.add(evento);});

        eventos.sort(Comparator.comparing(Evento::getFecha));
        return List.copyOf(eventos);
    }
    public long sizeEventos() {return dao.sizeEventos();}

    public boolean crearEvento(Organizador organizador) {
        Evento evento = generarEvento(organizador);
        if (evento != null) {
            getByIdOrganizador(organizador.getIdOrganizador()).agregarEvento(evento);
            return dao.insertEvento(evento);
        } else {
            return false;
        }
    }
    public boolean updateByIdEvento(Organizador organizador) {
        Evento eventoActualizado = actualizarEvento(organizador);
        if (eventoActualizado != null) {
            return dao.updateByIdEvento(eventoActualizado);
        } else {
            return false;
        }
    }
    public boolean deleteByIdEvento(Organizador organizador) {return borrarEvento(organizador);}
    
    
    // ---> METODO DE LA INTERFAZ DAO PARA USUARIO <--- \\
    public Usuario getByIdUsuario(Long id) {return dao.getByIdUsuario(id);}
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = dao.getAllUsuarios();
        usuarios.sort(Comparator.comparing(Usuario::getIdUsuario));
        return List.copyOf(usuarios);
    }
    public long sizeUsuarios() {return dao.sizeUsuarios();}

    public boolean actualizarUsuario(Usuario usuario) {
        Object usuarioAActualizar = actualizarCuenta(usuario);
        if (usuarioAActualizar instanceof Usuario) {
            return dao.updateByIdUsuario((Usuario) usuarioAActualizar);
        } else {
            return false;
        }
    }
    public boolean borrarUsuario(Usuario usuario) {return borrarCuenta(usuario);}

    
    // ---> METODO DE LA INTERFAZ DAO PARA ORGANIZADOR <--- \\
    public Organizador getByIdOrganizador(Long id) {return dao.getByIdOrganizador(id);}
    public List<Organizador> getAllOrganizadores() {
        List<Organizador> organizadores = dao.getAllOrganizadores();
        organizadores.sort(Comparator.comparing(Organizador::getIdOrganizador));
        return List.copyOf(organizadores);
    }
    public long sizeOrganizadores() {return dao.sizeOrganizadores();}

    public boolean actualizarOrganizador(Organizador organizador) {
        Object organizadorAActualizar = actualizarCuenta(organizador);
        if (organizadorAActualizar instanceof Organizador) {
            return dao.updateByIdOrganizador((Organizador) organizadorAActualizar);
        } else {
            return false;
        }
    }
    public boolean borrarOrganizador(Organizador organizador) {return borrarCuenta(organizador);}


    // ---> METODO DE LA INTERFAZ DAO PARA INSCRIPCION <--- \\
    public Inscripcion getByIdInscripcion(Long id) {return dao.getByIdInscripcion(id);}
    public List<Inscripcion> getAllInscripciones() {
        List<Inscripcion> inscripciones = dao.getAllInscripciones();
        inscripciones.sort(Comparator.comparing(Inscripcion::getIdInscripcion));
        return List.copyOf(inscripciones);
    }
    public long sizeInscripciones() {return dao.sizeInscripciones();}

    public boolean inscribirseAEvento(Usuario usuario) {
        Inscripcion inscripcion = crearInscripcion(usuario);
        if (inscripcion == null) {
            return false;
        } else {
            return dao.insertInscripcion(inscripcion);
        }
    }
    public boolean desapuntarseEvento(Usuario usuario) {return borrarInscripcion(usuario);}
    
    
    // ---> CREADOR DE ENTIDADES <--- \\
    private Evento generarEvento(Organizador organizador){
        // ---> VARIABLES PARA EL EVENTO <--- \\
        Categoria categoriaEvento = null;
        String nombreEvento;
        Ubicacion ubicacionEvento;
        LocalDate fechaEvento = null;
        Double duracionEvento = 0.0;

        
        // ---> MENU DE ENTRADA DE CREACIÓN DE EVENTO <--- \\
        System.out.println("===================");
        System.out.println("CREACIÓN DE EVENTO:");
        System.out.println("===================");
        
        
        // ---> CREAR NOMBRE DE EVENTO <--- \\
        System.out.println("\nINTRODUCE EL NOMBRE DEL EVENTO: ");
        nombreEvento = scanner.nextLine();

        
        // ---> CREAR CATEGORIA DE EVENTO <--- \\
        System.out.println("\nINTRODUCE EL NÚMERO DE LA CATEGORIA DEL EVENTO: ");
        System.out.println("1. CONFERENCIA");
        System.out.println("2. TALLER");
        System.out.println("3. ACTIVIDAD");
        
        int opcion = 0;
        
        do {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("DEBES INTRODUCIR UN NÚMERO CORRECTO.");
                scanner.nextLine();
            }
        } while (opcion < 1 || opcion > 3);

        categoriaEvento = switch (opcion) {
            case 1 -> Categoria.CONFERENCIA;
            case 2 -> Categoria.TALLER;
            case 3 -> Categoria.ACTIVIDAD;
            default -> null;
        };
        
        
        // ---> CREAR UBICACIÓN DE EVENTO <--- \\
        System.out.println("\nLA UBICACIÓN DEL EVENTO SERÁ...: ");
        System.out.println("1. ONLINE");
        System.out.println("2. PRESENCIAL");

        opcion = 0;

        do {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("DEBES INTRODUCIR UN NÚMERO CORRECTO.");
                scanner.nextLine();
            }
        } while (opcion < 1 || opcion > 2);
        
        if (opcion == 1) {
            ubicacionEvento = new Ubicacion();
        } else {
            System.out.println("\nINTRODUCE LA DIRECCIÓN DEL EVENTO: ");
            String direccionEvento = scanner.nextLine();
            ubicacionEvento = new Ubicacion(direccionEvento);
        }
        
        
        // ---> CREAR FECHA DE EVENTO <--- \\
        System.out.println("\nINTRODUCE LA FECHA DEL EVENTO: (formato dd/MM/yyyy)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            try {
                fechaEvento = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: El formato de fecha debe ser dd/MM/yyyy");
            }
        } while (fechaEvento == null);


        // ---> CREAR DURACIÓN DE EVENTO <--- \\
        System.out.println("\nINTRODUCE LA DURACIÓN DEL EVENTO (en horas): ");
        do {
            try {
                duracionEvento = scanner.nextDouble();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("DEBES INTRODUCIR UN NÚMERO CORRECTO.");
                duracionEvento = null;
                scanner.nextLine();
            }
        } while (duracionEvento == null);


        // ---> CREAR Y RETORNAR EVENTO <--- \\
        return new Evento(
                dao.generarIdEvento(),
                categoriaEvento,
                nombreEvento,
                ubicacionEvento,
                fechaEvento,
                duracionEvento,
                organizador
        );
    }
    public Object register() {
        // ---> VARIABLES COMUNES <--- \\
        String nombre;
        String correo;
        Integer telefono = null;
        String contraseña;
        
        // ---> EXPRESIONES REGULARES PARA VALIDACIÓN <--- \\
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        String telefonoRegex = "^[0-9]{9}$";
        
        // ---> PREGUNTAR TIPO DE REGISTRO <--- \\
        System.out.println("========================");
        System.out.println("SELECCIONE TIPO DE REGISTRO:");
        System.out.println("1. Usuario");
        System.out.println("2. Organizador");
        System.out.println("========================");
        
        int opcion = 0;
        do {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
            } catch (Exception e) {
                System.out.println("DEBES INTRODUCIR UN NÚMERO VÁLIDO (1 o 2).");
                scanner.nextLine();
            }
        } while (opcion != 1 && opcion != 2);

        String tipo = (opcion == 1) ? "USUARIO" : "ORGANIZADOR";
        
        // ---> RECOGER NOMBRE <--- \\
        System.out.println("\nINTRODUCE EL NOMBRE DEL " + tipo + ": ");
        nombre = scanner.nextLine();

        // ---> RECOGER Y VALIDAR EMAIL <--- \\
        boolean emailValido = false;
        do {
            System.out.println("\nINTRODUCE EL CORREO DEL " + tipo + ": ");
            correo = scanner.nextLine();
            
            if (correo.matches(emailRegex)) {
                emailValido = true;
            } else {
                System.out.println("FORMATO DE EMAIL INCORRECTO. DEBE SER EJEMPLO@DOMINIO.COM");
            }
        } while (!emailValido);

        // ---> RECOGER Y VALIDAR TELÉFONO <--- \\
        String telefonoStr;
        boolean telefonoValido = false;
        do {
            System.out.println("\nINTRODUCE EL TELÉFONO DEL " + tipo + " (9 dígitos): ");
            telefonoStr = scanner.nextLine();
            
            if (telefonoStr.matches(telefonoRegex)) {
                try {
                    telefono = Integer.parseInt(telefonoStr);
                    telefonoValido = true;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL PROCESAR EL NÚMERO.");
                }
            } else {
                System.out.println("EL TELÉFONO DEBE CONTENER EXACTAMENTE 9 DÍGITOS.");
            }
        } while (!telefonoValido);

        // ---> GESTIÓN DE CONTRASEÑA <--- \\
        do {
            System.out.println("\nINTRODUCE LA CONTRASEÑA DEL " + tipo + ": ");
            contraseña = scanner.nextLine();

            System.out.println("\nINTRODUCE LA CONTRASEÑA DE NUEVO: ");
            String contraseñaNueva = scanner.nextLine();
            if (!contraseñaNueva.equals(contraseña)) {
                System.out.println("NO COINCIDEN LAS CONTRASEÑAS. INTRODUCE LAS NUEVAS CONTRASEÑAS Y VUELVE A INTENTARLO.");
                contraseña = null;
            }
        } while (contraseña == null);
            
        if (opcion == 1){
            Usuario user = new Usuario(dao.generarIdUsuario(), nombre, correo, telefono, contraseña);
            if (dao.insertUsuario(user)){
                return user;
            } else {
                return null;

            }
        } else {
            Organizador organizador = new Organizador(dao.generarIdOrganizador(), nombre, correo, telefono, contraseña);
            if (dao.insertOrganizador(organizador)){
                return organizador;
            } else {
                return null;
            }
        }
    }
    private Inscripcion crearInscripcion(Usuario usuario) {
        // ---> MENU DE ENTRADA DE CREACIÓN DE INSCRIPCIÓN <--- \\
        List<Evento> eventos = dao.getAllEventos();
        System.out.println("=================================");
        System.out.println("¿A QUÉ EVENTO DESEAS INSCRIBIRTE?");
        System.out.println("=================================");
        eventos.forEach(evento -> System.out.println(evento.getIdEvento() + " - " + evento.getNombre()));
        System.out.println("\nIntroduzca el ID del evento:");


        // ---> VALIDAR QUE EL ID EXISTA Y NO SE REGISTRE EN UNO YA REGISTRADO <--- \\
        Evento evento;
        do {
            try {
                String input = scanner.nextLine();
                if (input.equals("/q")) {
                    return null;
                }
                Long eventoId = Long.parseLong(input);
                evento = eventos.stream()
                        .filter(event -> event.getIdEvento().equals(eventoId))
                        .findFirst()
                        .orElse(null);
                if (evento == null) {
                    System.out.println("EL ID NO COINCIDE CON NINGÚN EVENTO. INTENTE DE NUEVO O SALGA ESCRIBIENDO /q");
                }
                if (usuario.getEventosPresentes().stream().filter(
                        eventoAux -> eventoAux.getIdEvento().equals(eventoId)).count() > 0) {
                    System.out.println("EL USUARIO YA ESTA INSCRITO AL EVENTO. INTENTE DE NUEVO O SALGA ESCRIBIENDO /q");
                    evento = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("DEBE INTRODUCIR UN NÚMERO VÁLIDO. INTENTE DE NUEVO O SALGA ESCRIBIENDO /q");
                evento = null;
            }
        } while (evento == null);

        return new Inscripcion(
                dao.generarIdInscripcion(),
                usuario,
                evento
        );
    }


    // ---> ELIMINADOR DE ENTIDADES <--- \\
    private boolean borrarCuenta(Object object){
        // ---> DETERMINAR TIPO Y OBTENER DATOS <--- \\
        String tipo = (object instanceof Usuario) ? "USUARIO" : "ORGANIZADOR";
        String contraseña = (object instanceof Usuario) ?
                ((Usuario) object).getContraseña() :
                ((Organizador) object).getContraseña();
        Long id = (object instanceof Usuario) ?
                ((Usuario) object).getIdUsuario() :
                ((Organizador) object).getIdOrganizador();

        // ---> VERIFICAR CONTRASEÑA <--- \\
        System.out.println("INTRODUCE SU CONTRASEÑA PARA ELIMINAR EL " + tipo + ":");
        String password = scanner.nextLine();
        if (!contraseña.equals(password)) {
            System.out.println("CONTRASEÑA INCORRECTA. INTRODUCE LA CONTRASEÑA CORRECTA Y VUELVE A INTENTARLO.");
            return false;
        }

        // ---> MENSAJE DE CONFIRMACIÓN ESPECÍFICO <--- \\
        if (object instanceof Organizador) {
            System.out.println("AUNQUE EL ORGANIZADOR SE ELIMINE, SE MANTENDRAN LOS EVENTOS QUE SE HAGAN EN EL MISMO.");
        } else {
            System.out.println("SE ELIMINARÁN TODAS LAS INSCRIPCIONES ASOCIADAS AL USUARIO.");
        }

        System.out.println("¿Está seguro de continuar? S/n");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("n")) {
            return false;
        }

        if (object instanceof Organizador) {
            getAllEventos().forEach(evento -> {
                if (evento.getOrganizador().getIdOrganizador().equals(id)) {
                    evento.cancelarEvento();
                }
            });
        }

        // ---> ELIMINAR SEGÚN TIPO <--- \\
        return (object instanceof Usuario) ?
                dao.deleteByIdUsuario(id) :
                dao.deleteByIdOrganizador(id);
    }
    private boolean borrarInscripcion(Usuario usuario) {
        // ---> VALIDAR SI EL USUARIO ESTA INSCRITO AL EVENTO <--- \\
        if (usuario.getEventosPresentes().isEmpty()) {
            System.out.println("EL USUARIO NO ESTA INSCRITO AL EVENTO.");
            return false;
        }


        // ---> ORDENAR LAS INSCRIPCIONES POR EL ID DEL EVENTO <--- \\
        List<Evento> eventosList = usuario.getEventosPresentes();
        eventosList.sort(Comparator.comparing(Evento::getIdEvento));


        // ---> MENU DE ENTRADA DE CREACIÓN DE INSCRIPCIÓN <--- \\
        System.out.println("===========================================");
        System.out.println("INDICA EL EVENTO AL QUE DESEA DESAPUNTARSE: ");
        System.out.println("===========================================");
        eventosList.forEach(evento -> System.out.println(evento.getIdEvento() + " - " + evento.getNombre()));
        System.out.println("\nINTRODUCE EL ID DEL EVENTO AL QUE DESEA DESAPUNTARSE: ");


        // ---> VALIDAR QUE EL ID DEL EVENTO EXISTA <--- \\
        Evento evento = null;
        boolean salir = false;
        do {
            try {
                String input = scanner.nextLine();
                if (input.equals("/q")) {
                    salir = true;
                }
                Long eventoId = Long.parseLong(input);
                evento = usuario.getEventosPresentes().stream()
                        .filter(event -> event.getIdEvento().equals(eventoId))
                        .findFirst()
                        .orElse(null);
                if (evento == null) {
                    System.out.println("EL ID NO COINCIDE CON NINGÚN EVENTO. INTENTE DE NUEVO O SALGA ESCRIBIENDO /q");
                }
            } catch (NumberFormatException e) {
                System.out.println("DEBE INTRODUCIR UN NÚMERO VÁLIDO. INTENTE DE NUEVO O SALGA ESCRIBIENDO /q");
            }
        } while (evento == null);

        if (salir){
            return false;
        }

        // ---> ELIMINAR INSCRIPCIÓN <--- \\
        Long idEventoFinal = evento.getIdEvento();
        return dao.getAllInscripciones().stream()
                .filter(inscripcion -> inscripcion.getEvento().getIdEvento().equals(idEventoFinal))
                .findFirst()
                .map(inscripcion -> dao.deleteByIdInscripcion(inscripcion.getIdInscripcion()))
                .orElse(false);
    }
    private boolean borrarEvento(Organizador organizador) {
        // ---> VARIABLES PARA LA ELIMINACION DEL EVENTO <--- \\
        List<Evento> eventoList = organizador.getEventosCreados();
        eventoList.sort(Comparator.comparing(Evento::getIdEvento));
        Evento eventoABorrar = null;


        // ---> MENÚ DE ENTRADA <--- \\
        System.out.println("===========================================");
        System.out.println("INDICA EL EVENTO AL QUE DESEA ELIMINARSE: ");
        System.out.println("===========================================");
        eventoList.forEach(evento -> System.out.println(evento.getIdEvento() + " - " + evento.getNombre()));

        System.out.println("\nINTRODUCE EL ID DEL EVENTO AL QUE DESEA ELIMINARSE: ");
        do {
            try {
                String input = scanner.nextLine();
                if (input.equals("/q")) {
                    return false;
                }
                Long eventoId = Long.parseLong(input);
                eventoABorrar = eventoList.stream()
                        .filter(event -> event.getIdEvento().equals(eventoId))
                        .findFirst()
                        .orElse(null);
            } catch (Exception e) {
                eventoABorrar = null;
                System.out.println("DEBES INTRODUCIR UN NÚMERO VÁLIDO. INTENTE DE NUEVO O SALGA ESCRIBIENDO /q");
            }
        } while (eventoABorrar == null);

        return dao.deleteByIdEvento(eventoABorrar.getIdEvento());
    }


    // ---> MODIFICADOR DE ENTIDADES <--- \\
    private Object actualizarCuenta(Object persona) {
        // ---> VALIDAR TIPO DE OBJETO <--- \\
        if (!(persona instanceof Usuario) && !(persona instanceof Organizador)) {
            System.out.println("TIPO DE USUARIO NO VÁLIDO");
            return null;
        }


        // ---> DETERMINAR TIPO Y OBTENER DATOS ACTUALES <--- \\
        String nombreActual = (persona instanceof Usuario) ?
                ((Usuario) persona).getNombre() :
                ((Organizador) persona).getNombre();
        String correoActual = (persona instanceof Usuario) ?
                ((Usuario) persona).getCorreo() :
                ((Organizador) persona).getCorreo();
        Integer telefonoActual = (persona instanceof Usuario) ?
                ((Usuario) persona).getTelefono() :
                ((Organizador) persona).getTelefono();
        String contraseñaActual = (persona instanceof Usuario) ?
                ((Usuario) persona).getContraseña() :
                ((Organizador) persona).getContraseña();
        Long id = (persona instanceof Usuario) ?
                ((Usuario) persona).getIdUsuario() :
                ((Organizador) persona).getIdOrganizador();


        // ---> EXPRESIONES REGULARES PARA VALIDACIÓN <--- \\
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        String telefonoRegex = "^[0-9]{9}$";


        // ---> ACTUALIZAR NOMBRE <--- \\
        String nombreNuevo = nombreActual;
        System.out.println("\n¿Desea modificar el nombre? (S/n)");
        System.out.println("Nombre actual: " + nombreActual);
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            System.out.println("Introduce el nuevo nombre:");
            nombreNuevo = scanner.nextLine();
        }


        // ---> ACTUALIZAR CORREO <--- \\
        String correoNuevo = correoActual;
        System.out.println("\n¿Desea modificar el correo? (S/n)");
        System.out.println("Correo actual: " + correoActual);
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            boolean emailValido = false;
            do {
                System.out.println("Introduce el nuevo correo:");
                correoNuevo = scanner.nextLine();
                if (correoNuevo.matches(emailRegex)) {
                    emailValido = true;
                } else {
                    System.out.println("FORMATO DE EMAIL INCORRECTO. DEBE SER EJEMPLO@DOMINIO.COM");
                }
            } while (!emailValido);
        }


        // ---> ACTUALIZAR TELÉFONO <--- \\
        Integer telefonoNuevo = telefonoActual;
        System.out.println("\n¿Desea modificar el teléfono? (S/n)");
        System.out.println("Teléfono actual: " + telefonoActual);
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            boolean telefonoValido = false;
            do {
                System.out.println("Introduce el nuevo teléfono (9 dígitos):");
                String telefonoStr = scanner.nextLine();
                if (telefonoStr.matches(telefonoRegex)) {
                    try {
                        telefonoNuevo = Integer.parseInt(telefonoStr);
                        telefonoValido = true;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL PROCESAR EL NÚMERO.");
                    }
                } else {
                    System.out.println("EL TELÉFONO DEBE CONTENER EXACTAMENTE 9 DÍGITOS.");
                }
            } while (!telefonoValido);
        }


        // ---> ACTUALIZAR CONTRASEÑA <--- \\
        String contraseñaNueva = contraseñaActual;
        System.out.println("\n¿Desea modificar la contraseña? (S/n)");
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            // Verificar contraseña actual
            System.out.println("Introduce la contraseña actual:");
            if (!scanner.nextLine().equals(contraseñaActual)) {
                System.out.println("CONTRASEÑA INCORRECTA. OPERACIÓN CANCELADA.");
                return persona;
            }

            // Establecer nueva contraseña con verificación
            do {
                System.out.println("Introduce la nueva contraseña:");
                contraseñaNueva = scanner.nextLine();

                System.out.println("Introduce la nueva contraseña de nuevo:");
                String contraseñaVerificacion = scanner.nextLine();

                if (!contraseñaVerificacion.equals(contraseñaNueva)) {
                    System.out.println("LAS CONTRASEÑAS NO COINCIDEN. INTÉNTALO DE NUEVO.");
                    contraseñaNueva = contraseñaActual;
                }
            } while (contraseñaNueva.equals(contraseñaActual));
        }


        // ---> CREAR Y RETORNAR OBJETO ACTUALIZADO <--- \\
        if (persona instanceof Usuario) {
            return new Usuario(id, nombreNuevo, correoNuevo, telefonoNuevo, contraseñaNueva);
        } else {
            return new Organizador(id, nombreNuevo, correoNuevo, telefonoNuevo, contraseñaNueva);
        }
    }
    private Evento actualizarEvento(Organizador organizador) {
        // ---> VERIFICAR SI EL ORGANIZADOR TIENE EVENTOS <--- \\
        List<Evento> eventosCreados = organizador.getEventosCreados();
        if (eventosCreados == null || eventosCreados.isEmpty()) {
            System.out.println("NO TIENES EVENTOS CREADOS PARA ACTUALIZAR");
            return null;
        }
        eventosCreados.sort(Comparator.comparing(Evento::getIdEvento));


        // ---> MOSTRAR Y SELECCIONAR EVENTO A ACTUALIZAR <--- \\
        System.out.println("\n=== EVENTOS DISPONIBLES PARA ACTUALIZAR ===");
        for (int i = 0; i < eventosCreados.size(); i++) {
            System.out.println((i + 1) + ". " + eventosCreados.get(i).getNombre());
        }

        int seleccion = -1;

        do {
            System.out.println("\nSelecciona el id del evento a actualizar (/q para cancelar):");
            try {
                String input = scanner.nextLine();
                if (input.equals("/q")) {
                    System.out.println("OPERACIÓN CANCELADA");
                    return null;
                }
                seleccion = Integer.parseInt(input);
                if (seleccion < 0 || seleccion > eventosCreados.size()) {
                    System.out.println("SELECCIÓN NO VÁLIDA");
                }
            } catch (NumberFormatException e) {
                System.out.println("ENTRADA NO VÁLIDA");
            }
        } while (seleccion > 0 && seleccion < eventosCreados.size());


        // ---> OBTENER EVENTO SELECCIONADO <--- \\
        Evento eventoSeleccionado = eventosCreados.get(seleccion - 1);


        // ---> DATOS ACTUALES DEL EVENTO <--- \\
        Long idEvento = eventoSeleccionado.getIdEvento();
        Categoria categoriaActual = eventoSeleccionado.getCategoria();
        String nombreActual = eventoSeleccionado.getNombre();
        Ubicacion ubicacionActual = eventoSeleccionado.getUbicacion();
        LocalDate fechaActual = eventoSeleccionado.getFecha();
        Double duracionActual = eventoSeleccionado.getDuracion();


        // ---> ACTUALIZAR NOMBRE <--- \\
        String nombreNuevo = nombreActual;
        System.out.println("\n¿Desea modificar el nombre del evento? (S/n)");
        System.out.println("Nombre actual: " + nombreActual);
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            System.out.println("Introduce el nuevo nombre:");
            nombreNuevo = scanner.nextLine();
        }

        // ---> ACTUALIZAR CATEGORÍA <--- \\
        Categoria categoriaNueva = categoriaActual;
        System.out.println("\n¿Desea modificar la categoría del evento? (S/n)");
        System.out.println("Categoría actual: " + categoriaActual);
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            System.out.println("Seleccione la nueva categoría:");
            System.out.println("1. CONFERENCIA");
            System.out.println("2. TALLER");
            System.out.println("3. ACTIVIDAD");
            boolean categoriaValida = false;
            do {
                try {
                    int opcion = Integer.parseInt(scanner.nextLine());
                    switch (opcion) {
                        case 1:
                            categoriaNueva = Categoria.CONFERENCIA;
                            categoriaValida = true;
                            break;
                        case 2:
                            categoriaNueva = Categoria.TALLER;
                            categoriaValida = true;
                            break;
                        case 3:
                            categoriaNueva = Categoria.ACTIVIDAD;
                            categoriaValida = true;
                            break;
                        default:
                            System.out.println("OPCIÓN NO VÁLIDA. INTRODUCE UN NÚMERO DEL 1 AL 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ENTRADA NO VÁLIDA. INTRODUCE UN NÚMERO DEL 1 AL 3.");
                }
            } while (!categoriaValida);
        }

        // ---> ACTUALIZAR UBICACIÓN <--- \\
        Ubicacion ubicacionNueva = ubicacionActual;
        System.out.println("\n¿Desea modificar la ubicación del evento? (S/n)");
        System.out.println("Ubicación actual: " + ubicacionActual);
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            System.out.println("Introduce la nueva ubicación:");
            ubicacionNueva = new Ubicacion(scanner.nextLine());
        }

        // ---> ACTUALIZAR FECHA <--- \\
        LocalDate fechaNueva = fechaActual;
        System.out.println("\n¿Desea modificar la fecha del evento? (S/n)");
        System.out.println("Fecha actual: " + fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            boolean fechaValida = false;
            do {
                System.out.println("Introduce la nueva fecha (formato DD/MM/AAAA):");
                String fechaStr = scanner.nextLine();
                try {
                    fechaNueva = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    // Validar que la fecha no sea en el pasado
                    if (fechaNueva.isBefore(LocalDate.now())) {
                        System.out.println("LA FECHA NO PUEDE SER ANTERIOR A HOY");
                    } else {
                        fechaValida = true;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("FORMATO DE FECHA INCORRECTO. DEBE SER DD/MM/AAAA");
                }
            } while (!fechaValida);
        }

        // ---> ACTUALIZAR DURACIÓN <--- \\
        Double duracionNueva = duracionActual;
        System.out.println("\n¿Desea modificar la duración del evento? (S/n)");
        System.out.println("Duración actual: " + duracionActual + " horas");
        if (!scanner.nextLine().equalsIgnoreCase("n")) {
            boolean duracionValida = false;
            do {
                System.out.println("Introduce la nueva duración en horas:");
                try {
                    duracionNueva = Double.parseDouble(scanner.nextLine());
                    if (duracionNueva <= 0) {
                        System.out.println("LA DURACIÓN DEBE SER UN VALOR POSITIVO");
                    } else {
                        duracionValida = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("FORMATO DE DURACIÓN INCORRECTO. DEBE SER UN NÚMERO DECIMAL");
                }
            } while (!duracionValida);
        }

        // ---> CREAR Y RETORNAR EVENTO ACTUALIZADO <--- \\
        return new Evento(idEvento, categoriaNueva, nombreNuevo, ubicacionNueva, fechaNueva, duracionNueva, organizador);
    }


    // ---> LOGIN DEL USUARIO/ORGANIZADOR <--- \\
    public Object login(){
        // ---> MENSAJE DE BIENVENIDA <--- \\
        System.out.println("\n===== INICIAR SESIÓN =====");


        // ---> SOLICITAR CREDENCIALES <--- \\
        System.out.println("\nIntroduce tu nombre o correo electrónico:");
        String identificador = scanner.nextLine().trim();

        System.out.println("Introduce tu contraseña:");
        String contraseña = scanner.nextLine();


        // ---> VERIFICAR EN LISTA DE USUARIOS <--- \\
        List<Usuario> usuarios = getAllUsuarios();
        for (Usuario usuario : usuarios) {
            if ((usuario.getNombre().equalsIgnoreCase(identificador) ||
                    usuario.getCorreo().equalsIgnoreCase(identificador)) &&
                    usuario.getContraseña().equals(contraseña)) {
                System.out.println("\n¡BIENVENIDO/A " + usuario.getNombre().toUpperCase() + "!");
                return usuario;
            }
        }

        // ---> VERIFICAR EN LISTA DE ORGANIZADORES <--- \\
        List<Organizador> organizadores = getAllOrganizadores();
        for (Organizador organizador : organizadores) {
            if ((organizador.getNombre().equalsIgnoreCase(identificador) ||
                    organizador.getCorreo().equalsIgnoreCase(identificador)) &&
                    organizador.getContraseña().equals(contraseña)) {
                System.out.println("\n¡BIENVENIDO/A " + organizador.getNombre().toUpperCase() + "!");
                return organizador;
            }
        }

        // ---> CREDENCIALES INCORRECTAS <--- \\
        System.out.println("NOMBRE/CORREO O CONTRASEÑA INCORRECTOS");

        // ---> OFRECER REGISTRO SI EL USUARIO NO EXISTE <--- \\
        System.out.println("\n¿Deseas registrarte? (S/n)");
        String respuesta = scanner.nextLine();
        if (!respuesta.equalsIgnoreCase("n")) {
            return register();
        }
        return null;
    }

    public List<Evento> getMyEvents(Object object) {
        if (object instanceof Usuario) {
            List<Evento> eventos = ((Usuario) object).getEventosPresentes();
            eventos.sort(Comparator.comparing(Evento::getIdEvento));
            return eventos;
        } else if (object instanceof Organizador) {
            List<Evento> eventos = ((Organizador) object).getEventosCreados();
            eventos.sort(Comparator.comparing(Evento::getIdEvento));
            return eventos;
        }
        return List.of();
    }
}