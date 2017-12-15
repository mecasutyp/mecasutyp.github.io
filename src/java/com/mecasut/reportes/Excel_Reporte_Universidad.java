package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

public class Excel_Reporte_Universidad {
    HSSFWorkbook libro;
    HSSFSheet eficacia;
    CellStyle top;
    CellStyle datos_uni;
    CellStyle datos_uni_sub;
    CellStyle titulo_cat;
    CellStyle titulo_indicador;
    CellStyle titulo_indicador_subr;
    CellStyle linea_doble;
    //Estilos de Indicador
    CellStyle arial10_bordoble;
    CellStyle arial8_bordoble_text;
    CellStyle arial10_leyenda_res;
    CellStyle arial10_leyenda2_res;
    CellStyle resultado_base;
    CellStyle resultado_hor;
    CellStyle no_cuadro;
    CellStyle titulo_Cua_arial10MayNe;
    CellStyle titulo_Cua_arial9Ne;
    CellStyle res1decimal;
    CellStyle res1decimallidobleAria10;
    CellStyle resVerdecimallidobleArial9;
    CellStyle resVerdecimallidobleArial10;
    CellStyle ajustarContenido;
    CellStyle resmiles;
    CellStyle estilo;
    CellStyle texto_vertical;

    Excel_Reporte_Universidad(String ruta) throws FileNotFoundException, IOException {
        InputStream inp = new FileInputStream(ruta);
        //Creo el libro de excel a partir de un archivo para su modificación
        libro = new HSSFWorkbook(inp);
        eficacia = libro.getSheetAt(0);
        int filas = 39;
        HSSFRow[] filasEficacia = new HSSFRow[filas];
        HSSFCell[][] celdasEficacia = new HSSFCell[3][filas];
        for (int i = 0;
                i <= 2; i++) {
            for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                if (i == 0) {
                    filasEficacia[j] = eficacia.getRow(j);
                }
                celdasEficacia[i][j] = filasEficacia[j].getCell(i);
            }
        }

        top = celdasEficacia[0][0].getCellStyle();
        datos_uni = celdasEficacia[0][2].getCellStyle();
        datos_uni_sub = celdasEficacia[0][4].getCellStyle();
        titulo_cat = celdasEficacia[0][6].getCellStyle();
        titulo_indicador = celdasEficacia[0][8].getCellStyle();
        titulo_indicador_subr = celdasEficacia[0][10].getCellStyle();
        linea_doble = celdasEficacia[0][12].getCellStyle();
        //Estilos de Indicador
        arial10_bordoble = celdasEficacia[0][14].getCellStyle();
        arial8_bordoble_text = celdasEficacia[0][16].getCellStyle();
        arial10_leyenda_res = celdasEficacia[0][18].getCellStyle();
        arial10_leyenda2_res = celdasEficacia[0][20].getCellStyle();
        resultado_base = celdasEficacia[0][22].getCellStyle();
        resultado_hor = celdasEficacia[0][24].getCellStyle();
        no_cuadro = celdasEficacia[0][26].getCellStyle();
        titulo_Cua_arial10MayNe = celdasEficacia[0][28].getCellStyle();
        titulo_Cua_arial9Ne = celdasEficacia[0][30].getCellStyle();
        res1decimal = celdasEficacia[0][32].getCellStyle();
        res1decimallidobleAria10 = celdasEficacia[0][34].getCellStyle();
        resVerdecimallidobleArial9 = celdasEficacia[0][36].getCellStyle();
        resVerdecimallidobleArial10 = celdasEficacia[0][38].getCellStyle();
        ajustarContenido = celdasEficacia[2][0].getCellStyle();
        resmiles = celdasEficacia[2][2].getCellStyle();
        texto_vertical=celdasEficacia[2][4].getCellStyle();
        libro.removeSheetAt(0);

    }

    public HSSFWorkbook regresarLibro() {
        return libro;
    }
    
    public static String obtenerComentario(String iduni, String idperiodo, int indicador){
         ConexionMySQL con = new ConexionMySQL();
        String comentario = "Sin comentarios";
        try{
            String consulta = "SELECT comentario FROM comentarios WHERE  id_universidad ="+iduni+" "
                    + "AND id_periodo="+idperiodo+" AND indicador="+indicador;
            ResultSet rs = con.Consultar(consulta);
            if (rs.next()) {
                comentario = rs.getString("comentario");
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar comentario:" + ex);
        } finally {
            con.Desconectar();
        }
        return comentario;
    }
 
    public void espaciosEstilosVer(HSSFCell[][] celdas, int filaInicial, int filaFinal, int celdaInicial, int celdaFinal, String estilo, String texto, int incremento, int combinar, HSSFSheet hoja) {
        for (int i = celdaInicial; i <= celdaFinal; i++) {
            for (int j = filaInicial; j <= filaFinal; j++) {
                celdas[j][ i + incremento].setCellValue(".");
                if (estilo.equals("datos_uni")) {
                    celdas[j][  i + incremento].setCellStyle(datos_uni);
                }
                if (estilo.equals("top")) {
                    celdas[j][  i + incremento].setCellStyle(top);
                }
                if (estilo.equals("datos_uni_sub")) {
                    celdas[j][  i + incremento].setCellStyle(datos_uni_sub);
                }
                if (estilo.equals("titulo_cat")) {
//                    System.err.println("no friegues el titulo cat es: \n" + titulo_cat.toString() + "\n" + titulo_cat + "\n" + estilo);
                    celdas[j][  i + incremento].setCellStyle(titulo_cat);
                }
                if (estilo.equals("titulo_indicador")) {
                    celdas[j][  i + incremento].setCellStyle(titulo_indicador);
                }
                if (estilo.equals("titulo_indicador_subr")) {
                    celdas[j][  i + incremento].setCellStyle(titulo_indicador_subr);
                }
                if (estilo.equals("linea_doble")) {
                    celdas[j][  i + incremento].setCellStyle(linea_doble);
                }
                if (estilo.equals("arial10_bordoble")) {
                    celdas[j][  i + incremento].setCellStyle(arial10_bordoble);
                }
                if (estilo.equals("arial8_bordoble_text")) {
                    celdas[j][  i + incremento].setCellStyle(arial8_bordoble_text);
                }
                if (estilo.equals("arial10_leyenda_res")) {
                    celdas[j][  i + incremento].setCellStyle(arial10_leyenda_res);
                }
                if (estilo.equals("arial10_leyenda2_res")) {
                    celdas[j][  i + incremento].setCellStyle(arial10_leyenda2_res);
                }
                if (estilo.equals("resultado_base")) {
                    celdas[j][  i + incremento].setCellStyle(resultado_base);
                }
                if (estilo.equals("resultado_hor")) {
                    celdas[j][  i + incremento].setCellStyle(resultado_hor);
                }
                if (estilo.equals("no_cuadro")) {
                    celdas[j][  i + incremento].setCellStyle(no_cuadro);
                }
                if (estilo.equals("titulo_Cua_arial10MayNe")) {
                    celdas[j][  i + incremento].setCellStyle(titulo_Cua_arial10MayNe);
                }
                if (estilo.equals("titulo_Cua_arial9Ne")) {
                    celdas[j][  i + incremento].setCellStyle(titulo_Cua_arial9Ne);
                }
                if (estilo.equals("res1decimal")) {
                    celdas[j][  i + incremento].setCellStyle(res1decimal);
                }
                if (estilo.equals("res1decimallidobleAria10")) {
                    celdas[j][  i + incremento].setCellStyle(res1decimallidobleAria10);
                }
                if (estilo.equals("resVerdecimallidobleArial9")) {
                    celdas[j][  i + incremento].setCellStyle(resVerdecimallidobleArial9);
                }
                if (estilo.equals("resVerdecimallidobleArial10")) {
                    celdas[j][  i + incremento].setCellStyle(resVerdecimallidobleArial10);
                }
                if (estilo.equals("ajustarContenido")) {
                    celdas[j][  i + incremento].setCellStyle(ajustarContenido);
                }

                if (estilo.equals("resmiles")) {
                    celdas[j][  i + incremento].setCellStyle(resmiles);
                }
                if (estilo.equals("texto_vertical")) {
                    celdas[j][  i + incremento].setCellStyle(texto_vertical);
                }
            }
        }
        celdas[filaInicial][celdaInicial + incremento].setCellValue(texto);
        if (combinar == 1) {
            hoja.addMergedRegion(new CellRangeAddress(celdaInicial + incremento, celdaFinal + incremento, filaInicial, filaFinal));
        }
    }

    public double getEntero(double decimal) {
        decimal = decimal * (java.lang.Math.pow(1, 1));
        decimal = java.lang.Math.round(decimal);
        decimal = decimal / java.lang.Math.pow(1, 1);
        return decimal;
    }

    public double getDecimal(double decimal) {
//        decimal = decimal * (java.lang.Math.pow(10, 1));
//        decimal = java.lang.Math.round(decimal);
//        decimal = decimal / java.lang.Math.pow(10, 1);
//        
        decimal = Math.rint(decimal * 10)/10;
        return decimal;
    }
}
