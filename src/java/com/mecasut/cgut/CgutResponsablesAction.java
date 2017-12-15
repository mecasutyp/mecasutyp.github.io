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
 * Actualización 2016: Salvador Zamora Arias
 */
public class CgutResponsablesAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    ConexionMySQL conexion = new ConexionMySQL();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession sesion = request.getSession(false);
        CgutResponsablesForm frm = (CgutResponsablesForm) form;

        
        /*Realizando las consultas crrespondientes a las universidades y a los responsables*/
        /*Realizando las consultas crrespondientes a las universidades y a los responsables*/
        
        if (frm.getAsk().equals("buscaResponsable")) {
            if (frm.getCboUni() >= 0) {
                nuevoResponsables2(frm);
                frm.setResponsables(cargarResponsables(frm));
            }else{
                nuevoResponsables(frm);
            }
            return mapping.findForward(SUCCESS);
        }
        frm.setResponsables(cargarResponsables(frm));

        if (frm.getAsk().equals("buscaResponsable2")) {
            conexion.Conectar();
            ResultSet rs;
            try {
                if (frm.getCboResponsables() > 0) {
                    String sql = "select * from responsables where id_responsable ="
                            .concat(String.valueOf(frm.getCboResponsables()));
                    rs = conexion.Consultar(sql);
                    if (rs.next()) {
                        frm.setNombreResponsable(rs.getString(3));
                        frm.setNombreAPResponsable(rs.getString(4));
                        frm.setNombreAMResponsable(rs.getString(5));
                        frm.setNombreCargo(rs.getString(6));
                        
                         String[] parts = rs.getString("telefono").split("#"); 
                    
                        if(parts.length>1){
                            frm.setNombreLada(parts[0]);
                             frm.setNombreTelefono(parts[1]);
                        }else{
                            frm.setNombreLada("");
                             frm.setNombreTelefono(rs.getString("telefono"));
                        }
                       
                        
                        frm.setNombreMail(rs.getString(8));
                        frm.setNombreFechaI(rs.getString(9));
                        frm.setNombreFechaR(rs.getString(10));
                        if (rs.getBoolean(11)) {
                            String s = rs.getString(11);
                            frm.setResponsablesActivo("Desactivar Responsable");
                        } else {
                            frm.setResponsablesActivo("Activar Responsable");
                        }
                    }
                } else {
                    nuevoResponsables2(frm);
                }
            } catch (SQLException ex) {
                System.err.println("ERROR CgutResponsablesAction.java \n\n"+ ex.getMessage());
            } finally {
                conexion.Desconectar();
                return mapping.findForward(SUCCESS);
            }
        }
        
        /*INICIANDO LA FUNCION ACTUALIZAR O AGREGAR APARTIR DE DAR CLIC EN GUARDARRESPONSABLE*/
        /*INICIANDO LA FUNCION ACTUALIZAR O AGREGAR APARTIR DE DAR CLIC EN GUARDARRESPONSABLE*/
        
        if (frm.getAsk().equals("guardarResponsable")) {
            conexion.Conectar();
            String sql = "", sql2 = "", fechaI, fechaR;
            if (frm.getNombreFechaI().equals("")) {
                fechaI = "null";
            } else {
                fechaI = "'" + frm.getNombreFechaI() + "'";
            }
            if (frm.getNombreFechaR().equals("")) {
                fechaR = "null";
            } else {
                fechaR = "'" + frm.getNombreFechaR() + "'";
            }
            if (frm.getCboResponsables() > 0) {
               
                sql = "update responsables set id_universidad = '".concat(String.valueOf(frm.getCboUni())).concat("', nombre ='").concat(frm.getNombreResponsable()).concat("', apaterno='").concat(frm.getNombreAPResponsable()).concat("', amaterno ='").concat(frm.getNombreAMResponsable()).concat("', cargo ='").concat(frm.getNombreCargo()).concat("', telefono ='").concat(frm.getNombreTelefono()).concat("', mail ='").concat(frm.getNombreMail()).concat("', fecha_ingreso =").concat(fechaI).concat(", fecha_retiro =").concat(fechaR).concat(" where id_responsable = '").concat(String.valueOf(frm.getCboResponsables())).concat("'");
                
                conexion.Modificar(sql);
                conexion.Desconectar();
                nuevoResponsables(frm);
                return mapping.findForward(SUCCESS);
            } else {
       

sql2 = "insert into responsables values(null,'".concat(String.valueOf(frm.getCboUni())).concat("','").concat(frm.getNombreResponsable()).concat("','").concat(frm.getNombreAPResponsable()).concat("','").concat(frm.getNombreAMResponsable()).concat("','").concat(frm.getNombreCargo()).concat("','").concat(frm.getNombreTelefono()).concat("','").concat(frm.getNombreMail()).concat("',").concat(fechaI).concat(",").concat(fechaR).concat(",1").concat(",'ru')");
conexion.Insertar(sql2);
                conexion.Desconectar();
                nuevoResponsables(frm);
                return mapping.findForward(SUCCESS);
            }
        }
        if (frm.getAsk().equals("bajaResponsables")) {
            if (frm.getCboResponsables() > 0) {
                String sql = "select activo from responsables where id_responsable = ".concat(String.valueOf(frm.getCboResponsables()));
                ResultSet rs = conexion.Consultar(sql);
                if (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        String sql2 = "update responsables set activo = 0 where id_responsable = ".concat(String.valueOf(frm.getCboResponsables()));
                        conexion.Modificar(sql2);
                        conexion.Desconectar();
                        frm.setResponsablesActivo("Activar Responsable");
                    } else {
                        String sql3 = "update responsables set activo = 1 where id_responsable = ".concat(String.valueOf(frm.getCboResponsables()));
                        conexion.Modificar(sql3);
                        conexion.Desconectar();
                        frm.setResponsablesActivo("Desactivar Responsable");
                    }
                }
                return mapping.findForward(SUCCESS);
            }
        }
        if (frm.getAsk().equals("nuevoResponsable")) {
            frm.setCboUni(0);
            frm.setCboResponsables(0);
            frm.setNombreResponsable("");
            frm.setNombreAPResponsable("");
            frm.setNombreAMResponsable("");
            frm.setNombreCargo("");
            frm.setNombreLada("");
            frm.setNombreTelefono("");
            frm.setNombreMail("");
            frm.setNombreFechaI("");
            frm.setNombreFechaR("");
            //frm.setUniActivo("");
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }

    private ArrayList cargarResponsables(CgutResponsablesForm frm) {
        ArrayList responsables = new ArrayList();
        if (frm.getCboUni() >= 0) {
            try {
                String sql = "select responsables.id_responsable, nombre, apaterno, amaterno from responsables where id_universidad =" + String.valueOf(frm.getCboUni());
                ResultSet rs = conexion.Consultar(sql);
                while (rs.next()) {
                    responsables.add(new Coleccion(rs.getInt(1), rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)));
                }
            } catch (SQLException ex) {
                Logger.getLogger(CgutResponsablesAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            nuevoResponsables(frm);
        }
        conexion.Desconectar();

        return responsables;

    }

    private void nuevoResponsables2(CgutResponsablesForm frm) {
        frm.setCboResponsables(-1);
        frm.setNombreResponsable("");
        frm.setNombreAPResponsable("");
        frm.setNombreAMResponsable("");
        frm.setNombreCargo("");
        frm.setNombreLada("");
        frm.setNombreTelefono("");
        frm.setNombreMail("");
        frm.setNombreFechaI("");
        frm.setNombreFechaR("");
    }

    private void nuevoResponsables(CgutResponsablesForm frm) {
        frm.setResponsables(new ArrayList());
        frm.setCboUni(-1);
        frm.setNombreResponsable("");
        frm.setNombreAPResponsable("");
        frm.setNombreAMResponsable("");
        frm.setNombreCargo("");
        frm.setNombreLada("");
        frm.setNombreTelefono("");
        frm.setNombreMail("");
        frm.setNombreFechaI("");
        frm.setNombreFechaR("");
        //frm.setUniActivo("");
    }
}
