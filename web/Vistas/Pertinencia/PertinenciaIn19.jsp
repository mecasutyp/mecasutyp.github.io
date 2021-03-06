<%-- 
    Document   : PertinenciaIn19
    Created on : 26/09/2012, 07:53:10 AM
    Author     : Daniel Ram�rez Torres
    Actualizaci�n 2016: Salvador Zamora Arias
--%>

<%@page import="com.mecasut.shared.Universidad"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!--OBTENER LA SESION-->
<%
    try {
        HttpSession sesion = request.getSession(false);
        int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
         Universidad uni = new Universidad();
     String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
%>
<!--TERMINA OBTENER LA SESION-->

<html:form action="/PertinenciaIn19">
    <div id="pert19">
        <%
            ConexionMySQL con = new ConexionMySQL();
            int noProg = 1;
            int noProgb = 1;
            int noCuadroCalidad = 1;
            int cuadroa = 1;
            int cuadro = 1;
            int cuadrob = 1;
            int contador=0;
            int contador2=0;
            ResultSet anio=null;
            String Matricula = "0";
            try {
                String conanio = "select anio from periodos where id_periodo=".concat(sesion.getAttribute("idPeriodo").toString());
                anio = con.Consultar(conanio);
                String niveles = "Select distinct(pe.id_nivel),descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(") order by pe.id_nivel"));
                ResultSet niv = con.Consultar(niveles);
                String cons_org = "select id_organismo,nombre from organismos_acreditacion where activo=1 and extranjero='0' order by id_organismo";
                ResultSet organismos = con.Consultar(cons_org);
                String paises_org = "select id_organismo, pais from organismos_acreditacion where activo=1 and extranjero='1' order by id_organismo";
                ResultSet paises = con.Consultar(paises_org);
                String cons_orge = "select id_organismo,nombre,sigla from organismos_acreditacion where activo=1 and extranjero='1' order by id_organismo";
                ResultSet organismose = con.Consultar(cons_orge);
                String conmatricula = "select MATRICULA_TOTAL from datos_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()));
                ResultSet matricula = con.Consultar(conmatricula);
                if (matricula.next()) {
                    Matricula = matricula.getString(1);
                }
                String programas = "";
                ResultSet prog;
                String nombre_org = "";
                ResultSet name_org;
                String nombre_orge = "";
                ResultSet name_orge;
                while (niv.next()) {//CANTIDAD DE NIVELES QUE TIENE LA UNIVERSIDAD EN EL PERIODO ACTUAL
                    contador++;
        %>
        <h2 onclick="operacionesPertinencia19(<%=cuadro%>);">Cuadro 19.1.<%=niv.getString("id_nivel")%><br/>Relaci&oacute;n de Programas Educativos de <%=niv.getString("descripcion")%> Acreditados
            <input type="hidden" name="numeracion<%=cuadro%>" value="1.<%=niv.getString("id_nivel")%>" />
           <input type="hidden" name="cuadro1<%=niv.getString("id_nivel")%>" value="<%=cuadro%>" />
        </h2>
         
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th class="rounded-company">No.</th>
                        <th class="rounded-q1">Nombre del programa educativo</th>
                        <th class="rounded-q1">Matr&iacute;cula </br>inicial </br>atendida</th>
                        <th class="rounded-q1">Matr&iacute;cula acreditada</th>
                        <th class="rounded-q1">Acreditado</th>
                        <th size="11" class="rounded-q1">Nombre del organismo acreditador</th>
<!--                        <th class="rounded-q1">Pais</th>-->
                        <th class="rounded-q1">Vigencia de la acreditaci&oacute;n</th>
                        <th class="rounded-q4">Fecha de inicio de la acreditaci&oacute;n</th>
                    </tr>
                </thead>

                <%
                    String matricula_t = "select matricula from mat_total_universidad where id_universidad=".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(" and id_nivel=").concat(niv.getString(1)));
                    ResultSet resmat = con.Consultar(matricula_t);
                    while (resmat.next()) {
                %>
                <input  type = "hidden" name ='mat<%=cuadro%>' value='<%=resmat.getString(1)%>' />

                <%
                    }
                    programas = "SELECT id_pe,nombre_programa,version FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(niv.getString(1))).concat(" AND id_pe IN (SELECT id_pe FROM pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(")")));
                    prog = con.Consultar(programas);
                    while (prog.next()) { 
                       //Aquin no carga nada OLAFF
                        String datos = "Select matricula_inicial_aten,matricula_acreditada,acreditados,id_organismo,fecha_acreditacion,vigencia_acreditacion,id_pe from pertinenciain19 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString(1))).concat(" order by id_pe");
                        ResultSet valores = con.Consultar(datos);
                        if (valores.next()) 
                        {
                        String datos2 = "Select matricula_inicial from pertinenciain18_1 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(valores.getString("id_pe"))).concat(" order by id_pe");
                        ResultSet valores2 = con.Consultar(datos2);
                        
                        %>
                <tr>
                    <td><%=noProg%></td>
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                    <%if(valores2.next()){%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matricula<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_inicial")%>" onblur="indicador19(this,<%=cuadro%>);"/></td>
                    <%}else{%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matricula<%=cuadro%><%=noProg%>"  value="0" onblur="indicador19(this,<%=cuadro%>);"/></td>
                    <%}%>
                    <td><input size="9" maxlength="6" type="text" name="matricula_acre<%=cuadro%><%=noProg%>"  value="<%=valores.getString(2)%>" onblur="indicador19(this,<%=cuadro%>);"/></td>
                    <td>
                        <div id="radio1<%=cuadro%><%=noProg%>">
                            <input type="radio" id="option<%=cuadro%><%=noProg%><%=1%>" name="option<%=cuadro%><%=noProg%>" value="1" <%=valores.getString(3).equals("1") ? "checked='checked'" : ""%> onclick="operacionesPertinencia19(<%=cuadro%>);"/><label for="option<%=cuadro%><%=noProg%>1" >Si</label>
                            <input type="radio" id="option<%=cuadro%><%=noProg%><%=2%>" name="option<%=cuadro%><%=noProg%>" value="0" <%=valores.getString(3).equals("0") ? "checked='checked'" : ""%>onclick="operacionesPertinencia19(<%=cuadro%>);" /><label for="option<%=cuadro%><%=noProg%>2" >No</label>
                        </div>
                    </td>
                    <td><input type="hidden" name='id_organismo<%=cuadro%><%=noProg%>' value='<%=valores.getString(4)%>' />
                        <select name="organismo<%=cuadro%><%=noProg%>" width='10%' onblur="select19();" onchange="cambiaorganismo()">
                            <%while (organismos.next()) {%>
                            <option value="<%=organismos.getString(1)%>"><%=organismos.getString(2)%></option>
                            <%}%>
                        </select>
                        
                    </td>
                     
                    <td>
                        <input size="10" readonly=\"readonly\" id="vigencia_acreditacion<%=cuadro%><%=noProg%>"  maxlength="10" name="vigencia_acreditacion<%=cuadro%><%=noProg%>" value="<%=valores.getString(6)%>" onchange="validarfecha19(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="fecha_acreditacion<%=cuadro%><%=noProg%>" maxlength="10" value="<%=valores.getString(5)%>" name="fecha_acreditacion<%=cuadro%><%=noProg%>" onchange="validarfecha19(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
                </tr>
                <% organismos.beforeFirst();
                
                } else 
                {  
                  String datos2 = "Select matricula_inicial from pertinenciain18_1 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString("id_pe"))).concat(" order by id_pe");
                  ResultSet valores2 = con.Consultar(datos2);
                        
            %>
                <tr>
                    <td><%=noProg%></td>
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                     <%if(valores2.next()){%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matricula<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_inicial")%>" onblur="indicador19(this,<%=cuadro%>);"/></td>
                    <%}else{%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matricula<%=cuadro%><%=noProg%>"  value="0" onblur="indicador19(this,<%=cuadro%>);"/></td>
                    <%}%>
                    <td><input size="9" maxlength="6" type="text" name="matricula_acre<%=cuadro%><%=noProg%>"  value="0" onblur="indicador19(this,<%=cuadro%>);"/></td>
                     <td>
                        <div id="radio1<%=cuadro%><%=noProg%>">
                            <input type="radio" id="option<%=cuadro%><%=noProg%><%=1%>" name="option<%=cuadro%><%=noProg%>" value="1" onclick="operacionesPertinencia19(<%=cuadro%>);"/><label for="option<%=cuadro%><%=noProg%>1" >Si</label>
                            <input checked="checked" type="radio" id="option<%=cuadro%><%=noProg%><%=2%>" name="option<%=cuadro%><%=noProg%>" value="0" onclick="operacionesPertinencia19(<%=cuadro%>);" /><label for="option<%=cuadro%><%=noProg%>2" >No</label>
                        </div>
                    </td>                   
                    <td>
                        <select name="organismo<%=cuadro%><%=noProg%>">
                            <%while (organismos.next()) {%>
                            <option value="<%=organismos.getString(1)%>"><%=organismos.getString(2)%></option>
                            <%}%>
                        </select>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="vigencia_acreditacion<%=cuadro%><%=noProg%>"  name="vigencia_acreditacion<%=cuadro%><%=noProg%>" value="0000-00-00" onchange="validarfecha19(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="fecha_acreditacion<%=cuadro%><%=noProg%>"  name="fecha_acreditacion<%=cuadro%><%=noProg%>" value="0000-00-00" onchange="validarfecha19(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
                </tr>
                <%}
                        noProg++;
                        organismos.beforeFirst();
                    }
                %>
                <input type="hidden" name="noProg<%=cuadro%>" value="<%=noProg - 1%>" />
                <tr>
                    <td align="center" colspan="2">TOTAL</td>
                    <td align="center" ><input readonly=\"readonly\" class="inputok" style='width:90%' type='text' name='total1<%=cuadro%>' value='0' onblur="operacionesPertinencia19(<%=cuadro%>);"></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total3<%=cuadro%>' value='0' onblur="operacionesPertinencia19(<%=cuadro%>);"></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total2<%=cuadro%>' value='0' onblur="operacionesPertinencia19(<%=cuadro%>);"></td>
                    <td align="center" colspan="3"></td>
                </tr>
                <tfoot>
                    <tr>
                        <td  colspan='2'>DISTRIBUCI�N PORCENTUAL<input type="hidden" id="totalPEE<%=cuadro%>" value="<%=contador%>" /></td>
                        <td>&nbsp;</td>
                        <td>PEA2</td>
                         <td>PEA1</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td  colspan='2'></td>
                        <td>&nbsp;</td>
                        <td><span style="font-weight: bold"  id="porcentajeSINPEE<%=cuadro%>"></span></td>
                        <td><span style="font-weight: bold"  id="porcentajeCONPEE<%=cuadro%>"></span></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan='7' class='rounded-foot-left'>&nbsp;</td><td class="rounded-foot-right" ></td>
                    </tr>
                </tfoot>
            </table>
                        
        </div>
        <%
                noProg = 1;
                cuadro++;
                cuadroa++; 
                prog.beforeFirst();

            }%>
         
        <%
            niv.beforeFirst();
            while (niv.next()) {//CANTIDAD DE NIVELES QUE TIENE LA UNIVERSIDAD EN EL PERIODO ACTUAL

        %>
        
        <h2 onclick="operacionesPertinencia19b();">Cuadro 19.2.<%=niv.getString("id_nivel")%><br/>Relaci&oacute;n de Programas Educativos de <%=niv.getString("descripcion")%> Acreditados Internacionalmente
        <input type="hidden" name="numeracion<%=cuadrob%>" value="2.<%=niv.getString("id_nivel")%>" />
        <input type="hidden" name="cuadro2<%=niv.getString("id_nivel")%>" value="<%=cuadro%>" />
        
        </h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th class="rounded-company">No.</th>
                        <th class="rounded-q1">Nombre del programa educativo que ofrece la universidad tecnol&oacute;gica</th>
                        <th class="rounded-q1">Matr&iacute;cula</br> inicial </br>atendida</th>
                        <th class="rounded-q1">Matr&iacute;cula acreditada</th>
                        <th class="rounded-q1">Acreditado</th>
                        <th class="rounded-q1">Nombre del organismo acreditador</th>
                         <th class="rounded-q1">Pais</th>
                        <th class="rounded-q1">Vigencia de la acreditaci&oacute;n</th>
                        <th class="rounded-q4">Fecha de la acreditaci&oacute;n</th>
                    </tr>
                </thead>
                <%
                    programas = "SELECT id_pe,nombre_programa,version FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(niv.getString(1))).concat(" AND id_pe IN (SELECT id_pe FROM pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(")")));
                    prog = con.Consultar(programas);
                    while (prog.next()) { //CANTIDAD DE PROGRAMAS EDUCATIVOS QUE TIENE EN EL NIVEL 'X'
                        contador2++;
                        String datos = "Select matricula_inicial_aten,matricula_acreditada,acreditados,pertinenciain19_1.id_organismo,vigencia_acreditacion,fecha_acreditacion, pais from pertinenciain19_1 inner join organismos_acreditacion on organismos_acreditacion.id_organismo= pertinenciain19_1.id_organismo where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString(1))).concat(" order by id_pe");
                        ResultSet valores = con.Consultar(datos);
                        String datos2 = "Select matricula_inicial from pertinenciain18_1 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString("id_pe"))).concat(" order by id_pe");
                        ResultSet valores2 = con.Consultar(datos2);
                
                %>
                
                <%if (valores.next()) {
                      
                         // nombre_orge = "SELECT nombre FROM organismos_acreditacion WHERE id_organismo=(SELECT id_organismo FROM pertinenciain19_1 WHERE pertinenciain19_1.id_organismo = organismos_acreditacion.id_organismo AND id_pe =".concat(prog.getString("id_pe")).concat(" and extranjero=1)");
                        // name_orge = con.Consultar(nombre_orge);
%>
                <tr>
                    <td><%=noProgb%></td>
                    <td><input type="hidden" name='idb<%=cuadrob%><%=noProgb%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                   <%if(valores2.next()){%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matriculab<%=cuadrob%><%=noProgb%>"  value="<%=valores2.getString("matricula_inicial")%>" onblur="indicador19(this,<%=cuadrob%>);"/></td>
                    <%}else{%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matriculab<%=cuadrob%><%=noProgb%>"  value="0" onblur="indicador19(this,<%=cuadrob%>);"/></td>
                    <%}%>
                    <td><input size="9" maxlength="6" type="text" name="matricula_acreb<%=cuadrob%><%=noProgb%>"  value="<%=valores.getString(2)%>" onblur="indicador19(this,<%=cuadro%>,2000);"/></td>
                    <td>
                       <div id="radio2<%=cuadrob%><%=noProgb%>">
                            <input type="radio" id="optionb<%=cuadrob%><%=noProgb%><%=1%>" name="optionb<%=cuadrob%><%=noProgb%>" value="1" onclick="operacionesPertinencia19b();"  <%if (valores.getInt(3)==1  ) {%> checked='checked' <%}%>/><label for="optionb<%=cuadrob%><%=noProgb%>1" >Si</label>
                            <input type="radio" id="optionb<%=cuadrob%><%=noProgb%><%=2%>" name="optionb<%=cuadrob%><%=noProgb%>" value="0" onclick="operacionesPertinencia19b();"  <%if (valores.getInt(3)==0  ) {%> checked='checked' <%}%>/><label for="optionb<%=cuadrob%><%=noProgb%>2" >No</label>
                        </div>
                    </td>
                    <td><input type="hidden" name='id_organismob<%=cuadrob%><%=noProgb%>' value='<%=valores.getString(4)%>' />
                        <select  name="organismob<%=cuadrob%><%=noProgb%>" id="organismob<%=cuadrob%><%=noProgb%>" onchange="cambiaorganismo(this.value, <%=cuadrob%>, <%=noProgb%>)">
                            <%while (organismose.next()) {%>
                            <option value="<%=organismose.getString(1)%>"><%=organismose.getString(2).concat(" (").concat(organismose.getString(3)).concat(")")%> </option>
                            <%}%>
                        </select>
                         <select style="visibility:hidden" name="paises<%=cuadrob%><%=noProgb%>" id="paises<%=cuadrob%><%=noProgb%>" width='10%' onblur="">
                            <%while (paises.next()) {%>
                            <option value="<%=paises.getString(1)%>"><%=paises.getString(2)%></option>
                            <%}%>
                        </select>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="pais<%=cuadrob%><%=noProgb%>"  maxlength="10" name="pais<%=cuadrob%><%=noProgb%>" value="<%=valores.getString(7)%>"/>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="vigencia_acreditacionb<%=cuadrob%><%=noProgb%>" maxlength="10" name="vigencia_acreditacionb<%=cuadrob%><%=noProgb%>" value="<%=valores.getString(5)%>" onchange="validarfecha19b(<%=cuadrob%>,<%=noProgb%>,this);"/>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="fecha_acreditacionb<%=cuadrob%><%=noProgb%>" maxlength="10" value="<%=valores.getString(6)%>" name="fecha_acreditacionb<%=cuadrob%><%=noProgb%>" onchange="validarfecha19b(<%=cuadrob%>,<%=noProgb%>,this);"/>
                    </td>
                </tr>
                <% organismose.beforeFirst();
                paises.beforeFirst();
                } else {%>
                <tr>
                    <td><%=noProgb%></td>
                    <td><input type="hidden" name='idb<%=cuadrob%><%=noProgb%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                   <%if(valores2.next()){%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matriculab<%=cuadrob%><%=noProgb%>"  value="<%=valores2.getString("matricula_inicial")%>" onblur="indicador19(this,<%=cuadro%>);"/></td>
                    <%}else{%>
                    <td><input size="9" maxlength="6" type="text" disabled name="matriculab<%=cuadrob%><%=noProgb%>"  value="0" onblur="indicador19(this,<%=cuadro%>);"/></td>
                    <%}%><td><input size="9" maxlength="6" type="text" name="matricula_acreb<%=cuadrob%><%=noProgb%>"  value="0" onblur="indicador19(this,2,2000);"/></td>
                    <td>
                        <div id="radio2<%=cuadrob%><%=noProgb%>">
                            <input type="radio" id="optionb<%=cuadrob%><%=noProgb%><%=1%>" name="optionb<%=cuadrob%><%=noProgb%>" value="1" onclick="operacionesPertinencia19b();"/><label for="optionb<%=cuadrob%><%=noProgb%>1" >Si</label>
                            <input type="radio" id="optionb<%=cuadrob%><%=noProgb%><%=2%>" name="optionb<%=cuadrob%><%=noProgb%>" value="0" onclick="operacionesPertinencia19b();" checked='checked'/><label for="optionb<%=cuadrob%><%=noProgb%>2" >No</label>
                        </div>
                    </td>                    
                   <td>
                       <input type="hidden" name='id_organismob<%=cuadrob%><%=noProgb%>' value='-2' />
                        <select  name="organismob<%=cuadrob%><%=noProgb%>" id="organismob<%=cuadrob%><%=noProgb%>" onblur="select19b()" onchange="cambiaorganismo(this.value, <%=cuadrob%>, <%=noProgb%>)">
                            <%while (organismose.next()) {%>
                            <option value="<%=organismose.getString(1)%>"><%=organismose.getString(2).concat(" (").concat(organismose.getString(3)).concat(")")%> </option>
                            <%}%>
                        </select>
                         <select style="display: none;" name="paises<%=cuadrob%><%=noProgb%>" id="paises<%=cuadrob%><%=noProgb%>" width='10%' onblur="">
                            <%while (paises.next()) {%>
                            <option value="<%=paises.getString(1)%>"><%=paises.getString(2)%></option>
                            <%}%>
                        </select>
                    </td>
                    <td>
                          <input size="10" readonly=\"readonly\" id="pais<%=cuadrob%><%=noProgb%>"  maxlength="10" name="pais<%=cuadrob%><%=noProgb%>" value=""/>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="vigencia_acreditacionb<%=cuadrob%><%=noProgb%>"  name="vigencia_acreditacionb<%=cuadrob%><%=noProgb%>"  value="0"/>
                    </td>
                    <td>
                        <input size="10" readonly=\"readonly\" id="fecha_acreditacionb<%=cuadrob%><%=noProgb%>"  name="fecha_acreditacionb<%=cuadrob%><%=noProgb%>"/>
                    </td>
                </tr>
                <%}%>
                <%
                        noProgb++;
                        organismose.beforeFirst();
                        paises.beforeFirst();
                    }
                %>
                <input type="hidden"  id="noProgb<%=cuadrob%>" name="noProgb<%=cuadrob%>" value="<%=noProgb - 1%>" />
                <tr>
                    <td align="center" colspan="2">TOTAL</td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total1b<%=cuadrob%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total3b<%=cuadrob%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total2b<%=cuadrob%>' value='0' ></td>
                    <td align="center" colspan="4"></td>
                </tr>
                <tfoot>
                    <tr>
                        <td  colspan='2'>DISTRIBUCI�N PORCENTUAL <input type="hidden" id="totalPEE1I<%=cuadro%>" value="<%=contador2%>" /></td>
                        <td>&nbsp;</td>
                        <td>PEAI2</td>
                         <td>PEAI1</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                     <tr>
                        <td  colspan='2'><input type="hidden" id="totalPEE1I<%=cuadrob%>" value="<%=contador2%>" /></td>
                        <td>&nbsp;</td>
                        <td>
                        <input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='peaI2<%=cuadrob%>' value='0' >
                        </td>
                        <td>
                       <input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='peaI<%=cuadrob%>' value='0' >
                       </td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan='8' class='rounded-foot-left'>&nbsp;</td><td class="rounded-foot-right" ></td>
                    </tr>
                </tfoot>
            </table>
        </div>
                       
        <%
                    noProgb = 1;
                    cuadrob++;
                    prog.beforeFirst();
                    cuadro++;
                }
            
            anio.next(); 
            niv.beforeFirst();
                while (niv.next()) {//CANTIDAD DE NIVELES QUE TIENE LA UNIVERSIDAD EN EL PERIODO ACTUAL
                    contador++;
                    String conmodalidad = "Select id_modelo from modelos_universidad where id_universidad=".concat(sesion.getAttribute("idUniversidad").toString())
                            .concat(" and id_periodo=").concat(sesion.getAttribute("idPeriodo").toString()).concat(" and id_nivel=").concat(niv.getString("id_nivel"));
                    ResultSet modalidad = con.Consultar(conmodalidad);
                    modalidad.next();
        %>
        <!---------------------------///////////////////////////-PROGRAMAS DE CALIDAD-->    
                   
        <h2 onclick="OperacionesPertinenciaCalidad(<%=cuadro%>,<%=niv.getString("id_nivel")%>);">Cuadro 19.3.<%=niv.getString("id_nivel")%><br/>Comparativo de Programas Educativos de Calidad por CIEES y COPAES de <%=niv.getString("descripcion")%>, <%=anio.getString("anio")%>	

            <input type="hidden" name="numeracion<%=cuadro%>" value="1.<%=niv.getString("id_nivel")%>" />
        <input type="hidden" name="cuadro3<%=niv.getString("id_nivel")%>" value="<%=cuadro%>" />
        <input type="hidden" name="modalidad<%=niv.getString("id_nivel")%>" value="<%=modalidad.getString("id_modelo")%>" /></h2>
        
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th class="rounded-company">No.</th>
                        <th class="rounded-q1">Nombre del programa educativo</th>
                        <th class="rounded-q1">A�o de incorporaci�n</th>
                        <th class="rounded-q1">Matr&iacute;cula </br>inicial </br>atendida</th>
                        <th class="rounded-q1">Matr&iacute;cula evaluable</th>
                        <th class="rounded-q1">Matr&iacute;cula de calidad</th>
                        <th size="11" class="rounded-q1">Nombre del organismo acreditador</th>
                        <th class="rounded-q1">CIEES</th>
                        <th class="rounded-q1">COPAES</th>
                        <th class="rounded-q1">Total de matr&iacute;cula de calidad</th>
                        <th class="rounded-q1">Nivel 1 de CIEES</th>
                        <th class="rounded-q1">Acreditado por COPAES</th>
                        <th class="rounded-q1">Programa de calidad</th>
                        <th class="rounded-q1">Programa evaluable</th>
                        <th class="rounded-q4">% de pe de calidad</th>
                    </tr>
                </thead>

                <%
                int totalcol[] = new int[14];
                    String matricula_t = "select matricula from mat_total_universidad where id_universidad=".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(" and id_nivel=").concat(niv.getString(1)));
                    ResultSet resmat = con.Consultar(matricula_t);
                    while (resmat.next()) {
                %>
                <input  type = "hidden" name ='mat<%=cuadro%>' value='<%=resmat.getString(1)%>' />

                <%
                    }
                    programas = "SELECT pe.id_pe,pe.nombre_programa,pe.version,peu.anio_incorporacion, peu.clasificacion FROM programa_educativo pe INNER JOIN pe_universidad peu "
 + "on pe.id_pe=peu.id_pe WHERE id_nivel=".concat(String.valueOf(niv.getString(1))).concat(" AND (peu.id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and peu.id_periodo =").concat(sesion.getAttribute("idPeriodo").toString()).concat(")"));
                    prog = con.Consultar(programas);
                    while (prog.next()) { 
                       //Aquin no carga nada OLAFF
                        String datos = "Select matricula_inicial_aten,matricula_acreditada,acreditados,pertinenciain19.id_organismo, sigla,fecha_acreditacion,vigencia_acreditacion,"
 + " id_pe from pertinenciain19 INNER JOIN organismos_acreditacion org ON org.id_organismo= pertinenciain19.id_organismo where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString(1))).concat(" order by id_pe");
                        ResultSet valores = con.Consultar(datos);
                      if (valores.next()) {
                        String datos2 = "Select matricula_con_eva, matricula_inicial from pertinenciain18_1 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(valores.getString("id_pe"))).concat(" order by id_pe");
                        ResultSet valores2 = con.Consultar(datos2);
                        String datosnivelciees = "Select val_1 from pertinenciain18 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(valores.getString("id_pe"))).concat(" order by id_pe");
                        ResultSet nivelciees = con.Consultar(datosnivelciees);
                        String datos3 = "Select matricula_acreditada from pertinenciain19_1 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(valores.getString("id_pe"))).concat(" order by id_pe");
                        ResultSet valores3 = con.Consultar(datos3);
                        boolean tienecop=false, tieneciees=false, tienecopinter=false;
                %>
                <tr>
                    <td><%=noProg%>
                        <input size="9" maxlength="6" type="hidden" name="clasi<%=cuadro%><%=noProg%>" value="<%=prog.getString("clasificacion")%>"/></td>
                    
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                    <td><input size="9" maxlength="6" disabled id="etiqueta" type="text"name="anio_inco<%=cuadro%><%=noProg%>" value="<%=prog.getString("anio_incorporacion")%>"/></td>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_inicial<%=cuadro%><%=noProg%>"  value="<%=valores.getString("matricula_inicial_aten")%>" /></td><%totalcol[0]+=valores.getInt("matricula_inicial_aten");%>
                    <td>
                    
                        <input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_evaluable<%=cuadro%><%=noProg%>"  value="<%=valores.getString("matricula_inicial_aten")%>" />
                    
                    </td><%totalcol[1]+=valores.getInt("matricula_inicial_aten");%>
                    
                    
                    <%if(valores.getInt("matricula_acreditada")>0){%>
                    <td>
                      <input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_calidad<%=cuadro%><%=noProg%>"  value="<%=valores.getString(2)%>" /><%totalcol[2]+=valores.getInt(2);%>
                    <input size="9" maxlength="6" type="hidden" disabled id="etiqueta" name="matricula_calidadguardada<%=cuadro%><%=noProg%>"  value="<%=valores.getString(2)%>" />
                    </td>
                    <%tienecop=true;}%>
                    <%if(valores2.next() && valores2.getInt("matricula_con_eva")>0 && !tienecop){%>
                    <td>
                        <input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_calidad<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_con_eva")%>" /><%totalcol[2]+=valores2.getInt("matricula_con_eva");%>
                        <input size="9" maxlength="6" type="hidden" disabled id="etiqueta" name="matricula_calidadguardada<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_con_eva")%>" />
                    </td>
                    <% tieneciees=true; }%>
                    <%if(valores3.next() && valores3.getInt("matricula_acreditada")>0 && !tienecop && !tieneciees){%>
                    <td>
                        <input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_calidad<%=cuadro%><%=noProg%>"  value="<%=valores3.getString("matricula_acreditada")%>" /><%totalcol[2]+=valores3.getInt("matricula_acreditada");%>
                         <input size="9" maxlength="6" type="hidden" disabled id="etiqueta" name="matricula_calidadguardada<%=cuadro%><%=noProg%>"  value="<%=valores3.getString("matricula_acreditada")%>" />
                    </td>
                    <% tienecopinter=true; }%>
                    <% if(!tieneciees && !tienecop && !tienecopinter){%>
                    <td>
                        <input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_calidad<%=cuadro%><%=noProg%>"  value="0" />
                    <input size="9" maxlength="6" type="hidden" disabled id="etiqueta" name="matricula_calidadguardada<%=cuadro%><%=noProg%>"  value="" />
                    </td>
                    <%}%>
                    <td>
                        <%if(valores.getString("id_organismo").equals("-2") || valores.getString("id_organismo").equals("-1")){%>
                        <%if(valores2.getInt("matricula_con_eva")>0){%>
                        
                        <span id="organismo_calidad<%=cuadro%><%=noProg%>">Comit&eacute;s Interinstitucionales para la Evaluaci&oacute;n de la Educaci&oacute;n Superior, A.C. (CIEES)</span>
                        
                        <%}else{%>
                         <span id="organismo_calidad<%=cuadro%><%=noProg%>">-----</span>
                        <%}}else{%>
                            
                             <span id="organismo_calidad<%=cuadro%><%=noProg%>"><%=valores.getString("sigla")%></span>
                        <%  }%>
                    </td>
                      <%if(valores2.getInt("matricula_con_eva")>0){%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="tiene_ciees<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_con_eva")%>" /></td><%totalcol[3]+=valores2.getInt("matricula_con_eva");%>
                    <%}else{%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="tiene_ciees<%=cuadro%><%=noProg%>"  value="0" /></td>
                    <%}%>
                    <%if(valores.getInt("matricula_acreditada")>0){%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="tiene_copaes<%=cuadro%><%=noProg%>"  value="<%=valores.getString(2)%>" /></td><%totalcol[4]+=valores2.getInt(2);%>
                    <%}else{%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="tiene_copaes<%=cuadro%><%=noProg%>"  value="0" /></td>
                    <%}%>
                    <%if(valores.getInt("matricula_acreditada")>0 || valores2.getInt("matricula_con_eva")>0){%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="total_calidad<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_inicial")%>" /></td><%totalcol[5]+=valores2.getInt("matricula_inicial");%>
                    <%}else{%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="total_calidad<%=cuadro%><%=noProg%>"  value="0" /></td>
                    <%}%>
                    <!--Se verifica si tiene CIEES--> 
                    <%if(nivelciees.next()){
                        if(nivelciees.getInt("val_1")==1){%>
                       <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="ciees_1<%=cuadro%><%=noProg%>"  value="1" /></td> <%totalcol[6]+=1;%>
                    <%  }else{%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="ciees_1<%=cuadro%><%=noProg%>"  value="0" /></td> 
                    <%  }
                      }   %>    
                     <!--Se verifica si tiene COPAES--> 
                     <%if(tienecop){%>
                       <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="copaes<%=cuadro%><%=noProg%>"  value="1" /></td> <%totalcol[7]+=1;%>
                    <%  }else{%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="copaes<%=cuadro%><%=noProg%>"  value="0" /></td> 
                    <%  }%>
                       <!--Se verifica si tiene COPAES y CIESS--> 
                     <%if(tienecop || tieneciees){%>
                       <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="prog_calidad<%=cuadro%><%=noProg%>"  value="1" /></td> <%totalcol[8]+=1;%>
                    <%  }else{%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="prog_calidad<%=cuadro%><%=noProg%>"  value="0" /></td> 
                    <%  }%> 
                     <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="prog_evaluable<%=cuadro%><%=noProg%>"  value="1" /></td>     <%totalcol[9]+=1;%>                       
                     <td></td>
                </tr>
                
                <%
                }else{
                        String datos2 = "Select matricula_inicial, matricula_con_eva from pertinenciain18_1 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString("id_pe"))).concat(" order by id_pe");
                        ResultSet valores2 = con.Consultar(datos2);
                        if(valores2.next()){
                         String datosnivelciees = "Select val_1 from pertinenciain18 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString("id_pe"))).concat(" order by id_pe");
                        ResultSet nivelciees = con.Consultar(datosnivelciees);
                     boolean tieneciees=false;
                  %>          
                  <tr>
                    <td><%=noProg%>
                       <input size="9" maxlength="6" type="hidden" name="clasi<%=cuadro%><%=noProg%>" value="<%=prog.getString("clasificacion")%>"/></td>
                    
                    </td>
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                      <td><input size="9" maxlength="6"  id="etiqueta" type="text" name="anio_inco<%=cuadro%><%=noProg%>" value="<%=prog.getString("anio_incorporacion")%>"/></td>
                  
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_inicial<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_inicial")%>" /></td><%totalcol[0]+=valores2.getInt("matricula_inicial");%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_evaluable<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_inicial")%>" /></td><%totalcol[1]+=valores2.getInt("matricula_inicial");%>
                    <td>
                        <input size="9" maxlength="6" type="text" disabled id="etiqueta" name="matricula_calidad<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_con_eva")%>" /><%totalcol[2]+=valores2.getInt("matricula_con_eva");%>
                        <input size="9" maxlength="6" type="hidden" disabled id="etiqueta" name="matricula_calidadguardada<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_con_eva")%>" />
                    </td>
                    <%if(valores2.getInt("matricula_con_eva")>0){%>
                    <td>
                        <span id="organismo_calidad<%=cuadro%><%=noProg%>">Comit&eacute;s Interinstitucionales para la Evaluaci&oacute;n de la Educaci&oacute;n Superior, A.C. (CIEES)</span>
                        <!--<input size="20" type="text" disabled id="etiqueta" name="organismo_calidad<%=cuadro%><%=noProg%>"  value="Comit&eacute;s Interinstitucionales para la Evaluaci&oacute;n de la Educaci&oacute;n Superior, A.C. (CIEES)" /> -->
                    </td>
                    <%}else{%>
                      <td>
                          <span id="organismo_calidad<%=cuadro%><%=noProg%>">-----</span>
                          <!--<input size="20" type="text" disabled id="etiqueta" name="organismo_calidad<%=cuadro%><%=noProg%>"  value="------" /> -->
                      </td>
                    <%}%>
                    
                    <%if(valores2.getInt("matricula_con_eva")>0){%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="tiene_ciees<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_con_eva")%>" /></td><%totalcol[3]+=valores2.getInt("matricula_con_eva");%>
                    <%}else{%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="tiene_ciees<%=cuadro%><%=noProg%>"  value="0" /></td>
                    <%}%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="tiene_copaes<%=cuadro%><%=noProg%>"  value="0" /></td>
                    <%if (valores2.getInt("matricula_con_eva")>0){%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="total_calidad<%=cuadro%><%=noProg%>"  value="<%=valores2.getString("matricula_inicial")%>" /></td><%totalcol[5]+=valores2.getInt("matricula_inicial");%>
                    <%}else{%>
                        <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="total_calidad<%=cuadro%><%=noProg%>"  value="0" /></td>
                    <%}%>
                    <!--Se verifica si tiene CIEES--> 
                    <%if(nivelciees.next()){
                        if(nivelciees.getInt("val_1")==1){%>
                       <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="ciees_1<%=cuadro%><%=noProg%>"  value="1" /></td> <%totalcol[6]+=1;%>
                    <%  tieneciees=true;}else{%>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="ciees_1<%=cuadro%><%=noProg%>"  value="0" /></td> 
                    <%  }
                      }   %> 
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="copaes<%=cuadro%><%=noProg%>"  value="0" /></td> 
                      <!--Se verifica si tiene COPAES y CIESS--> 
                     <%if( tieneciees){%>
                       <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="prog_calidad<%=cuadro%><%=noProg%>"  value="1" /></td> <%totalcol[8]+=1;%>
                    <%  }else{%>
                     <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="prog_calidad<%=cuadro%><%=noProg%>"  value="0" /></td> 
                    <%  }%> 
                     <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="prog_evaluable<%=cuadro%><%=noProg%>"  value="1" /></td><%totalcol[9]+=1;%>                            
                     <td></td>
                  </tr>
                <%
                
                    }else{%>
                   <h7 class="error">
                        Para poder ver este indicador, primero debe llenar el Indicador 18 de Pertinencia
                    </h7> 
                    <%break;
                    }
                }%>
                
                <%
                         noProg++;
                        organismos.beforeFirst();
                    }
                %>
                 <tr>
                    
                    <td colspan="3">TOTAL</td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_0<%=cuadro%>"  value="<%=totalcol[0]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_1<%=cuadro%>"  value="<%=totalcol[1]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_2<%=cuadro%>"  value="<%=totalcol[2]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_a<%=cuadro%>"  value="" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_3<%=cuadro%>"  value="<%=totalcol[3]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_4<%=cuadro%>"  value="<%=totalcol[4]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_5<%=cuadro%>"  value="<%=totalcol[5]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_6<%=cuadro%>"  value="<%=totalcol[6]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_7<%=cuadro%>"  value="<%=totalcol[7]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_8<%=cuadro%>"  value="<%=totalcol[8]%>" /></td>
                    <td><input size="6" type="text" disabled id="etiqueta" name="tot_9<%=cuadro%>"  value="" /></td>
                    <td><input size="9" maxlength="6" type="text" disabled id="etiqueta" name="porcevaluable<%=cuadro%>"  value="<%=(double)((double)totalcol[8]/(double)totalcol[9])*100%>" /> </td>
                    <td>  <input type="hidden" name="noProg<%=cuadro%>" value="<%=noProg - 1%>" />
               </td>
                </tr>
                
            </table>
        </div>
        <%
                noProg = 1;
                cuadro++;
                prog.beforeFirst();
                noCuadroCalidad++;
            }
            
            
            } catch (SQLException ex) {
                System.err.println("Jsp Error pertinencia 19: " + ex);
            } finally {
                con.Desconectar();
            }
        %>
         <h3><a href="#">Archivo</a></h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <tr><td><input type="file" id="archivo" name="archivo"/></td></tr>
                 <tr>
                    <td><input type="hidden" id="nomuni" name="nomuni" value="<%=universidad%>">
                        <input type="hidden" id="IdUni" name="IdUni" value="<%=idUniversidad%>">
                        <input type="hidden" id="IdPer" name="IdPer" value="<%=idPeriodo%>"></td>
                </tr><tr><td></td></tr>
                <tfoot>
                    <tr>
                        <td colspan="4" class="rounded-foot-left">Notas: Por el momento solo se pueden enviar archivos formato PDF y con tama�o m&aacute;ximo de 2MB.
                        <br>Los archivos ya enviados <strong>No apareceran aqui</strong></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        <input type="hidden" name="noCuadros" value="<%=cuadro - 1%>" />
        <input type="hidden" name="noCuadrosCalidad" value="<%=noCuadroCalidad - 1%>" />
        <input type="hidden" name="cuadroa" value="<%=cuadroa - 1%>" />
        <input type="hidden" name="noCuadrosb" value="<%=cuadrob - 1%>"/>
        <input type="hidden" name="noMatricula" value="<%=Matricula%>"/>
        <input type="hidden" name="anio" value="<%=anio.getString("anio")%>"/>
        <html:hidden name="PertinenciaIn19Form" property="valores" styleId="valores" />

       
        
    </div>
        
           <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=19";
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
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarPertinenciaIn19(),'PertinenciaIn19Datos');" >Guardar</button>

    <%          }
            }
    } else {%>
   <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
<div id="pert19" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>





