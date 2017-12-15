<%--
    Document   : EficaciaIn3
    Created on : 10/05/2012, 12:34:12 PM
    Author     : Daniel Ramírez Torres
    Actualización 2016: Salvador Zamora Arias
--%>
<%@page import="com.mecasut.shared.Universidad"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.*"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
    try {
%>
<html:form action="/EficaciaIn3">
    <div id="efi3" align="center">
<%
  Universidad uni = new Universidad();
    HttpSession sesion = request.getSession(false);
    String IdUni=sesion.getAttribute("idUniversidad").toString();
    String IdPer=sesion.getAttribute("idPeriodo").toString();
    String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
    String tablas = "";
    ConexionMySQL con = new ConexionMySQL();
    int c = 1;
    int pro;
    try {

        String sql2 = "select distinct(pe.id_nivel), descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(") order by pe.id_nivel"));
        ResultSet rs2 = con.Consultar(sql2);
        //DECLARACION DE RESULTADO PARA LAS CARRERAS POR nivel
        while (rs2.next()) {
            int sumcol[]= new int[6];
            pro=1;
            //Se realiza una consulta para visualizar los datos en caso de existir
            String sql1 = "select mat_sep,rep_sep ,mat_ene,rep_ene, mat_may,rep_may from eficaciain3 where id_universidad=".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo=".concat(sesion.getAttribute("idPeriodo").toString()).concat(" and id_nivel='".concat(rs2.getString(1)).concat("'")))+" AND id_pe!=0";
            ResultSet rs1 = con.Consultar(sql1);
            String sqlpe = "SELECT * FROM pe_universidad pu INNER JOIN programa_educativo pe ON pe.id_pe=pu.id_pe  WHERE pu.id_universidad=".concat(sesion.getAttribute("idUniversidad").toString())
                    .concat(" and pu.id_periodo=".concat(sesion.getAttribute("idPeriodo").toString())
                    .concat(" and pe.id_nivel='".concat(rs2.getString(1)).concat("'")));
            ResultSet rspe = con.Consultar(sqlpe);

            //TITULOS
            tablas = tablas.concat("<h2><a href='#'>Cuadro 3.1.").concat(rs2.getString("id_nivel")).concat("<br/>");
            tablas = tablas.concat(rs2.getString("descripcion")).concat(" Porcentaje Promedio Cuatrimestral de Reprobaci&oacute;n").concat("</a></h2>");

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
            tablas = tablas.concat("<input type='hidden' name='niv," + c + "' value=" + rs2.getString("id_nivel") + ">");
            tablas = tablas.concat("<th width='25%' class='rounded-q1'>Programa educativo</th>");
            tablas = tablas.concat("<th width='12%' class='rounded-q1'>Matr&iacute;cula inicial atendida cuatrimestre Sep-Dic</th>");
            tablas = tablas.concat("<th width='12%' class='rounded-q1'>Alumnos reprobados definitivos del cuatrimestre Septiembre-Diciembre</th>");
            tablas = tablas.concat("<th width='12%' class='rounded-q1'>Matr&iacute;cula inicial atendida cuatrimestre Enero-Abril</th>");
            tablas = tablas.concat("<th width='12%' class='rounded-q1'>Alumnos reprobados definitivos del cuatrimestre enero-abril</th>");
            tablas = tablas.concat("<th width='12%' class='rounded-q1'>Matr&iacute;cula inicial atendida cuatrimestre Mayo-Agosto</th>");
            tablas = tablas.concat("<th width='12%' class='rounded-q1'>Alumnos reprobados definitivos del cuatrimestre Mayo-Agosto</th>");
//            tablas = tablas.concat("<th width='10%' class='rounded-q1'>% Promedio de Reprobaci&oacute;n</th>");
            tablas = tablas.concat("</tr>");
            tablas = tablas.concat("</thead>");
            while(rspe.next()){
                if (rs1.next()) {
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td align='center'>".concat(rspe.getString("nombre_programa")).concat(" ("+rspe.getString("version")+") "));
                    tablas = tablas.concat("<input type='hidden' name='id_prog,"+c+"-"+pro+"' value='"+rspe.getString("id_pe") +"' />");  
                    tablas = tablas.concat("</td>");
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='mat_sep," + c + "-"+pro+"' value='".concat(rs1.getString(1)).concat("' onblur='indicador3(this);'></td>"));
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='rep_sep," + c + "-"+pro+"' value='".concat(rs1.getString(2)).concat("' onblur='indicador3(this);'></td>"));

                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='mat_ene," + c + "-"+pro+"' value='".concat(rs1.getString(3)).concat("' onblur='indicador3(this);'></td>"));
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='rep_ene," + c + "-"+pro+"' value='".concat(rs1.getString(4)).concat("' onblur='indicador3(this);'></td>"));

                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='mat_may," + c + "-"+pro+"' value='".concat(rs1.getString(5)).concat("' onblur='indicador3(this);'></td>"));
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='rep_may," + c + "-"+pro+"' value='".concat(rs1.getString(6)).concat("' onblur='indicador3(this);'></td>"));
//                    tablas = tablas.concat("<td rowspan='3' align='center'>RET</td>");
                    tablas = tablas.concat("</tr>");
                    
                    for(int z=0;z<6;z++){
                        sumcol[z]+=rs1.getInt(z+1);
                    }
                    
                } else {
                    tablas = tablas.concat("<tr>");
                    tablas = tablas.concat("<td align='center'>".concat(rspe.getString("nombre_programa")).concat(" ("+rspe.getString("version")+") "));
                    tablas = tablas.concat("<input type='hidden' name='id_prog,"+c+"-"+pro+"' value='"+rspe.getString("id_pe") +"' />");  
                    tablas = tablas.concat("</td>");
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='mat_sep," + c + "-"+pro+"' value='0' onblur='indicador3(this);'></td>");
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='rep_sep," + c + "-"+pro+"' value='0' onblur='indicador3(this);'></td>");

                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='mat_ene," + c + "-"+pro+"' value='0' onblur='indicador3(this);'></td>");
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='rep_ene," + c + "-"+pro+"' value='0' onblur='indicador3(this);'></td>");

                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='mat_may," + c + "-"+pro+"' value='0' onblur='indicador3(this);'></td>");
                    tablas = tablas.concat("<td align='center'><input style='width:90%' maxlength='6' type='text' name='rep_may," + c + "-"+pro+"' value='0' onblur='indicador3(this);'></td>");

//                    tablas = tablas.concat("<td rowspan='3' align='center'>RET</td>");
                    tablas = tablas.concat("</tr>");
                }
                pro++;
            }
            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td align='center'><strong>TOTAL</strong></td>");
            for(int z=0;z<6;z++){
                tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name=\"totalcol,"+c+"-"+z+"\" value='"+sumcol[z]+"'></td>");
            }
            tablas = tablas.concat("</tr>");
            
            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td rowspan='5' align='center'></td>");
            tablas = tablas.concat("<td colspan='2' align='center'>RE1</td>");
            tablas = tablas.concat("<td colspan='2' align='center'>RE2</td>");
            tablas = tablas.concat("<td colspan='2' align='center'>RE3</td>");
//            tablas = tablas.concat("<td rowspan='2' align='center'>RET</td>");
            tablas = tablas.concat("</tr>");

            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td colspan='2' align='center'>3/2</td>");
            tablas = tablas.concat("<td colspan='2' align='center'>5/4</td>");
            tablas = tablas.concat("<td colspan='2' align='center'>7/6</td>");
            tablas = tablas.concat("</tr>");

            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tot_1," + c + "' value='0' ></td>");
            tablas = tablas.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tot_2," + c + "' value='0' ></td>");
            tablas = tablas.concat("<td colspan='2' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tot_3," + c + "' value='0' ></td>");
            tablas = tablas.concat("</tr>");
            
            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td colspan='6' align='center'>RET</td>");
            tablas = tablas.concat("</tr>");
            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td colspan='6' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tot_4," + c + "' value='0' ></td>");
            tablas = tablas.concat("</tr>");

            //PIE DE LA TABLE
            tablas = tablas.concat("<tfoot>");
            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td colspan='6' class='rounded-foot-left'>"
            + "<input type='hidden' name='numProg-"+c+"' id='numProg-"+c+"' value='"+(pro-1)+"'/>"
            + "Notas: No hay notas adicionales.</td>");
            tablas = tablas.concat("<td class='rounded-foot-right'></td>");
            tablas = tablas.concat("</tr>");
            tablas = tablas.concat("</tfoot>");
            tablas = tablas.concat("</table></div>");
         c++;   
        }

        String sql3 = "select distinct(pe.id_nivel), abreviatura, descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo =".concat(sesion.getAttribute("idPeriodo").toString()).concat(") order by pe.id_nivel"));
        ResultSet rs3 = con.Consultar(sql3);
        if (rs3.next()) {
            String sqlnivmax = "select (max(id_nivel)+1)as maxid from nivel_pe";
            ResultSet rsnivmax = con.Consultar(sqlnivmax);
            rsnivmax.next();
            tablas = tablas.concat("<h2><a href='#'>Cuadro 3.2<br/> Porcentaje Promedio de Reprobaci&oacute;n de la Universidad</br>");
            tablas = tablas.concat(" </a></h2>");
            tablas = tablas.concat("<div align='center'><table align='center' id='rounded-corner' width='60%'>");
            tablas = tablas.concat("<thead>");
            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<th scope='col' class='rounded-company'> Nivel</th>");
            tablas = tablas.concat("<th class='rounded-q4'> Promedio</th>");
            tablas = tablas.concat("</tr>");
            tablas = tablas.concat("</thead>");
            //cuerpo dinamico de los totales
            int cu = 1;
            do {
                tablas = tablas.concat("<tr>");
                tablas = tablas.concat("<td class='rounded-q1' width='30%'align='center'>".concat(rs3.getString("descripcion")).concat("</td>"));
                tablas = tablas.concat("<td  class='rounded-q1' width='30%' align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tot_4," + cu + "' value='0'></td>");
                tablas = tablas.concat("</tr>");
                cu++;
            } while (rs3.next());
            tablas = tablas.concat("<tfoot>");
            tablas = tablas.concat("<tr>");
            tablas = tablas.concat("<td class='rounded-foot-left' width='30%' style='font-weight: bold' align='center'>Promedio total de la UT</td>");
            tablas = tablas.concat("<td class='rounded-foot-right'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tot_t' value='0'/></td>");
            tablas = tablas.concat("</tr>");
            tablas = tablas.concat("</tfoot>");
            tablas = tablas.concat("</table></div>");
        }
             
    } catch (SQLException ex) {
        System.err.println("JSPERROR-EficaciaIn3:" + ex.getMessage());
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
                        <input type="hidden" id="IdUni" name="IdUni" value="<%=IdUni%>">
                        <input type="hidden" id="IdPer" name="IdPer" value="<%=IdPer%>"></td>
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
        <input type="hidden" name="numC" value="<%=c%>" />
        <input type="hidden" name="fin" />
        <html:hidden name="EficaciaIn3Form" property="valores" styleId="valores" />
    </div>
    
   
    
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR

        String consulta = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=3";
        ResultSet rs = con.Consultar(consulta);      
        String comentario="Sin comentarios";
        if(rs.next()){//SI HAY UN COMENTARIO REGISTRADO SE OBTIENE PARA MOSTRARSE
            comentario = rs.getString("comentario");
        }
%>
                <input type="text" id="comentario" name="comentario" style=" width: 90%; " value="<%= comentario %>"/>
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
    <button type="button" class="btnGuardarIndicador"  onclick="enviarDatosIndicadores(guardarEficaciaIn3(),'EficaciaIn3Datos','EficaciaIn3Form');" >Guardar</button>
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
