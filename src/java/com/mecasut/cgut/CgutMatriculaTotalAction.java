/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * Daniel Ramírez Torres
 */
public class CgutMatriculaTotalAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";
    ConexionMySQL conexion = new ConexionMySQL();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        CgutMatriculaTotalForm frm = (CgutMatriculaTotalForm) form;
        if (!frm.getMat_total().equals("")) {
            String valores = request.getParameter("mat_total").toString();
            System.err.println(valores);
            String datos[] = valores.split("-");
            String datos2[];
            String consulta = "";
            String consulta2 = "";
            int mat_total = 0;
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
            ResultSet niveles = conexion.Consultar("Select id_nivel from nivel_pe where id_nivel > 0 and activo = 1");
            for (int i = 0; i < datos.length; i++) {
                niveles.beforeFirst();
                datos2 = datos[i].split(",");
                mat_total = 0;//se reinicia el valor para que la suma sea solo por nivel
                for (int j = 1; niveles.next(); j++) {
                    String sql = "update mat_total_universidad set matricula = ".concat(datos2[j]).concat(" where id_universidad = '").concat(datos2[0]).concat("' and id_periodo = '").concat(String.valueOf(idPeriodo)).concat("' and id_nivel = '").concat(niveles.getString(1)).concat("'");
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "insert into mat_total_universidad values (".concat(datos2[0]).concat(",").concat(String.valueOf(idPeriodo)).concat(",").concat(niveles.getString(1)).concat(",").concat(datos2[j]).concat(");");
                        conexion.Insertar(sql2);
                    }
                    mat_total += Integer.parseInt(datos2[j]);//suma de la cantida de alumnos por nivel para conocer la matricula total
                }
                //Consultas para insertar la matricula total en la tabla datos_universidad
                consulta = "UPDATE datos_universidad SET matricula_total=".concat(String.valueOf(mat_total))
                        .concat(" WHERE id_universidad = '").concat(datos2[0])
                        .concat("' and id_periodo = '").concat(String.valueOf(idPeriodo)).concat("'");
                if (conexion.Modificar(consulta) == 0) {
                    consulta2 = "INSERT INTO datos_universidad (id_universidad, id_periodo, matricula_total)".concat(" VALUES (")
                            .concat(datos2[0]).concat(",").concat(String.valueOf(idPeriodo)).concat(",")
                            .concat(String.valueOf(mat_total)).concat(")");
                    conexion.Insertar(consulta2);
                }
            }
            conexion.Desconectar();
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
}
