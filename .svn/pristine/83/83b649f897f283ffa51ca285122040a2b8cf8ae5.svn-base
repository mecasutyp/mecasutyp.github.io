<%-- 
    Document   : Mensajes
    Created on : 31/10/2014
    Author     : Cuauhtemoc Medina Muñoz
--%>
<%@page import="javax.net.ssl.SSLEngineResult.Status"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("usuario") == null) {
%>
<jsp:forward page="/login.msut">
    <jsp:param name="error" value="true"></jsp:param>
    <jsp:param name="ask" value="false"></jsp:param>
    <jsp:param name="errorMessage" value="Sesi&oacute;n caducada o inv&aacute;lida<br/>El recurso al que deseas acceder est&aacute; restringido, debes iniciar sesi&oacute;n para utilizarlo."></jsp:param>
</jsp:forward>
<%            }
%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<tiles:insert template="/Vistas/Master.jsp">
    <tiles:put name="title" type="string">
        MECASUT - Mensajes
    </tiles:put>
    <tiles:put name="scripts" type="string">
        <!--<script language="javascript" type="text/javascript" src="js/mensajes.js"></script>-->
        <script language="javascript" type="text/javascript" src="js/mensajesNuevo.js"></script>
    </tiles:put>
    <tiles:put name="body" type="string">
        <br/><br/><br/>
        <div>
            <div align="center">
                <h2>Mensajes</h2>
                <div name="tabPerfil" id="tableMessages">
                    <table id="rounded-corner" style="width: 100%" align="center">
                        <thead>
                            <tr>
                                <th width="25%" class="rounded-company">Remitente</th>
                                <th width="25%" class="rounded-q1">Asunto</th>
                                <th width="20%" class="rounded-q1">Estatus</th>
                                <th width="20%" class="rounded-q1">Fecha/Hora</th>
                                <th width="10%" class="rounded-q4">Eliminar</th>
                            </tr>
                        </thead>
                        <tbody id="cuerpo"></tbody>
                        <tr>
                            <th class="rounded-q1" colspan="6"></th>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div id="leermensaje" title="Mensaje completo" style="display: block;">
            <input type="hidden" id="idDest" />
            <strong class="titul2">Mensaje de:</strong> <strong id="deUttec"></strong>
            <br/>
            <strong id="asunto" class="titul2">Asunto</strong> 
            <br/>
            <textarea id="asuntoM" style="width:100%; font-size: 1em;" value="" styleClass="text ui-widget-content ui-corner-all" 
                      disabled="disabled" rows="1" maxlength="100" ></textarea>
            <br/>
            <br/>
            <textarea id="mensajeCompleto" style="width:100%; font-size: .9em;" value="" styleClass="text ui-widget-content ui-corner-all" 
                      disabled="disabled" rows="12" ></textarea>
            <br/>
            <br/>
            <strong class="titul2">Respuesta:</strong>
            <textarea id="mensajeCompletoR" style="width:100%; font-size: .9em;" value="" styleClass="text ui-widget-content ui-corner-all" 
                      rows="14" ></textarea>
            <p class="validateTips"></p>
        </div>
    </tiles:put>
</tiles:insert>