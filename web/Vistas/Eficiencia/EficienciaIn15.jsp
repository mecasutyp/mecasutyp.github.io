<%--
    Document   : EficienciaIn15
    Created on : 30/05/2012, 11:25:58 PM
    Author     : Daniel Ramìrez Torres
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%> 
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.mecasut.shared.Universidad"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%try {%>
<script type="text/javascript">
    $(function() {
        $( "[id^=radio]" ).buttonset();
    });
</script>
<html:form action="/EficienciaIn15">
    
    <div id="efici15" align="center">
        <%--  Se obtiene la sesion  --%>
        <%
        Universidad uni = new Universidad();
            HttpSession sesion = request.getSession(false);
            String IdUni = sesion.getAttribute("idUniversidad").toString();
            String IdPer = sesion.getAttribute("idPeriodo").toString();
            String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
        %>
        
        
        <%--  Se consultaran los datos en caso de existir  --%>
        <%
            ConexionMySQL con = new ConexionMySQL();
            String opcion = null;
            String periodo_inicio = null;
            String periodo_fin = null;
            String proxima_auditoria = null;
            String datos[] = new String[20];
            int contador = 1, contadorb = 1, cont = 0;
            String sql5 = "";
            ResultSet rs5 = null;
            try {

                String sql3 = "SELECT id_certificacion,nombre_certificacion FROM certificaciones WHERE activo=1 and universidad=0";
                ResultSet rs3 = con.Consultar(sql3);

                int j = 0;
                for (int i = 1; i < 6; i++) {
                    String sql2 = "SELECT opcion,periodo_inicio,periodo_fin,proxima_auditoria FROM eficienciain15_1 WHERE id_universidad=".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo=").concat(sesion.getAttribute("idPeriodo").toString().concat(" and num_fila='").concat(String.valueOf(i)).concat("'"));
                    ResultSet rs2 = con.Consultar(sql2);
                    if (rs2.next()) {
                        opcion = rs2.getString("opcion");
                        periodo_inicio = rs2.getString("periodo_inicio");
                        periodo_fin = rs2.getString("periodo_fin");
                        proxima_auditoria = rs2.getString("proxima_auditoria");
                        datos[j] = opcion;
                        datos[j + 1] = periodo_inicio;
                        datos[j + 2] = periodo_fin;
                        datos[j + 3] = proxima_auditoria;
                    }
                    j += 4;
                }

                while (rs3.next()) {
                    String sql1 = "SELECT sin_certificado,en_proceso,con_certificado,en_proceso_recertificacion,con_recertificado FROM eficienciain15 WHERE id_universidad='".concat(sesion.getAttribute("idUniversidad").toString()).concat("' and id_periodo='").concat(sesion.getAttribute("idPeriodo").toString().concat("' and id_certificacion=").concat(rs3.getString(1)));
                    ResultSet rs1 = con.Consultar(sql1);

        %>
        <h3><a href="#">Cuadro 15.<%=contador%><br/>Situaci&oacute;n del certificado <%=rs3.getString("nombre_certificacion")%></a></h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company"></th>
                        <th scope="col" colspan="3" class="rounded-q1">Indicar la Situaci&oacute;n en que se encuentra la UT</th>
                        <th scope="col" class="rounded-q4"></th>
                    </tr>        
                </thead>
                <%    if (rs1.next()) {
                %>
                <tr>
                    <td colspan="5">
                        <div id="radios1">
                            <input name="id_certificado<%=contador%>" type="hidden" value="<%=rs3.getString("id_certificacion")%>"/>
                            <input type="radio" id="radioss1<%=contador%>" name="radioss<%=contador%>" value="1" <%=!rs1.getString("sin_certificado").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15Radios();"/><label for="radioss1<%=contador%>">Sin Certificado</label>
                            <input type="radio" id="radioss2<%=contador%>" name="radioss<%=contador%>" value="1" <%=!rs1.getString("en_proceso").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15Radios();"/><label for="radioss2<%=contador%>">En proceso de Certificaci&oacute;n</label>
                            <input type="radio" id="radioss3<%=contador%>" name="radioss<%=contador%>" value="1" <%=!rs1.getString("con_certificado").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15Radios();"/><label for="radioss3<%=contador%>">Con Certificado</label>
                            <input type="radio" id="radioss4<%=contador%>" name="radioss<%=contador%>" value="1" <%=!rs1.getString("en_proceso_recertificacion").equals("0") ? "checked='checked'" : ""%>  onclick="Eficiencia15Radios();"/><label for="radioss4<%=contador%>">En Proceso de Recertificaci&oacute;n</label>
                            <input type="radio" id="radioss5<%=contador%>" name="radioss<%=contador%>" value="1" <%=!rs1.getString("con_recertificado").equals("0") ? "checked='checked'" : ""%>  onclick="Eficiencia15Radios();"/><label for="radioss5<%=contador%>">Con Recertificado</label>
                        </div>
                    </td>
                </tr>
                <%} else {%>
                <tr>
                    <td colspan="5">
                        <div id="radios1">
                            <input name="id_certificado<%=contador%>" type="hidden" value="<%=rs3.getString("id_certificacion")%>"/>
                            <input type="radio" id="radioss1<%=contador%>" name="radioss<%=contador%>" value="1" onclick="Eficiencia15Radios();"/><label for="radioss1<%=contador%>">Sin Certificado</label>
                            <input type="radio" id="radioss2<%=contador%>" name="radioss<%=contador%>" value="1" onclick="Eficiencia15Radios();"/><label for="radioss2<%=contador%>">En proceso de Certificaci&oacute;n</label>
                            <input type="radio" id="radioss3<%=contador%>" name="radioss<%=contador%>" value="1" onclick="Eficiencia15Radios();"/><label for="radioss3<%=contador%>">Con Certificado</label>
                            <input type="radio" id="radioss4<%=contador%>" name="radioss<%=contador%>" value="1" onclick="Eficiencia15Radios();"/><label for="radioss4<%=contador%>">En Proceso de Recertificaci&oacute;n</label>
                            <input type="radio" id="radioss5<%=contador%>" name="radioss<%=contador%>" value="1" onclick="Eficiencia15Radios();"/><label for="radioss5<%=contador%>">Con Recertificado</label>
                        </div>
                    </td>
                </tr>
                <%}%>
                <tfoot>
                    <tr>
                        <td colspan="4" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>

            </table>
        </div>
        <%
            contador++;
            if (rs3.getInt("id_certificacion") == 1) {
        %>

        <h3><a href="#">Cuadro 15.<%=contador%><br/>Procesos de Certificados</a></h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">Nombre del Macroproceso o Proceso de Certificado </th>
                        <th class="rounded-q1">Opci&oacute;n</th>
                        <th class="rounded-q1">Periodo que comprende la certificaci&oacute;n o recertificaci&oacute;n</th>
                        <th class="rounded-q4" width="30%" >Fecha de la proxima auditoria para obtener la recertificaci&oacute;n</th>
                    </tr>
                </thead>
                <tr>
                    <td align="center" >Aced&eacute;mico/Educativo</td>
                    <td width="10%">
                        <div id="radios2"  >
                            <input type="radio" id="radio6" name="radio21" <%=datos[0] == null ? "" : Integer.parseInt(datos[0]) == 1 ? "checked='checked'" : ""%> value="1" onclick="Eficiencia15Radios1();" /><label for="radio6">SI</label>
                            <input type="radio" id="radio7" name="radio21" <%=datos[0] == null ? "checked='checked'" : Integer.parseInt(datos[0]) == 0 ? "checked='checked'" : ""%> value="0" onclick="Eficiencia15Radios1();" /><label for="radio7">NO</label>
                        </div>
                    </td>
                    <td align="center" ><input readonly=\"readonly\" class="fechas" type='text' id="inicio1" name='fila11' maxlength="10" value='<%=datos[1] == null ? "0000-00-00" : datos[1]%>' onblur="Eficiencia15Radios1();" ><input id="fin1" readonly=\"readonly\" type='text' name='fila21'  maxlength="10" value='<%=datos[2] == null ? "0000-00-00" : datos[2]%>' onblur="Eficiencia15Radios1();" ></td>
                    <td align="center" ><input readonly=\"readonly\" type='text' id="prox1" name='fila31'  maxlength="10"  value='<%=datos[3] == null ? "0000-00-00" : datos[3]%>' onblur="Eficiencia15Radios1();" ></td>
                </tr>
                <tr>
                    <td align="center" >Vinculaci&oacute;n</td>
                    <td align="center" width="10%" >
                        <div id="radios3">
                            <input type="radio" id="radio8" name="radio22" value="1" <%=datos[4] == null ? "" : Integer.parseInt(datos[4]) == 1 ? "checked='checked'" : ""%> onclick="Eficiencia15Radios1();" /><label for="radio8">SI</label>
                            <input type="radio" id="radio9" name="radio22"  value="0" <%=datos[4] == null ? "checked='checked'" : Integer.parseInt(datos[4]) == 0 ? "checked='checked'" : ""%> onclick="Eficiencia15Radios1();" /><label for="radio9">NO</label>
                        </div></td>
                    <td align="center" ><input readonly=\"readonly\" id="inicio2" type='text' name='fila12'  maxlength="10" value='<%=datos[5] == null ? "0000-00-00" : datos[5]%>' onblur="Eficiencia15Radios1();" ><input id="fin2" readonly=\"readonly\" type='text' name='fila22'  maxlength="10" value='<%=datos[6] == null ? "0000-00-00" : datos[6]%>' onblur="Eficiencia15Radios1();" ></td>
                    <td align="center" ><input readonly=\"readonly\" type='text' id="prox2" name='fila32'  maxlength="10" value='<%=datos[7] == null ? "0000-00-00" : datos[7]%>' onblur="Eficiencia15Radios1();" ></td>
                </tr>
                <tr>
                    <td align="center" >Administraci&oacute;n/Gesti&oacute;n de Recursos</td>
                    <td align="center" width="10%" >
                        <div id="radios4">
                            <input type="radio" id="radio10" name="radio23" value="1" <%=datos[8] == null ? "" : Integer.parseInt(datos[8]) == 1 ? "checked='checked'" : ""%> onclick="Eficiencia15Radios1();" /><label for="radio10">SI</label>
                            <input type="radio" id="radio11" name="radio23" <%=datos[8] == null ? "checked='checked'" : Integer.parseInt(datos[8]) == 0 ? "checked='checked'" : ""%> value="0" onclick="Eficiencia15Radios1();" /><label for="radio11">NO</label>
                        </div>
                    </td>
                    <td align="center" ><input readonly=\"readonly\" id="inicio3" type='text' name='fila13'  maxlength="10" value='<%=datos[9] == null ? "0000-00-00" : datos[9]%>' onblur="Eficiencia15Radios1();" ><input id="fin3" readonly=\"readonly\" type='text' name='fila23'  maxlength="10" value='<%=datos[10] == null ? "0000-00-00" : datos[10]%>' onblur="Eficiencia15Radios1();" ></td>
                    <td align="center" ><input readonly=\"readonly\" type='text' id="prox3" name='fila33'  maxlength="10" value='<%=datos[11] == null ? "0000-00-00" : datos[11]%>' onblur="Eficiencia15Radios1();" ></td>
                </tr>
                <tr>
                    <td align="center" >Educaci&oacute;n Continua</td>
                    <td align="center" width="10%">
                        <div id="radios5">
                            <input type="radio" id="radio12" name="radio24" value="1"  <%=datos[12] == null ? "" : Integer.parseInt(datos[12]) == 1 ? "checked='checked'" : ""%> onclick="Eficiencia15Radios1();" /><label for="radio12">SI</label>
                            <input type="radio" id="radio13" name="radio24"  <%=datos[12] == null ? "checked='checked'" : Integer.parseInt(datos[12]) == 0 ? "checked='checked'" : ""%> value="0" onclick="Eficiencia15Radios1();" /><label for="radio13">NO</label>
                        </div>
                    </td>
                    <td align="center" ><input readonly=\"readonly\" id="inicio4" type='text' name='fila14'  maxlength="10" value='<%=datos[13] == null ? "0000-00-00" : datos[13]%>' onblur="Eficiencia15Radios1();" ><input id="fin4" readonly=\"readonly\" type='text' name='fila24'  maxlength="10" value='<%=datos[14] == null ? "0000-00-00" : datos[14]%>' onblur="Eficiencia15Radios1();" ></td>
                    <td align="center" ><input readonly=\"readonly\" type='text' id="prox4" name='fila34'  maxlength="10" value='<%=datos[15] == null ? "0000-00-00" : datos[15]%>' onblur="Eficiencia15Radios1();" ></td>
                </tr>
                <tr>
                    <td align="center" >Planeaci&oacute;n y Evaluaci&oacute;n</td>
                    <td align="center" width="10%">
                        <div id="radios6">
                            <input type="radio" id="radio14" name="radio25" value="1" <%=datos[16] == null ? "" : Integer.parseInt(datos[16]) == 1 ? "checked='checked'" : ""%> onclick="Eficiencia15Radios1();" /><label for="radio14">SI</label>
                            <input type="radio" id="radio15" name="radio25" <%=datos[16] == null ? "checked='checked'" : Integer.parseInt(datos[16]) == 0 ? "checked='checked'" : ""%>value="0" onclick="Eficiencia15Radios1();" /><label for="radio15">NO</label>
                        </div>
                    </td>
                    <td align="center" ><input readonly=\"readonly\" id="inicio5" type='text' name='fila15'  maxlength="10" value='<%=datos[17] == null ? "0000-00-00" : datos[17]%>' onblur="Eficiencia15Radios1();" ><input readonly=\"readonly\" id="fin5" type='text' name='fila25'  maxlength="10" value='<%=datos[18] == null ? "0000-00-00" : datos[18]%>' onblur="Eficiencia15Radios1();" ></td>
                    <td align="center" ><input readonly=\"readonly\" type='text' id="prox5" name='fila35'  maxlength="10" value='<%=datos[19] == null ? "0000-00-00" : datos[19]%>' onblur="Eficiencia15Radios1();" ></td>
                </tr>
                <tr>
                    <td class="rounded-q1" align="center" >Total</td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total_radios' value='0'></td>
                    <td class="rounded-foot-right"></td>
                    <td class="rounded-foot-right"></td>
                </tr>
                <tfoot>
                    <tr>
                        <td colspan="3" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <%
                    contador++;
                    //  contadorb = 0;
                }
            }
        %>

        <%
            sql5 = "SELECT id_certificacion,nombre_certificacion FROM certificaciones WHERE activo=1 and universidad=1";
            rs5 = con.Consultar(sql5);
            while (rs5.next()) {
                String sql4 = "SELECT sin_certificado,en_proceso,con_certificado,en_proceso_recertificacion,con_recertificado FROM eficienciain15 WHERE id_universidad='".concat(sesion.getAttribute("idUniversidad").toString()).concat("' and id_periodo='").concat(sesion.getAttribute("idPeriodo").toString().concat("' and id_certificacion=").concat(rs5.getString("id_certificacion")));
                ResultSet rs4 = con.Consultar(sql4);

        %>

        <h3><a href="#">Cuadro 15.<%=contador + cont%><br/>
                Otras Certificaciones 
                <% if(!rs5.getString("id_certificacion").equals("21")){%>
                   (Situaci&oacute;n del Certificado <%=rs5.getString("nombre_certificacion")%> ) 
                <%}%></a></h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company"></th>
                        <th scope="col" colspan="3" class="rounded-q1">Indicar la Situaci&oacute;n en que se encuentra la UT</th>
                        <th scope="col" class="rounded-q4"></th>
                    </tr>        
                </thead>
                <%    if (rs4.next()) {
                %>
                <tr>
                    <td colspan="5">
                        <div id="radiosb">
                            <input name="id_certificadob<%=contadorb%>" type="hidden" value="<%=rs5.getString("id_certificacion")%>"/>
                            <input type="radio" id="option1<%=contadorb%>" name="option<%=contadorb%>" value="1" <%=!rs4.getString("sin_certificado").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15RadiosB();"/><label for="option1<%=contadorb%>">Sin Certificado</label>
                            <input type="radio" id="option2<%=contadorb%>" name="option<%=contadorb%>" value="1" <%=!rs4.getString("en_proceso").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15RadiosB();" /><label for="option2<%=contadorb%>">En proceso de Certificaci&oacute;n</label>
                            <input type="radio" id="option3<%=contadorb%>" name="option<%=contadorb%>" value="1" <%=!rs4.getString("con_certificado").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15RadiosB();" /><label for="option3<%=contadorb%>">Con Certificado</label>
                            <input type="radio" id="option4<%=contadorb%>" name="option<%=contadorb%>" value="1" <%=!rs4.getString("en_proceso_recertificacion").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15RadiosB();" /><label for="option4<%=contadorb%>">En Proceso de Recertificaci&oacute;n</label>
                            <input type="radio" id="option5<%=contadorb%>" name="option<%=contadorb%>" value="1" <%=!rs4.getString("con_recertificado").equals("0") ? "checked='checked'" : ""%> onclick="Eficiencia15RadiosB();"/><label for="option5<%=contadorb%>">Con Recertificado</label>
                        </div>
                    </td>
                </tr>
                <%} else {%>
                <tr>
                    <td colspan="5">
                        <div id="radiosb">
                            <input name="id_certificadob<%=contadorb%>" type="hidden" value="<%=rs5.getString("id_certificacion")%>"/>
                            <input type="radio" id="option1<%=contadorb%>" name="option<%=contadorb%>" value="1" onclick="Eficiencia15RadiosB();"/><label for="option1<%=contadorb%>">Sin Certificado</label>
                            <input type="radio" id="option2<%=contadorb%>" name="option<%=contadorb%>" value="1" onclick="Eficiencia15RadiosB();"/><label for="option2<%=contadorb%>">En proceso de Certificaci&oacute;n</label>
                            <input type="radio" id="option3<%=contadorb%>" name="option<%=contadorb%>" value="1" onclick="Eficiencia15RadiosB();"/><label for="option3<%=contadorb%>">Con Certificado</label>
                            <input type="radio" id="option4<%=contadorb%>" name="option<%=contadorb%>" value="1" onclick="Eficiencia15RadiosB();"/><label for="option4<%=contadorb%>">En Proceso de Recertificaci&oacute;n</label>
                            <input type="radio" id="option5<%=contadorb%>" name="option<%=contadorb%>" value="1" onclick="Eficiencia15RadiosB();"/><label for="option5<%=contadorb%>">Con Recertificado</label>
                        </div>
                    </td>
                </tr>
                <%}%>
                <tfoot>
                    <tr>
                        <td colspan="4" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>

            </table>
        </div>
        <%
                contadorb++;
                cont++;
            }
        %>

        <h3><a href="#">Archivo</a></h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <tr><td><input type="file" id="archivo" name="archivo" /></td></tr>
                <tr>
                    <td><input type="hidden" id="nomuni" name="nomuni" value="<%=universidad%>">
                        <input type="hidden" id="IdUni" name="IdUni" value="<%=IdUni%>">
                        <input type="hidden" id="IdPer" name="IdPer" value="<%=IdPer%>"></td>
                </tr>
                <tr><td></td></tr>
                
                 <tfoot>
                    <tr>
                        <td colspan="4" class="rounded-foot-left">Notas: Por el momento solo se pueden enviar archivos formato PDF y con tamaño m&aacute;ximo de 2MB.
                        <br>Los archivos ya enviados <strong>No apareceran aqui</strong></td>
                        
                    </tr>
                </tfoot>
            </table>
        </div>
        
        
        <%
            } catch (SQLException ex) {
                System.err.println("Jsp Error eficiencia 15: " + ex);
            } finally {
                con.Desconectar();
            }%>

    </div>

    <html:hidden name="EficienciaIn15Form" property="valores" styleId="valores" />
    <input name="nocuadros" type="hidden" value="<%=contador - 1%>"/>
    <input name="nocuadrosb" type="hidden" value="<%=contadorb - 1%>"/>
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=15";
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
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarEficiencia15(),'EficienciaIn15Datos','EficienciaIn15Form');" >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
<%            }
%>
