/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Actualización 2016: Salvador Zamora Arias
 */
package com.mecasut.admon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Joshua , Cuauhtemoc Medina Muñoz
 */
public class registroUniversidadAction extends org.apache.struts.action.Action {

    /* Forwarders */
    private static final String SUCCESS = "success";
    private static final String EFICACIA = "eficacia";
    private static final String LOGOUT = "logout";

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

        registroUniversidadForm frm = (registroUniversidadForm) form;
        HttpSession sesion = request.getSession(false);
         if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }

        if (frm.getAsk().equals("cambiarIdUniversidad")) {
            frm.setUniversidad(sesion.getAttribute("idUniversidad").toString());
            frm.iniciarFormulario();
            return mapping.findForward(SUCCESS);
        }


        if (frm.getAsk().equals("guardar")) {
            Guardar(Integer.parseInt(sesion.getAttribute("idPeriodo").toString()),
                    Integer.parseInt(sesion.getAttribute("idUniversidad").toString()),
                    frm.getCboEstado(),
                    frm.getMunicipio(),
                    frm.getColonia(),
                    frm.getCalle(),
                    frm.getNumero(),
                    frm.getCp(),
                    frm.getSeleccionados(),
                    frm.getAlNuevoIngreso(),
                    frm.getMatTotal(),
                    frm.getProfTC(),
                    frm.getProfAsign(),
                    frm.getModSeleccionados(),
                    frm.getServSeleccionados(),
                    frm.getEdifSeleccionados(),
                    frm.getLstAniosInc(),
                    frm.getLstNuevoIngreso(),
                    frm.getLstMat(),
                    frm.getLstClas(),
                    frm);
            frm.setDireccion(sesion.getAttribute("idUniversidad").toString());
            return mapping.findForward(SUCCESS);
        }

        if (frm.getAsk().equals("logout")) {
            if (sesion != null) {
                sesion.invalidate();
                sesion = null;
            }
            return mapping.findForward(LOGOUT);
        }

        if (frm.getAsk().equals("truncar")) {
            int idUniversidad = frm.getCboUniversidad();

            ConexionMySQL conexion = new ConexionMySQL();
            try {
                CallableStatement sp = conexion.PrepararSP("sp_TruncarUni(?)");
                sp.setInt(1, idUniversidad);
                sp.execute();
            } catch (SQLException ex) {
                System.err.println("Truncar " + ex.getMessage());
            } finally {
                conexion.Desconectar();
            }

        }

        if (frm.getAsk().equals("eficacia")) {
            return mapping.findForward(EFICACIA);
        }

        if (frm.getAsk().equals("cboNivel") || frm.getAsk().equals("agregar")) {
            frm.setProgramasEducativos(
                    obtenerDatos("Select pe.id_pe, CONCAT(n.abreviatura, ' - ', pe.nombre_programa,' "
                    + "(', pe.version,')') as nombre from programa_educativo pe inner join nivel_pe n on n.id_nivel = "
                    + "pe.id_nivel where pe.activo = 1 and pe.id_pe and pe.id_nivel="
                    + frm.getCbonivel(), "pe.id_pe", "nombre", "pe",
                    frm.getSeleccionados()));
        } else {
            frm.setProgramasEducativos(new ArrayList());
        }
        return mapping.findForward(SUCCESS);
    }

    private int getIdModeloEducativo(String modelo) {
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet rs = conexion.Consultar("Select id_modelo from modelos where descripcion = '".concat(modelo).concat("'"));
            rs.next();
            return rs.getInt("id_modelo");
        } catch (SQLException ex) {
            System.err.println("ERROR GET ID MODELO EDUCATIVO " + ex.getMessage());
            return -1;
        } finally {
            conexion.Desconectar();
        }
    }

    private int getIdNivelEducativo(String nivel) {
        ConexionMySQL conexion = new ConexionMySQL();
        try {

            ResultSet rs = conexion.Consultar("Select id_nivel from nivel_pe where abreviatura = '".concat(nivel).concat("'"));
             rs.next();
            return rs.getInt("id_nivel");
        } catch (SQLException ex) {
            System.err.println("ERROR GET ID NIVEL EDUCATIVO " + ex.getMessage());
            return -1;
        } finally {
            conexion.Desconectar();
        }
    }

    private void Guardar(int periodo,
            int universidad,
            int estado,
            String municipio,
            String colonia,
            String calle,
            String numero,
            String cp,
            String pe,
            int nuevo_in,
            int matTotal,
            int ptc,
            int pAsignatura,
            String modelosE,
            String servSel,
            String edifSel,
            String aniosInc,
            String nuevoing,
            String matriculatot,
            String clasificacion,
            registroUniversidadForm frm) {

        ConexionMySQL conexion = new ConexionMySQL();
        ResultSet rs;
        String sql = "";

        /*MODIFICACIÓN DE LA FECHA DE LA UNIVERSIDAD,
         * EL RFC Y
         * LA ABREVIATURA
         */
        try {
            conexion.Conectar();
            sql = "UPDATE universidades SET fecha_acreditacion='".concat(String.valueOf(frm.getFechaCreacion())).concat("', ")
                    .concat(" rfc='").concat(frm.getRfc()).concat("',")
                    .concat(" abreviatura='").concat(frm.getAbreviatura()).concat("' ")
                    .concat(" WHERE id_universidad=").concat(String.valueOf(universidad));
            conexion.Modificar(sql);
        } catch (SQLException ex) {
            System.err.println("ERRORBD: cambiando la fecha de creacion de la universidad: " + ex.getMessage());
        } finally {
            conexion.Desconectar();
        }


        try {
            /* Guardar la Direccion de la Universidad*/
            rs = conexion.Consultar("Select id_universidad from domicilios_universidad where id_periodo = "
                    .concat(String.valueOf(periodo)).concat(" and id_universidad = ").concat(String.valueOf(universidad)));
            conexion.Modificar("Update domicilios_universidad set activo = 0 where id_universidad = ".concat(String.valueOf(universidad)));
            String estado2 = "";
            if (frm.getCboEstado() == -1) {
                estado2 = "NULL";
            } else {
                estado2 = String.valueOf(frm.getCboEstado());
            }
            if (rs.next()) {
                conexion.Modificar("Update domicilios_universidad set activo = 1, id_estado = "
                        .concat(estado2).concat(", municipio = '").concat(municipio)
                        .concat("', colonia = '").concat(colonia).concat("', calle = '").concat(calle)
                        .concat("', numero = '").concat(numero).concat("', codigo_postal = '").concat(cp)
                        .concat("' where id_periodo = ").concat(String.valueOf(periodo))
                        .concat(" and id_universidad = ").concat(String.valueOf(universidad)));
            } else {
                conexion.Insertar("Insert domicilios_universidad VALUES('".concat(String.valueOf(periodo)).concat("', '")
                        .concat(String.valueOf(universidad)).concat("', ").concat(estado2).concat(", '")
                        .concat(municipio).concat("', '").concat(colonia).concat("', '").concat(calle).concat("', '")
                        .concat(numero).concat("', '").concat(cp).concat("',1)"));
            }
        } catch (Exception ex) {
            System.err.println("Error guardando la direccion de la universidad: " + ex.getMessage());
        } finally {
            conexion.Desconectar();
        }



        /*
         * 
         * GUARDADO DE LOS DATOS DE CONTACTO
         * 
         */

        /*RECTOR*/
        try {
            //System.err.println("\n\n\n\n\n\n\n ID RESPONSABLE : " + frm.getTxtRecId());
            sql = "UPDATE responsables SET "
                    .concat("nombre='").concat(frm.getTxtRecNombre()).concat("', ")
                    .concat("apaterno='").concat(frm.getTxtRecApaterno()).concat("', ")
                    .concat("amaterno='").concat(frm.getTxtRecAmaterno()).concat("', ");
                    if(frm.getTxtRecLada().equals("")){
                            sql+="telefono='".concat(frm.getTxtRecTelefono());
                     }else{
                         sql+="telefono='".concat(frm.getTxtRecLada()+"#"+frm.getTxtRecTelefono());
                     }
                    
                    sql+="', mail='".concat(frm.getTxtRecCorreo())
                    .concat("' WHERE id_responsable=").concat(frm.getTxtRecId())
                    .concat(" AND id_universidad=").concat(String.valueOf(universidad))
                    .concat(" AND tipoResponsable='RU'");
//                    System.err.println("update rector "+sql);
            conexion.Modificar(sql);
        } catch (Exception ex) {
            System.err.println("ERROR GUARDANDO EL RECTOR " + ex.getMessage());
        } finally {
            conexion.Desconectar();
        }

        /*RESPONSABLE*/
        try {
            sql = "UPDATE responsables SET "
                    .concat("nombre='").concat(frm.getTxtResNombre()).concat("', ")
                    .concat("apaterno='").concat(frm.getTxtResApaterno()).concat("', ")
                    .concat("amaterno='").concat(frm.getTxtResAmaterno()).concat("', ")
                    .concat("cargo='").concat(frm.getTxtResCargo()).concat("', ");
                     if(frm.getTxtResLada().equals("")){
                            sql+="telefono='".concat(frm.getTxtResTelefono());
                     }else{
                         sql+="telefono='".concat(frm.getTxtResLada()+"#"+frm.getTxtResTelefono());
                     }
                    sql+="',mail='".concat(frm.getTxtResCorreo())
                    .concat("' WHERE id_responsable=").concat(frm.getTxtResId())
                    .concat(" AND id_universidad=").concat(String.valueOf(universidad))
                    .concat(" AND tipoResponsable='RC'");
//                    System.err.println("update responsable "+sql);
            conexion.Modificar(sql);
        } catch (Exception ex) {
            System.err.println("ERROR GUARDANDO EL RC " + ex.getMessage());
        } finally {
            conexion.Desconectar();
        }

        /*CAPTURISTA*/
        try {
            sql = "UPDATE responsables SET "
                    .concat("nombre='").concat(frm.getTxtCapNombre()).concat("', ")
                    .concat("apaterno='").concat(frm.getTxtCapApaterno()).concat("', ")
                    .concat("amaterno='").concat(frm.getTxtCapAmaterno()).concat("', ")
                    .concat("cargo='").concat(frm.getTxtCapCargo()).concat("', ");
                    if(frm.getTxtCapLada().equals("")){
                           sql+="telefono='".concat(frm.getTxtCapTelefono());
                    }else{
                        sql+="telefono='".concat(frm.getTxtCapLada()+"#"+frm.getTxtCapTelefono());
                    }
                      sql+="', ".concat("mail='").concat(frm.getTxtCapCorreo())
                    .concat("' WHERE id_responsable=").concat(frm.getTxtCapId())
                    .concat(" AND id_universidad=").concat(String.valueOf(universidad))
                    .concat(" AND tipoResponsable='CU'");
            conexion.Modificar(sql);
            sql = "UPDATE usuarios SET id_responsable=".concat(frm.getTxtCapId())
                    .concat(" WHERE id_univ=").concat(String.valueOf(universidad));
//            System.err.println("update responsable "+sql);
            conexion.Modificar(sql);
        } catch (Exception ex) {
            System.err.println("ERROR GUARDANDO EL CAPTURISTA " + ex.getMessage());
        } finally {
            conexion.Desconectar();
        }



        /*
         * DATOS ACADEMICOS DE LA UNIVERSIDAD
         */
        try {
            /* Guardar Datos de los profesores*/
            rs = conexion.Consultar("Select * from datos_universidad where id_periodo = ".concat(String.valueOf(periodo)).concat(" and id_universidad = ").concat(String.valueOf(universidad)));
            if (rs.next()) {
                conexion.Modificar("Update datos_universidad set prof_tc = ".concat(String.valueOf(ptc)).concat(", prof_asig = ")
                        .concat(String.valueOf(pAsignatura)).concat(" where id_periodo = ").concat(String.valueOf(periodo))
                        .concat(" and id_universidad = ").concat(String.valueOf(universidad)));
            } else {
                conexion.Insertar("Insert datos_universidad VALUES(".concat(String.valueOf(universidad))
                        .concat(", ").concat(String.valueOf(periodo)).concat(", 0, 0, ").concat(String.valueOf(ptc))
                        .concat(", ").concat(String.valueOf(pAsignatura)).concat(")"));
            }
        } catch (Exception ex2) {
            System.err.println("ERROR registroUniversidadAction.java guardando datos de los profesores" + ex2.getMessage());
        } finally {
            conexion.Desconectar();
        }
        try {
            /*Guardar modelos Educativos*/
            String[] nmodelosEducativos = modelosE.split("-");
            conexion.Eliminar("Delete from modelos_universidad where id_universidad = ".concat(String.valueOf(universidad))
                    .concat(" and id_periodo=").concat(String.valueOf(periodo)));
            for (int i = 1; i <= nmodelosEducativos.length - 1; i++) {
                sql = "Insert into modelos_universidad values";
                String[] modelosEducativos = nmodelosEducativos[i].split(",");
                for (int j = 0; j <= modelosEducativos.length - 2; j++) {
                    if (j == modelosEducativos.length - 2) {
                        sql = sql.concat("(").concat(String.valueOf(universidad)).concat(",").concat(String.valueOf(periodo)).concat(",")
                                .concat(String.valueOf(getIdNivelEducativo(modelosEducativos[0]))).concat(",")
                                .concat(String.valueOf(getIdModeloEducativo(modelosEducativos[j + 1]))).concat(")");
                    } else {
                        sql = sql.concat("(").concat(String.valueOf(universidad)).concat(",")
                                .concat(String.valueOf(periodo)).concat(",").concat(String.valueOf(getIdNivelEducativo(modelosEducativos[0])))
                                .concat(",").concat(String.valueOf(getIdModeloEducativo(modelosEducativos[j + 1]))).concat("),");
                    }
                }
               // System.out.println("insert modelos "+sql);
                conexion.Insertar(sql);
            }
        } catch (Exception ex3) {
            System.err.println("ERRORBD: Guardando Modelos educativos " + ex3.getMessage());
        } finally {
            conexion.Desconectar();
        }

        /*SERVICIOS*/
        try {
            /*Guardar Servicios*/
            if (!servSel.equals("")) { // eliminará y agregará de nuevo la lista
                String[] servicios = servSel.split(",");
                conexion.Eliminar("Delete from servicios_universidad where id_universidad = ".concat(String.valueOf(universidad))
                        .concat(" and id_periodo=").concat(String.valueOf(periodo)));
                sql = "Insert into servicios_universidad values";
                for (int i = 0; i <= servicios.length - 1; i++) {
                    if (i == servicios.length - 1) {
                        sql = sql.concat("(").concat(String.valueOf(universidad)).concat(",").concat(String.valueOf(periodo))
                                .concat(",").concat(servicios[i]).concat(")");
                    } else {
                        sql = sql.concat("(").concat(String.valueOf(universidad)).concat(",").concat(String.valueOf(periodo))
                                .concat(",").concat(servicios[i]).concat("),");
                    }
                }
                conexion.Insertar(sql);
            } else { //solo eliminará la lista
                conexion.Eliminar("Delete from servicios_universidad where id_universidad = ".concat(String.valueOf(universidad))
                        .concat(" and id_periodo=").concat(String.valueOf(periodo)));
            }
        } catch (Exception ex4) {
            System.err.println("ERRORBD: Guardar Servicios " + sql + ex4.getMessage());
        } finally {
            conexion.Desconectar();
        }

        /*EDIFICIOS*/
        try {
            /*Guardar Edificios*/
            if (!edifSel.equals("")) {
                String[] edificios = edifSel.split(",");
                conexion.Eliminar("Delete from edificios_universidad where id_universidad = ".concat(String.valueOf(universidad))
                        .concat(" and id_periodo=").concat(String.valueOf(periodo)));
                sql = "Insert into edificios_universidad values";
                for (int i = 0; i <= edificios.length - 1; i++) {
                    if (i == edificios.length - 1) {
                        sql = sql.concat("(").concat(String.valueOf(universidad)).concat(",").concat(String.valueOf(periodo)).concat(",").concat(edificios[i]).concat(")");
                    } else {
                        sql = sql.concat("(").concat(String.valueOf(universidad)).concat(",").concat(String.valueOf(periodo)).concat(",").concat(edificios[i]).concat("),");
                    }
                }
                conexion.Insertar(sql);
            } else {
                conexion.Eliminar("Delete from edificios_universidad where id_universidad = ".concat(String.valueOf(universidad))
                        .concat(" and id_periodo=").concat(String.valueOf(periodo)));
            }
        } catch (Exception ex5) {
            System.err.println("ERRORBD: Guardar edificios " + ex5.getMessage());
        } finally {
            conexion.Desconectar();
        }

        /*PROGRAMAS EDUCATIVOS */
        try {
            //conexion.Eliminar("Delete from pe_universidad where id_universidad = ".concat(String.valueOf(universidad)));
            conexion.Eliminar("Delete from pe_universidad where id_universidad = "
                    .concat(String.valueOf(universidad)).concat(" AND id_periodo=").concat(String.valueOf(periodo)));
        } catch (Exception x) {
            System.err.println("ERROR ELIMINANDO PROGRAMAS EDUCATIVOS" + x.getMessage());
        } finally {
            conexion.Desconectar();
        }
        if (pe.length() != 0) {
            /*Guardar Programas Educativos*/
            String[] programasEducativos = pe.split(",");
            String[] aniosIncor = aniosInc.split(",");
            String[] nuevoingre = nuevoing.split(",");
            String[] matriculato = matriculatot.split(",");
            String[] clasificaci = clasificacion.split(",");
            
            if (programasEducativos.length > 0) {
                for (int i = 0; i <= programasEducativos.length - 1; i++) {
                    for (int j = Integer.parseInt(aniosIncor[i]); j <= (1990 + periodo); j++) {
                        try {
                            sql = "UPDATE pe_universidad SET activo=0 WHERE id_periodo="
                                    .concat(String.valueOf(j - 1990))
                                    .concat(" AND id_universidad=").concat(String.valueOf(universidad)).concat(" AND id_pe=").concat(programasEducativos[i]);
                           // System.out.println("sql update pe_universidad " + sql);
                            if (conexion.Modificar(sql) == 0) {
                                sql = "INSERT INTO pe_universidad VALUES (".concat(String.valueOf(universidad)).concat(",").concat(String.valueOf(j - 1990)).concat(",").concat(programasEducativos[i]).concat(",").concat(aniosIncor[i]);
                                
                                if ((j - 1990) == periodo) {
                                    sql = sql.concat(",1");
                                } else {
                                    sql = sql.concat(",0");
                                }
                                if(clasificaci[i].equals("0")){
                                    clasificaci[i]="TB";
                                }
                                if(clasificaci[i].equals("1")){
                                    clasificaci[i]="TN";
                                }
                                 sql = sql.concat(","+nuevoingre[i]+", "+matriculato[i]+", '"+clasificaci[i]+"' )");
                                 //System.out.println("sql insert pe_universidad " + sql);
                                   conexion.Insertar(sql);
                            }else{
                               // System.errprintln("INFO : El programa educativo : '" + programasEducativos[i] +  "' ya se encontraba en el periodo : '"+ String.valueOf(j - 1990) +"'");
                            }
                        } catch (Exception ex) {
                            System.err.println("ERROR GUARDANDO PROGRAMAS EDUCATIVOS " + ex.getMessage());
                        } finally {
                            conexion.Desconectar();
                        }
                    }
                }
            }
        }
    }

    private ArrayList obtenerDatos(String consulta, String id, String nombre, String tipo, String param) {
        ArrayList campos = new ArrayList();
        ArrayList act = new ArrayList();
        if (param != null && !param.equals("")) {
            act.addAll(Arrays.asList(param.split(",")));
        }
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet rs = conexion.Consultar(consulta);
            rs.last();
            int j = rs.getRow();
            rs.beforeFirst();
            for (int i = 0; i < j; i++) {
                rs.next();
                if (tipo.equals("pe")) {
                    String idd = rs.getString(id);
                    if (act.contains(idd)) {
                    } else {
                        campos.add(new Coleccion(rs.getInt(id), rs.getString(nombre)));
                    }
                } else {
                    campos.add(new Coleccion(rs.getInt(id), rs.getString(nombre)));
                }
            }
            return campos;
        } catch (SQLException ex) {
            System.err.println("ERROR OBTENERDATOS " + ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        return campos;
    }
}