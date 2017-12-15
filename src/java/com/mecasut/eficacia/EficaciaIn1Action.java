/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.eficacia;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 * Actualización 2016: Salvador Zamora Arias
 */
public class EficaciaIn1Action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String UNIVERSIDAD = "universidad";
    private static final String LOGOUT = "logout";

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

        //SESIONES
        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();

        EficaciaIn1Form frm = (EficaciaIn1Form) form;
        if (frm.getAsk().equals("universidad")) {
            return mapping.findForward(UNIVERSIDAD);
        }

        String valores = frm.getValores();
        if (!valores.equals("-1")) {
            boolean res = Guardar(valores, idUniversidad, idPeriodo);
            if (res == false) {
                return mapping.findForward(null);
            } else {
                return mapping.findForward(SUCCESS);
            }
        } else {
            return mapping.findForward(null);
        }
    }

    private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
        if (valores.equals("-")) {//LOS VALORES ESTAN MAL
            return false;
        } else {
            String[] values;//Valores de todo el formulario
            values = valores.split("-");
            String[] datos;//Valores de cada insert
            ConexionMySQL conexion = new ConexionMySQL();

            for (int i = 1; i < values.length; i++) {
                try {
                    datos = values[i].split(",");
                    String sql = "UPDATE eficaciain1 SET egresados_bachillerato=".concat(datos[0])
                            .concat(", nuevo_ingreso=").concat(datos[1])
                            .concat(", c1=").concat(datos[2])
                            .concat(", c2=").concat(datos[3])
                            .concat(", c3=").concat(datos[4])
                            .concat(", aplica=").concat(datos[5])
                            .concat(", comentario='").concat(datos[6]).concat("'")
                            .concat(" WHERE id_universidad=").concat(idUniversidad)
                            .concat(" AND id_periodo=").concat(idPeriodo);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO eficaciain1 VALUES";
                        for (int j = 1; j < values.length; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 1) {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",")
                                        .concat(idPeriodo).concat(",")
                                        .concat(datos[0]).concat(",")
                                        .concat(datos[1]).concat(",")
                                        .concat(datos[2]).concat(",")
                                        .concat(datos[3]).concat(",")
                                        .concat(datos[4]).concat(",")
                                        .concat(datos[5]).concat(",'")
                                        .concat(datos[6]).concat("')");
                            } else {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",")
                                        .concat(idPeriodo).concat(",")
                                        .concat(datos[0]).concat(",")
                                        .concat(datos[1]).concat(",")
                                        .concat(datos[2]).concat(",")
                                        .concat(datos[3]).concat(",")
                                        .concat(datos[4]).concat(",")
                                        .concat(datos[5]).concat(",'")
                                        .concat(datos[6]).concat("'),");
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EficaciaIn1Action.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    conexion.Desconectar();
                }
            }
        }
        return true;
    }
}
