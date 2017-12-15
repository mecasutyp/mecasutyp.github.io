/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.eficacia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.mecasut.conexion.ConexionMySQL;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joshua
 */
public class EficaciaIn4Action extends org.apache.struts.action.Action {

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
        HttpSession sesion = request.getSession(false);
        String idUniversidad = sesion.getAttribute("idUniversidad").toString();
        String idPeriodo = sesion.getAttribute("idPeriodo").toString();
        EficaciaIn4Form frm = (EficaciaIn4Form) form;
        if (!frm.getValores().equals("Error")) {
            boolean band = Guardar(frm.getValores(), idUniversidad, idPeriodo);
            if (band == false) {
                return mapping.findForward(null);
            } else {
                return mapping.findForward(SUCCESS);
            }
        } else {
            return mapping.findForward(null);
        }
    }

  private boolean Guardar(String valores, String idUniversidad, String idPeriodo) {
        if (valores.equals("Error")) {
            return false;
        } else {
            System.out.println("valores "+valores);
            String [] values; 
            String [] values2; 
            String [] causas; 
            String [] datosxcausa; 
            String [] pes; 
            String [] datosxpe; 
            values = valores.split("_");//POR NIVEL
            ConexionMySQL conexion = new ConexionMySQL();
            
            
        try {
            for (int i = 0; i < values.length; i++) {//POR NIVEL
                values2 = values[i].split("&");
                causas = values2[0].split("-");
                pes = values2[1].split("-");
                
                for (int z = 0; z < causas.length; z++) {//POR NIVEL
                    datosxcausa = causas[z].split(",");
                    String sql = "UPDATE eficaciain4 SET sep = "
                            .concat(datosxcausa[2])
                            .concat(", ene = ").concat(datosxcausa[3])
                            .concat(", may = ").concat(datosxcausa[4])
                            .concat(" WHERE id_universidad = ").concat(idUniversidad)
                            .concat(" AND id_periodo = ").concat(idPeriodo)
                            .concat(" AND id_nivel = ").concat(datosxcausa[0])
                            .concat(" AND id_causa = ").concat(datosxcausa[1]);
                    if (conexion.Modificar(sql) == 0) {
                        String sql2 = "INSERT INTO eficaciain4 VALUES ";
                            sql2+="("+idUniversidad+", "+idPeriodo+", "+datosxcausa[0]+", "+datosxcausa[1]+", "
                                    + ""+datosxcausa[2]+","+datosxcausa[3]+","+datosxcausa[4]+"  )";
                        conexion.Insertar(sql2);
                    }
                }
                
                for (int z = 0; z < pes.length; z++) {//POR NIVEL
                    datosxpe = pes[z].split(",");
                     String sql = "UPDATE eficaciain4_1 SET "
                            .concat( "tot_deser_sep= ").concat(datosxpe[2])
                            .concat(", tot_deser_ene = ").concat(datosxpe[3])
                            .concat(", tot_deser_may = ").concat(datosxpe[4])
                            .concat(" WHERE id_universidad = ").concat(idUniversidad)
                            .concat(" AND id_periodo = ").concat(idPeriodo)
                            .concat(" AND id_nivel = ").concat(datosxpe[0])
                            .concat(" AND id_pe = ").concat(datosxpe[1]);
                    if(conexion.Modificar(sql) == 0){
                        String sql2="INSERT INTO eficaciain4_1 VALUES("+idUniversidad+", "+idPeriodo+", "
                                + " "+datosxpe[0]+", "+datosxpe[2]+", "+datosxpe[3]+", "+datosxpe[4]+","
                                + ""+datosxpe[1]+" )";
                        conexion.Insertar(sql2);
                    }
                }
                
                System.out.println("Cambio de nivel -----------------------------------");
            }
        } catch (Exception ex) {
            System.err.println("ERROR Eficaciain4Datos " +ex);
            return false;
        } finally {
            conexion.Desconectar();
        }
            
//            String[] values;//Valores de todo el formulario
//            String[] datos;//Valores de cada insert
//            String[] datos2;
//            String[] values2;
//            values = valores.split("-");
//            values2 = valores.split("x");
//            ConexionMySQL conexion = new ConexionMySQL();
//            try {
//                for (int i = 1; i < values.length - 1; i++) {
//                    datos = values[i].split(",");
//                    String sql = "Update eficaciain4 set sep = "
//                            .concat(datos[2])
//                            .concat(", ene = ").concat(datos[3])
//                            .concat(", may = ").concat(datos[4])
//                            .concat(" where id_universidad = ").concat(idUniversidad)
//                            .concat(" and id_periodo = ").concat(idPeriodo)
//                            .concat(" and id_nivel = ").concat(datos[0])
//                            .concat(" and id_causa = ").concat(datos[1]);
//                    if (conexion.Modificar(sql) == 0) {
//                        String sql2 = "Insert into eficaciain4 values";
//                        for (int j = 1; j < values.length - 1; j++) {
//                            if (j == values.length - 2) {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat(")");
//                            } else {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values[j]).concat("),");
//                            }
//                        }
//                        conexion.Insertar(sql2);
//                    }
//                }
//                for (int i = 1; i < values2.length; i++) {
//                    datos2 = values2[i].split(",");
//                    String sql = "Update eficaciain4_1 set tot_deser_sep= "
//                            .concat(datos2[1])
//                            .concat(", tot_deser_ene = ").concat(datos2[2])
//                            .concat(", tot_deser_may = ").concat(datos2[3])
//                            .concat(" where id_universidad = ").concat(idUniversidad)
//                            .concat(" and id_periodo = ").concat(idPeriodo)
//                            .concat(" and id_nivel = ").concat(datos2[0]);
//                    if (conexion.Modificar(sql) == 0) {
//                        String sql2 = "Insert into eficaciain4_1 values";
//                        for (int j = 1; j < values2.length; j++) {
//                            if (j == values2.length - 1) {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values2[j]).concat(")");
//                            } else {
//                                sql2 = sql2.concat("(").concat(idUniversidad).concat(",").concat(idPeriodo).concat(",").concat(values2[j]).concat("),");
//                            }
//                        }
//                        conexion.Insertar(sql2);
//                    }
//                }
//            } catch (SQLException ex) {
//                System.err.println("ERROR Eficaciain4Datos " +ex);
//                return false;
//            } finally {
//                conexion.Desconectar();
//            }
            return true;
        }
    }
}