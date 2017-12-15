<%-- 
    Document   : CgutResponsables
    Created on : 5/07/2013, 08:07:02 AM
    author Daniel Ram�rez Torres
    Actualizaci�n 2016: Salvador Zamora Arias
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
<html:form action="/CgutResponsablesAction" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="2">
                <h2>Informaci�n de los Responsables:</h2>
            </td>
        </tr>
        <tr>
            <td align="right" width="300px">
                <label>Nombre de la Universidad:</label>
            </td>
            <td>
                <span id="comboUniversidad">
                    <html:select onchange="retrieveURL('/CgutResponsablesAction.msut?ask=buscaResponsable','CgutResponsablesForm');" 
                                 styleId="cboUni" property="cboUni" style="width:auto;" >
                        <html:option value="-1">Elige una Universidad...</html:option>
                        <html:optionsCollection name="CgutResponsablesForm" property="uni" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2"><h2>Especificaciones:</h2></td>
        </tr>
        <tr>
            <td align="right" width="300px">
                <label>Responsables:</label> 
            </td>
            <td> 
                <span id="comboResponsables">
                    <html:select onchange="retrieveURL('/CgutResponsablesAction.msut?ask=buscaResponsable2','CgutResponsablesForm');" 
                                 styleId="cboResponsables" property="cboResponsables" style="width:auto;" >
                        <html:option value="-1">Ingresar nuevo Responsable...</html:option>
                        <html:optionsCollection name="CgutResponsablesForm"  property="responsables" label="name" 
                                                value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Nombre:</label></td>
            <td>
                <span id="nombreResponsables">
                    <html:text maxlength="30" styleId="nombreResponsable" property="nombreResponsable" style="width:350px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Apellido Paterno:</label></td>
            <td>
                <span id="nombreAPResponsables">
                    <html:text maxlength="30" styleId="nombreAPResponsable" property="nombreAPResponsable" style="width:350px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Apellido Materno:</label></td>
            <td>
                <span id="nombreAMResponsables">
                    <html:text maxlength="30" styleId="nombreAMResponsable" property="nombreAMResponsable" style="width:350px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Cargo:</label></td>
            <td>
                <span id="nombreCargos">
                    <html:text maxlength="50" styleId="nombreCargo" property="nombreCargo" style="width:250px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Lada:</label></td>
            <td>
                <span id="nombreLada">
                    <html:text maxlength="15" styleId="nombreLada" property="nombreLada" style="width:150px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Tel�fono:</label></td>
            <td>
                <span id="nombreTelefonos">
                    <html:text maxlength="15" styleId="nombreTelefono" property="nombreTelefono" style="width:150px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Correo Electr�nico:</label></td>
            <td>
                <span id="nombreMails">
                    <html:text maxlength="50" styleId="nombreMail" property="nombreMail" style="width:300px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <span id="responsables">
                    <logic:equal name="CgutResponsablesForm" property="cboUni" value="0" >
                        <logic:notEqual name="CgutResponsablesForm" property="responsablesActivo" value="">
                            <a class="green" onclick="bajaResponsables();">
                                <i><strong><bean:write name="CgutResponsablesForm" property="responsablesActivo"></bean:write></strong></i>
                                </a>
                        </logic:notEqual>
                    </logic:equal>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <span id="mostrarBotonesResponsable">
                  
                    <a class="green" onclick="retrieveURL('/CgutResponsablesAction.msut?ask=nuevoResponsable','CgutResponsablesForm');">Nuevo registro</a>
                    <%--<a class="green" onclick="retrieveURL('/CgutResponsablesAction.msut?ask=guardarResponsable','CgutResponsablesForm');">Guardar cambios</a>--%>
                    <a class="green" onclick="validarFormularioResponsables();">Guardar cambios</a>
                </span>
            </td>
        </tr>
    </table>
</html:form>

