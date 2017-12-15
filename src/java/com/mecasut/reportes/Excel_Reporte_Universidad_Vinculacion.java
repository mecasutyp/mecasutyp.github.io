package com.mecasut.reportes;
/*
 * Actualización 2016: Salvador Zamora Arias
 */
import com.mecasut.conexion.ConexionMySQL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Excel_Reporte_Universidad_Vinculacion {

    public Excel_Reporte_Universidad crearVinculacion(String universidad, String periodo, Excel_Reporte_Universidad obj) throws FileNotFoundException, IOException {
        int columnas = 14;
        int celdas = 200;
        String sql1 = "";
        ResultSet rs = null;
        ResultSet rs2 = null;
        ConexionMySQL con = new ConexionMySQL();
        int nuevaPos = 0, noC = 1;
        int posIniTi = 0;
        HSSFSheet vinculacionS = obj.regresarLibro().createSheet("VINCULACION");
        HSSFRow[] filasvinculacionS = new HSSFRow[celdas];
        HSSFCell[][] celdasVinculacionS = new HSSFCell[columnas][celdas];
        for (int l = 0; l < columnas; l++) {
            for (int j = 0; j < celdas - 1; j++) {
                filasvinculacionS[j] = vinculacionS.createRow(j);
                celdasVinculacionS[l][j] = filasvinculacionS[j].createCell(l);
            }
        }
        String titulo_cat = "titulo_cat";
        String titulo_indicador = "titulo_indicador";
        String titulo_indicador_subr = "titulo_indicador_subr";
        //Estilos de Indicador
        String titulo_Cua_arial10MayNe = "titulo_Cua_arial10MayNe";
        String titulo_Cua_arial9Ne = "titulo_Cua_arial9Ne";
        String ajustarContenido = "ajustarContenido";
        vinculacionS.setColumnWidth(0, 37 * 95);
        vinculacionS.setColumnWidth(1, 37 * 158);
        vinculacionS.setColumnWidth(2, 37 * 139);
        vinculacionS.setColumnWidth(3, 37 * 99);
        vinculacionS.setColumnWidth(4, 37 * 127);
        vinculacionS.setColumnWidth(5, 37 * 99);
        vinculacionS.setColumnWidth(6, 37 * 87);
        vinculacionS.setColumnWidth(7, 37 * 87);
        vinculacionS.setColumnWidth(8, 37 * 87);
        vinculacionS.setColumnWidth(9, 37 * 60);
        vinculacionS.setColumnWidth(10, 37 * 60);
        vinculacionS.setColumnWidth(11, 37 * 60);
        vinculacionS.setColumnWidth(12, 37 * 60);
        vinculacionS.setColumnWidth(13, 37 * 60);
        obj.espaciosEstilosVer(celdasVinculacionS, 0, 10, posIniTi, nuevaPos, titulo_cat, "IV. VINCULACIÓN", 0, 1, vinculacionS);
        posIniTi += 2;
//26
        try {
            //Cuadro 26.1.1 Nacionales
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 26", 0, 0, null);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Total de organismos vinculados", 1, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 26.1.1 Total de Organismos Nacionales Vinculados", 3, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 5].setHeightInPoints(23);
            filasvinculacionS[posIniTi + nuevaPos + 6].setHeightInPoints(23);
            filasvinculacionS[posIniTi + nuevaPos + 7].setHeightInPoints(23);
            filasvinculacionS[posIniTi + nuevaPos + 11].setHeightInPoints(52);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 4, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "a) Organismos nacionales vinculados acumulados al ciclo escolar", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "b) Convenios firmados acumulados al ciclo escolar", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "c) Convenios firmados acumulados con instituciones de educación superior nacionales en el ciclo escolar", 7, 1, vinculacionS);
            
            
            //Cuadro 26.1.2 Nacionales
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 26.1.2 Total de Organismos Nacionales Vinculados Acumulados por Sector", 9, 1, vinculacionS);
            //obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Organismos vinculados por tipo de organismo", 10, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de Organismos vinculados acumulados", 11, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Públicos", 11, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Privados", 11, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Sociales", 11, 1, vinculacionS);
            sql1 = "SELECT conv_acum,conv_acum_sup,publicos,privados,sociales FROM vinculacionin26 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            int total_Acu = 0;
            double pu = 0, pr = 0, so = 0, subtotal2 = 0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("conv_acum"), 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("conv_acum_sup"), 7, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("publicos"), 12, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("privados"), 12, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("sociales"), 12, 1, vinculacionS);
                total_Acu = rs.getInt("publicos") + rs.getInt("privados") + rs.getInt("sociales");
                if (total_Acu != 0) {
                    pu = obj.getDecimal(rs.getDouble("publicos") / (double) total_Acu * 100);
                    pr = obj.getDecimal(rs.getDouble("privados") / (double) total_Acu * 100);
                    so = obj.getDecimal(rs.getDouble("sociales") / (double) total_Acu * 100);
                    subtotal2 = pu + pr + so;
                }
                  obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_Acu), 12, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_Acu), 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf( obj.getDecimal(subtotal2)+" %" ), 13, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pu)+" %", 13, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pr)+" %", 13, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(so)+" %", 13, 0, vinculacionS);
            } else {
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, ajustarContenido, "No Concluido", 5, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 5, 1, vinculacionS);
                
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 12, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 7, 1, vinculacionS);
                
            }
          
            nuevaPos += 13;
            
            //Cuadro 26.1.1 Internacionales
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 26.2.1 Total de Organismos Internacionales Vinculados", 3, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 5].setHeightInPoints(23);
            filasvinculacionS[posIniTi + nuevaPos + 6].setHeightInPoints(23);
            filasvinculacionS[posIniTi + nuevaPos + 7].setHeightInPoints(23);
            filasvinculacionS[posIniTi + nuevaPos + 11].setHeightInPoints(52);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 4, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "a) Organismos internacionales vinculados acumulados al ciclo escolar", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "b) Convenios firmados acumulados al ciclo escolar", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "c) Convenios firmados acumulados con instituciones de educación superior en el ciclo escolar", 7, 1, vinculacionS);
            
            
            //Cuadro 26.1.2 Internacionales
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 26.2.2 Total de Organismos Internacionales Vinculados Acumulados por Sector", 9, 1, vinculacionS);
            //obj.espaciosEstilosVer(celdasVinculacionS, 0, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Organismos vinculados por tipo de organismo", 10, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de Organismos vinculados acumulados", 11, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Públicos", 11, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Privados", 11, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Sociales", 11, 1, vinculacionS);
            sql1 = "SELECT conv_acum,conv_acum_sup,publicos,privados,sociales FROM vinculacionin26_internacional WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            total_Acu = 0;
            pu = 0; pr = 0; so = 0; subtotal2 = 0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("conv_acum"), 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("conv_acum_sup"), 7, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("publicos"), 12, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("privados"), 12, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("sociales"), 12, 1, vinculacionS);
                total_Acu = rs.getInt("publicos") + rs.getInt("privados") + rs.getInt("sociales");
                if (total_Acu != 0) {
                    pu = obj.getDecimal(rs.getDouble("publicos") / (double) total_Acu * 100);
                    pr = obj.getDecimal(rs.getDouble("privados") / (double) total_Acu * 100);
                    so = obj.getDecimal(rs.getDouble("sociales") / (double) total_Acu * 100);
                    subtotal2 = pu + pr + so;
                }
                 obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_Acu), 12, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_Acu), 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(subtotal2+ " %"), 13, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pu)+" %", 13, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(pr)+" %", 13, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(so)+" %", 13, 0, vinculacionS);
            } else {
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, ajustarContenido, "No Concluido", 5, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 5, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 7, 1, vinculacionS);
                
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 12, 1, vinculacionS);
            }
           
            nuevaPos += 14;
            
            //Movilidad Nacional
            Double porcentajed = 0.0 , porcentajea = 0.0;
            sql1 = "SELECT alumnos,docentes FROM vinculacionin26 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 26.3.1", 1, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Movilidad Nacional", 2, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos con Movilidad", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Profesores con Movilidad", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 3, 1, vinculacionS);
            if(rs.next())
            {
                
                porcentajea =  (rs.getDouble(1) / (rs.getDouble(1) + rs.getDouble(2))*100);
                porcentajed =  (rs.getDouble(2) / (rs.getDouble(1) + rs.getDouble(2))*100);
                if(Double.isNaN(porcentajea)){
                    porcentajea=0.0;
                }
                if(Double.isNaN(porcentajed)){
                    porcentajed=0.0;
                }
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString(1), 4, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString(2), 4, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido,String.valueOf(rs.getInt(1) + rs.getInt(2)), 4, 1, vinculacionS);
            }else
            {
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 4, 1, vinculacionS);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(porcentajea)+ " %"), 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(porcentajed)+ " %"), 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(porcentajea + porcentajed)+ " %"), 5, 1, vinculacionS);
            nuevaPos += 6;
            
            //Movilidad Internacional
            porcentajed = 0.0; 
            porcentajea = 0.0;
            sql1 = "SELECT alumnos,docentes FROM vinculacionin26_internacional WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 26.3.2", 1, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Movilidad Internacional", 2, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Alumnos con Movilidad", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Profesores con Movilidad", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 3, 1, vinculacionS);
            if(rs.next())
            {
                porcentajea =  (rs.getDouble(1) / (rs.getDouble(1) + rs.getDouble(2))*100);
                porcentajed =  (rs.getDouble(2) / (rs.getDouble(1) + rs.getDouble(2))*100);
                if(Double.isNaN(porcentajea)){
                    porcentajea=0.0;
                }
                if(Double.isNaN(porcentajed)){
                    porcentajed=0.0;
                }
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString(1), 4, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString(2), 4, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(rs.getInt(1) + rs.getInt(2)), 4, 1, vinculacionS);
            }else
            {
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No concluido", 4, 1, vinculacionS);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(porcentajea)+ " %"), 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(porcentajed)+ " %"), 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(porcentajea + porcentajed)+ " %"), 5, 1, vinculacionS);
            nuevaPos += 7;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 26);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, vinculacionS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 26" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 26" + e);
        } finally {
            con.Desconectar();
        }
//27
        try {
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 27", 0, 0, null);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Distribución de los servicios y estudios tecnológicos prestados e ingresos por este rubro", 1, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 27.1 Ingresos Propios Captados", 3, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 5].setHeightInPoints(81);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Presupuesto total autorizado Federal y Estatal", 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Recursos captados por Servicios y Estudios Tecnológicos", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Recursos captados por Colegiaturas y Servicios Escolares", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Recursos captados por otros servicios proporcionados por la universidad (diferentes a los anteriores)", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total de recursos captados por Ingresos Propios", 5, 1, vinculacionS);
            double autorizado = 0;
            sql1 = "Select autorizado from eficaciain11 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
            rs = con.Consultar(sql1);
            if (rs.next()) {
                autorizado = rs.getDouble(1);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(autorizado), 6, 1, vinculacionS);
            sql1 = "SELECT rec_servicios,rec_colegiaturas,rec_diferentes FROM vinculacionin27 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            double total_Rec = 0;
            double ipc1 = 0, ipc2 = 0, ipc3 = 0, ipc4 = 0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("rec_servicios"), 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("rec_colegiaturas"), 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("rec_diferentes"), 6, 1, vinculacionS);
                total_Rec = rs.getDouble("rec_servicios") + rs.getDouble("rec_colegiaturas") + rs.getDouble("rec_diferentes");
                if (autorizado != 0) {
                    ipc1 = obj.getDecimal(rs.getDouble("rec_servicios") / (double) autorizado * 100);
                    ipc2 = obj.getDecimal(rs.getDouble("rec_colegiaturas") / (double) autorizado * 100);
                    ipc3 = obj.getDecimal(rs.getDouble("rec_diferentes") / (double) autorizado * 100);
                    ipc4 = obj.getDecimal(total_Rec / (double) autorizado * 100);
                }
            } else {
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6, 1, vinculacionS);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_Rec), 6, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "", 7, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 7].setHeightInPoints(16);
            filasvinculacionS[posIniTi + nuevaPos + 6].setHeightInPoints(16);
            filasvinculacionS[posIniTi + nuevaPos + 8].setHeightInPoints(16);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "IPC1", 7, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "IPC2", 7, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "IPC3", 7, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "IPC4", 7, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(ipc1), 8, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(ipc2), 8, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(ipc3), 8, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(ipc4), 8, 0, vinculacionS);
            nuevaPos += 9;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 27);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, vinculacionS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 27" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 27" + e);
        } finally {
            con.Desconectar();
        }
//28
        try {
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 28", 0, 0, null);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Distribución de los servicios y estudios tecnológicos prestados e ingresos por este rubro", 1, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 28.1", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Servicios y Estudios Tecnológicos Según Tipo y Sector", 4, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 5].setHeightInPoints(18);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "SERVICOS Y ESTUDIOS TECNOLÓGICOS", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "ORGANISMOS VINCULADOS", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PÚBLICOS", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PRIVADOS", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "SOCIALES", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 6, 1, vinculacionS);
            sql1 = "Select id_estudio, descripcion from estudios_tecnologicos";
            int est = 0, suma_Hor = 0, col1 = 0, col2 = 0, col3 = 0, suma_Total = 0;
            rs = con.Consultar(sql1);
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, rs.getString("descripcion"), 7 + est, 1, vinculacionS);
                sql1 = "Select org_publicos, org_privados, org_sociales from vinculacionin28 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_estudio=").concat(rs.getString(1));
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("org_publicos"), 7 + est, 1, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("org_privados"), 7 + est, 1, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("org_sociales"), 7 + est, 1, vinculacionS);
                    suma_Hor = rs2.getInt("org_publicos") + rs2.getInt("org_privados") + rs2.getInt("org_sociales");
                    col1 += rs2.getInt("org_publicos");
                    col2 += rs2.getInt("org_privados");
                    col3 += rs2.getInt("org_sociales");
                    suma_Total += suma_Hor;
                } else {
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 7 + est, 1, vinculacionS);
                }
                obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(suma_Hor), 7 + est, 0, vinculacionS);
                est++;
            }
            suma_Hor = 0;
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 7 + est, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(col1), 7 + est, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(col2), 7 + est, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(col3), 7 + est, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(suma_Total), 7 + est, 0, vinculacionS);
            nuevaPos += est + 7;
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 28.2", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Ingresos Propios por Servicios y Estudios Tecnológicos Según Tipo y Sector", 4, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 5].setHeightInPoints(18);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos + 1, titulo_Cua_arial9Ne, "SERVICOS Y ESTUDIOS TECNOLÓGICOS", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "ORGANISMOS VINCULADOS", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PÚBLICOS", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "PRIVADOS", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "SOCIALES", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 6, 1, vinculacionS);
            est = 0;
            suma_Hor = 0;
            col1 = 0;
            col2 = 0;
            col3 = 0;
            suma_Total = 0;
            rs.beforeFirst();
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, rs.getString("descripcion"), 7 + est, 1, vinculacionS);
                sql1 = "Select ing_publicos, ing_privados, ing_sociales from vinculacionin28 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_estudio=").concat(rs.getString(1));
                rs2 = con.Consultar(sql1);
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("ing_publicos"), 7 + est, 1, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("ing_privados"), 7 + est, 1, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("ing_sociales"), 7 + est, 1, vinculacionS);
                    suma_Hor = rs2.getInt("ing_publicos") + rs2.getInt("ing_privados") + rs2.getInt("ing_sociales");
                    col1 += rs2.getInt("ing_publicos");
                    col2 += rs2.getInt("ing_privados");
                    col3 += rs2.getInt("ing_sociales");
                    suma_Total += suma_Hor;
                } else {
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 7 + est, 1, vinculacionS);
                }
                obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(suma_Hor), 7 + est, 0, vinculacionS);
                est++;
            }
            suma_Hor = 0;
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 7 + est, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(col1), 7 + est, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(col2), 7 + est, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(col3), 7 + est, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(suma_Total), 7 + est, 0, vinculacionS);
            nuevaPos += est;
            nuevaPos += 8;
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 28);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, vinculacionS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 27" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 27" + e);
        } finally {
            con.Desconectar();
        }
//29
        try {
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 29", 0, 0, null);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Cursos de Educación Continua", 1, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 29.1", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Egresados que asisten a cursos según nivel de estudios por tipo", 4, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 5].setHeightInPoints(25);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TIPO DE ASISTENTE", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "CAPACITACIÓN", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "ACTUALIZACIÓN", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "DESARROLLO PROFESIONAL", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TOTAL", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "%", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Indicador", 5, 1, vinculacionS);
            sql1 = "Select distinct(pe.id_nivel), n.descripcion, n.nombre from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(") order by pe.id_nivel");
            rs = con.Consultar(sql1);
            int contNiv = 0, totalHor = 0, totVercap = 0, totVeract = 0, totVerdes = 0, totalTota = 0;
            double totalPor = 0;
            ArrayList totalHo = new ArrayList();
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "EGRESADOS DE " + rs.getString("n.descripcion"), 6 + contNiv, 1, vinculacionS);
                sql1 = "Select capacitacion, actualizacion, desarrollo_p from vinculacionin29 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1));
                rs2 = con.Consultar(sql1);
              
                obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "EEC-" + rs.getString("id_nivel"), 6 + contNiv, 1, vinculacionS);
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("capacitacion"), 6 + contNiv, 1, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("actualizacion"), 6 + contNiv, 1, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("desarrollo_p"), 6 + contNiv, 1, vinculacionS);
                    totalHor = rs2.getInt("capacitacion") + rs2.getInt("actualizacion") + rs2.getInt("desarrollo_p");
                    obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(totalHor), 6 + contNiv, 1, vinculacionS);
                    totVercap += rs2.getInt("capacitacion");
                    totalHo.add(totalHor);
                    totVeract += rs2.getInt("actualizacion");
                    totVerdes += rs2.getInt("desarrollo_p");
                    totalTota += totalHor;
                } else {
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6 + contNiv, 1, vinculacionS);
                    totalHo.add(0);
                }
                contNiv++;
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Otros tipos de Asistente", 6 + contNiv, 1, vinculacionS);
            sql1 = "Select capacitacion, actualizacion, desarrollo_p from vinculacionin29 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=-1");
            rs = con.Consultar(sql1);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("capacitacion"), 6 + contNiv, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("actualizacion"), 6 + contNiv, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("desarrollo_p"), 6 + contNiv, 1, vinculacionS);
                totVercap += rs.getInt("capacitacion");
                totVeract += rs.getInt("actualizacion");
                totVerdes += rs.getInt("desarrollo_p");
                totalHor = rs.getInt("capacitacion") + rs.getInt("actualizacion") + rs.getInt("desarrollo_p");
                totalTota += totalHor;
                obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, String.valueOf(totalHor), 6 + contNiv, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(totalHor / (double) totalTota * 100)), 6 + contNiv, 1, vinculacionS);
                totalPor += obj.getDecimal(totalHor / (double) totalTota * 100);
            } else {
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6 + contNiv, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "0", 6 + contNiv, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "0", 6 + contNiv, 1, vinculacionS);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "EEC-4", 6 + contNiv, 1, vinculacionS);
            //Total
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 7 + contNiv, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totVercap), 7 + contNiv, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totVeract), 7 + contNiv, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totVerdes), 7 + contNiv, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(totalTota), 7 + contNiv, 1, vinculacionS);
            if (!totalHo.isEmpty()) {
                for (int i = 0; i < totalHo.size(); i++) {
                    if (totalTota != 0) {
                        obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(Integer.valueOf(totalHo.get(i).toString()) / (double) totalTota * 100)), 6 + i, 1, vinculacionS);
                        totalPor += obj.getDecimal(Integer.valueOf(totalHo.get(i).toString()) / (double) totalTota * 100);
                    } else {
                        obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 6 + i, 1, vinculacionS);
                    }
                }
            } else {
                for (int i = 0; i < contNiv; i++) {
                    obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 6 + i, 1, vinculacionS);
                }
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(totalPor)), 7 + contNiv, 1, vinculacionS);
            nuevaPos += 7 + contNiv + totalHo.size();
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 29);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, vinculacionS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 29" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 29" + e);
        } finally {
            con.Desconectar();
        }

//30
        try {
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 30", 0, 0, null);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Cursos Demandados", 1, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 30.1", 3, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cursos en Educación Continua por Demanda Según Tipo", 4, 1, vinculacionS);
            filasvinculacionS[posIniTi + nuevaPos + 5].setHeightInPoints(30);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Cursos", 5, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Capacitación", 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Actualización", 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Desarrollo Profesional", 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "%", 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Indicador", 5, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Por Demanda", 6, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Por Oferta de la UT", 7, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Total", 8, 1, vinculacionS);
            sql1 = "SELECT d_capacitacion,d_actualizacion,d_desarrollo,o_capacitacion,o_actualizacion,o_desarrollo FROM vinculacionin30 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
            rs = con.Consultar(sql1);
            int total_de = 0, total_o = 0, capa = 0, actu = 0, desa = 0;
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("d_capacitacion"), 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("d_actualizacion"), 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("d_desarrollo"), 6, 1, vinculacionS);
                total_de = rs.getInt("d_capacitacion") + rs.getInt("d_actualizacion") + rs.getInt("d_desarrollo");
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("o_capacitacion"), 7, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("o_actualizacion"), 7, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs.getString("o_desarrollo"), 7, 1, vinculacionS);
                total_o = rs.getInt("o_capacitacion") + rs.getInt("o_actualizacion") + rs.getInt("o_desarrollo");
                capa = rs.getInt("o_capacitacion") + rs.getInt("d_capacitacion");
                actu = rs.getInt("d_actualizacion") + rs.getInt("o_actualizacion");
                desa = rs.getInt("d_desarrollo") + rs.getInt("o_desarrollo");
            } else {
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 6, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 7, 1, vinculacionS);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_de), 6, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total_o), 7, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(capa), 8, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(actu), 8, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(desa), 8, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(desa + actu + capa), 8, 0, vinculacionS);
            if ((desa + actu + capa) > 0) {
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(total_de / (double) (desa + actu + capa) * 100)), 6, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(total_o / (double) (desa + actu + capa) * 100)), 7, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(obj.getDecimal(total_o / (double) (desa + actu + capa) * 100) + obj.getDecimal(total_de / (double) (desa + actu + capa) * 100)), 8, 0, vinculacionS);
            } else {
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 6, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 7, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "0", 8, 0, vinculacionS);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TCD1", 6, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "TCD2", 7, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "", 8, 0, vinculacionS);
            nuevaPos += 8;
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 30);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, vinculacionS);
            nuevaPos += 4;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 30" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 30" + e);
        } finally {
            con.Desconectar();
        }
//31
        try {
            noC = 0;
            int numPre = 1;
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_indicador", "Indicador 31", 3, 0, null);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Tasa de los alumnos satisfechos en Educación Continua", 4, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Concentrado de los datos de las encuestas aplicadas y promedios", 7, 1, vinculacionS);
//encabezados cuadro
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial10MayNe", "Cuadro 31.1", 8, 1, vinculacionS);
            filasvinculacionS[posIniTi + 11 + nuevaPos].setHeightInPoints(23);
            filasvinculacionS[posIniTi + 12 + nuevaPos].setHeightInPoints(56);
            filasvinculacionS[posIniTi + 13 + nuevaPos].setHeightInPoints(72);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "NO.", 9, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "DESCRIPCIÓN", 9, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "FRECUENCIAS", 9, 1, vinculacionS);
            for (int i = 1; i <= 5; i++) {
                obj.espaciosEstilosVer(celdasVinculacionS, i + 1, i + 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", String.valueOf(6 - i), 10, 0, vinculacionS);
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO APLICA (NA)", 10, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "NO ESPECIFICADO (NE)", 10, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL            H", 10, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL I", 10, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL           J", 10, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 12, 12, posIniTi + nuevaPos, posIniTi + 1 + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL             K, BASE 5", 9, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 13, 13, posIniTi + nuevaPos, posIniTi + 2 + nuevaPos, "titulo_Cua_arial9Ne", "TOTAL             K, BASE 10", 9, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "A", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "B", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "C", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "D", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "E", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "F", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "G", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Σ A a G", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "Σ A a E", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "A * 5 +B*  4 +C * 3 + D * 2 + E* 1", 11, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "titulo_Cua_arial9Ne", "J / I", 11, 0, vinculacionS);
            sql1 = "select id_pregunta, pregunta from encuesta_preguntas where id_encuesta=10 and activo=1";
            ResultSet rs1 = con.Consultar(sql1);
            int total_a = 0, total_b = 0, total_c = 0, total_d = 0, total_e = 0, total_f = 0, total_g = 0, total_h = 0,total_h_tes =0;
            while (rs1.next()) {
                filasvinculacionS[posIniTi + 11 + numPre + nuevaPos].setHeightInPoints(55);
                obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", String.valueOf(numPre), 11 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", rs1.getNString("pregunta"), 11 + numPre, 0, vinculacionS);
                sql1 = "select r_a, r_b, r_c, r_d, r_e, r_f, r_g from vinculacionin31 where id_universidad = ".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pregunta=").concat(rs1.getString(1));
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
                        obj.espaciosEstilosVer(celdasVinculacionS, i + 1, i + 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", String.valueOf(rs2.getInt(i)), 11 + numPre, 0, vinculacionS);
                        totalh += rs2.getInt(i);
                        if (i <= 5) {
                            totali += rs2.getInt(i);
                            totalj += rs2.getInt(i) * (6 - i);
                        }
                    }
                    obj.espaciosEstilosVer(celdasVinculacionS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totalh), 11 + numPre, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totali), 11 + numPre, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totalj), 11 + numPre, 0, vinculacionS);
                    double totk=obj.getDecimal(Double.valueOf(totalj) / Double.valueOf(totali));
                    obj.espaciosEstilosVer(celdasVinculacionS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(totk), 11 + numPre, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(obj.getDecimal(totk* 2)), 11 + numPre, 0, vinculacionS);
                    //Totales
                    total_h += totalh;
                    total_h_tes =  totalh;
                } else {
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "ajustarContenido", "No Concluido", 11 + numPre, 1, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 11 + numPre, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 11 + numPre, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 11 + numPre, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 11 + numPre, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "0", 11 + numPre, 0, vinculacionS);
                }
                numPre++;
            }
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "TOTAL", 11 + numPre, 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_b), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_c), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_d), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_e), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_f), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_g), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_h), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a + total_b + total_c + total_d + total_e), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(total_a * 5 + total_b * 4 + total_c * 3 + total_d * 2 + total_e * 1), 11 + numPre, 0, vinculacionS);
            double tot_K = obj.getDecimal(((double) total_a * 5 + (double) total_b * 4 + (double) total_c * 3 + (double) total_d * 2 + (double) total_e * 1) / ((double) total_a + (double) total_b + (double) total_c + (double) total_d + (double) total_e));
            obj.espaciosEstilosVer(celdasVinculacionS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf(tot_K), 11 + numPre, 0, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", String.valueOf( (tot_K * 2) ), 11 + numPre, 0, vinculacionS);
             
            
            //DISTRIBUCÍON PORCENTUAL 
                obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "DISTRIBUCIÓN PORCENTUAL", 12 + numPre, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "MS", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "S", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "RS", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "PS", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NS", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NA", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "NE", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "TES", 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 12 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 12 + numPre, 0, vinculacionS);
                
                obj.espaciosEstilosVer(celdasVinculacionS, 0, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10", "", 13 + numPre, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_a / (double) total_h ) * 100)+" %" ) , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 3, 3, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_b / (double) total_h ) * 100)+" %" ) , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 4, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_c / (double) total_h ) * 100)+" %" ) , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 5, 5, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_d / (double) total_h ) * 100)+" %" ) , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 6, 6, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_e / (double) total_h ) * 100)+" %" ) , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 7, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_f / (double) total_h ) * 100)+" %" ) , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 8, 8, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal( ((double) total_g / (double) total_h ) * 100)+" %" ) , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 9, 9, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 13 + numPre, 0, vinculacionS);
                double tes=   ((double)(((double) (total_a + total_b)/ (numPre-1)))/total_h_tes)*100   ; 
                obj.espaciosEstilosVer(celdasVinculacionS, 10, 10, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10",String.valueOf(obj.getDecimal(tes) + " % ") , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 11, 11, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 12, 12, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 13 + numPre, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 13, 13, posIniTi + nuevaPos, posIniTi + nuevaPos, "res1decimallidobleAria10","" , 13 + numPre, 0, vinculacionS);
                // FIN DISTRIBUCÍON PORCENTUAL 
            
            noC++;
            nuevaPos += 14 + numPre;
            numPre = 1;
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 31);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, vinculacionS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 31" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 31" + e);
        } finally {
            con.Desconectar();
        }


//32
        try {
            noC = 1;
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 0, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador, "Indicador 32", 0, 0, null);
            obj.espaciosEstilosVer(celdasVinculacionS, 0, 4, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_indicador_subr, "Bolsa de trabajo:", 1, 1, vinculacionS);
            sql1 = "Select distinct(pe.id_nivel),nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
            rs = con.Consultar(sql1);
            while (rs.next()) {
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Cuadro 32." + rs.getString("id_nivel"), 3, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial10MayNe, "Egresados de  " + rs.getString("descripcion") + " colocados", 4, 1, vinculacionS);
                filasvinculacionS[posIniTi + nuevaPos + 4].setHeightInPoints(40);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "1", 5, 1, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "2", 5, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Egresados de " + rs.getString("descripcion") + " colocados en plazas contactadas por el área de bolsa de trabajo de la universidad", 6, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Plazas de " + rs.getString("descripcion") + " contactadas por el área de bolsa de trabajo de la universidad", 6, 0, vinculacionS);
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "BT" + noC + "=", 8, 1, vinculacionS);
                sql1 = "Select egresados_colocados, plazas from vinculacionin32 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel = ").concat(rs.getString(1));
                rs2 = con.Consultar(sql1);
                double total = 0;
                if (rs2.next()) {
                    obj.espaciosEstilosVer(celdasVinculacionS, 1, 1, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("egresados_colocados"), 7, 0, vinculacionS);
                    obj.espaciosEstilosVer(celdasVinculacionS, 2, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, rs2.getString("plazas"), 7, 0, vinculacionS);
                    if (rs2.getInt("plazas") != 0) {
                        total = obj.getDecimal(rs2.getInt("egresados_colocados") / rs2.getDouble("plazas") * 100);
                    }
                } else {
                    obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, "No Concluido", 7, 1, vinculacionS);
                }
                obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, String.valueOf(total), 9, 1, vinculacionS);
                nuevaPos += 9;
                noC++;
            }
            
//              //COMENTARIOS
            String com = Excel_Reporte_Universidad.obtenerComentario(universidad, periodo, 32);
            obj.espaciosEstilosVer(celdasVinculacionS, 1, 2, posIniTi + nuevaPos, posIniTi + nuevaPos, titulo_Cua_arial9Ne, "Comentario", 3 , 1, vinculacionS);
            obj.espaciosEstilosVer(celdasVinculacionS, 3, 7, posIniTi + nuevaPos, posIniTi + nuevaPos, ajustarContenido, com, 3 , 1, vinculacionS);
            nuevaPos += 6;
        } catch (SQLException ex) {
            System.err.println("Excel por Universidad SQL Error Indicador 32" + ex.getMessage());
        } catch (Exception e) {
            System.err.println("Excel por Universidad Error Indicador 32" + e);
        } finally {
            con.Desconectar();
        }
        return obj;

    }
}
