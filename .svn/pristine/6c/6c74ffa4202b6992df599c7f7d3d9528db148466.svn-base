<%-- 
    Document   : EquidadIn33
    Created on : 29/05/2012, 12:50:27 PM
    Author     : Juan García Barradas
    Actualización 2016: Salvador Zamora Arias
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
            HttpSession sesion = request.getSession(false);
            int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
            ConexionMySQL con = new ConexionMySQL();
            
            String v1 = "0";
            String v2 = "0";
            try {
                String consultaEfiIn1="select nuevo_ingreso from datos_universidad WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" AND id_periodo=").concat(String.valueOf(idPeriodo));
                ResultSet rsEfiIn1= con.Consultar(consultaEfiIn1);
                String consulta = "select egresados from egresados_estado where id_estado = (select id_estado from domicilios_universidad where id_universidad=".concat(String.valueOf(idUniversidad)).concat(" AND id_periodo=").concat(String.valueOf(idPeriodo)).concat(") and id_periodo=".concat(String.valueOf(idPeriodo)));
                ResultSet rs = con.Consultar(consulta);
                while(rsEfiIn1.next()){
                    v1 = rsEfiIn1.getString("nuevo_ingreso");
                }

                while (rs.next()) {
                    v2 = rs.getString("egresados");
                }

            } catch (SQLException ex) {
                System.err.println("Error en consulta equidadin33"+ex);
            } finally {
                con.Desconectar();
            }

%>

<html:form action="/EquidadIn33">
    <div id="equi33">
        <h3><a href="#">Cuadro 33.1<br/>Cobertura</a></h3>
        <div align="center">
            <table id="rounded-corner" style="width: 40%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q4"></th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Alumnos nuevo ingreso en la universidad</th>
                        <th align="center" class="rounded-q1">Egresados de bachillerato en el estado</th>
                        <th align="center" class="rounded-q1">COB=1/2</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: &eacute;ste cuadro es llenado autom&aacute;ticamente.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input type="text" style="width:90%" value="<%=v1%>" name="i33v1" id="i33v1" readonly="readonly" class="inputok"/></td>
                    <td align="center"><input type="text" style="width:90%" value="<%=v2%>" name="i33v2" id="i33v2" readonly="readonly" class="inputok"/></td>
                    <td><input type="text" style="width:90%" name="COB" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
    </div>
</html:form>
