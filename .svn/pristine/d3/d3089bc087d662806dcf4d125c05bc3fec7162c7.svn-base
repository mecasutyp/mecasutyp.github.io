<%-- 
    Document   : CgutFamilias
    Created on : 16-jul-2013, 13:21:10
    Author     : Daniel Ramírez Torres
--%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%  HttpSession sesion = request.getSession();
    String id_periodo = "", id_user = "";
    if (sesion.getAttribute("usuario") == null) {
%>
<jsp:forward page="/login.msut">
    <jsp:param name="error" value="true"></jsp:param>
    <jsp:param name="ask" value="false"></jsp:param>
    <jsp:param name="errorMessage" value="Sesi&oacute;n caducada o inv&aacute;lida<br/>El recurso al que deseas acceder est&aacute; restringido, debes iniciar sesi&oacute;n para utilizarlo."></jsp:param>
</jsp:forward>
<%  } else {
        id_periodo = sesion.getAttribute("idPeriodo").toString();
        id_user = sesion.getAttribute("idUsuario").toString();
    }
%>

<html:form action="/CgutFamiliasAction" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="4">
                <h2>Información de las Familias:</h2>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>Área:</label>
            </td>
            <td colspan="2">
                <span id="comboAreasFam">
                    <html:select styleId="cboAreaFam" onchange="retrieveURL('/CgutFamiliasAction.msut?ask=buscaAreaFamilia','CgutFamiliasForm');" property="cboAreaFam" style="width:auto;" >
                        <html:option value="-1">Elige un área...</html:option>
                        <html:optionsCollection name="CgutFamiliasForm" property="areasFam" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>Familia:</label>
            </td>
            <td colspan="2">
                <span id="comboFamiliasOriginal">
                    <html:select onchange="retrieveURL('/CgutFamiliasAction.msut?ask=buscaFamilia','CgutFamiliasForm');" property="cboFamilia" style="width:auto;" >
                        <html:option value="-1">Ingresar nueva familia...</html:option>
                        <html:optionsCollection name="CgutFamiliasForm" property="familias" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4"><h2>Especificaciones:</h2></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Nombre de la Familia:</label></td>
            <td colspan="2">
                <span id="nombreFamiliaS">
                    <html:text styleId="nombreFamilia" maxlength="80" property="nombreFamilia" style="width:400px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Nomenclatura:</label></td>
            <td colspan="2">
                <span id="nomenclaturaFamS">
                    <html:text styleId="nomenclaturaFam" maxlength="4" property="nomenclaturaFam" maxlength="4"  style="width:100px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2">

            </td>
            <td colspan="2" align="left">
                <span id="familiaActivo">
                    <logic:notEqual name="CgutFamiliasForm" property="familiaActivo" value="">
                        <a class="green" onclick="bajaFamilia();">
                            <i><strong><bean:write name="CgutFamiliasForm" property="familiaActivo"></bean:write></strong></i>
                            </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <a class="green" onclick="retrieveURL('/CgutFamiliasAction.msut?ask=nuevaFamilia','CgutFamiliasForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioFamilias();">Guardar cambios</a>
            </td>
        </tr>
    </table>

</html:form>
