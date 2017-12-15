<%--
    Document   : EficaciaIn10
    Created on : 14/05/2012, 01:44:12 PM
    Author     : Cuauhtemoc Medina Mu�oz
    Actualizaci�n 2016: Salvador Zamora Arias
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!--OBTENER LA SESION-->
<%  try {
%>
<!--TERMINA OBTENER LA SESION-->
<html:form action="/EficaciaIn10">
    <div id="efi10" align="center">
        <%
            HttpSession sesion = request.getSession(false);
            int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
            int muestreo = 0;
            String query = "";
            ResultSet resultado = null;
            String tablas = "";
            String ap = "";
            ConexionMySQL con = new ConexionMySQL();
            int c = 1;
            int no = 1, pregu = 0, anio = 0, volver = 0, nopreguntas=0;
            //Validar MAt
            String sql4 = "";
            ResultSet rs4 = null;
            try {
                //N�MERO DE NIVELES
                String sql = "Select distinct(pe.id_nivel),descripcion,n.id_nivel from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(String.valueOf(idPeriodo)).concat(") order by pe.id_nivel"));
                ResultSet rs = con.Consultar(sql);

                //RESPUESTA A LAS PREGUNTAS (VALORES DE BD)
                String respuestas = "";
                ResultSet resp;
                String sql3 = "select year(fecha_inicio) from periodos where id_periodo='".concat(sesion.getAttribute("idPeriodo").toString()).concat("'");
                ResultSet rs3 = con.Consultar(sql3);
                if (rs3.next()) {
                    anio = rs3.getInt(1);
                }
                String anios = "";
                //A�OS ESCOLARES
                 while (rs.next() && (rs.getString("id_nivel").equals("1") ||rs.getString(1).equals("2") ||rs.getString(1).equals("3") )) {//while de niveles
                    String aplica = "1";
                    sql = "SELECT aplica FROM indicadores_aplica "
                            .concat(" WHERE id_universidad=").concat(String.valueOf(idUniversidad))
                            .concat(" and indicador='10' and id_nivel=" + rs.getString("id_nivel") + " AND id_periodo=").concat(String.valueOf(idPeriodo));
                    ResultSet rs2 = con.Consultar(sql);
                    if (rs2.next()) {
                        aplica = rs2.getString("aplica");
                    }
                    //se realiza una consulta para validar si existen datos en el indicador 5
                    sql4 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(String.valueOf(idUniversidad)).concat("' and id_periodo ='").concat(String.valueOf(idPeriodo)).concat("'and id_nivel = '").concat(String.valueOf(rs.getInt(1))).concat("'");
                    rs4 = con.Consultar(sql4);
                    rs4.next();
                    if (rs4.getString(1) != null) {
                        volver = 1;
                        String cons = "select id_pregunta, pregunta from encuesta_preguntas where id_encuesta=(select id_encuesta from encuestas where id_nivel=".concat(rs.getString(1)).concat(" and encuesta_preguntas.id_encuesta=encuestas.id_encuesta and indicador=10) and activo=1");
                        ResultSet preg = con.Consultar(cons);
                          if (rs.getString(1).equals("1") || rs.getString(1).equals("2")) {
                            tablas = tablas.concat("<h2 onclick='checkEficaciaIn10(" + c + ")' align='left'>".concat("Cuadro 10.1.".concat(rs.getString("id_nivel"))).concat("<br/>Tasa de Empleadores Satisfechos del Nivel ").concat(rs.getString("descripcion")).concat(" ").concat(String.valueOf(anio)).concat("</h2>"));
                            anios = "1";
                        } else {
                              tablas = tablas.concat("<h2 onclick='checkEficaciaIn10(" + c + ")' align='left'>".concat("Cuadro 10.1.".concat(rs.getString("id_nivel"))).concat("<br/>Tasa de Empleadores Satisfechos del Nivel ").concat(rs.getString("descripcion")).concat(" ").concat(String.valueOf(anio + 1)).concat("</h2>"));
                            anios = "2";
                        }
                        tablas = tablas.concat("<div><table id='radiosOptions' style='width: 100%'>");
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td>");
                        tablas = tablas.concat("<div style='display:none;' id='radio" + c + "' align='center'>"
                                + "<input type='radio' id='radio" + c + "1' name='radio" + c + "' value='1' onclick='checkEficaciaIn10(" + c + ");' " + (aplica.equals("1") ? "checked='checked'" : "''") + " /> <label for='radio" + c + "1' >Si se aplic&oacute;</label> "
                                + "<input type='radio' id='radio" + c + "2' name='radio" + c + "' value='0' onclick='checkEficaciaIn10(" + c + ");'" + (aplica.equals("0") ? "checked='checked'" : "''") + "/><label for='radio" + c + "2' >No se aplic&oacute;</label>"
                                + "<input type='hidden' id='idNivel" + c + "' value='" + rs.getString("id_nivel") + "' />"
                                + "</div>");
                        tablas = tablas.concat("</td>");
                        tablas = tablas.concat("</tr>");
                        tablas = tablas.concat("</table>");
                        //TABLA
                        tablas = tablas.concat("<div id='tabla" + c + "'>");
                        tablas = tablas.concat("<table id='rounded-corner' style='width: 100%'>");
                        //ENCABEZADO
                        tablas = tablas.concat("<thead>");
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<th rowspan='3' class='rounded-company'>#</th>");
                        tablas = tablas.concat("<th rowspan='3' class='rounded-q1'>DESCRIPCI�N</th>");
                        tablas = tablas.concat("<th colspan='10' class='rounded-q1'>FRECUENCIAS</th>");
                        tablas = tablas.concat("<th rowspan='2' class='rounded-q1'>TOTAL K, BASE 5</th>");
                        tablas = tablas.concat("<th rowspan='3' class='rounded-q4'>TOTAL K, BASE 10</th>");
                        tablas = tablas.concat("</tr>");
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td>5</td>");
                        tablas = tablas.concat("<td>4</td>");
                        tablas = tablas.concat("<td>3</td>");
                        tablas = tablas.concat("<td>2</td>");
                        tablas = tablas.concat("<td>1</td>");
                        tablas = tablas.concat("<td> N/A </td>");
                        tablas = tablas.concat("<td width='6%'> N/E </td>");
                        tablas = tablas.concat("<td>TOTAL H</td>");
                        tablas = tablas.concat("<td>TOTAL I</td>");
                        tablas = tablas.concat("<td>TOTAL J</td>");
                        tablas = tablas.concat("</tr>");
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td>A</td>");
                        tablas = tablas.concat("<td>B</td>");
                        tablas = tablas.concat("<td>C</td>");
                        tablas = tablas.concat("<td>D</td>");
                        tablas = tablas.concat("<td>E</td>");
                        tablas = tablas.concat("<td>F</td>");
                        tablas = tablas.concat("<td>G</td>");
                        tablas = tablas.concat("<td>&Sigma; A a G</td>");
                        tablas = tablas.concat("<td>&Sigma; A a E</td>");
                        tablas = tablas.concat("<td>A*5 + B*4 + C*3 + D*2 + E*1</td>");
                        tablas = tablas.concat("<td>J/I</td>");
                        tablas = tablas.concat("</tr>");
                        tablas = tablas.concat("</thead>");
                        //CUERPO
                        tablas = tablas.concat("<input type='hidden' name='id_niv" + c + "' value='".concat(rs.getString(1)).concat("' />"));
                        while (preg.next()) {                           //PREGUNTAS                           
                            respuestas = "select r_a, r_b, r_c, r_d, r_e, r_f, r_g from eficaciain7_10 where id_universidad = ".concat(String.valueOf(idUniversidad)).concat(" and id_periodo =").concat(String.valueOf(idPeriodo)).concat(" and id_pregunta=").concat(preg.getString(1));
                            resp = con.Consultar(respuestas);
                            if (resp.next()) {
                                //VALORES LLENOS DE LA BASE DE DATOS
                                tablas = tablas.concat("<tr>");
                                tablas = tablas.concat("<td width='5%' align='center'>".concat(String.valueOf(no)).concat("</td>")); //N�MERO CONSECUTIVO
                                tablas = tablas.concat("<td width='21%' align='center'>".concat(preg.getString("pregunta")).concat("<input type='hidden' name='id_preg" + c + "_" + no + "' value='".concat(preg.getString(1)).concat("' /></td>"))); //PREGUNTA
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='".concat(resp.getString(1)).concat("' name='val_A_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>")); //A
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='".concat(resp.getString(2)).concat("' name='val_B_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>")); //B
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='".concat(resp.getString(3)).concat("' name='val_C_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>")); //C
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='".concat(resp.getString(4)).concat("' name='val_D_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>")); //D
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='".concat(resp.getString(5)).concat("' name='val_E_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>")); //E
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='".concat(resp.getString(6)).concat("' name='val_F_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>")); //F
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='".concat(resp.getString(7)).concat("' name='val_G_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>")); //G
                                tablas = tablas.concat("<td width='6%' align='center'><input class='inputok' readonly=\"readonly\" id='total_h".concat(String.valueOf(c)).concat(String.valueOf(no)).concat("' style='width:90%' type='text' name='th1_" + c + "_" + no + "' value='0'/> </td>"));
                                tablas = tablas.concat("<td width='6%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th2_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("<td width='6%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th3_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("<td width='6%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th4_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("<td width='11%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th5_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("</tr>");
                                pregu++;
                                nopreguntas++;
                            } else {
                                //VALORES VAC�OS
                                tablas = tablas.concat("<tr>");
                                tablas = tablas.concat("<td width='5%' align='center'>".concat(String.valueOf(no)).concat("</td>")); //N�MERO CONSECUTIVO
                                tablas = tablas.concat("<td width='21%' align='center'>".concat(preg.getString("pregunta")).concat("<input type='hidden' name='id_preg" + c + "_" + no + "' value='".concat(preg.getString(1)).concat("' /></td>"))); //PREGUNTA
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='0' name='val_A_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>"); //A
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='0' name='val_B_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>"); //B
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='0' name='val_C_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>"); //C
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='0' name='val_D_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>"); //D
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='0' name='val_E_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>"); //E
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='0' name='val_F_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>"); //F
                                tablas = tablas.concat("<td width='6%' align='center'><input maxlength='6' style='width:90%' value='0' name='val_G_" + c + "_" + no + "' onblur='indicador10(this," + c + "," + no + ",0,1);' type='text'> </td>"); //G
                                tablas = tablas.concat("<td width='6%' align='center'><input class='inputok' readonly=\"readonly\" id='total_h".concat(String.valueOf(c)).concat(String.valueOf(no)).concat("' style='width:90%' type='text' name='th1_" + c + "_" + no + "' value='0'/> </td>"));
                                tablas = tablas.concat("<td width='6%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th2_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("<td width='6%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th3_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("<td width='6%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th4_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("<td width='11%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th5_" + c + "_" + no + "' value='0'/> </td>");
                                tablas = tablas.concat("</tr>");
                                pregu++;
                                nopreguntas++;
                            }
                            no++;
                        }
                        tablas = tablas.concat("<input type='hidden' name='no_Preguntas_Niv" + c + "' value='" + pregu + "' />");

                        pregu = 0;
                        tablas = tablas.concat("<input type='hidden' name='no_fil_niv" + c + "' value='" + no + "' onblur='indicador10(this," + c + ",0,0,0);' />");
                        no = 1;
                        //PIE DE LA TABLA
                        //RESULTADOS
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>TOTAL</td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-1' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-2' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-3' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-4' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-5' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-6' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-7' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-8' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-9' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-10' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-11' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-12' type='text'/> </td>");
                        tablas = tablas.concat("</tr>");
                        //SIN NOTAS ADICIONALES
                        
                              
                    //DISTRIBUCION PORCENTUAL NOMBRE
                    tablas = tablas.concat("<tr>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'>DISTRIBUCI�N PORCENTUAL</td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>MS</td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>S</td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>RS</td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>PS</td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NS</td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NA</td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>NE</td>");

                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='1' class='rounded-foot-left'>TES</td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text'/> </td>");
                    tablas = tablas.concat("</tr>");

                    //DISTRIBUCION PORCENTUAL VALOR
                    tablas = tablas.concat("<tr>");
                         tablas = tablas.concat("<td align='center' style='font-weight: bold' colspan='2' class='rounded-foot-left'></td>");

                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-1-d-ms' type='text'/></td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-2-d-s' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-3-d-rs' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-4-d-ps' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-5-d-ns' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-6-d-na' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-7-d-ne' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-8-d' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-9-d-tes' type='text'/><input type='hidden' name='totalpreguntas' value='"+nopreguntas+"'> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-10-d' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-11-d' type='text'/> </td>");
                        tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' name='tv_" + c + "-12-d' type='text'/> </td>");
                    tablas = tablas.concat("</tr>");
                        
                        
                        if (!rs.getString("id_nivel").equals("3")) {//niveles que no son el 3
                            query = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(String.valueOf(idUniversidad))
                                    .concat("' and id_periodo ='").concat(String.valueOf(idPeriodo))
                                    .concat("'and id_nivel = '").concat(String.valueOf(rs.getInt("id_nivel"))).concat("' and anio=1");
                        } else { //nivel 3 (Licenciatura)
                            query = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(String.valueOf(idUniversidad))
                                    .concat("' and id_periodo ='").concat(String.valueOf(idPeriodo))
                                    .concat("'and id_nivel = '").concat(String.valueOf(rs.getInt("id_nivel"))).concat("' and anio=2");
                        }
                        resultado = con.Consultar(query);
                        if (resultado.next()) {
                            if (resultado.getInt("suma") > 30) {
                                muestreo = 30;
                            } else {
                                muestreo = resultado.getInt("suma");
                            }
                        }
                        tablas = tablas.concat("<div style='display:none;' name='muestreo_nivel_")
                                .concat(String.valueOf(c)).concat("'>")
                                .concat(String.valueOf(muestreo)).concat("</div>");
                        tablas = tablas.concat("<tfoot>");
                        tablas = tablas.concat("<tr>");
                        tablas = tablas.concat("<td colspan='13' class='rounded-foot-left'>Notas: Se encuestar� al menos a ")
                                .concat(String.valueOf(muestreo))
                                .concat(" personas, por disposici&oacute;n de la CGUT. Atenci�n, por lo anterior la columna 'H' debe ser igual o mayor a la cantidad mencionada.</td>");
                        tablas = tablas.concat("<td class='rounded-foot-right'></td>");
                        tablas = tablas.concat("</tr>");
                        tablas = tablas.concat("</tfoot>");
                        tablas = tablas.concat("</table></div></div>");
                        preg.beforeFirst();
                        c++;
                    }//while
                }

            } catch (SQLException ex) {
                System.err.print("error eficacia 10: " + ex);

            } finally {
                con.Desconectar();
            }
        %>
        <%= tablas%>
        <input type="hidden" value="<%=c%>" name="fin" />   
        <input type="hidden" value="<%=pregu%>" name="pregu" />    
    </div>
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=10";
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
        if (rs.getInt(1) == 1) {
            if ((sesion.getAttribute("iniciado").toString().equals("1")) && (sesion.getAttribute("terminado").toString().equals("0"))) {
                if (!sesion.getAttribute("tipoUsuario").equals("CGenUT")) {
    %>
    
    <button type="button" class="btnGuardarIndicador" onclick="validarGuardado10(<%=c%>,0,1,0);" >Guardar</button>
    <%}
        }
    } else {
    %>
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
