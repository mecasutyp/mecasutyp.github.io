/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

import com.mecasut.conexion.ConexionMySQL;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Salvador Zamora Arias
 */
public class Excel_Reporte_Directorio extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String LOGOUT = "logout";
    ConexionMySQL con;
    ResultSet rs;
    String consulta;
    HttpSession sesion;
    String periodo;
    String anio;
    HojaCalculo obj;

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

        con = new ConexionMySQL();
        sesion = request.getSession(false);
        periodo = request.getParameter("ipe").toString();

        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }

        //obtener el valor de ask desde la url
        String ask = request.getParameter("ask");
        if (ask.equals("directorio")) {
            try {
                //obtener el año del periodo seleccionado
                consulta = "select anio from periodos where id_periodo=".concat(periodo);
                ResultSet rsCon = con.Consultar(consulta);
                if (rsCon.next()) {
                    anio = rsCon.getString(1);
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Directorio reporte, consultando cantidad de universidades y/o periodo activo " + ex.getMessage());
                con.Desconectar();
            } finally {
                con.Desconectar();
            }

            String rutaRelativaWeb = "/files/Sabana2.xlsx";//Especifíco en donde se encuentra el archivo de excel en forma relativa
            String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);//Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
            InputStream inp = new FileInputStream(rutaAbsolutaEnDisco);//Cargo en archivo en un Stream
            obj = new HojaCalculo();
            obj.leerLibro(inp);
            inp.close();
            obj.obtenerEstilos();
            obj.eliminarHoja(0);

            try {
                limpiarBasurero();
                System.out.println("Generando Directorio...");
                /*LISTAS NECESARIAS*/
                ListaUniversidades univs = new ListaUniversidades(periodo);
                ListaNiveles niveles = new ListaNiveles(false);
                /*EFICACIA*/
                ExcelDirectorio directorio = new ExcelDirectorio(anio, obj, periodo, univs, niveles);
                obj = directorio.hacerDirectorio();
                directorio = null;
        
                univs = null;
                niveles = null;
                /*IMPRIMIR EL EXCEL*/
                EnviarExcel(response, obj.getLibro(), "Directorio " + anio + "- " + (Integer.parseInt(anio) + 1));
                obj = null;
                limpiarBasurero();
                return mapping.findForward(SUCCESS);
            } catch (Exception ex) {
                System.err.println(ex);
            } catch (OutOfMemoryError e){
                System.err.println("OUT OF MEMORY personalizado by Salvador Zamora: \n "
                        + "\n La memoria se ha agotado, el total disponible es: "+Runtime.getRuntime().totalMemory()
                        + "\n La memoria libre es: "+Runtime.getRuntime().freeMemory());
                limpiarBasurero();
            }
        }
        return mapping.findForward(SUCCESS);
    }

    /**
     * Este método es genérico para exportar un libro creado con HSSF a un
     * Response
     *
     * @param response Response a donde se va a enviar el libro .xlsx
     * @param libro Libro que se va a exportar
     * @param nombreArchivo Nombre del archivo a guardar en el equipo
     */
    private void EnviarExcel(HttpServletResponse response, Workbook libro, String nombreArchivo) {
        try {
            System.out.println("Generando exportacion Directorio...");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".xlsx");
            ServletOutputStream fileOut = response.getOutputStream();
            libro.write(fileOut);
            fileOut.flush();
            fileOut.close();
            System.out.println("Directorio Generada");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void limpiarBasurero() {
        System.err.println("\n\n\n\n\nMEMORIA MÁXIMA : " + Runtime.getRuntime().maxMemory()/1024/1024+" Mb");
        System.err.println("MEMORIA LIBRE : " + Runtime.getRuntime().freeMemory()/1024/1024+" Mb");
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
    }
}