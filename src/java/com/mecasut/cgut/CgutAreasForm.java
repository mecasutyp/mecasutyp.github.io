/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.admon.CgutInterfaceForm;
import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EQ-30
 */
public class CgutAreasForm extends org.apache.struts.action.ActionForm {

    private ArrayList areas = new ArrayList();
    private String nombreArea, areaActivo;
    private int cboArea = -1;
    private String ask = "login";

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public ArrayList getAreas() {
        return areas;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public int getCboArea() {
        return cboArea;
    }

    public void setCboArea(int cboArea) {
        this.cboArea = cboArea;
    }

    public String getAreaActivo() {
        return areaActivo;
    }

    public void setAreaActivo(String areaActivo) {
        this.areaActivo = areaActivo;
    }

    public final void setAreas() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from areas_pe");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("ID_area"), tabla.getString("NOMBRE_AREA")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        areas = campos;
    }

    public CgutAreasForm() {
        super();
        setAreas();
        // TODO Auto-generated constructor stub
    }
}
