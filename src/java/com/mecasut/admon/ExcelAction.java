/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Universidad;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Irvin Joshua Luévano García, Daniel Ramirez Torres
 */
public class ExcelAction extends org.apache.struts.action.Action {

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

        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }

        String universidad;
        String periodo;

        Universidad uni = new Universidad();
        if (request.getParameter("iun") != null) {
            universidad = request.getParameter("iun").toString();
            periodo = request.getParameter("ipe").toString();
        } else {
            universidad = sesion.getAttribute("idUniversidad").toString();
            periodo = String.valueOf(registroUniversidadForm.getPeriodoActual());
        }
        String ask = request.getParameter("ask");
        if (ask.equals("forllen")) {
            ConexionMySQL con = new ConexionMySQL();
            con.Conectar();
            String consulta = "Select nombre, apaterno, amaterno, tipo, cargo, telefono, mail from responsables_universidad ru inner join responsables r on ru.id_responsable = r.id_responsable where ru.tipo = 'RC' and ru.id_universidad = ".concat(universidad).concat(" and ru.id_periodo = ").concat(periodo);
            ResultSet rs = con.Consultar(consulta);
            //Si existe un responsable de captura empieza a generar el reporte
            if (rs.next()) {
                //Especifico en donde se encuentra el archivo de excel en forma relativa
                String rutaRelativaWeb = "/files/reporte_excel.xls";
                //Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
                String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);
                //Cargo en archivo en un Stream
                InputStream inp = new FileInputStream(rutaAbsolutaEnDisco);
                //Creo el libro de excel a partir de un archivo para su modificación
                HSSFWorkbook wb = new HSSFWorkbook(inp);
                inp.close();

//EFICACIA
                //Obtengo la hoja 1 y la asigno a un nuevo objeto
                HSSFSheet eficacia = wb.getSheetAt(0);
                int filas=1055;
                //Creo dos arreglos que van a contener las filas y las celdas
                //Son 701 por q es el numero de filas que hay en la hoja eficacia del libro real
                HSSFRow[] filasEficacia = new HSSFRow[filas];
                HSSFCell[][] celdasEficacia = new HSSFCell[14][filas];
                //Generamos todos los objetos para manipular las celdas de forma mas sencilla
                for (int i = 0; i <= 13; i++) {
                    for (int j = 0; j < eficacia.getLastRowNum(); j++) {
                        if (i == 0) {
                            filasEficacia[j] = eficacia.getRow(j);
                        }
                        celdasEficacia[i][j] = filasEficacia[j].getCell(i);
                    }
                }
                celdasEficacia[2][3].setCellValue(uni.getNombreUniversidad(universidad));
                celdasEficacia[2][4].setCellValue(rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno")));
                celdasEficacia[2][5].setCellValue(rs.getString("cargo"));
                celdasEficacia[2][6].setCellValue(rs.getString("mail"));
                celdasEficacia[2][7].setCellValue(rs.getString("telefono"));
//1
                consulta = "Select egresados_bachillerato, nuevo_ingreso, c1, c2, c3, aplica from eficaciain1 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    if (rs.getInt("aplica") == 1) {
                        for (int i = 1; i <= 5; i++) {
                            celdasEficacia[i][19].setCellValue(rs.getInt(i));
                        }
                    } else {
                        celdasEficacia[1][19].setCellValue("No se aplicó el EXANI");
                        eficacia.addMergedRegion(new CellRangeAddress(19, 19, 1, 5));
                    }
                }
//2            
//version 2004
                int restar = 0;
                consulta = "select id_pe,nombre_programa,version from programa_educativo where version='2004' and id_pe in (select id_pe from pe_universidad where id_nivel=2 and id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")"));
                rs = con.Consultar(consulta);
                int x;
                for (x = 1; rs.next(); x++) {
                    consulta = "select id_pe,sep, ene, may from eficaciain2 where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs.getString(1)));
                    ResultSet rs2 = con.Consultar(consulta);
                    celdasEficacia[1][31 + x].setCellValue(rs.getString(2));
                    while (rs2.next()) {
                        celdasEficacia[3][31 + x].setCellValue(rs2.getFloat(2));
                        celdasEficacia[4][31 + x].setCellValue(rs2.getFloat(3));
                        celdasEficacia[5][31 + x].setCellValue(rs2.getFloat(4));
                    }
                }
                restar += 21 - x;
//version !2004
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
                rs = con.Consultar(consulta);
                ResultSet rs2;
                int salto = 26;
                x = 0;
                while (rs.next()) {
                    consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and version!='2004' and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                    rs2 = con.Consultar(consulta);
                    while (rs2.next()) {
                        consulta = "select id_pe,sep, ene, may from eficaciain2 where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs2.getString(1)));
                        ResultSet rs3 = con.Consultar(consulta);
                        celdasEficacia[1][58 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(2));
                        if (rs3.next()) {
                            celdasEficacia[3][58 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getFloat(2));
                            celdasEficacia[4][58 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getFloat(3));
                            celdasEficacia[5][58 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getFloat(4));
                        }
                        x++;
                    }
                    restar += 20 - x;
                    x = 0;
                }
//3
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    salto = 9;
                    do {
                        consulta = "select mat_sep,rep_sep,mat_ene,rep_ene,mat_may,rep_may from eficaciain3 where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo).concat(" and id_nivel='").concat(rs.getString(1)).concat("'");
                        rs2 = con.Consultar(consulta);
                        if (rs2.next()) {
                            for (int i = 1; i <= 6; i++) {
                                celdasEficacia[i][189 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(i));
                            }
                        }
                    } while (rs.next());
                }
////4
//                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
//                rs = con.Consultar(consulta);
//                consulta = "Select id_causa, descripcion from causas_desercion where id_periodo=".concat(periodo);
//                rs2 = con.Consultar(consulta);
//                while (rs.next()) {
//                    salto = 26;
//                    x = 0;
//                    int i = 0;
//                    rs2.beforeFirst();
//                    while (rs2.next()) {
//                        consulta = "Select sep, ene, may from eficaciain4 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1)).concat(" and id_causa=").concat(rs2.getString(1));
//                        ResultSet rs3 = con.Consultar(consulta);
//                        celdasEficacia[1][238 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(2));
//                        if (rs3.next()) {
//                            celdasEficacia[4][238 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(1));
//                            celdasEficacia[5][238 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(2));
//                            celdasEficacia[6][238 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(3));
//                        }
//                        i++;
//                        x++;
//                    }
//                    //eficacia.shiftRows(258 - restar + (salto * (rs.getInt(1) - 1)), eficacia.getLastRowNum(), -1 * (20 - x), true, true);
//                    restar += 20 - x;
//                }
//
////5
//                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
//                rs = con.Consultar(consulta);
//                //Itera sobre los niveles que contiene la universidad
//                while (rs.next()) {
//                    //Si el nivel es TSU
//                    if (rs.getInt(1) == 1) {
//                        //Consulta los programas educativos del periodo anterior para el cuadro del periodo anterior
//                        consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=1 and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(String.valueOf(Integer.parseInt(periodo) - 1)).concat(")"));
//                        rs2 = con.Consultar(consulta);
//                        //Por cada programa:
//                        int i = 0;
//                        while (rs2.next()) {
//                            //Selecciona el modelo y la información 
//                            consulta = "select id_modelo, ingreso, egresados, titulados from eficaciain5 where id_universidad =".concat(universidad).concat(" and anio = 1 and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs2.getString(1)));
//                            ResultSet rs3 = con.Consultar(consulta);
//                            //Si existe información
//                            while (rs3.next()) {
//                                //Insertamos el nombre del programa educativo y la versión
//                                celdasEficacia[1][320 + i].setCellValue(rs2.getString(2).concat(" (").concat(rs2.getString(3)).concat(")"));
//                                //Evaluamos si es modelo 2x3 Regular o Despresurizado
//                                switch (rs3.getInt(1)) {
//                                    //Caso 2x3
//                                    case 1:
//                                        celdasEficacia[2][320 + i].setCellValue(rs3.getInt(2));
//                                        celdasEficacia[3][320 + i].setCellValue(rs3.getInt(3));
//                                        celdasEficacia[3][344 + i].setCellValue(rs3.getInt(4));
//                                        break;
//                                    //Caso Regular
//                                    case 2:
//                                        celdasEficacia[4][320 + i].setCellValue(rs3.getInt(2));
//                                        celdasEficacia[5][320 + i].setCellValue(rs3.getInt(3));
//                                        celdasEficacia[5][344 + i].setCellValue(rs3.getInt(4));
//                                        break;
//                                    //Caso despresurizado
//                                    case 3:
//                                        celdasEficacia[6][320 + i].setCellValue(rs3.getInt(2));
//                                        celdasEficacia[7][320 + i].setCellValue(rs3.getInt(3));
//                                        celdasEficacia[7][344 + i].setCellValue(rs3.getInt(4));
//                                        break;
//                                }
//                            }//iterador de datos periodo anterior
//                            i++;
//                        }//iterador de programas tsu periodo anterior
//                        //Consulta los programas educativos del periodo actual para el cuadro del periodo actual
//                        consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel= 1 and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")"));
//                        rs2 = con.Consultar(consulta);
//                        //Por cada programa:
//                        i = 0;
//                        while (rs2.next()) {
//                            //Selecciona el modelo y la información 
//                            consulta = "select id_modelo, ingreso, egresados, titulados from eficaciain5 where id_universidad =".concat(universidad).concat(" and anio = 2 and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs2.getString(1)));
//                            ResultSet rs3 = con.Consultar(consulta);
//                            //Si existe información
//                            while (rs3.next()) {
//                                //Insertamos el nombre del programa educativo y la versión
//                                celdasEficacia[1][372 + i].setCellValue(rs2.getString(2).concat(" (").concat(rs2.getString(3)).concat(")"));
//                                //Evaluamos si es modelo 2x3 Regular o Despresurizado
//                                switch (rs3.getInt(1)) {
//                                    //Caso 2x3
//                                    case 1:
//                                        celdasEficacia[2][372 + i].setCellValue(rs3.getInt(2));
//                                        celdasEficacia[3][372 + i].setCellValue(rs3.getInt(3));
//                                        celdasEficacia[3][396 + i].setCellValue(rs3.getInt(4));
//                                        break;
//                                    //Caso Regular
//                                    case 2:
//                                        celdasEficacia[4][372 + i].setCellValue(rs3.getInt(2));
//                                        celdasEficacia[5][372 + i].setCellValue(rs3.getInt(3));
//                                        celdasEficacia[5][396 + i].setCellValue(rs3.getInt(4));
//                                        break;
//                                    //Caso despresurizado
//                                    case 3:
//                                        celdasEficacia[6][372 + i].setCellValue(rs3.getInt(2));
//                                        celdasEficacia[7][372 + i].setCellValue(rs3.getInt(3));
//                                        celdasEficacia[7][396 + i].setCellValue(rs3.getInt(4));
//                                        break;
//                                }
//                            }//iterador de datos periodo actual
//                            i++;
//                        }//iterador de programas tsu periodo actual
//                    } else if (rs.getInt(1) == 2) {//es tsu?, no?, es licencia?
//                        //Consulta los programas educativos del periodo anterior para el cuadro del periodo anterior
//                        consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(rs.getString(1)).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =").concat(universidad).concat(" and id_periodo =".concat(String.valueOf(Integer.parseInt(periodo) - 1)).concat(")"));
//                        rs2 = con.Consultar(consulta);
//                        //Por cada programa:
//                        int i = 0;
//                        while (rs2.next()) {
//                            //Selecciona el modelo y la información 
//                            consulta = "select ingreso, egresados, titulados from eficaciain5 where id_universidad =".concat(universidad).concat(" and anio = 1 and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs2.getString(1)));
//                            ResultSet rs3 = con.Consultar(consulta);
//                            //Mientras exista información
//                            while (rs3.next()) {
//                                //Insertamos el nombre del programa educativo y la versión
//                                celdasEficacia[1][422 + i].setCellValue(rs2.getString(2).concat(" (").concat(rs2.getString(3)).concat(")"));
//                                celdasEficacia[2][422 + i].setCellValue(rs3.getInt(1));
//                                celdasEficacia[3][422 + i].setCellValue(rs3.getInt(2));
//                                celdasEficacia[3][445 + i].setCellValue(rs3.getInt(3));
//                            }//iterador de datos periodo anterior
//                            i++;
//                        }//iterador de programas LP periodo anterior
//
//                        //Consulta los programas educativos del periodo actual para el cuadro del periodo actual
//                        consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(rs.getString(1)).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =").concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")"));
//                        rs2 = con.Consultar(consulta);
//                        //Por cada programa:
//                        i = 0;
//                        while (rs2.next()) {
//                            //Selecciona el modelo y la información 
//                            consulta = "select ingreso, egresados, titulados from eficaciain5 where id_universidad =".concat(universidad).concat(" and anio = 2 and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs2.getString(1)));
//                            ResultSet rs3 = con.Consultar(consulta);
//                            //Mientras exista información
//                            while (rs3.next()) {
//                                //Insertamos el nombre del programa educativo y la versión
//                                celdasEficacia[1][471 + i].setCellValue(rs2.getString(2).concat(" (").concat(rs2.getString(3)).concat(")"));
//                                celdasEficacia[2][471 + i].setCellValue(rs3.getInt(1));
//                                celdasEficacia[3][471 + i].setCellValue(rs3.getInt(2));
//                                celdasEficacia[3][494 + i].setCellValue(rs3.getInt(3));
//                            }//iterador de datos periodo actual
//                            i++;
//                        }//iterador de programas LP periodo actual
//                    } else if (rs.getInt(1) == 3) {//es licencia?, no?, es Ing?
//                        //Consulta los programas educativos del periodo anterior para el cuadro del periodo anterior
//                        consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(rs.getString(1)).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =").concat(universidad).concat(" and id_periodo =".concat(String.valueOf(Integer.parseInt(periodo) - 1)).concat(")"));
//                        rs2 = con.Consultar(consulta);
//                        //Por cada programa:
//                        int i = 0;
//                        while (rs2.next()) {
//                            //Selecciona el modelo y la información 
//                            consulta = "select ingreso, egresados, titulados from eficaciain5 where id_universidad =".concat(universidad).concat(" and anio = 1 and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs2.getString(1)));
//                            ResultSet rs3 = con.Consultar(consulta);
//                            //Mientras exista información
//                            while (rs3.next()) {
//                                //Insertamos el nombre del programa educativo y la versión
//                                celdasEficacia[1][520 + i].setCellValue(rs2.getString(2).concat(" (").concat(rs2.getString(3)).concat(")"));
//                                celdasEficacia[2][520 + i].setCellValue(rs3.getInt(1));
//                                celdasEficacia[3][520 + i].setCellValue(rs3.getInt(2));
//                                celdasEficacia[3][543 + i].setCellValue(rs3.getInt(3));
//                            }//iterador de datos periodo anterior
//                            i++;
//                        }//iterador de programas Lic. periodo anterior
//                        //Consulta los programas educativos del periodo actual para el cuadro del periodo actual
//                        consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(rs.getString(1)).concat(" and id_pe in (select id_pe from pe_universidad where id_universidad =").concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")"));
//                        rs2 = con.Consultar(consulta);
//                        //Por cada programa:
//                        i = 0;
//                        while (rs2.next()) {
//                            //Selecciona el modelo y la información 
//                            consulta = "select ingreso, egresados, titulados from eficaciain5 where id_universidad =".concat(universidad).concat(" and anio = 2 and id_periodo =".concat(periodo).concat(" and id_pe=").concat(rs2.getString(1)));
//                            ResultSet rs3 = con.Consultar(consulta);
//                            //Mientras exista información
//                            while (rs3.next()) {
//                                //Insertamos el nombre del programa educativo y la versión
//                                celdasEficacia[1][569 + i].setCellValue(rs2.getString(2).concat(" (").concat(rs2.getString(3)).concat(")"));
//                                celdasEficacia[2][569 + i].setCellValue(rs3.getInt(1));
//                                celdasEficacia[3][569 + i].setCellValue(rs3.getInt(2));
//                                celdasEficacia[3][592 + i].setCellValue(rs3.getInt(3));
//                            }//iterador de datos periodo actual
//                            i++;
//                        }//iterador de programas Ing. periodo actual
//                    }
//                }//iterador de niveles
////5_1
//                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
//                rs = con.Consultar(consulta);
//                if (rs.next()) {
//                    salto = 25;
//                    do {
//                        consulta = "select anio, titulados_universidad, titulados_dgp from eficaciain5_1 where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1));
//                        rs2 = con.Consultar(consulta);
//                        for (int i = 0;rs2.next();i++) {
//                            celdasEficacia[1][618 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(1).concat(" - ").concat(String.valueOf(rs2.getInt(1) + 2)));
//                            celdasEficacia[2][618 + i +(salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(2));
//                            celdasEficacia[4][618 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(3));
//                        }
//                    } while (rs.next());
//                }
////6
//                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
//                rs = con.Consultar(consulta);
//                if (rs.next()) {
//                    salto = 10;
//                    do {
//                        consulta = "select e_trabajando, afin from eficaciain6 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("' and id_nivel = '").concat(String.valueOf(rs.getInt(1))).concat("'");
//                        rs2 = con.Consultar(consulta);
//                        if (rs2.next()) {
//                            celdasEficacia[1][697 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(1));
//                            celdasEficacia[4][697 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(2));
//                        }
//                    } while (rs.next());
//                }
////7
//                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
//                rs = con.Consultar(consulta);
//                salto = 29;
//                while (rs.next()) {
//                consulta = "select id_pregunta, pregunta from encuesta_preguntas where id_encuesta=(select id_encuesta from encuestas where id_nivel=".concat(rs.getString(1)).concat(" and encuesta_preguntas.id_encuesta=encuestas.id_encuesta and indicador=7) and activo=1");
//                rs2 = con.Consultar(consulta);
//                   // System.out.println("Entrando a Nivel: " + rs.getString(1));
//                    x = 0;
//                    rs2.beforeFirst();
//                    for (int i = 0; rs2.next(); i++) {
//                      //  System.out.println("Nivel: " + rs.getString(1));
//                      //  System.out.println("Pregunta: " + rs2.getString("id_pregunta"));
//                        consulta = "select r_a, r_b, r_c, r_d, r_e, r_f, r_g from eficaciain7_10 where id_universidad = ".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pregunta=").concat(rs2.getString(1));
//                        ResultSet rs3 = con.Consultar(consulta);
//                        if (rs3.next()) {
//                        //    System.out.println(rs2.getString(2));
//                            celdasEficacia[1][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(2));
//                            celdasEficacia[2][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(1));
//                            celdasEficacia[3][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(2));
//                            celdasEficacia[4][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(3));
//                            celdasEficacia[5][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(4));
//                            celdasEficacia[6][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(5));
//                            celdasEficacia[7][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(6));
//                            celdasEficacia[8][733 + i + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(7));
//                        }
//                        x++;
//                    }
//                    //eficacia.shiftRows(753 - restar + (salto * (rs.getInt(1) - 1)), eficacia.getLastRowNum(), -1 * (20 - x), true, true);
//                    restar += 20 - x;
//                }
////8
//                salto = 11;
//                consulta = "select year(fecha_inicio) from periodos where id_periodo='".concat(periodo).concat("'");
//                ResultSet rss = con.Consultar(consulta);
//                String consulta1 = "Select distinct(pe.id_nivel),nombre, descripcion, abreviatura from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad=".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
//                rs = con.Consultar(consulta1);
//                if (rss.next()) {
//                    //Se agrega el año del periodo actual 
//                    while (rs.next()) {
//                        if (rs.getString(1).equals("3") || rs.getString(1).equals("1") || rs.getString(1).equals("2")) {
//                            String consulta2 = "select sobresaliente,destacado,sin_testimonio,aplica from eficaciain8 where id_universidad=".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(" and id_nivel='").concat(rs.getString("id_nivel")).concat("'"));
//                            rs2 = con.Consultar(consulta2);
//                            while (rs2.next()) {
//                                celdasEficacia[3][825 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(1));
//                                celdasEficacia[4][825 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(2));
//                                celdasEficacia[5][825 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(3));
//                            }
//                        }
//                    }
//                    //egetsu
//                    celdasEficacia[1][824].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" que presentaron el EGETSU"));
//                    celdasEficacia[2][824].setCellValue("Egresados en agosto ".concat(rss.getString(1)));
//                    celdasEficacia[3][824].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" con testimonio de desempeño sobresaliente del EGETSU"));
//                    celdasEficacia[4][824].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" con testimonio de desempeño satisfactorio del EGETSU"));
//                    celdasEficacia[5][824].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" sin testimonio del EGETSU"));
//                    //lic prof
//                    celdasEficacia[1][835].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" que presentaron el examen de egreso"));
//                    celdasEficacia[2][835].setCellValue("Egresados en agosto ".concat(rss.getString(1)));
//                    celdasEficacia[4][835].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" con puntaje medio"));
//                    celdasEficacia[3][835].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" con puntaje alto"));
//                    celdasEficacia[5][835].setCellValue("Egresados en agosto ".concat(rss.getString(1)).concat(" con puntaje alto"));
//                    //EGEL
//                    celdasEficacia[1][846].setCellValue("Egresados en abril ".concat(rss.getString(1)).concat(" que presentaron el EGEL"));
//                    celdasEficacia[2][846].setCellValue("Egresados en abril ".concat(rss.getString(1)));
//                    celdasEficacia[4][846].setCellValue("Egresados en abril ".concat(rss.getString(1)).concat(" con testimonio de desempeño sobresaliente del EGEL"));
//                    celdasEficacia[3][846].setCellValue("Egresados en abril ".concat(rss.getString(1)).concat(" con testimonio de desempeño satisfactorio del EGEL"));
//                    celdasEficacia[5][846].setCellValue("Egresados en abril ".concat(rss.getString(1)).concat(" sin testimonio del EGEL"));
//                }
////9
//                salto = 15;
//                consulta = "select year(fecha_inicio) from periodos where id_periodo='".concat(periodo).concat("'");
//                rss = con.Consultar(consulta);
//                consulta1 = "Select distinct(pe.id_nivel),nombre, descripcion, abreviatura from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad=".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(") order by pe.id_nivel"));
//                rs = con.Consultar(consulta1);
//                if (rss.next()) {
//                    //Se agrega el año del periodo actual 
//                    while (rs.next()) {
//                        if (rs.getString(1).equals("1")) {
//                            String consulta2 = "select egre_continuan,egre_continuan_ut,egre_continuan_ut_con from eficaciain9 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("' and id_nivel=").concat(rs.getString(1));
//                            rs2 = con.Consultar(consulta2);
//                            while (rs2.next()) {
//                                celdasEficacia[1][860].setCellValue(rs2.getInt(1));
//                                celdasEficacia[1][866].setCellValue(rs2.getInt(2));
//                                celdasEficacia[2][866].setCellValue(rs2.getInt(3));
//                            }
//                        }
//                        if (!rs.getString(1).equals("1") && !rs.getString(1).equals("3") && !rs.getString(1).equals("4")) {
//                            String consulta2 = "select egre_continuan,egre_continuan_ut,egre_continuan_ut_con from eficaciain9 where id_universidad ='".concat(universidad).concat("' and id_periodo ='").concat(periodo).concat("' and id_nivel=").concat(rs.getString(1));
//                            rs2 = con.Consultar(consulta2);
//                            while (rs2.next()) {
//                                celdasEficacia[1][874 + (salto * (rs.getInt(1) - 2))].setCellValue(rs2.getInt(1));
//                                celdasEficacia[1][880 + (salto * (rs.getInt(1) - 2))].setCellValue(rs2.getInt(2));
//                               // System.out.println(874 + (salto * (rs.getInt(1) - 2)));
//                            }
//                        }
//                    }
//                    //tsu
//                    celdasEficacia[1][859].setCellValue("Egresados de TSU de agosto ".concat(rss.getString(1)).concat(" que continuan estudios superiores"));
//                    celdasEficacia[2][859].setCellValue("Egresados de TSU en agosto ".concat(rss.getString(1)));
//                    celdasEficacia[1][865].setCellValue("Egresados de TSU en agosto ".concat(rss.getString(1)).concat(" que continuan estudios superiores de licencia profesional en una UT"));
//                    celdasEficacia[2][865].setCellValue("Egresados de TSU de agosto ".concat(rss.getString(1)).concat(" que continuan estudios superiores de licenciatura en una UT"));
//                    //LP
//                    celdasEficacia[1][879].setCellValue("Egresados de LP de agosto ".concat(rss.getString(1)).concat(" que continuan estudios superiores de licenciatura en una UT"));
//                    celdasEficacia[2][879].setCellValue("Egresados de LP en agosto ".concat(rss.getString(1)));
//                    celdasEficacia[1][873].setCellValue("Egresados de LP de agosto ".concat(rss.getString(1)).concat(" que continuan estudios superiores"));
//                    celdasEficacia[2][873].setCellValue("Egresados de LP de agosto ".concat(rss.getString(1)));
//
//                }
////10
//                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
//                rs = con.Consultar(consulta);
//                salto = 28;
//                while (rs.next()) {
//                consulta = "select id_pregunta, pregunta from encuesta_preguntas where id_encuesta=(select id_encuesta from encuestas where id_nivel=".concat(rs.getString(1)).concat(" and encuesta_preguntas.id_encuesta=encuestas.id_encuesta and indicador=10) and activo=1");
//                rs2 = con.Consultar(consulta);
//                    x = 0;
//                    rs2.beforeFirst();
//                    for (int i = 0; rs2.next(); i++) {
//                        consulta = "select r_a, r_b, r_c, r_d, r_e, r_f, r_g from eficaciain7_10 where id_universidad = ".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pregunta=").concat(rs2.getString(1));
//                        ResultSet rs3 = con.Consultar(consulta);
//                        if (rs3.next()) {
//                         //   System.out.println(rs2.getString(2));
//                            celdasEficacia[1][892 + i].setCellValue(rs2.getString(2));
//                            celdasEficacia[2][892 + i].setCellValue(rs3.getInt(1));
//                            celdasEficacia[3][892 + i].setCellValue(rs3.getInt(2));
//                            celdasEficacia[4][892 + i].setCellValue(rs3.getInt(3));
//                            celdasEficacia[5][892 + i].setCellValue(rs3.getInt(4));
//                            celdasEficacia[6][892 + i].setCellValue(rs3.getInt(5));
//                            celdasEficacia[7][892 + i].setCellValue(rs3.getInt(6));
//                            celdasEficacia[8][892 + i].setCellValue(rs3.getInt(7));
//                        }
//                        x++;
//                    }
//                    //eficacia.shiftRows(912 - restar + (salto * (rs.getInt(1) - 1)), eficacia.getLastRowNum(), -1 * (20 - x), true, true);
//                    restar += 20 - x;
//                }
////11
//                consulta = "Select ejercido, autorizado from eficaciain11 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
//                rs = con.Consultar(consulta);
//                if (rs.next()) {
//                    celdasEficacia[1][981].setCellValue(rs.getDouble(1));
//                    celdasEficacia[2][981].setCellValue(rs.getDouble(2));
//                } else {
//                    celdasEficacia[1][981].setCellValue(0);
//                    celdasEficacia[2][981].setCellValue(0);
//                }
//                celdasEficacia = null;
//                filasEficacia = null;
//FIN EFICACIA

//EFICIENCIA
                HSSFSheet eficiencia = wb.getSheetAt(1);
                HSSFRow[] filaseficiencia = new HSSFRow[128];
                HSSFCell[][] celdaseficiencia = new HSSFCell[9][128];

                for (int i = 0; i <= 8; i++) {
                    for (int j = 0; j <= 127; j++) {
                        if (i == 0) {
                            filaseficiencia[j] = eficiencia.getRow(j);
                            if (filaseficiencia[j] == null) {
                                filaseficiencia[j] = eficiencia.createRow(j);
                            }
                        }
                        celdaseficiencia[i][j] = filaseficiencia[j].getCell(i);
                    }
                }
                restar = 0;
//12
                String consultar = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                ResultSet rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdaseficiencia[2][7].setCellValue(rs1.getString(1));
                } else {
                    celdaseficiencia[2][7].setCellValue(0);
                }
//13
                consulta = "Select id_edificio,descripcion,capacidad from edificios where id_edificio=(select id_edificio from edificios_universidad where id_universidad=".concat(universidad).concat(" and id_periodo=".concat(periodo).concat(" and edificios_universidad.id_edificio=edificios.id_edificio order by id_edificio)"));
                rs2 = con.Consultar(consulta);
                x = 0;
                while (rs2.next()) {
                    consulta = "Select no_laboratorios, espacio_academico, observaciones from eficienciain13 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_edifi=").concat(rs2.getString(1));
                    ResultSet rs3 = con.Consultar(consulta);
                    celdaseficiencia[0][17 + x].setCellValue(rs2.getString(2));
                    celdaseficiencia[2][17 + x].setCellValue(rs2.getInt(3));
                    if (rs3.next()) {
                        celdaseficiencia[3][17 + x].setCellValue(rs3.getInt(1));
                        celdaseficiencia[5][17 + x].setCellValue(rs3.getInt(2));
                        celdaseficiencia[7][17 + x].setCellValue(rs3.getString(3));
                    }
                    x++;
                }
                eficiencia.shiftRows(32, eficiencia.getLastRowNum(), -1 * (15 - x), true, true);
                restar += 15 - x;
                consulta = "Select turno_matutino, turno_vespertino from eficienciain13_1 where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    celdaseficiencia[2][38].setCellValue(rs.getInt(1));
                    celdaseficiencia[3][38].setCellValue(rs.getInt(2));
                }
//14
                consultar = "SELECT * FROM eficienciain14 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdaseficiencia[2][65].setCellValue(rs1.getInt(3));
                    celdaseficiencia[3][65].setCellValue(rs1.getInt(4));
                    celdaseficiencia[4][65].setCellValue(rs1.getInt(5));
                    celdaseficiencia[6][65].setCellValue(rs1.getInt(6));
                    celdaseficiencia[7][65].setCellValue(rs1.getInt(7));
                    celdaseficiencia[2][69].setCellValue(rs1.getInt(8));
                    celdaseficiencia[3][69].setCellValue(rs1.getInt(9));
                    celdaseficiencia[4][69].setCellValue(rs1.getInt(10));
                    celdaseficiencia[6][69].setCellValue(rs1.getInt(11));
                    celdaseficiencia[7][69].setCellValue(rs1.getInt(12));
                } else {
                    celdaseficiencia[2][65].setCellValue(0);
                    celdaseficiencia[3][65].setCellValue(0);
                    celdaseficiencia[4][65].setCellValue(0);
                    celdaseficiencia[6][65].setCellValue(0);
                    celdaseficiencia[7][65].setCellValue(0);
                    celdaseficiencia[2][69].setCellValue(0);
                    celdaseficiencia[3][69].setCellValue(0);
                    celdaseficiencia[4][69].setCellValue(0);
                    celdaseficiencia[6][69].setCellValue(0);
                    celdaseficiencia[7][69].setCellValue(0);
                }
//15 
//otras certificaciones
                consultar = "SELECT id_certificacion,nombre_certificacion FROM certificaciones WHERE activo=1";
                rs1 = con.Consultar(consultar);
                x = 0;
                while (rs1.next()) {
                    if (rs1.getInt(1) != 1) {
                        String consultar2 = "SELECT sin_certificado,en_proceso,con_certificado,en_proceso_recertificacion,con_recertificado FROM eficienciain15 WHERE id_universidad='".concat(universidad).concat("' and id_periodo='").concat(periodo).concat("' and id_certificacion=").concat(rs1.getString(1));
                        rs2 = con.Consultar(consultar2);
                        while (rs2.next()) {
                            if (rs2.getInt(1) == 1) {
                                celdaseficiencia[5][77 + x].setCellValue("Sin certificado");
                            }
                            if (rs2.getInt(2) == 1) {
                                celdaseficiencia[5][77 + x].setCellValue("En proceso");
                            }
                            if (rs2.getInt(3) == 1) {
                                celdaseficiencia[5][77 + x].setCellValue("Con certificado");
                            }
                            if (rs2.getInt(4) == 1) {
                                celdaseficiencia[5][77 + x].setCellValue("En proceso recertificación");
                            }
                            if (rs2.getInt(5) == 1) {
                                celdaseficiencia[5][77 + x].setCellValue("Con recertificación");
                            }
                            if (rs2.getInt(1) == 0 && rs2.getInt(2) == 0 && rs2.getInt(3) == 0 && rs2.getInt(4) == 0 && rs2.getInt(5) == 0) {
                                celdaseficiencia[5][77 + x].setCellValue("Sin certificado");
                            }
                        }
                        celdaseficiencia[1][77 + x].setCellValue(rs1.getString(2));
                    }
                    x++;
                }
//iso 9000
                consultar = "SELECT sin_certificado,en_proceso,con_certificado,en_proceso_recertificacion,con_recertificado FROM eficienciain15 WHERE id_universidad='".concat(universidad).concat("' and id_periodo='").concat(periodo).concat("' and id_certificacion=1");
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    if (rs1.getInt(1) == 1) {
                        celdaseficiencia[1][94].setCellValue("X");
                    }
                    if (rs1.getInt(2) == 1) {
                        celdaseficiencia[2][94].setCellValue("X");
                    }
                    if (rs1.getInt(3) == 1) {
                        celdaseficiencia[3][94].setCellValue("X");
                    }
                    if (rs1.getInt(4) == 1) {
                        celdaseficiencia[4][94].setCellValue("X");
                    }
                    if (rs1.getInt(5) == 1) {
                        celdaseficiencia[4][94].setCellValue("X");
                    }

                }
                for (int i = 1; i <= 6; i++) {
                    consultar = "SELECT opcion,periodo_inicio,periodo_fin,proxima_auditoria FROM eficienciain15_1 WHERE id_universidad='".concat(universidad).concat("' and id_periodo='").concat(periodo).concat("' and num_fila=").concat(String.valueOf(i));
                    rs1 = con.Consultar(consultar);
                    if (rs1.next()) {
                        celdaseficiencia[3][99 + i].setCellValue(rs1.getInt(1));
                        celdaseficiencia[4][99 + i].setCellValue(rs1.getString(2) + " al " + rs1.getString(3));
                        celdaseficiencia[7][99 + i].setCellValue(rs1.getString(4));
                    }
                }
//16
                consultar = "SELECT libros, titulos, revistas, bibliotecas FROM eficienciain16 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdaseficiencia[1][114].setCellValue(rs1.getInt(1));
                    celdaseficiencia[2][114].setCellValue(rs1.getInt(2));
                    celdaseficiencia[5][114].setCellValue(rs1.getInt(3));
                    celdaseficiencia[7][114].setCellValue(rs1.getInt(4));
                } else {
                    celdaseficiencia[1][114].setCellValue(0);
                    celdaseficiencia[2][114].setCellValue(0);
                    celdaseficiencia[5][114].setCellValue(0);
                    celdaseficiencia[7][114].setCellValue(0);
                }
//17
                consultar = "SELECT matricula_total,prof_tc FROM datos_universidad WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdaseficiencia[3][114].setCellValue(rs1.getInt(1));
                    celdaseficiencia[1][126].setCellValue(rs1.getInt(1));
                    celdaseficiencia[2][126].setCellValue(rs1.getInt(2));
                } else {
                    celdaseficiencia[3][114].setCellValue("Matricula no registrada");
                    celdaseficiencia[1][126].setCellValue("Matricula no registrada");
                    celdaseficiencia[2][126].setCellValue(0);
                }
                filaseficiencia = null;
                celdaseficiencia = null;
//FIN EFICIENCIA

//PERTINENCIA

                HSSFSheet pertinencia = wb.getSheetAt(2);
                HSSFRow[] filasPertinencia = new HSSFRow[852];
                HSSFCell[][] celdasPertinencia = new HSSFCell[15][852];
                for (int i = 0; i <= 14; i++) {
                    for (int j = 0; j < 851; j++) {
                        if (i == 0) {
                            filasPertinencia[j] = pertinencia.getRow(j);
                        }
                        celdasPertinencia[i][j] = filasPertinencia[j].getCell(i);
                    }
                }
//18
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
                rs = con.Consultar(consulta);
                salto = 63;
                x = 0;
                int xx = 0;
                restar = 0;
                int restar1 = 0;
                while (rs.next()) {
                    String programas = "SELECT id_pe,nombre_programa FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(rs.getString(1))).concat(" AND id_pe IN (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                    rs1 = con.Consultar(programas);
                    while (rs1.next()) {
                        celdasPertinencia[1][9 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs1.getString(2));
                        celdasPertinencia[1][35 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs1.getString(2));
                        consulta = "Select evaluacion_diagnostica,val_1,val_2,val_3,vigencia_evaluacion,fecha_evaluacion from pertinenciain18 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(rs1.getString("id_pe")).concat(" order by id_pe");
                        ResultSet rs3 = con.Consultar(consulta);
                        if (rs3.next()) {
                            celdasPertinencia[4][9 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(1));
                            celdasPertinencia[5][9 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(2));
                            celdasPertinencia[6][9 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(3));
                            celdasPertinencia[7][9 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(4));
                            celdasPertinencia[8][9 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(5));
                            celdasPertinencia[9][9 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(6));
                            x++;
                        }
                        String consulta2 = "Select matricula_inicial,matricula_sin_eva,matricula_con_eva,val_1,val_2,val_3 from pertinenciain18_1 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(rs1.getString("id_pe")).concat(" order by id_pe");
                        ResultSet rs4 = con.Consultar(consulta2);
                        if (rs4.next()) {
                            celdasPertinencia[4][35 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs4.getInt(1));
                            celdasPertinencia[5][35 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs4.getInt(2));
                            celdasPertinencia[6][35 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs4.getInt(3));
                            celdasPertinencia[7][35 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs4.getInt(4));
                            celdasPertinencia[8][35 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs4.getString(5));
                            celdasPertinencia[9][35 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs4.getString(6));
                            xx++;
                        }
                    }
                    restar1 += 20 - xx;
                    restar += 20 - x;
                    xx = 0;
                    x = 0;
                }

//19
                //nacionales
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
                rs = con.Consultar(consulta);
                salto = 32;
                x = 0;
                restar = 0;
                while (rs.next()) {
                    String programas = "SELECT id_pe,nombre_programa FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(rs.getString(1))).concat(" AND id_pe IN (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                    rs1 = con.Consultar(programas);
                    while (rs1.next()) {
                        celdasPertinencia[1][200 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs1.getString(2));
                        consulta = "Select matricula_inicial_aten,matricula_acreditada,acreditados,id_organismo,fecha_acreditacion,vigencia_acreditacion from pertinenciain19 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(String.valueOf(rs1.getString(1))).concat(" order by id_pe");
                        ResultSet rs3 = con.Consultar(consulta);
                        while (rs3.next()) {
                            celdasPertinencia[4][200 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(1));
                            celdasPertinencia[5][200 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(2));
                            celdasPertinencia[6][200 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(3));
                            consulta = "select nombre from organismos_acreditacion where id_organismo=".concat(rs3.getString(4)).concat(" and extranjero=0");
                            rs2 = con.Consultar(consulta);
                            if (rs2.next()) {
                                celdasPertinencia[7][200 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(1));
                            }
                            celdasPertinencia[8][200 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(5));
                            celdasPertinencia[9][200 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(6));
                        }
                        x++;
                    }
                    // pertinencia.shiftRows(220 - restar + (salto * (rs.getInt(1) - 1)), pertinencia.getLastRowNum(), -1 * (20 - x), true, true);
                    // System.out.println(220 - restar + (salto * (rs.getInt(1) - 1)) + "nacionales");
                    restar += 20 - x;
                    x = 0;
                }
//internacionales
                //19
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
                rs = con.Consultar(consulta);
                salto = 32;
                xx = 0;
                restar = 0;
                while (rs.next()) {
                    String programas = "SELECT id_pe,nombre_programa FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(rs.getString(1))).concat(" AND id_pe IN (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                    rs1 = con.Consultar(programas);
                    while (rs1.next()) {
                        celdasPertinencia[1][301 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs1.getString(2));
                        consulta = "Select matricula_inicial_aten,matricula_acreditada,acreditados,id_organismo,fecha_acreditacion,vigencia_acreditacion from pertinenciain19_1 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(String.valueOf(rs1.getString(1))).concat(" order by id_pe");
                        ResultSet rs3 = con.Consultar(consulta);
                        if (rs3.next()) {
                            celdasPertinencia[4][301 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(1));
                            celdasPertinencia[5][301 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(2));
                            celdasPertinencia[6][301 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(3));
                            consulta = "select nombre from organismos_acreditacion where id_organismo=".concat(rs3.getString(4)).concat(" and extranjero=1");
                            rs2 = con.Consultar(consulta);
                            if (rs2.next()) {
                                celdasPertinencia[7][301 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(1));
                            }
                            celdasPertinencia[8][301 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(5));
                            celdasPertinencia[9][301 + xx + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(6));
                        }
                        xx++;
                    }
                    //  pertinencia.shiftRows(320 - restar + (salto * (rs.getInt(1) - 1)), pertinencia.getLastRowNum(), -1 * (20 - xx), true, true);
                    //pertinencia.shiftRows(321 - ((20*niv) - c) - restar + (salto * (rs.getInt(1) - 1)), pertinencia.getLastRowNum(), -1 * (20 - xx), true, true);
                    restar += 20 - xx;
                    xx = 0;
                }

//20
                consulta = "Select * from pertinenciain20 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    for (int i = 0; i <= 4; i++) {
                        celdasPertinencia[i][407].setCellValue(rs.getInt(i + 3));
                    }
                    for (int i = 0; i <= 4; i++) {
                        celdasPertinencia[i][414].setCellValue(rs.getInt(i + 8));
                    }
                    for (int i = 0; i <= 4; i++) {
                        celdasPertinencia[i][421].setCellValue(rs.getInt(i + 13));
                    }
                    for (int i = 0; i <= 2; i++) {
                        celdasPertinencia[i][427].setCellValue(rs.getInt(i + 18));
                    }
                    /*for (int i = 0; i <= 4; i++) {
                     celdasPertinencia[i][433].setCellValue(rs.getInt(i + 21));
                     }*/
                    for (int i = 0; i <= 2; i++) {
                        celdasPertinencia[i][439].setCellValue(rs.getInt(i + 26));
                    }
                    celdasPertinencia[4][439].setCellValue(rs.getInt(29));
                    for (int i = 0; i <= 2; i++) {
                        celdasPertinencia[i][446].setCellValue(rs.getInt(i + 30));
                    }
                    celdasPertinencia[4][446].setCellValue(rs.getInt(33));
                } else {
                    for (int i = 0; i <= 4; i++) {
                        celdasPertinencia[i][407].setCellValue(0);
                    }
                    for (int i = 0; i <= 4; i++) {
                        celdasPertinencia[i][414].setCellValue(0);
                    }
                    for (int i = 0; i <= 4; i++) {
                        celdasPertinencia[i][421].setCellValue(0);
                    }
                    for (int i = 0; i <= 2; i++) {
                        celdasPertinencia[i][427].setCellValue(0);
                    }
                    for (int i = 0; i <= 4; i++) {
                        celdasPertinencia[i][433].setCellValue(0);
                    }
                    for (int i = 0; i <= 2; i++) {
                        celdasPertinencia[i][439].setCellValue(0);
                    }
                    celdasPertinencia[4][439].setCellValue(0);
                    for (int i = 0; i <= 2; i++) {
                        celdasPertinencia[i][446].setCellValue(0);
                    }
                    celdasPertinencia[4][446].setCellValue(0);
                }
//21      
                consulta = "select id_servicio from servicios_universidad where id_universidad=".concat(String.valueOf(universidad)).concat(" and id_periodo=").concat(String.valueOf(periodo));
                rs = con.Consultar(consulta);
                int noServicios = 0;
                String consulta1 = "select count(id_servicio) from encuesta_servicios";
                rs1 = con.Consultar(consulta1);
                if (rs1.next()) {
                    noServicios = rs1.getInt(1);
                }
                rs.next();
                for (int i = 1; i <= noServicios; i++) {
                    if (rs.getInt(1) == i) {
                        celdasPertinencia[3][455 + i].setCellValue(1);
                        rs.next();
                    } else {
                        celdasPertinencia[4][455 + i].setCellValue(1);
                    }
                }
                String servicios = "SELECT a.id_servicio, b.nombre, b.descripcion FROM servicios_universidad a INNER JOIN encuesta_servicios b ON a.id_servicio=b.id_servicio WHERE a.id_universidad=".concat(String.valueOf(universidad)).concat(" and a.id_periodo=").concat(String.valueOf(periodo));
                ResultSet serv = con.Consultar(servicios);
                String preguntas = "";
                ResultSet preg;
                int poPreg = 0;
                salto = 11;
                while (serv.next()) {
                    preguntas = "SELECT id_pregunta, pregunta FROM encuesta_preguntas WHERE id_encuesta=3 AND id_servicio=".concat(serv.getString(1));
                    preg = con.Consultar(preguntas);
                    celdasPertinencia[0][478 + (salto * (serv.getInt(1) - 1))].setCellValue(serv.getString(3));
                    while (preg.next()) {
                        String respuestas = "SELECT r_a, r_b, r_c, r_d, r_e, r_f, r_g FROM pertinenciain21 WHERE id_pregunta=".concat(preg.getString("id_pregunta").concat(" and id_universidad=").concat(String.valueOf(universidad)).concat(" and id_periodo=").concat(String.valueOf(periodo)));
                        ResultSet resp = con.Consultar(respuestas);
                        celdasPertinencia[1][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(preg.getString(2));
                        if (resp.next()) {
                            celdasPertinencia[3][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(resp.getInt(1));
                            celdasPertinencia[4][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(resp.getInt(2));
                            celdasPertinencia[5][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(resp.getInt(3));
                            celdasPertinencia[6][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(resp.getInt(4));
                            celdasPertinencia[7][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(resp.getInt(5));
                            celdasPertinencia[8][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(resp.getInt(6));
                            celdasPertinencia[9][478 + poPreg + (salto * (serv.getInt(1) - 1))].setCellValue(resp.getInt(7));
                        }
                        poPreg++;
                    }
                    poPreg = 0;
                }


//22            
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
                rs = con.Consultar(consulta);
                salto = 35;
                x = 0;
                restar = 0;
                while (rs.next()) {
                    String programas = "SELECT id_pe,nombre_programa FROM programa_educativo WHERE id_nivel=".concat(String.valueOf(rs.getString(1))).concat(" AND id_pe IN (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                    rs1 = con.Consultar(programas);
                    while (rs1.next()) {
                        celdasPertinencia[1][661 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs1.getString(2));
                        consulta = "Select matricula_ini_pe,matricula_pert,pertinente,anio_inicio,anio_estudio,anio_analisis from pertinenciain22 where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(" and id_pe=").concat(String.valueOf(rs1.getString(1))).concat(" order by id_pe");
                        ResultSet rs3 = con.Consultar(consulta);
                        if (rs3.next()) {
                            celdasPertinencia[4][661 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(1));
                            celdasPertinencia[5][661 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(2));
                            celdasPertinencia[6][661 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt(3));
                            celdasPertinencia[7][661 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(4));
                            celdasPertinencia[8][661 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(5));
                            celdasPertinencia[9][661 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getString(6));
                        }
                        x++;
                    }
                    //   pertinencia.shiftRows(614 - restar + (salto * (rs.getInt(1) - 1)), pertinencia.getLastRowNum(), -1 * (20 - x), true, true);
                    restar += 20 - x;
                    x = 0;
                }
////23
                consulta = "SELECT * FROM pertinenciain23 WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    celdasPertinencia[5][769].setCellValue(rs.getInt(3));
                    celdasPertinencia[5][770].setCellValue(rs.getInt(4));
                    celdasPertinencia[5][771].setCellValue(rs.getInt(5));
                    celdasPertinencia[5][772].setCellValue(rs.getInt(6));
                    celdasPertinencia[5][773].setCellValue(rs.getInt(7));
                    celdasPertinencia[5][774].setCellValue(rs.getInt(8));
                    celdasPertinencia[5][775].setCellValue(rs.getInt(9));
                    celdasPertinencia[5][776].setCellValue(rs.getInt(10));
                    celdasPertinencia[5][778].setCellValue(rs.getInt(11));
                    celdasPertinencia[5][779].setCellValue(rs.getInt(12));
                    celdasPertinencia[5][780].setCellValue(rs.getInt(13));
                    celdasPertinencia[5][781].setCellValue(rs.getInt(14));
                    celdasPertinencia[1][789].setCellValue(rs.getInt(15));
                    celdasPertinencia[2][789].setCellValue(rs.getInt(16));
                    celdasPertinencia[4][789].setCellValue(rs.getInt(17));
                    celdasPertinencia[6][789].setCellValue(rs.getInt(18));
                    celdasPertinencia[7][789].setCellValue(rs.getInt(19));
                    celdasPertinencia[9][789].setCellValue(rs.getInt(20));
                    celdasPertinencia[11][789].setCellValue(rs.getInt(21));
                    celdasPertinencia[1][794].setCellValue(rs.getInt(22));
                    celdasPertinencia[2][794].setCellValue(rs.getInt(22));
                    celdasPertinencia[4][794].setCellValue(rs.getInt(23));
                }
//24
                consulta = "Select * from pertinenciain24 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    for (int i = 0; i <= 7; i++) {
                        celdasPertinencia[5][814 + i].setCellValue(rs.getInt(i + 3));
                    }
                    for (int i = 0; i <= 3; i++) {
                        celdasPertinencia[5][823 + i].setCellValue(rs.getInt(i + 11));
                    }
                    celdasPertinencia[2][834].setCellValue(rs.getInt(15));
                    celdasPertinencia[4][834].setCellValue(rs.getInt(16));
                    celdasPertinencia[6][834].setCellValue(rs.getInt(17));
                    celdasPertinencia[2][837].setCellValue(rs.getInt(18));
                    celdasPertinencia[4][837].setCellValue(rs.getInt(19));
                    celdasPertinencia[6][837].setCellValue(rs.getInt(20));
                } else {
                    for (int i = 0; i <= 7; i++) {
                        celdasPertinencia[5][814 + i].setCellValue(0);
                    }
                    for (int i = 0; i <= 3; i++) {
                        celdasPertinencia[5][823 + i].setCellValue(0);
                    }
                    celdasPertinencia[2][834].setCellValue(0);
                    celdasPertinencia[4][834].setCellValue(0);
                    celdasPertinencia[6][834].setCellValue(0);
                    celdasPertinencia[2][837].setCellValue(0);
                    celdasPertinencia[4][837].setCellValue(0);
                    celdasPertinencia[6][837].setCellValue(0);
                }
//25
                consulta = "Select * from pertinenciain25 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    celdasPertinencia[1][847].setCellValue(rs.getInt(3));
                    celdasPertinencia[2][847].setCellValue(rs.getInt(4));
                    celdasPertinencia[4][847].setCellValue(rs.getInt(5));
                    celdasPertinencia[6][847].setCellValue(rs.getInt(6));
                    celdasPertinencia[1][849].setCellValue(rs.getInt(7));
                    celdasPertinencia[2][849].setCellValue(rs.getInt(8));
                    celdasPertinencia[4][849].setCellValue(rs.getInt(9));
                    celdasPertinencia[6][849].setCellValue(rs.getInt(10));
                } else {
                    celdasPertinencia[1][847].setCellValue(0);
                    celdasPertinencia[2][847].setCellValue(0);
                    celdasPertinencia[4][847].setCellValue(0);
                    celdasPertinencia[6][847].setCellValue(0);
                    celdasPertinencia[1][849].setCellValue(0);
                    celdasPertinencia[2][849].setCellValue(0);
                    celdasPertinencia[4][849].setCellValue(0);
                    celdasPertinencia[6][849].setCellValue(0);
                }
                celdasPertinencia = null;
                filasPertinencia = null;

//FIN PERTINENCIA

//VINCULACION

                HSSFSheet vinculacion = wb.getSheetAt(3);
                HSSFRow[] filasVinculacion = new HSSFRow[187];
                HSSFCell[][] celdasVinculacion = new HSSFCell[13][187];
                for (int i = 0; i <= 12; i++) {
                    for (int j = 0; j < 186; j++) {
                        if (i == 0) {
                            filasVinculacion[j] = vinculacion.getRow(j);
                        }
                        celdasVinculacion[i][j] = filasVinculacion[j].getCell(i);
                    }
                }
//26
                consulta = "Select * from vinculacionin26 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
                rs = con.Consultar(consulta);

                if (rs.next()) {
                    celdasVinculacion[4][7].setCellValue(rs.getInt(3));
                    celdasVinculacion[4][8].setCellValue(rs.getInt(4));
                    celdasVinculacion[1][13].setCellValue(rs.getInt(5));
                    celdasVinculacion[2][13].setCellValue(rs.getInt(6));
                    celdasVinculacion[3][13].setCellValue(rs.getInt(7));
                } else {
                    celdasVinculacion[4][7].setCellValue(0);
                    celdasVinculacion[4][8].setCellValue(0);
                    celdasVinculacion[1][13].setCellValue(0);
                    celdasVinculacion[2][13].setCellValue(0);
                    celdasVinculacion[3][13].setCellValue(0);
                }
//27
                consulta = "Select * from vinculacionin27 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    celdasVinculacion[2][22].setCellValue(rs.getInt(3));
                    celdasVinculacion[3][22].setCellValue(rs.getInt(4));
                    celdasVinculacion[4][22].setCellValue(rs.getInt(5));
                } else {
                    celdasVinculacion[2][22].setCellValue(0);
                    celdasVinculacion[3][22].setCellValue(0);
                    celdasVinculacion[4][22].setCellValue(0);
                }
//28
                consulta = "Select id_estudio, descripcion from estudios_tecnologicos where id_periodo = 16";
                rs = con.Consultar(consulta);
                int c = 0;
                while (rs.next()) {
                    consulta = "Select org_publicos, org_privados, org_sociales, ing_publicos, ing_privados, ing_sociales from vinculacionin28 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_estudio=").concat(rs.getString(1));
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        celdasVinculacion[2][34 + c].setCellValue(rs2.getInt(1));
                        celdasVinculacion[3][34 + c].setCellValue(rs2.getInt(2));
                        celdasVinculacion[4][34 + c].setCellValue(rs2.getInt(3));
                        celdasVinculacion[2][50 + c].setCellValue(rs2.getInt(4));
                        celdasVinculacion[3][50 + c].setCellValue(rs2.getInt(5));
                        celdasVinculacion[4][50 + c].setCellValue(rs2.getInt(6));
                    }
                    c++;
                }
//29
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") or n.id_nivel = -1 order by pe.id_nivel");
                rs = con.Consultar(consulta);
                salto = 1;
                while (rs.next()) {
                    consulta = "Select capacitacion, actualizacion, desarrollo_p, total_egresados from vinculacionin29 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel=").concat(rs.getString(1));
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        celdasVinculacion[2][70 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(1));
                        celdasVinculacion[3][70 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(2));
                        celdasVinculacion[4][70 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(3));
                        celdasVinculacion[4][80 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(4));
                    }
                    consulta = "Select capacitacion, actualizacion, desarrollo_p, total_egresados from vinculacionin29 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel= -1");
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        celdasVinculacion[2][73].setCellValue(rs2.getInt(1));
                        celdasVinculacion[3][73].setCellValue(rs2.getInt(2));
                        celdasVinculacion[4][73].setCellValue(rs2.getInt(3));
                    }
                }
//30
                consulta = "Select * from vinculacionin30 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    celdasVinculacion[2][119].setCellValue(rs.getInt(3));
                    celdasVinculacion[3][119].setCellValue(rs.getInt(4));
                    celdasVinculacion[4][119].setCellValue(rs.getInt(5));
                    celdasVinculacion[2][120].setCellValue(rs.getInt(6));
                    celdasVinculacion[3][120].setCellValue(rs.getInt(7));
                    celdasVinculacion[4][120].setCellValue(rs.getInt(8));
                } else {
                    celdasVinculacion[2][119].setCellValue(0);
                    celdasVinculacion[3][119].setCellValue(0);
                    celdasVinculacion[4][119].setCellValue(0);
                    celdasVinculacion[2][120].setCellValue(0);
                    celdasVinculacion[3][120].setCellValue(0);
                    celdasVinculacion[4][120].setCellValue(0);
                }
//31
                consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas WHERE id_encuesta='5' and activo='1'";
                rs = con.Consultar(consulta);
                x = 0;
                for (int i = 0; rs.next(); i++) {
                    consulta = "SELECT r_a, r_b, r_c, r_d, r_e, r_f, r_g FROM vinculacionin31 WHERE id_pregunta=".concat(rs.getString("id_pregunta").concat(" and id_universidad=").concat(String.valueOf(universidad)).concat(" and id_periodo=").concat(String.valueOf(periodo)));
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        celdasVinculacion[1][137 + i].setCellValue(rs.getString(2));
                        celdasVinculacion[2][137 + i].setCellValue(rs2.getInt(1));
                        celdasVinculacion[3][137 + i].setCellValue(rs2.getInt(2));
                        celdasVinculacion[4][137 + i].setCellValue(rs2.getInt(3));
                        celdasVinculacion[5][137 + i].setCellValue(rs2.getInt(4));
                        celdasVinculacion[6][137 + i].setCellValue(rs2.getInt(5));
                        celdasVinculacion[7][137 + i].setCellValue(rs2.getInt(6));
                        celdasVinculacion[8][137 + i].setCellValue(rs2.getInt(7));
                    }
                    x++;
                }
                vinculacion.shiftRows(157, vinculacion.getLastRowNum(), -1 * (20 - x), true, true);
//32
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") or n.id_nivel = -1 order by pe.id_nivel");
                rs = con.Consultar(consulta);
                salto = 8;
                while (rs.next()) {
                    consulta = "Select egresados_colocados, plazas from vinculacionin32 where id_universidad = ".concat(universidad).concat(" and id_periodo = ").concat(periodo).concat(" and id_nivel = ").concat(rs.getString(1));
                    rs2 = con.Consultar(consulta);
                    if (rs2.next()) {
                        celdasVinculacion[1][167 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(1));
                        celdasVinculacion[2][167 + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getInt(2));
                    }
                }

                celdasVinculacion = null;
                filasVinculacion = null;

//FIN VINCULACION

//EQUIDAD

                HSSFSheet equidad = wb.getSheetAt(4);
                HSSFRow[] filasequidad = new HSSFRow[174];
                HSSFCell[][] celdasequidad = new HSSFCell[10][174];
                for (int i = 0; i <= 9; i++) {
                    for (int j = 0; j <= 173; j++) {
                        if (i == 0) {
                            filasequidad[j] = equidad.getRow(j);
                            if (filasequidad[j] == null) {
                                filasequidad[j] = equidad.createRow(j);
                            }
                        }
                        celdasequidad[i][j] = filasequidad[j].getCell(i);
                    }
                }
//33
                consultar = "select nuevo_ingreso from datos_universidad WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdasequidad[1][7].setCellValue(rs1.getInt(1));
                } else {
                    celdasequidad[1][7].setCellValue(rs1.getInt(1));
                }
                consultar = "select egresados from egresados_estado where id_estado = (select id_estado from domicilios_universidad where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo.concat(")"));
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdasequidad[2][7].setCellValue(rs1.getInt(1));
                } else {
                    celdasequidad[2][7].setCellValue(0);
                }
//34
                consulta = "select distinct(pe.id_nivel), n.nombre,descripcion from programa_educativo pe inner join nivel_pe n on pe.id_nivel = n.id_nivel where pe.id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =").concat(periodo).concat(") order by pe.id_nivel");
                rs = con.Consultar(consulta);
                salto = 27;
                x = 0;
                while (rs.next()) {
                    consulta = "select id_pe,nombre_programa,version from programa_educativo where id_nivel=".concat(String.valueOf(rs.getString("id_nivel"))).concat(" and version='2009' and id_pe in (select id_pe from pe_universidad where id_universidad =".concat(universidad).concat(" and id_periodo =".concat(periodo).concat(")")));
                    rs2 = con.Consultar(consulta);
                    while (rs2.next()) {
                        consulta = "select ingreso_hombres,ingreso_mujeres,reingreso_hombres,reingreso_mujeres from equidadin34 where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo).concat(" and id_pe=").concat(rs2.getString(1));
                        ResultSet rs3 = con.Consultar(consulta);
                        celdasequidad[1][29 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs2.getString(2));
                        if (rs3.next()) {
                            celdasequidad[2][29 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt("ingreso_hombres"));
                            celdasequidad[3][29 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt("ingreso_mujeres"));
                            celdasequidad[5][29 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt("reingreso_hombres"));
                            celdasequidad[6][29 + x + (salto * (rs.getInt(1) - 1))].setCellValue(rs3.getInt("reingreso_mujeres"));
                        }
                        x++;
                    }
                    x = 0;
                }
//35
                consultar = "SELECT * FROM equidadin35 WHERE id_universidad=".concat(universidad).concat(" AND id_periodo=").concat(periodo);
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdasequidad[1][132].setCellValue(rs1.getInt("dep_realizados"));
                    celdasequidad[2][132].setCellValue(rs1.getInt("dep_programados"));
                    celdasequidad[3][132].setCellValue(rs1.getInt("cul_realizados"));
                    celdasequidad[4][132].setCellValue(rs1.getInt("cul_programados"));
                    celdasequidad[5][132].setCellValue(rs1.getInt("com_realizados"));
                    celdasequidad[6][132].setCellValue(rs1.getInt("com_programados"));
                    celdasequidad[5][140].setCellValue(rs1.getInt("dep_personas"));
                    celdasequidad[6][140].setCellValue(rs1.getString("dep_aspectos"));
                    celdasequidad[5][141].setCellValue(rs1.getInt("cul_personas"));
                    celdasequidad[6][141].setCellValue(rs1.getString("cul_aspectos"));
                    celdasequidad[5][142].setCellValue(rs1.getInt("com_personas"));
                    celdasequidad[6][142].setCellValue(rs1.getString("com_aspectos"));
                } else {
                    celdasequidad[1][132].setCellValue(0);
                    celdasequidad[2][132].setCellValue(0);
                    celdasequidad[3][132].setCellValue(0);
                    celdasequidad[4][132].setCellValue(0);
                    celdasequidad[5][132].setCellValue(0);
                    celdasequidad[6][132].setCellValue(0);
                    celdasequidad[5][140].setCellValue(0);
                    celdasequidad[6][140].setCellValue(0);
                    celdasequidad[5][141].setCellValue(0);
                    celdasequidad[6][141].setCellValue(0);
                    celdasequidad[5][142].setCellValue(0);
                    celdasequidad[6][142].setCellValue(0);
                }
//36
                for (int i = 1; i < 13; i++) {
                    consultar = "select apoyos_otorgados from equidadin36 where id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo).concat(" and id_apoyo=").concat(String.valueOf(i));
                    rs1 = con.Consultar(consultar);
                    if (rs1.next()) {
                        celdasequidad[2][152 + i].setCellValue(rs1.getInt(1));
                    } else {
                        celdasequidad[2][152 + i].setCellValue(0);
                    }
                }
                consultar = "SELECT matricula_total FROM datos_universidad WHERE id_universidad=".concat(universidad).concat(" and id_periodo=").concat(periodo);
                rs1 = con.Consultar(consultar);
                if (rs1.next()) {
                    celdasequidad[4][152].setCellValue(rs1.getInt(1));
                } else {
                    celdasequidad[4][152].setCellValue(0);
                }
                equidad.setForceFormulaRecalculation(true);
                filasequidad = null;
                celdasequidad = null;

//FIN EQUIDAD

                System.err.println("Fin Equidad");
                eficacia.setForceFormulaRecalculation(true);
                eficiencia.setForceFormulaRecalculation(true);
                pertinencia.setForceFormulaRecalculation(true);
                vinculacion.setForceFormulaRecalculation(true);
                equidad.setForceFormulaRecalculation(true);
                eficacia = null;
                eficiencia = null;
                pertinencia = null;
                vinculacion = null;
                equidad = null;
                EnviarExcel(response, wb, "FORMATO DE LLENADO MECASUT (borrador)");
            }
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