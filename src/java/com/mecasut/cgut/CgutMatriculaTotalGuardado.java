/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
@WebServlet(name = "CgutMatriculaTotalGuardado", urlPatterns = {"/CgutMatriculaTotalGuardado"})
public class CgutMatriculaTotalGuardado extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ConexionMySQL conexion = new ConexionMySQL();
        HttpSession sesion = request.getSession(false);
        conexion.Conectar();

        String valores = request.getParameter("cadena").toString();
        String datos[] = valores.split("-");
        String datos2[];
        String consulta = "";
        String consulta2 = "";
        int mat_total = 0;
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
        try {
            ResultSet niveles = conexion.Consultar("Select id_nivel from nivel_pe where id_nivel > 0 and activo = 1");
            for (int i = 0; i < datos.length; i++) {
                niveles.beforeFirst();
                datos2 = datos[i].split(",");
                mat_total = 0;//se reinicia el valor para que la suma sea solo por nivel
                for (int j = 1; niveles.next(); j++) {
                    String sql = "update mat_total_universidad set matricula = ".concat(datos2[j]).concat(" where id_universidad = '").concat(datos2[0]).concat("' and id_periodo = '").concat(String.valueOf(idPeriodo)).concat("' and id_nivel = '").concat(niveles.getString(1)).concat("'");
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "insert into mat_total_universidad values (".concat(datos2[0]).concat(",").concat(String.valueOf(idPeriodo)).concat(",").concat(niveles.getString(1)).concat(",").concat(datos2[j]).concat(");");
                        conexion.Insertar(sql2);
                    }
                    mat_total += Integer.parseInt(datos2[j]);//suma de la cantida de alumnos por nivel para conocer la matricula total
                }
                //Consultas para insertar la matricula total en la tabla datos_universidad
                consulta = "UPDATE datos_universidad SET matricula_total=".concat(String.valueOf(mat_total))
                        .concat(" WHERE id_universidad = '").concat(datos2[0])
                        .concat("' and id_periodo = '").concat(String.valueOf(idPeriodo)).concat("'");
                if (conexion.Modificar(consulta) == 0) {
                    consulta2 = "INSERT INTO datos_universidad (id_universidad, id_periodo, matricula_total)".concat(" VALUES (")
                            .concat(datos2[0]).concat(",").concat(String.valueOf(idPeriodo)).concat(",")
                            .concat(String.valueOf(mat_total)).concat(")");
                    conexion.Insertar(consulta2);
                }
            }
            out.println("ok");
            System.out.println("Datos de Matricula Total Guardados correctamente");
        } catch (Exception ex) {
            out.println("error");
            System.err.println("Error");
            System.err.println("ERRORBD: CgutMatriculaTotalGuardado.java Servlet. " + ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
