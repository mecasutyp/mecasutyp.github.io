/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public class CgutServiciosAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    ConexionMySQL conexion = new ConexionMySQL();
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

        CgutServiciosForm frm = (CgutServiciosForm) form;

        if (frm.getAsk().equals("buscarServicio")) {
            if (frm.getCboServicios() != -1) {
                try {
                    String consulta = "SELECT * FROM encuesta_servicios WHERE id_servicio="
                            .concat(String.valueOf(frm.getCboServicios()));
                    ResultSet rs = conexion.Consultar(consulta);
                    while (rs.next()) {
                        frm.setNombre(rs.getString("nombre"));
                        frm.setDescripcion(rs.getString("descripcion"));
                    }
                } catch (Exception ex) {
                    System.err.println("ERRORBD: Buscando Servicios en CgutServiciosAction.java " + ex.getMessage());
                } finally {
                    return mapping.findForward(SUCCESS);
                }
            } else {
                frm.setNombre("");
                frm.setDescripcion("");
                return mapping.findForward(SUCCESS);
            }
        }

        if (frm.getAsk().equals("nuevo")) {
            frm.setCboServicios(-1);
            frm.setNombre("");
            frm.setDescripcion("");
        }

        if (frm.getAsk().equals("guardar")) {
            String query = "";
            if (frm.getCboServicios() == -1) { // nuevo registro
                try {
                    query = "INSERT INTO encuesta_servicios () VALUES (null, '".concat(frm.getNombre()).concat("','")
                            .concat(frm.getDescripcion()).concat("')");
                    conexion.Insertar(query);
                    frm.setCboServicios(-1);
                    frm.setNombre("");
                    frm.setDescripcion("");
                    frm.setServicios();
                } catch (Exception e) {
                    System.err.println("ERROR BD: Insertando un servicio " + e.getMessage());
                } finally {
                    conexion.Desconectar();
                    return mapping.findForward(SUCCESS);
                }
            } else { // actualización de registro
                try {
                    query = "UPDATE encuesta_servicios SET nombre='".concat(frm.getNombre()).concat("', descripcion='")
                            .concat(frm.getDescripcion()).concat("' WHERE id_servicio=").concat(String.valueOf(frm.getCboServicios()));
                    conexion.Modificar(query);
                } catch (Exception e) {
                    System.err.println("ERROR BD: Modificando un servicio " + e.getMessage());
                } finally {
                    return mapping.findForward(SUCCESS);
                }
            }
        }

        return mapping.findForward(SUCCESS);
    }
}
