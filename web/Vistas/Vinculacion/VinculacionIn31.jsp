<%--
    Document   : VinculacionIn31
    Created on : 25/06/2012, 03:02:36 PM
    Author     : Cuauhtemoc Medina
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!--OBTENER LA SESION-->
<%try {
        HttpSession sesion = request.getSession(false);
        int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
        int no = 1, c = 1;

%>
<!--TERMINA OBTENER LA SESION-->

<html:form action="/VinculacionIn31">
    <%
        ConexionMySQL con = new ConexionMySQL();
        boolean mostrarIndicador = false;
        int poblacion = 0;
        int muestreo = 0 , nopreguntas=0;;
        String consulta = "";
        String aplica = "0";
        try {
            //modificar consulta daniel, consulta para obtener la matricula inicial
            //String consulta = "SELECT matricula_total from datos_universidad where id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));

            consulta = "SELECT count(capacitacion) FROM vinculacionin29"
                    .concat(" WHERE id_universidad=").concat(String.valueOf(idUniversidad))
                    .concat(" AND id_periodo=").concat(String.valueOf(idPeriodo));
            ResultSet rs = con.Consultar(consulta);
            consulta = "SELECT aplica FROM indicadores_aplica "
                    .concat(" WHERE id_universidad=").concat(String.valueOf(idUniversidad))
                    .concat(" and indicador='31' AND id_periodo=").concat(String.valueOf(idPeriodo));
            ResultSet rs2 = con.Consultar(consulta);
            if (rs2.next()) {
                aplica = rs2.getString("aplica");
            }
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    consulta = "SELECT SUM(capacitacion) + SUM(actualizacion) + SUM(desarrollo_p) FROM vinculacionin29"
                            .concat(" WHERE id_universidad=").concat(String.valueOf(idUniversidad))
                            .concat(" AND id_periodo=").concat(String.valueOf(idPeriodo));
                    rs = con.Consultar(consulta);
                    if (rs.next()) {
                        poblacion = rs.getInt(1);
                        mostrarIndicador = true;
                    }
                }
            }

            if (poblacion >= 2835) {
                muestreo = (int) (poblacion * .15);
            } else if (poblacion >= 1501) {
                muestreo = 300;
            } else if (poblacion >= 1001) {
                muestreo = 250;
            } else if (poblacion >= 751) {
                muestreo = 200;
            } else if (poblacion >= 501) {
                muestreo = 150;
            } else if (poblacion >= 401) {
                muestreo = 100;
            } else if (poblacion >= 301) {
                muestreo = 90;
            } else if (poblacion >= 201) {
                muestreo = 75;
            } else if (poblacion >= 151) {
                muestreo = 60;
            } else if (poblacion >= 101) {
                muestreo = 50;
            } else if (poblacion >= 71) {
                muestreo = 40;
            } else if (poblacion >= 31) {
                muestreo = 30;
            } else {
                //MUESTREO ES IGUAL AL TOTAL DE POBLACION
                muestreo = poblacion;
            }
            if (mostrarIndicador == true) {
    %>

    <span id="validacionTiene">
        <h2>Indique si su Universidad cuenta con Educación Continua</h2>
        <div id="radios1" align="center">
            <input type="radio" id="radio1" name="radioAplica" value="1" <%=aplica.equals("1") ? "checked='checked'" : ""%>/> <label for="radio1" >Si aplica</label>
            <input type="radio" id="radio2" name="radioAplica" value="0" <%=aplica.equals("0") ? "checked='checked'" : ""%>/><label for="radio2" >No aplica</label>
        </div>
    </span>
    <br/>
    <div id="vinc31">
        <h2> Cuadro 31.1<br/>Tasa de Alumnos Satisfechos en Educaci&oacute;n Continua </h2>
        <div>
            <table id='rounded-corner' style='width: 100%'>
                <thead>
                    <tr>
                        <th rowspan='3' class='rounded-company'>#</th>
                        <th rowspan='3' class='rounded-q1'>Descripci&oacute;n</th>
                        <th colspan='10' class='rounded-q1'>Frecuencias</th>
                        <th rowspan='2' class='rounded-q1'>Total K, Base 5</th>
                        <th rowspan='3' class='rounded-q4'>Total K, Base 10</th>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>4</td>
                        <td>3</td>
                        <td>2</td>
                        <td>1</td>
                        <td>N/A</td>
                        <td width="6%">N/E</td>
                        <td>TOTAL H</td>
                        <td>TOTAL I</td>
                        <td>TOTAL J</td>
                    </tr>
                    <tr>
                        <td>A</td>
                        <td>B</td>
                        <td>C</td>
                        <td>D</td>
                        <td>E</td>
                        <td>F</td>
                        <td>G</td>
                        <td>&Sigma; A a G</td>
                        <td>&Sigma; A a E</td>
                        <td>A*5 + B*4 + C*3 + D*2 + E*1</td>
                        <td>J/I</td>
                    </tr>
                </thead>
                <%
                    String preguntas = "select id_pregunta, pregunta from encuesta_preguntas where id_encuesta=10 and activo=1";
                    ResultSet preg = con.Consultar(preguntas);

                    String respuestas = "";
                    ResultSet resp;
                    while (preg.next()) {
                        respuestas = "SELECT r_a, r_b, r_c, r_d, r_e, r_f, r_g FROM vinculacionin31 WHERE id_pregunta=".concat(preg.getString("id_pregunta").concat(" and id_universidad=").concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo)));
                        resp = con.Consultar(respuestas);
                        if (resp.next()) {
                %>
                <tr>
                    <td width="5%" align="center"><input name="id_pre,<%=c%>,<%=no%>" type="hidden" value="<%=preg.getString(1)%>"/><%=no%></td>
                    <td width="21%" align="center"><%=preg.getString("pregunta")%></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="<%=resp.getString(1)%>" name="val_A,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="<%=resp.getString(2)%>" name="val_B,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="<%=resp.getString(3)%>" name="val_C,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="<%=resp.getString(4)%>" name="val_D,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="<%=resp.getString(5)%>" name="val_E,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="<%=resp.getString(6)%>" name="val_F,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="<%=resp.getString(7)%>" name="val_G,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input readonly="readonly" class='inputok' style="width:90%" type="text" name="tot_H,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_I,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_J,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_K_5,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_K_10,<%=c%>,<%=no%>" value="0"/></td>
                </tr>

                <%
                    no++;
                } else {
                %>
                <tr>
                    <td width="5%" align="center"><input name="id_pre,<%=c%>,<%=no%>" type="hidden" value="<%=preg.getString(1)%>"/><%=no%></td>
                    <td width="21%" align="center"><%=preg.getString("pregunta")%></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="0" name="val_A,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="0" name="val_B,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="0" name="val_C,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="0" name="val_D,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="0" name="val_E,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="0" name="val_F,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input maxlength="6" style="width:90%" value="0" name="val_G,<%=c%>,<%=no%>" onblur="indicador31(this,<%=c%>,<%=no%>,0,1)" type="text"/></td>
                    <td width="6%" align="center"><input class='inputok' readonly="readonly" style="width:90%" type="text" name="tot_H,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_I,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_J,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_K_5,<%=c%>,<%=no%>" value="0"/></td>
                    <td width="6%" align="center"><input readonly="readonly" id="etiqueta" style="width:90%" type="text" name="tot_K_10,<%=c%>,<%=no%>" value="0"/></td>
                </tr>

                <%
                            no++;
                        }
                         nopreguntas++;
                    }
                %>
                <tr>
                <input type="hidden" value="<%=no%>" name="no_fil_niv,<%=c%>" onblur='indicador31(this,<%=c%>,0,0,0);'/> 
                <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>TOTAL</td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-1" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-2" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-3" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-4" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-5" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-6" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-7" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-8" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-9" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-10" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-11" type="text" value="0"/> </td>
                <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="tv_<%=c%>-12" type="text" value="0"/> </td>
                </tr>
                    <!--DISTRIBUCION PORCENTUAL NOMBRE-->
                    <tr>
                         <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>DISTRIBUCIÓN PORCENTUAL</td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>MS</td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>S</td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>RS</td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>PS</td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NS</td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NA</td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NE</td>

                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text'/> </td>
                         <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>TES</td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text'/> </td>
                    </tr>

                    <!--DISTRIBUCION PORCENTUAL VALOR-->
                    <tr>
                         <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'></td>

                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-1-d-ms' type='text'/></td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-2-d-s' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-3-d-rs' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-4-d-ps' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-5-d-ns' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-6-d-na' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-7-d-ne' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-8-d' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-9-d-tes' type='text'/><input type='hidden' name='totalpreguntas' value='<%=nopreguntas%>'> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-10-d' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-11-d' type='text'/> </td>
                        <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' name='tv_<%=c%>-12-d' type='text'/> </td>
                    </tr>
                        
                <tfoot>
                    <tr>
                        <td colspan='13' class='rounded-foot-left'>Notas: Se encuestará al menos a <%=muestreo%> personas, ya que el total de asistentes es <%=poblacion%>. Atención, por lo anterior la columna 'H' debe igual o mayor a la cantidad mencionada.</td>
                        <td class='rounded-foot-right'></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <%
                    c++;
                }
            } catch (SQLException ex) {
                System.err.println("ERROR JSP Vinculacion31 "+ex.getMessage());
            } finally {
                con.Desconectar();
            }


        %>
        <input type="hidden" value="<%=c%>" name="cuad" />
        <input type="hidden" value="<%=muestreo%>" name="fin" />
        <html:hidden name="VinculacionIn31Form" property="valores" styleId="valores" />
    </div>
   <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=31";
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
    <%if (mostrarIndicador == false) {%>
    <h7 class="error">
        Para poder ver este indicador, primero debe llenar el <br/>
        <a href="#" onclick='$("#tabs").tabs("select", 3);'>Indicador 4.4 de Vinculaci&oacute;n</a>
    </h7>
    <%} else {
        consulta = "select activo from system_mecasut";
        ResultSet rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
    %>
    <button type="button" class="btnGuardarIndicador" onclick="retrieveURL('/VinculacionIn31.msut?valores='+validarGuardado31(<%=c%>,0,1,0), 'VinculacionIn31Form'); " >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%      }
        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
<div id="vinc30" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
