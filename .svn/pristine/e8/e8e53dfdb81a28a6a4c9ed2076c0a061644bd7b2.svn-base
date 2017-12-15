/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author Cuauhtemoc Medina
 */
public class EficienciaIn16Action extends org.apache.struts.action.Action {
    
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

        EficienciaIn16Form frm = (EficienciaIn16Form) form;
        boolean band = false;

        if (!frm.getValores().equals("-1")) {
            band = Guardar(frm.getValores(), idUniversidad, idPeriodo);
        }

        if (band == false) {
            return mapping.findForward(null);
        } else {
            return mapping.findForward(SUCCESS);
        }
        
    }

    private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
        String[] values;
        values=valores.split(",");
        ConexionMySQL conexion = new ConexionMySQL();
        
        try {
            String sql="UPDATE eficienciain16 SET libros=".concat(values[0]).concat(", ")
                    .concat("titulos=").concat(values[1]).concat(", ")
                    .concat("revistas=").concat(values[2]).concat(", ")
                    .concat("bibliotecas=").concat(values[3]).concat(" WHERE id_universidad=").concat(idUniversidad)
                    .concat(" AND id_periodo=").concat(idPeriodo);
            //HACE UNA ACTUALIZACION, SI DEVUELVE CERO QUIERE DECIR QUE NO EXISTÍA EL REGISTRO
            //POR LO TANTO SE PROCEDE A REALIZAR LA ACTUALIZACION
            if (conexion.Modificar(sql) == 0){
                sql="INSERT INTO eficienciain16 () VALUES ("
                        .concat(idUniversidad).concat(",")
                        .concat(idPeriodo).concat(",")
                        .concat(values[0]).concat(",")
                        .concat(values[1]).concat(",")
                        .concat(values[2]).concat(",")
                        .concat(values[3]).concat(")");
                conexion.Insertar(sql);
            }
            return true;
        } catch (SQLException ex) {
            System.err.println("Error de guardado eficiencia 16: "+ex);
            return false;
        }finally{
            conexion.Desconectar();
        }
    }
    
}
