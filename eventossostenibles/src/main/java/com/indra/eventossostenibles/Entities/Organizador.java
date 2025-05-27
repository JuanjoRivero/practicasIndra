package com.indra.eventossostenibles.Entities;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Organizador {
    // ---> ATRIBUTOS DEL ORGANIZADOR <--- \\
    @Expose
    private Long idOrganizador;
    @Expose
    private String nombre;
    @Expose
    private String correo;
    @Expose
    private Integer telefono;
    @Expose
    private String contraseña;
    @Expose(serialize = false)
    private List<Evento> eventosCreados = new ArrayList<>();


    // ---> CONSTRUCTOR <--- \\
    public Organizador(Long id, String nombre, String correo, Integer telefono, String contraseña) {
        this.idOrganizador = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
    }


    // ---> METODOS DE idOrganizador <--- \\
    public Long getIdOrganizador() {return idOrganizador;}


    // ---> METODOS DE telefono <--- \\
    public Integer getTelefono() {return telefono;}


    // ---> METODOS DE nombre <--- \\
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}


    // ---> METODOS DE correo <--- \\
    public String getCorreo() {return correo;}


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


    // ---> METODOS DE eventosCreados <--- \\
    public List<Evento> getEventosCreados() {return eventosCreados;}
    public void setEventosCreados(List<Evento> eventosCreados) {this.eventosCreados = eventosCreados;}
    public void agregarEvento(Evento eventosPresente) {
        if (this.eventosCreados == null) {
            this.eventosCreados = new ArrayList<>();
        }
        
        if (eventosPresente != null && !this.eventosCreados.contains(eventosPresente)) {
            this.eventosCreados.add(eventosPresente);
        }
    }


    // ---> METODO toString() DEL ORGANIZADOR <--- \\
    @Override
    public String toString() {
        return "Organizador {" +
                "\n\t- ID: " + idOrganizador +
                "\n\t- Nombre: " + nombre +
                "\n\t- Email: " + correo +
                "\n\t- Teléfono: " + telefono +
                "\n\t- Contraseña: " + mostrarParcialmenteContraseña() +
                "\n\t- Eventos creados: " + enumerarEventos() +
                "\n}";
    }


    // ---> METODO AUXILIAR PARA toString() DEL ORGANIZADOR <--- \\
    private String enumerarEventos() {
        if (eventosCreados.isEmpty()) {return "Ninguno";}

        StringBuffer sb = new StringBuffer();
        eventosCreados.forEach(evento -> sb.append(evento.getNombre() + ", "));
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }
}