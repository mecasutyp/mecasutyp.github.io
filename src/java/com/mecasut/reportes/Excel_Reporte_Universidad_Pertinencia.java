//Actualización 2016: Salvador Zamora Arias
package com.mecasut.reportes;
/*
 * Salvador Zamora Arias 05/09/2016 modificación
 */
import com.mecasut.conexion.ConexionMySQL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Excel_Reporte_Universidad_Pertinencia {

    public Excel_Reporte_Universidad_Pertinencia() {
    }

    public Excel_Reporte_Universidad crearPertinencia(String universidad, String periodo, Excel_Reporte_Universidad obj) throws FileNotFoundException, IOException {
        int columnas = 14;
        int celdas = 1000;
        String sql1 = "";
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ConexionMySQL con = new ConexionMySQL();

        int nuevaPos = 0, noC = 1, noProg = 1, cProg = 1;
        int posIniTi = 0;
        //Creo dos arreglos que van a contener las filas y las celdas          
        //Categoría PERTINENCIA
        HSSFSheet pertinenciaS = obj.regresarLibro().createSheet("PERTINENCIA");
        HSSFRow[] filasPertinenciaS = new HSSFRow[celdas];
        HSSFCell[][] celdasPertinenciaS = new HSSFCell[columnas][celdas];
        for (int l = 0; l < columnas; l++) {
            for (int j = 0; j < celdas - 1; j++) {
                filasPertinenciaS[j] = pertinenciaS.createRow(j);
                celdasPertinenciaS[l][j] = filasPertinenciaS[j].createCell(l);
            }
        }

        String titulo_cat = "titulo_cat";
        String titulo_indicador = "titulo_indicador";
        String titulo_indicador_subr = "titulo_indicador_subr";
        //Estilos de Indicador
        String titulo_Cua_arial10MayNe = "titulo_Cua_arial10MayNe";
        String titulo_Cua_arial9Ne = "titulo_Cua_arial9Ne";
        String ajustarContenido = "ajustarContenido";
        String texto_vertical = "texto_vertical";

        pertinenciaS.setColumnWidth(0, 37 * 88);
        pertinenciaS.setColumnWidth(1, 37 * 213);
        pertinenciaS.setColumnWidth(2, 37 * 88);
        pertinenciaS.setColumnWidth(3, 37 * 71);
        pertinenciaS.setColumnWidth(4, 37 * 93);
        pertinenciaS.setColumnWidth(5, 37 * 83);
        pertinenciaS.setColumnWidth(6, 37 * 80);
        pertinenciaS.setColumnWidth(7, 37 * 80);
        pertinenciaS.setColumnWidth(8, 37 * 89);
        pertinenciaS.setColumnWidth(9, 37 * 100);
        pertinenciaS.setColumnWidth(10, 37 * 76);
        pertinenciaS.setColumnWidth(11, 37 * 76);
        pertinenciaS.setColumnWidth(12, 37 * 76);
        pertinenciaS.setColumnWidth(13, 37 * 76);
        obj.espaciosEstilosVer(celdasPertinenciaS, 0, 10, posIniTi, nuevaPos, titulo_cat, "III. PERTINENCIA", 0, 1, pertinenciaS);
//Indicador 18
        try {
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 18", 2, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Programas Educativos con Evaluación Diagnóstica", 3, 1, pertinenciaS);
            //Encabezados
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            int noP = 0, noP2 = 0;
            noC = 1;
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 18.1." + rs.getString("id_nivel"), 5, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Relación de Programas Educativos de " + rs.getString("descripcion") + " con Evaluación Diagnóstica Según Nivel", 6, 1, pertinenciaS);
                filasPertinenciaS[posIniTi + nuevaPos + 7].setHeightInPoints(38);
                filasPertinenciaS[posIniTi + nuevaPos + 8].setHeightInPoints(38);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "NO.", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "PROGRAMA EDUCATIVO", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "PROGRAMA EDUCATIVO CON EVALUACIÓN DIAGNÓSTICA  SI= 1 / NO= 0", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NIVEL DEL PROGRAMA EDUCATIVO CON EVALUACIÓN DIAGNÓSTICA", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "1", 8, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "2", 8, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "3", 8, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "VIGENCIA DE LA EVALUACIÓN DIAGNÓSTICA", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "FECHA DE INICIO DE LA EVALUACIÓN DIAGNÓSTICA", 7, 1, pertinenciaS);
//Cuadro 1
                sql1 = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                rs2 = con.Consultar(sql1);
                int eva_diag = 0, niv1 = 0, niv2 = 0, niv3 = 0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(noP + 1), 9 + noP, 1, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("nombre_programa"), 9 + noP, 1, pertinenciaS);
//Valores cuadro 1
                    sql1 = "Select evaluacion_diagnostica,val_1,val_2,val_3,vigencia_evaluacion,fecha_evaluacion from pertinenciain18 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(rs2.getString("id_pe")).concat(" order by id_pe");
                    rs3 = con.Consultar(sql1);
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("evaluacion_diagnostica"), 9 + noP, 1, pertinenciaS);
                        eva_diag += rs3.getInt("evaluacion_diagnostica");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("val_1"), 9 + noP, 1, pertinenciaS);
                        niv1 += rs3.getInt("val_1");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("val_2"), 9 + noP, 1, pertinenciaS);
                        niv2 += rs3.getInt("val_2");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("val_3"), 9 + noP, 1, pertinenciaS);
                        niv3 += rs3.getInt("val_3");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("vigencia_evaluacion"), 9 + noP, 1, pertinenciaS);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("fecha_evaluacion"), 9 + noP, 1, pertinenciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 9 + noP, 1, pertinenciaS);


                    }
                    noP++;
                }
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(eva_diag), 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(niv1), 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(niv2), 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(niv3), 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                //DISTRIBUCIÓN PORCENTUAL TITULO
                 obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "DISTRIBUCIÓN PORCENTUAL", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEE1", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                //DISTRIBUCIÓN PORCENTUAL VALOR
                 obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 11 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 11 + noP, 1, pertinenciaS);
                double pee1 = (double)((double) eva_diag / (double) noP) *100;
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,String.valueOf(obj.getDecimal(pee1) +" %") , 11 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 11 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 11 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 11 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 11 + noP, 1, pertinenciaS);
                
                //Fin cuadro1

                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 18.2." + rs.getString("id_nivel"), 12 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Relación de la Matrícula en Programas Educativos de " + rs.getString("descripcion") + " con Evaluación Diagnóstica Según Nivel de Evaluación", 13 + noP, 1, pertinenciaS);
                //Cuadro 2                       
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "NO.", noP + 14 + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "PROGRAMA EDUCATIVO", 14 + noP + noP2, 1, pertinenciaS);
                filasPertinenciaS[posIniTi + nuevaPos + 13 + noP + noP2].setHeightInPoints(38);
                filasPertinenciaS[posIniTi + nuevaPos + 14 + noP + noP2].setHeightInPoints(38);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "MATRÍCULA INICIAL ATENDIDA", 14 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "MATRÍCULA SIN EVALUACIÓN DIAGNÓSTICA", 14 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "MATRÍCULA CON EVALUACIÓN DIAGNÓSTICA", 14 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NIVEL DE LA MATRÍCULA CON EVALUACIÓN DIAGNÓSTICA", 14 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "1", 15 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "2", 15 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "3", 15 + noP + noP2, 1, pertinenciaS);
                sql1 = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat("  and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                rs2 = con.Consultar(sql1);
                int mat = 0, sin_Ev = 0, con_Ev = 0, c2niv1 = 0, c2niv2 = 0, c2niv3 = 0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(noP2 + 1), 16 + noP + noP2, 1, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("nombre_programa"), 16 + noP + noP2, 1, pertinenciaS);
                    sql1 = "Select matricula_inicial,matricula_sin_eva,matricula_con_eva,val_1,val_2,val_3 from pertinenciain18_1 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(rs2.getString("id_pe")).concat(" order by id_pe");
                    rs3 = con.Consultar(sql1);

                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_inicial"), 16 + noP + noP2, 1, pertinenciaS);
                        mat += rs3.getInt("matricula_inicial");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_sin_eva"), 16 + noP + noP2, 1, pertinenciaS);
                        sin_Ev += rs3.getInt("matricula_sin_eva");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_con_eva"), 16 + noP + noP2, 1, pertinenciaS);
                        con_Ev += rs3.getInt("matricula_con_eva");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("val_1"), 16 + noP + noP2, 1, pertinenciaS);
                        c2niv1 += rs3.getInt("val_1");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("val_2"), 16 + noP + noP2, 1, pertinenciaS);
                        c2niv2 += rs3.getInt("val_2");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("val_3"), 16 + noP + noP2, 1, pertinenciaS);
                        c2niv3 += rs3.getInt("val_3");
                    } else {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 16 + noP + noP2, 1, pertinenciaS);
                    }
                    noP2++;
                }
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 16 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 16 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(mat), 16 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(sin_Ev), 16 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(con_Ev), 16 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(c2niv1), 16 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(c2niv2), 16 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(c2niv3), 16 + noP + noP2, 1, pertinenciaS);
                  
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 17 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "DISTRIBUCIÓN PORCENTUAL", 17 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 17 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 17 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEE2", 17 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEE3", 17 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 17 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 17 + noP + noP2, 1, pertinenciaS);
                
                  obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 18 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 18 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 18 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 18 + noP + noP2, 1, pertinenciaS);
                double pee2= (double)(((double)con_Ev/(double)mat)*100);
                double pee3= (double)(((double)c2niv1/(double)mat)*100);
                
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(pee2)+" %") , 18 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(pee3)+" %") , 18 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 18 + noP + noP2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 18 + noP + noP2, 1, pertinenciaS);
                
                nuevaPos += noP + 14 + noP2;
                noP = 0;
                noP2 = 0;
                noC++;
            }
            nuevaPos += 4;
              
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 18);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, pertinenciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 18" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 18" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 19
        try {
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 19", 2, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "A. Programas Educativos Acreditados en el país", 3, 1, pertinenciaS);
            //Encabezados
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            int noP = 0, noP2 = 0;
            noC = 1;
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 19.1." + rs.getString("id_nivel"), 5, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Relación de Programas Educativos de " + rs.getString("descripcion") + " Acreditados Nacionalmente", 6, 1, pertinenciaS);
                filasPertinenciaS[posIniTi + nuevaPos + 7].setHeightInPoints(50);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NO.", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PROGRAMA EDUCATIVO", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula Acreditada", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Acreditados        SI=1  / No=0", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nombre del organismo acreditador", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Vigencia de la acreditación", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Fecha de inicio de la Acreditación", 7, 1, pertinenciaS);
//Cuadro 1
                sql1 = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                rs2 = con.Consultar(sql1);
                int mat_at = 0, mat_acre = 0, acre = 0, niv3 = 0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(noP + 1), 8 + noP, 1, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("nombre_programa"), 8 + noP, 1, pertinenciaS);
                    sql1 = "Select matricula_inicial_aten,matricula_acreditada,acreditados,id_organismo,fecha_acreditacion,vigencia_acreditacion from pertinenciain19 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(String.valueOf(rs2.getString(1))).concat(" order by id_pe");
                    rs3 = con.Consultar(sql1);
                    if (rs3.next()) {
                        filasPertinenciaS[posIniTi + nuevaPos + 8 + noP].setHeightInPoints(30);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_inicial_aten"), 8 + noP, 1, pertinenciaS);
                        mat_at += rs3.getInt("matricula_inicial_aten");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_acreditada"), 8 + noP, 1, pertinenciaS);
                        mat_acre += rs3.getInt("matricula_acreditada");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("acreditados"), 8 + noP, 1, pertinenciaS);
                        acre += rs3.getInt("acreditados");
                        sql1 = "SELECT nombre FROM organismos_acreditacion WHERE id_organismo=" + rs3.getString("id_organismo") + " and extranjero='0'";
                        rs4 = con.Consultar(sql1);
                        if (rs4.next()) {
                            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs4.getString("nombre"), 8 + noP, 1, pertinenciaS);
                        }
                        obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("vigencia_acreditacion"), 8 + noP, 1, pertinenciaS);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("fecha_acreditacion"), 8 + noP, 1, pertinenciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 8 + noP, 1, pertinenciaS);
                    }
                    noP++;
                }
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(mat_at), 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(mat_acre), 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(acre), 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 8 + noP, 1, pertinenciaS);
               //DISTRIBUCION PORCENTUAL
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "DISTRIBUCIÓN PORCENTUAL", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEA2", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEA1", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                double pea2= (double) ((double)((double)mat_acre/(double)mat_at)*100);
                  double pea1= (double) ((double)((double)acre/(double)noP)*100);
                 obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(pea2))+" %", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(pea1))+"%", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                
                //Fin cuadro1
                nuevaPos += noP + 8;
                noP = 0;
                noC++;
            }
            nuevaPos += 2;
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 19", 2, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "B. Programas Educativos Acreditados en el Extranjero", 3, 1, pertinenciaS);
            rs.beforeFirst();
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 19.2." + rs.getString("id_nivel"), 5, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Relación de Programas Educativos de " + rs.getString("descripcion") + " Acreditados Internacionalmente", 6, 1, pertinenciaS);
                filasPertinenciaS[posIniTi + nuevaPos + 7].setHeightInPoints(50);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NO.", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PROGRAMA EDUCATIVO", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula Acreditada", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Acreditados        SI=1  / No=0", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nombre del organismo acreditador", 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Siglas", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "País", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Vigencia de la acreditación", 7, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Fecha de inicio de la Acreditación", 7, 01, pertinenciaS);
//Cuadro 2
                sql1 = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                rs2 = con.Consultar(sql1);
                int mat_at = 0, mat_acre = 0, acre = 0, niv3 = 0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(noP + 1), 8 + noP, 1, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("nombre_programa"), 8 + noP, 1, pertinenciaS);
                    sql1 = "Select matricula_inicial_aten,matricula_acreditada,acreditados,id_organismo,vigencia_acreditacion,fecha_acreditacion from pertinenciain19_1 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(String.valueOf(rs2.getString(1))).concat(" order by id_pe");
                    rs3 = con.Consultar(sql1);
                    if (rs3.next()) {
                        filasPertinenciaS[posIniTi + nuevaPos + 8 + noP].setHeightInPoints(30);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_inicial_aten"), 8 + noP, 1, pertinenciaS);
                        mat_at += rs3.getInt("matricula_inicial_aten");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_acreditada"), 8 + noP, 1, pertinenciaS);
                        mat_acre += rs3.getInt("matricula_acreditada");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("acreditados"), 8 + noP, 1, pertinenciaS);
                        acre += rs3.getInt("acreditados");
                        sql1 = "SELECT nombre,sigla,pais FROM organismos_acreditacion WHERE id_organismo=" + rs3.getString("id_organismo") + " and extranjero='1'";
                        rs4 = con.Consultar(sql1);
                        if (rs4.next()) {
                            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs4.getString("nombre"), 8 + noP, 1, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs4.getString("sigla"), 8 + noP, 1, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs4.getString("pais"), 8 + noP, 1, pertinenciaS);
                        }
                        obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("vigencia_acreditacion"), 8 + noP, 1, pertinenciaS);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("fecha_acreditacion"), 8 + noP, 1, pertinenciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 8 + noP, 1, pertinenciaS);
                    }
                    noP++;
                }
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(mat_at), 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(mat_acre), 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(acre), 8 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 8 + noP, 1, pertinenciaS);
                
                //DISTRIBUCIÓN PORCENTUAL
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "DISTRIBUCIÓN PORCENTUAL", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEAI2", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEAI1", 9 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 9 + noP, 1, pertinenciaS);
                double peaI2= (double) ((double)((double)mat_acre/(double)mat_at)*100);
                  double peaI1= (double) ((double)((double)acre/(double)noP)*100);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(peaI2))+" %", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(peaI1))+" %", 10 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 10 + noP, 1, pertinenciaS);
                
                
                //Fin cuadro1
                nuevaPos += noP + 8;
                noP = 0;
                noC++;
            }
            nuevaPos += 3;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 19);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, pertinenciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 19" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 19" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 20
        try {
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 20", 2, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Enfoques Centrados en el aprendizaje", 3, 1, pertinenciaS);
            //Encabezados
//Cuadro 1
            noC = 1;
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 20.1", 5, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cantidad de acciones que ha realizado la institución durante el período a evaluar para contar con PE que cumplan con elementos descritos en el  Modelo Educativo centrado en el aprendizaje", 6, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cursos", 7, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Talleres", 7, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Otro", 7, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Total", 7, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Internos", 8, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Externos", 8, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Internos", 8, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Externos", 8, 0, pertinenciaS);
//Cuadro 2
            filasPertinenciaS[posIniTi + nuevaPos + 13].setHeightInPoints(27);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 20.2", 12, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cantidad de  Profesores de Tiempo Completo (PTC) que han participado en las acciones durante el período a evaluar", 13, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cursos", 14, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Talleres", 14, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Otro", 14, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Total", 14, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Internos", 15, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Externos", 15, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Internos", 15, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Externos", 15, 0, pertinenciaS);
//Cuadro 3
            filasPertinenciaS[posIniTi + nuevaPos + 20].setHeightInPoints(27);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 20.3", 19, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cantidad de Profesores de Asignatura (PA) que han participado en las acciones durante el periodo a evaluar", 20, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cursos", 21, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Talleres", 21, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Otro", 21, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Total", 21, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Internos", 22, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Externos", 22, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Internos", 22, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Externos", 22, 0, pertinenciaS);
//Cuadro 4
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 20.4", 26, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cantidad de material de los Enfoque Centrados en el Aprendizaje", 27, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Difusión", 28, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Capacitación", 28, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Otro", 28, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 28, 1, pertinenciaS);
//Cuadro 5
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 20.5", 32, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Usuarios a quien va dirigido el material", 33, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PTC", 34, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PA", 34, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos", 34, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Otro", 34, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Especifique", 34, 0, pertinenciaS);
//Cuadro 6
            filasPertinenciaS[posIniTi + nuevaPos + 40].setHeightInPoints(46);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 20.6", 38, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cantidad de PTC según situación en los enfoques", 39, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Terminaron la capacitación ", 40, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Están en proceso de capacitación", 40, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "No tienen capacitación", 40, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 40, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PTC  que están aplicando estos enfoques", 40, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nota: El total deberá de coincidir con el indicador No. 23", 42, 1, pertinenciaS);
//Cuadro 7
            filasPertinenciaS[posIniTi + nuevaPos + 40].setHeightInPoints(46);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 20.7", 45, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cantidad de PA según situación en los enfoques", 46, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Terminaron la capacitación ", 47, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Están en proceso de capacitación", 47, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "No tienen capacitación", 47, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 47, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PA  que están aplicando estos enfoques", 47, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nota: El total deberá de coincidir con el indicador No. 24", 49, 1, pertinenciaS);


            sql1 = "Select * from pertinenciain20 where id_Universidad = ".concat(universidad).concat(" and id_Periodo = ").concat(periodo);
            rs = con.Consultar(sql1);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cursos_realizados_i"), 9, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cursos_realizados_e"), 9, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("talleres_realizados_i"), 9, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("talleres_realizados_e"), 9, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("acciones_otro"), 9, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(rs.getInt("cursos_realizados_i")
                        + rs.getInt("cursos_realizados_e") + rs.getInt("talleres_realizados_i") + rs.getInt("talleres_realizados_e") + rs.getInt("acciones_otro")), 9, 0, pertinenciaS);

                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_cursos_i"), 16, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_cursos_e"), 16, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_talleres_i"), 16, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_talleres_e"), 16, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_otros"), 16, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(rs.getInt("ptc_cursos_i") + rs.getInt("ptc_cursos_e") + rs.getInt("ptc_talleres_i") + rs.getInt("ptc_talleres_e") + rs.getInt("ptc_otros")), 16, 0, pertinenciaS);

                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_cursos_i"), 23, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_cursos_e"), 23, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_talleres_i"), 23, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_talleres_e"), 23, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_otros"), 23, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(rs.getInt("pa_cursos_i") + rs.getInt("pa_cursos_e") + rs.getInt("pa_talleres_i") + rs.getInt("pa_talleres_e") + rs.getInt("pa_otros")), 23, 0, pertinenciaS);

                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("material_difusion"), 29, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("material_capacitacion"), 29, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("material_otro"), 29, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(rs.getInt("material_difusion") + rs.getInt("material_capacitacion") + rs.getInt("material_otro")), 29, 0, pertinenciaS);
//5
                if (rs.getInt("material_ptc") == 1) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "X", 35, 0, pertinenciaS);
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "", 35, 0, pertinenciaS);
                }
                if (rs.getInt("material_d_pa") == 1) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "X", 35, 0, pertinenciaS);
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "", 35, 0, pertinenciaS);
                }
                if (rs.getInt("material_d_alumnos") == 1) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "X", 35, 0, pertinenciaS);
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "", 35, 0, pertinenciaS);
                }
                if (rs.getInt("material_d_otro") == 1) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "X", 35, 0, pertinenciaS);
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "", 35, 0, pertinenciaS);
                }
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("material_especifique"), 35, 0, pertinenciaS);
                //6
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_terminaron"), 41, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_proceso"), 41, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_no_tienen"), 41, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(rs.getInt("ptc_terminaron") + rs.getInt("ptc_proceso") + rs.getInt("ptc_no_tienen")), 41, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ptc_aplicando"), 41, 1, pertinenciaS);
                //7
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_terminaron"), 48, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_proceso"), 48, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_no_tienen"), 48, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(rs.getInt("pa_terminaron") + rs.getInt("pa_proceso") + rs.getInt("pa_no_tienen")), 48, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("pa_aplicando"), 48, 1, pertinenciaS);
            } else {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 9, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 9, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 16, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 16, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 23, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 23, 1, pertinenciaS);

                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 29, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 29, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 41, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 41, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 41, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 48, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 48, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 48, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 35, 1, pertinenciaS);
            }
            nuevaPos += 49;
            noC++;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 20);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, pertinenciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 20" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 20" + e);
        } finally {
            con.Desconectar();
        }
//21
        try {
            noC = 0;
            ArrayList Servicios = new ArrayList();
            ArrayList col_A = new ArrayList();
            ArrayList col_B = new ArrayList();
            ArrayList col_C = new ArrayList();
            ArrayList col_D = new ArrayList();
            ArrayList col_E = new ArrayList();
            ArrayList col_F = new ArrayList();
            ArrayList col_G = new ArrayList();
            ArrayList col_H = new ArrayList();
            ArrayList col_I = new ArrayList();
            ArrayList col_J = new ArrayList();
            ArrayList col_K5 = new ArrayList();
            ArrayList col_K10 = new ArrayList();
            int numServ = 0, contSi = 0, contNo = 0;
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 21", 3, 0, null);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Programas Educativos Centrados en el Estudiante", 4, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 21", 6, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Programas Educativos Centrados en el Estudiante", 7, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "No.", 8, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Servicio.", 8, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "No. de Preguntas", 8, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "¿Se tiene el servicio?  ", 8, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "SI", 9, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NO", 9, 0, pertinenciaS);
            sql1 = "select * from  encuesta_servicios";
            rs = con.Consultar(sql1);
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(numServ + 1), 10 + numServ, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, rs.getString("nombre"), 10 + numServ, 0, pertinenciaS);
                sql1 = "select *from servicios_universidad where id_universidad='".concat(universidad).concat("' and id_periodo='".concat(periodo).concat("' and id_servicio='".concat(rs.getString(1)).concat("'")));
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "1", 10 + numServ, 0, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "", 10 + numServ, 0, pertinenciaS);
                    contSi++;
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "0", 10 + numServ, 0, pertinenciaS);
                    contNo++;
                }
                sql1 = "select count(pregunta) from encuesta_preguntas where id_servicio=".concat(rs.getString(1));
                rs3 = con.Consultar(sql1);
                if (rs3.next()) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString(1), 10 + numServ, 0, pertinenciaS);
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "", 10 + numServ, 0, pertinenciaS);
                }
                numServ++;
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de Servicios", 10 + numServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(contSi), 10 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(contNo), 10 + numServ, 0, pertinenciaS);


            //Cuadros Servicios Encuestas
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 21.1", 12 + numServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Grado de Satisfacción de los Alumnos por Servicios", 13 + numServ, 1, pertinenciaS);
//                    filasPertinenciaS[posIniTi + 14 + nuevaPos].setHeightInPoints(23);
//                    filasPertinenciaS[posIniTi + 15 + nuevaPos].setHeightInPoints(35);
//                    filasPertinenciaS[posIniTi + 16 + nuevaPos].setHeightInPoints(36);
//                    espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, titulo_Cua_arial9Ne, "NO.", 14 + numServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, titulo_Cua_arial9Ne, "DESCRIPCIÓN", 14 + numServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "FRECUENCIAS", 14 + numServ, 1, pertinenciaS);
            for (int i = 1; i <= 5; i++) {
                obj.espaciosEstilosVer(celdasPertinenciaS, i + 1, i + 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(6 - i), 15 + numServ, 0, pertinenciaS);
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NO APLICA (NA)", 15 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NO ESPECIFICADO (NE)", 15 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL            H", 15 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL I", 15 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL           J", 15 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "TOTAL             K, BASE 5", 14 + numServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, titulo_Cua_arial9Ne, "TOTAL             K, BASE 10", 14 + numServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "A", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "B", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "C", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "D", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "E", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "F", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "G", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Σ A a G", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Σ A a E", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "A * 5 +B*  4 +C * 3 + D * 2 + E* 1", 16 + numServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "J / I", 16 + numServ, 0, pertinenciaS);
            sql1 = "select * from  encuesta_servicios";
            rs = con.Consultar(sql1);
            int contServ = 0, tot_serv = 1;
            while (rs.next()) {
                sql1 = "select *from servicios_universidad where id_universidad='".concat(universidad).concat("' and id_periodo='".concat(periodo).concat("' and id_servicio='".concat(rs.getString(1)).concat("'")));
                rs2 = con.Consultar(sql1);
                while (rs2.next()) {
                    sql1 = "select id_pregunta,pregunta from encuesta_preguntas where id_servicio=".concat(rs2.getString(3));
                    rs4 = con.Consultar(sql1);
                    int r_a = 0, r_b = 0, r_c = 0, r_d = 0, r_e = 0, r_f = 0, r_g = 0, totalh_h = 0, totalh_i = 0, totalh_J;
                    int totalh = 0, totali = 0, totalj = 0;
                    while (rs4.next()) {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, (contServ + 1) + ". " + rs4.getString("pregunta"), 17 + numServ + contServ, 1, pertinenciaS);
                        sql1 = "SELECT r_a, r_b, r_c, r_d, r_e, r_f, r_g FROM pertinenciain21 WHERE id_pregunta=".concat(rs4.getString("id_pregunta").concat(" and id_universidad=").concat(universidad).concat(" and id_periodo=").concat(periodo));
                        rs5 = con.Consultar(sql1);
                        if (rs5.next()) {
                            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs5.getString("r_a"), 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs5.getString("r_b"), 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs5.getString("r_c"), 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs5.getString("r_d"), 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs5.getString("r_e"), 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs5.getString("r_f"), 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs5.getString("r_g"), 17 + numServ + contServ, 0, pertinenciaS);
                            r_a += rs5.getInt("r_a");
                            r_b += rs5.getInt("r_b");
                            r_c += rs5.getInt("r_c");
                            r_d += rs5.getInt("r_d");
                            r_e += rs5.getInt("r_e");
                            r_f += rs5.getInt("r_f");
                            r_g += rs5.getInt("r_g");
                            //Totales Verticales
                            totalh = rs5.getInt("r_a") + rs5.getInt("r_b") + rs5.getInt("r_c") + rs5.getInt("r_d") + rs5.getInt("r_e") + rs5.getInt("r_f") + rs5.getInt("r_g");
                            totalh_h += totalh;
                            obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalh), 17 + numServ + contServ, 0, pertinenciaS);
                            totali = rs5.getInt("r_a") + rs5.getInt("r_b") + rs5.getInt("r_c") + rs5.getInt("r_d") + rs5.getInt("r_e");
                            totalh_i += totali;
                            obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totali), 17 + numServ + contServ, 0, pertinenciaS);
                            for (int i = 1; i <= 5; i++) {
                                totalj += rs5.getInt(i) * (6 - i);
                            }
                            obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalj), 17 + numServ + contServ, 0, pertinenciaS);
                            if (totali != 0) {
                                double totalk= obj.getDecimal( (double) totalj / (double) totali );
                                obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalk), 17 + numServ + contServ, 0, pertinenciaS);
                                obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(totalk * 2)), 17 + numServ + contServ, 0, pertinenciaS);
                            } else {
                                obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                                obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                            }

                            totalj = 0;

                        } else {
                            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 17 + numServ + contServ, 1, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                            obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                        }
                        contServ++;
                    }
                    filasPertinenciaS[posIniTi + nuevaPos + 17 + numServ + contServ].setHeightInPoints(24);
                    //Totales Horizontales 
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Subtotal " + tot_serv, 17 + numServ + contServ, 1, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(r_a), 17 + numServ + contServ, 0, pertinenciaS);
                    col_A.add(r_a);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(r_b), 17 + numServ + contServ, 0, pertinenciaS);
                    col_B.add(r_b);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(r_c), 17 + numServ + contServ, 0, pertinenciaS);
                    col_C.add(r_c);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(r_d), 17 + numServ + contServ, 0, pertinenciaS);
                    col_D.add(r_d);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(r_e), 17 + numServ + contServ, 0, pertinenciaS);
                    col_E.add(r_e);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(r_f), 17 + numServ + contServ, 0, pertinenciaS);
                    col_F.add(r_f);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(r_g), 17 + numServ + contServ, 0, pertinenciaS);
                    col_G.add(r_g);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalh_h), 17 + numServ + contServ, 0, pertinenciaS);
                    col_H.add(totalh_h);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalh_i), 17 + numServ + contServ, 0, pertinenciaS);
                    col_I.add(totalh_i);
                    totalh_J = r_a * 5 + r_b * 4 + r_c * 3 + r_d * 2 + r_e * 1;
                    obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalh_J), 17 + numServ + contServ, 0, pertinenciaS);
                    if (totalh_i != 0) {
                        double total_K=obj.getDecimal((double) totalh_J / (double) totalh_i);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_K), 17 + numServ + contServ, 0, pertinenciaS);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(total_K * 2)), 17 + numServ + contServ, 0, pertinenciaS);
                        col_K5.add(obj.getDecimal(totalh_J / (double) totalh_i));
                        col_K10.add(obj.getDecimal((totalh_J / (double) totalh_i) * 2));
                    } else {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 17 + numServ + contServ, 0, pertinenciaS);
                        col_K5.add(0);
                        col_K10.add(0);
                    }
                    contServ += 1;
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos + (contServ - 2), texto_vertical, rs.getString(2), 17 + numServ, 1, pertinenciaS);
                    Servicios.add(rs.getString(2));
                    nuevaPos += contServ;
                    contServ = 0;
                    tot_serv++;
                }
            }
            int coluA = 0, coluB = 0, coluC = 0, coluD = 0, coluE = 0, coluF = 0, coluG = 0, coluH = 0, coluI = 0, coluJ = 0, coluK5 = 0, coluK10 = 0;
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "Total", 17 + numServ + contServ, 1, pertinenciaS);
            for (int i = 0; i < col_A.size(); i++) {
                coluA += Integer.parseInt(col_A.get(i).toString());
                coluB += Integer.parseInt(col_B.get(i).toString());
                coluC += Integer.parseInt(col_C.get(i).toString());
                coluD += Integer.parseInt(col_D.get(i).toString());
                coluE += Integer.parseInt(col_E.get(i).toString());
                coluF += Integer.parseInt(col_F.get(i).toString());
                coluG += Integer.parseInt(col_G.get(i).toString());
                coluH += Integer.parseInt(col_H.get(i).toString());
                coluI += Integer.parseInt(col_I.get(i).toString());
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluA), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluB), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluC), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluD), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluE), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluF), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluG), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluH), 17 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluI), 17 + numServ + contServ, 0, pertinenciaS);
            coluJ = coluA * 5 + coluB * 4 + coluC * 3 + coluD * 2 + coluE * 1;
            obj.espaciosEstilosVer(celdasPertinenciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(coluJ), 17 + numServ + contServ, 0, pertinenciaS);
            if (coluI != 0) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(obj.getDecimal((double) coluJ / (double) coluI)), 17 + numServ + contServ, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, String.valueOf(obj.getDecimal(((double) coluJ / (double) coluI) * 2)), 17 + numServ + contServ, 0, pertinenciaS);
            } else {
                obj.espaciosEstilosVer(celdasPertinenciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "0", 17 + numServ + contServ, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "0", 17 + numServ + contServ, 0, pertinenciaS);
            }
//Cuadro Final de Totales
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 21.2", 20 + numServ + contServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Servicios que Ofrece la Universidad", 21 + numServ + contServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "No.", 22 + numServ + contServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Servicio.", 22 + numServ + contServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Calificación", 22 + numServ + contServ, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Escala 5", 23 + numServ + contServ, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Escala 10", 23 + numServ + contServ, 0, pertinenciaS);
            if (!Servicios.isEmpty()) {
                for (int i = 0; i < Servicios.size(); i++) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(i + 1), 24 + numServ + contServ + i, 0, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, Servicios.get(i).toString(), 24 + numServ + contServ + i, 0, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, col_K5.get(i).toString(), 24 + numServ + contServ + i, 0, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, col_K10.get(i).toString(), 24 + numServ + contServ + i, 0, pertinenciaS);
                }
            }
            if (coluI != 0) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal((double) coluJ / (double) coluI)), 24 + numServ + contServ + Servicios.size(), 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(((double) coluJ / (double) coluI) * 2)), 24 + numServ + contServ + Servicios.size(), 0, pertinenciaS);
            } else {
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 24 + numServ + contServ + Servicios.size(), 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 24 + numServ + contServ + Servicios.size(), 0, pertinenciaS);
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de Servicios", 24 + numServ + contServ + Servicios.size(), 1, pertinenciaS);
            nuevaPos += 27 + numServ + Servicios.size();
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 21" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 21" + e);
        } finally {
            con.Desconectar();
        }
//22
        try {
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 22", 0, 0, null);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Programas Educativos Pertinentes", 1, 1, pertinenciaS);
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            int noP = 0;
            noC = 1;
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 22.1." + rs.getString("id_nivel"), 2, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Relación de Programas Educativos Pertinentes de " + rs.getString("descripcion") + " por Matrícula Según Fechas de Estudios", 3, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NO.", 4, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "NOMBRE DEL PROGRAMA EDUCATIVO QUE OFRECE LA UNIVERSIDAD TECNOLÓGICA", 4, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial ", 4, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula Pertinente", 4, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Pertinente        SI=1  / No=0", 4, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Año de inicio de la Carrera", 4, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Año del último estudio de factibilidad", 4, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Año del último estudio de análisis de la situación de trabajo", 4, 1, pertinenciaS);
                sql1 = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                rs2 = con.Consultar(sql1);
                int mat_in = 0, mat_pert = 0, pert = 0, niv3 = 0;
                float ttales=0,total=0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(noP + 1), 5 + noP, 0, pertinenciaS);
                    obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("nombre_programa"), 5 + noP, 1, pertinenciaS);
                    sql1 = "Select matricula_ini_pe,matricula_pert,pertinente,anio_inicio,anio_estudio,anio_analisis from pertinenciain22 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(String.valueOf(rs2.getString(1))).concat(" order by id_pe");
                    rs3 = con.Consultar(sql1);
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_ini_pe"), 5 + noP, 0, pertinenciaS);
                        mat_in += rs3.getInt("matricula_ini_pe");
                        obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("matricula_pert"), 5 + noP, 0, pertinenciaS);
                        mat_pert += rs3.getInt("matricula_pert");
                       
                        if(mat_pert!=0){
                         ttales=(mat_in/mat_pert)*100;
                        }
                        obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("pertinente"), 5 + noP, 0, pertinenciaS);
                        pert += rs3.getInt("pertinente");
                        if(pert!=0){
                        total=(pert/pert)*100;
                        }
                        obj.espaciosEstilosVer(celdasPertinenciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("anio_inicio"), 5 + noP, 0, pertinenciaS);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("anio_estudio"), 5 + noP, 0, pertinenciaS);
                        obj.espaciosEstilosVer(celdasPertinenciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("anio_analisis"), 5 + noP, 0, pertinenciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasPertinenciaS, 4, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 5 + noP, 1, pertinenciaS);
                    }
                    noP++;
                }
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 5 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 5 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(mat_in), 5 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(mat_pert), 5 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pert), 5 + noP, 1, pertinenciaS);
                //Fin cuadro1
                 obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 6 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Distribución porcentual", 6 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 6 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEP2", 6 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PEP1", 6 + noP, 1, pertinenciaS);
               double pep2=0,pep1=0; 
               if(mat_in!=0){
                pep2= (double) (((double)mat_pert / (double)mat_in)*100);
               }
               if(noP!=0){
                pep1= (double) (((double)pert / (double) noP)*100);
               }
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 7 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 7 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 7 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(pep2))+" %", 7 + noP, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(pep1))+" %", 7 + noP, 1, pertinenciaS);
                
                
                nuevaPos += noP + 7;
                noP = 0;
                noC++;
            }
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 22);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, pertinenciaS);
            nuevaPos += 5;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 22 " + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 22 " + e);
        } finally {
            con.Desconectar();
        }
        //23
        try {
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 23", 0, 0, null);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Perfil del profesor de tiempo completo", 1, 1, pertinenciaS);
            int noP = 0;
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 23.1", 3, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Nivel de Estudios de los Profesores de Tiempo Completo", 4, 1, pertinenciaS);
            filasPertinenciaS[posIniTi + nuevaPos + 5].setHeightInPoints(20);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "No.", 5, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nivel Máximo de Estudios", 5, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 5, 0, pertinenciaS);
            for (int i = 0; i < 9; i++) {
                if (i == 8) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Subtotal 1", 6 + i, 1, pertinenciaS);
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(i + 1), 6 + i, 0, pertinenciaS);
                }
                noP++;
            }
            for (int i = 0; i < 4; i++) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(i + 9), 6 + noP + i, 0, pertinenciaS);
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MEDIA SUPERIOR SIN CERTIFICADO ", 6, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MEDIA SUPERIOR  CON CERTIFICADO", 7, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "TÉCNICO SUPERIOR UNIVERSITARIO SIN TÍTULO", 8, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "TÉCNICO SUPERIOR UNIVERSITARIO CON TÍTULO", 9, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "LICENCIATURA SIN TÍTULO", 10, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "LICENCIATURA CON TÍTULO", 11, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "ESPECIALIDAD SIN GRADO", 12, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MAESTRÍA SIN GRADO", 13, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "ESPECIALIDAD CON GRADO", 15, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MAESTRÍA CON GRADO", 16, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "DOCTORADO SIN GRADO", 17, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "DOCTORADO CON GRADO", 18, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Subtotal 2", 19, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 20, 1, pertinenciaS);
             obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Distribución porcentual", 21, 1, pertinenciaS);
             obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 22, 1, pertinenciaS);
            sql1 = "SELECT * FROM pertinenciain23 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            int subtotal1 = 0, subtotal2 = 0, total_cuerpo = 0;
             double ptcp2=0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("media_superior"), 6, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("media_superior_con"), 7, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("tsu"), 8, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("tsu_con"), 9, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("lic"), 10, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("lic_titulo"), 11, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("especialidad"), 12, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("maestria"), 13, 1, pertinenciaS);
                subtotal1 = rs.getInt("media_superior") + rs.getInt("media_superior_con") + rs.getInt("tsu") + rs.getInt("tsu_con") + rs.getInt("lic") + rs.getInt("lic_titulo") + rs.getInt("especialidad") + rs.getInt("maestria");
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("especialidad_grado"), 15, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("maestria_grado"), 16, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("doctorado"), 17, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("doctorado_grado"), 18, 1, pertinenciaS);
                subtotal2 = rs.getInt("especialidad_grado") + rs.getInt("maestria_grado") + rs.getInt("doctorado") + rs.getInt("doctorado_grado");

                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cap_competencias"), 27, 0, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cap_tutorias"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("apl_competencias"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("apl_tutorias"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("promep"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 9, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("becados"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 11, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cuerpos_academicos"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cuerpos_formacion"), 32, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cuerpos_consolidacion"), 32, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cuerpos_consolidados"), 32, 1, pertinenciaS);
                total_cuerpo = rs.getInt("cuerpos_consolidados") + rs.getInt("cuerpos_formacion") + rs.getInt("cuerpos_consolidacion");
                 ptcp2= (double) (Double.parseDouble(rs.getString("promep")) / (double) (subtotal1+subtotal2) ) *100   ;
            } else {
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos + 7, ajustarContenido, "No Concluido", 6, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos + 3, ajustarContenido, "No Concluido", 15, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 32, 1, pertinenciaS);
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal1), 14, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal2), 19, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal2 + subtotal1), 20, 1, pertinenciaS);
                            
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,"PTCP1", 21, 1, pertinenciaS);
                double ptcp1= (double) ((double) ((double)subtotal2 / (double)(subtotal1+ subtotal2)) *100 );
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(ptcp1))+" %", 22, 1, pertinenciaS);
                
            //Total cuadro 2
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal2 + subtotal1), 27, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_cuerpo), 32, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 23.2", 23, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Perfil Académico de los Profesores de Tiempo Completo", 24, 1, pertinenciaS);
            filasPertinenciaS[posIniTi + nuevaPos + 25].setHeightInPoints(20);
            filasPertinenciaS[posIniTi + nuevaPos + 26].setHeightInPoints(20);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "TOTAL DE PC", 25, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Capacitados en", 25, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Que aplican", 25, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Competencias profesionales", 26, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Impartición de tutorías", 26, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Competencias profesionales", 26, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Tutorias", 26, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "Cuentan con perfil PRODEP", 25, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 9, 10, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "Becados por otra fuente para estudios de posgrado", 25, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 11, 12, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "Participan en cuerpos académicos", 25, 1, pertinenciaS);
            filasPertinenciaS[posIniTi + nuevaPos + 30].setHeightInPoints(20);
            filasPertinenciaS[posIniTi + nuevaPos + 31].setHeightInPoints(20);
            
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Distribución porcentual", 28, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 28, 1, pertinenciaS);
           
            obj.espaciosEstilosVer(celdasPertinenciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,String.valueOf(obj.getDecimal(ptcp2))+ "%", 28, 1, pertinenciaS);
            
            
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuerpos Académicos", 30, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 31, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Formación", 31, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Consolidación", 31, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Consolidados", 31, 1, pertinenciaS);
            nuevaPos += 32;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 23);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, pertinenciaS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 23" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 23" + e);
        } finally {
            con.Desconectar();
        }
//24
        try {
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 24", 0, 0, null);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Nivel de estudios de los Profesores de Asignatura y Experiencia Laboral en la Materia:", 1, 1, pertinenciaS);
            int noP = 0;
            filasPertinenciaS[posIniTi + nuevaPos + 5].setHeightInPoints(20);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 24.1", 3, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Nivel de Estudios de los Profesores de Asignatura", 4, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "No.", 5, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nivel Máximo de Estudios", 5, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PA", 5, 0, pertinenciaS);
            for (int i = 0; i < 9; i++) {
                if (i == 8) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Subtotal 1", 6 + i, 1, pertinenciaS);
                } else {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(i + 1), 6 + i, 0, pertinenciaS);
                }
                noP++;
            }
            for (int i = 0; i < 4; i++) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(i + 9), 6 + noP + i, 0, pertinenciaS);
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MEDIA SUPERIOR SIN CERTIFICADO ", 6, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MEDIA SUPERIOR  CON CERTIFICADO", 7, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "TÉCNICO SUPERIOR UNIVERSITARIO CON TÍTULO", 8, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "TÉCNICO SUPERIOR UNIVERSITARIO SIN TÍTULO", 9, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "LICENCIATURA SIN TÍTULO", 10, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "LICENCIATURA CON TÍTULO", 11, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "ESPECIALIDAD SIN GRADO", 12, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MAESTRÍA SIN GRADO", 13, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "ESPECIALIDAD CON GRADO", 15, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "MAESTRÍA CON GRADO", 16, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "DOCTORADO SIN GRADO", 17, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "DOCTORADO CON GRADO", 18, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Subtotal 2", 19, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 20, 1, pertinenciaS);
            sql1 = "SELECT * FROM pertinenciain24 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            int subtotal1 = 0, subtotal2 = 0, total_rel = 0, total_norel = 0, tot_tra = 0, tot_ult = 0, tot_mas = 0;
            if (rs.next()) {
                for (int i = 1; i <= 8; i++) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("fila" + i), 5 + i, 1, pertinenciaS);
                    subtotal1 += rs.getInt("fila" + i);
                }
                for (int i = 9; i <= 12; i++) {
                    obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("fila" + i), 6 + i, 1, pertinenciaS);
                    subtotal2 += rs.getInt("fila" + i);
                }
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("trabajo_actual1"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ultimos_3anios1"), 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("mas_3anios1"), 27, 1, pertinenciaS);
                total_rel = rs.getInt("trabajo_actual1") + rs.getInt("ultimos_3anios1") + rs.getInt("mas_3anios1");
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("trabajo_actual2"), 30, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("ultimos_3anios2"), 30, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("mas_3anios2"), 30, 1, pertinenciaS);
                total_norel = rs.getInt("trabajo_actual2") + rs.getInt("ultimos_3anios2") + rs.getInt("mas_3anios2");
                tot_tra = rs.getInt("trabajo_actual1") + rs.getInt("trabajo_actual2");
                tot_ult = rs.getInt("ultimos_3anios1") + rs.getInt("ultimos_3anios2");
                tot_mas = rs.getInt("mas_3anios1") + rs.getInt("mas_3anios2");
            } else {
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos + 7, ajustarContenido, "No Concluido", 6, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos + 3, ajustarContenido, "No Concluido", 15, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 27, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 30, 1, pertinenciaS);
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal1), 14, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal2), 19, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal2 + subtotal1), 20, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_rel), 27, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_norel), 30, 1, pertinenciaS);
            //Total
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_rel + total_norel), 31, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_tra), 31, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_ult), 31, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_mas), 31, 1, pertinenciaS);
//Cuadro2
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 24.2", 23, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Situación de Trabajo en Empresa de los Profesores de Asignatura", 24, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "Profesores de Asignatura", 25, 1, pertinenciaS);
            filasPertinenciaS[posIniTi + nuevaPos + 25].setHeightInPoints(25);
            filasPertinenciaS[posIniTi + nuevaPos + 26].setHeightInPoints(25);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Situación en el trabajo relacionado con su ejercicio profesional", 25, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Con trabajo Actual", 26, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Sin trabajo en los ultimos tres años", 26, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Sin trabajo más de tres años", 26, 1, pertinenciaS);
//vALORES-->
            filasPertinenciaS[posIniTi + nuevaPos + 28].setHeightInPoints(25);
            filasPertinenciaS[posIniTi + nuevaPos + 29].setHeightInPoints(25);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "Profesores de Asignatura", 28, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Situación en el trabajo no relacionado con su ejercicio profesional", 28, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Con trabajo Actual", 29, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Sin trabajo en los ultimos tres años", 29, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Sin trabajo más de tres años", 29, 1, pertinenciaS);
            
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PAEL1", 32, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 32, 1, pertinenciaS);
            double pa1 = total_rel + total_norel;
            double pa2 = total_rel;
            double pael = (( pa2 / pa1 ) * 100);
            DecimalFormat formato = new DecimalFormat("##.#");
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(formato.format(pael)), 33, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 33, 1, pertinenciaS);
            nuevaPos += 34;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 24);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, pertinenciaS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 24" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 24" + e);
        } finally {
            con.Desconectar();
        }
//25
        try {
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 25", 0, 0, null);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Capacitación del Personal de la Universidad Tecnológica:", 1, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 25.1", 3, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Capacitación del Personal", 4, 1, pertinenciaS);
            filasPertinenciaS[posIniTi + nuevaPos + 5].setHeightInPoints(30);
            filasPertinenciaS[posIniTi + nuevaPos + 7].setHeightInPoints(20);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 5, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Mandos medios y superiores", 5, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Personal administrativo y secretarial", 5, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Profesores de Tiempo Completo", 5, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Profesores de Asignatura", 5, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "CON CAPACITACION", 7, 1, pertinenciaS);
            sql1 = "SELECT superiores,administrativo,profesores_tc,profesores_asignatura,superiores_con,administrativo_con,profesores_tc_con,profesores_asignatura_con FROM pertinenciain25 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            int subtotal1 = 0, subtotal2 = 0;
            double cp1 = 0, cp2 = 0, cp3 = 0, cp4 = 0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("superiores"), 6, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("administrativo"), 6, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("profesores_tc"), 6, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("profesores_asignatura"), 6, 1, pertinenciaS);
                subtotal1 += rs.getInt("superiores") + rs.getInt("administrativo") + rs.getInt("profesores_tc") + rs.getInt("profesores_asignatura");
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("superiores_con"), 8, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("administrativo_con"), 8, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("profesores_tc_con"), 8, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("profesores_asignatura_con"), 8, 1, pertinenciaS);
                subtotal2 += rs.getInt("superiores_con") + rs.getInt("administrativo_con") + rs.getInt("profesores_tc_con") + rs.getInt("profesores_asignatura_con");
                if (rs.getInt("superiores") != 0) {
                    cp1 = obj.getDecimal(rs.getDouble("superiores_con") / rs.getDouble("superiores") * 100);
                }
                if (rs.getInt("administrativo") != 0) {
                    cp2 = obj.getDecimal(rs.getDouble("administrativo_con") / rs.getDouble("administrativo") * 100);
                }
                if (rs.getInt("profesores_tc") != 0) {
                    cp3 = obj.getDecimal(rs.getDouble("profesores_tc_con") / rs.getDouble("profesores_tc") * 100);
                }
                if (rs.getInt("profesores_asignatura") != 0) {
                    cp4 = obj.getDecimal(rs.getDouble("profesores_asignatura_con") / rs.getDouble("profesores_asignatura") * 100);
                }
            } else {
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6, 1, pertinenciaS);
                obj.espaciosEstilosVer(celdasPertinenciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 8, 1, pertinenciaS);
            }
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal1), 6, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal2), 8, 0, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "CP1=", 9, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "CP2=", 9, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "CP3=", 9, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "CP4=", 9, 1, pertinenciaS);
            //obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "", 9, 0, pertinenciaS);
            
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(cp1), 10, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(cp2), 10, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(cp3), 10, 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(cp4), 10, 1, pertinenciaS);
            
            obj.espaciosEstilosVer(celdasPertinenciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "%", 9, 1, pertinenciaS);
            nuevaPos += 10;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 25);
            obj.espaciosEstilosVer(celdasPertinenciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, pertinenciaS);
            obj.espaciosEstilosVer(celdasPertinenciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, pertinenciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 25" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 25" + e);
        } finally {
            con.Desconectar();
        }
        return obj;
    }
}
