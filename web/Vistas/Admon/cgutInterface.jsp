<%--
    Document   : cgutInterface
    Created on : 27/02/2012, 11:55:59 AM
    Author     : Irvin Joshua Lu�vano Garc�a, Cuauhtemoc Medina Mu�oz
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page import="java.sql.SQLException"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%  HttpSession sesion = request.getSession();
    String id_periodo = "", id_user = "";
    String anio = "";
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
        ConexionMySQL con = new ConexionMySQL();
        String sql = "select anio from periodos where id_Periodo=".concat(id_periodo);
        ResultSet rs0 = con.Consultar(sql);
        if (rs0.next()) {
            anio = rs0.getString("anio");
        }
    }
%>
<tiles:insert template="/Vistas/Master.jsp">
    <tiles:put name="title" type="string">
        MECASUT - CGUT
    </tiles:put>
    <tiles:put name="scripts" type="string">
        <script language="javascript" type="text/javascript" src="js/mostrarFecha.js"></script>
        <script language="javascript" type="text/javascript" src="js/cgut.js"></script>
    </tiles:put>
    <tiles:put name="body" type="string">
        <section>
            <br/>
            <table width="100%" height="100%">
                <tr>
                    <td width="15%" >
                        <p class="separar"style="text-align: left;">Periodo Activo <br><%=anio%> - <%=Integer.parseInt(anio)+1%></p>
                    </td>
                    <td width="47%" align="center">
                        <h1 style="text-align: center;">Interfaz - Administrador CGUT</h1>
                        <p style="text-align: center;">Bienvenido Coordinador General.</p>
                    </td>
                    <td width="15%" align="center">
                    </td>
                </tr>
            </table>
            <div id="CGUTmenu" class="usual">
                <ul>
                    <li id="aUni"><a id="aUni" href="#tabUni"
                           onclick="cargarArchivos('CgutUniversidad', 'DivContenidoUni', 'Universidades','aUni');">Universidades</a></li>
                    <li id="aEgresados"><a id="aEgresados" onclick="cargarArchivos('CgutEgresados', 'DivContenidoEgresados', 'Egresados','aEgresados');" href="#tabEgresados"
                           >Cantidad de Alumnos</a></li>
                    <li id="aProg"><a id="aProg" href="#tabProg" onclick="cargarArchivos('CgutAreas', 'DivContenidoAreas', '�reas','aProg');">Programas Educativos</a></li>
                    <li id="aCau"><a id="aCau" href="#tabCau"
                           onclick="cargarArchivos('CgutCausas', 'DivContenidoCausas', 'Causas de Deserci�n','aCau');">Causas de Deserci�n</a></li>

                    <li id="aServicios"><a id="aServicios" href="#tabServicios"
                           onclick="cargarArchivos('CgutServicios', 'DivContenidoServicios', 'Servicios','aServicios');">Servicios</a></li>
                    <li id="aEnc"><a id="aEnc" href="#tabEnc" 
                           onclick="cargarArchivos('CgutPreguntas', 'DivContenidoPreguntas', 'Preguntas','aEnc');">Encuestas y Preguntas</a></li>
                    <li id="aMod"><a id="aMod" href="#tabMod"
                           onclick="cargarArchivos('CgutModelos', 'DivContenidoModelos', 'Modelos','aMod');">Modalidad</a></li>
                    <li id="aprogE"><a id="aprogE" href="#tabProgE"
                           onclick="cargarArchivos('CgutNiveles', 'DivContenidoNiveles', 'Niveles','aprogE');">Niveles</a></li>
                    <li id="aCert" ><a id="aCert" href="#tabCert" onclick="cargarArchivos('CgutCertificaciones', 'DivContenidoCertificaciones', 'Certificaciones','aCert');">Certificaciones</a></li>
                    <li id="aOrgan"><a id="aOrgan" href="#tabOrganismos" 
                           onclick="cargarArchivos('CgutOrganismos', 'DivContenidoOrganismos', 'Organismos Acreditadores','aOrgan');">Organismos Acreditadores </a>
                    </li>                    
                    <li id="aEdif"><a id="aEdif" href="#tabEdificios"
                           onclick="cargarArchivos('CgutEdificios', 'DivContenidoEdificios', 'Edificios','aEdif');">Edificios</a></li>
                    <li id="aResp"><a id="aResp" href="#tabResp" onclick="cargarArchivos('CgutResponsables', 'DivContenidoResponsables', 'Responsables','aResp');">Responsables</a></li>
                    <li id="aUsu"><a id="aUsu" href="#tabUsu"
                           onclick="cargarArchivos('CgutUsuarios', 'DivContenidoUsuarios', 'Usuarios','aUsu');">Usuarios</a></li>
                    <li id="aMant"><a id="aMant" href="#tabMant" onclick="cargarArchivos('CgutMantenimientoMecasut', 'DivContenidoMecasut', 'Mantenimiento','aMant');">Mantenimiento</a></li>
                    <li id="aProgreso"><a id="aUsu" href="#tabProgreso"
                           onclick="cargarArchivos('CgutUniversidadesProgreso', 'DivContenidoProgreso', 'Progreso','aProgreso');">Progreso</a></li>
                           
                </ul>

                <%--Universidad--%>
                <div id="tabUni">
                    <ul>
                        <li><a href="#DivContenidoUni" >&nbsp;&nbsp;&nbsp;Universidad</a></li>
                    </ul>
                    <div style="alignment-adjust: central" id="DivContenidoUni" class="box"></div>
                </div>
                <%--Fin de la Universidad--%>

                <%--Cantidad de Alumnos--%>
                <div id="tabEgresados">
                    <ul>
                        <li><a href="#DivContenidoEgresados" onclick="cargarArchivos('CgutEgresados', 'DivContenidoEgresados', 'Egresados','aEgresados');">&nbsp;&nbsp;&nbsp;Egresados de Bachillerato</a></li>
                    </ul>
                    <div id="DivContenidoEgresados" class="box"></div>
                    <ul>
                        <li><a href="#DivContenidonuevoIngreso" onclick="cargarArchivos('CgutNuevoIngreso', 'DivContenidonuevoIngreso', 'Nuevo Ingreso','aEgresados');">&nbsp;&nbsp;&nbsp;Alumnos de Nuevo Ingreso</a></li>
                    </ul>
                    <div id="DivContenidonuevoIngreso" class="box"></div>
                    <ul>
                        <li><a href="#DivContenidoMatriculaTotal" onclick="cargarArchivos('CgutMatriculaTotal', 'DivContenidoMatriculaTotal', 'Matricula Total','aEgresados');">&nbsp;&nbsp;&nbsp;Matricula total</a></li>
                    </ul>
                    <div id="DivContenidoMatriculaTotal" class="box"></div>
                </div>
                <%--Fin Cantidad de Alumnos--%>

                <%--Programas Educativos--%>
                <div id="tabProg">
                    <%-- Empieza el men� para �reas --%>
                    <ul>
                        <li><a href="#DivContenidoAreas" onclick="cargarArchivos('CgutAreas', 'DivContenidoAreas', '�reas','aProg');/*verificarPest();*/">&nbsp;&nbsp;&nbsp;&Aacute;reas</a></li>
                    </ul>
                    <div id="DivContenidoAreas" class="box"></div>

                    <%-- Empieza el men� para familias--%>
                    <ul>
                        <li><a href="#DivContenidoFamilias" onclick="cargarArchivos('CgutFamilias', 'DivContenidoFamilias', 'Familias','aProg');/*verificarPest();*/" >&nbsp;&nbsp;&nbsp;Familias</a></li> </ul>
                    <div id="DivContenidoFamilias" class="box"></div>

                    <%-- Empieza el men� para programas educativos --%>
                    <ul><li><a href="#DivContenidoProgramas" onclick="cargarArchivos('CgutProgramas', 'DivContenidoProgramas', 'Programas Eucativos','aProg');/*verificarPest();*/">&nbsp;&nbsp;&nbsp;Programas</a></li> </ul>
                    <div id="DivContenidoProgramas" class="box"></div>
                </div>
                <%--Fin de programas educativos--%>

                <%--Causas Desercion--%>
                <div id="tabCau">
                    <ul>
                        <li><a href="#DivContenidoCausas">&nbsp;&nbsp;&nbsp;Causas</a></li>
                    </ul>
                    <div id="DivContenidoCausas" class="box"></div>
                </div>
                <%--Fin Causas Desercion--%>

                <%--Servicios--%>
                <div id="tabServicios">
                    <ul>
                        <li><a href="#DivContenidoServicios">&nbsp;&nbsp;&nbsp;Servicios</a></li>
                    </ul>
                    <div id="DivContenidoServicios" class="box"></div>
                </div>
                <%--Fin Servicios--%>


                <%--Encuestas--%>
                <div id="tabEnc">
                    <ul>
                        <li><a href="#DivContenidoPreguntas">&nbsp;&nbsp;&nbsp;Preguntas</a></li>
                    </ul>
                    <div id="DivContenidoPreguntas" class="box"></div>
                </div>
                <%--Fin Preguntas--%>

                <%--Modelos--%>
                <div id="tabMod">
                    <ul>
                        <li><a href="#DivContenidoModelos" >&nbsp;&nbsp;&nbsp;Modelos</a></li>
                    </ul>
                    <div id="DivContenidoModelos" class="box"></div>
                </div>
                <%--Fin Modelos--%>

                <%--Niveles PE--%>
                <div id="tabProgE">
                    <ul>
                        <li><a href="#DivContenidoProgramasE" >&nbsp;&nbsp;&nbsp;Niveles</a></li>
                    </ul>
                    <div id="DivContenidoNiveles" class="box"></div>
                </div>
                <%--Fin Niveles PE--%>
                <%--Certificaciones--%>
                <div id="tabCert">
                    <ul>
                        <li><a href="#DivContenidoCert" >&nbsp;&nbsp;&nbsp;Certificaciones</a></li>
                    </ul>
                    <div id="DivContenidoCertificaciones" class="box">
                        <%--Cargado de Form--%>
                    </div>
                </div>
                <%--Fin Certificaciones--%>
                <%--Menu Organismos--%>
                <div id="tabOrganismos">
                    <ul>
                        <li><a href="#DivContenidoOrganismos" >&nbsp;&nbsp;&nbsp;Certificaciones</a></li>
                    </ul>
                    <div id="DivContenidoOrganismos" class="box"></div>
                </div>
                <%--Menu Organismos--%>

                <%--Edificios--%>
                <div id="tabEdificios">
                    <ul>
                        <li><a href="#DivContenidoEdificios" >&nbsp;&nbsp;&nbsp;Edificios</a></li>
                    </ul>
                    <div id="DivContenidoEdificios" class="box"></div>
                </div>
                <%--Fin Edificios--%>
                <%--Responsables--%>
                <div id="tabResp">
                    <ul>
                        <li><a href="#DivContenidoResponsables" >&nbsp;&nbsp;&nbsp;Responsables</a></li>
                    </ul>
                    <div id="DivContenidoResponsables" class="box"></div>
                </div>
                <%--Fin Responsables--%>

                <%--Gestion de Usuarios--%>
                <div id="tabUsu">
                    <ul>
                        <li><a href="#DivContenidoUsuariosCGUT" >&nbsp;&nbsp;&nbsp;Usuarios </a></li>
                    </ul>
                    <div id="DivContenidoUsuarios" class="box"></div>
                </div>
                <%--Fin Gestion de Usuarios--%>

                <%--Mantenimiento--%>
                <div id="tabMant">
                    <ul>
                        <li><a href="#DivContenidoMecasut" onclick="cargarArchivos('CgutMantenimientoMecasut', 'DivContenidoMecasut', 'Mantenimiento Mecasut','aMant');">&nbsp;&nbsp;&nbsp;Mecasut</a></li>
                    </ul>
                    <div id="DivContenidoMecasut" class="box"></div>

                    <ul>
                        <li><a href="#DivContenidoUttes" onclick="cargarArchivos('CgutMantenimientoUniversidad', 'DivContenidoUttes', 'Mantenimiento Universidades','aMant');">&nbsp;&nbsp;&nbsp;Universidades</a></li>
                    </ul>
                   
                    <div id="DivContenidoUttes" class="box"></div>
                    
                    
                </div>
                <%--Fin Mantenimiento--%>
                
                <%--Gestion de Usuarios--%>
                <div id="tabProgreso">
                    <ul>
                        <li><a href="#DivContenidoProgreso" >&nbsp;&nbsp;&nbsp;Progreso Universidades </a></li>
                    </ul>
                    <div id="DivContenidoProgreso"></div>
                </div>
                <%--Fin Gestion de Usuarios--%>
            </div>                   
        </section>
        <script type="text/javascript">
            $(document).ready(function() {               
                cargarArchivos('CgutUniversidad', 'DivContenidoUni', 'Universidades');
                
                $("#CGUTmenu").tabs();                
                $("section > div > div").accordion({
                    icons: {
                        "header": "ui-icon-arrowreturnthick-1-s", 
                        "headerSelected": "ui-icon-arrowreturnthick-1-n",
                        heightStyle: "content"
                    },
                    collapsible: true,
                    autoHeight: false,
                    navigation: true                   
                });                
            });
        </script>
    </tiles:put>
</tiles:insert>