/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Cuauhtemoc Medina Muñoz
 * Actualización 2016: Salvador Zamora Arias
 */
public class Excel_Reporte_Sabana_Respaldo extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String LOGOUT = "logout";
    ConexionMySQL con;
    ResultSet rs;
    String consulta;
    HttpSession sesion;
    String periodo;
    String anio;
    int cantidadUniversidades;
    ArrayList ids;
    ArrayList univs;
    ArrayList idNivel;
    ArrayList nombreNivel;
    ArrayList periodos;
    HojaCalculo obj;
    int contCol;

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

        con = new ConexionMySQL();
        sesion = request.getSession(false);
        periodo = request.getParameter("ipe").toString();

        if (sesion.getAttribute("usuario") == null) {
            return mapping.findForward(LOGOUT);
        }

        //obtener el valor de ask desde la url
        String ask = request.getParameter("ask");
        if (ask.equals("sabana")) {
            try {
                //obtener el año del periodo seleccionado
                consulta = "select anio from periodos where id_periodo=".concat(periodo);
                ResultSet rsCon = con.Consultar(consulta);
                if (rsCon.next()) {
                    anio = rsCon.getString(1);
                }
                //consulta = "SELECT count(id_universidad) as 'cantidad' FROM universidades WHERE activo=1 and id_universidad>0";
                consulta = "SELECT count(id_universidad) as 'cantidad' FROM universidades WHERE id_universidad>0";
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    cantidadUniversidades = rs.getInt("cantidad");
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana reporte, consultando cantidad de universidades y/o periodo activo " + ex.getMessage());
            }

            /*
             * ------------- CONSULTA GENERAL PARA CONOCER LOS IDS Y NOMBRES DE LAS UNIVERSIDADES
             */
            ids = new ArrayList();//almacena los ids de las universidades
            univs = new ArrayList();//almacena los nombres de las universidades
            try {
                consulta = "SELECT id_universidad, nombre_universidad FROM universidades WHERE activo=1 and id_universidad>0";
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    ids.add(rs.getString("id_universidad"));
                    univs.add(rs.getString("nombre_universidad"));
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana reporte, consultando nombre de las universidades " + ex.getMessage());
            }


            /*
             * ------------- CONSULTA GENERAL PARA CONOCER LOS IDS Y NOMBRES DE LAS UNIVERSIDADES
             */
            idNivel = new ArrayList();
            nombreNivel = new ArrayList();
            try {
                consulta = "SELECT id_nivel, nombre FROM nivel_pe WHERE id_nivel>0";
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    idNivel.add(rs.getString("id_nivel"));
                    nombreNivel.add(rs.getString("nombre"));
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana reporte, consultando los niveles " + ex.getMessage());
            }

            /*MODIFICAR PARA NUEVOS EXCEL*/
            int c = 11 //INDICADOR1
                    + 4 * (idNivel.size() + 2) //INDICADOR 2
                    + 3 + 3 * 4 * (idNivel.size()) //INDICADOR 3 
                    + 3 + 3 * 4 * (idNivel.size()) //INDICADOR 4
                    + 6 + 6 * (idNivel.size()) //INDICADOR 6
                    + 4 + 4 * (idNivel.size()); //INDICADOR 7
            int NoColumnas = c;
            /*
             * para las filas se agregan 5 porque:
             * 1 para el espacio libre en la primer fila
             * 3 para los encabezados
             * 1 para los totales
             */
            int f = 5 + cantidadUniversidades;

            String rutaRelativaWeb = "/files/Sabana2.xlsx";//Especifico en donde se encuentra el archivo de excel en forma relativa
            String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);//Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
            InputStream inp = new FileInputStream(rutaAbsolutaEnDisco);//Cargo en archivo en un Stream
            obj = new HojaCalculo();
            obj.leerLibro(inp);
            inp.close();
            obj.obtenerEstilos();
            obj.eliminarHoja(0);
            obj.crearHoja("SABANA " + anio, f, c);
            obj.setAlturaFilas(1, 30);
            obj.setAlturaFilas(2, 60);
            obj.setAnchoColumna(1, 17500);
            for (int i = 2; i <= NoColumnas; i++) {
                obj.setAnchoColumna(i, 4250);
            }

            periodos = new ArrayList();
            periodos.add("SEP-DIC");
            periodos.add("ENE-ABR");
            periodos.add("MAY-AGO");

            indicador1();
            indicador2();
            indicador3();
            indicador4();
            indicador5();
            indicador6();
            indicador7();
          
            EnviarExcel(response, obj.getLibro(), "Sabana " + anio);

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
    private void EnviarExcel(HttpServletResponse response, Workbook libro, String nombreArchivo) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".xlsx");
            ServletOutputStream fileOut = response.getOutputStream();
            libro.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void indicador1() {
        obj.combinarCeldas("NO", 0, 0, 1, 2, "h1");
        obj.combinarCeldas("UNIVERSIDAD", 1, 1, 1, 2, "h1");
        obj.combinarCeldas("1. ALUMNOS DE NUEVO INGRESO CON EXANI II", 2, 4, 1, 1, "h1");
        obj.val(2, 2, "Alumnos de nuevo ingreso inscritos a la universidad que presentaron el EXANI II en el ciclo escolar", "h2");
        obj.val(3, 2, "Egresados de bachillerato que presentaron el EXANI - II para ingresar a la universidad en el ciclo escolar", "h2");
        obj.val(4, 2, "%", "h2");
        obj.combinarCeldas("1. ALUMNOS DE NUEVO INGRESO CON EXANI II", 5, 7, 1, 1, "h1");
        obj.val(5, 2, "Alumnos de nuevo ingreso inscritos a la universidad que presentaron el EXANI II en el ciclo escolar", "h2");
        obj.val(6, 2, "Alumnos de nuevo ingreso en la universidad en el ciclo escolar", "h2");
        obj.val(7, 2, "%", "h2");
        obj.combinarCeldas("1. ALUMNOS DE NUEVO INGRESO CON EXANI II", 8, 10, 1, 1, "h1");
        obj.val(8, 2, "Alumnos de nuevo ingreso que presentaron el EXANI – II y lograron 1,101 a 1,300 puntos de calificación", "h2");
        obj.val(9, 2, "Alumnos de nuevo ingreso inscritos a la universidad que presentaron el EXANI II en el ciclo escolar", "h2");
        obj.val(10, 2, "%", "h2");
        int sumIngreso = 0, sumEgreso = 0, nuevoIngreso = 0, sumC1 = 0;
        for (int i = 0; i <= ids.size() - 1; i++) {
            obj.val(0, i + 3, String.valueOf((i + 1)), "resultado");//CONSECUTIVO
            obj.val(1, i + 3, univs.get(i).toString(), "resultado");//NOMBRE DE LA UNIVERSIDAD
            try {
                consulta = "SELECT (c1+c2+c3) as ingreso, egresados_bachillerato, (c1+c2+c3)/egresados_bachillerato*100 as porcentaje, aplica, nuevo_ingreso, "
                        .concat(" (c1+c2+c3)/nuevo_ingreso*100 as porcentaje2, c1, c1/(c1+c2+c3)*100 as porcentaje3 ")
                        .concat(" FROM eficaciain1 WHERE id_universidad=").concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    if (rs.getBoolean("aplica")) {
                        /*SUMAS*/
                        sumIngreso += rs.getInt("ingreso");
                        sumEgreso += rs.getInt("egresados_bachillerato");
                        nuevoIngreso += rs.getInt("nuevo_ingreso");
                        sumC1 += rs.getInt("c1");
                        /*ASIGNAR VALORES*/
                        /*primera parte*/
                        obj.val(2, i + 3, rs.getInt("ingreso"), "datos");
                        obj.val(3, i + 3, rs.getInt("egresados_bachillerato"), "datos");
                        obj.val(4, i + 3, rs.getFloat("porcentaje"), "resultado");
                        /*segunda parte*/
                        obj.val(5, i + 3, rs.getInt("ingreso"), "datos");
                        obj.val(6, i + 3, rs.getInt("nuevo_ingreso"), "datos");
                        obj.val(7, i + 3, rs.getFloat("porcentaje2"), "resultado");
                        /*TERCERA PARTE*/
                        obj.val(8, i + 3, rs.getInt("c1"), "datos");
                        obj.val(9, i + 3, rs.getInt("ingreso"), "datos");
                        obj.val(10, i + 3, rs.getFloat("porcentaje3"), "resultado");
                    } else {
                        /*ASIGNAR VALORES*/
                        for (int q = 2; q <= 10; q++) {
                            obj.val(q, i + 3, "NO APLICA", "error");
                        }
                    }
                } else { // no devolvió nada la consulta, por lo tanto la universidad no ha llenado el indicador
                    for (int q = 2; q <= 10; q++) {
                        obj.val(q, i + 3, "NO REGISTRADO", "error");
                    }
                }
            } catch (SQLException ex) {
                System.err.println("ERROR BD: Sabana general, Indicador 1 " + ex.getMessage());
            }
        }
        //TOTALES
        obj.combinarCeldas("TOTAL", 0, 1, ids.size() + 3, ids.size() + 3, "total");
        /*totales EFICACINAIN1 PRIMERA PARTE*/
        obj.val(2, ids.size() + 3, sumIngreso, "total");
        obj.val(3, ids.size() + 3, sumEgreso, "total");
        obj.val(4, ids.size() + 3, getPorcentaje(sumIngreso, sumEgreso), "total2");

        /*TOTALES EFICACIAIN1 SEGUNDA PARTE*/
        obj.val(5, ids.size() + 3, sumIngreso, "total");
        obj.val(6, ids.size() + 3, nuevoIngreso, "total");
        obj.val(7, ids.size() + 3, getPorcentaje(sumIngreso, nuevoIngreso), "total2");

        /*TOTALES EFICACIAIN1 TERCERA PARTE*/
        obj.val(8, ids.size() + 3, sumC1, "total");
        obj.val(9, ids.size() + 3, nuevoIngreso, "total");
        obj.val(10, ids.size() + 3, getPorcentaje(sumC1, nuevoIngreso), "total2");

        contCol = 11; /*contador de columnas extras*/
    }

    private void indicador2() {
        float sumsep = 0, sumene = 0, summay = 0, sumprom = 0;
        int cantidadPE = 1;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO NIVEL 5B Versión 2004", contCol, contCol + 3, 1, 1, "h1");
            obj.val(contCol, 2, "sep-dic", "h2");
            obj.val(contCol + 1, 2, "ene-abr", "h2");
            obj.val(contCol + 2, 2, "may-ago", "h2");
            obj.val(contCol + 3, 2, "promedio", "h2");
            for (int x = 0; x <= ids.size(); x++) {
                if (x != ids.size()) {
                    consulta = "SELECT id_pe FROM pe_universidad WHERE id_universidad=".concat(ids.get(x).toString())
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_pe IN(select id_pe from programa_educativo where id_nivel=1 AND version=2004)");
                    rs = con.Consultar(consulta);
                    if (rs.next()) {
                        consulta = "SELECT sep, ene, may, (sep+ene+may)/3 AS promedio "
                                .concat(" FROM eficaciain2 WHERE id_universidad=").concat(ids.get(x).toString())
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_pe IN (select id_pe from programa_educativo where version=2004)");
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            /*OPERACIONES PARA LOS TOTALES*/
                            sumsep += rs.getFloat("sep");
                            sumene += rs.getFloat("ene");
                            summay += rs.getFloat("may");
                            sumprom += rs.getFloat("promedio");
                            cantidadPE++;
                            /*ASIGNACION DE VALORES*/
                            obj.val(contCol, x + 3, rs.getFloat("sep"), "datos2");
                            obj.val(contCol + 1, x + 3, rs.getFloat("ene"), "datos2");
                            obj.val(contCol + 2, x + 3, rs.getFloat("may"), "datos2");
                            obj.val(contCol + 3, x + 3, rs.getFloat("promedio"), "resultado");
                        } else {
                            for (int p = 0; p <= 3; p++) {
                                obj.val(contCol + p, x + 3, "NO CONCLUIDO", "error");
                            }
                        }
                    } else { // NO TIENE EL NIVEL
                        for (int p = 0; p <= 3; p++) {
                            obj.val(contCol + p, x + 3, "NO TIENE EL NIVEL", "error");
                        }
                    }
                } else {/*TOTALES POR TABLA*/
                    /*totales EFICACINAIN2 PRIMERA PARTE*/
                    if (cantidadPE != 1) {
                        obj.val(contCol, ids.size() + 3, (sumsep / (cantidadPE - 1)), "total2");
                        obj.val(contCol + 1, ids.size() + 3, (sumene / (cantidadPE - 1)), "total2");
                        obj.val(contCol + 2, ids.size() + 3, (summay / (cantidadPE - 1)), "total2");
                        obj.val(contCol + 3, ids.size() + 3, (sumprom / (cantidadPE - 1)), "total2");
                    } else {
                        obj.val(contCol, ids.size() + 3, 0, "total2");
                        obj.val(contCol + 1, ids.size() + 3, 0, "total2");
                        obj.val(contCol + 2, ids.size() + 3, 0, "total2");
                        obj.val(contCol + 3, ids.size() + 3, 0, "total2");
                    }
                }
            }
            contCol += 4;

            for (int i = 0; i <= idNivel.size(); i++) {
                sumsep = 0;
                sumene = 0;
                summay = 0;
                sumprom = 0;
                cantidadPE = 1;
                if (i != idNivel.size()) {
                    //ENCABEZADOS
                    obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO DEL NIVEL " + nombreNivel.get(i), contCol, contCol + 3, 1, 1, "h1");
                    obj.val(contCol, 2, "sep-dic", "h2");
                    obj.val(contCol + 1, 2, "ene-abr", "h2");
                    obj.val(contCol + 2, 2, "may-ago", "h2");
                    obj.val(contCol + 3, 2, "promedio", "h2");
                    for (int x = 0; x <= ids.size(); x++) {
                        if (x != ids.size()) {
                            if (contieneNivel(idNivel.get(i).toString(), ids.get(x).toString())) {
                                consulta = "SELECT sep, ene, may, (sep+ene+may)/3 AS promedio "
                                        .concat(" FROM eficaciain2 WHERE id_universidad=").concat(ids.get(x).toString())
                                        .concat(" AND id_periodo=").concat(periodo)
                                        .concat(" AND id_pe IN (SELECT id_pe FROM programa_educativo WHERE id_nivel=")
                                        .concat(idNivel.get(i).toString()).concat(")");
                                rs = con.Consultar(consulta);
                                if (rs.next()) { /*CONSULTA LOS DATOS*/
                                    /*OPERACIONES PARA LOS TOTALES*/
                                    sumsep += rs.getFloat("sep");
                                    sumene += rs.getFloat("ene");
                                    summay += rs.getFloat("may");
                                    sumprom += rs.getFloat("promedio");
                                    cantidadPE++;
                                    /*ASIGNACION DE VALORES*/
                                    obj.val(contCol, x + 3, rs.getFloat("sep"), "datos2");
                                    obj.val(contCol + 1, x + 3, rs.getFloat("ene"), "datos2");
                                    obj.val(contCol + 2, x + 3, rs.getFloat("may"), "datos2");
                                    obj.val(contCol + 3, x + 3, rs.getFloat("promedio"), "resultado");
                                } else {
                                    for (int p = 0; p <= 3; p++) {
                                        obj.val(contCol + p, x + 3, "NO CONCLUIDO", "error");
                                    }
                                }
                            } else {
                                for (int p = 0; p <= 3; p++) {
                                    obj.val(contCol + p, x + 3, "NO TIENE EL NIVEL", "error");
                                }
                            }
                        } else {/*TOTALES POR TABLA*/
                            /*TOTALES EFICACINAIN1 PRIMERA PARTE*/
                            if (cantidadPE != 1) {
                                obj.val(contCol, ids.size() + 3, (sumsep / (cantidadPE - 1)), "total2");
                                obj.val(contCol + 1, ids.size() + 3, (sumene / (cantidadPE - 1)), "total2");
                                obj.val(contCol + 2, ids.size() + 3, (summay / (cantidadPE - 1)), "total2");
                                obj.val(contCol + 3, ids.size() + 3, (sumprom / (cantidadPE - 1)), "total2");
                            } else {
                                obj.val(contCol, ids.size() + 3, 0, "total2");
                                obj.val(contCol + 1, ids.size() + 3, 0, "total2");
                                obj.val(contCol + 2, ids.size() + 3, 0, "total2");
                                obj.val(contCol + 3, ids.size() + 3, 0, "total2");
                            }
                        }
                    }
                } else {//tabla de totales
                    //ENCABEZADOS
                    obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO TOTAL", contCol, contCol + 3, 1, 1, "h1");
                    obj.val(contCol, 2, "sep-dic", "h2");
                    obj.val(contCol + 1, 2, "ene-abr", "h2");
                    obj.val(contCol + 2, 2, "may-ago", "h2");
                    obj.val(contCol + 3, 2, "promedio", "h2");

                    cantidadPE = 1;
                    for (int x = 0; x <= ids.size(); x++) {
                        if (x != ids.size()) {
                            consulta = "SELECT sep, ene, may, (sep+ene+may)/3 AS promedio "
                                    .concat(" FROM eficaciain2 WHERE id_universidad=").concat(ids.get(x).toString())
                                    .concat(" AND id_periodo=").concat(periodo);
                            rs = con.Consultar(consulta);
                            if (rs.next()) {
                                /*OPERACIONES PARA LOS TOTALES*/
                                sumsep += rs.getFloat("sep");
                                sumene += rs.getFloat("ene");
                                summay += rs.getFloat("may");
                                sumprom += rs.getFloat("promedio");
                                cantidadPE++;
                                /*ASIGNACION DE VALORES*/
                                obj.val(contCol, x + 3, rs.getFloat("sep"), "datos2");
                                obj.val(contCol + 1, x + 3, rs.getFloat("ene"), "datos2");
                                obj.val(contCol + 2, x + 3, rs.getFloat("may"), "datos2");
                                obj.val(contCol + 3, x + 3, rs.getFloat("promedio"), "resultado");
                            } else {
                                for (int p = 0; p <= 3; p++) {
                                    obj.val(contCol + p, x + 3, "NO CONCLUIDO", "error");
                                }
                            }
                        } else {/*TOTALES POR TABLA*/
                            /*totales EFICACINAIN2 PRIMERA PARTE*/
                            if (cantidadPE != 1) {
                                obj.val(contCol, ids.size() + 3, (sumsep / (cantidadPE - 1)), "total2");
                                obj.val(contCol + 1, ids.size() + 3, (sumene / (cantidadPE - 1)), "total2");
                                obj.val(contCol + 2, ids.size() + 3, (summay / (cantidadPE - 1)), "total2");
                                obj.val(contCol + 3, ids.size() + 3, (sumprom / (cantidadPE - 1)), "total2");
                            } else {
                                obj.val(contCol, ids.size() + 3, 0, "total2");
                                obj.val(contCol + 1, ids.size() + 3, 0, "total2");
                                obj.val(contCol + 2, ids.size() + 3, 0, "total2");
                                obj.val(contCol + 3, ids.size() + 3, 0, "total2");
                            }
                        }
                    }
                }
                contCol += 4;
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Sabana Indicador 2." + ex.getMessage());
        }
    }

    private void indicador3() {
        ArrayList campos = new ArrayList();
        campos.add(" rep_sep, mat_sep, rep_sep/mat_sep*100 as porcentaje ");
        campos.add(" rep_ene, mat_ene, rep_ene/mat_ene*100 as porcentaje ");
        campos.add(" rep_may, mat_may, rep_may/mat_may*100 as porcentaje ");

        try {
            for (int i = 0; i <= idNivel.size(); i++) { //MUEVE LOS NIVELES
                if (i != idNivel.size()) { //TABLAS DE NIVELES
                    for (int per = 0; per <= periodos.size(); per++) { //MUEVE LOS PERIODOS
                        if (per != periodos.size()) {
                            //ENCABEZADOS
                            obj.combinarCeldas("3. REPROBACIÓN ".concat(periodos.get(per).toString()).concat(" ").concat(nombreNivel.get(i).toString()), contCol, contCol + 2, 1, 1, "h1");
                            obj.val(contCol, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre ".concat(periodos.get(per).toString()), "h2");
                            obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre ".concat(periodos.get(per).toString()), "h2");
                            obj.val(contCol + 2, 2, "%", "h2");
                            int sumrep = 0, summat = 0;
                            for (int unis = 0; unis <= ids.size(); unis++) {
                                if (unis != ids.size()) {//Cuerpo
                                    if (contieneNivel(idNivel.get(i).toString(), ids.get(unis).toString())) { //la universidad si tiene el nivel
                                        consulta = "SELECT ".concat(campos.get(per).toString()).concat(" FROM eficaciain3 ")
                                                .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                                .concat(" AND id_periodo=").concat(periodo)
                                                .concat(" AND id_nivel=").concat(idNivel.get(i).toString());
                                        rs = con.Consultar(consulta);
                                        if (rs.next()) { //si hay una respuesta de la consulta
                                            sumrep += rs.getInt(1);
                                            summat += rs.getInt(2);
                                            obj.val(contCol, unis + 3, rs.getInt(1), "datos");
                                            obj.val(contCol + 1, unis + 3, rs.getInt(2), "datos");
                                            obj.val(contCol + 2, unis + 3, rs.getFloat(3), "resultado");
                                        } else { //si no hay una respuesta de la consulta
                                            obj.val(contCol, unis + 3, (int) 0, "datos");
                                            obj.val(contCol + 1, unis + 3, (int) 0, "datos");
                                            obj.val(contCol + 2, unis + 3, (float) 0, "resultado");
                                        }
                                    } else {//la universidad no tiene el nivel
                                        for (int p = 0; p <= 2; p++) {
                                            obj.val(contCol + p, unis + 3, "NO TIENE EL NIVEL", "error");
                                        }
                                    }
                                } else {//TOTALES INFERIORES
                                    obj.val(contCol, unis + 3, sumrep, "total");
                                    obj.val(contCol + 1, unis + 3, summat, "total");
                                    obj.val(contCol + 2, unis + 3, getPorcentaje(sumrep, summat), "total2");
                                }
                            }
                        } else {
                            /* TOTALES POR NIVEL */
                            //ENCABEZADOS
                            obj.combinarCeldas("3. REPROBACIÓN ANUAL ".concat(nombreNivel.get(i).toString()), contCol, contCol + 2, 1, 1, "h1");
                            obj.val(contCol, 2, "Número de alumnos reprobados por primer examen extraordinario", "h2");
                            obj.val(contCol + 1, 2, "Matrícula inicial atendida anual", "h2");
                            obj.val(contCol + 2, 2, "%", "h2");
                            int sumrep = 0, summat = 0;
                            for (int unis = 0; unis <= ids.size(); unis++) {
                                if (unis != ids.size()) {//Cuerpo
                                    consulta = "SELECT SUM(rep_sep)+ SUM(rep_ene)+SUM(rep_may) AS reprobados, SUM(mat_sep)+ SUM(mat_ene)+SUM(mat_may) AS matricula FROM eficaciain3 "
                                            .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                            .concat(" AND id_periodo=").concat(periodo)
                                            .concat(" AND id_nivel=").concat(idNivel.get(i).toString());
                                    rs = con.Consultar(consulta);
                                    if (rs.next()) { //si hay una respuesta de la consulta
                                        sumrep += rs.getInt(1);
                                        summat += rs.getInt(2);
                                        int reprobados = rs.getInt("reprobados");
                                        int matricula = rs.getInt("matricula");
                                        obj.val(contCol, unis + 3, reprobados, "datos");
                                        obj.val(contCol + 1, unis + 3, matricula, "datos");
                                        obj.val(contCol + 2, unis + 3, getPorcentaje(reprobados, matricula), "resultado");
                                    } else { //si no hay una respuesta de la consulta
                                        for (int p = 0; p <= 2; p++) {
                                            obj.val(contCol + p, unis + 3, "NO TIENE EL NIVEL", "error");
                                        }
                                    }
                                } else {//TOTALES INFERIORES
                                    obj.val(contCol, unis + 3, sumrep, "total");
                                    obj.val(contCol + 1, unis + 3, summat, "total");
                                    obj.val(contCol + 2, unis + 3, getPorcentaje(sumrep, summat), "total2");
                                }
                            }
                        }
                        contCol += 3;
                    }
                } else {/*TABLA DE SUPER TOTALES*/
                    //ENCABEZADOS
                    obj.combinarCeldas("3. REPROBACIÓN ANUAL TOTAL", contCol, contCol + 2, 1, 1, "h1");
                    obj.val(contCol, 2, "Número de alumnos reprobados por primer examen extraordinario ", "h2");
                    obj.val(contCol + 1, 2, "Matrícula inicial atendida anual ", "h2");
                    obj.val(contCol + 2, 2, "%", "h2");
                    int sumrep = 0, summat = 0;
                    for (int unis = 0; unis <= ids.size(); unis++) {
                        if (unis != ids.size()) {//Cuerpo
                            consulta = "SELECT SUM(rep_sep)+ SUM(rep_ene)+SUM(rep_may) AS reprobados, SUM(mat_sep)+ SUM(mat_ene)+SUM(mat_may) AS matricula FROM eficaciain3 "
                                    .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                    .concat(" AND id_periodo=").concat(periodo);
                            rs = con.Consultar(consulta);
                            if (rs.next()) { //si hay una respuesta de la consulta
                                sumrep += rs.getInt(1);
                                summat += rs.getInt(2);
                                int reprobados = rs.getInt("reprobados");
                                int matricula = rs.getInt("matricula");
                                obj.val(contCol, unis + 3, reprobados, "datos");
                                obj.val(contCol + 1, unis + 3, matricula, "datos");
                                obj.val(contCol + 2, unis + 3, getPorcentaje(reprobados, matricula), "resultado");
                            } else { //si no hay una respuesta de la consulta
                                obj.val(contCol, unis + 3, (int) 0, "datos");
                                obj.val(contCol + 1, unis + 3, (int) 0, "datos");
                                obj.val(contCol + 2, unis + 3, (float) 0, "resultado");
                            }
                        } else {//TOTALES INFERIORES
                            obj.val(contCol, unis + 3, sumrep, "total");
                            obj.val(contCol + 1, unis + 3, summat, "total");
                            obj.val(contCol + 2, unis + 3, getPorcentaje(sumrep, summat), "total2");
                        }
                    }
                    contCol += 3;
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 3 " + ex.getMessage());
        }
    }

    private void indicador4() {
        ArrayList camposMat = new ArrayList();
        camposMat.add(" mat_sep ");
        camposMat.add(" mat_ene ");
        camposMat.add(" mat_may ");
        ArrayList camposDes = new ArrayList();
        camposDes.add(" tot_deser_sep ");
        camposDes.add(" tot_deser_ene ");
        camposDes.add(" tot_deser_may ");
        int sumdes = 0, summat = 0, matriculaNivel = 0;
        try {
            for (int nivel = 0; nivel <= idNivel.size(); nivel++) { /*MUEVE LOS NIVELES*/
                if (nivel != idNivel.size()) { /*PINTA TABLAS DE NIVELES */
                    for (int per = 0; per <= periodos.size(); per++) { /*MUEVE LOS PERIODOS*/
                        if (per != periodos.size()) {/*PINTA TABLAS DE PERIODOS*/
                            //ENCABEZADOS
                            obj.combinarCeldas("4. DESERCIÓN ".concat(periodos.get(per).toString()).concat(" ").concat(nombreNivel.get(nivel).toString()), contCol, contCol + 2, 1, 1, "h1");
                            obj.val(contCol, 2, "Número de alumnos desertores del cuatrimestre ".concat(periodos.get(per).toString()), "h2");
                            obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre ".concat(periodos.get(per).toString()), "h2");
                            obj.val(contCol + 2, 2, "%", "h2");
                            for (int unis = 0; unis <= ids.size(); unis++) {
                                if (unis != ids.size()) {//Cuerpo
                                    if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) { //la universidad si tiene el nivel
                                        consulta = "SELECT ".concat(camposMat.get(per).toString()).concat(" FROM eficaciain3 ")
                                                .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                                .concat(" AND id_periodo=").concat(periodo)
                                                .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                                        rs = con.Consultar(consulta);
                                        if (rs.next()) {//si ya llenaron el indicador 3
                                            matriculaNivel = rs.getInt(1);
                                            consulta = "SELECT ".concat(camposDes.get(per).toString()).concat(" FROM eficaciain4_1")
                                                    .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                                    .concat(" AND id_periodo=").concat(periodo)
                                                    .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                                            rs = con.Consultar(consulta);
                                            if (rs.next()) { //está llenado el ind 3, pero no el 4
                                                summat += matriculaNivel;
                                                sumdes += rs.getInt(1);
                                                obj.val(contCol, unis + 3, rs.getInt(1), "datos");
                                                obj.val(contCol + 1, unis + 3, matriculaNivel, "datos");
                                                obj.val(contCol + 2, unis + 3, getPorcentaje(rs.getInt(1), matriculaNivel), "resultado");
                                            } else { //si no hay una respuesta de la consulta
                                                for (int p = 0; p <= 2; p++) {
                                                    obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                                }
                                            }
                                        } else {
                                            for (int p = 0; p <= 2; p++) {
                                                obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                            }
                                        }
                                    } else {//la universidad no tiene el nivel
                                        for (int p = 0; p <= 2; p++) {
                                            obj.val(contCol + p, unis + 3, "NO TIENE EL NIVEL", "error");
                                        }
                                    }
                                } else {//TOTALES INFERIORES
                                    obj.val(contCol, unis + 3, sumdes, "total");
                                    obj.val(contCol + 1, unis + 3, summat, "total");
                                    obj.val(contCol + 2, unis + 3, getPorcentaje(sumdes, summat), "total2");
                                }
                            }
                        } else {/*PINTA LOS TOTALES DEL NIVEL*/
                            //ENCABEZADOS
                            obj.combinarCeldas("4. DESERCIÓN ANUAL ".concat(nombreNivel.get(nivel).toString()), contCol, contCol + 2, 1, 1, "h1");
                            obj.val(contCol, 2, "Número de alumnos desertores anual", "h2");
                            obj.val(contCol + 1, 2, "Matrícula inicial atendida anual", "h2");
                            obj.val(contCol + 2, 2, "%", "h2");
                            sumdes = 0;
                            summat = 0;
                            matriculaNivel = 0;
                            for (int unis = 0; unis <= ids.size(); unis++) {
                                if (unis != ids.size()) {//Cuerpo
                                    if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) { //la universidad si tiene el nivel
                                        consulta = "SELECT mat_sep+mat_ene+mat_may as 'matricula' FROM eficaciain3 "
                                                .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                                .concat(" AND id_periodo=").concat(periodo)
                                                .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                                        rs = con.Consultar(consulta);
                                        if (rs.next()) {
                                            matriculaNivel = rs.getInt("matricula");
                                            consulta = "SELECT tot_deser_sep+tot_deser_ene+tot_deser_may as desertores FROM eficaciain4_1"
                                                    .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                                    .concat(" AND id_periodo=").concat(periodo)
                                                    .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                                            rs = con.Consultar(consulta);
                                            if (rs.next()) {
                                                summat += matriculaNivel;
                                                sumdes += rs.getInt("desertores");
                                                obj.val(contCol, unis + 3, rs.getInt("desertores"), "datos");
                                                obj.val(contCol + 1, unis + 3, matriculaNivel, "datos");
                                                obj.val(contCol + 2, unis + 3, getPorcentaje(sumdes, summat), "total2");
                                            } else { //si no hay una respuesta de la consulta
                                                for (int p = 0; p <= 2; p++) {
                                                    obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                                }
                                            }
                                        } else {
                                            for (int p = 0; p <= 2; p++) {
                                                obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                            }
                                        }
                                    } else {//la universidad no tiene el nivel
                                        for (int p = 0; p <= 2; p++) {
                                            obj.val(contCol + p, unis + 3, "NO TIENE EL NIVEL", "error");
                                        }
                                    }
                                } else {//TOTALES INFERIORES
                                    obj.val(contCol, unis + 3, sumdes, "total");
                                    obj.val(contCol + 1, unis + 3, summat, "total");
                                    obj.val(contCol + 2, unis + 3, getPorcentaje(sumdes, summat), "total2");
                                }
                            }
                        }
                        contCol += 3;
                    }
                } else {/*TABLA DE TOTALES DE LOS NIVELES*/
                    obj.combinarCeldas("4. DESERCIÓN ANUAL TOTAL", contCol, contCol + 2, 1, 1, "h1");
                    obj.val(contCol, 2, "Número de alumnos desertores anual", "h2");
                    obj.val(contCol + 1, 2, "Matrícula inicial atendida anual", "h2");
                    obj.val(contCol + 2, 2, "%", "h2");
                    for (int unis = 0; unis <= ids.size(); unis++) {
                        if (unis != ids.size()) {//Cuerpo
                            consulta = "SELECT SUM(mat_sep)+ SUM(mat_ene)+SUM(mat_may) AS matricula FROM eficaciain3 "
                                    .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                    .concat(" AND id_periodo=").concat(periodo);
                            rs = con.Consultar(consulta);
                            if (rs.next()) { //si hay una respuesta de la consulta
                                matriculaNivel = rs.getInt("matricula");
                                consulta = "SELECT SUM(tot_deser_sep)+ SUM(tot_deser_ene)+SUM(tot_deser_may) AS desertores FROM eficaciain4_1 "
                                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                        .concat(" AND id_periodo=").concat(periodo);
                                rs = con.Consultar(consulta);
                                if (rs.next()) { //si hay una respuesta de la consulta
                                    summat += matriculaNivel;
                                    sumdes += rs.getInt("desertores");
                                    obj.val(contCol, unis + 3, rs.getInt("desertores"), "datos");
                                    obj.val(contCol + 1, unis + 3, matriculaNivel, "datos");
                                    obj.val(contCol + 2, unis + 3, getPorcentaje(rs.getInt("desertores"), matriculaNivel), "resultado");
                                }
                            } else { //si no hay una respuesta de la consulta
                                obj.val(contCol, unis + 3, (int) 0, "datos");
                                obj.val(contCol + 1, unis + 3, (int) 0, "datos");
                                obj.val(contCol + 2, unis + 3, (float) 0, "resultado");
                            }
                        } else {//TOTALES INFERIORES
                            obj.val(contCol, unis + 3, sumdes, "total");
                            obj.val(contCol + 1, unis + 3, summat, "total");
                            obj.val(contCol + 2, unis + 3, getPorcentaje(sumdes, summat), "total2");
                        }
                    }
                    contCol += 3;
                }
            }
            //} catch (SQLException e) {
            //System.err.println("ERROR BD: Sabana general Indicador 4 Consulta \n " + e.getMessage());
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 4 " + ex.getMessage());
        }
    }

    private void indicador5() {
    }

    private void indicador6() {
        try {
            for (int nivel = 0; nivel <= nombreNivel.size(); nivel++) {//MUEVE LOS NIVELES 
                if (nivel != nombreNivel.size()) {//CUERPO
                    /*MERCADO LABORAL*/
                    obj.combinarCeldas("6. EGRESADOS EN EL MERCADO LABORAL " + nombreNivel.get(nivel).toString(), contCol, contCol + 2, 1, 1, "h1");
                    obj.val(contCol, 2, "Egresados del ciclo escolar anterior trabajando a seis meses de egreso", "h2");
                    obj.val(contCol + 1, 2, "Total de egresados del ciclo escolar anterior", "h2");
                    obj.val(contCol + 2, 2, "%", "h2");
                    /*ÁREA AFÍN*/
                    obj.combinarCeldas("6. EGRESADOS TRABAJANDO EN ÁREA AFÍN " + nombreNivel.get(nivel).toString(), contCol + 3, contCol + 5, 1, 1, "h1");
                    obj.val(contCol + 3, 2, "Egresados del ciclo escolar anterior cuya actividad laboral es acorde a su formación académica por programa educativo", "h2");
                    obj.val(contCol + 4, 2, "Egresados del ciclo escolar anterior trabajando a seis meses de egreso", "h2");
                    obj.val(contCol + 5, 2, "%", "h2");
                    int sumegresados = 0, sumafines = 0, sume_trabajando = 0;
                    for (int unis = 0; unis <= ids.size(); unis++) {
                        if (unis != ids.size()) {//CUERPO DE LA TABLA
                            if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) { //la universidad si tiene el nivel
                                consulta = "SELECT SUM(egresados) AS egreso FROM eficaciain5 WHERE anio=2 AND"
                                        .concat(" id_universidad=").concat(ids.get(unis).toString())
                                        .concat(" AND id_periodo=").concat(periodo)
                                        .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                                rs = con.Consultar(consulta);
                                if (rs.next()) {//si devuelve la suma de ingreso 
                                    int egreso = rs.getInt("egreso");
                                    sumegresados += egreso;
                                    consulta = "SELECT e_trabajando, afin FROM eficaciain6 WHERE "
                                            .concat(" id_universidad=").concat(ids.get(unis).toString())
                                            .concat(" AND id_periodo=").concat(periodo)
                                            .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                                    rs = con.Consultar(consulta);
                                    if (rs.next()) { //está llenado el ind 5, pero no el 6
                                        sumafines += rs.getInt("afin");
                                        sume_trabajando += rs.getInt("e_trabajando");
                                        obj.val(contCol, unis + 3, rs.getInt("e_trabajando"), "datos");
                                        obj.val(contCol + 1, unis + 3, egreso, "datos");
                                        obj.val(contCol + 2, unis + 3, getPorcentaje(rs.getInt("e_trabajando"), egreso), "resultado");
                                        obj.val(contCol + 3, unis + 3, rs.getInt("afin"), "datos");
                                        obj.val(contCol + 4, unis + 3, rs.getInt("e_trabajando"), "datos");
                                        obj.val(contCol + 5, unis + 3, getPorcentaje(rs.getInt("afin"), rs.getInt("e_trabajando")), "resultado");
                                    } else { //si no hay una respuesta de la consulta
                                        for (int p = 0; p <= 5; p++) {
                                            obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                        }
                                    }
                                } else { // no devolvío la suma de ingreso, osea no llenaron el indicador 5
                                    for (int p = 0; p <= 5; p++) {
                                        obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                    }
                                }
                            } else {//la universidad no tiene el nivel
                                for (int p = 0; p <= 5; p++) {
                                    obj.val(contCol + p, unis + 3, "NO TIENE EL NIVEL", "error");
                                }
                            }
                        } else {//TOTALES INFERIORES
                            obj.val(contCol, unis + 3, sume_trabajando, "total");
                            obj.val(contCol + 1, unis + 3, sumegresados, "total");
                            obj.val(contCol + 2, unis + 3, getPorcentaje(sume_trabajando, sumegresados), "total2");
                            obj.val(contCol + 3, unis + 3, sumafines, "total");
                            obj.val(contCol + 4, unis + 3, sume_trabajando, "total");
                            obj.val(contCol + 5, unis + 3, getPorcentaje(sumafines, sume_trabajando), "total2");
                        }
                    }
                } else {/*TOTALES DE EGRESADOS EN EL MERCADO LABORAL*/
                    /*MERCADO LABORAL*/
                    obj.combinarCeldas("6. EGRESADOS EN EL MERCADO LABORAL (TOTAL)", contCol, contCol + 2, 1, 1, "h1");
                    obj.val(contCol, 2, "Egresados del ciclo escolar anterior trabajando a seis meses de egreso", "h2");
                    obj.val(contCol + 1, 2, "Total de egresados del ciclo escolar anterior", "h2");
                    obj.val(contCol + 2, 2, "%", "h2");
                    /*ÁREA AFÍN*/
                    obj.combinarCeldas("6. EGRESADOS TRABAJANDO EN ÁREA AFÍN (TOTAL)", contCol + 3, contCol + 5, 1, 1, "h1");
                    obj.val(contCol + 3, 2, "Egresados del ciclo escolar anterior cuya actividad laboral es acorde a su formación académica por programa educativo", "h2");
                    obj.val(contCol + 4, 2, "Egresados del ciclo escolar anterior trabajando a seis meses de egreso", "h2");
                    obj.val(contCol + 5, 2, "%", "h2");
                    int sumegresados = 0, sumafines = 0, sume_trabajando = 0;
                    for (int unis = 0; unis <= ids.size(); unis++) {
                        if (unis != ids.size()) {//CUERPO DE LA TABLA
                            consulta = "SELECT SUM(egresados) AS egreso FROM eficaciain5 WHERE anio=2 AND"
                                    .concat(" id_universidad=").concat(ids.get(unis).toString())
                                    .concat(" AND id_periodo=").concat(periodo);
                            rs = con.Consultar(consulta);
                            if (rs.next()) {//si devuelve la suma de ingreso 
                                int egreso = rs.getInt("egreso");
                                sumegresados += egreso;
                                consulta = "SELECT SUM(e_trabajando) as e_trabajando, SUM(afin) as afin FROM eficaciain6 WHERE "
                                        .concat(" id_universidad=").concat(ids.get(unis).toString())
                                        .concat(" AND id_periodo=").concat(periodo);
                                rs = con.Consultar(consulta);
                                if (rs.next()) { //está llenado el ind 5, pero no el 6
                                    sumafines += rs.getInt("afin");
                                    sume_trabajando += rs.getInt("e_trabajando");
                                    obj.val(contCol, unis + 3, rs.getInt("e_trabajando"), "datos");
                                    obj.val(contCol + 1, unis + 3, egreso, "datos");
                                    obj.val(contCol + 2, unis + 3, getPorcentaje(rs.getInt("e_trabajando"), egreso), "resultado");
                                    obj.val(contCol + 3, unis + 3, rs.getInt("afin"), "datos");
                                    obj.val(contCol + 4, unis + 3, rs.getInt("e_trabajando"), "datos");
                                    obj.val(contCol + 5, unis + 3, getPorcentaje(rs.getInt("afin"), rs.getInt("e_trabajando")), "resultado");
                                } else { //si no hay una respuesta de la consulta
                                    for (int p = 0; p <= 5; p++) {
                                        obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                    }
                                }
                            } else { // no devolvío la suma de ingreso, osea no llenaron el indicador 5
                                for (int p = 0; p <= 5; p++) {
                                    obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                }
                            }
                        } else {//TOTALES INFERIORES
                            obj.val(contCol, unis + 3, sume_trabajando, "total");
                            obj.val(contCol + 1, unis + 3, sumegresados, "total");
                            obj.val(contCol + 2, unis + 3, getPorcentaje(sume_trabajando, sumegresados), "total2");
                            obj.val(contCol + 3, unis + 3, sumafines, "total");
                            obj.val(contCol + 4, unis + 3, sume_trabajando, "total");
                            obj.val(contCol + 5, unis + 3, getPorcentaje(sumafines, sume_trabajando), "total2");
                        }
                    }
                }
                contCol += 6;
            }
        } catch (Exception ex) {
            System.err.println("Exception Indicador 6: " + ex.getMessage());
        }
    }

    private void indicador7() {
        int muestreo = 0, summuestreo = 0, sumsatisfechos = 0;
        try {
            for (int nivel = 0; nivel <= nombreNivel.size(); nivel++) {//MUEVE LOS NIVELES 
                if (nivel != nombreNivel.size()) {//CUERPO
                    obj.combinarCeldas("7. TASA DE EGRESADOS SATISFECHOS DEL NIVEL " + nombreNivel.get(nivel).toString(), contCol, contCol + 2, 1, 1, "h1");
                    obj.val(contCol, 2, "Total de egresados muy satisfechos y satisfechos", "h2");
                    obj.val(contCol + 1, 2, "Total de egresados", "h2");
                    obj.val(contCol + 2, 2, "%", "h2");
                    obj.combinarCeldas("7. GRADO DE SATISFACCION DE LOS EGRESADOS (escala de 10) DEL NIVEL " + nombreNivel.get(nivel).toString(), contCol + 3, contCol + 3, 1, 2, "h1");
                    for (int unis = 0; unis <= ids.size(); unis++) {
                        if (unis != ids.size()) {//CUERPO DE LA TABLA
                            if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) {/*TIENE EL NIVEL*/
                                consulta = "SELECT SUM(egresados) AS egreso FROM eficaciain5 WHERE anio=2 AND"
                                        .concat(" id_universidad=").concat(ids.get(unis).toString())
                                        .concat(" AND id_periodo=").concat(periodo);
                                rs = con.Consultar(consulta);
                                muestreo = 0;
                                sumsatisfechos = 0;
                                if (rs.next()) {
                                    muestreo = getMuestreo(rs.getInt("egreso"));
                                    consulta = "SELECT SUM(r_a) as suma FROM eficaciain7_10 WHERE id_pregunta IN "
                                            .concat("(SELECT id_pregunta FROM encuesta_preguntas WHERE id_encuesta = (SELECT id_encuesta FROM encuestas WHERE indicador=7 AND id_nivel=")
                                            .concat(idNivel.get(nivel).toString()).concat(" AND ")
                                            .concat(" id_universidad=").concat(ids.get(unis).toString())
                                            .concat(" AND id_periodo=").concat(periodo).concat("))");
                                    rs = con.Consultar(consulta);
                                    if (rs.next()) {//INDICADOR LLENO
                                        summuestreo += muestreo;
                                        sumsatisfechos += rs.getInt("suma");
                                        obj.val(contCol, unis + 3, rs.getInt("suma"), "datos");
                                        obj.val(contCol + 1, unis + 3, muestreo, "datos");
                                        obj.val(contCol + 2, unis + 3, getPorcentaje(rs.getInt("suma"), muestreo), "resultado");
                                        obj.val(contCol + 3, unis + 3, getGradoSatisfaccion(ids.get(unis).toString(), idNivel.get(nivel).toString(), "7"), "resultado");
                                    } else {//INDICADOR VACÍO
                                        for (int p = 0; p <= 2; p++) { //falta llenar el indicador 7
                                            obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                        }
                                    }
                                } else {
                                    for (int p = 0; p <= 2; p++) { //falta llenar el indicador 5 
                                        obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                    }
                                }
                            } else {/*NO TIENE NIVEL*/
                                for (int p = 0; p <= 2; p++) {
                                    obj.val(contCol + p, unis + 3, "NO TIENE EL NIVEL", "error");
                                }

                            }
                        } else {//TOTALES INFERIORES
                            obj.val(contCol, unis + 3, sumsatisfechos, "total");
                            obj.val(contCol + 1, unis + 3, summuestreo, "total");
                            obj.val(contCol + 2, unis + 3, getPorcentaje(sumsatisfechos, summuestreo), "total2");
                            obj.val(contCol + 3, unis + 3, 0, "total2");
                        }
                    }
                } else {/*TABLA DE TOTALES DEL INDICADOR 7*/
                    obj.combinarCeldas("7. TASA DE EGRESADOS SATISFECHOS TOTAL", contCol, contCol + 2, 1, 1, "h1");
                    obj.val(contCol, 2, "Total de egresados muy satisfechos y satisfechos", "h2");
                    obj.val(contCol + 1, 2, "Total de egresados", "h2");
                    obj.val(contCol + 2, 2, "%", "h2");
                    obj.combinarCeldas("7. GRADO DE SATISFACCION DE LOS EGRESADOS (escala de 10) TOTAL", contCol + 3, contCol + 3, 1, 2, "h1");
                }
                contCol += 4;
            }
        } catch (Exception ex) {
            System.err.println("ERROR: EXCEPTION en Indicador 7 : \n" + ex.getMessage());
        }
    }

    private void indicador8() {
        try {
            for (int nivel = 0; nivel <= nombreNivel.size(); nivel++) {//MUEVE LOS NIVELES 
                if (nivel != nombreNivel.size()) {//CUERPO
                    obj.combinarCeldas("7. TASA DE EGRESADOS SATISFECHOS DEL NIVEL " + nombreNivel.get(nivel).toString(), contCol, contCol + 2, 1, 1, "h1");
                    obj.val(contCol, 2, "Total de egresados muy satisfechos y satisfechos", "h2");
                    obj.val(contCol + 1, 2, "Total de egresados", "h2");
                    obj.val(contCol + 2, 2, "%", "h2");
                    obj.combinarCeldas("7. GRADO DE SATISFACCION DE LOS EGRESADOS (escala de 10) DEL NIVEL " + nombreNivel.get(nivel).toString(), contCol + 3, contCol + 3, 1, 2, "h1");
                    for (int unis = 0; unis <= ids.size(); unis++) {
                        if (unis != ids.size()) {//CUERPO DE LA TABLA
                            if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) {/*TIENE EL NIVEL*/
                                consulta = "SELECT SUM(egresados) AS egreso FROM eficaciain5 WHERE anio=2 AND"
                                        .concat(" id_universidad=").concat(ids.get(unis).toString())
                                        .concat(" AND id_periodo=").concat(periodo);
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    if (rs.next()) {//INDICADOR LLENO
                                    } else {//INDICADOR VACÍO
                                        for (int p = 0; p <= 2; p++) { //falta llenar el indicador 7
                                            obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                        }
                                    }
                                } else {
                                    for (int p = 0; p <= 2; p++) { //falta llenar el indicador 5 
                                        obj.val(contCol + p, unis + 3, "NO CONCLUIDO", "error");
                                    }
                                }
                            } else {/*NO TIENE NIVEL*/
                                for (int p = 0; p <= 2; p++) {
                                    obj.val(contCol + p, unis + 3, "NO TIENE EL NIVEL", "error");
                                }

                            }
                        } else {//TOTALES INFERIORES
                            
                        }
                    }
                } else {/*TABLA DE TOTALES DEL INDICADOR 7*/

                }
                contCol += 4;
            }
        } catch (Exception ex) {
            System.err.println("ERROR: EXCEPTION en Indicador 7 : \n" + ex.getMessage());
        }
    }

    private boolean contieneNivel(String nivel, String idUniversidad) {
        boolean bandera = false;
        try {
            consulta = "SELECT ".concat(nivel).concat(" IN (SELECT id_nivel FROM programa_educativo ")
                    .concat(" WHERE id_pe IN ( SELECT DISTINCT id_pe FROM pe_universidad WHERE ")
                    .concat(" id_universidad=").concat(idUniversidad)
                    .concat(" AND id_periodo=").concat(periodo).concat(" AND activo=1)) AS TieneNivel");
            rs = con.Consultar(consulta);
            if (rs.next()) {
                bandera = rs.getBoolean("TieneNivel");
            }
        } catch (SQLException ex) {
            System.err.println("\n\n\nError: método contiene nivel: " + ex.getMessage());
            return false;
        }
        return bandera;
    }

    private float getPorcentaje(int x, int y) {
        float porcentaje = 0;
        if (y != 0) {
            porcentaje = ((float) x / (float) y) * 100;
        }
        return porcentaje;
    }

    private int getMuestreo(int poblacion) {
        int muestreo = 0;
        if (poblacion >= 2835) {
            muestreo = (int) (poblacion * .15);
        } else if (poblacion >= 1501) {
            muestreo = 300;
        } else if (poblacion >= 1001) {
            muestreo = 250;
        } else if (poblacion >= 751) {
            muestreo = 200;
        } else if (poblacion >= 501) {
            muestreo = 150;
        } else if (poblacion >= 401) {
            muestreo = 100;
        } else if (poblacion >= 301) {
            muestreo = 90;
        } else if (poblacion >= 201) {
            muestreo = 75;
        } else if (poblacion >= 151) {
            muestreo = 60;
        } else if (poblacion >= 101) {
            muestreo = 50;
        } else if (poblacion >= 71) {
            muestreo = 40;
        } else if (poblacion >= 31) {
            muestreo = 30;
        } else {
            //MUESTREO ES IGUAL AL TOTAL DE POBLACION
            muestreo = poblacion;
        }
        return muestreo;
    }

    private float getGradoSatisfaccion(String idUniversidad, String idNivel, String indicador) {
        float gradoSatisfaccion = 0;
        float a = 0, b = 0, c = 0, d = 0, e = 0;
        try {
            consulta = "SELECT SUM(r_a) as a, SUM(r_b) as b, SUM(r_c) as c, SUM(r_d) as d, SUM(r_e) as e "
                    .concat(" FROM eficaciain7_10 WHERE id_pregunta IN ")
                    .concat(" (SELECT id_pregunta FROM encuesta_preguntas WHERE id_encuesta = (SELECT id_encuesta FROM encuestas WHERE indicador=")
                    .concat(indicador).concat(" AND id_nivel=")
                    .concat(idNivel).concat(" AND ")
                    .concat(" id_universidad=").concat(idUniversidad)
                    .concat(" AND id_periodo=").concat(periodo).concat("))");
            rs = con.Consultar(consulta);
            if (rs.next()) {
                a = rs.getInt("a");
                b = rs.getInt("b");
                c = rs.getInt("c");
                d = rs.getInt("d");
                e = rs.getInt("e");
            }
            if ((a + b + c + d + e) != 0) {
                gradoSatisfaccion = (((a * 5) + (b * 4) + (c * 3) + (d * 2) + (e * 1)) / (a + b + c + d + e)) * 2;
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Sabana General getGradoSatisfaccion: " + ex.getMessage());
            return gradoSatisfaccion;
        }
        return gradoSatisfaccion;
    }
}