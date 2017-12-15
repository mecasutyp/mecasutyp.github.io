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
public class ListaApoyos {
    
    ConexionMySQL con;
    ResultSet rs;
    ResultSet rsotras;
    ArrayList<Apoyos> apoyos;

    public ListaApoyos() {
        apoyos = new ArrayList<Apoyos>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT id_apoyo, descripcion FROM apoyos_estudiante where id_apoyo !=12 and activo=1 order by descripcion";
            rs = con.Consultar(sql);
            String sqlotras = "SELECT id_apoyo, descripcion FROM apoyos_estudiante where id_apoyo=12 and activo=1";
            rsotras = con.Consultar(sqlotras);
            int rows=0;
            while(rs.next()){
                rows++;
            }
            rs.beforeFirst();
            int con=0;
            int id_apoy=0;
            String desc="";
            while (rs.next()) {
                if(con!=(rows-1)){
                    id_apoy=rs.getInt("id_apoyo");
                    desc=rs.getString("descripcion");
                }else{
                    rsotras.next();
                    id_apoy=rsotras.getInt("id_apoyo");
                    desc=rsotras.getString("descripcion");
                }
                apoyos.add(
                        new Apoyos(id_apoy,
                        desc));
                con++;
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Apoyos en ListaApoyos.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }
    
    public int size(){
        return apoyos.size();
    }
    
    public int getId(int index){
        return apoyos.get(index).getId();
    }
    
    public String getNombre(int index){
        return apoyos.get(index).getNombre();
    }
    
}
