<%--
    Document   : ErrorConexionBD
    Created on : 27/06/2012, 10:30:24 AM
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
        MECASUT - Datos enviados exitosamente
    </tiles:put>
    <tiles:put name="scripts" type="string">
    </tiles:put>
    <tiles:put name="body" type="string">
        <br/>
        <h1 style="text-align: center;">Los datos se han enviado correctamente.</h1>
        <p style="text-align: center;"><a href="#" onclick="window.open('AcusePDF.msut?ask=sendInf');">Imprimir Acuse (PDF)</a></p>
    </tiles:put>
</tiles:insert>

<script type="text/javascript">
    $(function() {
        window.open('AcusePDF.msut?ask=sendInf');                  
    });
            
</script>