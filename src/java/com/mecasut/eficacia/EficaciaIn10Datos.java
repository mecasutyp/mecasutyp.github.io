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
@WebServlet(name = "EficaciaIn10Datos", urlPatterns = {"/EficaciaIn10Datos"})
public class EficaciaIn10Datos extends HttpServlet {

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
                //Datos vacios, aunque es impo sible xD
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
    private boolean Guardar(String valores, String idUniversidad, String Id_Periodo) {
        if (valores.equals("Error")) {
            return false;
        } else {
            String[] values;//Valores de todo el formulario
            values = valores.split("-");
            String[] datos;//Valores de cada insert
            ConexionMySQL conexion = new ConexionMySQL();

            try {
                for (int i = 1; i < values.length; i++) {
                    datos = values[i].split(",");
                    if (values[i].split(",")[0].equals("NOAPLICA")) {
                        String query = "UPDATE indicadores_aplica SET aplica='0' WHERE id_universidad="
                                .concat(idUniversidad)
                                .concat(" AND indicador='10' AND id_periodo=").concat(Id_Periodo)
                                .concat(" AND id_nivel=").concat(values[i].split(",")[1]);
                        if (conexion.Modificar(query) == 0) {
                            query = "INSERT INTO indicadores_aplica(id_universidad,id_periodo,id_nivel,indicador,aplica) VALUES("
                                    .concat(idUniversidad).concat(", ").concat(Id_Periodo).concat(", ").concat(values[i].split(",")[1]).concat(",'10','0')");
                            conexion.Insertar(query);
                        } else {
                            //eliminar los datos de la tabla eficaciain7_10
                            query = "CALL sp_delete_datos_7_10('" + idUniversidad + "', '" + Id_Periodo + "', '" + values[i].split(",")[1] + "', '10')";
                            conexion.Eliminar(query);
                        }
                    } else {
                        String query = "UPDATE indicadores_aplica SET aplica='1' WHERE id_universidad="
                                .concat(idUniversidad)
                                .concat(" and indicador='10' AND id_periodo=").concat(Id_Periodo)
                                .concat(" AND id_nivel=").concat(datos[8]);
                        if (conexion.Modificar(query) == 0) {
                            query = "INSERT INTO indicadores_aplica (id_universidad,id_periodo,id_nivel,indicador,aplica) VALUES("
                                    .concat(idUniversidad).concat(", ").concat(Id_Periodo)
                                    .concat(", '" + datos[8] + "'").concat(", '10','1')");
                            conexion.Insertar(query);
                        }
                        String sql = "call insert_eficaciain7_10 ".concat("(").concat(idUniversidad).concat(",") //uni
                                .concat(Id_Periodo).concat(",") //periodo                                 
                                .concat(datos[0]).concat(",") // id_pregunta
                                .concat(datos[1]).concat(",") //r_a
                                .concat(datos[2]).concat(",") //r_b
                                .concat(datos[3]).concat(",") //r_c
                                .concat(datos[4]).concat(",") //r_d
                                .concat(datos[5]).concat(",") //r_e
                                .concat(datos[6]).concat(",") //r_f
                                .concat(datos[7]) //r_g
                                .concat(")");
                        conexion.Insertar(sql);
                    }
                }
            } catch (SQLException ex) {
                System.err.println("error de guardado eficacia 10: " + ex);
                return false;
            }
            return true;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
