/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.vinculacion;

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
 * @author Joshua
 */
public class VinculacionIn28Action extends org.apache.struts.action.Action {

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
        VinculacionIn28Form frm = (VinculacionIn28Form) form;
        if (!frm.getValores().equals("-1")) {
            boolean band = Guardar(frm.getValores(), idUniversidad, idPeriodo);
            if (band == false) {
                return mapping.findForward(null);
            } else {
                return mapping.findForward(SUCCESS);
            }
        } else {
            return mapping.findForward(null);
        }
    }
    private boolean Guardar(String valores, String idUniversidad, String idPeriodo){
        String[] values;//Valores de todo el formulario
        String[] datos;//Valores de cada insert
        values = valores.split("-");
        ConexionMySQL conexion = new ConexionMySQL();
        
        try {
            for (int i = 1; i < values.length; i++){
                datos = values[i].split(",");
                String sql = "Update vinculacionin28 set org_publicos = "
                        .concat(datos[1])
                        .concat(", org_privados = ").concat(datos[2])
                        .concat(", org_sociales = ").concat(datos[3])
                        .concat(", ing_publicos = ").concat(datos[4])
                        .concat(", ing_privados = ").concat(datos[5])
                        .concat(", ing_sociales = ").concat(datos[6])
                        .concat(" where id_universidad = ").concat(idUniversidad)
                        .concat(" and id_periodo = ").concat(idPeriodo)
                        .concat(" and id_estudio = ").concat(datos[0]);
                if(conexion.Modificar(sql) == 0){
                    String sql2 = "Insert into vinculacionin28 values";
                    for (int j = 1; j < values.length; j++){
                        if (j == values.length-1){
                            sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat(")");
                        }else{
                            sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat("),");
                        }
                    }
                    conexion.Insertar(sql2);
                }
            }
        } catch (SQLException ex) {
            return false;
        } finally{
            conexion.Desconectar();
        }
        return true;
    }
}
