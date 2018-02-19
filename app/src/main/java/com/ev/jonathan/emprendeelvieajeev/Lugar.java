package com.ev.jonathan.emprendeelvieajeev;

public class Lugar {

    private String nombre_lugar;
    private String informacion;
    private String tipo;

    public Lugar() {

    }

    public Lugar(String nombre_lugar, String tipo, String informacion) {
        this.nombre_lugar = nombre_lugar;
        this.informacion = informacion;
        this.tipo = tipo;
    }

    public String getNombre_lugar() {
        return nombre_lugar;
    }

    public void setNombre_lugar(String nombre_lugar) {
        this.nombre_lugar = nombre_lugar;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return  "<p align=\"justify\"><br><b>&nbsp;Nombre:</b> " + nombre_lugar +
                "<br><b>&nbsp;Tipo de Lugar:</b> " + tipo +
                "<br><b>&nbsp;Informacion:</b> <br>&nbsp;" + informacion +"<br></p>";
    }
}
