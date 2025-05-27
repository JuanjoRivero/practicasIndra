package com.indra.eventossostenibles.Entities;

import com.google.gson.annotations.Expose;

import java.util.*;

public class Usuario {
    // ---> ATRIBUTOS DEL USUARIO <--- \\
    @Expose
    private Long idUsuario;
    @Expose
    private String nombre;
    @Expose
    private String correo;
    @Expose
    private Integer telefono;
    @Expose
    private String contraseña;
    @Expose(serialize = false)
    private List<Evento> eventosPresentes = new ArrayList<>();


    // ---> CONSTRUCTOR <--- \\
    public Usuario(Long id, String nombre, String correo, Integer telefono, String contraseña) {
        this.idUsuario = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
    }


    // ---> METODOS DE idUsuario <--- \\
    public Long getIdUsuario() {return idUsuario;}


    // ---> METODOS DE nombre <--- \\
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}


    // ---> METODOS DE correo <--- \\
    public String getCorreo() {return correo;}


    // ---> METODOS DE telefono <--- \\
    public Integer getTelefono() {return telefono;}


    // ---> METODOS DE contraseña <--- \\
    public String getContraseña() {return contraseña;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    private String mostrarParcialmenteContraseña() {
        StringBuffer sb = new StringBuffer();
        sb.append(contraseña.charAt(0));
        for (int i = 0; i < (contraseña.length() - 2); i++) {
            sb.append("*");
        }
        sb.append(contraseña.charAt(contraseña.length() - 1));
        return sb.toString();
    }


    // ---> METODOS DE eventosPresentes <--- \\
    public List<Evento> getEventosPresentes() {
        if (eventosPresentes == null) {
            eventosPresentes = new ArrayList<>();
        }
        return eventosPresentes;
    }
    public void setEventosPresentes(List<Evento> eventosPresentes) {this.eventosPresentes = eventosPresentes;}
    public void agregarEvento(Evento eventoPresente) {this.eventosPresentes.add(eventoPresente);}


    // ---> METODO toString() DEL USUARIO <--- \\
    @Override
    public String toString() {


        return "Usuario {" +
                "\n\t- ID: " + idUsuario +
                "\n\t- Nombre: " + nombre +
                "\n\t- Email: " + correo +
                "\n\t- Teléfono: " + telefono +
                "\n\t- Contraseña: " + mostrarParcialmenteContraseña() +
                "\n\t- Eventos en los que participa: " + enumerarEventos() +
                "\n}";
    }


    // ---> METODO AUXILIAR PARA toString() DEL USUARIO <--- \\
    private String enumerarEventos() {
        if (eventosPresentes == null || eventosPresentes.isEmpty()) {
            return "Ninguno";
        }

        StringBuffer sb = new StringBuffer();
        eventosPresentes.forEach(evento -> sb.append(evento.getNombre() + ", "));
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }
}