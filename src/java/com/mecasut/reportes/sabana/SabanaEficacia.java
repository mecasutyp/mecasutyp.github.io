/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 *
 *
 * @author Cuauhtemoc Medina Muñoz
 * Actualización 2016: Salvador Zamora Arias
 */
public class SabanaEficacia extends SabanaCategorias {

    ListaCausas causas;
    ListaModalidades modalidades;
    int contCol = 0;
    int c = 0;
    int f = 5;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Sabana Eficacia Eliminada :)");
    }

    public SabanaEficacia(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.obj = obj;
        this.f += univs.size();
        this.causas = new ListaCausas();
        this.modalidades = new ListaModalidades();
    }

    public HojaCalculo hacerEficacia() {
        matriculaPE();
        programasEducativosPorNivel();
        matriculaTotal();
        nuevoIngreso();
        indicador1();
        indicador2();
        indicador3();
        indicador4();
        indicador4b();
        indicador5();
        indicador5b();
        indicador6();
        indicador7();
        indicador7b();
        indicador8();
        indicador9();
        indicador10();
        indicador10b();
        indicador11();

        niveles = null;
        con.Desconectar();
        return obj;
    }

    private void matriculaPE() {
        ListaProgramasEducativos pes = new ListaProgramasEducativos();
        //System.out.println("\n\n\n\n\n El tamaño del PES es : " + pes.size());
        c = 3 + pes.size();
        obj.crearHoja("Programas Educativos", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 5000);
        }
        columnasPrincipales(2);
        obj.setAlturaFilas(2, 57);
        contCol = 2;
        try {
            int unistmp=0;   
            int totalcolumna=0;
            int [][] totalxnivel=new int[univs.size()][pes.size()];
            for (int pe = 0; pe < pes.size(); pe++) {
                totalcolumna=0;
                obj.val(contCol, 2, pes.getNombre(pe), "h1");
                obj.combinarCeldas(pes.getNombre(pe), contCol, contCol, 1, 2, "h1");
                for (int unis = 0; unis < univs.size(); unis++) {
                 
                    consulta = "SELECT id_universidad, ingreso_hombres+ingreso_mujeres+reingreso_hombres+reingreso_mujeres as 'matricula' "
                            .concat(" from equidadin34 ind")
                            .concat(" WHERE id_pe=").concat(pes.getId(pe))
                            .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo);
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 3, rs.getInt("matricula"), "datos");
                        totalcolumna=totalcolumna+rs.getInt("matricula");
                        totalxnivel[unis][pe]+=rs.getInt("matricula");
                    } else {
                        obj.val(contCol, unis + 3, "SIN DATOS", "error");
                    }
                      unistmp=unis+1;
                }
               
                obj.val(contCol, unistmp + 3, totalcolumna, "total");
                contCol += 1;
            }
        } catch (Exception x) {
            System.err.println("ERROR BD: Sabana general, Eficacia, matriculaPE() " + x.getMessage());
        }
    }

    private void programasEducativosPorNivel() {/*PE X NIVEL X UT*/
        c = 2 + niveles.size() + 1;
        obj.crearHoja("PE's por Nivel ", f, c);
        obj.setAnchoColumna(1, 17500);
        columnasPrincipales(2);
        contCol = 2;
       
        try {
            obj.combinarCeldas("PROGRMAS EDUCATIVOS ", contCol, contCol + niveles.size() - 1, 1, 1, "h1");
            int totalcolum=0;
            int []totalregistro=new int[univs.size()];
            for (int i = 0; i < niveles.size(); i++) { //MUEVE LOS NIVELES
                totalcolum=0;
                //ENCABEZADOS
                obj.val(contCol, 2, niveles.getAbreviatura(i), "h2");
                int unis;
                for (unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT COUNT(*) FROM pe_universidad u "
                            .concat(" INNER JOIN programa_educativo pe on u.id_pe=pe.id_pe")
                            .concat(" WHERE u.id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND u.id_periodo=").concat(periodo)
                            .concat(" AND pe.id_nivel=").concat(String.valueOf(niveles.getId(i)));
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 3, rs.getInt(1), "datos");
                        totalcolum=totalcolum+rs.getInt(1);
                        totalregistro[unis]=totalregistro[unis]+rs.getInt(1);
                    }
                }
                obj.val(contCol, unis + 3, totalcolum, "total");
                contCol += 1;
                
            }
             obj.val(contCol, 2, "Total", "h2");
             for (int i = 0; i < univs.size(); i++) {
                obj.val(contCol, i+3, totalregistro[i], "total");
            }
            
           
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Eficacia, matriculaTotal() " + ex.getMessage());
        }
    }

    private void matriculaTotal() {
        c = 3 + niveles.size();
        obj.crearHoja("Matrícula Total ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;

        try {
            obj.combinarCeldas("MATRÍCULA TOTAL ", contCol, contCol + niveles.size() - 1, 1, 1, "h1");
              int totalcolumna=0, unistmp=0;
              int []totalregistro= new int [univs.size()];
            for (int i = 0; i < niveles.size(); i++) { //MUEVE LOS NIVELES
                totalcolumna=0;
                //ENCABEZADOS
                obj.val(contCol, 2, niveles.getAbreviatura(i), "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT matricula FROM mat_total_universidad "
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(i)));
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 3, rs.getInt("matricula"), "datos");
                        totalcolumna=totalcolumna+rs.getInt("matricula");
                        totalregistro[unis]=totalregistro[unis]+rs.getInt("matricula");
                    }
                    unistmp=unis+1;
                }
                obj.val(contCol, unistmp + 3, totalcolumna, "total");
                contCol += 1;
            }
            obj.val(contCol, 2, "Total", "h2");
             for (int i = 0; i < univs.size(); i++) {
                obj.val(contCol, i+3, totalregistro[i], "total");
            }
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Eficacia, matriculaTotal() " + ex.getMessage());
        }
    }

    private void nuevoIngreso() {
        c = 3;
        obj.crearHoja("Nuevo Ingreso", f, c);
        obj.setAnchoColumna(1, 17500);
        obj.setAnchoColumna(1, 10000);
        columnasPrincipales(2);
        try {
            obj.combinarCeldas("NUEVO INGRESO", 2, 2, 1, 2, "h1");
             int totalcolumna=0, unistmp=0;
            for (int unis = 0; unis < univs.size(); unis++) { //MUEVE LOS NIVELES
                //ENCABEZADOS
                consulta = "SELECT nuevo_ingreso FROM datos_universidad "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(2, unis + 3, rs.getInt("nuevo_ingreso"), "datos");
                    totalcolumna=totalcolumna+rs.getInt("nuevo_ingreso");
                }
                obj.val(2, unis + 4,totalcolumna , "total");
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Eficacia, nuevoIngreso() " + ex.getMessage());
        }
    }

    private void indicador1() {
        c = 18;
        obj.crearHoja("Ind. 01 ", f, c);
        obj.setAlturaFilas(1, 50);
        obj.setAlturaFilas(2, 120);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        obj.combinarCeldas("1. ALUMNOS DE NUEVO INGRESO CON EXANI II", 2, 13, 1, 1, "h1");
        obj.combinarCeldas("Egresados de bachillerato que presentaron el EXANI - II en la UT", 2, 3, 2, 2, "h1");
        obj.combinarCeldas("Alumnos de nuevo ingreso a la UT", 4, 5, 2, 2, "h1");
        obj.combinarCeldas("Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 1,101 a 1,300 puntos de calificación", 6, 7, 2, 2, "h1");
        obj.combinarCeldas("Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 901 a 1,100 puntos de calificación", 8, 9, 2, 2, "h1");
        obj.combinarCeldas("Alumnos de nuevo ingreso que presentaron el EXANI - II y lograron 700 a 900 puntos de calificación", 10, 11, 2, 2, "h1");
        obj.combinarCeldas("Alumnos de nuevo ingreso inscritos a la universidad que presentaron el EXANI II en el ciclo escolar", 12, 12, 2, 2, "h1");
        obj.combinarCeldas("Alumnos de nuevo ingreso inscritos a la universidad que no presentaron EXANI - II", 13, 13, 2, 2, "h1");

        int totalcol2=0,totalcol3=0,totalcol4=0,totalcol5=0,totalcol6=0,totalcol7=0,totalcol8=0, itmp=0; 
        for (int i = 0; i <= univs.size() - 1; i++) {
            obj.val(0, i + 3, String.valueOf((i + 1)), "resultado");//CONSECUTIVO
            obj.val(1, i + 3, univs.getNombre(i), "resultado");//NOMBRE DE LA UNIVERSIDAD
            try {
                consulta = "SELECT aplica, c1, c2, c3, (c1+c2+c3) as ingreso, egresados_bachillerato, nuevo_ingreso "
                        .concat(" FROM eficaciain1 WHERE id_universidad=").concat(String.valueOf(univs.getId(i)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    if (rs.getBoolean("aplica")) {
                        /*ASIGNAR VALORES*/
                        
                        obj.val(2, i + 3, rs.getInt("egresados_bachillerato"), "datos");
                        obj.val(3, i + 3,Math.round((double) ((rs.getDouble("ingreso")/rs.getDouble("egresados_bachillerato"))*100))+" %", "datos");
                        obj.val(4, i + 3, rs.getInt("nuevo_ingreso"), "datos");
                        obj.val(5, i + 3,Math.round((double) ((rs.getDouble("ingreso")/rs.getDouble("nuevo_ingreso"))*100))+" %", "datos");
                        obj.val(6, i + 3, rs.getInt("c1"), "datos");
                        obj.val(7, i + 3,Math.round((double) ((rs.getDouble("c1")/rs.getDouble("ingreso"))*100))+" %", "datos");
                        obj.val(8, i + 3, rs.getInt("c2"), "datos");
                        obj.val(9, i + 3,Math.round((double) ((rs.getDouble("c2")/rs.getDouble("ingreso"))*100))+" %", "datos");
                        obj.val(10, i + 3, rs.getInt("c3"), "datos");
                        obj.val(11, i + 3,Math.round((double) ((rs.getDouble("c3")/rs.getDouble("ingreso"))*100))+" %", "datos");
                        obj.val(12, i + 3, rs.getInt("ingreso"), "datos");
                        obj.val(13, i + 3, rs.getInt("nuevo_ingreso") - rs.getInt("ingreso"), "datos");
                        
                        totalcol2+=rs.getInt("egresados_bachillerato");
                        totalcol3+=rs.getInt("nuevo_ingreso");
                        totalcol4+=rs.getInt("c1");
                        totalcol5+=rs.getInt("c2");
                        totalcol6+=rs.getInt("c3");
                        totalcol7+=rs.getInt("ingreso");
                        totalcol8+=(rs.getInt("nuevo_ingreso") - rs.getInt("ingreso"));
                    } else {/*NO APLICA*/
                        obj.combinarCeldas("NO APLICA", 2, 13, i + 3, i + 3, "error");
                    }
                } else { // no devolvió nada la consulta, por lo tanto la universidad no ha llenado el indicador
                    obj.combinarCeldas("NO CONCLUIDO", 2, 13, i + 3, i + 3, "error");
                }
            } catch (SQLException ex) {
                System.err.println("ERROR BD: Sabana general, Eficacia, Indicador 1 " + ex.getMessage());
            }
            itmp=i+1;
        }
        obj.val(2, itmp + 3,totalcol2 , "total");
        obj.val(4, itmp + 3,totalcol3 , "total");
        obj.val(6, itmp + 3,totalcol4 , "total");
        obj.val(8, itmp + 3,totalcol5 , "total");
        obj.val(10, itmp + 3,totalcol6 , "total");
        obj.val(12, itmp + 3,totalcol7 , "total");
        obj.val(13, itmp + 3,totalcol8 , "total");
    }

    private void indicador2() {
        c = 2 + 5 * (niveles.size() + 1);
        obj.crearHoja("Ind. 02 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        int noSep = 0;
        int noEne = 0;
        int noMay = 0;
        float sumSep = 0;
        float sumEne = 0;
        float sumMay = 0;
        boolean bandera = false;
         double totalcol1=0,totalcol2=0,totalcol3=0;
          int xtemp=0;
        try {
            //ENCABEZADOS
//            obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO NIVEL TECNICO SUPERIOR UNIVERSITARIO Versión 2004", contCol, contCol + 3, 1, 1, "h1");
//            obj.val(contCol, 2, "sep-dic", "h2");
//            obj.val(contCol + 1, 2, "ene-abr", "h2");
//            obj.val(contCol + 2, 2, "may-ago", "h2");
//            obj.val(contCol + 3, 2, "PROMEDIO", "h2");
//            for (int x = 0; x < univs.size(); x++) {
//                xtemp=0;
//                  totalcol1=0;totalcol2=0;totalcol3=0;
//                consulta = "SELECT id_pe FROM pe_universidad WHERE id_universidad=".concat(String.valueOf(univs.getId(x)))
//                        .concat(" AND id_periodo=").concat(periodo)
//                        .concat(" AND id_pe IN(select id_pe from programa_educativo where id_nivel=1 AND version=2004)");
//                rs = con.Consultar(consulta);
//                if (rs.next()) {
//                    consulta = "SELECT sep, ene, may"
//                            .concat(" FROM eficaciain2 WHERE id_universidad="
//                            + "").concat(String.valueOf(univs.getId(x)))
//                            .concat(" AND id_periodo=").concat(periodo)
//                            .concat(" AND id_pe IN (select id_pe from programa_educativo where version=2004)");
//                    rs = con.Consultar(consulta);
//                    sumSep = 0;
//                    noSep = 0;
//                    sumEne = 0;
//                    noEne = 0;
//                    sumMay = 0;
//                    noMay = 0;
//                    bandera = false;
//                    while (rs.next()) {
//                        bandera = true;
//                        if (rs.getFloat("sep") > 0) {
//                            noSep++;
//                            sumSep += rs.getFloat("sep");
//                        }
//                        if (rs.getFloat("ene") > 0) {
//                            noEne++;
//                            sumEne += rs.getFloat("ene");
//                        }
//                        if (rs.getFloat("may") > 0) {
//                            noMay++;
//                            sumMay += rs.getFloat("may");
//                        }
//                    }
//                    if (bandera) {
//                        if (noSep > 0) {
//                            obj.val(contCol, x + 3, sumSep / noSep, "datos2");
//                        } else {
//                            obj.val(contCol, x + 3, 0, "datos2");
//                        }
//                        if (noEne > 0) {
//                            obj.val(contCol + 1, x + 3, sumEne / noEne, "datos2");
//                        } else {
//                            obj.val(contCol + 1, x + 3, 0, "datos2");
//                        }
//                        if (noMay > 0) {
//                            obj.val(contCol + 2, x + 3, sumMay / noMay, "datos2");
//                        } else {
//                            obj.val(contCol + 2, x + 3, 0, "datos2");
//                        }
//                     obj.val(contCol + 3, x + 3, ((sumSep / noSep) + (sumEne / noEne) + (sumMay / noMay))/3, "datos2");
//                    } else {
//                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, x + 3, x + 3, "error");
//                    }
//                } else { // NO TIENE EL NIVEL
//                    obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 3, x + 3, x + 3, "error");
//                }
//                xtemp=x+1;
//            }
//                totalcol1=Math.round(totalcol1*100)/100;
//               totalcol2=Math.round(totalcol2*100)/100;
//               totalcol3=Math.round(totalcol3*100)/100;
//               obj.val(contCol, xtemp + 3, totalcol1, "total");
//               obj.val(contCol + 1, xtemp + 3, totalcol2, "total");
//               obj.val(contCol + 2, xtemp + 3, totalcol3, "total");
//               obj.val(contCol + 3, xtemp + 3,((totalcol1 + totalcol2 + totalcol3) / 3) , "total2");
//            contCol += 4;
            for (int i = 0; i < niveles.size(); i++) {
                //ENCABEZADOS
                obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO DEL NIVEL " + niveles.getAbreviatura(i), contCol, contCol + 3, 1, 1, "h1");
                obj.val(contCol, 2, "sep-dic", "h2");
                obj.val(contCol + 1, 2, "ene-abr", "h2");
                obj.val(contCol + 2, 2, "may-ago", "h2");
                obj.val(contCol + 3, 2, "PROMEDIO", "h2");
                 xtemp=0;
                 totalcol1=0;totalcol2=0;totalcol3=0;
                for (int x = 0; x < univs.size(); x++) {
                    obj.val(contCol + 3, x + 3, 0, "datos2");
                    if (contieneNivel(String.valueOf(niveles.getId(i)), String.valueOf(univs.getId(x)))) {
                        consulta = "SELECT sep, ene, may"
                                .concat(" FROM eficaciain2 WHERE id_universidad=").concat(String.valueOf(univs.getId(x)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_pe IN (SELECT id_pe FROM programa_educativo WHERE id_nivel=")
                                .concat(String.valueOf(niveles.getId(i))).concat(")");
                        rs = con.Consultar(consulta);
                        sumSep = 0;
                        noSep = 0;
                        sumEne = 0;
                        noEne = 0;
                        sumMay = 0;
                        noMay = 0;
                        bandera = false;
                        while (rs.next()) {
                            bandera = true;
                            if (rs.getFloat("sep") > 0) {
                                noSep++;
                                sumSep += rs.getFloat("sep");
                            }
                            if (rs.getFloat("ene") > 0) {
                                noEne++;
                                sumEne += rs.getFloat("ene");
                            }
                            if (rs.getFloat("may") > 0) {
                                noMay++;
                                sumMay += rs.getFloat("may");
                            }
                        }
                        if (bandera) {
                            if (noSep > 0) {
                                obj.val(contCol, x + 3, sumSep / noSep, "datos2");
                                totalcol1+=sumSep / noSep;
                            } else {
                                obj.val(contCol, x + 3, 0, "datos2");
                            }
                            if (noEne > 0) {
                                obj.val(contCol + 1, x + 3, sumEne / noEne, "datos2");
                                totalcol2+=sumEne / noEne;
                            } else {
                                obj.val(contCol + 1, x + 3, 0, "datos2");
                            }
                            if (noMay > 0) {
                                obj.val(contCol + 2, x + 3, sumMay / noMay, "datos2");
                                totalcol3+=sumMay / noMay;
                            } else {
                                obj.val(contCol + 2, x + 3, 0, "datos2");
                            }
                            
                            obj.val(contCol + 3, x + 3, ((sumSep / noSep) + (sumEne / noEne) + (sumMay / noMay))/3, "datos2");
                           
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, x + 3, x + 3, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, x + 3, x + 3, "error");
                         
                    }
                    
                    xtemp=x+1;
                }
                
                totalcol1=Math.round(totalcol1*100)/100;
                totalcol2=Math.round(totalcol2*100)/100;
                totalcol3=Math.round(totalcol3*100)/100;
                obj.val(contCol, xtemp + 3, totalcol1, "total");
                obj.val(contCol + 1, xtemp + 3, totalcol2, "total");
                obj.val(contCol + 2, xtemp + 3, totalcol3, "total");
                obj.val(contCol + 3, xtemp + 3,((totalcol1 + totalcol2 + totalcol3) / 3) , "total2");
                contCol += 4;
            }
            
            ////////////////////////////////
            contCol++;
            float [] promedios =new float[univs.size()];
            obj.combinarCeldas("TOTAL PROMEDIO", contCol, contCol + niveles.size(), 1, 1, "h1");
            for (int i = 0; i < niveles.size(); i++) {
                //ENCABEZADOS
                
                obj.val(contCol, 2, niveles.getAbreviatura(i), "h2");
                 xtemp=0;
                 totalcol1=0;totalcol2=0;totalcol3=0;
              
                for (int x = 0; x < univs.size(); x++) {
                    obj.val(contCol, x + 3, 0, "datos2");
                    if (contieneNivel(String.valueOf(niveles.getId(i)), String.valueOf(univs.getId(x)))) {
                        consulta = "SELECT sep, ene, may"
                                .concat(" FROM eficaciain2 WHERE id_universidad=").concat(String.valueOf(univs.getId(x)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_pe IN (SELECT id_pe FROM programa_educativo WHERE id_nivel=")
                                .concat(String.valueOf(niveles.getId(i))).concat(")");
                        rs = con.Consultar(consulta);
                        sumSep = 0;
                        noSep = 0;
                        sumEne = 0;
                        noEne = 0;
                        sumMay = 0;
                        noMay = 0;
                        bandera = false;
                        while (rs.next()) {
                            bandera = true;
                            if (rs.getFloat("sep") > 0) {
                                noSep++;
                                sumSep += rs.getFloat("sep");
                            }
                            if (rs.getFloat("ene") > 0) {
                                noEne++;
                                sumEne += rs.getFloat("ene");
                            }
                            if (rs.getFloat("may") > 0) {
                                noMay++;
                                sumMay += rs.getFloat("may");
                            }
                        }
                        if (bandera) {
                           
                                obj.val(contCol, x + 3, ((sumSep / noSep) + (sumEne / noEne) + (sumMay / noMay))/3, "datos2");
                                promedios[x]=promedios[x]+(((sumSep / noSep) + (sumEne / noEne) + (sumMay / noMay))/3);
                                totalcol1+=sumSep / noSep;
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol, x + 3, x + 3, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol, x + 3, x + 3, "error");
                         
                    }
                    
                    xtemp=x+1;
                }
                
                obj.val(contCol, xtemp + 3, totalcol1, "total2");
                contCol += 1;
            }
//            obj.val(contCol, 2, "TOTAL UT", "h2");
//            for (int i = 0; i < univs.size(); i++) {
//                obj.val(contCol, i + 3, promedios[i]/3, "datos2");
//            }
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Eficacia, Indicador 2. << " + ex.getMessage());
        }
    }

    private void indicador3() {
        c = 2 + 15 * niveles.size();
        obj.crearHoja("Ind. 03 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;

        try {
            int totalcol0,totalcol1,totalcol2,totalcol3,totalcol4,totalcol5, unistmp=0;
            double []totalcolpor= new double[4];
           int contCol2=contCol;
           int [] nivelesxuni=new int[univs.size()];
           double[][] promxniveldeuni = new double[univs.size()][niveles.size()];
           int [][] matrxuni = new int[univs.size()][6];
            for (int i = 0; i < niveles.size(); i++) { //MUEVE LOS NIVELES
                //ENCABEZADOS
                totalcol0=0;totalcol1=0;totalcol2=0;totalcol3=0;totalcol4=0;totalcol5=0;
                obj.combinarCeldas("3. REPROBACIÓN CUATRIMESTRAL ".concat(niveles.getAbreviatura(i)), contCol2, contCol2 +9, 1, 1, "h1");
                obj.val(contCol, 2, "Número de alumnos reprobados del cuatrimestre Sep-Dic ", "h2");
                obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 2, 2, "%", "h2");
                obj.val(contCol + 3, 2, "Número de alumnos reprobados del cuatrimestre Ene-Abr ", "h2");
                obj.val(contCol + 4, 2, "Matrícula inicial atendida cuatrimestre Ene-Abr", "h2");
                obj.val(contCol + 5, 2, "%", "h2");
                obj.val(contCol + 6, 2, "Número de alumnos reprobados del cuatrimestre May-Ago ", "h2");
                obj.val(contCol + 7, 2, "Matrícula inicial atendida cuatrimestre May-Ago", "h2");
                obj.val(contCol + 8, 2, "%", "h2");
                obj.val(contCol + 9, 2, "PROMEDIO DE REPROBACIÓN "+ niveles.getAbreviatura(i), "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(i)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
                        consulta = "SELECT rep_sep, mat_sep, rep_ene, mat_ene, rep_may, mat_may FROM eficaciain3 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(i)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            ///ACUMULA MATRIUCLAS POR UNIVERSIDAD
                            matrxuni[unis][0]+=rs.getInt("rep_sep");
                            matrxuni[unis][1]+=rs.getInt("mat_sep");
                            matrxuni[unis][2]+=rs.getInt("rep_ene");
                            matrxuni[unis][3]+=rs.getInt("mat_ene");
                            matrxuni[unis][4]+=rs.getInt("rep_may");
                            matrxuni[unis][5]+=rs.getInt("mat_may");
                            obj.val(contCol, unis + 3, rs.getInt("rep_sep"), "datos");
                            obj.val(contCol + 1, unis + 3, rs.getInt("mat_sep"), "datos");
                            obj.val(contCol + 2, unis + 3,(((rs.getDouble("rep_sep")/rs.getDouble("mat_sep"))*100)*100)/100, "datos2");
                            obj.val(contCol + 3, unis + 3, rs.getInt("rep_ene"), "datos");
                            obj.val(contCol + 4, unis + 3, rs.getInt("mat_ene"), "datos");
                            obj.val(contCol + 5, unis + 3,(rs.getDouble("rep_ene")/rs.getDouble("mat_ene"))*100 , "datos2");
                            obj.val(contCol + 6, unis + 3, rs.getInt("rep_may"), "datos");
                            obj.val(contCol + 7, unis + 3, rs.getInt("mat_may"), "datos");
                            obj.val(contCol + 8, unis + 3, (rs.getDouble("rep_may")/rs.getDouble("mat_may"))*100, "datos2");
                            promxniveldeuni [unis][i]=(((((rs.getDouble("rep_sep")/rs.getDouble("mat_sep"))*100) + ((rs.getDouble("rep_ene")/rs.getDouble("mat_ene"))*100 ) + ((rs.getDouble("rep_may")/rs.getDouble("mat_may"))*100) )/3 )*100)/100;
                            obj.val(contCol + 9, unis + 3, promxniveldeuni[unis][i], "datos2");
                            totalcol0+=rs.getInt("rep_sep");
                            totalcol1+=rs.getInt("mat_sep");
                            totalcol2+=rs.getInt("rep_ene");
                            totalcol3+=rs.getInt("mat_ene");
                            totalcol4+=rs.getInt("rep_may");
                            totalcol5+=rs.getInt("mat_may");
                            totalcolpor[0]+=(((rs.getDouble("rep_sep")/rs.getDouble("mat_sep"))*100)*100)/100;
                            totalcolpor[1]+=(rs.getDouble("rep_ene")/rs.getDouble("mat_ene"))*100 ;
                            totalcolpor[2]+=(rs.getDouble("rep_may")/rs.getDouble("mat_may"))*100;
                            totalcolpor[3]+=promxniveldeuni[unis][i];
                            nivelesxuni[unis]+=1;
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 9, unis + 3, unis + 3, "error");
                        }
                    } else {//la universidad no tiene el nivel
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 9, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                } 
                obj.val(contCol, unistmp + 3, totalcol0, "total");
                obj.val(contCol + 1, unistmp + 3, totalcol1, "total");
                obj.val(contCol + 2, unistmp + 3, totalcolpor[0], "total2");
                obj.val(contCol + 3, unistmp + 3, totalcol2, "total");
                obj.val(contCol + 4, unistmp + 3, totalcol3, "total");
                obj.val(contCol + 5, unistmp + 3, totalcolpor[1], "total2");
                obj.val(contCol + 6, unistmp + 3, totalcol4, "total");
                obj.val(contCol + 7, unistmp + 3, totalcol5, "total");
                obj.val(contCol + 8, unistmp + 3, totalcolpor[2], "total2");
                obj.val(contCol + 9, unistmp + 3, totalcolpor[3], "total2");
                contCol += 10;
                contCol2+=10;
                
            }
            
            ////IMPRIME PROMEDIOS POR NIVEL 
            contCol+=1;
            double totalcolxnivel=0;
            double promtot=0;
            obj.combinarCeldas("TOTAL ", contCol, contCol + niveles.size(), 1, 1, "h1");
            double [] promtotxuni = new double[univs.size()] ;
            
            obj.val(contCol + niveles.size(), 2, "Promedio total ", "h2");
            for (int i = 0; i < niveles.size(); i++) { //MUEVE LOS NIVELES
                totalcolxnivel=0;
                obj.val(contCol, 2, "Promedio de reprobación ".concat(niveles.getAbreviatura(i)), "h2");
                int unis;
                for ( unis = 0; unis < univs.size(); unis++) {//MUEVE LAS UNIVERSIDADES
                    promtotxuni [unis] +=promxniveldeuni[unis][i];
                    obj.val(contCol, unis + 3, promxniveldeuni[unis][i], "datos2");
                    obj.val(contCol+1, unis + 3, (promtotxuni[unis]/nivelesxuni[unis]) , "datos2");
                    totalcolxnivel+= promxniveldeuni[unis][i];
                    promtot+=(promtotxuni[unis]/nivelesxuni[unis]);
                }
                
                obj.val(contCol, unis + 3, totalcolxnivel, "total2");
                obj.val(contCol + 1, unis + 3, promtot, "total2");
                contCol+=1;
            }
            
            //IMPRIME REPROBADOS POR CUATRIMESTRE DE TODOS LOS NIVELES JUNTOS
            contCol+=2;
            double [] totalcol= new double[10];
            obj.combinarCeldas("TOTAL ", contCol, contCol + 9, 1, 1, "h1");
               obj.val(contCol, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre Sep-Dic ", "h2");
                obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 2, 2, "%", "h2");
                obj.val(contCol + 3, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre Ene-Abr ", "h2");
                obj.val(contCol + 4, 2, "Matrícula inicial atendida cuatrimestre Ene-Abr", "h2");
                obj.val(contCol + 5, 2, "%", "h2");
                obj.val(contCol + 6, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre May-Ago ", "h2");
                obj.val(contCol + 7, 2, "Matrícula inicial atendida cuatrimestre May-Ago", "h2");
                obj.val(contCol + 8, 2, "%", "h2");
                obj.val(contCol + 9, 2, "PROMEDIO", "h2");
                int unistmp2=0;
              for (int unis = 0; unis < univs.size(); unis++) {
                  obj.val(contCol, unis + 3, matrxuni[unis][0], "datos");
                  obj.val(contCol + 1 , unis + 3, matrxuni[unis][1], "datos");
                  if(matrxuni[unis][1]!=0){
                      obj.val(contCol + 2 , unis + 3,(double) ((double)matrxuni[unis][0]/ (double)matrxuni[unis][1])*100, "datos2");
                      totalcol[2]+= (double) ((double)matrxuni[unis][0]/ (double)matrxuni[unis][1])*100;
                  }else{obj.val(contCol + 2 , unis + 3,0, "datos2");}
                  obj.val(contCol + 3 , unis + 3, matrxuni[unis][2], "datos");
                  obj.val(contCol + 4 , unis + 3, matrxuni[unis][3], "datos");
                  if(matrxuni[unis][3]!=0){
                    obj.val(contCol + 5 , unis + 3,(double) ((double)matrxuni[unis][2]/(double) matrxuni[unis][3])*100, "datos2");
                    totalcol[5]+=(double) ((double)matrxuni[unis][2]/(double) matrxuni[unis][3])*100;
                  }else{obj.val(contCol + 5 , unis + 3,0, "datos2");}
                  obj.val(contCol + 6 , unis + 3, matrxuni[unis][4], "datos");
                  obj.val(contCol + 7 , unis + 3, matrxuni[unis][5], "datos");
                  if(matrxuni[unis][5]!=0){
                    obj.val(contCol + 8 , unis + 3,(double) ((double)matrxuni[unis][4]/(double) matrxuni[unis][5])*100, "datos2");
                    totalcol[8]+=(double) ((double)matrxuni[unis][4]/(double) matrxuni[unis][5])*100;
                  }else{obj.val(contCol + 8 , unis + 3,0, "datos2");}
                  double promtota=(((double) ((double)matrxuni[unis][0]/ (double)matrxuni[unis][1])*100)
                          +((double) ((double)matrxuni[unis][2]/(double) matrxuni[unis][3])*100)+(double) ((double)matrxuni[unis][4]/(double) matrxuni[unis][5])*100)/3;
                  obj.val(contCol + 9 , unis + 3,promtota, "datos2");
                  //ACUMULADORES PARA TOTAL POR COLUMNA
                  totalcol[0]+= matrxuni[unis][0];
                  totalcol[1]+= matrxuni[unis][1];
                   totalcol[3]+= matrxuni[unis][2];
                  totalcol[4]+= matrxuni[unis][4];
                   totalcol[6]+= matrxuni[unis][4];
                  totalcol[7]+= matrxuni[unis][5];
                  totalcol[9]+= promtota;
                  unistmp2=unis+1;
              }  
              obj.val(contCol , unistmp2 + 3, totalcol[0], "total");
              obj.val(contCol + 1 , unistmp2 + 3, totalcol[1], "total");
              obj.val(contCol + 2 , unistmp2 + 3, totalcol[2], "total2");
              obj.val(contCol + 3 , unistmp2 + 3, totalcol[3], "total");
              obj.val(contCol + 4 , unistmp2 + 3, totalcol[4], "total");
              obj.val(contCol + 5 , unistmp2 + 3, totalcol[5], "total2");
              obj.val(contCol + 6 , unistmp2 + 3, totalcol[6], "total");
              obj.val(contCol + 7 , unistmp2 + 3, totalcol[7], "total");
              obj.val(contCol + 8 , unistmp2 + 3, totalcol[8], "total2");
              obj.val(contCol + 9 , unistmp2 + 3, totalcol[9], "total2");
              
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Eficacia, Indicador 3 " + ex.getMessage());
        }
    }

    private void indicador4() {
        c = 2 + 15 * niveles.size();
        obj.crearHoja("Ind. 04 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;

        int mat_ene = 0, mat_sep = 0, mat_may = 0;
 DecimalFormat df = new DecimalFormat("#.##");
        try {
              double [] totalcol = new double[10];
              double [][] promedioxniv = new double[univs.size()][niveles.size()];
              int [] nivelesxuni= new int[univs.size()];
              double []totalpromxuni= new double[univs.size()];
                int unistmp=0;
              int [][] matrxuni=new int[univs.size()][6]  ;
            for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                //ENCABEZADOS
                 for (int z = 0; z < totalcol.length; z++) {
                    totalcol[z]=0;
                }
                obj.combinarCeldas("4. DESERCIÓN CUATRIMESTRAL ".concat(niveles.getAbreviatura(nivel)), contCol, contCol + 9, 1, 1, "h1");
                obj.val(contCol, 2, "Alumnos desertores definitivos del cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 2, 2, "%", "h2");
                obj.val(contCol + 3, 2, "Alumnos desertores definitivos del cuatrimestre Ene-Abr ", "h2");
                obj.val(contCol + 4, 2, "Matrícula inicial atendida cuatrimestre Ene-Abr", "h2");
                obj.val(contCol + 5, 2, "%", "h2");
                obj.val(contCol + 6, 2, "Alumnos desertores definitivos del cuatrimestre May-Ago", "h2");
                obj.val(contCol + 7, 2, "Matrícula inicial atendida cuatrimestre May-Ago", "h2");
                obj.val(contCol + 8, 2, "%", "h2");
                obj.val(contCol + 9, 2, "PROMEDIO", "h2");
                       
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
                        consulta = "SELECT mat_sep, mat_ene, mat_may FROM eficaciain3 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) {//si ya llenaron el indicador 3
                            mat_sep = rs.getInt("mat_sep");
                            mat_ene = rs.getInt("mat_ene");
                            mat_may = rs.getInt("mat_may");
                            consulta = "SELECT tot_deser_sep, tot_deser_ene, tot_deser_may FROM eficaciain4_1"
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                            rs = con.Consultar(consulta);
                            if (rs.next()) { //está llenado el ind 3, pero no el 4
                                matrxuni[unis][0]+=rs.getInt("tot_deser_sep");
                                matrxuni[unis][1]+=mat_sep;
                                matrxuni[unis][2]+=rs.getInt("tot_deser_ene");
                                matrxuni[unis][3]+=mat_ene;
                                matrxuni[unis][4]+=rs.getInt("tot_deser_may");
                                matrxuni[unis][5]+=mat_may;
                                obj.val(contCol, unis + 3, rs.getInt("tot_deser_sep"), "datos");
                                obj.val(contCol + 1, unis + 3, mat_sep, "datos");
                                obj.val(contCol + 2, unis + 3, Math.round((((double)rs.getInt("tot_deser_sep")/(double)mat_sep)*100)*100.0)/100.0, "datos2");
                                obj.val(contCol + 3, unis + 3, rs.getInt("tot_deser_ene"), "datos");
                                obj.val(contCol + 4, unis + 3, mat_ene, "datos");
                                obj.val(contCol + 5, unis + 3, Math.round((((double)rs.getInt("tot_deser_ene")/(double)mat_ene)*100)*100.0)/100.0, "datos2");
                                obj.val(contCol + 6, unis + 3, rs.getInt("tot_deser_may"), "datos");
                                obj.val(contCol + 7, unis + 3, mat_may, "datos");
                                obj.val(contCol + 8, unis + 3, Math.round((((double)rs.getInt("tot_deser_may")/(double)mat_may)*100)*100.0)/100.0, "datos2");
                                 totalcol[0]+=rs.getInt("tot_deser_sep");
                                totalcol[1]+=mat_sep;
                                totalcol[2]+=rs.getInt("tot_deser_ene");
                                totalcol[3]+=mat_ene;
                                totalcol[4]+=rs.getInt("tot_deser_may");
                                totalcol[5]+=mat_may;
                                double prom1=Math.round((((double)rs.getInt("tot_deser_sep")/(double)mat_sep)*100)*100.0)/100.0;
                                double prom2=Math.round((((double)rs.getInt("tot_deser_ene")/(double)mat_ene)*100)*100.0)/100.0;
                                double prom3=Math.round((((double)rs.getInt("tot_deser_may")/(double)mat_may)*100)*100.0)/100.0;
                                totalcol[6]+=prom1;
                                totalcol[7]+=prom2;
                                totalcol[8]+=prom3;
                                totalcol[9]+=Math.round(((prom1 + prom2 + prom3)/3)*100.0)/100.0 ;
                                promedioxniv[unis][nivel]=Math.round(((prom1 + prom2 + prom3)/3)*100.0)/100.0 ;
                                totalpromxuni[unis]+=Math.round(((prom1 + prom2 + prom3)/3)*100.0)/100.0 ;
                                 obj.val(contCol + 9, unis + 3, Math.round(((prom1 + prom2 + prom3)/3)*100.0)/100.0 , "datos2");
                                 nivelesxuni[unis]+=1;
                            } else { /*NO CONCLUIDO*/
                                obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 9, unis + 3, unis + 3, "error");
                            }
                        } else { /*NO CONCLUIDO*/
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 9, unis + 3, unis + 3, "error");
                        }
                    } else { /*NO TIENE EL NIVEL*/
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 9, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                }
                 obj.val(contCol, unistmp + 3,totalcol[0] , "total");
                obj.val(contCol + 1, unistmp + 3,totalcol[1] , "total");
                obj.val(contCol + 2, unistmp + 3,totalcol[6] , "total2");
                obj.val(contCol + 3, unistmp + 3,totalcol[2] , "total");
                obj.val(contCol + 4, unistmp + 3,totalcol[3] , "total");
                obj.val(contCol + 5, unistmp + 3,totalcol[7] , "total2");
                obj.val(contCol + 6, unistmp + 3,totalcol[4], "total");
                obj.val(contCol + 7, unistmp + 3,totalcol[5] , "total");
                obj.val(contCol + 8, unistmp + 3,totalcol[8] , "total2");
                obj.val(contCol + 9, unistmp + 3,totalcol[9] , "total2");
                contCol += 10;
            }
            //SE IMPRIMEN PROMEDIOS POR NIVEL 
            contCol+=1;
            obj.combinarCeldas("TOTAL ", contCol, contCol + niveles.size(), 1, 1, "h1");
            double totalcolpromxn=0;
            double totalcolprom2=0;
            for (int niv = 0; niv < niveles.size(); niv++) {///MUEVE LOS NIVELES
                totalcolpromxn=0;
                obj.val(contCol, 2, "Promedio de deserción "+niveles.getAbreviatura(niv), "h2");
                obj.val(contCol + 1, 2, "Promedio de niveles ", "h2");
                 for (int unis = 0; unis < univs.size(); unis++) {//MUEVE LAS UNIVERSIDADES
                     
                    obj.val(contCol, unis + 3, promedioxniv[unis][niv] , "datos2");
                    //obj.val(contCol + 1, unis + 3, Math.round(((double)((double)promedioxniv[unis][niv])/(double)nivelesxuni[unis])*100.0)/100.0 , "datos2");
                    obj.val(contCol + 1, unis + 3, (totalpromxuni[unis]/ nivelesxuni[unis]), "datos2");
                    totalcolpromxn+= promedioxniv[unis][niv] ;
                    totalcolprom2+=(totalpromxuni[unis]/ nivelesxuni[unis]);
                    unistmp=unis+1;
                }
                  obj.val(contCol, unistmp + 3, totalcolpromxn, "total2");//IMPRIME TOTALES DE COLUMNA
                  obj.val(contCol + 1, unistmp + 3, totalcolprom2, "total2");//IMPRIME TOTALES DE COLUMNA
                 contCol+=1;
            }
            
             //IMPRIME DESERTORES POR CUATRIMESTRE DE TODOS LOS NIVELES JUNTOS
            contCol+=2;
             for (int z = 0; z < totalcol.length; z++) {
                    totalcol[z]=0;
                }
            obj.combinarCeldas("TOTAL ", contCol, contCol + 9, 1, 1, "h1");
               obj.val(contCol, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre Sep-Dic ", "h2");
                obj.val(contCol + 1, 2, "Matrícula inicial atendida cuatrimestre Sep-Dic", "h2");
                obj.val(contCol + 2, 2, "%", "h2");
                obj.val(contCol + 3, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre Ene-Abr ", "h2");
                obj.val(contCol + 4, 2, "Matrícula inicial atendida cuatrimestre Ene-Abr", "h2");
                obj.val(contCol + 5, 2, "%", "h2");
                obj.val(contCol + 6, 2, "Número de alumnos reprobados por primer examen extraordinario del cuatrimestre May-Ago ", "h2");
                obj.val(contCol + 7, 2, "Matrícula inicial atendida cuatrimestre May-Ago", "h2");
                obj.val(contCol + 8, 2, "%", "h2");
                obj.val(contCol + 9, 2, "PROMEDIO", "h2");
                int unistmp2=0;
              for (int unis = 0; unis < univs.size(); unis++) {
                  obj.val(contCol, unis + 3, matrxuni[unis][0], "datos");
                  obj.val(contCol + 1 , unis + 3, matrxuni[unis][1], "datos");
                  if(matrxuni[unis][1]!=0){
                      obj.val(contCol + 2 , unis + 3,(double) ((double)matrxuni[unis][0]/ (double)matrxuni[unis][1])*100, "datos2");
                      totalcol[2]+= (double) ((double)matrxuni[unis][0]/ (double)matrxuni[unis][1])*100;
                  }else{obj.val(contCol + 2 , unis + 3,0, "datos2");}
                  obj.val(contCol + 3 , unis + 3, matrxuni[unis][2], "datos");
                  obj.val(contCol + 4 , unis + 3, matrxuni[unis][3], "datos");
                  if(matrxuni[unis][3]!=0){
                    obj.val(contCol + 5 , unis + 3,(double) ((double)matrxuni[unis][2]/(double) matrxuni[unis][3])*100, "datos2");
                    totalcol[5]+=(double) ((double)matrxuni[unis][2]/(double) matrxuni[unis][3])*100;
                  }else{obj.val(contCol + 5 , unis + 3,0, "datos2");}
                  obj.val(contCol + 6 , unis + 3, matrxuni[unis][4], "datos");
                  obj.val(contCol + 7 , unis + 3, matrxuni[unis][5], "datos");
                  if(matrxuni[unis][5]!=0){
                    obj.val(contCol + 8 , unis + 3,(double) ((double)matrxuni[unis][4]/(double) matrxuni[unis][5])*100, "datos2");
                    totalcol[8]+=(double) ((double)matrxuni[unis][4]/(double) matrxuni[unis][5])*100;
                  }else{obj.val(contCol + 8 , unis + 3,0, "datos2");}
                  double promtota=(((double) ((double)matrxuni[unis][0]/ (double)matrxuni[unis][1])*100)
                          +((double) ((double)matrxuni[unis][2]/(double) matrxuni[unis][3])*100)+(double) ((double)matrxuni[unis][4]/(double) matrxuni[unis][5])*100)/3;
                  obj.val(contCol + 9 , unis + 3,promtota, "datos2");
                  //ACUMULADORES PARA TOTAL POR COLUMNA
                  totalcol[0]+= matrxuni[unis][0];
                  totalcol[1]+= matrxuni[unis][1];
                   totalcol[3]+= matrxuni[unis][2];
                  totalcol[4]+= matrxuni[unis][4];
                   totalcol[6]+= matrxuni[unis][4];
                  totalcol[7]+= matrxuni[unis][5];
                  totalcol[9]+= promtota;
                  unistmp2=unis+1;
              }  
              obj.val(contCol , unistmp2 + 3, totalcol[0], "total");
              obj.val(contCol + 1 , unistmp2 + 3, totalcol[1], "total");
              obj.val(contCol + 2 , unistmp2 + 3, totalcol[2], "total2");
              obj.val(contCol + 3 , unistmp2 + 3, totalcol[3], "total");
              obj.val(contCol + 4 , unistmp2 + 3, totalcol[4], "total");
              obj.val(contCol + 5 , unistmp2 + 3, totalcol[5], "total2");
              obj.val(contCol + 6 , unistmp2 + 3, totalcol[6], "total");
              obj.val(contCol + 7 , unistmp2 + 3, totalcol[7], "total");
              obj.val(contCol + 8 , unistmp2 + 3, totalcol[8], "total2");
              obj.val(contCol + 9 , unistmp2 + 3, totalcol[9], "total2");
              
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 4 " + ex.getMessage());
        }
    }

    private void indicador4b() {
        c = 2 + 5 * causas.size() * niveles.size();
        obj.crearHoja("Ind. 04 b ", f + 1, c);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 1500);
        }
        /*COLUMNAS PRINCIPALES*/
       // columnasPrincipales(2);
        obj.setAnchoColumna(1, 17500);
        obj.combinarCeldas("NO", 0, 0, 1, 3, "h1");
        obj.combinarCeldas("UNIVERSIDAD", 1, 1, 1, 3, "h1");
        obj.setAlturaFilas(2, 40);
        int itmp=0;
        for (int i = 0; i <= univs.size() - 1; i++) {
            obj.val(0, i + 4, String.valueOf((i + 1)), "resultado");//CONSECUTIVO
            obj.val(1, i + 4, univs.getNombre(i).toString(), "resultado");//NOMBRE DE LA UNIVERSIDAD
            itmp=i+1;
        }
        obj.val(1, itmp + 4, "TOTAL", "total");//NOMBRE DE LA UNIVERSIDAD
            
        /* FIN COLUMNAS PRINCIPALES*/
        contCol = 2;
        try {
            int [][] totalxnivel = new int [univs.size()][niveles.size()];
            int totalcol0, totalcol1, totalcol2, unistmp=0;
            int [][][]totalcausas = new int [niveles.size()][univs.size()][3];
            int [][][]totalcausas2 = new int [niveles.size()][univs.size()][3];
            for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                totalcol0=0;totalcol1=0;totalcol2=0;
                obj.combinarCeldas("4. PRINCIPALES CAUSAS DE DESERCIÓN NIVEL " + niveles.getAbreviatura(nivel),
                        contCol, (contCol + (3 * (causas.size()+1)) -1 )+1, 1, 1, "h1");
                for (int causa = 0; causa < causas.size(); causa++) {
                    totalcol0=0;totalcol1=0;totalcol2=0;
                    obj.combinarCeldas(causas.getNombre(causa), contCol, contCol + 2, 2, 2, "h1");
                    obj.val(contCol, 3, "sep", "h2");
                    obj.val(contCol + 1, 3, "ene", "h2");
                    obj.val(contCol + 2, 3, "may", "h2");
                    for (int unis = 0; unis < univs.size(); unis++) { //MUEVE LAS UNIVERSIDADES 
                         //totalcol0=0;totalcol1=0;totalcol2=0;
                        if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                            consulta = "SELECT sep, ene, may FROM eficaciain4"
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_causa=").concat(String.valueOf(causas.getId(causa)))
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                            rs = con.Consultar(consulta);
                            if (rs.next()) {
                                obj.val(contCol, unis + 4, rs.getInt("Sep"), "datos");
                                obj.val(contCol + 1, unis + 4, rs.getInt("Ene"), "datos");
                                obj.val(contCol + 2, unis + 4, rs.getInt("May"), "datos");
                                totalcol0+=rs.getInt("Sep");
                                totalcol1+=rs.getInt("Ene");
                                totalcol2+=rs.getInt("May");
                                totalcausas[nivel][unis][0]+=rs.getInt("Sep");
                                totalcausas[nivel][unis][1]+=rs.getInt("Ene");
                                totalcausas[nivel][unis][2]+=rs.getInt("May");
                                totalcausas2[nivel][unis][0]+=rs.getInt("Sep");
                                totalcausas2[nivel][unis][1]+=rs.getInt("Ene");
                                totalcausas2[nivel][unis][2]+=rs.getInt("May");
                            } else {/*NO HAY RESPUESTA, PONER CEROS*/
                                for (int i = 0; i < 3; i++) {
                                    obj.val(contCol + i, unis + 4, 0, "datos");
                                }
                            }
                        } else {/*NO TIENE EL NIVEL*/
                            if (causa == 0) {
                                obj.combinarCeldas("NO TIENE EL NIVEL", contCol, (contCol + (3 * causas.size()) - 1), unis + 4, unis + 4, "error");
                            }
                        }
                        unistmp=unis+1;
                         
                    }
                   obj.val(contCol, unistmp + 4, totalcol0, "total");
                    obj.val(contCol + 1, unistmp +4,totalcol1, "total");
                    obj.val(contCol + 2, unistmp + 4, totalcol2, "total");
                      
                contCol += 3;

               
                }
        //SE IMPRIMEN TOTALES DE TODAS LAS CAUSAS POR NIVEL
                
                obj.combinarCeldas("TOTAL", contCol, contCol + 3, 2, 2, "h1");
                obj.val(contCol, 3, "sep", "h2");
                obj.val(contCol + 1, 3, "ene", "h2");
                obj.val(contCol + 2, 3, "may", "h2");
                obj.val(contCol + 3, 3, "Total", "h2");
                int [] totalcol=new int [4];
                
                    for (int unis = 0; unis < univs.size(); unis++) {
                        obj.val(contCol, unis + 4, totalcausas[nivel][unis][0], "datos");
                        obj.val(contCol + 1, unis + 4, totalcausas[nivel][unis][1], "datos");
                        obj.val(contCol + 2, unis + 4, totalcausas[nivel][unis][2], "datos");
                        obj.val(contCol + 3, unis + 4, (totalcausas[nivel][unis][0]+totalcausas[nivel][unis][1]+totalcausas[nivel][unis][2]), "datos");
                        totalcol[0]+=totalcausas[nivel][unis][0];    
                        totalcol[1]+=totalcausas[nivel][unis][1];
                        totalcol[2]+=totalcausas[nivel][unis][2];
                        totalcol[3]+=(totalcausas[nivel][unis][0]+totalcausas[nivel][unis][1]+totalcausas[nivel][unis][2]);
                        totalxnivel[unis][nivel]=(totalcausas[nivel][unis][0]+totalcausas[nivel][unis][1]+totalcausas[nivel][unis][2]);
                        unistmp=unis+1;
                    }
                    obj.val(contCol, unistmp + 4, totalcol[0], "total");
                    obj.val(contCol + 1, unistmp + 4, totalcol[1], "total");
                    obj.val(contCol + 2, unistmp + 4, totalcol[2], "total");
                    obj.val(contCol + 3, unistmp + 4, totalcol[3], "total");
               contCol+=4;
                for (int i = 0; i < univs.size(); i++) {
                     totalcausas[nivel][i][0]=0;
                     totalcausas[nivel][i][1]=0;
                     totalcausas[nivel][i][2]=0;
                }
            }
            contCol+=1;
            
            //TOTALES POR NIVEL
             for (int i=contCol; i <= NoColumnas; i++) {
                obj.setAnchoColumna(i, 3500);
            }
             obj.combinarCeldas("TOTAL POR NIVEL", contCol , contCol + niveles.size(), 1, 1, "h1");
             int [] totaltodosniveles=new int [univs.size()];
             int totalcol=0;
             for (int nivel = 0; nivel < niveles.size(); nivel++) {
                 totalcol=0;
                obj.combinarCeldas(niveles.getAbreviatura(nivel), contCol, contCol, 2, 2, "h1");
                
                 for (int unis = 0; unis < univs.size(); unis++) {
                    obj.val(contCol, unis + 4, totalxnivel[unis][nivel], "datos");
                    totaltodosniveles[unis]+=totalxnivel[unis][nivel];
                    totalcol+=totalxnivel[unis][nivel];
                    unistmp=unis+1;
                 }
                  obj.val(contCol, unistmp + 4, totalcol, "total");
                contCol+=1;
            }
             obj.val(contCol , 2, "TOTAL", "h1");
             totalcol=0;
                for (int unis = 0; unis < univs.size(); unis++) {
                   obj.val(contCol, unis+ 4, totaltodosniveles[unis], "datos");
                    totalcol+=totaltodosniveles[unis];
                 }
                obj.val(contCol, unistmp + 4, totalcol, "total");
                  //TOTALES POR NIVEL
             for (int i=contCol; i <= NoColumnas; i++) {
                obj.setAnchoColumna(i, 3500);
            }
             
             contCol+=2;
             
             obj.combinarCeldas("TOTAL POR CUATRIMESTRE", contCol , contCol + 3, 1, 1, "h1");
                      
                 totalcol=0;
                obj.val(contCol, 2, "SEP", "h2");
                obj.val(contCol + 1, 2, "ENE", "h2");
                obj.val(contCol + 2, 2, "MAY", "h2");
                obj.val(contCol + 3, 2, "TOTAL", "h2");
               
                int totalcolcuatri [] = new int[4];
            
                 for (int unis = 0; unis < univs.size(); unis++) {
                       int totsepcuatri=0, totenecuatri=0, totmaycuatri=0, totalcuatri=0;
                        for (int nivel = 0; nivel < niveles.size(); nivel++) {
                            totsepcuatri+=totalcausas2[nivel][unis][0];
                            totenecuatri+=totalcausas2[nivel][unis][1];
                            totmaycuatri+=totalcausas2[nivel][unis][2];
                            
                            
                        }
                        totalcolcuatri[0]+=totsepcuatri;
                            totalcolcuatri[1]+=totenecuatri;
                            totalcolcuatri[2]+=totmaycuatri;
                        totalcuatri = totsepcuatri + totenecuatri + totmaycuatri;
                        totalcolcuatri[3]+=totalcuatri;
                    obj.val(contCol, unis + 4, totsepcuatri, "datos");
                    obj.val(contCol + 1, unis + 4, totenecuatri, "datos");
                    obj.val(contCol + 2, unis + 4, totmaycuatri, "datos");
                    obj.val(contCol + 3, unis + 4, totalcuatri, "datos");
                    
                    unistmp=unis+1;
                 }
                 obj.val(contCol, unistmp + 4, totalcolcuatri[0], "total");
                obj.val(contCol + 1, unistmp + 4, totalcolcuatri[1], "total");
                obj.val(contCol + 2, unistmp + 4, totalcolcuatri[2], "total");
                obj.val(contCol + 3, unistmp + 4, totalcolcuatri[3], "total");
                contCol+=1;
            
        } catch (Exception ex) {
            System.err.println("\n\n\nERROR: Sabana general Indicador 4 B " + ex.getMessage());
        } finally {
            causas = null;
        }
    }

    private void indicador5() {
        c = 2 + niveles.size() * modalidades.size() * 3 * 2;
        obj.crearHoja("Ind. 05 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        obj.setAlturaFilas(3, 57);
        contCol = 2;
        try {
            int totalcol0, totalcol1, totalcol2, unistmp=0;
            for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                for (int an = 2; an >= 1; an--) {
                    if (an == 2) {//año actual
                        obj.combinarCeldas("5. TASA DE EGRESO Y TITULACIÓN " + niveles.getAbreviatura(nivel) + " (" + (Integer.parseInt(anio) + 1) + ")", contCol, contCol + modalidades.size() * 3 - 1, 1, 1, "h1");
                    } else {//año actual -1
//                        if (niveles.getId(nivel) != 3) {
                            obj.combinarCeldas("5. TASA DE EGRESO Y TITULACIÓN " + niveles.getAbreviatura(nivel) + " (" + (anio) + ")", contCol, contCol + modalidades.size() * 3 - 1, 1, 1, "h1");
//                        }
                    }
//                    if (niveles.getId(nivel) == 3 && an == 1) {
                        /*no debe hacer nada porque cuando el nivel es 3 (Lic) y el año es 1 (actual menos 1)
                         * no hay nada registrado en la base de datos.
                         */
//                    } else {
                        for (int mod = 0; mod < modalidades.size(); mod++) {
                            totalcol0=0; totalcol1=0; totalcol2=0;
                            obj.combinarCeldas(modalidades.getNombre(mod), contCol, contCol + 2, 2, 2, "h1");
                            obj.val(contCol, 3, "INGRESO", "h2");
                            obj.val(contCol + 1, 3, "EGRESO", "h2");
                            obj.val(contCol + 2, 3, "TITULADOS", "h2");
                            for (int unis = 0; unis < univs.size(); unis++) {
                                if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                                    if (contieneModalidad(String.valueOf(modalidades.getId(mod)), String.valueOf(univs.getId(unis)), String.valueOf(niveles.getId(nivel)))) {
                                        consulta = "SELECT SUM(ingreso) as 'ingreso', SUM(egresados) AS 'egresados', SUM(titulados) as'titulados' FROM eficaciain5"
                                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                                .concat(" AND id_periodo=").concat(periodo)
                                                .concat(" AND id_modelo=").concat(String.valueOf(modalidades.getId(mod)))
                                                .concat(" AND anio=").concat(String.valueOf(an));
                                        rs = con.Consultar(consulta);
                                        if (rs.next()) {
                                            obj.val(contCol, unis + 4, rs.getInt("ingreso"), "datos");
                                            obj.val(contCol + 1, unis + 4, rs.getInt("egresados"), "datos");
                                            obj.val(contCol + 2, unis + 4, rs.getInt("titulados"), "datos");
                                            totalcol0+=rs.getInt("ingreso");
                                            totalcol1+=rs.getInt("egresados");
                                            totalcol2+=rs.getInt("titulados");
                                        } else {//devuelve 0 filas
                                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                                        }
                                    } else {//el nivel no tiene la modalidad
                                        obj.combinarCeldas("SIN MODALIDAD", contCol, contCol + 2, unis + 4, unis + 4, "error");
                                    }
                                } else {//no tiene nivel
                                    obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, unis + 4, unis + 4, "error");
                                }
                                unistmp=unis+1;
                            }
                             obj.val(contCol, unistmp + 4, totalcol0, "total");
                            obj.val(contCol + 1, unistmp + 4, totalcol1, "total");
                            obj.val(contCol + 2, unistmp + 4, totalcol2, "total");
                                           
                            contCol += 3;
                        }
                    }
//                }
            }
            //ENCABEZADOS
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 5 " + ex.getMessage());
        } finally {
            modalidades = null;
        }
    }

        private void indicador5b() {
        c = 5 * niveles.size();
        obj.crearHoja("Ind. 05 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        obj.setAlturaFilas(3, 57);
        contCol = 2;
        int totalcol[]=new int[2];
        double totalcolporc;
        int unistmp=0;
        
        try {
             for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                 totalcol[0]=0;
                 totalcol[1]=0;
                 totalcolporc=0;
               obj.combinarCeldas("Registro de ".concat(niveles.getAbreviatura(nivel).concat(" ante la Dirección General de Profesiones de la Secretaría de educación Pública")), contCol, contCol + 2, 1, 2, "h1");
                obj.val(contCol, 3, "Total de registro de titulados por parte de la universidad", "h2");
                obj.val(contCol + 1, 3, "Total de registro de titulados por parte de la D.G.P", "h2");
                obj.val(contCol + 2, 3, "Porcentaje de registro ante la D.G.P", "h2");
                       
                for (int unis = 0; unis < univs.size(); unis++) {//MUEVE LAS UNIVERSIDADES
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
                        consulta = "SELECT SUM(titulados_universidad) as titulados_universidad, SUM(titulados_dgp) as titulados_dgp  from eficaciain5_1"
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) {//si ya llenaron el indicador 3
                           
                                obj.val(contCol, unis + 4, rs.getInt("titulados_universidad"), "datos");
                                obj.val(contCol + 1, unis + 4,rs.getInt("titulados_dgp") , "datos");
                                double porcentaje= (rs.getDouble("titulados_dgp") / rs.getDouble("titulados_universidad") )*100;
                                obj.val(contCol + 2, unis + 4,porcentaje, "datos2");
                                totalcol[0]+=rs.getInt("titulados_universidad");
                                totalcol[1]+=rs.getInt("titulados_dgp");
                                totalcolporc+=porcentaje;
                              
                        } else { /*NO CONCLUIDO*/
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                        }
                    } else { /*NO TIENE EL NIVEL*/
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                obj.val(contCol, unistmp + 4, totalcol[0], "datos");
                obj.val(contCol + 1, unistmp + 4,totalcol[1] , "datos");
                obj.val(contCol + 2, unistmp + 4,totalcolporc , "datos2");
                contCol += 3;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 5 b" + ex.getMessage());
        } finally {
      
        }
    }

    
    private void indicador6() {
        c = (2 + 3 * niveles.size())*3;
        obj.crearHoja("Ind. 06 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        obj.setAlturaFilas(3, 57);
        contCol = 2;
        try {
            int totalcol0,totalcol1,totalcol2,unistmp=0;
            //ENCABEZADOS
            obj.combinarCeldas("6.1 EGRESADOS EN EL MARCADO LABORAL Y TRABAJANDO EN ÁREA AFÍN ", contCol, contCol + (niveles.size() * 3) - 1, 1, 1, "h1");
            int inicia62=(contCol + (niveles.size() * 3))+1;
             obj.combinarCeldas("6.2 EGRESADOS DEL CICLO ESCOLAR ANTERIOR TRABAJANDO A SEIS MESES DE EGRESO POR SECTOR", inicia62 , inicia62 +(niveles.size() * 3 ) -1 , 1, 1, "h1");
//            int inicia63=(inicia62 + (niveles.size() * 3))+1; 
//              obj.combinarCeldas("6.3 EGRESADOS DEL CICLO ESCOLAR ANTERIOR TRABAJANDO A SEIS MESES DE EGRESO POR ACTIVIDAD ECONOMICA", inicia63 , inicia63 +(niveles.size() * 9 ) -1 , 1, 1, "h1");
              
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;totalcol1=0;totalcol2=0;
                obj.combinarCeldas("Egresados del sector primario " + niveles.getAbreviatura(nivel) + " en el mercado laboral", contCol, contCol + 2, 2, 2, "h1");
                if (niveles.getId(nivel) == 3) {//LICENCIATURA, OSEA EGRESADOS EN ABRIL
                    obj.val(contCol, 3, "Egresados en el Sector secundario " + (Integer.parseInt(anio) + 1) + " trabajando a seis meses de su egreso", "h2");
                    obj.val(contCol + 1, 3, "Egresados en el Sector terciario " + (Integer.parseInt(anio) + 1), "h2");
                    obj.val(contCol + 2, 3, "Egresados abril " + (Integer.parseInt(anio) + 1) + " cuya actividad laboral es acorde a su formación académica por programa educativo", "h2");
                } else {
                    obj.val(contCol, 3, "Egresados de agosto " + (anio) + " trabajando a seis meses de su egreso", "h2");
                    obj.val(contCol + 1, 3, "Total de egresados de agosto " + (anio), "h2");
                    obj.val(contCol + 2, 3, "Egresados agosto " + (anio) + " cuya actividad laboral es acorde a su formación académica por programa educativo", "h2");
                }
                 for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                        consulta = "SELECT sum(egresados) as suma from eficaciain5 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND id_periodo=").concat(periodo);
                        if (niveles.getId(nivel) != 3) {
                            consulta = consulta.concat(" AND anio=1");
                        } else {
                            consulta = consulta.concat(" AND anio=2");
                        }
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            obj.val(contCol + 1, unis + 4, rs.getInt("suma"), "datos");
                            totalcol0+=rs.getInt("suma");
                            consulta = "SELECT e_trabajando, afin FROM eficaciain6 "
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                    .concat(" AND id_periodo=").concat(periodo);
                            rs = con.Consultar(consulta);
                            
                            if (rs.next()) { //si hay una respuesta de la consulta
                                obj.val(contCol, unis + 4, rs.getInt("e_trabajando"), "datos");
                                obj.val(contCol + 2, unis + 4, rs.getInt("afin"), "datos");
                                totalcol1+=rs.getInt("e_trabajando");
                                totalcol2+=rs.getInt("afin");
                            } else { //si no hay una respuesta de la consulta
                                obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                            }
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                    obj.val(contCol + 1, unistmp + 4, totalcol0, "total");
                    obj.val(contCol, unistmp + 4, totalcol1, "total");
                    obj.val(contCol + 2, unistmp + 4, totalcol2, "total");
                               
                contCol += 3;
            }
            
            ///LLENA DATOS DEL CUADRO 6.2
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;totalcol1=0;totalcol2=0;
                obj.combinarCeldas("Egresados del nivel " + niveles.getAbreviatura(nivel) + " trabajando a seis meses de su egreso", inicia62, inicia62 + 2, 2, 2, "h1");
                    obj.val(inicia62, 3, "Egresados en el sector primario " + (Integer.parseInt(anio) ) + " ", "h2");
                    obj.val(inicia62 + 1, 3, "Egresados en el sector secundario " + (Integer.parseInt(anio) ), "h2");
                    obj.val(inicia62 + 2, 3, "Egresados en el sector terciario" + (Integer.parseInt(anio)) + " ", "h2");
               
                 for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                        consulta = "SELECT sum(egresados) as suma from eficaciain5 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND id_periodo=").concat(periodo);
                        if (niveles.getId(nivel) != 3) {
                            consulta = consulta.concat(" AND anio=1");
                        } else {
                            consulta = consulta.concat(" AND anio=2");
                        }
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            //obj.val(inicia62 + 1, unis + 4, rs.getInt("suma"), "datos");
                            consulta = "SELECT esprimario,essecundario, esterciario FROM eficaciain6 "
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                    .concat(" AND id_periodo=").concat(periodo);
                            rs = con.Consultar(consulta);
                            if (rs.next()) { //si hay una respuesta de la consulta
                                obj.val(inicia62, unis + 4, rs.getInt("esprimario"), "datos");
                                obj.val(inicia62 + 1, unis + 4, rs.getInt("essecundario"), "datos");
                                obj.val(inicia62 + 2, unis + 4, rs.getInt("esterciario"), "datos");
                                totalcol0+=rs.getInt("esprimario");
                                totalcol1+=rs.getInt("essecundario");
                                totalcol2+=rs.getInt("esterciario");
                            } else { //si no hay una respuesta de la consulta
                                obj.combinarCeldas("NO CONCLUIDO", inicia62, inicia62 + 2, unis + 4, unis + 4, "error");
                            }
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", inicia62, inicia62 + 2, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", inicia62, inicia62 + 2, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                  obj.val(inicia62, unistmp + 4, totalcol0, "total");
                obj.val(inicia62 + 1, unistmp + 4, totalcol1, "total");
                obj.val(inicia62 + 2, unistmp + 4, totalcol2, "total");
                inicia62 += 3;
            }
               
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 6 " + ex.getMessage());
        }
    }

    private void indicador7() {
        int ra, rb, rc, rd, re, rf, rg, numEncuestas = 0;
        int totalcol0,totalcol1,totalcol2,totalcol3,totalcol4,totalcol5,totalcol6,totalcol7,totalcol8,totalcol9,totalcol10
                ,totalcol11,totalcol12,totalcol13,totalcol14,totalcol17,totalcol18,totalcol19,totalcol20, unistmp=0;
        double totalcol15,totalcol16;
        double escala5, escala10, totMult, totSum;
        c = 2 + 21 * niveles.size();
        obj.crearHoja("Ind. 07 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;totalcol1=0;totalcol2=0;totalcol3=0;totalcol4=0;totalcol5=0;totalcol6=0;totalcol7=0;totalcol8=0;totalcol9=0;totalcol10=0;
                totalcol11=0;totalcol12=0;totalcol13=0;totalcol14=0;totalcol15=0;totalcol16=0;totalcol17=0;totalcol18=0;totalcol19=0;totalcol20=0;
                for (int y = 0; y <= 14; y++) {
                    obj.setAnchoColumna(contCol + y, 1900);
                }
                obj.combinarCeldas("7. TASA DE EGRESADOS SATISFECHOS DEL NIVEL " + niveles.getAbreviatura(nivel), contCol, contCol + 19, 1, 1, "h1");
                obj.combinarCeldas("RESPUESTAS", contCol, contCol + 7, 2, 2, "h1");
                obj.val(contCol, 3, "MS", "h2");
                obj.val(contCol + 1, 3, "S", "h2");
                obj.val(contCol + 2, 3, "RS", "h2");
                obj.val(contCol + 3, 3, "PS", "h2");
                obj.val(contCol + 4, 3, "NS", "h2");
                obj.val(contCol + 5, 3, "NA", "h2");
                obj.val(contCol + 6, 3, "NE", "h2");
                obj.val(contCol + 7, 3, "TOTAL", "h2");
                obj.combinarCeldas("GRADO DE APLICACIÓN", contCol + 8, contCol + 14, 2, 2, "h1");
                obj.val(contCol + 8, 3, "MS * 5", "h2");
                obj.val(contCol + 9, 3, "S * 4", "h2");
                obj.val(contCol + 10, 3, "RS * 3", "h2");
                obj.val(contCol + 11, 3, "PS * 2", "h2");
                obj.val(contCol + 12, 3, "NS * 1", "h2");
                obj.val(contCol + 13, 3, "TOTAL", "h2");
                obj.val(contCol + 14, 3, "MS+S+RS+PS+NS", "h2");
                obj.combinarCeldas("CALIFICACIÓN ESCALA 5", contCol + 15, contCol + 15, 2, 3, "h1");
                obj.combinarCeldas("CALIFICACIÓN ESCALA 10", contCol + 16, contCol + 16, 2, 3, "h1");
                obj.combinarCeldas("TOTAL MS+S", contCol + 17, contCol + 17, 2, 3, "h1");
                obj.combinarCeldas("MUY SATISFECHOS Y SATISFECHOS", contCol + 18, contCol + 18, 2, 3, "h1");
                obj.combinarCeldas("TOTAL DE EGRESADOS ENCUESTADOS", contCol + 19, contCol + 19, 2, 3, "h1");
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (unis == 0) {
                        consulta = "SELECT COUNT(id_pregunta) as numEncuestas FROM encuesta_preguntas WHERE id_encuesta=("
                                .concat(" SELECT id_encuesta FROM encuestas WHERE id_nivel=")
                                .concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND indicador=7)");
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            numEncuestas = rs.getInt("numEncuestas");//numEncuestas es la cantidad de preguntas
                        } else {
                            numEncuestas = 0;
                        }
                    }
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                        consulta = "SELECT SUM(r_a) as r_a, SUM(r_b) as r_b, SUM(r_c) as r_c,"
                                .concat(" SUM(r_d) as r_d, SUM(r_e) as r_e, SUM(r_f) as r_f, SUM(r_g) as r_g")
                                .concat(" FROM eficaciain7_10 WHERE id_pregunta IN(")
                                .concat(" SELECT id_pregunta FROM encuesta_preguntas WHERE id_encuesta=(")
                                .concat(" SELECT id_encuesta FROM encuestas WHERE id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND indicador=7))")
                                .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            ra = rs.getInt("r_a");
                            rb = rs.getInt("r_b");
                            rc = rs.getInt("r_c");
                            rd = rs.getInt("r_d");
                            re = rs.getInt("r_e");
                            rf = rs.getInt("r_f");
                            rg = rs.getInt("r_g");
                            obj.val(contCol, unis + 4, ra, "datos");
                            obj.val(contCol + 1, unis + 4, rb, "datos");
                            obj.val(contCol + 2, unis + 4, rc, "datos");
                            obj.val(contCol + 3, unis + 4, rd, "datos");
                            obj.val(contCol + 4, unis + 4, re, "datos");
                            obj.val(contCol + 5, unis + 4, rf, "datos");
                            obj.val(contCol + 6, unis + 4, rg, "datos");
                            obj.val(contCol + 7, unis + 4, ra + rb + rc + rd + re + rf + rg, "datos");
                            obj.val(contCol + 8, unis + 4, ra * 5, "datos");
                            obj.val(contCol + 9, unis + 4, rb * 4, "datos");
                            obj.val(contCol + 10, unis + 4, rc * 3, "datos");
                            obj.val(contCol + 11, unis + 4, rd * 2, "datos");
                            obj.val(contCol + 12, unis + 4, re * 1, "datos");
                            totMult = (ra * 5) + (rb * 4) + (rc * 3) + (rd * 2) + (re * 1);
                            totSum = ra + rb + rc + rd + re;
                            totalcol0+=ra;totalcol1+=rb;totalcol2+=rc;totalcol3+=rd;totalcol4+=re;totalcol5+=rf;
                            totalcol6+=rg;totalcol7+=( ra + rb + rc + rd + re + rf + rg);   
                            totalcol8+=(ra*5);totalcol9+=(rb*4);totalcol10+=(rc*3);totalcol11+=(rd*2);
                            totalcol12+=(re*1);
                            if (totSum != 0) {
                                escala5 = totMult / totSum;
                            } else {
                                escala5 = 0;
                            }
                            escala10 = escala5 * 2;
                            obj.val(contCol + 13, unis + 4, totMult, "datos");
                            obj.val(contCol + 14, unis + 4, totSum, "datos");
                            obj.val(contCol + 15, unis + 4, escala5, "datos2");
                            obj.val(contCol + 16, unis + 4, escala10, "datos2");
                            obj.val(contCol + 17, unis + 4, ra + rb, "datos");
                            totalcol13+=totMult;totalcol14+=totSum;totalcol15+=escala5;totalcol16+=escala10;
                            totalcol17+=(ra+rb);
                            if (numEncuestas != 0) {
                                obj.val(contCol + 18, unis + 4, (ra + rb) / numEncuestas, "datos");
                                obj.val(contCol + 19, unis + 4, (ra + rb + rc + rd + re + rf + rg) / numEncuestas, "datos");
                                totalcol18+=((ra + rb) / numEncuestas);totalcol19+=((ra + rb + rc + rd + re + rf + rg) / numEncuestas);
                            } else {
                                obj.val(contCol + 18, unis + 4, "0", "datos");
                                obj.val(contCol + 19, unis + 4, "0", "datos");
                            }
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 7, unis + 4, unis + 4, "error");
                            obj.combinarCeldas("NO CONCLUIDO", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
                            obj.combinarCeldas("NO CONCLUIDO", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 7, unis + 4, unis + 4, "error");
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                    obj.val(contCol, unistmp + 4, totalcol0, "total");
                    obj.val(contCol + 1, unistmp + 4, totalcol1, "total");
                    obj.val(contCol + 2, unistmp + 4, totalcol2, "total");
                    obj.val(contCol + 3, unistmp + 4, totalcol3, "total");
                    obj.val(contCol + 4, unistmp + 4, totalcol4, "total");
                    obj.val(contCol + 5, unistmp + 4, totalcol5, "total");
                    obj.val(contCol + 6, unistmp + 4, totalcol6, "total");
                    obj.val(contCol + 7, unistmp + 4, totalcol7, "total");
                    obj.val(contCol + 8, unistmp + 4, totalcol8, "total");
                    obj.val(contCol + 9, unistmp + 4, totalcol9, "total");
                    obj.val(contCol + 10, unistmp + 4, totalcol10, "total");
                    obj.val(contCol + 11, unistmp + 4, totalcol11, "total");
                    obj.val(contCol + 12, unistmp + 4, totalcol12, "total");
                    obj.val(contCol + 13, unistmp + 4, totalcol13, "total");
                    obj.val(contCol + 14, unistmp + 4, totalcol14, "total");
                    obj.val(contCol + 15, unistmp + 4, totalcol15, "total2");
                    obj.val(contCol + 16, unistmp + 4, totalcol16, "total2");
                    obj.val(contCol + 17, unistmp + 4, totalcol17, "total");
                    obj.val(contCol + 18, unistmp + 4, totalcol18, "total");
                    obj.val(contCol + 19, unistmp + 4, totalcol19, "total");
                    
                
                contCol += 21;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 7 " + ex.getMessage());
        }
    }

    private void indicador7b() {
         double totalcol0;int unistmp=0;
        int ra, rb, rc, rd, re, rf, rg, pregsNivel, numEncuestas = 0;
        double escala5, escala10, totMult, totSum;

        ListaPreguntas preguntas = new ListaPreguntas("7");

        c = 2 + niveles.size() + preguntas.size();
        obj.crearHoja("Ind. 07 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;
                pregsNivel = preguntas.getPreguntasxNivel(String.valueOf(niveles.getId(nivel)));
                if (pregsNivel != 0) {
                    obj.combinarCeldas("7 b. TASA DE EGRESADOS SATISFECHOS DEL NIVEL " + niveles.getAbreviatura(nivel), contCol,
                            (contCol + pregsNivel - 1), 1, 1, "h1");
                    //System.out.println(niveles.getAbreviatura(nivel) + " " + contCol + " - " + (contCol + pregsNivel - 1));
                }
                for (int p = 0; p < preguntas.size(); p++) {
                    totalcol0=0;
                    if (preguntas.getNivelPregunta(p) == niveles.getId(nivel)) {
                        obj.val(contCol, 2, preguntas.getNombrePregunta(p), "h2");
                        //System.out.println(preguntas.getNombrePregunta(p) + " --- " + contCol);

                        for (int unis = 0; unis < univs.size(); unis++) {
                            if (unis == 0) {
                                consulta = "SELECT COUNT(id_pregunta) as numEncuestas FROM encuesta_preguntas WHERE id_encuesta=("
                                        .concat(" SELECT id_encuesta FROM encuestas WHERE id_nivel=")
                                        .concat(String.valueOf(niveles.getId(nivel)))
                                        .concat(" AND indicador=7)");
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    numEncuestas = rs.getInt("numEncuestas");//numEncuestas es la cantidad de preguntas
                                } else {
                                    numEncuestas = 0;
                                }
                            }
                            if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                                consulta = "SELECT SUM(r_a) as r_a, SUM(r_b) as r_b, SUM(r_c) as r_c,"
                                        .concat(" SUM(r_d) as r_d, SUM(r_e) as r_e, SUM(r_f) as r_f, SUM(r_g) as r_g")
                                        .concat(" FROM eficaciain7_10 WHERE id_pregunta = ")
                                        .concat(String.valueOf(preguntas.getId(p)))
                                        .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                        .concat(" AND id_periodo=").concat(periodo);
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    ra = rs.getInt("r_a");
                                    rb = rs.getInt("r_b");
                                    rc = rs.getInt("r_c");
                                    rd = rs.getInt("r_d");
                                    re = rs.getInt("r_e");
                                    rf = rs.getInt("r_f");
                                    rg = rs.getInt("r_g");
                                    totMult = (ra * 5.0) + (rb * 4.0) + (rc * 3.0) + (rd * 2) + (re * 1);
                                    totSum = ra + rb + rc + rd + re;
                                    if (totSum != 0) {
                                        escala5 = totMult / totSum;
                                    } else {
                                        escala5 = 0;
                                    }
                                    escala10 = escala5 * 2;
                                    obj.val(contCol, unis + 3, escala10, "datos2");
                                    totalcol0+=escala10;
                                } else {
                                    obj.val(contCol, unis + 3, "No Concluido", "datos");
                                }
                            } else {
                               obj.val(contCol, unis + 3, "S/PE", "error");
                             }
                            unistmp=unis+1;
                        }
                        obj.val(contCol, unistmp + 3, totalcol0, "total2");
                        contCol++;
                    }//if
                }//for preguntas
                contCol++;
            }//for niveles
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 7 b " + ex);
        }
    }

    private void indicador8() {
        c = 2 + 4 * niveles.size();
        obj.crearHoja("Ind. 08 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            int totalcol0,totalcol1,totalcol2,totalcol3, unistmp=0;
            for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                totalcol0=0;totalcol1=0;totalcol2=0;totalcol3=0;
                //ENCABEZADOS
                obj.combinarCeldas("8. EGRESADOS DE ".concat(niveles.getAbreviatura(nivel)) + " QUE PRESENTARON EGETSU", contCol, contCol + 3, 1, 1, "h1");
                if (niveles.getId(nivel) == 3) {//LICENCIATURA, OSEA EGRESADOS EN ABRIL
                    obj.val(contCol, 2, "Egresados de abril " + (Integer.parseInt(anio) + 1) + " con testimonio de desempeño sobresaliente del EGETSU", "h2");
                    obj.val(contCol + 1, 2, "Egresados de abril " + (Integer.parseInt(anio) + 1) + " con testimonio de desempeño satisfactorio del EGETSU", "h2");
                    obj.val(contCol + 2, 2, "Egresados de abril " + (Integer.parseInt(anio) + 1) + " sin testimonio del EGETSU", "h2");
                    obj.val(contCol + 3, 2, "Egresados en el mes de abril " + (Integer.parseInt(anio) + 1), "h2");
                } else {
                    obj.val(contCol, 2, "Egresados de agosto " + anio + " con testimonio de desempeño sobresaliente del EGETSU", "h2");
                    obj.val(contCol + 1, 2, "Egresados de agosto " + anio + " con testimonio de desempeño satisfactorio del EGETSU", "h2");
                    obj.val(contCol + 2, 2, "Egresados de agosto " + anio + " sin testimonio del EGETSU", "h2");
                    obj.val(contCol + 3, 2, "Egresados en el mes de agosto " + anio, "h2");
                }
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
                        consulta = "SELECT aplica, sobresaliente, destacado, sin_testimonio FROM eficaciain8"
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            if (rs.getBoolean("aplica")) {
                                obj.val(contCol, unis + 3, rs.getInt("sobresaliente"), "datos");
                                obj.val(contCol + 1, unis + 3, rs.getInt("destacado"), "datos");
                                obj.val(contCol + 2, unis + 3, rs.getInt("sin_testimonio"), "datos");
                                totalcol0+=rs.getInt("sobresaliente");
                                totalcol1+=rs.getInt("destacado");
                                totalcol2+=rs.getInt("sin_testimonio");
                            } else {
                                obj.combinarCeldas("NO APLICA", contCol, contCol + 3, unis + 3, unis + 3, "error");
                            }
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                        }
                        consulta = "SELECT sum(egresados) as suma from eficaciain5 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND id_periodo=").concat(periodo);
                        if (niveles.getId(nivel) != 3) {
                            consulta = consulta.concat(" AND anio=1");
                        } else {
                            consulta = consulta.concat(" AND anio=2");
                        }
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            obj.val(contCol + 3, unis + 3, rs.getInt("suma"), "datos");
                            totalcol3+=rs.getInt("suma");
                        } else {
                            obj.val(contCol + 3, unis + 3, "NO CONCLUIDO", "error");
                        }
                    } else {//la universidad no tiene el nivel
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 3, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                }
                obj.val(contCol, unistmp + 3, totalcol0, "total");
                obj.val(contCol + 1, unistmp + 3, totalcol1, "total");
                obj.val(contCol + 2, unistmp + 3, totalcol2, "total");
                obj.val(contCol + 3, unistmp + 3, totalcol3, "total");
                
                contCol += 4;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Eficacia, Indicador 3 " + ex.getMessage());
        }
    }

    private void indicador9() {
         
        c = 2 + 5 * niveles.size();
        obj.crearHoja("Ind. 09 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 5000);
        }
        columnasPrincipales(3);
        obj.setAlturaFilas(3, 70);
        contCol = 2;
        try {
            int totalcol0, totalcol1,totalcol2, totalcol3, unistmp=0;
            //ENCABEZADOS
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;totalcol1=0;totalcol2=0;totalcol3=0;
                if (niveles.getId(nivel) == 1) {//TSU
                    obj.combinarCeldas("9. EGRESADOS EN ESTUDIOS SUPERIORES A SEIS MESES DE SU EGRESO DE " + niveles.getAbreviatura(nivel), contCol, contCol + 4, 1, 2, "h1");
                    obj.val(contCol, 3, "Egresados de agosto " + anio + " que siguen estudios de licencia profesional o licenciatura", "h2");
                    obj.val(contCol + 1, 3, "Egresados de Agosto " + anio + " que continuan estudios superiores de Licencia Profesional en una UT", "h2");
                    obj.val(contCol + 2, 3, "Egresados de Agosto " + anio + " que continuan estudios superiores de Licenciatura en una UT", "h2");
                    obj.val(contCol + 3, 3, "Egresados de Agosto " + anio, "h2");
                    obj.val(contCol + 4, 3, "Porcentaje de egresados de Agosto  " + anio + " que continuan estudios superiores de licenciatura en una UT", "h2");
                    for (int unis = 0; unis < univs.size(); unis++) {
                        int egreconti=0;
                        if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                            consulta = "SELECT egre_continuan, egre_continuan_ut, egre_continuan_ut_con FROM eficaciain9"
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                            rs = con.Consultar(consulta);
                            if (rs.next()) {
                                obj.val(contCol, unis + 4, rs.getInt("egre_continuan"), "datos");
                                obj.val(contCol + 1, unis + 4, rs.getInt("egre_continuan_ut"), "datos");
                                obj.val(contCol + 2, unis + 4, rs.getInt("egre_continuan_ut_con"), "datos");
                                egreconti=rs.getInt("egre_continuan_ut_con");
                                totalcol0+=rs.getInt("egre_continuan");
                                totalcol1+=rs.getInt("egre_continuan_ut");
                                totalcol2+=rs.getInt("egre_continuan_ut_con");
                            } else {
                                obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 6, unis + 4, unis + 4, "error");
                            }
                            consulta = "SELECT sum(egresados) as suma from eficaciain5 "
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND anio=1");
                            rs = con.Consultar(consulta);
                            if (rs.next()) {
                                obj.val(contCol + 3, unis + 4, rs.getInt("suma"), "datos");
                                if(rs.getInt("suma")>0){
                                    obj.val(contCol + 4, unis + 4,((double)egreconti /(double)rs.getInt("suma"))*100, "datos2");
                                }else{
                                    obj.val(contCol + 4, unis + 4,0, "datos2");
                                }
                                 totalcol3+=rs.getInt("suma");
                            }else{
                                obj.combinarCeldas("DATO", contCol, contCol + 4, unis + 4, unis + 4, "error");
                            }
                        } else {
                            obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 4, unis + 4, unis + 4, "error");
                        }
                        unistmp=unis+1;
                    }
                     obj.val(contCol, unistmp + 4, totalcol0, "total");
                    obj.val(contCol + 1, unistmp + 4, totalcol1, "total");
                    obj.val(contCol + 2, unistmp + 4, totalcol2, "total");
                    obj.val(contCol + 3, unistmp + 4, totalcol3, "total2");
                    contCol += 6;
                } else {
                    if (niveles.getId(nivel) == 3) {//LICENCIATURA 
                        obj.combinarCeldas("9. EGRESADOS EN ESTUDIOS SUPERIORES A SEIS MESES DE SU EGRESO DE " + niveles.getAbreviatura(nivel), contCol, contCol + 1, 1, 2, "h1");
                        obj.val(contCol, 3, "Egresados de Abril " + (Integer.parseInt(anio) + 1) + " que continuan estudios superiores", "h2");
                        obj.val(contCol + 1, 3, "Egresados de Abril " + (Integer.parseInt(anio) + 1), "h2");
                        for (int unis = 0; unis < univs.size(); unis++) {
                            if (contieneNivel(String.valueOf(univs.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                                consulta = "SELECT egre_continuan FROM eficaciain9"
                                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                        .concat(" AND id_periodo=").concat(periodo)
                                        .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    obj.val(contCol, unis + 4, rs.getInt("egre_continuan"), "datos");
                                    totalcol0+=rs.getInt("egre_continuan");
                                } else {
                                    obj.val(contCol, unis + 4, "NO CONLCUIDO", "datos");
                                }
                                consulta = "SELECT sum(egresados) as suma from eficaciain5 "
                                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                        .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                        .concat(" AND id_periodo=").concat(periodo)
                                        .concat(" AND anio=2");
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    obj.val(contCol + 1, unis + 4, rs.getInt("suma"), "datos");
                                    totalcol1+=rs.getInt("suma");
                                }
                            } else {
                                obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 1, unis + 4, unis + 4, "error");
                            }
                            unistmp=unis+1;
                        }
                        obj.val(contCol, unistmp + 4, totalcol0, "total");
                        obj.val(contCol + 1, unistmp + 4, totalcol1, "total");
                        contCol += 3;
                    } else {//OTROS (LP)
                        obj.combinarCeldas("9. EGRESADOS EN ESTUDIOS SUPERIORES A SEIS MESES DE SU EGRESO DE " + niveles.getAbreviatura(nivel), contCol, contCol + 1, 1, 2, "h1");
                        obj.val(contCol, 3, "Egresados de Agosto " + anio + " que continuan estudios superiores", "h2");
                        obj.val(contCol + 1, 3, "Egresados de Agosto " + anio, "h2");
                        for (int unis = 0; unis < univs.size(); unis++) {
                            if (contieneNivel(String.valueOf(univs.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                                consulta = "SELECT egre_continuan FROM eficaciain9"
                                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                        .concat(" AND id_periodo=").concat(periodo)
                                        .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    obj.val(contCol, unis + 4, rs.getInt("egre_continuan"), "datos");
                                    totalcol0+=rs.getInt("egre_continuan");
                                } else {
                                    obj.val(contCol, unis + 4, "NO CONLCUIDO", "datos");
                                }
                                consulta = "SELECT sum(egresados) as suma from eficaciain5 "
                                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                        .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                        .concat(" AND id_periodo=").concat(periodo)
                                        .concat(" AND anio=1");
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    obj.val(contCol + 1, unis + 4, rs.getInt("suma"), "datos");
                                    totalcol1+=rs.getInt("suma");
                                }
                            } else {
                                obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 1, unis + 4, unis + 4, "error");
                            }
                            unistmp=unis+1;
                        }
                        obj.val(contCol, unistmp + 4, totalcol0, "total");
                        obj.val(contCol + 1, unistmp + 4, totalcol1, "total");
                        contCol += 3;
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 9 " + ex.getMessage());
        }
    }

    private void indicador10() {
        int totalcol0,totalcol1,totalcol2,totalcol3,totalcol4,totalcol5,totalcol6,totalcol7,totalcol8,totalcol9,totalcol10
                ,totalcol11,totalcol12,totalcol13,totalcol14,totalcol17,totalcol18,totalcol19,totalcol20, unistmp=0;
        double totalcol15,totalcol16;
        int ra, rb, rc, rd, re, rf, rg, numEncuestas = 0;
        double escala5, escala10, totMult, totSum;
        c = 2 + 21 * niveles.size();
        obj.crearHoja("Ind. 10 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                 totalcol0=0;totalcol1=0;totalcol2=0;totalcol3=0;totalcol4=0;totalcol5=0;totalcol6=0;totalcol7=0;totalcol8=0;totalcol9=0;totalcol10=0;
                totalcol11=0;totalcol12=0;totalcol13=0;totalcol14=0;totalcol15=0;totalcol16=0;totalcol17=0;totalcol18=0;totalcol19=0;totalcol20=0;
               
                for (int y = 0; y <= 14; y++) {
                    obj.setAnchoColumna(contCol + y, 1900);
                }
                obj.combinarCeldas("10. TASA DE EMPLEADORES SATISFECHOS DEL NIVEL " + niveles.getAbreviatura(nivel), contCol, contCol + 19, 1, 1, "h1");
                obj.combinarCeldas("RESPUESTAS", contCol, contCol + 7, 2, 2, "h1");
                obj.val(contCol, 3, "MS", "h2");
                obj.val(contCol + 1, 3, "S", "h2");
                obj.val(contCol + 2, 3, "RS", "h2");
                obj.val(contCol + 3, 3, "PS", "h2");
                obj.val(contCol + 4, 3, "NS", "h2");
                obj.val(contCol + 5, 3, "NA", "h2");
                obj.val(contCol + 6, 3, "NE", "h2");
                obj.val(contCol + 7, 3, "TOTAL", "h2");
                obj.combinarCeldas("GRADO DE APLICACIÓN", contCol + 8, contCol + 14, 2, 2, "h1");
                obj.val(contCol + 8, 3, "MS * 5", "h2");
                obj.val(contCol + 9, 3, "S * 4", "h2");
                obj.val(contCol + 10, 3, "RS * 3", "h2");
                obj.val(contCol + 11, 3, "PS * 2", "h2");
                obj.val(contCol + 12, 3, "NS * 1", "h2");
                obj.val(contCol + 13, 3, "TOTAL", "h2");
                obj.val(contCol + 14, 3, "MS+S+RS+PS+NS", "h2");
                obj.combinarCeldas("CALIFICACIÓN ESCALA 5", contCol + 15, contCol + 15, 2, 3, "h1");
                obj.combinarCeldas("CALIFICACIÓN ESCALA 10", contCol + 16, contCol + 16, 2, 3, "h1");
                obj.combinarCeldas("TOTAL MS+S", contCol + 17, contCol + 17, 2, 3, "h1");
                obj.combinarCeldas("MUY SATISFECHOS Y SATISFECHOS", contCol + 18, contCol + 18, 2, 3, "h1");
                obj.combinarCeldas("TOTAL DE EMPLEADORES ENCUESTADOS", contCol + 19, contCol + 19, 2, 3, "h1");
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (unis == 0) {
                        consulta = "SELECT COUNT(id_pregunta) as numEncuestas FROM encuesta_preguntas WHERE id_encuesta=("
                                .concat(" SELECT id_encuesta FROM encuestas WHERE id_nivel=")
                                .concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND indicador=10)");
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            numEncuestas = rs.getInt("numEncuestas");
                        } else {
                            numEncuestas = 0;
                        }
                    }
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                        consulta = "SELECT SUM(r_a) as r_a, SUM(r_b) as r_b, SUM(r_c) as r_c,"
                                .concat(" SUM(r_d) as r_d, SUM(r_e) as r_e, SUM(r_f) as r_f, SUM(r_g) as r_g")
                                .concat(" FROM eficaciain7_10 WHERE id_pregunta IN(")
                                .concat(" SELECT id_pregunta FROM encuesta_preguntas WHERE id_encuesta=(")
                                .concat(" SELECT id_encuesta FROM encuestas WHERE id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND indicador=10))")
                                .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            ra = rs.getInt("r_a");
                            rb = rs.getInt("r_b");
                            rc = rs.getInt("r_c");
                            rd = rs.getInt("r_d");
                            re = rs.getInt("r_e");
                            rf = rs.getInt("r_f");
                            rg = rs.getInt("r_g");
                            obj.val(contCol, unis + 4, ra, "datos");
                            obj.val(contCol + 1, unis + 4, rb, "datos");
                            obj.val(contCol + 2, unis + 4, rc, "datos");
                            obj.val(contCol + 3, unis + 4, rd, "datos");
                            obj.val(contCol + 4, unis + 4, re, "datos");
                            obj.val(contCol + 5, unis + 4, rf, "datos");
                            obj.val(contCol + 6, unis + 4, rg, "datos");
                            obj.val(contCol + 7, unis + 4, ra + rb + rc + rd + re + rf + rg, "datos");
                            obj.val(contCol + 8, unis + 4, ra * 5, "datos");
                            obj.val(contCol + 9, unis + 4, rb * 4, "datos");
                            obj.val(contCol + 10, unis + 4, rc * 3, "datos");
                            obj.val(contCol + 11, unis + 4, rd * 2, "datos");
                            obj.val(contCol + 12, unis + 4, re * 1, "datos");
                            totMult = (ra * 5) + (rb * 4) + (rc * 3) + (rd * 2) + (re * 1);
                            totSum = ra + rb + rc + rd + re;
                            totalcol0+=ra;
                            totalcol1+=rb;
                            totalcol2+=rc;
                            totalcol3+=rd;
                            totalcol4+=re;
                            totalcol5+=rf;
                            totalcol6+=rg;
                            totalcol7+=(ra + rb + rc + rd + re + rf + rg);
                            totalcol8+=(ra*5);
                            totalcol9+=(rb*4);
                            totalcol10+=(rc*3);
                            totalcol11+=(rd*2);
                            totalcol12+=(re*1);
                            
                            
                            if (totSum != 0) {
                                escala5 = totMult / totSum;
                            } else {
                                escala5 = 0;
                            }
                            escala10 = escala5 * 2;
                            obj.val(contCol + 13, unis + 4, totMult, "datos");
                            obj.val(contCol + 14, unis + 4, totSum, "datos");
                            obj.val(contCol + 15, unis + 4, escala5, "datos2");
                            obj.val(contCol + 16, unis + 4, escala10, "datos2");
                            obj.val(contCol + 17, unis + 4, ra + rb, "datos");
                            totalcol13+=totMult;
                            totalcol14+=totSum;
                            totalcol15+=escala5;
                            totalcol16+=escala10;
                            totalcol17+=(ra+rb);
                            if (numEncuestas != 0) {
                                obj.val(contCol + 18, unis + 4, (ra + rb) / numEncuestas, "datos");
                                obj.val(contCol + 19, unis + 4, (ra + rb + rc + rd + re + rf + rg) / numEncuestas, "datos");
                                totalcol18+=( (ra + rb) / numEncuestas);
                                totalcol19+=((ra + rb + rc + rd + re + rf + rg) / numEncuestas);
                            } else {
                                obj.val(contCol + 18, unis + 4, "0", "datos");
                                obj.val(contCol + 19, unis + 4, "0", "datos");
                            }
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 7, unis + 4, unis + 4, "error");
                            obj.combinarCeldas("NO CONCLUIDO", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
                            obj.combinarCeldas("NO CONCLUIDO", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 7, unis + 4, unis + 4, "error");
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                 obj.val(contCol, unistmp + 4, totalcol0, "total");
                obj.val(contCol + 1, unistmp + 4, totalcol1, "total");
                obj.val(contCol + 2, unistmp + 4, totalcol2, "total");
                obj.val(contCol + 3, unistmp + 4, totalcol3, "total");
                obj.val(contCol + 4, unistmp + 4, totalcol4, "total");
                obj.val(contCol + 5, unistmp + 4, totalcol5, "total");
                obj.val(contCol + 6, unistmp + 4, totalcol6, "total");
                obj.val(contCol + 7, unistmp + 4, totalcol7, "total");
                obj.val(contCol + 8, unistmp + 4, totalcol8, "total");
                obj.val(contCol + 9, unistmp + 4, totalcol9, "total");
                obj.val(contCol + 10, unistmp + 4, totalcol10, "total");
                obj.val(contCol + 11, unistmp + 4, totalcol11, "total");
                obj.val(contCol + 12, unistmp + 4, totalcol12, "total");
                obj.val(contCol + 13, unistmp + 4, totalcol13, "total");
                obj.val(contCol + 14, unistmp + 4, totalcol14, "total");
                obj.val(contCol + 15, unistmp + 4, totalcol15, "total2");
                obj.val(contCol + 16, unistmp + 4, totalcol16, "total2");
                obj.val(contCol + 17, unistmp + 4, totalcol17, "total");
                obj.val(contCol + 18, unistmp + 4, totalcol18, "total");
                obj.val(contCol + 19, unistmp + 4, totalcol19, "total");
                contCol += 21;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 10 " + ex.getMessage());
        }
    }
    
    private void indicador10b() {
        double totalcol0; int unistmp=0;
        int ra, rb, rc, rd, re, rf, rg, pregsNivel, numEncuestas = 0;
        double escala5, escala10, totMult, totSum;

        ListaPreguntas preguntas = new ListaPreguntas("10");

        c = 2 + niveles.size() + preguntas.size();
        obj.crearHoja("Ind. 10 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;
                pregsNivel = preguntas.getPreguntasxNivel(String.valueOf(niveles.getId(nivel)));
                if (pregsNivel != 0) {
                    obj.combinarCeldas("10 b. TASA DE EMPLEADORES SATISFECHOS DEL NIVEL " + niveles.getAbreviatura(nivel), contCol,
                            (contCol + pregsNivel - 1), 1, 1, "h1");
                    //System.out.println(niveles.getAbreviatura(nivel) + " " + contCol + " - " + (contCol + pregsNivel - 1));
                }
                for (int p = 0; p < preguntas.size(); p++) {
                    totalcol0=0;
                    if (preguntas.getNivelPregunta(p) == niveles.getId(nivel)) {
                        obj.val(contCol, 2, preguntas.getNombrePregunta(p), "h2");
                        //System.out.println(preguntas.getNombrePregunta(p) + " --- " + contCol);

                        for (int unis = 0; unis < univs.size(); unis++) {
                            if (unis == 0) {
                                consulta = "SELECT COUNT(id_pregunta) as numEncuestas FROM encuesta_preguntas WHERE id_encuesta=("
                                        .concat(" SELECT id_encuesta FROM encuestas WHERE id_nivel=")
                                        .concat(String.valueOf(niveles.getId(nivel)))
                                        .concat(" AND indicador=10)");
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    numEncuestas = rs.getInt("numEncuestas");//numEncuestas es la cantidad de preguntas
                                } else {
                                    numEncuestas = 0;
                                }
                            }
                            if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                                consulta = "SELECT SUM(r_a) as r_a, SUM(r_b) as r_b, SUM(r_c) as r_c,"
                                        .concat(" SUM(r_d) as r_d, SUM(r_e) as r_e, SUM(r_f) as r_f, SUM(r_g) as r_g")
                                        .concat(" FROM eficaciain7_10 WHERE id_pregunta = ")
                                        .concat(String.valueOf(preguntas.getId(p)))
                                        .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                        .concat(" AND id_periodo=").concat(periodo);
                                rs = con.Consultar(consulta);
                                if (rs.next()) {
                                    ra = rs.getInt("r_a");
                                    rb = rs.getInt("r_b");
                                    rc = rs.getInt("r_c");
                                    rd = rs.getInt("r_d");
                                    re = rs.getInt("r_e");
                                    rf = rs.getInt("r_f");
                                    rg = rs.getInt("r_g");
                                    totMult = (ra * 5.0) + (rb * 4.0) + (rc * 3.0) + (rd * 2) + (re * 1);
                                    totSum = ra + rb + rc + rd + re;
                                    if (totSum != 0) {
                                        escala5 = totMult / totSum;
                                    } else {
                                        escala5 = 0;
                                    }
                                    escala10 = escala5 * 2;
                                    obj.val(contCol, unis + 3, escala10, "datos2");
                                    totalcol0+=escala10;
                                } else {
                                    obj.val(contCol, unis + 3, "No Concluido", "datos");
                                }
                            } else {
                                //obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol, unis + 3, unis + 3, "error");
                                obj.val(contCol, unis + 3, "S/PE", "error");
                                //System.out.println(univs.getNombre(unis) + "NO TIENE EL NIVEL " + niveles.getNombre(nivel));
                            }
                            unistmp=unis+1;
                        }//for unis
                        obj.val(contCol, unistmp + 3, totalcol0, "total2");            
                        contCol++;
                    }//if
                }//for preguntas
                
                contCol++;
            }//for niveles
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 10 b " + ex);
        }
    }
    
    
    

    private void indicador11() {
        double totalcol0=0, totalcol1=0;int unistmp=0;
        c = 25;
        obj.crearHoja("Ind. 11 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            contCol++;
              //ENCABEZADOS
            obj.combinarCeldas("11.1 PRESUPUESTO ORIGINAL", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto Original Asignado Federal y Estatal", "h2");
            obj.val(contCol + 1, 2, "Presupuesto Original Asignado Federal", "h2");
            obj.val(contCol + 2, 2, "Presupuesto Original AsignadoEstatal", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                
                consulta = "SELECT original_f_e, original_f, original_e FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("original_f_e"), "datos2");
                    obj.val(contCol + 1, unis + 3, rs.getDouble("original_f"), "datos2");
                    obj.val(contCol + 2, unis + 3, rs.getDouble("original_e"), "datos2");
                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            
               contCol+=4; 
              //ENCABEZADOS
            obj.combinarCeldas("11.2 Presupuesto Total Autorizado Federal, ampliaciones, reducciones y ejercido ", contCol, contCol + 3, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto Original Autorizado Federal", "h2");
            obj.val(contCol + 1, 2, "Ampliaciones", "h2");
            obj.val(contCol + 2, 2, "Reducciones", "h2");
            obj.val(contCol + 3, 2, "Presupuesto Ejercido Federal", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                
                consulta = "SELECT autorizado_f, ampliaciones_f, reducciones_f, ejercido_f FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("autorizado_f"), "datos2");
                    obj.val(contCol + 1, unis + 3, rs.getDouble("ampliaciones_f"), "datos2");
                    obj.val(contCol + 2, unis + 3, rs.getDouble("reducciones_f"), "datos2");                    
                    obj.val(contCol + 3, unis + 3, rs.getDouble("ejercido_f"), "datos2");                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            
             contCol+=5; 
              //ENCABEZADOS
            obj.combinarCeldas("11.3 Presupuesto Total Autorizado Estatal, ampliaciones, reducciones y ejercido ", contCol, contCol + 3, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto Original Asignado Estatal", "h2");
            obj.val(contCol + 1, 2, "Ampliaciones", "h2");
            obj.val(contCol + 2, 2, "Reducciones", "h2");
            obj.val(contCol + 3, 2, "Presupuesto Ejercido Estatal", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                
                consulta = "SELECT autorizado_e, ampliaciones_e, reducciones_e, ejercido_e FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("autorizado_e"), "datos2");
                    obj.val(contCol + 1, unis + 3, rs.getDouble("ampliaciones_e"), "datos2");
                    obj.val(contCol + 2, unis + 3, rs.getDouble("reducciones_e"), "datos2");                    
                    obj.val(contCol + 3, unis + 3, rs.getDouble("ejercido_e"), "datos2");                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            
              contCol+=5; 
              //ENCABEZADOS
            obj.combinarCeldas("11.4 Presupuesto Total Autorizado Federal y Estatal, ampliaciones, reducciones y ejercido ", contCol, contCol + 3, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto Original Asignado Federal y Estatal", "h2");
            obj.val(contCol + 1, 2, "Ampliaciones Totales Federales y Estatales", "h2");
            obj.val(contCol + 2, 2, "Reducciones Totales Federales y Estatales", "h2");
            obj.val(contCol + 3, 2, "Presupuesto Total Ejercido Federal y Estatal", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                
                consulta = "SELECT t_autorizado_f_e, t_ampliaciones_f_e, t_reducciones_f_e, t_ejercido_f_e FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("t_autorizado_f_e"), "datos2");
                    obj.val(contCol + 1, unis + 3, rs.getDouble("t_ampliaciones_f_e"), "datos2");
                    obj.val(contCol + 2, unis + 3, rs.getDouble("t_reducciones_f_e"), "datos2");                    
                    obj.val(contCol + 3, unis + 3, rs.getDouble("t_ejercido_f_e"), "datos2");                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
              contCol+=5; 
                //ENCABEZADOS
            obj.combinarCeldas("11.5 PRESUPUESTO EJERCIDO", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol, 2, "Presupuesto ejercido", "h2");
            obj.val(contCol + 1, 2, "Presupuesto total autorizado Federal y Estatal", "h2");
            obj.val(contCol + 2, 2, "%", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                
                consulta = "SELECT ejercido, autorizado FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("ejercido"), "datos2");
                    obj.val(contCol + 1, unis + 3, rs.getDouble("autorizado"), "datos2");
                    if(rs.getDouble("autorizado")>0){
                        obj.val(contCol + 2, unis + 3, (rs.getDouble("ejercido")/rs.getDouble("autorizado"))*100, "datos2");
                    }else{
                        obj.val(contCol + 2, unis + 3, 0, "datos2");
                    }
                    totalcol0+=rs.getDouble("ejercido");
                    totalcol1+=rs.getDouble("autorizado");
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
                obj.val(contCol, unistmp + 3, totalcol0, "total2");
                obj.val(contCol + 1, unistmp + 3, totalcol1, "total2");
                if(totalcol1>0){
                    obj.val(contCol + 2, unistmp + 3, (totalcol0/totalcol1)*100 , "total2");
                }
               contCol+=4; 
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 11 " + ex.getMessage());
        }
    }
}
