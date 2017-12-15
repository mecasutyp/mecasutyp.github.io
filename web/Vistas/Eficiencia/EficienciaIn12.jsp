<%--
    Document   : EficienciaIn12
    Created on : 29/05/2012, 01:43:12 PM
    Author     : Cuauhtemoc Medina Muñoz
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!--OBTENER LA SESION-->
<%
try{
            HttpSession sesion = request.getSession(false);
            int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
%>
<!--TERMINA OBTENER LA SESION-->

<%
            ConexionMySQL con = new ConexionMySQL();

            String presupuesto = "";
            String matricula = "0";
            boolean resultado = false;
            try {
                
                String autorizado = "SELECT autorizado FROM eficaciain11 WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));
                ResultSet aut = con.Consultar(autorizado);
                while (aut.next()) {
                    presupuesto = aut.getString(1);
                    resultado = true;
                }
                String consulta = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" AND id_periodo=").concat(String.valueOf(idPeriodo));
                ResultSet mat = con.Consultar(consulta);
                while (mat.next()) {
                    matricula = mat.getString(1);
                }
            } catch (SQLException ex) {
                System.err.println("Error eficiciencia 12: "+ex);
            } finally {
                con.Desconectar();
            }
            
            if (resultado == true) {
%>
<html:form action="/EficienciaIn12">
    <div id="efic12">
        <h3>Cuadro 12.1<br/>Costo por Alumno</h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th width='20%'rowspan="2" class="rounded-q4">CPA 1/2</th>
                    </tr>
                    <tr>
                        <th width='20%' class="rounded-q1">Presupuesto total autorizado Federal y Estatal</th>
                        <th width='20%' class="rounded-q1">Matr&iacute;cula inicial atendida en el ciclo escolar</th>
                    </tr>
                </thead>
                <tr>
                    <td align="center"><input style="width:90%" type="text" name="presupuesto" value="<%=presupuesto%>" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" type="text" name="matricula" value="<%=matricula%>" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" type="text" name="costoporalumno" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: Éste cuadro es llenado autom&aacute;ticamente con información del <b>indicador 1.11</b></td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</html:form>

    <%
        } else {
    %>
    <div class="error">
        Para poder ver este indicador, primero debe llenar el <br/>       
        <a href="global.msut?ask=eficacia">Indicador 1.11 de Eficacia</a>   
    </div>
    <%
        }
} catch (NullPointerException exNull) {
%>
<div id="efis1" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
