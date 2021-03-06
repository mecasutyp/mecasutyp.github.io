<%--
    Document   : EficaciaIn6
    Created on : 9/05/2012, 01:49:02 PM
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
<html:form action="/EficaciaIn6">
    <div id="efi6" align="center">
        <%
            //se recupera la sesion
            HttpSession sesion = request.getSession(false);
            String idUniversidad = sesion.getAttribute("idUniversidad").toString();
            String idPeriodo = sesion.getAttribute("idPeriodo").toString();
            String tabla6 = "";
            int volver = 0;
            int indicadores;
            ConexionMySQL con = new ConexionMySQL();
            int num = 0;
            int programaeducativo = 1;
            try 
            {
                
                int total = 1;
                //se crea la consulta para recuperar el nivel, nombre del nivel y descripcion del nivel
                String sql = "Select distinct(pe.id_nivel),  n.descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = '".concat(idUniversidad).concat("' and id_periodo ='").concat(idPeriodo).concat("') order by id_nivel");
                ResultSet rs = con.Consultar(sql);
                String sql5 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(sesion.getAttribute("idUniversidad").toString()).concat("' and id_periodo ='").concat(sesion.getAttribute("idPeriodo").toString()).concat("' and anio=1");
                ResultSet rs5 = con.Consultar(sql5);
                rs5.next();
                if (rs5.getString("suma") != null) 
                {
                    volver = 1;
                                      
                    while(rs.next())
                    {
                        String sql3 = "select anio from periodos where id_periodo='".concat(String.valueOf(sesion.getAttribute("idPeriodo"))).concat("'");
                        ResultSet rs3 = con.Consultar(sql3);
                        if (rs3.next()) 
                        {
                            tabla6 = tabla6.concat("<h3><a href='#'>Cuadro 6.1.").concat(rs.getString("id_nivel")).concat("<br>");
                            tabla6 = tabla6.concat("Egresados de ").concat(rs.getString("n.descripcion")).concat(" incorporados al mercado laboral y que trabajan en &Aacute;rea afin</a></h3>");
                            tabla6 = tabla6.concat("<div align='center'><table width='60%' id='rounded-corner'>");
                            tabla6 = tabla6.concat("<thead>");
                            tabla6 = tabla6.concat("<tr>");
                            tabla6 = tabla6.concat("<th class='rounded-company'>1</th>");
                            tabla6 = tabla6.concat("<th class='rounded-q1'>2</th>");
                            tabla6 = tabla6.concat("<th class='rounded-q4'>3</th>");
                            tabla6 = tabla6.concat("</tr>");
                            tabla6 = tabla6.concat("<tr>");
                            if (rs.getInt("id_nivel") == 1 || rs.getInt("id_nivel") == 2) 
                            {

                                tabla6 = tabla6.concat("<td width='20%'>Egresados de ").concat(String.valueOf(rs3.getInt("anio"))).concat(" trabajando a seis meses de egreso</td>");
                                tabla6 = tabla6.concat("<td width='20%'>Total de egresados de  ").concat(String.valueOf(rs3.getInt("anio"))).concat("</td>");
                                tabla6 = tabla6.concat("<td width='20%'>Egresados ").concat(String.valueOf(rs3.getInt("anio"))).concat(" cuya actividad laboral es acorde a su formaci�n acad�mica por programa educativo</td>");
                            } else 
                            {
                                tabla6 = tabla6.concat("<td width='20%'>Egresados de ").concat(String.valueOf(rs3.getInt("anio") + 1)).concat(" trabajando a seis meses de egreso</td>");
                                tabla6 = tabla6.concat("<td width='20%'>Total de egresados de ").concat(String.valueOf(rs3.getInt("anio") + 1)).concat(" que a la fecha ya cumplieron 6 meses de haber egresado </td>");
                                tabla6 = tabla6.concat("<td width='20%'>Egresados ").concat(String.valueOf(rs3.getInt("anio") + 1)).concat(" cuya actividad laboral es acorde a su formaci�n acad�mica por programa educativo</td>");

                            }
                            tabla6 = tabla6.concat("</tr>");
                            tabla6 = tabla6.concat("</thead>");
                        }
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("</thead>");
                        tabla6 = tabla6.concat("<tr>");
                        String sql2 = "select e_trabajando, afin from eficaciain6 where id_universidad ='".concat(idUniversidad).concat("' and id_periodo ='").concat(idPeriodo).concat("' and id_nivel = '").concat(String.valueOf(rs.getInt("id_nivel"))).concat("'");
                        ResultSet rs2 = con.Consultar(sql2);
                        String sql4 = null;
                        if (rs.getInt("id_nivel") != 3) 
                        {
                            sql4 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(idUniversidad).concat("' and id_periodo ='").concat(idPeriodo).concat("'and id_nivel = '").concat(String.valueOf(rs.getInt("id_nivel"))).concat("' and anio=1");
                        } else 
                        {
                            sql4 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(idUniversidad).concat("' and id_periodo ='").concat(idPeriodo).concat("'and id_nivel = '").concat(String.valueOf(rs.getInt("id_nivel"))).concat("' and anio=2");
                        }

                        ResultSet rs4 = con.Consultar(sql4);
                        //si encuentra datos correspondientes a EficaciaIn6
                        if (rs2.next()) 
                        {
                            
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='e_trabajando").concat(String.valueOf(num)).concat("' value='").concat(String.valueOf(rs2.getInt("e_trabajando"))).concat("' onblur='indicador6(this);' ></td>");

                            if (rs4.next()) 
                            {
                                if (rs4.getString("suma") != null) 
                                {
                                    tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totalegresados").concat(String.valueOf(num)).concat("' value='").concat(rs4.getString("suma")).concat("' onblur='indicador6(this);' ></td>");
                                } else 
                                {
                                    tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totalegresados").concat(String.valueOf(num)).concat("' value='0' onblur='indicador6(this);' ></td>");
                                }
                            }
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='afin").concat(String.valueOf(num)).concat("' value='").concat(String.valueOf(rs2.getInt("afin"))).concat("' onblur='indicador6(this);' ></td>");
                            
                        } else 
                        {
                            //caso contrario a las cajas de texto les pone el valor "0"
                            
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='e_trabajando").concat(String.valueOf(num)).concat("' value='0' onblur='indicador6(this);' ></td>");
                            if (rs4.next()) 
                            {
                                if (rs4.getString("suma") != null) 
                                {
                                    tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totalegresados").concat(String.valueOf(num)).concat("' value='").concat(rs4.getString("suma")).concat("' onblur='indicador6(this);' ></td>");
                                } else 
                                {
                                    tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totalegresados").concat(String.valueOf(num)).concat("' value='0' onblur='indicador6(this);' ></td>");
                                }
                            }
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='afin").concat(String.valueOf(num)).concat("' value='0' onblur='indicador6(this);' ></td>");
                            
                        }
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<td colspan='2' align='center'>EML<br/>1/2</td>");
                        tabla6 = tabla6.concat("<td align='center'>ETFA<br/>3/1</td>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='eml").concat(String.valueOf(num)).concat("' value='0'></td>");
                        tabla6 = tabla6.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='eta").concat(String.valueOf(num)).concat("' value='0'></td>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("<tfoot>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<td colspan='2' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>");
                        tabla6 = tabla6.concat("<td class='rounded-foot-right'><input type='hidden' name='programaeducativo").concat(String.valueOf(num)).concat("' value='").concat(String.valueOf(rs.getInt("id_nivel"))).concat("' /></td>");
                        
                        tabla6 = tabla6.concat("</tr>");
                        
                        tabla6 = tabla6.concat("</tfoot>");
                        tabla6 = tabla6.concat("</table></div>");
                        
                        num++;
                        programaeducativo++;
                    }
                       
                    rs.beforeFirst();
                    num=0;
                    programaeducativo = 1;
                    while(rs.next())
                    {
                        String sql2 = "select esprimario, essecundario, esterciario from eficaciain6 where id_universidad ='".concat(idUniversidad).concat("' and id_periodo ='").concat(idPeriodo).concat("' and id_nivel = '").concat(String.valueOf(rs.getInt("id_nivel"))).concat("'");
                        ResultSet rs2 = con.Consultar(sql2);
                        tabla6 = tabla6.concat("<h3><a href='#'>Cuadro 6.2.").concat(rs.getString("id_nivel")).concat("<br>");
                        tabla6 = tabla6.concat("Egresados de ").concat(rs.getString("n.descripcion")).concat("  incorporados al mercado laboral y que trabajan en �rea af�n por sector</a></h3>");
                        tabla6 = tabla6.concat("<div align='center'><table width='60%' id='rounded-corner'>");
                        tabla6 = tabla6.concat("<thead>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<th width='10%' class='rounded-company'>1</th>");
                        tabla6 = tabla6.concat("<th  class='rounded-q1'>2</th>");
                        tabla6 = tabla6.concat("<th  class='rounded-q1'>3</th>");
                        tabla6 = tabla6.concat("<th  class='rounded-q4'>4</th>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<th width='20%'>Egresados laborando en el Sector primario</th>");
                        tabla6 = tabla6.concat("<th width='20%'>Egresados laborando en el Sector secundario</th>");
                        tabla6 = tabla6.concat("<th width='20%'>Egresados laborando en el Sector terciario</th>");
                        tabla6 = tabla6.concat("<th width='20%'>Total</th>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("<tr>");
                        if(rs2.next())
                        {
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='esp").concat(String.valueOf(num)).concat("' value='").concat(rs2.getString("esprimario")).concat("' onblur='indicador6(this);' ></td>");
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='ess").concat(String.valueOf(num)).concat("' value='").concat(rs2.getString("essecundario")).concat("' onblur='indicador6(this);' ></td>");
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='est").concat(String.valueOf(num)).concat("'' value='").concat(rs2.getString("esterciario")).concat("' onblur='indicador6(this);' ></td>");
                        }else
                        {
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='esp").concat(String.valueOf(num)).concat("' value='0' onblur='indicador6(this);' ></td>");
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='ess").concat(String.valueOf(num)).concat("' value='0' onblur='indicador6(this);' ></td>");
                            tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' name='est").concat(String.valueOf(num)).concat("'' value='0' onblur='indicador6(this);' ></td>");
                        }
                        
                        tabla6 = tabla6.concat("<td width='20%' align='center'><input maxlength='6' style='width:90%' type='text' id='etiqueta' name='tegresadossector").concat(String.valueOf(num)).concat("'' value='0' onblur='indicador6(this);' ></td>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<td align='center'>EMLSP<br/></td>");
                        tabla6 = tabla6.concat("<td align='center'>EMLSS<br/></td>");
                        tabla6 = tabla6.concat("<td align='center'>EMLST<br/></td>");
                        tabla6 = tabla6.concat("<td align='center'><br/></td>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("</thead>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totemlsp").concat(String.valueOf(num)).concat("' value='0'></td>");
                        tabla6 = tabla6.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totemlss").concat(String.valueOf(num)).concat("' value='0'></td>");
                        tabla6 = tabla6.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totemlst").concat(String.valueOf(num)).concat("' value='0'></td>");
                        tabla6 = tabla6.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='totalsector").concat(String.valueOf(num)).concat("' value='0'></td>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("<tfoot>");
                        tabla6 = tabla6.concat("<tr>");
                        tabla6 = tabla6.concat("<td colspan='3' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>");
                        tabla6 = tabla6.concat("<td class='rounded-foot-right'></td>");
                        tabla6 = tabla6.concat("</tr>");
                        tabla6 = tabla6.concat("</tfoot>");
                        
                        tabla6 = tabla6.concat("</table></div>");
                        
                        num++;
                        programaeducativo++;
                    }
                    
                    rs.beforeFirst();
                    num=0;
                    programaeducativo=1;
                    while(rs.next())
                    {
                        String sql2 = "select  emleag, emlmin,emlatc, emlaco, emlsfs, emlaim, emlapa, emlasp, emlacm from eficaciain6 where id_universidad ='".concat(idUniversidad).concat("' and id_periodo ='").concat(idPeriodo).concat("' and id_nivel = '").concat(String.valueOf(rs.getInt("id_nivel"))).concat("'");
                        ResultSet rs2 = con.Consultar(sql2);
                        num++;
                        programaeducativo++;
                    }
                }
                 tabla6 = tabla6.concat("<input type='hidden' name='niveles").concat("' value='").concat(String.valueOf(num)).concat("' />");
            } catch (SQLException ex) {
                System.err.println("Error eficacia 6: " + ex);
            } finally {
                con.Desconectar();
            }
                  
%>
        

        <%=tabla6%>
      
        <input type="hidden" name="nivelesuniversidad" value="<%=num %>" />
        <input type="hidden" name="fin" />
        
        <html:hidden name="EficaciaIn6Form" property="valores" styleId="valores"/>
    </div>
    
     <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consulta = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=6";
        ResultSet rs = con.Consultar(consulta);      
        String comentario="Sin comentarios";
        if(rs.next()){//SI HAY UN COMENTARIO REGISTRADO SE OBTIENE PARA MOSTRARSE
            comentario = rs.getString("comentario");
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
         consulta = "select activo from system_mecasut";
        rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
    %>
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(guardarEficaciaIn6(),'EficaciaIn6Datos','EficaciaIn6Form');" >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluaci�n ha sido desactivada.</button>
    <%        }
        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
    System.err.println("Error indicador 6 : " + exNull);
%>

La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
</div>
<%            }
%>

