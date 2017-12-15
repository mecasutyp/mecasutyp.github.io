//Actualización 2016: Salvador Zamora Arias
package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * @author Danny
 */
public class Excel_Reporte_Universidad_Equidad {

    public Excel_Reporte_Universidad crearEquidad(String universidad, String periodo, Excel_Reporte_Universidad obj) throws FileNotFoundException, IOException {
        int columnas = 11;
        int celdas = 200;
        String sql1 = "";
        String sqlotras = "";
        ResultSet rs = null;
        ResultSet rsotras = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ConexionMySQL con = new ConexionMySQL();
        int nuevaPos = 0, noC = 1, noProg = 1, cProg = 1;
        int posIniTi = 0;
        //Creo dos arreglos que van a contener las filas y las celdas          
        //Categoría PERTINENCIA
        HSSFSheet equidadS = obj.regresarLibro().createSheet("EQUIDAD");
        HSSFRow[] filasEquidadS = new HSSFRow[celdas];
        HSSFCell[][] celdasEquidadS = new HSSFCell[columnas][celdas];
        for (int l = 0; l < columnas; l++) {
            for (int j = 0; j < celdas - 1; j++) {
                filasEquidadS[j] = equidadS.createRow(j);
                celdasEquidadS[l][j] = filasEquidadS[j].createCell(l);
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
        equidadS.setColumnWidth(0, 37 * 78);
        equidadS.setColumnWidth(1, 37 * 116);
        equidadS.setColumnWidth(2, 37 * 106);
        equidadS.setColumnWidth(3, 37 * 98);
        equidadS.setColumnWidth(4, 37 * 105);
        equidadS.setColumnWidth(5, 37 * 94);
        equidadS.setColumnWidth(6, 37 * 94);
        equidadS.setColumnWidth(7, 37 * 80);
        equidadS.setColumnWidth(8, 37 * 81);
        equidadS.setColumnWidth(9, 37 * 80);
        equidadS.setColumnWidth(10, 37 * 80);
        obj.espaciosEstilosVer(celdasEquidadS, 0, 10, posIniTi, nuevaPos, titulo_cat, "V. EQUIDAD", 0, 1, equidadS);
        nuevaPos += 1;
//33
        try {
            obj.espaciosEstilosVer(celdasEquidadS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 33", 0, 0, null);
            obj.espaciosEstilosVer(celdasEquidadS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Cobertura", 1, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 33.1", 3, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "1", 4, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "2", 4, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 4, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos nuevo ingreso en la universidad", 5, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Egresados de bachillerato en el Estado", 5, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "COB=1/2", 5, 0, equidadS);
//            
            int nueIng = 0, egreBa = 0;
            double tot = 0;
            sql1 = "select nuevo_ingreso from datos_universidad WHERE id_universidad=".concat(String.valueOf(universidad)).concat(" AND id_periodo=").concat(String.valueOf(periodo));
            rs = con.Consultar(sql1);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("nuevo_ingreso"), 6, 1, equidadS);
                nueIng = rs.getInt("nuevo_ingreso");
            } else {
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6, 1, equidadS);
            }
            sql1 = "select egresados from egresados_estado where id_estado = (select id_estado from domicilios_universidad where id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo).concat(") AND id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("egresados"), 6, 1, equidadS);
                egreBa = rs.getInt("egresados");
            } else {
                obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6, 1, equidadS);
            }
            if (egreBa != 0) {
                tot = obj.getDecimal(nueIng / (double) egreBa * 100);
            }
            obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot), 6, 1, equidadS);
            nuevaPos += 7;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 33);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, equidadS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 33" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 33" + e);
        } finally {
            con.Desconectar();
        }
//34
        try {
            int conNiv = 0, posTemp = 0, a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, k = 0, l = 0;
            obj.espaciosEstilosVer(celdasEquidadS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 34", 0, 0, null);
            obj.espaciosEstilosVer(celdasEquidadS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Alumno Atendido", 1, 1, equidadS);
            ArrayList totNivel = new ArrayList();//Guardo los totales horizontales de los niveles
            sql1 = "Select distinct(pe.id_nivel), n.nombre, n.descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(") order by pe.id_nivel");
            rs = con.Consultar(sql1);
            filasEquidadS[posIniTi + nuevaPos + 5].setHeightInPoints(68);
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasEquidadS, 1 + conNiv, 1 + conNiv, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula de " + rs.getString("n.descripcion") + " al inicio del ciclo escolar", 5, 1, equidadS);
                posTemp = posIniTi + nuevaPos;
                conNiv++;
            }
            obj.espaciosEstilosVer(celdasEquidadS, 1 + conNiv, 1 + conNiv, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula total atendida al inicio del ciclo escolar", 5, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1 + conNiv, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 34.1", 3, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1 + conNiv, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Distribución de la Matrícula por Nivel Educativo", 4, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "34.2- Alumno atendido por nivel educativo, carrera, ingreso y reingreso según sexo", 10, 1, equidadS);
            rs.beforeFirst();
            int numC = 1;
            int totalNivelC1 = 0;
            while (rs.next()) {
                int numP = 0;
                obj.espaciosEstilosVer(celdasEquidadS, 1, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 34.1." + rs.getString("id_nivel"), 12, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 1, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Matrícula del Nivel " + rs.getString("n.descripcion") + ", Ingreso, Reingreso Según Sexo", 13, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 2, titulo_Cua_arial9Ne, "PROGRMA EDUCATIVO", 14, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "INGRESO", 14, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 5, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "REINGRESO", 14, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 8, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 14, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "HOMBRES", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "MUJERES", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "HOMBRES", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "MUJERES", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "HOMBRES", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "MUJERES", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 2, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "ABSOLUTOS", 16, 1, equidadS);
                sql1 = "select id_pe, nombre_programa from programa_educativo where id_nivel=".concat(rs.getString(1)).concat(" and id_pe in(select id_pe from pe_universidad where id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo).concat(")")));
                rs2 = con.Consultar(sql1);
                int total_ing = 0, total_rei = 0, total_hi = 0, total_mi = 0, total_hr = 0, total_mr = 0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("nombre_programa"), 17 + numP, 1, equidadS);
                    sql1 = "select ingreso_hombres,ingreso_mujeres,reingreso_hombres,reingreso_mujeres from equidadin34 where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo).concat(" and id_pe=").concat(rs2.getString(1));
                    rs3 = con.Consultar(sql1);
                    int tot_ing = 0, tot_rei = 0, tot_h = 0, tot_m = 0;
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("ingreso_hombres"), 17 + numP, 1, equidadS);
                        obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("ingreso_mujeres"), 17 + numP, 1, equidadS);
                        tot_ing = rs3.getInt("ingreso_hombres") + rs3.getInt("ingreso_mujeres");
                        total_ing += tot_ing;
                        total_hi += rs3.getInt("ingreso_hombres");
                        total_mi += rs3.getInt("ingreso_mujeres");
                        obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("reingreso_hombres"), 17 + numP, 1, equidadS);
                        obj.espaciosEstilosVer(celdasEquidadS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("reingreso_mujeres"), 17 + numP, 1, equidadS);
                        tot_rei = rs3.getInt("reingreso_hombres") + rs3.getInt("reingreso_mujeres");
                        total_rei += tot_rei;
                        total_hr += rs3.getInt("reingreso_hombres");
                        total_mr += rs3.getInt("reingreso_mujeres");
                        tot_h = rs3.getInt("ingreso_hombres") + rs3.getInt("reingreso_hombres");
                        tot_m = rs3.getInt("ingreso_mujeres") + rs3.getInt("reingreso_mujeres");
                    } else {
                        obj.espaciosEstilosVer(celdasEquidadS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 17 + numP, 1, equidadS);
                        obj.espaciosEstilosVer(celdasEquidadS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 17 + numP, 1, equidadS);
                    }
                    obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_ing), 17 + numP, 1, equidadS);
                    obj.espaciosEstilosVer(celdasEquidadS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_rei), 17 + numP, 1, equidadS);
                    obj.espaciosEstilosVer(celdasEquidadS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_h), 17 + numP, 1, equidadS);
                    obj.espaciosEstilosVer(celdasEquidadS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_m), 17 + numP, 1, equidadS);
                    obj.espaciosEstilosVer(celdasEquidadS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(tot_m + tot_h), 17 + numP, 1, equidadS);
                    numP++;
                }
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 17 + numP, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_hi), 17 + numP, 1, equidadS);
                totNivel.add(total_hi);
                a += total_hi;
                obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_mi), 17 + numP, 1, equidadS);
                totNivel.add(total_mi);
                b += total_mi;
                obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_ing), 17 + numP, 1, equidadS);
                totNivel.add(total_ing);
                c += total_ing;
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_hr), 17 + numP, 1, equidadS);
                totNivel.add(total_hr);
                d += total_hr;
                obj.espaciosEstilosVer(celdasEquidadS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_mr), 17 + numP, 1, equidadS);
                totNivel.add(total_mr);
                e += total_mr;
                obj.espaciosEstilosVer(celdasEquidadS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_rei), 17 + numP, 1, equidadS);
                totNivel.add(total_rei);
                f += total_rei;
                obj.espaciosEstilosVer(celdasEquidadS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_hi + total_hr), 17 + numP, 1, equidadS);
                totNivel.add(total_hi + total_hr);
                g += (total_hi + total_hr);
                obj.espaciosEstilosVer(celdasEquidadS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_mi + total_mr), 17 + numP, 1, equidadS);
                totNivel.add(total_mi + total_mr);
                k += total_mi + total_mr;
                obj.espaciosEstilosVer(celdasEquidadS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_mi + total_mr + total_hi + total_hr), 17 + numP, 1, equidadS);
                totNivel.add(total_mi + total_mr + total_hi + total_hr);
                obj.espaciosEstilosVer(celdasEquidadS, numC, numC, posTemp, posTemp, titulo_Cua_arial9Ne, String.valueOf(total_mi + total_mr + total_hi + total_hr), 6, 1, equidadS);
                totalNivelC1 += total_mi + total_mr + total_hi + total_hr;
                l += total_mi + total_mr + total_hi + total_hr;
                numC++;
                nuevaPos += 8 + numP;
            }
            obj.espaciosEstilosVer(celdasEquidadS, numC, numC, posTemp, posTemp, titulo_Cua_arial9Ne, String.valueOf(totalNivelC1), 6, 1, equidadS);
//Cuadro 3
            obj.espaciosEstilosVer(celdasEquidadS, 1, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 34.2", 12, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Matrícula por Nivel, Ingreso, Reingreso Según Sexo", 13, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 2, titulo_Cua_arial9Ne, "NIVEL", 14, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "INGRESO", 14, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "REINGRESO", 14, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 8, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 14, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "HOMBRES", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "MUJERES", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "HOMBRES", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "MUJERES", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "HOMBRES", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "MUJERES", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "ABSOLUTOS", 16, 1, equidadS);
            rs.beforeFirst();
            int segmen = totNivel.size() / (numC - 1);
            numC = 1;
            int h = 0;
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, rs.getString("n.descripcion"), 16 + numC, 1, equidadS);
                int j = 2;
                for (int i = h; i < segmen * numC; i++) {
                    obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, totNivel.get(i).toString(), 16 + numC, 1, equidadS);
                    j++;
                }
                h += segmen;
                numC++;
            }
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(a), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(b), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(c), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(d), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(e), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(f), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(g), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(k), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(l), 16 + numC, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RELATIVOS", 17 + numC, 1, equidadS);
            rs.beforeFirst();
            nuevaPos += numC;
            numC = 1;
            h = 0;
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, rs.getString("n.descripcion"), 17 + numC, 1, equidadS);
                int j = 2;
                for (int i = h; i < segmen * numC; i++) {
                    if (i == h) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(0 + h).toString())
                                / Double.parseDouble(totNivel.get(2 + h).toString()) * 100)), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 1) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(1 + h).toString())
                                / Double.parseDouble(totNivel.get(2 + h).toString()) * 100)), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 2) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(1 + h).toString())
                                / Double.parseDouble(totNivel.get(2 + h).toString()) * 100 + (Integer.parseInt(totNivel.get(0 + h).toString())
                                / Double.parseDouble(totNivel.get(2 + h).toString()) * 100))), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 3) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(3 + h).toString())
                                / Double.parseDouble(totNivel.get(5 + h).toString()) * 100)), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 4) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(4 + h).toString())
                                / Double.parseDouble(totNivel.get(5 + h).toString()) * 100)), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 5) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(4 + h).toString())
                                / Double.parseDouble(totNivel.get(5 + h).toString()) * 100 + (Integer.parseInt(totNivel.get(3 + h).toString())
                                / Double.parseDouble(totNivel.get(5 + h).toString()) * 100))), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 6) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(6 + h).toString())
                                / Double.parseDouble(totNivel.get(8 + h).toString()) * 100)), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 7) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(7 + h).toString())
                                / Double.parseDouble(totNivel.get(8 + h).toString()) * 100)), 17 + numC, 1, equidadS);
                    }
                    if (i == h + 8) {
                        obj.espaciosEstilosVer(celdasEquidadS, j, j, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne,
                                String.valueOf(obj.getDecimal(Integer.parseInt(totNivel.get(7 + h).toString())
                                / Double.parseDouble(totNivel.get(8 + h).toString()) * 100 + (Integer.parseInt(totNivel.get(6 + h).toString())
                                / Double.parseDouble(totNivel.get(8 + h).toString()) * 100))), 17 + numC, 1, equidadS);
                    }
                    j++;
                }
                h += segmen;
                numC++;
            }
            nuevaPos += numC + 18;
            obj.espaciosEstilosVer(celdasEquidadS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 34.3" , 1, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Índice de Mascunilidad", 2, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "1", 3, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "2", 3, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "IM= 1/2*100", 3, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de hombres", 4, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de mujeres", 4, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(g), 5, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(k), 5, 1, equidadS);
            if (k != 0) {
                obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal((g / (double) k) * 100)), 5, 1, equidadS);
            } else {
                obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 5, 1, equidadS);

            }
            nuevaPos += 2 + numC;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 34);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, equidadS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 34" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 34" + e);
        } finally {
            con.Desconectar();
        }

        //35
        try {
            double pd1 = 0, pd2 = 0, pd3 = 0;
            int sumaEventosRea = 0, totalPersonasAten = 0;
            obj.espaciosEstilosVer(celdasEquidadS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 35", 0, 0, null);
            obj.espaciosEstilosVer(celdasEquidadS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Promoción Deportiva, Cultural y Comunitaria:", 1, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 35.1", 3, 1, equidadS);
            for (int i = 1; i < 7; i++) {
                obj.espaciosEstilosVer(celdasEquidadS, i, i, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(i), 4, 0, equidadS);
            }
            filasEquidadS[posIniTi + nuevaPos + 5].setHeightInPoints(95);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Eventos deportivos realizados por la universidad en el ciclo escolar", 5, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Eventos deportivos programados por la universidad en el ciclo escolar", 5, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Eventos culturales realizados por la universidad en el ciclo escolar", 5, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Eventos culturales programados por la universidad en el ciclo escolar", 5, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Eventos comunitarios realizados por la universidad en el ciclo escolar", 5, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Eventos comunitarios programados por la universidad en el ciclo escolar", 5, 0, equidadS);
            sql1 = "SELECT * FROM equidadin35 ".concat("WHERE id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo));
            rs = con.Consultar(sql1);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("dep_realizados"), 6, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("dep_programados"), 6, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cul_realizados"), 6, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cul_programados"), 6, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("com_realizados"), 6, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("com_programados"), 6, 1, equidadS);
                if (rs.getDouble("dep_programados") > 0) {
                    pd1 = obj.getDecimal((rs.getDouble("dep_realizados") / rs.getDouble("dep_programados")) * 100);
                }
                if (rs.getDouble("cul_programados") > 0) {
                    pd2 = obj.getDecimal((rs.getDouble("cul_realizados") / rs.getDouble("cul_programados")) * 100);
                }
                if (rs.getDouble("com_programados") > 0) {
                    pd3 = obj.getDecimal((rs.getDouble("com_realizados") / rs.getDouble("com_programados")) * 100);
                }
                obj.espaciosEstilosVer(celdasEquidadS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("dep_realizados"), 14, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("dep_personas"), 14, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 6, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("dep_aspectos"), 14, 1, equidadS);
                sumaEventosRea = rs.getInt("dep_realizados") + rs.getInt("cul_realizados") + rs.getInt("com_realizados");
                obj.espaciosEstilosVer(celdasEquidadS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cul_realizados"), 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cul_personas"), 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 6, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("cul_aspectos"), 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("com_realizados"), 16, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("com_personas"), 16, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 6, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("com_aspectos"), 16, 1, equidadS);
                totalPersonasAten = rs.getInt("dep_personas") + rs.getInt("cul_personas") + rs.getInt("com_personas");
            } else {
                obj.espaciosEstilosVer(celdasEquidadS, 1, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 6, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 3, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 14, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 3, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 15, 1, equidadS);
                obj.espaciosEstilosVer(celdasEquidadS, 3, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 16, 1, equidadS);
            }
            obj.espaciosEstilosVer(celdasEquidadS, 1, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 35.2", 11, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Población Beneficiada por Evento", 12, 1, equidadS);
            filasEquidadS[posIniTi + nuevaPos + 13].setHeightInPoints(66);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TIPO", 13, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de eventos realizados", 13, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de personas atendidas", 13, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 6, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Aspectos más importantes a resaltar de los eventos", 13, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Deportivos", 14, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Culturales", 15, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comunitarios", 16, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 17, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PD1 = ", 7, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PC2 = ", 7, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PC3 = ", 7, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pd1), 8, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pd2), 8, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pd3), 8, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(sumaEventosRea), 17, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalPersonasAten), 17, 0, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 6, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 17, 1, equidadS);
            nuevaPos += 17;
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 35);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, equidadS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 35" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 35" + e);
        } finally {
            con.Desconectar();
        }
        //36
        try {
            int contAPoyos = 1;
            int sumaBecas = 0, matTotal = 0;
            obj.espaciosEstilosVer(celdasEquidadS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 36", 0, 0, null);
            obj.espaciosEstilosVer(celdasEquidadS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Becas", 1, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 36.1", 3, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Alumnos de la universidad con Beca", 4, 1, equidadS);
            filasEquidadS[posIniTi + nuevaPos + 5].setHeightInPoints(95);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "Tipo de Becas o Apoyos Economicos al Estudiante", 5, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Número de Becas otorgadas en \"el Ciclo escolar\"", 5, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula Atendida en \"el Ciclo Escolar\"", 5, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Porcentaje de alumnos beneficiados", 5, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "A", 6, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "B", 6, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "A/B=x100", 6, 1, equidadS);
            sql1 = "select matricula_total from datos_universidad where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);

            rs2 = con.Consultar(sql1);
            if (rs2.next()) {
                matTotal = rs2.getInt("matricula_total");
            }
            sql1 = "select id_apoyo, descripcion from apoyos_estudiante where id_apoyo!=12 and activo =1 order by descripcion";
            rs = con.Consultar(sql1);
            sqlotras = "select id_apoyo, descripcion from apoyos_estudiante where id_apoyo=12 and activo =1";
            rsotras = con.Consultar(sqlotras);
            String id_apoy="";
            String descrip="";
            int rows=0;
            while(rs.next()){
                rows++;
            }
            rs.beforeFirst();
            while (rs.next()) {
                if(contAPoyos!=rows){
                    id_apoy=rs.getString("id_apoyo");
                    descrip=rs.getString("descripcion");
                }else{
                    rsotras.next();
                    id_apoy=rsotras.getString("id_apoyo");
                    descrip=rsotras.getString("descripcion");
                }
                obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, descrip, 6 + contAPoyos, 1, equidadS);
                sql1 = "select apoyos_otorgados from equidadin36 where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo).concat(" and id_apoyo=").concat(id_apoy);
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasEquidadS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("apoyos_otorgados"), 6 + contAPoyos, 1, equidadS);
                    sumaBecas += rs2.getInt("apoyos_otorgados");
                    if (matTotal > 0) {
                        obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(rs2.getInt("apoyos_otorgados") / (double) matTotal * 100)), 6 + contAPoyos, 1, equidadS);
                    } else {
                        obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf("0"), 6 + contAPoyos, 1, equidadS);
                    }
                } else {
                    obj.espaciosEstilosVer(celdasEquidadS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 6 + contAPoyos, 1, equidadS);
                    obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf("0"), 6 + contAPoyos, 1, equidadS);
                }
                contAPoyos++;
            }
            obj.espaciosEstilosVer(celdasEquidadS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Totales", 6 + contAPoyos, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(sumaBecas), 6 + contAPoyos, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(matTotal), 6 + contAPoyos, 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos + (contAPoyos - 2), titulo_Cua_arial9Ne, String.valueOf(matTotal), 7, 1, equidadS);
            if (matTotal > 0) {
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumaBecas / (double) matTotal * 100)), 6 + contAPoyos, 1, equidadS);
            } else {
                obj.espaciosEstilosVer(celdasEquidadS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf("0"), 6 + contAPoyos, 1, equidadS);
            }
            nuevaPos += 7 + contAPoyos;
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 36);
            obj.espaciosEstilosVer(celdasEquidadS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, equidadS);
            obj.espaciosEstilosVer(celdasEquidadS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, equidadS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 36" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 36" + e);
        } finally {
            con.Desconectar();
        }
        return obj;
    }
}