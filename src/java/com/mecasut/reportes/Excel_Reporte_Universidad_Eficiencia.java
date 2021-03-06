//Actualización 2016: Salvador Zamora Arias

package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Excel_Reporte_Universidad_Eficiencia {

    Excel_Reporte_Universidad_Eficiencia() {
    }

    public Excel_Reporte_Universidad crearEficiencia(String universidad, String periodo, Excel_Reporte_Universidad obj) throws FileNotFoundException, IOException {
        //Categoría Eficiencia

        int celdas = 1000;
        String consulta = "";
        String sql2 = "";
        String sql1 = "";
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ConexionMySQL con = new ConexionMySQL();
        String top = "";
        String datos_uni = "";
        String datos_uni_sub = "";
        String titulo_cat = "titulo_cat";
        String titulo_indicador = "titulo_indicador";
        String titulo_indicador_subr = "";
        String linea_doble = "";
        //Estilos de Indicador
        String arial10_bordoble = "";
        String arial8_bordoble_text = "";
        String arial10_leyenda_res = "";
        String arial10_leyenda2_res = "";
        String resultado_base = "";
        String resultado_hor = "";
        String no_cuadro = "";
        String titulo_Cua_arial10MayNe = "titulo_Cua_arial10MayNe";
        String titulo_Cua_arial9Ne = "titulo_Cua_arial9Ne";
        String res1decimal = "";
        String res1decimallidobleAria10 = "";
        String resVerdecimallidobleArial9 = "";
        String resVerdecimallidobleArial10 = "";
        String ajustarContenido = "ajustarContenido";
        String resmiles = "";

        int nuevaPos = 0, noC = 1, noProg = 1, cProg = 1;
        //int posIni = 26;
        int posIniTi = 0;
        HSSFSheet eficienciaS = obj.regresarLibro().createSheet("EFICIENCIA");
        int columnas = 9;
        HSSFRow[] filasEficienciaS = new HSSFRow[200];
        HSSFCell[][] celdasEficienciaS = new HSSFCell[columnas][200];
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j < 199; j++) {
                filasEficienciaS[j] = eficienciaS.createRow(j);
                celdasEficienciaS[i][j] = filasEficienciaS[j].createCell(i);
            }
        }
        posIniTi = 0;
        nuevaPos = 0;
        eficienciaS.setColumnWidth(0, 54 * 37);
        eficienciaS.setColumnWidth(1, 37 * 104);
        eficienciaS.setColumnWidth(2, 37 * 147);
        eficienciaS.setColumnWidth(3, 37 * 106);
        eficienciaS.setColumnWidth(4, 37 * 102);
        eficienciaS.setColumnWidth(5, 37 * 85);
        eficienciaS.setColumnWidth(6, 37 * 89);
        eficienciaS.setColumnWidth(7, 37 * 98);
        eficienciaS.setColumnWidth(8, 37 * 80);
        obj.espaciosEstilosVer(celdasEficienciaS, 0, 8, posIniTi, nuevaPos, "titulo_cat", "II. EFICIENCIA", 0, 1, eficienciaS);

//Indicador 12 
        try {
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 12", 2, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Costo por alumno", 3, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 12.1", 4, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 6, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 6, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "CPA=1/2", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto total autorizado Federal y Estatal", 7, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Mat", 7, 0, eficienciaS);
            sql1 = "select autorizado from eficaciain11 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'");
            rs = con.Consultar(sql1);
            double presupuesto = 0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resmiles", rs.getString(1), 8, 0, eficienciaS);
                presupuesto = rs.getDouble(1);
            } else {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Complete Indicador 11", 8, 0, eficienciaS);
            }
            filasEficienciaS[8].setHeightInPoints(27);
            sql1 = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(1), 8, 0, eficienciaS);
                if (rs.getDouble(1) > 0 || rs.getDouble(1) > 0.0) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resmiles", String.valueOf(obj.getDecimal(presupuesto / rs.getDouble(1))), 8, 0, eficienciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resmiles", "0", 8, 0, eficienciaS);
                }
            } else {
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 8, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 8, 0, eficienciaS);
            }
            nuevaPos += 9;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 12" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 12" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 13
        try {
            boolean cuadro3 = false;
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 13", 2, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Utilización de espacios", 3, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 13.1", 4, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Capacidad Instalada en la Universidad", 5, 1, eficienciaS);
            //Encabezados
            filasEficienciaS[posIniTi + nuevaPos + 6].setHeightInPoints(60);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Descripción de los Edificios, Laboratorios y Talleres", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Capacidad de los edificios", 6, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "No. de unidades, laboratorios y talleres", 6, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total de espacios docentes", 6, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Capacidad del espacio académico ocupado por otras áreas de trabajo", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Observaciones", 6, 1, eficienciaS);
            //sql1 = "Select id_edificio,descripcion,capacidad from edificios where id_edificio=(select id_edificio from edificios_universidad where id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo).concat(" and edificios_universidad.id_edificio=edificios.id_edificio order by id_edificio)"));
            sql1 = "Select id_edificio,descripcion,capacidad from edificios where id_edificio IN(select id_edificio from edificios_universidad where id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo).concat(" and edificios_universidad.id_edificio=edificios.id_edificio order by id_edificio)"));
            rs = con.Consultar(sql1);
            int num_edificios = 0, no_lab = 0, tot_espacios = 0, cap_esp = 0;
            while (rs.next()) {
                cuadro3 = true;
                filasEficienciaS[posIniTi + nuevaPos + 7 + num_edificios].setHeightInPoints(35);
                obj.espaciosEstilosVer(celdasEficienciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs.getString("descripcion"), 7 + num_edificios, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs.getString("capacidad"), 7 + num_edificios, 0, eficienciaS);
                sql1 = "SELECT no_laboratorios,espacio_academico,observaciones FROM eficienciain13 WHERE id_edifi='".concat(rs.getString(1)).concat("' and id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo));
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    no_lab += rs2.getInt("no_laboratorios");
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs2.getString("no_laboratorios"), 7 + num_edificios, 0, eficienciaS);
                    obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs2.getString("espacio_academico"), 7 + num_edificios, 1, eficienciaS);
                    obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs2.getString("observaciones"), 7 + num_edificios, 1, eficienciaS);

                    obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(rs.getInt("capacidad") * rs2.getInt("no_laboratorios")), 7 + num_edificios, 1, eficienciaS);
                    tot_espacios += rs.getInt("capacidad") * rs2.getInt("no_laboratorios");
                    cap_esp += rs2.getInt("espacio_academico");
                } else {

                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No concluido", 7 + num_edificios, 0, eficienciaS);
                    obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No concluido", 7 + num_edificios, 1, eficienciaS);
                    obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No concluido", 7 + num_edificios, 1, eficienciaS);
                    obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "No concluido", 7 + num_edificios, 1, eficienciaS);
                }
                num_edificios++;
            }
            sql1 = "SELECT count(id_universidad) as cont FROM edificios_universidad WHERE id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo));
            ResultSet rs0 = con.Consultar(sql1);
            if (rs0.next()) {
                if (rs0.getInt("cont") == 0) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 0, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "La Universidad no cuenta con Edificios", 7 + num_edificios, 1, eficienciaS);
                    num_edificios++;
                }
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total", 7 + num_edificios, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(no_lab), 7 + num_edificios, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(tot_espacios), 7 + num_edificios, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(cap_esp), 7 + num_edificios, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "", 7 + num_edificios, 1, eficienciaS);
              //CALCULA TUE
            String sqlmat = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo);
            ResultSet rsmat = con.Consultar(sqlmat);
            int mat=0;
            if (rsmat.next()) {
                mat = rsmat.getInt(1);
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Matricula inicial total", 8 + num_edificios, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(mat), 8 + num_edificios, 0, eficienciaS);
            double tue= ((double) mat / (double) tot_espacios )*100;
              DecimalFormat df = new DecimalFormat("#.##");//creo objeto df
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Porcentaje total de aprovechamiento", 9 + num_edificios, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(df.format(tue)), 9 + num_edificios, 0, eficienciaS);
            
          nuevaPos += 10 + num_edificios;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 13);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, eficienciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 13" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 13" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 14
        try {
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 14", 2, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Utilización del Equipo de Cómputo", 3, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 14.1", 4, 1, eficienciaS);
            //obj.espaciosEstilosVer(celdasEficienciaS, 1, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "En el siguiente cuadro se deberá desglosar la distribución del equipo de cómputo de la institución.", 5, 1, eficienciaS);
            //Encabezados
            filasEficienciaS[posIniTi + nuevaPos + 6].setHeightInPoints(28);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Distribución del Equipo de Cómputo", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total de PC’S", 7, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Docente de Tiempo completo", 7, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Docente de Asignatura", 7, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Alumnos", 7, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Personal Administrativo", 7, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Mandos Medios y Superiores", 7, 1, eficienciaS);
            //Resultados Base
            sql1 = "SELECT * FROM eficienciain14 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            int suma1 = 0, suma2 = 0;
            double col1_1 = 0, col2_1 = 0, col3_1 = 0, col4_1 = 0, col5_1 = 0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("docente_ptc"), 8, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("docente_asignatura"), 8, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("alumnos"), 8, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("personal_admin"), 8, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("mandos"), 8, 1, eficienciaS);
                suma1 = rs.getInt("docente_ptc") + rs.getInt("docente_asignatura") + rs.getInt("alumnos") + rs.getInt("personal_admin") + rs.getInt("mandos");
                //Totales
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((rs.getDouble("docente_ptc") / (double) suma1) * 100)), 9, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("docente_asignatura") / (double) suma1 * 100)), 9, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("alumnos") / (double) suma1 * 100)), 9, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("personal_admin") / (double) suma1 * 100)), 9, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("mandos") / (double) suma1 * 100)), 9, 1, eficienciaS);
                col1_1 = obj.getDecimal((rs.getDouble("docente_ptc") / (double) suma1) * 100);
                col2_1 = obj.getDecimal(rs.getDouble("docente_asignatura") / (double) suma1 * 100);
                col3_1 = obj.getDecimal(rs.getDouble("alumnos") / (double) suma1 * 100);
                col4_1 = obj.getDecimal(rs.getDouble("personal_admin") / (double) suma1 * 100);
                col5_1 = obj.getDecimal(rs.getDouble("mandos") / (double) suma1 * 100);
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getEntero(col1_1 + col2_1 + col3_1 + col4_1 + col5_1)), 9, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("docentes_ptc_int"), 12, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("docente_asignatura_int"), 12, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("alumnos_int"), 12, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("personal_admin_int"), 12, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("mandos_int"), 12, 1, eficienciaS);
                suma2 = rs.getInt("docentes_ptc_int") + rs.getInt("docente_asignatura_int") + rs.getInt("alumnos_int") + rs.getInt("personal_admin_int") + rs.getInt("mandos_int");
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("docentes_ptc_int") / (double) suma2 * 100)), 13, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("docente_asignatura_int") / (double) suma2 * 100)), 13, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("alumnos_int") / (double) suma2 * 100)), 13, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("personal_admin_int") / (double) suma2 * 100)), 13, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs.getDouble("mandos_int") / (double) suma2 * 100)), 13, 1, eficienciaS);
                col1_1 = obj.getDecimal(rs.getDouble("docentes_ptc_int") / (double) suma2 * 100);
                col2_1 = obj.getDecimal(rs.getDouble("docente_asignatura_int") / (double) suma2 * 100);
                col3_1 = obj.getDecimal(rs.getDouble("alumnos_int") / (double) suma2 * 100);
                col4_1 = obj.getDecimal(rs.getDouble("personal_admin_int") / (double) suma2 * 100);
                col5_1 = obj.getDecimal(rs.getDouble("mandos_int") / (double) suma2 * 100);
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getEntero(col1_1 + col2_1 + col3_1 + col4_1 + col5_1)), 13, 0, eficienciaS);
            } else {
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 8, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 12, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 9, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 9, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 9, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 9, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 9, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 9, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficienciaS);
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(suma1), 8, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(suma2), 12, 0, eficienciaS);
            filasEficienciaS[posIniTi + nuevaPos + 10].setHeightInPoints(28);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Distribución del Equipo de Cómputo con Internet", 10, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total de PC’S", 11, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Docente de Tiempo completo", 11, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Docente de Asignatura", 11, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Alumnos", 11, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Personal Administrativo", 11, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Mandos Medios y Superiores", 11, 1, eficienciaS);
            nuevaPos += 14;
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 14);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, eficienciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 14" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 14" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 15
        try {
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 15", 2, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Procesos Certificados", 3, 0, null);
            //Encabezados
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Nombre del Certificado", 5, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Estado", 5, 1, eficienciaS);
            //Resultados Base
            sql1 = "SELECT id_certificacion,nombre_certificacion FROM certificaciones WHERE activo=1";
            rs = con.Consultar(sql1);
            int num_cert = 0;
            boolean si = false;
            while (rs.next()) {
                if (rs.getInt(1) != 1) {
                    sql1 = "SELECT sin_certificado,en_proceso,con_certificado,en_proceso_recertificacion,con_recertificado FROM eficienciain15 WHERE id_universidad='".concat(universidad).concat("' and id_periodo='").concat(periodo).concat("' and id_certificacion=").concat(rs.getString(1));
                    rs2 = con.Consultar(sql1);
                    while (rs2.next()) {
                        si = true;
                        if (rs2.getInt(1) == 1) {
                            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", "Sin certificado", 6 + num_cert, 1, eficienciaS);
                        }
                        if (rs2.getInt(2) == 1) {
                            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", "En proceso", 6 + num_cert, 1, eficienciaS);
                        }
                        if (rs2.getInt(3) == 1) {
                            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", "Con certificado", 6 + num_cert, 1, eficienciaS);
                        }
                        if (rs2.getInt(4) == 1) {
                            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", "En proceso recertificación", 6 + num_cert, 1, eficienciaS);
                        }
                        if (rs2.getInt(5) == 1) {
                            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", "Con recertificación", 6 + num_cert, 1, eficienciaS);
                        }
                        if (rs2.getInt(1) == 0 && rs2.getInt(2) == 0 && rs2.getInt(3) == 0 && rs2.getInt(4) == 0 && rs2.getInt(5) == 0) {
                            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", "Sin certificado", 6 + num_cert, 1, eficienciaS);
                        }
                    }
                    if (si == false) {
                        obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", "No Concluido", 6 + num_cert, 1, eficienciaS);
                    }
                    obj.espaciosEstilosVer(celdasEficienciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "datos_uni_sub", rs.getString("nombre_certificacion"), 6 + num_cert, 1, eficienciaS);
                    num_cert++;
                }
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "En caso de que la Certificación sea ISO, llenar los cuadros 15.1 y 15.2", 8 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 15.1", 9 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Situación del Certificado ISO", 10 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Indicar con una X la situación en que se encuentra la UT", 11 + num_cert, 1, eficienciaS);
            filasEficienciaS[posIniTi + nuevaPos + 12 + num_cert].setHeightInPoints(37);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Sin certificado", 12 + num_cert, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "En proceso de certificación", 12 + num_cert, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Con certificado", 12 + num_cert, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "En proceso de recertificación", 12 + num_cert, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Con recertificado", 12 + num_cert, 0, eficienciaS);
            sql1 = "SELECT sin_certificado,en_proceso,con_certificado,en_proceso_recertificacion,con_recertificado FROM eficienciain15 WHERE id_universidad='".concat(universidad).concat("' and id_periodo='").concat(periodo).concat("' and id_certificacion=1");
            rs = con.Consultar(sql1);
            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "X", 13 + num_cert, 0, eficienciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "", 13 + num_cert, 0, eficienciaS);
                }
                if (rs.getInt(2) == 1) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "X", 13 + num_cert, 0, eficienciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "", 13 + num_cert, 0, eficienciaS);
                }
                if (rs.getInt(3) == 1) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "X", 13 + num_cert, 0, eficienciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "", 13 + num_cert, 0, eficienciaS);
                }
                if (rs.getInt(4) == 1) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "X", 13 + num_cert, 0, eficienciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "", 13 + num_cert, 0, eficienciaS);
                }
                if (rs.getInt(5) == 1) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "X", 13 + num_cert, 0, eficienciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "", 13 + num_cert, 0, eficienciaS);
                }
            } else {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 13 + num_cert, 1, eficienciaS);
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 15.2", 15 + num_cert, 1, eficienciaS);
            filasEficienciaS[posIniTi + nuevaPos + 18 + num_cert].setHeightInPoints(37);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Procesos  Certificados", 16 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "Nombre del Macroproceso o Proceso certificado", 17 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "SI = 1                        No = 0", 17 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 6, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "Periodo que comprende la certificación o recertificación", 17 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "Fecha de la próxima auditoria para obtener la recertificación", 17 + num_cert, 1, eficienciaS);

            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Académico / Educativo", 19 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Vinculación", 20 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Administración / Gestion de Recursos", 21 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Educación Continua", 22 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Planeación y Evaluación", 23 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total", 24 + num_cert, 1, eficienciaS);
            int cont = 0;
            for (int i = 1; i <= 5; i++) {
                sql1 = "SELECT opcion,periodo_inicio,periodo_fin,proxima_auditoria FROM eficienciain15_1 WHERE id_universidad='".concat(universidad).concat("' and id_periodo='").concat(periodo).concat("' and num_fila=").concat(String.valueOf(i));
                rs = con.Consultar(sql1);
                if (rs.next()) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString(1), 18 + num_cert + i, 0, eficienciaS);
                    if(rs.getString(2)==null || rs.getString(3)==null ){
                        obj.espaciosEstilosVer(celdasEficienciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", " ", i + 18 + num_cert, 1, eficienciaS);
                    }else{
                        obj.espaciosEstilosVer(celdasEficienciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString(2) + " al " + rs.getString(3), i + 18 + num_cert, 1, eficienciaS);
                    }
                    obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString(4), i + 18 + num_cert, 1, eficienciaS);
                    cont += rs.getInt(1);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 18 + num_cert + i, 1, eficienciaS);
                }
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(cont), 24 + num_cert, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 4, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "", 24 + num_cert, 1, eficienciaS);
            nuevaPos += 25 + num_cert;
             
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 15);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, eficienciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 15" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 15" + e);
        } finally {
            con.Desconectar();
        }

//Indicador 16
        try {
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 16", 2, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Distribución de Libros y Títulos por Alumnos", 3, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 16.1", 5, 1, eficienciaS);
            //Encabezados
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "3", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "4", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "5", 6, 1, eficienciaS);
            filasEficienciaS[posIniTi + nuevaPos + 7].setHeightInPoints(66);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Número de Libros", 7, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Número de  Títulos", 7, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Matrícula inicial atendida en el ciclo escolar", 7, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Número de subscripciones a revistas fisicamente o electronicas vigentes al ciclo evaluado:", 7, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Número de subscripciones a bibliotecas virtuales vigentes al ciclo escolar evaluado para ser consultadas por los alumnos:", 7, 1, eficienciaS);
            //Datos Base
            sql1 = "SELECT libros, titulos, revistas, bibliotecas FROM eficienciain16 WHERE id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            double col1 = 0, col2 = 0, mat = 0;
            if (rs.next()) {
                col1 = rs.getDouble("libros");
                col2 = rs.getDouble("titulos");
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("libros"), 8, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("titulos"), 8, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("revistas"), 8, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 7, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("bibliotecas"), 8, 1, eficienciaS);
            } else {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 8, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 5, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 8, 1, eficienciaS);
            }
            sql1 = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            if (rs.next()) {
                mat = rs.getDouble(1);
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "DLPA", 9, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "DTPA", 9, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1/3", 10, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2/3", 10, 0, eficienciaS);
            if (mat > 0.0 || mat > 0) {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getEntero(col1 / mat)), 11, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getEntero(col2 / mat)), 11, 0, eficienciaS);
            } else {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 0, eficienciaS);
            }
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(mat), 8, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Libros por alumno", 12, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Títulos por alumno", 12, 0, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 8, posIniTi + nuevaPos, posIniTi + 3 + nuevaPos, "titulo_Cua_arial9Ne", "", 9, 1, eficienciaS);

            nuevaPos += 13;
              
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 16);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, eficienciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 16" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 16" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 17
        try {
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 17", 2, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Relación Alumno / Docente:", 3, 0, null);
            obj.espaciosEstilosVer(celdasEficienciaS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 17.1", 5, 1, eficienciaS);
            //Encabezados
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 6, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "RAD = 1/2", 6, 1, eficienciaS);
            filasEficienciaS[posIniTi + nuevaPos + 7].setHeightInPoints(62);
            obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Matrícula total al inicio del ciclo escolar", 7, 1, eficienciaS);
            obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Profesores de tiempo completo al inicio del ciclo escolar", 7, 1, eficienciaS);
            sql1 = "SELECT matricula_total, prof_tc FROM datos_universidad WHERE id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("matricula_total"), 8, 0, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs.getString("prof_tc"), 8, 0, eficienciaS);
                if (rs.getDouble("prof_tc") > 0 || rs.getDouble("prof_tc") > 0.0) {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getEntero(rs.getDouble("matricula_total") / rs.getDouble("prof_tc"))), 8, 1, eficienciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 8, 1, eficienciaS);
                }
            } else {
                obj.espaciosEstilosVer(celdasEficienciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 8, 1, eficienciaS);
                obj.espaciosEstilosVer(celdasEficienciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 8, 1, eficienciaS);
            }

            nuevaPos += 14;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 17" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 17" + e);
        } finally {
            con.Desconectar();
        }
        return obj;
    }
}
