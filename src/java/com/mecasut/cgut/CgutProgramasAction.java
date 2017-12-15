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
 *Daniel Ramírez Torres
 */
public class CgutProgramasAction extends org.apache.struts.action.Action {
    /* forward name="success" path="" */

    private static final String SUCCESS = "success";
    ConexionMySQL conexion = new ConexionMySQL();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession sesion = request.getSession(false);
        CgutProgramasForm frm = (CgutProgramasForm) form;
        if (frm.getAsk().equals("buscaAreaProg")) {
            if (frm.getCboAreaProg() > 0) {
                frm.setFamiliasProg(llenarDatos("Select id_familia,nombre_familia from familia_pe where activo = 1 and id_area=".concat(String.valueOf(frm.getCboAreaProg())).concat(" order by nombre_familia")));
                frm.setCboNivel(-2);
                frm.setNombrePrograma("");
                frm.setProgramaActivo("");
                frm.setVersionPrograma("");
                frm.setNomenclaturaProg("");
            } else {
                nuevoPrograma(frm, false);
            }
            return mapping.findForward(SUCCESS);
        }

        frm.setFamiliasProg(llenarDatos("Select id_familia,nombre_familia from familia_pe where activo = 1 and id_area=".concat(String.valueOf(frm.getCboAreaProg())).concat(" order by nombre_familia")));
        if (frm.getAsk().equals("buscaFamiliaProg")) {
            if (frm.getCboFamiliaProg() > 0) {
                frm.setProgramasEducativos(llenarDatos("select id_pe, CONCAT(nombre_programa,' (',version,')') from programa_educativo where id_familia=".concat(String.valueOf(frm.getCboFamiliaProg())).concat(" order by nombre_programa")));
            } else {
                frm.setCboPE(-1);
                frm.setNombrePrograma("");
                frm.setProgramaActivo("");
                frm.setNomenclaturaProg("");
                frm.setVersionPrograma("");
                frm.setCboNivel(-1);
            }
            return mapping.findForward(SUCCESS);
        }
        frm.setProgramasEducativos(llenarDatos("select id_pe, CONCAT(nombre_programa,' (',version,')') from programa_educativo where id_familia=".concat(String.valueOf(frm.getCboFamiliaProg())).concat(" order by nombre_programa")));
        if (frm.getAsk().equals("buscaPrograma")) {
            if (frm.getCboPE() > 0) {
                ResultSet rs = conexion.Consultar("select * from programa_educativo pe inner join nivel_pe np on pe.id_nivel=np.id_nivel where pe.id_pe=".concat(String.valueOf(frm.getCboPE())).concat(" order by nombre_programa"));
                if (rs.next()) {
                    frm.setNombrePrograma(rs.getString("nombre_programa"));
                    frm.setNomenclaturaProg(rs.getString("nomenclatura"));
                    frm.setCboNivel(rs.getInt("id_nivel"));
                    frm.setVersionPrograma(rs.getString("version"));
                    if (rs.getBoolean("activo")) {
                        frm.setProgramaActivo("Desactivar Programa educativo");
                    } else {
                        frm.setProgramaActivo("Activar Programa educativo");
                    }
                }
            } else {
                frm.setNombrePrograma("");
                frm.setProgramaActivo("");
                frm.setNomenclaturaProg("");
                frm.setVersionPrograma("");
                frm.setCboNivel(-1);
            }
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("nuevoPrograma")) {
            nuevoPrograma(frm, false);
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("guardarPrograma")) {
            String update = "update programa_educativo set nombre_programa='".concat(frm.getNombrePrograma()).concat("', version='".concat(frm.getVersionPrograma()).concat("', nomenclatura='".concat(frm.getNomenclaturaProg())).concat("', id_nivel=".concat(String.valueOf(frm.getCboNivel())).concat(" where id_pe=").concat(String.valueOf(frm.getCboPE()))));
            String insert = "insert into programa_educativo (nombre_programa,version, nomenclatura,id_familia, id_nivel, activo) values ('".concat(frm.getNombrePrograma()).concat("','").concat(frm.getVersionPrograma()).concat("','").concat(frm.getNomenclaturaProg()).concat("',").concat(String.valueOf(frm.getCboFamiliaProg())).concat(",").concat(String.valueOf(frm.getCboNivel())).concat(",1)");
            guardarDatos(frm, update, insert);
            nuevoPrograma(frm, true);
            frm.setProgramasEducativos(llenarDatos("select id_pe, CONCAT(nombre_programa,' (',version,')') from programa_educativo where id_familia=".concat(String.valueOf(frm.getCboFamiliaProg()))));
            return mapping.findForward(SUCCESS);
        }
        if (frm.getAsk().equals("bajaPrograma")) {
            if (frm.getCboPE() > 0) {
                String consulta = "select activo from programa_educativo where id_pe=".concat(String.valueOf(frm.getCboPE()));
                String update1 = "update programa_educativo set activo=";
                String update2 = " where id_pe=";
                if (bajaDatos(consulta, update1, update2, frm.getCboPE()) == 1) {
                    frm.setProgramaActivo("Activar Programa educativo");
                } else {
                    frm.setProgramaActivo("Desactivar Programa educativo");
                }
            }
            return mapping.findForward(SUCCESS);
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
            Logger.getLogger(CgutProgramasForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        return campos;
    }

    private void nuevoPrograma(CgutProgramasForm frm, boolean combos) {
        if (combos == false) {
            frm.setCboAreaProg(-1);
            frm.setCboFamiliaProg(-1);
            frm.setFamiliasProg(new ArrayList());
        }
        frm.setCboPE(-1);
        frm.setProgramasEducativos(new ArrayList());
        frm.setNombrePrograma("");
        frm.setProgramaActivo("");
        frm.setVersionPrograma("");
        frm.setNomenclaturaProg("");
        frm.setCboNivel(-2);
    }

    private void guardarDatos(CgutProgramasForm frm, String update, String insert) {
        try {
            if (conexion.Modificar(update) == 0) {
                try {
                    conexion.Insertar(insert);
                } catch (SQLException ex1) {
                    Logger.getLogger(CgutProgramasAction.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutProgramasAction.class.getName()).log(Level.SEVERE, null, ex);
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
}
