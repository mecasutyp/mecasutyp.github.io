<%-- 
    Document   : CgutModelos
    Created on : 3/07/2013, 09:57:24 AM
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

<html:form action="/CgutNiveles" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="2">
                <h2>Información de los Niveles:</h2>
            </td>
        </tr>
        <tr>
            <td align="right" width="300px">
                <label>Nivel:</label>
            </td>
            <td>
                <span id="comboProgramas">
                    <html:select styleId="cboNivel" onchange="retrieveURL('/CgutNiveles.msut?ask=buscaNivel','CgutNivelesForm');" property="cboNivel" style="width:auto;" >
                        <html:option value="-2">Ingresar nuevo Nivel...</html:option>
                        <html:optionsCollection name="CgutNivelesForm" property="niveles" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2"><h2>Especificaciones:</h2></td>
        </tr>
        <tr>
            <td align="right"><label>Nivel:</label></td>
            <td>
                <span id="nombreNiv">
                    <html:text maxlength="3" styleId="nombreNivel" property="nombreNivel" style="width:50px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Descripción:</label></td>
            <td>
                <span id="nombreDesc">
                    <html:text maxlength="80" styleId="nombreDescripcion" property="nombreDescripcion" style="width:500px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Abreviatura:</label></td>
            <td>
                <span id="nombreAbre">
                    <html:text maxlength="5" styleId="nombreAbreviatura" property="nombreAbreviatura" style="width:80px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <span id="nivelAct">
                    <logic:notEqual name="CgutNivelesForm" property="nivelActivo" value="">
                        <a class="green" onclick="bajaNivel();">
                            <i><strong><bean:write name="CgutNivelesForm" property="nivelActivo"></bean:write></strong></i>
                            </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <a class="green" onclick="retrieveURL('/CgutNiveles.msut?ask=nuevoNivel','CgutNivelesForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioNiveles();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>
