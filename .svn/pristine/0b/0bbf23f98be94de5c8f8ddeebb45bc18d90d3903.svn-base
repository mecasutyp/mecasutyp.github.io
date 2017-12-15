/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Actualización 2016: Salvador Zamora Arias
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
 * @author josh
 */
public class EnvioErroresAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
     private static final String SUCCESS = "success";
    private static final String EFICACIA = "eficacia";
    private static final String EFICIENCIA = "eficiencia";
    private static final String PERTINENCIA = "pertinencia";
    private static final String VINCULACION = "vinculacion";
    private static final String EQUIDAD = "equidad";
    private static final String LOGOUT = "logout";
    private static final String UNIVERSIDAD = "universidad";
    private static final String INICIAR = "iniciar";
    private static final String ADMIN = "admin";
    private static final String MENSAJES = "mensajes";

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

        EnvioErroresForm frm = (EnvioErroresForm) form;
        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }

        if (frm.getAsk().equals("respuestas")) {
           // System.out.println("asdfasdfasdfasdfasdfasdfasdfasdf");  
        }

        if (frm.getAsk().equals("comentario")) {
            ConexionMySQL conexion = new ConexionMySQL();
            try {
                conexion.Insertar("Insert mensajeria(id_remitente, id_destinatario, tipo, asunto,mensaje, fecha, estatus) VALUES".concat("(").concat(sesion.getAttribute("idResponsable").toString()).concat(",0, ").concat(String.valueOf(frm.getTipo())).concat(",'").concat(frm.getAsunto()).concat("',   '").concat(frm.getComentario()).concat("',NOW(), 'No Leido')"));
            } catch (SQLException ex1) {
                Logger.getLogger(registroUniversidadAction.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                conexion.Desconectar();
            }
        }

        if (frm.getAsk().equals("respuesta")) {
            String msj = request.getParameter("msj").toString();
            String idDest = request.getParameter("idDest").toString();
            String idRespo = sesion.getAttribute("idResponsable").toString();
            String tipo = request.getParameter("tipo").toString();
            ConexionMySQL conexion = new ConexionMySQL();
            try {
                String consulta = "INSERT INTO reporte_errores (id_responsable, id_destinatario, tipo, comentario, fecha_redactado, leido)"
                        .concat(" VALUES(").concat(idRespo).concat(",").concat(idDest).concat(",").concat(tipo).concat(",'").concat(msj).concat("',NOW(),0").concat(")");
                conexion.Insertar(consulta);
            } catch (SQLException ex1) {
                Logger.getLogger(registroUniversidadAction.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                conexion.Desconectar();
            }
        }

        //delete from reporte_errores where id_reporte=
        //eliminar comentario
        if (frm.getAsk().equals("elm")) {
            String id_mensaje = request.getParameter("im").toString();
            System.out.println("mensaje" + id_mensaje);
            ConexionMySQL conexion = new ConexionMySQL();
            try {
                String consulta = "delete from reporte_errores where id_reporte=".concat(id_mensaje);
                conexion.Insertar(consulta);
            } catch (SQLException ex1) {
                Logger.getLogger(registroUniversidadAction.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                conexion.Desconectar();

            }
        }
        //CARGAR MENSAJE
        if (frm.getAsk().equals("leerMen")) {
            String id_mensaje = request.getParameter("id_mensaje").toString();
            System.out.println("mensaje" + id_mensaje);
            ConexionMySQL conexion = new ConexionMySQL();
            try {
                // String consulta = "delete from reporte_errores where id_reporte=".concat(id_mensaje);
                String consulta = "select * from mensajeria where id_mensaje=".concat(id_mensaje)+" and estatus!='Eliminado'";
                ResultSet rs = conexion.Consultar(consulta);
               
                if (rs.next()) {
                       frm.setComentario(rs.getString("mensaje"));
                }
                                
            } catch (SQLException ex1) {
                Logger.getLogger(registroUniversidadAction.class.getName()).log(Level.SEVERE, null, ex1);
            } finally {
                conexion.Desconectar();

            }
             return mapping.findForward(SUCCESS);
        }


        if (frm.getRe().equals("eficacia")) {
            return mapping.findForward(EFICACIA);
        }
        if (frm.getRe().equals("eficiencia")) {
            return mapping.findForward(EFICIENCIA);
        }
        if (frm.getRe().equals("pertinencia")) {
            return mapping.findForward(PERTINENCIA);
        }
        if (frm.getRe().equals("vinculacion")) {
            return mapping.findForward(VINCULACION);
        }
        if (frm.getRe().equals("equidad")) {
            return mapping.findForward(EQUIDAD);
        }
        if (frm.getRe().equals("universidad")) {
            return mapping.findForward(UNIVERSIDAD);
        }
        if (frm.getRe().equals("iniciar")) {
            return mapping.findForward(INICIAR);
        }
        if (frm.getRe().equals("admin")) {
            return mapping.findForward(ADMIN);
        }
        if (frm.getRe().equals("mensajes")) {
            return mapping.findForward(MENSAJES);
        }
        return mapping.findForward(LOGOUT);
    }
}
