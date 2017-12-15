/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

import java.sql.SQLException;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 * Actualización 2016: Salvador Zamora Arias
 */
public class ExcelVinculacion extends SabanaCategorias {

    int contCol = 0;
    int c = 0;
    int f = 5;
    ListaEstudios estudios;

    public ExcelVinculacion() {
        super();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Sabana Vinculacion Eliminada :)");
    }

    public ExcelVinculacion(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.f += univs.size();
        this.estudios = new ListaEstudios();
    }

    public HojaCalculo hacerVinculacion() {
        indicador26();
        indicador27();
        indicador28();
        indicador28b();
        indicador29();
        //indicador29b();
        indicador30();
        indicador31();
        indicador31b();
        indicador32();
        con.Desconectar();
        return obj;
    }

    public void indicador26() {
        int totalcol []=new int [14];
        int unistmp= 0;
        c = 21;
        obj.crearHoja("Ind. 26 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS", contCol, contCol + 2, 1, 1, "h1");
            obj.combinarCeldas("Unidades productivas nacionales (Porcentaje)", contCol , contCol + 2, 2, 2, "h1");
            obj.val(contCol , 3, "Organismos Públicos", "h2");
            obj.val(contCol + 1, 3, "Organismos Privados", "h2");
            obj.val(contCol + 2, 3, "Organismos Sociales", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS (Porcentaje)", contCol + 4, contCol + 6, 1, 1, "h1");
            obj.combinarCeldas("Unidades productivas internacionales", contCol + 4, contCol + 6, 2, 2, "h1");
            obj.val(contCol + 4, 3, "Organismos Públicos", "h2");
            obj.val(contCol + 5, 3, "Organismos Privados", "h2");
            obj.val(contCol + 6, 3, "Organismos Sociales", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS (Porcentaje)", contCol + 8, contCol + 9, 1, 1, "h1");
            obj.combinarCeldas("Movilidad nacional", contCol + 8, contCol + 9, 2, 2, "h1");
            obj.val(contCol + 8, 3, "Alumnos con movilidad", "h2");
            obj.val(contCol + 9, 3, "Docentes con movilidad", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS (Porcentaje)", contCol + 11, contCol + 12, 1, 1, "h1");
            obj.combinarCeldas("Movilidad internacional", contCol + 11, contCol + 12, 2, 2, "h1");
            obj.val(contCol + 11, 3, "Alumnos con movilidad", "h2");
            obj.val(contCol + 12, 3, "Docentes con movilidad", "h2");
            double sumporc []= new double[10];
            int countporc[]= new int[10];
            for (int unis = 0; unis < univs.size(); unis++) {
                
                consulta = "SELECT  publicos, privados, sociales, alumnos, docentes FROM vinculacionin26 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    double sumpps=0;
                    sumpps = rs.getDouble("publicos")+rs.getDouble("privados")+rs.getDouble("sociales");
                    if(rs.getDouble("publicos")>0){
                        obj.val(contCol , unis + 4, redondear((rs.getDouble("publicos")/sumpps*100)), "datos2");
                        sumporc[0]+=redondear((rs.getDouble("publicos")/sumpps*100));
                        countporc[0]++;
                    }else{
                      obj.val(contCol , unis + 4, 0, "datos2");
                    }
                    if(rs.getDouble("privados")>0){
                        obj.val(contCol + 1, unis + 4, redondear((rs.getDouble("privados")/sumpps*100)), "datos2");
                        sumporc[1]+=redondear((rs.getDouble("privados")/sumpps*100));
                        countporc[1]++;
                    }else{
                        obj.val(contCol + 1 , unis + 4, 0, "datos2");
                    }
                    if(rs.getDouble("sociales")>0){
                        obj.val(contCol + 2, unis + 4, redondear((rs.getDouble("sociales")/sumpps*100)), "datos2");
                        sumporc[2]+=redondear((rs.getDouble("sociales")/sumpps*100));
                        countporc[2]++;
                    }else{
                        obj.val(contCol + 2 , unis + 4, 0, "datos2");
                    }
                    
                    double summovi=0;
                    summovi=rs.getDouble("alumnos")+rs.getDouble("docentes");
                            
                    if(rs.getDouble("alumnos")>0){
                        obj.val(contCol + 8, unis + 4, redondear((rs.getDouble("alumnos") /summovi*100)), "datos");
                        sumporc[3]+=redondear((rs.getDouble("alumnos")/summovi*100));
                        countporc[3]++;
                    }else{
                        obj.val(contCol + 8, unis + 4, 0, "datos");
                    }
                    if(rs.getDouble("alumnos")>0){
                        obj.val(contCol + 9, unis + 4, redondear((rs.getDouble("docentes")/summovi*100)), "datos");
                        sumporc[4]+=redondear((rs.getDouble("docentes")/summovi*100));
                        countporc[4]++;
                    }else{
                        obj.val(contCol + 9, unis + 4, 0, "datos");
                    }
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol , unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 1, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 2, unis + 4, "NO CONCLUIDO", "error");
                    
                    obj.val(contCol + 8, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 9, unis + 4, "NO CONCLUIDO", "error");
                }
                
                consulta = "SELECT  publicos, privados, sociales, alumnos, docentes FROM vinculacionin26_internacional "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    double sumpps=0;
                    sumpps = rs.getDouble("publicos")+rs.getDouble("privados")+rs.getDouble("sociales");
                     if(rs.getDouble("publicos")>0){
                        obj.val(contCol + 4, unis + 4, redondear((rs.getDouble("publicos")/sumpps*100)), "datos2");
                        sumporc[5]+=redondear((rs.getDouble("publicos")/sumpps*100));
                        countporc[5]++;
                     }else{
                        obj.val(contCol + 4, unis + 4, 0, "datos2");
                     }
                     if(rs.getDouble("privados")>0){
                        obj.val(contCol + 5, unis + 4, redondear((rs.getDouble("privados")/sumpps*100)), "datos2");
                        sumporc[6]+=redondear((rs.getDouble("privados")/sumpps*100));
                        countporc[6]++;
                     }else{
                        obj.val(contCol + 5, unis + 4, 0, "datos2");
                     }
                     if(rs.getDouble("sociales")>0){
                        obj.val(contCol + 6, unis + 4, redondear((rs.getDouble("sociales")/sumpps*100)), "datos2");
                        sumporc[7]+=redondear((rs.getDouble("sociales")/sumpps*100));
                        countporc[7]++;
                     }else{
                        obj.val(contCol + 6, unis + 4, 0, "datos2");
                     }
                    
                    double summovi=0;
                    summovi=rs.getDouble("alumnos")+rs.getDouble("docentes");
                     if(rs.getDouble("alumnos")>0){
                        obj.val(contCol + 11, unis + 4, redondear((rs.getDouble("alumnos") /summovi*100)), "datos");
                        sumporc[8]+=redondear((rs.getDouble("alumnos")/summovi*100));
                        countporc[8]++;
                     }else{
                        obj.val(contCol + 11, unis + 4, 0, "datos2");
                     }
                     if(rs.getDouble("docentes")>0){
                        obj.val(contCol + 12, unis + 4, redondear((rs.getDouble("docentes") /summovi*100)), "datos");
                        sumporc[9]+=redondear((rs.getDouble("docentes")/summovi*100));
                        countporc[9]++;
                     }else{
                        obj.val(contCol + 12, unis + 4, 0, "datos2");
                     }
                    
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol + 4, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 5, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 6, unis + 4, "NO CONCLUIDO", "error");
                    
                    obj.val(contCol + 11, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 12, unis + 4, "NO CONCLUIDO", "error");
                }
                unistmp=unis+1;
            }
            
            //IMPRIME TOTALES
                obj.val(contCol , unistmp + 4, redondear((sumporc[0]/countporc[0])), "total2");
                obj.val(contCol + 1, unistmp + 4, redondear((sumporc[1]/countporc[1])), "total2");
                obj.val(contCol + 2, unistmp + 4, redondear((sumporc[2]/countporc[2])), "total2");
                
                
                obj.val(contCol + 4, unistmp + 4, redondear((sumporc[5]/countporc[5])), "total2");
                obj.val(contCol + 5, unistmp + 4, redondear((sumporc[6]/countporc[6])), "total2");
                obj.val(contCol + 6, unistmp + 4, redondear((sumporc[7]/countporc[7])), "total2");
                
                obj.val(contCol + 8, unistmp + 4, redondear((sumporc[3]/countporc[3])), "total2");
                obj.val(contCol + 9, unistmp + 4, redondear((sumporc[4]/countporc[4])), "total2");
                
                obj.val(contCol + 11, unistmp + 4, redondear((sumporc[8]/countporc[8])), "total2");
                obj.val(contCol + 12, unistmp + 4, redondear((sumporc[9]/countporc[9])), "total2");
            
            
        } catch (SQLException ex) {
            System.err.println("ERROR BD:  Excel, Vinculacion Indicador 26 " + ex.getMessage());
        }
    }

    private void indicador27() {
        double totalcol[]=new double [4];
        int unistmp=0;
        c = 6;
        obj.crearHoja("Ind. 27 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("27. ORGANISMOS VINCULADOS POR TIPO DE ORGANISMO", contCol, contCol + 3, 1, 1, "h1");
//            obj.val(contCol, 2, "Presupuesto total autorizado federal y Estatal", "h2");
            obj.val(contCol , 2, "IPC1", "h2");
            obj.val(contCol + 1, 2, "IPC2", "h2");
            obj.val(contCol + 2, 2, "IPC3", "h2");
            double sumpor[] = new double[4];
            int countpor[] = new int[4];
            for (int unis = 0; unis < univs.size(); unis++) {
                double autorizado=0;
                consulta = "SELECT autorizado FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    autorizado = rs.getDouble("autorizado");
//                    obj.val(contCol, unis + 3, rs.getDouble("autorizado"), "datos2");
//                } else { //si no hay una respuesta de la consulta
//                    obj.val(contCol, unis + 3, "NO CONCLUIDO", "error");
                }
                consulta = "SELECT rec_servicios, rec_colegiaturas, rec_diferentes FROM vinculacionin27 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    if(rs.getDouble("rec_servicios")>0){
                        obj.val(contCol , unis + 3, redondear((rs.getDouble("rec_servicios")   /autorizado*100)), "datos2");
                        sumpor[0]+=redondear((rs.getDouble("rec_servicios")   /autorizado*100));
                        countpor[0]++;
                    }else{
                        obj.val(contCol , unis + 3, 0, "datos2");
                    }
                    if(rs.getDouble("rec_colegiaturas")>0){
                        obj.val(contCol + 1, unis + 3, redondear((rs.getDouble("rec_colegiaturas")/autorizado*100)), "datos2");
                        sumpor[1]+=redondear((rs.getDouble("rec_colegiaturas")   /autorizado*100));
                        countpor[1]++;
                    }else{
                        obj.val(contCol + 1, unis + 3, 0, "datos2");
                    }
                    if(rs.getDouble("rec_diferentes")>0){
                        obj.val(contCol + 2, unis + 3, redondear((rs.getDouble("rec_diferentes")  /autorizado*100)), "datos2");
                        sumpor[2]+=redondear((rs.getDouble("rec_diferentes")   /autorizado*100));
                        countpor[2]++;
                    }else{
                        obj.val(contCol + 2, unis + 3, 0, "datos2");
                    }
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 1, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            //IMPRIME PROMEDIO TOTAL
            obj.val(contCol, unistmp + 3, redondear(sumpor[0]/countpor[0]), "total2");
            obj.val(contCol + 1, unistmp + 3, redondear(sumpor[1]/countpor[1]), "total2");
            obj.val(contCol + 2, unistmp + 3, redondear(sumpor[2]/countpor[2]), "total2");
              
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Excel, Vinculacion Indicador 27 " + ex.getMessage());
        }
    }

    private void indicador28() {
        int totalcol []=new int [3];int unistmp=0;
        c = 2 + 3 * estudios.size();
        obj.crearHoja("Ind. 28 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("28. SERVICIOS Y ESTUDIOS TEGNOLÓGICOS SEGÚN TIPO Y SECTOR", contCol, contCol + (3 * estudios.size()) - 1, 1, 1, "h1");
            for (int est = 0; est < estudios.size(); est++) {
                for (int reinarray = 0; reinarray < 3; reinarray++) {
                    totalcol[reinarray]=0;
                }
                obj.combinarCeldas(estudios.getNombre(est), contCol, contCol + 2, 2, 2, "h2");
                obj.val(contCol, 3, "Públicos", "h2");
                obj.val(contCol + 1, 3, "Privados", "h2");
                obj.val(contCol + 2, 3, "Sociales", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT SUM(org_publicos) as 'org_publicos', SUM(org_privados) as 'org_privados', SUM(org_sociales) AS 'org_sociales' FROM vinculacionin28 "
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_estudio=").concat(String.valueOf(estudios.getId(est)))
                            .concat(" AND id_periodo=").concat(periodo);
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 4, rs.getInt("org_publicos"), "datos");
                        obj.val(contCol + 1, unis + 4, rs.getInt("org_privados"), "datos");
                        obj.val(contCol + 2, unis + 4, rs.getInt("org_sociales"), "datos");
                        totalcol[0]+=rs.getInt("org_publicos");
                        totalcol[1]+=rs.getInt("org_privados");
                        totalcol[2]+=rs.getInt("org_sociales");
                    } else { //si no hay una respuesta de la consulta
                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                  //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 3; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
                contCol += 3;
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 28 " + ex.getMessage());
        }
    }

    private void indicador28b() {
         double totalcol []=new double [3];int unistmp=0;
        c = 2 + 3 * estudios.size();
        obj.crearHoja("Ind. 28 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("28. INGRESOS PROPIOS POR SERVICIOS Y ESTUDIOS TEGNOLÓGICOS SEGÚN TIPO Y SECTOR", contCol, contCol + (3 * estudios.size()) - 1, 1, 1, "h1");
            for (int est = 0; est < estudios.size(); est++) {
                for (int reinarray = 0; reinarray < 3; reinarray++) {
                 totalcol[reinarray]=0;
                }
                obj.combinarCeldas(estudios.getNombre(est), contCol, contCol + 2, 2, 2, "h2");
                obj.val(contCol, 3, "Públicos", "h2");
                obj.val(contCol + 1, 3, "Privados", "h2");
                obj.val(contCol + 2, 3, "Sociales", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT ing_publicos, ing_privados, ing_sociales FROM vinculacionin28 "
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_estudio=").concat(String.valueOf(estudios.getId(est)))
                            .concat(" AND id_periodo=").concat(periodo);
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 4, rs.getDouble("ing_publicos"), "datos2");
                        obj.val(contCol + 1, unis + 4, rs.getDouble("ing_privados"), "datos2");
                        obj.val(contCol + 2, unis + 4, rs.getDouble("ing_sociales"), "datos2");
                         totalcol[0]+=rs.getDouble("ing_publicos");
                        totalcol[1]+=rs.getDouble("ing_privados");
                        totalcol[2]+=rs.getDouble("ing_sociales");
                    } else { //si no hay una respuesta de la consulta
                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                  //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 3; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total2");
                    }
                contCol += 3;
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 28 " + ex.getMessage());
        } finally {
            estudios = null;
        }
    }

    private void indicador29() {
        int totalcol[]=new int[4];
        int unistmp=0;
        boolean tieneNiv = false;
        this.niveles = new ListaNiveles(true);
        c = 2 + 4 * niveles.size();
        obj.crearHoja("Ind. 29 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("29. EGRESADOS QUE ASISTEN A CURSOS SEGÚN TIPO", contCol, contCol + (3 * niveles.size()) - 1, 1, 1, "h1");
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                  for (int reinarray = 0; reinarray < 4; reinarray++) {
                    totalcol[reinarray]=0;
                   }
                obj.combinarCeldas(niveles.getAbreviatura(nivel), contCol, contCol + 2, 2, 2, "h1");
                obj.val(contCol, 3, "EEC1", "h2");
                obj.val(contCol + 1, 3, "EEC2", "h2");
                obj.val(contCol + 2, 3, "EEC3", "h2");
                double sumporc [] = new double[3];
                int countporc [] = new int[3];
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (nivel != 0) {//verificar que la universidad tenga el nivel 
                        if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                            tieneNiv = true;
                        } else {
                            tieneNiv = false;
                        }
                    } else {//no se verifica que se tenga el nivel
                        tieneNiv = true;
                    }
                    if (tieneNiv) {
                        consulta = "SELECT capacitacion, actualizacion, desarrollo_p FROM vinculacionin29 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            double total = (rs.getDouble("capacitacion")+rs.getDouble("actualizacion")+rs.getDouble("desarrollo_p"));
                            if(rs.getDouble("capacitacion")>0){
                                obj.val(contCol, unis + 4, redondear((rs.getDouble("capacitacion")/total*100)), "datos2");
                                sumporc[0]+=redondear((rs.getDouble("capacitacion")/total*100));
                                countporc[0]++;
                            }else{
                                obj.val(contCol, unis + 4, 0, "datos2");
                            }  
                            if(rs.getDouble("actualizacion")>0){
                                obj.val(contCol + 1, unis + 4, redondear((rs.getDouble("actualizacion")/total*100)), "datos2");
                                sumporc[1]+=redondear((rs.getDouble("actualizacion")/total*100));
                                countporc[1]++;
                            }else{
                                obj.val(contCol + 1, unis + 4, 0, "datos2");
                            }  
                            if(rs.getDouble("desarrollo_p")>0){
                                obj.val(contCol + 2, unis + 4, redondear((rs.getDouble("desarrollo_p")/total*100)), "datos2");
                                sumporc[2]+=redondear((rs.getDouble("desarrollo_p")/total*100));
                                countporc[2]++;
                            }else{
                                obj.val(contCol + 2, unis + 4, 0, "datos2");
                            }  
                                
//                            obj.val(contCol + 3, unis + 4, (rs.getInt("capacitacion")+rs.getInt("actualizacion")+rs.getInt("desarrollo_p")), "datos");
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 2, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }

                obj.val(contCol, unistmp + 4, redondear((sumporc[0]/countporc[0])), "total2");
                obj.val(contCol + 1, unistmp + 4, redondear((sumporc[1]/countporc[1])), "total2");
                obj.val(contCol + 2, unistmp + 4, redondear((sumporc[2]/countporc[2])), "total2");
                
                contCol += 3;
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Excel, Vinculacion Indicador 29 " + ex.getMessage());
        }
    }

    private void indicador30() {
        int totalcol []=new int [8];
        int unistmp=0;
        c = 10;
        obj.crearHoja("Ind. 30 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("30. CURSOS EN EDUCACIÓN CONTINUA POR DEMANDA SEGÚN TIPO", contCol, contCol + 1, 1, 1, "h1");
            obj.combinarCeldas("TCD1", contCol, contCol , 2, 3, "h1");
            obj.combinarCeldas("TCD2", contCol + 1, contCol + 1, 2, 3, "h1");
            
            double sumporc []= new double[2];
            int countporc []= new int[2];
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT d_capacitacion, d_actualizacion, d_desarrollo, "
                        .concat(" o_capacitacion, o_actualizacion, o_desarrollo FROM vinculacionin30 ")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    double demanda=0, oferta=0, suma=0;
                    demanda=(rs.getDouble("d_capacitacion")+rs.getDouble("d_actualizacion")+rs.getDouble("d_desarrollo"));
                    oferta =(rs.getDouble("o_capacitacion")+rs.getDouble("o_actualizacion")+rs.getDouble("o_desarrollo"));
                    suma=demanda+oferta;
                    if(demanda>0){
                        obj.val(contCol , unis + 4, redondear((demanda/suma*100)), "datos2");
                        sumporc[0]+=redondear((demanda/suma*100));
                        countporc[0]++;
                    }else{
                        obj.val(contCol, unis + 4, 0, "datos");
                    }
                    if(oferta>0){
                        obj.val(contCol + 1, unis + 4, redondear((oferta/suma*100)), "datos2");
                        sumporc[1]+=redondear((oferta/suma*100));
                        countporc[1]++;
                    }else{
                        obj.val(contCol + 1, unis + 4, 0, "datos");
                    }
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
//                    obj.combinarCeldas("NO CONCLUIDO", contCol + 4, contCol + 7, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
             //IMPRIME LOS PROMEDIOS
                obj.val(contCol, unistmp + 4, redondear((sumporc[0]/countporc[0])), "total2");
                obj.val(contCol + 1, unistmp + 4, redondear((sumporc[1]/countporc[1])), "total2");
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Excel Vinculacion Indicador 30 " + ex.getMessage());
        }
    }
    
    
    private void indicador31() {
        int unistmp=0;
        int ra, rb, rc, rd, re, rf, rg;
        c = 2 + 21;
        obj.crearHoja("Ind. 31 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 8250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            obj.combinarCeldas("31. TASA DE ALUMNOS SATISFECHOS DE EDUCACIÓN CONTINUA", contCol, contCol + 1, 1, 1, "h1");
            obj.combinarCeldas("DISTRIBUCIÓN PORCENTUAL", contCol, contCol + 1, 2, 2, "h1");
            obj.val(contCol, 3, "MS", "h2");
            obj.val(contCol + 1, 3, "TES", "h2");
            double sumporc[] = new double[2];
            int countporc[] = new int[2];
            for (int unis = 0; unis < univs.size(); unis++) {
                if (unis == 0) {
                    consulta = "SELECT COUNT(id_pregunta) as numEncuestas FROM encuesta_preguntas WHERE id_encuesta=("
                            .concat(" SELECT id_encuesta FROM encuestas WHERE indicador=31)");
                    rs = con.Consultar(consulta);
                   
                }
                consulta = "SELECT SUM(r_a) as r_a, SUM(r_b) as r_b, SUM(r_c) as r_c,"
                        .concat(" SUM(r_d) as r_d, SUM(r_e) as r_e, SUM(r_f) as r_f, SUM(r_g) as r_g")
                        .concat(" FROM vinculacionin31 WHERE id_pregunta IN(")
                        .concat(" SELECT id_pregunta FROM encuesta_preguntas WHERE id_encuesta=(")
                        .concat(" SELECT id_encuesta FROM encuestas WHERE indicador=31))")
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
                    double total=ra + rb + rc + rd + re + rf + rg;
                    if(ra>0){
                        obj.val(contCol , unis + 4, redondear((ra/total*100)), "datos2");
                        sumporc[0]+=redondear((ra/total* 100));
                        countporc[0]++;
                    }else{
                        obj.val(contCol , unis + 4, 0, "datos2");
                    }
                    if((ra+rb)>0){
                        obj.val(contCol + 1, unis + 4, redondear(((ra+rb)/total*100)), "datos2");
                        sumporc[1]+=redondear(((ra+rb)/total* 100));
                        countporc[1]++;
                    }else{
                        obj.val(contCol + 1, unis + 4, 0, "datos2");
                    }
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
                }
               unistmp=unis+1; 
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                obj.val(contCol , unistmp + 4, redondear(sumporc[0]/countporc[0]), "total2");
                obj.val(contCol + 1, unistmp + 4, redondear(sumporc[1]/countporc[1]), "total2");
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel, indicador 31" + ex.getMessage());
        }
    }

    private void indicador31b() {
        double totalcol0=0;
        int unistmp=0;
        int ra, rb, rc, rd, re, rf, rg, pregsNivel, numEncuestas = 0;
        double escala5, escala10, totMult, totSum;

        ListaPreguntas preguntas = new ListaPreguntas("31");

        c = 2 + niveles.size() + preguntas.size();
        obj.crearHoja("Ind. 31 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS

            pregsNivel = preguntas.size();
            if (pregsNivel != 0) {
                obj.combinarCeldas("31. TASA DE ALUMNOS SATISFECHOS DE EDUCACIÓN CONTINUA ", contCol, (contCol + pregsNivel - 1), 1, 1, "h1");
            }
            for (int p = 0; p < preguntas.size(); p++) {
                obj.val(contCol, 2, preguntas.getNombrePregunta(p), "h2");
                      totalcol0=0;  
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (unis == 0) {
                        consulta = "SELECT COUNT(id_pregunta) as numEncuestas FROM encuesta_preguntas WHERE id_encuesta=("
                                .concat(" SELECT id_encuesta FROM encuestas WHERE indicador=31)");
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            numEncuestas = rs.getInt("numEncuestas");
                        } else {
                            numEncuestas = 0;
                        }
                    }
                    consulta = "SELECT SUM(r_a) as r_a, SUM(r_b) as r_b, SUM(r_c) as r_c,"
                            .concat(" SUM(r_d) as r_d, SUM(r_e) as r_e, SUM(r_f) as r_f, SUM(r_g) as r_g")
                            .concat(" FROM vinculacionin31 WHERE id_pregunta = ")
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
                    unistmp=unis+1;
                }//for unis
                    obj.val(contCol, unistmp + 3, totalcol0, "total2");
                contCol++;
            }//for preguntas
            contCol++;
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 31 b " + ex);
        }
    }

    private void indicador32() {
        int totalcol[] = new int [2];
        int unistmp=0;
        this.niveles = new ListaNiveles(false);
        c = 2 + 4 * niveles.size();
        obj.crearHoja("Ind. 32 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("32. EGRESADOS COLOCADOS", contCol, contCol + (2 * niveles.size()) - 1, 1, 1, "h1");
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol[0]=0;totalcol[1]=0;
                obj.combinarCeldas(niveles.getAbreviatura(nivel), contCol, contCol + 1, 2, 2, "h1");
                obj.val(contCol, 3, "Egresados Colocados en plazas contactadas por el área de bolsa de trabajo de la UT", "h2");
                obj.val(contCol + 1, 3, "Plazas contactadas por el área de bolsa de trabajo de la UT", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                        consulta = "SELECT egresados_colocados, plazas FROM vinculacionin32 "
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            obj.val(contCol, unis + 4, rs.getInt("egresados_colocados"), "datos");
                            obj.val(contCol + 1, unis + 4, rs.getInt("plazas"), "datos");
                            totalcol[0]+=rs.getInt("egresados_colocados");
                            totalcol[1]+=rs.getInt("plazas");
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 1, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                 //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 2; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
                contCol += 2;
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 32 " + ex.getMessage());
        }
    }
}