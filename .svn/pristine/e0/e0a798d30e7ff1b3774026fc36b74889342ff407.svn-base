<%-- 
    Document   : CgutServicios
    Created on : 29/07/2013, 02:46:33 PM
    Author     : Cuauhtemoc Medina Muñoz
--%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
    HttpSession sesion = request.getSession();
    String id_periodo = "", id_user = "";
    if (sesion.getAttribute("usuario") == null) {
%>
<jsp:forward page="/login.msut">
    <jsp:param name="error" value="true"></jsp:param>
    <jsp:param name="ask" value="false"></jsp:param>
    <jsp:param name="errorMessage" value="Sesi&oacute;n caducada o inv&aacute;lida<br/>El recurso al que deseas acceder est&aacute; restringido, debes iniciar sesi&oacute;n para utilizarlo."></jsp:param>
</jsp:forward>
<%    } else {
        id_periodo = sesion.getAttribute("idPeriodo").toString();
        id_user = sesion.getAttribute("idUsuario").toString();
    }
%>
<html:form action="/CgutServicios" onsubmit="return false">
    <table align="center" border="0px" width="900px">
        <tr>
            <td colspan="2">
                <h2>Servicios</h2>
            </td>
        </tr>
        <tr>
            <td align="right" style="width: 33%"><label>Servicio:</label></td>
            <td align="left">
                <span id="comboServicio">
                    <html:select onchange="retrieveURL('/CgutServicios.msut?ask=buscarServicio','CgutServiciosForm');" 
                                 property="cboServicios" style="width:auto;" >
                        <html:option value="-1">Ingresar Nuevo Servicio...</html:option>
                        <html:optionsCollection name="CgutServiciosForm" property="servicios" label="name" 
                                                value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <h2>Detalles:</h2>
            </td>
        </tr>
        <tr>
            <td align="right">
                <span id="labelNombre">
                    <label>Nombre del Servicio:</label>
                </span>
            </td>
            <td align="left">
                <span id="CampoNombre">
                    <html:text style="width: 350px" property="nombre" styleId="nombre"></html:text>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <span id="labelDescription">
                    <label>Descripción:</label>
                </span>
            </td>
            <td align="left">
                <span id="CampoDescripcion">
                    <html:textarea rows="3" style="width: 350px" property="descripcion" styleId="descripcion"></html:textarea>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <a class="green" onclick="retrieveURL('/CgutServicios.msut?ask=nuevo','CgutServiciosForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioServicios();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>