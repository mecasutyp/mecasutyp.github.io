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
public class ListaServicios {
    
    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Servicios> servicios;
    
    public ListaServicios(){
        servicios = new ArrayList<Servicios>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_servicio, nombre, descripcion FROM encuesta_servicios";
            rs = con.Consultar(sql);
            while (rs.next()) {
                servicios.add(
                        new Servicios(rs.getInt("id_servicio"),
                        rs.getString("nombre"), rs.getString("descripcion")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Servicios en ListaServicios.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return servicios.size();
    }
    
    public int getId(int index){
        return servicios.get(index).getId();
    }
    
    public String getNombre(int index){
        return servicios.get(index).getNombre();
    }
    
    public String getDescripcio(int index){
        return servicios.get(index).getDescripcion();
    }
    
}