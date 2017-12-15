/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joshua, Cuauhtemoc Medina Muñoz
 */
public class registroUniversidadForm extends org.apache.struts.action.ActionForm {

    //Atributos Formulario
    private String name;
    private String[] lstServiciosSeleccionados;
    private int cbonivel, cboEstado, cboArea, cboFamilia, cboPE, cboclasificacionPE,
            alNuevoIngreso, matTotal, profTC, profAsign, cboUniversidad, clasificacionPE, nuevo_ingresoPE, matricula_totalPE;
    private String municipio = "", colonia = "", calle = "", 
            numero = "", cp = "",
            respCaptura = "",
            seleccionados = "", 
            lstPe, lstAniosInc = "", 
            lstAi, lstCe, lstOr, lstClas="", lstNuev="",lstMat="",
            ask = "", modelos = "", 
            modSeleccionados = "", 
            servSeleccionados = "", 
            edifSeleccionados = "",
            sigla = "", nombreOrg = "", pais = "", nombreCertificacion = "", fechaInicioC = "", fechaFinC = "";
    private ArrayList estados,
            areas = new ArrayList(), familias = new ArrayList(), programasEducativos = new ArrayList(),
            peSeleccionados = new ArrayList(), aniosIncSeleccionados = new ArrayList(),clasificacionSeleccionados = new ArrayList(), 
            nuevoingresoSeleccionados=new ArrayList(), matriculatotalSeleccionados=new ArrayList(),
            servicios = new ArrayList(), edificios = new ArrayList(),
            universidades = new ArrayList(), 
            lstEdificiosSeleccionados = new ArrayList(), 
            niveles = new ArrayList();
//  
    private List lstSeleccionados = new ArrayList();
    private boolean orgInt = false, certUni = false;
    /*
     * **********************************************************
     * MODIFICACIONES A PETICIÓN DE LA CGUT
     * **********************************************************
     */
    private String abreviatura = "";
    private String rfc = "";
    private String fechaCreacion = "";
    private String txtRecId = "";
    private String txtRecNombre = "";
    private String txtRecApaterno = "";
    private String txtRecAmaterno = "";
    private String txtRecLada = "";
    private String txtRecTelefono = "";
    private String txtRecCorreo = "";
    private String txtResId = "";
    private String txtResNombre = "";
    private String txtResApaterno = "";
    private String txtResAmaterno = "";
    private String txtResCargo = "";
    private String txtResLada = "";
    private String txtResTelefono = "";
    private String txtResCorreo = "";
    private String txtCapId = "";
    private String txtCapNombre = "";
    private String txtCapApaterno = "";
    private String txtCapAmaterno = "";
    private String txtCapCargo = "";
    private String txtCapLada = "";
    private String txtCapTelefono = "";
    private String txtCapCorreo = "";
    private String universidad = "";

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String idUni) {
        this.universidad = idUni;
    }


    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public int getCbonivel() {
        return cbonivel;
    }

    public void setCbonivel(int cbonivel) {
        this.cbonivel = cbonivel;
    }

    public String getTxtResId() {
        return txtResId;
    }

    public void setTxtResId(String txtResId) {
        this.txtResId = txtResId;
    }

    public String getTxtCapId() {
        return txtCapId;
    }

    public void setTxtCapId(String txtCapId) {
        this.txtCapId = txtCapId;
    }

    public String getTxtRecId() {
        return txtRecId;
    }

    public void setTxtRecId(String txRecId) {
        this.txtRecId = txRecId;
    }

    public String getTxtRecNombre() {
        return txtRecNombre;
    }

    public void setTxtRecNombre(String txtRecNombre) {
        this.txtRecNombre = txtRecNombre;
    }

    public String getTxtRecApaterno() {
        return txtRecApaterno;
    }

    public void setTxtRecApaterno(String txtRecApaterno) {
        this.txtRecApaterno = txtRecApaterno;
    }

    public String getTxtRecAmaterno() {
        return txtRecAmaterno;
    }

    public void setTxtRecAmaterno(String txtRecAmaterno) {
        this.txtRecAmaterno = txtRecAmaterno;
    }

    public String getTxtRecTelefono() {
        return txtRecTelefono;
    }

    public void setTxtRecTelefono(String txtRecTelefono) {
        this.txtRecTelefono = txtRecTelefono;
    }
     public String getTxtRecLada() {
        return txtRecLada;
    }

    public void setTxtRecLada(String txtRecLada) {
        this.txtRecLada = txtRecLada;
    }

    public String getTxtRecCorreo() {
        return txtRecCorreo;
    }

    public void setTxtRecCorreo(String txtRecCorreo) {
        this.txtRecCorreo = txtRecCorreo;
    }

    public String getTxtResNombre() {
        return txtResNombre;
    }

    public void setTxtResNombre(String txtResNombre) {
        this.txtResNombre = txtResNombre;
    }

    public String getTxtResApaterno() {
        return txtResApaterno;
    }

    public void setTxtResApaterno(String txtResApaterno) {
        this.txtResApaterno = txtResApaterno;
    }

    public String getTxtResAmaterno() {
        return txtResAmaterno;
    }

    public void setTxtResAmaterno(String txtResAmaterno) {
        this.txtResAmaterno = txtResAmaterno;
    }

    public String getTxtResCargo() {
        return txtResCargo;
    }

    public void setTxtResCargo(String txtResCargo) {
        this.txtResCargo = txtResCargo;
    }

    public String getTxtResTelefono() {
        return txtResTelefono;
    }

    public void setTxtResTelefono(String txtResTelefono) {
        this.txtResTelefono = txtResTelefono;
    }
    
    public String getTxtResLada() {
        return txtResLada;
    }

    public void setTxtResLada(String txtResLada) {
        this.txtResLada = txtResLada;
    }

    public String getTxtResCorreo() {
        return txtResCorreo;
    }

    public void setTxtResCorreo(String txtResCorreo) {
        this.txtResCorreo = txtResCorreo;
    }

    public String getTxtCapNombre() {
        return txtCapNombre;
    }

    public void setTxtCapNombre(String txtCapNombre) {
        this.txtCapNombre = txtCapNombre;
    }

    public String getTxtCapApaterno() {
        return txtCapApaterno;
    }

    public void setTxtCapApaterno(String txtCapApaterno) {
        this.txtCapApaterno = txtCapApaterno;
    }

    public String getTxtCapAmaterno() {
        return txtCapAmaterno;
    }

    public void setTxtCapAmaterno(String txtCapAmaterno) {
        this.txtCapAmaterno = txtCapAmaterno;
    }

    public String getTxtCapCargo() {
        return txtCapCargo;
    }

    public void setTxtCapCargo(String txtCapCargo) {
        this.txtCapCargo = txtCapCargo;
    }

    public String getTxtCapTelefono() {
        return txtCapTelefono;
    }

    public void setTxtCapTelefono(String txtCapTelefono) {
        this.txtCapTelefono = txtCapTelefono;
    }
     public String getTxtCapLada() {
        return txtCapLada;
    }

    public void setTxtCapLada(String txtCapLada) {
        this.txtCapLada = txtCapLada;
    }

    public String getTxtCapCorreo() {
        return txtCapCorreo;
    }

    public void setTxtCapCorreo(String txtCapCorreo) {
        this.txtCapCorreo = txtCapCorreo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public final void setFechaCreacionInicial() {
        ConexionMySQL con = new ConexionMySQL();
        try {
            con.Conectar();
            String consulta = "SELECT fecha_acreditacion FROM universidades WHERE id_universidad="
                    .concat(String.valueOf(universidad));
            ResultSet rs = con.Consultar(consulta);
            while (rs.next()) {
                this.fechaCreacion = rs.getString("fecha_acreditacion");
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Error en la consulta de la fecha de la universidad: " + ex.getMessage());
        } finally {
            con.Desconectar();
        }
    }

    /*SET RECTOR INICIAL*/
    private void setRector() {
        ConexionMySQL con = new ConexionMySQL();
        ResultSet rs, rs2;
        String consulta = "";
        this.txtRecId = "";
        try {
            consulta = "SELECT id_responsable FROM responsables_universidad WHERE id_universidad=".concat(String.valueOf(universidad))
                    .concat(" AND id_periodo=").concat(String.valueOf(getPeriodoActual())).concat(" AND tipo='RU'");
           // System.out.println("consulta 331 "+consulta);
            rs = con.Consultar(consulta);
            if (rs.next()) {
                //se llena con el registro del rector del periodo actual
                this.txtRecId = rs.getString("id_responsable");
                consulta = "SELECT * FROM responsables WHERE id_responsable="
                        .concat(this.txtRecId)
                        .concat(" AND id_universidad=")
                        .concat(String.valueOf(universidad));
              //  System.out.println("consulta 340 "+consulta);
                rs2 = con.Consultar(consulta);
                if (rs2.next()) {
                    this.txtRecNombre = rs2.getString("nombre");
                    this.txtRecApaterno = rs2.getString("apaterno");
                    this.txtRecAmaterno = rs2.getString("amaterno");
                    String[] parts = rs2.getString("telefono").split("#"); 
                    
                    if(parts.length>1){
                        this.txtRecTelefono = parts[1];
                        this.txtRecLada = parts[0];
                    }else{
                        this.txtRecTelefono = rs2.getString("telefono");
                    }
                    
                    this.txtRecCorreo = rs2.getString("mail");
                }
            } else if (this.txtRecId.equals("")) {
                /*
                 * quiere decir que no había registro del rector en el periodo actual
                 * por lo tanto se buscará de nuevo pero el periodo anterior
                 */
                consulta = "SELECT id_responsable FROM responsables_universidad WHERE id_universidad=".concat(String.valueOf(universidad))
                        .concat(" AND id_periodo=").concat(String.valueOf(getPeriodoActual() - 1)).concat(" AND tipo='RU'");
              //  System.out.println("consulta 364 "+consulta);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    //se llena con el registro del rector del periodo actual
                    this.txtRecId = rs.getString("id_responsable");
                    consulta = "SELECT * FROM responsables WHERE id_responsable="
                            .concat(this.txtRecId)
                            .concat(" AND id_universidad=")
                            .concat(String.valueOf(universidad));
                   // System.out.println("consulta 373 "+consulta);
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        this.txtRecNombre = rs2.getString("nombre");
                        this.txtRecApaterno = rs2.getString("apaterno");
                        this.txtRecAmaterno = rs2.getString("amaterno");
                        String[] parts = rs2.getString("telefono").split("#"); 
                    
                        if(parts.length>1){
                            this.txtRecTelefono = parts[1];
                            this.txtRecLada = parts[0];
                        }else{
                            this.txtRecTelefono = rs2.getString("telefono");
                        }
                    
                        this.txtRecCorreo = rs2.getString("mail");
                    }
                    consulta = "INSERT INTO responsables_universidad () VALUES ("
                            .concat(String.valueOf(universidad)).concat(String.valueOf(","))
                            .concat(String.valueOf(getPeriodoActual())).concat(",")
                            .concat(this.txtRecId)
                            .concat(",'RU')");
                  //  System.out.println("insert 395 "+consulta);
                    con.Insertar(consulta);
                } else {
                    consulta = "INSERT INTO responsables () VALUES(NULL,"
                            .concat(String.valueOf(universidad))
                            .concat(",'','','','Rector','','',NULL,NULL,1,'RU')");
                   // System.out.println("insertar 401 "+consulta);
                    con.Insertar(consulta);
                    consulta = "SELECT id_responsable FROM responsables WHERE id_universidad=".concat(String.valueOf(universidad))
                            .concat(" AND cargo='Rector' AND tipoResponsable='RU' AND activo='1'");
                    rs = con.Consultar(consulta);
                    if (rs.next()) {
                        this.txtRecId = rs.getString("id_responsable");
                    }
                    consulta = "INSERT INTO responsables_universidad () VALUES ("
                            .concat(String.valueOf(universidad)).concat(String.valueOf(","))
                            .concat(String.valueOf(getPeriodoActual())).concat(",")
                            .concat(this.txtRecId)
                            .concat(",'RU')");
                    con.Insertar(consulta);
                }
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Cargando datos del rector: setRector(); " + ex.getMessage());
        } finally {
            con.Desconectar();
        }
    }

    /*SET RESPONSABLE DE CONTACTO INICIAL*/
    private void setResponsable() {
        ConexionMySQL con = new ConexionMySQL();
        ResultSet rs, rs2;
        String consulta = "";
        this.txtResId = "";
        try {
            consulta = "SELECT id_responsable FROM responsables_universidad WHERE id_universidad=".concat(String.valueOf(universidad))
                    .concat(" AND id_periodo=").concat(String.valueOf(getPeriodoActual())).concat(" AND tipo='RC'");
            rs = con.Consultar(consulta);
            if (rs.next()) {
                //se llena con el registro del rector del periodo actual
                this.txtResId = rs.getString("id_responsable");
                consulta = "SELECT * FROM responsables WHERE id_responsable="
                        .concat(this.txtResId)
                        .concat(" AND id_universidad=")
                        .concat(String.valueOf(universidad));
                rs2 = con.Consultar(consulta);
                if (rs2.next()) {
                    this.txtResNombre = rs2.getString("nombre");
                    this.txtResApaterno = rs2.getString("apaterno");
                    this.txtResAmaterno = rs2.getString("amaterno");
                    this.txtResCargo = rs2.getString("cargo");
                    String[] parts = rs2.getString("telefono").split("#"); 
                    
                    if(parts.length>1){
                        this.txtResTelefono = parts[1];
                        this.txtResLada = parts[0];
                    }else{
                        this.txtResTelefono = rs2.getString("telefono");
                    }
                    this.txtResCorreo = rs2.getString("mail");
                }
            } else if (this.txtResId.equals("")) {
                /*
                 * quiere decir que no había registro del rector en el periodo actual
                 * por lo tanto se buscará de nuevo pero el periodo anterior
                 */
                consulta = "SELECT id_responsable FROM responsables_universidad WHERE id_universidad=".concat(String.valueOf(universidad))
                        .concat(" AND id_periodo=").concat(String.valueOf(getPeriodoActual() - 1)).concat(" AND tipo='RC'");
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    //se llena con el registro del rector del periodo actual
                    this.txtResId = rs.getString("id_responsable");
                    consulta = "SELECT * FROM responsables WHERE id_responsable=".concat(rs.getString("id_responsable"));
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        this.txtResNombre = rs2.getString("nombre");
                        this.txtResApaterno = rs2.getString("apaterno");
                        this.txtResAmaterno = rs2.getString("amaterno");
                        this.txtResCargo = rs2.getString("cargo");
                          String[] parts = rs2.getString("telefono").split("#"); 
                    
                        if(parts.length>1){
                            this.txtResTelefono = parts[1];
                            this.txtResLada = parts[0];
                        }else{
                            this.txtResTelefono = rs2.getString("telefono");
                         }   
                        this.txtResCorreo = rs2.getString("mail");
                    }
                    consulta = "INSERT INTO responsables_universidad () VALUES ("
                            .concat(String.valueOf(universidad)).concat(String.valueOf(","))
                            .concat(String.valueOf(getPeriodoActual())).concat(",")
                            .concat(this.txtResId)
                            .concat(",'RC')");
                    con.Insertar(consulta);
                } else {
                   consulta = "INSERT INTO responsables () VALUES(NULL,"
                            .concat(String.valueOf(universidad))
                            .concat(",'','','','','','',NULL,NULL,1,'RC')");
                    con.Insertar(consulta);
                    consulta = "SELECT id_responsable FROM responsables WHERE id_universidad=".concat(String.valueOf(universidad))
                            .concat("  AND tipoResponsable='RC' AND activo='1'");
                    rs = con.Consultar(consulta);
                    if (rs.next()) {
                        this.txtResId = rs.getString("id_responsable");
                    }
                    consulta = "INSERT INTO responsables_universidad () VALUES ("
                            .concat(String.valueOf(universidad)).concat(String.valueOf(","))
                            .concat(String.valueOf(getPeriodoActual())).concat(",")
                            .concat(this.txtResId)
                            .concat(",'RC')");
                    con.Insertar(consulta);
                }
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Cargando datos del responsable: setResponsable(); " + ex.getMessage());
        } finally {
            con.Desconectar();
        }
    }

    /*SET RESPONSABLE DE CONTACTO INICIAL*/
    private void setCapturista() {
        ConexionMySQL con = new ConexionMySQL();
        ResultSet rs, rs2;
        String consulta = "";
        this.txtCapId = "";
        try {
            consulta = "SELECT id_responsable FROM responsables_universidad WHERE id_universidad=".concat(String.valueOf(universidad))
                    .concat(" AND id_periodo=").concat(String.valueOf(getPeriodoActual())).concat(" AND tipo='CU'");
            rs = con.Consultar(consulta);
            if (rs.next()) {
                //se llena con el registro del rector del periodo actual
                this.txtCapId = rs.getString("id_responsable");
                consulta = "SELECT * FROM responsables WHERE id_responsable="
                        .concat(this.txtCapId)
                        .concat(" AND id_universidad=")
                        .concat(String.valueOf(universidad));
                rs2 = con.Consultar(consulta);
                if (rs2.next()) {
                    this.txtCapNombre = rs2.getString("nombre");
                    this.txtCapApaterno = rs2.getString("apaterno");
                    this.txtCapAmaterno = rs2.getString("amaterno");
                    this.txtCapCargo = rs2.getString("cargo");
                    String[] parts = rs2.getString("telefono").split("#"); 
                    
                    if(parts.length>1){
                        this.txtCapTelefono = parts[1];
                        this.txtCapLada = parts[0];
                    }else{
                        this.txtCapTelefono = rs2.getString("telefono");
                    }
                    this.txtCapCorreo = rs2.getString("mail");
                }
            } else if (this.txtCapId.equals("")) {
                /*
                 * quiere decir que no había registro del capturista en el periodo actual
                 * por lo tanto se buscará de nuevo pero el periodo anterior
                 */
                consulta = "SELECT id_responsable FROM responsables_universidad WHERE id_universidad=".concat(String.valueOf(universidad))
                        .concat(" AND id_periodo=").concat(String.valueOf(getPeriodoActual() - 1)).concat(" AND tipo='CU'");
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    //se llena con el registro del rector del periodo actual
                    this.txtCapId = rs.getString("id_responsable");
                    consulta = "SELECT * FROM responsables WHERE id_responsable=".concat(rs.getString("id_responsable"));
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        this.txtCapNombre = rs2.getString("nombre");
                        this.txtCapApaterno = rs2.getString("apaterno");
                        this.txtCapAmaterno = rs2.getString("amaterno");
                        this.txtCapCargo = rs2.getString("cargo");
                        String[] parts = rs2.getString("telefono").split("#"); 
                    
                        if(parts.length>1){
                            this.txtCapTelefono = parts[1];
                            this.txtCapLada = parts[0];
                        }else{
                            this.txtCapTelefono = rs2.getString("telefono");
                        }
                        this.txtCapCorreo = rs2.getString("mail");
                    }
                    consulta = "INSERT INTO responsables_universidad () VALUES ("
                            .concat(String.valueOf(universidad)).concat(String.valueOf(","))
                            .concat(String.valueOf(getPeriodoActual())).concat(",")
                            .concat(this.txtCapId)
                            .concat(",'CU')");
                    con.Insertar(consulta);
                } else {
                   consulta = "INSERT INTO responsables () VALUES(NULL,"
                            .concat(String.valueOf(universidad))
                            .concat(",'','','','','','',NULL,NULL,1,'CU')");
                    con.Insertar(consulta);
                    consulta = "SELECT id_responsable FROM responsables WHERE id_universidad=".concat(String.valueOf(universidad))
                            .concat("  AND tipoResponsable='CU' AND activo='1'");
                    rs = con.Consultar(consulta);
                    if (rs.next()) {
                        this.txtCapId = rs.getString("id_responsable");
                    }
                    consulta = "INSERT INTO responsables_universidad () VALUES ("
                            .concat(String.valueOf(universidad)).concat(String.valueOf(","))
                            .concat(String.valueOf(getPeriodoActual())).concat(",")
                            .concat(this.txtCapId)
                            .concat(",'CU')");
                    con.Insertar(consulta);
                    /*MODIFICAR EL RESPONSABLE DE LA CUENTA DE LA UT*/
                    consulta = "UPDATE usuarios SET id_responsable=".concat(this.txtCapId)
                            .concat(" WHERE id_univ=").concat(universidad);
                    con.Modificar(consulta);
                }
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Cargando datos del rector: setCapturista(); " + ex.getMessage());
        } finally {
            con.Desconectar();
        }
    }

    /*
     * **********************************************************
     *  FIN MODIFICACIONES A PETICIÓN DE LA CGUT
     * **********************************************************
     */
    public String getLstAi() {
        return lstAi;
    }

    public void setLstAi(String lstAi) {
        this.lstAi = lstAi;
    }
     public String getLstNuev() {
        return lstNuev;
    }

    public void setLstNuev(String lstNuev) {
        this.lstNuev = lstNuev;
    }

     public String getLstClas() {
        return lstClas;
    }

    public void setLstClas(String lstClas) {
        this.lstClas = lstClas;
    }
    
      public String getLstNuevoIngreso() {
        return lstNuev;
    }

    public void setLstNuevoIngreso(String lstNuevoIngreso) {
        this.lstNuev = lstNuevoIngreso;
    }
      public String getLstMatriculaTotal() {
        return lstMat;
    }

    public void setLstMatriculaTotal(String lstMatriculaTotal) {
        this.lstMat = lstMatriculaTotal;
    }
    
      public String getLstClasificacion() {
        return lstClas;
    }

    public void setLstClasificacion(String lstClasificacion) {
        this.lstClas = lstClasificacion;
    }
    
    public String getLstMat() {
        return lstMat;
    }

    public void setLstMat(String lstMat) {
        this.lstMat = lstMat;
    }

    public boolean isCertUni() {
        return certUni;
    }

    public void setCertUni(boolean certUni) {
        this.certUni = certUni;
    }

    public boolean isOrgInt() {
        return orgInt;
    }

    public void setOrgInt(boolean orgInt) {
        this.orgInt = orgInt;
    }

    public String getLstCe() {
        return lstCe;
    }

    public void setLstCe(String lstCe) {
        this.lstCe = lstCe;
    }

    public String getLstOr() {
        return lstOr;
    }

    public void setLstAniosInc(String lstAniosInc) {
        this.lstAniosInc = lstAniosInc;
    }

    public String getLstAniosInc() {
        return lstAniosInc;
    }

    public void setLstOr(String lstOr) {
        this.lstOr = lstOr;
    }

    public String getNombreCertificacion() {
        return nombreCertificacion;
    }

    public void setNombreCertificacion(String nombreCertificacion) {
        this.nombreCertificacion = nombreCertificacion;
    }

    public String getFechaInicioC() {
        return fechaInicioC;
    }

    public void setFechaInicioC(String fechaInicioC) {
        this.fechaInicioC = fechaInicioC;
    }

    public String getFechaFinC() {
        return fechaFinC;
    }

    public void setFechaFinC(String fechaFinC) {
        this.fechaFinC = fechaFinC;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNombreOrg() {
        return nombreOrg;
    }

    public void setNombreOrg(String nombreOrg) {
        this.nombreOrg = nombreOrg;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getModSeleccionados() {
        return modSeleccionados;
    }

    public void setModSeleccionados(String modSeleccionados) {
        this.modSeleccionados = modSeleccionados;
    }

    public final void setModSeleccionadosBD() {
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet tabla = conexion.Consultar("Select n.abreviatura, m.descripcion from (nivel_pe n inner join modelos_universidad mu on n.id_nivel = mu.id_nivel) inner join modelos m on m.id_modelo = mu.id_modelo where mu.id_universidad = "
                    .concat(String.valueOf(universidad)).concat(" and mu.id_periodo = ").concat(String.valueOf(getPeriodoActual())));
            if (!tabla.next()) {
                tabla = conexion.Consultar("Select n.abreviatura, m.descripcion from (nivel_pe n inner join modelos_universidad mu on n.id_nivel = mu.id_nivel) inner join modelos m on m.id_modelo = mu.id_modelo where mu.id_universidad = "
                        .concat(String.valueOf(universidad)).concat(" and mu.id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
            }
            tabla.beforeFirst();
            while (tabla.next()) {
                modSeleccionados = modSeleccionados.concat(tabla.getString(1)).concat(tabla.getString(2)).concat(", ");
            }
            if (modSeleccionados.length() >4 ) {
                modSeleccionados = modSeleccionados.substring(0, modSeleccionados.length() - 2);
            }
        } catch (SQLException ex) {
            System.err.println("Error: setModSeleccionadosBD");
        } finally {
            conexion.Desconectar();
        }
    }

    public String getServSeleccionados() {
        return servSeleccionados;
    }

    public void setServSeleccionados(String servSeleccionados) {
        this.servSeleccionados = servSeleccionados;
    }

    public final void setServSeleccionadosBD() {
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet tabla = conexion.Consultar("Select id_servicio from servicios_universidad where id_universidad = ".concat(String.valueOf(universidad)).concat(" and id_periodo = ").concat(String.valueOf(getPeriodoActual())));
            if (!tabla.next()) {
                tabla = conexion.Consultar("Select id_servicio from servicios_universidad where id_universidad = ".concat(String.valueOf(universidad)).concat(" and id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
            }
            tabla.beforeFirst();
            while (tabla.next()) {
                servSeleccionados = servSeleccionados.concat(tabla.getString(1)).concat(", ");
            }
            if (servSeleccionados.length() != 0) {
                servSeleccionados = servSeleccionados.substring(0, servSeleccionados.length() - 2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
    }

    public final void setEdifSeleccionadosBD() {
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet tabla = conexion.Consultar("Select id_edificio from edificios_universidad where id_universidad = ".concat(String.valueOf(universidad)).concat(" and id_periodo = ").concat(String.valueOf(getPeriodoActual())));
            if (!tabla.next()) {
                tabla = conexion.Consultar("Select id_edificio from edificios_universidad where id_universidad = ".concat(String.valueOf(universidad)).concat(" and id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
            }
            tabla.beforeFirst();
            while (tabla.next()) {
                edifSeleccionados = edifSeleccionados.concat(tabla.getString(1)).concat(", ");
            }
            if (edifSeleccionados.length() != 0) {
                edifSeleccionados = edifSeleccionados.substring(0, edifSeleccionados.length() - 2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
    }

    public String getEdifSeleccionados() {
        return edifSeleccionados;
    }

    public void setEdifSeleccionados(String edifSeleccionados) {
        this.edifSeleccionados = edifSeleccionados;
    }

    public int getAlNuevoIngreso() {
        return alNuevoIngreso;
    }

    public final void setAlNuevoIngreso(int alNuevoIngreso) {
        this.alNuevoIngreso = alNuevoIngreso;
    }

    public int getMatTotal() {
        return matTotal;
    }

    public final void setMatTotal(int matTotal) {
        this.matTotal = matTotal;
    }

    public int getProfAsign() {
        return profAsign;
    }

    public final void setProfAsign(int profAsign) {
        this.profAsign = profAsign;
    }

    public int getProfTC() {
        return profTC;
    }

    public final void setProfTC(int profTC) {
        this.profTC = profTC;
    }

    public String getLstPe() {
        return lstPe;
    }

    public void setLstPe(String lstPe) {
        this.lstPe = lstPe;
    }

    public String getModelos() {
        return modelos;
    }

    public final void setModelos() {
        String campos = "";
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet tabla = conexion.Consultar("Select descripcion from modelos where activo = 1");
            while (tabla.next()) {
                campos = campos.concat(tabla.getString(1)).concat(",");
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        campos = campos.substring(0, campos.length() - 1);
        modelos = campos;
    }

    public ArrayList getPeSeleccionados() {
        return peSeleccionados;
    }

    public final void setPeSeleccionados() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet tabla = conexion.Consultar("Select pe.id_pe, CONCAT(n.abreviatura, ' - ', pe.nombre_programa,' (', pe.version,')') as nombre from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual())));
//          MUESTRA LOS PROGRAMAS EDUCATIVOS DEL PERIODO ANTERIOR
            //            if (!tabla.next()) {
//                tabla = conexion.Consultar("Select pe.id_pe, CONCAT(n.nombre, ' - ' ,pe.nombre_programa,' (', pe.version,')') as nombre from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
//            }
            tabla.beforeFirst();
            while (tabla.next()) {
                seleccionados = seleccionados.concat(tabla.getString(1)).concat(", ");
                campos.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
            }
            if (seleccionados.length() != 0) {
                seleccionados = seleccionados.substring(0, seleccionados.length() - 2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        peSeleccionados = campos;
    }

    public ArrayList getAniosIncSeleccionados() {
        return aniosIncSeleccionados;
    }
    public ArrayList getclasificacionSeleccionados() {
          return clasificacionSeleccionados;
    }
     public final void setclasificacionSeleccionados() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet tabla = conexion.Consultar("Select clasificacion from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual())));
           
//            if (!tabla.next()) {
//                tabla = conexion.Consultar("Select anio_incorporacion from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
//            }
            tabla.beforeFirst();
            while (tabla.next()) {
                
                if(tabla.getString(1).equals("TB")){
                    campos.add(new Coleccion(0, tabla.getString(1)));
                }
                if(tabla.getString(1).equals("TN")){
                    campos.add(new Coleccion(1, tabla.getString(1)));
                }
                    lstClas = lstClas.concat(tabla.getString(1)).concat(",");
                
            }
            if (lstClas.length() != 0) {
                lstClas = lstClas.substring(0, lstClas.length() - 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        clasificacionSeleccionados = campos;
    }
    
     public ArrayList getnuevoingresoSeleccionados() {
          return nuevoingresoSeleccionados;
    }
       public final void setnuevoingresoSeleccionados() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet tabla = conexion.Consultar("Select nuevo_ingreso from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual())));
           
//            if (!tabla.next()) {
//                tabla = conexion.Consultar("Select anio_incorporacion from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
//            }
            tabla.beforeFirst();
            while (tabla.next()) {
                campos.add(new Coleccion(tabla.getInt(1), tabla.getString(1)));
                lstNuev = lstNuev.concat(tabla.getString(1)).concat(",");
            }
            if (lstNuev.length() != 0) {
                lstNuev = lstNuev.substring(0, lstNuev.length() - 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        nuevoingresoSeleccionados = campos;
    }
    public ArrayList getmatriculatotalSeleccionados() {
             return matriculatotalSeleccionados;
       }
    public final void setmatriculatotalSeleccionados() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet tabla = conexion.Consultar("Select matricula_total from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual())));
           
//            if (!tabla.next()) {
//                tabla = conexion.Consultar("Select anio_incorporacion from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
//            }
            tabla.beforeFirst();
            while (tabla.next()) {
                campos.add(new Coleccion(tabla.getInt(1), tabla.getString(1)));
                lstMat = lstMat.concat(tabla.getString(1)).concat(",");
            }
            if (lstMat.length() != 0) {
                lstMat = lstMat.substring(0, lstMat.length() - 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        matriculatotalSeleccionados = campos;
    }
     
    public final void setAniosIncSeleccionados() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet tabla = conexion.Consultar("Select anio_incorporacion from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual())));
           
//            if (!tabla.next()) {
//                tabla = conexion.Consultar("Select anio_incorporacion from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe inner join nivel_pe n on n.id_nivel = pe.id_nivel where pu.id_universidad = ".concat(String.valueOf(universidad)).concat(" and pu.id_periodo = ").concat(String.valueOf(getPeriodoActual() - 1)));
//            }
            tabla.beforeFirst();
            while (tabla.next()) {
                campos.add(new Coleccion(tabla.getInt(1), tabla.getString(1)));
                lstAniosInc = lstAniosInc.concat(tabla.getString(1)).concat(",");
            }
            if (lstAniosInc.length() != 0) {
                lstAniosInc = lstAniosInc.substring(0, lstAniosInc.length() - 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        aniosIncSeleccionados = campos;
    }

    public String getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(String seleccionados) {
        this.seleccionados = seleccionados;
    }

    public String getRespCaptura() {
        return respCaptura;
    }

    public void setRespCaptura(String respCaptura) {
        this.respCaptura = respCaptura;
    }

    public List getLstSeleccionados() {
        return lstSeleccionados;
    }

    public void setLstSeleccionados(List lstSeleccionados) {
        this.lstSeleccionados = lstSeleccionados;
    }

    public String[] getLstServiciosSeleccionados() {
        return lstServiciosSeleccionados;
    }

    public void setLstServiciosSeleccionados(String[] lstServiciosSeleccionados) {
        this.lstServiciosSeleccionados = lstServiciosSeleccionados;
    }

    public List getLstEdificiosSeleccionados() {
        return lstEdificiosSeleccionados;
    }

    public void setLstEdificiosSeleccionados(ArrayList lstEdificiosSeleccionados) {
        this.lstEdificiosSeleccionados = lstEdificiosSeleccionados;
    }

    @Override
    public String toString() {
        return "registroUniversidadForm -" + " ask:" + ask;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCboUniversidad() {
        return cboUniversidad;
    }

    public void setCboUniversidad(int cboUniversidad) {
        this.cboUniversidad = cboUniversidad;
    }

    public int getCboEstado() {
        return cboEstado;
    }

    public void setCboEstado(int cboEstado) {
        this.cboEstado = cboEstado;
    }

    public ArrayList getEstados() {
        return estados;
    }

    public final void setEstados() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from estados");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_estado"), tabla.getString("nombre")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        estados = campos;
    }

    public ArrayList getServicios() {
        return servicios;
    }

    public final void setServicios() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from encuesta_servicios");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_servicio"), tabla.getString("nombre")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        servicios = campos;
    }

    public ArrayList getEdificios() {
        return edificios;
    }

    public final void setEdificios() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from edificios where activo = 1");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_edificio"), tabla.getString("descripcion")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        edificios = campos;
    }

    public ArrayList getAreas() {
        return areas;
    }

    public final void setAreas() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from areas_pe where activo = 1");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_area"), tabla.getString("nombre_area")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        areas = campos;
    }

    public final void setUniversidades() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        int i;
        try {
            ResultSet tabla = conexion.Consultar("Select * from universidades");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_universidad"), tabla.getString("nombre_universidad")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        universidades = campos;
    }

    public ArrayList getUniversidades() {
        return universidades;
    }

    public ArrayList getFamilias() {
        return familias;
    }

    public void setFamilias(ArrayList familias) {
        this.familias = familias;
    }

    public ArrayList getProgramasEducativos() {
        return programasEducativos;
    }

    public void setProgramasEducativos(ArrayList programasEducativos) {
        this.programasEducativos = programasEducativos;
    }

    public int getCboArea() {
        return cboArea;
    }

    public void setCboArea(int cboArea) {
        this.cboArea = cboArea;
    }

    public int getCboFamilia() {
        return cboFamilia;
    }

    public void setCboFamilia(int cboFamilia) {
        this.cboFamilia = cboFamilia;
    }

    public int getCboPE() {
        return cboPE;
    }

    public void setCboPE(int cboPE) {
        this.cboPE = cboPE;
    }

      public int getCboclasificacionPE() {
        return cboclasificacionPE;
    }

    public void setCboclasificacionPE(int cboclasificacionPE) {
        this.cboclasificacionPE = cboclasificacionPE;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }
    
    public int getClasificacionPE() {
        return clasificacionPE;
    }

    public void setClasificacionPE(int clasificacionPE) {
        this.clasificacionPE = clasificacionPE;
    }
     public int getNuevo_ingresoPE() {
        return nuevo_ingresoPE;
    }

    public final void setNuevo_ingresoPE(int nuevo_ingresoPE) {
        this.nuevo_ingresoPE = nuevo_ingresoPE;
    }

     public int getMatricula_totalPE() {
        return matricula_totalPE;
    }

    public final void setMatricula_totalPE(int matricula_totalPE) {
        this.matricula_totalPE = matricula_totalPE;
    }
    
    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     *
     */
    public registroUniversidadForm() {
        super();
    }

    public void iniciarFormulario() {
        ConexionMySQL conexion = new ConexionMySQL();

        try {
            ResultSet tabla = conexion.Consultar("Select * from datos_universidad where id_universidad = ".concat(String.valueOf(universidad)).concat(" and id_periodo = ").concat(String.valueOf(getPeriodoActual())));
            if (tabla.next()) {
                setAlNuevoIngreso(tabla.getInt(3));
                setMatTotal(tabla.getInt(4));
                setProfTC(tabla.getInt(5));
                setProfAsign(tabla.getInt(6));
            } else {
                setAlNuevoIngreso(0);
                setMatTotal(0);
                setProfTC(0);
                setProfAsign(0);
            }
            tabla = conexion.Consultar("Select organismos_completos, certificaciones_completas from cert_org_universidad where id_universidad = ".concat(String.valueOf(universidad)).concat(" and id_periodo = ").concat(String.valueOf(getPeriodoActual())));
            if (tabla.next()) {
                this.orgInt = tabla.getBoolean(1);
                this.certUni = tabla.getBoolean(2);
            } else {
                this.orgInt = false;
                this.certUni = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        setEstados();
        setServicios();
        setEdificios();
        setAreas();
        setDireccion(universidad);
        setPeSeleccionados();
        setAniosIncSeleccionados();
        setnuevoingresoSeleccionados();
        setmatriculatotalSeleccionados();
        setclasificacionSeleccionados();
        setModSeleccionadosBD();
        setServSeleccionadosBD();
        setEdifSeleccionadosBD();
        setModelos();
        setUniversidades();
        setFechaCreacionInicial();
        setRector();
        setResponsable();
        setCapturista();
        setNiveles();
        setRfc();
        setAbreviatura();
    }

    public final ArrayList getPersonas(int universidad, String puesto) {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        int i = 0;
        try {
            String consulta = "Select id_responsable, CONCAT( nombre,  ' ', apaterno,  ' ', amaterno ) AS nombre from responsables where activo = 1 ".concat(puesto).concat(" and id_universidad = ").concat(String.valueOf(universidad));
            ResultSet tabla = conexion.Consultar(consulta);
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_responsable"), tabla.getString("nombre")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        return campos;
    }

    public final void setDireccion(String univ) {
        ConexionMySQL conexion = new ConexionMySQL();
            
        try {
            String consulta = "Select * from domicilios_universidad where id_universidad = ".concat(String.valueOf(Integer.parseInt(univ))).concat(" and activo = 1");
            ResultSet rs = conexion.Consultar(consulta);
            if (rs.next()) {
                setCboEstado(rs.getInt("id_estado"));
                setMunicipio(rs.getString("municipio"));
                setColonia(rs.getString("colonia"));
                setCalle(rs.getString("calle"));
                setNumero(rs.getString("numero"));
                setCp(rs.getString("codigo_postal"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar domicilios "+ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
    }

    public static final int getPeriodoActual() {
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            ResultSet rs = conexion.Consultar("Select id_periodo from periodos where activo = 1");
            if (rs.next()) {
                return rs.getInt("id_periodo");
            }
        } catch (SQLException ex) {
            System.err.println("Error guardando el periodo actual "+ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        return -1;
    }

    public ArrayList getNiveles() {
        return niveles;
    }

    public final void setNiveles() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from nivel_pe where activo = 1 and id_nivel>0");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_nivel"),tabla.getString("descripcion").concat(" - ").concat( tabla.getString("abreviatura"))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(registroUniversidadForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        niveles = campos;
    }

    private void setRfc() {
        String rfc = "";
        ConexionMySQL conexion = new ConexionMySQL();
        ResultSet rs;
        try {
            String consulta = "SELECT rfc FROM universidades WHERE id_universidad=".concat(String.valueOf(universidad));
            rs = conexion.Consultar(consulta);
            if (rs.next()) {
                rfc = rs.getString("rfc");
            }
        } catch (Exception x) {
            System.err.println("ERROR registroUniversidad.java setRfc " + x.getMessage());
        } finally {
            conexion.Desconectar();
        }
        this.rfc = rfc;
    }

    private void setAbreviatura() {
        String ab = "";
        ConexionMySQL conexion = new ConexionMySQL();
        ResultSet rs;
        try {
            String consulta = "SELECT abreviatura FROM universidades WHERE id_universidad=".concat(String.valueOf(universidad));
            rs = conexion.Consultar(consulta);
            if (rs.next()) {
                ab = rs.getString("abreviatura");
            }
        } catch (Exception x) {
            System.err.println("ERROR registroUniversidad.java setRfc " + x.getMessage());
        } finally {
            conexion.Desconectar();
        }
        this.abreviatura = ab;
    }
}