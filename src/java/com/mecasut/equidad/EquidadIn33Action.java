/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mecasut.equidad;

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
 * @author Juan
 */
public class EquidadIn33Action extends org.apache.struts.action.Action {
    
    private static final String SUCCESS = "success";
    private static final String UNIVERSIDAD = "universidad";
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
         HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();

        EquidadIn33Form frm = (EquidadIn33Form) form;
        
        if (frm.getAsk().equals("universidad")){
            return mapping.findForward(UNIVERSIDAD);
        }

        String valores = frm.getValores();
        boolean res = Guardar(valores, idUniversidad, idPeriodo);
        if (res==false)
            return mapping.findForward(null);
        else
        return mapping.findForward(SUCCESS);
    }


    private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
        if (valores.equals("-")){
            return false;
        }else{
            String[] values;//Valores de todo el formulario
            values = valores.split("-");
            String[] datos;//Valores de cada insert
            ConexionMySQL conexion = new ConexionMySQL();
            
            for (int i = 1; i < values.length; i++) {
                try {
                    datos = values[i].split(",");
                    String sql = "UPDATE equidadin33 SET  nuevo_ingreso=".concat(datos[0])
                            .concat(", egresados_bachillerato=").concat(datos[1])
                            .concat(" WHERE id_universidad=").concat(idUniversidad)
                            .concat(" AND id_periodo=").concat(idPeriodo);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO equidadin33 VALUES";
                        for (int j = 1; j < values.length; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 1) {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",")
                                        .concat(idPeriodo).concat(",")
                                        .concat(datos[0]).concat(",")
                                        .concat(datos[1]).concat(")");
                            } else {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",")
                                        .concat(idPeriodo).concat(",")
                                        .concat(datos[0]).concat(",")
                                        .concat(datos[1]).concat("),");
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EquidadIn33Action.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    conexion.Desconectar();
                }
            }
        }
        return true;
    }
}
