/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel Ramírez Torres
 */
public class CgutResponsablesForm extends org.apache.struts.action.ActionForm {

    private int cboUni = -1;
    private int cboResponsables = -1;
    private int cboResponsablesForm = -1;
    private String nombreResponsable = "";
    private String nombreAPResponsable = "";
    private String nombreAMResponsable = "";
    private String nombreCargo = "";
    private String nombreLada = "";
    private String nombreTelefono = "";
    private String nombreFechaI = "";
    private String nombreMail = "";
    private String nombreFechaR = "";
    private String responsablesActivo = "";
    private ArrayList uni = new ArrayList();
    private ArrayList responsables = new ArrayList();
    private ArrayList uni2 = new ArrayList();
    private ArrayList uniUsuarios = new ArrayList();
    private String ask = "login";

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public int getCboUni() {
        return cboUni;
    }

    public void setCboUni(int cboUni) {
        this.cboUni = cboUni;
    }

    public int getCboResponsables() {
        return cboResponsables;
    }

    public void setCboResponsables(int cboResponsables) {
        this.cboResponsables = cboResponsables;
    }

    public ArrayList getResponsables() {
        return responsables;
    }

    public void setResponsables(ArrayList responsables) {
        this.responsables = responsables;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getNombreAPResponsable() {
        return nombreAPResponsable;
    }

    public void setNombreAPResponsable(String nombreAPResponsable) {
        this.nombreAPResponsable = nombreAPResponsable;
    }

    public ArrayList getUni() {
        return uni;
    }

    public void setUni() {
        ArrayList uni = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet tabla = conexion.Consultar("Select * from universidades where id_universidad != -1 AND activo=1");
            while (tabla.next()) {
                uni.add(new Coleccion(tabla.getInt(1), tabla.getString(3)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        this.uni = uni;
        this.uni2 = uni;
        this.uniUsuarios = uni;
    }

    public ArrayList getUni2() {
        return uni2;
    }

    public void setUni2(ArrayList uni2) {
        this.uni2 = uni2;
    }

    public ArrayList getUniUsuarios() {
        return uniUsuarios;
    }

    public void setUniUsuarios(ArrayList uniUsuarios) {
        this.uniUsuarios = uniUsuarios;
    }

    public String getNombreAMResponsable() {
        return nombreAMResponsable;
    }

    public void setNombreAMResponsable(String nombreAMResponsable) {
        this.nombreAMResponsable = nombreAMResponsable;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getNombreTelefono() {
        return nombreTelefono;
    }

    public void setNombreTelefono(String nombreTelefono) {
        this.nombreTelefono = nombreTelefono;
    }
    public String getNombreLada() {
        return nombreLada;
    }

    public void setNombreLada(String nombreLada) {
        this.nombreLada = nombreLada;
    }

    public String getNombreMail() {
        return nombreMail;
    }

    public void setNombreMail(String nombreMail) {
        this.nombreMail = nombreMail;
    }

    public String getResponsablesActivo() {
        return responsablesActivo;
    }

    public void setResponsablesActivo(String responsablesActivo) {
        this.responsablesActivo = responsablesActivo;
    }

    public void setCboResponsablesForm(int cboResponsablesForm) {
        this.cboResponsablesForm = cboResponsablesForm;
    }

    public int getCboResponsablesForm() {
        return cboResponsablesForm;
    }

    public String getNombreFechaI() {
        return nombreFechaI;
    }

    public void setNombreFechaI(String nombreFechaI) {
        this.nombreFechaI = nombreFechaI;
    }

    public String getNombreFechaR() {
        return nombreFechaR;
    }

    public void setNombreFechaR(String nombreFechaR) {
        this.nombreFechaR = nombreFechaR;
    }

    public CgutResponsablesForm() {
        super();
        setUni();
        // TODO Auto-generated constructor stub
    }
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
}