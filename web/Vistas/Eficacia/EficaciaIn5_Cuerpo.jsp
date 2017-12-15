<%--
    Document   : EficaciaIn5
    Created on : 14/05/2012, 01:47:34 PM
    Author     : Cuauhtemoc Medina Mu�oz, Daniel Ramirez Torres
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
    try {
        //Recuperamos la sesi�n para obtener los datos
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();
        String anio = request.getParameter("anio");
        String nivel = request.getParameter("nivel");
        String tipo = request.getParameter("tipo");
        int numAnio = 0;

        int c = 1;
        int numCuadro = 1;
        int numMo = 1;
        int nom = 1;
        ConexionMySQL con = new ConexionMySQL();


        //Obtenemos el a�o actual
        String sql = "Select anio from periodos where id_periodo = ".concat(idPeriodo);
        ResultSet rsAnio = con.Consultar(sql);
        rsAnio.next();
     //   if (Integer.parseInt(nivel) != 3){
            if (rsAnio.getInt(1) == Integer.parseInt(anio)){
                numAnio = 1;
            } else {
                numAnio = 2;
            }
       // } 
    /*   else {
            numAnio = 2;

        }*/
        if (tipo.equals("1")){
            //Obtenemos los niveles con los que cuenta la universidad y hacemos un acordeon por nivel
            sql = "Select descripcion from nivel_pe where id_nivel=" + nivel;
            ResultSet rsNivel = con.Consultar(sql);
            rsNivel.next();
            int cuatri=3;
            %>
            
             <% if(nivel.equals("1") || nivel.equals("3") ){
                    String sql4 = "select cuatri from eficaciain5 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(nivel).concat(" and anio="+numAnio);
                     ResultSet rscuatri = con.Consultar(sql4);
                     
                     
                     if(rscuatri.next()){
                         cuatri=rscuatri.getInt("cuatri");
                     }
             %>
             <table style='width: 100%; height: 100;'>
                <tr>
                    
                    <th >Enero - Abril <input size="6" type="radio" id="radiocuatri<%=numCuadro%><%=numAnio%><%=1%>" name="radiocuatri<%=numCuadro%><%=numAnio%>" value="1" <%if(cuatri==1){%>checked="checked"<%}%>/>    </th> 
                    <th >Mayo - Agosto <input type="radio" id="radiocuatri<%=numCuadro%><%=numAnio%><%=2%>" name="radiocuatri<%=numCuadro%><%=numAnio%>" value="1" <%if(cuatri==2){%>checked="checked"<%}%>/></th>
                    <th >Septiembre - Diciembre <input type="radio" id="radiocuatri<%=numCuadro%><%=numAnio%><%=3%>" name="radiocuatri<%=numCuadro%><%=numAnio%>" value="1" <%if(cuatri==3){%>checked="checked"<%}%>/></th>
                </tr>   
             </table>
         <%}%> 
            <%
            for (int j = 1; j <= 2; j++) {
%>
<table id='rounded-corner' style='width: 100%; height: 50;'>
    
    <thead>
             
        <tr>
            <%if (j == 1) { %>
            <th rowspan='3' class='rounded-company'>No.</th>
            <th rowspan='3'>Carrera</th>
            <th colspan='9' class='rounded-q4'>Egresados de <%=rsNivel.getString("descripcion")%></th>
            <%} else {%>
            <th rowspan='3' class='rounded-q1'>No.</th>
            <th rowspan='3'>Carrera</th>
            <th colspan='9' class='rounded-q4'>Titulados de <%=rsNivel.getString("descripcion")%></th>
            <%}%>
        </tr>
        <tr>
            <%
                int numeromodelos = 0;
                String sql2 = "Select mu.id_modelo, m.descripcion from modelos_universidad mu inner join modelos m on mu.id_modelo = m.id_modelo where mu.id_universidad = ".concat(idUniversidad).concat(" and mu.id_periodo = ").concat(idPeriodo).concat(" and mu.id_nivel = ").concat(nivel);
                ResultSet rsModelo = con.Consultar(sql2);
                while (rsModelo.next()) {
            %>
                    <td colspan='2'><%=rsModelo.getString(2)%></td>
            <%
                numeromodelos += 1;
                }
                if (j == 1) {
            %>
            <th colspan='2'>Total</th>
            <th rowspan='2'>Tasa de Egreso</th>
            <%} else {%>
            <th colspan='2'>Total</th>
            <th rowspan='2'>Tasa de Titulaci&oacute;n</th>

            <%}%>
        </tr>
        <tr>
            <%

                rsModelo.beforeFirst();
                while (rsModelo.next()) {
            %>   
            <td>Ingreso</td>
            <%if (j == 1) {%>
            <td>Egreso</td>
            <%} else {%>
            <td>Titulaci&oacute;n</td>
            <%}%>
            <%                             nom++;
                }
            %>
            <%if (j == 1) {%>
            <td>Ingreso</td>
            <td>Egreso</td>
            <%} else {%>
            <td>Ingreso</td>
            <td>Titulaci&oacute;n</td>
            <%}%>
        </tr>
    </thead>
    <tbody>
        
        <%
            String sql3 = "select pu.id_pe, nombre_programa,version from programa_educativo pe inner join pe_universidad pu on pe.id_pe = pu.id_pe where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(nivel);
            ResultSet rsProgEdu = con.Consultar(sql3);
            int no = 1;
            while (rsProgEdu.next()) {
        %>
        <tr>
            <td align='center'><%=String.valueOf(no)%></td>
            <td align='center'><%=rsProgEdu.getString("nombre_programa")%> (<%=rsProgEdu.getString("version")%>)</td>

            <%
                rsModelo.beforeFirst();
                nom = 1;
                while (rsModelo.next()) {

            %>
    <input type='hidden' name='id_prog<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='<%=rsProgEdu.getString(1)%>' >
    <input type='hidden' name='id_mod<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='<%=rsModelo.getString(1)%>' >

    <%

        sql = "select ingreso, egresados from eficaciain5 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(nivel).concat(" and id_pe=").concat(rsProgEdu.getString(1)).concat(" and id_modelo=").concat(rsModelo.getString(1)).concat(" and anio=").concat(String.valueOf(numAnio));
        ResultSet rsVal = con.Consultar(sql);
        if (rsVal.next()) {
            //El primer valor indicado el numero de cuadro o en su caso el numero de acordeon,
            //El segundo valor indica el numero de modelo
            //El tercer valor indica el numero de tabla
            //El cuarto valor indica el numero de programa educativo
            String tit = "0";
            if (j == 1) {
    %>

<!--Olasff Aqui se debe de realizar el cambio-->
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='in_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='<%=rsVal.getString(1)%>' onblur='indicador5(this,<%=numCuadro%>,<%=numeromodelos%>,<%=j%>,<%=no%>,1,0,1);' ></td>
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='eg_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='<%=rsVal.getString(2)%>' onblur='indicador5(this,<%=numCuadro%>,<%=numeromodelos%>,<%=j%>,<%=no%>,1,0,1);' ></td>
        <%} else {
            sql = "select titulados from eficaciain5 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(nivel).concat(" and id_pe=").concat(rsProgEdu.getString(1)).concat(" and id_modelo=").concat(rsModelo.getString(1)).concat(" and anio=").concat(String.valueOf(numAnio));
            ResultSet rsValt = con.Consultar(sql);
            if (rsValt.next()) {
                tit = rsValt.getString(1);
            }

        %>
    <td align='center'><input maxlength='6' id="etiqueta" readonly="readonly" style='width:90%' type='text' name='in_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='<%=rsVal.getString(1)%>' onblur='indicador5(this,<%=numCuadro%>,<%=numeromodelos%>,<%=j%>,<%=no%>,1,0,1);' ></td>
    <td align='center'><input maxlength='6'  style='width:90%' type='text' name='eg_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='<%=tit%>' onblur='indicador5(this,<%=numCuadro%>,<%=numeromodelos%>,<%=j%>,<%=no%>,1,0,1);' ></td>
        <%
            }
        } else {
            if (j == 1) {
        %>
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='in_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='0' onblur='indicador5(this,<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>,1,0,1);' ></td>
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='eg_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='0' onblur='indicador5(this,<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>,1,0,1);' ></td>
        <%
        } else {

        %>
    <td align='center'><input maxlength='6' id="etiqueta" readonly="readonly" style='width:90%' type='text' name='in_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='0' onblur='indicador5(this,<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>,1,0,1);' ></td>
    <td align='center'><input maxlength='6'  style='width:90%' type='text' name='eg_<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>' value='0' onblur='indicador5(this,<%=numCuadro%>,<%=nom%>,<%=j%>,<%=no%>,1,0,1);' ></td>
        <%
                    }
                }
                nom++;
            }
        %>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='t_ing_<%=numCuadro%>,<%=j%>,<%=no%>' value='0'></td>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='t_egr_<%=numCuadro%>,<%=j%>,<%=no%>' value='0'></td>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='ta_egr_<%=numCuadro%>,<%=j%>,<%=no%>' value='0'></td>
</tr>
<%
        no++;
    }%>
<tr>
    <td colspan='2' align='center' style='font-weight: bold'>Total</td>
    <%
        rsModelo.beforeFirst();
        nom = 1;
        while (rsModelo.next()) {
    %>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='tot_ingreso_niv<%=nom%>,<%=j%>' value='0'></td>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='tot_egreso_niv<%=nom%>,<%=j%>' value='0'></td>
        <%  nom++;
            }
        %>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='ingreso_<%=numCuadro%>,<%=j%>' value='0'></td>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='egreso_<%=numCuadro%>,<%=j%>' value='0'></td>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='tasaTot_<%=numCuadro%>,<%=j%>' value='0'></td>
</tr>
</tbody> 
</table>

<input maxlength='6' style='width:90%' type='hidden' name='tot_p<%=numCuadro%>' value='<%=no%>' >
<input maxlength='6' style='width:90%' type='hidden' name='tot_mo<%=numCuadro%>' value='<%=nom%>' >
<input maxlength='6' style='width:90%' type='hidden' name='id_niv<%=numCuadro%>' value='<%=nivel%>' >
<%}%>

<%
    String sqlmodelos = "select count(id_modelo) from modelos where id_modelo in (select id_modelo from modelos_universidad where id_universidad = " + idUniversidad +"  and id_periodo = " + idPeriodo + " group by id_modelo) and activo = 1";
    ResultSet rsModelos = con.Consultar(sqlmodelos);
    int nummodelos = 0;
    while (rsModelos.next()) 
    {
         nummodelos = Integer.parseInt(rsModelos.getString(1));

    }
 %>
    <input style="width: 90%" type="hidden" name="numeromodelos" value='<%=nummodelos%>' >

</div>
<%
    String consulta = "select activo from system_mecasut";
    ResultSet rs = con.Consultar(consulta);
    rs.next();
    if (rs.getInt("activo") == 1) {
        if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
            if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
%>
<div style="width: 100%; text-align: right; padding-top: 20px;">
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(operacionesIn5(0,<%=numCuadro%>,<%=numAnio%>,0,0,0,1),'EficaciaIn5Datos','EficaciaIn5Form');">Guardar</button>
</div>
<%}
    }
} else {%>
<div style="width: 100%; text-align: right; padding-top: 20px;">
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
</div>
<%        }%>

</div>

<%
    }
    if (tipo.equals("2")) {
        int anioUni = 1990;
        try {
            sql = "Select min(anio_incorporacion) from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe where pu.id_universidad = ".concat(idUniversidad).concat(" and pe.id_nivel = ").concat(nivel);
            ResultSet rsFAcred = con.Consultar(sql);
            rsFAcred.next();
            anioUni = rsFAcred.getInt(1);
            //System.err.println("de "+anioUni);
        } catch (Exception exep) {
            anioUni = 1990;
        }
%>
<table id='rounded-corner' style='width: 100%'>
    <thead>
        <tr>
            <th class='rounded-company'>No.</th>
            <th>Generaci&oacute;n</th>
            <th>Registro de Titulados por parte de la Universidad</th>
            <th>Registro de Titulados por parte de la D.G.P.</th>
            <th class='rounded-q4'>Porcentaje de registro ante la D.G.P.</th>
        </tr>
    </thead><tbody>
        <%
            int j = 1;
            for (; anioUni <= rsAnio.getInt(1) ; anioUni++) {
        %>
        <tr>

            <td align='center' style='font-weight: bold'><%=String.valueOf(j)%></td>
            <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' value='<%=String.valueOf(anioUni + 1)%>'></td>
    <input type='hidden' name='anio<%=j%>' value='<%=anioUni%>' >

    <%
        sql = "Select titulados_universidad, titulados_dgp from eficaciain5_1 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel = ").concat(nivel).concat(" and anio = ").concat(String.valueOf(anioUni));
        ResultSet rsTitulados = con.Consultar(sql);
        if (rsTitulados.next()) {
    %>
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='valU_<%=nivel%>,<%=j%>' value='<%=rsTitulados.getString(1)%>' onblur='indicador5(this,<%=nivel%>,0,0,0,0,0,2);' ></td>
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='valD_<%=nivel%>,<%=j%>' value='<%=rsTitulados.getString(2)%>' onblur='indicador5(this,<%=nivel%>,0,0,0,0,0,2);' ></td>
        <%  } else {%>
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='valU_<%=nivel%>,<%=j%>' value='0' onblur='indicador5(this,<%=nivel%>,0,0,0,0,0,2);' ></td>
    <td align='center'><input maxlength='6' style='width:90%' type='text' name='valD_<%=nivel%>,<%=j%>' value='0' onblur='indicador5(this,<%=nivel%>,0,0,0,0,0,2);' ></td>
        <%}%>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='porcentDGP_<%=nivel%>,<%=j%>' value='0'></td>
</tr>
<%
        j++;
    }
    if (j == 1) {
%>
<tr>
    <td colspan='5' align='center' style='font-weight: bold'>A&uacute;n no ha egresado ninguna generaci&oacute;n de este nivel</td>
</tr>
<%                        } else {%>
<tr>
    <td colspan='2' align='center' style='font-weight: bold'>Total</td>
    <td align='center'><input readonly="readonly" id="etiqueta" style='width:90%' type='text' name='totU_<%=nivel%>' value='0'></td>
    <td align ='center'><input readonly = "readonly" id = "etiqueta" style ='width:90%' type ='text' name ='totD_<%=nivel%>' value = '0' > </td> 
    <td align ='center'><input readonly = "readonly" id = "etiqueta" style ='width:90%' type ='text' name ='totPorcentDGP_<%=nivel%>' value='0'></td>
</tr>
<%                            }
%>
</tbody>
<tfoot><tr>
        <td colspan='4' class='rounded-foot-left'>&nbsp;Notas: No hay notas adicionales.</td>
        <td class='rounded-foot-right'></td>
    </tr>
</tfoot>
</table>
<%
    String consulta = "select activo from system_mecasut";
    ResultSet rs = con.Consultar(consulta);
    rs.next();
    if (rs.getInt("activo") == 1) {
        if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
            if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
%>

<div style="width: 100%; text-align: right; padding-top: 20px;">

    <button id="button2" type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(operacionesIn5_1(<%=nivel%>,1),'EficaciaIn5Datos','EficaciaIn5Form');">Guardar</button>
</div>

<%}
    }
} else {%>
<div style="width: 100%; text-align: right;">
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
</div>
<%        }%>
<input type='hidden' name='totalFil_<%=nivel%>' value='<%=j%>'>
<%
    }
} catch (NullPointerException exNull) {
%>
La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
<%            }
%>

<script type="text/javascript">
    $(document).ready(function() {
        $("form[name='EficaciaIn5Form'] .btnGuardarIndicador").button({
            icons: {
                primary: "ui-icon-disk"
            }
        });
        $("form[name='EficaciaIn5Form'] .btnGuardarIndicador").css("align", "right");
        $("form[name='+EficaciaIn5Form+'] .btnGuardarIndicador").css("bottom", "10px");
        $("form[name='+EficaciaIn5Form+'] .btnGuardarIndicador").css("z-index", "1000");
    });
    
</script>
