package com.mecasut.eficiencia;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Daniel Ramìrez Torres
 */
public class EficienciaIn15Action extends org.apache.struts.action.Action {

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
        String Id_Periodo = sesion.getAttribute("idPeriodo").toString();
        EficienciaIn15Form frm = (EficienciaIn15Form) form;
        Guardar(frm.getValores(), idUniversidad, Id_Periodo);
        return mapping.findForward(SUCCESS);
    }

    private boolean Guardar(String valores, String Id_Universidad, String Id_Periodo) {
        if (valores.equals("Error")) {
            return false;
        } else {
            String[] values;//Valores de todo el formulario
            String[] datos;//Valores de cada insert
            String[] values2;
            values = valores.split("z");
            values2 = valores.split("x");
            ConexionMySQL conexion = new ConexionMySQL();
            
            try {
                for (int i = 1; i < values.length - 1; i++) {
                    datos = values[i].split(",");
                    String sql = "Update eficienciain15 set sin_certificado = ".concat(datos[1]).concat(", en_proceso = ").concat(datos[2]).concat(", con_certificado = ").concat(datos[3]).concat(", en_proceso_recertificacion = ").concat(datos[4]).concat(", con_recertificado = ").concat(datos[5]).concat(" where id_certificacion = ").concat(datos[0]).concat(" and id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into eficienciain15 values";
                        for (int j = 1; j < values.length - 1; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 2) {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(")"));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat("),"));
                            }
                        }
                        conexion.Insertar(sql2);
                        System.err.println("SQL eficiencia15DAtos 15 ACTION"+sql2);
                    }
                }
                for (int i = 1; i < values2.length; i++) {

                    datos = values2[i].split(",");
                    String sql = "Update eficienciain15_1 set opcion = ".concat(datos[1]).concat(", periodo_inicio = ").concat(datos[2]).concat(", periodo_fin = ").concat(datos[3]).concat(", proxima_auditoria = ").concat(datos[4]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and num_fila = ").concat(datos[0]);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into eficienciain15_1 values";
                        for (int j = 1; j < values2.length; j++) {
                            datos = values2[j].split(",");
                            if (j == values2.length - 1) {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(")"));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat("),"));
                            }
                        }

                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado eficiencia 15: "+ex);
                return false;
            }
            return true;
        }
    }
}
