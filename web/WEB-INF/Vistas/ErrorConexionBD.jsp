<%-- 
    Document   : ErrorConexionBD
    Created on : 27/06/2012, 10:17:41 AM
    Author     : Joshua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.ResultSet, com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insert template="/Vistas/Master.jsp">
    <tiles:put name="title" type="string">
        MECASUT - Error de conex&oacute;n con la base de datos
    </tiles:put>
    <tiles:put name="scripts" type="string">
    </tiles:put>
    <tiles:put name="body" type="string">
        <br/>
        <h1 style="text-align: center;">Ha ocurrido un error mientras se conectaba con la base de datos</h1>
        <br/>
        <h2 style="text-align: center;">Por favor verifique que el servidor est&eacute; funcionando y que los datos de la conexi&oacute;n sean correctos.</h2>
        <h2 style="text-align: center;">Si no sabe por qu&eacute; est&aacute; recibiendo este error comuniquese con el administrador del sistema.</h2>
    </tiles:put>
</tiles:insert>