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
public class ExcelEficiencia extends SabanaCategorias {

    ListaCertificaciones certificaciones;
    ListaEdificios edificios;
    int contCol = 0;
    int c = 0;
    int f = 5;

    public ExcelEficiencia() {
        super();
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Sabana Eficiencia Eliminada :)");
    }

    public ExcelEficiencia(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.certificaciones = new ListaCertificaciones();
        this.edificios = new ListaEdificios();
        this.f += univs.size();
    }

    public HojaCalculo hacerEficiencia() {
        indicador12();
        indicador13();
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
                         obj.val(contCol + 2, unis + 3, redondear(rs.getDouble("autorizado") / univs.getMatriculaTotal(unis)), "datos2");
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
                obj.val(contCol + 2, unistmp + 3, redondear(totalcol0/totalcol1), "total2");
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Excel, Eficiencia Indicador 12 " + ex.getMessage());
        }
    }

    private void indicador13() {
        int unistmp=0;
        double sumporc = 0;//ACUMULADOR PARA PORCENTAJE >0
        int counrpoc = 0; //CONTADOR PARA PORCENTAJE >0
        
        c = 7;
        obj.crearHoja("Ind. 13 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 6250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("13. CAPACIDAD INSTALADA EN LAS UNIVERSIDADES", contCol, contCol , 1, 1, "h1");
//            obj.val(contCol, 2, "Número de unidades, laboratorios y talleres", "h2");
//            obj.val(contCol + 1, 2, "Total de espacios docentes", "h2");
//            obj.val(contCol + 2, 2, "Total del espacio académico ocupado por otras áreas de trabajo", "h2");
//            obj.val(contCol + 3, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            obj.val(contCol, 2, "%", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT SUM(no_laboratorios) as 'cantidadEdificios', SUM(e.capacidad*i.no_laboratorios) as 'totalEspacios', "
                        .concat(" SUM(i.espacio_academico) as 'espacioAcademico' FROM eficienciain13 i INNER JOIN edificios ")
                        .concat(" e ON i.id_edifi=e.id_edificio")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
//                    obj.val(contCol, unis + 3, rs.getInt("cantidadEdificios"), "datos");
//                    obj.val(contCol + 1, unis + 3, rs.getInt("totalEspacios"), "datos");
//                    obj.val(contCol + 2, unis + 3, rs.getInt("espacioAcademico"), "datos");
//                    obj.val(contCol + 3, unis + 3, univs.getMatriculaTotal(unis), "datos");
                    if(univs.getMatriculaTotal(unis) > 0 && rs.getInt("totalEspacios") > 0){
                        obj.val(contCol, unis + 3, redondear(((double)univs.getMatriculaTotal(unis)/rs.getInt("totalEspacios"))*100), "datos2");
                        sumporc+=redondear(((double)univs.getMatriculaTotal(unis)/rs.getInt("totalEspacios"))*100);
                        counrpoc++;
                    }else{
                        obj.val(contCol, unis + 3, 0, "datos");
                    }
//                    totalcols[0]+=rs.getDouble("cantidadEdificios");
//                    totalcols[1]+=rs.getDouble("totalEspacios");
//                    totalcols[2]+=rs.getDouble("espacioAcademico");
//                    totalcols[3]+=(double)univs.getMatriculaTotal(unis);
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol , unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            //IMPRIME LOS TOTALES DE COLUMNA
                 obj.val(contCol, unistmp + 3, redondear(sumporc/counrpoc), "total2");
//            int tco;
//            for (tco = 0; tco < 4; tco++) {
//                 obj.val(contCol+tco, unistmp + 3, totalcols[tco], "total");
//            }
//            if(totalcols[3]>0){
//                obj.val(contCol+tco, unistmp + 3, redondear((totalcols[3]/totalcols[1])*100), "total2");
//            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Excel, Eficiencia Indicador 13 " + ex.getMessage());
        }
    }


    private void indicador14() {
        
        double sumporc []= new double[10];//ACUMULADOR PARA PORCENTAJE >0
        int counrpoc []= new int [10]; //CONTADOR PARA PORCENTAJE >0
        int unistmp=0;
        c = 19;
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
                double tot1=0, totint=0;
                if (rs.next()) { //si hay una respuesta de la consulta
                    tot1=(rs.getInt("docente_ptc") + rs.getInt("docente_asignatura")
                            +rs.getInt("alumnos") + rs.getInt("personal_admin") + rs.getInt("mandos"));
                    if(rs.getDouble("docente_ptc")>0){
                        obj.val(contCol, unis + 4, redondear(rs.getDouble("docente_ptc")/tot1*100), "datos2");
                        sumporc[0]+=redondear(rs.getDouble("docente_ptc")/tot1*100);
                        counrpoc[0]++;
                    }else{
                        obj.val(contCol, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("docente_asignatura")>0){
                        obj.val(contCol + 1, unis + 4, redondear(rs.getDouble("docente_asignatura")/tot1*100), "datos2");
                        sumporc[1]+=redondear(rs.getDouble("docente_asignatura")/tot1*100);
                        counrpoc[1]++;
                    }else{
                        obj.val(contCol + 1, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("alumnos")>0){
                        obj.val(contCol + 2, unis + 4, redondear(rs.getDouble("alumnos")/tot1*100), "datos2");
                        sumporc[2]+=redondear(rs.getDouble("alumnos")/tot1*100);
                        counrpoc[2]++;
                    }else{
                        obj.val(contCol + 2, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("personal_admin")>0){
                        obj.val(contCol + 3, unis + 4, redondear(rs.getDouble("personal_admin")/tot1*100), "datos2");
                        sumporc[3]+=redondear(rs.getDouble("personal_admin")/tot1*100);
                        counrpoc[3]++;
                    }else{
                        obj.val(contCol + 3, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("mandos")>0){
                        obj.val(contCol + 4, unis + 4, redondear(rs.getDouble("mandos")/tot1*100), "datos2");
                        sumporc[4]+=redondear(rs.getDouble("mandos")/tot1*100);
                        counrpoc[4]++;
                    }else{
                        obj.val(contCol + 4, unis + 4, 0, "datos2");
                    }    

                    obj.val(contCol + 5, unis + 4, tot1, "datos");
                    
                    totint=(rs.getDouble("docentes_ptc_int") + rs.getDouble("docente_asignatura_int")
                            +rs.getDouble("alumnos_int") + rs.getDouble("personal_admin_int") + rs.getDouble("mandos_int"));
                    if(rs.getDouble("docentes_ptc_int")>0){
                        obj.val(contCol + 6, unis + 4, redondear(rs.getDouble("docentes_ptc_int")/totint*100), "datos2");
                        sumporc[5]+=redondear(rs.getDouble("docentes_ptc_int")/totint*100);
                        counrpoc[5]++;
                    }else{
                        obj.val(contCol + 6, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("docente_asignatura_int")>0){
                        obj.val(contCol + 7, unis + 4, redondear(rs.getDouble("docente_asignatura_int")/totint*100), "datos2");
                        sumporc[6]+=redondear(rs.getDouble("docente_asignatura_int")/totint*100);
                        counrpoc[6]++;
                    }else{
                        obj.val(contCol + 7, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("alumnos_int")>0){
                        obj.val(contCol + 8, unis + 4, redondear(rs.getDouble("alumnos_int")/totint*100), "datos2");
                        sumporc[7]+=redondear(rs.getDouble("alumnos_int")/totint*100);
                        counrpoc[7]++;
                    }else{
                        obj.val(contCol + 8, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("personal_admin_int")>0){
                        obj.val(contCol + 9, unis + 4, redondear(rs.getDouble("personal_admin_int")/totint*100), "datos2");
                        sumporc[8]+=redondear(rs.getDouble("personal_admin_int")/totint*100);
                        counrpoc[8]++;
                    }else{
                        obj.val(contCol + 9, unis + 4, 0, "datos2");
                    }    
                    if(rs.getDouble("mandos_int")>0){
                        obj.val(contCol + 10, unis + 4, redondear(rs.getDouble("mandos_int")/totint*100), "datos2");
                        sumporc[9]+=redondear(rs.getDouble("mandos_int")/totint*100);
                        counrpoc[9]++;
                    }else{
                        obj.val(contCol + 10, unis + 4, 0, "datos2");
                    }    
                    obj.val(contCol + 11, unis + 4, totint , "datos");
                    
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 6, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 7, contCol + 11, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
                //IMPRIME LOS TOTALES DE COLUMNA
                    obj.val(contCol, unistmp + 4, redondear(sumporc[0]/counrpoc[0]), "total2");
                    obj.val(contCol + 1, unistmp + 4, redondear(sumporc[1]/counrpoc[1]), "total2");
                    obj.val(contCol + 2, unistmp + 4, redondear(sumporc[2]/counrpoc[2]), "total2");
                    obj.val(contCol + 3, unistmp + 4, redondear(sumporc[3]/counrpoc[3]), "total2");
                    obj.val(contCol + 4, unistmp + 4, redondear(sumporc[4]/counrpoc[4]), "total2");
                    
                    obj.val(contCol + 6, unistmp + 4, redondear(sumporc[5]/counrpoc[5]), "total2");
                    obj.val(contCol + 7, unistmp + 4, redondear(sumporc[6]/counrpoc[6]), "total2");
                    obj.val(contCol + 8, unistmp + 4, redondear(sumporc[7]/counrpoc[7]), "total2");
                    obj.val(contCol + 9, unistmp + 4, redondear(sumporc[8]/counrpoc[8]), "total2");
                    obj.val(contCol + 10, unistmp + 4, redondear(sumporc[9]/counrpoc[9]), "total2");
                   
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Excel, Eficiencia Indicador 14 " + ex.getMessage());
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
            System.err.println("ERROR BD: Excel, Eficiencia Indicador 15 " + ex.getMessage());
        } finally {
            certificaciones = null;
        }
    }

    private void indicador16() {
        int unistmp=0;
        
        double sumporc []= new double[2];//ACUMULADOR PARA PORCENTAJE >0
        int countpoc []= new int [2]; //CONTADOR PARA PORCENTAJE >0
        
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
            obj.combinarCeldas("16. DISTRIBUCIÓN DE LIBROS Y TÍTULOS POR ALUMNOS", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol , 2, "DLPA", "h2");
            obj.val(contCol + 1, 2, "DTPA", "h2");
            obj.val(contCol + 2, 2, "Matrícula inicial atendida en el ciclo escolar", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT libros, titulos, revistas, bibliotecas FROM eficienciain16 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                double totmat= (double) univs.getMatriculaTotal(unis);
                obj.val(contCol + 2, unis + 3, totmat, "datos");
                if (rs.next()) { //si hay una respuesta de la consulta
                    if(rs.getDouble("libros")>0){
                        obj.val(contCol , unis + 3, redondear(rs.getDouble("libros")/totmat), "datos2");
                        sumporc[0]+=redondear(rs.getDouble("libros")/totmat);
                        countpoc[0]++;
                    }else{
                        obj.val(contCol , unis + 3, 0, "datos2");
                    }
                    if(rs.getDouble("titulos")>0){
                        obj.val(contCol + 1, unis + 3, redondear(rs.getDouble("titulos")/totmat), "datos2");
                        sumporc[1]+=redondear(rs.getDouble("titulos")/totmat);
                        countpoc[1]++;
                    }else{
                        obj.val(contCol + 1, unis + 3, 0, "datos2");
                    }
                    
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            //IMPRIME LOS PROMEDIOS DE COLUMNA > 0
                obj.val(contCol , unistmp + 3, redondear(sumporc[0]/countpoc[0]), "total2");
                obj.val(contCol + 1, unistmp + 3, redondear(sumporc[1]/countpoc[1]), "total2");
            
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Excel, Eficiencia Indicador 16 " + ex.getMessage());
        }
    }

    private void indicador17() {
        int unistmp=0;
        double sumporc = 0;//ACUMULADOR PARA PORCENTAJE >0
        int countpoc = 0; //CONTADOR PARA PORCENTAJE >0
        c = 5;
        obj.crearHoja("Ind. 17 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 8250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("17. RELACIÓN ALUMNO / DOCENTE", contCol, contCol , 1, 1, "h1");
            obj.val(contCol, 2, "RAD (matricula / profesores)", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                if(univs.getPtc(unis) > 0){
                    obj.val(contCol , unis + 3, redondear((double)univs.getMatriculaTotal(unis)/(double)univs.getPtc(unis)), "datos2");
                    sumporc+=redondear((double)univs.getMatriculaTotal(unis)/(double)univs.getPtc(unis));
                    countpoc++;
                }else{
                    obj.val(contCol, unis + 3, 0 , "datos");
                }
                unistmp=unis+1;
            }
                    obj.val(contCol, unistmp + 3, redondear(sumporc/countpoc), "total2");
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel, Eficiencia Indicador 17 " + ex.getMessage());
        }
    }
}