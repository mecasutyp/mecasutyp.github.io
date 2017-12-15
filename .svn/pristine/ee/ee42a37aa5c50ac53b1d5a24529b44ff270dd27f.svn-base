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
 * @author Daniel Ramìrez Torres
 */
public class EficaciaIn8Action extends org.apache.struts.action.Action {
    private static final String SUCCESS = "success";
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);

        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String Id_Periodo = sesion.getAttribute("idPeriodo").toString();
        EficaciaIn8Form frm = (EficaciaIn8Form) form;
        Guardar(frm.getValores(), idUniversidad, Id_Periodo);
        return mapping.findForward(SUCCESS);
    }

    private boolean Guardar(String valores, String Id_Universidad, String Id_Periodo) {
        if (valores.equals("Error")) {
            return false;
        } else {
            String[] values;//Valores de todo el formulario
            String[] datos;//Valores de cada insert
            values = valores.split("-");

            ConexionMySQL conexion = new ConexionMySQL();
            
            try {
                for (int i = 1; i < values.length; i++) {

                    datos = values[i].split(",");
                    String sql = "Update eficaciain8 set sobresaliente = ".concat(datos[1])
                            .concat(", destacado = ").concat(datos[2])
                            .concat(", sin_testimonio = ").concat(datos[3])
                            .concat(", aplica = ").concat(datos[4])
                            .concat(" where id_universidad = ")
                            .concat(Id_Universidad).concat(" and id_periodo = ")
                            .concat(Id_Periodo).concat(" and id_nivel = ").concat(datos[0]);
                    
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into eficaciain8 values";
                        for (int j = 1; j < values.length; j++) {
                            if (j == values.length - 1) {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(values[j]).concat(")"));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(values[j]).concat("),"));
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado eficacia 8: "+ex);
                return false;
            }
            return true;
        }
    }
}
