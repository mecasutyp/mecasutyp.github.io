/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public class Certificaciones {

    int id;
    String nombre;

    public Certificaciones(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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