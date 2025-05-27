package com.indra.eventossostenibles.Entities;

import com.google.gson.annotations.Expose;

public class Ubicacion {
    // ---> ATRIBUTOS DE LA UBICACIÓN <--- \\
    @Expose
    private TipoUbicacion tipoUbicacion;
    @Expose
    private String ubicacion;

    public Ubicacion(String ubicacion) {
        this.tipoUbicacion = TipoUbicacion.PRESENCIAL;
        this.ubicacion = ubicacion;
    }


    // ---> CONSTRUCTOR <--- \\
    public Ubicacion() {
        this.tipoUbicacion = TipoUbicacion.ONLINE;
        this.ubicacion = null;
    }


    // ---> MÉTODO toString() DE LA UBICACIÓN <--- \\
    @Override
    public String toString() {
        if (tipoUbicacion.equals(TipoUbicacion.ONLINE)){
            return "El evento es ONLINE, no tiene ubicación";
        } else {
            return "La dirección del evento es en " + ubicacion;
        }
    }
}
