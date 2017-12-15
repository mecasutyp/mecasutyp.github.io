<%-- 
    Document   : PertinenciaIn20
    Created on : 2/10/2012, 12:46:54 PM
    Author     : Daniel Ramírez Torres
--%>

<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page import="com.mysql.jdbc.MySQLConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
    try {%>
<html:form action="/PertinenciaIn20">
    <%
            ConexionMySQL con = new ConexionMySQL();
            HttpSession sesion = request.getSession(false);
            String sql = "", val23 = "";
            int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
            int val1 = 0, val2 = 0, val3 = 0, val4 = 0, val5 = 0, val6 = 0, val7 = 0, val8 = 0, val9 = 0, val10 = 0, val11 = 0, val12 = 0, val13 = 0, val14 = 0, val15 = 0, val16 = 0;
            int val17 = 0, val18 = 0, val19 = 0, val20 = 0, val21 = 0, val22 = 0, val24 = 0, val25 = 0, val26 = 0, val27 = 0, val28 = 0, val29 = 0, val30 = 0, val31 = 0;
            sql = "Select * from pertinenciain20 where id_Universidad = ".concat(String.valueOf(idUniversidad)).concat(" and id_Periodo = ").concat(String.valueOf(idPeriodo));
            ResultSet rs = con.Consultar(sql);
            if (rs.next()) {
                val1 = rs.getInt(3);
                val2 = rs.getInt(4);
                val3 = rs.getInt(5);
                val4 = rs.getInt(6);
                val5 = rs.getInt(7);
                val6 = rs.getInt(8);
                val7 = rs.getInt(9);
                val8 = rs.getInt(10);
                val9 = rs.getInt(11);
                val10 = rs.getInt(12);
                val11 = rs.getInt(13);
                val12 = rs.getInt(14);
                val13 = rs.getInt(15);
                val14 = rs.getInt(16);
                val15 = rs.getInt(17);
                val16 = rs.getInt(18);
                val17 = rs.getInt(19);
                val18 = rs.getInt(20);
                val19 = rs.getInt(21);
                val20 = rs.getInt(22);
                val21 = rs.getInt(23);
                val22 = rs.getInt(24);
                val23 = rs.getString(25);
                val24 = rs.getInt(26);
                val25 = rs.getInt(27);
                val26 = rs.getInt(28);
                val27 = rs.getInt(29);
                val28 = rs.getInt(30);
                val29 = rs.getInt(31);
                val30 = rs.getInt(32);
                val31 = rs.getInt(33);
            }

            sql = "SELECT prof_tc, prof_asig from datos_universidad WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));
            ResultSet prof = con.Consultar(sql);
            prof.next();
    %>
    <div id="pert20">
        <h2 onclick="operacionesPertinencia201(1,6,1);">Cuadro 20.1<br/>Cantidad de acciones que ha realizado la instituci&oacute;n durante el periodo a evaluar para contar con PE que cumplan con elementos descritos en el  Modelo Educativo centrado en el aprendizaje</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <input hidden="hidden" name="totPtc" value="<%=prof.getString(1)%>"/>
                <input hidden="hidden" name="totAsi" value="<%=prof.getString(2)%>"/>
                <thead>
                    <tr>
                        <th colspan="2" scope="col" class="rounded-company">Cursos</th>
                        <th colspan="2" class="rounded-q1">Talleres</th>
                        <th rowspan="2" class="rounded-q1">Otros</th>
                        <th rowspan="2" class="rounded-q4">Total</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Internos</th>
                        <th align="center" class="rounded-q1">Externos</th>
                        <th align="center" class="rounded-q1">Internos</th>
                        <th align="center" class="rounded-q1">Externos</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_1" value="<%=val1%>" onblur="indicador20(this,1,6,1);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_2" value="<%=val2%>" onblur="indicador20(this,1,6,1);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_3" value="<%=val3%>" onblur="indicador20(this,1,6,1);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_4" value="<%=val4%>" onblur="indicador20(this,1,6,1);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_5" value="<%=val5%>" onblur="indicador20(this,1,6,1);"/></td>
                    <td><input type="text" style="width:90%" name="total1" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2 onclick="operacionesPertinencia201(6,11,2);">Cuadro 20.2<br/>Cantidad de  Profesores de Tiempo Completo (PTC) que han participado en las acciones durante el periodo a evaluar</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th colspan="2" scope="col" class="rounded-company">Cursos</th>
                        <th colspan="2" class="rounded-q1">Talleres</th>
                        <th rowspan="2" class="rounded-q1">Otros</th>
                        <th rowspan="2" class="rounded-q4">Total</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Internos</th>
                        <th align="center" class="rounded-q1">Externos</th>
                        <th align="center" class="rounded-q1">Internos</th>
                        <th align="center" class="rounded-q1">Externos</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_6" value="<%=val6%>" onblur="indicador20(this,6,11,2);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_7" value="<%=val7%>" onblur="indicador20(this,6,11,2);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_8" value="<%=val8%>" onblur="indicador20(this,6,11,2);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_9" value="<%=val9%>" onblur="indicador20(this,6,11,2);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_10" value="<%=val10%>" onblur="indicador20(this,6,11,2);"/></td>
                    <td><input type="text" style="width:90%" name="total2" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2 onclick="operacionesPertinencia201(11,16,3);">Cuadro 20.3<br/>Cantidad de Profesores de Asignatura (PA) que han participado en las acciones durante el periodo a evaluar</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th colspan="2" scope="col" class="rounded-company">Cursos</th>
                        <th colspan="2" class="rounded-q1">Talleres</th>
                        <th rowspan="2" class="rounded-q1">Otros</th>
                        <th rowspan="2" class="rounded-q4">Total</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Internos</th>
                        <th align="center" class="rounded-q1">Externos</th>
                        <th align="center" class="rounded-q1">Internos</th>
                        <th align="center" class="rounded-q1">Externos</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_11" value="<%=val11%>" onblur="indicador20(this,11,16,3);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_12" value="<%=val12%>" onblur="indicador20(this,11,16,3);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_13" value="<%=val13%>" onblur="indicador20(this,11,16,3);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_14" value="<%=val14%>" onblur="indicador20(this,11,16,3);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_15" value="<%=val15%>" onblur="indicador20(this,11,16,3);"/></td>
                    <td><input type="text" style="width:90%" name="total3" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2 onclick="operacionesPertinencia201(16,19,4);">Cuadro 20.4<br/>Cantidad de material de los Enfoques Centrados en el Aprendizaje</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">Difusi&oacute;n</th>
                        <th class="rounded-q1">Capacitaci&oacute;n</th>
                        <th class="rounded-q1">Otro</th>
                        <th class="rounded-q4">Total</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_16" value="<%=val16%>" onblur="indicador20(this,16,19,4);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_17" value="<%=val17%>" onblur="indicador20(this,16,19,4);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_18" value="<%=val18%>" onblur="indicador20(this,16,19,4);"/></td>
                    <td><input type="text" style="width:90%" name="total4" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <%--aqui va los  --%>
        <h2>Cuadro 20.5<br/>Usuarios a quien va dirigido el material</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">PTC</th>
                        <th class="rounded-q1">PA</th>
                        <th class="rounded-q1">Alumnos</th>
                        <th class="rounded-q1">Otro</th>
                        <th class="rounded-q4">Especifique</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr id="check">
                    <td align="center"><input type="checkbox" id="val_19" value="<%=val19%>" name="val_19" /><label for="val_19">Si</label></td>
                    <td align="center"><input type="checkbox" id="val_20" value="<%=val20%>" name="val_20"/><label for="val_20">Si</label></td>
                    <td align="center"><input type="checkbox" id="val_21" value="<%=val21%>" name="val_21"/><label for="val_21">Si</label></td>
                    <td align="center"><input type="checkbox" id="val_22" value="<%=val22%>" onclick="checkedToBool(22);" name="val_22"/><label for="val_22">Si</label></td>
                    <td align="center"><input maxlength="25" style="width:90%" value="<%=val23%>" disabled name="val_23"/></td>
                </tr>
            </table>
        </div>
        <h2 onclick="operacionesPertinencia201(24,27,6);">Cuadro 20.6<br/>Cantidad de PTC seg&uacute;n situaci&oacute;n en los enfoques</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">Terminaron la capacitaci&oacute;n</th>
                        <th class="rounded-q1">Est&aacute;n en proceso de capacitaci&oacute;n</th>
                        <th class="rounded-q1">No tienen capacitaci&oacute;n</th>
                        <th class="rounded-q1">Total</th>
                        <th class="rounded-q4">PTC que est&aacute;n aplicando estos enfoques</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: El total deberá de coincidir con el indicador No. 23</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_24" value="<%=val24%>" onblur="indicador20(this,24,27,6);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_25" value="<%=val25%>"  onblur="indicador20(this,24,27,6);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  name="val_26" value="<%=val26%>"  onblur="indicador20(this,24,27,6);"/></td>
                    <td><input type="text" style="width:90%" name="total6"  readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input maxlength="6" style="width:90%" name="val_27" value="<%=val27%>"  onblur="indicador20(this,0,0,0);"/></td>
                </tr>
            </table>
        </div>
        <h2 onclick="operacionesPertinencia201(28,31,7);">Cuadro 20.7<br/>Cantidad de PA seg&uacute;n situaci&oacute;n en los enfoques</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">Terminaron la capacitaci&oacute;n</th>
                        <th class="rounded-q1">Est&aacute;n en proceso de capacitaci&oacute;n</th>
                        <th class="rounded-q1">No tienen capacitaci&oacute;n</th>
                        <th class="rounded-q1">Total</th>
                        <th class="rounded-q4">PA que est&aacute;n aplicando estos enfoques</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: El total deberá de coincidir con el indicador No. 24</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input maxlength="6" style="width:90%"  value="<%=val28%>"  name="val_28" onblur="indicador20(this,28,31,7);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  value="<%=val29%>"  name="val_29" onblur="indicador20(this,28,31,7);"/></td>
                    <td align="center"><input maxlength="6" style="width:90%"  value="<%=val30%>"  name="val_30" onblur="indicador20(this,28,31,7);"/></td>
                    <td><input type="text" style="width:90%" name="total7" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input maxlength="6" style="width:90%"  value="<%=val31%>"  name="val_31" onblur="indicador20(this);"/></td>
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
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=20";
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
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarIndicador20(),'PertinenciaIn20Datos');">Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
    <html:hidden  property="valores" styleId="valores" />
</html:form>
<%
} catch (NullPointerException exNull) {
%>
<div id="pert20" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<% }
%>

