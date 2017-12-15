/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mecasut.vinculacion;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.SQLException;
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
public class VinculacionIn30Action extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
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
        
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();
        VinculacionIn30Form frm = (VinculacionIn30Form) form;
        if (!frm.getValores().equals("-1")) {
            boolean band = Guardar(frm.getValores(), idUniversidad, idPeriodo);
            if (band == false) {
                return mapping.findForward(null);
            } else {
                return mapping.findForward(SUCCESS);
            }
        } else {
            return mapping.findForward(null);
        }
    }

        private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
        ConexionMySQL conexion = new ConexionMySQL();
        
        try {
            String[] datos = valores.split(",");
            String sql = "Update vinculacionin30 set d_capacitacion = ".concat(datos[0])
                    .concat(", d_actualizacion = ").concat(datos[1])
                    .concat(", d_desarrollo = ").concat(datos[2])
                    .concat(", o_capacitacion = ").concat(datos[3])
                    .concat(", o_actualizacion = ").concat(datos[4])
                    .concat(", o_desarrollo = ").concat(datos[5])
                    .concat(" where id_universidad = ")
                    .concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo);
            if (conexion.Modificar(sql) == 0) {
                sql = "Insert into vinculacionin30 values";
                sql = sql.concat("(").concat(idUniversidad).concat(",") //uni
                        .concat(idPeriodo).concat(",") //periodo
                        .concat(valores) //Valores
                        .concat(")");
            }
            conexion.Insertar(sql);
        } catch (SQLException ex) {
            System.err.println("Error al guardar en VinculacionIn30Action. Error: " + ex.getMessage());
            return false;
        }
        return true;
    }
}
