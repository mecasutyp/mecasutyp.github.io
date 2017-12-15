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
 * @author Daniel Ramirez Torres
 */
@WebServlet(name = "VinculacionIn28Datos", urlPatterns = {"/VinculacionIn28Datos"})
public class VinculacionIn28Datos extends HttpServlet {

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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
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
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into vinculacionin28 values";
                        for (int j = 1; j < values.length; j++) {
                            if (j == values.length - 1) {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat(")");
                            } else {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat("),");
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                return false;
            } finally {
                conexion.Desconectar();
            }
            return true;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}