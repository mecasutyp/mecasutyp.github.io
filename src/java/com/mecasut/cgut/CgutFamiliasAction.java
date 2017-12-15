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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Daniel Ramírez Torres
 */
public class CgutFamiliasAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    ConexionMySQL conexion = new ConexionMySQL();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        CgutFamiliasForm frm = (CgutFamiliasForm) form;
        if (frm.getAsk().equals("buscaAreaFamilia")) {
            if (frm.getCboAreaFam() > 0) {
                frm.setFamilias(llenarDatos("Select id_familia, nombre_familia from familia_pe where id_area=".concat(String.valueOf(frm.getCboAreaFam())).concat(" order by nombre_familia")));
                frm.setNombreFamilia("");
                frm.setNomenclaturaFam("");
            } else {
                nuevaFamilia(frm, false);
            }
            return mapping.findForward(SUCCESS);
        }
        frm.setFamilias(llenarDatos("select id_familia, nombre_familia from familia_pe where id_area=".concat(String.valueOf(frm.getCboAreaFam())).concat(" order by nombre_familia")));
        if (frm.getAsk().equals("buscaFamilia")) {
            if (frm.getCboFamilia() > 0) {
                ResultSet rs = conexion.Consultar("select * from familia_pe where id_familia=".concat(String.valueOf(frm.getCboFamilia())).concat(" order by nombre_familia"));
                if (rs.next()) {
                    frm.setNombreFamilia(rs.getString("nombre_familia"));
                    frm.setNomenclaturaFam(rs.getString("nomenclatura"));
                    if (rs.getBoolean("activo")) {
                        frm.setFamiliaActivo("Desactivar Familia");
                    } else {
                        frm.setFamiliaActivo("Activar Familia");
                    }
                }
            } else {
                frm.setNombreFamilia("");
                frm.setFamiliaActivo("");
                frm.setNomenclaturaFam("");
            }
            return mapping.findForward(SUCCESS);
        }

        if (frm.getAsk().equals("bajaFamilia")) {
            if (frm.getCboFamilia() > 0) {
                String consulta = "select activo from familia_pe where id_familia=".concat(String.valueOf(frm.getCboFamilia()).concat(" order by nombre_familia"));
                String update1 = "update familia_pe set activo=";
                String update2 = " where id_familia=";
                if (bajaDatos(consulta, update1, update2, frm.getCboFamilia()) == 1) {
                    frm.setFamiliaActivo("Activar Familia");
                } else {
                    frm.setFamiliaActivo("Desactivar Familia");
                }
            }
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("guardarFamilia")) {
            String update = "update familia_pe set nombre_familia='".concat(frm.getNombreFamilia()).concat("', nomenclatura='".concat(frm.getNomenclaturaFam())).concat("' where id_familia=").concat(String.valueOf(frm.getCboFamilia()));
            String insert = "insert into familia_pe (nombre_familia,nomenclatura,id_area, activo) values ('".concat(frm.getNombreFamilia()).concat("','").concat(frm.getNomenclaturaFam()).concat("',").concat(String.valueOf(frm.getCboAreaFam())).concat(",1)");
            guardarDatos(frm, update, insert);
            //nuevaFamilia(frm, true);
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("nuevaFamilia")) {
            nuevaFamilia(frm, false);
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }

    private void nuevaFamilia(CgutFamiliasForm frm, boolean campos) {
        if (campos == false) {
            frm.setCboAreaFam(-1);
        }
        frm.setCboFamilia(-1);
        frm.setFamilias(new ArrayList());
        frm.setNombreFamilia("");
        frm.setFamiliaActivo("");
        frm.setNomenclaturaFam("");
    }

    private void guardarDatos(CgutFamiliasForm frm, String update, String insert) {
        try {
            if (conexion.Modificar(update) == 0) {
                try {
                    conexion.Insertar(insert);
                } catch (SQLException ex1) {
                    Logger.getLogger(CgutFamiliasAction.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutFamiliasAction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CgutFamiliasAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activo;
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
            Logger.getLogger(CgutFamiliasForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        return campos;
    }
}