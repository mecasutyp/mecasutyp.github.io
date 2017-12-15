/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danny
 */
@WebServlet(name = "LeerMensajes", urlPatterns = {"/LeerMensajes"})
public class LeerMensajes extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String mensaje = "";
        String consulta = "";


        String id_mensaje = request.getParameter("id_mensaje").toString();
        System.err.println("id_mensaje " + request.getParameter("id_mensaje").toString());
        String accion = request.getParameter("ac").toString();
        System.err.println("accion " + request.getParameter("ac").toString());
        String datos = request.getParameter("mss").toString();
        System.err.println("datos " + request.getParameter("mss").toString());
        String tipo = request.getParameter("tipo").toString();
        System.err.println("tipo " + request.getParameter("tipo").toString());
        String asunto = request.getParameter("asunto").toString();
        
        

        ConexionMySQL conexion = new ConexionMySQL();
        ResultSet rs = null;

        try {
            if (accion.equals("leer")) {
               consulta = "select * from mensajeria where id_mensaje=".concat(id_mensaje)+" and status != 'Eliminado'";
                rs = conexion.Consultar(consulta);
                if (rs.next()) {
                    mensaje = rs.getString("mensaje");
                    consulta = "UPDATE mensajeria SET estatus='Leido' WHERE  id_mensaje=" + id_mensaje;
                    conexion.Modificar(consulta);
                }
                out.println(mensaje);
            }
            if (accion.equals("leido")) {
                consulta = "UPDATE mensajeria SET estatus='Leido' WHERE id_mensaje=".concat(id_mensaje);
                conexion.Modificar(consulta);
                out.println(mensaje);
            }
            if (accion.equals("responder")) {
                if (tipo.equals("CGUTyP")) {
                    consulta = "select id_remitente, id_destinatario from mensajeria where id_mensaje=" + id_mensaje;

                    rs = conexion.Consultar(consulta);
                    if (rs.next()) {
                        consulta = "insert into mensajeria (id_remitente,id_destinatario,tipo,mensaje,fecha,estatus,asunto)"
                                + " values(" + rs.getString("id_destinatario") + "," + rs.getString("id_remitente") + ",'CGUT','" + datos + "',now(),'No Leido','"+asunto+"')";

                        conexion.Insertar(consulta);
                        consulta = "UPDATE mensajeria SET estatus='Atendido' WHERE  id_mensaje=" + id_mensaje;
                        conexion.Modificar(consulta);
                    }
                    out.println("ok");
                }
                if (tipo.equals("UT")) {
                    consulta = "select id_remitente, id_destinatario from mensajeria where id_mensaje=" + id_mensaje;
                    rs = conexion.Consultar(consulta);
                    if (rs.next()) {
                        consulta = "insert into mensajeria (id_remitente,id_destinatario,tipo,mensaje,fecha,estatus,asunto)"
                                + " values(" + rs.getString("id_destinatario") + ",0,'UT','" + datos + "',now(),'No Leido','"+asunto+"')";
                        conexion.Insertar(consulta);
                        consulta = "UPDATE mensajeria SET estatus='Atendido' WHERE  id_mensaje=" + id_mensaje;
                        conexion.Modificar(consulta);
                    }
                    out.println("ok");
                }
            }
            conexion.Desconectar();
        } catch (Exception ex) {
            out.println("error");
            System.err.println("ERRORBD: Consulta Mensajes Servlet" + ex);
            out.close();
        }
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
