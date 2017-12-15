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
public class PertinenciaIn19Action extends org.apache.struts.action.Action {
    /* forward name="success" path="" */

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String Id_Periodo = sesion.getAttribute("idPeriodo").toString();
        PertinenciaIn19Form frm = (PertinenciaIn19Form) form;
        Guardar(frm.getValores(), idUniversidad, Id_Periodo);
        return mapping.findForward(SUCCESS);
    }

    private boolean Guardar(String valores, String Id_Universidad, String Id_Periodo) {
        if (valores.equals("Error")) {
            return false;
        } else {
            String[] values;//Valores de todo el formulario
            String[] values1;
            String[] datos;//Valores de cada insert
            values = valores.split("x");
            values1 = valores.split("z");
            ConexionMySQL conexion = new ConexionMySQL();
            
            try {
                for (int i = 1; i < values.length - 1; i++) {
                    datos = values[i].split(",");
                    String sql0 = "Update pertinenciain19 set matricula_inicial_aten = ".concat(datos[1]).concat(", matricula_acreditada = ").concat(datos[2]).concat(", acreditados = ").concat(datos[3]).concat(", vigencia_acreditacion = ").concat(datos[4]).concat(", id_organismo = ").concat(datos[6]).concat(", fecha_acreditacion = ").concat(datos[5]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and id_pe = ").concat(datos[0]);
                    conexion.Modificar(sql0);
                    if (conexion.Modificar(sql0) == 0) {
                        String sql1 = "Insert into pertinenciain19 values";
                        for (int j = 1; j < values.length - 1; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 2) {
                                sql1 = sql1.concat("(".concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(")")));
                            } else {
                                sql1 = sql1.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",".concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat("),")));
                            }
                        }
                        conexion.Insertar(sql1);
                    }
                }
                for (int i = 1; i < values1.length; i++) {
                    datos = values1[i].split(",");
                    String sql = "Update pertinenciain19_1 set matricula_inicial_aten = ".concat(datos[1]).concat(", matricula_acreditada = ").concat(datos[2]).concat(", acreditados = ").concat(datos[3]).concat(", vigencia_acreditacion = ").concat(datos[4]).concat(", id_organismo = ").concat(datos[6]).concat(", fecha_acreditacion = ").concat(datos[5]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and id_pe = ").concat(datos[0]);
                    conexion.Modificar(sql);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into pertinenciain19_1 values";
                        for (int j = 1; j < values1.length; j++) {
                            datos = values1[j].split(",");
                            if (j == values1.length - 1) {
                                sql2 = sql2.concat("(".concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(")")));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",".concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat("),")));
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado pertinencia 19: "+ex);
                return false;
            }
            return true;
        }
    }
}
