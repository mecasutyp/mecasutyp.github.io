<%--
    Document   : EficaciaIn9
    Created on : 9/05/2012, 01:49:16 PM
    Author     : Daniel Ram�rez Torres
    Actualizaci�n 2016: Salvador Zamora Arias
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%try {%>
<html:form action="/EficaciaIn9">
    <div id="efi9" align="center">
        <%
            //se recupera la sesion
            HttpSession sesion = request.getSession(false);
            int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
            ConexionMySQL con = new ConexionMySQL();
            int tsu = 0;
            int year = 0;
            int contador = 1, cont = 2, conte = 1, volver = 0;

            try {
                //se crea la consulta para recuperar los datos correspondientes a EficaciaIn9
                String sql2 = "select anio from periodos where id_periodo='".concat(sesion.getAttribute("idPeriodo").toString()).concat("'");
                ResultSet rs2 = con.Consultar(sql2);
                //si encuentra datos se los asigna a las variables creadas con anterioridad para
                //utilizarlas y poner los valores correspondientes en las cajas de texto
                String sql1 = "select distinct(pe.id_nivel),nombre,descripcion, abreviatura from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(") order by pe.id_nivel"));
                //String sql1 = "select distinct(pe.id_nivel),nombre,descripcion, abreviatura from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(" and version='2009') order by pe.id_nivel"));
                ResultSet rs1 = con.Consultar(sql1);

                String sql5 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(sesion.getAttribute("idUniversidad").toString()).concat("' and id_periodo ='").concat(sesion.getAttribute("idPeriodo").toString()).concat("' and anio=1");
                ResultSet rs5 = con.Consultar(sql5);
                rs5.next();
                
                if (rs5.getString("suma") != null) {
                    volver = 1;
                    if (rs2.next()) {
                        year = rs2.getInt("anio");
                    }
                    while (rs1.next()) {
                        if(rs1.getInt("id_nivel")<4){
                        if (rs1.getString("id_nivel").equals("3")) {
                            sql5 = "select sum(egresados) as suma, id_nivel from eficaciain5 where id_universidad='".concat(String.valueOf(idUniversidad)).concat("' and id_periodo ='").concat(String.valueOf(idPeriodo)).concat("' and id_nivel = '").concat(String.valueOf(rs1.getInt("id_nivel"))).concat("' and anio=2");
                        } else {
                            sql5 = "select sum(egresados) as suma, id_nivel from eficaciain5 where id_universidad='".concat(String.valueOf(idUniversidad)).concat("' and id_periodo ='").concat(String.valueOf(idPeriodo)).concat("' and id_nivel = '").concat(String.valueOf(rs1.getInt("id_nivel"))).concat("' and anio=1");
                        }
                       // System.err.println(sql5);
                        ResultSet rs4 = con.Consultar(sql5);
                        if (rs1.getString("id_nivel").equals("1")) {
                            String sql = "select egre_continuan,egre_continuan_ut,egre_continuan_ut_con from eficaciain9 where id_universidad ='".concat(String.valueOf(idUniversidad)).concat("' and id_periodo ='").concat(String.valueOf(idPeriodo)).concat("' and id_nivel=").concat(rs1.getString("id_nivel"));
                            ResultSet rs = con.Consultar(sql);
        %>
        <h2><a href='#'>Cuadro 9.1.<%=rs1.getString("id_nivel")%><br>
                Egresados de <%=rs1.getString("descripcion")%> en estudios superiores a seis meses de su egreso</a></h2>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                <input type="hidden" name='id<%=contador%>' value='<%=rs1.getString("id_nivel")%>' />
                <th scope="col" class="rounded-company">1</th>
                <th class="rounded-q1">2</th>
                <th class="rounded-q4">SUP1</th>
                </tr>
                <tr>
                    <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=year%> que contin�an estudios superiores en la UT</th>
                    <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=year%> </th>
                    <th align="center" class="rounded-q1">1/2 * 100</th>
                </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <%if (rs.next()) {%>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan<%=contador%>' value='<%=rs.getString("egre_continuan")%>' onblur="indicador9(this);"></td>
                        <%if (rs4.next()) {%>
                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='<%=rs4.getString("suma")%>' readonly="readonly" id="etiqueta"onblur="indicador9(this);"></td>
                        <% } else {%>
                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='0' readonly="readonly" id="etiqueta"onblur="indicador9(this);"></td>
                        <% }%>
                    <td><input type="text" style="width:90%" name="sup1<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2><a href='#'> Cuadro 9.2.<%=rs1.getString("id_nivel")%><br>
                Egresados de <%=rs1.getString("descripcion")%> en estudios superiores en una Universidad Tecn&oacute;logica a seis meses de su egreso </a></h2>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q1">3</th>
                        <th class="rounded-q1">4</th>
                        <th class="rounded-q1">SUP2</th>
                        <th class="rounded-q1">SUP3</th>
                        <th class="rounded-q4">SUP4</th>
                    </tr>
                    <tr>
                        <th width='20%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("abreviatura")%> de <%=year%> que continuan estudios superiores de licencia profesional en la UT</th>
                        <th width='20%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("abreviatura")%> de <%=year%> que continuan estudios superiores de licenciatura en la UT</th>
                        <th width='20%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("abreviatura")%> de <%=year%> que continuan estudios superiores de licencia profesional y licenciatura en la UT</th>
                        <th width='10%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("abreviatura")%> de <%=year%> </th>
                        <th width='10%' align="center" class="rounded-q1">1/4 * 100</th>
                        <th width='10%' align="center" class="rounded-q1">2/4 * 100</th>
                        <th width='10%' align="center" class="rounded-q1">3/4 * 100</th>
                    </tr>
                </thead>
                <tr>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut<%=contador%>' value='<%=rs.getString("egre_continuan_ut")%>' onblur="indicador9(this);"></td>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut_con<%=contador%>' value='<%=rs.getString("egre_continuan_ut_con")%>' onblur="indicador9(this);"></td>
                        <%} else {%>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan<%=contador%>' value='0' onblur="indicador9(this);"></td>
                        <%if (rs4.next()) {%>
                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='<%=rs4.getString("suma")%>' readonly="readonly" id="etiqueta" onblur="indicador9(this);"></td>
                        <%} else {%>

                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='0' readonly="readonly" id="etiqueta" onblur="indicador9(this);"></td>
                        <%}%>
                    <td><input type="text" style="width:90%" name="sup1<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>                    
                </tr>
            </table>
        </div>
        <h2><a href='#'>Cuadro 9.2.<%=rs1.getString("id_nivel")%><br>
                Egresados de <%=rs1.getString("descripcion")%> en estudios superiores en una Universidad Tecn&oacute;logica a seis meses de su egreso </a></h2>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q1">3</th>
                        <th class="rounded-q1">4</th>
                        <th class="rounded-q1">SUP2</th>
                        <th class="rounded-q1">SUP3</th>
                        <th class="rounded-q4">SUP4</th>
                    </tr>
                    <tr>
                        <th width='20%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> de <%=year%> que continuan estudios superiores de licencia profesional una UT</th>
                        <th width='20%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> de <%=year%> que continuan estudios superiores de licenciatura en una UT</th>
                        <th width='20%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> de <%=year%> que continuan estudios superiores de licencia profesional o licenciatura en una UT</th>
                        <th width='10%' align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=year%></th>
                        <th width='10%' align="center" class="rounded-q1">1/4 * 100</th>
                        <th width='10%' align="center" class="rounded-q1">2/4 * 100</th>
                        <th width='10%' align="center" class="rounded-q1">3/4 * 100</th>
                    </tr>
                </thead>
                <tr>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut<%=contador%>' value='0' onblur="indicador9(this);"></td>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut_con<%=contador%>' value='0' onblur="indicador9(this);"></td>
                        <%}%>
                    <td align="center"><input type="text" style="width:90%" name="egre_continuan_ut_es<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_tsu<%=contador%>' value='0' readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type='text' maxlength="6" name='sup2<%=contador%>' value='0' readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type="text" style="width:90%" name="sup3<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>
                    <td align="center"><input type="text" style="width:90%" name="sup4<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                <tfoot>
                    <tr>
                        <td colspan="6" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <%}
            if (!rs1.getString("id_nivel").equals("1")) {
                String sql = "select egre_continuan,egre_continuan_ut,egre_continuan_ut_con from eficaciain9 where id_universidad ='".concat(String.valueOf(idUniversidad)).concat("' and id_periodo ='").concat(String.valueOf(idPeriodo)).concat("' and id_nivel=").concat(rs1.getString("id_nivel"));
                ResultSet rs = con.Consultar(sql);
        %>
        <h2><a href='#'>Cuadro 9.1.<%=rs1.getString("id_nivel")%><br>
                Egresados de <%=rs1.getString("descripcion")%> en estudios superiores a seis meses de su egreso </a></h2>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                <input type="hidden" name='id<%=contador%>' value='<%=rs1.getString("id_nivel")%>' />
                <th scope="col" class="rounded-company">1</th>
                <th class="rounded-q1">2</th>
                <th class="rounded-q4">SUP1</th>
                </tr>
                <tr>
                    <%if (rs1.getString("id_nivel").equals("3")) {%>
                    <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year + 1)%> que continuan estudios de posgrado</th>
                    <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year + 1)%></th>
                    <%} else {%>                    
                    <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year)%> que continuan estudios superiores</th>
                    <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year)%></th>                    
                    <%}%>
                    <th align="center" class="rounded-q1">1/2 * 100</th>
                </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <%if (rs.next()) {%>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan<%=contador%>' value='<%=rs.getString("egre_continuan")%>' onblur="indicador9(this);"></td>
                        <%if (rs4.next()) {%>
                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='<%=rs4.getString("suma")%>' readonly="readonly" id="etiqueta" ></td>
                        <%} else {%>
                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='0' readonly="readonly" id="etiqueta" ></td>
                        <%}%>
                    <td><input type="text" style="width:90%" name="sup1<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2><a href='#'>Cuadro 9.2.<%=rs1.getString("id_nivel")%><br>
                Egresados de <%=rs1.getString("descripcion")%> en estudios superiores en una Universidad Tecn&oacute;logica a seis meses de su egreso</a></h2>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q4">SUP1</th>
                    </tr>
                    <tr>
                        <%if (rs1.getString("id_nivel").equals("3")) {%>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> de <%=Integer.valueOf(year + 1)%> que continuan estudios de posgrado en la UT</th>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year + 1)%></th>
                        <%} else {%>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> de <%=Integer.valueOf(year)%> que continuan estudios superiores en la UT</th>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year)%></th>
                        <%}%>
                        <th align="center" class="rounded-q1">1/2 * 100</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut<%=contador%>' value='<%=rs.getString("egre_continuan_ut")%>' onblur="indicador9(this);"></td>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut_con<%=contador%>' value='<%=rs.getString("egre_continuan_ut_con")%>' readonly="readonly" id="etiqueta" onblur="indicador9(this);"></td>
                        <%} else {%>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan<%=contador%>' value='0' onblur="indicador9(this);"></td>
                        <%if (rs4.next()) {%>
                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='<%=rs4.getString("suma")!=null ? rs4.getString("suma"): "0"%> ' readonly="readonly" id="etiqueta" onblur="indicador9(this);"></td>
                        <%} else {%>
                    <td align="center"><input type='text' maxlength="6" name='total_egresados<%=contador%>' value='0' readonly="readonly" id="etiqueta" onblur="indicador9(this);"></td>
                        <%}%>
                    <td><input type="text" style="width:90%" name="sup1<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <h2><a href='#'>Cuadro 9.<%=cont%> <br>
                Egresados de <%=rs1.getString("descripcion")%> en estudios superiores a seis meses de su egreso</a></h2>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q4">SUP1</th>
                    </tr>
                    <tr>
                        <%if (rs1.getString("id_nivel").equals("3")) {%>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> de <%=Integer.valueOf(year + 1)%> que continuan estudios superiores en la UT</th>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year + 1)%></th>
                        <%} else {%>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> de <%=Integer.valueOf(year)%> que continuan estudios superiores en la UT</th>
                        <th align="center" class="rounded-q1">Egresados de <%=rs1.getString("descripcion")%> en <%=Integer.valueOf(year)%></th>
                        <%}%>
                        <th align="center" class="rounded-q1">1/2 * 100</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
                <tr>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut<%=contador%>' value='0' onblur="indicador9(this);"></td>
                    <td align="center"><input type='text' maxlength="6" name='egre_continuan_ut_con<%=contador%>' value='0' onblur="indicador9(this);" readonly="readonly" id="etiqueta"></td>
                        <%}%>
                    <td><input type="text" style="width:90%" name="sup<%=contador%>" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
            </table>
        </div>
        <%}
                        conte += 2;
                        contador++;
                        cont += 2;
                        }
                    }
                } else {
                    volver = 0;
                }
            } catch (SQLException ex) {
                System.err.println("Error eficacin 9" + ex);
            } finally {
                con.Desconectar();
            }
        %>
    </div>
    <input type="hidden" name='tsu' value='<%=tsu%>' />
    <input type="hidden" name='noCuadros' value='<%=contador - 1%>' />
    <html:hidden name="EficaciaIn9Form" property="valores" styleId="valores" />
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=9";
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
    <%if (volver == 0) {%>
    <h7 class="error">
        Para poder ver este indicador, primero debe llenar el <br/>
        <a href="#" onclick='$("#tabs").tabs("select", 4);'>Indicador 5 de Eficacia</a>
    </h7>
    <%} else {
        String consulta = "select activo from system_mecasut";
        ResultSet rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
                    //<button type="button" class="btnGuardarIndicador" onclick="retrieveURL('/EficaciaIn9.msut?valores='+guardarEficaciaIn9(), 'EficaciaIn9Form'); " >Guardar</button>
%>
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarEficaciaIn9(),'EficaciaIn9Datos','EficaciaIn9Form');" >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }
        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
<%            }
%>


