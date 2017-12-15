/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.equidad;

import com.mecasut.conexion.ConexionMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "EquidadIn35Datos", urlPatterns = {"/EquidadIn35Datos"})
public class EquidadIn35Datos extends HttpServlet {

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
            String[] values;//Valores de todo el formulario
            values = valores.split("-");
            String[] datos;//Valores de cada insert
            ConexionMySQL conexion = new ConexionMySQL();

            for (int i = 1; i < values.length; i++) {
                try {
                    datos = values[i].split(",");
                    String sql = "UPDATE equidadin35 SET  dep_realizados=".concat(datos[0])
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
