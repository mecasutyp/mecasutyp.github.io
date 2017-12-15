package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Universidad;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/*
 * @Daniel Ramírez Torres
 */

public class Excel_Reporte_Eficacia extends org.apache.struts.action.Action {
    /* forward name="success" path="" */

    private static final String SUCCESS = "success";
    private static final String LOGOUT = "logout";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String consulta = "";
        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }
        String universidad;
        String periodo;
        String anio = "";
        ConexionMySQL con = new ConexionMySQL();
        Universidad uni = new Universidad();
        periodo = request.getParameter("ipe").toString();
      
        consulta = "select anio from  periodos where id_periodo=".concat(periodo);
        ResultSet rsCon = con.Consultar(consulta);

        if (rsCon.next()) {
            anio = rsCon.getString("anio");
        }

        String ask = request.getParameter("ask");
        if (ask.equals("cat_Eficacia")) {
            //Especifico en donde se encuentra el archivo de excel en forma relativa
            String rutaRelativaWeb = "/files/I_Eficacia.xls";
            //Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
            String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);
            //Cargo en archivo en un Stream
            InputStream inp = new FileInputStream(rutaAbsolutaEnDisco);
            //Creo el libro de excel a partir de un archivo para su modificación
            HSSFWorkbook wb = new HSSFWorkbook(inp);
            inp.close();
//EFICACIA   
            //Variables para los estIlos
            CellStyle hoja3_col_G, linea_simple, borde_doble = null, linea_simple_decimal, linea_simple_formula;
            String forum_ver1 = null;
            String forum_ver2 = null;
            String forum_ver3 = null;
            String forum_ver4 = null;
            String forum_ver5 = null;
            String forum_ver6 = null;
            String forum_ver7 = null;
            String forum_ver8 = null;
//INICIA HOJA 1         
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            HSSFSheet eficacia = wb.getSheetAt(0);
            //Creo dos arreglos que van a contener las filas y las celdas
            HSSFRow[] filasEficacia = new HSSFRow[200];
            HSSFCell[][] celdasEficacia = new HSSFCell[5][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            borde_doble = celdasEficacia[2][198].getCellStyle();
            consulta = "Select id_universidad, nombre_universidad from universidades where activo = 1 and id_universidad>0";
            rsCon = con.Consultar(consulta);
            ArrayList ids = new ArrayList();//almacena los ids de las universidades
            ArrayList univs = new ArrayList();//almacena los nombres de las universidades
            while (rsCon.next()) {
                ids.add(rsCon.getString(1));
                univs.add(rsCon.getString(2));
            }
            /**/
            int cont = 1;
            //LISTA UNIVERSIDADES
            //Se extrae el estilo de la tabla existente y se crea un objeto con ese estilo para aplicarlo en las otras celdas
            CellStyle doble_linea = celdasEficacia[0][4].getCellStyle();
            CellStyle izq_linea = celdasEficacia[2][4].getCellStyle();
            CellStyle cen_linea = celdasEficacia[3][4].getCellStyle();
            CellStyle der_linea = celdasEficacia[4][4].getCellStyle();
            //Titulo del libro seguido del año del MECASUT
            celdasEficacia[0][0].setCellValue("1.A ALUMNOS DE NUEVO INGRESO zamora prueba CON EXANI II EN RELACIÓN A LOS SUSTENTANTES " + anio);
            //while (rsCon.next()) {
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][3 + cont].setCellValue(cont);
                celdasEficacia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficacia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficacia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficacia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficacia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficacia[4][3 + cont].setCellStyle(der_linea);
                consulta = "select egresados_bachillerato, nuevo_ingreso, c1, c2, c3,aplica from eficaciain1 where id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                ResultSet rs1 = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs1.next()) {
                    celdasEficacia[3][3 + cont].setCellValue(rs1.getInt(1));
                    celdasEficacia[2][3 + cont].setCellValue(rs1.getInt(3) + rs1.getInt(4) + rs1.getInt(5));
                }
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficacia.setForceFormulaRecalculation(true);
            celdasEficacia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficacia[2][cont + 3].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficacia[3][cont + 3].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
//INCICIA HOJA 2
            eficacia = null;
            filasEficacia = null;
            celdasEficacia = null;

            cont = 1;
            eficacia = wb.getSheetAt(1);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[5][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            cont = 1;
            //LISTA UNIVERSIDADES
            //Se extrae el estilo de la tabla existente y se crea un objeto con ese estilo para aplicarlo en las otras celdas
            //Titulo del libro seguido del año del MECASUT
            celdasEficacia[0][0].setCellValue("1.B ALUMNOS DE NUEVO INGRESO CON EXANI II EN RELACIÓN AL TOTAL DE ALUMNOS DE NUEVO INGRESO " + anio);
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][3 + cont].setCellValue(cont);
                celdasEficacia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficacia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficacia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficacia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficacia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficacia[4][3 + cont].setCellStyle(der_linea);
                consulta = "select c1, c2, c3,aplica from eficaciain1 where id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                ResultSet rs1 = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs1.next()) {
                    celdasEficacia[2][3 + cont].setCellValue(rs1.getInt(1) + rs1.getInt(2) + rs1.getInt(3));
                }
                consulta = "select nuevo_ingreso from datos_universidad where id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                ResultSet rs2 = con.Consultar(consulta);
                while (rs2.next()) {
                    celdasEficacia[3][3 + cont].setCellValue(rs2.getInt(1));
                }
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficacia.setForceFormulaRecalculation(true);
            celdasEficacia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficacia[2][cont + 3].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficacia[3][cont + 3].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
//INICIA HOJA 3            
            eficacia = null;
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficacia = null;
            celdasEficacia = null;
            eficacia = wb.getSheetAt(2);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[10][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            hoja3_col_G = celdasEficacia[6][5].getCellStyle();
            //LISTA UNIVERSIDADES
            //Se extrae el estilo de la tabla existente y se crea un objeto con ese estilo para aplicarlo en las otras celdas
            //Titulo del libro seguido del año del MECASUT
            celdasEficacia[0][0].setCellValue("1C. ALUMNOS DE NUEVO INGRESO CON EXANI II CON CALIFICACIONES SEGÚN PUNTAJE, " + anio);
            cont = 1;
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(doble_linea);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficacia[1][4 + cont].setCellStyle(doble_linea);
                celdasEficacia[2][4 + cont].setCellStyle(izq_linea);
                celdasEficacia[3][4 + cont].setCellStyle(cen_linea);
                celdasEficacia[4][4 + cont].setCellStyle(der_linea);
                consulta = "select c1, c2, c3,aplica from eficaciain1 where id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                ResultSet rs1 = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs1.next()) {
                    celdasEficacia[2][4 + cont].setCellValue(rs1.getInt(1));
                    celdasEficacia[3][4 + cont].setCellValue(rs1.getInt(2));
                    celdasEficacia[4][4 + cont].setCellValue(rs1.getInt(3));
                }
                //total F de Excel
                celdasEficacia[5][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficacia[5][cont + 4].setCellFormula("SUM(C" + (cont + 5) + ":E" + (cont + 5) + ")");
                celdasEficacia[5][cont + 4].setCellStyle(doble_linea);
                // G Excel          
                celdasEficacia[6][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficacia[6][cont + 4].setCellFormula("IF(F" + (cont + 5) + ">0 ,C" + (cont + 5) + "/F" + (cont + 5) + "*100,0)");
                celdasEficacia[6][cont + 4].setCellStyle(hoja3_col_G);
                //H
                celdasEficacia[7][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficacia[7][cont + 4].setCellFormula("IF(F" + (cont + 5) + ">0 ,D" + (cont + 5) + "/F" + (cont + 5) + "*100,0)");
                celdasEficacia[7][cont + 4].setCellStyle(hoja3_col_G);
                //I 
                celdasEficacia[8][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficacia[8][cont + 4].setCellFormula("IF(F" + (cont + 5) + ">0 ,E" + (cont + 5) + "/F" + (cont + 5) + "*100,0)");
                celdasEficacia[8][cont + 4].setCellStyle(hoja3_col_G);
                //total J
                celdasEficacia[9][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficacia[9][cont + 4].setCellFormula("SUM(G" + (cont + 5) + ":I" + (cont + 5) + ")");
                celdasEficacia[9][cont + 4].setCellStyle(hoja3_col_G);
                cont++;

            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            eficacia.setForceFormulaRecalculation(true);
            celdasEficacia[2][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[2][cont + 4].setCellFormula("SUM(C6" + ":C" + (cont + 4) + ")");
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[3][cont + 4].setCellFormula("SUM(D6" + ":D" + (cont + 4) + ")");
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[4][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[4][cont + 4].setCellFormula("SUM(E6" + ":E" + (cont + 4) + ")");
            celdasEficacia[4][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellFormula("SUM(F6" + ":F" + (cont + 4) + ")");
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");

//Inicia Cuadro 4 
            eficacia = null;
            filasEficacia = null;
            celdasEficacia = null;
            eficacia = wb.getSheetAt(3);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[6][200];
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            celdasEficacia[0][0].setCellValue("2. APROVECHAMIENTO ACADÉMICO POR CUATRIMESTRE, " + anio);
            celdasEficacia[0][1].setCellValue("DEL NIVEL 5B (VERSIÓN 2004)");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            int cont_sep = 0, cont_ene = 0, cont_may = 0;
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            String formu = celdasEficacia[5][5].getCellFormula();
            forum_ver1 = celdasEficacia[2][198].getCellFormula();
            forum_ver2 = celdasEficacia[3][198].getCellFormula();
            forum_ver3 = celdasEficacia[4][198].getCellFormula();

            double columna1 = 0.0d, columna2 = 0.0d, columna3 = 0.0d;
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
                //Se obtienen los programas educativos version 2004
                consulta = "select id_pe,nombre_programa,version from programa_educativo where version='2004' and id_pe in (select id_pe from pe_universidad where id_nivel=1 and id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo).concat(")");
                ResultSet rs1 = con.Consultar(consulta);
                while (rs1.next()) {
                    //se obtienen los resultados de lo cuatrimestres
                    String consulta1 = " select sep, ene, may from eficaciain2 where id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo =".concat(periodo).concat(" and id_pe='".concat(rs1.getString("id_pe")).concat("'")));
                    ResultSet rs2 = con.Consultar(consulta1);
                    if (rs2.next()) {
                        if (rs2.getFloat(1) > 0) {
                            columna1 += rs2.getFloat(1);
                            cont_sep++;
                        }
                        if (rs2.getDouble(2) > 0) {
                            columna2 += rs2.getFloat(2);
                            cont_ene++;
                        }
                        if (rs2.getDouble(3) > 0) {
                            columna3 += rs2.getFloat(3);
                            cont_may++;
                        }
                        if (columna1 == 0.0 && cont_sep == 0) {
                            celdasEficacia[2][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[2][4 + cont].setCellValue(columna1 / cont_sep);
                        }
                        if (columna2 == 0.0 && cont_ene == 0) {
                            celdasEficacia[3][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[3][4 + cont].setCellValue(columna2 / cont_ene);
                        }
                        if (columna3 == 0.0 && cont_may == 0) {
                            celdasEficacia[4][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[4][4 + cont].setCellValue(columna3 / cont_may);
                        }
                    } else {
                        celdasEficacia[2][4 + cont].setCellValue(columna1 / cont_sep);
                        celdasEficacia[3][4 + cont].setCellValue(columna2 / cont_ene);
                        celdasEficacia[4][4 + cont].setCellValue(columna3 / cont_may);
                    }
                }
                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
                cont_sep = 0;
                cont_may = 0;
                cont_ene = 0;
                columna1 = 0.0;
                columna2 = 0.0;
                columna3 = 0.0;
                //F Excel          
                celdasEficacia[5][cont + 4].setCellFormula(formu.replaceAll("6", String.valueOf(cont + 5)));
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            //Se agregan las formulas y los bordes de las filas y columnas
            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[4][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[4][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;


//INICIA CUADRO 5
            eficacia = null;
            filasEficacia = null;
            celdasEficacia = null;
            eficacia = wb.getSheetAt(4);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[6][200];
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            celdasEficacia[0][0].setCellValue("2. APROVECHAMIENTO ACADÉMICO POR CUATRIMESTRE, " + anio);
            celdasEficacia[0][1].setCellValue("DEL NIVEL 5B CON COMPETENCIAS PROFESIONALES");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            cont_sep = 0;
            cont_ene = 0;
            cont_may = 0;
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            formu = celdasEficacia[5][5].getCellFormula();
            forum_ver1 = celdasEficacia[2][198].getCellFormula();
            forum_ver2 = celdasEficacia[3][198].getCellFormula();
            forum_ver3 = celdasEficacia[4][198].getCellFormula();
            columna1 = 0.0d;
            columna2 = 0.0d;
            columna3 = 0.0d;
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
                //Se obtienen los programas educativos version 2004
                consulta = "select id_pe,nombre_programa,version from programa_educativo where version!='2004' and id_pe in (select id_pe from pe_universidad where id_nivel=1 and id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo).concat(")");
                ResultSet rs1 = con.Consultar(consulta);
                while (rs1.next()) {
                    //se obtienen los resultados de lo cuatrimestres
                    String consulta1 = " select sep, ene, may from eficaciain2 where id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo =".concat(periodo).concat(" and id_pe='".concat(rs1.getString("id_pe")).concat("'")));
                    ResultSet rs2 = con.Consultar(consulta1);
                    if (rs2.next()) {
                        if (rs2.getFloat(1) > 0) {
                            columna1 += rs2.getFloat(1);
                            cont_sep++;
                        }
                        if (rs2.getDouble(2) > 0) {
                            columna2 += rs2.getFloat(2);
                            cont_ene++;
                        }
                        if (rs2.getDouble(3) > 0) {
                            columna3 += rs2.getFloat(3);
                            cont_may++;
                        }
                        if (columna1 == 0.0 && cont_sep == 0) {
                            celdasEficacia[2][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[2][4 + cont].setCellValue(columna1 / cont_sep);
                        }
                        if (columna2 == 0.0 && cont_ene == 0) {
                            celdasEficacia[3][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[3][4 + cont].setCellValue(columna2 / cont_ene);
                        }
                        if (columna3 == 0.0 && cont_may == 0) {
                            celdasEficacia[4][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[4][4 + cont].setCellValue(columna3 / cont_may);
                        }
                    } else {
                        celdasEficacia[2][4 + cont].setCellValue(0);
                        celdasEficacia[3][4 + cont].setCellValue(0);
                        celdasEficacia[4][4 + cont].setCellValue(0);
                    }
                }
                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
                cont_sep = 0;
                cont_may = 0;
                cont_ene = 0;
                columna1 = 0.0;
                columna2 = 0.0;
                columna3 = 0.0;
                //F Excel          
                celdasEficacia[5][cont + 4].setCellFormula(formu.replaceAll("6", String.valueOf(cont + 5)));
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            //Se agregan las formulas y los bordes de las filas y columnas
            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[4][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[4][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;

//Inicia cuadro 6           
            eficacia = null;
            filasEficacia = null;
            celdasEficacia = null;
            eficacia = wb.getSheetAt(5);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[6][200];
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            celdasEficacia[0][0].setCellValue("2. APROVECHAMIENTO ACADÉMICO POR CUATRIMESTRE, " + anio);
            celdasEficacia[0][1].setCellValue("DEL NIVEL 5A CON COMPETENCIAS PROFESIONALES");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            cont_sep = 0;
            cont_ene = 0;
            cont_may = 0;
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            formu = celdasEficacia[5][5].getCellFormula();
            forum_ver1 = celdasEficacia[2][198].getCellFormula();
            forum_ver2 = celdasEficacia[3][198].getCellFormula();
            forum_ver3 = celdasEficacia[4][198].getCellFormula();
            columna1 = 0.0d;
            columna2 = 0.0d;
            columna3 = 0.0d;
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
                //Se obtienen los programas educativos version 2004
                consulta = "select id_pe,nombre_programa,version from programa_educativo where version!='2004' and id_pe in (select id_pe from pe_universidad where id_nivel=3 and id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo).concat(")");
                ResultSet rs1 = con.Consultar(consulta);
                while (rs1.next()) {
                    //se obtienen los resultados de lo cuatrimestres
                    String consulta1 = " select sep, ene, may from eficaciain2 where id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo =".concat(periodo).concat(" and id_pe='".concat(rs1.getString("id_pe")).concat("'")));
                    ResultSet rs2 = con.Consultar(consulta1);
                    if (rs2.next()) {
                        if (rs2.getFloat(1) > 0) {
                            columna1 += rs2.getFloat(1);
                            cont_sep++;
                        }
                        if (rs2.getDouble(2) > 0) {
                            columna2 += rs2.getFloat(2);
                            cont_ene++;
                        }
                        if (rs2.getDouble(3) > 0) {
                            columna3 += rs2.getFloat(3);
                            cont_may++;
                        }
                        if (columna1 == 0.0 && cont_sep == 0) {
                            celdasEficacia[2][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[2][4 + cont].setCellValue(columna1 / cont_sep);
                        }
                        if (columna2 == 0.0 && cont_ene == 0) {
                            celdasEficacia[3][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[3][4 + cont].setCellValue(columna2 / cont_ene);
                        }
                        if (columna3 == 0.0 && cont_may == 0) {
                            celdasEficacia[4][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[4][4 + cont].setCellValue(columna3 / cont_may);
                        }
                    } else {
                        celdasEficacia[2][4 + cont].setCellValue(0);
                        celdasEficacia[3][4 + cont].setCellValue(0);
                        celdasEficacia[4][4 + cont].setCellValue(0);
                    }
                }
                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
                cont_sep = 0;
                cont_may = 0;
                cont_ene = 0;
                columna1 = 0.0;
                columna2 = 0.0;
                columna3 = 0.0;
                //F Excel          
                celdasEficacia[5][cont + 4].setCellFormula(formu.replaceAll("6", String.valueOf(cont + 5)));
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            //Se agregan las formulas y los bordes de las filas y columnas
            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[4][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[4][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;

//Inicia cuadro 7           
            eficacia = null;
            filasEficacia = null;
            celdasEficacia = null;
            eficacia = wb.getSheetAt(6);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[6][200];
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            celdasEficacia[0][0].setCellValue("2. APROVECHAMIENTO ACADÉMICO POR CUATRIMESTRE, " + anio);
            celdasEficacia[0][1].setCellValue("DEL NIVEL 5B3 LICENCIA PROFESIONAL");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            cont_sep = 0;
            cont_ene = 0;
            cont_may = 0;
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            formu = celdasEficacia[5][5].getCellFormula();
            forum_ver1 = celdasEficacia[2][198].getCellFormula();
            forum_ver2 = celdasEficacia[3][198].getCellFormula();
            forum_ver3 = celdasEficacia[4][198].getCellFormula();
            columna1 = 0.0d;
            columna2 = 0.0d;
            columna3 = 0.0d;
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
                //Se obtienen los programas educativos version 2004
                consulta = "select id_pe,nombre_programa,version from programa_educativo where version!='2004' and id_pe in (select id_pe from pe_universidad where id_nivel=2 and id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo).concat(")");
                ResultSet rs1 = con.Consultar(consulta);
                while (rs1.next()) {
                    //se obtienen los resultados de lo cuatrimestres
                    String consulta1 = " select sep, ene, may from eficaciain2 where id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo =".concat(periodo).concat(" and id_pe='".concat(rs1.getString("id_pe")).concat("'")));
                    ResultSet rs2 = con.Consultar(consulta1);
                    if (rs2.next()) {
                        if (rs2.getFloat(1) > 0) {
                            columna1 += rs2.getFloat(1);
                            cont_sep++;
                        }
                        if (rs2.getDouble(2) > 0) {
                            columna2 += rs2.getFloat(2);
                            cont_ene++;
                        }
                        if (rs2.getDouble(3) > 0) {
                            columna3 += rs2.getFloat(3);
                            cont_may++;
                        }
                        if (columna1 == 0.0 && cont_sep == 0) {
                            celdasEficacia[2][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[2][4 + cont].setCellValue(columna1 / cont_sep);
                        }
                        if (columna2 == 0.0 && cont_ene == 0) {
                            celdasEficacia[3][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[3][4 + cont].setCellValue(columna2 / cont_ene);
                        }
                        if (columna3 == 0.0 && cont_may == 0) {
                            celdasEficacia[4][4 + cont].setCellValue(0);
                        } else {
                            celdasEficacia[4][4 + cont].setCellValue(columna3 / cont_may);
                        }
                    } else {
                        celdasEficacia[2][4 + cont].setCellValue(0);
                        celdasEficacia[3][4 + cont].setCellValue(0);
                        celdasEficacia[4][4 + cont].setCellValue(0);
                    }
                }
                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
                cont_sep = 0;
                cont_may = 0;
                cont_ene = 0;
                columna1 = 0.0;
                columna2 = 0.0;
                columna3 = 0.0;
                //F Excel          
                celdasEficacia[5][cont + 4].setCellFormula(formu.replaceAll("6", String.valueOf(cont + 5)));
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            //Se agregan las formulas y los bordes de las filas y columnas
            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[4][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[4][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;

//Cuadro 8             
//            eficacia = null;
//            filasEficacia = null;
//            celdasEficacia = null;
//            eficacia = wb.getSheetAt(7);
//            filasEficacia = new HSSFRow[200];
//            celdasEficacia = new HSSFCell[9][200];
//            for (int i = 0; i <= 8; i++) {
//                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
//                    if (i == 0) {
//                        filasEficacia[j] = eficacia.getRow(j);
//                    }
//                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
//                }
//            }
//            celdasEficacia[0][0].setCellValue("2. APROVECHAMIENTO ACADÉMICO POR CUATRIMESTRE, " + anio);
//            cont = 1;
//            borde_doble = celdasEficacia[2][198].getCellStyle();
//            linea_simple = celdasEficacia[0][5].getCellStyle();
//            //se mantiene el estilo de borde simple y con formato decimal
//            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
//            //Se obtienen las formulas pr columna para ser utilizadas despues
//            formu = celdasEficacia[5][5].getCellFormula();
//            forum_ver1 = celdasEficacia[2][198].getCellFormula();
//            forum_ver2 = celdasEficacia[3][198].getCellFormula();
//            forum_ver3 = celdasEficacia[4][198].getCellFormula();
//
//
//            String form_aux1 = celdasEficacia[2][5].getCellFormula();
//            System.out.println(form_aux1 + " form_aux1");
//            String form_aux2 = celdasEficacia[2][5].getCellFormula();
//            System.out.println(form_aux2 + " form_aux2");
//            String form_aux3 = celdasEficacia[2][5].getCellFormula();
//            System.out.println(form_aux3 + " form_aux3");
//
//            String form_aux1_con = celdasEficacia[6][5].getCellFormula();
//            System.out.println(form_aux1_con + " form_aux1_con");
//            String form_aux2_con = celdasEficacia[7][5].getCellFormula();
//            System.out.println(form_aux2_con + " form_aux2_con");
//            String form_aux3_con = celdasEficacia[8][5].getCellFormula();
//            System.out.println(form_aux3_con + " form_aux3_con");
//
//            for (int i = 0; i <= ids.size() - 1; i++) {
//                celdasEficacia[0][4 + cont].setCellValue(cont);
//                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
//                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
//                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
//
//                celdasEficacia[2][4 + cont].setCellFormula(form_aux1.replace("6", String.valueOf(5 + cont)));
//                celdasEficacia[3][4 + cont].setCellFormula(form_aux2.replace("6", String.valueOf(5 + cont)));
//                celdasEficacia[4][4 + cont].setCellFormula(form_aux3.replace("6", String.valueOf(5 + cont)));

//                celdasEficacia[6][4 + cont].setCellFormula(form_aux1_con.replace("6", String.valueOf(4 + cont)));
//                System.out.println("replace 1 " + form_aux1_con.replace("6", String.valueOf(4 + cont)));
//                celdasEficacia[7][4 + cont].setCellFormula(form_aux2_con.replace("6", String.valueOf(4 + cont)));
//                System.out.println("replace 2 " + form_aux2_con.replace("6", String.valueOf(4 + cont)));
//                celdasEficacia[8][4 + cont].setCellFormula(form_aux3_con.replace("6", String.valueOf(4 + cont)));
//                System.out.println("replace 3 " + form_aux3_con.replace("6", String.valueOf(4 + cont)));
//

            //Se obtienen los programas educativos version 2004
//                consulta = "select id_pe,nombre_programa,version from programa_educativo where version!='2004' and id_pe in (select id_pe from pe_universidad where id_nivel=2 and id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo).concat(")");
//                ResultSet rs1 = con.Consultar(consulta);
//                while (rs1.next()) {
//                    //se obtienen los resultados de lo cuatrimestres
//                    String consulta1 = " select sep, ene, may from eficaciain2 where id_universidad =".concat(ids.get(i).toString()).concat(" and id_periodo =".concat(periodo).concat(" and id_pe='".concat(rs1.getString("id_pe")).concat("'")));
//                    ResultSet rs2 = con.Consultar(consulta1);
//                    if (rs2.next()) {
//                        if (rs2.getFloat(1) > 0) {
//                            columna1 += rs2.getFloat(1);
//                            cont_sep++;
//                        }
//                        if (rs2.getDouble(2) > 0) {
//                            columna2 += rs2.getFloat(2);
//                            cont_ene++;
//                        }
//                        if (rs2.getDouble(3) > 0) {
//                            columna3 += rs2.getFloat(3);
//                            cont_may++;
//                        }
//                        if (columna1 == 0.0 && cont_sep == 0) {
//                            celdasEficacia[2][4 + cont].setCellValue(0);
//                        } else {
//                            celdasEficacia[2][4 + cont].setCellValue(columna1 / cont_sep);
//                        }
//                        if (columna2 == 0.0 && cont_ene == 0) {
//                            celdasEficacia[3][4 + cont].setCellValue(0);
//                        } else {
//                            celdasEficacia[3][4 + cont].setCellValue(columna2 / cont_ene);
//                        }
//                        if (columna3 == 0.0 && cont_may == 0) {
//                            celdasEficacia[4][4 + cont].setCellValue(0);
//                        } else {
//                            celdasEficacia[4][4 + cont].setCellValue(columna3 / cont_may);
//                        }
//                    } else {
//                        celdasEficacia[2][4 + cont].setCellValue(0);
//                        celdasEficacia[3][4 + cont].setCellValue(0);
//                        celdasEficacia[4][4 + cont].setCellValue(0);
//                    }
//                }
//
//
//
//
//                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
//                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
//                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
//                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
//                //F Excel          
//                celdasEficacia[5][cont + 4].setCellFormula(formu.replaceAll("6", String.valueOf(cont + 5)));
//                cont++;
//            }
//            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
//            //Se agregan las formulas y los bordes de las filas y columnas
//            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
//            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
//            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
//            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
//            celdasEficacia[4][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
//            celdasEficacia[4][cont + 4].setCellStyle(borde_doble);
//            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
//
//            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
//            eficacia.setForceFormulaRecalculation(true);
//            //Se vacian las variables 
//            forum_ver1 = null;
//            forum_ver2 = null;
//            forum_ver3 = null;
//
//
//

            //Inicia cuadro 9        
            eficacia = null;
            filasEficacia = null;
            celdasEficacia = null;
            eficacia = wb.getSheetAt(8);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[12][200];
            for (int i = 0; i <= 11; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            celdasEficacia[0][0].setCellValue("3. REPROBACIÓN DEFINITIVA POR UNA O MÁS ASIGNATURAS AL TÉRMINO DEL CUATRIMESTRE, " + anio);
            celdasEficacia[0][1].setCellValue("NIVEL 5B");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            linea_simple_formula = celdasEficacia[4][5].getCellStyle();
            String formu1 = celdasEficacia[4][5].getCellFormula();
            String formu2 = celdasEficacia[7][5].getCellFormula();
            String formu3 = celdasEficacia[10][5].getCellFormula();
            String formu4 = celdasEficacia[11][5].getCellFormula();
            forum_ver1 = celdasEficacia[2][198].getCellFormula();
            forum_ver2 = celdasEficacia[3][198].getCellFormula();
            forum_ver3 = celdasEficacia[5][198].getCellFormula();
            forum_ver4 = celdasEficacia[6][198].getCellFormula();
            forum_ver5 = celdasEficacia[8][198].getCellFormula();
            forum_ver6 = celdasEficacia[9][198].getCellFormula();

            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[4][4 + cont].setCellFormula(formu1.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[7][4 + cont].setCellFormula(formu2.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[7][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[10][4 + cont].setCellFormula(formu3.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[10][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[11][4 + cont].setCellFormula(formu4.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[11][4 + cont].setCellStyle(linea_simple_formula);
                consulta = "select rep_sep,mat_sep,rep_ene,mat_ene,rep_may,mat_may from eficaciain3 where id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=".concat(periodo).concat(" and id_nivel=1"));
                ResultSet rs1 = con.Consultar(consulta);
                while (rs1.next()) {
                    celdasEficacia[2][4 + cont].setCellValue(rs1.getInt(1));
                    celdasEficacia[3][4 + cont].setCellValue(rs1.getInt(2));
                    celdasEficacia[5][4 + cont].setCellValue(rs1.getInt(3));
                    celdasEficacia[6][4 + cont].setCellValue(rs1.getInt(4));
                    celdasEficacia[8][4 + cont].setCellValue(rs1.getInt(5));
                    celdasEficacia[9][4 + cont].setCellValue(rs1.getInt(6));
                }
                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[6][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[8][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[9][4 + cont].setCellStyle(linea_simple_decimal);
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            //Se agregan las formulas y los bordes de las filas y columnas
            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[6][cont + 4].setCellFormula(forum_ver4.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[6][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[8][cont + 4].setCellFormula(forum_ver5.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[8][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[9][cont + 4].setCellFormula(forum_ver6.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[9][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            linea_simple_decimal = null;
            borde_doble = null;
            linea_simple_formula = null;
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            forum_ver4 = null;
            forum_ver5 = null;
            forum_ver6 = null;
            //Inicia cuadro 9        
            eficacia = null;
            filasEficacia = null;
            celdasEficacia = null;

            //inicia cuadro 10                                 
            eficacia = wb.getSheetAt(9);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[12][200];
            for (int i = 0; i <= 11; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            celdasEficacia[0][0].setCellValue("3. REPROBACIÓN DEFINITIVA POR UNA O MÁS ASIGNATURAS AL TÉRMINO DEL CUATRIMESTRE, " + anio);
            celdasEficacia[0][1].setCellValue("NIVEL 5A");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            linea_simple_formula = celdasEficacia[4][5].getCellStyle();
            formu1 = celdasEficacia[4][5].getCellFormula();
            formu2 = celdasEficacia[7][5].getCellFormula();
            formu3 = celdasEficacia[10][5].getCellFormula();
            formu4 = celdasEficacia[11][5].getCellFormula();
            forum_ver1 = celdasEficacia[2][198].getCellFormula();
            forum_ver2 = celdasEficacia[3][198].getCellFormula();
            forum_ver3 = celdasEficacia[5][198].getCellFormula();
            forum_ver4 = celdasEficacia[6][198].getCellFormula();
            forum_ver5 = celdasEficacia[8][198].getCellFormula();
            forum_ver6 = celdasEficacia[9][198].getCellFormula();

            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[4][4 + cont].setCellFormula(formu1.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[7][4 + cont].setCellFormula(formu2.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[7][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[10][4 + cont].setCellFormula(formu3.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[10][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[11][4 + cont].setCellFormula(formu4.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[11][4 + cont].setCellStyle(linea_simple_formula);
                consulta = "select rep_sep,mat_sep,rep_ene,mat_ene,rep_may,mat_may from eficaciain3 where id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=".concat(periodo).concat(" and id_nivel=3"));
                ResultSet rs1 = con.Consultar(consulta);
                while (rs1.next()) {
                    celdasEficacia[2][4 + cont].setCellValue(rs1.getInt(1));
                    celdasEficacia[3][4 + cont].setCellValue(rs1.getInt(2));
                    celdasEficacia[5][4 + cont].setCellValue(rs1.getInt(3));
                    celdasEficacia[6][4 + cont].setCellValue(rs1.getInt(4));
                    celdasEficacia[8][4 + cont].setCellValue(rs1.getInt(5));
                    celdasEficacia[9][4 + cont].setCellValue(rs1.getInt(6));
                }
                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[6][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[8][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[9][4 + cont].setCellStyle(linea_simple_decimal);
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            //Se agregan las formulas y los bordes de las filas y columnas
            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[6][cont + 4].setCellFormula(forum_ver4.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[6][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[8][cont + 4].setCellFormula(forum_ver5.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[8][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[9][cont + 4].setCellFormula(forum_ver6.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[9][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            linea_simple_decimal = null;
            borde_doble = null;
            linea_simple_formula = null;
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            forum_ver4 = null;
            forum_ver5 = null;
            forum_ver6 = null;
//cuadro 11

            eficacia = wb.getSheetAt(10);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[12][200];
            for (int i = 0; i <= 11; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
            celdasEficacia[0][0].setCellValue("3. REPROBACIÓN DEFINITIVA POR UNA O MÁS ASIGNATURAS AL TÉRMINO DEL CUATRIMESTRE, " + anio);
            celdasEficacia[0][1].setCellValue("NIVEL 5B3 LICENCIA PROFESIONAL");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            linea_simple_formula = celdasEficacia[4][5].getCellStyle();
            formu1 = celdasEficacia[4][5].getCellFormula();
            formu2 = celdasEficacia[7][5].getCellFormula();
            formu3 = celdasEficacia[10][5].getCellFormula();
            formu4 = celdasEficacia[11][5].getCellFormula();
            forum_ver1 = celdasEficacia[2][198].getCellFormula();
            forum_ver2 = celdasEficacia[3][198].getCellFormula();
            forum_ver3 = celdasEficacia[5][198].getCellFormula();
            forum_ver4 = celdasEficacia[6][198].getCellFormula();
            forum_ver5 = celdasEficacia[8][198].getCellFormula();
            forum_ver6 = celdasEficacia[9][198].getCellFormula();

            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[4][4 + cont].setCellFormula(formu1.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[7][4 + cont].setCellFormula(formu2.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[7][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[10][4 + cont].setCellFormula(formu3.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[10][4 + cont].setCellStyle(linea_simple_formula);
                celdasEficacia[11][4 + cont].setCellFormula(formu4.replaceAll("6", String.valueOf(cont + 5)));
                celdasEficacia[11][4 + cont].setCellStyle(linea_simple_formula);
                consulta = "select rep_sep,mat_sep,rep_ene,mat_ene,rep_may,mat_may from eficaciain3 where id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=".concat(periodo).concat(" and id_nivel=2 "));
                ResultSet rs1 = con.Consultar(consulta);
                while (rs1.next()) {
                    celdasEficacia[2][4 + cont].setCellValue(rs1.getInt(1));
                    celdasEficacia[3][4 + cont].setCellValue(rs1.getInt(2));
                    celdasEficacia[5][4 + cont].setCellValue(rs1.getInt(3));
                    celdasEficacia[6][4 + cont].setCellValue(rs1.getInt(4));
                    celdasEficacia[8][4 + cont].setCellValue(rs1.getInt(5));
                    celdasEficacia[9][4 + cont].setCellValue(rs1.getInt(6));
                }
                celdasEficacia[2][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[3][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[4][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[5][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[6][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[8][4 + cont].setCellStyle(linea_simple_decimal);
                celdasEficacia[9][4 + cont].setCellStyle(linea_simple_decimal);
                cont++;
            }
            eficacia.shiftRows(198, eficacia.getLastRowNum(), -1 * (200 - (cont + 6)), true, true);
            //Se agregan las formulas y los bordes de las filas y columnas
            celdasEficacia[2][cont + 4].setCellFormula(forum_ver1.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[2][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[3][cont + 4].setCellFormula(forum_ver2.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[3][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellFormula(forum_ver3.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[6][cont + 4].setCellFormula(forum_ver4.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[6][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[8][cont + 4].setCellFormula(forum_ver5.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[8][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[9][cont + 4].setCellFormula(forum_ver6.replaceAll("198", String.valueOf(cont + 4)));
            celdasEficacia[9][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[5][cont + 4].setCellStyle(borde_doble);
            celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            linea_simple_decimal = null;
            borde_doble = null;
            linea_simple_formula = null;
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            forum_ver4 = null;
            forum_ver5 = null;
            forum_ver6 = null;

            //cuadro 12 totales causas de desercion

            eficacia = wb.getSheetAt(11);
            filasEficacia = new HSSFRow[200];
            celdasEficacia = new HSSFCell[12][200];
            for (int i = 0; i <= 11; i++) {
                for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                    if (i == 0) {
                        filasEficacia[j] = eficacia.getRow(j);
                    }
                    celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                }
            }
//            celdasEficacia[0][0].setCellValue("3. REPROBACIÓN DEFINITIVA POR UNA O MÁS ASIGNATURAS AL TÉRMINO DEL CUATRIMESTRE,, " + anio);
//            celdasEficacia[0][1].setCellValue("NIVEL 5B, 5B3 y 5A");
            cont = 1;
            borde_doble = celdasEficacia[2][198].getCellStyle();
            linea_simple = celdasEficacia[0][5].getCellStyle();
            //se mantiene el estilo de borde simple y con formato decimal
            linea_simple_decimal = celdasEficacia[2][5].getCellStyle();
            //Se obtienen las formulas pr columna para ser utilizadas despues
            linea_simple_formula = celdasEficacia[4][5].getCellStyle();
           //System.out.println(forum_ver1);
            for (int i = 0; i <= ids.size() - 1; i++) {
                celdasEficacia[0][4 + cont].setCellValue(cont);
                celdasEficacia[0][4 + cont].setCellStyle(linea_simple);
                celdasEficacia[1][4 + cont].setCellValue(univs.get(i).toString());
                celdasEficacia[1][4 + cont].setCellStyle(linea_simple);

                cont++;
            }
         // celdasEficacia[2][198].setCellFormula(forum_ver1.replaceAll("199", String.valueOf(cont + 5)));
         celdasEficacia[0][cont + 5].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            eficacia.setForceFormulaRecalculation(true);
            //Se vacian las variables 
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            linea_simple_decimal = null;
            borde_doble = null;
            linea_simple_formula = null;
            forum_ver1 = null;
            forum_ver2 = null;
            forum_ver3 = null;
            forum_ver4 = null;
            forum_ver5 = null;
            forum_ver6 = null;












            EnviarExcel(response, wb, "Formato EFICACIA " + anio);
            return mapping.findForward(SUCCESS);
        }
        return mapping.findForward(SUCCESS);
    }

    /**
     * Este método es genérico para exportar un libro creado con HSSF a un
     * Response
     *
     * @param response Response a donde se va a enviar el libro .xls
     * @param libro Libro que se va a exportar
     * @param nombreArchivo Nombre del archivo a guardar en el equipo
     */
    private void EnviarExcel(HttpServletResponse response, HSSFWorkbook libro, String nombreArchivo) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".xls");
            ServletOutputStream fileOut = response.getOutputStream();
            libro.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void removeRow(HSSFSheet sheet, int rowIndex) {
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            HSSFRow removingRow = sheet.getRow(rowIndex);
            if (removingRow != null) {
                sheet.removeRow(removingRow);
            }
        }

    }

    /**
     * HSSFRow Copy Command Inserts a existing row into a new row, will
     * automatically push down any existing rows. Copy is done cell by cell and
     * supports, and the command tries to copy all properties available (style,
     * merged cells, values, etc...)
     *
     * @param workbook Workbook containing the worksheet that will be changed
     * @param worksheet WorkSheet containing rows to be copied
     * @param sourceRowNum Source Row Number
     * @param destinationRowNum Destination Row Number
     */
    private void CopyRow(HSSFWorkbook workbook, HSSFSheet worksheet, int sourceRowNum, int destinationRowNum) {

        HSSFRow newRow = worksheet.getRow(destinationRowNum);
        HSSFRow sourceRow = worksheet.getRow(sourceRowNum);

        // Si existe una fila hay que moverlas y crear una nueva
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Obtenemos una copia de las celdas
            HSSFCell oldCell = sourceRow.getCell(i);
            HSSFCell newCell = newRow.createCell(i);

            // Si es nulo el valor de la celda anterior brincar a la siguiente
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Copiar el estilo
            HSSFCellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            newCell.setCellStyle(newCellStyle);

            // Si hay comentarios hay que copiarlos
            if (newCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // Si hay un hipervínculo hay que copiarlo
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Asignar el tipo de valor de la celda
            newCell.setCellType(oldCell.getCellType());

            // Asignar el valor a la celda
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

        // Si hay celdas combinadas copiarlas a la nueva fila
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum()
                        + (cellRangeAddress.getFirstRow()
                        - cellRangeAddress.getLastRow())),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }
}
