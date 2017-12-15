<%-- 
    Document   : CgutPreguntas
    Created on : 31/07/2013, 08:10:28 AM
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

<html:form action="/CgutPreguntas" onsubmit="return false">
    <table align="center" border="0px" width="900">
        <tr>
            <td colspan="4">
                <h2>Preguntas:</h2>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Encuesta:</label></td>
            <td align="left">
                <span id="comboPregEncuesta">
                    <html:select onchange="retrieveURL('/CgutPreguntas.msut?ask=buscarPregEncuesta','CgutPreguntasForm');" 
                                 styleId="cmbPregEnc" property="cmbPregEnc" style="width:auto;" >
                        <html:option value="-1">Elige una Encuesta...</html:option>
                        <html:option value="3">Satisfacción Alumnos Servicios</html:option>
                        <html:option value="10">Satisfacción Alumnos Educación Continua</html:option>
                        <html:optionsCollection name="CgutPreguntasForm" property="encuestas2" 
                                                label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <%--Las categorias se muestran solo cuando se selecciona la encuesta 3 --%>
        <tr>
            <td align="right">
                <span id="labelCat">
                    <logic:equal name="CgutPreguntasForm" property="cmbPregEnc" value="3">
                        <label>Categor&iacute;a:</label>
                    </logic:equal>
                </span>
            </td>
            <td colspan="3">
                <span id="cmboEnc2">
                    <logic:equal name="CgutPreguntasForm" property="cmbPregEnc" value="3">
                        <html:select styleId="cboCategoPre2" 
                                     onchange="retrieveURL('/CgutPreguntas.msut?ask=buscaCategoPre2','CgutPreguntasForm');"
                                     property="cboCategoPre2" style="width:auto;" >
                            <html:option value="-1">Elige una Categor&iacute;a...</html:option>
                            <html:optionsCollection name="CgutPreguntasForm" 
                                                    property="categoPre2" label="name" value="id"></html:optionsCollection>
                        </html:select>
                    </logic:equal>
                </span>
            </td> 
        </tr>
        <%--FIN Las categorias se muestran solo cuando se selecciona la encuesta 3 --%>
        <%--Preguntas--%>
        <tr>
            <td align="right">
                <label>Preguntas:</label>
            </td>
            <td colspan="3">
                <span id="comboPregPreguntas">
                    <html:select onchange="retrieveURL('/CgutPreguntas.msut?ask=buscarPregPregunta','CgutPreguntasForm');" 
                                 styleId="cmbPregPreguntas" property="cmbPregPreguntas" style="width:800px;" >
                        <html:option value="-1">Ingresar nueva Pregunta...</html:option>
                        <html:optionsCollection name="CgutPreguntasForm" property="preguntas" label="name" value="id"></html:optionsCollection>
                    </html:select>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4"><h2>Especificaciones:</h2></td>
        </tr>
        <tr>
            <td align="right"><label>Pregunta:</label></td>
            <td align="left">
                <span id="txtpreguntas">
                    <html:text styleId="txtNombrePreguntas" 
                               property="txtNombrePreguntas" style="width:800px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Nombre:</label></td>
            <td align="left">
                <span id="txtPalabraClavePreguntas">
                    <html:text styleId="txtPalabraClavePreguntas" 
                               property="txtPalabraClavePreguntas" style="width:800px;"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" >
                <span id="preguntasact">
                    <logic:notEqual name="CgutPreguntasForm" property="preguntasActiva" value="">
                        <a class="green" onclick="bajaPreguntas();">
                            <i><strong><bean:write name="CgutPreguntasForm" property="preguntasActiva"></bean:write></strong></i>
                        </a>
                    </logic:notEqual>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <a class="green" onclick="retrieveURL('/CgutPreguntas.msut?ask=nuevaPregunta','CgutPreguntasForm');">Nuevo registro</a>
                <a class="green" onclick="validarFormularioPreguntas();">Guardar cambios</a>
            </td>
        </tr>
    </table>
</html:form>