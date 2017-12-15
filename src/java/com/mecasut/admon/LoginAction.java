/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Joshua, Cuauhtemoc Medina MuÃ±oz
 */
public class LoginAction extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String ADMIN = "admin";
    private static final String FAILURE = "failure";
    private static final String ERRORBD = "errorbd";
    private String idresponsable = "";
    private String idUsuario = "";
    private String tiempo_Ini = "";
    private boolean act_unis=true;

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoginForm formbean = (LoginForm) form;
        //Devuelve el tipo de usuario que trata de ingresar
        if (formbean.getAsk().equals("recuperar")) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("tipoUsuario").equals("CG")) {
                return mapping.findForward(ADMIN);
            }
            return mapping.findForward(SUCCESS);
        }

        if (formbean.getAsk().equals("login")) {
            boolean entra = false;
            String user = formbean.getUser();
            String password = formbean.getPassword();
            int universidad = formbean.getUniversidad();
           
            int captcha = formbean.getCaptcha().length();

            String remoteAddr = request.getRemoteAddr();
            ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
            reCaptcha.setPrivateKey("6LfiySgTAAAAAAeXgpXoyxz_7xcB-jN6vpggFFy3");
            String challenge = request.getParameter("recaptcha_challenge_field");
            String uresponse = request.getParameter("recaptcha_response_field");
            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
            String tipo;
            if (reCaptchaResponse.isValid()) {
                entra = true;
                tipo= ValidarUsuario(user, password, universidad);
            } else {
                formbean.setError("true");
                formbean.setErrorMessage("Por favor verifica que el Captcha esté correctamente validado.");
               
                formbean.setCaptcha("");
                return mapping.findForward(FAILURE);
            }
            if (tipo.equals("bderror")) {
                return mapping.findForward(ERRORBD);
            }
            if ((user == null) || password == null || user.equals("") || password.equals("")) {
                formbean.setError("true");
                formbean.setErrorMessage("Por favor verifica que los datos ingresados sean correctos y vuelve a intentarlo.");
                return mapping.findForward(FAILURE);
            } else if (tipo.equals("desactivado")) {
                //el usuario ha sido desactivado
                formbean.setError("true");
                formbean.setErrorMessage("Por favor verifica que los datos ingresados sean correctos y vuelve a intentarlo.");
                return mapping.findForward(FAILURE);

            } else if (tipo.equals("CG")) {/*
                 * USUARIO CGUT EN CGUT
                 */
                formbean.setError("false");
                HttpSession sesion = request.getSession(true);
                sesion.setAttribute("usuario", user);
                sesion.setAttribute("idUniversidad", universidad);
                sesion.setAttribute("tipoUsuario", tipo);
                sesion.setAttribute("idPeriodo", formbean.getPeriodoActual());
                sesion.setAttribute("idResponsable", idresponsable);
                sesion.setAttribute("idUsuario", idUsuario);
                sesion.setAttribute("tiempo_Inicial", expirarSesion());
                sesion.setMaxInactiveInterval(500);
                return mapping.findForward(ADMIN);
            } else if (tipo.equals("CGenUT")) {/*
                 * USUARIO CGUT EN UT
                 */
                formbean.setError("false");
                HttpSession sesion = request.getSession(true);
                sesion.setAttribute("usuario", user);
                sesion.setAttribute("idUniversidad", universidad);
                sesion.setAttribute("tipoUsuario", tipo);
                sesion.setAttribute("idPeriodo", formbean.getPeriodoActual());
                sesion.setAttribute("idResponsable", idresponsable);
                sesion.setAttribute("idUsuario", idUsuario);
                sesion.setAttribute("tiempo_Inicial", expirarSesion());
                sesion.setMaxInactiveInterval(500);
                //registroUniversidadForm.setUniversidad(universidad);
                return mapping.findForward(SUCCESS);
            } else if (tipo.equals("UT")) {/*
                 * USUARIO CGUT EN UT
                 */
               act_unis=DeshabilitadoaUnis();
                if(!act_unis){
                    formbean.setError("true");
                    formbean.setErrorMessage("Sistema deshabilitado para las universidades");
                    return mapping.findForward(FAILURE);
                }else{
                    formbean.setError("false");
                    HttpSession sesion = request.getSession(true);
                    sesion.setAttribute("usuario", user);
                    sesion.setAttribute("idUniversidad", universidad);
                    sesion.setAttribute("tipoUsuario", tipo);
                    sesion.setAttribute("idPeriodo", formbean.getPeriodoActual());
                    sesion.setAttribute("idResponsable", idresponsable);
                    sesion.setAttribute("idUsuario", idUsuario);
                    sesion.setAttribute("tiempo_Inicial", expirarSesion());
                    sesion.setMaxInactiveInterval(500);
                    //registroUniversidadForm.setUniversidad(universidad);
                    return mapping.findForward(SUCCESS);
                }
            } else {
                formbean.setError("true");
                formbean.setErrorMessage("Favor de verificar los datos </br> El nombre de usuario y/o contraseña son incorrectos, o el usuario no está registrado en la universidad seleccionada.");
                return mapping.findForward(FAILURE);
            }
        } else {
            return mapping.findForward(FAILURE);
        }
    }

      public boolean DeshabilitadoaUnis() throws SQLException {
          boolean activo=false;
        try {
             ConexionMySQL conexion = new ConexionMySQL();
                if (!conexion.Conectar()) {
                    activo = false;
                }
                String consulta = "SELECT activo_unis from system_mecasut" ;
                ResultSet rs = conexion.Consultar(consulta);
                if (rs.next()) { //EXISTE EL USUARIO
                   if(rs.getInt("activo_unis")==1){
                       activo= true;
                   }else{
                       activo= false;
                   }
                }
            
        } catch (Exception ex) {
            System.err.println("ERROR LOGIN: \n");
            System.err.println(ex.getMessage());
            activo=false;
        }
        return activo;
    }
    
    public String ValidarUsuario(String user, String pass, int universidad) throws SQLException {
        try {
            if (universidad == -1) {
                System.out.println("LOGINERROR: Error de compatibilidad de usuario y contraseña o universidad: Usuario: ".concat(user).concat(" Universidad: ").concat(String.valueOf(universidad)));
                return "error";
            } else {
                ConexionMySQL conexion = new ConexionMySQL();
                if (!conexion.Conectar()) {
                    return "bderror";
                }
                String consulta = "SELECT id_usuario, id_responsable, id_univ, user, password, tipo, activo FROM usuarios WHERE user='".concat(user).concat("'");
                //System.out.println(consulta);
                ResultSet rs = conexion.Consultar(consulta);
                if (rs.next()) { //EXISTE EL USUARIO
                    if (rs.getString("tipo").equals("CG")) {//USUARIO CGUT 
                        if (rs.getBoolean("activo")) {//EL USUARIO ESTÃ� ACTIVO
                            if (universidad == 0) {//USUARIO CGUT QUIERE ENTRAR A LA INTERFAZ DE ADMINITRACIÃ“N
                                if (pass.equals(rs.getString("password"))) {//CONTRASEÃ‘A CORRECTA
                                    System.out.println("LOGININFO: Conectado satisfactoriamente Usuario CGUT MECASUT: ".concat(user).concat(" en la Interfaz de Administración de la CGUT "));
                                    idresponsable = rs.getString("id_responsable");
                                    idUsuario = rs.getString("id_usuario");
                                    return "CG";
                                } else {//CONTRASEÃ‘A INCORRECTA
                                    System.out.println("LOGINERROR: Contraseña incorrecta para el Usuario CGUT MECASUT: ".concat(user));
                                    return "error";
                                }
                            } else {//USUARIO CGUT QUIERE ENTRAR A UNA UT
                                if (pass.equals(rs.getString("password"))) {//CONTRASEÃ‘A CORRECTA
                                    System.out.println("LOGININFO: Conectado satisfactoriamente Usuario CGUT MECASUT : ".concat(user).concat(" en la universidad: ").concat(String.valueOf(universidad)));
                                    idresponsable = rs.getString("id_responsable");
                                    idUsuario = rs.getString("id_usuario");
                                    return "CGenUT";
                                } else {//CONTRASEÃ‘A INCORRECTA
                                    System.out.println("LOGINERROR: Contraseña incorrecta para el Usuario CGUT MECASUT: ".concat(user));
                                    return "error";
                                }
                            }
                        } else {//el usuario estÃ¡ inactivo
                            System.out.println("LOGININFO: Intento de conexion con usuario desactivado Usuario CGUT MECASUT: ".concat(user));
                            return "desactivado";
                        }
                    } else { //USUARIO UT en UT
                        if (rs.getBoolean("activo")) {//EL USUARIO ESTÃ� ACTIVO
                            if (universidad == rs.getInt("id_univ")) {//VALIDANDO QUE EL USUARIO ESTE REGISTRADO CON ESA UNIV
                                if (pass.equals(rs.getString("password"))) {//CONTRASEÃ‘A CORRECTA
                                    System.out.println("LOGININFO: Conectado satisfactoriamente Usuario UT MECASUT: ".concat(user).concat(" en la Universidad ").concat(String.valueOf(universidad)));
                                    idresponsable = rs.getString("id_responsable");
                                    idUsuario = rs.getString("id_usuario");
                                    return "UT";
                                } else {//CONTRASEÃ‘A INCORRECTA
                                    System.out.println("LOGINERROR: Error de compatibilidad de usuario y contraseña o universidad MECASUT: Usuario: ".concat(user).concat(" Universidad: ").concat(String.valueOf(universidad)));
                                    return "error";
                                }
                            } else {//USUARIO NO REGISTRADO EN LA UT
                                System.out.println("LOGINERROR: Error de compatibilidad de usuario y contraseña o universidad MECASUT: Usuario: ".concat(user).concat(" Universidad: ").concat(String.valueOf(universidad)));
                                return "error";
                            }
                        } else {//el usuario estÃ¡ inactivo
                            System.out.println("LOGININFO: Intento de conexion con usuario desactivado Usuario UT MECASUT: ".concat(user).concat(" en Universidad:").concat(String.valueOf(universidad)));
                            return "desactivado";
                        }
                    }
                } else {//NO EXISTE EL USUARIO
                    System.out.println("LOGINERROR: No existe el usuario Usuario: ".concat(user));
                    return "error";
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR LOGIN: \n");
            System.err.println(ex.getMessage());
            return "error";
        }
    }

    public String expirarSesion() {
        Date hora_inicio = new Date();
        SimpleDateFormat tiempo = new SimpleDateFormat("hh:mm");
        tiempo.format(hora_inicio);
        this.tiempo_Ini = tiempo.format(hora_inicio);
        return tiempo.format(hora_inicio);
    }
}
