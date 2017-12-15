<%-- 
    Document   : Enviarcorreo
    Created on : 20/01/2017, 09:30:59 AM
    Author     : Salvador Zamora Arias
--%>

<%@page import="utilerias.Archivos"%>
<%@page import="com.mecasut.shared.Universidad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utilerias.SendMail" %>

<%  if (request.getParameter("archivo")!=null){
        try{
        //SE RECUPERAN LOS VALORES
            String base64=(String)request.getParameter("archivo");
            base64=((String)request.getParameter("archivo")).replaceAll(" ", "");;
            String Universidad=(String)request.getParameter("universidad");
            String Indicador=(String)request.getParameter("indicador");
            String Extension=(String)request.getParameter("extension");
            int IdUni=Integer.parseInt(request.getParameter("IdUni"));
            int IdPer=Integer.parseInt(request.getParameter("IdPer"));
        //SE ENVIA EL CORREO CON LA CLASE MAL
            SendMail sm=new SendMail( "Correo enviado desde el sistema MECASUT ", base64, Universidad, Indicador, Extension);
            String respuesta =sm.SendMailArchivo();
            if(respuesta.equals("Si")){
                Archivos ar= new Archivos(IdUni, IdPer, Integer.parseInt(Indicador));
                ar.RegistrarArchivo(); 
            }
            out.print(respuesta);
        }catch(Exception e){
            System.err.println("Error Enviarcorreo jsp"+e.getMessage());
        }
    }
              
%>