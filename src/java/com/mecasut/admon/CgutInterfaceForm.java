/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import utilerias.Encriptacion;
import java.sql.*;
import com.mecasut.shared.Coleccion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Juan García Barradas, Irvin Joshua Luévano García, Cuauhtemoc Medina Muñoz
 */
public class CgutInterfaceForm extends org.apache.struts.action.ActionForm {
//    /*-----------------------------------------------------------------------------------------------------------*/
//    


//    private int cboRector;
//    private int cboContacto;
//    private int cboArea = -1;
//    private int cboAreaFam = -1;
//    private int cboAreaProg = -1;
//    private int cboFamilia = -1;
//    private int cboFamiliaProg = -1;
//    private int cboUniversidadesUs = -1;
//    
//    private int cboEncuestas = -1;
//    private int cboNivelEncuesta = -1;
//    private int cboEncuestaIndicador = -1;
//    private int cmbPregEnc = -1;


//    private int cboResponsables = -1;
//    private int cboResponsables2 = -1;
//    private int cboResponsablesForm = -1;
//    private int cboUni = -1;

//    private int cboCategorias = -1;
//    private int cboCategorias2 = -1;
//    private int cboIndicadores = -1;
//    private int cboUni2 = -1;
//    private int cboRes2 = -1;
//    private int cboCategoPre = -1;
//    private int cboCategoPre2 = -1;

//    private int cboCertificacion = -1;

//    
//    
//   
//    private String preguntasActiva = "";
//    
//    private String categoriaPreActiva = "";
//    private String encuestasPreActiva = "";
//    private String nombreCategoPregu = "";
//    private String nombreCategoDesc = "";
    
//  private String certificacionActiva = "";  
//    private String txtNombrePreguntas = "";

//    private String nivelActivo = "";
//    private String categoriaActiva = "";
//    private String responsablesActivo = "";

//    private String indicadorActivo = "";
//    private String nombreEncuestas = "";



//    private String nombreResponsable = "";
//    private String nombreAPResponsable = "";
//    private String nombreAMResponsable = "";
//    private String nombreCargo = "";
//    private String nombreTelefono = "";
//    private String nombreMail = "";
//    private String nombreFechaI = "";
//    private String nombreFechaR = "";



//    private String nombreCategoria = "";
//    private String nombreD = "";
//    private String arJsp = "";
//    private String arjs = "";
//    private String tipoR = "";
//    private String egresados_bachillerato = "";
//    private String nuevo_ingreso = "";
//    private String mat_total = "";
//    private String nombreCertificacion = "";

//    private ArrayList encuestas = new ArrayList();
//    private ArrayList encuestas2 = new ArrayList();
//    private ArrayList nivelEncuesta = new ArrayList();
//    private ArrayList preguntas = new ArrayList();

//    private ArrayList programa = new ArrayList();
//    private ArrayList nivel2 = new ArrayList();
//    private ArrayList responsables = new ArrayList();
//    private ArrayList responsables2 = new ArrayList();
//    private ArrayList uni = new ArrayList();

//    private ArrayList categorias = new ArrayList();
//    private ArrayList categorias2 = new ArrayList();
//    private ArrayList indicadores = new ArrayList();

//    private ArrayList uni2 = new ArrayList();
//    private ArrayList responsa2 = new ArrayList();
//    private ArrayList categoPre = new ArrayList();
//    private ArrayList categoPre2 = new ArrayList();

//    private ArrayList certificaciones = new ArrayList();

//    private int cboPE;
//    private int cboNivel = -2;
//    private int number;
//    
//    
//    
//    private String ask = "login";
//    
//    
//    private String name;
//    private String nombreArea, areaActivo;
//    private String nombreFamilia, familiaActivo, nomenclaturaFam;
//    private String nombrePrograma, programaActivo, nomenclaturaProg, versionPrograma;
//    private String responsableUs;
//    private String municipio = "", colonia = "", calle = "", numero = "", cp = "", telefono = "", mail = "", telefonoC = "", mailC = "", respCaptura = "", mailRespCaptura = "", telefonoRespCaptura = "";
//    
//    private ArrayList areas = new ArrayList();
//    private ArrayList areasFam = new ArrayList();
//    private ArrayList areasProg = new ArrayList();
//    private ArrayList familias = new ArrayList();
//    private ArrayList familiasProg = new ArrayList();
//    private ArrayList programasEducativos = new ArrayList();

//    private ArrayList universidadesUs = new ArrayList();

//    private boolean cambioDeResponsable = false;
//    private String txtPalabraClavePreguntas = "";
//
//    public int getCboEncuestaIndicador() {
//        return cboEncuestaIndicador;
//    }
//
//    public void setCboEncuestaIndicador(int cboEncuestaIndicador) {
//        this.cboEncuestaIndicador = cboEncuestaIndicador;
//    }
//
//    public int getCboNivelEncuesta() {
//        return cboNivelEncuesta;
//    }
//
//    public void setCboNivelEncuesta(int cboNivelEncuesta) {
//        this.cboNivelEncuesta = cboNivelEncuesta;
//    }
//
//    public ArrayList getNivelEncuesta() {
//        return nivelEncuesta;
//    }
//    
//    public void setNivelEncuesta(ArrayList nivelEncuesta){
//        this.nivelEncuesta=nivelEncuesta;
//    }
//
//    public void setNivelesEncuestas() {
//        ArrayList level = new ArrayList();
//        ConexionMySQL con = new ConexionMySQL();
//        try {
//            ResultSet tabla = con.Consultar("SELECT id_nivel, nombre FROM nivel_pe WHERE id_nivel>0");
//            while (tabla.next()) {
//                level.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            con.Desconectar();
//        }
//        this.nivelEncuesta = level;
//    }
//
//    public ArrayList getResponsables2() {
//        return responsables2;
//    }
//
//    public void setResponsables2(ArrayList responsables2) {
//        this.responsables2 = responsables2;
//    }
//
//    public String getTxtPalabraClavePreguntas() {
//        return txtPalabraClavePreguntas;
//    }
//
//    public void setTxtPalabraClavePreguntas(String txtPalabraClavePreguntas) {
//        this.txtPalabraClavePreguntas = txtPalabraClavePreguntas;
//    }
//
//    public int getCboResponsablesForm() {
//        return cboResponsablesForm;
//    }
//
//    public void setCboResponsablesForm(int cboResponsablesForm) {
//        this.cboResponsablesForm = cboResponsablesForm;
//    }
//
//    public boolean isCambioDeResponsable() {
//        return cambioDeResponsable;
//    }
//
//    public void setCambioDeResponsable(boolean cambioDeResponsable) {
//        this.cambioDeResponsable = cambioDeResponsable;
//    }
//
//    public ArrayList getPreguntas() {
//        return preguntas;
//    }
//
//    public void setPreguntas(ArrayList preguntas) {
//        this.preguntas = preguntas;
//    }
//
//    public String getTxtNombrePreguntas() {
//        return txtNombrePreguntas;
//    }
//
//    public void setTxtNombrePreguntas(String txtNombrePreguntas) {
//        this.txtNombrePreguntas = txtNombrePreguntas;
//    }
//
//    public int getCmbPregEnc() {
//        return cmbPregEnc;
//    }
//
//    public void setCmbPregEnc(int cmbPregEnc) {
//        this.cmbPregEnc = cmbPregEnc;
//    }
//    private int cmbPregPreguntas = -1;
//
//    public int getCmbPregPreguntas() {
//        return cmbPregPreguntas;
//    }
//
//    public void setCmbPregPreguntas(int cmbPregPreguntas) {
//        this.cmbPregPreguntas = cmbPregPreguntas;
//    }
//    
//    public String getMat_total() {
//        return mat_total;
//    }
//
//    public void setMat_total(String mat_total) {
//        this.mat_total = mat_total;
//    }
//    
//    public String getNuevo_ingreso() {
//        return nuevo_ingreso;
//    }
//
//    public void setNuevo_ingreso(String nuevo_ingreso) {
//        this.nuevo_ingreso = nuevo_ingreso;
//    }
//    

//    

//    



//    

//    

//

//    
//    public String getEgresados_bachillerato() {
//        return egresados_bachillerato;
//    }
//
//    public void setEgresados_bachillerato(String egresados_bachillerato) {
//        this.egresados_bachillerato = egresados_bachillerato;
//    }
//
//    public String getPreguntasActiva() {
//        return preguntasActiva;
//    }
//
//    public void setPreguntasActiva(String preguntasActiva) {
//        this.preguntasActiva = preguntasActiva;
//    }
//
//    public String getEncuestasPreActiva() {
//        return encuestasPreActiva;
//    }
//
//    public void setEncuestasPreActiva(String encuestasPreActiva) {
//        this.encuestasPreActiva = encuestasPreActiva;
//    }
//
//    public String getCategoriaPreActiva() {
//        return categoriaPreActiva;
//    }
//
//    public void setCategoriaPreActiva(String categoriaPreActiva) {
//        this.categoriaPreActiva = categoriaPreActiva;
//    }
//

//

//
//    public ArrayList getCategoPre2() {
//        return categoPre2;
//    }
//
//    public void setCategoPre2(ArrayList categoPre2) {
//        this.categoPre2 = categoPre2;
//    }
//
//    public int getCboCategoPre2() {
//        return cboCategoPre2;
//    }
//
//    public void setCboCategoPre2(int cboCategoPre2) {
//        this.cboCategoPre2 = cboCategoPre2;
//    }
//
//    public ArrayList getCategoPre() {
//        return categoPre;
//    }
//
//    public void setCategoPre() {
//        ArrayList categoPre = new ArrayList();
//        ConexionMySQL con = new ConexionMySQL();
//        
//        try {
//            ResultSet tabla = con.Consultar("select * from encuesta_servicios");
//            while (tabla.next()) {
//                categoPre.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            con.Desconectar();
//        }
//        this.categoPre = categoPre;
//    }
//
//    public int getCboCategoPre() {
//        return cboCategoPre;
//    }
//
//    public void setCboCategoPre(int cboCategoPre) {
//        this.cboCategoPre = cboCategoPre;
//    }
//
//    public String getNombreCategoDesc() {
//        return nombreCategoDesc;
//    }
//
//    public void setNombreCategoDesc(String nombreCategoDesc) {
//        this.nombreCategoDesc = nombreCategoDesc;
//    }
//
//    public String getNombreCategoPregu() {
//        return nombreCategoPregu;
//    }
//
//    public void setNombreCategoPregu(String nombreCategoPregu) {
//        this.nombreCategoPregu = nombreCategoPregu;
//    }
//
//    public String getTipoR() {
//        return tipoR;
//    }
//
//    public void setTipoR(String tipoR) {
//        this.tipoR = tipoR;
//    }
//
//    public int getCboRes2() {
//        return cboRes2;
//    }
//
//    public void setCboRes2(int cboRes2) {
//        this.cboRes2 = cboRes2;
//    }
//
//    public int getCboUni2() {
//        return cboUni2;
//    }
//
//    public void setCboUni2(int cboUni2) {
//        this.cboUni2 = cboUni2;
//    }
//
//    public ArrayList getResponsa2() {
//        return responsa2;
//    }
//
//    public void setResponsa2(ArrayList responsa2) {
//        this.responsa2 = responsa2;
//    }
//
//    public ArrayList getUni2() {
//        return uni2;
//    }
//
//    public void setUni2(ArrayList uni2) {
//        this.uni2 = uni2;
//    }
//
//    public String getIndicadorActivo() {
//        return indicadorActivo;
//    }
//
//    public void setIndicadorActivo(String indicadorActivo) {
//        this.indicadorActivo = indicadorActivo;
//    }
//
//    public String getArJsp() {
//        return arJsp;
//    }
//
//    public void setArJsp(String arJsp) {
//        this.arJsp = arJsp;
//    }
//
//    public String getArjs() {
//        return arjs;
//    }
//
//    public void setArjs(String arjs) {
//        this.arjs = arjs;
//    }
//
//    public String getNombreD() {
//        return nombreD;
//    }
//
//    public void setNombreD(String nombreD) {
//        this.nombreD = nombreD;
//    }
//
//    public ArrayList getCategorias2() {
//        return categorias2;
//    }
//
//    public void setCategorias2(ArrayList categorias2) {
//        this.categorias2 = categorias2;
//    }
//
//    public int getCboCategorias2() {
//        return cboCategorias2;
//    }
//
//    public void setCboCategorias2(int cboCategorias2) {
//        this.cboCategorias2 = cboCategorias2;
//    }
//
//    public int getCboIndicadores() {
//        return cboIndicadores;
//    }
//
//    public void setCboIndicadores(int cboIndicadores) {
//        this.cboIndicadores = cboIndicadores;
//    }
//
//    public ArrayList getIndicadores() {
//        return indicadores;
//    }
//
//    public void setIndicadores(ArrayList indicadores) {
//        this.indicadores = indicadores;
//    }
//
//    public String getCategoriaActiva() {
//        return categoriaActiva;
//    }
//
//    public void setCategoriaActiva(String categoriaActiva) {
//        this.categoriaActiva = categoriaActiva;
//    }
//
//    public String getNombreCategoria() {
//        return nombreCategoria;
//    }
//
//    public void setNombreCategoria(String nombreCategoria) {
//        this.nombreCategoria = nombreCategoria;
//    }
//
//    public ArrayList getCategorias() {
//        return categorias;
//    }
//
//    public void setCategorias() {
//        ArrayList categorias = new ArrayList();
//        ConexionMySQL con = new ConexionMySQL();
//        
//        try {
//            ResultSet tabla = con.Consultar("select * from system_categorias");
//            while (tabla.next()) {
//                categorias.add(new Coleccion(tabla.getInt(1), tabla.getString(3)));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            con.Desconectar();
//        }
//        this.categorias = categorias;
//        this.categorias2 = categorias;
//    }
//
//    public int getCboCategorias() {
//        return cboCategorias;
//    }
//
//    public void setCboCategorias(int cboCategorias) {
//        this.cboCategorias = cboCategorias;
//    }
//

//
//    public String getResponsablesActivo() {
//        return responsablesActivo;
//    }
//
//    public void setResponsablesActivo(String responsablesActivo) {
//        this.responsablesActivo = responsablesActivo;
//    }
//
//    public String getNivelActivo() {
//        return nivelActivo;
//    }
//
//    public void setNivelActivo(String nivelActivo) {
//        this.nivelActivo = nivelActivo;
//    }
//

//
//    public String getCertificacionActiva() {
//        return certificacionActiva;
//    }
//
//    public void setCertificacionActiva(String certificacionActiva) {
//        this.certificacionActiva = certificacionActiva;
//    }
//
//    public String getNombreUsu() {
//        return nombreUsu;
//    }
//
//    public void setNombreUsu(String nombreUsu) {
//        this.nombreUsu = nombreUsu;
//    }
//

//
//    public int getCboResponsables2() {
//        return cboResponsables2;
//    }
//
//    public void setCboResponsables2(int cboResponsables2) {
//        this.cboResponsables2 = cboResponsables2;
//    }
//

//
//    public String getNombreMail() {
//        return nombreMail;
//    }
//
//    public void setNombreMail(String nombreMail) {
//        this.nombreMail = nombreMail;
//    }
//
//    public String getNombreAMResponsable() {
//        return nombreAMResponsable;
//    }
//
//    public void setNombreAMResponsable(String nombreAMResponsable) {
//        this.nombreAMResponsable = nombreAMResponsable;
//    }
//
//    public String getNombreAPResponsable() {
//        return nombreAPResponsable;
//    }
//
//    public void setNombreAPResponsable(String nombreAPResponsable) {
//        this.nombreAPResponsable = nombreAPResponsable;
//    }
//
//    public String getNombreCargo() {
//        return nombreCargo;
//    }
//
//    public void setNombreCargo(String nombreCargo) {
//        this.nombreCargo = nombreCargo;
//    }
//
//    public String getNombreFechaI() {
//        return nombreFechaI;
//    }
//
//    public void setNombreFechaI(String nombreFechaI) {
//        this.nombreFechaI = nombreFechaI;
//    }
//
//    public String getNombreFechaR() {
//        return nombreFechaR;
//    }
//
//    public void setNombreFechaR(String nombreFechaR) {
//        this.nombreFechaR = nombreFechaR;
//    }
//
//    public String getNombreResponsable() {
//        return nombreResponsable;
//    }
//
//    public void setNombreResponsable(String nombreResponsable) {
//        this.nombreResponsable = nombreResponsable;
//    }
//
//    public String getNombreTelefono() {
//        return nombreTelefono;
//    }
//
//    public void setNombreTelefono(String nombreTelefono) {
//        this.nombreTelefono = nombreTelefono;
//    }
//
//    public int getCboUni() {
//        return cboUni;
//    }
//
//    public void setCboUni(int cboUni) {
//        this.cboUni = cboUni;
//    }
//
//    public ArrayList getUni() {
//        return uni;
//    }
//


//    public int getCboResponsables() {
//        return cboResponsables;
//    }
//
//    public void setCboResponsables(int cboResponsables) {
//        this.cboResponsables = cboResponsables;
//    }
//
//    public ArrayList getResponsables() {
//        return responsables;
//    }
//
//    public void setResponsables(ArrayList responsables) {
//        this.responsables = responsables;
//    }
//

//
//    public ArrayList getNivel2() {
//        return nivel2;
//    }
//
//    public void setNivel2() {
//        ArrayList nivel2 = new ArrayList();
//        ConexionMySQL con = new ConexionMySQL();
//        
//        try {
//            ResultSet tabla = con.Consultar("select * from nivel_pe");
//            while (tabla.next()) {
//                nivel2.add(new Coleccion(tabla.getInt(1), tabla.getString(3)));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            con.Desconectar();
//        }
//        this.nivel2 = nivel2;
//    }
//
//
//    public ArrayList getPrograma() {
//        return programa;
//    }
//
//    public void setPrograma(ArrayList programa) {
//        this.programa = programa;
//    }
//
//    public String getNombreModelo() {
//        return nombreModelo;
//    }
//
//    public void setNombreModelo(String nombreModelo) {
//        this.nombreModelo = nombreModelo;
//    }
//
//    public String getNombreCertificacion() {
//        return nombreCertificacion;
//    }
//
//    public void setNombreCertificacion(String nombreCertificacion) {
//        this.nombreCertificacion = nombreCertificacion;
//    }
//
//    public int getCboModelo() {
//        return cboModelo;
//    }
//
//    public void setCboModelo(int cboModelo) {
//        this.cboModelo = cboModelo;
//    }
//
//    public int getCboCertificacion() {
//        return cboCertificacion;
//    }
//
//    public void setCboCertificacion(int cboCertificacion) {
//        this.cboCertificacion = cboCertificacion;
//    }
//    

//

//
//    public ArrayList getCertificaciones() {
//        return certificaciones;
//    }
//
//    public void setCertificaciones() {
//        ArrayList certificaciones = new ArrayList();
//        ConexionMySQL con = new ConexionMySQL();
//        
//        try {
//            ResultSet tabla = con.Consultar("select * from certificaciones");
//            while (tabla.next()) {
//                certificaciones.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            con.Desconectar();
//        }
//        this.certificaciones = certificaciones;
//    }
//    
//    

//
//    public ArrayList getEncuestas2() {
//        return encuestas2;
//    }
//
//    public void setEncuestas2(ArrayList encuestas2) {
//        this.encuestas2 = encuestas2;
//    }
//
//    public int getCboEncuestas() {
//        return cboEncuestas;
//    }
//
//    public void setCboEncuestas(int cboEncuestas) {
//        this.cboEncuestas = cboEncuestas;
//    }
//
//    public ArrayList getEncuestas() {
//        return encuestas;
//    }
//
//    public void setEncuestas() {
//        ArrayList encuestas = new ArrayList();
//        ConexionMySQL con = new ConexionMySQL();
//        
//        try {
//            ResultSet tabla = con.Consultar("SELECT e.id_encuesta, CONCAT(e.nombre, ' (',n.nombre,')') as nombre FROM encuestas e INNER JOIN nivel_pe n on e.id_nivel=n.id_nivel");
//            while (tabla.next()) {
//                encuestas.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            con.Desconectar();
//        }
//        this.encuestas = encuestas;
//        this.encuestas2 = encuestas;
//    }
//
//    public String getNombreEncuestas() {
//        return nombreEncuestas;
//    }
//
//    public void setNombreEncuestas(String nombreEncuestas) {
//        this.nombreEncuestas = nombreEncuestas;
//    }
//
//    public int getCboUniversidadesUs() {
//        return cboUniversidadesUs;
//    }
//
//    public void setCboUniversidadesUs(int cboUniversidadesUs) {
//        this.cboUniversidadesUs = cboUniversidadesUs;
//    }
//

//
//    public String getResponsableUs() {
//        return responsableUs;
//    }
//
//    public void setResponsableUs(String responsableUs) {
//        this.responsableUs = responsableUs;
//    }
//
//    public ArrayList getUniversidadesUs() {
//        return universidadesUs;
//    }
//
//    public void setUniversidadesUs() {
//        ArrayList campos = new ArrayList();
//        ConexionMySQL conexion = new ConexionMySQL();
//        
//        int i = 0;
//        try {
//            ResultSet tabla = conexion.Consultar("Select * from universidades where id_universidad > 0  AND activo=1");
//            while (tabla.next()) {
//                i++;
//            }
//            tabla.beforeFirst();
//            for (int j = 0; j < i; j++) {
//                tabla.next();
//                campos.add(new Coleccion(tabla.getInt("ID_universidad"), tabla.getString("NOMBRE_UNIVERSIDAD")));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            conexion.Desconectar();
//        }
//        this.uni = campos;
//        universidadesUs = campos;
//    }
//
//    public int getCboNivel() {
//        return cboNivel;
//    }
//
//    public void setCboNivel(int cboNivel) {
//        this.cboNivel = cboNivel;
//    }
//

//
//    public String getVersionPrograma() {
//        return versionPrograma;
//    }
//
//    public void setVersionPrograma(String versionPrograma) {
//        this.versionPrograma = versionPrograma;
//    }
//
//    public String getNomenclaturaProg() {
//        return nomenclaturaProg;
//    }
//
//    public void setNomenclaturaProg(String nomenclaturaProg) {
//        this.nomenclaturaProg = nomenclaturaProg;
//    }
//
//    public String getNombrePrograma() {
//        return nombrePrograma;
//    }
//
//    public void setNombrePrograma(String nombrePrograma) {
//        this.nombrePrograma = nombrePrograma;
//    }
//
//    public String getProgramaActivo() {
//        return programaActivo;
//    }
//
//    public void setProgramaActivo(String programaActivo) {
//        this.programaActivo = programaActivo;
//    }
//
//    public ArrayList getAreasProg() {
//        return areasProg;
//    }
//
//    public int getCboAreaProg() {
//        return cboAreaProg;
//    }
//
//    public void setCboAreaProg(int cboAreaProg) {
//        this.cboAreaProg = cboAreaProg;
//    }
//
//    public int getCboFamiliaProg() {
//        return cboFamiliaProg;
//    }
//
//    public void setCboFamiliaProg(int cboFamiliaProg) {
//        this.cboFamiliaProg = cboFamiliaProg;
//    }
//
//    public ArrayList getFamiliasProg() {
//        return familiasProg;
//    }
//
//    public void setFamiliasProg(ArrayList familiasProg) {
//        this.familiasProg = familiasProg;
//    }
//
//    public String getNomenclaturaFam() {
//        return nomenclaturaFam;
//    }
//
//    public void setNomenclaturaFam(String nomenclaturaFam) {
//        this.nomenclaturaFam = nomenclaturaFam;
//    }
//
//    public ArrayList getAreasFam() {
//        return areasFam;
//    }
//
//    public int getCboAreaFam() {
//        return cboAreaFam;
//    }
//
//    public void setCboAreaFam(int cboAreaFam) {
//        this.cboAreaFam = cboAreaFam;
//    }
//
//    public String getFamiliaActivo() {
//        return familiaActivo;
//    }
//
//    public void setFamiliaActivo(String familiaActivo) {
//        this.familiaActivo = familiaActivo;
//    }
//
//    public String getNombreFamilia() {
//        return nombreFamilia;
//    }
//
//    public void setNombreFamilia(String nombreFamilia) {
//        this.nombreFamilia = nombreFamilia;
//    }
//
//    public String getAreaActivo() {
//        return areaActivo;
//    }
//
//    public void setAreaActivo(String areaActivo) {
//        this.areaActivo = areaActivo;
//    }
//
//    public String getNombreArea() {
//        return nombreArea;
//    }
//
//    public void setNombreArea(String nombreArea) {
//        this.nombreArea = nombreArea;
//    }
//
//    public String getMailRespCaptura() {
//        return mailRespCaptura;
//    }
//
//    public void setMailRespCaptura(String mailRespCaptura) {
//        this.mailRespCaptura = mailRespCaptura;
//    }
//
//    public String getMailC() {
//        return mailC;
//    }
//
//    public void setMailC(String mailC) {
//        this.mailC = mailC;
//    }
//
//    public String getRespCaptura() {
//        return respCaptura;
//    }
//
//    public void setRespCaptura(String respCaptura) {
//        this.respCaptura = respCaptura;
//    }
//
//    public String getTelefonoRespCaptura() {
//        return telefonoRespCaptura;
//    }
//
//    public void setTelefonoRespCaptura(String telefonoRespCaptura) {
//        this.telefonoRespCaptura = telefonoRespCaptura;
//    }
//
//    @Override
//    public String toString() {
//        return "CgutInterfaceForm -" + " ask:" + ask;
//    }
//
//    public String getCalle() {
//        return calle;
//    }
//
//    public void setCalle(String calle) {
//        this.calle = calle;
//    }
//
//    public String getColonia() {
//        return colonia;
//    }
//
//    public void setColonia(String colonia) {
//        this.colonia = colonia;
//    }
//
//    public String getCp() {
//        return cp;
//    }
//
//    public void setCp(String cp) {
//        this.cp = cp;
//    }
//
//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    public String getMunicipio() {
//        return municipio;
//    }
//
//    public void setMunicipio(String municipio) {
//        this.municipio = municipio;
//    }
//
//    public String getNumero() {
//        return numero;
//    }
//
//    public void setNumero(String numero) {
//        this.numero = numero;
//    }
//
//    public String getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(String telefono) {
//        this.telefono = telefono;
//    }
//
//    public String getTelefonoC() {
//        return telefonoC;
//    }
//
//    public void setTelefonoC(String telefonoC) {
//        this.telefonoC = telefonoC;
//    }
//
//    public int getCboRector() {
//        return cboRector;
//    }
//
//    public void setCboRector(int cboRector) {
//        this.cboRector = cboRector;
//    }
//
//    public ArrayList getAreas() {
//        return areas;
//    }
//
//    
//
//    public final void setAreas() {
//        ArrayList campos = new ArrayList();
//        ConexionMySQL conexion = new ConexionMySQL();
//        
//        int i = 0;
//        try {
//            ResultSet tabla = conexion.Consultar("Select * from areas_pe");
//            tabla.last();
//            int j = tabla.getRow();
//            tabla.beforeFirst();
//            for (i = 0; i < j; i++) {
//                tabla.next();
//                campos.add(new Coleccion(tabla.getInt("ID_area"), tabla.getString("NOMBRE_AREA")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            conexion.Desconectar();
//        }
//        areas = campos;
//    }
//
//    public final void setAreasFam() {
//        ArrayList campos = new ArrayList();
//        ConexionMySQL conexion = new ConexionMySQL();
//        
//        int i = 0;
//        try {
//            ResultSet tabla = conexion.Consultar("Select * from areas_pe where activo = 1");
//            tabla.last();
//            int j = tabla.getRow();
//            tabla.beforeFirst();
//            for (i = 0; i < j; i++) {
//                tabla.next();
//                campos.add(new Coleccion(tabla.getInt("ID_area"), tabla.getString("NOMBRE_AREA")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            conexion.Desconectar();
//        }
//        areasFam = campos;
//    }
//
//    public final void setAreasProg() {
//        ArrayList campos = new ArrayList();
//        ConexionMySQL conexion = new ConexionMySQL();
//        
//        int i = 0;
//        try {
//            ResultSet tabla = conexion.Consultar("Select * from areas_pe where activo = 1");
//            tabla.last();
//            int j = tabla.getRow();
//            tabla.beforeFirst();
//            for (i = 0; i < j; i++) {
//                tabla.next();
//                campos.add(new Coleccion(tabla.getInt("ID_area"), tabla.getString("NOMBRE_AREA")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            conexion.Desconectar();
//        }
//        areasProg = campos;
//    }
//
//    public ArrayList getFamilias() {
//        return familias;
//    }
//    

//
//    public void setFamilias(ArrayList familias) {
//        this.familias = familias;
//    }
//
//    public ArrayList getProgramasEducativos() {
//        return programasEducativos;
//    }
//
//    public void setProgramasEducativos(ArrayList programasEducativos) {
//        this.programasEducativos = programasEducativos;
//    }
//
//    public int getCboArea() {
//        return cboArea;
//    }
//
//    public void setCboArea(int cboArea) {
//        this.cboArea = cboArea;
//    }
//
//    public int getCboFamilia() {
//        return cboFamilia;
//    }
//
//    public void setCboFamilia(int cboFamilia) {
//        this.cboFamilia = cboFamilia;
//    }
//
//    public int getCboPE() {
//        return cboPE;
//    }
//
//    public void setCboPE(int cboPE) {
//        this.cboPE = cboPE;
//    }
//
//    public String getAsk() {
//        return ask;
//    }
//
//    public void setAsk(String ask) {
//        this.ask = ask;
//    }
//
//    public int getCboContacto() {
//        return cboContacto;
//    }
//
//    public void setCboContacto(int cboContacto) {
//        this.cboContacto = cboContacto;
//    }
//
//    /*
//    public final void setDatosContacto() {
//        ConexionMySQL conexion = new ConexionMySQL();
//        
//        try {
//            String consulta = "Select * from responsables_universidad ru inner join responsables r on ru.id_responsable = r.id_responsable where ru.id_universidad = ".concat(String.valueOf(universidad)).concat(" and ru.id_periodo = ".concat(String.valueOf(getPeriodoActual())));
//            ResultSet rs = conexion.Consultar(consulta);
//            if (rs.next()) {
//                rs.beforeFirst();
//                while (rs.next()) {
//                    if (rs.getString("tipo").equals("RU")) //RU:RectorUniversidad
//                    {
//                        setCboRector(rs.getInt("id_responsable"));
//                        setTelefono(rs.getString("telefono"));
//                        setMail(rs.getString("mail"));
//                    } else if (rs.getString("tipo").equals("CU")) //CU:ContactoUniversidad
//                    {
//                        setCboContacto(rs.getInt("id_responsable"));
//                        setTelefonoC(rs.getString("telefono"));
//                        setMailC(rs.getString("mail"));
//                    } else if (rs.getString("tipo").equals("RC")) //RC:ResponsableCaptura
//                    {
//                        setRespCaptura(String.valueOf(rs.getInt("id_responsable")));
//                        setTelefonoRespCaptura(rs.getString("telefono"));
//                        setMailRespCaptura(rs.getString("mail"));
//                    }
//                }
//            } else {
//                //setContactoInvalido("true");
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            conexion.Desconectar();
//        }
//    }
//    */
//    public final ArrayList getPersonas(int universidad, String puesto) {
//        ArrayList campos = new ArrayList();
//        ConexionMySQL conexion = new ConexionMySQL();
//        
//        int i = 0;
//        try {
//            String consulta = "Select ID_responsable, CONCAT( nombre,  ' ', apaterno,  ' ', amaterno ) AS nombre from responsables where activo = 1 ".concat(puesto).concat(" and id_universidad = ").concat(String.valueOf(universidad));
//            ResultSet tabla = conexion.Consultar(consulta);
//            tabla.last();
//            int j = tabla.getRow();
//            tabla.beforeFirst();
//            for (i = 0; i < j; i++) {
//                tabla.next();
//                campos.add(new Coleccion(tabla.getInt("ID_responsable"), tabla.getString("nombre")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            conexion.Desconectar();
//        }
//        return campos;
//    }
//

//
//    
//
//    
//
//    
//
//    public String getName() {
//        return name;
//    }
//
//    
//
//    
//
//    public void setName(String string) {
//        name = string;
//    }
//
//    /**
//     * @return
//     */
//    public int getNumber() {
//        return number;
//    }
//
//    /**
//     * @param i
//     */
//    public void setNumber(int i) {
//        number = i;
//    }
//
//    /**
//     *
//     */
//    public CgutInterfaceForm() {
//        super();
//        setAreas();
//        setAreasFam();
//        setAreasProg();
//        setUniversidadesUs();
//        //setDatosContacto();
//        setCausas();
//        setEncuestas();
//        setCertificaciones();
//        setEdificios();
//        setNivel2();
//        setCategorias();
//        setCategoPre();
//        setUni();
//        setUniMan();
//        setPeriodos();
//        setNivelesEncuestas();
//    }
//
}
