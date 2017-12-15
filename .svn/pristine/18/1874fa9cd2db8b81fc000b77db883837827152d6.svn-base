/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author DELL
 */
public class CgutModelosAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    ConexionMySQL conexion = new ConexionMySQL();

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

        HttpSession sesion = request.getSession(false);
        CgutModelosForm frm = (CgutModelosForm) form;
        
//        if (frm.getAsk().equals("buscaModelo")) {
//            if (frm.getCboModelo() > 0) {
//                String sql = "select * from modelos where id_modelo =".concat(String.valueOf(frm.getCboModelo()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    frm.setNombreModelo(rs.getString(2));
//                    if (rs.getBoolean(3)) {
//                        frm.setModeloActivo("Desactivar Modelo");
//                    } else {
//                        frm.setModeloActivo("Activar Modelo");
//                    }
//                }
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            } else {
//                nuevoModelo(frm);
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            }
//        }

        if (frm.getAsk().equals("bajaModelo")) {
            if (frm.getCboModelo() > 0) {
                String sql = "select activo from modelos where id_modelo = ".concat(String.valueOf(frm.getCboModelo()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        String sql2 = "update modelos set activo = 0 where id_modelo = ".concat(String.valueOf(frm.getCboModelo()));
                        conexion.Modificar(sql2);
                        conexion.Desconectar();
                        frm.setModeloActivo("Activar Modelo");
                        return mapping.findForward(SUCCESS);
                    } else {
                        String sql3 = "update modelos set activo = 1 where id_modelo = ".concat(String.valueOf(frm.getCboModelo()));
                        conexion.Modificar(sql3);
                        conexion.Desconectar();
                        frm.setModeloActivo("Desactivar Modelo");
                        return mapping.findForward(SUCCESS);
                    }
                }
            }
        }

        if (frm.getAsk().equals("guardarModelo")) {
            if (frm.getCboModelo() > 0) {
                String sql = "update modelos set descripcion ='".concat(frm.getNombreModelo()).concat("' where id_modelo='").concat(String.valueOf(frm.getCboModelo())).concat("'");
                conexion.Modificar(sql);
                conexion.Desconectar();
                nuevoModelo(frm);
                frm.setModelos();
                return mapping.findForward(SUCCESS);
            } else {
                String sql2 = "insert into modelos values(null,'".concat(frm.getNombreModelo()).concat("',1)");
                conexion.Insertar(sql2);
                conexion.Desconectar();
                nuevoModelo(frm);
                frm.setModelos();
                return mapping.findForward(SUCCESS);
            }
        }

        if (frm.getAsk().equals("buscaModelo")) {
            if (frm.getCboModelo() > 0) {
                String sql = "select * from modelos where id_modelo =".concat(String.valueOf(frm.getCboModelo()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    frm.setNombreModelo(rs.getString(2));
                    if (rs.getBoolean(3)) {
                        frm.setModeloActivo("Desactivar Modelo");
                    } else {
                        frm.setModeloActivo("Activar Modelo");
                    }
                }
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            } else {
                nuevoModelo(frm);
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            }
        }

        if (frm.getAsk().equals("nuevoModelo")) {
            nuevoModelo(frm);
            return mapping.findForward(SUCCESS);
        }
        
        //return final
        return mapping.findForward(SUCCESS);
    }

    private void nuevoModelo(CgutModelosForm frm) {
        frm.setCboModelo(-1);
        frm.setNombreModelo("");
    }
}
