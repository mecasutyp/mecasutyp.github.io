/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.eficiencia;

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
@WebServlet(name = "EficienciaIn15Datos", urlPatterns = {"/EficienciaIn15Datos"})
public class EficienciaIn15Datos extends HttpServlet {

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
        }catch(Exception e){
            out.println("Error en catch dopost");
            System.err.println("Error 15 Datos "+e.getMessage());
        }finally {
            out.close();
        }
    }

    private boolean Guardar(String valores, String Id_Universidad, String Id_Periodo) {
        if (valores.equals("Error")) {
            System.err.println("Error valores ");
            return false;
        } else {
            String[] values;//Valores de todo el formulario
            String[] datos;//Valores de cada insert
            String[] values2;
            values = valores.split("z");
            values2 = valores.split("x");
            ConexionMySQL conexion = new ConexionMySQL();

            try {
                for (int i = 1; i < values.length - 1; i++) {
                    datos = values[i].split(",");
                    String sql = "Update eficienciain15 set sin_certificado = ".concat(datos[1]).concat(", en_proceso = ").concat(datos[2]).concat(", con_certificado = ").concat(datos[3]).concat(", en_proceso_recertificacion = ").concat(datos[4]).concat(", con_recertificado = ").concat(datos[5]).concat(" where id_certificacion = ").concat(datos[0]).concat(" and id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into eficienciain15 values";
                        for (int j = 1; j < values.length - 1; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 2) {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(")"));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat("),"));
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                }
                for (int i = 1; i < values2.length; i++) {

                    datos = values2[i].split(",");
                    String sql = "Update eficienciain15_1 set opcion = ".concat(datos[1]).concat(", periodo_inicio = ").concat(datos[2]).concat(", periodo_fin = ").concat(datos[3]).concat(", proxima_auditoria = ").concat(datos[4]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and num_fila = ").concat(datos[0]);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into eficienciain15_1 values";
                        for (int j = 1; j < values2.length; j++) {
                            datos = values2[j].split(",");
                            if (j == values2.length - 1) {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat(")"));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[4]).concat("),"));
                            }
                        }

                        conexion.Insertar(sql2);
                    }
                }
            }catch (SQLException ex) {
                System.err.println("Error de guardado eficiencia 15: " + ex);
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
