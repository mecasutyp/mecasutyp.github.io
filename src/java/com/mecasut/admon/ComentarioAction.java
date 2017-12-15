/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.eficacia.EficaciaIn1Action;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Salvador Zamora Arias 
 * @date 23/08/2017
 */
public class ComentarioAction extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession sesion = request.getSession(false);
            String comentario = request.getParameter("Comentario");
            String indicad = request.getParameter("Indicador");
            String iduni = sesion.getAttribute("idUniversidad").toString();
            String idper = sesion.getAttribute("idPeriodo").toString();
            Guardar(comentario, indicad, iduni, idper);
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    private boolean Guardar(String comentario, String indica, String idUniversidad, String idPeriodo) {
        if (comentario.equals("")) {
                return false;
        } else {
            ConexionMySQL conexion = new ConexionMySQL();
                try {
                    String sql = "UPDATE comentarios SET comentario = '"+comentario+"' WHERE "
                            + "id_universidad="+idUniversidad+" AND id_periodo="+idPeriodo+" AND indicador="+indica;
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO comentarios VALUES ("+idUniversidad+","+idPeriodo+","
                                + " "+indica+", '"+comentario+"')";
                        conexion.Insertar(sql2);
                    }
                } catch (SQLException ex) {
                    System.err.println("SQLException ComentarioAction "+ex.getMessage());
                 } finally {
                    conexion.Desconectar();
                }
            return true;
        }
    }    
}
