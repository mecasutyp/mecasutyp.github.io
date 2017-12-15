<%-- 
    Document   : CgutProgramas
    Created on : 17-jul-2013, 9:36:58
    Author     : Daniel Ram�rez Torres
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
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

<html:form action="/CgutProgramasAction" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="4">
                <h2>Informaci�n de los Programas Educativos:</h2>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>�rea:</label>
            </td>
            <td colspan="2">
                <span id="comboAreasProg">
                    <html:select styleId="cboAreaProg" onchange="retrieveURL('/CgutProgramasAction.msut?ask=buscaAreaProg','CgutProgramasForm');" property="cboAreaProg" style="width:auto;" >
                        <html:option value="-1">Elige un �rea...</html:option>
                        <html:optionsCollection name="CgutProgramasForm" property="areasProg" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>

        <tr>
            <td colspan="2" align="right">
                <label>Familia:</label>
            </td>
            <td colspan="2">
                <span id="comboFamiliasProg">
                    <html:select styleId="cboFamiliaProg" onchange="retrieveURL('/CgutProgramasAction.msut?ask=buscaFamiliaProg','CgutProgramasForm');" property="cboFamiliaProg" style="width:auto;" >
                        <html:option value="-1">Elige una familia...</html:option>
                        <html:optionsCollection name="CgutProgramasForm" property="familiasProg" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>Programas Educativos:</label>
            </td>
            <td colspan="2">
                <span id="comboProgramasOriginal">
                    <html:select onchange="retrieveURL('/CgutProgramasAction.msut?ask=buscaPrograma','CgutProgramasForm');" property="cboPE" style="width:auto;" >
                        <html:option value="-1">Ingresar nuevo programa...</html:option>
                        <html:optionsCollection name="CgutProgramasForm" property="programasEducativos" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4"><h2>Especificaciones:</h2></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Nombre del Programa:</label></td>
            <td colspan="2">
                <span id="nombreProg">
                    <html:text styleId="nombrePrograma" maxlength="80" property="nombrePrograma" style="width:400px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Nomenclatura:</label></td>
            <td colspan="2">
                <span id="nomenclaturaProgspan">
                    <html:text styleId="nomenclaturaProg" maxlength="8" property="nomenclaturaProg" maxlength="8" style="width:100px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>Nivel:</label>
            </td>
            <td colspan="2">
                <span id="comboNiveles">
                    <html:select styleId="cboNivel" property="cboNivel" style="width:auto;" >
                        <html:option value="-2">Seleccione nivel...</html:option>
                        <html:optionsCollection name="CgutProgramasForm" property="niveles" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>Versi�n:</label>
            </td>
            <td colspan="2">
                <span id="versionNiveles">
                    <html:text styleId="versionPrograma" maxlength="15" style="width:100px;" property="versionPrograma"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2">

            </td>
            <td colspan="2" align="left">
                <span id="programaActivo">
                    <logic:notEqual name="CgutProgramasForm" property="programaActivo" value="">
                        <a class="green" onclick="bajaPrograma();">
                            <i><strong><bean:write name="CgutProgramasForm" property="programaActivo"></bean:write></strong></i>
                        </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <a class="green" onclick="retrieveURL('/CgutProgramasAction.msut?ask=nuevoPrograma','CgutProgramasForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioProgramas();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>

