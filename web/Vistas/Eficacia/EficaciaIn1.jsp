<%--
    Document   : EficaciaIn1
    Created on : 14/05/2012, 01:43:12 PM
    Author     : Cuauhtemoc Medina Mu�oz
    Actualizaci�n 2016: Salvador Zamora Arias
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<% try {
%>
<html:form action="/EficaciaIn1" >    
    <%
        HttpSession sesion = request.getSession(false);
        int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
          
        ConexionMySQL con = new ConexionMySQL();
        ArrayList datosError = new ArrayList();
        ResultSet rs = null;
        String v1 = "0";
        String v2 = "0";
        String v3 = "0";
        String v4 = "0";
        String v5 = "0";
        String comentarios="Sin comentarios";
        String nuevo_in = "0";
        String aplica = "1";
        try {
            String consulta = "select egresados_bachillerato, nuevo_ingreso, c1, c2, c3,aplica, comentario from eficaciain1 where id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));
            rs = con.Consultar(consulta);
            while (rs.next()) {
                v1 = rs.getString("egresados_bachillerato");
                v2 = rs.getString("nuevo_ingreso");
                v3 = rs.getString("c1");
                v4 = rs.getString("c2");
                v5 = rs.getString("c3");
                comentarios = rs.getString("comentario");
                aplica = rs.getString("aplica");
            }

            consulta = "select nuevo_ingreso from datos_universidad where id_universidad=".concat(String.valueOf(idUniversidad)).concat(" and id_periodo=").concat(String.valueOf(idPeriodo));
            rs = con.Consultar(consulta);
            while (rs.next()) {
                nuevo_in = rs.getString("nuevo_ingreso");
            }
        } catch (SQLException ex) {
            System.err.println("Error en consulta eficaciain1: " + ex);
            datosError.add("Error en consulta eficaciain1:");
            datosError.add(ex);
        } catch (Exception ex) {
            System.err.println("Error en jsp eficaciain1: " + ex);
            datosError.add("Error en jsp eficaciain1:");
            datosError.add(ex);
        } finally {
            rs.close();
            rs = null;
            con.Desconectar();
        }
    %>
    
    <span id="seRealizoEXANI" style="display:none;" ><!--SE OCULTO YA QUE TODAS LAS UNIVERSIDADES TIENEN QUE LLENARLO-->
        <h2>Indique si se aplic&oacute; el EXANI</h2>
        <div id="radios1" align="center" >
            <input type="radio" id="radio1" name="radio" value="1" onclick="checkEficaciaIn1();" checked='checked'/> <label for="radio1" >Si se aplic&oacute;</label>
            <input type="radio" id="radio2" name="radio" value="0" onclick="checkEficaciaIn1();" /><label for="radio2" >No se aplic&oacute;</label>
        </div>
    </span>
    <br/>
    <div id="efi1" style="display: none;" align="center">
        <h2>Cuadro 1.1<br/>Alumnos de Nuevo Ingreso con EXANI II</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q1">3</th>
                        <th class="rounded-q1">4</th>
                        <th class="rounded-q1">5</th>
                        <th class="rounded-q1">6</th>
                        <th class="rounded-q4">7</th>
                    </tr>
                    <tr>
                        <th rowspan="2" align="center" class="rounded-q1">Egresados de bachillerato que presentaron el EXANI - II en la UT</th>
                        <th rowspan="2" align="center" class="rounded-q1">Alumnos de nuevo ingreso a la UT</th>
                        <th colspan="3" align="center" class="rounded-q1">Rango de Calificaciones Obtenidas</th>
                        <th rowspan="2" align="center" class="rounded-q1">Alumnos de nuevo ingreso inscritos a la universidad que presentaron el EXANI II en el ciclo escolar</th>
                        <th rowspan="2" align="center" class="rounded-q1">Alumnos de nuevo ingreso inscritos a la universidad que no presentaron EXANI - II</th>
                    </tr>
                    <tr>
                        <td align="center">Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 1,101 a 1,300 puntos de calificaci&oacute;n</td>
                        <td align="center">Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 901 a 1,100 puntos de calificaci&oacute;n</td>
                        <td align="center">Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 700 a 900 puntos de calificaci&oacute;n</td>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales
                        </td>
                        <td  class="rounded-q1">

                        </td>
                        <td colspan="5" class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v1%>" property="i1v1" styleId="i1v1" name="EficaciaIn1Form" onblur="indicador1(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="border:none; background-color:#e8edff; border-style:none; text-align:center; font-weight: bold; width:90%;" value="<%=nuevo_in%>" property="i1v2" readonly="true" styleId="i1v2" name="EficaciaIn1Form" onblur="indicador1(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v3%>" property="i1v3" styleId="i1v3" name="EficaciaIn1Form" onblur="indicador1(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v4%>" property="i1v4" styleId="i1v4" name="EficaciaIn1Form" onblur="indicador1(this);"/></td>
                    <td align="center"><html:text maxlength="6" style="width:90%" value="<%=v5%>" property="i1v5" styleId="i1v5" name="EficaciaIn1Form" onblur="indicador1(this);"/></td>
                    <td><input type="text" style="width:90%" name="alumnosExaniNI" value="0" readonly="readonly" id="etiqueta"></td>
                    <td><input type="text" style="width:90%" name="alumnosNoExaniNI" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                <tr>
                    <td align="center">ANI1<br/>6/1</td>
                    <td align="center">ANI2<br/>6/2</td>
                    <td align="center">ANI3<br/>3/6</td>
                    <td align="center">ANI4<br/>4/6</td>
                    <td align="center">ANI5<br/>5/6</td>
                    <td rowspan="2" align="center">&nbsp;</td>
                    <td rowspan="2" align="center">&nbsp;</td>
                </tr>
                <tr>
                    <td align="center"><input type="text" name="i1ani1" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type="text" name="i1ani2" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type="text" name="i1ani3" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type="text" name="i1ani4" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type="text" name="i1ani5" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                <tr>
                    <th colspan="2" align="center" class="rounded-q1">Comentario</th>
                    <th colspan="5" align="left" class="rounded-q1">
                        <html:text maxlength="500" style="width:90%" value="<%=comentarios%>" property="comentario" styleId="comentario" name="EficaciaIn1Form" />
                    </th>
                 
                </tr>
                    
            </table>
        </div>
    </div>
    

    <%
        String consulta = "select activo from system_mecasut";
        rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
    %>
    
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardar1(),'EficaciaIn1Datos','EficaciaIn1Form');" >Guardar</button>
    <%            }
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }
    %>
    <input name="fin" type="hidden"/>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
<div id="efis1" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
