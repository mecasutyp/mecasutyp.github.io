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
@WebServlet(name = "PertinenciaIn20Datos", urlPatterns = {"/PertinenciaIn20Datos"})
public class PertinenciaIn20Datos extends HttpServlet {

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
                    String sql = "Update pertinenciain20 set cursos_realizados_i = ".concat(datos[0])
                            .concat(", cursos_realizados_e = ").concat(datos[1])
                            .concat(", talleres_realizados_i = ").concat(datos[2])
                            .concat(", talleres_realizados_e = ").concat(datos[3])
                            .concat(", acciones_otro = ").concat(datos[4])
                            .concat(", ptc_cursos_i = ").concat(datos[5])
                            .concat(", ptc_cursos_e = ").concat(datos[6])
                            .concat(", ptc_talleres_i = ").concat(datos[7])
                            .concat(", ptc_talleres_e = ").concat(datos[8])
                            .concat(", ptc_otros = ").concat(datos[9])
                            .concat(", pa_cursos_i = ").concat(datos[10])
                            .concat(", pa_cursos_e = ").concat(datos[11])
                            .concat(", pa_talleres_i = ").concat(datos[12])
                            .concat(", pa_talleres_e = ").concat(datos[13])
                            .concat(", pa_otros = ").concat(datos[14])
                            .concat(", material_difusion = ").concat(datos[15])
                            .concat(", material_capacitacion = ").concat(datos[16])
                            .concat(", material_otro = ").concat(datos[17])
                            .concat(", material_ptc = '").concat(datos[18])
                            .concat("', material_d_pa = '").concat(datos[19])
                            .concat("', material_d_alumnos = '").concat(datos[20])
                            .concat("', material_d_otro = '").concat(datos[21])
                            .concat("', material_especifique = '").concat(datos[22])
                            .concat("', ptc_terminaron = ").concat(datos[23])
                            .concat(", ptc_proceso  = ").concat(datos[24])
                            .concat(", ptc_no_tienen = ").concat(datos[25])
                            .concat(", ptc_aplicando = ").concat(datos[26])
                            .concat(", pa_terminaron = ").concat(datos[27])
                            .concat(", pa_proceso = ").concat(datos[28])
                            .concat(", pa_no_tienen = ").concat(datos[29])
                            .concat(", pa_aplicando = ").concat(datos[30])
                            .concat(" where id_universidad = ").concat(Id_Universidad)
                            .concat(" and id_periodo = ").concat(Id_Periodo);



                    conexion.Modificar(sql);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into pertinenciain20 values";
                        for (int j = 1; j < values.length; j++) {
                            datos = values[j].split(",");
                            sql2 = sql2.concat("(").concat(Id_Universidad)
                                    .concat(",".concat(Id_Periodo)
                                    .concat(",").concat(datos[0])
                                    .concat(",").concat(datos[1])
                                    .concat(",").concat(datos[2])
                                    .concat(",").concat(datos[3])
                                    .concat(",").concat(datos[4])
                                    .concat(",").concat(datos[5])
                                    .concat(",").concat(datos[6])
                                    .concat(",").concat(datos[7])
                                    .concat(",").concat(datos[8])
                                    .concat(",").concat(datos[9])
                                    .concat(",").concat(datos[10])
                                    .concat(",").concat(datos[11])
                                    .concat(",").concat(datos[12])
                                    .concat(",").concat(datos[13])
                                    .concat(",").concat(datos[14])
                                    .concat(",").concat(datos[15])
                                    .concat(",").concat(datos[16])
                                    .concat(",").concat(datos[17])
                                    .concat(",").concat(datos[18])
                                    .concat(",").concat(datos[19])
                                    .concat(",").concat(datos[20])
                                    .concat(",").concat(datos[21])
                                    .concat(",'").concat(datos[22])
                                    .concat("',").concat(datos[23])
                                    .concat(",").concat(datos[24])
                                    .concat(",").concat(datos[25])
                                    .concat(",").concat(datos[25])
                                    .concat(",").concat(datos[27])
                                    .concat(",").concat(datos[28])
                                    .concat(",").concat(datos[29])
                                    .concat(",").concat(datos[30])
                                    .concat(")"));
                        }
                        conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado pertinencia 20: " + ex);
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
