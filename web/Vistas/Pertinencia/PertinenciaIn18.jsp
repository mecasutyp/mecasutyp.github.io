<%-- 
    Document   : PertinenciaIn18
    Created on : 09/10/2012, 08:25:15 AM
    Author     : Daniel Ram�rez Torres
    Actualizaci�n 2016: Salvador Zamora Arias
--%>
<%@page import="com.mecasut.admon.*, com.mecasut.shared.Universidad"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!--OBTENER LA SESION-->
<%
try{
    HttpSession sesion = request.getSession(false);
    int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
    int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
     Universidad uni = new Universidad();
     String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
%>
<!--TERMINA OBTENER LA SESION-->
<html:form action="/PertinenciaIn18">
    <div id="pert18">
        <%
            ConexionMySQL con = new ConexionMySQL();
            int noProg = 1, noProgb = 1;
            int cuadro = 1, cuadrob = 1;
            int conta = 1, contb = 2;
            try {
                String niveles = "Select distinct(pe.id_nivel), descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(") order by pe.id_nivel"));
                ResultSet niv = con.Consultar(niveles);
                String programas = "";
                ResultSet prog;
                while (niv.next()) {//CANTIDAD DE NIVELES QUE TIENE LA UNIVERSIDAD EN EL PERIODO ACTUAL

        %>

        <h2 onclick="blurVal18(<%=cuadro%>);">Cuadro 18.1.<%=niv.getString("id_nivel")%>
            
            <br/>Relaci&oacute;n de programas educativos de <%=niv.getString("descripcion")%> con evaluaci&oacute;n diagn&oacute;stica seg�n nivel</h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th rowspan="2" width='4%' class="rounded-company">No.</th>
                        <th rowspan="2" width='25%'class="rounded-q1">Nombre del programa educativo</th>
                        <th rowspan="2" width='8%' class="rounded-q1">Programa educativo</br> con evaluaci&oacute;n</br> diagn&oacute;stica</th>
                        <th colspan="3" class="rounded-q1">Nivel del programa educativo con </br>evaluaci&oacute;n diagn&oacute;stica</th>
                        <th rowspan="2" width='8%' class="rounded-q1">Vigencia de la </br>evaluaci&oacute;n</br> diagn&oacute;stica</th>
                        <th rowspan="2" width='10%' class="rounded-q4">Fecha de inicio de la </br>evaluaci&oacute;n diagn&oacute;stica</th>
                    </tr>
                    <tr>
                        <th width='5%'class="rounded-q1">1</th>
                        <th width='5%' class="rounded-q1">2</th>
                        <th width='5%' class="rounded-q1">3</th>
                    </tr>
                </thead>
                <%
                    String matricula_t = "select matricula from mat_total_universidad where id_universidad=".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(" and id_nivel=").concat(niv.getString(1)));
                    ResultSet resmat = con.Consultar(matricula_t);
                    while (resmat.next()) {
                %>
                <input  type = "hidden" name ='mat<%=cuadro%>' value='<%=resmat.getString(1)%>' />
                <%}
                    programas = "SELECT id_pe,nombre_programa,version FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(niv.getString(1))).concat(" AND id_pe IN (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(")")));
                    prog = con.Consultar(programas);
                    while (prog.next()) { //CANTIDAD DE PROGRAMAS EDUCATIVOS QUE TIENE EN EL NIVEL 'X'
                        String datos = "Select evaluacion_diagnostica,val_1,val_2,val_3,vigencia_evaluacion,fecha_evaluacion from pertinenciain18 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(prog.getString("id_pe")).concat(" order by id_pe");
                        ResultSet valores = con.Consultar(datos);
                        if (valores.next()) {
                %>
                <tr>
                    <td><%=noProg%></td>
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString("version")%>)</td>
                    <td>
                        <div size="6"id="rad<%=cuadro%><%=noProg%>">
                            <input type="radio" id="radio<%=cuadro%><%=noProg%><%=1%>" name="radio<%=cuadro%><%=noProg%>" value="1" <%=valores.getString(1).equals("1") ? "checked='checked'" : ""%> onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/><label for="radio<%=cuadro%><%=noProg%>1" >Si</label>
                            <input type="radio" id="radio<%=cuadro%><%=noProg%><%=2%>" name="radio<%=cuadro%><%=noProg%>" value="0" <%=valores.getString(1).equals("0") ? "checked='checked'" : ""%> onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/><label for="radio<%=cuadro%><%=noProg%>2" >No</label>
                        </div>
                    </td>
                    <td><input size="6" type="radio" id="radioniv<%=cuadro%><%=noProg%><%=1%>" name="radioniv<%=cuadro%><%=noProg%>" value="1" <%=valores.getString(2).equals("1") ? "checked='checked'" : ""%> onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/></td>
                    <td><input type="radio" id="radioniv<%=cuadro%><%=noProg%><%=2%>" name="radioniv<%=cuadro%><%=noProg%>" value="1" <%=valores.getString(3).equals("1") ? "checked='checked'" : ""%> onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/></td>
                    <td><input type="radio" id="radioniv<%=cuadro%><%=noProg%><%=3%>" name="radioniv<%=cuadro%><%=noProg%>" value="1" <%=valores.getString(4).equals("1") ? "checked='checked'" : ""%> onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/></td>
                    <td>
                        <input size="11" readonly=\"readonly\" id="vigencia_evaluacion<%=cuadro%><%=noProg%>" maxlength="10" style="text-align:center" name="vigencia_evaluacion<%=cuadro%><%=noProg%>" value="<%=valores.getString(5)%>" onchange="validarfecha18(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
                    <td>
                        <input size="11" readonly=\"readonly\" id="fecha_evaluacion<%=cuadro%><%=noProg%>"  maxlength="10" style="text-align:center" name="fecha_evaluacion<%=cuadro%><%=noProg%>" value="<%=valores.getString(6)%>" onchange="validarfecha18(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
                </tr>
                <%} else {%>
                <tr>
                    <td><%=noProg%></td>
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString("version")%>)</td>
                    <td>
                        <div id="rad<%=cuadro%><%=noProg%>">
                            <input type="radio" id="radio<%=cuadro%><%=noProg%><%=1%>" name="radio<%=cuadro%><%=noProg%>" value="1" onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/><label for="radio<%=cuadro%><%=noProg%>1" >Si</label>
                            <input type="radio" id="radio<%=cuadro%><%=noProg%><%=2%>" name="radio<%=cuadro%><%=noProg%>" value="0" onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);" checked='checked'/><label for="radio<%=cuadro%><%=noProg%>2" >No</label>
                        </div>
                    </td>
                    <td><input type="radio" id="radioniv<%=cuadro%><%=noProg%><%=1%>" name="radioniv<%=cuadro%><%=noProg%>" value="0" onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/></td>
                    <td><input type="radio" id="radioniv<%=cuadro%><%=noProg%><%=2%>" name="radioniv<%=cuadro%><%=noProg%>" value="0"  onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/></td>
                    <td><input type="radio" id="radioniv<%=cuadro%><%=noProg%><%=1%>" name="radioniv<%=cuadro%><%=noProg%>" value="0" onclick="operacionesPertinencia18(<%=cuadro%>,<%=noProg%>);"/></td>
                    <td>
                        <input size="11" id="vigencia_evaluacion<%=cuadro%><%=noProg%>" maxlength="10" style="text-align:center" name="vigencia_evaluacion<%=cuadro%><%=noProg%>" value="0000-00-00" onchange="validarfecha18(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
                    <td>
                        <input size="11" id="fecha_evaluacion<%=cuadro%><%=noProg%>"  maxlength="10" style="text-align:center" name="fecha_evaluacion<%=cuadro%><%=noProg%>" value="0000-00-00" onchange="validarfecha18(<%=cuadro%>,<%=noProg%>,this);"/>
                    </td>
            </tr>
                <%}//cierra el else
                        noProg++;
                    }//cierra prog
                %>
                <input type="hidden" name="noProg<%=cuadro%>" value="<%=noProg - 1%>" />
                <tr>
                    <td align="center" colspan="2">TOTAL </td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total1<%=cuadro%>' value='0' onblur="operacionesPertinencia18(<%=cuadro%>);"></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='totaln1<%=cuadro%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='totaln2<%=cuadro%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='totaln3<%=cuadro%>' value='0' ></td>
                    <td align="center" colspan="3"></td>
                </tr>
                <tr>
                    <td align="center" colspan="2">DISTRIBUCI�N PORCENTUAL</td>
                    <td align="center" >PEE1</td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                </tr>
                <tr>
                    <td align="center" colspan="2"></td>
                    
                    <td align="center" >
                        <input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='totaln1<%=cuadro%>-d-p' value='' >
                        <input type="hidden" name="noprogramas<%=cuadro%>" value="<%=noProg-1%>">
                    </td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    
                </tr>
                <tfoot>
                <td colspan='7' class='rounded-foot-left'>Notas: S&oacute;lo se registrar&aacute;n los programas educativos evaluados a partir de enero de 2011 a agosto de 2016.</td>
                <td class='rounded-foot-right'></td>
                </tfoot>
            </table>
        </div>


        <h2>Cuadro 18.2.<%=niv.getString("id_nivel")%><br/>Relaci&oacute;n de la matr&iacute;cula en programas educativos de <%=niv.getString("descripcion")%>  con evaluaci�n diagn�stica seg�n nivel de evaluaci&oacute;n</h2>
        <div>
            <input type="hidden" name="idNivel<%=cuadro%>" value="<%=niv.getString("id_nivel")%>"/>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th class="rounded-company"></th>
                        <th class="rounded-q1"></th>
                        <th class="rounded-q1"></th>
                        <th class="rounded-q1"></th>
                        <th class="rounded-q1"></th>
                        <th colspan="3" class="rounded-q4">Nivel del programa educativo con</br> evaluaci&oacute;n diagn&oacute;stica</th>
                    </tr>
                    <tr>
                        <th rowspan="2" class="rounded-q1">No.</th>
                        <th rowspan="2" class="rounded-q1">Nombre del Programa Educativo</th>
                        <th rowspan="2" width='10%' class="rounded-q1">Matr&iacute;cula inicial</br> atendida</th>
                        <th rowspan="2" width='15%' class="rounded-q1">Matr&iacute;cula sin evaluaci&oacute;n</br> diagn&oacute;stica</th>
                        <th rowspan="2" width='15%' class="rounded-q1">Matr&iacute;cula con evaluaci&oacute;n</br> diagn&oacute;stica</th>
                        <th rowspan="2" width='7%' class="rounded-q1">1</th>
                        <th rowspan="2" width='7%' class="rounded-q1">2</th>
                        <th rowspan="2" width='7%' class="rounded-q1">3</th>
                    </tr>
                </thead>
                <%
                    programas = "SELECT id_pe,nombre_programa,version FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(niv.getString(1))).concat(" AND id_pe IN (SELECT id_pe FROM pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(")")));
                    prog = con.Consultar(programas);
                    while (prog.next()) { //CANTIDAD DE PROGRAMAS EDUCATIVOS QUE TIENE EN EL NIVEL 'X'
                        String datos1 = "Select matricula_inicial,matricula_sin_eva,matricula_con_eva,val_1,val_2,val_3 from pertinenciain18_1 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(prog.getString("id_pe")).concat(" order by id_pe");
                        ResultSet valores1 = con.Consultar(datos1);
                        if (valores1.next()) {
                %>
                <tr>
                    <td><%=noProgb%></td>
                    <td><input type="hidden" name='idb<%=cuadrob%><%=noProgb%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString("version")%>)</td>
                    <td>
                        <input size="11" maxlength="6" id="matricula_inicial<%=cuadrob%><%=noProgb%>"  name="matricula_inicial<%=cuadrob%><%=noProgb%>" value="<%=valores1.getString(1)%>" onblur="indicador18(this,<%=cuadrob%>,222);"/>
                    </td>
                    <td>
                        <input size="11" maxlength="6" id="matricula_sin_eva<%=cuadrob%><%=noProgb%>"  name="matricula_sin_eva<%=cuadrob%><%=noProgb%>" value="<%=valores1.getString(2)%>" onblur="indicador18(this,<%=cuadrob%>,222);"/>
                    </td>
                    <td>
                        <input size="11" disabled maxlength="6" id="matricula_con_eva<%=cuadrob%><%=noProgb%>"  name="matricula_con_eva<%=cuadrob%><%=noProgb%>" value="<%=valores1.getString(3)%>" onblur="indicador18(this,<%=cuadrob%>,222);"/>
                    </td>
                    <td><input size="8" disabled maxlength="6" type="text" name="nivel1b<%=cuadrob%><%=noProgb%>"  value="<%=valores1.getString(4)%>" onblur="indicador18(this,<%=cuadrob%>,222);"/></td>
                    <td><input size="8" disabled maxlength="6" type="text" name="nivel2b<%=cuadrob%><%=noProgb%>"  value="<%=valores1.getString(5)%>" onblur="indicador18(this,<%=cuadrob%>,222);"/></td>
                    <td><input size="8" disabled maxlength="6" type="text" name="nivel3b<%=cuadrob%><%=noProgb%>"  value="<%=valores1.getString(6)%>" onblur="indicador18(this,<%=cuadrob%>,222);"/></td>
                </tr>
                <%} else {%>
                <tr>
                    <td><%=noProgb%></td>
                    <td><input type="hidden" name='idb<%=cuadrob%><%=noProgb%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString("version")%>)</td>
                    <td>
                        <input size="11" maxlength="6" id="matricula_inicial<%=cuadrob%><%=noProgb%>"  name="matricula_inicial<%=cuadrob%><%=noProgb%>" value="0" onblur="indicador18(this,<%=cuadrob%>,222);"/>
                    </td>
                    <td>
                        <input size="11" maxlength="6" id="matricula_sin_eva<%=cuadrob%><%=noProgb%>"  name="matricula_sin_eva<%=cuadrob%><%=noProgb%>" value="0" onblur="indicador18(this,<%=cuadrob%>,222);"/>
                    </td>
                    <td>
                        <input size="11" maxlength="6" id="matricula_con_eva<%=cuadrob%><%=noProgb%>"  name="matricula_con_eva<%=cuadrob%><%=noProgb%>" value="0" onblur="indicador18(this,<%=cuadrob%>,222);"/>
                    </td>
                    <td><input size="8" disabled maxlength="6" type="text" name="nivel1b<%=cuadrob%><%=noProgb%>"  value="0" onblur="indicador18(this,<%=cuadrob%>,222);"/></td>
                    <td><input size="8" disabled maxlength="6" type="text" name="nivel2b<%=cuadrob%><%=noProgb%>"  value="0" onblur="indicador18(this,<%=cuadrob%>,222);"/></td>
                    <td><input size="8" disabled maxlength="6" type="text" name="nivel3b<%=cuadrob%><%=noProgb%>"  value="0" onblur="indicador18(this,<%=cuadrob%>,222);"/></td>
                </tr>
                <%}
                        noProg++;
                        noProgb++;
                    }
                %>
                <input type="hidden" name="noProgb<%=cuadrob%>" value="<%=noProgb - 1%>" />
                <tr>
                    <td align="center" colspan="2">TOTAL </td>
                    <td align="center" ><input readonly=\"readonly\" size="10" maxlength="10" class="inputok" style='width:90%' type='text' name='total1b<%=cuadrob%>' value='0' onblur="operacionesPertinencia18b(<%=cuadrob%>);" ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total2b<%=cuadrob%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total3b<%=cuadrob%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='totaln1b<%=cuadrob%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='totaln2b<%=cuadrob%>' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='totaln3b<%=cuadrob%>' value='0' ></td>
                    <td align="center" colspan="3"></td>
                </tr>
                 <tr>
                    <td align="center" colspan="2">DISTRIBUCI�N PORCENTUAL</td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" >PEE2</td>
                    <td align="center" >PEE3</td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" colspan="3"></td>
                </tr>
                 <tr>
                    <td align="center" colspan="2"></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total<%=cuadrob%>-d-pee2' value='0' ></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total<%=cuadrob%>-d-pee3' value='0' ></td>
                    <td align="center" ></td>
                    <td align="center" ></td>
                    <td align="center" colspan="3"></td>
                </tr>
                <tfoot>
                <td colspan='7' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>
                <td class='rounded-foot-right'></td>
                </tfoot>
            </table>
        </div>
        
        
        
            <%
                    noProg = 1;
                    noProgb = 1;
                    conta += 2;
                    contb += 2;
                    cuadro++;
                    cuadrob++;
                    prog.beforeFirst();

                }//cierra niv
            } catch (SQLException ex) {
                System.err.println("error pertinencia 18: " + ex);
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
        <html:hidden name="PertinenciaIn18Form" property="valores" styleId="valores" />
        <input type="hidden" name="noCuadros" value="<%=cuadro - 1%>" />
        <input type="hidden" name="noCuadrosb" value="<%=cuadrob - 1%>" />
    </div>
    
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=18";
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
    <%        String consulta = "select activo from system_mecasut";
        ResultSet rs = con.Consultar(consulta);

        rs.next();

        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")){
    %>
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarPertinenciaIn18(),'PertinenciaIn18Datos','PertinenciaIn18Form');" >Guardar</button>
    <%}}
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }%>
</html:form>
    <%
    } catch (NullPointerException exNull) {
    %>
    <div id="pert18" align="center">
        La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
    </div>
    <%            }
    %>
        

    
    