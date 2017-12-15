package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Daniel Ramírez Torres
 */
public class CgutMantenimientoMecasutForm extends org.apache.struts.action.ActionForm {

    private int cboPeriodos = -1;
    private ArrayList periodos = new ArrayList();
    private String ask = "login";
    private int perActual;
    private int estadoActual;

    public int getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(int estadoActual) {
        this.estadoActual = estadoActual;
    }

    public int getPerActual() {
        return perActual;
    }

    public void setPerActual(int perActual) {
        this.perActual = perActual;
    }
//    

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public int getCboPeriodos() {
        return cboPeriodos;
    }

    public void setCboPeriodos(int cboPeriodos) {
        this.cboPeriodos = cboPeriodos;
    }

    public final void setPeriodos() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from periodos");
            while (tabla.next()) {
                campos.add(new Coleccion(tabla.getInt("id_periodo"), tabla.getString("anio")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutMantenimientoMecasutForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        periodos = campos;
    }

    public ArrayList getPeriodos() {
        return periodos;
    }

    public void setPeriodoActual() {
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet rs = conexion.Consultar("Select id_periodo from periodos where activo = 1");
            if (rs.next()) {
                setCboPeriodos(rs.getInt("id_periodo"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }

    }

    public CgutMantenimientoMecasutForm() {
        super();
        setPeriodos();
        setPeriodoActual();



    }
}
