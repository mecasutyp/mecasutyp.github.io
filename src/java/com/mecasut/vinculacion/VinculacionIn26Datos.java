/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.vinculacion;

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
 * @author Daniel Ramirez Torres
 */
@WebServlet(name = "VinculacionIn26Datos", urlPatterns = {"/VinculacionIn26Datos"})
public class VinculacionIn26Datos extends HttpServlet {

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

    private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
        if (valores.equals("Error")) {
            return false;
        } else {
            ConexionMySQL conexion = new ConexionMySQL();
            try {
                String[] datos = valores.split(",");
                String sql = "Update vinculacionin26 set conv_acum = ".concat(datos[0])
                        .concat(", conv_acum_sup = ").concat(datos[1])
                        .concat(", publicos = ").concat(datos[2])
                        .concat(", privados = ").concat(datos[3])
                        .concat(", sociales = ").concat(datos[4])
                        .concat(", alumnos = ").concat(datos[5])
                        .concat(", docentes = ").concat(datos[6])
                        .concat(" where id_universidad = ").concat(idUniversidad)
                        .concat(" and id_periodo = ").concat(idPeriodo);
                if (conexion.Modificar(sql) == 0) {
                    sql = "Insert into vinculacionin26 values";
                    sql = sql.concat("(").concat(idUniversidad).concat(",") //uni
                            .concat(idPeriodo).concat(",") //periodo
                            .concat(datos[0]).concat(",")
                            .concat(datos[1]).concat(",")
                            .concat(datos[2]).concat(",")
                            .concat(datos[3]).concat(",")
                            .concat(datos[4]).concat(",")
                            .concat(datos[6]).concat(",")
                            .concat(datos[5])//Valores
                            .concat(")");
                }
                conexion.Insertar(sql);
                
                String sql1 = "Update vinculacionin26_internacional set conv_acum = ".concat(datos[7])
                        .concat(", conv_acum_sup = ").concat(datos[8])
                        .concat(", publicos = ").concat(datos[9])
                        .concat(", privados = ").concat(datos[10])
                        .concat(", sociales = ").concat(datos[11])
                        .concat(", alumnos = ").concat(datos[12])
                        .concat(", docentes = ").concat(datos[13])
                        .concat(" where id_universidad = ").concat(idUniversidad)
                        .concat(" and id_periodo = ").concat(idPeriodo);
                if (conexion.Modificar(sql1) == 0) {
                    sql1 = "Insert into vinculacionin26_internacional values";
                    sql1 = sql1.concat("(").concat(idUniversidad).concat(",") //uni
                            .concat(idPeriodo).concat(",") //periodo
                            .concat(datos[7]).concat(",")
                            .concat(datos[8]).concat(",")
                            .concat(datos[9]).concat(",")
                            .concat(datos[10]).concat(",")
                            .concat(datos[11]).concat(",")
                            .concat(datos[13]).concat(",")
                            .concat(datos[12])//Valores
                            .concat(")");
                }
                conexion.Insertar(sql1);
            } catch (SQLException ex) {
                System.err.println("Error al guardar en VinculacionIn26Action. Error: " + ex.getMessage());
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
