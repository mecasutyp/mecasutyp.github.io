/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.admon.CgutInterfaceForm;
import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Daniel Ramírez Torres
 * Actualización 2016: Salvador Zamora Arias
 */
public class CgutProgramasForm extends org.apache.struts.action.ActionForm {

    private int cboAreaProg = -1;
    private int cboFamiliaProg = -1;
    private int cboNivel = -2;
    private int cboPE;
    private String ask = "login";
    private String nombrePrograma, nomenclaturaProg, versionPrograma, programaActivo;
    private ArrayList areasProg = new ArrayList();
    private ArrayList familiasProg = new ArrayList();
    private ArrayList programasEducativos = new ArrayList();
    private ArrayList niveles = new ArrayList();

    public void setCboNivel(int cboNivel) {
        this.cboNivel = cboNivel;
    }

    public ArrayList getNiveles() {
        return niveles;
    }

    public String getProgramaActivo() {
        return programaActivo;
    }

    public void setProgramaActivo(String programaActivo) {
        this.programaActivo = programaActivo;
    }

    public String getVersionPrograma() {
        return versionPrograma;
    }

    public void setVersionPrograma(String versionPrograma) {
        this.versionPrograma = versionPrograma;
    }

    public int getCboNivel() {
        return cboNivel;
    }

    public String getNomenclaturaProg() {
        return nomenclaturaProg;
    }

    public void setNomenclaturaProg(String nomenclaturaProg) {
        this.nomenclaturaProg = nomenclaturaProg;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public ArrayList getProgramasEducativos() {
        return programasEducativos;
    }

    public void setProgramasEducativos(ArrayList programasEducativos) {
        this.programasEducativos = programasEducativos;
    }

    public int getCboPE() {
        return cboPE;
    }

    public void setCboPE(int cboPE) {
        this.cboPE = cboPE;
    }

    public int getCboFamiliaProg() {
        return cboFamiliaProg;
    }

    public void setCboFamiliaProg(int cboFamiliaProg) {
        this.cboFamiliaProg = cboFamiliaProg;
    }

    public ArrayList getFamiliasProg() {
        return familiasProg;
    }

    public void setFamiliasProg(ArrayList familiasProg) {
        this.familiasProg = familiasProg;
    }

    public ArrayList getAreasProg() {
        return areasProg;
    }

    public void setAreasProg(ArrayList areasProg) {
        this.areasProg = areasProg;
    }

    public int getCboAreaProg() {
        return cboAreaProg;
    }

    public void setCboAreaProg(int cboAreaProg) {
        this.cboAreaProg = cboAreaProg;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public final void setAreasProg() {
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
                campos.add(new Coleccion(tabla.getInt("ID_area"), tabla.getString("NOMBRE_AREA")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutProgramasForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        areasProg = campos;
    }

    public final void setNiveles() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from nivel_pe");
            tabla.last();
            int j = tabla.getRow();
            tabla.beforeFirst();
            for (i = 0; i < j; i++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_nivel"), tabla.getString("abreviatura").concat(" (").concat(tabla.getString("descripcion")).concat(")")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        niveles = campos;
    }

    public CgutProgramasForm() {
        super();
        setAreasProg();
        setNiveles();

    }
}