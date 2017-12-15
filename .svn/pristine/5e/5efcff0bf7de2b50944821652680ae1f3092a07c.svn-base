<%-- 
    Document   : CgutCategorias
    Created on : 5/07/2013, 08:07:19 AM
   Author     : Daniel Ramírez Torres
--%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<% HttpSession sesion = request.getSession();
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

<html:form action="/CgutCategoriasAction">    
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="2">
                <h2>Información de las Categor&iacute;as:</h2>
            </td>
        </tr>
        <tr>
            <td align="right" width="300px">
                <label>Categor&iacute;as:</label>
            </td>
            <td>
                <span id="comboCategorias">
                    <html:select styleId="cboCategorias" onchange="retrieveURL('/CgutCategoriasAction.msut?ask=buscaCategorias','CgutCategoriasForm');" 
                                 property="cboCategorias" style="width:auto;" >
                        <html:option value="-1">Ingresar nueva Categor&iacute;a...</html:option>
                        <html:optionsCollection name="CgutCategoriasForm" property="categorias" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2"><h2>Especificaciones:</h2></td>
        </tr>
                <tr>
            <td align="right"><label>Categor&iacute;a:sfsfsfsdf</label></td>
            <td>
                <span id="nombreDed">
                    <html:text maxlength="15"  styleId="ded" property="ded" style="width:150px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Categor&iacute;a:</label></td>
            <td>
                <span id="nombreCat">
                    <html:text maxlength="15"  styleId="nombrCategorias" property="nombrCategorias" style="width:150px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <span id="btnCategori">
                    <logic:notEqual name="CgutCategoriasForm" property="categoriaActiva" value="">
                        <a class="green" onclick="bajaCategoria();">
                            <i><strong><bean:write name="CgutCategoriasForm" property="categoriaActiva"></bean:write></strong></i>
                        </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <a class="green" onclick="retrieveURL('/CgutCategoriasAction.msut?ask=nuevaCategoria','CgutCategoriasForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioCategoria();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>

