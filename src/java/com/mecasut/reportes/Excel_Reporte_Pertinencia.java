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
 * @author 
 * Actualización 2016: Salvador Zamora Arias
 */
public class Excel_Reporte_Pertinencia extends org.apache.struts.action.Action {

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
        if (ask.equals("cat_Pertinencia")) {
            //Especifico en donde se encuentra el archivo de excel en forma relativa
            String rutaRelativaWeb = "/files/III_Pertinencia.xls";
            //Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
            String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);
            //Cargo en archivo en un Stream
            InputStream inp = new FileInputStream(rutaAbsolutaEnDisco);
            //Creo el libro de excel a partir de un archivo para su modificación
            HSSFWorkbook wb = new HSSFWorkbook(inp);
            inp.close();
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 1--------------------------------
            * --------------------------------------------------------------------------------
            */
            HSSFSheet pertinencia = wb.getSheetAt(0);
            HSSFRow[] filasPertinencia = new HSSFRow[200];
            HSSFCell[][] celdasPertinencia = new HSSFCell[5][200];
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            CellStyle doublelinea = celdasPertinencia[2][198].getCellStyle();
            CellStyle doublelineadecimal = celdasPertinencia[4][198].getCellStyle();
            CellStyle normal = celdasPertinencia[0][4].getCellStyle();
            CellStyle numero = celdasPertinencia[2][4].getCellStyle();
            CellStyle numeroDecimal = celdasPertinencia[4][4].getCellStyle();
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
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("18. PROGRAMAS EDUCATIVOS DEL TSU CON EVALUACIÓN DIAGNÓSTICA, " + anio);
            celdasPertinencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(normal);
                celdasPertinencia[3][3 + cont].setCellStyle(normal);
                celdasPertinencia[4][3 + cont].setCellStyle(numeroDecimal);
                consulta="SELECT COUNT(id_universidad) FROM pertinenciain18 WHERE id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo).concat(" AND evaluacion_diagnostica=1");
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getInt(1));
                }
                consulta="SELECT COUNT(id_universidad) FROM pe_universidad WHERE id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo).concat(" AND activo=1");
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[3][cont + 3].setCellValue(rs.getInt(1));
                }
                //operaciones de porcetaje de la columna E
                celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
                celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelineadecimal);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 2--------------------------------
            * --------------------------------------------------------------------------------
            */
            pertinencia = wb.getSheetAt(1);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[5][200];
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 1;
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("18. MATRÍCULA DE LOS PROGRAMAS EDUCATIVOS DEL TSU CON EVALUACIÓN DIAGNÓSTICA, " + anio);
            celdasPertinencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(normal);
                celdasPertinencia[3][3 + cont].setCellStyle(normal);
                celdasPertinencia[4][3 + cont].setCellStyle(numeroDecimal);
                consulta="SELECT SUM(matricula_con_eva) FROM pertinenciain18_1 WHERE id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo).concat(" AND id_pe IN (SELECT id_pe FROM pertinenciain18 WHERE evaluacion_diagnostica=1)");
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getInt(1));
                }
                consulta="SELECT SUM(matricula_inicial) FROM pertinenciain18_1 WHERE id_universidad=".concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[3][cont + 3].setCellValue(rs.getInt(1));
                }
                //operaciones de porcetaje de la columna E
                celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelineadecimal);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 8--------------------------------
            * --------------------------------------------------------------------------------
            */
            pertinencia = wb.getSheetAt(7);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[45][200];
            for (int i = 0; i <= 44; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 1;
            CellStyle dobleLineaTexto = celdasPertinencia[0][6].getCellStyle();
            CellStyle dobleLineaNumero = celdasPertinencia[7][6].getCellStyle();
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("20. RELACIÓN DE UNIVERSIDADES TECNOLÓGICAS DE ACUERDO AL STATUS DE LOS ENFOQUES CENTRADOS EN EL APRENDIZAJE, " + anio);
            celdasPertinencia[14][0].setCellValue("20. RELACIÓN DE UNIVERSIDADES TECNOLÓGICAS DE ACUERDO AL STATUS DE LOS ENFOQUES CENTRADOS EN EL APRENDIZAJE, " + anio);
            celdasPertinencia[26][0].setCellValue("20. RELACIÓN DE UNIVERSIDADES TECNOLÓGICAS DE ACUERDO AL STATUS DE LOS ENFOQUES CENTRADOS EN EL APRENDIZAJE, " + anio);
            celdasPertinencia[38][0].setCellValue("20. RELACIÓN DE UNIVERSIDADES TECNOLÓGICAS DE ACUERDO AL STATUS DE LOS ENFOQUES CENTRADOS EN EL APRENDIZAJE, " + anio);
            celdasPertinencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][4 + cont].setCellValue(cont);
                celdasPertinencia[1][4 + cont].setCellValue(univs.get(i).toString());
                 //O No.
                celdasPertinencia[14][cont + 4].setCellValue(cont);
                //P universidad
                celdasPertinencia[15][cont + 4].setCellValue(univs.get(i).toString());
                //Q PROFESORES ASIGNATURA cursos internos
                //aa no
                celdasPertinencia[26][cont + 4].setCellValue(cont);
                //ab universidad
                celdasPertinencia[27][cont + 4].setCellValue(univs.get(i).toString());
                //am numeroDecimal
                celdasPertinencia[38][cont + 4].setCellValue(cont);
                //an universidad tecnologica
                
                consulta = "SELECT * FROM pertinenciain20 WHERE  id_universidad=".concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    //C cursos internos
                    celdasPertinencia[2][cont + 4].setCellValue(rs.getInt("cursos_realizados_i"));
                    //D Cursos externos
                    celdasPertinencia[3][cont + 4].setCellValue(rs.getInt("cursos_realizados_e"));
                    //E talleres internos
                    celdasPertinencia[4][cont + 4].setCellValue(rs.getInt("talleres_realizados_i"));
                    //F talleres externos
                    celdasPertinencia[5][cont + 4].setCellValue(rs.getInt("talleres_realizados_e"));
                    //G otros
                    celdasPertinencia[6][cont + 4].setCellValue(rs.getInt("acciones_otro"));
                    //h Total
                    celdasPertinencia[7][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    celdasPertinencia[7][cont + 4].setCellFormula("SUM(C"+(cont+5)+":G"+(cont+5)+")");
                    //I PROFESORES TIEMPO COMPLETO cursos internos
                    celdasPertinencia[8][cont + 4].setCellValue(rs.getInt("ptc_cursos_i"));
                    //J cursos externos
                    celdasPertinencia[9][cont + 4].setCellValue(rs.getInt("ptc_cursos_e"));    
                    //K talleres internos
                    celdasPertinencia[10][cont + 4].setCellValue(rs.getInt("ptc_talleres_i"));
                    //L talleres externos
                    celdasPertinencia[11][cont + 4].setCellValue(rs.getInt("ptc_talleres_e"));
                    //M otros
                    celdasPertinencia[12][cont + 4].setCellValue(rs.getInt("ptc_otros"));
                    //N Total
                    celdasPertinencia[13][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    celdasPertinencia[13][cont + 4].setCellFormula("SUM(I"+(cont+5)+":M"+(cont+5)+")");
                    //Q cursos interos
                    celdasPertinencia[16][cont + 4].setCellValue(rs.getInt("pa_cursos_i"));
                    //R cursos externos
                    celdasPertinencia[17][cont + 4].setCellValue(rs.getInt("pa_cursos_e"));
                    //S talleres internos
                    celdasPertinencia[18][cont + 4].setCellValue(rs.getInt("pa_talleres_i"));
                    //T talleres externos
                    celdasPertinencia[19][cont + 4].setCellValue(rs.getInt("pa_talleres_e"));
                    //U otros
                    celdasPertinencia[20][cont + 4].setCellValue(rs.getInt("pa_otros"));
                    //V totales
                    celdasPertinencia[21][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    celdasPertinencia[21][cont + 4].setCellFormula("SUM(Q"+(cont+5)+":U"+(cont+5)+")");
                    //W MATERIALES difusion
                    celdasPertinencia[22][cont + 4].setCellValue(rs.getInt("material_difusion"));
                    //X capacitacion
                    celdasPertinencia[23][cont + 4].setCellValue(rs.getInt("material_capacitacion"));
                    //Y otro
                    celdasPertinencia[24][cont + 4].setCellValue(rs.getInt("material_otro"));
                    //Z Total
                    celdasPertinencia[25][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    celdasPertinencia[25][cont + 4].setCellFormula("SUM(W"+(cont+5)+":Y"+(cont+5)+")");
                    //ac usuarios a quien va dirigido el material PTC
                    celdasPertinencia[28][cont + 4].setCellValue(rs.getInt("material_ptc"));
                    //ad PA
                    celdasPertinencia[29][cont + 4].setCellValue(rs.getInt("material_d_pa"));
                    //ae alumnos
                    celdasPertinencia[30][cont + 4].setCellValue(rs.getInt("material_d_alumnos"));
                    //af otro
                    celdasPertinencia[31][cont + 4].setCellValue(rs.getInt("material_d_otro"));
                    //ag especifique
                    celdasPertinencia[32][cont + 4].setCellValue(rs.getString("material_especifique"));
                    //ah terminaron la capacitacion
                    celdasPertinencia[33][cont + 4].setCellValue(rs.getInt("ptc_terminaron"));
                    //ai en proceso
                    celdasPertinencia[34][cont + 4].setCellValue(rs.getInt("ptc_proceso"));
                    //aj no tienen capacitacion
                    celdasPertinencia[35][cont + 4].setCellValue(rs.getInt("ptc_no_tienen"));
                    //ak total
                    celdasPertinencia[36][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    celdasPertinencia[36][cont + 4].setCellFormula("SUM(AH"+(cont+5)+":AJ"+(cont+5)+")");
                    //al ptc que aplican los enfoques
                    celdasPertinencia[37][cont + 4].setCellValue(rs.getInt("ptc_aplicando"));
                    //ao terminaron la capacitacion
                    celdasPertinencia[40][cont + 4].setCellValue(rs.getInt("pa_terminaron"));
                    //ap estan en proces de capacitacion
                    celdasPertinencia[41][cont + 4].setCellValue(rs.getInt("pa_proceso"));
                    //aq no tienen capacitacion
                    celdasPertinencia[42][cont + 4].setCellValue(rs.getInt("pa_no_tienen"));
                    //ar total
                    celdasPertinencia[43][cont + 4].setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    celdasPertinencia[43][cont + 4].setCellFormula("SUM(AO"+(cont+5)+":AQ"+(cont+5)+")");
                    //as ptc que estan aplicando los enfoques
                    celdasPertinencia[44][cont + 4].setCellValue(rs.getInt("pa_aplicando"));
                }
                //estilos
                celdasPertinencia[39][cont + 4].setCellValue(univs.get(i).toString());
                celdasPertinencia[0][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[1][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[2][4 + cont].setCellStyle(numero);
                celdasPertinencia[3][4 + cont].setCellStyle(numero);
                celdasPertinencia[4][4 + cont].setCellStyle(numero);
                celdasPertinencia[5][4 + cont].setCellStyle(numero);
                celdasPertinencia[6][4 + cont].setCellStyle(numero);
                celdasPertinencia[7][4 + cont].setCellStyle(dobleLineaNumero);
                celdasPertinencia[8][4 + cont].setCellStyle(numero);
                celdasPertinencia[9][4 + cont].setCellStyle(numero);
                celdasPertinencia[10][4 + cont].setCellStyle(numero);
                celdasPertinencia[11][4 + cont].setCellStyle(numero);
                celdasPertinencia[12][4 + cont].setCellStyle(numero);
                celdasPertinencia[13][4 + cont].setCellStyle(dobleLineaNumero);
                celdasPertinencia[14][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[15][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[16][4 + cont].setCellStyle(numero);
                celdasPertinencia[17][4 + cont].setCellStyle(numero);
                celdasPertinencia[18][4 + cont].setCellStyle(numero);
                celdasPertinencia[19][4 + cont].setCellStyle(numero);
                celdasPertinencia[20][4 + cont].setCellStyle(numero);
                celdasPertinencia[21][4 + cont].setCellStyle(dobleLineaNumero);
                celdasPertinencia[22][4 + cont].setCellStyle(numero);
                celdasPertinencia[23][4 + cont].setCellStyle(numero);
                celdasPertinencia[24][4 + cont].setCellStyle(numero);
                celdasPertinencia[25][4 + cont].setCellStyle(dobleLineaNumero);
                celdasPertinencia[26][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[27][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[28][4 + cont].setCellStyle(numero);
                celdasPertinencia[29][4 + cont].setCellStyle(numero);
                celdasPertinencia[30][4 + cont].setCellStyle(numero);
                celdasPertinencia[31][4 + cont].setCellStyle(numero);
                celdasPertinencia[32][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[33][4 + cont].setCellStyle(numero);
                celdasPertinencia[34][4 + cont].setCellStyle(numero);
                celdasPertinencia[35][4 + cont].setCellStyle(numero);
                celdasPertinencia[36][4 + cont].setCellStyle(numero);
                celdasPertinencia[37][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[38][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[39][4 + cont].setCellStyle(dobleLineaTexto);
                celdasPertinencia[40][4 + cont].setCellStyle(numero);
                celdasPertinencia[41][4 + cont].setCellStyle(numero);
                celdasPertinencia[42][4 + cont].setCellStyle(numero);
                celdasPertinencia[43][4 + cont].setCellStyle(normal);
                celdasPertinencia[44][4 + cont].setCellStyle(dobleLineaTexto);
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C6" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D6" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[4][cont + 3].setCellFormula("SUM(E6" + ":E" + (cont + 3) + ")");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[5][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[5][cont + 3].setCellFormula("SUM(F6" + ":F" + (cont + 3) + ")");
            celdasPertinencia[5][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[6][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[6][cont + 3].setCellFormula("SUM(G6" + ":G" + (cont + 3) + ")");
            celdasPertinencia[6][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[7][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[7][cont + 3].setCellFormula("SUM(C" + (cont + 4) + ":G" + (cont + 4) + ")");
            celdasPertinencia[7][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[8][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[8][cont + 3].setCellFormula("SUM(I6" + ":I" + (cont + 3) + ")");
            celdasPertinencia[8][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[9][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[9][cont + 3].setCellFormula("SUM(J6" + ":J" + (cont + 3) + ")");
            celdasPertinencia[9][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[10][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[10][cont + 3].setCellFormula("SUM(K6" + ":K" + (cont + 3) + ")");
            celdasPertinencia[10][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[11][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[11][cont + 3].setCellFormula("SUM(L6" + ":L" + (cont + 3) + ")");
            celdasPertinencia[11][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[12][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[12][cont + 3].setCellFormula("SUM(M6" + ":M" + (cont + 3) + ")");
            celdasPertinencia[12][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[13][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[13][cont + 3].setCellFormula("SUM(I6" + (cont + 4) + ":M" + (cont + 4) + ")");
            celdasPertinencia[13][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[16][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[16][cont + 3].setCellFormula("SUM(Q6" + ":Q" + (cont + 3) + ")");
            celdasPertinencia[16][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[17][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[17][cont + 3].setCellFormula("SUM(R6" + ":R" + (cont + 3) + ")");
            celdasPertinencia[17][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[18][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[18][cont + 3].setCellFormula("SUM(S6" + ":S" + (cont + 3) + ")");
            celdasPertinencia[18][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[19][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[19][cont + 3].setCellFormula("SUM(T6" + ":T" + (cont + 3) + ")");
            celdasPertinencia[19][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[20][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[20][cont + 3].setCellFormula("SUM(U6" + (cont + 4) + ":U" + (cont + 4) + ")");
            celdasPertinencia[20][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[21][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);  
            celdasPertinencia[21][cont + 3].setCellFormula("SUM(Q6" + ":U" + (cont + 3) + ")");
            celdasPertinencia[21][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[22][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[22][cont + 3].setCellFormula("SUM(W6" + ":W" + (cont + 3) + ")");
            celdasPertinencia[22][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[23][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[23][cont + 3].setCellFormula("SUM(X6" + ":X" + (cont + 3) + ")");
            celdasPertinencia[23][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[24][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[24][cont + 3].setCellFormula("SUM(Y6" + ":Y" + (cont + 3) + ")");
            celdasPertinencia[24][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[25][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[25][cont + 3].setCellFormula("SUM(W" + (cont+4) + ":Y" + (cont + 4) + ")");
            celdasPertinencia[25][cont + 3].setCellStyle(doublelinea);
            //Ac
            celdasPertinencia[28][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[28][cont + 3].setCellFormula("SUM(AC6" + ":AC" + (cont + 3) + ")");
            celdasPertinencia[28][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[29][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[29][cont + 3].setCellFormula("SUM(AD6" + ":AD" + (cont + 3) + ")");
            celdasPertinencia[29][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[30][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[30][cont + 3].setCellFormula("SUM(AE6" + ":AE" + (cont + 3) + ")");
            celdasPertinencia[30][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[31][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[31][cont + 3].setCellFormula("SUM(AF6" + ":AF" + (cont + 3) + ")");
            celdasPertinencia[31][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[33][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[33][cont + 3].setCellFormula("SUM(AH6" + ":AH" + (cont + 3) + ")");
            celdasPertinencia[33][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[34][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[34][cont + 3].setCellFormula("SUM(AI6" + ":AI" + (cont + 3) + ")");
            celdasPertinencia[34][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[35][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[35][cont + 3].setCellFormula("SUM(AJ6" + ":AJ" + (cont + 3) + ")");
            celdasPertinencia[35][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[36][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[36][cont + 3].setCellFormula("SUM(AH" + (cont + 4) +":AJ" + (cont + 4) + ")");
            celdasPertinencia[36][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[37][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[37][cont + 3].setCellFormula("SUM(AL6" + ":AL" + (cont + 3) + ")");
            celdasPertinencia[37][cont + 3].setCellStyle(doublelinea);
            //
            celdasPertinencia[40][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[40][cont + 3].setCellFormula("SUM(AO6" + ":AO" + (cont + 3) + ")");
            celdasPertinencia[40][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[41][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[41][cont + 3].setCellFormula("SUM(AP6" + ":AP" + (cont + 3) + ")");
            celdasPertinencia[41][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[42][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[42][cont + 3].setCellFormula("SUM(AQ6" + ":AQ" + (cont + 3) + ")");
            celdasPertinencia[42][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[43][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[43][cont + 3].setCellFormula("SUM(AO" + (cont + 4) +":AQ" + (cont + 4) + ")");
            celdasPertinencia[43][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[44][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[44][cont + 3].setCellFormula("SUM(AS6" + ":AS" + (cont + 3) + ")");
            celdasPertinencia[44][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 9--------------------------------
            * --------------------------------------------------------------------------------
            */
            pertinencia = wb.getSheetAt(8);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[4][200];
            for (int i = 0; i <= 3; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 1;
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("21. PROMEDIO DE SATISFACCION DE LOS PROGRAMAS EDUCATIVOS CENTRADOS EN EL ESTUDIANTE (SERVICIOS), " + anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(numeroDecimal);
                consulta="SELECT (SUM(r_a)*5+SUM(r_b)*4+SUM(r_c)*3+SUM(r_d)*2+SUM(r_e)*1)/(SUM(r_a)+SUM(r_b)+SUM(r_c)+SUM(r_d)+SUM(r_e))*2 as total10 FROM pertinenciain21 WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getDouble(1));
                }
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")/COUNTIF(C5" + ":C" + (cont + 3) +",\">0\")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelineadecimal);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 16--------------------------------
            * --------------------------------------------------------------------------------
            */
            pertinencia = wb.getSheetAt(15);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[5][200];
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 1;
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("22. PROGRAMAS EDUCATIVOS PERTINENTES , " + anio);
            celdasPertinencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(normal);
                celdasPertinencia[3][3 + cont].setCellStyle(normal);
                celdasPertinencia[4][3 + cont].setCellStyle(numeroDecimal);
                consulta="SELECT COUNT(id_universidad) FROM pertinenciain22 WHERE pertinente=1 AND id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getInt(1));
                }
                consulta="SELECT COUNT(id_universidad) FROM pertinenciain22 WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[3][cont + 3].setCellValue(rs.getInt(1));
                }
                celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
                celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelineadecimal);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 17--------------------------------
            * --------------------------------------------------------------------------------
            */
            pertinencia = wb.getSheetAt(16);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[5][200];
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 1;
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("22. MATRÍCULA DE LOS PROGRAMAS EDUCATIVOS PERTINENTES, " + anio);
            celdasPertinencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(normal);
                celdasPertinencia[3][3 + cont].setCellStyle(normal);
                celdasPertinencia[4][3 + cont].setCellStyle(numeroDecimal);
                consulta="SELECT SUM(matricula_ini_pe) FROM pertinenciain22 WHERE pertinente=1 AND id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getInt(1));
                }
                consulta="SELECT matricula_total FROM datos_universidad WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[3][cont + 3].setCellValue(rs.getInt(1));
                }
                celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
                celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelineadecimal);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 18--------------------------------
            * --------------------------------------------------------------------------------
            */
            
            pertinencia = wb.getSheetAt(17);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[17][200];
            for (int i = 0; i <= 16; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 1;
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("23. NIVEL DE ESTUDIOS DEL PROFESOR DE TIEMPO COMPLETO, " + anio);
            celdasPertinencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(normal);
                celdasPertinencia[3][3 + cont].setCellStyle(normal);
                celdasPertinencia[4][3 + cont].setCellStyle(normal);
                celdasPertinencia[5][3 + cont].setCellStyle(normal);
                celdasPertinencia[6][3 + cont].setCellStyle(normal);
                celdasPertinencia[7][3 + cont].setCellStyle(normal);
                celdasPertinencia[8][3 + cont].setCellStyle(normal);
                celdasPertinencia[9][3 + cont].setCellStyle(normal);
                celdasPertinencia[10][3 + cont].setCellStyle(normal);
                celdasPertinencia[11][3 + cont].setCellStyle(normal);
                celdasPertinencia[12][3 + cont].setCellStyle(normal);
                celdasPertinencia[13][3 + cont].setCellStyle(normal);
                celdasPertinencia[14][3 + cont].setCellStyle(normal);
                celdasPertinencia[15][3 + cont].setCellStyle(normal);
                celdasPertinencia[16][3 + cont].setCellStyle(normal);
                consulta="SELECT media_superior, media_superior_con, tsu, tsu_con, lic, lic_titulo, especialidad, maestria,"
                    .concat(" especialidad_grado, maestria_grado, doctorado, doctorado_grado FROM pertinenciain23")
                    .concat(" WHERE id_universidad=")
                    .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getInt("media_superior"));
                    celdasPertinencia[3][cont + 3].setCellValue(rs.getInt("media_superior_con"));
                    celdasPertinencia[4][cont + 3].setCellValue(rs.getInt("tsu"));
                    celdasPertinencia[5][cont + 3].setCellValue(rs.getInt("tsu_con"));
                    celdasPertinencia[6][cont + 3].setCellValue(rs.getInt("lic"));
                    celdasPertinencia[7][cont + 3].setCellValue(rs.getInt("lic_titulo"));
                    celdasPertinencia[8][cont + 3].setCellValue(rs.getInt("especialidad"));
                    celdasPertinencia[9][cont + 3].setCellValue(rs.getInt("maestria"));
                    celdasPertinencia[11][cont + 3].setCellValue(rs.getInt("especialidad_grado"));
                    celdasPertinencia[12][cont + 3].setCellValue(rs.getInt("maestria_grado"));
                    celdasPertinencia[13][cont + 3].setCellValue(rs.getInt("doctorado"));
                    celdasPertinencia[14][cont + 3].setCellValue(rs.getInt("doctorado_grado"));
                }
                
                celdasPertinencia[10][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
                celdasPertinencia[10][cont + 3].setCellFormula("SUM(C"+(cont+4)+":J"+(cont+4)+")");
                celdasPertinencia[15][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
                celdasPertinencia[15][cont + 3].setCellFormula("SUM(L"+(cont+4)+":O"+(cont+4)+")");
                celdasPertinencia[16][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
                celdasPertinencia[16][cont + 3].setCellFormula("K"+(cont+4)+"+P"+(cont+4));
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[0][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[4][cont + 3].setCellFormula("SUM(E5" + ":E" + (cont + 3) + ")");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[5][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[5][cont + 3].setCellFormula("SUM(F5" + ":F" + (cont + 3) + ")");
            celdasPertinencia[5][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[6][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[6][cont + 3].setCellFormula("SUM(G5" + ":G" + (cont + 3) + ")");
            celdasPertinencia[6][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[7][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[7][cont + 3].setCellFormula("SUM(H5" + ":H" + (cont + 3) + ")");
            celdasPertinencia[7][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[8][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[8][cont + 3].setCellFormula("SUM(I5" + ":I" + (cont + 3) + ")");
            celdasPertinencia[8][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[9][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[9][cont + 3].setCellFormula("SUM(J5" + ":J" + (cont + 3) + ")");
            celdasPertinencia[9][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[10][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[10][cont + 3].setCellFormula("SUM(K5" + ":K" + (cont + 3) + ")");
            celdasPertinencia[10][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[11][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[11][cont + 3].setCellFormula("SUM(L5" + ":L" + (cont + 3) + ")");
            celdasPertinencia[11][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[12][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[12][cont + 3].setCellFormula("SUM(M5" + ":M" + (cont + 3) + ")");
            celdasPertinencia[12][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[13][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[13][cont + 3].setCellFormula("SUM(N5" + ":N" + (cont + 3) + ")");
            celdasPertinencia[13][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[14][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[14][cont + 3].setCellFormula("SUM(O5" + ":O" + (cont + 3) + ")");
            celdasPertinencia[14][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[15][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[15][cont + 3].setCellFormula("SUM(P5" + ":P" + (cont + 3) + ")");
            celdasPertinencia[15][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[16][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[16][cont + 3].setCellFormula("SUM(Q5" + ":Q" + (cont + 3) + ")");
            celdasPertinencia[16][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 19--------------------------------
            * --------------------------------------------------------------------------------
            */
            pertinencia = wb.getSheetAt(18);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[5][200];
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 1;
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("23. PROFESORES DE TIEMPO COMPLETO CON POSGRADO, " + anio);
            celdasPertinencia[2][2].setCellValue(anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(normal);
                celdasPertinencia[3][3 + cont].setCellStyle(normal);
                celdasPertinencia[4][3 + cont].setCellStyle(numeroDecimal);
                consulta="SELECT (especialidad_grado + maestria_grado + doctorado + doctorado_grado) as posgrados FROM pertinenciain23 WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getInt("posgrados"));
                }
                consulta="SELECT prof_tc FROM datos_universidad WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[3][cont + 3].setCellValue(rs.getInt(1));
                }
                celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
                celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C5" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D5" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);            
            celdasPertinencia[4][cont + 3].setCellFormula("IF(D"+(cont+4)+">0,C"+(cont+4)+"/D"+ (cont + 4) + "*100,0)");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelineadecimal);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            
            
            
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 20--------------------------------
            * --------------------------------------------------------------------------------
            */
            pertinencia = wb.getSheetAt(19);
            filasPertinencia = new HSSFRow[200];
            celdasPertinencia = new HSSFCell[10][200];
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j < 199; j++) {
                    if (i == 0) {
                        filasPertinencia[j] = pertinencia.getRow(j);
                    }
                    celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                }
            }
            cont = 2;
            //LISTA UNIVERSIDADES
            //Titulo del libro seguido del año del MECASUT
            celdasPertinencia[0][0].setCellValue("23. PROFESORES DE TIEMPO COMPLETO CON POSGRADO, " + anio);
            for(int i=0;i<=ids.size()-1;i++){
                celdasPertinencia[0][3 + cont].setCellValue(cont-1);
                celdasPertinencia[1][3 + cont].setCellValue(univs.get(i).toString());
                //estilos
                celdasPertinencia[0][3 + cont].setCellStyle(normal);
                celdasPertinencia[1][3 + cont].setCellStyle(normal);
                celdasPertinencia[2][3 + cont].setCellStyle(normal);
                celdasPertinencia[3][3 + cont].setCellStyle(normal);
                celdasPertinencia[4][3 + cont].setCellStyle(normal);
                celdasPertinencia[5][3 + cont].setCellStyle(normal);
                celdasPertinencia[6][3 + cont].setCellStyle(normal);
                celdasPertinencia[7][3 + cont].setCellStyle(normal);
                celdasPertinencia[8][3 + cont].setCellStyle(normal);
                celdasPertinencia[9][3 + cont].setCellStyle(normal);
                //valores
                consulta="SELECT cap_competencias, cap_tutorias, apl_competencias, apl_tutorias, promep, becados, cuerpos_academicos FROM pertinenciain23"
                        .concat(" WHERE id_universidad=").concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[2][cont + 3].setCellValue(rs.getInt("cap_competencias"));
                    celdasPertinencia[3][cont + 3].setCellValue(rs.getInt("cap_tutorias"));
                    celdasPertinencia[4][cont + 3].setCellValue(rs.getInt("apl_competencias"));
                    celdasPertinencia[5][cont + 3].setCellValue(rs.getInt("apl_tutorias"));
                    celdasPertinencia[6][cont + 3].setCellValue(rs.getInt("promep"));
                    celdasPertinencia[7][cont + 3].setCellValue(rs.getInt("becados"));
                    celdasPertinencia[8][cont + 3].setCellValue(rs.getInt("cuerpos_academicos"));
                }
                consulta="SELECT prof_tc FROM datos_universidad WHERE id_universidad="
                        .concat(ids.get(i).toString()).concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                while (rs.next()){
                    celdasPertinencia[9][cont + 3].setCellValue(rs.getInt(1));
                }
                cont++;
            }
            pertinencia.shiftRows(198, pertinencia.getLastRowNum(), -1 * (200 - (cont + 5)), true, true);
            pertinencia.setForceFormulaRecalculation(true);
            celdasPertinencia[2][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[2][cont + 3].setCellFormula("SUM(C6" + ":C" + (cont + 3) + ")");
            celdasPertinencia[2][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[3][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[3][cont + 3].setCellFormula("SUM(D6" + ":D" + (cont + 3) + ")");
            celdasPertinencia[3][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[4][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[4][cont + 3].setCellFormula("SUM(E6" + ":E" + (cont + 3) + ")");
            celdasPertinencia[4][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[5][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[5][cont + 3].setCellFormula("SUM(F6" + ":F" + (cont + 3) + ")");
            celdasPertinencia[5][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[6][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[6][cont + 3].setCellFormula("SUM(G6" + ":G" + (cont + 3) + ")");
            celdasPertinencia[6][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[7][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[7][cont + 3].setCellFormula("SUM(H6" + ":H" + (cont + 3) + ")");
            celdasPertinencia[7][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[8][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[8][cont + 3].setCellFormula("SUM(I6" + ":I" + (cont + 3) + ")");
            celdasPertinencia[8][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[9][cont + 3].setCellType(HSSFCell.CELL_TYPE_FORMULA);         
            celdasPertinencia[9][cont + 3].setCellFormula("SUM(J6" + ":J" + (cont + 3) + ")");
            celdasPertinencia[9][cont + 3].setCellStyle(doublelinea);
            celdasPertinencia[0][cont + 4].setCellValue("FUENTE: MODELO DE EVALUACIÓN DE LA CALIDAD DEL SUBSISTEMA DE UNIVERSIDADES TECNOLÓGICAS, " + anio + ".");
            
            /*
            * --------------------------------------------------------------------------------
            * --------------------------------INICIA LA HOJA 21--------------------------------
            * --------------------------------------------------------------------------------
            */
            
            
            
            
            /*
             * ----------------------------------------------------------------------------------------------
             * ---------------------FINALIZACIÓN DE LAS HOJAS DE CÁLCULO DE EXCEL----------------------------
             * ----------------------------------------------------------------------------------------------
             */
            EnviarExcel(response, wb, "Formato Pertinencia " + anio);
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