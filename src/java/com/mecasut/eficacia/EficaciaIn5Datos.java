/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.eficacia;

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
@WebServlet(name = "EficaciaIn5Datos", urlPatterns = {"/EficaciaIn5Datos"})
public class EficaciaIn5Datos extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();
        String datos = request.getParameter("inf");
        try {
            if (datos != null) {
                if (Guardar(datos, idUniversidad, idPeriodo) == true) {
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
        String[] values = valores.split("-");//Valores de todo el formulario
        String[] values1 = valores.split("x");//Valores de todo el formulario
        String[] datos;//Valores de cada insert
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            for (int i = 1; i < values.length; i++) {
                datos = values[i].split(",");
                 String sql = "Update eficaciain5 set ingreso = ".concat(datos[4])
                        .concat(", egresados = ").concat(datos[5])
                        .concat(", titulados = ").concat(datos[6])
                         .concat(", cuatri = ").concat(datos[7])
                        .concat(" where id_universidad = ").concat(idUniversidad)
                        .concat(" and id_periodo = ").concat(idPeriodo)
                        .concat(" and id_nivel = ").concat(datos[0])
                        .concat(" and id_pe = ").concat(datos[1])
                        .concat(" and id_modelo = ").concat(datos[2])
                        .concat(" and anio = ").concat(datos[3]);
                if (conexion.Modificar(sql) == 0) {
                    sql = "Insert into eficaciain5 values";
                    for (int j = 1; j < values.length; j++) {
                        if (j == values.length - 1) {
                            sql = sql.concat("(").concat(idUniversidad).concat(",") //uni
                                    .concat(idPeriodo).concat(",") //periodo
                                    .concat(values[j]) //Valores
                                    .concat(")");
                        } else {
                            sql = sql.concat("(").concat(idUniversidad).concat(",") //uni
                                    .concat(idPeriodo).concat(",") //periodo
                                    .concat(values[j]) //Valores
                                    .concat("),");
                        }
                    }
                    conexion.Insertar(sql);
                    break;
                }
            }
            for (int i = 1; i < values1.length; i++) {
                datos = values1[i].split(",");
                String sql = "Update eficaciain5_1 set titulados_universidad = ".concat(datos[2])
                        .concat(", titulados_dgp = ").concat(datos[3])
                        .concat(" where id_universidad = ").concat(idUniversidad)
                        .concat(" and id_periodo = ").concat(idPeriodo)
                        .concat(" and id_nivel = ").concat(datos[0])
                        .concat(" and anio = ").concat(datos[1]);
                if (conexion.Modificar(sql) == 0) {
                    sql = "Insert into eficaciain5_1 values";
//                    for (int j = 1; j < values1.length; j++) {
//                        if (j == values1.length - 1) {
                            sql = sql.concat("(").concat(idUniversidad).concat(",") //uni
                                    .concat(idPeriodo).concat(",") //periodo
                                    .concat(datos[0]).concat(",") //Valores
                                    .concat(datos[1]).concat(",") //Valores
                                    .concat(datos[2]).concat(",") //Valores
                                    .concat(datos[3]) //Valores
                                    .concat(")");
//                        } else {
//                            sql = sql.concat("(").concat(idUniversidad).concat(",") //uni
//                                    .concat(idPeriodo).concat(",") //periodo
//                                    .concat(values1[j]) //Valores
//                                    .concat("),");
//                        }
//                    }
                    conexion.Insertar(sql);
                   // break;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al guardar en EficaciaIn5Datos. Error: " + ex.getMessage());
            return false;
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
