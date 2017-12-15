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
 */
public class CgutFamiliasForm extends org.apache.struts.action.ActionForm {

    private String nombreFamilia, nomenclaturaFam, familiaActivo;
    private ArrayList areasFam = new ArrayList();
    private ArrayList familias = new ArrayList();
    private int cboAreaFam = -1;
    private int cboFamilia = -1;
    private String ask = "login";

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public ArrayList getAreasFam() {
        return areasFam;
    }

    public void setAreasFam(ArrayList areasFam) {
        this.areasFam = areasFam;
    }

    public void setFamilias(ArrayList familias) {
        this.familias = familias;
    }

    public ArrayList getFamilias() {
        return familias;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getNomenclaturaFam() {
        return nomenclaturaFam;
    }

    public void setNomenclaturaFam(String nomenclaturaFam) {
        this.nomenclaturaFam = nomenclaturaFam;
    }

    public String getFamiliaActivo() {
        return familiaActivo;
    }

    public void setFamiliaActivo(String familiaActivo) {
        this.familiaActivo = familiaActivo;
    }

    public int getCboAreaFam() {
        return cboAreaFam;
    }

    public void setCboAreaFam(int cboAreaFam) {
        this.cboAreaFam = cboAreaFam;
    }

    public int getCboFamilia() {
        return cboFamilia;
    }

    public void setCboFamilia(int cboFamilia) {
        this.cboFamilia = cboFamilia;
    }

    public final void setAreasFam() {
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
            Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.Desconectar();
        }
        areasFam = campos;
    }

    public CgutFamiliasForm() {
        super();
        setAreasFam();
        // TODO Auto-generated constructor stub
    }
}
