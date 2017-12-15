/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mecasut.pertinencia;

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
 * @author Cuauhtemoc Medina
 */
public class PertinenciaIn21Action extends org.apache.struts.action.Action {
    
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

        PertinenciaIn21Form frm = (PertinenciaIn21Form) form;
        if (!frm.getValores().equals("-1")) {
            boolean band = Guardar(frm.getValores(), idUniversidad, idPeriodo);
            if (band == false) {
                return mapping.findForward(null);
            } else {
                return mapping.findForward(SUCCESS);
            }
        }else{
            return mapping.findForward(null);
        }
    }

    private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
        String[] values;//Valores de todo el formulario
        values = valores.split("-");
        String[] datos;//Valores de cada insert
        ConexionMySQL conexion = new ConexionMySQL();
        
        try {
            for (int i = 1; i < values.length; i++) {
                datos = values[i].split(",");
                String sql = "Update pertinenciain21 set r_a = ".concat(datos[1])
                        .concat(", r_b = ").concat(datos[2]).concat(", r_c = ").concat(datos[3])
                        .concat(", r_d = ").concat(datos[4]).concat(", r_e = ").concat(datos[5])
                        .concat(", r_f = ").concat(datos[6]).concat(", r_g = ").concat(datos[7])
                        .concat(" where id_universidad = ").concat(idUniversidad)
                        .concat(" and id_periodo = ").concat(idPeriodo)
                        .concat(" and id_pregunta = ").concat(datos[0]);
                if (conexion.Modificar(sql) == 0) {
                    String sql2 = "Insert into pertinenciain21 values";
                    for (int j = 1; j < values.length; j++) {
                        datos = values[j].split(",");
                        if (j == values.length - 1) {
                            sql2 = sql2.concat("(").concat(idUniversidad).concat(",") //uni
                                    .concat(idPeriodo).concat(",") //periodo
                                    .concat(datos[0]).concat(",") // id_pregunta
                                    .concat(datos[1]).concat(",") //r_a
                                    .concat(datos[2]).concat(",") //r_b
                                    .concat(datos[3]).concat(",") //r_c
                                    .concat(datos[4]).concat(",") //r_d
                                    .concat(datos[5]).concat(",") //r_e
                                    .concat(datos[6]).concat(",") //r_f
                                    .concat(datos[7]) //r_g
                                    .concat(")");
                        } else {
                            sql2 = sql2.concat("(").concat(idUniversidad).concat(",") //uni
                                    .concat(idPeriodo).concat(",") //periodo
                                    .concat(datos[0]).concat(",") // id_pregunta
                                    .concat(datos[1]).concat(",") //r_a
                                    .concat(datos[2]).concat(",") //r_b
                                    .concat(datos[3]).concat(",") //r_c
                                    .concat(datos[4]).concat(",") //r_d
                                    .concat(datos[5]).concat(",") //r_e
                                    .concat(datos[6]).concat(",") //r_f
                                    .concat(datos[7]) //r_g
                                    .concat("),");
                        }
                    }
                    conexion.Insertar(sql2);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error de guardado pertinencia 21: "+ex);
            return false;
        }
        return true;
    }
}