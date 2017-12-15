/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

/**
 *
 * @author DIN-A-C14-02
 */
public class Preguntas {
    
    private int id_pregunta;
    private int id_encuesta;
    private int id_servicio;
    private String pregunta;
    private boolean activo;
    private String nombre_pregunta;
    private int id_nivel;

    public Preguntas() {
    
    }

    public Preguntas(int id_pregunta, int id_encuesta, int id_servicio, String pregunta, boolean activo, String nombre_pregunta, int id_nivel) {
        this.id_pregunta = id_pregunta;
        this.id_encuesta = id_encuesta;
        this.id_servicio = id_servicio;
        this.pregunta = pregunta;
        this.activo = activo;
        this.nombre_pregunta = nombre_pregunta;
        this.id_nivel = id_nivel;
    }
    
    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public int getId_encuesta() {
        return id_encuesta;
    }

    public void setId_encuesta(int id_encuesta) {
        this.id_encuesta = id_encuesta;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombre_pregunta() {
        return nombre_pregunta;
    }

    public void setNombre_pregunta(String nombre_pregunta) {
        this.nombre_pregunta = nombre_pregunta;
    }

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }
    
}
