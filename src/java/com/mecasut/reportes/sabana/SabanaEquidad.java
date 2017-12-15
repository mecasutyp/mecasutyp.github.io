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
public class SabanaEquidad extends SabanaCategorias {

    int contCol = 0;
    int c = 0;
    int f = 5;
    ListaApoyos apoyos;

    public SabanaEquidad() {
        super();
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Sabana Equidad Eliminada :)");
    }

    public SabanaEquidad(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.f += univs.size();
        this.apoyos = new ListaApoyos();
    }

    public HojaCalculo hacerEquidad() {
        indicador33();
        indicador34();
        indicador35();
        indicador36();
        con.Desconectar();
        return obj;
    }

    private void indicador33() {
        int totalcol[]=new int [2];
        double totalcold = 0;
        int unistmp=0;
        c = 5;
        obj.crearHoja("Ind. 33 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("33. COBERTURA", contCol, contCol + 2, 1, 1, "h1");
            obj.val(contCol, 2, "Alumnos de nuevo ingreso en la universidad", "h2");
            obj.val(contCol + 1, 2, "Egresados de bachillerato en el estado", "h2");
            obj.val(contCol + 2, 2, "%", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                obj.val(contCol, unis + 3, univs.getNuevoIngreso(unis), "datos");
                totalcol[0]+=univs.getNuevoIngreso(unis);
                consulta = "SELECT egresados FROM egresados_estado WHERE id_estado="
                        .concat("(SELECT id_estado FROM domicilios_universidad WHERE id_universidad=")
                        .concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo)
                        .concat(")")
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol + 1, unis + 3, rs.getInt("egresados"), "datos");
                    if(rs.getInt("egresados")>0){
                        obj.val(contCol + 2, unis + 3, (double)((double)univs.getNuevoIngreso(unis)/(double)rs.getInt("egresados"))*100, "datos2");
                        totalcold+=(double)((double)univs.getNuevoIngreso(unis)/(double)rs.getInt("egresados"))*100;
                    }else{
                        obj.val(contCol + 2, unis + 3, 0, "datos2");
                    }
                    totalcol[1]+=rs.getInt("egresados");
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol + 1, unis + 3, "DATO NO REGISTRADO", "error");
                    obj.val(contCol + 2, unis + 3, 0, "datos");
                }
                unistmp=unis+1;
            }
              //IMPRIME LOS TOTALES DE COLUMNA
            int tco;
                    for (tco = 0; tco < 2; tco++) {
                         obj.val(contCol+tco, unistmp + 3, totalcol[tco], "total");
                    }
                    obj.val(contCol+tco, unistmp + 3, totalcold, "total");
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 33 " + ex.getMessage());
        }
    }

    private void indicador34() {
        int totalcol[]=new int [4];
        int unistmp=0;
        c = 6;
        obj.crearHoja("Ind. 34 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("34. INGRESO Y REINGRESO SEGÚN SEXO ", contCol, contCol + 3, 1, 1, "h1");
            obj.combinarCeldas("INGRESO", contCol, contCol + 1, 2, 2, "h1");
            obj.combinarCeldas("REINGRESO", contCol + 2, contCol + 3, 2, 2, "h1");
            obj.val(contCol, 3, "Hombres", "h2");
            obj.val(contCol + 1, 3, "Mujeres", "h2");
            obj.val(contCol + 2, 3, "Hombres", "h2");
            obj.val(contCol + 3, 3, "Mujeres", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT SUM(ingreso_hombres) as ingreso_hombres, SUM(ingreso_mujeres) as ingreso_mujeres, "
                        .concat(" SUM(reingreso_hombres) as reingreso_hombres, SUM(reingreso_mujeres) as reingreso_mujeres FROM equidadin34 ")
                        .concat(" WHERE id_universidad=")
                        .concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 4, rs.getInt("ingreso_hombres"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("ingreso_mujeres"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("reingreso_hombres"), "datos");
                    obj.val(contCol + 3, unis + 4, rs.getInt("reingreso_mujeres"), "datos");
                    totalcol[0]+=rs.getInt("ingreso_hombres");
                    totalcol[1]+=rs.getInt("ingreso_mujeres");
                    totalcol[2]+=rs.getInt("reingreso_hombres");
                    totalcol[3]+=rs.getInt("reingreso_mujeres");
                } else { //si no hay una respuesta de la consulta
                    obj.val(contCol, unis + 4, "DATO NO REGISTRADO", "error");
                }
                unistmp=unis+1;
            }
              //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 4; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 34 " + ex.getMessage());
        }
    }

    private void indicador35() {
         int totalcol[]=new int [9];
        int unistmp=0;
        c = 11;
        obj.crearHoja("Ind. 35 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("35. PROMOCIÓN DEPORTIVA, CULTURAL Y COMUNITARIA ", contCol, contCol + 8, 1, 1, "h1");
            obj.combinarCeldas("Eventos deportivos", contCol, contCol + 2, 2, 2, "h1");
            obj.val(contCol, 3, "Realizados", "h2");
            obj.val(contCol + 1, 3, "Programados", "h2");
            obj.val(contCol + 2, 3, "Personas Atendidas", "h2");
            obj.combinarCeldas("Eventos culturales", contCol + 3, contCol + 5, 2, 2, "h1");
            obj.val(contCol + 3, 3, "Realizados", "h2");
            obj.val(contCol + 4, 3, "Programados", "h2");
            obj.val(contCol + 5, 3, "Personas Atendidas", "h2");
            obj.combinarCeldas("Eventos Comunitarios", contCol + 6, contCol + 8, 2, 2, "h1");
            obj.val(contCol + 6, 3, "Realizados", "h2");
            obj.val(contCol + 7, 3, "Programados", "h2");
            obj.val(contCol + 8, 3, "Personas Atendidas", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT dep_realizados, dep_programados, dep_personas, cul_realizados, cul_programados, "
                        .concat("cul_personas, com_realizados, com_programados, com_personas FROM equidadin35 ")
                        .concat(" WHERE id_universidad=")
                        .concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 4, rs.getInt("dep_realizados"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("dep_programados"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("dep_personas"), "datos");
                    obj.val(contCol + 3, unis + 4, rs.getInt("cul_realizados"), "datos");
                    obj.val(contCol + 4, unis + 4, rs.getInt("cul_programados"), "datos");
                    obj.val(contCol + 5, unis + 4, rs.getInt("cul_personas"), "datos");
                    obj.val(contCol + 6, unis + 4, rs.getInt("com_realizados"), "datos");
                    obj.val(contCol + 7, unis + 4, rs.getInt("com_programados"), "datos");
                    obj.val(contCol + 8, unis + 4, rs.getInt("com_personas"), "datos");
                    totalcol[0]+=rs.getInt("dep_realizados");
                    totalcol[1]+=rs.getInt("dep_programados");
                    totalcol[2]+=rs.getInt("dep_personas");
                    totalcol[3]+=rs.getInt("cul_realizados");
                    totalcol[4]+=rs.getInt("cul_programados");
                    totalcol[5]+=rs.getInt("cul_personas");
                    totalcol[6]+=rs.getInt("com_realizados");
                    totalcol[7]+=rs.getInt("com_programados");
                    totalcol[8]+=rs.getInt("com_personas");
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 3, contCol + 5, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 6, contCol + 8, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
              //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 9; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 35 " + ex.getMessage());
        }
    }

    private void indicador36() {
        int totalcol0=0, totalcoltm=0, unistmp=0;
        c = 3 + apoyos.size();
        obj.crearHoja("Ind. 36 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("36. ALUMNOS DE LA UNIVERSIDAD CON BECA", contCol + 1, contCol + apoyos.size(), 1, 1, "h1");
            obj.combinarCeldas("MATRÍCULA TOTAL", contCol, contCol, 1, 2, "h1");
            for (int unis = 0; unis < univs.size(); unis++) {
                obj.val(contCol, unis + 3, univs.getMatriculaTotal(unis), "datos");
                totalcoltm+=univs.getMatriculaTotal(unis);
                unistmp=unis+1;
            }
            obj.val(contCol, unistmp + 3, totalcoltm, "total");
            contCol++;
            unistmp=0;
            for (int apoyo = 0; apoyo < apoyos.size(); apoyo++) {
                totalcol0=0;
                obj.val(contCol, 2, apoyos.getNombre(apoyo), "h1");
                
                for (int unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT apoyos_otorgados FROM equidadin36 WHERE id_universidad="
                            .concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_apoyo=").concat(String.valueOf(apoyos.getId(apoyo)));
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        obj.val(contCol, unis + 3, rs.getInt("apoyos_otorgados"), "datos");
                        totalcol0+=rs.getInt("apoyos_otorgados");
                    } else { //si no hay una respuesta de la consulta
                        obj.val(contCol, unis + 3, "NO CONCLUIDO", "error");
                    }
                    unistmp=unis+1;
                }
                obj.val(contCol, unistmp + 3, totalcol0, "total");
                contCol++;
            }
        } catch (SQLException ex) {
            System.err.println("ERROR BD: Sabana general, Vinculacion Indicador 36 " + ex.getMessage());
        } finally {
            apoyos = null;
        }
    }
}