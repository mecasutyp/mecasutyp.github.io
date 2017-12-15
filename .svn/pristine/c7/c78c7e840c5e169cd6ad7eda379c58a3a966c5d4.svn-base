/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Danny
 */
public class MensajesAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

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
        MensajesForm frm = (MensajesForm) form;
        HttpSession sesion = request.getSession(false);


        //CARGAR MENSAJE
        if (frm.getAsk().equals("leerMen")) {
            String id_mensaje = request.getParameter("id_mensaje").toString();
            ConexionMySQL conexion = new ConexionMySQL();
            try {
                String consulta = "select * from mensajeria where id_mensaje=".concat(id_mensaje)+" and estatus!='Eliminado'";
                ResultSet rs = conexion.Consultar(consulta);
                if (rs.next()) {
                }
                System.out.println("consulta de comen =" + frm.getMensaje());
            } catch (SQLException ex1) {
                Logger.getLogger(MensajesAction.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                conexion.Desconectar();
            }
                System.out.println("consulta de comen2 =" + frm.getMensaje());
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
}
