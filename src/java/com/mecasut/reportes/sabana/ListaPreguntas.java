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
public class ListaPreguntas {

    ConexionMySQL con;
    ResultSet rs;
    ArrayList<Preguntas> preguntas;

    public ListaPreguntas(String indicador) {
        preguntas = new ArrayList<Preguntas>();
        try {
            con = new ConexionMySQL();
            con.Conectar();
            String sql = "SELECT * from encuesta_preguntas ep"
                    .concat(" INNER JOIN encuestas e")
                    .concat(" ON e.id_encuesta=ep.id_encuesta ")
                    .concat(" WHERE e.indicador='").concat(indicador).concat("' ORDER BY e.id_nivel");
            rs = con.Consultar(sql);
            while (rs.next()) {
                preguntas.add(
                        new Preguntas(rs.getInt("id_pregunta"),
                        rs.getInt("id_encuesta"),
                        rs.getInt("id_servicio"),
                        rs.getString("pregunta"),
                        rs.getBoolean("activo"),
                        rs.getString("nombre_pregunta"),
                        rs.getInt("id_nivel")));
            }
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Consultando Preguntas en ListaPreguntas.java " + ex.getMessage());
            con.Desconectar();
        } finally {
            con.Desconectar();
        }
    }

    public int size() {
        return preguntas.size();
    }

    public int getId(int index) {
        return preguntas.get(index).getId_pregunta();
    }

    public int getIdEncuesta(int index) {
        return preguntas.get(index).getId_encuesta();
    }

    public String getPregunta(int index) {
        return preguntas.get(index).getPregunta();
    }

    public String getNombrePregunta(int index) {
        return preguntas.get(index).getNombre_pregunta();
    }
    
    public int getNivelPregunta(int index){
        return preguntas.get(index).getId_nivel();
    }

    public int getPreguntasxNivel(String nivel) {
        int contador = 0;
        for (Preguntas p : preguntas ){
            if ( String.valueOf(p.getId_nivel()).equals(nivel) ){
                contador ++;
            }
        }
        return contador;
    }
}
