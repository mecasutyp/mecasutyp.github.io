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
public class ListaModalidades {
    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Modalidades> modalidades;
    
    public ListaModalidades(){
        modalidades = new ArrayList<Modalidades>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_modelo, descripcion FROM modelos where activo=1";
            rs = con.Consultar(sql);
            while (rs.next()) {
                modalidades.add(
                        new Modalidades(rs.getInt("id_modelo"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando modalidades en ListaModalidades.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return modalidades.size();
    }
    
    public int getId(int index){
        return modalidades.get(index).getId();
    }
    
    public String getNombre(int index){
        return modalidades.get(index).getNombre();
    }
    
}