/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import utilerias.Encriptacion;

/**
 *
 * @author josh
 */
public class PerfilAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String LOGOUT = "logout";

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
        PerfilForm frm = (PerfilForm) form;
        ConexionMySQL conexion = new ConexionMySQL();
        String ask = request.getParameter("ask");
        if(ask == null){
            ask = request.getAttribute("ask").toString();
        }
        if (ask.equals("guardarPerfil")) {
            String consulta = "Update responsables set cargo = '".concat(frm.getCargo()).concat("', telefono = '").concat(frm.getTelefono()).concat("', mail = '").concat(frm.getMail()).concat("'").concat("where id_responsable = ").concat(sesion.getAttribute("idResponsable").toString());
            conexion.Modificar(consulta);
            return mapping.findForward(SUCCESS);
        }
        if (ask.equals("guardarUsuario")) {
            String idUser = sesion.getAttribute("idUsuario").toString();
            String consulta = "Select password from usuarios where id_usuario = ".concat(idUser);
            ResultSet rs = conexion.Consultar(consulta);
            if (rs.next()) {
                if (rs.getString(1).equals(frm.getPassword())) {
                    consulta = "UPDATE usuarios SET password = '"
                    .concat(Encriptacion.Encriptar(frm.getNuevoPassword(), "SHA-1"))
                    .concat("' WHERE id_usuario = ").concat(idUser);
                    conexion.Modificar(consulta);
                    frm.setTipo("ok");
                    frm.setPassword("");
                    frm.setNuevoPassword("");
                    frm.setUser("");
                    return mapping.findForward(SUCCESS);
                } else {
                    frm.setPassword("");
                    frm.setNuevoPassword("");
                    frm.setUser("");
                    request.setAttribute("errorPassword", "true");
                }
            }
        }
        return mapping.findForward(SUCCESS);
    }
}