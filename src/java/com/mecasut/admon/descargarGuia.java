/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DIN-A-C14-02
 */
@WebServlet(name = "descargarGuia", urlPatterns = {"/descargarGuia"})
public class descargarGuia extends HttpServlet {

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
        response.setContentType("application/pdf");
        ServletContext ctx = getServletContext();
        InputStream is = ctx.getResourceAsStream("/files/guiaGeneralNueva.pdf");
        int read = 0;
        byte[] bytes = new byte[2048];
        OutputStream os = response.getOutputStream();
        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
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
        
        response.setContentType("application/pdf");

        ServletContext ctx = getServletContext();
        InputStream is = ctx.getResourceAsStream("/files/guiaGeneralNueva.pdf");

        System.out.println("descargarGuia "+is.toString());
        int read = 0;

        byte[] bytes = new byte[1024];
        OutputStream os = response.getOutputStream();

        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        System.out.println("descargarGuia read "+read);
        os.flush();
        os.close();
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
        
        response.setContentType("application/pdf");

        ServletContext ctx = getServletContext();
        InputStream is = ctx.getResourceAsStream("/files/guiaGeneralNueva.pdf");

        System.out.println("descargar Guia 2"+is.toString());
        int read = 0;

        byte[] bytes = new byte[2048];
        OutputStream os = response.getOutputStream();

        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        System.out.println("descargar Guia read 2"+read);
        os.flush();
        os.close();
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