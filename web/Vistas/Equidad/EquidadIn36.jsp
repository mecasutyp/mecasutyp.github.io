<%-- 
    Document   : EquidadIn36
    Created on : 3/06/2012, 03:06:33 AM
    Author     : Juan
--%>


<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html:form action="/EquidadIn36">
    <div id="equi36">
        <%
            HttpSession sesion = request.getSession(false);
            String idUniversidad = sesion.getAttribute("idUniversidad").toString();
            String idPeriodo = sesion.getAttribute("idPeriodo").toString();

            String tablas = "";
            ConexionMySQL con = new ConexionMySQL();
            try {

                int c = 1;
                tablas = tablas.concat("<h3><a href='#'>Cuadro 36.1<br/>");
                tablas = tablas.concat("Alumnos de la Universidad con Beca</a></h3>");
                tablas = tablas.concat("<div align='center'><table id='rounded-corner' style='width: 80%'>");
                tablas = tablas.concat("<thead>");
                tablas = tablas.concat("<tr>");
                tablas = tablas.concat("<th rowspan='2' class='rounded-company'>Tipos de becas o apoyos econ&oacute;micos al estudiante</th>");
                tablas = tablas.concat("<th  class='rounded-q1'>N&uacute;mero de becas otorgadas en \"el Ciclo Escolar\"</th>");
                tablas = tablas.concat("<th class='rounded-q1'>Matr&iacute;cula atendida en \"el Ciclo Escolar\"</th>");
                tablas = tablas.concat("<th class='rounded-q4'>Porcentaje de alumnos beneficiados</th>");
                tablas = tablas.concat("</tr>");
                tablas = tablas.concat("<tr>");
                tablas = tablas.concat("<th class='rounded-q1' >A</th>");
                tablas = tablas.concat("<th class='rounded-q1'>B</th> ");
                tablas = tablas.concat("<th class='rounded-q1'>A/B=x100</th>");
                tablas = tablas.concat("</tr>");
                tablas = tablas.concat("</thead>");
                String sql = " select id_apoyo, descripcion from apoyos_estudiante where id_apoyo!=12 and activo =1  order by descripcion";
                ResultSet rs = con.Consultar(sql);
                String sqlotros = "select id_apoyo, descripcion from apoyos_estudiante where id_apoyo=12 and activo =1  ";
                ResultSet rsotros = con.Consultar(sqlotros);
                
                int rows = 0;
                while (rs.next()) {
                    rows++;
                }
                rs.beforeFirst();
                int cont = 0;
                String id_apoy="";
                String descripcion="";

                while (rs.next()) {
                    if(cont!=(rows-1)){
                        id_apoy=rs.getString("id_apoyo");
                        descripcion=rs.getString("descripcion");
                    }else{
                        rsotros.next();        
                         id_apoy=rsotros.getString("id_apoyo");
                        descripcion=rsotros.getString("descripcion");
                    }

                    String sql2 = "select apoyos_otorgados from equidadin36 where id_universidad=".concat(idUniversidad).concat(" and id_periodo=").concat(idPeriodo).concat(" and id_apoyo=").concat(id_apoy);
                    ResultSet rs2 = con.Consultar(sql2);
                    if (cont == 0) {
                        if (rs2.next()) {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>".concat(descripcion).concat("</td>"));
                            tablas = tablas.concat("<td align='center'><input type='text' name='val".concat(id_apoy).concat("_1' value='".concat(rs2.getString("apoyos_otorgados")).concat("' onblur='indicador36(this);'></td>")));
                            String sql4 = "select matricula_total from datos_universidad where id_universidad=".concat(idUniversidad).concat(" and id_periodo=").concat(idPeriodo);
                            ResultSet rs4 = con.Consultar(sql4);
                            if (rs4.next()) {
                                tablas = tablas.concat("<td align='center' rowspan='".concat(String.valueOf(rows)).concat("'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='valmat_2' value='".concat(rs4.getString(1)).concat("'></td>")));
                            } else {
                                tablas = tablas.concat("<td align='center' rowspan='".concat(String.valueOf(rows)).concat("'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='valmat_2' value='0'></td>"));
                            }
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th".concat(id_apoy).concat("-").concat("' value='0'></td>"));
                            tablas = tablas.concat("</tr>");
                        } else {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>".concat(descripcion).concat("</td>"));
                            tablas = tablas.concat("<td align='center'><input type='text' name='val".concat(id_apoy).concat("_1' value='0' onblur='indicador36(this);'></td>"));
                            String sql4 = "select matricula_total from datos_universidad where id_universidad=".concat(idUniversidad).concat(" and id_periodo=").concat(idPeriodo);
                            ResultSet rs4 = con.Consultar(sql4);
                            if (rs4.next()) {
                                tablas = tablas.concat("<td align='center' rowspan='".concat(String.valueOf(rows)).concat("'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='valmat_2' value='".concat(rs4.getString("matricula_total")).concat("'></td>")));
                            } else {
                                tablas = tablas.concat("<td align='center' rowspan='".concat(String.valueOf(rows)).concat("'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='valmat_2' value='0'></td>"));
                            }
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th".concat(id_apoy).concat("-").concat("' value='0'></td>"));
                            tablas = tablas.concat("</tr>");
                        }
                    } else {
                        if (rs2.next()) {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>".concat(descripcion).concat("</td>"));
                            tablas = tablas.concat("<td align='center'><input type='text' name='val".concat(id_apoy).concat("_1' value='".concat(rs2.getString("apoyos_otorgados")).concat("' onblur='indicador36(this);'></td>")));
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th".concat(id_apoy).concat("-").concat("' value='0'></td>"));
                            tablas = tablas.concat("</tr>");
                        } else {
                            tablas = tablas.concat("<tr>");
                            tablas = tablas.concat("<td align='center'>".concat(descripcion).concat("</td>"));
                            tablas = tablas.concat("<td align='center'><input type='text' name='val".concat(id_apoy).concat("_1' value='0' onblur='indicador36(this);'></td>"));
                            tablas = tablas.concat("<td align='center'><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='th".concat(id_apoy).concat("-").concat("' value='0'></td>"));
                            tablas = tablas.concat("</tr>");
                        }
                    }
                    cont++;
                }

                tablas = tablas.concat("<tr>");
                tablas = tablas.concat("<td>Totales</td>");
                tablas = tablas.concat("<td><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv".concat("-1' value='0'></th>"));
                tablas = tablas.concat("<td><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv".concat("-2' value='0'></th>"));
                tablas = tablas.concat("<td><input readonly=\"readonly\" id=\"etiqueta\" style='width:90%' type='text' name='tv".concat("-3' value='0'></td>"));
                tablas = tablas.concat("</tr>");

                tablas = tablas.concat("<tfoot>");
                tablas = tablas.concat("<tr>");
                tablas = tablas.concat("<td colspan='3' class='rounded-foot-left'>Notas: No hay notas adicionales.</td>");
                tablas = tablas.concat("<td class='rounded-foot-right'></td>");
                tablas = tablas.concat("</tr>");
                tablas = tablas.concat("</tfoot>");
                tablas = tablas.concat("</table></div>");
                c++;
            } catch (SQLException ex) {
                System.err.println("JSPERROR-EquidadIn36Form:" + ex.getMessage());
            } finally {
                con.Desconectar();
            }
        %>
        <%=tablas%>
        <input type="hidden" name="fin" />
        <html:hidden name="EquidadIn36Form" property="valores" styleId="valores" />
    </div>
      <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=36";
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
    <button type="button" class="btnGuardarIndicador" onclick="enviarDatosIndicadores(validarGuardado36(),'EquidadIn36Datos','EquidadIn36Form');">Guardar</button>
    <%}
        }
    } else {%>
    <button type="button" class="btnGuardarIndicador" disabled="disabled">La evaluación ha sido desactivada.</button>
    <%        }%>
</html:form>