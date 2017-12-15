<%-- 
    Document   : CgutMantenimientoProgreso
    Created on : 13/01/2014, 10:31:51 AM
    Author     : Cuauhtemoc Medina Mu�oz
    Actualizaci�n 2016: Salvador Zamora Arias
--%>
<%@page import="com.mecasut.reportes.sabana.ListaUniversidades"%>
<%@page import="com.mecasut.conexion.ConexionMySQL"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>

<%
    HttpSession sesion = request.getSession(false);
    int idPeriodo = Integer.parseInt(sesion.getAttribute("idPeriodo").toString());
    ResultSet rs = null;
    ResultSet rs2 = null;
    ConexionMySQL con = new ConexionMySQL();
    String consulta="", consulta2="";

    ListaUniversidades univs = new ListaUniversidades(String.valueOf(idPeriodo));
    int avance = 0 ;
    Float suma= (float) 0;
    Integer [] categorias = new Integer[5];
    int indicadores=0;
    try {
        for (int i=1; i<=5;i++){
            consulta="SELECT count(id_categoria) as 'no' FROM system_indicadores WHERE id_categoria="
                    .concat(String.valueOf(i));
            rs = con.Consultar(consulta);
            if (rs.next()){
                indicadores+=rs.getInt("no");
                categorias[i-1]=rs.getInt("no");
            }
        }
%>

<table align="center" style="border: 0; width: 100%">
    <table id="tabla-progreso" style="width: 100%;" boder="1">
        <thead>
            <tr>
                <th scope="col" class="rounded-company"></th>
                <th class="rounded-q1" colspan="<%=indicadores+2%>"></th>
                <th  class="rounded-q4"></th>
            </tr>
            <tr>
                <th scope="col" rowspan="2" class="rounded-q1">No.</th>
                <th class="rounded-q1" rowspan="2">Universidad</th>
                <th class="rounded-q1" colspan="<%=categorias[0]%>">Eficacia</th>
                <th class="rounded-q1" colspan="<%=categorias[1]%>">Eficiencia</th>
                <th class="rounded-q1" colspan="<%=categorias[2]%>">Pertinencia</th>
                <th class="rounded-q1" colspan="<%=categorias[3]%>">Vinculaci&oacute;n</th>
                <th class="rounded-q1" colspan="<%=categorias[4]%>">Equidad</th>
                <th class="rounded-q1" rowspan="2" style="writing-mode: tb-rl; filter: flipV flipH;">Finalizado</th>
                <th class="rounded-q1" rowspan="2" colspan="<%=categorias[4]%>">Porcentaje</th>
            </tr>
            <tr>
                <%  for(int i=1; i<=indicadores;i++){   %>
                        <td class="td1" align="center"><%=i%></td>
                <%  }   %>
            </tr>
        </thead>
        <%  for (int unis=0;unis<univs.size();unis++){
                if (unis%30==0 && unis!=0){ //Encabezados
        %>
                    <tr>
                        <th scope="col" rowspan="2" class="rounded-q1">No.</th>
                        <th rowspan="2" class="rounded-q1">Universidad</th>
                        <th class="rounded-q1" colspan="<%=categorias[0]%>">Eficacia</th>
                        <th class="rounded-q1" colspan="<%=categorias[1]%>">Eficiencia</th>
                        <th class="rounded-q1" colspan="<%=categorias[2]%>">Pertinencia</th>
                        <th class="rounded-q1" colspan="<%=categorias[3]%>">Vinculaci&oacute;n</th>
                        <th class="rounded-q1" colspan="<%=categorias[4]%>">Equidad</th>
                        <th class="rounded-q1" rowspan="2" style="writing-mode: tb-rl; filter: flipV flipH;">Finalizado</th>
                        <th class="rounded-q1" rowspan="2" colspan="<%=categorias[4]%>">Porcentaje</th>
                    </tr>
                    <tr>
                        <%  for(int i=1; i<=indicadores;i++){   %>
                        <td class="td1" align="center"><%=i%></td>
                        <%  }   %>
                    </tr>
        <%     }    %>
            <tr>
                <%  if(unis==0 || unis%2==0){ //nombre de las universidades %>
                        <td class="td2" align="center"><%=unis+1%></td>
                        <td class="td2" align="center" style="text-align: left; width: fit-content;">
                            <%=univs.getNombre(unis).substring(24)%>
                        </td>
                <% }else{   %>
                        <td class="td1" align="center"><%=unis+1%></td>
                        <td class="td1" align="center" style="text-align: left; width: fit-content;">
                            <%=univs.getNombre(unis).substring(24)%>
                        </td>
                <% }
                    consulta = "call sp_ProgresoUniversidades('"
                        .concat(String.valueOf(univs.getId(unis))).concat("','")
                        .concat(String.valueOf(idPeriodo)).concat("')");
                    rs = con.Consultar(consulta);
                    rs.next();
                    suma = (float) 0;
                    for(int i=1; i<=indicadores;i++){
                        if (i==12 || i== 17 || i==33){ //Valores Fijos, por lo cual se pinta una estrella
                            if (unis==0 || unis%2==0){//Filas azules
                %>
                            <td class="td2" align="center" style="color: #000;">&starf;</td>
                <%         }else{//Filas de otro color  %>
                            <td align="center" class="td1" style="color: #000;">&starf;</td>
                <%         }
                       }else{
                            if (rs.getInt("ind"+i)!=0){
                                suma++;
                                if (unis==0 || unis%2==0){//Filas azules
                %>
                                    <td align="center" class="td2" style="color: darkgreen;">&#10004;</td>
                <%              }else{  %>
                                    <td align="center" class="td1" style="color: darkgreen;">&#10004;</td>
                <%              }
                            }else{
                                if (unis==0 || unis%2==0){//Filas azules
                %>
                                    <td align="center" class="td2" style="color: #B32B2B;">&#10008;</td>
                <%              }else{  %>
                                    <td align="center" class="td1" style="color: #B32B2B;">&#10008;</td>
                <%
                                }
                            }
                       }
                        if (i==indicadores){
                            /*FINALIZADO ?*/
                            rs2 = null;
                            consulta2 = "SELECT mecasut_terminado FROM control_universidad WHERE id_universidad="
                            .concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=")
                            .concat(String.valueOf(idPeriodo));
                            rs2 = con.Consultar(consulta2);
                            if (rs2.next()){
                                if (rs2.getInt("mecasut_terminado")==0){
                                    if (unis == 0 || unis%2==0){
                            %>
                                        <td align="center" class="td2" style="color: #B32B2B;">&#10008;</td>
                            <%
                                    }else{
                            %>
                                        <td align="center" class="td1" style="color: #B32B2B;">&#10008;</td>
                            <%
                                    }
                                }else{
                                    if (unis==0 || unis%2==0){//Filas azules
                            %>
                                        <td align="center" class="td2" style="color: darkgreen;">&#10004;</td>
                            <%      }else{  %>
                                        <td align="center" class="td1" style="color: darkgreen;">&#10004;</td>
                            <%      }
                                }
                            }else{
                                  if (unis == 0 || unis%2==0){
                            %>
                                        <td align="center" class="td2" style="color: #B32B2B;">&#10008;</td>
                            <%      }else{  %>
                                        <td align="center" class="td1" style="color: #B32B2B;">&#10008;</td>
                            <%
                                    }      
                            }
                            /*FINALIZADO ?*/
                            avance = (int) (suma*100/(indicadores-3));
                            if (unis==0 || unis%2==0){//Filas azules
                %>
                                <td class="td2"><%=avance%> %</td>
                <%          }else{  %>
                                <td class="td1"><%=avance%> %</td>
                <%          }
                        }
                   }
                %>
            </tr>
        <% }    %>
        <tfoot>
            <tr>
                <td class="rounded-foot-left"></td>
                <td class="td1" colspan="<%=indicadores+2%>" class="rounded-q1" style="text-align: justify;">
                    Nota: El progreso mostrado en porcentaje es relativo, un indicador 
                    ser� indicado en la tabla con &#10004;, cuando la universidad ha realizado 
                    al menos un guardado, de lo contrario se marca con &#10008;. La &starf; 
                    muestra los indicadores que son llenado autom&aacute;ticamente.
                </td>
                <td class="rounded-foot-right"></td>
            </tr>
        </tfoot>
    </table>
</table>
<%            
    } catch (Exception ex) {
        System.err.println("SQLError CgutUnivesidadesProgreso.jsp " + ex);
    } finally {
        rs.close();
        rs = null;
        con.Desconectar();
    }
%>