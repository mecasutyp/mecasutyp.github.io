/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Cuauh
 */
public class CgutEdificiosAction extends org.apache.struts.action.Action {

    ConexionMySQL conexion = new ConexionMySQL();
    /* forward name="success" path="" */
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

        CgutEdificiosForm frm = (CgutEdificiosForm) form;
        
        if (frm.getAsk().equals("bajaEdificio")) {
            if (frm.getCboEdificio() > 0) {
                String sql = "select activo from edificios where id_edificio = ".concat(String.valueOf(frm.getCboEdificio()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        sql = "update edificios set activo = 0 where id_edificio = ".concat(String.valueOf(frm.getCboEdificio()));
                        conexion.Modificar(sql);
                        conexion.Desconectar();
                        frm.setEdificioActivo("Activar Edificio");
                        return mapping.findForward(SUCCESS);
                    } else {
                        sql = "update edificios set activo = 1 where id_edificio = ".concat(String.valueOf(frm.getCboEdificio()));
                        conexion.Modificar(sql);
                        conexion.Desconectar();
                        frm.setEdificioActivo("Desactivar Edificio");
                        return mapping.findForward(SUCCESS);
                    }
                }
            }
        }

        if (frm.getAsk().equals("guardarEdificio")) {
            if (frm.getCboEdificio() > 0) {
                String sql = "update edificios set descripcion ='".concat(frm.getNombreEdificio()).concat("', capacidad = ").concat(frm.getCapacidadEdificio()).concat(" where id_edificio='").concat(String.valueOf(frm.getCboEdificio())).concat("'");
                conexion.Modificar(sql);
                conexion.Desconectar();
                nuevoEdificio(frm);
                frm.setEdificios();
                return mapping.findForward(SUCCESS);
            } else {
                String sql2 = "insert into edificios(descripcion,capacidad,activo) values('".concat(frm.getNombreEdificio()).concat("',").concat(frm.getCapacidadEdificio()).concat(",1)");
                conexion.Insertar(sql2);
                conexion.Desconectar();
                nuevoEdificio(frm);
                frm.setEdificios();
                return mapping.findForward(SUCCESS);
            }
        }

        if (frm.getAsk().equals("buscaEdificio")) {
            if (frm.getCboEdificio() > 0) {
                String sql = "select * from edificios where id_edificio =".concat(String.valueOf(frm.getCboEdificio()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    frm.setNombreEdificio(rs.getString(2));
                    frm.setCapacidadEdificio(rs.getString(3));
                    if (rs.getBoolean(4)) {
                        frm.setEdificioActivo("Desactivar Edificio");
                    } else {
                        frm.setEdificioActivo("Activar Edificio");
                    }
                }
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            } else {
                nuevoEdificio(frm);
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            }
        }

        if (frm.getAsk().equals("nuevoEdificio")) {
            nuevoEdificio(frm);
            return mapping.findForward(SUCCESS);
        }

        return mapping.findForward(SUCCESS);
    }

    private void nuevoEdificio(CgutEdificiosForm frm) {
        frm.setCboEdificio(-1);
        frm.setNombreEdificio("");
        frm.setCapacidadEdificio("");
    }
}
