<%-- 
    Document   : EquidadIn35
    Created on : 31/05/2012, 11:15:27 AM
    Author     : Juan
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
    String v3 = "0";
    String v4 = "Sin aspectos a resaltar.";
    String v5 = "0";
    String v6 = "0";
    String v7 = "0";
    String v8 = "Sin aspectos a resaltar.";
    String v9 = "0";
    String v10 = "0";
    String v11 = "0";
    String v12 = "Sin aspectos a resaltar.";

    try {
        String consulta = "SELECT * FROM equidadin35 ".concat("WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" AND id_periodo=").concat(String.valueOf(idPeriodo)));
        ResultSet rs = con.Consultar(consulta);
        while (rs.next()) {
            v1 = rs.getString("dep_realizados");
            v2 = rs.getString("dep_programados");
            v3 = rs.getString("dep_personas");
            v4 = rs.getString("dep_aspectos");
            v5 = rs.getString("cul_realizados");
            v6 = rs.getString("cul_programados");
            v7 = rs.getString("cul_personas");
            v8 = rs.getString("cul_aspectos");
            v9 = rs.getString("com_realizados");
            v10 = rs.getString("com_programados");
            v11 = rs.getString("com_personas");
            v12 = rs.getString("com_aspectos");
        }
    } catch (SQLException ex) {
        System.err.println("Error en consulta equidadin35");
    } finally {
        con.Desconectar();
    }
%>

<html:form action="/EquidadIn35">
    <div id="equi35">
        <h3><a href="#">Cuadro 35.1<br/></a>Promoci&oacute;n Deportiva, Cultural y Comunitaria</h3>
        <div align="center">
            <table id="rounded-corner" style="width: 60%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q1">3</th>
                        <th class="rounded-q1">4</th>
                        <th class="rounded-q1">5</th>
                        <th class="rounded-q4">6</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Eventos deportivos realizados por la universidad en el ciclo escolar</th>
                        <th align="center" class="rounded-q1">Eventos deportivos programados por la universidad en el ciclo escolar</th>
                        <th align="center" class="rounded-q1">Eventos culturales realizados por la universidad en el ciclo escolar</th>
                        <th align="center" class="rounded-q1">Eventos culturales programados por la universidad en el ciclo escolar</th>
                        <th align="center" class="rounded-q1">Eventos comunitarios realizados por la universidad en el ciclo escolar</th>
                        <th align="center" class="rounded-q1">Eventos comunitarios programados por la universidad en el ciclo escolar</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="5" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v1%>" property="dep_realizados" styleId="i35v1" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v2%>" property="dep_programados" styleId="i35v2" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v5%>" property="cul_realizados" styleId="i35v5" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v6%>" property="cul_programados" styleId="i35v6" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v9%>" property="com_realizados" styleId="i35v9" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v10%>" property="com_programados" styleId="i35v10" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                </tr>
                <tr>
                    <td colspan="2">PD1=</td>
                    <td colspan="2">PC2=</td>
                    <td colspan="2">PC3=</td>
                </tr>
                <tr>
                    <td colspan="2"><input type="text" style="width:90%" name="PD1" value="0" readonly="readonly" id="etiqueta"></td>
                    <td colspan="2"><input type="text" style="width:90%" name="PC2" value="0" readonly="readonly" id="etiqueta"></td>
                    <td colspan="2"><input type="text" style="width:90%" name="PC3" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h3><a href="#">Cuadro 35.2<br/>Poblaci&oacute;n Beneficiada por Evento</a></h3>
        <div align="center">
            <table id="rounded-corner" style="width: 70%">
                <thead>
                    <tr>
                        <th style="width: 25%" align="center" class="rounded-company">TIPO</th>
                        <th style="width: 25%" align="center" class="rounded-q1">Total de eventos realizados</th>
                        <th style="width: 15%" align="center" class="rounded-q1">Total de personas atendidas</th>
                        <th style="width: 35%" align="center" class="rounded-q4">Aspectos m&aacute;s importantes a resaltar</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="3" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center">Deportivos</td>
                    <td align="center"><input type="text" style="width:90%" name="dep_eventos" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v3%>" property="dep_personas" styleId="i35v3" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="500" style="width:90%" value="<%=v4%>" property="dep_aspectos" styleId="i35v4" name="EquidadIn35Form" onblur="operacionesIndicador35();"/></td>
                </tr>
                <tr>
                    <td align="center">Culturales</td>
                    <td align="center"><input type="text" style="width:90%" name="cul_eventos" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v7%>" property="cul_personas" styleId="i35v7" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="500" style="width:90%" value="<%=v8%>" property="cul_aspectos" styleId="i35v8" name="EquidadIn35Form" onblur="operacionesIndicador35();"/></td>
                </tr>
                <tr>
                    <td align="center">Comunitarios</td>
                    <td align="center"><input type="text" style="width:90%" name="com_eventos" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v11%>" property="com_personas" styleId="i35v11" name="EquidadIn35Form" onblur="indicador35(this);"/></td>
                    <td align="center"><html:text maxlength="500" style="width:90%" value="<%=v12%>" property="com_aspectos" styleId="i35v12" name="EquidadIn35Form" onblur="operacionesIndicador35();"/></td>
                </tr>
                <tr>
                    <td align="center">Total</td>
                    <td align="center"><input type="text" style="width:90%" name="t_eventos" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type="text" style="width:90%" name="t_personas" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"></td>
                </tr>
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
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=35";
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
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(validarGuardado35(),'EquidadIn35Datos','EquidadIn35Form');" >Guardar</button>
    <%}}
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
</html:form>
