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
 * Daniel Ramírez Torres
 */
public class CgutCategoriasAction extends org.apache.struts.action.Action {

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
        CgutCategoriasForm frm = (CgutCategoriasForm) form;
        
        if (frm.getAsk().equals("buscaCategorias")) {
            frm.setNombrCategorias("no mames");
        
            frm.setDed("no mames");
            if (frm.getCboCategorias() > 0) {
                frm.getNombrCategorias();
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            } else {
                nuevaCategoria(frm);
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            }
        }
        if (frm.getAsk().equals("nuevaCategoria")) {
            nuevaCategoria(frm);
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("guardarCategoria")) {
            if (frm.getCboCategorias() > 0) {
                String sql = "update system_categorias set nombre = '".concat(frm.getNombrCategorias()).concat("'").concat("where id_categoria = '").concat(String.valueOf(frm.getCboCategorias())).concat("'");
                conexion.Modificar(sql);
                conexion.Desconectar();
                nuevaCategoria(frm);
                frm.setCategorias();
                return mapping.findForward(SUCCESS);
            } else {
                String sql2 = "insert into system_categorias values(null,'".concat(sesion.getAttribute("idPeriodo").toString()).concat("','").concat(frm.getNombrCategorias()).concat("',1)");
                conexion.Insertar(sql2);
                conexion.Desconectar();
                nuevaCategoria(frm);
                frm.setCategorias();
                return mapping.findForward(SUCCESS);
            }
        }
        if (frm.getAsk().equals("bajaCategoria")) {
            if (frm.getCboCategorias() > 0) {
                String sql = "select activo from system_categorias where id_categoria = ".concat(String.valueOf(frm.getCboCategorias()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        String sql2 = "update system_categorias set activo = 0 where id_categoria = ".concat(String.valueOf(frm.getCboCategorias()));
                        conexion.Modificar(sql2);
                        conexion.Desconectar();
                        frm.setCategoriaActiva("Activar Categoría");
                        return mapping.findForward(SUCCESS);
                    } else {
                        String sql3 = "update system_categorias set activo = 1 where id_categoria = ".concat(String.valueOf(frm.getCboCategorias()));
                        conexion.Modificar(sql3);
                        conexion.Desconectar();
                        frm.setCategoriaActiva("Desactivar Categoría");
                        return mapping.findForward(SUCCESS);
                    }
                }
            }
        }
        return mapping.findForward(SUCCESS);
    }

    private void nuevaCategoria(CgutCategoriasForm frm) {
        frm.setCboCategorias(-1);
        frm.setNombrCategorias("");
    }
}
