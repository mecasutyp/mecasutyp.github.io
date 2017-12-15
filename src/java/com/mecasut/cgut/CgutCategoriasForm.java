package com.mecasut.cgut;
import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Daniel Ramírez Torres
 */
public class CgutCategoriasForm extends org.apache.struts.action.ActionForm {

    private String ask = "login";
    private int cboCategorias = -1;
    private String nombrCategorias = "";
    private String ded = "";

    public String getDed() {
        return ded;
    }

    public void setDed(String ded) {
        this.ded = ded;
    }
    private String categoriaActiva = "";
    private ArrayList categorias = new ArrayList();
    private ArrayList categorias2 = new ArrayList();

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public int getCboCategorias() {
        return cboCategorias;
    }

    public void setCboCategorias(int cboCategorias) {
        this.cboCategorias = cboCategorias;
    }

    public String getNombrCategorias() {
        return nombrCategorias;
    }

    public void setNombrCategorias(String nombrCategorias) {
        this.nombrCategorias = nombrCategorias;
    }

   

    public String getCategoriaActiva() {
        return categoriaActiva;
    }

    public void setCategoriaActiva(String categoriaActiva) {
        this.categoriaActiva = categoriaActiva;
    }

    public ArrayList getCategorias() {
        return categorias;
    }

    public ArrayList getCategorias2() {
        return categorias2;
    }

    public void setCategorias2(ArrayList categorias2) {
        this.categorias2 = categorias2;
    }

    public void setCategorias() {
        ArrayList categorias = new ArrayList();
        ConexionMySQL con = new ConexionMySQL();

        try {
            ResultSet tabla = con.Consultar("select * from system_categorias");
            while (tabla.next()) {
                categorias.add(new Coleccion(tabla.getInt(1), tabla.getString(3)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            con.Desconectar();
        }
        this.categorias = categorias;
        this.categorias2 = categorias;
    }

    public CgutCategoriasForm() {
        super();
        setCategorias();
        
    }
}

