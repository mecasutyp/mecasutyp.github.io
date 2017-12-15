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
public class SabanaVinculacion extends SabanaCategorias {

    int contCol = 0;
    int c = 0;
    int f = 5;
    ListaEstudios estudios;

    public SabanaVinculacion() {
        super();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Sabana Vinculacion Eliminada :)");
    }

    public SabanaVinculacion(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
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
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS", contCol, contCol + 1, 1, 1, "h1");
            obj.combinarCeldas("Organismos nacionales", contCol, contCol + 1, 2, 2, "h1");
            obj.val(contCol, 3, "Número de convenios firmados acumulados al ciclo escolar", "h2");
            obj.val(contCol + 1, 3, "Número de convenidos firmados acumulados con instituciones de educación superior en el ciclo escolar", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS", contCol + 3, contCol + 5, 1, 1, "h1");
            obj.combinarCeldas("Unidades productivas nacionales", contCol + 3, contCol + 5, 2, 2, "h1");
            obj.val(contCol + 3, 3, "Organismos Públicos", "h2");
            obj.val(contCol + 4, 3, "Organismos Privados", "h2");
            obj.val(contCol + 5, 3, "Organismos Sociales", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS", contCol + 7, contCol + 8, 1, 1, "h1");
            obj.combinarCeldas("Organismos internacionales", contCol + 7, contCol + 8, 2, 2, "h1");
            obj.val(contCol + 7, 3, "Número de convenios firmados acumulados al ciclo escolar", "h2");
            obj.val(contCol + 8, 3, "Número de convenidos firmados acumulados con instituciones de educación superior en el ciclo escolar", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS", contCol + 10, contCol + 12, 1, 1, "h1");
            obj.combinarCeldas("Unidades productivas internacionales", contCol + 10, contCol + 12, 2, 2, "h1");
            obj.val(contCol + 10, 3, "Organismos Públicos", "h2");
            obj.val(contCol + 11, 3, "Organismos Privados", "h2");
            obj.val(contCol + 12, 3, "Organismos Sociales", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS", contCol + 14, contCol + 15, 1, 1, "h1");
            obj.combinarCeldas("Movilidad nacional", contCol + 14, contCol + 15, 2, 2, "h1");
            obj.val(contCol + 14, 3, "Alumnos con movilidad", "h2");
            obj.val(contCol + 15, 3, "Docentes con movilidad", "h2");
            
            obj.combinarCeldas("26. ORGANISMOS VINCULADOS", contCol + 17, contCol + 18, 1, 1, "h1");
            obj.combinarCeldas("Movilidad internacional", contCol + 17, contCol + 18, 2, 2, "h1");
            obj.val(contCol + 17, 3, "Alumnos con movilidad", "h2");
            obj.val(contCol + 18, 3, "Docentes con movilidad", "h2");
            
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT conv_acum, conv_acum_sup, publicos, privados, sociales, alumnos, docentes FROM vinculacionin26 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 4, rs.getInt("conv_acum"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("conv_acum_sup"), "datos");
                    
                    obj.val(contCol + 3, unis + 4, rs.getInt("publicos"), "datos");
                    obj.val(contCol + 4, unis + 4, rs.getInt("privados"), "datos");
                    obj.val(contCol + 5, unis + 4, rs.getInt("sociales"), "datos");
                    
                    obj.val(contCol + 14, unis + 4, rs.getInt("alumnos"), "datos");
                    obj.val(contCol + 15, unis + 4, rs.getInt("docentes"), "datos");
                    totalcol[0]+=rs.getInt("conv_acum");
                    totalcol[1]+=rs.getInt("conv_acum_sup");
                    totalcol[2]+=rs.getInt("publicos");
                    totalcol[3]+=rs.getInt("privados");
                    totalcol[4]+=rs.getInt("sociales");
                    totalcol[5]+=rs.getInt("alumnos");
                    totalcol[6]+=rs.getInt("docentes");
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 1, unis + 4, "NO CONCLUIDO", "error");
                    
                    obj.val(contCol + 3, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 4, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 5, unis + 4, "NO CONCLUIDO", "error");
                    
                    obj.val(contCol + 14, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 15, unis + 4, "NO CONCLUIDO", "error");
                }
                
                consulta = "SELECT conv_acum, conv_acum_sup, publicos, privados, sociales, alumnos, docentes FROM vinculacionin26_internacional "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol + 7, unis + 4, rs.getInt("conv_acum"), "datos");
                    obj.val(contCol + 8, unis + 4, rs.getInt("conv_acum_sup"), "datos");
                    
                    obj.val(contCol + 10, unis + 4, rs.getInt("publicos"), "datos");
                    obj.val(contCol + 11, unis + 4, rs.getInt("privados"), "datos");
                    obj.val(contCol + 12, unis + 4, rs.getInt("sociales"), "datos");
                    
                    obj.val(contCol + 17, unis + 4, rs.getInt("alumnos"), "datos");
                    obj.val(contCol + 18, unis + 4, rs.getInt("docentes"), "datos");
                    
                    totalcol[7]+=rs.getInt("conv_acum");
                    totalcol[8]+=rs.getInt("conv_acum_sup");
                    totalcol[9]+=rs.getInt("publicos");
                    totalcol[10]+=rs.getInt("privados");
                    totalcol[11]+=rs.getInt("sociales");
                    totalcol[12]+=rs.getInt("alumnos");
                    totalcol[13]+=rs.getInt("docentes");
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol + 7, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 8, unis + 4, "NO CONCLUIDO", "error");
                    
                    obj.val(contCol + 10, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 11, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 12, unis + 4, "NO CONCLUIDO", "error");
                    
                    obj.val(contCol + 17, unis + 4, "NO CONCLUIDO", "error");
                    obj.val(contCol + 18, unis + 4, "NO CONCLUIDO", "error");
                }
                unistmp=unis+1;
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                     obj.val(contCol, unistmp + 4, totalcol[0], "total");
                     obj.val(contCol + 1, unistmp + 4, totalcol[1], "total");
                     obj.val(contCol + 3, unistmp + 4, totalcol[2], "total");
                     obj.val(contCol + 4, unistmp + 4, totalcol[3], "total");
                     obj.val(contCol + 5, unistmp + 4, totalcol[4], "total");
                     obj.val(contCol + 14, unistmp + 4, totalcol[5], "total");
                     obj.val(contCol + 15, unistmp + 4, totalcol[6], "total");
                     
                       obj.val(contCol + 7, unistmp + 4, totalcol[7], "total");
                     obj.val(contCol + 8, unistmp + 4, totalcol[8], "total");
                     obj.val(contCol + 10, unistmp + 4, totalcol[9], "total");
                     obj.val(contCol + 11, unistmp + 4, totalcol[10], "total");
                     obj.val(contCol + 12, unistmp + 4, totalcol[11], "total");
                     obj.val(contCol + 17, unistmp + 4, totalcol[12], "total");
                     obj.val(contCol + 18, unistmp + 4, totalcol[13], "total");
                     
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 26 " + ex.getMessage());
        }
    }

    private void indicador27() {
        double totalcol[]=new double [4];
        double nummayor0[]=new double [4];
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
            obj.val(contCol, 2, "Presupuesto total autorizado federal y Estatal", "h2");
            obj.val(contCol + 1, 2, "Recursos captados por servicios y estudios tecnológicos", "h2");
            obj.val(contCol + 2, 2, "Recursos captados por colegiaturas y servicios escolares", "h2");
            obj.val(contCol + 3, 2, "Recursos captados por otros servicios proporcionados por la universidad diferentes a los anteriores", "h2");
            nummayor0[0]=0;
            nummayor0[1]=0;
            nummayor0[2]=0;
            nummayor0[3]=0;
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT autorizado FROM eficaciain11 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getDouble("autorizado"), "datos2");
                    totalcol[0]+=rs.getDouble("autorizado");
                    if(rs.getDouble("autorizado")>0){
                        nummayor0[0]++;
                    }
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, "NO CONCLUIDO", "error");
                }
                consulta = "SELECT rec_servicios, rec_colegiaturas, rec_diferentes FROM vinculacionin27 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol + 1, unis + 3, rs.getDouble("rec_servicios"), "datos2");
                    obj.val(contCol + 2, unis + 3, rs.getDouble("rec_colegiaturas"), "datos2");
                    obj.val(contCol + 3, unis + 3, rs.getDouble("rec_diferentes"), "datos2");
                    totalcol[1]+=rs.getDouble("rec_servicios");
                    totalcol[2]+=rs.getDouble("rec_colegiaturas");
                    totalcol[3]+=rs.getDouble("rec_diferentes");
                    if(rs.getDouble("rec_servicios")>0){
                        nummayor0[1]++;
                    }
                    if(rs.getDouble("rec_colegiaturas")>0){
                        nummayor0[2]++;
                    }
                    if(rs.getDouble("rec_diferentes")>0){
                        nummayor0[3]++;
                    }
                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 1, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
              //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 4; tco++) {
                         obj.val(contCol+tco, unistmp + 3, (totalcol[tco]/nummayor0[tco]), "total2");
                    }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 27 " + ex.getMessage());
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
            obj.combinarCeldas("29. EGRESADOS QUE ASISTEN A CURSOS SEGÚN TIPO", contCol, contCol + (4 * niveles.size()) - 1, 1, 1, "h1");
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                  for (int reinarray = 0; reinarray < 4; reinarray++) {
                    totalcol[reinarray]=0;
                   }
                obj.combinarCeldas(niveles.getAbreviatura(nivel), contCol, contCol + 3, 2, 2, "h1");
                obj.val(contCol, 3, "Capacitación", "h2");
                obj.val(contCol + 1, 3, "Actualización", "h2");
                obj.val(contCol + 2, 3, "Desarrollo Profesional", "h2");
                obj.val(contCol + 3, 3, "Total Egresados", "h2");
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
                            obj.val(contCol, unis + 4, rs.getInt("capacitacion"), "datos");
                            obj.val(contCol + 1, unis + 4, rs.getInt("actualizacion"), "datos");
                            obj.val(contCol + 2, unis + 4, rs.getInt("desarrollo_p"), "datos");
                            obj.val(contCol + 3, unis + 4, (rs.getInt("capacitacion")+rs.getInt("actualizacion")+rs.getInt("desarrollo_p")), "datos");
                            totalcol[0]+=rs.getInt("capacitacion");
                            totalcol[1]+=rs.getInt("actualizacion");
                            totalcol[2]+=rs.getInt("desarrollo_p");
                            totalcol[3]+=(rs.getInt("capacitacion")+rs.getInt("actualizacion")+rs.getInt("desarrollo_p"));
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 4, unis + 4, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 3, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
                 //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 4; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
                contCol += 4;
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 29 " + ex.getMessage());
        }
    }

//    private void indicador29b() {
//        int totalcol=0;
//        int unistmp=0;
//        this.niveles = new ListaNiveles(false);
//        c = 2 + niveles.size();
//        obj.crearHoja("Ind. 29 b ", f, c);
//        obj.setAnchoColumna(1, 17500);
//        int NoColumnas = c;
//        for (int i = 2; i <= NoColumnas; i++) {
//            obj.setAnchoColumna(i, 4250);
//        }
//        columnasPrincipales(2);
//        contCol = 2;
//        try {
//            //ENCABEZADOS
//            obj.combinarCeldas("29. EGRESADOS ACUMULADOS", contCol, contCol + niveles.size() - 1, 1, 1, "h1");
//            for (int nivel = 0; nivel < niveles.size(); nivel++) {
//                totalcol=0;
//                obj.val(contCol, 2, niveles.getAbreviatura(nivel), "h1");
//                for (int unis = 0; unis < univs.size(); unis++) {
//                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
//                        consulta = "SELECT total_egresados FROM vinculacionin29 "
//                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
//                                .concat(" AND id_nivel=").concat(String.valueOf(niveles.getId(nivel)))
//                                .concat(" AND id_periodo=").concat(periodo);
//                        rs = con.Consultar(consulta);
//                        if (rs.next()) { //si hay una respuesta de la consulta
//                            obj.val(contCol, unis + 3, rs.getInt("total_egresados"), "datos");
//                            totalcol+=rs.getInt("total_egresados");
//                        } else { //si no hay una respuesta de la consulta
//                            obj.val(contCol, unis + 3, "NO CONCLUIDO", "error");
//                        }
//                    } else {
//                        obj.val(contCol, unis + 3, "NO TIENE EL NIVEL", "error");
//                    }
//                    unistmp=unis+1;
//                }
//                obj.val(contCol, unistmp + 3, totalcol, "h1");
//                contCol += 1;
//            }
//        } catch (SQLException ex) {
//            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 29 " + ex.getMessage());
//        }
//    }

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
            obj.combinarCeldas("30. CURSOS EN EDUCACIÓN CONTINUA POR DEMANDA SEGÚN TIPO", contCol, contCol + 7, 1, 1, "h1");
            obj.combinarCeldas("CURSOS POR DEMANDA", contCol, contCol + 3, 2, 2, "h1");
            obj.combinarCeldas("CURSOS POR OFERTA DE LA UT", contCol + 4, contCol + 7, 2, 2, "h1");
            obj.val(contCol, 3, "Capacitación", "h2");
            obj.val(contCol + 1, 3, "Actualización", "h2");
            obj.val(contCol + 2, 3, "Desarrollo Profesional", "h2");
            obj.val(contCol + 3, 3, "Total", "h2");
            obj.val(contCol + 4, 3, "Capacitación", "h2");
            obj.val(contCol + 5, 3, "Actualización", "h2");
            obj.val(contCol + 6, 3, "Desarrollo Profesional", "h2");
            obj.val(contCol + 7, 3, "Total", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT d_capacitacion, d_actualizacion, d_desarrollo, "
                        .concat(" o_capacitacion, o_actualizacion, o_desarrollo FROM vinculacionin30 ")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 4, rs.getInt("d_capacitacion"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("d_actualizacion"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("d_desarrollo"), "datos");
                    obj.val(contCol + 3, unis + 4, (rs.getInt("d_capacitacion")+rs.getInt("d_actualizacion")+rs.getInt("d_desarrollo")), "datos");
                    obj.val(contCol + 4, unis + 4, rs.getInt("o_capacitacion"), "datos");
                    obj.val(contCol + 5, unis + 4, rs.getInt("o_actualizacion"), "datos");
                    obj.val(contCol + 6, unis + 4, rs.getInt("o_desarrollo"), "datos");
                    obj.val(contCol + 7, unis + 4, (rs.getInt("o_capacitacion")+rs.getInt("o_actualizacion")+rs.getInt("o_desarrollo")), "datos");
                    totalcol[0]+=rs.getInt("d_capacitacion");
                    totalcol[1]+=rs.getInt("d_actualizacion");
                    totalcol[2]+=rs.getInt("d_desarrollo");
                    totalcol[3]+=(rs.getInt("d_capacitacion")+rs.getInt("d_actualizacion")+rs.getInt("d_desarrollo"));
                    totalcol[4]+=rs.getInt("o_capacitacion");
                    totalcol[5]+=rs.getInt("o_actualizacion");
                    totalcol[6]+=rs.getInt("o_desarrollo");
                    totalcol[7]+=(rs.getInt("o_capacitacion")+rs.getInt("o_actualizacion")+rs.getInt("o_desarrollo"));
                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 4, contCol + 7, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 8; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 30 " + ex.getMessage());
        }
    }

    private void indicador31() {
        double totalcol[]=new double[20];
        int unistmp=0;
        int ra, rb, rc, rd, re, rf, rg, numEncuestas = 0;
        double escala5, escala10, totMult, totSum;
        c = 2 + 21;
        obj.crearHoja("Ind. 31 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            for (int y = 0; y <= 14; y++) {
                obj.setAnchoColumna(contCol + y, 1900);
            }
            obj.combinarCeldas("31. TASA DE ALUMNOS SATISFECHOS DE EDUCACIÓN CONTINUA", contCol, contCol + 19, 1, 1, "h1");
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
                    totalcol[0]+=ra;
                    totalcol[1]+=rb;
                    totalcol[2]+=rc;
                    totalcol[3]+=rd;
                    totalcol[4]+=re;
                    totalcol[5]+=rf;
                    totalcol[6]+=rg;
                    totalcol[7]+=ra + rb + rc + rd + re + rf + rg;
                    totalcol[8]+=ra * 5;
                    totalcol[9]+=rb * 4;
                    totalcol[10]+= rc * 3;
                    totalcol[11]+= rd * 2;
                    totalcol[12]+= re * 1;
                    
                    totMult = (ra * 5) + (rb * 4) + (rc * 3) + (rd * 2) + (re * 1);
                    totSum = ra + rb + rc + rd + re;
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
                    totalcol[13]+=totMult;
                    totalcol[14]+=totSum;
                    totalcol[15]+=escala5;
                    totalcol[16]+=escala10;
                    totalcol[17]+=ra+rb;
                    if (numEncuestas != 0) {
                        obj.val(contCol + 18, unis + 4, (ra + rb) / numEncuestas, "datos");
                        obj.val(contCol + 19, unis + 4, (ra + rb + rc + rd + re + rf + rg) / numEncuestas, "datos");
                        totalcol[18]+=(ra + rb) / numEncuestas;
                        totalcol[19]+=(ra + rb + rc + rd + re + rf + rg) / numEncuestas;
                    } else {
                        obj.val(contCol + 18, unis + 4, "0", "datos");
                        obj.val(contCol + 19, unis + 4, "0", "datos");
                    }
                } else {
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 7, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 8, contCol + 14, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 15, contCol + 19, unis + 4, unis + 4, "error");
                }
               unistmp=unis+1; 
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 20; tco++) {
                        if(tco==15 || tco==16){
                            obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total2");
                        }else{
                            obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                        }
                    }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 7 " + ex.getMessage());
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