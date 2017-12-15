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
@WebServlet(name = "EficaciaIn4Datos", urlPatterns = {"/EficaciaIn4Datos"})
public class EficaciaIn4Datos extends HttpServlet {

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
            System.out.println("valores "+valores);
            String [] values; 
            String [] values2; 
            String [] causas; 
            String [] datosxcausa; 
            String [] pes; 
            String [] datosxpe; 
            values = valores.split("_");//POR NIVEL
            ConexionMySQL conexion = new ConexionMySQL();
            
            
        try {
            for (int i = 0; i < values.length; i++) {//POR NIVEL
                values2 = values[i].split("&");
                causas = values2[0].split("-");
                pes = values2[1].split("-");
                
                for (int z = 0; z < causas.length; z++) {//POR NIVEL
                    datosxcausa = causas[z].split(",");
                    String sql = "UPDATE eficaciain4 SET sep = "
                            .concat(datosxcausa[2])
                            .concat(", ene = ").concat(datosxcausa[3])
                            .concat(", may = ").concat(datosxcausa[4])
                            .concat(" WHERE id_universidad = ").concat(idUniversidad)
                            .concat(" AND id_periodo = ").concat(idPeriodo)
                            .concat(" AND id_nivel = ").concat(datosxcausa[0])
                            .concat(" AND id_causa = ").concat(datosxcausa[1]);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO eficaciain4 VALUES ";
                            sql2+="("+idUniversidad+", "+idPeriodo+", "+datosxcausa[0]+", "+datosxcausa[1]+", "
                                    + ""+datosxcausa[2]+","+datosxcausa[3]+","+datosxcausa[4]+"  )";
                        conexion.Insertar(sql2);
                    }
                }
                
                for (int z = 0; z < pes.length; z++) {//POR NIVEL
                    datosxpe = pes[z].split(",");
                     String sql = "UPDATE eficaciain4_1 SET "
                            .concat( "tot_deser_sep= ").concat(datosxpe[2])
                            .concat(", tot_deser_ene = ").concat(datosxpe[3])
                            .concat(", tot_deser_may = ").concat(datosxpe[4])
                            .concat(" WHERE id_universidad = ").concat(idUniversidad)
                            .concat(" AND id_periodo = ").concat(idPeriodo)
                            .concat(" AND id_nivel = ").concat(datosxpe[0])
                            .concat(" AND id_pe = ").concat(datosxpe[1]);
                    if(conexion.Modificar(sql) == 0){
                        String sql2="INSERT INTO eficaciain4_1 VALUES("+idUniversidad+", "+idPeriodo+", "
                                + " "+datosxpe[0]+", "+datosxpe[2]+", "+datosxpe[3]+", "+datosxpe[4]+","
                                + ""+datosxpe[1]+" )";
                        conexion.Insertar(sql2);
                    }
                }
                
                System.out.println("Cambio de nivel -----------------------------------");
            }
        } catch (Exception ex) {
            System.err.println("ERROR Eficaciain4Datos " +ex);
            return false;
        } finally {
            conexion.Desconectar();
        }
            
//            String[] values;//Valores de todo el formulario
//            String[] datos;//Valores de cada insert
//            String[] datos2;
//            String[] values2;
//            values = valores.split("-");
//            values2 = valores.split("x");
//            ConexionMySQL conexion = new ConexionMySQL();
//            try {
//                for (int i = 1; i < values.length - 1; i++) {
//                    datos = values[i].split(",");
//                    String sql = "Update eficaciain4 set sep = "
//                            .concat(datos[2])
//                            .concat(", ene = ").concat(datos[3])
//                            .concat(", may = ").concat(datos[4])
//                            .concat(" where id_universidad = ").concat(idUniversidad)
//                            .concat(" and id_periodo = ").concat(idPeriodo)
//                            .concat(" and id_nivel = ").concat(datos[0])
//                            .concat(" and id_causa = ").concat(datos[1]);
//                    if (conexion.Modificar(sql) == 0) {
//                        String sql2 = "Insert into eficaciain4 values";
//                        for (int j = 1; j < values.length - 1; j++) {
//                            if (j == values.length - 2) {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat(")");
//                            } else {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat("),");
//                            }
//                        }
//                        conexion.Insertar(sql2);
//                    }
//                }
//                for (int i = 1; i < values2.length; i++) {
//                    datos2 = values2[i].split(",");
//                    String sql = "Update eficaciain4_1 set tot_deser_sep= "
//                            .concat(datos2[1])
//                            .concat(", tot_deser_ene = ").concat(datos2[2])
//                            .concat(", tot_deser_may = ").concat(datos2[3])
//                            .concat(" where id_universidad = ").concat(idUniversidad)
//                            .concat(" and id_periodo = ").concat(idPeriodo)
//                            .concat(" and id_nivel = ").concat(datos2[0]);
//                    if (conexion.Modificar(sql) == 0) {
//                        String sql2 = "Insert into eficaciain4_1 values";
//                        for (int j = 1; j < values2.length; j++) {
//                            if (j == values2.length - 1) {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values2[j]).concat(")");
//                            } else {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values2[j]).concat("),");
//                            }
//                        }
//                        conexion.Insertar(sql2);
//                    }
//                }
//            } catch (SQLException ex) {
//                System.err.println("ERROR Eficaciain4Datos " +ex);
//                return false;
//            } finally {
//                conexion.Desconectar();
//            }
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
