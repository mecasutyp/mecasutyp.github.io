package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Daniel RAmírez Torres
 */
public class CgutOrganismosForm extends org.apache.struts.action.ActionForm {

    private ArrayList organismos = new ArrayList();
    private ArrayList paises = new ArrayList();
    private int cboOrganismo = 0;
    private int extranjero = 0;
    private int selpais = 1;
    private String nombreOrganismo = "";
    private String ask = "login";
    private String organismoActivo = "";
    private String sigla = "";
    private String pais = "";
    private int cboPaises = -1;

    public int getCboPaises() {
        return cboPaises;
    }

    public void setCboPaises(int cboPaises) {
        this.cboPaises = cboPaises;
    }

    public ArrayList getPaises() {
        return paises;
    }

    public void setPaises(ArrayList paises) {
        this.paises = paises;
    }

    public int getExtranjero() {
        return extranjero;
    }

    public void setExtranjero(int extranjero) {
        this.extranjero = extranjero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getOrganismoActivo() {
        return organismoActivo;
    }

    public void setOrganismoActivo(String organismoActivo) {
        this.organismoActivo = organismoActivo;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getNombreOrganismo() {
        return nombreOrganismo;
    }

    public void setNombreOrganismo(String nombreOrganismo) {
        this.nombreOrganismo = nombreOrganismo;
    }

    public int getCboOrganismo() {
        return cboOrganismo;
    }

    public void setCboOrganismo(int cboOrganismo) {
        this.cboOrganismo = cboOrganismo;
    }

    public ArrayList getOrganismos() {
        return organismos;
    }

    public void setOrganismos(ArrayList organismos) {
        this.organismos = organismos;
    }

    public int getSelpais() {
        return selpais;
    }

    public void setSelpais(int selpais) {
        this.selpais = selpais;
    }

    public final void setListaOrganismos() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from organismos_acreditacion where id_organismo>0");
            while (tabla.next()) {
                i++;
            }
            tabla.beforeFirst();
            for (int j = 0; j < i; j++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("id_organismo"), tabla.getString("nombre")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        organismos = campos;
    }

    public final void setListaPais() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();
        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select distinct(pais) from organismos_acreditacion where id_organismo>0");
            while (tabla.next()) {
                i++;
            }
            tabla.beforeFirst();
            for (int j = 0; j < i; j++) {
                tabla.next();
                campos.add(new Coleccion(j, tabla.getString("pais")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        paises = campos;
    }

    public CgutOrganismosForm() {
        super();
        setListaOrganismos();
        setListaPais();
    }
}
