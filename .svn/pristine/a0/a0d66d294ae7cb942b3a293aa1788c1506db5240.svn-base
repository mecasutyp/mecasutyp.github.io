<%-- 
    Document   : Perfil
    Created on : Dec 10, 2012, 8:31:21 PM
    Author     : Joshua
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
        MECASUT - Perfil de Usuario
    </tiles:put>
    <tiles:put name="scripts" type="string">
        <script language="javascript" type="text/javascript" src="js/perfilUsuario.js"></script>
    </tiles:put>
    <tiles:put name="body" type="string">
        <br/><br/><br/>
        <div>
            <span id="errorMessage" style="display: none">
            </span>
            <%

                String user = sesion.getAttribute("usuario").toString();
                String consulta = "Select nombre, apaterno, amaterno, cargo, telefono, mail from responsables where id_responsable = ".concat(sesion.getAttribute("idResponsable").toString());
                ResultSet rs = conexion.Consultar(consulta);
                if (rs.next()) {
            %>
            <html:form action="/Perfil">
                <div align="center">
                    <h2>Perfil</h2>
                    <div class="box" name="tabPerfil">
                        <table align="center" border="0px" width="600">
                            <tr>
                                <td colspan="2" align="right"><label>Nombre:</label></td>
                                <td colspan="2">
                                    <html:text property="nombre" maxlength="30" disabled="disabled" value="<%=rs.getString(1)%>"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Apellido Paterno:</label></td>
                                <td colspan="2">
                                    <html:text property="apaterno" maxlength="30" disabled="disabled" value="<%=rs.getString(2)%>"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Apellido Materno</label></td>
                                <td colspan="2">
                                    <html:text property="amaterno" maxlength="30" disabled="disabled" value="<%=rs.getString(3)%>"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Cargo:</label></td>
                                <td colspan="2">
                                    <html:text property="cargo" maxlength="50" value="<%=rs.getString(4)%>" readonly="readonly" disabled="disabled"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Tel&eacute;fono:</label></td>
                                <td colspan="2">
                                    <html:text property="telefono" maxlength="15" value="<%=rs.getString(5)%>"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>E-Mail:</label></td>
                                <td colspan="2">
                                    <html:text property="mail" maxlength="50" value="<%=rs.getString(6)%>"/>
                                </td>
                            </tr>
                        </table>
                        <a onclick="retrieveURL('/Perfil.msut?ask=guardarPerfil','PerfilForm');">Guardar</a>
                    </div>
                </div>
                <div align="center">
                    <h2>Datos del Usuario</h2>
                    <div class="box" name="tabUsuario">
                        <table align="center" border="0px" width="500">
                            <tr>
                                <td colspan="2" align="center"><label>Usuario: <%=user%></label></td>
                            </tr>
                            <tr>
                                <td align="right"><label>Contraseña Actual:</label></td>
                                <td>
                                    <html:password property="password" maxlength="20"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><label>Nueva Contraseña:</label></td>
                                <td>
                                    <input name="passNuevo" type="password" maxlength="20"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right"><label>Confirmar Contraseña:</label></td>
                                <td>
                                    <html:password property="nuevoPassword" maxlength="20"/>
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" id="ask" name="ask"/>
                        <input type="hidden" id="idUser" name="idUser"/>
                        <a onclick="guardarUsuario();">Guardar</a>
                    </div>
                </div>
            </html:form>
            <%                }
            %>
        </div>
        <%

            String errorPassword = "false";
            try {
                errorPassword = request.getAttribute("errorPassword").toString();
        %>
        <script type="text/javascript">
            $("#errorMessage").html("La contraseña actual es incorrecta");
            $("#errorMessage").addClass("error");
            $("#errorMessage").slideDown();
            $().alerta("rojo","La contrase&ntilde;a actual es incorrecta, no se han guardado los datos");
        </script>
        <%
            } catch (Exception ex) {
            }

        %>
    </tiles:put>
</tiles:insert>