package com.indra.eventossostenibles.Entities;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.util.*;

public class Evento {
    // ---> ATRIBUTOS DEL EVENTO <--- \\
    @Expose
    private Long idEvento;
    @Expose
    private Estado estado;
    @Expose
    private Categoria categoria;
    @Expose
    private String nombre;
    @Expose
    private LocalDate fecha;
    @Expose
    private Ubicacion ubicacion;
    @Expose
    private Double duracion;
    @Expose(serialize = false)
    private List<Usuario> usuarios;
    @Expose
    private Organizador organizador;



    // ---> CONSTRUCTOR <--- \\
    public Evento(Long id, Categoria categoria, String nombre, Ubicacion ubicacion, LocalDate fecha, Double duracion, Organizador organizador) {
        this.idEvento = id;
        if (fecha.isAfter(LocalDate.now())) {
            this.estado = Estado.ACTIVO;
        } else {
            this.estado = Estado.FINALIZADO;
        }
        this.categoria = categoria;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.duracion = duracion;
        this.usuarios = new ArrayList<>();
        this.organizador = organizador;
    }

    // ---> METODO DE estado <--- \\
    public Estado getEstado() {return estado;}


    // ---> METODO DE categoria <--- \\
    public Categoria getCategoria() {return categoria;}


    // ---> METODOS DE organizador <--- \\
    public Organizador getOrganizador() {return organizador;}
    public void setOrganizador(Organizador organizador) {this.organizador = organizador;}


    // ---> METODO DE idEvento <--- \\
    public Long getIdEvento() {return idEvento;}


    // ---> METODOS DE nombre <--- \\
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}


    // ---> METODO DE ubicacion <--- \\
    public Ubicacion getUbicacion() {return ubicacion;}


    // ---> METODO DE fecha <--- \\
    public LocalDate getFecha() {return fecha;}


    // ---> METODO DE duracion <--- \\
    public Double getDuracion() {return duracion;}


    // ---> METODO DE estado <--- \\
    public void actualizarEstado() {
        if (fecha.isAfter(LocalDate.now())) {
            this.estado = Estado.ACTIVO;
        } else {
            this.estado = Estado.FINALIZADO;
        }
    }

    public void cancelarEvento(){
        this.estado = Estado.CANCELADO;
    }


    // ---> METODOS DE usuarios <--- \\
    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }
        return usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) {this.usuarios = usuarios;}


    // ---> METODO toString() DEL EVENTO <--- \\
    @Override
    public String toString() {


        return "Evento {" +
                "\n\t- ID de evento: " + idEvento +
                "\n\t- Estado del evento: " + getEstado().toString() +
                "\n\t- Tipo de evento: " + categoria +
                "\n\t- Nombre del evento: " + nombre +
                "\n\t- " + ubicacion +
                "\n\t- Fecha del evento: " + fecha +
                "\n\t- Duración: " + duracion +
                "\n\t- Usuarios: " + enumerarUsuarios() +
                "\n\t- Organizador: {" +
                "\n\t\t- Nombre del organizador: " + organizador.getNombre() +
                "\n\t\t- Email del organizador: " + organizador.getCorreo() +
                "\n\t  }" +
                "\n}";
    }


    // ---> METODO AUXILIAR PARA toString() DEL EVENTO <--- \\
    private String enumerarUsuarios() {
        if (getUsuarios().isEmpty()) {return "Ninguno";}

        StringBuffer sb = new StringBuffer();
        getUsuarios().forEach(usuario -> sb.append(usuario.getNombre() + ", "));
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }
}
