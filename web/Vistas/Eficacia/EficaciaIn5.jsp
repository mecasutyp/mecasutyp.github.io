<%--
    Document   : EficaciaIn5
    Created on : 14/05/2012, 01:47:34 PM
    Author     : Cuauhtemoc Medina Muñoz, Daniel Ramirez Torres
    Actualización 2016: Salvador Zamora Arias
--%>

<%@page import="com.mecasut.shared.Universidad"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
    try {
%>
<div align="center">Se debe reportar el cuatrimestre con mayor egreso del periodo</div>
<html:form action="/EficaciaIn5">
    
    <div id="efi5" align="center">
        <%
        //Recuperamos la sesión para obtener los datos
        Universidad uni= new Universidad();
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();
        String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
        int c = 1;
        int numtC = 1;
        ConexionMySQL con = new ConexionMySQL();
            //Obtenemos el año actual
            String sql = "Select anio from periodos where id_periodo = ".concat(idPeriodo);
            ResultSet rsAnio = con.Consultar(sql);
            rsAnio.next();
            //Obtenemos los niveles con los que cuenta la universidad y hacemos un acordeon por nivel
            sql = "Select distinct(pe.id_nivel), n.descripcion, n.nombre from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(") order by pe.id_nivel");
            ResultSet rsNivel = con.Consultar(sql);
            while (rsNivel.next()) 
            {
                int numC = 1;
                for (int i = 1; i <= 2; i++) 
                {
                    /*if (rsNivel.getString(1).equals("3")) 
                    {
                        i++;
                    }*/
                   
                    if (i == 1) 
                    {
        %>
                        <h3><a href='#' onclick="cargarCuerpo('<%=rsNivel.getString(1)%>','<%=rsAnio.getInt(1)%>','<%=numtC%>','1');">Cuadro 5.1<%="." + rsNivel.getString("id_nivel")%> <br/>
                        Tasa de Egreso y Titulaci&oacute;n de <%=rsNivel.getString(2)%> (<%=String.valueOf(rsAnio.getInt(1))%>)</a></h3>
        <%
                    } else {
        %>
                        <h3><a href='#' onclick="cargarCuerpo('<%=rsNivel.getString(1)%>','<%=rsAnio.getInt(1) + 1%>','<%=numtC%>','1');">Cuadro 5.1<%="." + rsNivel.getString("id_nivel")%> <br/>
                        Tasa de Egreso y Titulaci&oacute;n de <%=rsNivel.getString(2)%> (<%=String.valueOf(rsAnio.getInt(1) + 1)%>)</a></h3>
        <%
                    }
                    if (numtC == 1) 
                    {
        %>
                        <div id="cuadro<%=numtC%>" style="text-align: center;">
                            <input type="hidden" name="anio" value="<%=rsAnio.getInt(1)%>"/>
                            <input type="hidden" name="nivel" value="<%=rsNivel.getInt(1)%>"/>
                            Cargando Indicador
                        </div>
        <%} 
                    else {
        %>
                        <div id="cuadro<%=numtC%>">
                        </div>
        <%
                    }
                    numC++;
                    numtC++;
                }
                c++;
            }
        %>
        <%-- 
       comienza la segunda tabla
        --%>
        <%
            rsNivel.beforeFirst();
            int i = 1;
            while (rsNivel.next()) 
            {
        %>
                <h3><a href='#' onclick="cargarCuerpo('<%=rsNivel.getString(1)%>','<%=rsAnio.getInt(1)%>','<%=numtC%>','2');">Cuadro 5.2<%=".".concat(rsNivel.getString("id_nivel"))%><br/>
                Registro de <%=rsNivel.getString(2)%> ante la Direcci&oacute;n General de Profesiones de la Secretar&iacute;a de Educaci&oacute;n P&uacute;blica</a></h3>
                <div id="cuadro_tipo2<%=numtC%>" style="text-align: center;">
                </div>
        <%             
                i++;
                numtC++;
            }
        %>
           <h3><a href="#">Archivo</a></h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <tr><td><input type="file" id="archivo" name="archivo" /></td></tr>
                <tr>
                    <td><input type="hidden" id="nomuni" name="nomuni" value="<%=universidad%>">
                        <input type="hidden" id="IdUni" name="IdUni" value="<%= idUniversidad %>">
                        <input type="hidden" id="IdPer" name="IdPer" value="<%=idPeriodo%>"></td>
                </tr>
                <tr><td></td></tr>
                
                 <tfoot>
                    <tr>
                        <td colspan="4" class="rounded-foot-left">Notas: Por el momento solo se pueden enviar archivos formato PDF y con tamaño m&aacute;ximo de 2MB.
                        <br>Los archivos ya enviados <strong>No apareceran aqui</strong></td>
                        
                    </tr>
                </tfoot>
            </table>
            <%
                String consulta = "select activo from system_mecasut";
                ResultSet rs = con.Consultar(consulta);
                rs.next();
                if (rs.getInt("activo") == 1) {
                    if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                        if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
            %>
            <div >
                <button type="button" class="btnGuardarIndicador" onclick="enviaarchivo();">Guardar Archivo</button>
            </div>
            <%}
                }
            } else {%>
            <div >
                <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
            </div>
            <%        }%>
        </div>
        
        <html:hidden name="EficaciaIn5Form" property="valores" styleId="valores" />
    </div>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
<%            }
%>
