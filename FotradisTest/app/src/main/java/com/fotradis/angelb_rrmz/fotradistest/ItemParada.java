package com.fotradis.angelb_rrmz.fotradistest;

/**
 * Created by ANGELB_RRMZ on 26/04/2018.
 */

public class ItemParada {
    String nombre;
    String latitud;
    String longitud;
    String referencias;

    public ItemParada() {}

    public ItemParada(String nombre, String latitud, String longitud, String referencias) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.referencias = referencias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() { return latitud; }

    public void setLatitud(String latitud) { this.latitud = latitud; }

    public String getLongitud() { return longitud; }

    public void setLongitud(String longitud) { this.longitud = longitud; }

    public void setReferencias(String referencias) { this.referencias = referencias; }

    public String getReferencias() { return referencias; }
}
