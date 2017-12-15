package com.mecasut.reportes;

import com.mecasut.admon.registroUniversidadForm;
import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Universidad;
import java.sql.ResultSet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Daniel Ramirez Torres
 */
public class Excel_Reporte_Borrador extends org.apache.struts.action.Action {
    /* forward name="success" path="" */

    public Excel_Reporte_Borrador() {
    }
    private static final String SUCCESS = "success";
    String rutaRelativaWeb = "";
    //Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
    String rutaAbsolutaEnDisco;
    private static final String LOGOUT = "logout";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }

        String universidad;
        String periodo;
        Universidad uni = new Universidad();
        if (request.getParameter("iun") != null) {
            universidad = request.getParameter("iun").toString();
            periodo = request.getParameter("ipe").toString();
        } else {
            universidad = sesion.getAttribute("idUniversidad").toString();
            periodo = String.valueOf(registroUniversidadForm.getPeriodoActual());
        }
        String ask = request.getParameter("ask");

        if (ask.equals("forllenNuevo")) {

            limpiar();
            //Variables y/o objetos de las consultas
            String consulta = "";
            ResultSet rs = null;
            ConexionMySQL con = new ConexionMySQL();
            con.Conectar();
            con.Conectar();
            consulta = "Select nombre, apaterno, amaterno, tipo, cargo, telefono, mail from responsables_universidad ru inner join responsables r on ru.id_responsable = r.id_responsable where ru.tipo = 'RC' and ru.id_universidad = ".concat(universidad).concat(" and ru.id_periodo = ").concat(periodo);
            rs = con.Consultar(consulta);
            if (rs.next()) {
                System.out.print("Creando excel  ");
                rutaRelativaWeb = "/files/Stilos.xls";
                //Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
                rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);
                Excel_Reporte_Universidad obj = new Excel_Reporte_Universidad(rutaAbsolutaEnDisco);
                Excel_Reporte_Universidad_Informacion informacionHoja = new Excel_Reporte_Universidad_Informacion();
                Excel_Reporte_Universidad_Eficacia eficaciaHoja = new Excel_Reporte_Universidad_Eficacia();
                Excel_Reporte_Universidad_Pertinencia pertinenciaHoja = new Excel_Reporte_Universidad_Pertinencia();
                Excel_Reporte_Universidad_Eficiencia eficienciaHoja = new Excel_Reporte_Universidad_Eficiencia();
                Excel_Reporte_Universidad_Vinculacion vinculacionHoja = new Excel_Reporte_Universidad_Vinculacion();
                Excel_Reporte_Universidad_Equidad equidadHoja = new Excel_Reporte_Universidad_Equidad();

                obj = informacionHoja.crearInformacion(universidad, periodo, obj);
                obj = eficaciaHoja.crearEficacia(universidad, periodo, obj);
                obj = eficienciaHoja.crearEficiencia(universidad, periodo, obj);
                obj = pertinenciaHoja.crearPertinencia(universidad, periodo, obj);
                obj = vinculacionHoja.crearVinculacion(universidad, periodo, obj);
                obj = equidadHoja.crearEquidad(universidad, periodo, obj);
                //Categoría PERTINENCIA

                con.Desconectar();
                EnviarExcel(response, obj.regresarLibro(), "FORMATO MECASUT " + uni.getNombreUniversidad(universidad) + "(borrador)");
                limpiar();
                //}
            }
        }
        return mapping.findForward(SUCCESS);
    }

    private void EnviarExcel(HttpServletResponse response, HSSFWorkbook libro, String nombreArchivo) {
        try {
            nombreArchivo=nombreArchivo.replace(",", "");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".xls");
            ServletOutputStream fileOut = response.getOutputStream();
            libro.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void limpiar() {
        System.err.println("\n\n\n No");
        System.err.println("Memoria Máxima " + Runtime.getRuntime().maxMemory() / 1024 / 1024);
        System.err.println("Memoria Libre " + Runtime.getRuntime().freeMemory() / 1024 / 1024);
    }
    
   public double redondear(double numero){
        return Math.rint(numero*100)/100;
    }
}
