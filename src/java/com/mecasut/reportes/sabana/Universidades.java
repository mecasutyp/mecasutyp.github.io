/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;


/**
 *
 * @author Cuauh
 */
public class Universidades {

    int id;
    String nombre;
    int nuevoIngreso;
    int matriculaTotal;
    int profTiCom; //Profesores de tiempo completo
    int profAsig; //Profesores de Asignatura

    public Universidades() {
    }

    public Universidades(int id, String nombre, int nuevoIngreso, int matriculaTotal, int profTiCom, int profAsig) {
        this.id = id;
        this.nombre = nombre;
        this.nuevoIngreso = nuevoIngreso;
        this.matriculaTotal = matriculaTotal;
        this.profTiCom = profTiCom;
        this.profAsig = profAsig;
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

    public int getNuevoIngreso() {
        return nuevoIngreso;
    }

    public void setNuevoIngreso(int nuevoIngreso) {
        this.nuevoIngreso = nuevoIngreso;
    }

    public int getMatriculaTotal() {
        return matriculaTotal;
    }

    public void setMatriculaTotal(int matriculaTotal) {
        this.matriculaTotal = matriculaTotal;
    }

    public int getProfTiCom() {
        return profTiCom;
    }

    public void setProfTiCom(int profTiCom) {
        this.profTiCom = profTiCom;
    }

    public int getProfAsig() {
        return profAsig;
    }

    public void setProfAsig(int profAsig) {
        this.profAsig = profAsig;
    }
}
