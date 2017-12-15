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

/**
 *
 * @author DELL
 */
public class CgutUniversidadForm extends org.apache.struts.action.ActionForm {

    private ArrayList universidades = new ArrayList();
    private String ask = "";
    private String cvecgut = "";
    private String nombreUni = "";
    private String abreviatura = "";
    private String fechaAcred = "";
    private String uniActivo = "";
    private String rfc="";

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    private int universidad = -1;
    
    public int getUniversidad() {
        return universidad;
    }

    public void setUniversidad(int universidad) {
        this.universidad = universidad;
    }  
    
    public String getUniActivo() {
        return uniActivo;
    }

    public void setUniActivo(String uniActivo) {
        this.uniActivo = uniActivo;
    }
    
    public String getFechaAcred() {
        return fechaAcred;
    }

    public void setFechaAcred(String fechaAcred) {
        this.fechaAcred = fechaAcred;
    }
    
    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
    
    public String getNombreUni() {
        return nombreUni;
    }

    public void setNombreUni(String nombreUni) {
        this.nombreUni = nombreUni;
    }
    
    public String getCvecgut() {
        return cvecgut;
    }

    public void setCvecgut(String cvecgut) {
        this.cvecgut = cvecgut;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public ArrayList getUniversidades() {
        return universidades;
    }

    public void setUniversidades(ArrayList universidades) {
        this.universidades = universidades;
    }

    public final void setUniversidades() {
        ArrayList campos = new ArrayList();
        ConexionMySQL conexion = new ConexionMySQL();

        int i = 0;
        try {
            ResultSet tabla = conexion.Consultar("Select * from universidades where id_universidad>0 order by nombre_universidad");
            while (tabla.next()) {
                i++;
            }
            tabla.beforeFirst();
            for (int j = 0; j < i; j++) {
                tabla.next();
                campos.add(new Coleccion(tabla.getInt("ID_universidad"), tabla.getString("NOMBRE_UNIVERSIDAD")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            conexion.Desconectar();
        }
        universidades = campos;
    }
    
    public CgutUniversidadForm() {
        super();
        setUniversidades();
        // TODO Auto-generated constructor stub
    }

}