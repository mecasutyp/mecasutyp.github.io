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
public class ListaEdificios {
    
    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Edificios> edificios;
    
    public ListaEdificios() {
        edificios = new ArrayList<Edificios>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_edificio, descripcion, capacidad FROM edificios";
            rs = con.Consultar(sql);
            while (rs.next()) {
                edificios.add(
                        new Edificios(rs.getInt("id_edificio"),
                        rs.getString("descripcion"), rs.getInt("capacidad")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Edificios en ListaEdificios.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return edificios.size();
    }
    
    public int getId(int index){
        return edificios.get(index).getId();
    }
    
    public int getCapacidad(int index){
        return edificios.get(index).getCapacidad();
    }
    
    public String getNombre(int index){
        return edificios.get(index).getNombre();
    }
    
}