<%-- 
    Document   : CgutUniversidad
    Created on : 2/07/2013, 09:16:19 AM
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
<html:form action="/CgutUniversidad" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="4">
                <h2>Informaci�n de la Universidad:</h2>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="4">
                <span id="universidades">
                    <html:select onchange="retrieveURL('/CgutUniversidad.msut?ask=buscaUni','CgutUniversidadForm');cambiarFecha();" property="universidad" style="width:auto;" >
                        <html:option value="-1">Ingresar nueva universidad...</html:option>
                        <html:optionsCollection name="CgutUniversidadForm" property="universidades" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
    </table>
    <!--t1-->
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="4"><h2>Detalles:</h2></td>
        </tr>
        <tr>
            <td colspan="2" align="right" width="30%"><label>Clave CGUT:</label></td>
            <td colspan="2">
                <span id="clave">
                    <html:text styleId="cvecgut" maxlength="14" property="cvecgut" style="width:150px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right" width="30%"><label>RFC:</label></td>
            <td colspan="2">
                <span id="rfcIN">
                    <html:text styleId="rfc" maxlength="16" property="rfc" style="width:150px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Nombre de la Universidad:</label></td>
            <td colspan="2">
                <span id="nombreUniS">
                    <html:text styleId="nombreUni" maxlength="80" property="nombreUni" style="width:500px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Abreviatura:</label></td>
            <td colspan="2">
                <span id="abreviaturaS">
                    <html:text styleId="abreviatura" maxlength="15" property="abreviatura" style="width:150px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right"><label>Fecha de Creaci&oacute;n:</label></td>
            <td colspan="2">
                <input type="text" style="width: 150px" name="fechaAcreditacion" id="fechaAcreditacion"/><i onclick="cambiarFecha();">(aaaa-mm-dd)</i>
                <span id="fechaOculta" onchange="cambiarFecha();">
                    <html:hidden  styleId="fechaAcred" property="fechaAcred"></html:hidden>
                    </span>
                </td>
            </tr>
            <tr>
                <td colspan="2">

                </td>
                <td colspan="2" align="left">
                    <span id="uniact">
                    <logic:notEqual name="CgutUniversidadForm" property="uniActivo" value="">
                        <a class="green" onclick="bajaUniversidad();">
                            <i><strong><bean:write name="CgutUniversidadForm" filter="false" property="uniActivo"></bean:write></strong></i>
                            </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <a class="green" onclick="retrieveURL('/CgutUniversidad.msut?ask=nuevaUni',
                    'CgutUniversidadForm');MostrarFecha('fechaAcreditacion');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioUni();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>
<script>
    $(document).ready(function() {
        $( "#fechaAcreditacion" ).datepicker({
            showOn: "button",
            changeMonth: true,
            changeYear: true,
            dateFormat:"yy-mm-dd",
            buttonImage: "images/calendar.gif",
            buttonImageOnly: true
        });
    });
</script>