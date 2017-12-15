/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

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
 *
 * @author DIN-A-C14-02
 */
@WebServlet(name = "progresoUniversidad", urlPatterns = {"/progresoUniversidad"})
public class progresoUniversidad extends HttpServlet {

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
        ConexionMySQL con = new ConexionMySQL();
        try {
            con.Conectar();
            HttpSession sesion = request.getSession(false);
            String idUniversidad = sesion.getAttribute("idUniversidad").toString();
            String id_periodo = sesion.getAttribute("idPeriodo").toString();
            String idUniv = request.getParameter("idUniverisdad");
            String ask = request.getParameter("ask");
            ResultSet resultado;
            String faltantes = "";
            if (ask.equals("consultarProgreso")) {
                String consulta2 = "call sp_ProgresoUniversidades('".concat(idUniversidad).concat("','")
                        .concat(id_periodo).concat("')");
                resultado = con.Consultar(consulta2);
                resultado.next();
                if (resultado.getInt("ind1") == 0) {
                    faltantes = faltantes.concat("Indicador 1.1").concat(",");
                }
                if (resultado.getInt("ind2") == 0) {
                    faltantes = faltantes.concat("Indicador 1.2").concat(",");
                }
                if (resultado.getInt("ind3") == 0) {
                    faltantes = faltantes.concat("Indicador 1.3").concat(",");
                }
                if (resultado.getInt("ind4") == 0) {
                    faltantes = faltantes.concat("Indicador 1.4").concat(",");
                }
                if (resultado.getInt("ind5") == 0) {
                    faltantes = faltantes.concat("Indicador 1.5").concat(",");
                }
                if (resultado.getInt("ind6") == 0) {
                    faltantes = faltantes.concat("Indicador 1.6").concat(",");
                }
                if (resultado.getInt("ind7") == 0) {
                    faltantes = faltantes.concat("Indicador 1.7").concat(",");
                }
                if (resultado.getInt("ind8") == 0) {
                    faltantes = faltantes.concat("Indicador 1.8").concat(",");
                }
                if (resultado.getInt("ind9") == 0) {
                    faltantes = faltantes.concat("Indicador 1.9").concat(",");
                }
                if (resultado.getInt("ind10") == 0) {
                    faltantes = faltantes.concat("Indicador 1.10").concat(",");
                }
                if (resultado.getInt("ind11") == 0) {
                    faltantes = faltantes.concat("Indicador 1.11").concat(",");
                }
                if (resultado.getInt("ind13") == 0) {
                    faltantes = faltantes.concat("Indicador 2.2").concat(",");
                }
                if (resultado.getInt("ind14") == 0) {
                    faltantes = faltantes.concat("Indicador 2.3").concat(",");
                }
                if (resultado.getInt("ind15") == 0) {
                    faltantes = faltantes.concat("Indicador 2.4").concat(",");
                }
                if (resultado.getInt("ind16") == 0) {
                    faltantes = faltantes.concat("Indicador 2.5").concat(",");
                }
                if (resultado.getInt("ind18") == 0) {
                    faltantes = faltantes.concat("Indicador 3.1").concat(",");
                }
                if (resultado.getInt("ind19") == 0) {
                    faltantes = faltantes.concat("Indicador 3.2").concat(",");
                }
                if (resultado.getInt("ind20") == 0) {
                    faltantes = faltantes.concat("Indicador 3.3").concat(",");
                }
                if (resultado.getInt("ind21") == 0) {
                    faltantes = faltantes.concat("Indicador 3.4").concat(",");
                }
                if (resultado.getInt("ind22") == 0) {
                    faltantes = faltantes.concat("Indicador 3.5").concat(",");
                }
                if (resultado.getInt("ind23") == 0) {
                    faltantes = faltantes.concat("Indicador 3.6").concat(",");
                }
                if (resultado.getInt("ind24") == 0) {
                    faltantes = faltantes.concat("Indicador 3.7").concat(",");
                }
                if (resultado.getInt("ind25") == 0) {
                    faltantes = faltantes.concat("Indicador 3.8").concat(",");
                }
                if (resultado.getInt("ind26") == 0) {
                    faltantes = faltantes.concat("Indicador 4.1").concat(",");
                }
                if (resultado.getInt("ind27") == 0) {
                    faltantes = faltantes.concat("Indicador 4.2").concat(",");
                }
                if (resultado.getInt("ind28") == 0) {
                    faltantes = faltantes.concat("Indicador 4.3").concat(",");
                }
                if (resultado.getInt("ind29") == 0) {
                    faltantes = faltantes.concat("Indicador 4.4").concat(",");
                }
                if (resultado.getInt("ind30") == 0) {
                    faltantes = faltantes.concat("Indicador 4.5").concat(",");
                }
                if (resultado.getInt("ind31") == 0) {
                    faltantes = faltantes.concat("Indicador 4.6").concat(",");
                }
                if (resultado.getInt("ind32") == 0) {
                    faltantes = faltantes.concat("Indicador 4.7").concat(",");
                }
                if (resultado.getInt("ind34") == 0) {
                    faltantes = faltantes.concat("Indicador 5.2").concat(",");
                }
                if (resultado.getInt("ind35") == 0) {
                    faltantes = faltantes.concat("Indicador 5.3").concat(",");
                }
                if (resultado.getInt("ind36") == 0) {
                    faltantes = faltantes.concat("Indicador 5.4").concat(",");
                }
                if (faltantes.length() > 0) {
                    faltantes = faltantes.substring(0, faltantes.length() - 1);
                } else {
                    faltantes = "completos";
                }
                out.print(faltantes);
            } else {
                System.err.println("ERROR progreso");
            }
        } catch (SQLException x) {
            System.err.println("Error consultado el progreso de las Universidades" + x);
        } finally {
            con.Desconectar();
            out.close();
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
