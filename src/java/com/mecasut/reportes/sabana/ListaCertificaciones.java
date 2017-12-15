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
public class ListaCertificaciones {

    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Certificaciones> certificaciones;

    public ListaCertificaciones() {
        certificaciones = new ArrayList<Certificaciones>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_certificacion, nombre_certificacion FROM certificaciones";
            rs = con.Consultar(sql);
            while (rs.next()) {
                certificaciones.add(
                        new Certificaciones(rs.getInt("id_certificacion"),
                        rs.getString("nombre_certificacion")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Certificaciones en ListaCertificaciones.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return certificaciones.size();
    }
    
    public int getId(int index){
        return certificaciones.get(index).getId();
    }
    
    public String getNombre(int index){
        return certificaciones.get(index).getNombre();
    }
    
}