<%-- 
    Document   : CgutNuevoIngreso
    Created on : 10-jul-2013, 10:58:47
    Author     : Daniel Ram�rez Torres
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
    HttpSession sesion = request.getSession();
    String id_periodo = "", id_user = "";
    if (sesion.getAttribute("usuario") == null) {
%>
<jsp:forward page="/login.msut">
    <jsp:param name="error" value="true"></jsp:param>
    <jsp:param name="ask" value="false"></jsp:param>
    <jsp:param name="errorMessage" value="Sesi&oacute;n caducada o inv&aacute;lida<br/>El recurso al que deseas acceder est&aacute; restringido, debes iniciar sesi&oacute;n para utilizarlo."></jsp:param>
</jsp:forward>
<%  } else {
        id_periodo = sesion.getAttribute("idPeriodo").toString();
        id_user = sesion.getAttribute("idUsuario").toString();
    }
%>

<html:form action="/CgutNuevoIngresoAction" onsubmit="return false">
    <input type="hidden" name="inicioNuevoIng" id="inicioNuevoIng"/>
    <h2 align="center">Alumnos de Nuevo Ingreso por Universidad</h2>
    <%
        int a = 0, valor, i = 0;
        ResultSet rs = null;
        ResultSet rs2 = null;
        String tabla_nuevo_ing = "<table id='hor-minimalist-a' border='0px' align='center' cellpadding='10px' cellspacing='0px'>"
                + "<tr>"
                + "<th>Fecha Creaci�n</th>"
                + "<th align='center'>Universidad</th>"
                + "<th>Alumos de Nuevo Ingreso</th>"
                + "<th style='border: 3px'></th>"
                + "<th>Fecha Creaci�n</th>"
                + "<th align='center'>Universidad</th>"
                + "<th>Alumos de Nuevo Ingreso</th>"
                + "<th></th>"
                + "</tr>";
        ConexionMySQL con = new ConexionMySQL();
        try {
            String sql = "select id_universidad,fecha_acreditacion, nombre_universidad from universidades where id_universidad > 0 order by fecha_acreditacion asc";
            rs = con.Consultar(sql);
            while (rs.next()) {
                String sql2 = "select nuevo_ingreso from datos_universidad where id_universidad = ".concat(rs.getString(1)).concat(" and id_periodo = ").concat(id_periodo);
                rs2 = con.Consultar(sql2);
                if (rs2.next()) {
                    valor = rs2.getInt(1);
                } else {
                    valor = 0;
                }
                i++;
                if (a == 0) {
                    tabla_nuevo_ing += "<tr>";
                    tabla_nuevo_ing += "<td align='center'>" + rs.getInt("fecha_acreditacion") + "</td>";
                    tabla_nuevo_ing += "<td align='right'>" + rs.getString("nombre_universidad") + "</td>";
                    tabla_nuevo_ing += "<td><input type='text' onblur='solo_numeros(this);' maxlength='6' style='width: 70px' name='valn" + i + "' id='valn" + i + "' value='" + valor + "'/></td>";
                    tabla_nuevo_ing += "<td><input value='" + rs.getInt("id_universidad") + "' type='hidden' name='NuevoIng" + rs.getInt("id_universidad") + "' id='NuevoIng" + rs.getInt("id_universidad") + "'/></td>";
                    a = 1;
                } else {
                    tabla_nuevo_ing += "<td align='center'>" + rs.getInt("fecha_acreditacion") + "</td>";
                    tabla_nuevo_ing += "<td align='right'>" + rs.getString("nombre_universidad") + "</td>";
                    tabla_nuevo_ing += "<td><input type='text' onblur='solo_numeros(this);' maxlength='6' style='width: 70px' name='valn" + i + "' id='valn" + i + "' value='" + valor + "'/></td>";
                    tabla_nuevo_ing += "<td><input value='" + rs.getInt("id_universidad") + "' type='hidden' name='NuevoIng" + rs.getInt("id_universidad") + "' id='NuevoIng" + rs.getInt("id_universidad") + "'/></td>";
                    tabla_nuevo_ing += "</tr>";
                    a = 0;
                }
            }
            tabla_nuevo_ing += "<tr>"
                    + "<td colspan='8' align='right'>"
                    + "<a class='green' onclick='validar_nuevo();'>Guardar cambios</a>"
                    + "</td>"
                    + "</tr>"
                    + "</table>";
        } catch (SQLException ex) {
            System.err.println("Error " + ex.getMessage());
        } finally {
            con.Desconectar();
        }
    %>
    <%=tabla_nuevo_ing%>
    <input type="hidden" name="finNuevoIng" id="finNuevoIng"/>
</html:form>

