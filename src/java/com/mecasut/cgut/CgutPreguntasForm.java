/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.cgut;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public class CgutPreguntasForm extends org.apache.struts.action.ActionForm {

    private String ask = "";

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }
    private String preguntasActiva = "";
    private String txtNombrePreguntas = "";
    private ArrayList preguntas = new ArrayList();
    private String txtPalabraClavePreguntas = "";
    private ArrayList encuestas2 = new ArrayList();
    private int cmbPregEnc = -1;
    private int cboCategoPre2 = -1;
    private ArrayList categoPre2 = new ArrayList();
    private int cmbPregPreguntas = -1;

    public int getCmbPregPreguntas() {
        return cmbPregPreguntas;
    }

    public void setCmbPregPreguntas(int cmbPregPreguntas) {
        this.cmbPregPreguntas = cmbPregPreguntas;
    }

    public int getCmbPregEnc() {
        return cmbPregEnc;
    }

    public ArrayList getCategoPre2() {
        return categoPre2;
    }

    public void setCategoPre2(ArrayList categoPre2) {
        this.categoPre2 = categoPre2;
    }

    public int getCboCategoPre2() {
        return cboCategoPre2;
    }

    public void setCboCategoPre2(int cboCategoPre2) {
        this.cboCategoPre2 = cboCategoPre2;
    }

    public void setCmbPregEnc(int cmbPregEnc) {
        this.cmbPregEnc = cmbPregEnc;
    }

    public ArrayList getEncuestas2() {
        return encuestas2;
    }

    public void setEncuestas2(ArrayList encuestas2) {
        this.encuestas2 = encuestas2;
    }

    public String getTxtPalabraClavePreguntas() {
        return txtPalabraClavePreguntas;
    }

    public void setTxtPalabraClavePreguntas(String txtPalabraClavePreguntas) {
        this.txtPalabraClavePreguntas = txtPalabraClavePreguntas;
    }

    public ArrayList getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList preguntas) {
        this.preguntas = preguntas;
    }

    public String getTxtNombrePreguntas() {
        return txtNombrePreguntas;
    }

    public void setTxtNombrePreguntas(String txtNombrePreguntas) {
        this.txtNombrePreguntas = txtNombrePreguntas;
    }

    public String getPreguntasActiva() {
        return preguntasActiva;
    }

    public void setPreguntasActiva(String preguntasActiva) {
        this.preguntasActiva = preguntasActiva;
    }

    public void setEncuestas() {
        ArrayList encuestas = new ArrayList();
        ConexionMySQL con = new ConexionMySQL();

        try {
            ResultSet tabla = con.Consultar("SELECT e.id_encuesta, CONCAT(e.nombre, ' (',n.descripcion,')') as nombre FROM encuestas e INNER JOIN nivel_pe n on e.id_nivel=n.id_nivel");
            while (tabla.next()) {
                encuestas.add(new Coleccion(tabla.getInt(1), tabla.getString(2)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            con.Desconectar();
        }
        this.encuestas2 = encuestas;
    }

    public CgutPreguntasForm() {
        super();
        setEncuestas();
        // TODO Auto-generated constructor stub
    }
}
