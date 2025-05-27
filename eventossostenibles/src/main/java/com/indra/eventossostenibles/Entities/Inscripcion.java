package com.indra.eventossostenibles.Entities;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Inscripcion {
    // ---> ATRIBUTOS DEL EVENTO <--- \\
    @Expose
    private Long idInscripcion;
    @Expose
    private Usuario usuario;
    @Expose
    private Evento evento;
    @Expose
    private Date fechaInscripcion;


    // ---> CONSTRUCTOR <--- \\
    public Inscripcion(Long id, Usuario usuario, Evento evento) {
        this.idInscripcion = id;
        this.usuario = usuario;
        this.evento = evento;
        this.fechaInscripcion = new Date();
    }


    // ---> METODO DE idInscripcion <--- \\
    public Long getIdInscripcion() {return idInscripcion;}


    // ---> METODOS DE evento <--- \\
    public Evento getEvento() {return evento;}
    public void setEvento(Evento evento) {this.evento = evento;}


    // ---> METODOS DE usuario <--- \\
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}


    // ---> METODO toString() DE LA INSCRIPCIÓN <--- \\
    @Override
    public String toString() {
        return "Inscripción {" +
                "\n\t- ID de la inscripción: " + idInscripcion +
                "\n\t- Usuario inscrito: {" +
                "\n\t\t" + "- ID del usuario: " + usuario.getIdUsuario() +
                "\n\t\t" + "- Nombre del usuario: " + usuario.getNombre() +
                "\n\t\t" + "- Email del usuario: " + usuario.getCorreo() +
                "\n\t\t" + "- Teléfono del usuario: " + usuario.getTelefono() +
                "\n\t}" +
                "\n\t- Evento al que se inscribe: {" +
                "\n\t\t" + "- ID del evento: " + evento.getIdEvento() +
                "\n\t\t" + "- Nombre del evento: " + evento.getNombre() +
                "\n\t\t" + "- Tipo de evento: " + evento.getCategoria() +
                "\n\t\t" + "- Fecha del evento: " + evento.getFecha() +
                "\n\t\t" + "- Duración: " + evento.getDuracion() +
                "\n\t\t" + "- Ubicación del evento: " + evento.getUbicacion() +
                "\n\t\t" + "- Organizador del evento: {" +
                "\n\t\t\t" + "- Nombre del organizador: " + evento.getOrganizador().getNombre() +
                "\n\t\t\t" + "- Email del organizador: " + evento.getOrganizador().getCorreo() +
                "\n\t\t}" +
                "\n\t}" +
                "\n}";
    }
}