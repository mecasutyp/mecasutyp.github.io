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
public class VinculacionIn32Action extends org.apache.struts.action.Action {

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

        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();

        VinculacionIn32Form frm = (VinculacionIn32Form) form;
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
        String[] values;//Valores de todo el formulario
        values = valores.split("-");
        String[] datos;//Valores de cada insert
        ConexionMySQL conexion = new ConexionMySQL();
        
        try {
            for (int i = 0; i <= values.length - 1; i++) {
                datos = values[i].split(",");
                String sql = "Update vinculacionin32 set egresados_colocados = ".concat(datos[1])
                        .concat(", plazas = ").concat(datos[2])
                        .concat(" where id_universidad = ").concat(idUniversidad)
                        .concat(" and id_periodo = ").concat(idPeriodo)
                        .concat(" and id_nivel = ").concat(datos[0]);
                if (conexion.Modificar(sql) == 0) {
                    String sql2 = "Insert into vinculacionin32 values";
                    for (int j = 0; j <= values.length - 1; j++) {
                        datos = values[j].split(",");
                        if (j == values.length - 1) {
                            sql2 = sql2.concat("(").concat(idUniversidad).concat(",") //uni
                                    .concat(idPeriodo).concat(",") //periodo
                                    .concat(datos[0]).concat(",") // id_nivel
                                    .concat(datos[1]).concat(",") //egresados_colocados
                                    .concat(datos[2]) //plazas
                                    .concat(")");
                        } else {
                            sql2 = sql2.concat("(").concat(idUniversidad).concat(",") //uni
                                    .concat(idPeriodo).concat(",") //periodo
                                    .concat(datos[0]).concat(",") // id_nivel
                                    .concat(datos[1]).concat(",") //egresados_colocados
                                    .concat(datos[2]) //plazas
                                    .concat("),");
                        }
                    }
                    conexion.Insertar(sql2);
                }
            }
        } catch (SQLException ex) {
            System.err.println("ERROR vinculacionin32Action "+ex.getMessage());
            return false;
        } finally{
            conexion.Desconectar();
        }
        return true;
    }
}
