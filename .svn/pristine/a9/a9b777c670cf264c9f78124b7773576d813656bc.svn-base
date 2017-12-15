/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DIN-A-C14-02
 */
public class ListaOrganismos {

    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Organismos> organismos;

    public ListaOrganismos() {
        organismos = new ArrayList<Organismos>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_organismo, sigla, nombre, extranjero, pais FROM organismos_acreditacion WHERE activo=1 AND id_organismo>0";
            rs = con.Consultar(sql);
            while (rs.next()) {
                organismos.add(
                        new Organismos(rs.getInt("id_organismo"), rs.getString("nombre"), rs.getString("sigla"),
                        rs.getBoolean("extranjero"), rs.getString("pais")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Edificios en ListaEdificios.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }

    public int size() {
        return organismos.size();
    }

    public int getId(int index) {
        return organismos.get(index).getId();
    }
    
    public String getSiglas(int index){
        return organismos.get(index).getSigla();
    }
    
    public int getNacionales(){
        int nacionales=0;
        for (int i=0; i<organismos.size(); i++){
            if (!organismos.get(i).isExtranjero()){
                nacionales+=1;
            }
        }
        return nacionales;
    }
    
    public int getExtranjeros(){
        int extranjeros=0;
        for (int i=0; i<organismos.size(); i++){
            if (organismos.get(i).isExtranjero()){
                extranjeros+=1;
            }
        }
        return extranjeros;
    }

    public String getNombre(int index) {
        return organismos.get(index).getNombre();
    }
    
    public boolean isExtranjero(int index){
        return organismos.get(index).isExtranjero();
    }
}
