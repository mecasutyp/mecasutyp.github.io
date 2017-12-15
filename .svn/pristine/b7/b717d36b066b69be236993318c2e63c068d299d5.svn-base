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
 */
public class CgutUsuariosAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    ConexionMySQL conexion = new ConexionMySQL();
    private static final String SUCCESS = "success";
    private String ultimoQuery = "";

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

        CgutUsuariosForm frm = (CgutUsuariosForm) form;

        if (frm.getAsk().equals("buscaUsuariosResponsables")) {
            if (frm.getCboUniUsuarios() == -2) {
                frm.setNombreUsu("");
                frm.setPassword("");
                frm.setConfirmacion("");
                frm.setModificarPassword(false);
                frm.setListaUsuarios(new ArrayList());
            }
            if (frm.getCboUniUsuarios() == 0) {
                frm.setModificarPassword(false);
                String query = "SELECT id_responsable, CONCAT(nombre,' ', apaterno,' ',amaterno)as nombre FROM responsables WHERE "
                        .concat(" id_universidad=0");
                frm.setColeccionResponsables(llenarDatos(query));
                frm.setNombreUsu("");
                frm.setPassword("");
                frm.setConfirmacion("");
                frm.setListaUsuarios(new ArrayList());
            } else {
                frm.setVisible(true);
                try {
                    String datosUsuario = "SELECT * FROM usuarios WHERE id_univ=".concat(String.valueOf(frm.getCboUniUsuarios()));
                    ResultSet res = conexion.Consultar(datosUsuario);
                    if (res.next()) {
                        frm.setNombreUsu(res.getString("user"));
                        ultimoQuery = "SELECT id_usuario, user FROM usuarios WHERE user!='"
                                .concat(res.getString("user")).concat("'");
                        frm.setListaUsuarios(llenarDatos(ultimoQuery));
                    } else {
                        frm.setNombreUsu("");
                        frm.setPassword("");
                        frm.setConfirmacion("");
                        frm.setCboResUsu(-1);
                        frm.setModificarPassword(true);
                        ultimoQuery = "SELECT id_usuario, user FROM usuarios ";
                        frm.setListaUsuarios(llenarDatos(ultimoQuery));
                    }
                } catch (SQLException ex) {
                    System.err.println("ERROR BD: Error en la consulta de los datos correspondientes al usuario de la universidad");
                    System.err.println(ex.getMessage());
                } finally {
                    conexion.Desconectar();
                    return mapping.findForward(SUCCESS);
                }
            }
        }

        if (frm.getAsk().equals("cargarUsuarioCGUT")) {
            /*PARA NO PERDER EL VALOR DEL COMBO*/
            String query = "SELECT id_responsable, CONCAT(nombre,' ', apaterno,' ',amaterno)as nombre FROM responsables WHERE "
                    .concat(" id_universidad=0");
            frm.setColeccionResponsables(llenarDatos(query));
            /*FIN PARA NO PERDER EL VALOR DEL COMBO*/
            try {
                if (frm.getCboResUsu() != -2) {
                    String datosUsuario = "SELECT * FROM usuarios WHERE id_responsable=".concat(String.valueOf(frm.getCboResUsu()))
                            .concat(" AND id_univ=0");
                    ResultSet res = conexion.Consultar(datosUsuario);
                    frm.setVisible(true);
                    if (res.next()) {
                        frm.setNombreUsu(res.getString("user"));
                        ultimoQuery = "SELECT id_usuario, user FROM usuarios WHERE user!='"
                                .concat(res.getString("user")).concat("'");
                        frm.setListaUsuarios(llenarDatos(ultimoQuery));
                    } else {
                        ultimoQuery = "SELECT id_usuario, user FROM usuarios ";
                        frm.setListaUsuarios(llenarDatos(ultimoQuery));
                        frm.setNombreUsu("");
                        frm.setPassword("");
                        frm.setConfirmacion("");
                        frm.setModificarPassword(true);
                    }
                } else {
                    frm.setNombreUsu("");
                    frm.setModificarPassword(false);
                    frm.setVisible(false);
                }
            } catch (SQLException ex) {
                System.err.println("ERROR BD: Error en la consulta de los datos correspondientes al usuario de la universidad");
                System.err.println(ex.getMessage());
            } finally {
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            }
        }

        if (frm.getAsk().equals("nuevoUsuario")) {
            nuevoUsuario(frm);
            return mapping.findForward(SUCCESS);
        }

        if (frm.getAsk().equals("guardarUsuario")) {
            if (frm.getCboResUsu() == 0) { //usuarios de la UT
                try {
                    //eliminar usuario correspondiente a la universidad
                    String sql2 = "UPDATE usuarios SET user='".concat(frm.getNombreUsu()).concat("', password='")
                        .concat(frm.getPassword()).concat("' WHERE id_univ=").concat(String.valueOf(frm.getCboUniUsuarios()));
                    int resp = conexion.Modificar(sql2);
                    conexion.Desconectar();
                    if (resp == 0) {//No hubo ninguna modificación, por lo tanto se debe de realizar una inserción
                        //agregar el nuevo usuario
                        sql2 = "INSERT into usuarios values(null,'0','".concat(String.valueOf(frm.getCboUniUsuarios())).concat("','")
                                .concat(frm.getNombreUsu()).concat("','")
                                .concat(frm.getPassword()).concat("','UT','1')");
                        conexion.Insertar(sql2);
                        conexion.Desconectar();
                    }
                    nuevoUsuario(frm);
                    return mapping.findForward(SUCCESS);
                } catch (Exception ex) {
                    System.err.println("ERRORBD: " + ex.getMessage());
                    return mapping.findForward(SUCCESS);
                } finally {
                    return mapping.findForward(SUCCESS);
                }
            } else { //usuarios CGUT
                try {
                    //eliminar usuario correspondiente a la universidad
                    String sql2 = "DELETE FROM usuarios WHERE id_responsable=".concat(String.valueOf(frm.getCboResUsu()))
                            .concat(" AND id_univ=0");
                    conexion.Eliminar(sql2);
                    sql2 = "INSERT INTO usuarios VALUES(null,".concat(String.valueOf(frm.getCboResUsu())).concat(",'0','")
                            .concat(frm.getNombreUsu()).concat("','")
                            .concat(frm.getPassword()).concat("','CG','1')");
                    conexion.Insertar(sql2);
                    conexion.Desconectar();
                    nuevoUsuario(frm);
                    return mapping.findForward(SUCCESS);
                } catch (Exception ex) {
                    System.err.println("ERRORBD: " + ex.getMessage());
                    return mapping.findForward(SUCCESS);
                } finally {
                    return mapping.findForward(SUCCESS);
                }
            }
        }

        if (frm.getAsk().equals("cambiarPass")) {
            /*PARA NO PERDER EL VALOR DEL COMBO*/
            String query = "SELECT id_responsable, CONCAT(nombre,' ', apaterno,' ',amaterno)as nombre FROM responsables WHERE "
                    .concat(" id_universidad=0");
            frm.setColeccionResponsables(llenarDatos(query));
            frm.setListaUsuarios(llenarDatos(ultimoQuery));
            /*FIN PARA NO PERDER EL VALOR DEL COMBO*/
            frm.setModificarPassword(true);
            frm.setVisible(true);
        }

        if (frm.getAsk().equals("cancelarCambiarPass")) {
            frm.setModificarPassword(false);
        }

        return mapping.findForward(SUCCESS);
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
            System.err.println("ERROR BD: llenar datos de CgutUsuariosAction.java " + ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        return campos;
    }

    private void nuevoUsuario(CgutUsuariosForm frm) {
        frm.setCboUniUsuarios(-2);
        frm.setNombreUsu("");
        frm.setPassword("");
        frm.setConfirmacion("");
    }
}