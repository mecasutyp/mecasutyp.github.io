<%-- 
    Document   : PertinenciaIn22
    Created on : 4/07/2012, 11:14:30 AM
    Author     : Cuauhtemoc Medina Mu�oz, Daniel Ram�rez Torres
--%>

<%@page import="com.mecasut.shared.Universidad"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!--OBTENER LA SESION-->
<% try {
        HttpSession sesion = request.getSession(false);
        int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
        int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
         Universidad uni = new Universidad();
     String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
%>
<!--TERMINA OBTENER LA SESION-->
<html:form action="/PertinenciaIn22">
    <div id="pert22">
        <%
            ConexionMySQL con = new ConexionMySQL();
            int noProg = 1, cuadro = 1, anio_pe = 0;
            int contador=0;
            try {

                String niveles = "Select distinct(pe.id_nivel),descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(") order by pe.id_nivel"));
                ResultSet niv = con.Consultar(niveles);
                String programas = "";
                ResultSet prog;
                String sql1 = "select anio from periodos where id_periodo='".concat(sesion.getAttribute("idPeriodo").toString()).concat("'");
                ResultSet rs1 = con.Consultar(sql1);
                if (rs1.next()) {
                    anio_pe = rs1.getInt("anio");
                }
                while (niv.next()) {//CANTIDAD DE NIVELES QUE TIENE LA UNIVERSIDAD EN EL PERIODO ACTUAL
                    contador++;
%>
<h2 onclick="operacionesPertinencia22a(<%=cuadro%>);">Cuadro 22.1.<%=niv.getString("id_nivel")%><br/>Relaci&oacute;n de Programas Pertinentes del Nivel <%=niv.getString("descripcion")%> por Matr&iacute;cula Seg&uacute;n Fecha de Estudios
<input type="hidden" name="idNivel<%=cuadro%>" value="<%=niv.getString("id_nivel")%>"/></h2>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <thead>
                    <tr>
                        <th class="rounded-company">No.</th>
                        <th class="rounded-q1">Nombre del programa educativo que ofrece la universidad tecnol&oacute;gica</th>
                        <th width='7%' class="rounded-q1">Matr&iacute;cula inicial</th>
                        
                        <th width='8%' class="rounded-q1">Matr&iacute;cula pertinente</th>
                        <th width='8%' class="rounded-q1">A�o de incio </br>de la carrera</th>
                        <th width='8%'class="rounded-q1">A�o del &uacute;ltimo </br>estudio de</br>factibilidad</th>
                        <th width='9%' class="rounded-q1">A�o del &uacute;ltimo </br>estudio de an&aacute;lisis</br> de la situaci&oacute;n de trabajo</th>
                        <th class="rounded-q4">Pertinente</th>
                    </tr>
                </thead>
                <%
                    programas = "SELECT id_pe,nombre_programa,version FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(niv.getString(1))).concat(" AND id_pe IN (SELECT id_pe FROM pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(")")));
                    prog = con.Consultar(programas);
                    while (prog.next()) { //CANTIDAD DE PROGRAMAS EDUCATIVOS QUE TIENE EN EL NIVEL 'X'
                        String datos = "Select matricula_ini_pe,matricula_pert,pertinente,anio_inicio,anio_estudio,anio_analisis from pertinenciain22 where id_universidad =".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pe=").concat(String.valueOf(prog.getString(1))).concat(" order by id_pe");
                        ResultSet valores = con.Consultar(datos);
                       if (valores.next()) {%>
                <tr>
                    <td><%=noProg%></td>
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                    <td><input size="9" maxlength="6" type="text" name="matricula<%=cuadro%><%=noProg%>"  value="<%=valores.getString(1)%>" onblur="indicador22(this,<%=cuadro%>);"/></td>
                    
                    <td><input size="9" maxlength="6" type="text" name="matricula_pert<%=cuadro%><%=noProg%>"  value="<%=valores.getString(2)%>" onblur="indicador22(this,<%=cuadro%>);"/></td>
                    <td>
                        <select  id="fecha_inicio<%=cuadro%><%=noProg%>" name="fecha_inicio<%=cuadro%><%=noProg%>" onchange="verificapertinencia(<%=cuadro%>, <%=noProg%>)">
                            <option><%=valores.getString(4)%></option>                        
                            <%
                                for (int anio = anio_pe + 1; anio >= 1991; anio--) {
                            %>
                            <option><%=anio%></option>
                            <%
                                }
                            %>
                            }                                                     
                        </select>
                    </td>
                    <td>
                        <select  id="fecha_estudio<%=cuadro%><%=noProg%>" name="fecha_estudio<%=cuadro%><%=noProg%>" onchange="verificapertinencia(<%=cuadro%>, <%=noProg%>)">
                            <option><%=valores.getString(5)%></option>
                            <%
                                for (int anio = anio_pe + 1; anio >= 1991; anio--) {
                            %>
                            <option><%=anio%></option>                            
                            <%
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <select  name="fecha_trabajo<%=cuadro%><%=noProg%>" id="fecha_trabajo<%=cuadro%><%=noProg%>" onchange="verificapertinencia(<%=cuadro%>, <%=noProg%>)">
                            <option><%=valores.getString(6)%></option>
                            <%
                                for (int anio = anio_pe + 1; anio >= 1991; anio--) {
                            %>
                            <option><%=anio%></option>
                            <%
                                }
                            %>
                        </select>
                    </td>
                    <td>
                         <div id="rad<%=cuadro%><%=noProg%>">
                            <input type="radio" id="radios<%=cuadro%><%=noProg%><%=1%>" disabled name="radioss<%=cuadro%><%=noProg%>" value="1" <%=valores.getString(3).equals("1") ? "checked='checked'" : ""%> onclick="operacionesPertinencia22a(<%=cuadro%>);"/><label for="radios<%=cuadro%><%=noProg%>1" >Si</label>
                            <input type="radio" id="radios<%=cuadro%><%=noProg%><%=2%>" disabled name="radioss<%=cuadro%><%=noProg%>" value="0" <%=valores.getString(3).equals("0") ? "checked='checked'" : ""%> onclick="operacionesPertinencia22a(<%=cuadro%>);" /><label for="radios<%=cuadro%><%=noProg%>2" >No</label>
                        </div>
                    </td>
                </tr>
                <%} else {%>
                <tr>
                    <td><%=noProg%></td>
                    <td><input type="hidden" name='id<%=cuadro%><%=noProg%>' value='<%=prog.getString("id_pe")%>' /><%=prog.getString("nombre_programa")%> (<%=prog.getString(3)%>)</td>
                    <td><input size="9" maxlength="6" type="text" name="matricula<%=cuadro%><%=noProg%>"  value="0" onblur="indicador22(this,<%=cuadro%>);"/></td>
                   
                    <td><input size="9" maxlength="6" type="text" name="matricula_pert<%=cuadro%><%=noProg%>"  value="0" onblur="indicador22(this,<%=cuadro%>);"/></td>
                    <td>
                        <select id="fecha_inicio<%=cuadro%><%=noProg%>" name="fecha_inicio<%=cuadro%><%=noProg%>" onchange="verificapertinencia(<%=cuadro%>, <%=noProg%>)">
                            <%
                                for (int anio = anio_pe + 1; anio >= 1991; anio--) {
                            %>
                            <option><%=anio%></option>
                            <%
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <select id="fecha_estudio<%=cuadro%><%=noProg%>" name="fecha_estudio<%=cuadro%><%=noProg%>" onchange="verificapertinencia(<%=cuadro%>, <%=noProg%>)">
                            <%
                                for (int anio = anio_pe + 1; anio >= 1991; anio--) {
                                    out.print("<option>"+anio+"</option>");
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <select id="fecha_trabajo<%=cuadro%><%=noProg%>" name="fecha_trabajo<%=cuadro%><%=noProg%>" onchange="verificapertinencia(<%=cuadro%>, <%=noProg%>)">
                            <%
                                for (int anio = anio_pe + 1; anio >= 1991; anio--) {
                                    out.print("<option>"+anio+"</option>");
                                }
                            %>
                        </select>
                    </td>
                     <td>
                        <div id="rad<%=cuadro%><%=noProg%>">
                            <input type="radio" id="radios<%=cuadro%><%=noProg%><%=1%>" disabled name="radioss<%=cuadro%><%=noProg%>" value="1" onclick="operacionesPertinencia22a(<%=cuadro%>);"/><label for="radios<%=cuadro%><%=noProg%>1" >Si</label>
                            <input type="radio" id="radios<%=cuadro%><%=noProg%><%=2%>" disabled name="radioss<%=cuadro%><%=noProg%>" value="0" onclick="operacionesPertinencia22a(<%=cuadro%>);" checked='checked'/><label for="radios<%=cuadro%><%=noProg%>2" >No</label>
                        </div>
                    </td>
                </tr>
                <%}%>
                <%
                        noProg++;
                    }
                    String matricula = "SELECT matricula FROM mat_total_universidad WHERE id_nivel=".concat(String.valueOf(niv.getString(1))).concat(" and id_universidad =").concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(sesion.getAttribute("idPeriodo").toString());
                    ResultSet mat = con.Consultar(matricula);
                    mat.next();
                %>
                <input type="hidden" name="noProg<%=cuadro%>" value="<%=noProg - 1%>" />
                
                <tr>
                    <td align="center" colspan="2">TOTAL</td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total1<%=cuadro%>' value='0' ><input type="hidden" name="mat1<%=cuadro%>" value="<%=mat.getString(1)%>"/></td>
                    
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total3<%=cuadro%>' value='0' ></td>
                    <td align="center" colspan="3"></td>
                    <td align="center" ><input readonly=\"readonly\" id="etiqueta" style='width:90%' type='text' name='total2<%=cuadro%>' value='0' ></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>DISTRIBUCI�N PORCENTUAL</td>
                      <td>&nbsp;</td>
                    
                    <td>PEP2</td>
                  
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>PEP1</td>
                </tr>
                <tfoot>
                <tr>
                        <td  colspan='2'><input type="hidden" id="totalPEE1" value="<%=contador%>" /></td>
                        <td>&nbsp;</td>
                       
                        <td><input type="text" name='porcentajes<%=cuadro%>' readonly="readonly" id=etiqueta style="width:90%" /></td>
                       
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                          <td>&nbsp;</td>
                           <td><input type="text" name='porcentaje<%=cuadro%>' readonly="readonly" id=etiqueta style="width:90%" /></td>
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
                }
            } catch (SQLException ex) {
                System.err.println("Jsp error Pertinencia 22: " + ex.getMessage());
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
        
        <html:hidden name="PertinenciaIn22Form" property="valores" styleId="valores" />
        <input type="hidden" name="noCuadros" value="<%=cuadro%>" />
    </div>   
    
       <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=22";
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
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarPertinenciaIn22(),'PertinenciaIn22Datos','PertinenciaIn22Form');" >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
<div id="pert22" align="center">
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>
