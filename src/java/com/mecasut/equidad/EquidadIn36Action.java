package com.mecasut.equidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.mecasut.conexion.ConexionMySQL;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Juan García Barradas
 */
public class EquidadIn36Action extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();

        EquidadIn36Form frm = (EquidadIn36Form) form;

        boolean band = Guardar(frm.getValores(), idUniversidad, idPeriodo);
        if (band==false){
            return mapping.findForward(null);
        }else{
            return mapping.findForward(SUCCESS);
        }
    }


    private boolean Guardar(String valores, String idUniversidad, String idPeriodo){
        if(!valores.equals("Error")){
            String[] values;//Valores de todo el formulario
            String[] datos;//Valores de cada insert
            values = valores.split("-");
            ConexionMySQL conexion = new ConexionMySQL();
            
            try {
                for (int i = 1; i < values.length; i++){
                    datos = values[i].split(",");
                    String sql = "Update equidadin36 set apoyos_otorgados = "
                            .concat(datos[1])
                            .concat(" where id_universidad = ").concat(idUniversidad)
                            .concat(" and id_periodo = ").concat(idPeriodo)
                            .concat(" and id_apoyo = ").concat(datos[0]);
                    if(conexion.Modificar(sql) == 0){
                        String sql2 = "Insert into equidadin36 values";
                        for (int j = 1; j < values.length; j++){
                            if (j == values.length-1){
                                sql2 = sql2.concat("(".concat(idUniversidad).concat(",".concat(idPeriodo).concat(",").concat(values[j]).concat(")")));
                            }else{
                                sql2 = sql2.concat("(".concat(idUniversidad).concat(",".concat(idPeriodo).concat(",").concat(values[j]).concat("),")));
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
        }else{
            return true;
        }
    }
}
