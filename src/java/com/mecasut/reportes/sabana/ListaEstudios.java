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
 * @author Cuauhtemoc Medina Muñoz
 */
public class ListaEstudios {
    
    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Estudios> estudios;
    
    public ListaEstudios(){
        estudios = new ArrayList<Estudios>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_estudio, descripcion FROM estudios_tecnologicos";
            rs = con.Consultar(sql);
            while (rs.next()) {
                estudios.add(
                        new Estudios(rs.getInt("id_estudio"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Estudios en ListaEstudios.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return estudios.size();
    }
    
    public int getId(int index){
        return estudios.get(index).getId();
    }
    
    public String getNombre(int index){
        return estudios.get(index).getNombre();
    }
    
}
