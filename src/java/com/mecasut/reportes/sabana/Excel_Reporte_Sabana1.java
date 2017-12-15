/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

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
public class Excel_Reporte_Sabana1 extends org.apache.struts.action.Action {

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
    ArrayList idCausa;
    ArrayList nombreCausa;
    ArrayList idModalidad;
    ArrayList nombreModalidad;
    ArrayList matriculas;
    ArrayList ptcs;
    ArrayList certificaciones;
    ArrayList certifName;
    HojaCalculo obj;
    int contCol;
    int c;
    int f;

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
             * ------------- CONSULTA GENERAL PARA CONOCER LOS IDS Y NOMBRES DE LOS NIVELES
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
            /*
             * ------------- CONSULTA GENERAL PARA LAS MATRICULAS INICIALES ATENDIDAS EN LAS UNIVERSIDADES
             */
            matriculas = new ArrayList();
            ptcs = new ArrayList();
            try {
                for (int i = 0; i < ids.size(); i++) {
                    consulta = "SELECT matricula_total, prof_tc FROM datos_universidad "
                            .concat(" WHERE id_universidad=").concat(ids.get(i).toString())
                            .concat(" AND id_periodo=").concat(periodo);
                    rs = con.Consultar(consulta);
                    if (rs.next()) {
                        matriculas.add(rs.getString("matricula_total"));
                        ptcs.add(rs.getString("prof_tc"));
                    } else {
                        matriculas.add("0");
                        ptcs.add("0");
                    }
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana reporte, consultando las matriculas y los profesores de tiempo completo  " + ex.getMessage());
            }



            /*
             * ------------- CONSULTA GENERAL PARA CONOCER LAS CERTIFICACIONES
             */
            certificaciones = new ArrayList();
            certifName = new ArrayList();
            try {
                consulta = "SELECT id_certificacion, nombre_certificacion FROM certificaciones";
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    certificaciones.add(rs.getInt("id_certificacion"));
                    certifName.add(rs.getString("nombre_certificacion"));
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana reporte, consultando las certificaciones " + ex.getMessage());
            }


            /*
             * ------------- CONSULTA GENERAL PARA CONOCER LOS IDS Y NOMBRES DE LAS CAUSAS DE DESERCION
             */
            idCausa = new ArrayList();
            nombreCausa = new ArrayList();
            try {
                consulta = "SELECT id_causa, descripcion FROM causas_desercion";
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    idCausa.add(rs.getString("id_causa"));
                    nombreCausa.add(rs.getString("descripcion"));
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana reporte, consultando las causas de desercion" + ex.getMessage());
            }


            /*
             * ------------- CONSULTA GENERAL PARA CONOCER LOS IDS Y NOMBRES DE LAS MODALIDADES
             */
            idModalidad = new ArrayList();
            nombreModalidad = new ArrayList();
            try {
                consulta = "SELECT id_modelo, descripcion FROM modelos";
                rs = con.Consultar(consulta);
                while (rs.next()) {
                    idModalidad.add(rs.getString("id_modelo"));
                    nombreModalidad.add(rs.getString("descripcion"));
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana reporte, consultando las modalidades" + ex.getMessage());
            }

            f = 5 + cantidadUniversidades;

            String rutaRelativaWeb = "/files/Sabana2.xlsx";//Especifico en donde se encuentra el archivo de excel en forma relativa
            String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);//Ocupo una función para obtener la ruta absoluta del archivo dentro del servidor
            InputStream inp = new FileInputStream(rutaAbsolutaEnDisco);//Cargo en archivo en un Stream
            obj = new HojaCalculo();
            obj.leerLibro(inp);
            inp.close();
            obj.obtenerEstilos();
            obj.eliminarHoja(0);

            indicador1();
            indicador2();
            indicador3();
            indicador4();
            indicador4b();
            indicador5();
       
            indicador11();
            indicador12();
            indicador13();
            indicador13b();
            indicador14();
            indicador15();
            indicador16();
            indicador17();
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

    private void columnasPrincipales(int numero) {
        obj.setAlturaFilas(1, 30);
        obj.setAlturaFilas(2, 40);
        obj.setAnchoColumna(1, 17500);
        obj.combinarCeldas("NO", 0, 0, 1, numero, "h1");
        obj.combinarCeldas("UNIVERSIDAD", 1, 1, 1, numero, "h1");
        for (int i = 0; i <= ids.size() - 1; i++) {
            obj.val(0, i + numero + 1, String.valueOf((i + 1)), "resultado");//CONSECUTIVO
            obj.val(1, i + numero + 1, univs.get(i).toString(), "resultado");//NOMBRE DE LA UNIVERSIDAD
        }
    }

    private void indicador1() {
        c = 9;
        obj.crearHoja("Ind. 01 ", f, c);
        obj.setAlturaFilas(1, 30);
        obj.setAlturaFilas(2, 60);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        obj.combinarCeldas("NO", 0, 0, 1, 2, "h1");
        obj.combinarCeldas("UNIVERSIDAD", 1, 1, 1, 2, "h1");
        obj.combinarCeldas("1. ALUMNOS DE NUEVO INGRESO CON EXANI II", 2, 8, 1, 1, "h1");
        obj.val(2, 2, "Egresados de bachillerato que presentaron el EXANI - II en la UT", "h2");
        obj.val(3, 2, "Alumnos de nuevo ingreso a la UT", "h2");
        obj.val(4, 2, "Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 1,101 a 1,300 puntos de calificación", "h2");
        obj.val(5, 2, "Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 901 a 1,100 puntos de calificación", "h2");
        obj.val(6, 2, "Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 700 a 900 puntos de calificación", "h2");
        obj.val(7, 2, "Alumnos de nuevo ingreso inscritos a la universidad que presentaron el EXANI II en el ciclo escolar", "h2");
        obj.val(8, 2, "Alumnos de nuevo ingreso inscritos a la universidad que no presentaron EXANI - II", "h2");
        for (int i = 0; i <= ids.size() - 1; i++) {
            obj.val(0, i + 3, String.valueOf((i + 1)), "resultado");//CONSECUTIVO
            obj.val(1, i + 3, univs.get(i).toString(), "resultado");//NOMBRE DE LA UNIVERSIDAD
            try {
                consulta = "SELECT aplica, c1, c2, c3, (c1+c2+c3) as ingreso, egresados_bachillerato, nuevo_ingreso "
                        .concat(" FROM eficaciain1 WHERE id_universidad=").concat(ids.get(i).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    if (rs.getBoolean("aplica")) {
                        /*ASIGNAR VALORES*/
                        obj.val(2, i + 3, rs.getInt("egresados_bachillerato"), "datos");
                        obj.val(3, i + 3, rs.getInt("nuevo_ingreso"), "datos");
                        obj.val(4, i + 3, rs.getInt("c1"), "datos");
                        obj.val(5, i + 3, rs.getInt("c2"), "datos");
                        obj.val(6, i + 3, rs.getInt("c3"), "datos");
                        obj.val(7, i + 3, rs.getInt("ingreso"), "datos");
                        obj.val(8, i + 3, rs.getInt("nuevo_ingreso") - rs.getInt("ingreso"), "datos");
                    } else {/*NO APLICA*/
                        obj.combinarCeldas("NO APLICA", 2, 8, i + 3, i + 3, "error");
                    }
                } else { // no devolvió nada la consulta, por lo tanto la universidad no ha llenado el indicador
                    obj.combinarCeldas("NO CONCLUIDO", 2, 8, i + 3, i + 3, "error");
                }
            } catch (Exception ex) {
                System.err.println("ERROR BD: Sabana general, Indicador 1 " + ex.getMessage());
            }
        }
    }

    private void indicador2() {
        c = 2 + 3 * (idNivel.size() + 1);
        obj.crearHoja("Ind. 02 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO NIVEL 5B Versión 2004", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol, 2, "sep-dic", "h2");
            obj.val(contCol + 1, 2, "ene-abr", "h2");
            obj.val(contCol + 2, 2, "may-ago", "h2");
            for (int x = 0; x < ids.size(); x++) {
                consulta = "SELECT id_pe FROM pe_universidad WHERE id_universidad=".concat(ids.get(x).toString())
                        .concat(" AND id_periodo=").concat(periodo)
                        .concat(" AND id_pe IN(select id_pe from programa_educativo where id_nivel=1 AND version=2004)");
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    consulta = "SELECT sep, ene, may "
                            .concat(" FROM eficaciain2 WHERE id_universidad=").concat(ids.get(x).toString())
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_pe IN (select id_pe from programa_educativo where version=2004)");
                    rs = con.Consultar(consulta);
                    if (rs.next()) {
                        /*ASIGNACION DE VALORES*/
                        obj.val(contCol, x + 3, rs.getFloat("sep"), "datos2");
                        obj.val(contCol + 1, x + 3, rs.getFloat("ene"), "datos2");
                        obj.val(contCol + 2, x + 3, rs.getFloat("may"), "datos2");
                    } else {
                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, x + 3, x + 3, "error");
                    }
                } else { // NO TIENE EL NIVEL
                    obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, x + 3, x + 3, "error");
                }
            }
            contCol += 3;
            for (int i = 0; i < idNivel.size(); i++) {
                //ENCABEZADOS
                obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO DEL NIVEL " + nombreNivel.get(i), contCol, contCol + 2, 1, 1, "h1");
                obj.val(contCol, 2, "sep-dic", "h2");
                obj.val(contCol + 1, 2, "ene-abr", "h2");
                obj.val(contCol + 2, 2, "may-ago", "h2");
                for (int x = 0; x < ids.size(); x++) {
                    if (contieneNivel(idNivel.get(i).toString(), ids.get(x).toString())) {
                        consulta = "SELECT sep, ene, may "
                                .concat(" FROM eficaciain2 WHERE id_universidad=").concat(ids.get(x).toString())
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_pe IN (SELECT id_pe FROM programa_educativo WHERE id_nivel=")
                                .concat(idNivel.get(i).toString()).concat(")");
                        rs = con.Consultar(consulta);
                        if (rs.next()) { /*CONSULTA LOS DATOS*/
                            /*ASIGNACION DE VALORES*/
                            obj.val(contCol, x + 3, rs.getFloat("sep"), "datos2");
                            obj.val(contCol + 1, x + 3, rs.getFloat("ene"), "datos2");
                            obj.val(contCol + 2, x + 3, rs.getFloat("may"), "datos2");
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, x + 3, x + 3, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, x + 3, x + 3, "error");
                    }
                }
                contCol += 3;
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Sabana Indicador 2. << " + ex.getMessage());
        }
    }

    private void indicador3() {

        c = 2 + 6 * idNivel.size();
        obj.crearHoja("Ind. 03 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;

        try {
            for (int i = 0; i < idNivel.size(); i++) { //MUEVE LOS NIVELES
                //ENCABEZADOS
                obj.combinarCeldas("3. REPROBACIÓN CUATRIMETRAL ".concat(nombreNivel.get(i).toString()), contCol, contCol + 5, 1, 1, "h1");
                obj.val(contCol, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre Sep-Dic ", "h2");
                obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 2, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre Ene-Abr ", "h2");
                obj.val(contCol + 3, 2, "Matrícula inicial atendida cuatrimestre Ene-Abr", "h2");
                obj.val(contCol + 4, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre May-Ago ", "h2");
                obj.val(contCol + 5, 2, "Matrícula inicial atendida cuatrimestre May-Ago", "h2");
                for (int unis = 0; unis < ids.size(); unis++) {
                    if (contieneNivel(idNivel.get(i).toString(), ids.get(unis).toString())) { //la universidad si tiene el nivel
                        consulta = "SELECT rep_sep, mat_sep, rep_ene, mat_ene, rep_may, mat_may FROM eficaciain3 "
                                .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(idNivel.get(i).toString());
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            obj.val(contCol, unis + 3, rs.getInt("rep_sep"), "datos");
                            obj.val(contCol + 1, unis + 3, rs.getInt("mat_sep"), "datos");
                            obj.val(contCol + 2, unis + 3, rs.getInt("rep_ene"), "datos");
                            obj.val(contCol + 3, unis + 3, rs.getInt("mat_ene"), "datos");
                            obj.val(contCol + 4, unis + 3, rs.getInt("rep_may"), "datos");
                            obj.val(contCol + 5, unis + 3, rs.getInt("mat_may"), "datos");
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 5, unis + 3, unis + 3, "error");
                        }
                    } else {//la universidad no tiene el nivel
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 5, unis + 3, unis + 3, "error");
                    }
                }
                contCol += 6;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 3 " + ex.getMessage());
        }
    }

    private void indicador4() {
        c = 2 + 6 * idNivel.size();
        obj.crearHoja("Ind. 04 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;

        int mat_ene = 0, mat_sep = 0, mat_may = 0;

        try {
            for (int nivel = 0; nivel < idNivel.size(); nivel++) { //MUEVE LOS NIVELES
                //ENCABEZADOS
                obj.combinarCeldas("4. DESERCIÓN CUATRIMETRAL ".concat(nombreNivel.get(nivel).toString()), contCol, contCol + 5, 1, 1, "h1");
                obj.val(contCol, 2, "Alumnos desertores definitivos del cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 2, 2, "Alumnos desertores definitivos del cuatrimestre Ene-Abr ", "h2");
                obj.val(contCol + 3, 2, "Matrícula inicial atendida cuatrimestre Ene-Abr", "h2");
                obj.val(contCol + 4, 2, "Alumnos desertores definitivos del cuatrimestre May-Ago", "h2");
                obj.val(contCol + 5, 2, "Matrícula inicial atendida cuatrimestre May-Ago", "h2");
                for (int unis = 0; unis < ids.size(); unis++) {
                    if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) { //la universidad si tiene el nivel
                        consulta = "SELECT mat_sep, mat_ene, mat_may FROM eficaciain3 "
                                .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                        rs = con.Consultar(consulta);
                        if (rs.next()) {//si ya llenaron el indicador 3
                            mat_sep = rs.getInt("mat_sep");
                            mat_ene = rs.getInt("mat_ene");
                            mat_may = rs.getInt("mat_may");
                            consulta = "SELECT tot_deser_sep, tot_deser_ene, tot_deser_may FROM eficaciain4_1"
                                    .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                            rs = con.Consultar(consulta);
                            if (rs.next()) { //está llenado el ind 3, pero no el 4
                                obj.val(contCol, unis + 3, rs.getInt("tot_deser_sep"), "datos");
                                obj.val(contCol + 1, unis + 3, mat_sep, "datos");
                                obj.val(contCol + 2, unis + 3, rs.getInt("tot_deser_ene"), "datos");
                                obj.val(contCol + 3, unis + 3, mat_ene, "datos");
                                obj.val(contCol + 4, unis + 3, rs.getInt("tot_deser_may"), "datos");
                                obj.val(contCol + 5, unis + 3, mat_may, "datos");
                            } else { /*NO CONCLUIDO*/
                                obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 5, unis + 3, unis + 3, "error");
                            }
                        } else { /*NO CONCLUIDO*/
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 5, unis + 3, unis + 3, "error");
                        }
                    } else { /*NO TIENE EL NIVEL*/
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 5, unis + 3, unis + 3, "error");
                    }
                }
                contCol += 6;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 4 " + ex.getMessage());
        }
    }

    private void indicador4b() {
        c = 2 + 3 * idCausa.size() * idNivel.size();
        obj.crearHoja("Ind. 04 b ", f + 1, c);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 1500);
        }
        /*COLUMNAS PRINCIPALES*/
        obj.setAnchoColumna(1, 17500);
        obj.combinarCeldas("NO", 0, 0, 1, 3, "h1");
        obj.combinarCeldas("UNIVERSIDAD", 1, 1, 1, 3, "h1");
        obj.setAlturaFilas(2, 40);
        for (int i = 0; i <= ids.size() - 1; i++) {
            obj.val(0, i + 4, String.valueOf((i + 1)), "resultado");//CONSECUTIVO
            obj.val(1, i + 4, univs.get(i).toString(), "resultado");//NOMBRE DE LA UNIVERSIDAD
        }
        /* FIN COLUMNAS PRINCIPALES*/
        contCol = 2;
        try {
            for (int nivel = 0; nivel < idNivel.size(); nivel++) { //MUEVE LOS NIVELES
                obj.combinarCeldas("4. PRINCIPALES CAUSAS DE DESERCIÓN NIVEL " + nombreNivel.get(nivel).toString(),
                        contCol, (contCol + (3 * idCausa.size()) - 1), 1, 1, "h1");
                for (int causa = 0; causa < idCausa.size(); causa++) {
                    obj.combinarCeldas(nombreCausa.get(causa).toString(), contCol, contCol + 2, 2, 2, "h1");
                    obj.val(contCol, 3, "sep", "h2");
                    obj.val(contCol + 1, 3, "ene", "h2");
                    obj.val(contCol + 2, 3, "may", "h2");
                    for (int unis = 0; unis < ids.size(); unis++) { //MUEVE LAS UNIVERSIDADES 
                        if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) {
                            consulta = "SELECT sep, ene, may FROM eficaciain4"
                                    .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_causa=").concat(idCausa.get(causa).toString())
                                    .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                            rs = con.Consultar(consulta);
                            if (rs.next()) {
                                obj.val(contCol, unis + 4, rs.getInt("Sep"), "datos");
                                obj.val(contCol + 1, unis + 4, rs.getInt("Ene"), "datos");
                                obj.val(contCol + 2, unis + 4, rs.getInt("May"), "datos");
                            } else {/*NO HAY RESPUESTA, PONER CEROS*/
                                for (int i = 0; i < 3; i++) {
                                    obj.val(contCol + i, unis + 4, 0, "datos");
                                }
                            }
                        } else {/*NO TIENE EL NIVEL*/
                            if (causa == 0) {
                                obj.combinarCeldas("NO TIENE EL NIVEL", contCol, (contCol + (3 * idCausa.size()) - 1), unis + 4, unis + 4, "error");
                            }
                        }
                    }
                    contCol += 3;
                }
            }
        } catch (Exception ex) {
            System.err.println("\n\n\nERROR: Sabana general Indicador 4 B " + ex.getMessage());
        }
    }

    private void indicador5() {
        c = 2 + 3 * 2 * idModalidad.size() * idNivel.size();
        obj.crearHoja("Ind. 05 ", f + 1, c);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 2000);
        }
        /*COLUMNAS PRINCIPALES*/
        obj.setAnchoColumna(1, 17500);
        obj.combinarCeldas("NO", 0, 0, 1, 3, "h1");
        obj.combinarCeldas("UNIVERSIDAD", 1, 1, 1, 3, "h1");
        obj.setAlturaFilas(2, 40);
        for (int i = 0; i <= ids.size() - 1; i++) {
            obj.val(0, i + 4, String.valueOf((i + 1)), "resultado");//CONSECUTIVO
            obj.val(1, i + 4, univs.get(i).toString(), "resultado");//NOMBRE DE LA UNIVERSIDAD
        }
        /* FIN COLUMNAS PRINCIPALES*/
        contCol = 2;
        try {
            for (int nivel = 0; nivel < idNivel.size(); nivel++) { //MUEVE LOS NIVELES
                for (int an = 1; an <= 2; an++) {
                    if (an == 1) {
                        obj.combinarCeldas("5. TASA DE EGRESO Y TITULACION NIVEL " + nombreNivel.get(nivel).toString()
                                + " (AÑO " + String.valueOf(Integer.parseInt(anio) - 1) + ")",
                                contCol, (contCol + (3 * idModalidad.size()) - 1), 1, 1, "h1");
                    } else {
                        obj.combinarCeldas("5. TASA DE EGRESO Y TITULACION NIVEL " + nombreNivel.get(nivel).toString() + " (AÑO" + (anio) + ")",
                                contCol, (contCol + (3 * idModalidad.size()) - 1), 1, 1, "h1");
                    }
                    for (int modalidad = 0; modalidad < idModalidad.size(); modalidad++) { //MUEVE LAS MODALIDADES
                        obj.combinarCeldas(nombreModalidad.get(modalidad).toString(), contCol, contCol + 2, 2, 2, "h1");
                        obj.val(contCol, 3, "Ingreso", "h2");
                        obj.val(contCol + 1, 3, "Egresados", "h2");
                        obj.val(contCol + 2, 3, "Titulados", "h2");
                        for (int unis = 0; unis < ids.size(); unis++) { //MUEVE LAS UNIVERSIDADES 
                            if (contieneNivel(ids.get(unis).toString(), idNivel.get(nivel).toString())) {
                                consulta = "SELECT SUM( ingreso ) AS ingreso, SUM( egresados ) AS egresados, SUM( titulados ) AS titulados FROM eficaciain5 "
                                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                                        .concat(" AND id_periodo=").concat(periodo)
                                        .concat(" AND anio=").concat(String.valueOf(an))
                                        .concat(" AND id_modelo=").concat(idModalidad.get(modalidad).toString())
                                        .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    obj.val(contCol, unis + 4, rs.getInt("ingreso"), "datos");
                                    obj.val(contCol + 1, unis + 4, rs.getInt("egresados"), "datos");
                                    obj.val(contCol + 2, unis + 4, rs.getInt("titulados"), "datos");
                                } else {/*NO HAY RESPUESTA, PONER CEROS*/
                                    for (int i = 0; i < 3; i++) {
                                        obj.val(contCol + i, unis + 4, 0, "datos");
                                    }
                                }
                            } else {/*NO TIENE EL NIVEL*/
                                if (modalidad == 0) {
                                    obj.combinarCeldas("NO TIENE EL NIVEL", contCol, (contCol + (3 * idModalidad.size()) - 1), unis + 4, unis + 4, "error");
                                }
                            }
                        }
                        contCol += 3;
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("\n\n\n\nERROR: Sabana general Indicador 5" + ex.getMessage());
        }
    }

    private void indicador5b() {
    }

    private void indicador6() {
        c = 2 + 3 * (idNivel.size() + 1);
        obj.crearHoja("Ind. 06 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            int egresados = 0;
            for (int nivel = 0; nivel < idNivel.size(); nivel++) {
                obj.combinarCeldas("6. Egresados" + nombreNivel.get(nivel).toString() + " laborando a 6 meses de su egreso y que trabajan en el área afin",
                        contCol, contCol + 2, 1, 1, "h1");
                obj.val(contCol, 2, "Egresados trabajando a 6 meses de su egreso", "h2");
                obj.val(contCol + 1, 2, "Total de egresados", "h2");
                obj.val(contCol + 2, 2, "Egresados cuya actividad laboral es acorde a su formación académica por programa educativo", "h2");
                for (int unis = 0; unis < ids.size(); unis++) {
                    if (contieneNivel(idNivel.get(nivel).toString(), ids.get(unis).toString())) {
                        if (Integer.parseInt(idNivel.get(nivel).toString()) <= 2) {
                            consulta = "SELECT SUM(egresados) FROM eficaciain5 WHERE "
                                    .concat(" id_universidad=").concat(String.valueOf(ids.get(unis).toString()))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString())
                                    .concat(" AND anio=1");
                        } else {
                            consulta = "SELECT SUM(egresados) as egresados FROM eficaciain5 WHERE "
                                    .concat(" id_universidad=").concat(String.valueOf(ids.get(unis).toString()))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString())
                                    .concat(" AND anio=2");
                        }
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            egresados = rs.getInt("egresados");
                            consulta = "SELECT e_trabajando, afin FROM eficaciain5 WHERE "
                                    .concat(" id_universidad=").concat(String.valueOf(ids.get(unis).toString()))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_nivel=").concat(idNivel.get(nivel).toString());
                            rs = con.Consultar(consulta);
                            if (rs.next()) {
                                obj.val(contCol, unis + 3, rs.getInt("e_trabajando"), "datos");
                                obj.val(contCol + 1, unis + 3, egresados, "datos");
                                obj.val(contCol + 2, unis + 3, rs.getInt("afin"), "datos");
                            } else {/*NO SE HA CONCLUIDO*/
                                obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 3, unis + 3, "error");
                            }
                        } else {/*NO SE HA CONCLUIDO EL INDICADOR 5*/
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 3, unis + 3, "error");
                        }
                    } else {/*NO TIENE EL NIVEL*/
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, unis + 3, unis + 3, "error");
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Sabana Indicador 6. << " + ex.getMessage());
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

    private void indicador9() {
    }

    private void indicador10() {
    }

    private void indicador11() {
        c = 4;
        obj.crearHoja("Ind. 11 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("11. PRESUPUESTO EJERCIDO", contCol, contCol + 1, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto ejercido", "h2");
            obj.val(contCol + 1, 2, "Presupuesto total autorizado Federal y Estatal", "h2");
            for (int unis = 0; unis < ids.size(); unis++) {
                consulta = "SELECT ejercido, autorizado FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("ejercido"), "datos2");
                    obj.val(contCol + 1, unis + 3, rs.getDouble("autorizado"), "datos2");
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 3, unis + 3, "error");
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 11 " + ex.getMessage());
        }
    }

    private void indicador12() {
        c = 4;
        obj.crearHoja("Ind. 12 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("12. COSTO POR ALUMNO", contCol, contCol + 1, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto total autorizado Federal y Estatal", "h2");
            obj.val(contCol + 1, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            for (int unis = 0; unis < ids.size(); unis++) {
                consulta = "SELECT autorizado FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("autorizado"), "datos2");
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, "NO CONCLUIDO", "error");
                }
                obj.val(contCol + 1, unis + 3, Integer.parseInt(matriculas.get(unis).toString()), "datos");
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 12 " + ex.getMessage());
        }
    }

    private void indicador13() {
        c = 7;
        obj.crearHoja("Ind. 13 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("13. CAPACIDAD INSTALADA EN LAS UNIVERSIDADES", contCol, contCol + 3, 1, 1, "h1");
            obj.val(contCol, 2, "Cantidad total de edificios", "h2");
            obj.val(contCol + 1, 2, "Total de espacios docentes", "h2");
            obj.val(contCol + 2, 2, "Total del espacio académico ocupado por otras áreas de trabajo", "h2");
            obj.val(contCol + 3, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            for (int unis = 0; unis < ids.size(); unis++) {
                consulta = "SELECT SUM(i.id_edifi) as 'cantidadEdificios', SUM(e.capacidad*i.no_laboratorios) as 'totalEspacios', "
                        .concat(" SUM(i.espacio_academico) as 'espacioAcademico' FROM eficienciain13 i INNER JOIN edificios ")
                        .concat(" e ON i.id_edifi=e.id_edificio")
                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getInt("cantidadEdificios"), "datos");
                    obj.val(contCol + 1, unis + 3, rs.getInt("totalEspacios"), "datos");
                    obj.val(contCol + 2, unis + 3, rs.getInt("espacioAcademico"), "datos");
                    obj.val(contCol + 3, unis + 3, Integer.parseInt(matriculas.get(unis).toString()), "datos");
                } else {
                    obj.combinarCeldas("NO CONLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 13 " + ex.getMessage());
        }
    }

    private void indicador13b() {
        c = 5;
        obj.crearHoja("Ind. 13 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("13 b. DISTRIBUCIÓN DE LA MATRÍCULA INICIAL DEL CICLO ESCOLAR", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol, 2, "Turno matutino", "h2");
            obj.val(contCol + 1, 2, "Turno vespertino", "h2");
            obj.val(contCol + 1, 2, "Turno vespertino", "h2");
            obj.val(contCol + 2, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            for (int unis = 0; unis < ids.size(); unis++) {
                consulta = "SELECT turno_matutino, turno_vespertino FROM eficienciain13_1 "
                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getInt("turno_matutino"), "datos");
                    obj.val(contCol + 1, unis + 3, rs.getInt("turno_vespertino"), "datos");
                } else {
                    obj.combinarCeldas("NO CONLUIDO", contCol, contCol + 1, unis + 3, unis + 3, "error");
                }
                obj.val(contCol + 2, unis + 3, Integer.parseInt(matriculas.get(unis).toString()), "datos");
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 13 " + ex.getMessage());
        }
    }

    private void indicador14() {
        c = 12;
        obj.crearHoja("Ind. 14 ", f + 1, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        obj.setAlturaFilas(2, 25);
        obj.setAlturaFilas(3, 40);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("14. UTILIZACIÓN DEL EQUIPO DE CÓMPUTO", contCol, contCol + 9, 1, 1, "h1");
            obj.combinarCeldas("Distribución del Equipo de Cómputo", contCol, contCol + 4, 2, 2, "h1");
            obj.combinarCeldas("Distribución del Equipo de Cómputo con Internet", contCol + 5, contCol + 9, 2, 2, "h1");
            obj.val(contCol, 3, "Docente de tiempo completo", "h2");
            obj.val(contCol + 1, 3, "Docente de Asignatura", "h2");
            obj.val(contCol + 2, 3, "Alumnos", "h2");
            obj.val(contCol + 3, 3, "Personal Administrativo", "h2");
            obj.val(contCol + 4, 3, "Mandos Medios y Superiores", "h2");
            obj.val(contCol + 5, 3, "Docente de tiempo completo", "h2");
            obj.val(contCol + 6, 3, "Docente de Asignatura", "h2");
            obj.val(contCol + 7, 3, "Alumnos", "h2");
            obj.val(contCol + 8, 3, "Personal Administrativo", "h2");
            obj.val(contCol + 9, 3, "Mandos Medios y Superiores", "h2");
            for (int unis = 0; unis < ids.size(); unis++) {
                consulta = "SELECT docente_ptc, docente_asignatura, alumnos, personal_admin, mandos, docentes_ptc_int, "
                        .concat(" docente_asignatura_int, alumnos_int, personal_admin_int, mandos_int FROM eficienciain14")
                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 4, rs.getInt("docente_ptc"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("docente_asignatura"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("alumnos"), "datos");
                    obj.val(contCol + 3, unis + 4, rs.getInt("personal_admin"), "datos");
                    obj.val(contCol + 4, unis + 4, rs.getInt("mandos"), "datos");
                    obj.val(contCol + 5, unis + 4, rs.getInt("docentes_ptc_int"), "datos");
                    obj.val(contCol + 6, unis + 4, rs.getInt("docente_asignatura_int"), "datos");
                    obj.val(contCol + 7, unis + 4, rs.getInt("alumnos_int"), "datos");
                    obj.val(contCol + 8, unis + 4, rs.getInt("personal_admin_int"), "datos");
                    obj.val(contCol + 9, unis + 4, rs.getInt("mandos_int"), "datos");
                } else {
                    obj.combinarCeldas("NO CONLUIDO", contCol, contCol + 4, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONLUIDO", contCol + 5, contCol + 9, unis + 4, unis + 4, "error");
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 14 " + ex.getMessage());
        }
    }

    private void indicador15() {
        c = 2 + (certificaciones.size() * 5);
        obj.crearHoja("Ind. 15", f + 1, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        obj.setAlturaFilas(2, 25);
        obj.setAlturaFilas(3, 40);
        contCol = 2;
        /*titulo general*/
        try {
            for (int cert = 0; cert < certificaciones.size(); cert++) {//mueve las certificaciones
                obj.combinarCeldas("15. SITUCACIÓN DE CERTIFICACIONES", contCol, contCol + 4, 1, 1, "h1");
                obj.combinarCeldas("Certificado" + certifName.get(cert).toString(), contCol, contCol + 4, 2, 2, "h1");
                obj.val(contCol, 3, "Sin certificado", "h2");
                obj.val(contCol + 1, 3, "En proceso de certificación", "h2");
                obj.val(contCol + 2, 3, "Con certificación", "h2");
                obj.val(contCol + 3, 3, "En proceso de recertificación", "h2");
                obj.val(contCol + 4, 3, "Con recertificado", "h2");
                for (int unis = 0; unis < ids.size(); unis++) {
                    consulta = "SELECT sin_certificado, en_proceso, con_certificado, en_proceso_recertificacion, con_recertificado "
                            .concat(" FROM eficienciain15 ")
                            .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                            .concat(" AND id_periodo=").concat(periodo);
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 4, rs.getInt("sin_certificado"), "datos");
                        obj.val(contCol + 1, unis + 4, rs.getInt("en_proceso"), "datos");
                        obj.val(contCol + 2, unis + 4, rs.getInt("con_certificado"), "datos");
                        obj.val(contCol + 3, unis + 4, rs.getInt("en_proceso_recertificacion"), "datos");
                        obj.val(contCol + 4, unis + 4, rs.getInt("con_recertificado"), "datos");
                    } else {
                        obj.combinarCeldas("NO CONLUIDO", contCol, contCol + 4, unis + 4, unis + 4, "error");
                    }
                }
                contCol += 5;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 15 " + ex.getMessage());
        }
    }

    private void indicador16() {
        c = 6;
        obj.crearHoja("Ind. 16 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("16. DISTRIBUCIÓN DE LIBROS Y TÍTULOS POR ALUMNOS", contCol, contCol + 3, 1, 1, "h1");
            obj.val(contCol, 2, "Número de libros", "h2");
            obj.val(contCol + 1, 2, "Número de títulos", "h2");
            obj.val(contCol + 2, 2, "Número de subscripciones a revistas físicamente o electrónicas vigentes al ciclo evaluado", "h2");
            obj.val(contCol + 3, 2, "Número de subscripciones a bibliotecas virtuales vigentes al ciclo escolar evaluado para ser consultadas por los alumnos", "h2");
            for (int unis = 0; unis < ids.size(); unis++) {
                consulta = "SELECT libros, titulos, revistas, bibliotecas FROM eficienciain16 "
                        .concat(" WHERE id_universidad=").concat(ids.get(unis).toString())
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getInt("libros"), "datos");
                    obj.val(contCol + 1, unis + 3, rs.getInt("titulos"), "datos");
                    obj.val(contCol + 2, unis + 3, rs.getInt("revistas"), "datos");
                    obj.val(contCol + 3, unis + 3, rs.getInt("bibliotecas"), "datos");
                } else {
                    obj.combinarCeldas("NO CONLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 16 " + ex.getMessage());
        }
    }

    private void indicador17() {
        c = 4;
        obj.crearHoja("Ind. 17 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("17. RELACIÓN ALUMNO / DOCENTE", contCol, contCol + 1, 1, 1, "h1");
            obj.val(contCol, 2, "Matrícula total al inicio del ciclo escolar", "h2");
            obj.val(contCol + 1, 2, "Profesores de tiempo completo al iniciar el ciclo escolar", "h2");
            for (int unis = 0; unis < ids.size(); unis++) {
                obj.val(contCol, unis + 3, Integer.parseInt(matriculas.get(unis).toString()), "datos");
                obj.val(contCol + 1, unis + 3, Integer.parseInt(ptcs.get(unis).toString()), "datos");
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 17 " + ex.getMessage());
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
