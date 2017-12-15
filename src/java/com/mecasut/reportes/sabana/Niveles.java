/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

/**
 *
 * @author Cuauh
 */
public class Niveles {

    int id;
    String nombre;
    String descripcion;
    String abreviatura;

    public Niveles(int id, String nombre, String descripcion, String abreviatura) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.abreviatura = abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
      public String getAbreviatura() {
        return descripcion;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
