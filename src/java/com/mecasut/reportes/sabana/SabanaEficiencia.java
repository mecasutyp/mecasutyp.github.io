/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

import java.sql.SQLException;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public class SabanaEficiencia extends SabanaCategorias {

    ListaCertificaciones certificaciones;
    ListaEdificios edificios;
    int contCol = 0;
    int c = 0;
    int f = 5;

    public SabanaEficiencia() {
        super();
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Sabana Eficiencia Eliminada :)");
    }

    public SabanaEficiencia(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.certificaciones = new ListaCertificaciones();
        this.edificios = new ListaEdificios();
        this.f += univs.size();
    }

    public HojaCalculo hacerEficiencia() {
        indicador12();
        indicador13();
        indicador13b();
        indicador14();
        indicador15();
        indicador16();
        indicador17();
        niveles = null;
        con.Desconectar();
        return obj;
    }

    private void indicador12() {
        double totalcol0=0;
        int totalcol1=0, unistmp=0;
        c = 5;
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
            obj.combinarCeldas("12. COSTO POR ALUMNO", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto total autorizado Federal y Estatal", "h2");
            obj.val(contCol + 1, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            obj.val(contCol + 2, 2, "$", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT autorizado FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("autorizado"), "datos2");
                    totalcol0+=rs.getDouble("autorizado");
                    if(univs.getMatriculaTotal(unis)>0 && rs.getDouble("autorizado") > 0){
                         obj.val(contCol + 2, unis + 3, rs.getDouble("autorizado") / univs.getMatriculaTotal(unis), "datos");
                    }   
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, "NO CONCLUIDO", "error");
                   obj.val(contCol + 2, unis + 3, "NO CONCLUIDO", "error");
                }
                obj.val(contCol + 1, unis + 3, univs.getMatriculaTotal(unis), "datos");
                
                totalcol1+=univs.getMatriculaTotal(unis);
                unistmp=unis+1;
            }
            obj.val(contCol, unistmp + 3, totalcol0, "total2");
            obj.val(contCol + 1, unistmp + 3, totalcol1, "total");
            if(totalcol1 > 0 && totalcol0 > 0){
                obj.val(contCol + 2, unistmp + 3, totalcol0/totalcol1, "total2");
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Eficiencia Indicador 12 " + ex.getMessage());
        }
    }

    private void indicador13() {
        int totalcols[]= new int[4];
        int unistmp=0;
        
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
            obj.combinarCeldas("13. CAPACIDAD INSTALADA EN LAS UNIVERSIDADES", contCol, contCol + 4, 1, 1, "h1");
            obj.val(contCol, 2, "Número de unidades, laboratorios y talleres", "h2");
            obj.val(contCol + 1, 2, "Total de espacios docentes", "h2");
            obj.val(contCol + 2, 2, "Total del espacio académico ocupado por otras áreas de trabajo", "h2");
            obj.val(contCol + 3, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            obj.val(contCol + 4, 2, "%", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT SUM(no_laboratorios) as 'cantidadEdificios', SUM(e.capacidad*i.no_laboratorios) as 'totalEspacios', "
                        .concat(" SUM(i.espacio_academico) as 'espacioAcademico' FROM eficienciain13 i INNER JOIN edificios ")
                        .concat(" e ON i.id_edifi=e.id_edificio")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getInt("cantidadEdificios"), "datos");
                    obj.val(contCol + 1, unis + 3, rs.getInt("totalEspacios"), "datos");
                    obj.val(contCol + 2, unis + 3, rs.getInt("espacioAcademico"), "datos");
                    obj.val(contCol + 3, unis + 3, univs.getMatriculaTotal(unis), "datos");
                    if(univs.getMatriculaTotal(unis) > 0 && rs.getInt("totalEspacios") > 0){
                        obj.val(contCol + 4, unis + 3, (double)((double)univs.getMatriculaTotal(unis)/(double)rs.getInt("totalEspacios"))*100, "datos2");
                    }else{
                        obj.val(contCol + 4, unis + 3, 0, "datos");
                    }
                    totalcols[0]+=rs.getInt("cantidadEdificios");
                    totalcols[1]+=rs.getInt("totalEspacios");
                    totalcols[2]+=rs.getInt("espacioAcademico");
                    totalcols[3]+=univs.getMatriculaTotal(unis);
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            //IMPRIME LOS TOTALES DE COLUMNA
            int tco;
            for (tco = 0; tco < 4; tco++) {
                 obj.val(contCol+tco, unistmp + 3, totalcols[tco], "total");
            }
                obj.val(contCol+tco, unistmp + 3, (double)((double)totalcols[3]/(double)totalcols[1])*100, "total2");
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Eficiencia Indicador 13 " + ex.getMessage());
        }
    }

    private void indicador13b() {
        int totalcolcap[]=new int [3];
        int unistmp=0;
        c = (8 + edificios.size() * 3)+6;
        obj.crearHoja("Ind. 13 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 3000);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("13. UTILIZACIÓN DE ESPACIOS EN LAS UNIVERSIDADES", contCol, contCol + edificios.size() * 3 - 1 + 4, 1, 1, "h1");
            for (int edif = 0; edif < edificios.size(); edif++) {
                totalcolcap[0]=0;totalcolcap[1]=0;totalcolcap[2]=0;
                obj.combinarCeldas(edificios.getNombre(edif), contCol, contCol + 2, 2, 2, "h1");
                obj.val(contCol, 3, "Capacidad", "h2");
                obj.val(contCol + 1, 3, "Número", "h2");
                obj.val(contCol + 2, 3, "Total", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    obj.val(contCol, unis + 4, edificios.getCapacidad(edif), "datos");
                    totalcolcap[0]+=edificios.getCapacidad(edif);
                    consulta = "SELECT no_laboratorios FROM eficienciain13"
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_edifi=").concat(String.valueOf(edificios.getId(edif)));
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol + 1, unis + 4, rs.getInt("no_laboratorios"), "datos");
                        obj.val(contCol + 2, unis + 4, (rs.getInt("no_laboratorios")) * (edificios.getCapacidad(edif)), "datos");
                        totalcolcap[1]+=rs.getInt("no_laboratorios");
                        totalcolcap[2]+=(rs.getInt("no_laboratorios")) * (edificios.getCapacidad(edif));
                    } else {
                        obj.val(contCol + 1, unis + 4, "NO CONCLUIDO", "error");
                        obj.val(contCol + 2, unis + 4, "NO CONCLUIDO", "error");
                    }
                    unistmp=unis+1;
                }
                 //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 3; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcolcap[tco], "total");
                    }
                contCol += 3;
            }
            int totalcol2[]= new int[8];
            obj.combinarCeldas("NÚMERO TOTAL DE UNIDADES, LABORATORIOS Y TALLERES", contCol, contCol, 2, 3, "h1");
            obj.combinarCeldas("TOTAL DE ESPACIOS DOCENTES", contCol + 1, contCol + 1, 2, 3, "h1");
            obj.combinarCeldas("CAPACIDAD OCUPADA POR OTRAS ÁREAS DE TRABAJO", contCol + 2, contCol + 2, 2, 3, "h1");
            obj.combinarCeldas("MATRICULA TOTAL", contCol + 3, contCol + 3, 2, 3, "h1");
            for (int unis = 0; unis < univs.size(); unis++) {
                
                consulta = "SELECT SUM(no_laboratorios) as 'cantidadEdificios', SUM(e.capacidad*i.no_laboratorios) as 'totalEspacios', "
                        .concat(" SUM(i.espacio_academico) as 'espacioAcademico' FROM eficienciain13 i INNER JOIN edificios ")
                        .concat(" e ON i.id_edifi=e.id_edificio")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                int totalEspacios=0;
                if (rs.next()) { //si hay una respuesta de la consulta
                    totalEspacios=0;
                    obj.val(contCol, unis + 4, rs.getInt("cantidadEdificios"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("totalEspacios"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("espacioAcademico"), "datos");
                    obj.val(contCol + 3, unis + 4, univs.getMatriculaTotal(unis), "datos");
                    totalcol2[0]+=rs.getInt("cantidadEdificios");
                    totalcol2[1]+=rs.getInt("totalEspacios");
                    totalcol2[2]+=rs.getInt("espacioAcademico");
                    totalcol2[3]+=univs.getMatriculaTotal(unis);
                    totalEspacios=rs.getInt("totalEspacios");
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 4, unis + 4, "error");
                }
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                    int tco;
                    for (tco = 0; tco < 6; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol2[tco], "total");
                    }
                    //obj.val(contCol+tco, unistmp + 4, totalcol2[6] + totalcol2[7], "h1");
            for (int i = 0; i <=5; i++) {
                obj.setAnchoColumna(contCol+i, 4500);
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Eficiencia Indicador 13 b " + ex.getMessage());
        } finally {
            edificios = null;
        }
    }

    private void indicador14() {
        int totalcol []=new int[12]; int unistmp=0;
        c = 18;
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
            obj.combinarCeldas("14. UTILIZACIÓN DEL EQUIPO DE CÓMPUTO", contCol, contCol + 11, 1, 1, "h1");
            obj.combinarCeldas("Distribución del Equipo de Cómputo", contCol, contCol + 5, 2, 2, "h1");
            obj.combinarCeldas("Distribución del Equipo de Cómputo con Internet", contCol + 6, contCol + 11, 2, 2, "h1");
            obj.val(contCol, 3, "Docente de tiempo completo", "h2");
            obj.val(contCol + 1, 3, "Docente de Asignatura", "h2");
            obj.val(contCol + 2, 3, "Alumnos", "h2");
            obj.val(contCol + 3, 3, "Personal Administrativo", "h2");
            obj.val(contCol + 4, 3, "Mandos Medios y Superiores", "h2");
            obj.val(contCol + 5, 3, "TOTAL", "h2");
            obj.val(contCol + 6, 3, "Docente de tiempo completo", "h2");
            obj.val(contCol + 7, 3, "Docente de Asignatura", "h2");
            obj.val(contCol + 8, 3, "Alumnos", "h2");
            obj.val(contCol + 9, 3, "Personal Administrativo", "h2");
            obj.val(contCol + 10, 3, "Mandos Medios y Superiores", "h2");
            obj.val(contCol + 11, 3, "TOTAL", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT docente_ptc, docente_asignatura, alumnos, personal_admin, mandos, docentes_ptc_int, "
                        .concat(" docente_asignatura_int, alumnos_int, personal_admin_int, mandos_int FROM eficienciain14")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                int tot1=0, totint=0;
                if (rs.next()) { //si hay una respuesta de la consulta
                    
                    obj.val(contCol, unis + 4, rs.getInt("docente_ptc"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("docente_asignatura"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("alumnos"), "datos");
                    obj.val(contCol + 3, unis + 4, rs.getInt("personal_admin"), "datos");
                    obj.val(contCol + 4, unis + 4, rs.getInt("mandos"), "datos");
                    tot1=(rs.getInt("docente_ptc") + rs.getInt("docente_asignatura")
                            +rs.getInt("alumnos") + rs.getInt("personal_admin") + rs.getInt("mandos"));
                    obj.val(contCol + 5, unis + 4, tot1, "datos");
                    obj.val(contCol + 6, unis + 4, rs.getInt("docentes_ptc_int"), "datos");
                    obj.val(contCol + 7, unis + 4, rs.getInt("docente_asignatura_int"), "datos");
                    obj.val(contCol + 8, unis + 4, rs.getInt("alumnos_int"), "datos");
                    obj.val(contCol + 9, unis + 4, rs.getInt("personal_admin_int"), "datos");
                    obj.val(contCol + 10, unis + 4, rs.getInt("mandos_int"), "datos");
                    totint=(rs.getInt("docentes_ptc_int") + rs.getInt("docente_asignatura_int")
                            +rs.getInt("alumnos_int") + rs.getInt("personal_admin_int") + rs.getInt("mandos_int"));
                    obj.val(contCol + 11, unis + 4, totint , "datos");
                    totalcol[0]+=rs.getInt("docente_ptc");
                    totalcol[1]+=rs.getInt("docente_asignatura");
                    totalcol[2]+=rs.getInt("alumnos");
                    totalcol[3]+=rs.getInt("personal_admin");
                    totalcol[4]+=rs.getInt("mandos");
                    totalcol[5]+=tot1;
                    totalcol[6]+=rs.getInt("docentes_ptc_int");
                    totalcol[7]+=rs.getInt("docente_asignatura_int");
                    totalcol[8]+=rs.getInt("alumnos_int");
                    totalcol[9]+=rs.getInt("personal_admin_int");
                    totalcol[10]+=rs.getInt("mandos_int");
                    
                    totalcol[11]+=totint;
                    
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 6, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 7, contCol + 11, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
                //IMPRIME LOS TOTALES DE COLUMNA
                    int tco;
                    for (tco = 0; tco < 12; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
                   
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Eficiencia Indicador 14 " + ex.getMessage());
        }
    }

    private void indicador15() {
        int totalcol[]=new int[5];
        int unistmp=0;
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
                totalcol[0]=0;totalcol[1]=0;totalcol[2]=0;totalcol[3]=0;totalcol[4]=0;
                obj.combinarCeldas("15. SITUCACIÓN DE CERTIFICACIONES", contCol, contCol + 4, 1, 1, "h1");
                obj.combinarCeldas("Certificado " + certificaciones.getNombre(cert), contCol, contCol + 4, 2, 2, "h1");
                obj.val(contCol, 3, "Sin certificado", "h2");
                obj.val(contCol + 1, 3, "En proceso de certificación", "h2");
                obj.val(contCol + 2, 3, "Con certificación", "h2");
                obj.val(contCol + 3, 3, "En proceso de recertificación", "h2");
                obj.val(contCol + 4, 3, "Con recertificado", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT sin_certificado, en_proceso, con_certificado, en_proceso_recertificacion, con_recertificado "
                            .concat(" FROM eficienciain15 ")
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_certificacion=").concat(String.valueOf(certificaciones.getId(cert)));
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 4, rs.getInt("sin_certificado"), "datos");
                        obj.val(contCol + 1, unis + 4, rs.getInt("en_proceso"), "datos");
                        obj.val(contCol + 2, unis + 4, rs.getInt("con_certificado"), "datos");
                        obj.val(contCol + 3, unis + 4, rs.getInt("en_proceso_recertificacion"), "datos");
                        obj.val(contCol + 4, unis + 4, rs.getInt("con_recertificado"), "datos");
                        totalcol[0]+=rs.getInt("sin_certificado");
                        totalcol[1]+=rs.getInt("en_proceso");
                        totalcol[2]+=rs.getInt("con_certificado");
                        totalcol[3]+=rs.getInt("en_proceso_recertificacion");
                        totalcol[4]+=rs.getInt("con_recertificado");
                    } else {
                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 4, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 5; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
                contCol += 5;
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Eficiencia Indicador 15 " + ex.getMessage());
        } finally {
            certificaciones = null;
        }
    }

    private void indicador16() {
        int totalcol[]=new int[5];
        int unistmp=0;
        c = 7;
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
            obj.combinarCeldas("16. DISTRIBUCIÓN DE LIBROS Y TÍTULOS POR ALUMNOS", contCol, contCol + 4, 1, 1, "h1");
            obj.val(contCol, 2, "Número de libros", "h2");
            obj.val(contCol + 1, 2, "Número de títulos", "h2");
            obj.val(contCol + 2, 2, "Número de subscripciones a revistas físicamente o electrónicas vigentes al ciclo evaluado", "h2");
            obj.val(contCol + 3, 2, "Número de subscripciones a bibliotecas virtuales vigentes al ciclo escolar evaluado para ser consultadas por los alumnos", "h2");
            obj.val(contCol + 4, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT libros, titulos, revistas, bibliotecas FROM eficienciain16 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                obj.val(contCol + 4, unis + 3, univs.getMatriculaTotal(unis), "datos");
                totalcol[4]+= univs.getMatriculaTotal(unis);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getInt("libros"), "datos");
                    obj.val(contCol + 1, unis + 3, rs.getInt("titulos"), "datos");
                    obj.val(contCol + 2, unis + 3, rs.getInt("revistas"), "datos");
                    obj.val(contCol + 3, unis + 3, rs.getInt("bibliotecas"), "datos");
                    totalcol[0]+= rs.getInt("libros");
                    totalcol[1]+= rs.getInt("titulos");
                    totalcol[2]+= rs.getInt("revistas");
                    totalcol[3]+= rs.getInt("bibliotecas");
                    
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 5; tco++) {
                         obj.val(contCol+tco, unistmp + 3, totalcol[tco], "total");
                    }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Eficiencia Indicador 16 " + ex.getMessage());
        }
    }

    private void indicador17() {
        int totalcol[]=new int [2];
        int unistmp=0;
        c = 5;
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
            obj.combinarCeldas("17. RELACIÓN ALUMNO / DOCENTE", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol, 2, "Matrícula total al inicio del ciclo escolar", "h2");
            obj.val(contCol + 1, 2, "Profesores de tiempo completo al iniciar el ciclo escolar", "h2");
            obj.val(contCol + 2, 2, "TOTAL (matricula / profesores)", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                obj.val(contCol, unis + 3, univs.getMatriculaTotal(unis), "datos");
                obj.val(contCol + 1, unis + 3, univs.getPtc(unis), "datos");
                if(univs.getPtc(unis) > 0){
                    obj.val(contCol + 2, unis + 3, univs.getMatriculaTotal(unis)/univs.getPtc(unis), "datos");
                }else{
                    obj.val(contCol + 2, unis + 3, 0 , "datos");
                }
                totalcol[0]+=univs.getMatriculaTotal(unis);
                totalcol[1]+=univs.getPtc(unis);
                unistmp=unis+1;
            }
            //IMPRIME LOS TOTALES DE COLUMNA
                    int tco;
                    for ( tco = 0; tco < 2; tco++) {
                         obj.val(contCol+tco, unistmp + 3, totalcol[tco], "total");
                    }
                    obj.val(contCol+tco, unistmp + 3, totalcol[0]/totalcol[1], "total");
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Eficiencia Indicador 17 " + ex.getMessage());
        }
    }
}