<%--
    Document   : PertinenciaIn21_Cuerpo
    Created on : 1/03/2014, 010:54:34 AM
    Author     : Cuauhtemoc Medina Muñoz, Daniel Ramirez Torres
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
    try {
        //Recuperamos la sesión para obtener los datos
        HttpSession sesion = request.getSession(false);
        ConexionMySQL con = new ConexionMySQL();
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();
        String idServicio = request.getParameter("idServicio");
        int poblacion = Integer.parseInt(request.getParameter("poblacion"));
        int muestreo = 0;
         String query = "";
        ResultSet rs;
        int noPreg;
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
       
        if (Integer.parseInt(idServicio) < 0) {
%>
<table id='rounded-corner' style='width: 100%'>
    <thead>
        <tr>
            <th rowspan="2" class="rounded-company">No.</th>
            <th rowspan="2" class="rounded-q1">Servicio</th>
            <th colspan="2" class="rounded-q4">Calificacion</th>
        </tr>
        <tr>
            <td>Escala 5</td>
            <td>Escala 10</td>
        </tr>
    </thead>
    <%
        double escala=0.0;
        query = "SELECT u.id_servicio, s.nombre FROM servicios_universidad u INNER JOIN encuesta_servicios s ON u.id_servicio=s.id_servicio WHERE"
                .concat(" id_universidad=").concat(idUniversidad)
                .concat(" AND id_periodo=").concat(idPeriodo);
        rs = con.Consultar(query);
        ArrayList<Integer> serv = new ArrayList<Integer>();
        ArrayList<String> nomServ = new ArrayList<String>();
        while (rs.next()) {
            serv.add(rs.getInt("id_servicio"));
            nomServ.add(rs.getString("nombre"));
        }
        noPreg = 1;
        for (int i = 0; i < serv.size(); i++) {
            query = "SELECT e.nombre as 'NombreServicio', (SUM(r_a)*5 + SUM(r_b)*4 + SUM(r_c)*3 + SUM(r_d)*2+ SUM(r_e)) /"
                    .concat("(SUM(r_a) + SUM(r_b) + SUM(r_c) + SUM(r_d)+ SUM(r_e)) as 'escala'")
                    .concat(" FROM pertinenciain21, encuesta_servicios e WHERE")
                    .concat(" id_universidad=").concat(idUniversidad)
                    .concat(" AND id_periodo=").concat(idPeriodo)
                    .concat(" AND id_pregunta IN(SELECT id_pregunta FROM encuesta_preguntas WHERE")
                    .concat(" id_servicio=").concat(String.valueOf(serv.get(i))).concat(")");
            rs = con.Consultar(query);
            if (rs.next()) {
                escala += rs.getDouble("escala");
    %>
                <tr>
                    <td><%=noPreg%></td>
                    <td><%=nomServ.get(i)%></td>
                    <td><%=Math.round(rs.getDouble("escala") *10)/10.0%></td>
                    <td><%=Math.round((rs.getDouble("escala")*2) *10)/10.0%></td>
                </tr>
                
    <%
            }
            noPreg++;
        }
        escala = Math.round((escala/(noPreg-1))*10)/10.0;
    %>
    <tr>
        <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>Total de Servicios</td>
        <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="TotalFinal1" type="text" value="<%=escala%>"/> </td>
        <td align='center'><input readonly="readonly" id="etiqueta" style="width:90%" name="TotalFinal2" type="text" value="<%=escala*2%>"/> </td>
        
    </tr>
    <tfoot>
        <tr>
            <td colspan='3' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>
            <td class='rounded-foot-right'></td>
        </tr>
    </tfoot>
</table>
<%} else {
%>
<input type="hidden" value="<%=muestreo%>" name="fin" />
<table id='rounded-corner' style='width: 100%'>
    <thead>
        <tr>
            <th rowspan='3' class='rounded-company'>#</th>
            <th rowspan='3' class='rounded-q1'>DESCRIPCIÓN</th>
            <th colspan='10' class='rounded-q1'>FRECUENCIAS</th>
            <th rowspan='2' class='rounded-q1'>TOTAL K, BASE 5</th>
            <th rowspan='3' class='rounded-q4'>TOTAL K, BASE 10</th>
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
        //FILAS DE LA TABLA, PREGUNTAS
        query = "SELECT id_pregunta, pregunta FROM encuesta_preguntas WHERE id_encuesta=3 AND id_servicio=".concat(idServicio);
        rs = con.Consultar(query);
        noPreg = 1;
        String respuestas = "";
        ResultSet resp;
        while (rs.next()) {//FILAS O PREGUNTAS QUE TENDRÁ LA TABLA, DEPENDIENDO DEL SERIVICIO
            respuestas = "SELECT r_a, r_b, r_c, r_d, r_e, r_f, r_g FROM pertinenciain21 WHERE id_pregunta="
                    .concat(rs.getString("id_pregunta").concat(" and id_universidad=")
                    .concat(String.valueOf(idUniversidad)).concat(" and id_periodo=")
                    .concat(String.valueOf(idPeriodo)));
            resp = con.Consultar(respuestas);
            if (resp.next()) {
    %>
    <tr>
        <td width="5%" align="center"><%=noPreg%></td>
        <td width="21%" align="center"><%=rs.getString("pregunta")%></td>
        <td width="6%" align="center"><input id="no_preA,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="<%=resp.getString(1)%>" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preB,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="<%=resp.getString(2)%>" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preC,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="<%=resp.getString(3)%>" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preD,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="<%=resp.getString(4)%>" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preE,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="<%=resp.getString(5)%>" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preF,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="<%=resp.getString(6)%>" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preG,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="<%=resp.getString(7)%>" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preH,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text" name="<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preI,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text" name="<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preJ,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text" name="<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preK_5,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text" name="<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preK_10,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text" name="<%=rs.getString("id_pregunta")%>" value="0"/></td>
    </tr>
    
    <%
        noPreg++;
    } else {
    %>
    <tr>
        <td width="5%" align="center"><%=noPreg%></td>
        <td width="21%" align="center"><%=rs.getString("pregunta")%></td>
        <td width="6%" align="center"><input id="no_preA,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="0" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preB,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="0" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preC,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="0" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preD,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="0" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preE,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="0" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preF,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="0" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preG,<%=idServicio%>,<%=noPreg%>" maxlength="6" style="width:90%" value="0" name="<%=rs.getString("id_pregunta")%>" onblur="indicador21(this,<%=idServicio%>,<%=noPreg%>,0,1);" type="text"/></td>
        <td width="6%" align="center"><input id="no_preH,<%=idServicio%>,<%=noPreg%>" class="inputok"  readonly="readonly" style="width:90%" type="text"  name="th1_<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preI,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text"   name="th2_<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preJ,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text"   name="th3_<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preK_5,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text" name="th4_<%=rs.getString("id_pregunta")%>" value="0"/></td>
        <td width="6%" align="center"><input id="no_preK_10,<%=idServicio%>,<%=noPreg%>" class="inputok" readonly="readonly" style="width:90%" type="text"name="th5_<%=rs.getString("id_pregunta")%>" value="0"/></td>
    </tr>
    
     
    <%
                noPreg++;
            }
        }
        //PIE DE LA TABLA O TOTALES
    %>
    <input type="hidden" id="noProg<%=idServicio%>" value="<%=noPreg - 1%>" />
    <tr>
        <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>TOTAL</td>
        <td align='center'><input id="TotalA,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0" onblur="operaciones_servicios(<%=idServicio%>,0,0,0);"/> </td>
        <td align='center'><input id="TotalB,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalC,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalD,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalE,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalF,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalG,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalH,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalI,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalJ,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalK_5,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalK_10,<%=idServicio%>" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
    </tr>
     <tr>
      <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>DISTRIBUCIÓN PORCENTUAL</td>
        <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>MS</td>
        <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>S</td>
        <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>RS</td>
        <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>PS</td>
        <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NS</td>
        <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NA</td>
        <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NE</td>

       <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>
       <td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>TES</td>
       <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>
       <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>
       <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>
   </tr>
 
     <tr>
            <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'></td>

            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-1-d-ms' type='text'/></td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-2-d-s' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-3-d-rs' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-4-d-ps' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-5-d-ns' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-6-d-na' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-7-d-ne' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-8-d' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-9-d-tes' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-10-d' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-11-d' type='text'/> </td>
            <td align='center'><input class="inputok" readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_<%=idServicio%>-12-d' type='text'/> </td>
        </tr>
        <tr>
            <td><input type="hidden" id="nopreguntas-<%=idServicio%>" value="<%=noPreg-1%>"></td>
        </tr>
   
    <%if (rs.isLast()) {%>
    <tr>
        <td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>TOTALES</td>
        <td align='center'><input id="TotalA" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalB" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalC" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalD" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalE" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalF" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalG" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalH" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalI" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalJ" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalK_5" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
        <td align='center'><input id="TotalK_10" class="inputok" readonly="readonly" style="width:90%" type="text" value="0"/> </td>
    </tr>
   

    <%}%>
    <tfoot>
        <tr>
            <td colspan='13' class='rounded-foot-left'>Notas: Se encuestará al menos a <%=muestreo%> personas, ya que lo poblacion total es de <%=poblacion%>. Atención, por lo anterior la columna 'H' debe coincidir con la cantidad mencionada.</td>
            <td class='rounded-foot-right'></td>
        </tr>
    </tfoot>
</table>
<br/>
<%
    String consulta_Activo = "select activo from system_mecasut";
    ResultSet rs_ac = con.Consultar(consulta_Activo);
    rs_ac.next();
    if (rs_ac.getInt("activo") == 1) {
        if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
            if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
%>
<section style='text-align: right;'> <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(operaciones_servicios(<%=idServicio%>,0,1),'PertinenciaIn21Datos','PertinenciaIn21Form');" >Guardar</button></section>         
<%}
    }
} else {%>
<section style="text-align: right;"><button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button></section>
<%        }
    }%>    

<script type="text/javascript">
    $(document).ready(function() {
        $(".btnGuardarIndicador").button({
            icons: {
                primary: "ui-icon-disk"
            }
        });
        $(".btnGuardarIndicador").css("align", "right");
        $(".btnGuardarIndicador").css("bottom", "10px");
        $(".btnGuardarIndicador").css("z-index", "1000");
        $("input[id='TotalA,<%=idServicio%>']").blur();
    });
    
</script>

<%
} catch (NullPointerException exNull) {
%>
La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
<%    }
%>

