<%-- 
    Document   : VinculacionIn28
    Created on : 21/08/2012, 10:34:29 AM
    Author     : Joshua Luévano
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%try{%>
<html:form action="/VinculacionIn28">
    <div id="vinc28" align="center">
        <%
            HttpSession sesion = request.getSession(false);
            String idUniversidad = sesion.getAttribute("idUniversidad").toString();
            String idPeriodo = sesion.getAttribute("idPeriodo").toString();
            String tablas = "";
            ConexionMySQL con = new ConexionMySQL();
            int volver = 0;
            try {
                String consulta1 = "select rec_servicios from vinculacionin27 where id_universidad=".concat(idUniversidad).concat(" and id_periodo=".concat(idPeriodo));
                ResultSet rs1 = con.Consultar(consulta1);
                
                
                if (rs1.next()) {
                    String sql = "Select id_estudio, descripcion from estudios_tecnologicos where activo=1";
                    volver = 1;
                    ResultSet rs = con.Consultar(sql);
                    tablas = tablas.concat("<h3><a href='#'>Cuadro 28.1<br/>");
                    tablas = tablas.concat("Servicios y Estudios Tecnol&oacute;gicos Seg&uacute;n Tipo y Sector</a></h3>");
                    tablas = tablas.concat("<div><table id='rounded-corner' style='width: 100%'>");
                    tablas = tablas.concat("<thead>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<th rowspan='2' class='rounded-company'>Servicios y estudios tecnol&oacute;gicos</th>");
                    tablas = tablas.concat("<th colspan='3' class='rounded-q1'>Organismos vinculados</th>");
                    tablas = tablas.concat("<th rowspan='2' class='rounded-q4'>Total</th>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td>P&uacute;blicos</td>");
                    tablas = tablas.concat("<td>Privados</td>");
                    tablas = tablas.concat("<td>Sociales</td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</thead>");
                    tablas = tablas.concat("<input type='hidden' name='rec_servicios' value='").concat(rs1.getString("rec_servicios")).concat("' >");
                    while (rs.next()) {
                        String sql3 = "Select org_publicos, org_privados, org_sociales from vinculacionin28 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_estudio=").concat(rs.getString("id_estudio"));
                        ResultSet rs3 = con.Consultar(sql3);
                        if (rs3.next()) {
                            
                            
                            tablas = tablas.concat("<tr>");
                            
                            tablas = tablas.concat("<td align='center'>").concat(rs.getString("descripcion")).concat("</td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='7' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",1_1' value='").concat(rs3.getString("org_publicos")).concat("' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='7' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",2_1' value='").concat(rs3.getString("org_privados")).concat("' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='7' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",3_1' value='").concat(rs3.getString("org_sociales")).concat("' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th").concat(rs.getString("id_estudio")).concat("_1' value='0'></td>");
                            tablas = tablas.concat("</tr>");
                        } else {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>").concat(rs.getString("descripcion")).concat("</td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='7' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",1_1' value='0' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='7' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",2_1' value='0' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='7' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",3_1' value='0' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th").concat(rs.getString("id_estudio")).concat("_1' value='0'></td>");
                            tablas = tablas.concat("</tr>");
                        }
                    }
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td align='center' style='font-weight: bold'>Total</td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv1_1' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv2_1' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv3_1' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv4_1' value='0'></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("<tfoot>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='4' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>");
                    tablas = tablas.concat("<td class='rounded-foot-right'></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</tfoot>");
                    tablas = tablas.concat("</table></div>");

                    //Tabla 28.2
                    rs.beforeFirst();

                    tablas = tablas.concat("<h3><a href='#'>Cuadro 28.2<br/>");
                    tablas = tablas.concat("Ingresos Propios por Servicios y Estudios Tecnol&oacute;gicos seg&uacute;n Tipo y Sector</a></h3>");
                    tablas = tablas.concat("<div><table id='rounded-corner' style='width: 100%'>");
                    tablas = tablas.concat("<thead>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<th rowspan='2' class='rounded-company'>Servicios y estudios tecnol&oacute;gicos</th>");
                    tablas = tablas.concat("<th colspan='3' class='rounded-q1'>Organismos vinculados</th>");
                    tablas = tablas.concat("<th rowspan='2' class='rounded-q4'>Total</th>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td>P&uacute;blicos</td>");
                    tablas = tablas.concat("<td>Privados</td>");
                    tablas = tablas.concat("<td>Sociales</td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</thead>");
                    while (rs.next()) {
                        String sql3 = "Select ing_publicos, ing_privados, ing_sociales from vinculacionin28 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_estudio=").concat(rs.getString("id_estudio"));
                        ResultSet rs3 = con.Consultar(sql3);
                        if (rs3.next()) {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>").concat(rs.getString("descripcion")).concat("</td>");
                            tablas = tablas.concat("<td align='center'><input ").concat(rs3.getString("ing_publicos").equals("0") ? "disabled" : "").concat(" maxlength='12' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",1_2' value='").concat(rs3.getString("ing_publicos")).concat("' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input ").concat(rs3.getString("ing_privados").equals("0") ? "disabled" : "").concat(" maxlength='12' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",2_2' value='").concat(rs3.getString("ing_privados")).concat("' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input ").concat(rs3.getString("ing_sociales").equals("0") ? "disabled" : "").concat(" maxlength='12' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",3_2' value='").concat(rs3.getString("ing_sociales")).concat("' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th").concat(rs.getString("id_estudio")).concat("_2' value='0'></td>");
                            tablas = tablas.concat("</tr>");
                        } else {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>").concat(rs.getString("descripcion")).concat("</td>");
                            tablas = tablas.concat("<td align='center'><input disabled maxlength='12' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",1_2' value='0' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input disabled maxlength='12' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",2_2' value='0' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input disabled maxlength='12' style='width:90%' type='text' name='val").concat(rs.getString("id_estudio")).concat(",3_2' value='0' onblur='indicador28(this);' ></td>");
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th").concat(rs.getString("id_estudio")).concat("_2' value='0'></td>");
                            tablas = tablas.concat("</tr>");
                        }
                    }
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td align='center' style='font-weight: bold'>Total</td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv1_2' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv2_2' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv3_2' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" class='inputok' style='width:90%' type='text' name='tv4_2' value='0'></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("<tfoot>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='4' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>");
                    tablas = tablas.concat("<td class='rounded-foot-right'></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</tfoot>");
                    tablas = tablas.concat("</table></div>");
                } else {
                    volver = 0;
                }
            } catch (SQLException ex) {
                System.err.println("JSPERROR-VinculacionIn28:" + ex.getMessage());
            } finally {
                con.Desconectar();
            }
        %>
        <%=tablas%>
        <input type="hidden" name="fin"/>
        <html:hidden name="VinculacionIn28Form" property="valores" styleId="valores" />
    </div>
      <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=28";
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
    <%if (volver == 0) {%>
    <h7 class="error">
        Para poder ver este indicador, primero debe llenar el <br/>
        <a href="#" onclick='$("#tabs").tabs("select", 1);'>Indicador 4.2 de Vinculaci&oacute;n</a>
    </h7>
    <%}else{%>
    <%
        String consulta = "select activo from system_mecasut";
        ResultSet rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")){
    %>
            <button type="button" class="btnGuardarIndicador" onclick="guardar28()">Guardar</button>
    <%}}
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }}%>
</html:form>
    <%
    } catch (NullPointerException exNull) {
    %>
    
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
    