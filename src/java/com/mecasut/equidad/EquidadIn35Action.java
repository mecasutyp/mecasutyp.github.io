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
 * @author Juan García Barradas
 */

public class EquidadIn35Action extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();

        EquidadIn35Form frm = (EquidadIn35Form) form;

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
                    String sql = "UPDATE equidadin35 SET dep_realizados=".concat(datos[0])
                            .concat(", dep_programados=").concat(datos[1])
                            .concat(", dep_personas=").concat(datos[2])
                            .concat(", dep_aspectos=").concat(datos[3])
                            .concat(", cul_realizados=".concat(datos[4]))
                            .concat(", cul_programados=").concat(datos[5])
                            .concat(", cul_personas=").concat(datos[6])
                            .concat(", cul_aspectos=").concat(datos[7])
                            .concat(", com_realizados=".concat(datos[8]))
                            .concat(", com_programados=").concat(datos[9])
                            .concat(", com_personas=").concat(datos[10])
                            .concat(", com_aspectos=").concat(datos[11])
                            .concat(" WHERE id_universidad=").concat(idUniversidad)
                            .concat(" AND id_periodo=").concat(idPeriodo);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO equidadin35 VALUES";
                         sql2 = sql2.concat("(").concat(idUniversidad).concat(",")
                                        .concat(idPeriodo).concat(",")
                                        .concat(values[i]).concat(")");
                        
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
