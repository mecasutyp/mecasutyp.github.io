/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.pertinencia;

import com.mecasut.conexion.ConexionMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Danny
 */
@WebServlet(name = "PertinenciaIn24Datos", urlPatterns = {"/PertinenciaIn24Datos"})
public class PertinenciaIn24Datos extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession(false);
            String idUniversidad = sesion.getAttribute("idUniversidad").toString();
            String Id_Periodo = sesion.getAttribute("idPeriodo").toString();
            String datos = request.getParameter("inf");
            if (datos != null) {
                if (Guardar(datos, idUniversidad, Id_Periodo) == true) {
                    //Datos guardados Correctamente
                    out.println(1);
                } else {
                    //Datos con error en la base
                    out.println(0);
                }
            } else {
                //Datos vacios, aunque es imposible xD
                out.println(2);
            }
        } finally {
            out.close();
        }
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
                    String sql = "Update pertinenciain24 set fila1 = ".concat(datos[0]).concat(", fila2 = ").concat(datos[1]).concat(", fila3 = ").concat(datos[2]).concat(", fila4 = ").concat(datos[3]).concat(", fila5 = ").concat(datos[4]).concat(", fila6 = ").concat(datos[5]).concat(", fila7 = ").concat(datos[6]).concat(", fila8 = ").concat(datos[7]).concat(", fila9 = ").concat(datos[8]).concat(", fila10 = ").concat(datos[9]).concat(", fila11 = ").concat(datos[10]).concat(", fila12 = ").concat(datos[11]).concat(", trabajo_actual1 = ").concat(datos[12]).concat(", ultimos_3anios1 = ").concat(datos[13]).concat(", mas_3anios1 = ").concat(datos[14]).concat(", trabajo_actual2 = ").concat(datos[15]).concat(", ultimos_3anios2 = ").concat(datos[16]).concat(", mas_3anios2 = ").concat(datos[17]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo);
                    conexion.Modificar(sql);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into pertinenciain24 values";
                        for (int j = 1; j < values.length; j++) {
                            datos = values[j].split(",");
                            sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(",").concat(datos[6]).concat(",").concat(datos[7]).concat(",").concat(datos[8]).concat(",").concat(datos[9]).concat(",").concat(datos[10]).concat(",").concat(datos[11]).concat(",").concat(datos[12]).concat(",").concat(datos[13]).concat(",").concat(datos[14]).concat(",").concat(datos[15]).concat(",").concat(datos[16]).concat(",").concat(datos[17]).concat(")"));
                        }
                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado pertinencia 24: " + ex);
                return false;
            }
            return true;
        }
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
