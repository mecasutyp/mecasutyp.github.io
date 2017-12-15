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
 * @author Cuauh
 */
public class ListaCausas {
    
    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Causas> causas;
    
    
    public ListaCausas(){
        causas = new ArrayList<Causas>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_causa, descripcion FROM causas_desercion";
            rs = con.Consultar(sql);
            while (rs.next()) {
                causas.add(
                        new Causas(rs.getInt("id_causa"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Causas en ListaCausas.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return causas.size();
    }
    
    public int getId(int index){
        return causas.get(index).getId();
    }
    
    public String getNombre(int index){
        return causas.get(index).getNombre();
    }
    
    
}