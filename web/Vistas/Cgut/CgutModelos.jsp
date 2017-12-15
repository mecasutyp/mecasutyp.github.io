<%-- 
    Document   : CgutModelos
    Created on : 3/07/2013, 09:57:24 AM
    Author     : Cuauhtemoc Medina Mu�oz
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

<html:form action="/CgutModelos" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="4">
                <h2>Informaci�n de la Modalidad:</h2>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>Modalidad:</label>
            </td>
            <td colspan="2">
                <span id="comboModelo">
                    <html:select styleId="cboModelo" onchange="retrieveURL('/CgutModelos.msut?ask=buscaModelo','CgutModelosForm');" property="cboModelo" style="width:auto;" >
                        <html:option value="-1">Ingresar nueva Modalidad...</html:option>
                        <html:optionsCollection name="CgutModelosForm" property="modelos" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4"><h2>Especificaciones:</h2></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Nuevo nombre de Modelo:</label></td>
            <td colspan="2">
                <span id="nombreModelo2">
                    <html:text maxlength="50" styleId="nombreModelo" property="nombreModelo" style="width:400px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <span id="familiaact">
                    <logic:notEqual name="CgutModelosForm" property="modeloActivo" value="">
                        <a class="green" onclick="bajaModelo();">
                            <i><strong><bean:write name="CgutModelosForm" property="modeloActivo"></bean:write></strong></i>
                            </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <a class="green" onclick="retrieveURL('/CgutModelos.msut?ask=nuevoModelo','CgutModelosForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioModelo();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>