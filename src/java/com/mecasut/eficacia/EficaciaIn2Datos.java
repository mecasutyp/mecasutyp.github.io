/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.eficacia;

import com.mecasut.conexion.ConexionMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
@WebServlet(name = "EficaciaIn2Datos", urlPatterns = {"/EficaciaIn2Datos"})
public class EficaciaIn2Datos extends HttpServlet {

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
                    ResultSet rs = null;
                    rs = conexion.Consultar("select *from  eficaciain2 where id_universidad=" + Id_Universidad + " and id_periodo=" + Id_Periodo);
                    while (rs.next()) {

                    
                    }
                    datos = values[i].split(",");
                    String sql = "Update eficaciain2 set sep = ".concat(datos[1]).concat(", ene = ").concat(datos[2]).concat(", may = ").concat(datos[3]).concat(" where id_universidad = ").concat(Id_Universidad).concat(" and id_periodo = ").concat(Id_Periodo).concat(" and id_pe = ").concat(datos[0]);
                    conexion.Modificar(sql);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "Insert into eficaciain2 values";
                        for (int j = 1; j < values.length; j++) {
                            if (j == values.length - 1) {

                                sql2 = sql2.concat("(".concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",").concat(values[j]).concat(")")));
                            } else {

                                sql2 = sql2.concat("(").concat(Id_Universidad).concat(",".concat(Id_Periodo).concat(",".concat(values[j]).concat("),")));

                            }
                        }
                    conexion.Insertar(sql2);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("Error de guardado eficacia 2: " + ex + "\"");
                return false;
            }
            return true;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void consultar(String cadena, String periodo, String universidad, ConexionMySQL conexion) throws SQLException {
        ResultSet rs = null;

        rs = conexion.Consultar("select *from  eficaciain2 where id_universidad=" + universidad + " and id_periodo=" + periodo);

        while (rs.next()) {
        }






    }
}
