/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.eficacia;

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
 * @author D@ny
 */
public class EficaciaIn3Action extends org.apache.struts.action.Action {
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
            HttpServletRequest request, HttpServletResponse response) {
        HttpSession sesion = request.getSession(false);

        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String Id_Periodo = sesion.getAttribute("idPeriodo").toString();
        EficaciaIn3Form frm = (EficaciaIn3Form) form;
        Guardar(frm.getValores(), idUniversidad, Id_Periodo);
        return mapping.findForward(SUCCESS);
    }

    private boolean Guardar(String valores, String Id_Universidad, String Id_Periodo) {
          if (valores.equals("Error")) {
            return false;
        } else {
//            System.out.println("Valores "+valores);
            String[] values;//Valores de todo el formulario
            String[] datos;//Valores de cada insert
            values = valores.split("-");
            ConexionMySQL conexion = new ConexionMySQL();
            try {
                for (int i = 1; i < values.length; i++) {

                    datos = values[i].split(",");
                    String sql = "UPDATE eficaciain3 SET "
                            + "rep_sep = ".concat(datos[3]).concat(", "
                            + "mat_sep = ").concat(datos[2]).concat(", "
                            + "rep_ene= ").concat(datos[5]).concat(", "
                            + "mat_ene= ").concat(datos[4]).concat(", "
                            + "rep_may= ").concat(datos[7]).concat(", "
                            + "mat_may= ").concat(datos[6]).concat(" "
                            + "WHERE id_universidad = ").concat(Id_Universidad).concat(" and "
                            + "id_periodo = ").concat(Id_Periodo).concat(" and "
                            + "id_pe = ").concat(datos[1]);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO eficaciain3 VALUES";
                                sql2 = sql2.concat("(".concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",")
                                        .concat(datos[0]).concat(",")
                                        .concat(datos[3]).concat(",")
                                        .concat(datos[2]).concat(",")
                                        .concat(datos[5]).concat(",")
                                        .concat(datos[4]).concat(",")
                                        .concat(datos[7]).concat(",")
                                        .concat(datos[6]).concat(",")
                                        .concat(datos[1])
                                        .concat(")")));

                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado eficacia 3: "+ex);
                return false;
            }
            return true;
        }
    }
}
