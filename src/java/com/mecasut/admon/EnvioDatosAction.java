/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class EnvioDatosAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String LOGOUT = "logout";
    private String id_responsable = "";

    /**
     * This is the action called from the Struts framework.
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

        EnvioDatosForm formbean = (EnvioDatosForm) form;
        HttpSession sesion = request.getSession(false);

        if (sesion.getAttribute("usuario") == null) {
            request.setAttribute("mensaje", "Sesi&oacute;n inv&aacute;lida.");
            return mapping.findForward(FAILURE);
        }

        String user = formbean.getName();
        String password = formbean.getPassword();
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();
        String tipo = ValidarUsuario(user, password, idUniversidad);

        if (tipo.equals("bderror")) {
            request.setAttribute("mensaje", "Error al conectar con la base de datos.");
            return mapping.findForward(FAILURE);
        } else if (tipo.equals("desactivado")) {
            request.setAttribute("mensaje", "El usuario con el que intenta enviar los datos est&aacute; desactivado.");
            return mapping.findForward(FAILURE);
        } else if (tipo.equals("usuario")) {
            request.setAttribute("mensaje", "El usuario o contraseña son inválidos.");
            return mapping.findForward(FAILURE);
        } else if (tipo.equals("noexiste")) {
            request.setAttribute("mensaje", "El usuario no existe en la base de datos o no est&aacute; asociado con esta universidad.");
            return mapping.findForward(FAILURE);
        } else {
            ConexionMySQL conexion = new ConexionMySQL();
            
            ResultSet rs = conexion.Consultar("Select id_universidad from control_universidad where id_periodo = ".concat(idPeriodo).concat(" and id_universidad = ").concat(idUniversidad));
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy'-'MM'-'dd");
            Date fecha = new Date();
            String sql = "";
            if (rs.next()) {
                sql = "Update control_universidad set mecasut_terminado = 1, id_responsable_termino = ".concat(id_responsable).concat(", fecha_termino = '")
                        .concat(formateador.format(fecha)).concat("' where id_periodo = ").concat(idPeriodo).concat(" and id_universidad = ").concat(idUniversidad);
                conexion.Modificar(sql);
            } else {
                request.setAttribute("mensaje", "Ocurri&oacute; un error al tratar de recuperar la informaci&oacute;n de inicio de la universidad.");
                return mapping.findForward(FAILURE);
            }
            return mapping.findForward(SUCCESS);
        }
    }

    public String ValidarUsuario(String user, String pass, String universidad) throws SQLException {
        ConexionMySQL conexion = new ConexionMySQL();
        if (!conexion.Conectar()) {
            return "bderror";
        }
        String consulta = "Select user, password, tipo, id_universidad, usuarios.activo, usuarios.id_responsable from usuarios INNER JOIN responsables on usuarios.id_responsable = responsables.id_responsable where id_universidad = "
                .concat(universidad).concat(" and user='").concat(user).concat("'");
        ResultSet rs = conexion.Consultar(consulta);
        if (rs.next()) {
            String usuario = rs.getString("user");
            String contrasena = rs.getString("password");
            if (user.equals(usuario) && pass.equals(contrasena)) {
                if (rs.getBoolean("activo")) {
                    id_responsable = rs.getString("id_responsable");
                    return rs.getString("tipo");
                } else {
                    System.out.println("SENDINFO: Intento de conexion con usuario desactivado Usuario: ".concat(user).concat(" Universidad: ".concat(universidad)));
                    return "desactivado";
                }
            } else {
                System.out.println("SENDERROR: Error de compatibilidad de usuario y contraseña o universidad: Usuario: ".concat(user).concat(" Universidad: ").concat(universidad));
                return "usuario";
            }
        } else {
            System.out.println("SENDERROR: No existe el Usuario: ".concat(user).concat(" en la Universidad: ".concat(String.valueOf(universidad))));
            return "noexiste";
        }
    }
}