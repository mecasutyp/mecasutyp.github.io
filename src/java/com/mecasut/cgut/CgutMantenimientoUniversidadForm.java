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
 * @author EQ-30
 */
public class CgutMantenimientoUniversidadForm extends org.apache.struts.action.ActionForm {

    private String ask = "login";
    private ArrayList uniMan = new ArrayList();
    private String estadoUni = "";
    private int uniM = -1;
    private int cboReiniciar = -1;
    private String tablaEstatus = "";
    private boolean terminado = false;

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }
    
    public String getTablaEstatus() {
        return tablaEstatus;
    }

    public void setTablaEstatus(String tablaEstatus) {
        this.tablaEstatus = tablaEstatus;
    }
    
    public int getCboReiniciar() {
        return cboReiniciar;
    }

    public void setCboReiniciar(int cboReiniciar) {
        this.cboReiniciar = cboReiniciar;
    }

    public int getUniM() {
        return uniM;
    }

    public void setUniM(int uniM) {
        this.uniM = uniM;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public ArrayList getUniMan() {
        return uniMan;
    }

    public String getEstadoUni() {
        return estadoUni;
    }

    public void setEstadoUni(String estadoUni) {
        this.estadoUni = estadoUni;
    }

    public void setUniMan() {
        ArrayList uni = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet tabla = conexion.Consultar("Select * from universidades where id_universidad > 0  AND activo=1 order by nombre_universidad");
            while (tabla.next()) {
                uni.add(new Coleccion(tabla.getInt(1), tabla.getString(3)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        this.uniMan = uni;
    }

    public int getPeriodoActual() {
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet rs = conexion.Consultar("Select id_periodo from periodos where activo = 1");
            if (rs.next()) {
                return rs.getInt("id_periodo");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        return -1;
    }

    public CgutMantenimientoUniversidadForm() {
        super();
        setUniMan();
    }
}
