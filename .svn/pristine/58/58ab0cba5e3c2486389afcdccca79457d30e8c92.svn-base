/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
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
public class CgutEgresadosAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";
    ConexionMySQL conexion = new ConexionMySQL();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        CgutEgresadosForm frm = (CgutEgresadosForm) form;

        if (!frm.getEgresados_bachillerato().equals("")) {
            String valores = frm.getEgresados_bachillerato();
            String datos[] = valores.split("-");
            String datos2[];
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
            for (int i = 0; i < datos.length; i++) {
                datos2 = datos[i].split(",");
                String sql = "update egresados_estado set egresados = '".concat(datos2[1]).concat("' where id_estado = '").concat(datos2[0]).concat("' and id_periodo = '").concat(String.valueOf(idPeriodo)).concat("'");
                if (conexion.Modificar(sql) == 0) {
                    String sql2 = "insert into egresados_estado(id_estado, egresados, id_periodo) values ";
                    for (int j = 0; j < datos.length; j++) {
                        if (j == datos.length - 1) {
                            sql2 += "(".concat(datos[j]).concat(",").concat(String.valueOf(idPeriodo)).concat(")");
                        } else {
                            sql2 += "(".concat(datos[j]).concat(",").concat(String.valueOf(idPeriodo)).concat("),");
                        }
                    }
                    conexion.Insertar(sql2);
                }
            }
            conexion.Desconectar();
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }
}
