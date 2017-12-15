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
 * @author DELL
 */
public class CgutModelosForm extends org.apache.struts.action.ActionForm {

    private String ask = "";
    private int cboModelo = -1;
    private ArrayList modelos = new ArrayList();
    private String nombreModelo = "";
    private String modeloActivo = "";

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public int getCboModelo() {
        return cboModelo;
    }

    public void setCboModelo(int cboModelo) {
        this.cboModelo = cboModelo;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getModeloActivo() {
        return modeloActivo;
    }

    public void setModeloActivo(String modeloActivo) {
        this.modeloActivo = modeloActivo;
    }

    public ArrayList getModelos() {
        return modelos;
    }

    public void setModelos() {
        ArrayList modelos = new ArrayList();
        ConexionMySQL con = new ConexionMySQL();

        try {
            ResultSet tabla = con.Consultar("select * from modelos");
            while (tabla.next()) {
                modelos.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
                System.out.println("modelos CGutModelosForm "+ tabla.getInt(1)+" "+ tabla.getString(2));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            con.Desconectar();
        }
        this.modelos = modelos;
    }

    public CgutModelosForm() {
        super();
        setModelos();
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
