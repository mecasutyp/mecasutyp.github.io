/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.eficacia;

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
 * Actualización 2016: Salvador Zamora Arias
 */
@WebServlet(name = "EficaciaIn1Datos", urlPatterns = {"/EficaciaIn1Datos"})
public class EficaciaIn1Datos extends HttpServlet {
 //private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EficaciaIn1Datos.class);
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
                    
                    String sql = "UPDATE eficaciain1 SET egresados_bachillerato=".concat(datos[0])
                            .concat(", nuevo_ingreso=").concat(datos[1])
                            .concat(", c1=").concat(datos[2])
                            .concat(", c2=").concat(datos[3])
                            .concat(", c3=").concat(datos[4])
                            .concat(", aplica=").concat(datos[5])
                            .concat(", comentario='").concat(datos[6]).concat("'")
                            .concat(" WHERE id_universidad=").concat(idUniversidad)
                            .concat(" AND id_periodo=").concat(idPeriodo);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO eficaciain1 VALUES";
                        for (int j = 1; j < values.length; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 1) {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",")
                                        .concat(idPeriodo).concat(",")
                                        .concat(datos[0]).concat(",")
                                        .concat(datos[1]).concat(",")
                                        .concat(datos[2]).concat(",")
                                        .concat(datos[3]).concat(",")
                                        .concat(datos[4]).concat(",")
                                        .concat(datos[5]).concat(",'")
                                        .concat(datos[6]).concat("')");
                            } else {
                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",")
                                        .concat(idPeriodo).concat(",")
                                        .concat(datos[0]).concat(",")
                                        .concat(datos[1]).concat(",")
                                        .concat(datos[2]).concat(",")
                                        .concat(datos[3]).concat(",")
                                        .concat(datos[4]).concat(",")
                                        .concat(datos[5]).concat(",'")
                                        .concat(datos[6]).concat("'),");
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EficaciaIn1Action.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("SQLException Eficaciain1Datos "+ex.getMessage());
                    // log.error("SQLException: "+ex);
                 } finally {
                    conexion.Desconectar();
                }

            }
            return true;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
