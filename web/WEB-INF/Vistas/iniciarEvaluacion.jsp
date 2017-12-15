<%--
    Document   : iniciarEvaluacion
    Created on : Oct 26, 2012, 3:41:08 PM
    Author     : Joshua Luévano
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.CallableStatement"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@page import="com.mecasut.admon.*, com.mecasut.shared.Universidad"%>
<%@page import="java.sql.ResultSet, com.mecasut.conexion.ConexionMySQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    String cveUniversidad, cveEstado = "", estado = "", municipio = "", calle = "", colonia = "",
            numero = "", cp = "", cvePeriodo, universidad = "", rector = "", contacto = "", captura = "",
            contactoT = "", capturaT = "", telUniversidad = "", mailUniversidad = "";
    Universidad uni = new Universidad();
    try {
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("usuario") == null) {
%>
<jsp:forward page="/login.msut">
    <jsp:param name="error" value="true"></jsp:param>
    <jsp:param name="ask" value="false"></jsp:param>
    <jsp:param name="errorMessage" value="El recurso al que deseas acceder está restringido, debes iniciar sesión para utilizarlo."></jsp:param>
</jsp:forward>
<%            }
    int iniciado = 0, terminado = 0;
    cveUniversidad = String.valueOf(sesion.getAttribute("idUniversidad"));
    universidad = uni.getNombreUniversidad(cveUniversidad);
    cvePeriodo = sesion.getAttribute("idPeriodo").toString();
    ConexionMySQL conexion = new ConexionMySQL();
    String consulta = "Select mecasut_iniciado, mecasut_terminado from control_universidad where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo=").concat(cvePeriodo);

    ResultSet rs = conexion.Consultar(consulta);
    if (rs.next()) {
        iniciado = rs.getInt(1);
        terminado = rs.getInt(2);
        session.setAttribute("iniciado", iniciado);
        session.setAttribute("terminado", terminado);
    } else {
        iniciado = 0;
        terminado = 0;
    }

    CallableStatement sp = conexion.PrepararSP("sp_ValidarInicio(?, ?, ?, ?, ?, ?, ?, ?)");
    sp.setInt(1, Integer.parseInt(cveUniversidad));
    sp.setInt(2, Integer.parseInt(cvePeriodo));
    sp.registerOutParameter(3, java.sql.Types.INTEGER);
    sp.registerOutParameter(4, java.sql.Types.INTEGER);
    sp.registerOutParameter(5, java.sql.Types.INTEGER);
    sp.registerOutParameter(6, java.sql.Types.INTEGER);
    sp.registerOutParameter(7, java.sql.Types.INTEGER);
    sp.registerOutParameter(8, java.sql.Types.INTEGER);
   // sp.registerOutParameter(9, java.sql.Types.INTEGER);
    sp.execute();
    int servicios = sp.getInt(3);
    int edificios = sp.getInt(4);
    consulta = "Select * from domicilios_universidad where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo=").concat(cvePeriodo).concat(" and activo = 1");

    rs = conexion.Consultar(consulta);
    if (rs.next()) {
        cveEstado = rs.getString("id_estado");
        municipio = rs.getString("municipio");
        colonia = rs.getString("colonia");
        calle = rs.getString("calle");
        numero = rs.getString("numero");
        cp = rs.getString("codigo_postal");
    }
    consulta = "Select nombre from estados where id_estado = ".concat(cveEstado);

    rs = conexion.Consultar(consulta);
    if (rs.next()) {
        estado = rs.getString("nombre");
    }
    consulta = "Select nombre, apaterno, amaterno, tipo, cargo, telefono, mail from responsables_universidad ru inner join responsables r on ru.id_responsable = r.id_responsable where ru.id_universidad = ".concat(cveUniversidad).concat(" and ru.id_periodo = ").concat(String.valueOf(cvePeriodo));
    rs = conexion.Consultar(consulta);
    while (rs.next()) {
        if (rs.getString("tipo").equals("RU")) {
            rector = rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno"));
            telUniversidad = rs.getString("telefono");
            mailUniversidad = rs.getString("mail");
        }
        if (rs.getString("tipo").equals("RC")) {
            contacto = rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno")).concat("<br/>Cargo: ").concat(rs.getString("cargo"));
            contactoT = "Tel&eacute;fono: ".concat(rs.getString("telefono")).concat("<br/>E-Mail: ").concat(rs.getString("mail"));
        }
        if (rs.getString("tipo").equals("CU")) {
            captura = rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno")).concat("<br/>Cargo: ").concat(rs.getString("cargo"));
            capturaT = "Tel&eacute;fono: ".concat(rs.getString("telefono")).concat("<br/>E-Mail: ").concat(rs.getString("mail"));
        }
        if (rs.getString("tipo").equals("CS")) {
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="images/favicon.gif" type="image/x-icon" />
        <title>Verificar informaci&oacute;n para comenzar la evaluaci&oacute;n</title>
        <link rel="stylesheet" type="text/css" href="css/stylesheet.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery.toastmessage.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="css/tip-yellowsimple.css" />
        <script language="javascript" type="text/javascript" src="js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="js/jquery.ui.js"></script>
        <script language="javascript" type="text/javascript" src="js/jquery.toastmessage.js"></script>
        <script language="javascript" type="text/javascript" src="js/jquery.poshytip.js"></script>
        <script language="javascript" type="text/javascript" src="js/iniciarEvaluacion.js"></script>
        <script language="javascript" type="text/javascript">
            $(document).ready(function() {
                $("input[type='checkbox']").button();
            });
        </script>
        <style>
            h2{
                color:#1b48a7; 
                letter-spacing: 1px;
            }
            ul{
                list-style-type: none;
                max-width: 500px;
                font-weight: bolder;
            }
            ul li{
                padding: 7px;
                border-radius: 4px;
            }
            ul.normal{
                color: #8d4a1e;
            }
            ul.normal li:nth-child(even){
                background: #ffbb8e;
            }
            ul.normal li:nth-child(odd){
                background: #ffd8bf;
            }
            ul.revisado{
                color: #399;
            }
            ul.revisado li:nth-child(even){
                background: #d0e9ff;
            }
            ul.revisado li:nth-child(odd){
                background: #fff;
            }
        </style>
    </head>
    <body>
        <div align="center" id="dialog-cargando" title="Cargando..." style="z-index :1000">
            <img alt="MECASUT" src="images/ajax-loader.gif" />
            <br/>Por favor espere...<br/><br/>
        </div>
        <script type="text/javascript">
            cargando();
        </script>
        <%
            if ((iniciado == 1) && (terminado == 0)) {
        %>
        <jsp:forward page="/global.msut">
            <jsp:param name="ask" value="eficacia"/>
        </jsp:forward>
        <%                       } else {

        %>
        <div id="dialog-confirm" title="¿Seguro que desea iniciar la evaluaci&oacute;n?" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Si hace clic en "Iniciar Ahora" los datos se guardar&aacute;n y ya no podr&aacute;n ser modificados. <br/><br/> ¿Desea iniciar la evaluaci&oacute;n ahora?</p>
        </div>
        <img alt="MECASUT" src="images/mecasut.png"/>
        <img style="position: absolute; right: 15px; top: 0px;"  alt="MECASUT" src="images/Logo-UTyVll.png" align="right" />
        
        <!--Comienza Menú de Sesión-->
        <%

            String sql = "Select * from responsables where id_responsable = ".concat(sesion.getAttribute("idResponsable").toString());
            rs = conexion.Consultar(sql);
            rs.next();
            String nombre = rs.getString("nombre"), apellidos = rs.getString("apaterno") + " " + rs.getString("amaterno"), cargo = rs.getString("cargo"), mail = rs.getString("mail");
        %>
        <div align="right">
            <div style="height: 25px; width: 230px; position: inherit; top: 128px;">
                <div class="sesionMenu">
                    <table width="230px">
                        <tr>
                            <td>
                                <div class="bienvenido">
                                    <span>Bienvenido</span>
                                </div>
                            </td>
                            <td align="right" class="red">
                                <a href="registroUniversidad.msut?ask=logout">
                                    <span style="float:right;">Cerrar Sesi&oacute;n</span>
                                    <span class="ui-icon ui-icon-circle-close" style="float:right;"></span>
                                </a>
                            </td>
                        </tr>
                    </table>
                    <div class="nombre" ><%=nombre%><!--span class="blue" style="float:right;">Ver Perfil</span--></div>
                    <div class="apellidos" ><%=apellidos%><span class="blue">&nbsp;&nbsp;(<%=sesion.getAttribute("usuario")%>)</span></div>
                </div>
            </div>
        </div><!--Termina Menú de Sesión-->
        <!--Encabezado-->
        <div style="position: absolute; top: 139px; z-index :98" >
            <h1>Informaci&oacute;n actual correspondiente a la Universidad</h1>
        </div><!--Termina Encabezado-->
        <br/><br/><br/>
        <p style="text-align: center; color: #FF3333; font-weight: bolder;">Por favor verifique que la informaci&oacute;n mostrada a continuaci&oacute;n es correcta, <br/>
            Si encuentra errores o datos faltantes, haga clic en bot&oacute;n 'Corregir informaci&oacute;n' caso contrario, puede continuar con la evaluaci&oacute;n.
        </p>
        <div id="informacionCompleta">
            <!--Servicios-->
            <div id="divServicios" align="center">
                <br/>
                <%if (servicios == 1) {%>
                <h2>La <%=universidad%> solo ofrece el siguiente servicio:</h2>
                <ul class="normal">
                    <%
                        consulta = "Select es.nombre, es.descripcion from servicios_universidad su inner join encuesta_servicios es on su.id_servicio = es.id_servicio where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo = ").concat(cvePeriodo);
                        ResultSet rsServicios = conexion.Consultar(consulta);
                        while (rsServicios.next()) {%>
                    <li title="<%=rsServicios.getString("descripcion")%>"><%=rsServicios.getString("nombre")%></li>
                    <%}%>
                </ul>
                <%} else if (servicios == 0) {%>
                La <%=universidad%> no tiene ning&uacute;n servicio registrado
                <%} else if (servicios > 1) {%>
                <h2>Los servicios que ofrece la <%=universidad%> a la comunidad estudiantil son <%=servicios%>, los cuales se listan continuaci&oacute;n:</h2>
                <ul class="normal">
                    <%
                        consulta = "Select es.nombre, es.descripcion from servicios_universidad su inner join encuesta_servicios es on su.id_servicio = es.id_servicio where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo = ").concat(cvePeriodo);
                        ResultSet rsServicios = conexion.Consultar(consulta);
                        while (rsServicios.next()) {%>
                    <li title="<%=rsServicios.getString("descripcion")%>"><%=rsServicios.getString("nombre")%></li>
                    <%}%>
                </ul>
                <%}%>
                <input type="checkbox" id="check1" onclick="$('#divServicios ul').toggleClass('revisado');"/><label for="check1">Verificado</label>
            </div>
            <!--Edificios-->
            <div id="divEdificios" align="center">
                <br/>
                <%if (edificios == 1) {%>
                <h2>La <%=universidad%> solo cuenta con el siguiente tipo de edificio:</h2>
                <ul class="normal">
                    <%
                        consulta = "Select e.descripcion, e.capacidad from edificios_universidad eu inner join edificios e on eu.id_edificio = e.id_edificio where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo = ").concat(cvePeriodo);
                        ResultSet rsEdificios = conexion.Consultar(consulta);
                        while (rsEdificios.next()) {%>
                    <li title="Edificio con capacidad para <%=rsEdificios.getString("capacidad")%> personas"><%=rsEdificios.getString("descripcion")%></li>
                    <%}%>
                </ul>
                <%} else if (edificios == 0) {%>
                La <%=universidad%> no tiene ning&uacute;n edificio registrado
                <%} else if (edificios > 1) {%>
                <h2>La <%=universidad%> cuenta con <%=edificios%> tipos de edificios, los cuales se listan continuaci&oacute;n:</h2>
                <ul class="normal">
                    <%
                        consulta = "Select e.descripcion, e.capacidad from edificios_universidad eu inner join edificios e on eu.id_edificio = e.id_edificio where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo = ").concat(cvePeriodo);
                        ResultSet rsEdificios = conexion.Consultar(consulta);
                        while (rsEdificios.next()) {%>
                    <li title="Edificio con capacidad para <%=rsEdificios.getString("capacidad")%> personas"><%=rsEdificios.getString("descripcion")%></li>
                    <%}%>
                </ul>
                <%}%>
                <input type="checkbox" id="check2" onclick="$('#divEdificios ul').toggleClass('revisado');"/><label for="check2">Verificado</label>
            </div>
               
            <!--Domicilio-->
            <div id="divDomicilio" align="center">
                <br/>
                <h2>La <%=universidad%> tiene como domicilio actual:</h2>
                <ul class="normal" style="max-width: 1000px;">
                    <li title="Domicilio actual registrado"><%=calle%> <%=numero%>, <%=colonia%>, <%=municipio%>, <%=estado%>, C.P. <%=cp%></li>
                </ul>
                <input type="checkbox" id="check3" onclick="$('#divDomicilio ul').toggleClass('revisado');"/><label for="check3">Verificado</label>
            </div>
            <!--Datos de Contacto-->
            <div id="divContacto" align="center">
                <br/>
                <h2>Datos de contacto de la <%=universidad%>:</h2>
                <ul class="normal" style="max-width: 1000px;">
                    <li title="Datos de la Universidad">
                        Rector: <%=rector%><br/>
                        <%
                            String telpartido[]=telUniversidad.split("#");
                            if(telpartido.length>1){
                        %>
                            Lada: <%=telpartido[0]%>        Tel&eacute;fono: <%=telpartido[1]%><br/>
                        <% }else{ %>
                            Tel&eacute;fono: <%=telUniversidad%><br/>
                        <% } %>
                        
                        
                        E-Mail: <%=mailUniversidad%>
                    </li>
                    <li title="<%=contactoT%>">
                        Persona de Contacto: <%=contacto%>
                    </li>
                    <li title="<%=capturaT%>">
                        Responsable de Captura: <%=captura%>
                    </li>
                </ul>
                <input type="checkbox" id="check4" onclick="$('#divContacto ul').toggleClass('revisado');"/><label for="check4">Verificado</label>
            </div>

            <!--Datos Academicos Generales-->
            <div id="divAcademicos" align="center">
                <%
                    ResultSet rsAcad = conexion.Consultar("Select * from datos_universidad where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo = ").concat(String.valueOf(cvePeriodo)));
                    if (rsAcad.next()) {
                %>                
                <br/>
                <h2>Datos Acad&eacute;micos Generales:</h2>
                <ul class="normal" style="max-width: 700px;">
                    <li title="Alumnos de nuevo ingreso registrados">
                        N&uacute;mero de alumnos de nuevo ingreso: <%=rsAcad.getString(3)%>
                    </li>
                    <li title="Matricula registrada en la Universidad">
                        Matricula total: <%=rsAcad.getString(4)%>
                    </li>
                    <li title="Profesores de tiempo completo trabajando actualmente">
                        N&uacute;mero de profesores de tiempo completo: <%=rsAcad.getString(5)%>
                    </li>
                    <li title="Profesores de asignatura trabajando actualmente">
                        N&uacute;mero de profesores de asignatura: <%=rsAcad.getString(6)%>
                    </li>
                </ul>
                <input type="checkbox" id="check5" onclick="$('#divAcademicos ul').toggleClass('revisado');"/><label for="check5">Verificado</label>
                <%
                    }
                %>
            </div>
            <!--Niveles Modelos y Programas-->
            <%
                consulta = "Select distinct(pe.id_nivel), n.descripcion, n.nombre from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo = ").concat(cvePeriodo).concat(") order by pe.id_nivel");

                ResultSet rsNivel = conexion.Consultar(consulta);
                while (rsNivel.next()) {
            %>
            <div id="divProgramas<%=rsNivel.getString(1)%>" align="center">
                <br/>
                <h2>Informaci&oacute;n y programas educativos que se ofrecen del nivel <%=rsNivel.getString("n.descripcion")%>:</h2>
                <ul class="normal" style="max-width: 700px;">
                    <h3>Modelos de impartici&oacute;n de clases: </h3>
                    <li title="Modelos de impartici&oacute;n de clases del nivel <%=rsNivel.getString(3)%>">
                        <%
                            consulta = "Select mu.id_modelo, m.descripcion from modelos_universidad mu inner join modelos m on mu.id_modelo = m.id_modelo where mu.id_universidad = ".concat(cveUniversidad).concat(" and mu.id_periodo = ").concat(cvePeriodo).concat(" and mu.id_nivel = ").concat(rsNivel.getString(1));
                            ResultSet rsModelo = conexion.Consultar(consulta);
                            int c = 1;
                            while (rsModelo.next()) {
                        %>
                        <%=c%>. <%=rsModelo.getString(2)%><br/>
                        <%  c++;
                            }
                        %>
                    </li>
                    <h3>Programas educativos:</h3>
                    <li title="Programas educativos que se ofrecen para el nivel <%=rsNivel.getString(3)%>">
                        <%
                            consulta = "select pu.id_pe, nombre_programa from programa_educativo pe inner join pe_universidad pu on pe.id_pe = pu.id_pe where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo = ").concat(cvePeriodo).concat(" and id_nivel=").concat(rsNivel.getString(1));
                            ResultSet rsProgEdu = conexion.Consultar(consulta);
                            c = 1;
                            while (rsProgEdu.next()) {
                        %>
                        <%=c%>. <%=rsProgEdu.getString(2)%><br/>
                        <% c++;
                            }
                        %>
                    </li>
                </ul>
                <input type="checkbox" id="check6<%=rsNivel.getString(1)%>" onclick="$('#divProgramas<%=rsNivel.getString(1)%> ul').toggleClass('revisado');"/><label for="check6<%=rsNivel.getString(1)%>">Verificado</label>
            </div>
            <%
                }
            %>
        </div>          
        <br/>
        <div align="center">
            <button name="btnRegresar" onclick="window.location.href = 'global.msut?ask=universidad';">Corregir informaci&oacute;n</button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button name="btnRegresar" onclick="EnviarPDF();">Guardar PDF</button>
            <button name="btnEnviar" disabled="disabled" onclick="$( '#dialog-confirm' ).dialog( 'open' );">Comenzar Evaluaci&oacute;n</button>
        </div>
        <input type="hidden" id="indActual" value="iniciar"/>
        <%                       }%>
        <div style="filter:alpha(opacity=50); opacity:0.5;position: fixed; bottom: 10px; left: 10px" align="left">
            <div>MECASUT Version 1.228.1241 &alpha;lfa 2</div>
            <div>Prueba Piloto 1.1.1241 Revisi&oacute;n 228.25102012</div>
        </div>
        <div style="display: none;" id="copyright">Copyright &copy; 2012 <a href="http://www.apycom.com">Apycom jQuery Menus</a></div>
    </body>
</html>
<%
} catch (NullPointerException exNull) {
%>
<jsp:forward page="/login.msut">
    <jsp:param name="error" value="true"></jsp:param>
    <jsp:param name="ask" value="false"></jsp:param>
    <jsp:param name="errorMessage" value="El recurso al que deseas acceder está restringido, debes iniciar sesión para utilizarlo."></jsp:param>
</jsp:forward>
<%            } catch (SQLException ex) {
        System.err.println("Error en Iniciar Evaluacion jsp: "+ex.getMessage());
    }

%>