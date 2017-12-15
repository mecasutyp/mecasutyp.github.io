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
public class ExcelEficacia extends SabanaCategorias {

    ListaCausas causas;
    ListaModalidades modalidades;
    int contCol = 0;
    int c = 0;
    int f = 5;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Excel Eficacia Eliminada :)");
    }

    public ExcelEficacia(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.obj = obj;
        this.f += univs.size();
        this.causas = new ListaCausas();
        this.modalidades = new ListaModalidades();
    }

    public HojaCalculo hacerEficacia() {
        indicador1();
        indicador2();
        indicador3();
        indicador4();
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

    private void indicador1() {
        c = 10;
        double sumporc[]=new double[5];
        int countporc[]=new int[5];
        obj.crearHoja("Ind. 01 ", f, c);
        obj.setAlturaFilas(1, 50);
        obj.setAlturaFilas(2, 120);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
           
        columnasPrincipales(2);
        obj.combinarCeldas("1. ALUMNOS DE NUEVO INGRESO CON EXANI II", 2, 6, 1, 1, "h1");
        obj.combinarCeldas("ANI1", 2, 2, 2, 2, "h1");
        obj.combinarCeldas("ANI2", 3, 3, 2, 2, "h1");
        obj.combinarCeldas("ANI3", 4, 4, 2, 2, "h1");
        obj.combinarCeldas("ANI4", 5, 5, 2, 2, "h1");
        obj.combinarCeldas("ANI4", 6, 6, 2, 2, "h1");
        int itmp=0; 
        double ani1=0, ani2=0, ani3=0, ani4=0, ani5=0;
        for (int i = 0; i <= univs.size() - 1; i++) {
            ani1=0; ani2=0; ani3=0; ani4=0; ani5=0;
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
                        if(rs.getDouble("ingreso")>0){
                            ani1=redondear( ((rs.getDouble("ingreso")/rs.getDouble("egresados_bachillerato"))*100));
                            ani2=redondear( ((rs.getDouble("ingreso")/rs.getDouble("nuevo_ingreso"))*100));
                            sumporc[0]+=ani1;
                            sumporc[1]+=ani2;
                            countporc[0]++;
                            countporc[1]++;
                        }
                        if(rs.getDouble("c1")>0){
                            ani3=redondear( ((rs.getDouble("c1")/rs.getDouble("ingreso"))*100));
                            sumporc[2]+=ani3;
                            countporc[2]++;
                        }
                        if(rs.getDouble("c2")>0){
                            ani4=redondear( ((rs.getDouble("c2")/rs.getDouble("ingreso"))*100));
                            sumporc[3]+=ani4;
                            countporc[3]++;
                        }
                        if(rs.getDouble("c3")>0){
                            ani5=redondear( ((rs.getDouble("c3")/rs.getDouble("ingreso"))*100));
                            sumporc[4]+=ani5;
                            countporc[4]++;
                        }
                        obj.val(2, i + 3,ani1, "datos2");
                        obj.val(3, i + 3,ani2, "datos2");
                        obj.val(4, i + 3,ani3, "datos2");
                        obj.val(5, i + 3,ani4, "datos2");
                        obj.val(6, i + 3,ani5, "datos2");
                        
                    } else {/*NO APLICA*/
                        obj.combinarCeldas("NO APLICA", 2, 6, i + 3, i + 3, "error");
                    }
                } else { // no devolvió nada la consulta, por lo tanto la universidad no ha llenado el indicador
                    obj.combinarCeldas("NO CONCLUIDO", 2, 6, i + 3, i + 3, "error");
                }
            } catch (SQLException ex) {
                System.err.println("ERROR BD: Excel Eficacia, Indicador 1 " + ex.getMessage());
            }
            itmp=i+1;
        }
         obj.val(2, itmp + 3,redondear(sumporc[0]/countporc[0]) , "total2");
         obj.val(3, itmp + 3,redondear(sumporc[1]/countporc[1]) , "total2");
         obj.val(4, itmp + 3,redondear(sumporc[2]/countporc[2]) , "total2");
         obj.val(5, itmp + 3,redondear(sumporc[3]/countporc[3]) , "total2");
         obj.val(6, itmp + 3,redondear(sumporc[4]/countporc[4]) , "total2");
    }

    private void indicador2() {
        c = 3 * (niveles.size() );
        obj.crearHoja("Ind. 02 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 8250);
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
        int noPromSep=0,noPromEne=0,noPromMay=0;
          int xtemp=0;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO NIVEL TECNICO SUPERIOR UNIVERSITARIO Versión 2004", contCol, contCol , 1, 1, "h1");
            obj.val(contCol, 2, "PROMEDIO", "h2");
            noPromSep=0;noPromEne=0;noPromMay=0;
            for (int x = 0; x < univs.size(); x++) {
                xtemp=0;
                  totalcol1=0;totalcol2=0;totalcol3=0;
                consulta = "SELECT id_pe FROM pe_universidad WHERE id_universidad=".concat(String.valueOf(univs.getId(x)))
                        .concat(" AND id_periodo=").concat(periodo)
                        .concat(" AND id_pe IN(select id_pe from programa_educativo where id_nivel=1 AND version=2004)");
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    consulta = "SELECT sep, ene, may"
                            .concat(" FROM eficaciain2 WHERE id_universidad="
                            + "").concat(String.valueOf(univs.getId(x)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_pe IN (select id_pe from programa_educativo where version=2004)");
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
                                noPromSep++;
                                totalcol1+=Math.rint(sumSep / noSep*10)/10;
                            }
                            if (noEne > 0) {
                                noPromEne++;
                                totalcol2+=Math.rint(sumEne / noEne * 10)/10;
                            }
                            if (noMay > 0) {
                                noPromMay++;
                                totalcol3+=Math.rint(sumMay / noMay *10)/10;
                            }
                            
                        double prom = Math.rint((((Math.rint(sumSep / noSep*10)/10) + 
                                (Math.rint(sumEne / noEne * 10)/10) + (Math.rint(sumMay / noMay *10)/10))/3)*10)/10;
                        obj.val(contCol, x + 3, prom, "datos2");
                    
                    } else {
                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol, x + 3, x + 3, "error");
                    }
                } else { // NO TIENE EL NIVEL
                    obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol, x + 3, x + 3, "error");
                }
                xtemp=x+1;
            }
            totalcol1=Math.rint(totalcol1/noPromSep*10)/10;
            totalcol2=Math.rint(totalcol2/noPromEne*10)/10;
            totalcol3=Math.rint(totalcol3/noPromMay*10)/10;
            double tprom=Math.rint(((totalcol1+totalcol2+totalcol3)/3)*10)/10;

            obj.val(contCol, xtemp + 3, tprom , "total2");
            contCol += 1;
            
            
            noPromSep=0;noPromEne=0;noPromMay=0;
            tprom=0;
            for (int i = 0; i < niveles.size(); i++) {
                //ENCABEZADOS
                obj.combinarCeldas("2. APROVECHAMIENTO ACADÉMICO DEL NIVEL " + niveles.getAbreviatura(i), contCol, contCol , 1, 1, "h1");
                obj.val(contCol, 2, "PROMEDIO", "h2");
                 xtemp=0;
                 totalcol1=0;totalcol2=0;totalcol3=0;
                noPromSep=0;noPromEne=0;noPromMay=0;
                for (int x = 0; x < univs.size(); x++) {
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
                            double sum1=0, sum2=0, sum3=0;
                            if (sumSep > 0) {
                                noPromSep++;
                                totalcol1+=Math.rint(sumSep / noSep*10)/10;
                                sum1=Math.rint(sumSep / noSep*10)/10;
                            } 
                            if (sumEne > 0) {
                                noPromEne++;
                                totalcol2+=Math.rint(sumEne / noEne * 10)/10;
                                sum2=Math.rint(sumEne / noEne * 10)/10;
                            } 
                            if (sumMay > 0) {
                                noPromMay++;
                                totalcol3+=Math.rint(sumMay / noMay *10)/10;
                                sum3=Math.rint(sumMay / noMay *10)/10;
                            } 
                            double prom=0;
                        if(sum1>0 || sum2 >0 || sum3>0){    
                            prom = redondear((sum1 + sum2 + sum3)/3);
                        }
                                obj.val(contCol, x + 3, prom, "datos2");
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol , x + 3, x + 3, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol, x + 3, x + 3, "error");
                         
                    }
                    
                    xtemp=x+1;
                }
                totalcol1=redondear(totalcol1/noPromSep);
                totalcol2=redondear(totalcol2/noPromEne);
                totalcol3=redondear(totalcol3/noPromMay);
                 tprom=redondear(((totalcol1+totalcol2+totalcol3)/3));
                
                obj.val(contCol, xtemp + 3, tprom , "total2");
                contCol += 1;
            }
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel Eficacia, Indicador 2. << " + ex.getMessage());
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
            int  unistmp=0;
           int contCol2=contCol;
           double ani1, ani2,ani3;
            for (int i = 0; i < niveles.size(); i++) { //MUEVE LOS NIVELES
           double sumporc [] = new double [4];
           int countporc [] = new int [4];
                
                ani1=0;ani2=0;ani3=0;
                //ENCABEZADOS
                obj.combinarCeldas("3. REPROBACIÓN CUATRIMESTRAL ".concat(niveles.getAbreviatura(i)), contCol2, contCol2 +3, 1, 1, "h1");
                obj.val(contCol, 2, "% Sep-Dic", "h2");
                obj.val(contCol + 1, 2, "% Ene-Abr", "h2");
                obj.val(contCol + 2, 2, "% May-Ago", "h2");
                obj.val(contCol + 3, 2, "PROMEDIO DE REPROBACIÓN "+ niveles.getAbreviatura(i), "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(i)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
                        consulta = "SELECT rep_sep, mat_sep, rep_ene, mat_ene, rep_may, mat_may FROM eficaciain3 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(i)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            
                            if(rs.getDouble("rep_sep")>0){
                                ani1=redondear((rs.getDouble("rep_sep")/rs.getDouble("mat_sep"))*100);
                                sumporc[0]+=ani1;
                                countporc[0]++;
                                obj.val(contCol, unis + 3, ani1, "datos2");
                            }else{
                                obj.val(contCol, unis + 3, 0, "datos2");
                            }
                            if(rs.getDouble("rep_ene")>0){
                                ani2=redondear((rs.getDouble("rep_ene")/rs.getDouble("mat_ene"))*100 );
                                sumporc[1]+=ani2;
                                countporc[1]++;
                                obj.val(contCol + 1, unis + 3, ani2, "datos2");
                            }else{
                                obj.val(contCol + 1, unis + 3, 0, "datos2");
                            }

                            if(rs.getDouble("rep_may")>0){
                                ani3=redondear((rs.getDouble("rep_may")/rs.getDouble("mat_may"))*100);
                                sumporc[2]+=ani3;
                                countporc[2]++;
                                obj.val(contCol + 2, unis + 3, ani3, "datos2");
                            }else{
                                obj.val(contCol + 2, unis + 3, 0, "datos2");
                            }
                                                        
                            
                            if( (redondear((ani1 + ani2 + ani3 )/3))>0 ){
                                obj.val(contCol + 3, unis + 3, redondear((ani1 + ani2 + ani3 )/3), "datos2");
                                sumporc[3]+=redondear((ani1 + ani2 + ani3 )/3);
                                countporc[3]++;
                            }else{
                                obj.val(contCol + 3, unis + 3, 0, "datos2");
                            }
                            
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                        }
                    } else {//la universidad no tiene el nivel
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 3, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                } 
                for(int z=0; z<4;z++){
                    if(sumporc[z]>0){
                        obj.val(contCol +z, unistmp + 3, redondear(sumporc[z]/countporc[z]), "total2");
                    }else{
                        obj.val(contCol +z, unistmp + 3, 0, "total2");
                    }
                }
                contCol += 4;
                contCol2+=4;
                
            }
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel, Eficacia, Indicador 3 " + ex.getMessage());
        }
    }

    private void indicador4() {
        
        c = 10 * niveles.size();
        obj.crearHoja("Ind. 04 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;

        double mat_ene = 0, mat_sep = 0, mat_may = 0;
        try {
                int unistmp=0;
              double ani1, ani2, ani3, anit;
            for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                double sumporc []= new  double[4];
                int countporc []= new  int[4];
                ani1=0;ani2=0;ani3=0;anit=0;
               
                obj.combinarCeldas("4. DESERCIÓN CUATRIMESTRAL ".concat(niveles.getAbreviatura(nivel)), contCol, contCol + 3, 1, 1, "h1");
                obj.val(contCol , 2, "% Sep-Dic", "h2");
                obj.val(contCol + 1, 2, "% Ene-Abr", "h2");
                obj.val(contCol + 2, 2, "% May-Ago", "h2");
                obj.val(contCol + 3, 2, "PROMEDIO", "h2");
                       
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
                        consulta = "SELECT mat_sep, mat_ene, mat_may FROM eficaciain3 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) {//si ya llenaron el indicador 3
                            mat_sep = rs.getDouble("mat_sep");
                            mat_ene = rs.getDouble("mat_ene");
                            mat_may = rs.getDouble("mat_may");
                            consulta = "SELECT tot_deser_sep, tot_deser_ene, tot_deser_may FROM eficaciain4_1"
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_periodo=").concat(periodo)
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                            rs = con.Consultar(consulta);
                            if (rs.next()) { //está llenado el ind 3, pero no el 4
                                if(rs.getDouble("tot_deser_sep")>0){
                                    ani1=redondear((rs.getDouble("tot_deser_sep")/mat_sep)*100);
                                    obj.val(contCol , unis + 3, ani1, "datos2");
                                    sumporc[0]+=ani1;
                                    countporc[0]++;
                                }else{
                                    obj.val(contCol , unis + 3, 0, "datos2");
                                }
                                if(rs.getDouble("tot_deser_ene")>0){
                                    ani2=redondear((rs.getDouble("tot_deser_ene")/mat_ene)*100);
                                    obj.val(contCol + 1, unis + 3, ani2, "datos2");
                                    sumporc[1]+=ani2;
                                    countporc[1]++;
                                }else{
                                    obj.val(contCol + 1, unis + 3, 0, "datos2");
                                }
                                if(rs.getDouble("tot_deser_may")>0){
                                    ani3=redondear((rs.getDouble("tot_deser_may")/mat_may)*100);
                                    obj.val(contCol + 2, unis + 3, ani3, "datos2");
                                    sumporc[2]+=ani3;
                                    countporc[2]++;
                                }else{
                                    obj.val(contCol + 2, unis + 3, 0, "datos2");
                                }
                                if((ani1+ani2+ani3)>0){
                                    anit=redondear((ani1+ani2+ani3)/3);
                                    obj.val(contCol + 3, unis + 3, anit, "datos2");
                                    sumporc[3]+=anit;
                                    countporc[3]++;
                                }else{
                                    obj.val(contCol + 3, unis + 3, 0, "datos2");
                                }
//                                 nivelesxuni[unis]+=1;
                            } else { /*NO CONCLUIDO*/
                                obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                            }
                        } else { /*NO CONCLUIDO*/
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                        }
                    } else { /*NO TIENE EL NIVEL*/
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 3, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                }
                
                  for(int z=0; z<4;z++){
                    if(sumporc[z]>0){
                        obj.val(contCol +z, unistmp + 3, redondear(sumporc[z]/countporc[z]), "total2");
                    }else{
                        obj.val(contCol +z, unistmp + 3, 0, "total2");
                    }
                }
                contCol += 4;
            }
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 4 " + ex.getMessage());
        }
    }

    
    
    
    
    
    
    private void indicador5() {
        c = 2 + niveles.size() * modalidades.size() * 4;
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
            double totalcol0, totalcol1, totalcol2;
            int unistmp=0;
            for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                for (int an = 2; an >= 1; an--) {
                    if (an == 2) {//año actual
                        obj.combinarCeldas("5. TASA DE EGRESO Y TITULACIÓN " + niveles.getAbreviatura(nivel) + " (" + (Integer.parseInt(anio) + 1) + ")", contCol, contCol + modalidades.size() * 2 - 1, 1, 1, "h1");
                    } else {//año actual -1
                        if (niveles.getId(nivel) != 3) {
                            obj.combinarCeldas("5. TASA DE EGRESO Y TITULACIÓN " + niveles.getAbreviatura(nivel) + " (" + (anio) + ")", contCol, contCol + modalidades.size() * 2 - 1, 1, 1, "h1");
                        }
                    }
                    if (niveles.getId(nivel) == 3 && an == 1) {
                        /*no debe hacer nada porque cuando el nivel es 3 (Lic) y el año es 1 (actual menos 1)
                         * no hay nada registrado en la base de datos.
                         */
                    } else {
                        double poregreso=0, portitulacion=0;;
                        for (int mod = 0; mod < modalidades.size(); mod++) {
                            double sumporc []= new double[2];
                            int countporc []= new int[2];
                            totalcol0=0; totalcol1=0; totalcol2=0;
                            obj.combinarCeldas(modalidades.getNombre(mod), contCol, contCol + 1, 2, 2, "h1");
//                            obj.val(contCol, 3, "INGRESO", "h2");
                            obj.val(contCol, 3, "TASA DE EGRESO", "h2");
                            obj.val(contCol + 1, 3, "TASA DE TITULACIÓN", "h2");
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
                                           
                                            if(rs.getDouble("egresados")>0){
                                                poregreso= redondear((rs.getDouble("egresados")/rs.getDouble("ingreso"))*100);
                                                sumporc[0]+=poregreso;
                                                countporc[0]++;
                                                obj.val(contCol, unis + 4, poregreso, "datos2");
                                            }else{
                                                obj.val(contCol, unis + 4, 0, "datos2");
                                            }
                                            if(rs.getDouble("titulados")>0){
                                                portitulacion= redondear((rs.getDouble("titulados")/rs.getDouble("ingreso"))*100);
                                                sumporc[1]+=portitulacion;
                                                countporc[1]++;
                                                obj.val(contCol + 1, unis + 4, portitulacion, "datos2");
                                            }else{
                                                obj.val(contCol + 1, unis + 4, 0, "datos2");
                                            }
                                        } else {//devuelve 0 filas
                                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
                                        }
                                    } else {//el nivel no tiene la modalidad
                                        obj.combinarCeldas("SIN MODALIDAD", contCol, contCol + 1, unis + 4, unis + 4, "error");
                                    }
                                } else {//no tiene nivel
                                    obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 1, unis + 4, unis + 4, "error");
                                }
                                unistmp=unis+1;
                            }
                            
                            obj.val(contCol, unistmp + 4,redondear(sumporc[0]/countporc[0]) , "total2");
                            obj.val(contCol + 1, unistmp + 4, redondear(sumporc[1]/countporc[1]), "total2");
                                           
                            contCol += 2;
                        }
                    }
                }
            }
            //ENCABEZADOS
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel Indicador 5 " + ex.getMessage());
        } finally {
            modalidades = null;
        }
    }

        private void indicador5b() {
        c = 2 * niveles.size();
        obj.crearHoja("Ind. 05 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 6250);
        }
        columnasPrincipales(3);
        obj.setAlturaFilas(3, 57);
        contCol = 2;
        double totalcol[]=new double[2];
        double totalcolporc;
        int unistmp=0;
        
        try {
             for (int nivel = 0; nivel < niveles.size(); nivel++) { //MUEVE LOS NIVELES
                 double sumporc= 0;
                 int countporc= 0;
               obj.combinarCeldas("Registro de ".concat(niveles.getAbreviatura(nivel).concat(" ante la Dirección General de Profesiones de la Secretaría de educación Pública")), contCol, contCol, 1, 3, "h1");
                       
                for (int unis = 0; unis < univs.size(); unis++) {//MUEVE LAS UNIVERSIDADES
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
                        consulta = "SELECT SUM(titulados_universidad) as titulados_universidad, SUM(titulados_dgp) as titulados_dgp  from eficaciain5_1"
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) {//si ya llenaron el indicador 3
                                double porcentaje=0;
                                if(rs.getDouble("titulados_dgp")>0){
                                    porcentaje= redondear((rs.getDouble("titulados_dgp") / rs.getDouble("titulados_universidad") )*100);
                                    sumporc+=porcentaje;
                                    countporc++;
                                    obj.val(contCol, unis + 4,porcentaje, "datos2");
                                }else{
                                    obj.val(contCol, unis + 4, 0, "datos2");
                                }
                              
                        } else { /*NO CONCLUIDO*/
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol, unis + 4, unis + 4, "error");
                        }
                    } else { /*NO TIENE EL NIVEL*/
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                if(sumporc>0){
                    obj.val(contCol, unistmp + 4, redondear(sumporc/countporc) , "total2");
                }else{
                    obj.val(contCol, unistmp + 4, 0 , "total2");
                }    
                contCol += 1;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel Indicador 5 b" + ex.getMessage());
        } finally {
      
        }
    }

    
    private void indicador6() {
        c = (2 + 3 * niveles.size())*6;
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
            double totalcol0,totalcol1,totalcol2;
            int unistmp=0;
            //ENCABEZADOS
            obj.combinarCeldas("6.1 EGRESADOS EN EL MARCADO LABORAL Y TRABAJANDO EN ÁREA AFÍN ", contCol, contCol + (niveles.size() * 2) - 1, 1, 1, "h1");
            int inicia62=(contCol + (niveles.size() * 3))+1;
             obj.combinarCeldas("6.2 EGRESADOS DEL CICLO ESCOLAR ANTERIOR TRABAJANDO A SEIS MESES DE EGRESO POR SECTOR", inicia62 , inicia62 +(niveles.size() * 3 ) -1 , 1, 1, "h1");
            int inicia63=(inicia62 + (niveles.size() * 3))+1; 
              obj.combinarCeldas("6.3 EGRESADOS DEL CICLO ESCOLAR ANTERIOR TRABAJANDO A SEIS MESES DE EGRESO POR ACTIVIDAD ECONOMICA", inicia63 , inicia63 +(niveles.size() * 9 ) -1 , 1, 1, "h1");
              
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;totalcol1=0;totalcol2=0;
                obj.combinarCeldas("Egresados del sector primario " + niveles.getAbreviatura(nivel) + " en el mercado laboral", contCol, contCol + 1, 2, 2, "h1");
                if (niveles.getId(nivel) == 3) {//LICENCIATURA, OSEA EGRESADOS EN ABRIL
                    obj.val(contCol, 3, "Egresados en el Sector secundario " + (Integer.parseInt(anio) + 1) + " trabajando a seis meses de su egreso", "h2");
//                    obj.val(contCol + 1, 3, "Egresados en el Sector terciario " + (Integer.parseInt(anio) + 1), "h2");
                    obj.val(contCol + 1, 3, "Egresados abril " + (Integer.parseInt(anio) + 1) + " cuya actividad laboral es acorde a su formación académica por programa educativo", "h2");
                } else {
                    obj.val(contCol, 3, "Egresados de agosto " + (anio) + " trabajando a seis meses de su egreso", "h2");
//                    obj.val(contCol + 1, 3, "Total de egresados de agosto " + (anio), "h2");
                    obj.val(contCol + 1, 3, "Egresados agosto " + (anio) + " cuya actividad laboral es acorde a su formación académica por programa educativo", "h2");
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
                            double suma= rs.getDouble("suma");
                            double eml=0, etfa=0;
//                            obj.val(contCol + 1, unis + 4, rs.getInt("suma"), "datos");
                            totalcol0+=rs.getDouble("suma");
                            consulta = "SELECT e_trabajando, afin FROM eficaciain6 "
                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                    .concat(" AND id_periodo=").concat(periodo);
                            rs = con.Consultar(consulta);
                            
                            if (rs.next()) { //si hay una respuesta de la consulta
                                
                                if(rs.getDouble("e_trabajando")!=0){eml=redondear(rs.getDouble("e_trabajando")/suma*100);}
                                if(rs.getDouble("afin")!=0){
                                    etfa=redondear(rs.getDouble("afin")/rs.getDouble("e_trabajando")*100);
                                }
                                
                                obj.val(contCol, unis + 4, eml, "datos2");
                                obj.val(contCol + 1, unis + 4, etfa, "datos2");
                                totalcol1+=rs.getDouble("e_trabajando");
                                totalcol2+=rs.getDouble("afin");
                            } else { //si no hay una respuesta de la consulta
                                obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
                            }
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 1, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                    double t_eml=0, t_etfa=0;
                    
                    t_eml=redondear(totalcol1/totalcol0*100);
                    t_etfa=redondear(totalcol2/totalcol1*100);
                    
//                    obj.val(contCol + 1, unistmp + 4, totalcol0, "total");
                    obj.val(contCol, unistmp + 4, t_eml, "total2");
                    obj.val(contCol + 1, unistmp + 4, t_etfa, "total2");
                               
                contCol += 2;
            }
            
            ///LLENA DATOS DEL CUADRO 6.2
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol0=0;totalcol1=0;totalcol2=0;
                double emlsp=0, emlss=0, emlst=0;
                obj.combinarCeldas("Egresados del nivel " + niveles.getAbreviatura(nivel) + " trabajando a seis meses de su egreso", inicia62, inicia62 + 2, 2, 2, "h1");
                    obj.val(inicia62, 3, "EMLSP " + (Integer.parseInt(anio) ) + " ", "h2");
                    obj.val(inicia62 + 1, 3, "EMLSS" + (Integer.parseInt(anio) ), "h2");
                    obj.val(inicia62 + 2, 3, "EMLST" + (Integer.parseInt(anio)) + " ", "h2");
               double suma=0;
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
                                suma= rs.getDouble("esprimario") + rs.getDouble("essecundario") + rs.getDouble("esterciario");
                               
                                if(rs.getDouble("esprimario")>0.0){emlsp=redondear(rs.getDouble("esprimario")/suma*100);}
                                if(rs.getDouble("essecundario")>0.0){emlss=redondear(rs.getDouble("essecundario")/suma*100);}
                                if(rs.getDouble("esterciario")>0.0){emlst=redondear(rs.getDouble("esterciario")/suma*100);}
                                
                                obj.val(inicia62, unis + 4, emlsp, "datos2");
                                obj.val(inicia62 + 1, unis + 4, emlss, "datos2");
                                obj.val(inicia62 + 2, unis + 4, emlst, "datos2");
                                totalcol0+=rs.getDouble("esprimario");
                                totalcol1+=rs.getDouble("essecundario");
                                totalcol2+=rs.getDouble("esterciario");
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
                 double t_suma=0, t_emlsp=0, t_emlss=0, t_emlst=0;
                t_suma=totalcol0+totalcol1+totalcol2;
                t_emlsp=redondear(totalcol0/suma*100);
                t_emlss=redondear(totalcol1/suma*100);
                t_emlst=redondear(totalcol2/suma*100);
                 
                obj.val(inicia62, unistmp + 4, t_emlsp, "total2");
                obj.val(inicia62 + 1, unistmp + 4, t_emlss, "total2");
                obj.val(inicia62 + 2, unistmp + 4, t_emlst, "total2");
                inicia62 += 3;
            }
                
//            ///LLENA DATOS DEL CUADRO 6.3
//            for (int nivel = 0; nivel < niveles.size(); nivel++) {
//                totalcol0=0;totalcol1=0;totalcol2=0;
//                int totalcol3=0,totalcol4=0,totalcol5=0,totalcol6=0,totalcol7=0,totalcol8=0;
//                obj.combinarCeldas("Egresados del nivel " + niveles.getAbreviatura(nivel) + " trabajando a seis meses de su egreso", inicia63, inicia63 +8, 2, 2, "h1");
//                   obj.val(inicia63, 3, "Egresados en electricidad, agua y gas " + (Integer.parseInt(anio) ) + " ", "h2");
//                    obj.val(inicia63 + 1, 3, "Egresados en mineria " + (Integer.parseInt(anio) ), "h2");
//                    obj.val(inicia63 + 2, 3, "Egresados en transportes, correos y almacenamiento" + (Integer.parseInt(anio)) + " ", "h2");
//                    obj.val(inicia63 + 3, 3, "Egresados en construcción" + (Integer.parseInt(anio)) + " ", "h2");
//                    obj.val(inicia63 + 4, 3, "Egresados en servicios financieros y de seguros" + (Integer.parseInt(anio)) + " ", "h2");
//                    obj.val(inicia63 + 5, 3, "Egresados en industrias manufactureras" + (Integer.parseInt(anio)) + " ", "h2");
//                    obj.val(inicia63 + 6, 3, "Egresados en pesca y aculcultura" + (Integer.parseInt(anio)) + " ", "h2");
//                    obj.val(inicia63 + 7, 3, "Egresados en servicios privados no financieros" + (Integer.parseInt(anio)) + " ", "h2");
//                    obj.val(inicia63 + 8, 3, "Egresados en comercios" + (Integer.parseInt(anio)) + " ", "h2");
//               
//                    for (int unis = 0; unis < univs.size(); unis++) {
//                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
//                        consulta = "SELECT sum(egresados) as suma from eficaciain5 "
//                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
//                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
//                                .concat(" AND id_periodo=").concat(periodo);
//                        if (niveles.getId(nivel) != 3) {
//                            consulta = consulta.concat(" AND anio=1");
//                        } else {
//                            consulta = consulta.concat(" AND anio=2");
//                        }
//                        rs = con.Consultar(consulta);
//                        if (rs.next()) {
//                            //obj.val(inicia62 + 1, unis + 4, rs.getInt("suma"), "datos");
//                            consulta = "SELECT  emleag, emlmin, emlatc, emlaco, emlsfs, emlaim, emlapa, emlasp, emlacm FROM eficaciain6 "
//                                    .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
//                                    .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
//                                    .concat(" AND id_periodo=").concat(periodo);
//                            rs = con.Consultar(consulta);
//                            if (rs.next()) { //si hay una respuesta de la consulta
//                                
//                                obj.val(inicia63, unis + 4, rs.getInt("emleag"), "datos");
//                                obj.val(inicia63 + 1, unis + 4, rs.getInt("emlmin"), "datos");
//                                obj.val(inicia63 + 2, unis + 4, rs.getInt("emlatc"), "datos");
//                                obj.val(inicia63 + 3, unis + 4, rs.getInt("emlaco"), "datos");
//                                obj.val(inicia63 + 4, unis + 4, rs.getInt("emlsfs"), "datos");
//                                obj.val(inicia63 + 5, unis + 4, rs.getInt("emlaim"), "datos");
//                                obj.val(inicia63 + 6, unis + 4, rs.getInt("emlapa"), "datos");
//                                obj.val(inicia63 + 7, unis + 4, rs.getInt("emlasp"), "datos");
//                                obj.val(inicia63 + 8, unis + 4, rs.getInt("emlacm"), "datos");
//                                totalcol0+=rs.getInt("emleag");
//                                totalcol1+=rs.getInt("emlmin");
//                                totalcol2+=rs.getInt("emlatc");
//                                totalcol3+=rs.getInt("emlaco");
//                                totalcol4+=rs.getInt("emlsfs");
//                                totalcol5+=rs.getInt("emlaim");
//                                totalcol6+=rs.getInt("emlapa");
//                                totalcol7+=rs.getInt("emlasp");
//                                totalcol8+=rs.getInt("emlacm");
//                            } else { //si no hay una respuesta de la consulta
//                                obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 2, unis + 4, unis + 4, "error");
//                                obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 3, unis + 4, unis + 4, "error");
//                                obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 4, unis + 4, unis + 4, "error");
//                                obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 5, unis + 4, unis + 4, "error");
//                                obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 6, unis + 4, unis + 4, "error");
//                                obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 7, unis + 4, unis + 4, "error");
//                                obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 8, unis + 4, unis + 4, "error");
//                            }
//                        } else {
//                            obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 2, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 3, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 4, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 5, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 6, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 7, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", inicia63, inicia63 + 8, unis + 4, unis + 4, "error");
//                        }
//                    } else {
//                        obj.combinarCeldas("NO TIENE EL NIVEL", inicia63, inicia63 + 8, unis + 4, unis + 4, "error");
//                    }
//                    unistmp=unis+1;
//                }
//                    obj.val(inicia63, unistmp + 4, totalcol0, "total");
//                    obj.val(inicia63 + 1, unistmp + 4, totalcol1, "total");
//                    obj.val(inicia63 + 2, unistmp + 4, totalcol2, "total");
//                    obj.val(inicia63 + 3, unistmp + 4, totalcol3, "total");
//                    obj.val(inicia63 + 4, unistmp + 4, totalcol4, "total");
//                    obj.val(inicia63 + 5, unistmp + 4, totalcol5, "total");
//                    obj.val(inicia63 + 6, unistmp + 4, totalcol6, "total");
//                    obj.val(inicia63 + 7, unistmp + 4, totalcol7, "total");
//                    obj.val(inicia63 + 8, unistmp + 4, totalcol8, "total");
//                inicia63 += 9;
//            }
               
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 6 " + ex.getMessage());
        }
    }

    private void indicador7() {
        double ra, rb, rc, rd, re, rf, rg;
        double ms=0, totalh=0, tes=0;
        int numEncuestas = 0;
        double totalcol0,totalcol1,totalcol2,totalcol3,totalcol4,totalcol5,totalcol6,totalcol7,totalcol8,totalcol9,totalcol10
                ,totalcol11,totalcol12,totalcol13,totalcol14,totalcol17,totalcol18,totalcol19,totalcol20;
        int unistmp=0;
        double totalcol15,totalcol16;
        double escala5, escala10, totMult, totSum;
        c = 2 + 4 * niveles.size();
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
                ms=0;
                totalh=0;
                totalcol0=0;totalcol1=0;totalcol2=0;totalcol3=0;totalcol4=0;totalcol5=0;totalcol6=0;totalcol7=0;totalcol8=0;totalcol9=0;totalcol10=0;
                totalcol11=0;totalcol12=0;totalcol13=0;totalcol14=0;totalcol15=0;totalcol16=0;totalcol17=0;totalcol18=0;totalcol19=0;totalcol20=0;
                for (int y = 0; y <= 14; y++) {
                    obj.setAnchoColumna(contCol + y, 1900);
                }
                obj.combinarCeldas("7. TASA DE EGRESADOS SATISFECHOS DEL NIVEL " + niveles.getAbreviatura(nivel), contCol, contCol + 1, 1, 2, "h1");
//                obj.combinarCeldas("RESPUESTAS", contCol, contCol + 7, 2, 2, "h1");
                obj.val(contCol, 3, "MS", "h2");
                obj.val(contCol + 1, 3, "TES", "h2");
//                obj.val(contCol + 2, 3, "RS", "h2");
//                obj.val(contCol + 3, 3, "PS", "h2");
//                obj.val(contCol + 4, 3, "NS", "h2");
//                obj.val(contCol + 5, 3, "NA", "h2");
//                obj.val(contCol + 6, 3, "NE", "h2");
//                obj.val(contCol + 7, 3, "TOTAL", "h2");
//                obj.combinarCeldas("GRADO DE APLICACIÓN", contCol + 8, contCol + 14, 2, 2, "h1");
//                obj.val(contCol + 8, 3, "MS * 5", "h2");
//                obj.val(contCol + 9, 3, "S * 4", "h2");
//                obj.val(contCol + 10, 3, "RS * 3", "h2");
//                obj.val(contCol + 11, 3, "PS * 2", "h2");
//                obj.val(contCol + 12, 3, "NS * 1", "h2");
//                obj.val(contCol + 13, 3, "TOTAL", "h2");
//                obj.val(contCol + 14, 3, "MS+S+RS+PS+NS", "h2");
//                obj.combinarCeldas("CALIFICACIÓN ESCALA 5", contCol + 15, contCol + 15, 2, 3, "h1");
//                obj.combinarCeldas("CALIFICACIÓN ESCALA 10", contCol + 16, contCol + 16, 2, 3, "h1");
//                obj.combinarCeldas("TOTAL MS+S", contCol + 17, contCol + 17, 2, 3, "h1");
//                obj.combinarCeldas("MUY SATISFECHOS Y SATISFECHOS", contCol + 18, contCol + 18, 2, 3, "h1");
//                obj.combinarCeldas("TOTAL DE EGRESADOS ENCUESTADOS", contCol + 19, contCol + 19, 2, 3, "h1");
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
                            ra = rs.getDouble("r_a");
                            rb = rs.getDouble("r_b");
                            rc = rs.getDouble("r_c");
                            rd = rs.getDouble("r_d");
                            re = rs.getDouble("r_e");
                            rf = rs.getDouble("r_f");
                            rg = rs.getDouble("r_g");
                            totalh= ra + rb + rc + rd + re + rf + rg;
                            if(ra>0){
                                ms=redondear(ra/totalh*100);
                            }
                            if((ra+rb)>0){
                                tes=redondear((ra+rb)/totalh*100);
                            }
                            obj.val(contCol, unis + 4, ms, "datos2");
                            obj.val(contCol + 1, unis + 4, tes, "datos2");
//                            obj.val(contCol + 2, unis + 4, rc, "datos");
//                            obj.val(contCol + 3, unis + 4, rd, "datos");
//                            obj.val(contCol + 4, unis + 4, re, "datos");
//                            obj.val(contCol + 5, unis + 4, rf, "datos");
//                            obj.val(contCol + 6, unis + 4, rg, "datos");
//                            obj.val(contCol + 7, unis + 4, ra + rb + rc + rd + re + rf + rg, "datos");
//                            obj.val(contCol + 8, unis + 4, ra * 5, "datos");
//                            obj.val(contCol + 9, unis + 4, rb * 4, "datos");
//                            obj.val(contCol + 10, unis + 4, rc * 3, "datos");
//                            obj.val(contCol + 11, unis + 4, rd * 2, "datos");
//                            obj.val(contCol + 12, unis + 4, re * 1, "datos");
                            totMult = (ra * 5) + (rb * 4) + (rc * 3) + (rd * 2) + (re * 1);
                            totalcol0+=ra;totalcol1+=rb;totalcol2+=rc;totalcol3+=rd;totalcol4+=re;totalcol5+=rf;
                            totalcol6+=rg;totalcol7+=( ra + rb + rc + rd + re + rf + rg);   
                            totalcol8+=(ra*5);totalcol9+=(rb*4);totalcol10+=(rc*3);totalcol11+=(rd*2);
                            totSum = ra + rb + rc + rd + re;
                            totalcol12+=(re*1);
                            if (totSum != 0) {
                                escala5 = totMult / totSum;
                            } else {
                                escala5 = 0;
                            }
                            escala10 = escala5 * 2;
//                            obj.val(contCol + 13, unis + 4, totMult, "datos");
//                            obj.val(contCol + 14, unis + 4, totSum, "datos");
//                            obj.val(contCol + 15, unis + 4, escala5, "datos2");
//                            obj.val(contCol + 16, unis + 4, escala10, "datos2");
//                            obj.val(contCol + 17, unis + 4, ra + rb, "datos");
                            totalcol13+=totMult;totalcol14+=totSum;totalcol15+=escala5;totalcol16+=escala10;
                            totalcol17+=(ra+rb);
                            if (numEncuestas != 0) {
//                                obj.val(contCol + 18, unis + 4, (ra + rb) / numEncuestas, "datos");
//                                obj.val(contCol + 19, unis + 4, (ra + rb + rc + rd + re + rf + rg) / numEncuestas, "datos");
                                totalcol18+=((ra + rb) / numEncuestas);totalcol19+=((ra + rb + rc + rd + re + rf + rg) / numEncuestas);
                            } else {
//                                obj.val(contCol + 18, unis + 4, "0", "datos");
//                                obj.val(contCol + 19, unis + 4, "0", "datos");
                            }
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 1, unis + 4, unis + 4, "error");
//                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
//                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                double t_ms=0, t_tes=0;
                    if(totalcol0>0){
                    t_ms=redondear(totalcol0/totalcol7*100);
                    }
                    if((totalcol0+totalcol1)>0){
                    t_tes=redondear((totalcol0+totalcol1)/totalcol7*100);
                    }
                    obj.val(contCol, unistmp + 4, t_ms, "total");
                    obj.val(contCol + 1, unistmp + 4, t_tes, "total");
//                    obj.val(contCol + 2, unistmp + 4, totalcol2, "total");
//                    obj.val(contCol + 3, unistmp + 4, totalcol3, "total");
//                    obj.val(contCol + 4, unistmp + 4, totalcol4, "total");
//                    obj.val(contCol + 5, unistmp + 4, totalcol5, "total");
//                    obj.val(contCol + 6, unistmp + 4, totalcol6, "total");
//                    obj.val(contCol + 7, unistmp + 4, totalcol7, "total");
//                    obj.val(contCol + 8, unistmp + 4, totalcol8, "total");
//                    obj.val(contCol + 9, unistmp + 4, totalcol9, "total");
//                    obj.val(contCol + 10, unistmp + 4, totalcol10, "total");
//                    obj.val(contCol + 11, unistmp + 4, totalcol11, "total");
//                    obj.val(contCol + 12, unistmp + 4, totalcol12, "total");
//                    obj.val(contCol + 13, unistmp + 4, totalcol13, "total");
//                    obj.val(contCol + 14, unistmp + 4, totalcol14, "total");
//                    obj.val(contCol + 15, unistmp + 4, totalcol15, "total2");
//                    obj.val(contCol + 16, unistmp + 4, totalcol16, "total2");
//                    obj.val(contCol + 17, unistmp + 4, totalcol17, "total");
//                    obj.val(contCol + 18, unistmp + 4, totalcol18, "total");
//                    obj.val(contCol + 19, unistmp + 4, totalcol19, "total");
                    
                
                contCol += 2;
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
                    obj.val(contCol, 2, "EGELA " + (Integer.parseInt(anio) + 1) , "h2");
                    obj.val(contCol + 1, 2, "EGELB " + (Integer.parseInt(anio) + 1) , "h2");
                    obj.val(contCol + 2, 2, "EGELC " + (Integer.parseInt(anio) + 1) , "h2");
                    obj.val(contCol + 3, 2, "EGELD " + (Integer.parseInt(anio) + 1), "h2");
                } else {
                    obj.val(contCol, 2, "EGELA " + anio , "h2");
                    obj.val(contCol + 1, 2, "EGELB " + anio , "h2");
                    obj.val(contCol + 2, 2, "EGELC " + anio , "h2");
                    obj.val(contCol + 3, 2, "EGELD " + anio, "h2");
                }
                for (int unis = 0; unis < univs.size(); unis++) {
                    double egresados=0;
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) { //la universidad si tiene el nivel
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
                            egresados=rs.getDouble("suma");
                            totalcol3+=rs.getDouble("suma");
                        } else {
                            obj.val(contCol + 3, unis + 3, "NO CONCLUIDO", "error");
                        }
                        
                        
                        consulta = "SELECT aplica, sobresaliente, destacado, sin_testimonio FROM eficaciain8"
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            if (rs.getBoolean("aplica")) {
                                double egela=0, egelb=0, egelc=0, egeld=0, suma=0;
                                suma=(rs.getDouble("sobresaliente") + rs.getDouble("destacado") + rs.getDouble("sin_testimonio"));
                                if(suma>0){egela=redondear(suma/egresados*100);}
                                if(rs.getDouble("sobresaliente")>0){egelb=redondear(rs.getDouble("sobresaliente")/suma*100);}
                                if(rs.getDouble("destacado")>0){egelc=redondear(rs.getDouble("destacado")/suma*100);}
                                if(rs.getDouble("sin_testimonio")>0){egeld=redondear(rs.getDouble("sin_testimonio")/suma*100);}
                                
                                obj.val(contCol, unis + 3, egelb, "datos2");
                                obj.val(contCol + 1, unis + 3, egelc, "datos2");
                                obj.val(contCol + 2, unis + 3, egeld, "datos2");
                                obj.val(contCol + 3, unis + 3, egela, "datos2");
                                totalcol0+=rs.getDouble("sobresaliente");
                                totalcol1+=rs.getDouble("destacado");
                                totalcol2+=rs.getDouble("sin_testimonio");
                            } else {
                                obj.combinarCeldas("NO APLICA", contCol, contCol + 3, unis + 3, unis + 3, "error");
                            }
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                        }
                       
                    } else {//la universidad no tiene el nivel
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 3, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                }
                double t_egela=0, t_egelb=0, t_egelc=0, t_egeld=0, t_suma=0;
                t_suma = (totalcol0+totalcol1+totalcol2);
                if(t_suma>0){t_egela=redondear(t_suma/totalcol3*100);}
                if(totalcol0>0){t_egelb=redondear(totalcol0/t_suma*100);}
                if(totalcol1>0){t_egelc=redondear(totalcol1/t_suma*100);}
                if(totalcol2>0){t_egeld=redondear(totalcol2/t_suma*100);}
                
                obj.val(contCol, unistmp + 3, t_egela, "total2");
                obj.val(contCol + 1, unistmp + 3, t_egelb, "total2");
                obj.val(contCol + 2, unistmp + 3, t_egelc, "total2");
                obj.val(contCol + 3, unistmp + 3, t_egeld, "total2");
                
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
        double totalcol0,totalcol1,totalcol2,totalcol3,totalcol4,totalcol5,totalcol6,totalcol7,totalcol8,totalcol9,totalcol10
                ,totalcol11,totalcol12,totalcol13,totalcol14,totalcol17,totalcol18,totalcol19,totalcol20;
        int unistmp=0;
        double totalcol15,totalcol16;
        double ra, rb, rc, rd, re, rf, rg, ms, tes;
        int numEncuestas = 0;
        double escala5, escala10, totMult, totSum, totalh;
        c = 2 + 21 * niveles.size();
        obj.crearHoja("Ind. 10 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 8250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                 totalcol0=0;totalcol1=0;totalcol2=0;totalcol3=0;totalcol4=0;totalcol5=0;totalcol6=0;totalcol7=0;totalcol8=0;totalcol9=0;totalcol10=0;
                totalcol11=0;totalcol12=0;totalcol13=0;totalcol14=0;totalcol15=0;totalcol16=0;totalcol17=0;totalcol18=0;totalcol19=0;totalcol20=0;
               ms=0;
               tes=0;
                for (int y = 0; y <= 14; y++) {
                    obj.setAnchoColumna(contCol + y, 1900);
                }
                obj.combinarCeldas("10. TASA DE EMPLEADORES SATISFECHOS DEL NIVEL " + niveles.getAbreviatura(nivel), contCol, contCol + 1, 1, 2, "h1");
//                obj.combinarCeldas("RESPUESTAS", contCol, contCol + 7, 2, 2, "h1");
                obj.val(contCol, 3, "MS", "h2");
                obj.val(contCol + 1, 3, "TES", "h2");
//                obj.val(contCol + 2, 3, "MS--", "h2");
//                obj.val(contCol + 3, 3, "PS", "h2");
//                obj.val(contCol + 4, 3, "NS", "h2");
//                obj.val(contCol + 5, 3, "NA", "h2");
//                obj.val(contCol + 6, 3, "NE", "h2");
//                obj.val(contCol + 7, 3, "TOTAL", "h2");
//                obj.combinarCeldas("GRADO DE APLICACIÓN", contCol + 8, contCol + 14, 2, 2, "h1");
//                obj.val(contCol + 8, 3, "MS * 5", "h2");
//                obj.val(contCol + 9, 3, "S * 4", "h2");
//                obj.val(contCol + 10, 3, "RS * 3", "h2");
//                obj.val(contCol + 11, 3, "PS * 2", "h2");
//                obj.val(contCol + 12, 3, "NS * 1", "h2");
//                obj.val(contCol + 13, 3, "TOTAL", "h2");
//                obj.val(contCol + 14, 3, "MS+S+RS+PS+NS", "h2");
//                obj.combinarCeldas("CALIFICACIÓN ESCALA 5", contCol + 15, contCol + 15, 2, 3, "h1");
//                obj.combinarCeldas("CALIFICACIÓN ESCALA 10", contCol + 16, contCol + 16, 2, 3, "h1");
//                obj.combinarCeldas("TOTAL MS+S", contCol + 17, contCol + 17, 2, 3, "h1");
//                obj.combinarCeldas("MUY SATISFECHOS Y SATISFECHOS", contCol + 18, contCol + 18, 2, 3, "h1");
//                obj.combinarCeldas("TOTAL DE EMPLEADORES ENCUESTADOS", contCol + 19, contCol + 19, 2, 3, "h1");
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
                            ra = rs.getDouble("r_a");
                            rb = rs.getDouble("r_b");
                            rc = rs.getDouble("r_c");
                            rd = rs.getDouble("r_d");
                            re = rs.getDouble("r_e");
                            rf = rs.getDouble("r_f");
                            rg = rs.getDouble("r_g");
                            totalh= ra + rb + rc + rd + re + rf + rg;
                            if(ra>0){
                                ms=redondear(ra/totalh*100);
                            }
                            if((ra+rb)>0){
                                tes=redondear((ra+rb)/totalh*100);
                            }
                            obj.val(contCol, unis + 4, ms, "datos2");
                            obj.val(contCol + 1, unis + 4, tes, "datos2");
//                            obj.val(contCol + 2, unis + 4, ms, "datos2");
//                            obj.val(contCol + 3, unis + 4, tes, "datos2");
//                            obj.val(contCol + 4, unis + 4, re, "datos");
//                            obj.val(contCol + 5, unis + 4, rf, "datos");
//                            obj.val(contCol + 6, unis + 4, rg, "datos");
//                            obj.val(contCol + 7, unis + 4, ra + rb + rc + rd + re + rf + rg, "datos");
//                            obj.val(contCol + 8, unis + 4, ra * 5, "datos");
//                            obj.val(contCol + 9, unis + 4, rb * 4, "datos");
//                            obj.val(contCol + 10, unis + 4, rc * 3, "datos");
//                            obj.val(contCol + 11, unis + 4, rd * 2, "datos");
//                            obj.val(contCol + 12, unis + 4, re * 1, "datos");
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
//                            totalcol8+=(ra*5);
//                            totalcol9+=(rb*4);
//                            totalcol10+=(rc*3);
//                            totalcol11+=(rd*2);
//                            totalcol12+=(re*1);
//                            
                            
//                            if (totSum != 0) {
//                                escala5 = totMult / totSum;
//                            } else {
//                                escala5 = 0;
//                            }
//                            escala10 = escala5 * 2;
//                            obj.val(contCol + 13, unis + 4, totMult, "datos");
//                            obj.val(contCol + 14, unis + 4, totSum, "datos");
//                            obj.val(contCol + 15, unis + 4, escala5, "datos2");
//                            obj.val(contCol + 16, unis + 4, escala10, "datos2");
//                            obj.val(contCol + 17, unis + 4, ra + rb, "datos");
//                            totalcol13+=totMult;
//                            totalcol14+=totSum;
//                            totalcol15+=escala5;
//                            totalcol16+=escala10;
//                            totalcol17+=(ra+rb);
//                            if (numEncuestas != 0) {
//                                obj.val(contCol + 18, unis + 4, (ra + rb) / numEncuestas, "datos");
//                                obj.val(contCol + 19, unis + 4, (ra + rb + rc + rd + re + rf + rg) / numEncuestas, "datos");
//                                totalcol18+=( (ra + rb) / numEncuestas);
//                                totalcol19+=((ra + rb + rc + rd + re + rf + rg) / numEncuestas);
//                            } else {
//                                obj.val(contCol + 18, unis + 4, "0", "datos");
//                                obj.val(contCol + 19, unis + 4, "0", "datos");
//                            }
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
//                            obj.combinarCeldas("NO CONCLUIDO", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 1, unis + 4, unis + 4, "error");
//                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
//                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                double t_ms=0, t_tes=0;
                if(totalcol0>0){
                    t_ms=redondear(totalcol0/totalcol7*100);
                }
                if((totalcol0+totalcol1)>0){
                    t_tes=redondear((totalcol0+totalcol1)/totalcol7*100);
                }
                
                 obj.val(contCol, unistmp + 4, t_ms, "total2");
                obj.val(contCol + 1, unistmp + 4, t_tes, "total2");
//                obj.val(contCol + 2, unistmp + 4, t_ms, "total2");
//                obj.val(contCol + 3, unistmp + 4, t_tes, "total2");
//                obj.val(contCol + 4, unistmp + 4, totalcol4, "total");
//                obj.val(contCol + 5, unistmp + 4, totalcol5, "total");
//                obj.val(contCol + 6, unistmp + 4, totalcol6, "total");
//                obj.val(contCol + 7, unistmp + 4, totalcol7, "total");
//                obj.val(contCol + 8, unistmp + 4, totalcol8, "total");
//                obj.val(contCol + 9, unistmp + 4, totalcol9, "total");
//                obj.val(contCol + 10, unistmp + 4, totalcol10, "total");
//                obj.val(contCol + 11, unistmp + 4, totalcol11, "total");
//                obj.val(contCol + 12, unistmp + 4, totalcol12, "total");
//                obj.val(contCol + 13, unistmp + 4, totalcol13, "total");
//                obj.val(contCol + 14, unistmp + 4, totalcol14, "total");
//                obj.val(contCol + 15, unistmp + 4, totalcol15, "total2");
//                obj.val(contCol + 16, unistmp + 4, totalcol16, "total2");
//                obj.val(contCol + 17, unistmp + 4, totalcol17, "total");
//                obj.val(contCol + 18, unistmp + 4, totalcol18, "total");
//                obj.val(contCol + 19, unistmp + 4, totalcol19, "total");
                contCol += 2;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel general Indicador 10 " + ex.getMessage());
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
        c = 10;
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
            obj.combinarCeldas("11.1 PRESUPUESTO EJERCIDO", contCol, contCol + 2, 1, 1, "h1");
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
//               contCol+=4; 
//              //ENCABEZADOS
//            obj.combinarCeldas("11.2 PRESUPUESTO ORIGINAL", contCol, contCol + 2, 1, 1, "h1");
//            obj.val(contCol, 2, "Presupuesto original federal y estatal", "h2");
//            obj.val(contCol + 1, 2, "Presupuesto original federal", "h2");
//            obj.val(contCol + 2, 2, "Presupuesto original estatal", "h2");
//            for (int unis = 0; unis < univs.size(); unis++) {
//                
//                consulta = "SELECT original_f_e, original_f, original_e FROM eficaciain11 "
//                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
//                        .concat(" AND id_periodo=").concat(periodo);
//                rs = con.Consultar(consulta);
//                if (rs.next()) { //si hay una respuesta de la consulta
//                    obj.val(contCol, unis + 3, rs.getDouble("original_f_e"), "datos2");
//                    obj.val(contCol + 1, unis + 3, rs.getDouble("original_f"), "datos2");
//                    obj.val(contCol + 2, unis + 3, rs.getDouble("original_e"), "datos2");
//                    
//                } else { //si no hay una respuesta de la consulta
//                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 3, unis + 3, "error");
//                }
//                unistmp=unis+1;
//            }
//            
//               contCol+=4; 
//              //ENCABEZADOS
//            obj.combinarCeldas("11.3 Presupuesto federal, ampliaciones, reducciones y ejercido ", contCol, contCol + 3, 1, 1, "h1");
//            obj.val(contCol, 2, "Presupuesto autorizado federal", "h2");
//            obj.val(contCol + 1, 2, "Ampliaciones", "h2");
//            obj.val(contCol + 2, 2, "Reducciones", "h2");
//            obj.val(contCol + 3, 2, "Ejercido", "h2");
//            for (int unis = 0; unis < univs.size(); unis++) {
//                
//                consulta = "SELECT autorizado_f, ampliaciones_f, reducciones_f, ejercido_f FROM eficaciain11 "
//                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
//                        .concat(" AND id_periodo=").concat(periodo);
//                rs = con.Consultar(consulta);
//                if (rs.next()) { //si hay una respuesta de la consulta
//                    obj.val(contCol, unis + 3, rs.getDouble("autorizado_f"), "datos2");
//                    obj.val(contCol + 1, unis + 3, rs.getDouble("ampliaciones_f"), "datos2");
//                    obj.val(contCol + 2, unis + 3, rs.getDouble("reducciones_f"), "datos2");                    
//                    obj.val(contCol + 3, unis + 3, rs.getDouble("ejercido_f"), "datos2");                    
//                } else { //si no hay una respuesta de la consulta
//                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
//                }
//                unistmp=unis+1;
//            }
//            
//             contCol+=5; 
//              //ENCABEZADOS
//            obj.combinarCeldas("11.4 Presupuesto estatal, ampliaciones, reducciones y ejercido ", contCol, contCol + 3, 1, 1, "h1");
//            obj.val(contCol, 2, "Presupuesto autorizado estatal", "h2");
//            obj.val(contCol + 1, 2, "Ampliaciones", "h2");
//            obj.val(contCol + 2, 2, "Reducciones", "h2");
//            obj.val(contCol + 3, 2, "Ejercido", "h2");
//            for (int unis = 0; unis < univs.size(); unis++) {
//                
//                consulta = "SELECT autorizado_e, ampliaciones_e, reducciones_e, ejercido_e FROM eficaciain11 "
//                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
//                        .concat(" AND id_periodo=").concat(periodo);
//                rs = con.Consultar(consulta);
//                if (rs.next()) { //si hay una respuesta de la consulta
//                    obj.val(contCol, unis + 3, rs.getDouble("autorizado_e"), "datos2");
//                    obj.val(contCol + 1, unis + 3, rs.getDouble("ampliaciones_e"), "datos2");
//                    obj.val(contCol + 2, unis + 3, rs.getDouble("reducciones_e"), "datos2");                    
//                    obj.val(contCol + 3, unis + 3, rs.getDouble("ejercido_e"), "datos2");                    
//                } else { //si no hay una respuesta de la consulta
//                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
//                }
//                unistmp=unis+1;
//            }
//            
//              contCol+=5; 
//              //ENCABEZADOS
//            obj.combinarCeldas("11.5 Presupuesto total federal y estatal, ampliaciones, reducciones y ejercido ", contCol, contCol + 3, 1, 1, "h1");
//            obj.val(contCol, 2, "Presupuesto total autorizado federal y estatal", "h2");
//            obj.val(contCol + 1, 2, "Ampliaciones totales  federales y estatales", "h2");
//            obj.val(contCol + 2, 2, "Reducciones totales  federales y estatales", "h2");
//            obj.val(contCol + 3, 2, "Ejercido total  federal y estatal", "h2");
//            for (int unis = 0; unis < univs.size(); unis++) {
//                
//                consulta = "SELECT t_autorizado_f_e, t_ampliaciones_f_e, t_reducciones_f_e, t_ejercido_f_e FROM eficaciain11 "
//                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
//                        .concat(" AND id_periodo=").concat(periodo);
//                rs = con.Consultar(consulta);
//                if (rs.next()) { //si hay una respuesta de la consulta
//                    obj.val(contCol, unis + 3, rs.getDouble("t_autorizado_f_e"), "datos2");
//                    obj.val(contCol + 1, unis + 3, rs.getDouble("t_ampliaciones_f_e"), "datos2");
//                    obj.val(contCol + 2, unis + 3, rs.getDouble("t_reducciones_f_e"), "datos2");                    
//                    obj.val(contCol + 3, unis + 3, rs.getDouble("t_ejercido_f_e"), "datos2");                    
//                } else { //si no hay una respuesta de la consulta
//                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
//                }
//                unistmp=unis+1;
//            }
                
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 11 " + ex.getMessage());
        }
    }
}
