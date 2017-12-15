<%-- 
    Document   : CgutOrganismos
    Created on : 25/06/2013, 08:03:51 AM
    Author     : Daniel Ramírez Torres
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

<html:form action="/CgutOrganismos" onsubmit="return false">    
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="4">
                <h2>&nbsp;&nbsp;&nbsp;Información de los Organismos Acreditadores:</h2>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <label>Organismo Acreditador:</label>
            </td>
            <td colspan="2">
                <span id="universidades">
                    <html:select onchange="retrieveURL('/CgutOrganismos.msut?ask=buscaOrganismo','CgutOrganismosForm');organismoNacional();" property="cboOrganismo" style="width:auto;" >
                        <html:option value="0">Ingresar nueva organismo...</html:option>
                        <html:optionsCollection name="CgutOrganismosForm" property="organismos" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <h2>&nbsp;&nbsp;&nbsp;Detalles:</h2>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Nombre del Organismo:</label></td>
            <td colspan="2">
                <span id="nombreOrganismo">
                    <html:textarea styleId="nombreOrganismo" property="nombreOrganismo" style="width:500px;"/> 
                </span>
            </td>
        </tr>

        <tr>
            <td colspan="2" align="right"><label>Sigla:</label></td>
            <td colspan="2">
                <span id="sigla">
                    <html:text maxlength="20" styleId="sigla" property="sigla" style="width:150px;"/> 
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <span id="extranjero">
                    <html:hidden styleId="extranjero" property="extranjero" style="width:150px;"/> 
                </span>
            </td>
        </tr>

        <tr>
            <td align="center" colspan="3"><label>Extranjero:</label>
                <div id="radios1">                        
                    <input type="radio" id="radio1" name="radio" value="1" onclick="retrieveURL('/CgutOrganismos.msut?ask=cambiarExtranjero','CgutOrganismosForm');organismoNacional();" /><label for="radio1" >Si</label>
                    <input type="radio" id="radio2" name="radio" value="0" checked="checked" onclick="retrieveURL('/CgutOrganismos.msut?ask=cambiarNacional','CgutOrganismosForm');organismoNacional();" /><label for="radio2" >No</label>
                </div>
            </td>
        </tr>

        <tr>
            <td colspan="2" align="right">
                <span id="etpais">
                    <logic:notEqual name="CgutOrganismosForm" property="extranjero" value="0">
                        <label>País:</label>
                    </logic:notEqual>
                </span>  
            </td>
            <td >     
                <span id="pais">                                       
                    <logic:notEqual name="CgutOrganismosForm" property="extranjero" value="0">
                        <html:text maxlength="35" styleId="pais" property="pais" style="width:150px;"/>
                    </logic:notEqual>
                </span>                                       
            </td>
        <tr>
            <td colspan="2">
            </td>
            <td colspan="2" align="left">
                <span id="organismoActivo">
                    <logic:notEqual name="CgutOrganismosForm" property="organismoActivo" value="">
                        <a class="green" onclick="bajaOrganismo();">
                            <i><strong><bean:write name="CgutOrganismosForm" property="organismoActivo"></bean:write></strong></i>
                        </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <a class="green" onclick="retrieveURL('/CgutOrganismos.msut?ask=nuevoOrganismo','CgutOrganismosForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioOrg();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>
<script type="text/javascript">
    $(document).ready(function() {
        $("[id^=radio]").buttonset();  
    });
</script>