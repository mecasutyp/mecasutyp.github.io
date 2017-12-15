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
@WebServlet(name = "PertinenciaIn19Datos", urlPatterns = {"/PertinenciaIn19Datos"})
public class PertinenciaIn19Datos extends HttpServlet {

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
            String[] values1;
            String[] datos;//Valores de cada insert
            
            values = valores.split("x");
            values1 = valores.split("z");
            ConexionMySQL conexion = new ConexionMySQL();
            String sql1 = "",sql2 = "";
            try {
                for (int i = 1; i < values.length - 1; i++) {
                    datos = values[i].split(",");
                    String sql0 = "Update pertinenciain19 set matricula_inicial_aten = ".concat(datos[1]).concat(", matricula_acreditada = ").concat(datos[2]).concat(", acreditados = ").concat(datos[3]).concat(", vigencia_acreditacion = ").concat(datos[4]).concat(", id_organismo = ").concat(datos[6]).concat(", fecha_acreditacion = ").concat(datos[5]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and id_pe = ").concat(datos[0]);
                    conexion.Modificar(sql0);
                    if (conexion.Modificar(sql0) == 0) {
                         sql1 = "Insert into pertinenciain19 values";
                        for (int j = 1; j < values.length - 1; j++) {
                            datos = values[j].split(",");
                            if (j == values.length - 2) {
                                sql1 = sql1.concat("(".concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(")")));
                            } else {
                                sql1 = sql1.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",".concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat("),")));
                            }
                        }
                        conexion.Insertar(sql1);
                    }
                }
                for (int i = 1; i < values1.length; i++) {
                    datos = values1[i].split(",");
                    String sql = "Update pertinenciain19_1 set matricula_inicial_aten = ".concat(datos[1]).concat(", matricula_acreditada = ").concat(datos[2]).concat(", acreditados = ").concat(datos[3]).concat(", vigencia_acreditacion = ").concat(datos[4]).concat(", id_organismo = ").concat(datos[6]).concat(", fecha_acreditacion = ").concat(datos[5]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and id_pe = ").concat(datos[0]);
                    conexion.Modificar(sql);
                    if (conexion.Modificar(sql) == 0) {
                        sql2 = "Insert into pertinenciain19_1 values";
                        for (int j = 1; j < values1.length; j++) {
                            datos = values1[j].split(",");
                            if (j == values1.length - 1) {
                                sql2 = sql2.concat("(".concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat(")")));
                            } else {
                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",".concat(datos[0]).concat(",").concat(datos[1]).concat(",").concat(datos[2]).concat(",").concat(datos[3]).concat(",").concat(datos[6]).concat(",").concat(datos[4]).concat(",").concat(datos[5]).concat("),")));
                            }
                        }
                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado pertinencia 19: " + ex);
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
