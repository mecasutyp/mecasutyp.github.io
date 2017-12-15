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
 * @author Daniel Ramírez Torres
 */
public class PertinenciaIn20Action extends org.apache.struts.action.Action {

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
        PertinenciaIn20Form frm = (PertinenciaIn20Form) form;
        Guardar(frm.getValores(), idUniversidad, idPeriodo);
        return mapping.findForward(SUCCESS);

    }

    private boolean Guardar(String valores, String Id_Universidad, String Id_Periodo) {
        String[] values;//Valores de todo el formulario
        String[] datos;//Valores de cada insert
        values = valores.split("-");
        ConexionMySQL conexion = new ConexionMySQL();
        String sql = null;
        String sql2 = null;
        try {
            for (int i = 1; i < values.length; i++) {
                datos = values[i].split(",");
                sql = "Update pertinenciain20 set cursos_realizados_i = ".concat(datos[0])
                        .concat(", cursos_realizados_e = ").concat(datos[1])
                        .concat(", talleres_realizados_i = ").concat(datos[2])
                        .concat(", talleres_realizados_e = ").concat(datos[3])
                        .concat(", acciones_otro = ").concat(datos[4])
                        .concat(", ptc_cursos_i = ").concat(datos[5])
                        .concat(", ptc_cursos_e = ").concat(datos[6])
                        .concat(", ptc_talleres_i = ").concat(datos[7])
                        .concat(", ptc_talleres_e = ").concat(datos[8])
                        .concat(", ptc_otros = ").concat(datos[9])
                        .concat(", pa_cursos_i = ").concat(datos[10])
                        .concat(", pa_cursos_e = ").concat(datos[11])
                        .concat(", pa_talleres_i = ").concat(datos[12])
                        .concat(", pa_talleres_e = ").concat(datos[13])
                        .concat(", pa_otros = ").concat(datos[14])
                        .concat(", material_difusion = ").concat(datos[15])
                        .concat(", material_capacitacion = ").concat(datos[16])
                        .concat(", material_otro = ").concat(datos[17])
                        .concat(", material_ptc = '").concat(datos[18])
                        .concat("', material_d_pa = '").concat(datos[19])
                        .concat("', material_d_alumnos = '").concat(datos[20])
                        .concat("', material_d_otro = '").concat(datos[21])
                        .concat("', material_especifique = '").concat(datos[22])
                        .concat("', ptc_terminaron = ").concat(datos[23])
                        .concat(", ptc_proceso  = ").concat(datos[24])
                        .concat(", ptc_no_tienen = ").concat(datos[25])
                        .concat(", ptc_aplicando = ").concat(datos[26])
                        .concat(", pa_terminaron = ").concat(datos[27])
                        .concat(", pa_proceso = ").concat(datos[28])
                        .concat(", pa_no_tienen = ").concat(datos[29])
                        .concat(", pa_aplicando = ").concat(datos[30])
                        .concat(" where id_universidad = ").concat(Id_Universidad)
                        .concat(" and id_periodo = ").concat(Id_Periodo);


                conexion.Modificar(sql);
                if (conexion.Modificar(sql) == 0) {
                    sql2 = "Insert into pertinenciain20 values";
                    for (int j = 1; j < values.length; j++) {
                        datos = values[j].split(",");
                        sql2 = sql2.concat("(").concat(Id_Universidad)
                                .concat(",".concat(Id_Periodo)
                                .concat(",").concat(datos[0])
                                .concat(",").concat(datos[1])
                                .concat(",").concat(datos[2])
                                .concat(",").concat(datos[3])
                                .concat(",").concat(datos[4])
                                .concat(",").concat(datos[5])
                                .concat(",").concat(datos[6])
                                .concat(",").concat(datos[7])
                                .concat(",").concat(datos[8])
                                .concat(",").concat(datos[9])
                                .concat(",").concat(datos[10])
                                .concat(",").concat(datos[11])
                                .concat(",").concat(datos[12])
                                .concat(",").concat(datos[13])
                                .concat(",").concat(datos[14])
                                .concat(",").concat(datos[15])
                                .concat(",").concat(datos[16])
                                .concat(",").concat(datos[17])
                                .concat(",").concat(datos[18])
                                .concat(",").concat(datos[19])
                                .concat(",").concat(datos[20])
                                .concat(",").concat(datos[21])
                                .concat(",'").concat(datos[22])
                                .concat("',").concat(datos[23])
                                .concat(",").concat(datos[24])
                                .concat(",").concat(datos[25])
                                .concat(",").concat(datos[25])
                                .concat(",").concat(datos[27])
                                .concat(",").concat(datos[28])
                                .concat(",").concat(datos[29])
                                .concat(",").concat(datos[30])
                                .concat(")"));
                    }
                    conexion.Insertar(sql2);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL: " + sql + "\n+SQL2: " + sql2+"\nError de guardado pertinencia 20: " + ex);
            return false;
        }
        return true;
    }
}