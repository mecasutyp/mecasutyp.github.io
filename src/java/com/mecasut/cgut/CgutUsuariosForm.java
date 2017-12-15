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
import utilerias.Encriptacion;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public final class CgutUsuariosForm extends org.apache.struts.action.ActionForm {

    private String ask = "";
    private int cboUniUsuarios=-2;
    private ArrayList uniUsuarios = new ArrayList();
    private String nombreUsu = "";
    private String password = "";
    private String confirmacion = "";
    private boolean modificarPassword = false;
    /*COMOBO DE RESPONSABLES*/
    private int cboResUsu = 0;
    private ArrayList coleccionResponsables = new ArrayList();
    private ArrayList listaUsuarios = new ArrayList();
    private int comboListaUniversidades;
    private boolean visible=false;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getComboListaUniversidades() {
        return comboListaUniversidades;
    }

    public void setComboListaUniversidades(int comboListaUniversidades) {
        this.comboListaUniversidades = comboListaUniversidades;
    }

    public ArrayList getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    public int getCboResUsu() {
        return cboResUsu;
    }

    public void setCboResUsu(int cboResUsu) {
        this.cboResUsu = cboResUsu;
    }

    public ArrayList getColeccionResponsables() {
        return coleccionResponsables;
    }

    public void setColeccionResponsables(ArrayList coleccionResponsables) {
        this.coleccionResponsables = coleccionResponsables;
    }

    public boolean isModificarPassword() {
        return modificarPassword;
    }

    public int getCboUniUsuarios() {
        return cboUniUsuarios;
    }

    public void setCboUniUsuarios(int cboUniUsuarios) {
        this.cboUniUsuarios = cboUniUsuarios;
    }

    public void setModificarPassword(boolean modificarPassword) {
        this.modificarPassword = modificarPassword;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.equals("")) {
            this.password = "";
        } else {
            this.password = Encriptacion.Encriptar(password, "SHA-1");
        }
    }

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public ArrayList getUniUsuarios() {
        return uniUsuarios;
    }

    public void setUniUsuarios(ArrayList uniUsuarios) {
        this.uniUsuarios = uniUsuarios;
    }

    public void setUni() {
        ArrayList uni = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet tabla = conexion.Consultar("Select * from universidades where id_universidad != -1 AND activo=1 order by nombre_universidad");
            while (tabla.next()) {
                uni.add(new Coleccion(tabla.getInt(1), tabla.getString(3)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        this.uniUsuarios = uni;
    }

    public CgutUsuariosForm() {
        super();
        setUni();
    }

}
