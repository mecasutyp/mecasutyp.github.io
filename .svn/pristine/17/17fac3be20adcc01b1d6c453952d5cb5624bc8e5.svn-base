/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import utilerias.Encriptacion;

/**
 *
 * @author Joshua
 */
public class LoginForm extends org.apache.struts.action.ActionForm {

    private String ask = "login";
    private String user = "";
    private String password = "";
    private String error = "false";
    private String errorMessage = "";
    private int universidad = -1;
    private String captcha = "";
    private ArrayList universidades = new ArrayList();

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ArrayList getUniversidades() {
        return universidades;
    }

    public int getUniversidad() {
        return universidad;
    }

    public void setUniversidad(int universidad) {
        this.universidad = universidad;
    }

    public final void setUniversidades() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        if (!conexion.Conectar()) {
            return;
        }
        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from universidades where activo = 1 order by nombre_universidad");
            while (tabla.next()) {
                i++;
            }
            tabla.beforeFirst();
            for (int j = 0; j < i; j++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_universidad"), tabla.getString("nombre_universidad")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        universidades = campos;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPassword() {
        return Encriptacion.Encriptar(password, "SHA-1");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     * @param string
     */
    public void setUser(String string) {
        user = string;
    }

    /**
     *
     */
    public LoginForm() {
        super();
        setUniversidades();
    }

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
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

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (getUser() == null || getUser().length() < 1) {
            errors.add("name", new ActionMessage("error.name.required"));
        }
        return errors;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    
}
