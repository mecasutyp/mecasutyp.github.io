package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Universidad;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
/*
 Daniel Ramirez Torres 07/02/2014
 Actualización 2016: Salvador Zamora Arias
 */

public class Excel_Reporte_Universidad_Eficacia {
   

    public Excel_Reporte_Universidad_Eficacia() {
    }

    public Excel_Reporte_Universidad crearEficacia(String universidad, String periodo, Excel_Reporte_Universidad obj) throws FileNotFoundException, IOException {
        DecimalFormat formatea = new DecimalFormat("##.#");
        Excel_Reporte_Borrador red = new Excel_Reporte_Borrador();
        
        String consulta = "";
        String sql2 = "";
        String sql1 = "";
        String sqlcuatri = "";
        ResultSet rs = null;
        ResultSet rscuatri = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ConexionMySQL con = new ConexionMySQL();
        Universidad uni = new Universidad();
        String datos_uni = "datos_uni";
        String datos_uni_sub = "datos_uni_sub";
        String titulo_indicador = "titulo_indicador";
        String titulo_indicador_subr = "titulo_indicador_subr";
        String arial10_bordoble = "arial10_bordoble";
        String arial8_bordoble_text = "arial8_bordoble_text";
        String arial10_leyenda_res = "arial10_leyenda_res";
        String arial10_leyenda2_res = "arial10_leyenda2_res";
        String resultado_base = "resultado_base";
        String resultado_hor = "resultado_hor";
        String no_cuadro = "no_cuadro";
        String titulo_Cua_arial10MayNe = "titulo_Cua_arial10MayNe";
        String titulo_Cua_arial9Ne = "titulo_Cua_arial9Ne";
        String res1decimal = "res1decimal";
        String res1decimallidobleAria10 = "res1decimallidobleAria10";
        String resVerdecimallidobleArial9 = "resVerdecimallidobleArial9";
        String resVerdecimallidobleArial10 = "resVerdecimallidobleArial10";
        String ajustarContenido = "ajustarContenido";
        HSSFSheet eficaciaS = obj.regresarLibro().createSheet("EFICACIA");
        HSSFRow[] filasEficaciaS = new HSSFRow[982];
        HSSFCell[][] celdasEficaciaS = new HSSFCell[14][982];
        for (int i = 0; i <= 13; i++) {
            for (int j = 0; j < 981; j++) {
                filasEficaciaS[j] = eficaciaS.createRow(j);
                celdasEficaciaS[i][j] = filasEficaciaS[j].createCell(i);
            }
        }
        boolean there2004 = false;
        eficaciaS.setColumnWidth(0, 1221);
        eficaciaS.setColumnWidth(1, 6364);
        eficaciaS.setColumnWidth(2, 4800);
        eficaciaS.setColumnWidth(3, 4292);
        eficaciaS.setColumnWidth(4, 4033);
        eficaciaS.setColumnWidth(5, 3700);
        eficaciaS.setColumnWidth(6, 3663);
        eficaciaS.setColumnWidth(7, 3848);
        eficaciaS.setColumnWidth(8, 2479);
        eficaciaS.setColumnWidth(9, 2701);
        eficaciaS.setColumnWidth(10, 1887);
        eficaciaS.setColumnWidth(11, 2183);
        eficaciaS.setColumnWidth(12, 1961);
        eficaciaS.setColumnWidth(13, 2590);
        filasEficaciaS[10].setHeightInPoints(18);
        for (int i = 2; i <= 8; i++) {
            filasEficaciaS[i].setHeightInPoints(17);
        }
        eficaciaS.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, 3, 3, datos_uni, "UNIVERSIDAD TECNOLÓGICA:", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, 4, 4, datos_uni, "RESPONSABLE:", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, 5, 5, datos_uni, "PUESTO:", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, 6, 6, datos_uni, "CORREO ELECTRONICO:", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, 7, 7, datos_uni, "TELEFONO:", 0, 1, eficaciaS);
        try {
            consulta = "Select nombre, apaterno, amaterno, tipo, cargo, telefono, mail from responsables_universidad ru inner join responsables r on ru.id_responsable = r.id_responsable where ru.tipo = 'RC' and ru.id_universidad = ".concat(universidad).concat(" and ru.id_periodo = ").concat(periodo);
            rs = con.Consultar(consulta);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, 3, 3, datos_uni_sub, uni.getNombreUniversidad(universidad), 0, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, 4, 4, datos_uni_sub, rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno")), 0, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, 5, 5, datos_uni_sub, rs.getString("cargo"), 0, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, 6, 6, datos_uni_sub, rs.getString("mail"), 0, 1, eficaciaS);
                String[] parts = rs.getString("telefono").split("#"); 
                    
                if(parts.length>1){
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, 7, 7, datos_uni_sub, "("+parts[0]+") "+parts[1], 0, 1, eficaciaS);
                }else{
                     obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, 7, 7, datos_uni_sub, rs.getString("telefono"), 0, 1, eficaciaS);
                }
               
                
            }
        } catch (SQLException e) {
            System.err.println("Reporte por Universidad, Error de consulta de datos Universidad :" + e);
        }
//        //Titulo de Categoria
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 12, 10, 10, "titulo_cat", "I. EFICACIA", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, 12, 12, "titulo_indicador", "Indicador 1", 0, 0, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, 13, 13, "titulo_indicador_subr", "Alumnos de Nuevo Ingreso con EXANI II:", 0, 0, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, 15, 15, no_cuadro, "Cuadro 1.1 Alumnos de Nuevo Ingreso con EXANI II", 0, 1, eficaciaS);
        filasEficaciaS[15].setHeightInPoints(14);
//        //Inicia Cuadro 1
        filasEficaciaS[16].setHeightInPoints(14);
        for (int i = 1; i <= 7; i++) {
            obj.espaciosEstilosVer(celdasEficaciaS, i, i, 16, 16, arial10_bordoble, String.valueOf(i), 0, 0, eficaciaS);
        }
        filasEficaciaS[17].setHeightInPoints(14);
        filasEficaciaS[18].setHeightInPoints(93);
        obj.espaciosEstilosVer(celdasEficaciaS, 3, 5, 17, 17, arial8_bordoble_text, " Rango de Calificaciones Obtenidas", 0, 1, eficaciaS);
        filasEficaciaS[15].setHeightInPoints(14);
        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, 17, 18, arial8_bordoble_text, "Egresados de bachillerato que presentaron el EXANI - II en la UT", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, 17, 18, arial8_bordoble_text, "Alumnos de nuego ingreso a la UT", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, 18, 18, "arial8_bordoble_text", "Alumnos de nuevo ingreso que presentaron el EXANI – II y lograron 1,101 a 1,300 puntos de calificación", 0, 0, null);
        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, 18, 18, "arial8_bordoble_text", "Alumnos de nuevo ingreso que presentaron el EXANI – II y lograron 901 a 1,100 puntos de calificación", 0, 0, null);
        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, 18, 18, "arial8_bordoble_text", "Alumnos de nuevo ingreso que presentaron el EXANI – II y lograron 700 a 900 puntos de calificación", 0, 0, null);
        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, 17, 18, "arial8_bordoble_text", "Alumnos de nuevo ingreso inscritos a la universidad que presentaron el EXANI II en el ciclo escolar", 0, 1, eficaciaS);
        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, 17, 18, "arial8_bordoble_text", "Alumnos de nuevo ingreso inscritos a la universidad que no presentaron el EXANI II en el ciclo escolar", 0, 1, eficaciaS);
        filasEficaciaS[19].setHeightInPoints(29);
//        //Datos del indicador 1
        try {
            consulta = "Select egresados_bachillerato, nuevo_ingreso, c1, c2, c3, aplica, comentario from eficaciain1 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
            rs = con.Consultar(consulta);
            String comentario="Sin comentarios";
            if (rs.next()) {
                comentario = rs.getString("comentario");
                if (rs.getInt("aplica") == 1) {
                    float suma = 0;
                    for (int i = 1; i <= 5; i++) {
                        obj.espaciosEstilosVer(celdasEficaciaS, i, i, 19, 19, resultado_base, String.valueOf(rs.getInt(i)), 0, 1, eficaciaS);
                        if (i >= 3 && i <= 5) {
                            suma += rs.getInt(i);
                        }
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, 19, 19, titulo_Cua_arial9Ne, String.valueOf(suma), 0, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, 22, 22, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(suma / rs.getFloat("egresados_bachillerato") * 100)), 0, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, 22, 22, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(suma / rs.getFloat("nuevo_ingreso") * 100)), 0, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, 22, 22, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(rs.getFloat("c1") / suma * 100)), 0, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, 22, 22, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(rs.getFloat("c2") / suma * 100)), 0, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, 22, 22, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(rs.getFloat("c3") / suma * 100)), 0, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, 19, 19, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(rs.getInt("nuevo_ingreso") - suma)), 0, 0, eficaciaS);
                } else {
                    for (int i = 1; i <= 5; i++) {
                        obj.espaciosEstilosVer(celdasEficaciaS, i, i, 22, 22, resultado_hor, "0", 0, 0, eficaciaS);
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, 19, 19, titulo_Cua_arial9Ne, "0", 0, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, 19, 19, titulo_Cua_arial9Ne, "0", 0, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, 19, 19, resultado_base, "No se aplicó el EXANI", 0, 1, eficaciaS);
                }
            } else {
                for (int i = 1; i <= 5; i++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, i, i, 22, 22, titulo_Cua_arial9Ne, "0", 0, 1, eficaciaS);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, 19, 19, resultado_base, "NO CONCLUIDO", 0, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, 19, 19, titulo_Cua_arial9Ne, "0", 0, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, 19, 19, titulo_Cua_arial9Ne, "0", 0, 0, eficaciaS);
            }
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, 21, 21, arial10_leyenda2_res, "6/1", 0, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, 21, 21, arial10_leyenda2_res, "6/2", 0, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, 21, 21, arial10_leyenda2_res, "3/6", 0, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, 21, 21, arial10_leyenda2_res, "4/6", 0, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, 21, 21, arial10_leyenda2_res, "5/6", 0, 0, eficaciaS);
            for (int i = 1; i <= 5; i++) {
                obj.espaciosEstilosVer(celdasEficaciaS, i, i, 20, 20, arial10_leyenda_res, "ANI" + i, 0, 0, eficaciaS);
                celdasEficaciaS[i][20].setCellValue("ANI" + i);
            }
            obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, 20, 22, titulo_Cua_arial9Ne, "", 0, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, 24, 24, titulo_Cua_arial9Ne, "Comentario", 0, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 5, 24, 24, resultado_base, comentario, 0, 1, eficaciaS);
        } catch (SQLException ex) {
            System.err.println("Reporte por Universidad, Error de consulta de datos Indicador 1 :" + ex);
        } finally {
            con.Desconectar();
        }
////Indicador 2
        //Numero de Cuadro
        int nuevaPos = 0, noC = 1, noProg = 1, cProg = 1;
        //int posIni = 26;
        int posIniTi = 28;
        //Niveles por universidad
        sql1 = "";
        ArrayList Nivel_Uni = new ArrayList();
        ArrayList id_Nivel_Uni = new ArrayList();
        ArrayList Tot_Niv_Sep = new ArrayList();
        ArrayList Tot_Niv_Ene = new ArrayList();
        ArrayList Tot_Niv_May = new ArrayList();
        try {
            sql1 = "Select distinct(pe.id_nivel),nombre, descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, 25, 25, titulo_indicador, "Indicador 2", 0, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, 26, 26, titulo_indicador_subr, "Aprovechamiento Académico por cuatrimestre y ciclo escolar:", 0, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, no_cuadro, "Cuadro 2.1." + rs.getString("id_nivel"), 0, 0, eficaciaS);
                eficaciaS.addMergedRegion(new CellRangeAddress(posIniTi + nuevaPos, posIniTi + nuevaPos, 0, 5));
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Aprovechamiento Académico del Nivel " + rs.getString("descripcion") + " (con Competencias Profesionales) Según Carrera", 1, 1, eficaciaS);
                filasEficaciaS[(posIniTi + 2) + nuevaPos].setHeightInPoints(26);
                filasEficaciaS[(posIniTi + 3) + nuevaPos].setHeightInPoints(33);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "No.", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Carrera", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Calificación por Cuatrimestre", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre sep-dic", 3, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre ene-abr", 3, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre may-ago", 3, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Promedio Calificación (A+B+C)/3", 2, 1, eficaciaS);
//                //Programas Educativos
                sql1 = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and version!='2004' and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                rs2 = con.Consultar(sql1);
                int cantSep = 0, cantEne = 0, cantMay = 0, cantV = 0, cantTotV = 0;
                float sumSepH = 0, sumEneH = 0, sumMayH = 0, sumaV = 0, sumtTotV = 0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(cProg), 3 + cProg, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs2.getString("nombre_programa"), 3 + cProg, 1, eficaciaS);
                    String sql3 = "Select id_pe,sep, ene, may from eficaciain2 where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_pe='".concat(rs2.getString("id_pe")).concat("'")));
                    rs3 = con.Consultar(sql3);
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs3.getString("sep"), 3 + cProg, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs3.getString("ene"), 3 + cProg, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs3.getString("may"), 3 + cProg, 0, eficaciaS);
                        if (rs3.getFloat("sep") != 0.0 || Integer.parseInt(rs3.getString("sep")) != 0) {
                            sumSepH += rs3.getFloat("sep");
                            cantSep++;
                            cantV += 1;
                            sumaV += rs3.getFloat("sep");
                        }
                        if (rs3.getFloat("ene") != 0.0 || Integer.parseInt(rs3.getString("ene")) != 0) {
                            sumEneH += rs3.getFloat("ene");
                            cantEne++;
                            cantV += 1;
                            sumaV += rs3.getFloat("ene");
                        }
                        if (rs3.getFloat("may") != 0.0 || Integer.parseInt(rs3.getString("may")) != 0) {
                            sumMayH += rs3.getFloat("may");
                            cantMay++;
                            cantV += 1;
                            sumaV += rs3.getFloat("may");
                        }
                    } else {
                        for (int i = 3; i <= 5; i++) {
                            obj.espaciosEstilosVer(celdasEficaciaS, i, i, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, res1decimal, "0", 3 + cProg, 0, eficaciaS);
                        }
                    }
                    if (sumaV / cantV > 0) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, resVerdecimallidobleArial9, String.valueOf(obj.getDecimal(sumaV / cantV)), 3 + cProg, 0, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, resVerdecimallidobleArial9, "0", 3 + cProg, 0, eficaciaS);
                    }
                    if (sumaV / cantV > 0.0 || sumaV / cantV > 0) {
                        cantTotV += 1;
                        sumtTotV += sumaV / cantV;
                    }
                    cantV = 0;
                    sumaV = 0;
                    cProg++;
                }
                filasEficaciaS[(posIniTi + 3) + nuevaPos + cProg].setHeightInPoints(27);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Promedio de aprovechamiento Académico Cuatrimestral", 3 + cProg, 1, eficaciaS);
                 
                Nivel_Uni.add(rs.getString("descripcion"));
                id_Nivel_Uni.add(rs.getString(1));
                //Se agregan los totales horizontales a un ArrayList Para los ultimos totales
                Tot_Niv_Sep.add(sumSepH / cantSep);
                if (sumEneH / cantEne > 0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumSepH / cantSep)), 3 + cProg, 1, eficaciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, "0", 3 + cProg, 1, eficaciaS);
                }
                Tot_Niv_Ene.add(sumEneH / cantEne);
                if (sumEneH / cantEne > 0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumEneH / cantEne)), 3 + cProg, 1, eficaciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, "0", 3 + cProg, 1, eficaciaS);
                }
                Tot_Niv_May.add(sumMayH / cantMay);
                if (sumMayH / cantMay > 0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumMayH / cantMay)), 3 + cProg, 1, eficaciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, "0", 3 + cProg, 1, eficaciaS);
                }
//                //Total Vertical
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumtTotV / cantTotV)), 3 + cProg, 1, eficaciaS);
                nuevaPos += 5 + cProg;
                cProg = 1;
                noC++;
            }
            nuevaPos ++;
            
            
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 2" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 2" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 2 version 2004
        int cantSep = 0, cantEne = 0, cantMay = 0;
        float sumSepH = 0, sumEneH = 0, sumMayH = 0;
        try {
            sql1 = "Select distinct(pe.id_nivel),nombre, descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            if (rs.next()) {
                sql2 = "select id_pe,nombre_programa,version from programa_educativo where version='2004' and id_pe in (select id_pe from pe_universidad where id_nivel=1 and id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")"));
                rs2 = con.Consultar(sql2);
                while (rs2.next()) {
                    there2004 = true;
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 2.1." + rs2.getString("id_nivel"), 0, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Aprovechamiento Académico del Nivel " + rs.getString("descripcion") + " Versión 2004 Según Carrera", 0, 2, eficaciaS);
                    filasEficaciaS[(posIniTi + 2) + nuevaPos].setHeightInPoints(26);
                    filasEficaciaS[(posIniTi + 3) + nuevaPos].setHeightInPoints(33);
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "No.", 2, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Carrera", 2, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Calificación por Cuatrimestre", 2, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre sep-dic", 3, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre ene-abr", 3, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre may-ago", 3, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Promedio Calificación (A+B+C)/3", 2, 1, eficaciaS);
                    //Programas Educativos
                    String sql3 = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and version='2004' and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                    rs3 = con.Consultar(sql3);
                    int cantV = 0, cantTotV = 0;
                    float sumaV = 0, sumtTotV = 0;
                    while (rs3.next()) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(cProg), 3 + cProg, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs3.getString("nombre_programa"), 3 + cProg, 1, eficaciaS);
                        String sql4 = "Select id_pe,sep, ene, may from eficaciain2 where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_pe='".concat(rs3.getString("id_pe")).concat("'")));
                        rs4 = con.Consultar(sql4);
                        if (rs4.next()) {
                            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs4.getString("sep"), 3 + cProg, 1, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs4.getString("ene"), 3 + cProg, 1, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs4.getString("may"), 3 + cProg, 1, eficaciaS);
                            if (rs4.getFloat("sep") != 0.0 || Integer.parseInt(rs4.getString("sep")) != 0) {
                                sumSepH += rs4.getFloat("sep");
                                cantSep++;
                                cantV += 1;
                                sumaV += rs4.getFloat("sep");
                            }
                            if (rs4.getFloat("ene") != 0.0 || Integer.parseInt(rs4.getString("ene")) != 0) {
                                sumEneH += rs4.getFloat("ene");
                                cantEne++;
                                cantV += 1;
                                sumaV += rs4.getFloat("ene");
                            }
                            if (rs4.getFloat("may") != 0.0 || Integer.parseInt(rs4.getString("may")) != 0) {
                                sumMayH += rs4.getFloat("may");
                                cantMay++;
                                cantV += 1;
                                sumaV += rs4.getFloat("may");
                            }
                        } else {
                            for (int i = 3; i <= 5; i++) {
                                obj.espaciosEstilosVer(celdasEficaciaS, i, i, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, "0", 3 + cProg, 1, eficaciaS);
                            }
                        }
                        if (sumaV / cantV > 0) {
                            obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial9, String.valueOf(obj.getDecimal(sumaV / cantV)), 3 + cProg, 1, eficaciaS);
                        } else {
                            obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial9, "0", 3 + cProg, 1, eficaciaS);
                        }
                        if (sumaV / cantV > 0.0 || sumaV / cantV > 0) {
                            cantTotV += 1;
                            sumtTotV += sumaV / cantV;
                        }
                        cantV = 0;
                        sumaV = 0;
                        cProg++;
                    }
                    filasEficaciaS[(posIniTi + 3) + nuevaPos + cProg].setHeightInPoints(27);
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Promedio de aprovechamiento Académico Cuatrimestral", 3 + cProg, 1, eficaciaS);
                    if (sumSepH / cantSep > 0) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumSepH / cantSep)), 3 + cProg, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, "0", 3 + cProg, 1, eficaciaS);
                    }
                    if (sumEneH / cantEne > 0) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumEneH / cantEne)), 3 + cProg, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, "0", 3 + cProg, 1, eficaciaS);
                    }
                    if (sumMayH / cantMay > 0) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumMayH / cantMay)), 3 + cProg, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, "0", 3 + cProg, 1, eficaciaS);
                    }
                    //Total Vertical
                    if (sumtTotV / cantTotV > 0) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, String.valueOf(obj.getDecimal(sumtTotV / cantTotV)), 3 + cProg, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimallidobleAria10, "0", 3 + cProg, 1, eficaciaS);
                    }
                    nuevaPos += 5 + cProg;
                    cProg = 1;
                    noC++;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 2 2004" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 2 2004" + e);
        } finally {
            con.Desconectar();
        }
//Totales indicador 2 competencias
        double sumaSepH = 0, sumaEneH = 0, sumaMayH = 0, sumaTotalV = 0;
        int contSeph = 0, contEneh = 0, contMayh = 0, contToV = 0, contToPromV = 0;
        try {
            sql1 = "Select distinct(pe.id_nivel),nombre from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            if (rs.next()) {
                String sqlmaxniv = "select (max(id_nivel)+1) as id_nivelm from nivel_pe";
                ResultSet rsmaxniv = con.Consultar(sqlmaxniv);
                rsmaxniv.next();
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 2.2", 0, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Aprovechamiento Académico de la Universidad por Competencias Profesionales Según Nivel", 1, 1, eficaciaS);
                filasEficaciaS[(posIniTi + 2) + nuevaPos].setHeightInPoints(26);
                filasEficaciaS[(posIniTi + 3) + nuevaPos].setHeightInPoints(33);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "No.", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "NIVEL", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Calificación por Cuatrimestre", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre sep-dic", 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre ene-abr", 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre may-ago", 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, titulo_Cua_arial9Ne, "Promedio Calificaciones", 2, 1, eficaciaS);
                double sumaV = 0;
                int contNiv = 1, inTotal = 0;
                for (int i = 0; i <= Nivel_Uni.size() - 1; i++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(i + 1), 3 + i + 1, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, Nivel_Uni.get(i).toString(), 3 + i + 1, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(Float.parseFloat(Tot_Niv_Sep.get(i).toString()))), 3 + i + 1, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(Float.parseFloat(Tot_Niv_Ene.get(i).toString()))), 3 + i + 1, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(Float.parseFloat(Tot_Niv_May.get(i).toString()))), 3 + i + 1, 0, eficaciaS);
                    //Se evalua los totales por cuatrimestre 
                    if (Double.parseDouble(Tot_Niv_Sep.get(i).toString()) > 0.0 || Double.parseDouble(Tot_Niv_Sep.get(i).toString()) > 0.0) {
                        sumaV += Double.parseDouble(Tot_Niv_Sep.get(i).toString());
                        contToPromV += 1;
                        sumaSepH += Double.parseDouble(Tot_Niv_Sep.get(i).toString());
                        contSeph++;
                    }
                    if (Double.parseDouble(Tot_Niv_Ene.get(i).toString()) > 0.0 || Double.parseDouble(Tot_Niv_Ene.get(i).toString()) > 0.0) {
                        sumaV += Double.parseDouble(Tot_Niv_Ene.get(i).toString());
                        sumaEneH += Double.parseDouble(Tot_Niv_Ene.get(i).toString());
                        contToPromV += 1;
                        contEneh++;
                    }
                    if (Double.parseDouble(Tot_Niv_May.get(i).toString()) > 0.0 || Double.parseDouble(Tot_Niv_May.get(i).toString()) > 0.0) {
                        sumaV += Double.parseDouble(Tot_Niv_May.get(i).toString());
                        sumaMayH += Double.parseDouble(Tot_Niv_May.get(i).toString());
                        contToPromV += 1;
                        contMayh++;
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(sumaV / contToPromV)), 3 + i + 1, 0, eficaciaS);
                    if (sumaV / contToPromV > 0.0 || sumaV / contToPromV > 0) {
                        sumaTotalV += sumaV / contToPromV;
                        contToV++;
                    }
                    contNiv++;
                    inTotal++;
                    contToPromV = 0;
                    sumaV = 0.0;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Promedio de aprovechamiento Académico Cuatrimestral ", 3 + contNiv, 1, eficaciaS);
               
                filasEficaciaS[posIniTi + 3 + (contNiv) + nuevaPos].setHeightInPoints(26);
//                //Totales Horizontales
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(sumaSepH / contSeph)), 3 + contNiv, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(sumaEneH / contEneh)), 3 + contNiv, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(sumaMayH / contMayh)), 3 + contNiv, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, resVerdecimallidobleArial10, String.valueOf(obj.getDecimal(sumaTotalV / contToV)), 3 + contNiv, 1, eficaciaS);
//              //COMENTARIOS
                String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 2);
                nuevaPos+=2;
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + contNiv, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + contNiv, 1, eficaciaS);
                
                nuevaPos += 5 + contNiv;
                noC++;
            }

        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 2 Totales" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 2 Totales" + e);
        } finally {
            con.Desconectar();
        }
//Totales indicador 2 competencias y 2004
        try {
            if (there2004 == true) {
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 2." + noC, 0, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Aprovechamiento Académico de la Universidad", 1, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "No.", 2, 1, eficaciaS);
                filasEficaciaS[(posIniTi + 2) + nuevaPos].setHeightInPoints(26);
                filasEficaciaS[(posIniTi + 3) + nuevaPos].setHeightInPoints(33);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "NIVEL", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Calificación por Cuatrimestre", 2, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre sep-dic", 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre ene-abr", 3, 1, eficaciaS);
                celdasEficaciaS[3][(posIniTi + 3) + nuevaPos].setCellValue("Cuatrimestre ene-abr");
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cuatrimestre may-ago", 3, 1, eficaciaS);
                celdasEficaciaS[4][(posIniTi + 3) + nuevaPos].setCellValue("Cuatrimestre may-ago");
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "Promedio Calificaciones", 2, 1, eficaciaS);
                int inTotal = 0, cantV = 0;
                double totV = 0;
                String nivs = "";
//                //Total version 2004
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "1", 4, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "5B TSU versión 2004", 4, 0, eficaciaS);
                if (sumSepH / cantSep > 0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumSepH / cantSep)), 4, 0, eficaciaS);
                    cantV++;
                    totV += sumSepH / cantSep;
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 4, 0, eficaciaS);
                    celdasEficaciaS[2][posIniTi + 4 + nuevaPos].setCellValue("0");
                }
                if (sumEneH / cantEne > 0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumEneH / cantEne)), 4, 0, eficaciaS);
                    cantV++;
                    totV += sumEneH / cantEne;
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 4, 0, eficaciaS);
                }
                if (sumMayH / cantMay > 0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumMayH / cantMay)), 4, 0, eficaciaS);
                    cantV++;
                    totV += sumMayH / cantMay;
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 4, 0, eficaciaS);
                }
                //2004
                //promedio de calificaciones
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(totV / cantV)), 4, 0, eficaciaS);
                //competencias
                for (int i = 0; i <= Nivel_Uni.size() - 1; i++) {
                    nivs += Nivel_Uni.get(i).toString().concat(",");
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "2", 5, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, nivs.concat(" con competencias Profesionales"), 5, 0, eficaciaS);
                filasEficaciaS[posIniTi + 5 + nuevaPos].setHeightInPoints(40);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumaSepH / contSeph)), 5, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumaEneH / contEneh)), 5, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumaMayH / contMayh)), 5, 0, eficaciaS);
                //promedio de calificaciones
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(sumaTotalV / contToV)), 5, 0, eficaciaS);
                //Totales
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Promedio Total de universidad en número", 6, 1, eficaciaS);
                filasEficaciaS[posIniTi + 6 + nuevaPos].setHeightInPoints(32);
                //Totales finales horizontales}
                double a, a1, b, b1, c, c1;
                int contA = 0, contC = 0, contB = 0;
                if (cantSep > 0) {
                    a = sumSepH / cantSep;
                    contA++;
                } else {
                    a = 0;
                }
                if (contSeph > 0) {
                    a1 = sumaSepH / contSeph;
                    contA++;
                } else {
                    a1 = 0;
                }
                if (cantEne > 0) {
                    b = sumEneH / cantEne;
                    contB++;
                } else {
                    b = 0;
                }
                if (contEneh > 0) {
                    b1 = sumaEneH / contEneh;
                    contB++;
                } else {
                    b1 = 0;
                }
                if (cantMay > 0) {
                    c = sumMayH / cantMay;
                    contC++;
                } else {
                    c = 0;
                }
                if (contMayh > 0) {
                    c1 = sumaMayH / contMayh;
                    contC++;
                } else {
                    c1 = 0;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal((a + a1) / contA)), 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal((b + b1) / contB)), 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal((c + c1) / contC)), 6, 0, eficaciaS);
                //Total final vertical
                int contFinal = 0;
                double cantFinalCom, cantFinal2004;
                if (sumaTotalV / contToV > 0) {
                    cantFinalCom = sumaTotalV / contToV;
                    contFinal++;
                } else {
                    cantFinalCom = 0;
                }
                if (totV / cantV > 0) {
                    cantFinal2004 = totV / cantV;
                    contFinal++;
                } else {
                    cantFinal2004 = 0;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal((cantFinalCom + cantFinal2004) / contFinal)), 6, 0, eficaciaS);
                nuevaPos += 5 + 4;
            }
            Tot_Niv_Sep.clear();
            Tot_Niv_Ene.clear();
            Tot_Niv_Sep.clear();
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 2 totales 2004 competencias" + e);
        } finally {
            con.Desconectar();
        }
//indicador 3
        try {
            boolean siNivel = false;
            ArrayList resNivel = new ArrayList();
            sql1 = "Select distinct(pe.id_nivel),nombre, descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            noC = 1;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 3", 0, 0, eficaciaS);
            if(Integer.parseInt(periodo) > 24){
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Reprobación definitiva por cuatrimestre", 1, 0, eficaciaS);
            }else{
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Reprobación definitiva por cuatrimestre (En este indicador solo se presenta matrícula y reprobación total, no por programa educativo )", 1, 0, eficaciaS);
            }
            double totret [] = new double [Nivel_Uni.size()];
            
            while (rs.next()) {
                siNivel = true;
                String sqlpe = "SELECT * FROM pe_universidad pu INNER JOIN programa_educativo pe ON pe.id_pe=pu.id_pe  WHERE pu.id_universidad=".concat(universidad) .concat(" and pu.id_periodo=".concat(periodo)
                    .concat(" and pe.id_nivel='".concat(rs.getString("id_nivel")).concat("'")));
                ResultSet rspe = con.Consultar(sqlpe);
                sql1 = "select mat_sep,rep_sep ,mat_ene,rep_ene, mat_may,rep_may from eficaciain3 where id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo).concat(" and id_nivel='".concat(rs.getString(1)).concat("'")));
//                        +" AND id_pe="+rspe.getString("id_pe");
                ResultSet rs1 = con.Consultar(sql1);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 3.1." + rs.getString("id_nivel"), 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, rs.getString("descripcion") + " - Porcentaje Promedio Cuatrimestral de Reprobación", 4, 1, eficaciaS);
                //tiulos
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Programa educativo", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida cuatrimestre Sep-Dic", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos reprobados definitivos del cuatrimestre Septiembre-Diciembre", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida cuatrimestre Enero-Abril", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos reprobados definitivos del cuatrimestre enero-abril", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida cuatrimestre Mayo-Agosto", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos reprobados definitivos del cuatrimestre Mayo-Agosto", 6, 0, eficaciaS);
//                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "% Promedio de Reprobación", 6, 0, eficaciaS);
                filasEficaciaS[posIniTi + 6 + nuevaPos].setHeightInPoints(60);
                filasEficaciaS[posIniTi + 7 + nuevaPos].setHeightInPoints(20);
                
                int sumtot []= new int [6];
                while(rspe.next()){
//                    sql1 = "select mat_sep,rep_sep ,mat_ene,rep_ene, mat_may,rep_may from eficaciain3 where id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo).concat(" and id_nivel='".concat(rs.getString(1)).concat("'")))+
//                            " AND id_pe="+rspe.getString("id_pe");
//                    rs1 = con.Consultar(sql1);
                    if(rs1.next()){
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rspe.getString("nombre_programa"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs1.getString("mat_sep"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs1.getString("rep_sep"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs1.getString("mat_ene"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs1.getString("rep_ene"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs1.getString("mat_may"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs1.getString("rep_may"), 7, 0, eficaciaS);
                        sumtot[0]+=rs1.getInt("mat_sep");
                        sumtot[1]+=rs1.getInt("rep_sep");
                        sumtot[2]+=rs1.getInt("mat_ene");
                        sumtot[3]+=rs1.getInt("rep_ene");
                        sumtot[4]+=rs1.getInt("mat_may");
                        sumtot[5]+=rs1.getInt("rep_may");
                    }else{
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rspe.getString("nombre_programa"), 7, 0, eficaciaS);
                        for(int z=2; z<8;z++){
                            obj.espaciosEstilosVer(celdasEficaciaS, z, z, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "0", 7, 0, eficaciaS);
                        }
                    }
                    nuevaPos++;
                }
                //SUMA DE COLUMNAS 
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 7, 1, eficaciaS);
//                obj.espaciosEstilosVer(celdasEficaciaS, 3, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Calificación por Cuatrimestre", 2, 1, eficaciaS);
                 for(int a=0; a<6;a++){
                    obj.espaciosEstilosVer(celdasEficaciaS, (a+2), (a+2), posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+sumtot[a], 7, 0, eficaciaS);
                 }
                //PORCENTAJES 
                 nuevaPos++;
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RE1", 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RE2", 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RE3", 7, 1, eficaciaS); 
                 nuevaPos++;
                 double re1 = 0;
                 double re2 = 0;
                 double re3 = 0;
                 if(sumtot[1]>0){
                     re1 = red.redondear((double) sumtot[1]/sumtot[0] * 100);
                 }
                 if(sumtot[3]>0){
                     re2 = red.redondear((double) sumtot[3]/sumtot[2] * 100);
                 }
                 if(sumtot[5]>0){
                     re3 = red.redondear((double) sumtot[5]/sumtot[4] * 100);
                 }
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+re1 , 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+re2 , 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+re3 , 7, 1, eficaciaS); 
                nuevaPos++;
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RET", 7, 1, eficaciaS);                 
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+red.redondear((re1+re2+re3)/3) , 7, 1, eficaciaS);                 
                totret[noC-1]=red.redondear((re1+re2+re3)/3);
                
                noC++;
                nuevaPos += 7;
            }
//tabla de totales
            
            if (siNivel == true) {
                String sqlnivmax = "select (max(id_nivel)+1) as nivelmasuno from nivel_pe ";
                ResultSet rsnivmax = con.Consultar(sqlnivmax);
                rsnivmax.next();
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 3.2", 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Porcentaje Promedio de Reprobación de la universidad", 4, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nivel", 5, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "% Promedio", 5, 1, eficaciaS);
                double suma =0;
                int cont =0;
                for (int i = 0; i <= Nivel_Uni.size() - 1; i++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, Nivel_Uni.get(i).toString(), 6 + i, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, String.valueOf(totret[i]), 6 + i, 0, eficaciaS);
                    suma += totret[i];
                    cont++;
                }
              obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 6 + Nivel_Uni.size(), 0, eficaciaS);
               obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, ""+red.redondear(suma/ cont), 6 + Nivel_Uni.size(), 0, eficaciaS);      
                //nuevaPos += 12 + Nivel_Uni.size() - 1;
                resNivel.clear();
            }
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 3);
            nuevaPos+= 4 + Nivel_Uni.size();
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
            nuevaPos+=3;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 3 " + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 3 " + e);

        } finally {
            con.Desconectar();
        }
        
        
//Indicador 4
        try {
            nuevaPos+=5;
            boolean noConclu = false;
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            noC = 1;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 4", 0, 0, null);
            if(Integer.parseInt(periodo) > 24){
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Deserción Cuatrimestral:", 1, 0, null);
            }else{
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Deserción Cuatrimestral (En este indicador solo se presenta matrícula y deserción total, no por programa educativo ):", 1, 0, null);
            }
            ArrayList totalesNiv = new ArrayList();
             double totret [] = new double [Nivel_Uni.size()];
            while (rs.next()) {
                sql1 = "Select mat_sep, mat_ene, mat_may from eficaciain3 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1));
                ResultSet rs1 = con.Consultar(sql1);
                 String sql10 = "select mat_sep,mat_ene,mat_may from eficaciain3 where id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo).concat(" and id_nivel='".concat(rs.getString(1)).concat("'")));
//                            +" AND id_pe="+rspe.getString("id_pe");
                    ResultSet rs10 = con.Consultar(sql10);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 4.1." + rs.getString("id_nivel"), 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, rs.getString("descripcion") + " - Deserción Cuatrimestral", 4, 1, eficaciaS);
                //tiulos
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Programa educativo", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos desertores del cuatrimestre Septiembre-Diciembre", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida cuatrimestre Sep-Dic", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos desertores del cuatrimestre enero-abril", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida cuatrimestre Enero-Abril", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos desertores del cuatrimestre Mayo-Agosto", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Matrícula inicial atendida cuatrimestre Mayo-Agosto", 6, 0, eficaciaS);
//                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "% Promedio de Desertores", 6, 0, eficaciaS);
                filasEficaciaS[posIniTi + 6 + nuevaPos].setHeightInPoints(60);
                filasEficaciaS[posIniTi + 7 + nuevaPos].setHeightInPoints(20);
                //Datos de la base 
                 String sqlpe = "SELECT * FROM pe_universidad pu INNER JOIN programa_educativo pe ON pe.id_pe=pu.id_pe  WHERE pu.id_universidad=".concat(universidad) .concat(" and pu.id_periodo=".concat(periodo)
                    .concat(" and pe.id_nivel='".concat(rs.getString("id_nivel")).concat("'")));
                ResultSet rspe = con.Consultar(sqlpe);
                int totalcol [] = new int [6];
                while(rspe.next()){
                   
                    String sql3 = "Select tot_deser_sep, tot_deser_ene, tot_deser_may from eficaciain4_1 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1))+" AND id_pe="+rspe.getString("id_pe");
                    rs2 = con.Consultar(sql3);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rspe.getString("nombre_programa"), 7, 0, eficaciaS);
                    if(rs10.next()){
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, rs10.getString("mat_sep"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, rs10.getString("mat_ene"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, rs10.getString("mat_may"), 7, 0, eficaciaS);
                        totalcol[1]+=rs10.getInt("mat_sep");
                        totalcol[3]+=rs10.getInt("mat_ene");
                        totalcol[5]+=rs10.getInt("mat_may");
                    }else{
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, "0", 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, "0", 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, "0", 7, 0, eficaciaS);
                    }
                    if(rs2.next()){
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, rs2.getString("tot_deser_sep"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, rs2.getString("tot_deser_ene"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, rs2.getString("tot_deser_may"), 7, 0, eficaciaS);
                        totalcol[0]+=rs2.getInt("tot_deser_sep");
                        totalcol[2]+=rs2.getInt("tot_deser_ene");
                        totalcol[4]+=rs2.getInt("tot_deser_may");
                    }else{
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, "0", 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, "0", 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, resultado_base, "0", 7, 0, eficaciaS);
                    }
                    nuevaPos++;
                }
                //TOTALES DE COLUMNA
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 7, 0, eficaciaS);
                 for(int a=0; a<6;a++){
                    obj.espaciosEstilosVer(celdasEficaciaS, (a+2), (a+2), posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+totalcol[a], 7, 0, eficaciaS);
                 }
                 //PORCENTAJES 
                 nuevaPos++;
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RE1", 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RE2", 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RE3", 7, 1, eficaciaS); 
                 nuevaPos++;
                 double re1 = 0;
                 double re2 = 0;
                 double re3 = 0;
                 if(totalcol[0]>0){
                     re1 = red.redondear((double) totalcol[0]/totalcol[1] * 100);
                 }
                 if(totalcol[2]>0){
                     re2 = red.redondear((double) totalcol[2]/totalcol[3] * 100);
                 }
                 if(totalcol[4]>0){
                     re3 = red.redondear((double) totalcol[4]/totalcol[5] * 100);
                 }
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+re1 , 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+re2 , 7, 1, eficaciaS); 
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+re3 , 7, 1, eficaciaS); 
                nuevaPos++;
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "RET", 7, 1, eficaciaS);                 
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, ""+red.redondear((re1+re2+re3)/3) , 7, 1, eficaciaS);                 
                totret[noC-1]=red.redondear((re1+re2+re3)/3);
                noC++;
                nuevaPos += 7;
            }
            rs.beforeFirst();
            
//            //segunda parte 
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 4.2." + rs.getString("id_nivel"), 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Principales causas de Deserción del Nivel " + rs.getString("descripcion"), 4, 1, eficaciaS);
                filasEficaciaS[posIniTi + 5 + nuevaPos].setHeightInPoints(27);
                filasEficaciaS[posIniTi + 6 + nuevaPos].setHeightInPoints(27);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "CAUSAS DE DESERCIÓN", 5, 1, eficaciaS);
                //cuerpo
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Septiembre Diciembre", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Enero-Abril", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Mayo-Agosto", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "CUATRIMESTRE", 5, 1, eficaciaS);
                sql1 = "Select id_causa, descripcion from causas_desercion";
                rs2 = con.Consultar(sql1);
                int conCausas = 0;
                int colSep = 0, colEne = 0, colMay = 0;
                while (rs2.next()) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs2.getString("descripcion"), 7 + conCausas, 1, eficaciaS);
//                            //valores
                    sql1 = "Select sep, ene, may from eficaciain4 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and id_causa=").concat(rs2.getString(1));
                    rs3 = con.Consultar(sql1);
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs3.getString("sep"), 7 + conCausas, 0, eficaciaS);
                        colSep += rs3.getInt("sep");
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs3.getString("ene"), 7 + conCausas, 0, eficaciaS);
                        colEne += rs3.getInt("ene");
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, rs3.getString("may"), 7 + conCausas, 0, eficaciaS);
                        colMay += rs3.getInt("may");
                        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(rs3.getInt("sep") + rs3.getInt("may") + rs3.getInt("ene")), 7 + conCausas, 0, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, "No Concluido", 7 + conCausas, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 7 + conCausas, 0, eficaciaS);
                    }
                    conCausas++;
                }
//                        //total horizontal pie
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL DE DESERTORES", 7 + conCausas, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(colSep), 7 + conCausas, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(colEne), 7 + conCausas, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(colMay), 7 + conCausas, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(colSep + colMay + colEne), 7 + conCausas, 0, null);
                nuevaPos += 7 + conCausas;
                noC++;
            }
            
            if (Nivel_Uni.size() > 0) {
                int contN = 0;
                double total = 0;
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 4.3" , 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Porcentaje Promedio de Deserción de la universidad", 4, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Nivel", 5, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Promedio", 5, 1, eficaciaS);
                
                for (int i = 0; i <= Nivel_Uni.size() - 1; i++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, Nivel_Uni.get(i).toString(), 6 + i, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, res1decimal, String.valueOf(totret[i]), 6 + i, 0, eficaciaS);
                    if (totret[i] > 0) {
                        total += totret[i];
                    }
                        contN++;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PROMEDIO", 6 + Nivel_Uni.size(), 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(red.redondear(total / contN)), 6 + Nivel_Uni.size(), 0, eficaciaS);
                nuevaPos += 4 + Nivel_Uni.size();
            }
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 4);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
            nuevaPos+=3;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 4" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 4" + e);
        } finally {
            con.Desconectar();
        }
//5
        try {
            noC = 1;
            int anio = 0;
            int contModelos = 1, contModelos1 = 1, contModelos2 = 1;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 5", 3, 0, null);
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Tasa de Egreso, Titulación y la de Registro Ante la Dirección General de Profesiones:", 4, 0, null);
            sql1 = "Select anio from periodos where id_periodo = ".concat(periodo);
            rs = con.Consultar(sql1);
            if (rs.next()) {
                anio = rs.getInt(1);
            }
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            
            while (rs.next()) {
                sqlcuatri = "Select cuatri from eficaciain5 where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_nivel="+rs.getString("id_nivel")));
                 rscuatri = con.Consultar(sqlcuatri);
                int numC = 1;
                for (int i = 1; i <= 2; i++) {
                    int sumaIngre = 0, sumaET = 0, sumaIngre1 = 0, sumaET1 = 0;
//                    if (rs.getString(1).equals("3")) {
//                        i++;
//                    }
                     String cuatritx="";
                     if (rs.getString(1).equals("1") || rs.getString(1).equals("3")) {
                         sqlcuatri = "Select cuatri from eficaciain5 where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_nivel="+rs.getString("id_nivel")).concat(" and anio = " + i));
                        rscuatri = con.Consultar(sqlcuatri);
                            if(rscuatri.next()){
                              int cuatri= rscuatri.getInt("cuatri");
                               
                              if(cuatri==1){    cuatritx="Enero - Abril";   }
                              if(cuatri==2){    cuatritx="Mayo - Agosto";   }
                              if(cuatri==3){    cuatritx="Septiembre - Diciembre";   }
                            
                            }
                        }    
                    if (i == 1) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 5.1." + rs.getString("id_nivel"), 6, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Tasa de Egreso y Titulación de "+rs.getString("descripcion") + " (Año " + String.valueOf(anio)+") "+cuatritx, 7, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 5.1." + rs.getString("id_nivel"), 6, 1, eficaciaS);
                         if (rs.getString(1).equals("3")) {
                         obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Tasa de Egreso y Titulación de "+rs.getString("descripcion")+ " (Año " + String.valueOf(anio + 1)+") "+cuatritx, 7, 1, eficaciaS);
                        }else{
                             obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Tasa de Egreso y Titulación de "+rs.getString("descripcion")+ " (Año " + String.valueOf(anio + 1)+") "+cuatritx, 7, 1, eficaciaS);
                         }
                        
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos + 2, "titulo_Cua_arial9Ne", "NO.", 8, 1, eficaciaS);
                    for (int j = 1; j <= 2; j++) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 2, "titulo_Cua_arial9Ne", "CARRERA", 8, 1, eficaciaS);
                        sql1 = "Select mu.id_modelo, m.descripcion from modelos_universidad mu inner join modelos m on mu.id_modelo = m.id_modelo where mu.id_universidad = ".concat(universidad).concat(" and mu.id_periodo = ").concat(periodo).concat(" and mu.id_nivel = ").concat(rs.getString(1));
                        rs3 = con.Consultar(sql1);
                        while (rs3.next()) {
                            obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, 2 * contModelos, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "INGRESO", 10, 1, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs3.getString(2), 9, 1, eficaciaS);
                            if (j == 1) {
                                obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "EGRESO", 10, 1, eficaciaS);
                            } else {
                                obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TITULADOS", 10, 1, eficaciaS);
                            }
                            contModelos++;
                            contModelos2++;
                        }
                        //Encabezados de Resultados Derecha
                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total", 9, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, 2 * contModelos, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "INGRESO", 10, 1, eficaciaS);
                        
                        //Encabezados por cuadro/Nivel 
                        if (j == 1) {
                            obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos + 2, (3 * contModelos) - (contModelos - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, "titulo_Cua_arial9Ne", "TASA DE EGRESO", 9, 1, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, 2, (3 * contModelos) - (contModelos) + 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "EGRESADOS DE " + rs.getString("descripcion"), 8, 1, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "EGRESO", 10, 1, eficaciaS);
                        } else {
                            obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos + 2, (3 * contModelos) - (contModelos - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, "titulo_Cua_arial9Ne", "TASA DE TITULACIÓN", 9, 1, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, 2, (3 * contModelos) - (contModelos) + 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TITULADOS DE " + rs.getString("descripcion"), 8, 1, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TITULADOS", 10, 1, eficaciaS);
                        }
                        contModelos = 1;

                        sql1 = "select pu.id_pe, nombre_programa from programa_educativo pe inner join pe_universidad pu on pe.id_pe = pu.id_pe where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1));
                        rs2 = con.Consultar(sql1);
                        int numP = 1;

                        while (rs2.next()) {
                            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", String.valueOf(numP), 10 + numP, 0, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs2.getString("nombre_programa"), 10 + numP, 0, eficaciaS);
                            rs3.beforeFirst();
                            while (rs3.next()) {
                                if (j == 1) {
                                    sql1 = "select ingreso, egresados from eficaciain5 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and id_pe=").concat(rs2.getString(1)).concat(" and id_modelo=").concat(rs3.getString(1)).concat(" and anio=").concat(String.valueOf(i));
                                    rs4 = con.Consultar(sql1);
                                    if (rs4.next()) {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1, 2 * contModelos1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs4.getString("ingreso"), 10 + numP, 0, eficaciaS);
                                        obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos1) - (contModelos1 - 1), (3 * contModelos1) - (contModelos1 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs4.getString("egresados"), 10 + numP, 0, eficaciaS);
                                    } else {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "0", 10 + numP, 0, eficaciaS);
                                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "0", 10 + numP, 0, eficaciaS);
                                    }
                                } else {
                                    sql1 = "select ingreso,titulados from eficaciain5 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and id_pe=").concat(rs2.getString(1)).concat(" and id_modelo=").concat(rs3.getString(1)).concat(" and anio=").concat(String.valueOf(i));
                                    rs4 = con.Consultar(sql1);
                                    if (rs4.next()) {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1, 2 * contModelos1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs4.getString("ingreso"), 10 + numP, 0, eficaciaS);
                                        obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos1) - (contModelos1 - 1), (3 * contModelos1) - (contModelos1 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs4.getString("titulados"), 10 + numP, 0, eficaciaS);
                                    } else {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "0", 10 + numP, 0, eficaciaS);
                                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "0", 10 + numP, 0, eficaciaS);
                                    }
                                }
                                contModelos1++;
                            }
//SUMA DE HORIZONTALES
                            if (j == 1) {
                                sql1 = "SELECT sum(ingreso)as sumaI, sum(egresados) as sumaE FROM eficaciain5 WHERE id_universidad= ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and anio=").concat(String.valueOf(i)).concat(" and id_pe=").concat(rs2.getString(1));
                                rs5 = con.Consultar(sql1);
                                if (rs5.next()) {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1, 2 * contModelos1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumaI"), 10 + numP, 0, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos1) - (contModelos1 - 1), (3 * contModelos1) - (contModelos1 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumaE"), 10 + numP, 0, eficaciaS);
                                    sumaIngre += rs5.getInt("sumaI");
                                    sumaET += rs5.getInt("sumaE");
                                    if (rs5.getInt("sumaI") != 0) {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1 + 2, (3 * contModelos1) - (contModelos1 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs5.getDouble("sumaE") / rs5.getDouble("sumaI") * 100)), 10 + numP, 1, eficaciaS);
                                    } else {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1 + 2, (3 * contModelos1) - (contModelos1 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                    }
                                } else {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1, 2 * contModelos1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 0, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos1) - (contModelos1 - 1), (3 * contModelos1) - (contModelos1 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 0, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1 + 2, (3 * contModelos1) - (contModelos1 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                }
                            } else {
                                sql1 = "SELECT sum(ingreso)as sumaI, sum(titulados) as sumaT FROM eficaciain5 WHERE id_universidad= ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and anio=").concat(String.valueOf(i)).concat(" and id_pe=").concat(rs2.getString(1));
                                rs5 = con.Consultar(sql1);
                                if (rs5.next()) {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1, 2 * contModelos1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumaI"), 10 + numP, 0, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos1) - (contModelos1 - 1), (3 * contModelos1) - (contModelos1 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumaT"), 10 + numP, 0, eficaciaS);
                                    sumaIngre1 += rs5.getInt("sumaI");
                                    sumaET1 += rs5.getInt("sumaT");
                                    if (rs5.getInt("sumaI") != 0) {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1 + 2, (3 * contModelos1) - (contModelos1 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs5.getDouble("sumaT") / rs5.getDouble("sumaI") * 100)), 10 + numP, 1, eficaciaS);
                                    } else {
                                        obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1 + 2, (3 * contModelos1) - (contModelos1 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                    }
                                } else {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1, 2 * contModelos1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 0, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos1) - (contModelos1 - 1), (3 * contModelos1) - (contModelos1 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 0, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos1 + 2, (3 * contModelos1) - (contModelos1 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                }
                            }
                            contModelos1 = 1;
                            numP++;
                        }
                        obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL", 10 + numP, 1, eficaciaS);
                       
                        if (j == 1) {
                            obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos2, 2 * contModelos2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(sumaIngre), 10 + numP, 0, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos2) - (contModelos2 - 1), (3 * contModelos2) - (contModelos2 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(sumaET), 10 + numP, 0, eficaciaS);
                            if (sumaIngre != 0) {
                                obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos2 + 2, (3 * contModelos2) - (contModelos2 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(sumaET / (double) sumaIngre * 100)), 10 + numP, 1, eficaciaS);
                            } else {
                                obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos2 + 2, (3 * contModelos2) - (contModelos2 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                            }
                        } else {
                            obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos2, 2 * contModelos2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(sumaIngre1), 10 + numP, 0, eficaciaS);
                            obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos2) - (contModelos2 - 1), (3 * contModelos2) - (contModelos2 - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(sumaET1), 10 + numP, 0, eficaciaS);
                            if (sumaIngre1 != 0) {
                                obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos2 + 2, (3 * contModelos2) - (contModelos2 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(sumaET1 / (double) sumaIngre1 * 100)), 10 + numP, 1, eficaciaS);
                            } else {
                                obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos2 + 2, (3 * contModelos2) - (contModelos2 - 1) + 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                            }
                        }
                        contModelos2 = 1;
                        //SUMA DE VERTICALES
                        rs3.beforeFirst();
                        while (rs3.next()) {
                            if (j == 1) {
                                sql1 = "select sum(ingreso)as sumai, sum(egresados)as sumae from eficaciain5 where  id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and id_modelo=").concat(rs3.getString(1)).concat(" and anio=").concat(String.valueOf(i));
                                rs5 = con.Consultar(sql1);
                                if (rs5.next()) {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, 2 * contModelos, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumai"), 10 + numP, 1, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumae"), 10 + numP, 1, eficaciaS);
                                } else {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, 2 * contModelos, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                }
                            } else {
                                sql1 = "select sum(ingreso)as sumai, sum(titulados)as sumae from eficaciain5 where  id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and id_modelo=").concat(rs3.getString(1)).concat(" and anio=").concat(String.valueOf(i));
                                rs5 = con.Consultar(sql1);
                                if (rs5.next()) {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, 2 * contModelos, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumai"), 10 + numP, 1, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs5.getString("sumae"), 10 + numP, 1, eficaciaS);

                                } else {
                                    obj.espaciosEstilosVer(celdasEficaciaS, 2 * contModelos, 2 * contModelos, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                    obj.espaciosEstilosVer(celdasEficaciaS, (3 * contModelos) - (contModelos - 1), (3 * contModelos) - (contModelos - 1), posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10 + numP, 1, eficaciaS);
                                }
                            }
                            contModelos++;
                        }
                        contModelos = 1;
                        nuevaPos += numP + 3;
                    }
                    numC++;
                    nuevaPos += 4;
                }
                
               
                noC++;
                
                
                
            }
            //Cuadro 2
            int c2 = 1;
            rs.beforeFirst();
            while (rs.next()) {
                int anioUni = 1990;
                try {
                    sql1 = "Select min(anio_incorporacion) from pe_universidad pu inner join programa_educativo pe on pu.id_pe = pe.id_pe where pu.id_universidad = ".concat(universidad).concat(" and pe.id_nivel = ").concat(rs.getString(1));
                    rs2 = con.Consultar(sql1);
                    rs2.next();
                    anioUni = rs2.getInt(1);
                } catch (Exception exep) {
                    anioUni = 1990;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 5.2." + rs.getString("id_nivel"), 6, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Registro de  " + rs.getString("descripcion") + " ante la Dirección de Profesiones de la Secretaría de Educación Pública", 7, 1, eficaciaS);
                filasEficaciaS[posIniTi + 8 + nuevaPos].setHeightInPoints(39);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO.", 8, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "GENERACIÓN", 8, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "REGISTRO DE TITULADOS POR PARTE DE LA UNIVERSIDAD", 8, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "REGISTRO DE TITULADOS POR PARTE DE LA D.G.P.", 8, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "PORCENTAJE DE REGISTRO ANTE DGP", 8, 1, eficaciaS);
                int j = 1, sumaUni = 0, sumaDGP = 0;
                for (; anioUni <= anio ; anioUni++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(j), 8 + j, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", String.valueOf(anioUni + 1), 8 + j, 0, eficaciaS);
                    sql1 = "Select titulados_universidad, titulados_dgp from eficaciain5_1 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel = ").concat(rs.getString(1)).concat(" and anio = ").concat(String.valueOf(anioUni));
                    rs3 = con.Consultar(sql1);
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs3.getString("titulados_universidad"), 8 + j, 1, eficaciaS);
                        sumaUni += rs3.getInt("titulados_universidad");
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs3.getString("titulados_dgp"), 8 + j, 1, eficaciaS);
                        sumaDGP += rs3.getInt("titulados_dgp");
                        if (rs3.getInt("titulados_universidad") != 0) {
                            obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", String.valueOf(obj.getDecimal(rs3.getInt("titulados_dgp") / rs3.getDouble("titulados_universidad") * 100)), 8 + j, 1, eficaciaS);
                        } else {
                            obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "0", 8 + j, 1, eficaciaS);
                        }
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 8 + j, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "0", 8 + j, 1, eficaciaS);
                    }
                    j++;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL", 8 + j, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(sumaUni), 8 + j, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(sumaDGP), 8 + j, 1, eficaciaS);
                if (sumaUni != 0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(sumaDGP / (double) sumaUni * 100)), 8 + j, 1, eficaciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 6, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 8 + j, 1, eficaciaS);
                }
                nuevaPos += 5 + j;
                c2++;
            }
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 5-1" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 5-1" + e);
        } finally {
            con.Desconectar();
        }
//6
        try {
            noC = 1;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 6", 3, 0, null);
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            while (rs.next()) 
            {
                int anio = 0;
                sql1 = "select anio from periodos where id_periodo='".concat(periodo).concat("'");
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    anio = rs2.getInt(1);
                }
                //Titulos
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 6.1." + rs.getString("id_nivel"), 4, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Egresados de " + rs.getString("descripcion") + "  incorporados al mercado laboral y que trabajan en área afin", 5, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 6, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 6, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "3", 6, 1, eficaciaS);
                filasEficaciaS[posIniTi + 7 + nuevaPos].setHeightInPoints(48);
                //Cuerpo
                if (rs.getString(1).equals("1") || rs.getString(1).equals("2")) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + (anio) + "  trabajando a seis meses de egreso", 7, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total de egresados de " + (anio), 7, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados " + (anio) + " cuya actividad laboral es acorde a su formación académica por programa educativo", 7, 1, eficaciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + (anio+1) + "  trabajando a seis meses de egreso", 7, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total de egresados de " + (anio+1), 7, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados " + (anio+1) + " cuya actividad laboral es acorde a su formación académica por programa educativo", 7, 1, eficaciaS);
                }
//SQL Suma de egresados
                String sql3 = "select e_trabajando, afin from eficaciain6 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("' and id_nivel = '").concat(String.valueOf(rs.getInt(1))).concat("'");
                rs3 = con.Consultar(sql3);
                if (rs.getInt(1) == 1 || rs.getInt(1) == 2) {
                    sql1 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'and id_nivel = '").concat(String.valueOf(rs.getInt(1))).concat("' and anio=1");
                } else {
                    sql1 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'and id_nivel = '").concat(String.valueOf(rs.getInt(1))).concat("' and anio=2");
                }
                rs4 = con.Consultar(sql1);
                if (rs3.next()) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs3.getString("e_trabajando"), 8, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs3.getString("afin"), 8, 1, eficaciaS);
                    rs4.next();
                    if (rs4.getString(1) != null) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs4.getString(1), 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs3.getInt("e_trabajando") / rs4.getDouble(1) * 100)), 11, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(rs3.getInt("afin") / rs3.getDouble("e_trabajando") * 100)), 11, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Se Requieren Datos del Indicador 5", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 1, eficaciaS);
                    }
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 8, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 8, 1, eficaciaS);
                    rs4.next();
                    if (rs4.getString(1) != null) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs4.getString(1), 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 11, 1, eficaciaS);
                    }
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EML", 9, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "1/2", 10, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "ETA", 9, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "3/1", 10, 1, eficaciaS);
                nuevaPos += 10;
                noC++;
            }
            //Indicador 6.2
            noC= 1;
            rs.beforeFirst();
            while(rs.next())
            {
                //Encabezado
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 6.2."+ rs.getString("id_nivel"), 4, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Egresados de  " + rs.getString("descripcion") + "  incorporados al mercado laboral y que trabajan en área afín por sector", 5, 1, eficaciaS);
                //Titulo con numeros
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "3", 6, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "4", 6, 0, eficaciaS);
                //Titulos de columnas
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en el Sector primario", 7, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en el Sector secundario", 7, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en el Sector terciario", 7, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Total", 7, 0, eficaciaS);
                
                String sqleficacia = "select esprimario,essecundario, esterciario from eficaciain6 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("' and id_nivel = '").concat(String.valueOf(rs.getInt(1))).concat("'");
                ResultSet rseficacia = con.Consultar(sqleficacia);
                if (rseficacia.next()) 
                {
                    double totalefi2 = rseficacia.getInt(1) + rseficacia.getInt(2) + rseficacia.getInt(3);
                    double emlsp = (rseficacia.getInt(1) / totalefi2) * 100; 
                    double emlss = (rseficacia.getInt(2) / totalefi2) * 100;
                    double emlst = (rseficacia.getInt(3) / totalefi2) * 100; 
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rseficacia.getString(1), 8, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rseficacia.getString(2), 8, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rseficacia.getString(3), 8, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(Math.round(totalefi2)), 8, 0, eficaciaS);
                    
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(formatea.format(emlsp)), 10, 0, eficaciaS);                    
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(formatea.format(emlss)), 10, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(formatea.format(emlst)), 10, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(formatea.format(emlsp + emlss + emlst)), 10, 0, eficaciaS);
                    
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "EMLSP", 9, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "EMLSS", 9, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "EMLST", 9, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "", 9, 0, eficaciaS);
                noC++;
                nuevaPos += 3;
            }
            
//              //COMENTARIOS
            nuevaPos += 6;
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 6);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
            nuevaPos+=4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 6" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 6" + e);
        } finally {
            con.Desconectar();
        }
//7
        try {
            noC = 1;
            int numPre = 1;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 7", 3, 0, null);
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            while (rs.next()&& (rs.getString(1).equals("1") ||rs.getString(1).equals("2") ||rs.getString(1).equals("3") )) {
                //Titulos
                int anio = 0;
                sql1 = "select anio from periodos where id_periodo='".concat(periodo).concat("'");
                rs3 = con.Consultar(sql1);
                if (rs3.next()) {
                    anio = rs3.getInt(1);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Egresados de " + rs.getString("descripcion") + " Satisfechos :", 4, 0, null);
                if (rs.getString(1).equals("3") ) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Nota: Información correspondiente al ciclo escolar " + anio + "-" + (anio + 1) + ", para " + rs.getString("descripcion") + " egreso en " + (anio+1) + ".", 5, 0, null);
                      } else {
                     obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Nota: Información correspondiente al ciclo escolar " + anio + "-" + (anio + 1) + ", para " + rs.getString("descripcion") + " egreso en " + (anio) + ".", 5, 0, null);
             
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Grado de Satisfacción de los egresados de la universidad", 8, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Concentrado de datos en escala de 5 y 10", 9, 1, eficaciaS);
//encabezados cuadro
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 7.1." + rs.getString("id_nivel"), 10, 1, eficaciaS);
                filasEficaciaS[posIniTi + 11 + nuevaPos].setHeightInPoints(23);
                filasEficaciaS[posIniTi + 12 + nuevaPos].setHeightInPoints(56);
                filasEficaciaS[posIniTi + 13 + nuevaPos].setHeightInPoints(72);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "NO.", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "DESCRIPCIÓN", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "FRECUENCIAS", 11, 1, eficaciaS);
                for (int i = 1; i <= 5; i++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, i + 1, i + 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(6 - i), 12, 0, eficaciaS);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO APLICA (NA)", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO ESPECIFICADO (NE)", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL            H", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL I", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL           J", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL             K, BASE 5", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL             K, BASE 10", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "A", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "B", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "C", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "D", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "E", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "F", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "G", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Σ A a G", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Σ A a E", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "A * 5 +B*  4 +C * 3 + D * 2 + E* 1", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "J / I", 13, 0, eficaciaS);
                sql1 = "select id_pregunta, pregunta from encuesta_preguntas where id_encuesta=(select id_encuesta from encuestas where id_nivel=".concat(rs.getString(1)).concat(" and encuesta_preguntas.id_encuesta=encuestas.id_encuesta and indicador=7) and activo=1");
                ResultSet rs1 = con.Consultar(sql1);
                int total_a = 0, total_b = 0, total_c = 0, total_d = 0, total_e = 0, total_f = 0, total_g = 0, total_h = 0, total_h_tes=0;
                while (rs1.next()) {
                    filasEficaciaS[posIniTi + 13 + numPre + nuevaPos].setHeightInPoints(55);
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", String.valueOf(numPre), 13 + numPre, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs1.getNString("pregunta"), 13 + numPre, 0, eficaciaS);
                    sql1 = "select r_a, r_b, r_c, r_d, r_e, r_f, r_g from eficaciain7_10 where id_universidad = ".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pregunta=").concat(rs1.getString(1));
                    rs2 = con.Consultar(sql1);
                    if (rs2.next()) {
                        int totalh = 0, totali = 0, totalj = 0;
                        for (int i = 1; i <= 7; i++) {
                            if (i == 1) {
                                total_a += rs2.getInt(i);
                            }
                            if (i == 2) {
                                total_b += rs2.getInt(i);
                            }
                            if (i == 3) {
                                total_c += rs2.getInt(i);
                            }
                            if (i == 4) {
                                total_d += rs2.getInt(i);
                            }
                            if (i == 5) {
                                total_e += rs2.getInt(i);
                            }
                            if (i == 6) {
                                total_f += rs2.getInt(i);
                            }
                            if (i == 7) {
                                total_g += rs2.getInt(i);
                            }
                            obj.espaciosEstilosVer(celdasEficaciaS, i + 1, i + 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", String.valueOf(rs2.getInt(i)), 13 + numPre, 0, eficaciaS);
                            totalh += rs2.getInt(i);
                            if (i <= 5) {
                                totali += rs2.getInt(i);
                                totalj += rs2.getInt(i) * (6 - i);
                            }
                        }
                        obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totalh), 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totali), 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totalj), 13 + numPre, 0, eficaciaS);
                        double totk=obj.getDecimal(Double.valueOf(totalj) / Double.valueOf(totali));
                        obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totk), 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(obj.getDecimal(totk* 2)), 13 + numPre, 0, eficaciaS);
                        //Totales
                        total_h += totalh;
                        total_h_tes =  totalh;
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 13 + numPre, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);

                    }
                    numPre++;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "TOTAL", 13 + numPre, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_b), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_c), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_d), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_e), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_f), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_g), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_h), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a + total_b + total_c + total_d + total_e), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a * 5 + total_b * 4 + total_c * 3 + total_d * 2 + total_e * 1), 13 + numPre, 0, eficaciaS);
                double tot_K=obj.getDecimal(((double) total_a * 5 + (double) total_b * 4 + (double) total_c * 3 + (double) total_d * 2 + (double) total_e * 1) / ((double) total_a + (double) total_b + (double) total_c + (double) total_d + (double) total_e));
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf( tot_K ), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf( obj.getDecimal(tot_K * 2) ), 13 + numPre, 0, eficaciaS);
                
                //DISTRIBUCÍON PORCENTUAL 
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "DISTRIBUCIÓN PORCENTUAL", 14 + numPre, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "MS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "S", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "RS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "PS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NA", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NE", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "TES", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 14 + numPre, 0, eficaciaS);
                
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "", 15 + numPre, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_a / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_b / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_c / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_d / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_e / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_f / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_g / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                double tes=   ((double)(((double) (total_a + total_b)/ (numPre-1)))/total_h_tes)*100   ; 
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal(tes) + " % ") , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                // FIN DISTRIBUCÍON PORCENTUAL 
                noC++;
                nuevaPos += 14 + numPre;
                numPre = 1;
            }
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 7);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
            nuevaPos+=8;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 7" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 7" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 8                 
        try {
            noC = 1;
            int numPre = 1;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 8", 0, 0, null);
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Egresados que presentan Exámen", 1, 0, null);
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            while (rs.next()) {
                if (rs.getString(1).equals("3") || rs.getString(1).equals("1") || rs.getString(1).equals("2")) {
                    double columna1 = 0, columna2 = 0, columna3 = 0, columna4 = 0, columna5 = 0;
                    filasEficaciaS[posIniTi + 6 + nuevaPos].setHeightInPoints(91);
                    filasEficaciaS[posIniTi + 7 + nuevaPos].setHeightInPoints(20);
//                        //Titulos
                    int anio = 0;
                    sql1 = "select anio from periodos where id_periodo='".concat(periodo).concat("'");
                    rs2 = con.Consultar(sql1);
                    if (rs2.next()) {
                        anio = rs2.getInt(1);
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 8.1." + rs.getString("id_nivel"), 3, 1, eficaciaS);
                    for (int i = 1; i <= 5; i++) {
                        obj.espaciosEstilosVer(celdasEficaciaS, i, i, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(i), 5, 0, eficaciaS);
                    }
                    if (rs.getString(1).equals("1")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Egresados de " + rs.getString("descripcion") + " que Presentan EGETSU:", 4, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " que presentaron el EGETSU", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio), 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " con testimonio de desempeño sobresaliente del EGETSU", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " con testimonio de desempeño satisfactorio del EGETSU", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " sin testimonio del EGETSU", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGETA", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGETB", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGETC", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGETD", 8, 1, eficaciaS);
                    }
                    if (rs.getString(1).equals("2")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Egresados de " + rs.getString("descripcion") + " que presentan examen de egreso:", 4, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " que presentaron el examen de egreso", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio), 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " con puntaje alto", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " con puntaje medio", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio) + " con puntaje bajo", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EXAA", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EXAB", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EXAC", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EXAD", 8, 1, eficaciaS);
                    }
                    if (rs.getString(1).equals("3")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Egresados de " + rs.getString("descripcion") + " que presentan examen de EGEL:", 4, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio+1) + " que presentaron el examen de EGEL", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio+1), 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio+1) + " con testimonio de desempeño sobresaliente del EGEL", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio+1) + " con testimonio de desempeño satisfactorio del EGEL", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados en " + (anio+1) + " sin testimonio del EGEL", 6, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGELA", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGELB", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGELC", 8, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "EGELD", 8, 1, eficaciaS);
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "1/2", 9, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "3/1", 9, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "4/1", 9, 1, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "arial10_leyenda_res", "5/1", 9, 1, eficaciaS);
                    sql1 = "select sobresaliente,destacado,sin_testimonio,aplica from eficaciain8 where id_universidad=".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_nivel='").concat(rs.getString("id_nivel")).concat("'"));
                    rs3 = con.Consultar(sql1);
                     int aplica=-1;
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs3.getString("sobresaliente"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs3.getString("destacado"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs3.getString("sin_testimonio"), 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(rs3.getInt("sobresaliente") + rs3.getInt("destacado") + rs3.getInt("sin_testimonio")), 7, 0, eficaciaS);
                        columna3 = rs3.getInt("sobresaliente");
                        columna4 = rs3.getInt("destacado");
                        columna5 = rs3.getInt("sin_testimonio");
                        columna1 = columna3 + columna4 + columna5;
                         aplica=rs3.getInt("aplica");
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 7, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 7, 1, eficaciaS);
                    }
                    if (rs.getString(1).equals("1") || rs.getString(1).equals("2")) {
                        sql1 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'and id_nivel = '").concat(rs.getString(1)).concat("' and anio=1");
                    } else {
                        sql1 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'and id_nivel = '").concat(rs.getString(1)).concat("' and anio=2");
                    }
                    rs3 = con.Consultar(sql1);
                    if (rs3.next()) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs3.getString(1), 7, 0, eficaciaS);
                        columna2 = rs3.getInt(1);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Complete Indicador 5", 7, 0, eficaciaS);
                    }
//Totales
                    if (columna2 > 0 || columna2 > 0.0) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((columna1 / columna2) * 100)), 10, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10, 1, eficaciaS);
                    }
                    if (columna1 > 0 || columna1 > 0.0) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((columna3 / columna1) * 100)), 10, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((columna4 / columna1) * 100)), 10, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((columna5 / columna1) * 100)), 10, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 10, 1, eficaciaS);
                    }
                    if(aplica !=-1 && aplica==0){
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO SE APLICO", 12, 1, eficaciaS);
                    }
                    noC++;
                    nuevaPos += 11;
                }
            }
//              //COMENTARIOS
            
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 8);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
            
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 8" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 8" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 9
        try {
            noC = 1;
            nuevaPos+=7;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 9", 0, 0, null);
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Egresados en estudios superiores a seis meses de su egreso", 1, 0, null);
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") and pe.id_nivel not in (4,6,7)  order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            while (rs.next()) {
                
                double columna1 = 0, columna2 = 0, res2 = 0, res3 = 0;
                filasEficaciaS[posIniTi + 6 + nuevaPos].setHeightInPoints(63);
                filasEficaciaS[posIniTi + 7 + nuevaPos].setHeightInPoints(26);
//                        //Titulos
                int anio = 0;
                sql1 = "select anio from periodos where id_periodo='".concat(periodo).concat("'");
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    anio = rs2.getInt(1);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 9.1." + rs.getString("id_nivel"), 3, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 9.1." +  rs.getString("id_nivel"), 3, 1, eficaciaS);
                filasEficaciaS[posIniTi + 4 + nuevaPos].setHeightInPoints(26);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Egresados de " + rs.getString("descripcion") + " en estudios superiores a seis meses de su egreso", 4, 1, eficaciaS);
//Cuadro 1
                for (int i = 1; i <= 2; i++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, i, i, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(i), 5, 0, eficaciaS);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "SUP1", 5, 0, eficaciaS);
                if (rs.getString(1).equals("1") || rs.getString(1).equals("2")) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " en " + (anio) + "  que continuan estudios superiores", 6, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " en " + (anio), 6, 0, eficaciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " en " + (anio) + "  que continuan estudios superiores", 6, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " en " + (anio), 6, 0, eficaciaS);
                }
                 if (rs.getString(1).equals("3") ) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " en " + (anio) + "  que continuan estudios de posgrado", 6, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " en " + (anio), 6, 0, eficaciaS);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1/2*100", 6, 0, eficaciaS);
                //VALORES BASE
                sql1 = "select egre_continuan,egre_continuan_ut,egre_continuan_ut_con from eficaciain9 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("' and id_nivel=").concat(rs.getString(1));
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs2.getString("egre_continuan"), 7, 0, eficaciaS);
                    if (rs.getString(1).equals("1")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs2.getString("egre_continuan_ut"), 13, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs2.getString("egre_continuan_ut_con"), 13, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(rs2.getInt("egre_continuan_ut_con") + rs2.getInt("egre_continuan_ut")), 13, 0, eficaciaS);

                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs2.getString("egre_continuan_ut"), 13, 0, eficaciaS);

                    }
                    columna1 = rs2.getInt("egre_continuan");
                    res2 = rs2.getInt("egre_continuan_ut");
                    res3 = rs2.getInt("egre_continuan_ut_con");
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 7, 0, eficaciaS);
                    if (rs.getString(1).equals("1")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 13, 1, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 13, 0, eficaciaS);

                    }
                }
                if (rs.getString(1).equals("1") || rs.getString(1).equals("2")) {
                    sql1 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'and id_nivel = '").concat(rs.getString(1)).concat("' and anio=1");
                } else {
                    sql1 = "select sum(egresados) as suma from eficaciain5 where id_universidad='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'and id_nivel = '").concat(rs.getString(1)).concat("' and anio=2");
                }
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs2.getString(1), 7, 0, eficaciaS);
                    columna2 = rs2.getInt(1);
                    if (rs.getString(1).equals("1")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs2.getString(1), 13, 0, eficaciaS);

                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", rs2.getString(1), 13, 0, eficaciaS);
                    }
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Complete Indicador 5", 7, 0, eficaciaS);
                    if (rs.getString(1).equals("1")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Complete Indicador 5", 13, 0, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Complete Indicador 5", 13, 0, eficaciaS);
                    }
                }
                //total
                if (columna2 > 0 || columna2 > 0.0) {
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((columna1 / columna2) * 100)), 7, 0, eficaciaS);
                    if (rs.getString(1).equals("1")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((res2 / columna2) * 100)), 13, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((res3 / columna2) * 100)), 13, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal(((res3 + res2) / columna2) * 100)), 13, 0, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((res2 / columna2) * 100)), 13, 0, eficaciaS);
                    }
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 7, 0, eficaciaS);
                    if (rs.getString(1).equals("1")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 13, 0, eficaciaS);
                    }
                }
//Cuadro 2
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 9.2." + rs.getString("id_nivel"), 10, 1, eficaciaS);
                if (rs.getString(1).equals("1")) {
                    for (int i = 1; i <= 4; i++) {
                        obj.espaciosEstilosVer(celdasEficaciaS, i, i, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(i), 11, 0, eficaciaS);
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "SUP2", 11, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "SUP3", 11, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "SUP4", 11, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de TSU de " + (anio) + " que continuan estudios superiores de licencia profesional en la UT", 12, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de TSU de " + (anio) + " que continuan estudios superiores de licenciatura en la UT", 12, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de TSU de " + (anio) + " que continuan estudios superiores de licencia profesional y licenciatura en la UT", 12, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de TSU de " + (anio), 12, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1/4*100", 12, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2/4*100", 12, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "3/4*100", 12, 0, eficaciaS);
                } else {
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 11, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 11, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "SUP1", 11, 0, eficaciaS);
                    if (rs.getString(1).equals("2")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " de " + (anio +1) + " que continuan estudios superiores en la UT", 12, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " de " + (anio +1), 12, 0, eficaciaS);
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " de " + (anio+1) + " que continuan estudios superiores en la UT", 12, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " de " + (anio+1), 12, 0, eficaciaS);
                    }
                       if (rs.getString(1).equals("3")) {
                        obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " de " + (anio +1) + " que continuan estudios de posgrado en la UT", 12, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Egresados de " + rs.getString("descripcion") + " de " + (anio +1), 12, 0, eficaciaS);
                    }
                    obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1/2*100", 12, 0, eficaciaS);

                }
                noC += 2;
                nuevaPos += 11;
            }
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 9);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
            
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 9" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 9" + e);
        } finally {
            con.Desconectar();
        }
//Indicador 10
        try {
            noC = 0;
            int numPre = 1;
            nuevaPos += 5;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 10", 3, 0, null);
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            while (rs.next() && (rs.getString("id_nivel").equals("1") ||rs.getString("id_nivel").equals("2") ||rs.getString("id_nivel").equals("3") )) {
                //Titulos
                int anio = 0;
                sql1 = "select anio from periodos where id_periodo='".concat(periodo).concat("'");
                rs3 = con.Consultar(sql1);
                if (rs3.next()) {
                    anio = rs3.getInt("anio");
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Tasa de empleadores satisfechos de " + rs.getString("descripcion"), 4, 0, null);
                if (rs.getString("id_nivel").equals("3") ) {
                     obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Nota: Información correspondiente al ciclo escolar " + anio + "-" + (anio + 1) + ", para " + rs.getString("descripcion") + " egreso en " + (anio+1) + ".", 5, 0, null);
                    
                } else {
                   obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Nota: Información correspondiente al ciclo escolar " + anio + "-" + (anio + 1) + ", para " + rs.getString("descripcion") + " egreso en " + (anio ) + ".", 5, 0, null);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Grado de Satisfacción de los egresados del " + rs.getString("descripcion"), 8, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Concentrado de datos en escala de 5 y 10", 9, 1, eficaciaS);
//encabezados cuadro
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 10.1." + rs.getString("id_nivel"), 10, 1, eficaciaS);
                filasEficaciaS[posIniTi + 11 + nuevaPos].setHeightInPoints(23);
                filasEficaciaS[posIniTi + 12 + nuevaPos].setHeightInPoints(56);
                filasEficaciaS[posIniTi + 13 + nuevaPos].setHeightInPoints(72);
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "NO.", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "DESCRIPCIÓN", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "FRECUENCIAS", 11, 1, eficaciaS);
                for (int i = 1; i <= 5; i++) {
                    obj.espaciosEstilosVer(celdasEficaciaS, i + 1, i + 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(6 - i), 12, 0, eficaciaS);
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO APLICA (NA)", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO ESPECIFICADO (NE)", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL            H", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL I", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL           J", 12, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL             K, BASE 5", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL             K, BASE 10", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "A", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "B", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "C", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "D", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "E", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "F", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "G", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Σ A a G", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Σ A a E", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "A * 5 +B*  4 +C * 3 + D * 2 + E* 1", 13, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "J / I", 13, 0, eficaciaS);
                sql1 = "select id_pregunta, pregunta from encuesta_preguntas where id_encuesta=(select id_encuesta from encuestas where id_nivel=".concat(rs.getString(1)).concat(" and encuesta_preguntas.id_encuesta=encuestas.id_encuesta and indicador=10) and activo=1");
                ResultSet rs1 = con.Consultar(sql1);
                int total_a = 0, total_b = 0, total_c = 0, total_d = 0, total_e = 0, total_f = 0, total_g = 0, total_h = 0, total_h_tes=0;
                while (rs1.next()) {
                    filasEficaciaS[posIniTi + 13 + numPre + nuevaPos].setHeightInPoints(55);
                    obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", String.valueOf(numPre), 13 + numPre, 0, eficaciaS);
                    obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs1.getNString("pregunta"), 13 + numPre, 0, eficaciaS);
                    sql1 = "select r_a, r_b, r_c, r_d, r_e, r_f, r_g from eficaciain7_10 where id_universidad = ".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pregunta=").concat(rs1.getString(1));
                    rs2 = con.Consultar(sql1);
                    if (rs2.next()) {
                        int totalh = 0, totali = 0, totalj = 0;
                        for (int i = 1; i <= 7; i++) {
                            if (i == 1) {
                                total_a += rs2.getInt(i);
                            }
                            if (i == 2) {
                                total_b += rs2.getInt(i);
                            }
                            if (i == 3) {
                                total_c += rs2.getInt(i);
                            }
                            if (i == 4) {
                                total_d += rs2.getInt(i);
                            }
                            if (i == 5) {
                                total_e += rs2.getInt(i);
                            }
                            if (i == 6) {
                                total_f += rs2.getInt(i);
                            }
                            if (i == 7) {
                                total_g += rs2.getInt(i);
                            }
                            obj.espaciosEstilosVer(celdasEficaciaS, i + 1, i + 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", String.valueOf(rs2.getInt(i)), 13 + numPre, 0, eficaciaS);
                            totalh += rs2.getInt(i);
                            if (i <= 5) {
                                totali += rs2.getInt(i);
                                totalj += rs2.getInt(i) * (6 - i);
                            }
                        }
                        obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totalh), 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totali), 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totalj), 13 + numPre, 0, eficaciaS);
                        
                        double totk=obj.getDecimal( (Double.valueOf(totalj) / Double.valueOf(totali)) ) ;
                        obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totk ), 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf( obj.getDecimal(totk * 2) ), 13 + numPre, 0, eficaciaS);
                        //Totales
                        total_h += totalh;
                        total_h_tes=totalh;
                    } else {
                        obj.espaciosEstilosVer(celdasEficaciaS, 2, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 13 + numPre, 1, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                        obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 13 + numPre, 0, eficaciaS);
                    }
                    numPre++;
                }
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "TOTAL", 13 + numPre, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_b), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_c), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_d), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_e), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_f), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_g), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_h), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a + total_b + total_c + total_d + total_e), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a * 5 + total_b * 4 + total_c * 3 + total_d * 2 + total_e * 1), 13 + numPre, 0, eficaciaS);
                double tot_K=Math.rint((((double) total_a * 5 + (double) total_b * 4 + (double) total_c * 3 + (double) total_d * 2 + (double) total_e * 1) / ((double) total_a + (double) total_b + (double) total_c + (double) total_d + (double) total_e))* 10 ) /10;
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf( tot_K ), 13 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(Math.rint( (tot_K*2) * 10 ) /10), 13 + numPre, 0, eficaciaS);
                //DISTRIBUCION PORCENTUAL
                  obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "DISTRIBUCIÓN PORCENTUAL", 14 + numPre, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "MS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "S", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "RS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "PS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NS", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NA", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NE", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "TES", 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 14 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 14 + numPre, 0, eficaciaS);
                
                obj.espaciosEstilosVer(celdasEficaciaS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "", 15 + numPre, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_a / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_b / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_c / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_d / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_e / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_f / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_g / (double) total_h ) * 100)+" %" ) , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                double tes=   ((double)(((double) (total_a + total_b)/ (numPre-1)))/total_h_tes)*100   ; 
                obj.espaciosEstilosVer(celdasEficaciaS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal(tes) + " % ") , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 15 + numPre, 0, eficaciaS);
                
                
                noC++;

            nuevaPos += 14 + numPre;
                numPre = 1;
            }
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 10);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 10" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 10" + e);
        } finally {
            con.Desconectar();
        }
        
        //Indicador 11               
        try {
            nuevaPos+=8;
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 11", 0, 0, null);
            obj.espaciosEstilosVer(celdasEficaciaS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador_subr", "Presupuesto ejercido", 1, 0, null);
          
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 11.1 Presupuesto Original", 8, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 9, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 9, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "3", 9, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "1/3*100", 9, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "2/3*100", 9, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Original Asignado Federal", 10, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Original Asignado Estatal", 10, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Original Asignado Federal y Estatal", 10, 0, eficaciaS);
            
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 11.2 Presupuesto Total Autorizado Federal con Ampliaciones, Reducciones y Ejercido", 13, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 14, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 14, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "3", 14, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi +  nuevaPos, "titulo_Cua_arial9Ne", "4", 14, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi +  nuevaPos, "titulo_Cua_arial9Ne", "5", 14, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Original Asignado  Federal", 15, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Ampliaciones", 15, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Reducciones", 15, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Total Autorizado Federal", 15, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Ejercido Federal", 15, 0, eficaciaS);
            
                     
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 11.3 Presupuesto Total Autorizado Estatal con Ampliaciones, Reducciones y Ejercido", 18, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 19, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 19, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "3", 19, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi +  nuevaPos, "titulo_Cua_arial9Ne", "4", 19, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi +  nuevaPos, "titulo_Cua_arial9Ne", "5", 19, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto autorizado estatal", 20, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Ampliaciones", 20, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Reducciones", 20, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Total Autorizado  Estatal", 20, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Ejercido Estatal", 20, 0, eficaciaS);
            
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 11.4 Presupuesto Total Autorizado Federal y Estatal, Ampliaciones, Reducciones y Ejercido", 23, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 24, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 24, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "3", 24, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi +  nuevaPos, "titulo_Cua_arial9Ne", "4", 24, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi +  nuevaPos, "titulo_Cua_arial9Ne", "5", 24, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Original Asignado Federal y Estatal", 25, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Ampliaciones Totales Federales y Estatales", 25, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Reducciones Totales Federales y Estatales", 25, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Total Autorizado  Federal y Estatal", 25, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Total Ejercido Federal y Estatal", 25, 0, eficaciaS);
              
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 11.5 Presupuesto Ejercido", 28, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "1", 29, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "2", 29, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "1/2*100", 29, 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Total Ejercido Federal y Estatal", 30, 0, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Presupuesto Total Autorizado Federal y Estatal", 30, 0, eficaciaS);
            
            sql1 = "select * from eficaciain11 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("'");
            rs = con.Consultar(sql1);
            if (rs.next()) {
                
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(6), 11, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(7), 11, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(5), 11, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", ""+obj.getDecimal(rs.getDouble(6)/rs.getDouble(5)*100), 11, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", ""+obj.getDecimal(rs.getDouble(7)/rs.getDouble(5)*100), 11, 0, eficaciaS);
                
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(8), 16, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(9), 16, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(10), 16, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", String.valueOf( (rs.getDouble(8)+rs.getDouble(9))-rs.getDouble(10) ), 16, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(11), 16, 0, eficaciaS);
                
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(12), 21, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(13), 21, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(14), 21, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", String.valueOf( (rs.getDouble(12)+rs.getDouble(13))-rs.getDouble(14) ), 21, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(15), 21, 0, eficaciaS);
                
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(16), 26, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(17), 26, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(18), 26, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", String.valueOf( (rs.getDouble(16)+rs.getDouble(17))-rs.getDouble(18) ), 26, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(19), 26, 0, eficaciaS);
                
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(3), 31, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", rs.getString(4), 31, 0, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(obj.getDecimal((rs.getDouble(3) / rs.getDouble(4)) * 100)), 31, 0, eficaciaS);
                
            } else {
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 11, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 16, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 21, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 26, 1, eficaciaS);
                
                obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "resultado_base", "No Concluido", 6, 1, eficaciaS);
                obj.espaciosEstilosVer(celdasEficaciaS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "0", 6, 0, eficaciaS);
            }
            nuevaPos += 30;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 11);
            obj.espaciosEstilosVer(celdasEficaciaS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 + Nivel_Uni.size(), 1, eficaciaS);
            obj.espaciosEstilosVer(celdasEficaciaS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 + Nivel_Uni.size(), 1, eficaciaS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 11" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 11" + e);
        } finally {
            con.Desconectar();
        }
        return obj;
    }
}