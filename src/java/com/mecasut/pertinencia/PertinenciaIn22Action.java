
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
  Author     : Daniel Ramìrez Torres
 */
public class PertinenciaIn22Action extends org.apache.struts.action.Action {
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String Id_Periodo = sesion.getAttribute("idPeriodo").toString();
        PertinenciaIn22Form frm = (PertinenciaIn22Form) form;
        Guardar(frm.getValores(), idUniversidad, Id_Periodo);
        return mapping.findForward(SUCCESS);
    }

    private boolean Guardar(String valores, String Id_Universidad, String Id_Periodo) {
        if (valores.equals("Error")) {
            return false;
        } else {
            String[] values;//Valores de todo el formulario
            String[] datos;//Valores de cada insert
            values = valores.split("x");
            ConexionMySQL conexion = new ConexionMySQL();
            
            try {
                for (int i = 1; i < values.length; i++) {
                    datos = values[i].split(",");
                    String sql = "Update pertinenciain22 set matricula_ini_pe = ".concat(datos[1])
                            .concat(", matricula_pert = ").concat(datos[2])
                            .concat(", pertinente = ").concat(datos[3])
                            .concat(", anio_inicio = ").concat(datos[4])
                            .concat(", anio_estudio = ").concat(datos[5])
                            .concat(", anio_analisis = ").concat(datos[6])
                            .concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and id_pe = ").concat(datos[0]);
                    conexion.Modificar(sql);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into pertinenciain22 values";
                        for (int j = 1; j < values.length; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 1) {
                                sql2 = sql2.concat("(".concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(",").concat(datos[6]).concat(")")));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",".concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(",").concat(datos[6]).concat("),")));
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado pertinencia 22: "+ex);
                return false;
            }
            return true;
        }
    }
}
