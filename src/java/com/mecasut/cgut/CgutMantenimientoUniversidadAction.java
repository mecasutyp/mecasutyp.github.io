/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author EQ-30
 */
public class CgutMantenimientoUniversidadAction extends org.apache.struts.action.Action {

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
        CgutMantenimientoUniversidadForm frm = (CgutMantenimientoUniversidadForm) form;
        if (frm.getAsk().equals("mantUniversidad")) {
            int id_periodo = 0;
            String sql = "select id_periodo from periodos where activo = 1";
            ResultSet rs = conexion.Consultar(sql);
            if (rs.next()) {
                id_periodo = rs.getInt(1);
            }
            sql = "select mecasut_iniciado, mecasut_terminado from control_universidad where id_universidad =".concat(String.valueOf(frm.getUniM())).concat(" and id_periodo = ").concat(String.valueOf(id_periodo));
            rs = conexion.Consultar(sql);
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    frm.setEstadoUni("No iniciado");
                    frm.setTerminado(false);
                } else if (rs.getInt(2) == 0) {
                    frm.setEstadoUni("En proceso de evaluación");
                    tableStatus(frm, id_periodo);
                    frm.setTerminado(false);
                } else if (rs.getInt(2) == 1) {
                    frm.setEstadoUni("Terminado");
                    frm.setTerminado(true);
                }
            } else {
                frm.setEstadoUni("No iniciado");
                frm.setTerminado(false);
            }
            conexion.Desconectar();
        }
        if (frm.getAsk().equals("reiniciarDatos")) {
            int uni = Integer.parseInt(request.getParameter("ut"));
            try {
                CallableStatement sp = conexion.PrepararSP("sp_ReiniciarDatosUniversidad(?, ?)");
                sp.setInt(1, frm.getPeriodoActual());
                sp.setInt(2, uni);
                sp.execute();
                String sql = "select mecasut_iniciado, mecasut_terminado from control_universidad where id_universidad =".concat(String.valueOf(frm.getUniM())).concat(" and id_periodo = ").concat(String.valueOf(frm.getPeriodoActual()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    if (rs.getInt(1) == 0) {
                        frm.setEstadoUni("No iniciado");
                    } else if (rs.getInt(2) == 0) {
                        frm.setEstadoUni("En proceso de evaluación");
                    } else if (rs.getInt(2) == 1) {
                        frm.setEstadoUni("Terminado");
                    }
                } else {
                    frm.setEstadoUni("No iniciado");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CgutMantenimientoUniversidadAction.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conexion.Desconectar();
            }
        }
        if (frm.getAsk().equals("reactivarEvaluacion")){
            try{
                String query = "UPDATE control_universidad SET mecasut_terminado=0, id_responsable_termino=NULL, fecha_termino=NULL WHERE id_universidad="
                        .concat(String.valueOf(frm.getUniM())).concat(" and id_periodo = ").concat(String.valueOf(frm.getPeriodoActual()));
                conexion.Modificar(query);
                frm.setEstadoUni("En proceso de evaluación");
                frm.setTerminado(false);
            }catch(SQLException x){
                System.err.println("ERROR BD: reiniciando evaluaión universidad \n"+x.getMessage() +x.getStackTrace());
            }
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("reiniciarCat")) {
            int uni = Integer.parseInt(request.getParameter("ut"));
            int cat = Integer.parseInt(request.getParameter("cat"));
            try {
                CallableStatement sp = conexion.PrepararSP("sp_ReiniciarIndicadores(?, ?, ?)");
                sp.setInt(1, cat);
                sp.setInt(2, frm.getPeriodoActual());
                sp.setInt(3, uni);
                sp.execute();
                String sql = "select mecasut_iniciado, mecasut_terminado from control_universidad where id_universidad =".concat(String.valueOf(frm.getUniM())).concat(" and id_periodo = ").concat(String.valueOf(frm.getPeriodoActual()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    if (rs.getInt(1) == 0) {
                        frm.setEstadoUni("No iniciado");
                    } else if (rs.getInt(2) == 0) {
                        frm.setEstadoUni("En proceso de evaluaciÃ³n");
                    } else if (rs.getInt(2) == 1) {
                        frm.setEstadoUni("Terminado");
                    }
                } else {
                    frm.setEstadoUni("No iniciado");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CgutMantenimientoUniversidadAction.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conexion.Desconectar();
            }
        }

        return mapping.findForward(SUCCESS);
    }

    private void tableStatus(CgutMantenimientoUniversidadForm frm, int id_periodo) {
        ArrayList<String> categorias = new ArrayList<String>();
        ArrayList<String> categoriasNombre = new ArrayList<String>();
        ArrayList<String> indicadores = new ArrayList<String>();
        ArrayList<String> indicadoresDescripcion = new ArrayList<String>();
        String tabla = "";
        String query = "";
        ResultSet rs;
        int contador = 0;
        tabla = tabla.concat("<table>");
        try {
            query = "SELECT id_categoria, nombre FROM system_categorias WHERE activo=1";
            rs = conexion.Consultar(query);
            while (rs.next()) {
                categorias.add(rs.getString("id_categoria"));
                categoriasNombre.add(rs.getString("nombre"));
            }
            for (int j = 0; j < categorias.size(); j++) {
                tabla = tabla.concat("<tr><td colspan='2'>").concat(categoriasNombre.get(j)).concat("</td></tr>");
                query = "SELECT LOWER(archivo_jsp) as indicador, descripcion FROM system_indicadores WHERE"
                        .concat(" activo=1 AND id_categoria=").concat(categorias.get(j));
                rs = conexion.Consultar(query);
                while (rs.next()) {
                    indicadores.add(rs.getString("indicador"));
                    indicadoresDescripcion.add(rs.getString("descripcion"));
                }
                for (int i = 0; i < indicadores.size(); i++) {
                    query = "SELECT COUNT(id_universidad) as 'cantidad' FROM ".concat(indicadores.get(i))
                            .concat(" WHERE id_universidad=").concat(String.valueOf(frm.getUniM()))
                            .concat(" and id_periodo = ").concat(String.valueOf(id_periodo));
                    rs = conexion.Consultar(query);
                    while (rs.next()) {
                        tabla = tabla.concat("<tr><td>").concat(String.valueOf(contador)).concat("</td>");
                        if (rs.getInt("cantidad") > 0) {
                            tabla = tabla.concat("<td>Completado</td></tr>");
                        } else {
                            tabla = tabla.concat("<td> Noo </td></tr>");
                        }
                    }
                    contador++;
                }
            }
            tabla = tabla.concat("</table>");
            System.err.println(tabla);
            frm.setTablaEstatus(tabla);
        } catch (SQLException x) {
            System.err.println("ERRORBD: Mantenimiento, Consultando el estatus de las Universidades " + x.getMessage());
        } finally {
            conexion.Desconectar();
        }
    }
}
