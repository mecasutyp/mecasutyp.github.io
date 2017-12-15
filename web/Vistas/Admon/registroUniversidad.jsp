<%-- 
    Document   : registroUniversidad
    Created on : 27/02/2012, 11:55:59 AM
    Author     : Joshua , Cuauhtemoc Medina Muñoz
--%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@page import="com.mecasut.admon.*, com.mecasut.shared.Universidad"%>
<%  int iniciado = 0, terminado = 0;
    String cveUniversidad = "";
    Universidad uni = new Universidad();
    String cveCgut = "";
    String universidad = "";
    String fechaAcreditacion = "";
    String rfc = "";
    HttpSession sesion = request.getSession();

    if (sesion.getAttribute("usuario") == null) {
%>
        <jsp:forward page="/login.msut">
            <jsp:param name="error" value="true"></jsp:param>
            <jsp:param name="ask" value="false"></jsp:param>
            <jsp:param name="errorMessage" value="Sesi&oacute;n caducada o inv&aacute;lida<br/>El recurso al que deseas acceder est&aacute; restringido, debes iniciar sesi&oacute;n para utilizarlo."></jsp:param>
        </jsp:forward>
<%  } else {      
        cveUniversidad = String.valueOf(sesion.getAttribute("idUniversidad"));
        cveCgut = uni.getCveCgut(cveUniversidad);
        universidad = uni.getNombreUniversidad(cveUniversidad);
        fechaAcreditacion = uni.getFechaAcreditacion(cveUniversidad);
        rfc = uni.getRFC(cveUniversidad);
        ConexionMySQL conexion = new ConexionMySQL();
        String consulta = "Select mecasut_iniciado, mecasut_terminado from control_universidad where id_universidad = "
                .concat(sesion.getAttribute("idUniversidad").toString()).concat(" and id_periodo=")
                .concat(sesion.getAttribute("idPeriodo").toString());

        ResultSet rs = conexion.Consultar(consulta);
        if (rs.next()) {
            iniciado = rs.getInt("mecasut_iniciado");
            terminado = rs.getInt("mecasut_terminado");
        } else {
            iniciado = 0;
            terminado = 0;
        }
    }
    String nocompletos = "false";
    
    if (sesion.getAttribute("nocompletos") != null) {
        nocompletos = request.getAttribute("nocompletos").toString();       
    }
%>
<tiles:insert template="/Vistas/Master.jsp">
    <tiles:put name="title" type="string">
        MECASUT - Información de la Universidad
    </tiles:put>    
    <tiles:put name="scripts" type="string">
        <script language="javascript" type="text/javascript" src="js/autocomplete.js"></script>
        <script language="javascript" type="text/javascript" src="js/mailcheck.js"></script>
        <script language="javascript" type="text/javascript" src="js/jquery.bsmselect.js"></script>
        <script language="javascript" type="text/javascript" src="js/registroUniversidad.js"></script>
        <script language="javascript" type="text/javascript" src="js/jquery.combobox.js"></script>
        <script language="javascript" type="text/javascript" src="js/checkData.js"></script>
        <link rel="stylesheet" type="text/css" href="css/acstyle.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery.bsmselect.css" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <br>
       <%
        SimpleDateFormat formateador = new SimpleDateFormat("dd' de 'MMMM' de 'yyyy");
        Date fecha = new Date();
        %>
        <% String fec= formateador.format(fecha);%>
       
        <h1 style="text-align: center;">L&iacute;mite de captura: 19 de enero del 2018</h1>
        <br>
         <h1 style="text-align: center;">Informaci&oacute;n de la Universidad</h1>
        
         <%    if (iniciado != 1 ) { %>
        <p style="text-align: center;">Por favor actualice la informaci&oacuten de la universidad mostrada a continuaci&oacute;n, <br/>
            cuando termine pulse el bot&oacute;n Guardar.</p>
        <%}%>
        <br/>
        <div id="tabs">
            <ul>
                <li><a id="tabUni" href="#t1">Universidad</a></li>
                <li><a id="tabDom" href="#t2">Domicilio</a></li>
                <li><a id="tabCon" href="#t3">Datos Contacto</a></li>
                <li><a id="tabAca" href="#t4">Datos Acad&eacute;micos</a></li>

                <%  if (iniciado != 1 && terminado != 1) {  %>
                <li><a onclick="validarFormulario(false);">Guardar</a></li>
                <%  } else {    %>
                <script type="text/javascript">
                    $(document).ready(function() {
                        $("form[name='registroUniversidadForm'] input").attr("disabled", "disabled");
                        $("form[name='registroUniversidadForm'] select").attr("disabled", "disabled");
                        $("form[name='registroUniversidadForm'] button").attr("disabled", "disabled");
                    });
                </script>
                <%  }   %>
            </ul>
            <html:form action="/registroUniversidad">
                <span id="responsables">
                    <html:hidden styleId="txtRecId" property="txtRecId"></html:hidden>
                    <html:hidden styleId="txtResId" property="txtResId"></html:hidden>
                    <html:hidden styleId="txtCapId" property="txtCapId"></html:hidden>
                   
                    </span>
                    <span id="cambiarIdUniversidad">
                    <logic:equal name="registroUniversidadForm" property="universidad" value="">
                        <html:text styleId="universidad" property="universidad" value="<%=cveUniversidad%>" ></html:text>
                        <jsp:forward page="/registroUniversidad.msut">
                            <jsp:param name="ask" value="cambiarIdUniversidad"></jsp:param>
                            <jsp:param name="universidad" value="<%=cveUniversidad%>"></jsp:param>
                        </jsp:forward>
                    </logic:equal>
                </span>
                <span id="scr">
                    <logic:equal name="registroUniversidadForm" property="ask" value="sesion">
                        <jsp:forward page="/login.msut">
                            <jsp:param name="error" value="true"></jsp:param>
                            <jsp:param name="ask" value="false"></jsp:param>
                            <jsp:param name="errorMessage" value="Sesi&oacute;n caducada o inv&aacute;lida<br/>El recurso al que deseas acceder est&aacute; restringido, debes iniciar sesi&oacute;n para utilizarlo."></jsp:param>
                        </jsp:forward>
                    </logic:equal>
                </span>
               
                <%--INFORMACIÓN DE LA UNIVERSIDAD--%>
                <div id="t1">
                    <h2><a href="#">Informaci&oacute;n de la Universidad</a></h2>
                    <div class="box">
                        <table align="center" border="0px" cellspacing="12">
                            <tr>
                                <td align="right">
                                    <label>Clave CGUT: </label>
                                </td>
                                <td align="left">
                                    <%=cveCgut%>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <label>Universidad: </label>
                                </td>
                                <td align="left">
                                    <%=universidad%>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <label>RFC: </label><!--<%=rfc%><br/><br/>-->
                                </td>
                                <td align="left">
                                    <html:text styleId="rfc" property="rfc" size="20" ></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <label>Abreviatura: </label>
                                    </td>
                                    <td align="left">
                                    <html:text styleId="abreviatura" property="abreviatura" size="20" ></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <label>Fecha de Creaci&oacute;n: </label>
                                    </td>
                                    <td align="left">
                                    <html:text readonly="true" styleId="fechaCreacion" size="20" property="fechaCreacion"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="center">
                                        No hay m&aacute;s informaci&oacute;n disponible
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <h3><a href="#">Servicios que ofrece la Universidad</a></h3>
                        <div class="box" align="center">
                        <html:select property="lstServiciosSeleccionados" size="16" title="Elige uno o m&aacute;s servicios..." multiple="true">
                            <html:optionsCollection name="registroUniversidadForm" property="servicios" label="name" value="id" />
                        </html:select>
                    </div>
                    <h3><a href="#">Tipos de edificios con los que cuenta la universidad</a></h3>
                    <div class="box" align="center">
                        <html:select property="lstEdificiosSeleccionados" title="Elige uno o m&aacute;s tipos..." multiple="true">
                            <html:optionsCollection name="registroUniversidadForm" property="edificios" label="name" value="id" />
                        </html:select>
                    </div>
                    <%--
                    <h3><a href="#">Organismos acreditadores internacionales</a></h3>
                    <div class="box" align="center">
                        <h4>Por favor verifique que los Organismos de Acreditaci&oacute;n Extranjeros de su Universidad se encuentren en la siguente lista, si no cuenta con ninguno s&oacute;lo marque la casilla de verificaci&oacute;n</h4>
                        <span id="organismosIn">
                            <html:select property="lstOr" styleId="lstOrgInt" multiple="multiple" style="width:auto;">
                                <html:optionsCollection name="registroUniversidadForm" property="lstOrganismosInternacionales" label="name" value="id" />
                            </html:select>
                        </span>
                        <br/><br/>
                        <table align="center" border="1px">
                            <tr>
                                <td>
                                    <table align="center" border="0px" width="500" id="nuevoOrganismoExt">
                                        <tr>
                                            <td>
                                                <label for="sigla">Sigla: </label>
                                                <html:text property="sigla" maxlength="20"/>
                                            </td>
                                            <td>
                                                <label for="nombreOrg">Nombre: </label>
                                                <html:text property="nombreOrg" maxlength="100"/>
                                            </td>
                                            <td>
                                                <label for="pais">Pais: </label>
                                                <html:text property="pais" maxlength="40"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3">
                                                <div align="center">
                                                    <a id="nuevoOrgInt" onclick="retrieveURL('/registroUniversidad.msut?ask=nuevoOrganismo','registroUniversidadForm')" >Agregar un Organismo</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <br/>
                        <html:hidden property="orgInt"/>
                        <input type="checkbox" id="orgInt"/><label for="orgInt">Confirmo que los Organismos necesarios para la evaluaci&oacute;n se encuentran en la lista</label>
                    </div>
                    <h3><a href="#">Certificaciones Independientes</a></h3>
                    <div class="box" align="center">
                        <h4>Por favor verifique que las certificaciones con las que cuenta su Universidad se encuentren en la siguiente lista:</h4>
                        <span id="certificacionesIn">
                            <html:select property="lstCe" styleId="lstCert" multiple="multiple" style="width:auto;">
                                <html:optionsCollection name="registroUniversidadForm" property="lstCertificaciones" label="name" value="id" />
                            </html:select>
                        </span>
                        <br/><br/>
                        <table align="center" border="1px">
                            <tr>
                                <td>
                                    <table align="center" border="0px" width="500" id="nuevaCertificacion">
                                        <tr>
                                            <td>
                                                <label for="nombreOrg">Nombre: </label>
                                                <html:text property="nombreCertificacion" maxlength="50"/>
                                            </td>
                                            <td>
                                                <label for="fechaInicioC">Fecha de Inicio: </label>
                                                <html:text property="fechaInicioC" maxlength="10" readonly="readonly"/>
                                            </td>
                                            <td>
                                                <label for="fechaFinC">Fecha de Término: </label>
                                                <html:text property="fechaFinC" maxlength="10" readonly="readonly"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3">
                                                <div align="center">
                                                    <a id="nuevaCertInd" onclick="retrieveURL('/registroUniversidad.msut?ask=nuevaCertificacion','registroUniversidadForm')" >Agregar una Certificacion</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <br/>
                        <html:hidden property="certUni"/>
                        <input type="checkbox" id="certUni"/><label for="certUni">Confirmo que las Certificaciones necesarias para la evaluaci&oacute;n se encuentran en la lista</label>
                    </div>
                    --%>
                </div>

                <%--PESTAÑA DOMICILIO--%>
                <div id="t2">
                    <h2><a href="#">Domicilio</a></h2>
                    <div class="box">
                        <table align="center" border="0px" width="300">
                            <tr>
                                <td width="120" colspan="2" align="right"><label>Estado:</label></td>
                                <td colspan="2">
                                    <html:select property="cboEstado" styleId="cboEstado">
                                        <html:option value="-1">Elige un Estado...</html:option>
                                        <html:optionsCollection name="registroUniversidadForm" property="estados" label="name" value="id"></html:optionsCollection>
                                    </html:select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Municipio:</label></td>
                                <td colspan="2">
                                    <span id="municipio">
                                        <html:text property="municipio" />
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Colonia:</label></td>
                                <td colspan="2">
                                    <span id="colonia">
                                        <html:text property="colonia" />
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Calle:</label></td>
                                <td colspan="2">
                                    <span id="calle">
                                        <html:text property="calle" />
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Número:</label></td>
                                <td colspan="2">
                                    <span id="numero">
                                        <html:text property="numero" />
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>C.P.:</label></td>
                                <td colspan="2">
                                    <span id="cp">
                                        <html:text maxlength="5" property="cp" />
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <%--DATOS DE CONTACTO--%>
                <div id="t3">
                    <h3><a href="#">Datos del Rector</a></h3>
                    <div align="center" class="box">
                        <table align="center" border="0px" width="400">
                            <tr>
                                <td colspan="2" align="right"><label>Nombre: </label></td>
                                <td colspan="2">
                                    <html:text property="txtRecNombre"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Apellido Paterno: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtRecApaterno"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Apellido Materno: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtRecAmaterno"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Lada: </label></td>
                                    <td colspan="2">
                                    <html:text maxlength="7"  property="txtRecLada"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Teléfono: </label></td>
                                    <td colspan="2">
                                    <html:text maxlength="15" property="txtRecTelefono"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Correo Electrónico: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtRecCorreo"></html:text>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <h3><a href="#">Datos del Responsable</a></h3>
                        <div align="center" class="box">
                            <table align="center" border="0px" width="400">
                                <tr>
                                    <td colspan="2" align="right"><label>Nombre: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtResNombre"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Apellido Paterno: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtResApaterno"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Apellido Materno: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtResAmaterno"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right" ><label>Cargo: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtResCargo" maxlength="50" ></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Lada: </label></td>
                                    <td colspan="2">
                                    <html:text maxlength="7"  property="txtResLada"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Teléfono: </label></td>
                                    <td colspan="2">
                                    <html:text maxlength="15"property="txtResTelefono"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Correo Electrónico: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtResCorreo"></html:text>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <h3><a href="#">Datos del Capturista</a></h3>
                        <div align="center" class="box">
                            <table align="center" border="0px" width="400">
                                <tr>
                                    <td colspan="2" align="right"><label>Nombre: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtCapNombre"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Apellido Paterno: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtCapApaterno"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Apellido Materno: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtCapAmaterno"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"  maxlength="50"><label>Cargo: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtCapCargo"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Lada: </label></td>
                                    <td colspan="2">
                                    <html:text maxlength="7"  property="txtCapLada"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Teléfono: </label></td>
                                    <td colspan="2">
                                    <html:text maxlength="15" property="txtCapTelefono"></html:text>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right"><label>Correo Electrónico: </label></td>
                                    <td colspan="2">
                                    <html:text property="txtCapCorreo"></html:text>
                                    </td>
                                </tr>
                            </table>
                        </div>

                    </div>
                    <div id="t4">
                        <h3><a href="#">Datos Generales</a></h3>
                        <div class="box" name="tabDG">
                            <table align="center" border="0px" width="600">
                                <tr>
                                    <td colspan="2" align="right"><label>N&uacute;mero de alumnos de nuevo ingreso:</label></td>
                                    <td colspan="2">
                                    <html:text title="Valor registrado en la CGUT (No puede ser modificado)" property="alNuevoIngreso" maxlength="6" styleClass="soloNumeros" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>Matricula total:</label></td>
                                <td colspan="2">
                                    <html:text title="Valor registrado en la CGUT (No puede ser modificado)" property="matTotal" maxlength="6" styleClass="soloNumeros" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>N&uacute;mero de profesores de tiempo completo:</label></td>
                                <td colspan="2">
                                    <html:text property="profTC" maxlength="6" styleClass="soloNumeros"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><label>N&uacute;mero de profesores de asignatura:</label></td>
                                <td colspan="2">
                                    <html:text property="profAsign" maxlength="6" styleClass="soloNumeros"/>
                                </td>
                            </tr>
                        </table>
                    </div> 

                    <h3><a href="#">Programas Educativos</a></h3>
                    <div class="box">
                        <table align="center" border="0px" width="600">
                            <tr>
                                <td width="200" colspan="2" align="right">
                                    <label>Nivel:</label>
                                </td>
                                <td colspan="2">
                                    <html:select style="width:auto;" property="cbonivel" onchange="retrieveURL('/registroUniversidad.msut?ask=cboNivel','registroUniversidadForm',idUniSes);" >
                                        <html:option  value="-1">Elige un Nivel...</html:option>
                                        <html:optionsCollection name="registroUniversidadForm" property="niveles" label="name" value="id" />
                                    </html:select>
                                </td>
                            </tr>

                            <tr>
                                <td width="200" colspan="2" align="right"><label>Programa Educativo:</label></td>
                                <td colspan="2">
                                    <span id="educativeP">
                                        <html:select styleId="cboPE" property="cboPE" title="Elige un Programa Educativo...">
                                            <html:option value="-1">Elige un Programa Educativo...</html:option>
                                            <html:optionsCollection name="registroUniversidadForm" property="programasEducativos" label="name" value="id" />
                                        </html:select>
                                    </span>
                                </td>
                            </tr>
                            
                            <tr>
                                <td width="200" colspan="2" align="right"><label>Año de incorporaci&oacute;n a la UT:</label></td>
                                <td colspan="2">
                                    <span id="anioInUT">
                                        <select id="cboAnioInc" style="width: auto;">
                                            <%
                                                ConexionMySQL conexion = new ConexionMySQL();
                                                String consulta = "Select year(fecha_acreditacion) from universidades where id_universidad="
                                                        .concat(sesion.getAttribute("idUniversidad").toString());
                                                ResultSet rs = conexion.Consultar(consulta);
                                                consulta = "Select anio from periodos where activo = 1";
                                                ResultSet rs2 = conexion.Consultar(consulta);
                                                rs.next();
                                                rs2.next();
                                                for (int i = rs.getInt(1); i <= rs2.getInt(1); i++) {
                                            %>
                                                 <option value="<%=i%>"><%=i%></option>
                                            <%  }   %>
                                        </select>
                                    </span>
                                </td>
                            </tr>
                             <tr>
                                <td width="200" colspan="2" align="right">
                                    <label>Clasificación:</label>
                                </td>
                                <td colspan="2">
                                    <html:select styleId="cboclasificacionPE" style="width:auto;" property="cboclasificacionPE"  >
                                        <html:option  value="-1">Elige una Clasificaci&oacute;n...</html:option>
                                        <html:option  value="0">Tecnol&oacute;gica Bis</html:option>
                                        <html:option  value="1">Tecnol&oacute;gica No Bis</html:option>
                                    </html:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="200" colspan="2" align="right">
                                    <label>Alumnos de nuevo ingreso:</label>
                                </td>
                                <td colspan="2">
                                     <html:text property="nuevo_ingresoPE" maxlength="6" styleClass="soloNumeros"/>
                                </td>
                            </tr>
                            <tr>
                                <td width="200" colspan="2" align="right">
                                    <label>Matricula total:</label>
                                </td>
                                <td colspan="2">
                                     <html:text property="matricula_totalPE" maxlength="6" styleClass="soloNumeros"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <div align="center">
                                        <a class="botonAgregar" id="add">Agregar</a>
                                        <a class="botonQuitar" id="remove">Quitar</a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <label>Programas Educativos Seleccionados:</label>
                                    <div align="center">
                                        <html:select property="lstPe" styleId="lstSeleccionados" multiple="multiple" style="width:auto;" onchange="$('#lstAniosInc').prop('selectedIndex',$('#lstSeleccionados').prop('selectedIndex'));
                                                     $('#lstNuevoIngreso').prop('selectedIndex',$('#lstSeleccionados').prop('selectedIndex')); $('#lstMatriculaTotal').prop('selectedIndex',$('#lstSeleccionados').prop('selectedIndex')); $('#lstClasificacion').prop('selectedIndex',$('#lstSeleccionados').prop('selectedIndex'));">
                                            <html:optionsCollection name="registroUniversidadForm" property="peSeleccionados" label="name" value="id" />
                                        </html:select>
                                        <html:select property="lstAi" styleId="lstAniosInc" multiple="multiple" style="display: none;">
                                            <html:optionsCollection name="registroUniversidadForm" property="aniosIncSeleccionados" label="name" value="id" />
                                        </html:select>
                                        <html:select property="lstNuev" styleId="lstNuevoIngreso" multiple="multiple" style="display: none;">
                                            <html:optionsCollection name="registroUniversidadForm" property="nuevoingresoSeleccionados" label="name" value="id" />
                                        </html:select>
                                        <html:select property="lstMat" styleId="lstMatriculaTotal" multiple="multiple" style="display: none;">
                                            <html:optionsCollection name="registroUniversidadForm" property="matriculatotalSeleccionados" label="name" value="id" />
                                        </html:select>
                                        <html:select property="lstClas" styleId="lstClasificacion" multiple="multiple" style="display: none;">
                                            <html:optionsCollection name="registroUniversidadForm" property="clasificacionSeleccionados" label="name" value="id" />
                                        </html:select>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <h3><a href="#">Modalidad de impartici&oacute;n de Programas Educativos</a></h3>
                    <div class="box">
                        <table id="tablaModelos" align="center" width="70%">
                        </table>
                    </div>

                </div>
                <input type="hidden" id="indActual" value="universidad"/>
                <html:hidden name="registroUniversidadForm" property="seleccionados"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="lstAniosInc"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="lstNuevoIngreso"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="lstMatriculaTotal"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="lstClasificacion"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="modelos"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="modSeleccionados"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="servSeleccionados"></html:hidden>
                <html:hidden name="registroUniversidadForm" property="edifSeleccionados"></html:hidden>
            </html:form>
            <%
                if (nocompletos == "true") {
            %>
            <script type="text/javascript">
                
                $().alerta('showToast', {
                    text     : 'No se puede iniciar la evaluaci&oacute;n ya que los datos no est&aacute;n completos, verifique los datos y guardelos antes de dar click en Iniciar Evaluaci&oacute;n"',
                    sticky   : false,
                    position : 'top-right',
                    type     : 'error',
                    stayTime    : 12000
                });
                validarFormulario(false);
            </script>
            <%}%>

            <script type="text/javascript">
                $(function() {
                    $( "#fechaCreacion" ).datepicker({
                        showOn: "button",
                        minDate: new Date(1990, 1 - 1, 1),
                        changeMonth: true,
                        changeYear: true,
                        dateFormat:"yy-mm-dd",
                        buttonImage: "images/calendar.gif",
                        buttonImageOnly: true
                    });
                    
                });
                
                $(function() {
                    $( "*" ).tooltip();
                    $( "#radNotifi" ).buttonset();
                    checkCookie();
                });
                
            </script>
        </div>

        <div style="width: 1px" id="dialog-message" title="Notificaciones Importantes">
              <p align="justify" style="color: tomato;">
                <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 0 0 0;"></span>
                Comentarios: <br/>
                Es necesario que se realice un documento con los comentarios para el sistema y se envíe a stapia@nube.sep.gob.mx, consuelo.romero@nube.sep.gob.mx, salome.cedillo@nube.sep.gob.mx y armandoc.ceja@nube.sep.gob.mx
            </p>
            <hr />
            <p align="justify" style="color: tomato;">
                <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 0 0 0;"></span>
                Navegador:<br/>
                Es necesario utilizar Google Chrome o Mozilla Firefox para el correcto funcionamiento del sistema.
            </p>
            <hr />
            <p align="justify" style="color: tomato;">
                <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 0 0 0;"></span>
                Gu&iacute;a del Sistema:<br/>
                Asegurese de haber le&iacute;do la gu&iacute;a del sistema antes de iniciar con la captura de informaci&oacute;n.
                <a style="cursor: pointer; color: #09F;" onclick="window.open('/descargarGuia','_blank');">Descargar Gu&iacute;a</a>
            </p>
            <hr />
            <p align="justify">
                <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 0 0 0;"></span>
                Responsabilidad de los datos:<br/>
                Los datos capturados son responsabilidad de las Universidades Tecnológicas para cualquier fin legal o de seguimiento.
            </p>
            <hr />
            <p  align="justify">
                <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 0 0 0;"></span>
                Tiempo de sesi&oacute;n: <br/>
                El tiempo l&iacute;mite de inactividad es de 20 min. pasado este per&iacute;odo, la sesi&oacute;n caducar&aacute; y tendrá que iniciar sesi&oacute;n nuevamente ocasionando la p&eacute;rdida de los datos no guardados.
            </p>
            <hr />
            <p align="justify">
                <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 0 0 0;"></span>
                Llenado de Indicadores: <br/>
                Se recomienda que un indicador no sea llenado por más de un usuario al mismo tiempo.
            </p>
            <hr />
            <div id="radNotifi">
                ¿Desea volver a visualizar éste mensaje? &nbsp;<input type="radio" id="radNot1" name="radio" checked="checked"><label for="radNot1">Si</label>
                <input type="radio" id="radNot2" name="radio" ><label for="radNot2">No</label>
            </div>

        </div>

    </tiles:put>
</tiles:insert>
