<%-- 
    Document   : VinculacionIn27
    Created on : 20/08/2012, 09:07:03 AM
    Author     : Joshua
    Actualización 2016: Salvador Zamora Arias
--%>


<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%try {
        HttpSession sesion = request.getSession(false);
        int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
        ConexionMySQL con = new ConexionMySQL();

        String presupuesto = "";
        String sql = "";
        String matricula = "0";
        ResultSet rs = null;
        boolean resultado = false;
        try {
            sql = "Select autorizado from eficaciain11 where id_universidad = ".concat(String.valueOf(idUniversidad)).concat(" and id_periodo = ").concat(String.valueOf(idPeriodo));

            rs = con.Consultar(sql);
            while (rs.next()) {
                presupuesto = rs.getString("autorizado");
                resultado = true;
            }

        } catch (SQLException ex) {
            System.err.println("JSPError VinculacionIn27: " + ex);
        } finally {
            con.Desconectar();
        }

        if (resultado == true) {
%>
<html:form action="/VinculacionIn27">
    <div id="vinc27" align="center">
        <h2>Cuadro 27.1<br/> Ingresos Propios Captados </h2>
        <div>
            <table id='rounded-corner' style='width: 100%'>
                <thead>
                    <tr>
                        <th class='rounded-company'>Presupuesto total autorizado federal y Estatal</th>
                        <th class='rounded-q1'>Recursos captados por servicios y estudios tecnol&oacute;gicos</th>
                        <th class='rounded-q1'>Recursos captados por colegiaturas y servicios escolares</th>
                        <th class='rounded-q1'>Recursos captados por otros servicios proporcionados por la Universidad (diferentes a los anteriores)</th>
                        <th class='rounded-q4'>Total de recursos captados por ingresos Propios</th>
                    </tr>
                </thead>
                <tr>
                    <%
                        String tablas = "";
                        try {
                            tablas = "";

                            tablas = tablas.concat("<td><input readonly='readonly' id='etiqueta' style='width:90%' name='presTotalAut' type='text' value='").concat(presupuesto).concat("'/></td>");

                            sql = "Select rec_servicios, rec_colegiaturas, rec_diferentes from vinculacionin27 where id_universidad = ".concat(String.valueOf(idUniversidad)).concat(" and id_periodo = ").concat(String.valueOf(idPeriodo));
                            rs = con.Consultar(sql);
                            if (rs.next()) {
                                tablas = tablas.concat("<td><input maxlength='16' style='width:90%' value='").concat(rs.getString("rec_servicios")).concat("' name='valSer' onblur='indicador27(this)' type='text'/></td>");
                                tablas = tablas.concat("<td><input maxlength='16' style='width:90%' value='").concat(rs.getString("rec_colegiaturas")).concat("' name='valCol' onblur='indicador27(this)' type='text'/></td>");
                                tablas = tablas.concat("<td><input maxlength='16' style='width:90%' value='").concat(rs.getString("rec_diferentes")).concat("' name='valDif' onblur='indicador27(this)' type='text'/></td>");
                            } else {
                                tablas = tablas.concat("<td><input maxlength='16' style='width:90%' value='0' name='valSer' onblur='indicador27(this)' type='text'/></td>");
                                tablas = tablas.concat("<td><input maxlength='16' style='width:90%' value='0' name='valCol' onblur='indicador27(this)' type='text'/></td>");
                                tablas = tablas.concat("<td><input maxlength='16' style='width:90%' value='0' name='valDif' onblur='indicador27(this)' type='text'/></td>");
                            }
                        } catch (Exception ex) {
                            System.err.println("JSPERROR-VinculacionIn27:" + ex.getMessage());
                        } finally {
                            con.Desconectar();
                        }
                    %>
                    <%=tablas%>
                    <td><input readonly='readonly' id='etiqueta' style='width:90%' name='totIngPro' type='text' value='0'/></td>
                </tr>
                <tr>
                    <td align='center'></td>
                    <td align='center'>IPC1</td>
                    <td align='center'>IPC2</td>
                    <td align='center'>IPC3</td>
                    <td align='center'>IPC4</td>
                </tr>
                <tfoot>
                    <tr>
                        <td align='center'></td>
                        <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcSer" type="text" value="0"/></td>
                        <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcCol" type="text" value="0"/></td>
                        <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcDif" type="text" value="0"/></td>
                        <td class='rounded-foot-right'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcTot" type="text" value="0"/></td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
                    
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=27";
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
        rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
    %>
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardar27(),'VinculacionIn27Datos','VinculacionIn27Form');">Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
</html:form>


<%
} else {
%>
<div class="error">
    Para poder ver este indicador, primero debe llenar el <br/>
    <a href="global.msut?ask=eficacia">Indicador 1.11 de Eficacia</a>
</div>
<%        }
%>
<%
} catch (NullPointerException exNull) {
%>
<div id="vinc27" align="center">
La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
