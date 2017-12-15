<%-- 
    Document   : Buzon
    Created on : Dec 9, 2012, 11:02:53 AM
    Author     : josh
    Modifico    : Salvador Zamora 23/11/2016
    
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  ConexionMySQL conexion = new ConexionMySQL();
    HttpSession sesion = request.getSession();
    int noMensajes = 0;
    if (sesion.getAttribute("usuario") == null) {
%>
        <script type="text/javascript" language="javascript">
            document.location.href = "/";
        </script>
<%  } else {
        try {
            if (sesion.getAttribute("tipoUsuario").toString().equals("UT")) {
                conexion.Conectar();
                CallableStatement sp = conexion.PrepararSP("sp_NoMensajesUsuario(?, ?, ?)");
                sp.setInt(1, -1);
                sp.setInt(2, Integer.parseInt(sesion.getAttribute("idResponsable").toString()));
                sp.registerOutParameter(3, java.sql.Types.INTEGER);
                sp.execute();
                noMensajes = sp.getInt(3);
            } else if (sesion.getAttribute("tipoUsuario").toString().equals("CG")) {
                conexion.Conectar();
                CallableStatement sp = conexion.PrepararSP("sp_NoMensajesUsuario(?, ?, ?)");
                sp.registerOutParameter(3, java.sql.Types.INTEGER);
                sp.setInt(1, 1);
                sp.setInt(2, 0);
                sp.execute();
                noMensajes = sp.getInt(3);
            } else if (sesion.getAttribute("tipoUsuario").toString().equals("AD")) {
                conexion.Conectar();
                CallableStatement sp = conexion.PrepararSP("sp_NoMensajesUsuario(?, ?, ?)");
                sp.registerOutParameter(3, java.sql.Types.INTEGER);
                sp.setInt(1, 0);
                sp.setInt(2, 0);
                sp.execute();
                noMensajes = sp.getInt(3);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <a href="global.msut?ask=mensajes">
            <%  if (noMensajes > 0) {   %>
                <span class="ui-icon ui-icon-mail-closed" style="float:right;"></span><span style="background: #FF3333; border-color: #cd0a0a; color: #ffffff; height: 16px; float: right;"><%=noMensajes%></span>
            <%  } else {    %>
                <span class="ui-icon ui-icon-mail-open" style="float:right;"></span>
            <%  }   %>
        </a>
    </body>
</html>