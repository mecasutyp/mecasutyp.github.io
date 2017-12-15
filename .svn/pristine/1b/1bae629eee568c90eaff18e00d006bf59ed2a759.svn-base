<%-- 
    Document   : PertinenciaIn20
    Created on : 2/10/2012, 12:46:54 PM
    Author     : Joshua
--%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page import="com.mysql.jdbc.MySQLConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:form action="/PertinenciaIn20">
    <%
        ConexionMySQL con = new ConexionMySQL();
        HttpSession sesion = request.getSession(false);
        int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());

        String sql = "SELECT prof_tc, prof_asig from datos_universidad WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));
        ResultSet prof = con.Consultar(sql);
        prof.next();
    %>
    <div id="pert20">
        <h2>Cuadro 20.1<br/>Cantidad de acciones que ha realizado la instituci&oacute;n durante el periodo a evaluar</h2>
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
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_11" styleId="p20_11" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_12" styleId="p20_12" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_13" styleId="p20_13" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_14" styleId="p20_14" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_15" styleId="p20_15" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td><input type="text" style="width:90%" name="total1" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2>Cuadro 20.2<br/>Cantidad de  Profesores de Tiempo Completo (PTC) que han participado en las acciones durante el periodo a evaluar</h2>
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
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_21" styleId="p20_21" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_22" styleId="p20_22" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_23" styleId="p20_23" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_24" styleId="p20_24" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_25" styleId="p20_25" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td><input type="text" style="width:90%" name="total2" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2>Cuadro 20.3<br/>Cantidad de Profesores de Asignatura (PA) que han participado en las acciones durante el periodo a evaluar</h2>
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
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_31" styleId="p20_31" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_32" styleId="p20_32" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_33" styleId="p20_33" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_34" styleId="p20_34" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_35" styleId="p20_35" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td><input type="text" style="width:90%" name="total3" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2>Cuadro 20.4<br/>Cantidad de material de los Enfoque Centrados en el Aprendizaje</h2>
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
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_41" styleId="p20_41" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_42" styleId="p20_42" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_43" styleId="p20_43" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td><input type="text" style="width:90%" name="total4" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
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
                <tr>
                    <td align="center"><html:checkbox property="p20_51" styleId="p20_51" name="PertinenciaIn20Form"/><label for="p20_51">Si</label></td>
                    <td align="center"><html:checkbox property="p20_52" styleId="p20_52" name="PertinenciaIn20Form"/><label for="p20_52">Si</label></td>
                    <td align="center"><html:checkbox property="p20_53" styleId="p20_53" name="PertinenciaIn20Form"/><label for="p20_53">Si</label></td>
                    <td align="center"><html:checkbox property="p20_54" styleId="p20_54" name="PertinenciaIn20Form"/><label for="p20_54">Si</label></td>
                    <td align="center"><html:text maxlength="25" style="width:90%" property="p20_55" styleId="p20_55" name="PertinenciaIn20Form"/></td>
                </tr>
            </table>
        </div>
        <h2>Cuadro 20.6<br/>Cantidad de PTC seg&uacute;n situaci&oacute;n en los enfoques</h2>
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
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_61" styleId="p20_61" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_62" styleId="p20_62" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_63" styleId="p20_63" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td><input type="text" style="width:90%" name="total6" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_64" styleId="p20_64" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                </tr>
            </table>
        </div>
        <h2>Cuadro 20.7<br/>Cantidad de Profesores de Asignatura seg&uacute;n situaci&oacute;n en los enfoques</h2>
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
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales</td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_71" styleId="p20_71" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_72" styleId="p20_72" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_73" styleId="p20_73" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                    <td><input type="text" style="width:90%" name="total7" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" property="p20_74" styleId="p20_74" name="PertinenciaIn20Form" onblur="indicador20(this);"/></td>
                </tr>
            </table>
        </div>
    </div>
    <%
        String consulta = "select activo from system_mecasut";
        ResultSet rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")){
    %>
    <button type="button" class="btnGuardarIndicador" onclick="guardar20a();" >Guardar</button>
    <%}}
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
    <input name="fin" type="hidden"/>
</html:form>