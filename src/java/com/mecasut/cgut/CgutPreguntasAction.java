/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 *Actualización 2016: Salvador Zamora Arias
 */
public class CgutPreguntasAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    ConexionMySQL conexion = new ConexionMySQL();
    private static final String SUCCESS = "success";

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

        CgutPreguntasForm frm = (CgutPreguntasForm) form;
        if (frm.getAsk().equals("buscarPregEncuesta")) {
            if (frm.getCmbPregEnc() > 0) { //si el elemento seleccionado es mayor al inicial
                if (frm.getCmbPregEnc() == 3) {//llenar el combo de categorias
                    frm.setCategoPre2(llenarDatos("SELECT id_servicio, nombre FROM encuesta_servicios"));
                    frm.setTxtNombrePreguntas("");
                }
                if (frm.getCmbPregEnc() != 3) { //llenar el combo de preguntas segun la encuesta seleccionada
                    String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
                            + "WHERE id_encuesta='".concat(String.valueOf(frm.getCmbPregEnc())).concat("'");
                    frm.setPreguntas(llenarDatos(consulta));
                    frm.setTxtNombrePreguntas("");
                }
            } else { //el elemento seleccionado es el inicial
                nuevaPregunta(frm); //reiniciar los valores del formulario
            }
            return mapping.findForward(SUCCESS);
        }

        frm.setCategoPre2(llenarDatos("SELECT id_servicio, nombre FROM encuesta_servicios"));
        if (frm.getAsk().equals("buscaCategoPre2")) {
            ArrayList resultados = new ArrayList();
            if (frm.getCboCategoPre2() > 0) {
                String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
                        + "WHERE id_servicio=".concat(String.valueOf(frm.getCboCategoPre2()))
                        .concat(" AND id_encuesta=").concat(String.valueOf(String.valueOf(frm.getCmbPregEnc())));
                frm.setPreguntas(llenarDatos((consulta)));
            } else { //si el item seleccionado es el inicial, entonces el llenado de las preguntas es vacio
                frm.setPreguntas(resultados); //
            }
            return mapping.findForward(SUCCESS);
        }

        //------------------------------------------------------------------------------------------------------
        if (frm.getCmbPregEnc() == 3) {
            String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
                    + "WHERE id_servicio=".concat(String.valueOf(frm.getCboCategoPre2()))
                    .concat(" AND id_encuesta=").concat(String.valueOf(String.valueOf(frm.getCmbPregEnc())));
            frm.setPreguntas(llenarDatos((consulta)));
        } else {
            String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
                    + "WHERE id_encuesta='".concat(String.valueOf(frm.getCmbPregEnc())).concat("'");
            frm.setPreguntas(llenarDatos(consulta));
        }
        //------------------------------------------------------------------------------------------------------
        if (frm.getAsk().equals("buscarPregPregunta")) {
            if (frm.getCmbPregPreguntas() > 0) {
                try {
                    //Selecciona de la base de datos la pregunta y si estÃƒÂ¡ activo
                    String sql = "SELECT pregunta, activo, nombre_pregunta FROM encuesta_preguntas WHERE id_pregunta="
                            .concat(String.valueOf(String.valueOf(frm.getCmbPregPreguntas())));
                    ResultSet rs = conexion.Consultar(sql);
                    if (rs.next()) {
                        frm.setTxtNombrePreguntas(rs.getString("pregunta"));//asgina la pregunta al campo
                        frm.setTxtPalabraClavePreguntas(rs.getString("nombre_pregunta"));
                        if (rs.getBoolean("activo")) {//cambia el boton (activar/desactivar)
                            frm.setPreguntasActiva("Desactivar Pregunta");
                        } else {
                            frm.setPreguntasActiva("Activar Pregunta");
                        }
                    }
                } catch (SQLException ex) {
                    System.err.println("ERROR BD: Buscando preguntas segun la encuesta "+ex.getMessage());
                } finally {
                    conexion.Desconectar();
                    return mapping.findForward(SUCCESS);
                }
            } else {
                frm.setTxtNombrePreguntas("");
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            }
        }

        if (frm.getAsk().equals("bajaPreguntas")) {
            if (frm.getCmbPregPreguntas() > 0) {
                String sql = "select activo from  encuesta_preguntas where id_pregunta = ".concat(String.valueOf(frm.getCmbPregPreguntas()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        String sql2 = "update encuesta_preguntas set activo = 0 where id_pregunta = ".concat(String.valueOf(frm.getCmbPregPreguntas()));
                        conexion.Modificar(sql2);
                        conexion.Desconectar();
                        frm.setPreguntasActiva("Activar Pregunta");
                        return mapping.findForward(SUCCESS);
                    } else {
                        String sql3 = "update encuesta_preguntas set activo = 1 where id_pregunta = ".concat(String.valueOf(frm.getCmbPregPreguntas()));
                        conexion.Modificar(sql3);
                        conexion.Desconectar();
                        frm.setPreguntasActiva("Desactivar Pregunta");
                        return mapping.findForward(SUCCESS);
                    }
                }
            }
        }



        if (frm.getAsk().equals("guardarPregunta")) {
            try {
                boolean nuevo = false;
                boolean conCateg = false;
                if (frm.getCmbPregPreguntas() == -1) {//nuevo registro
                    nuevo = true;
                    if (frm.getCmbPregEnc() == 3) { // con categorias
                        conCateg = true;
                    }
                }
                String consulta = "";
                if (nuevo) { //nuevo registro
                    if (conCateg) { //con categorias
                        consulta = "INSERT INTO encuesta_preguntas (id_encuesta, id_servicio, pregunta, activo, nombre_pregunta) VALUES ('"
                                .concat(String.valueOf(frm.getCmbPregEnc())).concat("', '")
                                .concat(String.valueOf(frm.getCboCategoPre2())).concat("', '")
                                .concat(frm.getTxtNombrePreguntas()).concat("', 1,'")
                                .concat(frm.getTxtPalabraClavePreguntas()).concat("')");

                    } else {//nuevo sin categorias
                        consulta = "INSERT INTO encuesta_preguntas (id_encuesta, pregunta, activo, nombre_pregunta) VALUES ('"
                                .concat(String.valueOf(frm.getCmbPregEnc())).concat("', '")
                                .concat(frm.getTxtNombrePreguntas()).concat("', 1,'")
                                .concat(frm.getTxtPalabraClavePreguntas()).concat("')");
                    }
                    conexion.Insertar(consulta);
                    conexion.Desconectar();
                    nuevaPregunta(frm);
                } else {//actualizacion
                    consulta = "UPDATE encuesta_preguntas SET "
                            .concat("pregunta='").concat(frm.getTxtNombrePreguntas())
                            .concat("', nombre_pregunta='".concat(String.valueOf(frm.getTxtPalabraClavePreguntas())).concat("' ")
                            .concat("WHERE id_pregunta='").concat(String.valueOf(frm.getCmbPregPreguntas())))
                            .concat("'");
                    //System.err.println(consulta);
                    conexion.Modificar(consulta);
                    conexion.Desconectar();
                    nuevaPregunta(frm);
                }
                return mapping.findForward(SUCCESS);
            } catch (SQLException ex) {
                System.err.println("ERROR BD: actualizando o insertando preguntas en la base de datos: " +ex.getMessage());
                nuevaPregunta(frm);
            } finally {
                conexion.Desconectar();
            }
        }

        if (frm.getAsk().equals("nuevaPregunta")) {
            nuevaPregunta(frm);
            return mapping.findForward(SUCCESS);
        }
        
        
        return mapping.findForward(SUCCESS);
    }

    private void nuevaPregunta(CgutPreguntasForm frm) {
        frm.setCmbPregEnc(-1);
        frm.setCmbPregPreguntas(-1);
        frm.setTxtNombrePreguntas("");
        frm.setTxtPalabraClavePreguntas("");
        frm.setCboCategoPre2(-1);
    }
    
    private ArrayList llenarDatos(String consulta) {
        ArrayList campos = new ArrayList();
        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar(consulta);
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: LLenar datos de CguPreguntasAction "+ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        return campos;
    }
    
}