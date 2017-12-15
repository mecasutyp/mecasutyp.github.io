/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author EQ-30
 */
public class CgutAreasAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    ConexionMySQL conexion = new ConexionMySQL();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        CgutAreasForm frm = (CgutAreasForm) form;
        if (frm.getAsk().equals("buscaArea")) {
            if (frm.getCboArea() > 0) {
                ResultSet rs = conexion.Consultar("select * from areas_pe where id_area=".concat(String.valueOf(frm.getCboArea())));
                if (rs.next()) {
                    frm.setNombreArea(rs.getString("nombre_area"));
                    if (rs.getBoolean("activo")) {
                        frm.setAreaActivo("Desactivar Área");
                    } else {
                        frm.setAreaActivo("Activar Área");
                    }
                }

            } else {
                nuevaArea(frm);
            }
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("nuevaArea")) {
            nuevaArea(frm);
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("guardarArea")) {
            String update = "update areas_pe set nombre_area='".concat(frm.getNombreArea()).concat("' where id_area=").concat(String.valueOf(frm.getCboArea()));
            String insert = "insert into areas_pe (nombre_area, activo) values ('".concat(frm.getNombreArea()).concat("',1)");
            guardarDatos(frm, update, insert);
            frm.setAreas();
            nuevaArea(frm);
            return mapping.findForward(SUCCESS);
        }

        if (frm.getAsk().equals("bajaArea")) {
            if (frm.getCboArea() > 0) {
                String consulta = "select activo from areas_pe where id_area=".concat(String.valueOf(frm.getCboArea()));
                String update1 = "update areas_pe set activo=";
                String update2 = " where id_area=";
                if (bajaDatos(consulta, update1, update2, frm.getCboArea()) == 1) {
                    frm.setAreaActivo("Activar Área");
                } else {
                    frm.setAreaActivo("Desactivar Área");
                }
            }
            return mapping.findForward(SUCCESS);
        }

        return mapping.findForward(SUCCESS);
    }

    private void nuevaArea(CgutAreasForm frm) {
        frm.setCboArea(-1);
        frm.setNombreArea("");
        frm.setAreaActivo("");
    }

    private void guardarDatos(CgutAreasForm frm, String update, String insert) {
        try {
            if (conexion.Modificar(update) == 0) {
                try {
                    conexion.Insertar(insert);
                } catch (SQLException ex1) {
                    Logger.getLogger(CgutAreasAction.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutAreasAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int bajaDatos(String consulta, String update1, String update2, int id) {
        int activo = 0;
        try {
            String update = update1;
            ResultSet rs = conexion.Consultar(consulta);
            if (rs.next()) {
                activo = rs.getInt(1);
            }
            if (activo == 1) {
                update = update.concat("0").concat(update2).concat(String.valueOf(id));
                conexion.Modificar(update);
            } else {
                update = update.concat("1").concat(update2).concat(String.valueOf(id));
                conexion.Modificar(update);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutAreasAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activo;
    }
}
