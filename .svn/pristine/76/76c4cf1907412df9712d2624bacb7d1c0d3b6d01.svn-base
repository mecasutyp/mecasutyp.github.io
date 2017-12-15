/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import com.mecasut.conexion.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DIN-A-C14-02
 */
public class Archivos {
    private int id_universidad;
    private int id_periodo;
    private int indicador;
    private int estado;

    public Archivos(int id_universidad, int id_periodo, int indicador) {
        this.id_universidad = id_universidad;
        this.id_periodo = id_periodo;
        this.indicador = indicador;
    }
    
    
    public int getId_universidad() {
        return id_universidad;
    }

    public void setId_universidad(int id_universidad) {
        this.id_universidad = id_universidad;
    }

    public int getId_periodo() {
        return id_periodo;
    }

    public void setId_periodo(int id_periodo) {
        this.id_periodo = id_periodo;
    }

    public int getIndicador() {
        return indicador;
    }

    public void setIndicador(int indicador) {
        this.indicador = indicador;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
      
    public void RegistrarArchivo() {//REGISTRA QUE SE ENVIO UN ARCHIVO POR CORREO
        ConexionMySQL con = new ConexionMySQL();
        ResultSet rs, rs2;
        String query = "";
        try {
            query= "INSERT INTO archivo values (".concat(String.valueOf(id_universidad)).concat(", ")
                    .concat(String.valueOf(id_periodo)).concat(", ").concat(String.valueOf(indicador)).concat(", 1)");
            con.Insertar(query);
            
        } catch (SQLException ex) {
            System.err.println("ERRORBD: Insertando Archivo; " + ex.getMessage());
        } finally {
            con.Desconectar();
        }
    }
    
}
