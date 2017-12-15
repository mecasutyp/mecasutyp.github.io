<%-- 
    Document   : VinculacionIn26
    Created on : 17/08/2012, 10:58:31 AM
    Author     : Joshua
    Actualización 2016: Salvador Zamora Arias
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%try {%>
<html:form action="/VinculacionIn26">
    <div id="vinc26">
        <h2>Cuadro 26.1.1 <br/> Total de Organismos Nacionales vinculados</h2>
        <div align="center">
            <table id='rounded-corner' style='width: 50%'>
                <thead>
                    <tr>
                        <th class='rounded-company'></th>
                        <th class='rounded-q4'>Total</th>
                    </tr>
                </thead>
                <tr>
                    <td>a) Organismos vinculados acumulados al ciclo escolar</td>
                    <td><input readonly="readonly" id="etiqueta" style="width:90%" name="totOrgVinc" type="text" value="0"/></td>
                </tr>
                <%
                    HttpSession sesion = request.getSession(false);
                    String idUniversidad = sesion.getAttribute("idUniversidad").toString();
                    String idPeriodo = sesion.getAttribute("idPeriodo").toString();
                    String tablas = "";
                    ConexionMySQL con = new ConexionMySQL();
                    try {
                        String sql = "Select conv_acum, conv_acum_sup, publicos, privados, sociales from vinculacionin26 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo);

                        ResultSet rs = con.Consultar(sql);
                        if (rs.next()) {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td>b) N&uacute;mero de convenios firmados acumulados al ciclo escolar:</td>");
                            tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("conv_acum")).concat("' name='valConv' onblur='indicador26(this)' type='text'/></td>");
                            tablas = tablas.concat("</tr>");
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>c) N&uacute;mero de convenios firmados acumulados con instituciones de educación superior en el ciclo escolar:</td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='4' style='width:90%' value='").concat(rs.getString("conv_acum_sup")).concat("' name='valConvSup' onblur='indicador26(this)' type='text'/></td>");
                            tablas = tablas.concat("</tr>");
                        } else {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td>b) N&uacute;mero de convenios firmados acumulados al ciclo escolar:</td>");
                            tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valConv' onblur='indicador26(this)' type='text'/></td>");
                            tablas = tablas.concat("</tr>");
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>c) N&uacute;mero de convenios firmados acumulados con instituciones de educación superior en el ciclo escolar:</td>");
                            tablas = tablas.concat("<td align='center'><input maxlength='4' style='width:90%' value='0' name='valConvSup' onblur='indicador26(this)' type='text'/></td>");
                            tablas = tablas.concat("</tr>");
                        }
                %>
                <%=tablas%>
            </table>
        </div>
        
        <h2>Cuadro 26.1.2<br/>Total de Organismos Nacionales Vinculados Acumulados por Sector</h2>
        <div>
            <table id='rounded-corner' style='width: 100%'>
                <thead>
                    <tr>
                        <th class='rounded-company'>Total de Organismos vinculados acumulados</th>
                        <th class='rounded-q1'>P&uacute;blicos</th>
                        <th class='rounded-q1'>Privados</th>
                        <th class='rounded-q4'>Sociales</th>
                    </tr>
                </thead>
                <tr>
                    <td><input readonly="readonly" id="etiqueta" style="width:90%" name="totOrgVinc" type="text" value="0"/></td>
                        <%
                                rs.beforeFirst();
                                tablas = "";
                                if (rs.next()) {
                                    tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("publicos")).concat("' name='valPub' onblur='indicador26(this)' type='text'/></td>");
                                    tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("privados")).concat("' name='valPri' onblur='indicador26(this)' type='text'/></td>");
                                    tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("sociales")).concat("' name='valSoc' onblur='indicador26(this)' type='text'/></td>");
                                } else {
                                    tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valPub' onblur='indicador26(this)' type='text'/></td>");
                                    tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valPri' onblur='indicador26(this)' type='text'/></td>");
                                    tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valSoc' onblur='indicador26(this)' type='text'/></td>");
                                }
                            } catch (Exception ex) {
                                System.err.println("JSPERROR-VinculacionIn26:" + ex.getMessage());
                            } finally {
                                con.Desconectar();
                            }
                        %>
                        <%=tablas%>
                </tr>
                <tfoot>
                    <tr>
                        <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcSum" type="text" value="0"/></td>
                        <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcPub" type="text" value="0"/></td>
                        <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcPri" type="text" value="0"/></td>
                        <td class='rounded-foot-right'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcSoc" type="text" value="0"/></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        
        <h2>Cuadro 26.2.1 <br/> Total de Organismos Internacionales Vinculados</h2>
        <div align="center">
            <table id='rounded-corner' style='width: 50%'>
                <thead>
                    <tr>
                        <th class='rounded-company'></th>
                        <th class='rounded-q4'>Total</th>
                    </tr>
                </thead>
                <tr>
                    <td>a) Organismos vinculados acumulados al ciclo escolar</td>
                    <td><input readonly="readonly" id="etiqueta" style="width:90%" name="totOrgVincIn" type="text" value="0"/></td>
                </tr>
                <%
                  tablas = "";
                  try 
                  {
                    String sql = "Select conv_acum, conv_acum_sup, publicos, privados, sociales from vinculacionin26_internacional where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo);
                    ResultSet rs = con.Consultar(sql);
                        
                    if (rs.next()) 
                    {
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td>b) N&uacute;mero de convenios firmados acumulados al ciclo escolar:</td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("conv_acum")).concat("' name='valConvIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td align='center'>c) N&uacute;mero de convenios firmados acumulados con instituciones de educación superior en el ciclo escolar:</td>");
                        tablas = tablas.concat("<td align='center'><input maxlength='4' style='width:90%' value='").concat(rs.getString("conv_acum_sup")).concat("' name='valConvSupIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                    } else 
                    {
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td>b) N&uacute;mero de convenios firmados acumulados al ciclo escolar:</td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valConvIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td align='center'>c) N&uacute;mero de convenios firmados acumulados con instituciones de educación superior en el ciclo escolar:</td>");
                        tablas = tablas.concat("<td align='center'><input maxlength='4' style='width:90%' value='0' name='valConvSupIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                    }
%>
                <%=tablas%>
            </table>
        </div>
        
        <h2>Cuadro 26.2.2<br/>Total de Organismos Internacionales Vinculados Aumulados por Sector</h2>
        <div>
            <table id='rounded-corner' style='width: 100%'>
                <thead>
                    <tr>
                        <th class='rounded-company'>Total de Organismos vinculados acumulados</th>
                        <th class='rounded-q1'>P&uacute;blicos</th>
                        <th class='rounded-q1'>Privados</th>
                        <th class='rounded-q4'>Sociales</th>
                    </tr>
                </thead>
                <tr>
                    <td><input readonly="readonly" id="etiqueta" style="width:90%" name="totOrgVincIn" type="text" value="0"/></td>
<%
                    rs.beforeFirst();
                    tablas = "";
                    if (rs.next()) 
                    {
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("publicos")).concat("' name='valPubIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("privados")).concat("' name='valPriIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("sociales")).concat("' name='valSocIn' onblur='indicador26(this)' type='text'/></td>");
                    } else {
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valPubIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valPriIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valSocIn' onblur='indicador26(this)' type='text'/></td>");
                    }
                } catch (Exception ex) {
                    System.err.println("JSPERROR-VinculacionIn26:" + ex.getMessage());
                } finally {
                    con.Desconectar();
                }
%>
<%=tablas%>
                </tr>
                <tfoot>
                    <tr>
                        <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcSumIn" type="text" value="0"/></td>
                        <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcPubIn" type="text" value="0"/></td>
                        <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcPriIn" type="text" value="0"/></td>
                        <td class='rounded-foot-right'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcSocIn" type="text" value="0"/></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        <h2>Cuadro 26.3.1 <br/>Movilidad Nacional</h2>
        <div align="center">
            <table id='rounded-corner' style='width: 50%'>
                <thead>
                    <tr>
                        <th class='rounded-company'>Alumnos con Movilidad</th>
                        <th class='rounded-q1'>Docentes con Movilidad</th>
                        <th class='rounded-q4'>Total</th>
                    </tr>
                </thead>
                <%
                  tablas = "";
                  try 
                  {
                    String sql = "Select alumnos,docentes from vinculacionin26 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo);
                    ResultSet rs = con.Consultar(sql);
                        
                    if (rs.next()) 
                    {
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("alumnos")).concat("' name='valAlumno' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("docentes")).concat("' name='valDocentes' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input readonly='readonly' id='etiqueta' maxlength='4' style='width:90%' value='0' name='valTotalNacional' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                    } else 
                    {
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valAlumno' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valDocentes' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input readonly='readonly' id='etiqueta' maxlength='4' style='width:90%' value='0' name='valTotalNacional' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                    }
}               catch (Exception ex) {
                    System.err.println("JSPERROR-VinculacionIn26:" + ex.getMessage());
                } finally {
                    con.Desconectar();
                }
%>
            <%=tablas%>
            <tfoot>
                <tr>
                    <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcAlumno" type="text" value="0"/></td>
                    <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcDocente" type="text" value="0"/></td>
                    <td class='rounded-foot-right'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcTotalNacional" type="text" value="0"/></td>
               </tr>
            </tfoot>
            </table>
        </div>
            
            
        <h2>Cuadro 26.3.2 <br/> Movilidad Internacional</h2>
        <div align="center">
            <table id='rounded-corner' style='width: 50%'>
                <thead>
                    <tr>
                        <th class='rounded-company'>Alumnos con Movilidad</th>
                        <th class='rounded-q1'>Docentes con Movilidad</th>
                        <th class='rounded-q4'>Total</th>
                    </tr>
                </thead>
                <%
                  tablas = "";
                  try 
                  {
                    String sql = "Select alumnos,docentes from vinculacionin26_internacional where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo);
                    ResultSet rs = con.Consultar(sql);
                        
                    if (rs.next()) 
                    {
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("alumnos")).concat("' name='valAlumnoIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='").concat(rs.getString("docentes")).concat("' name='valDocenteIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input  readonly='readonly' id='etiqueta' maxlength='4' style='width:90%' value='0' name='valTotalInternacional' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                    } else 
                    {
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valAlumnoIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input maxlength='4' style='width:90%' value='0' name='valDocenteIn' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("<td><input readonly='readonly' id='etiqueta' maxlength='4' style='width:90%' value='0' name='valTotalInternacional' onblur='indicador26(this)' type='text'/></td>");
                        tablas = tablas.concat("</tr>");
                    }
}               catch (Exception ex) {
                    System.err.println("JSPERROR-VinculacionIn26:" + ex.getMessage());
                } finally {
                    con.Desconectar();
                }
%>
            <%=tablas%>
            <tfoot>
                <tr>
                    <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcAlumnoIn" type="text" value="0"/></td>
                    <td><input readonly="readonly" id="etiqueta" style="width:90%" name="porcDocenteIn" type="text" value="0"/></td>
                    <td class='rounded-foot-right'><input readonly="readonly" id="etiqueta" style="width:90%" name="porcTotalInternacional" type="text" value="0"/></td>
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
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=26";
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
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
    %>
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardar26(),'VinculacionIn26Datos','VinculacionIn26Form');">Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
<div id="vinc26" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
