<%-- 
    Document   : EficienciaIn14
    Created on : 31/05/2012, 02:35:05 PM
    Author     : Cuauhtemoc Medina
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!--OBTENER LA SESION-->
<%
    try {
        HttpSession sesion = request.getSession(false);
        int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
        int[] indicador14 = new int[10];
        String prof_tc = "0";
        String prof_asigna = "0";
        for (int i = 0; i <= 9; i++) {
            indicador14[i] = 0;
        }

        ConexionMySQL con = new ConexionMySQL();

        try {

            String consulta = "SELECT * FROM eficienciain14 WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));
            ResultSet rs = con.Consultar(consulta);
            String consulta1 = "SELECT prof_tc,prof_asig FROM datos_universidad WHERE id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));
            ResultSet rs1 = con.Consultar(consulta1);
            if (rs1.next()) {
                prof_tc = rs1.getString("prof_tc");
                prof_asigna = rs1.getString("prof_asig");
            }
            while (rs.next()) {
                indicador14[0] = rs.getInt(3);
                indicador14[1] = rs.getInt(4);
                indicador14[2] = rs.getInt(5);
                indicador14[3] = rs.getInt(6);
                indicador14[4] = rs.getInt(7);
                indicador14[5] = rs.getInt(8);
                indicador14[6] = rs.getInt(9);
                indicador14[7] = rs.getInt(10);
                indicador14[8] = rs.getInt(11);
                indicador14[9] = rs.getInt(12);
            }

        } catch (SQLException ex) {
            System.err.println("jsp Error eficiencia 12: " + ex);
        } finally {
            con.Desconectar();
        }

%>

<!--TERMINA OBTENER LA SESION-->
<html:form action="/EficienciaIn14">
    <div id="efic14">
        <h3>Cuadro 14.1<br/>Utilizaci&oacute;n del Equipo de C&oacute;mputo</h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th width="16%" scope="col" class="rounded-company"></th>
                        <th width="68"class="rounded-q1" colspan="4" >Distribuci&oacute;n del Equipo de C&oacute;mputo</th>
                        <th width="16%" class="rounded-q4"></th>
                    </tr>
                    <tr>
                        <th width="" class="rounded-q1">Total de PC's</th>
                        <th class="rounded-q1">Docente de Tiempo Completo</th>
                        <th class="rounded-q1">Docente de Asignatura</th>
                        <th class="rounded-q1">Alumnos</th>
                        <th class="rounded-q1">Personal Administrativo</th>
                        <th class="rounded-q1">Mandos Medios y Superiores</th>
                    </tr>
                </thead>
                <tr>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="pcs1" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="completo1" value="<%=indicador14[0]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="asignatura1" value="<%=indicador14[1]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="alumnos1" value="<%=indicador14[2]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="personal1" value="<%=indicador14[3]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="mandos1" value="<%=indicador14[4]%>" onblur="EficienciaIn14(this)"></td>
                </tr>
                <tr>
                    <td align="center"><input style="width:90%" type="text" name="total1" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" type="text" name="total2" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" type="text" name="total3" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" type="text" name="total4" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" type="text" name="total5" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" type="text" name="total6" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                <tr>
                    <th width="68"class="rounded-q1" colspan="6" >Distribuci&oacute;n del Equipo de C&oacute;mputo con Internet</th>
                </tr>
                <tr>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="pcs2" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="completo2" value="<%=indicador14[5]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="asignatura2" value="<%=indicador14[6]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="alumnos2" value="<%=indicador14[7]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="personal2" value="<%=indicador14[8]%>" onblur="EficienciaIn14(this)"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="mandos2" value="<%=indicador14[9]%>" onblur="EficienciaIn14(this)"></td>
                </tr>
                <tr>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="total7" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="total8" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="total9" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="total10" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="total11" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input style="width:90%" maxlength="7" type="text" name="total12" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                <tfoot>
                    <tr>
                        <td colspan="5" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
    <input type="hidden" name="prof_tc" value="<%=prof_tc%>" />
    <input type="hidden" name="prof_asigna" value="<%=prof_asigna%>" />
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=14";
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
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarEficIn14(),'EficienciaIn14Datos','EficienciaIn14Form');"  >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
<div id="efic14" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>