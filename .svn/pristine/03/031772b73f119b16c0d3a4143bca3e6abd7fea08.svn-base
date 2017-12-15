/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
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

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public class Excel_Reporte_Eficiencia extends org.apache.struts.action.Action {

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
        String consulta;
        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }
        String periodo;
        String anio = "";
        ConexionMySQL con = new ConexionMySQL();
        periodo = request.getParameter("ipe").toString();
        //obtener el año del periodo seleccionado
        consulta = "select anio from periodos where id_periodo=".concat(periodo);
        ResultSet rsCon = con.Consultar(consulta);
        ResultSet rs;
        if (rsCon.next()) {
            anio = rsCon.getString(1);
        }
        //obtener el valor de ask desde la url
        String ask = request.getParameter("ask");
        if (ask.equals("cat_Eficiencia")) {
            //Especifico en donde se encuentra el archivo de excel en forma relativa
            String rutaRelativaWeb = "/files/II_Eficiencia.xls";
            //Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
            String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);
            //Cargo en archivo en un Stream
            InputStream inp = new FileInputStream(rutaAbsolutaEnDisco);
            //Creo el libro de excel a partir de un archivo para su modificación
            HSSFWorkbook wb = new HSSFWorkbook(inp);
            inp.close();
            //EFICIENCIA 
            //Variables para los estilos

            
            
            /*
             * --------------------------------INICIA LA HOJA 1--------------------------------
             */
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            HSSFSheet eficiencia = wb.getSheetAt(0);
            //Creo dos arreglos que van a contener las filas y las celdas
            HSSFRow[] filasEficiencia = new HSSFRow[200];
            HSSFCell[][] celdasEficiencia = new HSSFCell[5][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }
            CellStyle doublelinea = celdasEficiencia[0][198].getCellStyle();
            consulta = "Select id_universidad, nombre_universidad from universidades where activo = 1 and id_universidad>0";
            rsCon = con.Consultar(consulta);
            ArrayList ids = new ArrayList();//almacena los ids de las universidades
            ArrayList univs = new ArrayList();//almacena los nombres de las universidades
            while (rsCon.next()){
                ids.add(rsCon.getString(1));
                univs.add(rsCon.getString(2));
            }
            int cont = 1;
            //LISTA UNIVERSIDADES
            //Se extrae el estilo de la tabla existente y se crea un objeto con ese estilo para aplicarlo en las otras celdas
            CellStyle doble_linea = celdasEficiencia[0][4].getCellStyle();
            CellStyle izq_linea = celdasEficiencia[2][4].getCellStyle();
            CellStyle cen_linea = celdasEficiencia[3][4].getCellStyle();
            CellStyle der_linea = celdasEficiencia[4][4].getCellStyle();
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("12. COSTO POR ALUMNO, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            double presupuesto = 0, matricula = 0, sumPresupuesto = 0, sumMatricula=0;
            for(int i=0;i<=ids.size()-1;i++){
                celdasEficiencia[0][3 + cont].setCellValue(cont);
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficiencia[4][3 + cont].setCellStyle(der_linea);
                consulta = "SELECT autorizado FROM eficaciain11 WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
               
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs.next()) {
                    presupuesto = rs.getDouble(1);
                    sumPresupuesto += presupuesto;
                    celdasEficiencia[2][3 + cont].setCellValue(presupuesto);
                }
                consulta = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs.next()) {
                    matricula = rs.getInt(1);
                    sumMatricula+=matricula;
                    celdasEficiencia[3][3 + cont].setCellValue(matricula);
                }
                //operaciones de porcetaje de la columna E
                if (matricula > 0){
                    celdasEficiencia[4][cont + 3].setCellValue(matricula + presupuesto);
                }else{
                    celdasEficiencia[4][cont + 3].setCellValue(0);
                }
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasEficiencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficiencia[3][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
                
            
            
            /*
             * --------------------------------INICIA LA HOJA 2--------------------------------
             */
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(1);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[5][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("13. UTILIZACIÓN DE ESPACIOS, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){ //RECORRE LAS FILAS
                celdasEficiencia[0][3 + cont].setCellValue(cont);//PONE LA NUMERACION
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());//PONE EL NOMBRE DE LA UNIVERSIDAD
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficiencia[4][3 + cont].setCellStyle(der_linea);
                //matricula total
                consulta = "SELECT matricula_total FROM datos_universidad WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs.next()) {
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                }
                //CAPACIDAD TOTAL INSTALADA
                consulta = "SELECT SUM((no_laboratorios*capacidad)) as 'suma' FROM edificios "
                        + "INNER JOIN eficienciain13 on edificios.id_edificio=eficienciain13.id_edifi "
                        + "WHERE id_edificio=(SELECT id_edificio FROM edificios_universidad "
                        + "WHERE id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo)
                        .concat(" AND edificios_universidad.id_edificio=edificios.id_edificio)");
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    if(rs.getInt(1) >0){
                        celdasEficiencia[3][cont + 3].setCellValue(rs.getInt(1));
                    }else{
                        celdasEficiencia[3][cont + 3].setCellValue(0);
                    }
                }
                //operaciones de porcetaje de la columna E
                celdasEficiencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
                celdasEficiencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasEficiencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficiencia[3][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");


            
            /*
             * --------------------------------INICIA LA HOJA 3--------------------------------
             */
            //Obtengo la hoja 2 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(2);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[6][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }  
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("13. UTILIZACIÓN DE ESPACIOS EN BASE A LA MATRÍCULA SEGÚN TURNO, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){ //RECORRE LAS FILAS
                celdasEficiencia[0][3 + cont].setCellValue(cont);//PONE LA NUMERACION
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());//PONE EL NOMBRE DE LA UNIVERSIDAD
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficiencia[4][3 + cont].setCellStyle(der_linea);
                //matricula total
                consulta = "SELECT matricula_total FROM datos_universidad WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs.next()) {
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                }
                //CAPACIDAD TOTAL INSTALADA
                int capacidad=0;
                consulta = "SELECT SUM((no_laboratorios*capacidad)) as 'suma' FROM edificios "
                        + "INNER JOIN eficienciain13 on edificios.id_edificio=eficienciain13.id_edifi "
                        + "WHERE id_edificio=(SELECT id_edificio FROM edificios_universidad "
                        + "WHERE id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo)
                        .concat(" AND edificios_universidad.id_edificio=edificios.id_edificio)");

                rs = con.Consultar(consulta);
                while (rs.next()) {
                    capacidad=rs.getInt(1);
                }
                int matutino=0, vespertino=0;
                consulta="SELECT turno_matutino, turno_vespertino FROM eficienciain13_1 WHERE id_universidad =".concat(ids.get(i).toString());
                rs = con.Consultar(consulta);
                while(rs.next()){
                    matutino=rs.getInt(1);
                    vespertino=rs.getInt(2);
                }
                double vespor;
                if(vespertino>0){
                    vespor = vespertino*100/(matutino+vespertino);//porcentaje del vespertino
                    vespor = Math.rint(vespor*100)/100;
                    capacidad=(int) ((vespor*capacidad/100)+capacidad);
                    celdasEficiencia[3][cont + 3].setCellValue(capacidad);
                }else{
                    celdasEficiencia[3][cont + 3].setCellValue(0);
                }
                //operaciones de porcetaje de la columna E
                celdasEficiencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
                celdasEficiencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasEficiencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficiencia[3][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            /*
             * --------------------------------------------------------------------------------
             * --------------------------------INICIA LA HOJA 4--------------------------------
             * --------------------------------------------------------------------------------
             */
            //Obtengo la hoja 4 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(3);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[29][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 28; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }
            cont = 1;
            
            //estilos
            CellStyle styleLadosDobles = celdasEficiencia[0][4].getCellStyle();
            CellStyle styleNormal = celdasEficiencia[1][4].getCellStyle();
            CellStyle styleFinalCuadro = celdasEficiencia[13][4].getCellStyle();
            CellStyle stylePrincipioCuadro = celdasEficiencia[14][4].getCellStyle();
            CellStyle styleTotal = celdasEficiencia[7][4].getCellStyle();
            
            
            
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("14. UTILIZACION DEL EQUIPO DE CÓMPUTO, " + anio);
            celdasEficiencia[1][0].setCellValue("14. PORCENTAJE DE UTILIZACION DEL EQUIPO DE CÓMPUTO, " + anio);
            for(int i=0;i<=ids.size()-1;i++){
                //Columna A "No."
                celdasEficiencia[0][3 + cont].setCellValue(cont);
                celdasEficiencia[0][cont + 3].setCellStyle(styleLadosDobles);
                //Columna B "Universidad"
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());
                celdasEficiencia[1][cont + 3].setCellStyle(styleLadosDobles);
                //Columna C a N 
                consulta = "SELECT docente_ptc, docente_asignatura, alumnos, personal_admin, mandos, docentes_ptc_int, docente_asignatura_int, alumnos_int, personal_admin_int, mandos_int FROM eficienciain14 WHERE id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                    celdasEficiencia[3][3 + cont].setCellValue(rs.getInt(2));
                    celdasEficiencia[4][3 + cont].setCellValue(rs.getInt(3));
                    celdasEficiencia[5][3 + cont].setCellValue(rs.getInt(4));
                    celdasEficiencia[6][3 + cont].setCellValue(rs.getInt(5));
                    celdasEficiencia[8][3 + cont].setCellValue(rs.getInt(6));
                    celdasEficiencia[9][3 + cont].setCellValue(rs.getInt(7));
                    celdasEficiencia[10][3 + cont].setCellValue(rs.getInt(8));
                    celdasEficiencia[11][3 + cont].setCellValue(rs.getInt(9));
                    celdasEficiencia[12][3 + cont].setCellValue(rs.getInt(10));
                }
                celdasEficiencia[2][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[3][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[4][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[5][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[6][cont + 3].setCellStyle(styleNormal);
                //columna h = 7 es un total
                celdasEficiencia[7][cont + 3].setCellStyle(styleLadosDobles);
                celdasEficiencia[7][cont + 3].setCellStyle(styleTotal); //Negritas
                celdasEficiencia[7][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[7][cont + 3].setCellFormula("SUM(C" + (cont + 4)+ ":G" + (cont + 4) + ")");
                
                celdasEficiencia[8][cont + 3].setCellStyle(styleLadosDobles);
                celdasEficiencia[9][cont + 3].setCellStyle(styleLadosDobles);
                celdasEficiencia[10][cont + 3].setCellStyle(styleLadosDobles);
                celdasEficiencia[11][cont + 3].setCellStyle(styleLadosDobles);
                celdasEficiencia[12][cont + 3].setCellStyle(styleLadosDobles);
                //columna N = 13 es un total
                celdasEficiencia[13][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[13][cont + 3].setCellStyle(styleTotal); //Negritas
                celdasEficiencia[13][cont + 3].setCellStyle(styleFinalCuadro);
                celdasEficiencia[13][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[13][cont + 3].setCellFormula("SUM(I" + (cont + 4)+ ":M" + (cont + 4) + ")");
                
                //Cuadro 2
                //Columna O "Número"
                celdasEficiencia[14][3 + cont].setCellValue(cont);
                celdasEficiencia[14][cont + 3].setCellStyle(stylePrincipioCuadro);
                //Columna P "Universidades"
                celdasEficiencia[15][3 + cont].setCellValue(univs.get(i).toString());
                celdasEficiencia[15][cont + 3].setCellStyle(styleLadosDobles);
                //Columna Q
                celdasEficiencia[16][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[16][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[16][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,C"+ (cont + 4)+"/H"+ (cont + 4)+"*100)");
                //Columna R
                celdasEficiencia[17][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[17][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[17][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,D"+ (cont + 4)+"/H"+ (cont + 4)+")*100");
                //Columna S
                celdasEficiencia[18][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[18][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[18][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,E"+ (cont + 4)+"/H"+ (cont + 4)+")*100");
                //Columna T
                celdasEficiencia[19][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[19][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[19][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,F"+ (cont + 4)+"/H"+ (cont + 4)+")*100");
                //Columna U
                celdasEficiencia[20][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[20][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[20][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,G"+ (cont + 4)+"/H"+ (cont + 4)+")*100");
                //Columna V total
                celdasEficiencia[21][cont + 3].setCellStyle(styleTotal); //Negritas
                celdasEficiencia[21][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[21][cont + 3].setCellFormula("SUM(Q" + (cont + 4)+ ":U" + (cont + 4) + ")");
                //Columna W
                celdasEficiencia[22][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[22][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[22][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,I"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
                //Columna x
                celdasEficiencia[23][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[23][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[23][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,J"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
                //Columna Y
                celdasEficiencia[24][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[24][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[24][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,K"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
                //Columna Z
                celdasEficiencia[25][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[25][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[25][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,L"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
                //Columna AA
                celdasEficiencia[26][cont + 3].setCellStyle(styleNormal);
                celdasEficiencia[26][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[26][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,M"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
                //Columna AB total
                celdasEficiencia[27][cont + 3].setCellStyle(styleTotal); //Negritas
                celdasEficiencia[27][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasEficiencia[27][cont + 3].setCellFormula("SUM(W" + (cont + 4)+ ":AA" + (cont + 4) + ")");

                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            /*
             * ----------TOTALES---------
             */
            //Total C
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            //Total D
            celdasEficiencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficiencia[3][cont + 3].setCellStyle(doublelinea);
            //Total E
            celdasEficiencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[4][cont + 3].setCellFormula("SUM(E5" + ":E" + (cont + 3) + ")");
            celdasEficiencia[4][cont + 3].setCellStyle(doublelinea);
            //Total F
            celdasEficiencia[5][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[5][cont + 3].setCellFormula("SUM(F5" + ":F" + (cont + 3) + ")");
            celdasEficiencia[5][cont + 3].setCellStyle(doublelinea);
            //Total G
            celdasEficiencia[6][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[6][cont + 3].setCellFormula("SUM(G5" + ":G" + (cont + 3) + ")");
            celdasEficiencia[6][cont + 3].setCellStyle(doublelinea);
            //Total H
            celdasEficiencia[7][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[7][cont + 3].setCellFormula("SUM(H5" + ":H" + (cont + 3) + ")");
            celdasEficiencia[7][cont + 3].setCellStyle(doublelinea);
            //Total I
            celdasEficiencia[8][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[8][cont + 3].setCellFormula("SUM(I5" + ":I" + (cont + 3) + ")");
            celdasEficiencia[8][cont + 3].setCellStyle(doublelinea);
            //Total J
            celdasEficiencia[9][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[9][cont + 3].setCellFormula("SUM(J5" + ":J" + (cont + 3) + ")");
            celdasEficiencia[9][cont + 3].setCellStyle(doublelinea);
            //Total K
            celdasEficiencia[10][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[10][cont + 3].setCellFormula("SUM(K5" + ":K" + (cont + 3) + ")");
            celdasEficiencia[10][cont + 3].setCellStyle(doublelinea);
            //Total L
            celdasEficiencia[11][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[11][cont + 3].setCellFormula("SUM(L5" + ":L" + (cont + 3) + ")");
            celdasEficiencia[11][cont + 3].setCellStyle(doublelinea);
            //Total M
            celdasEficiencia[12][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[12][cont + 3].setCellFormula("SUM(M5" + ":M" + (cont + 3) + ")");
            celdasEficiencia[12][cont + 3].setCellStyle(doublelinea);
            //Total N
            celdasEficiencia[13][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[13][cont + 3].setCellFormula("SUM(N5" + ":N" + (cont + 3) + ")");
            celdasEficiencia[13][cont + 3].setCellStyle(doublelinea);
            //Total Q
            celdasEficiencia[16][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[16][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,C"+ (cont + 4)+"/H"+ (cont + 4)+"*100)");
            celdasEficiencia[16][cont + 3].setCellStyle(doublelinea);
            //Total R
            celdasEficiencia[17][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[17][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,D"+ (cont + 4)+"/H"+ (cont + 4)+"*100)");
            celdasEficiencia[17][cont + 3].setCellStyle(doublelinea);
            //Total S
            celdasEficiencia[18][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[18][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,E"+ (cont + 4)+"/H"+ (cont + 4)+"*100)");
            celdasEficiencia[18][cont + 3].setCellStyle(doublelinea);
            //Total T
            celdasEficiencia[19][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[19][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,F"+ (cont + 4)+"/H"+ (cont + 4)+"*100)");
            celdasEficiencia[19][cont + 3].setCellStyle(doublelinea);
            //Total U
            celdasEficiencia[20][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[20][cont + 3].setCellFormula("IF(H" + (cont + 4)+ "=0,0,G"+ (cont + 4)+"/H"+ (cont + 4)+"*100)");
            celdasEficiencia[20][cont + 3].setCellStyle(doublelinea);
            //Total V
            celdasEficiencia[21][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[21][cont + 3].setCellFormula("SUM(Q" + (cont + 4)+ ":U" + (cont + 4) + ")");
            celdasEficiencia[21][cont + 3].setCellStyle(doublelinea);
            //Total W
            celdasEficiencia[22][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[22][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,I"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
            celdasEficiencia[22][cont + 3].setCellStyle(doublelinea);
            //Total X
            celdasEficiencia[23][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[23][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,J"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
            celdasEficiencia[23][cont + 3].setCellStyle(doublelinea);
            //Total Y
            celdasEficiencia[24][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[24][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,K"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
            celdasEficiencia[24][cont + 3].setCellStyle(doublelinea);
            //Total Z
            celdasEficiencia[25][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[25][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,L"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
            celdasEficiencia[25][cont + 3].setCellStyle(doublelinea);
            //Total AA
            celdasEficiencia[26][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[26][cont + 3].setCellFormula("IF(N" + (cont + 4)+ "=0,0,M"+ (cont + 4)+"/N"+ (cont + 4)+")*100");
            celdasEficiencia[26][cont + 3].setCellStyle(doublelinea);
            //Total AB
            celdasEficiencia[27][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[27][cont + 3].setCellFormula("SUM(W" + (cont + 4)+ ":AA" + (cont + 4) + ")");
            celdasEficiencia[27][cont + 3].setCellStyle(doublelinea);
            //notas finales
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            celdasEficiencia[14][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            /*
             * --------------------------------------------------------------------------------
             * --------------------------------INICIA LA HOJA 5--------------------------------
             * --------------------------------------------------------------------------------
             */
            //Obtengo la hoja 2 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(4);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[6][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("15. CERTIFICACIÓN ISO 9001:2000, " + anio);
            for(int i=0;i<=ids.size()-1;i++){ //RECORRE LAS FILAS
                celdasEficiencia[0][2 + cont].setCellValue(cont);//PONE LA NUMERACION
                celdasEficiencia[0][2 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][2 + cont].setCellValue(univs.get(i).toString());//PONE EL NOMBRE DE LA UNIVERSIDAD
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][2 + cont].setCellStyle(doble_linea);
                celdasEficiencia[2][2 + cont].setCellStyle(izq_linea);
                celdasEficiencia[3][2 + cont].setCellStyle(cen_linea);
                celdasEficiencia[4][2 + cont].setCellStyle(der_linea);
                //certificacion
                consulta="SELECT con_certificado FROM certificaciones c "
                        + "INNER JOIN eficienciain15 e on c.id_certificacion=e.id_certificacion "
                        + "WHERE nombre_certificacion LIKE '%ISO 9001%' AND c.activo=1 "
                        + "AND id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo)
                        .concat(" ORDER BY vigencia1 desc LIMIT 1");
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasEficiencia[2][cont+2].setCellValue(rs.getString(1));
                }
                //egresados
                consulta="select sum(egresados) as suma from eficaciain5 "
                        + "where id_universidad=".concat(ids.get(i).toString())
                        .concat(" and id_periodo =").concat(periodo).concat(" and anio=1");
                rs = con.Consultar(consulta);
                while(rs.next()){
                    if (rs.getInt(1)>0){
                        celdasEficiencia[3][cont+2].setCellValue(1);
                    }else{
                        celdasEficiencia[3][cont+2].setCellValue(0);
                    }
                }
                //operaciones de porcetaje de la columna E
                celdasEficiencia[4][cont + 2].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
                celdasEficiencia[4][cont + 2].setCellFormula("IF(D"+(cont+3)+">0,C"+(cont+3)+"/D"+ (cont + 3) + "*100,0)");
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 4)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 2].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 2].setCellFormula("SUM(C5" + ":C" + (cont + 2) + ")");
            celdasEficiencia[2][cont + 2].setCellStyle(doublelinea);
            celdasEficiencia[3][cont + 2].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasEficiencia[3][cont + 2].setCellFormula("SUM(D5" + ":D" + (cont + 2) + ")");
            celdasEficiencia[3][cont + 2].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 3].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            /*
             * --------------------------------------------------------------------------------
             * --------------------------------INICIA LA HOJA 6--------------------------------
             * --------------------------------------------------------------------------------
             */
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(5);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[5][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }  
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("16. LIBROS POR ALUMNO, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            //while (rsCon.next()) {
            for(int i=0;i<=ids.size()-1;i++){
                celdasEficiencia[0][3 + cont].setCellValue(cont);
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficiencia[4][3 + cont].setCellStyle(der_linea);
                consulta = "SELECT libros FROM eficienciain16 WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs.next()) {
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                }
                consulta = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                //VALORES DEL INDICADOR 1 POR UNIVERSIDAD
                while (rs.next()) {
                    celdasEficiencia[3][3 + cont].setCellValue(rs.getInt(1));
                }
                //operaciones de porcetaje de la columna E
                celdasEficiencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
                celdasEficiencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + ",0)");
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasEficiencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficiencia[3][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            /*
             * --------------------------------------------------------------------------------
             * --------------------------------INICIA LA HOJA 7--------------------------------
             * --------------------------------------------------------------------------------
             */
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(6);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[5][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }  
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("16. TITULOS POR ALUMNO, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            //while (rsCon.next()) {
            for(int i=0;i<=ids.size()-1;i++){
                celdasEficiencia[0][3 + cont].setCellValue(cont);
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficiencia[4][3 + cont].setCellStyle(der_linea);
                consulta = "SELECT titulos FROM eficienciain16 WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                }
                consulta = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    celdasEficiencia[3][3 + cont].setCellValue(rs.getInt(1));
                }
                //operaciones de porcetaje de la columna E
                celdasEficiencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
                celdasEficiencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + ",0)");
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasEficiencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficiencia[3][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            
            /*
             * --------------------------------------------------------------------------------
             * --------------------------------INICIA LA HOJA 8--------------------------------
             * --------------------------------------------------------------------------------
             */
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(7);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[3][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }
            //estilos
            
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("16. NÚMERO DE SUBSCRIPCIONES A REVISTAS FÍSICAMENTE O ELECTRÓNICAS VIGENTES, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasEficiencia[0][3 + cont].setCellValue(cont);
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(der_linea);
                consulta = "SELECT revistas FROM eficienciain16 WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                }
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            /*
             * --------------------------------------------------------------------------------
             * --------------------------------INICIA LA HOJA 9--------------------------------
             * --------------------------------------------------------------------------------
             */
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(8);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[3][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("16. NÚMERO DE SUBSCRIPCIONES A BIBLIOTECAS VIRTUALES VIGENTES AL CICLO ESCOLAR EVALUADO PARA SER CONSULTADAS POR LOS ALUMNOS, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasEficiencia[0][3 + cont].setCellValue(cont);
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(der_linea);
                consulta = "SELECT bibliotecas FROM eficienciain16 WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                }
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            /*
             * --------------------------------------------------------------------------------
             * --------------------------------INICIA LA HOJA 10--------------------------------
             * --------------------------------------------------------------------------------
             */
            //Obtengo la hoja 1 y la asigno a un nuevo objeto
            eficiencia = wb.getSheetAt(9);
            //Creo dos arreglos que van a contener las filas y las celdas
            filasEficiencia = new HSSFRow[200];
            celdasEficiencia = new HSSFCell[5][200];
            //Generamos todos los objetos para manipular las celdas de forma mas sencilla
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasEficiencia[j] = eficiencia.getRow(j);
                    }
                    celdasEficiencia[i][j] = filasEficiencia[j].getCell(i);
                }
            }  
            cont = 1;
            //Titulo del libro seguido del año del MECASUT
            celdasEficiencia[0][0].setCellValue("17. RELACIÓN ALUMNO / DOCENTE, " + anio);
            celdasEficiencia[2][2].setCellValue(anio);
            //while (rsCon.next()) {
            for(int i=0;i<=ids.size()-1;i++){
                celdasEficiencia[0][3 + cont].setCellValue(cont);
                celdasEficiencia[0][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //Se aplican los nuevos estilos a las celdas 
                celdasEficiencia[1][3 + cont].setCellStyle(doble_linea);
                celdasEficiencia[2][3 + cont].setCellStyle(izq_linea);
                celdasEficiencia[3][3 + cont].setCellStyle(cen_linea);
                celdasEficiencia[4][3 + cont].setCellStyle(der_linea);
                //matricula total
                consulta = "SELECT matricula_total, prof_tc FROM datos_universidad WHERE id_universidad=".concat(ids.get(i).toString()).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    celdasEficiencia[2][3 + cont].setCellValue(rs.getInt(1));
                    celdasEficiencia[3][3 + cont].setCellValue(rs.getInt(2));
                }
                //operaciones (división de la columna E)
                celdasEficiencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
                celdasEficiencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + ",0)");
                cont++;
            }
            eficiencia.shiftRows(198, eficiencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            eficiencia.setForceFormulaRecalculation(true);
            celdasEficiencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasEficiencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasEficiencia[2][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasEficiencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasEficiencia[3][cont + 3].setCellStyle(doublelinea);
            celdasEficiencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");

           
            /*
             * ----------------------------------------------------------------------------------------------
             * ---------------------FINALIZACIÓN DE LAS HOJAS DE CÁLCULO DE EXCEL----------------------------
             * ----------------------------------------------------------------------------------------------
             */
            EnviarExcel(response, wb, "Formato Eficiencia " + anio);
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