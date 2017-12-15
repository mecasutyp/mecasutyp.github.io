<%-- 
    Document   : VinculacionIn32
    Created on : 22/08/2012, 11:03:32 AM
    Author     : Joshua
    Actualización 2016: Salvador Zamora Arias
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%try{%>
<html:form action="/VinculacionIn32">
    <div id="vinc32">
        <%
            HttpSession sesion = request.getSession(false);
            String idUniversidad = sesion.getAttribute("idUniversidad").toString();
            String idPeriodo = sesion.getAttribute("idPeriodo").toString();
            int c = 1, cuad = 0;
            ConexionMySQL con = new ConexionMySQL();
            String sql2 = "Select distinct(pe.id_nivel), n.descripcion, n.nombre from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(") order by pe.id_nivel");

            ResultSet rs2 = con.Consultar(sql2);
            try {
                while (rs2.next()) {

        %>
        <h3><a href='#'>Cuadro 32.<%=rs2.getString("id_nivel")%><br/>Egresados del Nivel <%=rs2.getString("n.descripcion")%> colocados</a></h3>
        <div align="center">
            <table id='rounded-corner' style='width: 50%'>
                <thead>
                    <tr>
                        <th class='rounded-company'>1</th>
                        <th class='rounded-q4'>2</th>
                    </tr>
                    <tr>
                        <th>Egresados de <%=rs2.getString("n.descripcion")%> colocados en plazas contactadas por el &aacute;rea de bolsa de trabajo de la universidad</th>
                        <th>Plazas de <%=rs2.getString("n.descripcion")%> contactadas por el &aacute;rea de bolsa de trabajo de la universidad</th>
                    </tr>
                </thead>
                <input type="hidden" value='<%=rs2.getString("id_nivel")%>' name='Id_nivel<%=c%>'/>
                <%
                    String sql = "Select egresados_colocados, plazas from vinculacionin32 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel = ").concat(rs2.getString("id_nivel"));

                    ResultSet rs = con.Consultar(sql);
                    if (rs.next()) {%>
                <tr>                    
                    <td><input maxlength='4' style='width:90%' value='<%=rs.getString("egresados_colocados")%>' name='valEgresados_<%=c%>' onblur='indicador32(this)' type='text'/></td>
                    <td><input maxlength='4' style='width:90%' value='<%=rs.getString("plazas")%>' name='valPlazas_<%=c%>' onblur='indicador32(this)' type='text'/></td>
                </tr>
                <%
                } else {%>
                <tr>
                    <td><input maxlength='4' style='width:90%' value='0' name='valEgresados_<%=c%>' onblur='indicador32(this)' type='text'/></td>
                    <td><input maxlength='4' style='width:90%' value='0' name='valPlazas_<%=c%>' onblur='indicador32(this)' type='text'/></td>
                </tr>
                <% }
                %> 
                <tr>
                    <td colspan='2' align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='BT_name<%=c%>' value='BT<%=c%>' ></td>
                </tr>
                <tr>
                    <td colspan='2' align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='BT<%=c%>' value='0' ></td>
                </tr>
                <tfoot>
                    <tr>
                        <td class='rounded-foot-left'>Notas: No hay notas adicionales.</td>
                        <td class='rounded-foot-right'></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <%
                    c++;
                }
            } catch (Exception ex) {
                System.err.println("JSPERROR-VinculacionIn32:" + ex.getMessage());
            } finally {
                con.Desconectar();
            }
        %>
    </div>
    <input type="hidden" value='<%=c%>' name='No_niv'/>
       <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=32";
        ResultSet rscom = con.Consultar(consultacom);      
        String comentario="Sin comentarios";
        if(rscom.next()){//SI HAY UN COMENTARIO REGISTRADO SE OBTIENE PARA MOSTRARSE
            comentario = rscom.getString("comentario");
        }
%>
                <input type="text" id="comentario" maxlength="700" name="comentario" style=" width: 90%; " value="<%= comentario %>"/>
            </th>
        </tr>
    </table>
    <%
        String consulta = "select activo from system_mecasut";
        ResultSet rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")){
    %>
    <button type="button" class="btnGuardarIndicador" onclick="guardar32()">Guardar</button>
    <%}}
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
</html:form>
    <%
} catch (NullPointerException exNull) {
%>
<div id="vinc30" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
