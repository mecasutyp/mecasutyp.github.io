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
 * @author Cuauhtemoc Medina Muñoz
 */
public class CgutServiciosForm extends org.apache.struts.action.ActionForm {
    
    private String ask = "";
    private ArrayList servicios = new ArrayList();
    private String nombre = "";
    private int cboServicios = -1;
    private String descripcion = "";

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public ArrayList getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList Servicios) {
        this.servicios = Servicios;
    }

    public int getCboServicios() {
        return cboServicios;
    }

    public void setCboServicios(int cboServicios) {
        this.cboServicios = cboServicios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setServicios() {
        ArrayList x = new ArrayList();
        ConexionMySQL con = new ConexionMySQL();
        
        try {
            ResultSet tabla = con.Consultar("SELECT id_servicio, nombre FROM encuesta_servicios order by nombre");
            while (tabla.next()) {
                x.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando servicios en CgutServiciosForm.java "+ex.getMessage());
        } finally {
            con.Desconectar();
        }
        servicios = x;
    }
    
    public CgutServiciosForm() {
        super();
        setServicios();
        // TODO Auto-generated constructor stub
    }

}
