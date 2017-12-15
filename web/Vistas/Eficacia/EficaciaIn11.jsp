<%--
    Document   : EficaciaIn11
    Created on : 9/05/2012, 01:49:35 PM
    Author     : Daniel Ramìrez Torres
    Actualización 2016: Salvador Zamora Arias
--%>
<%@page import="com.mecasut.shared.Universidad"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%try {%>
<html:form action="/EficaciaIn11">
    <div id="efi11" align="center">
        <%
            //se recupera la sesion
            HttpSession sesion = request.getSession(false);
            Universidad uni = new Universidad();
            int idUniversidad = Integer.parseInt(sesion.getAttribute("idUniversidad").toString());
            int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
              String universidad = uni.getNombreUniversidad(session.getAttribute("idUniversidad").toString());
            ConexionMySQL con = new ConexionMySQL();

            String preE = "0", preT = "0", orig_f_e= "0", orig_f= "0", orig_e= "0", auto_f = "0", ampl_f = "0",
                    redd_f = "0", ejer_f = "0", auto_e = "0", ampl_e = "0", redd_e = "0", ejer_e = "0",
                    t_auto_f_e = "0", t_ampl_f_e = "0", t_redd_f_e = "0", t_ejer_f_e = "0";
            int anio=0;
                                  
            DecimalFormat formatea = new DecimalFormat("###,###");
            try {
                  String sqlanio = "select anio from periodos where id_periodo ='".concat(String.valueOf(idPeriodo)).concat("'");
                ResultSet rsanio = con.Consultar(sqlanio);
                
                if(rsanio.next()){
                    anio=rsanio.getInt("anio");
                }
                //se crea la consulta para recuperar los datos correspondientes a EficaciaIn11
                String sql = "select * from eficaciain11 where id_universidad ='".concat(String.valueOf(idUniversidad)).concat("' and id_periodo ='").concat(String.valueOf(idPeriodo)).concat("'");
                ResultSet rs = con.Consultar(sql);
                //si encuentra datos se los asigna a las variables creadas con anterioridad para
                while (rs.next()) {
                    preE = rs.getString(3);
                    preT = rs.getString(4);
                    orig_f_e= rs.getString(5);
                    orig_f= rs.getString(6);
                    orig_e= rs.getString(7);
                    auto_f = rs.getString(8);
                    ampl_f = rs.getString(9);
                    redd_f = rs.getString(10);
                    ejer_f = rs.getString(11);
                    auto_e = rs.getString(12);
                    ampl_e = rs.getString(13);                    
                    redd_e = rs.getString(14);
                    ejer_e = rs.getString(15);
                    t_auto_f_e = rs.getString(16);
                    t_ampl_f_e = rs.getString(17);
                    t_redd_f_e = rs.getString(18);
                    t_ejer_f_e = rs.getString(19);
                }
            } catch (SQLException ex) {
                System.err.print("Error en EficaciaIn11: " + ex);
            } finally {
                con.Desconectar();
            }
        %>
   <h3><a href='#'>Cuadro 11.1<br/>Presupuesto Original</a></h3>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th  class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th  class="rounded-q1">3</th>
                        <th rowspan="2" class="rounded-q1">1/3*100</th>
                        <th rowspan="2" class="rounded-q4">2/3*100</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Presupuesto Original Asignado  federal</th>
                        <th align="center" class="rounded-q1">Presupuesto Original Asignado estatal</th>
                        <th align="center" class="rounded-q1">Presupuesto Original Asignado federal y estatal</th>
                        
                    </tr>
                </thead>
                
                <tr>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=orig_f%>" property="orig_f" styleId="orig_f" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=orig_e%>" property="orig_e" styleId="orig_e" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=orig_f_e%>" property="orig_f_e" styleId="orig_f_e" onblur="EficaciaIn11(this)"/></td>
                    <td><input type="text" style="width:90%" name="porc_po1" value="0" readonly="readonly" id="etiqueta"></td>
                    <td><input type="text" style="width:90%" name="porc_po2" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                <tfoot>
                    <tr>
                        <td colspan="5" class="rounded-foot-left">Notas: En este indicador se registrará el presupuesto original del año fiscal de enero a diciembre del <%=anio+1%>. (suma de capítulos 1000, 2000 y 3000) referentes al gasto corriente.
No incluir el Capítulo 5000, Ingresos Propios u Otros Ingresos Extraordinarios, diferentes al presupuesto original.</td>
                        
                    </tr>
                </tfoot>
            </table>
        </div>      
   
    
<h3><a href='#'>Cuadro 11.2<br/> Presupuesto Total Autorizado Federal con Ampliaciones, Reducciones y Ejercido</a></h3>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th  class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q1">3</th>
                        <th class="rounded-q1">4</th>
                        <th  class="rounded-q4">5</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Presupuesto Original Asignado Federal</th>
                        <th align="center" class="rounded-q1">Ampliaciones</th>
                        <th align="center" class="rounded-q1">Reducciones</th>
                        <th align="center" class="rounded-q1">Presupuesto Total Autorizado Federal</th>
                        <th align="center" class="rounded-q1">Presupuesto Ejercido Federal</th>
                    </tr>
                </thead>
           
                <tr>
                   <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=auto_f%>" property="auto_f" styleId="auto_f" disabled="true" onblur="EficaciaIn11(this)"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=ampl_f%>" property="ampl_f" styleId="ampl_f" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=redd_f%>" property="redd_f" styleId="redd_f" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="" property="pta_f" styleId="pta_f" disabled="true"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=ejer_f%>" property="ejer_f" styleId="ejer_f" onblur="EficaciaIn11(this);"/></td>
                </tr>
                     <tfoot>
                    <tr>
                        <td colspan="5" class="rounded-foot-left">Notas: En este indicador se registrará el presupuesto original del año fiscal de enero a diciembre del <%=anio+1%>. (suma de capítulos 1000, 2000 y 3000) referentes al gasto corriente.
No incluir el Capítulo 5000, Ingresos Propios u Otros Ingresos Extraordinarios, diferentes al presupuesto original.</td>
                        </tr>
                </tfoot>
            </table>
        </div>        


                     
        
        <h3><a href='#'>Cuadro 11.3<br/>Presupuesto Total Autorizado Estatal con Ampliaciones, Reducciones y Ejercido</a></h3>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th  class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q1">3</th>
                        <th  class="rounded-q1">4</th>
                        <th  class="rounded-q4">5</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Presupuesto Original Asignado  Estatal</th>
                        <th align="center" class="rounded-q1">Ampliaciones</th>
                        <th align="center" class="rounded-q1">Reducciones</th>
                        <th align="center" class="rounded-q1">Presupuesto Total Autorizado Estatal</th>
                        <th align="center" class="rounded-q1">Presupuesto Ejercido Estatal</th>
                    </tr>
                </thead>
           
                <tr>
                     <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=auto_e%>" property="auto_e" styleId="auto_e" disabled="true" onblur="EficaciaIn11(this)"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=ampl_e%>" property="ampl_e" styleId="ampl_e" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=redd_e%>" property="redd_e" styleId="redd_e" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="" property="pta_e" styleId="pta_e" disabled="true"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=ejer_e%>" property="ejer_e" styleId="ejer_e" onblur="EficaciaIn11(this);"/></td>
                </tr>
                     <tfoot>
                    <tr>
                        <td colspan="5" class="rounded-foot-left">Notas: En este indicador se registrará el presupuesto original del año fiscal de enero a diciembre del <%=anio+1%>. (suma de capítulos 1000, 2000 y 3000) referentes al gasto corriente.
No incluir el Capítulo 5000, Ingresos Propios u Otros Ingresos Extraordinarios, diferentes al presupuesto original.</td>
                        </tr>
                </tfoot>
            </table>
        </div>       
                    
<h3><a href='#'>Cuadro 11.4<br/>Presupuesto Total Autorizado Federal y Estatal, Ampliaciones, Reducciones y Ejercido</a></h3>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th  class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th class="rounded-q1">3</th>
                        <th class="rounded-q1">4</th>
                        <th  class="rounded-q4">5</th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Presupuesto Original Asignado  Federal y Estatal</th>
                        <th align="center" class="rounded-q1">Ampliaciones totales federales y estatales</th>
                        <th align="center" class="rounded-q1">Reducciones totales federales y estatales</th>
                        <th align="center" class="rounded-q1">Presupuesto Total Autorizado  Federal y Estatal</th>
                        <th align="center" class="rounded-q1">Presupuesto Total Ejercido Federal y Estatal</th>
                    </tr>
                </thead>
           
                <tr>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=t_auto_f_e%>" property="t_auto_f_e" styleId="t_auto_f_e" disabled="true" onblur="EficaciaIn11(this)"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=t_ampl_f_e%>" property="t_ampl_f_e" styleId="t_ampl_f_e" disabled="true" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=t_redd_f_e%>" property="t_redd_f_e" styleId="t_redd_f_e" disabled="true" onblur="EficaciaIn11(this);"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="" property="t_pta_f_e" styleId="t_pta_f_e" disabled="true" /></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=t_ejer_f_e%>" property="t_ejer_f_e" styleId="t_ejer_f_e" disabled="true" onblur="EficaciaIn11(this);"/></td>
                </tr>
                     <tfoot>
                    <tr>
                        <td colspan="4" class="rounded-foot-left">Notas: Este cuadro se llenará automáticamente al completar los cuadros 11.2 y 11.3</td>
                        </tr>
                </tfoot>
            </table>
        </div>     

        <h3><a href='#'>Cuadro 11.5<br/>Presupuesto Ejercido</a></h3>
        <div align="center">
            <table id="rounded-corner">
                <thead>
                    <tr>
                        <th scope="col" class="rounded-company">1</th>
                        <th class="rounded-q1">2</th>
                        <th colspan="2" class="rounded-q4"></th>
                    </tr>
                    <tr>
                        <th align="center" class="rounded-q1">Presupuesto Total Ejercido Federal y Estatal</th>
                        <th align="center" class="rounded-q1">Presupuesto Total Autorizado Federal y Estatal</th>
                        <th>Porcentaje 1/2 * 100</th>
                    </tr>
                </thead>
               
                <tr>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=preE%>" property="preEje"  styleId="preEje" onchange="EficaciaIn11(this)"/></td>
                    <td align="center"><html:text maxlength='15' style="width:90%" name="EficaciaIn11Form" value="<%=preT%>" property="preTotalAuto" styleId="preTotalAuto" disabled="true" onchange="EficaciaIn11(this);"/></td>
                    <td><input type="text" style="width:90%" name="promedio" value="0" readonly="readonly" id="etiqueta"></td>
                </tr>
                
                 <tfoot>
                    <tr>
                        <td colspan="2" class="rounded-foot-left">Notas: No hay notas adicionales.</td>
                        <td class="rounded-foot-right"></td>
                    </tr>
                </tfoot>
            </table>
        </div>
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
    </div>
                
                
    <br />
    <table id='rounded-corner' style='width: 100%'>
        <tr>
            <th width="15%">Comentarios</th>
            <th >
<%      //SE CONSULTA INDICADOR 
        String consultacom = "SELECT comentario FROM comentarios WHERE "
            + "id_universidad="+sesion.getAttribute("idUniversidad").toString()+" AND "
            + "id_periodo="+ sesion.getAttribute("idPeriodo").toString()+" AND indicador=11";
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
    <button type="button" class="btnGuardarIndicador"  onclick="enviarDatosIndicadores(guardarEficaciaIn11(),'EficaciaIn11Datos','EficaciaIn11Form');"  >Guardar</button>
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
