/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Joshua
 */
public class GlobalAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String EFICACIA = "eficacia";
    private static final String EFICIENCIA = "eficiencia";
    private static final String PERTINENCIA = "pertinencia";
    private static final String VINCULACION = "vinculacion";
    private static final String EQUIDAD = "equidad";
    private static final String LOGOUT = "logout";
    private static final String UNIVERSIDAD = "universidad";
    private static final String PERFIL = "perfil";
    private static final String MENSAJES = "mensajes";
    private static final String INICIAR = "iniciar";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }
        String ask = request.getParameter("ask");

        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();

        ConexionMySQL conexion = new ConexionMySQL();

        
        String query = "Select mecasut_iniciado from control_universidad where id_periodo = "
                .concat(idPeriodo).concat(" and id_universidad = ").concat(idUniversidad);
        ResultSet rs = conexion.Consultar(query);
        if (ask.equals("validar")) {
            if (!rs.next()) {//si no hay registro
                if (ValidarInicio(Integer.parseInt(idUniversidad), Integer.parseInt(idPeriodo))) {
                    return mapping.findForward(INICIAR);
                } else {
                    request.setAttribute("nocompletos", "true");
                    return mapping.findForward(UNIVERSIDAD);
                }
            } else {//si hay registro
                return mapping.findForward(UNIVERSIDAD);
            }
        }
        if (ask.equals("mensajes")) {
            return mapping.findForward(MENSAJES);
        }
        if (ask.equals("perfil")) {
            return mapping.findForward(PERFIL);
        }
        if (ask.equals("validarInicio")) {
            if (!rs.next()) {
                if (ValidarInicio(Integer.parseInt(idUniversidad), Integer.parseInt(idPeriodo))) {
                    SimpleDateFormat formateador = new SimpleDateFormat("yyyy'-'MM'-'dd");
                    Date fecha = new Date();
                    String sql = "Insert control_universidad VALUES(".concat(sesion.getAttribute("idUniversidad").toString()).concat(", ")
                            .concat(sesion.getAttribute("idPeriodo").toString()).concat(", 1, ").concat(sesion.getAttribute("idResponsable").toString())
                            .concat(", '").concat(formateador.format(fecha)).concat("', 0, 0, null)");
                    conexion.Insertar(sql);
                    return mapping.findForward(UNIVERSIDAD);
                } else {
                    request.setAttribute("nocompletos", "true");
                    return mapping.findForward(UNIVERSIDAD);
                }
            } else {
                return mapping.findForward(UNIVERSIDAD);
            }
        }
        if (!rs.next()) {
            return mapping.findForward(LOGOUT);
        }
        if (ask.equals("eficacia")) {
            return mapping.findForward(EFICACIA);
        }
        if (ask.equals("eficiencia")) {
            return mapping.findForward(EFICIENCIA);
        }
        if (ask.equals("pertinencia")) {
            return mapping.findForward(PERTINENCIA);
        }
        if (ask.equals("vinculacion")) {
            return mapping.findForward(VINCULACION);
        }
        if (ask.equals("equidad")) {
            return mapping.findForward(EQUIDAD);
        }
        return mapping.findForward(LOGOUT);
    }

    private boolean ValidarInicio(int idUniversidad, int idPeriodo) {
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            CallableStatement sp = conexion.PrepararSP("sp_ValidarInicio(?, ?, ?, ?, ?, ?, ?,?)");
            sp.setInt(1, idUniversidad);
            sp.setInt(2, idPeriodo);
            sp.registerOutParameter(3, java.sql.Types.INTEGER);
            sp.registerOutParameter(4, java.sql.Types.INTEGER);
            sp.registerOutParameter(5, java.sql.Types.INTEGER);
            sp.registerOutParameter(6, java.sql.Types.INTEGER);
            sp.registerOutParameter(7, java.sql.Types.INTEGER);
            sp.registerOutParameter(8, java.sql.Types.INTEGER);
           
              
            sp.execute();
           
                    //.getMaxRows());
            int servicios = sp.getInt(3);
            int edificios = sp.getInt(4);
            int niveles = sp.getInt(5);
            int modelos = sp.getInt(6);
            int domicilio = sp.getInt(7);
            int contacto = sp.getInt(8);
            
            if (servicios > 0
                    && edificios >= 0
                    && niveles > 0
                    && modelos == niveles
                    && domicilio == 1
                    && contacto == 3
                    //&& completas == 2
                    ) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.err.println("Error: Global Action "+ex.getMessage());
            return false;
        } finally {
            conexion.Desconectar();
        }
    }
}