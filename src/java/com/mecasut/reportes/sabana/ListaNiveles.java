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
public class ListaNiveles {
    
    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Niveles> niveles;
    
    public ListaNiveles(boolean bandera){
        niveles = new ArrayList<Niveles>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql;
            if (bandera){ /*INDICADOR 29*/
                sql = "SELECT id_nivel, nombre, descripcion, abreviatura FROM nivel_pe";
            }else{
                sql = "SELECT id_nivel, nombre, descripcion, abreviatura FROM nivel_pe WHERE id_nivel>0";
            }
            rs = con.Consultar(sql);
            while (rs.next()) {
                niveles.add(
                        new Niveles(rs.getInt("id_nivel"),
                        rs.getString("nombre"), 
                        rs.getString("descripcion"),rs.getString("abreviatura") ));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Niveles en ListaNiveles.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return niveles.size();
    }
    
    public int getId(int index){
        return niveles.get(index).getId();
    }
    
    public String getNombre(int index){
        return niveles.get(index).getNombre();
    }
    
    public String getDescripcion(int index){
        return niveles.get(index).getDescripcion();
    }
     
    public String getAbreviatura(int index){
        return niveles.get(index).getAbreviatura();
    }
    
}
