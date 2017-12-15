<%-- 
    Document   : EficaciaIn4
    Created on : 3/05/2012, 11:15:37 AM
    Author     : Joshua Luévano Daniel Ramírez
--%>


<%@page import="com.mecasut.shared.Universidad"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%  try {
%>
<html:form action="/EficaciaIn4">
    <div id="efi4" align="center"> 
        <%
            Universidad uni = new Universidad();
            HttpSession sesion = request.getSession(false);
            String idUniversidad = sesion.getAttribute("idUniversidad").toString();
            String idPeriodo = sesion.getAttribute("idPeriodo").toString();
            String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
            String tablas = "";
            ConexionMySQL con = new ConexionMySQL();
            int no_niv = 1;
            try {
                int c = 1, c2 = 1,  no_fi = 1, cu = 1, ctot = 1;

                String sql = "Select id_causa, descripcion from causas_desercion where activo=1";
                String sql2 = "Select distinct(pe.id_nivel), n.descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(") order by pe.id_nivel");
                ResultSet rs2 = con.Consultar(sql2);
                ResultSet rs = con.Consultar(sql);

                //DECLARACION DE RESULTADO PARA LAS CARRERAS POR nivel
                while (rs2.next()) {
                       int sumcol[]= new int[6];
                    //SE CONSULTA PROGRAMAS EDUCATIVOS
                    String sqlpe = "SELECT * FROM pe_universidad pu INNER JOIN programa_educativo pe ON pe.id_pe=pu.id_pe  WHERE pu.id_universidad=".concat(sesion.getAttribute("idUniversidad").toString())
                        .concat(" and pu.id_periodo=".concat(sesion.getAttribute("idPeriodo").toString())
                        .concat(" and pe.id_nivel='".concat(rs2.getString(1)).concat("'")));;
                    ResultSet rspe = con.Consultar(sqlpe);
                    //TITULOS
                    tablas = tablas.concat("<h2><a href='#'>Cuadro 4.1.".concat(rs2.getString("pe.id_nivel")).concat(" <br/>").concat(rs2.getString("n.descripcion")).concat(" - Deserci&oacute;n Cuatrimestral<br/>"));
                    tablas = tablas.concat("</a><input type='text' style='display:none;'name='niv_".concat(String.valueOf(c)).concat("' value='").concat(rs2.getString("id_nivel"))+"'/></h2>" );
                    //ENCABEZADOS DE LA TABLA
                    tablas = tablas.concat("<div><table id='rounded-corner' style='width: 100%'>");
                    tablas = tablas.concat("<thead>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<th scope='col' class='rounded-company'>1</th>");
                    tablas = tablas.concat("<th class='rounded-q1'>2</th>");
                    tablas = tablas.concat("<th class='rounded-q1'>3</th>");
                    tablas = tablas.concat("<th class='rounded-q1'>4</th>");
                    tablas = tablas.concat("<th class='rounded-q1'>5</th>");
                    tablas = tablas.concat(" <th class='rounded-q1'>6</th>");
                    tablas = tablas.concat(" <th class='rounded-q4'>7</th>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<th width=25%' class='rounded-q1'>Programa educativo</th>");
                    tablas = tablas.concat("<th width='12%'class='rounded-q1'>Alumnos desertores definitivos del cuatrimestre Septiembre-Diciembre</th>");
                    tablas = tablas.concat("<th width='12%'class='rounded-q1'>Matr&iacute;cula inicial atendida cuatrimestre Sep-Dic</th>");
                    tablas = tablas.concat("<th width='12%'class='rounded-q1'>Alumnos desertores definitivos del cuatrimestre enero-abril</th>");
                    tablas = tablas.concat("<th width='12%'class='rounded-q1'>Matr&iacute;cula inicial atendida cuatrimestre Enero-Abril</th>");
                    tablas = tablas.concat("<th width='12%'class='rounded-q1'>Alumnos desertores definitivos del cuatrimestre Mayo-Agosto</th>");
                    tablas = tablas.concat("<th width='12%'class='rounded-q1'>Matr&iacute;cula inicial atendida cuatrimestre Mayo-Agosto</th>");
//                    tablas = tablas.concat("<th width='10%' class='rounded-q1'>% Promedio de Deserci&oacute;n</th>");
                    tablas = tablas.concat("</tr>");

                    tablas = tablas.concat("</thead>");
                    int prog=1; 
                    sql = "Select mat_sep, mat_ene, mat_may from eficaciain3 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(rs2.getString("pe.id_nivel"))+" AND id_pe!=0";
                    rs = con.Consultar(sql);
                while(rspe.next()){
                    
//                    sql = "Select mat_sep, mat_ene, mat_may from eficaciain3 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(rs2.getString("pe.id_nivel"));
                            //.concat(" AND id_pe="+rspe.getString("id_pe"));
                    if (rs.next()) {
                        String sql3 = "SELECT tot_deser_sep, tot_deser_ene, tot_deser_may FROM eficaciain4_1 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(rs2.getString("pe.id_nivel")).concat(" AND id_pe="+rspe.getString("id_pe"));
                        ResultSet rs3 = con.Consultar(sql3);
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td align='center' width='25%'>"+rspe.getString("nombre_programa")+" ("+rspe.getString("version") +") ");
                        tablas = tablas.concat("<input type='hidden' id='id_prog"+c+"-"+prog+"' name='id_prog"+c+"-"+prog+"' value='"+rspe.getString("id_pe") +"'</td>");
                        if (rs3.next()) {
                            tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_dessep" + cu + "_"+prog+"' value='").concat(rs3.getString("tot_deser_sep")).concat("' onblur='indicador4(this," + cu + ",0,1);'></td>");
                            sumcol[0]+=rs3.getInt("tot_deser_sep");
                        } else {
                            tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_dessep" + cu + "_"+prog+"' value='0' onblur='indicador4(this," + cu + ",0,1);'></td>");
                        }
                        rs3.beforeFirst();
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='matSep" + cu + "-"+prog+"' value='").concat(rs.getString("mat_sep")).concat("'></td>");
                        sumcol[1]+=rs.getInt("mat_sep");
                        if (rs3.next()) {
                            tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_desene" + cu + "_"+prog+"' value='").concat(rs3.getString("tot_deser_ene")).concat("' onblur='indicador4(this," + cu + ",0,1);'></td>");
                            sumcol[2]+=rs3.getInt("tot_deser_ene");
                        } else {
                            tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_desene" + cu + "_"+prog+"' value='0' onblur='indicador4(this," + cu + ",0,1);'></td>");
                        }
                        rs3.beforeFirst();
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='matEne" + cu + "-"+prog+"' value='").concat(rs.getString("mat_ene")).concat("'></td>");
                        sumcol[3]+=rs.getInt("mat_ene");
                        if (rs3.next()) {
                            tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_desmay" + cu + "_"+prog+"' value='").concat(rs3.getString("tot_deser_may")).concat("' onblur='indicador4(this," + cu + ",0,1);'></td>");
                            sumcol[4]+=rs3.getInt("tot_deser_may");
                        } else {
                            tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_desmay" + cu + "_"+prog+"' value='0' onblur='indicador4(this," + cu + ",0,1);'></td>");
                        }
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='matMay" + cu + "-"+prog+"' value='").concat(rs.getString("mat_may")).concat("'></td>");
                        sumcol[5]+=rs.getInt("mat_may");
                        tablas = tablas.concat("</tr>");
                    } else {
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td align='center'>"+rspe.getString("nombre_programa")+" ("+rspe.getString("version") +") </td>");
                        tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_deser" + cu + "_1' value='0' onblur='indicador4(this," + cu + ",0,1);'></td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='matSep" + cu + "' value='0' ></td>");
                        tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_deser" + cu + "_2' value='0' onblur='indicador4(this," + cu + ",0,1);'></td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='matEne" + cu + "' value='0'></td>");
                        tablas = tablas.concat("<td align='center'><input style='width:90%' type='text' name='tot_deser" + cu + "_3' value='0' onblur='indicador4(this," + cu + ",0,1);'></td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='matMay" + cu + "' value='0'></td>");
//                        tablas = tablas.concat("<td rowspan='3' align='center'>RET</td>");
                        tablas = tablas.concat("</tr>");

                    }
                    prog++;
                }
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td align='center'><strong>TOTAL</strong></td>");
                      for(int z=0;z<6;z++){
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name=\"totalcol,"+c+"-"+z+"\" value='"+sumcol[z]+"'></td>");
                        
                    }
                    tablas = tablas.concat("</tr>");
//                    
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td rowspan ='5'align='center'></td>");
                    tablas = tablas.concat("<td colspan='2' align='center'>RE1</td>");
                    tablas = tablas.concat("<td colspan='2' align='center'>RE2</td>");
                    tablas = tablas.concat("<td colspan='2' align='center'>RE3</td>");
                    tablas = tablas.concat("</tr>");

                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='2' align='center'>2/3</td>");
                    tablas = tablas.concat("<td colspan='2' align='center'>4/5</td>");
                    tablas = tablas.concat("<td colspan='2' align='center'>6/7</td>");
                    tablas = tablas.concat("</tr>");

                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='Div" + cu + "_1' value='0' ></td>");
                    tablas = tablas.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='Div" + cu + "_2' value='0' ></td>");
                    tablas = tablas.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='Div" + cu + "_3' value='0' ></td>");
                    tablas = tablas.concat("</tr>");
//                    
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='6' align='center'>RET</td>");
                    tablas = tablas.concat("</tr>");
                    
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='6' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='promD" + cu + "' value='0' onblur='operacionesEficacia4(" + cu + ",0,1);'></td>");
                    tablas = tablas.concat("</tr>");

                    //PIE DE LA TABLE
                    tablas = tablas.concat("<tfoot>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='6' class='rounded-foot-left'>Notas: No hay notas adicionales.");
                    tablas = tablas.concat("<input type='hidden' name ='num_prog"+c+"' id='num_prog"+c+"' value='"+prog+"' /></td>");
                    tablas = tablas.concat("<td class='rounded-foot-right'></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</tfoot>");
                    tablas = tablas.concat("</table></div>");
                    c++;
                    cu++;
                }
                
                rs2.beforeFirst();
                sql = "Select id_causa, descripcion from causas_desercion where activo=1";
                sql2 = "Select distinct(pe.id_nivel), n.descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(") order by pe.id_nivel");
                rs2 = con.Consultar(sql2);
                rs = con.Consultar(sql);
                while (rs2.next()) {
                    tablas = tablas.concat("<h3><a href='#'>Cuadro 4.2.").concat(rs2.getString("pe.id_nivel")).concat("<br/>");
                    tablas = tablas.concat("Principales Causas de Deserci&oacute;n del Nivel ").concat(rs2.getString("n.descripcion")).concat(" </a></h3>");
                    tablas = tablas.concat("<div><table id='rounded-corner' style='width: 100%'>");
                    tablas = tablas.concat("<thead>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<th rowspan='2' class='rounded-company'>Causas de deserci&oacute;n</th>");
                    tablas = tablas.concat("<th colspan='3' class='rounded-q1'>Alumnos desertores por cuatrimestre</th>");
                    tablas = tablas.concat("<th rowspan='2' class='rounded-q4'>Total</th>");
                    tablas = tablas.concat("<input type='hidden' name='id_nivel" + no_niv + "' value='" + rs2.getString("pe.id_nivel") + "' />");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td>Cuatrimestre Septiembre - Diciembre</td>");
                    tablas = tablas.concat("<td>Cuatrimestre Enero - Abril</td>");
                    tablas = tablas.concat("<td>Cuatrimestre Mayo - Agosto</td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</thead>");
                    while (rs.next()) {
                        String sql3 = "Select sep, ene, may from eficaciain4 where id_universidad = ".concat(idUniversidad).concat(" and id_periodo = ").concat(idPeriodo).concat(" and id_nivel=").concat(rs2.getString("pe.id_nivel")).concat(" and id_causa=").concat(rs.getString("id_causa"));
                        ResultSet rs3 = con.Consultar(sql3);
                        if (rs3.next()) {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>").concat(rs.getString("descripcion")).concat("<input type='hidden' name='id_causa" + no_niv + "_" + no_fi + "' value='" +rs.getString("id_causa") + "' /></td>");
                            tablas = tablas.concat("<td align='center'><input id='sep_dic_" + no_niv + "_" + no_fi + "' maxlength='6' style='width:90%' type='text' name='val".concat(rs2.getString("id_nivel")).concat(",").concat(rs.getString("id_causa")).concat("_1' value='".concat(rs3.getString("sep")).concat("' onblur='indicador4(this," + no_niv + ",0,0);' ></td>")));
                            tablas = tablas.concat("<td align='center'><input id='ene_abr_" + no_niv + "_" + no_fi + "' maxlength='6' style='width:90%' type='text' name='val".concat(rs2.getString("id_nivel")).concat(",").concat(rs.getString("id_causa")).concat("_2' value='".concat(rs3.getString("ene")).concat("' onblur='indicador4(this," + no_niv + ",0,0);' ></td>")));
                            tablas = tablas.concat("<td align='center'><input id='may_ago_" + no_niv + "_" + no_fi + "' maxlength='6' style='width:90%' type='text' name='val".concat(rs2.getString("id_nivel")).concat(",").concat(rs.getString("id_causa")).concat("_3' value='".concat(rs3.getString("may")).concat("' onblur='indicador4(this," + no_niv + ",0,0);' ></td>")));
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th" + no_niv + "_" + no_fi + "' value='0'></td>");
                            tablas = tablas.concat("</tr>");
                        } else {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>").concat(rs.getString("descripcion")).concat("<input type='hidden' name='id_causa" + no_niv + "_" + no_fi + "' value='" + rs.getString(1) + "' /></td>");
                            tablas = tablas.concat("<td align='center'><input id='sep_dic_" + no_niv + "_" + no_fi + "' maxlength='6' style='width:90%' type='text' name='val".concat(rs2.getString("id_nivel")).concat(",").concat(rs.getString("id_causa")).concat("_1' value='0' onblur='indicador4(this," + no_niv + ",0,0);'></td>"));
                            tablas = tablas.concat("<td align='center'><input id='ene_abr_" + no_niv + "_" + no_fi + "' maxlength='6' style='width:90%' type='text' name='val".concat(rs2.getString("id_nivel")).concat(",").concat(rs.getString("id_causa")).concat("_2' value='0' onblur='indicador4(this," + no_niv + ",0,0);'></td>"));
                            tablas = tablas.concat("<td align='center'><input id='may_ago_" + no_niv + "_" + no_fi + "' maxlength='6' style='width:90%' type='text' name='val".concat(rs2.getString("id_nivel")).concat(",").concat(rs.getString("id_causa")).concat("_3' value='0' onblur='indicador4(this," + no_niv + ",0,0);'></td>"));
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th" + no_niv + "_" + no_fi + "' value='0'></td>");
                            tablas = tablas.concat("</tr>");
                        }
                        no_fi++;
                    }
                    tablas = tablas.concat("<input type='hidden' name='no_fil_niv' value='" + no_fi + "' />");
                    no_fi = 1;
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td align='center' style='font-weight: bold'>Total de desertores</td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv" + no_niv + "_1' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv" + no_niv + "_2' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv" + no_niv + "_3' value='0'></td>");
                    tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='ttv" + no_niv + "' value='0' onblur='operacionesEficacia4(" + no_niv + ",0,0);'></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("<tfoot>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td colspan='4' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>");
                    tablas = tablas.concat("<td class='rounded-foot-right'></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</tfoot>");
                    tablas = tablas.concat("</table></div>");
                    c2++;
                    rs.beforeFirst();
                    no_niv++;
                    c++;
                }
                rs2.beforeFirst();
                if (rs2.next()) {
                    tablas = tablas.concat("<h2><a href='#'> Cuadro 4.3".concat("<br/>Porcentaje Promedio de Deserción de la Universidad</br>"));
                    tablas = tablas.concat("</a></h2>");
                    tablas = tablas.concat("<div align='center'><table align='center' id='rounded-corner' width='60%'>");
                    tablas = tablas.concat("<thead>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<th scope='col' class='rounded-company'> Nivel</th>");
                    tablas = tablas.concat("<th class='rounded-q4'> Promedio</th>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</thead>");
                    //cuerpo dinamico de los totales
                    do {
                        tablas = tablas.concat("<tr id='promsD'>");
                        tablas = tablas.concat("<td class='rounded-q1' width='30%'align='center'>".concat(rs2.getString("n.descripcion")).concat("</td>"));
                        tablas = tablas.concat("<td  class='rounded-q1' width='30%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='promD" + ctot + "' value='0'></td>");
                        tablas = tablas.concat("</tr>");
                        ctot++;
                    } while (rs2.next());
                    tablas = tablas.concat("<tfoot>");
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td class='rounded-foot-left' align='center' style='font-weight: bold'>Promedio de aprovechamiento Académico Cuatrimestral</td>");
                    tablas = tablas.concat("<td class='rounded-foot-right'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='prom' value='0'/></td>");
                    tablas = tablas.concat("</tr>");
                    tablas = tablas.concat("</tfoot>");
                    tablas = tablas.concat("</table></div>");
                }
            } catch (SQLException ex) {
                System.err.println("JSPERROR-EficaciaIn4:" + ex.getMessage());
            } finally {
                con.Desconectar();
            }
        %>
        <%=tablas%>
         <h3><a href="#">Archivo</a></h3>
        <div>
            <table id="rounded-corner" style="width: 100%">
                <tr><td><input type="file" id="archivo" name="archivo" /></td></tr>
                <tr>
                    <td><input type="hidden" id="nomuni" name="nomuni" value="<%=universidad%>">
                        <input type="hidden" id="IdUni" name="IdUni" value="<%= idUniversidad %>">
                        <input type="hidden" id="IdPer" name="IdPer" value="<%=idPeriodo%>"></td>
                </tr>
                <tr><td></td></tr>
                
                 <tfoot>
                    <tr>
                        <td colspan="4" class="rounded-foot-left">Notas: Por el momento solo se pueden enviar archivos formato PDF y con tamaño m&aacute;ximo de 2MB.
                        <br>Los archivos ya enviados <strong>No apareceran aqui</strong></td>
                        
                    </tr>
                </tfoot>
            </table>
        </div>
        <input type='hidden' name='no_niv' value='<%=no_niv%>' />
        <input type="hidden" name="fin" />
        <html:hidden name="EficaciaIn4Form" property="valores" styleId="valores" />
    </div>
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consulta = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=4";
        ResultSet rs = con.Consultar(consulta);      
        String comentario="Sin comentarios";
        if(rs.next()){//SI HAY UN COMENTARIO REGISTRADO SE OBTIENE PARA MOSTRARSE
            comentario = rs.getString("comentario");
        }
%>
                <input type="text" id="comentario" name="comentario" maxlength="700" style=" width: 90%; " value="<%= comentario %>"/>
            </th>
        </tr>
    </table>
    <%
         consulta = "select activo from system_mecasut";
         rs = con.Consultar(consulta);
        rs.next();
        if (rs.getInt("activo") == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
    %>
    <button type="button" class="btnGuardarIndicador"  onclick="operacionesEficacia4(0,1);" >Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
</html:form>
<%
} catch (NullPointerException exNull) {
%>
La sesi&oacute;n ha caducado, cierre e inicie sesi&oacute;n nuevamente y vuelva a intentarlo.
<%            }
%>
