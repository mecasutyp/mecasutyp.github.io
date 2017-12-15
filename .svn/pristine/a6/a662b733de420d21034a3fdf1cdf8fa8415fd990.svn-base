<%-- 
    Document   : CgutUsuarios
    Created on : 5/07/2013, 08:04:17 AM
    Author     : Cuauhtemoc Medina Muñoz
--%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/CgutUsuarios" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="2">
                <h2>Usuarios de las Universidades Tecnológicas:</h2>
            </td>
        </tr>
        <%--Combo donde estan los usuarios ya creados para evitar duplicarlos--%>
        <tr>
            <td colspan="2">
                <span id="cboListaUsuarios">
                    <html:select styleId="comboListaUniversidades" property="comboListaUniversidades" style="display:none;" >
                        <html:optionsCollection name="CgutUsuariosForm" property="listaUsuarios" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right" width="300px">
                <label>Instituci&oacute;n:</label>
            </td>
            <td>
                <span id="comboUniversidadUsuarios">
                    <html:select onchange="retrieveURL('/CgutUsuarios.msut?ask=buscaUsuariosResponsables','CgutUsuariosForm');" 
                                 styleId="cboUniUsuarios" property="cboUniUsuarios" style="width:auto;" >
                        <html:option value="-2">Elige una Universidad...</html:option>
                        <html:optionsCollection name="CgutUsuariosForm" property="uniUsuarios" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <%--Combo para seleccionar el responasable de la cgut al cual pertenece el usuario--%>
        <tr>
            <td align="right">
                <span id="spanEtiquetaResponsable">
                    <logic:equal name="CgutUsuariosForm" property="cboUniUsuarios" value="0">
                        <label>Responsable: </label>
                    </logic:equal>
                </span>
            </td>
            <td align="left">
                <span id="spancomboResponsables">
                    <logic:equal name="CgutUsuariosForm" property="cboUniUsuarios" value="0">
                        <html:select onchange="retrieveURL('/CgutUsuarios.msut?ask=cargarUsuarioCGUT','CgutUsuariosForm');" 
                                     styleId="cboResUsu" property="cboResUsu" style="width:auto;" >
                            <html:option value="-2">Selecciona un Responsable</html:option>
                            <html:optionsCollection name="CgutUsuariosForm" property="coleccionResponsables" label="name" 
                                                    value="id"></html:optionsCollection>
                        </html:select>
                    </logic:equal>
                </span>
            </td>
        </tr>
        <%--FIN Combo para seleccionar el responasable de la cgut al cual pertenece el usuario--%>
        <tr>
            <td colspan="2">
                <span id="spanTitulo2">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="visible" value="true">
                            <h2>Detalles del Usuario:</h2>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <span id="spanTituloUsuario">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="visible" value="true">
                            <label>Usuario:</label>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
            <td>
                <span id="nombreUsuario">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="visible" value="true">
                            <html:text maxlength="15" styleId="nombreUsu" property="nombreUsu" style="width:150px;"/>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <span id="spanPassword">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="modificarPassword" value="true">
                            <label>Password:</label>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
            <td>
                <span id="passwordd">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="modificarPassword" value="true">
                            <html:password maxlength="30" styleId="password" property="password" style="width:150px;"/>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <span id="spanconfirmacion">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="modificarPassword" value="true">
                            <label>Confirmaci&oacute;n de Password:</label>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
            <td>
                <span id="confirmaciondd">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="modificarPassword" value="true">
                            <html:password maxlength="30" styleId="confirmacion" property="confirmacion" style="width:150px;"/>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <span id="btnUsuarios">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:notEqual name="CgutUsuariosForm" property="visible" value="false">
                            <logic:equal name="CgutUsuariosForm" property="modificarPassword" value="false">
                                <a class="green" onclick="retrieveURL('/CgutUsuarios.msut?ask=cambiarPass','CgutUsuariosForm');">
                                    Reestablecer Contraseña y/o Nombre de Usuario
                                </a>
                            </logic:equal>
                        </logic:notEqual>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <span id="nuevoRegistroUsuario">
                    <logic:notEqual name="CgutUsuariosForm" property="cboUniUsuarios" value="-2">
                        <logic:equal name="CgutUsuariosForm" property="modificarPassword" value="true">
                            <a class="green" onclick="validarFormularioUsuarios();">Guardar</a>
                        </logic:equal>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
    </table>
</html:form>