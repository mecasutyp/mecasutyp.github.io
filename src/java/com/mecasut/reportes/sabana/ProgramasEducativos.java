/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

/**
 *
 * @author DIN-A-C14-02
 */
public class ProgramasEducativos {
    
    private int id_pe;
    private int id_nivel;
    private String nombre_programa;
    private String nomenclatura;
    private String version;
    private boolean activo;

    public ProgramasEducativos() {
    }

    public ProgramasEducativos(int id_pe, int id_nivel, String nombre_programa, String nomenclatura, String version, boolean activo) {
        this.id_pe = id_pe;
        this.id_nivel = id_nivel;
        this.nombre_programa = nombre_programa;
        this.nomenclatura = nomenclatura;
        this.version = version;
        this.activo = activo;
    }

    public int getId_pe() {
        return id_pe;
    }

    public void setId_pe(int id_pe) {
        this.id_pe = id_pe;
    }

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    public String getNombre_programa() {
        return nombre_programa;
    }

    public void setNombre_programa(String nombre_programa) {
        this.nombre_programa = nombre_programa;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
