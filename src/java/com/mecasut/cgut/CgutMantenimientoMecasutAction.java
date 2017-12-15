package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Daniel Ramírez Torres
 */
public class CgutMantenimientoMecasutAction extends org.apache.struts.action.Action {

    ConexionMySQL conexion = new ConexionMySQL();
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CgutMantenimientoMecasutForm frm = (CgutMantenimientoMecasutForm) form;
        if (frm.getAsk().equals("mantMecas")) {
            HttpSession sesion = request.getSession(true);
            String sql = "update periodos set activo = 0 where activo = 1";
            conexion.Modificar(sql);
            sql = "update periodos set activo = 1 where id_periodo = ".concat(String.valueOf(frm.getPerActual()));
            conexion.Modificar(sql);
            sql = "update system_mecasut set activo = ".concat(String.valueOf(frm.getEstadoActual())).concat(" where 1");
            conexion.Modificar(sql);
            sesion.setAttribute("idPeriodo", String.valueOf(frm.getPerActual()));
            conexion.Desconectar();
        }
        return mapping.findForward(SUCCESS);
    }
}
