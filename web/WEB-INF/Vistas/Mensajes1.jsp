<%-- 
    Document   : Perfil
    Created on : Dec 10, 2012, 8:31:21 PM
    Author     : Joshua, Cuauhtemoc Medina Mu�oz, Daniel Ramirez Torres
--%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("usuario") == null) {
%>
<jsp:forward page="/login.msut">
    <jsp:param name="error" value="true"></jsp:param>
    <jsp:param name="ask" value="false"></jsp:param>
    <jsp:param name="errorMessage" value="Sesi&oacute;n caducada o inv&aacute;lida<br/>El recurso al que deseas acceder est&aacute; restringido, debes iniciar sesi&oacute;n para utilizarlo."></jsp:param>
</jsp:forward>
<%            }
%>
<%@page import="java.sql.ResultSet, com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<tiles:insert template="/Vistas/Master.jsp">
    <%
        ConexionMySQL conexion = new ConexionMySQL();
    %>
    <tiles:put name="title" type="string">
        MECASUT - Mensajes
    </tiles:put>
    <tiles:put name="scripts" type="string">
        <script language="javascript" type="text/javascript" src="js/mensajes.js"></script>
    </tiles:put>
    <tiles:put name="body" type="string">
        <br/><br/><br/>
        <div>
            <%
                String id_re = "";
                String id_respon = "";
                String mensaje = "";
                String mensaje_com = "";
                String de = "";
                
                String utt = "";
                String consulta = "Select nombre, apaterno, amaterno, cargo, telefono, mail from responsables where id_responsable = ".concat(sesion.getAttribute("idResponsable").toString());
                ResultSet rs = conexion.Consultar(consulta);
                if (rs.next()) {
            %>
            <div align="center">
                <h2>Mensajes</h2>
                <div  name="tabPerfil">
                    <table id="rounded-corner" style="width: 100%" align="center">
                        <tr>                          
                            <th width="15%" class="rounded-company" >Fecha y hora</th>
                            <th width="25%" class="rounded-q1">Universidad</th>
                            <th width="20%" class="rounded-q1">Remitente</th>
                            <th width="20%" class="rounded-q1">Mensaje</th>
                            <th width="20%" class="rounded-q4">Eliminar Mensaje</th>
                        </tr>
                        <%
                            if (sesion.getAttribute("tipoUsuario").toString().equals("UT")) {
                                conexion.Conectar();
                                conexion.Modificar("Update reporte_errores set leido = 1 where tipo = 2 and id_destinatario=".concat(sesion.getAttribute("idResponsable").toString()));
                                String sql = "Select id_reporte, fecha_redactado, u.nombre_universidad, CONCAT(r.nombre, ' ', r.apaterno, ' ', r.amaterno), comentario,re.id_responsable from reporte_errores re inner join responsables r on re.id_responsable = r.id_responsable inner join universidades u on r.id_universidad = u.id_universidad where tipo = 2 and id_destinatario = ".concat(sesion.getAttribute("idResponsable").toString());
                                ResultSet res = conexion.Consultar(sql);
                                while (res.next()) {
                                    id_re = res.getString(1);
                                    mensaje = res.getString(5);
                                    mensaje = mensaje.substring(0, 10);
                                    id_respon = res.getString(6);
                                    mensaje_com = res.getString(5);
                                    de = res.getString(4);
                                    utt = res.getString(3);
                        %>                        
                        <tr>
                        <input type="hidden" id="tipo<%=id_re%>" value="1"/>
                        <input type="hidden" name="id" value="<%=id_re%>"/>
                        <input type="hidden" id="id_responsable<%=id_re%>" value="<%=id_respon%>"/>
                        <td><%=res.getString(2)%></td>
                        <td><%=res.getString(3)%></td>
                        <td><%=res.getString(4)%></td>                        
                        <td><a href="#" onclick="$('#leermensaje').dialog().dialog( 'open' ); selectmensaje(<%=id_re%>);" title="<%=mensaje_com%>"><%=mensaje%></a></td>
                        <input type="hidden" id="area<%=id_re%>" value="<%=mensaje_com%>"/>
                        </tr>
                        <%
                            }
                        %>
                        <div id="leermensaje" title="Mensaje completo" style="display: none;"  >
                            <strong class="titul2">Mensaje de:</strong> <strong><%=de%> (<%=utt%>)</strong>
                            <textarea id="area_text" disabled="disabled" style="width: 100%" rows="10">
                            </textarea>  
                            <br/>
                            <br/>
                            <strong class="titul2">Respuesta:</strong>
                            <textarea id="area_text_respuesta" style="width: 100%" rows="9" ></textarea> 
                            <p class="validateTips"></p>
                        </div>
                        <%
                        } else if (sesion.getAttribute("tipoUsuario").toString().equals("CG")) {
                            conexion.Conectar();
                            conexion.Modificar("Update reporte_errores set leido = 1 where tipo = 1");
                            String sql = "Select id_reporte, fecha_redactado, u.nombre_universidad, CONCAT(r.nombre, ' ', r.apaterno, ' ', r.amaterno), comentario,re.id_responsable from reporte_errores re inner join responsables r on re.id_responsable = r.id_responsable inner join universidades u on r.id_universidad = u.id_universidad where tipo = 1";
                            ResultSet res = conexion.Consultar(sql);
                            while (res.next()) {
                                id_re = res.getString(1);
                                mensaje = res.getString(5);
                                mensaje = mensaje.substring(0, 10);
                                id_respon = res.getString(6);
                                mensaje_com = res.getString(5);
                                de = res.getString(4);
                                utt = res.getString(3);
                        %>
                        <tr>
                        <input type="hidden" id="tipo<%=id_re%>" value="2"/>
                        <input type="hidden" name="id" value="<%=id_re%>"/>
                        <input type="hidden" id="id_responsable<%=id_re%>" value="<%=id_respon%>"/>
                        <td><%=res.getString(2)%></td>
                        <td><%=res.getString(3)%></td>
                        <td><%=res.getString(4)%></td>
                        <td><a href="#" onclick="$('#leermensaje').dialog().dialog( 'open' ); selectmensaje(<%=id_re%>);" title="Leer <%=mensaje%>"><%=mensaje%></a></td>
                        <td><a href="#" onclick="$('#confirm_del_mes').dialog().dialog( 'open' );eliminarMensaje(<%=id_re%>);" title="Eliminar Mensaje">Eliminar Mensaje</a></td>
                        <input type="hidden" id="area<%=id_re%>" value="<%=mensaje_com%>"/>
                        </tr>
                        <%                                            }
                        %>
                        <div id="leermensaje" title="Mensaje completo" style="display: none;"  >
                            <strong class="titul2">Mensaje de:</strong> <strong><%=de%> (<%=utt%>)</strong>
                            <textarea id="area_text" disabled="disabled" style="width: 100%" rows="10">
                            </textarea>  
                            <br/>
                            <br/>
                            <strong class="titul2">Respuesta:</strong>
                            <textarea id="area_text_respuesta" style="width: 100%" rows="9" ></textarea>  
                            <p class="validateTips"></p>
                        </div>

                        <div id="confirm_del_mes" title="Confirmaci&oacute;n" style="display: none;">
                            <p><span ></span>El mensaje ser&aacute; eliminado de forma permanente. �Est&aacute; seguro de eliminarlo?</p>
                        </div>


                        <%
                        } else if (sesion.getAttribute("tipoUsuario").toString().equals("AD")) {
                            conexion.Conectar();
                            conexion.Modificar("Update reporte_errores set leido = 1 where tipo = 0");
                            String sql = "Select id_reporte, fecha_redactado, u.nombre_universidad, CONCAT(r.nombre, ' ', r.apaterno, ' ', r.amaterno), comentario,re.id_responsable from reporte_errores re inner join responsables r on re.id_responsable = r.id_responsable inner join universidades u on r.id_universidad = u.id_universidad where tipo = 0";
                            ResultSet res = conexion.Consultar(sql);
                            while (res.next()) {
                                id_re = res.getString(1);
                                mensaje = res.getString(5);
                                mensaje = mensaje.substring(0, 10);
                                id_respon = res.getString(6);
                                mensaje_com = res.getString(5);
                                de = res.getString(4);
                                utt = res.getString(3);
                        %>
                        <tr>
                        <input type="hidden" id="tipo<%=id_re%>" value="2"/>
                        <input type="hidden" name="id" value="<%=id_re%>"/>
                        <input type="hidden" id="id_responsable<%=id_re%>" value="<%=id_respon%>"/>
                        <td><%=res.getString(2)%></td>
                        <td><%=res.getString(3)%></td>
                        <td><%=res.getString(4)%></td>
                        <td><a href="#" onclick="$('#leermensaje').dialog( 'open' ); selectmensaje(<%=id_re%>);" title="Leer Mensaje Completo"><%=mensaje%></a></td>
                        <input type="hidden" id="area<%=id_re%>" value="<%=mensaje_com%>"/>
                        </tr>
                        <%
                            }
                        %>
                        <div id="leermensaje" title="Mensaje completo" style="display: none;"  >
                            <strong class="titul2">Mensaje de:</strong> <strong><%=de%> (<%=utt%>)</strong>
                            <textarea id="area_text" disabled="disabled" style="width: 100%" rows="10">
                            </textarea>  
                            <br/>
                            <br/>
                            <strong class="titul2">Respuesta:</strong>
                            <textarea id="area_text_respuesta" style="width: 100%" rows="9" ></textarea>  
                            <p class="validateTips"></p>
                        </div>
                        <%
                            }
                        %>                                               
                        <tr>
                            <th class="rounded-q1" colspan="5"></th>
                        </tr>
                    </table>
                </div>
            </div>
            <%
                }%>
        </div>
    </tiles:put>
</tiles:insert>


