<%--
    Document   : EficaciaIn3
    Created on : 10/05/2012, 12:34:12 PM
    Author     : Daniel Ram�rez Torres
    Actualizaci�n 2016: Salvador Zamora Arias
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page import="javax.servlet.http.HttpSession"%>

<%  try {
        HttpSession sesion = request.getSession(false);
        sesion.getAttribute("idUniversidad").toString();
        sesion.getAttribute("idPeriodo").toString();
        ConexionMySQL con = new ConexionMySQL();
        String sql3 = "Select distinct(pe.id_nivel), descripcion, abreviatura from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad=".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(") order by pe.id_nivel"));
        ResultSet rs3 = con.Consultar(sql3);
        rs3.next();
        String sql4 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(sesion.getAttribute("idUniversidad").toString()).concat("' and id_periodo ='").concat(sesion.getAttribute("idPeriodo").toString()).concat("'and id_nivel = '").concat(rs3.getString("id_nivel")).concat("' and anio=1");
        ResultSet rs4 = con.Consultar(sql4);
        rs4.next();
        String tablas = "";
        if (rs4.getString("suma") != null) {%>
<html:form action="/EficaciaIn8">
       <div id="efi8">        
        <%
            int c = 1;
            int cuadro = 1;
            String check = "0";
            String sql5 = "";
            try {
                rs3.beforeFirst();
                while (rs3.next()) {
                    String sql1 = "select anio from periodos where id_periodo='".concat(sesion.getAttribute("idPeriodo").toString()).concat("'");
                    ResultSet rs1 = con.Consultar(sql1);
                    //Agregar un OR si otro nivel puede aplicar Examen General de Egreso
                    if (rs3.getString("pe.id_nivel").equals("3") || rs3.getString("pe.id_nivel").equals("1") || rs3.getString("pe.id_nivel").equals("2")) {
                        String ex = "EGE".concat(rs3.getString("abreviatura"));
                        if (!rs3.getString("pe.id_nivel").equals("3")) {
                            sql5 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(sesion.getAttribute("idUniversidad").toString()).concat("' and id_periodo ='").concat(sesion.getAttribute("idPeriodo").toString()).concat("'and id_nivel = '").concat(rs3.getString("pe.id_nivel")).concat("' and anio=1");
                        } else {
                            sql5 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(sesion.getAttribute("idUniversidad").toString()).concat("' and id_periodo ='").concat(sesion.getAttribute("idPeriodo").toString()).concat("'and id_nivel = '").concat(rs3.getString("pe.id_nivel")).concat("' and anio=2");
                        }
                        ResultSet rs5 = con.Consultar(sql5);
                        if (rs1.next()) {
                            tablas = tablas.concat("<h2><a href='#'>Cuadro 8.1.").concat(rs3.getString("id_nivel")).concat("<br/>");
                            if (!rs3.getString("pe.id_nivel").equals("2")) {
                                tablas = tablas.concat(" Egresados de ").concat(rs3.getString("descripcion")).concat(" que Presentan el ").concat(ex).concat("</a></h2>");
                            } else {
                                tablas = tablas.concat(" Egresados de ").concat(rs3.getString("descripcion")).concat(" que Presentan Ex&aacute;men de Egreso</a></h2>");
                            }
                            tablas = tablas.concat("<div><table id='rounded-corner' style='width: 100%'>");
                            tablas = tablas.concat("<thead>");
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<th width='10%' class='rounded-company'>1</th>");
                            tablas = tablas.concat("<th  class='rounded-q1'>2</th>");
                            tablas = tablas.concat("<th  class='rounded-q1'>3</th>");
                            tablas = tablas.concat("<th  class='rounded-q1'>4</th>");
                            tablas = tablas.concat("<th  class='rounded-q4'>5</th>");
                            tablas = tablas.concat("</tr>");
                            tablas = tablas.concat("</thead>");
                            tablas = tablas.concat("<tr>");

                            if (!rs3.getString("pe.id_nivel").equals("2")) {
                                if (!rs3.getString("pe.id_nivel").equals("3")) {
                                    tablas = tablas.concat("<th width='10%' class='rounded-company'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" que presentaron el ").concat(ex).concat("</th>"));
                                    tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" </th>"));


                                    tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" con testimonio de desempe�o sobresaliente del ").concat(ex).concat("</h2>"));
                                    tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" con testimonio de desempe�o satisfactorio del ").concat(ex).concat("</h2>"));
                                    tablas = tablas.concat("<th class='rounded-q4'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" sin testimonio del ").concat(ex).concat("</h2>"));
                                } else {
                                    tablas = tablas.concat("<th width='10%' class='rounded-company'>Egresados de ".concat(String.valueOf(rs1.getInt("anio") + 1)).concat(" que presentaron el ").concat(ex).concat("</th>"));
                                    tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio") + 1)).concat(" </th>"));
                                    tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio") + 1)).concat(" con testimonio de desempe�o sobresaliente del ").concat(ex).concat("</h2>"));
                                    tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio") + 1)).concat(" con testimonio de desempe�o satisfactorio del ").concat(ex).concat("</h2>"));
                                    tablas = tablas.concat("<th class='rounded-q4'>Egresados de ".concat(String.valueOf(rs1.getInt("anio") + 1)).concat(" sin testimonio del ").concat(ex).concat("</h2>"));

                                }
                            } else {
                                tablas = tablas.concat("<th width='10%' class='rounded-company'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat("  que presentaron el examen de egreso</th>"));

                                tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" </th>"));

                                tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" con puntaje alto</h2>"));
                                tablas = tablas.concat("<th class='rounded-q1'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" con puntaje medio</h2>"));
                                tablas = tablas.concat("<th class='rounded-q4'>Egresados de ".concat(String.valueOf(rs1.getInt("anio"))).concat(" con puntaje bajo</h2>"));
                            }

                            tablas = tablas.concat("</tr>");
                            String sql2 = "select sobresaliente,destacado,sin_testimonio,aplica from eficaciain8 where id_universidad=".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(" and id_nivel='").concat(rs3.getString("id_nivel")).concat("'"));
                            ResultSet rs2 = con.Consultar(sql2);
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" maxlength='6' style='width:90%; text-align:center;' type='text' name='total").concat(String.valueOf(cuadro)).concat("' value='0'></td>");
                            if (rs5.next()) {
                                if (rs5.getString("suma") != null) {
                                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" maxlength='6' style='width:90%' type='text' name='egresados".concat(String.valueOf(cuadro)).concat("' value='").concat(rs5.getString("suma")).concat("' onblur='indicador8(this);'></td>"));
                                } else {
                                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" maxlength='6' style='width:90%' type='text' name='egresados".concat(String.valueOf(cuadro)).concat("' value='0' onblur='indicador8(this);'></td>"));
                                }
                            }
                            if (rs2.next()) {
                                tablas = tablas.concat("<td align='center'><input maxlength='6' style='width:90%' type='text' name='sobresaliente".concat(String.valueOf(cuadro)).concat("' value='".concat(rs2.getString("sobresaliente")).concat("' onblur='indicador8(this);'></td>")));
                                tablas = tablas.concat("<td align='center'><input maxlength='6' style='width:90%' type='text' name='satisfactorio".concat(String.valueOf(cuadro)).concat("' value='".concat(rs2.getString("destacado")).concat("' onblur='indicador8(this);'></td>")));
                                tablas = tablas.concat("<td align='center'><input maxlength='6' style='width:90%' type='text' name='sin_testimonio".concat(String.valueOf(cuadro)).concat("' value='".concat(rs2.getString("sin_testimonio")).concat("' onblur='indicador8(this);'></td>")));
                                tablas = tablas.concat("</tr>");
                            } else {
                                tablas = tablas.concat("<td align='center'><input maxlength='6' style='width:90%' type='text' name='sobresaliente".concat(String.valueOf(cuadro)).concat("' value='0' onblur='indicador8(this);'></td>"));
                                tablas = tablas.concat("<td align='center'><input maxlength='6' style='width:90%' type='text' name='satisfactorio".concat(String.valueOf(cuadro)).concat("' value='0' onblur='indicador8(this);'></td>"));
                                tablas = tablas.concat("<td align='center'><input maxlength='6' style='width:90%' type='text' name='sin_testimonio".concat(String.valueOf(cuadro)).concat("' value='0' onblur='indicador8(this);'></td>"));
                                tablas = tablas.concat("</tr>");
                            }

                            tablas = tablas.concat("<tr>");
                            if (!rs3.getString("id_nivel").equals("2")) {
                                tablas = tablas.concat("<td colspan='2'>").concat(ex.substring(0, 4)).concat("A</td>");
                                tablas = tablas.concat("<td>").concat(ex.substring(0, 4)).concat("B</td>");
                                tablas = tablas.concat("<td>").concat(ex.substring(0, 4)).concat("C</td>");
                                tablas = tablas.concat("<td>").concat(ex.substring(0, 4)).concat("D</td>");
                            } else {
                                tablas = tablas.concat("<td colspan='2'>EXAA</td>");
                                tablas = tablas.concat("<td>EXAB</td>");
                                tablas = tablas.concat("<td>EXAC</td>");
                                tablas = tablas.concat("<td>EXAD</td>");
                            }
                            tablas = tablas.concat("</tr>");
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td colspan='2'>1/2 </td>");
                            tablas = tablas.concat("<td>3/1</td>");
                            tablas = tablas.concat("<td>4/1</td>");
                            tablas = tablas.concat("<td>5/1</td>");
                            tablas = tablas.concat("</tr>");

                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='div1".concat(String.valueOf(cuadro)).concat("' value='0'></td>"));
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='div2".concat(String.valueOf(cuadro)).concat("' value='0'></td>"));
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='div3".concat(String.valueOf(cuadro)).concat("' value='0'></td>"));
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='div4".concat(String.valueOf(cuadro)).concat("' value='0'></td>"));
                            tablas = tablas.concat("</tr>");
                            tablas = tablas.concat("<tfoot>");
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td colspan='2' class='rounded-foot-left'>");
                            tablas = tablas.concat("<input  type='hidden' name='id_cuadro").concat(String.valueOf(cuadro)).concat("' value='").concat(rs3.getString("id_nivel")).concat("'>Notas: No hay notas adicionales.</td>");
                            rs2.beforeFirst();

                            if (rs2.next()) {
                                if (rs2.getString("aplica").equals("1")) {
                                    tablas = tablas.concat("<td>�La universidad aplic&oacute; el ").concat(ex).concat(" a la generaci&oacute;n que egreso en del ciclo anterior?</td>");
                                    tablas = tablas.concat("<td><div id='radios1").concat(String.valueOf(cuadro)).concat("'>");
                                    tablas = tablas.concat("<input type='radio' id='radio1").concat(String.valueOf(cuadro)).concat("' name='radio").concat(String.valueOf(cuadro)).concat("' value='1' checked='checked' onclick='checkEficaciaIn8();'/> <label for='radio1").concat(String.valueOf(cuadro)).concat("'>Si se aplic&oacute;</label>");
                                    tablas = tablas.concat("<input type='radio' id='radio2").concat(String.valueOf(cuadro)).concat("' name='radio").concat(String.valueOf(cuadro)).concat("' value='0' onclick='checkEficaciaIn8();'/> <label for='radio2").concat(String.valueOf(cuadro)).concat("'>No se aplic&oacute;</label>");
                                    tablas = tablas.concat("</div></td>");
                                } else {
                                    tablas = tablas.concat("<td>�La universidad aplic&oacute; el ").concat(ex).concat(" a la generaci&oacute;n que egreso en del ciclo anterior?</td>");
                                    tablas = tablas.concat("<td><div id='radios1").concat(String.valueOf(cuadro)).concat("'>");
                                    tablas = tablas.concat("<input type='radio' id='radio1").concat(String.valueOf(cuadro)).concat("' name='radio").concat(String.valueOf(cuadro)).concat("' value='1' onclick='checkEficaciaIn8();'/> <label for='radio1").concat(String.valueOf(cuadro)).concat("'>Si se aplic&oacute;</label>");
                                    tablas = tablas.concat("<input type='radio' id='radio2").concat(String.valueOf(cuadro)).concat("' name='radio").concat(String.valueOf(cuadro)).concat("' value='0' checked='checked' onclick='checkEficaciaIn8();'/> <label for='radio2").concat(String.valueOf(cuadro)).concat("'>No se aplic&oacute;</label>");
                                    tablas = tablas.concat("</div></td>");
                                }
                            } else {
                                tablas = tablas.concat("<td>�La universidad aplic&oacute; el ").concat(ex).concat(" a la generaci&oacute;n que egreso en del ciclo anterior?</td>");
                                tablas = tablas.concat("<td><div id='radios1").concat(String.valueOf(cuadro)).concat("'>");
                                tablas = tablas.concat("<input type='radio' id='radio1").concat(String.valueOf(cuadro)).concat("' name='radio").concat(String.valueOf(cuadro)).concat("' value='1' onclick='checkEficaciaIn8();'/> <label for='radio1").concat(String.valueOf(cuadro)).concat("'>Si se aplic&oacute;</label>");
                                tablas = tablas.concat("<input type='radio' id='radio2").concat(String.valueOf(cuadro)).concat("' name='radio").concat(String.valueOf(cuadro)).concat("' value='0' checked='checked' onclick='checkEficaciaIn8();'/> <label for='radio2").concat(String.valueOf(cuadro)).concat("'>No se aplic&oacute;</label>");
                                tablas = tablas.concat("</div></td>");
                            }
                            tablas = tablas.concat("<td class='rounded-foot-right'></td>");
                            tablas = tablas.concat("</tr>");
                            tablas = tablas.concat("</tfoot>");
                            tablas = tablas.concat("</table></div>");
                            c++;
                            cuadro++;
                        }
                    }
                }

            } catch (SQLException ex) {
                System.err.println("JSPERROR-EficaciaIn8:" + ex.getMessage());
            } finally {
                con.Desconectar();
            }
        %>
        <%=tablas%>
        <input type="hidden" name="fin" />        
        <input type="hidden" name="nocuadros" value="<%=c - 1%>" />
        <html:hidden name="EficaciaIn8Form" property="valores" styleId="valores" />
    </div>
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=8";
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
                    //<button type="button" class="btnGuardarIndicador" onclick="retrieveURL('/EficaciaIn8.msut?valores='+guardarEficaciaIn8(), 'EficaciaIn8Form'); " >Guardar</button>
    %>
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarEficaciaIn8(),'EficaciaIn8Datos','EficaciaIn8Form');"  >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }%>
</html:form>
<%
} else {
%>
<div class="error">
    Para poder ver este indicador, primero debe llenar el <br/>
    <a href="#" onclick='$("#tabs").tabs("select", 4);'>Indicador 5 de Eficacia</a>
</div>
<%
        con.Desconectar();
    }
} catch (NullPointerException exNull) {
%>
<div id="efi8" align="center">   
    La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>