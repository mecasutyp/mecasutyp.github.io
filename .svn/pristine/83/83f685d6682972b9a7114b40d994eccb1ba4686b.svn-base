package com.mecasut.reportes.sabana;

import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public class HojaCalculo {

    Workbook wb;
    Sheet sabana;
    Row[] filas;
    Cell[][] celdas;
    Sheet hojaDeEstilos;
    CellStyle h1Style; //h1
    CellStyle h2Style; //h2
    CellStyle dataStyle; //datos
    CellStyle dataStyle2; //datos2
    CellStyle resStyle; //resultado
    CellStyle totStyle; //total
    CellStyle totStyle2; //total2
    CellStyle errorStyle; //error

    public void leerLibro(InputStream entrada) throws IOException {
        try {
            wb = WorkbookFactory.create(entrada);
        } catch (Exception ex) {
            System.err.println("Error Leyendo Libro Excel : " + ex.getMessage());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.err.println("Hoja de Calculo Eliminada :)");
    }

    public Workbook getLibro() {
        return wb;
    }

    public void obtenerEstilos() {
        //obtener hoja 1
        hojaDeEstilos = wb.getSheetAt(0);
        //obtener los estilos de la hoja de estilos del archivo de excel
        h1Style = hojaDeEstilos.getRow(0).getCell(0).getCellStyle();
        h2Style = hojaDeEstilos.getRow(1).getCell(0).getCellStyle();
        dataStyle = hojaDeEstilos.getRow(3).getCell(0).getCellStyle();
        totStyle = hojaDeEstilos.getRow(5).getCell(0).getCellStyle();
        resStyle = hojaDeEstilos.getRow(7).getCell(0).getCellStyle();
        dataStyle2 = hojaDeEstilos.getRow(9).getCell(0).getCellStyle();
        totStyle2 = hojaDeEstilos.getRow(11).getCell(0).getCellStyle();
        errorStyle = hojaDeEstilos.getRow(13).getCell(0).getCellStyle();
    }

    public void eliminarHoja(int indice) {
        wb.removeSheetAt(indice);
    }

    public void crearHoja(String nombre, int f, int c) {
        try {
            sabana = wb.createSheet(nombre);
            filas = new XSSFRow[f];
            celdas = new XSSFCell[c][f];
            f--;
            c--;
            for (int i = 0; i <= c; i++) { //mueve las filas
                for (int j = 0; j <= f; j++) { //mueve las columnas (celdas)
                    if (i == 0) {
                        filas[j] = sabana.createRow(j);
                    }
                    celdas[i][j] = filas[j].createCell(i);
                }
            }
        } catch (Exception ex) {
            System.err.println("\n\n\n\n EXCEPTION: En la creación de celdasSabana " + ex.getMessage());
        }
    }

    public void setAlturaFilas(int fila, int altura) {
        try {
            filas[fila].setHeightInPoints((float) altura);
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO SET ALTURA FILAS " + ex.getMessage());
        }
    }

    public void setAnchoColumna(int columna, int ancho) {
        sabana.setColumnWidth(columna, ancho); //ancho de la columna para el nombre de las universidades
    }

    public void combinarCeldas(String texto, int columnaInicial, int columnaFinal, int filaInicial, int filaFinal, String estilo) {
        if (texto.equals("NO APLICA") || texto.equals("NO CONCLUIDO") || texto.equals("NO TIENE EL NIVEL") || texto.equals("NO TIENE EL SERVICIO")  || texto.equals("SIN MODALIDAD")) {
            for (int i = columnaInicial; i <= columnaFinal; i++) {//mueve las columnas
                for (int j = filaInicial; j <= filaFinal; j++) {//mueve las filas
                    celdas[i][j].setCellValue(0);
                    setStyle(i, j, "datos");
                }
            }
            //sabana.addMergedRegion(new CellRangeAddress(filaInicial, filaFinal, columnaInicial, columnaFinal));
        }else{
            for (int i = columnaInicial; i <= columnaFinal; i++) {//mueve las columnas
                for (int j = filaInicial; j <= filaFinal; j++) {//mueve las filas
                    celdas[i][j].setCellValue(texto);
                    setStyle(i, j, estilo);
                }
            }
            sabana.addMergedRegion(new CellRangeAddress(filaInicial, filaFinal, columnaInicial, columnaFinal));
        }
    }

    public void combinarCeldas(int texto, int columnaInicial, int columnaFinal, int filaInicial, int filaFinal, String estilo) {
        
            for (int i = columnaInicial; i <= columnaFinal; i++) {//mueve las columnas
                for (int j = filaInicial; j <= filaFinal; j++) {//mueve las filas
                    celdas[i][j].setCellValue(texto);
                    setStyle(i, j, estilo);
                }
            }
            sabana.addMergedRegion(new CellRangeAddress(filaInicial, filaFinal, columnaInicial, columnaFinal));
        
    }
    
    public void val(int columna, int fila, int valor) {
        try {
            celdas[columna][fila].setCellValue(valor);
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO VAL" + ex);
        }
    }

    public void val(int columna, int fila, float valor) {
        try {
            celdas[columna][fila].setCellValue(valor);
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO VAL" + ex);
        }
    }

    public void val(int columna, int fila, String texto) {
        try {
            if (texto.equals("NO APLICA") || texto.equals("NO CONCLUIDO") || texto.equals("NO TIENE EL NIVEL") || texto.equals("NO TIENE EL SERVICIO")  || texto.equals("SIN MODALIDAD")) {
                celdas[columna][fila].setCellValue(0);
            }else{
                celdas[columna][fila].setCellValue(texto);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO VAL" + ex);
        }
    }

    public void val(int columna, int fila, int valor, String estilo) {
        try {
            celdas[columna][fila].setCellValue(valor);
            setStyle(columna, fila, estilo);
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO VAL" + ex);
        }
    }

    public void val(int columna, int fila, float valor, String estilo) {
        try {
            celdas[columna][fila].setCellValue(valor);
            setStyle(columna, fila, estilo);
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO VAL" + ex);
        }
    }

    public void val(int columna, int fila, double valor, String estilo) {
        try {
            celdas[columna][fila].setCellValue(valor);
            setStyle(columna, fila, estilo);
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO VAL DOUBLE" + ex);
        }
    }

    public void val(int columna, int fila, String texto, String estilo) {
        try {
            if (texto.equals("NO APLICA") || texto.equals("NO CONCLUIDO") || texto.equals("NO TIENE EL NIVEL") || texto.equals("NO TIENE EL SERVICIO")  || texto.equals("SIN MODALIDAD")) {
                celdas[columna][fila].setCellValue(0);
                setStyle(columna, fila, estilo);
            }else{
                celdas[columna][fila].setCellValue(texto);
                setStyle(columna, fila, estilo);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO VAL" + ex);
        }
    }

    public void setStyle(int columna, int fila, String estilo) {
        try {
            if (estilo.equals("h1")) {
                celdas[columna][fila].setCellStyle(h1Style);
            } else if (estilo.equals("h2")) {
                celdas[columna][fila].setCellStyle(h2Style);
            } else if (estilo.equals("resultado")) {
                celdas[columna][fila].setCellStyle(resStyle);
            } else if (estilo.equals("datos")) {
                celdas[columna][fila].setCellStyle(dataStyle);
            } else if (estilo.equals("datos2")) {
                celdas[columna][fila].setCellStyle(dataStyle2);
            } else if (estilo.equals("total")) {
                celdas[columna][fila].setCellStyle(totStyle);
            } else if (estilo.equals("total2")) {
                celdas[columna][fila].setCellStyle(totStyle2);
            } else if (estilo.equals("error")) {
                celdas[columna][fila].setCellStyle(errorStyle);
            }else if (estilo.equals("program")) {
            } else {
                System.err.println("ERROR EN LOS ESTILOS, EL ESTILO : <<<<  " + estilo + " >>>>NO EXISTE ");
            }
        } catch (Exception ex) {
            System.err.println("ERROR: HOJA CALCULO MÉTODO SET STYLE" + ex.getMessage());
        }
    }
}