/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

/**
 *
 * @author Cuauhtemoc Medina Muñoz
 */
public class ExcelPertinencia extends SabanaCategorias {

    private int contCol = 0;
    private int c = 0;
    private int f = 5;
    private ListaOrganismos organismos;
    private ListaServicios servicios;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Excel Pertinencia Eliminada :)");
    }

    public ExcelPertinencia(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.f += univs.size();
        this.organismos = new ListaOrganismos();
        this.servicios = new ListaServicios();
    }

    public HojaCalculo hacerPertinencia() {
        indicador18();
        indicador19();
        indicador19b();
//        indicador19c();
        indicador20();
        indicador21();
        indicador22();
        indicador23();
        indicador23b();
        indicador24();
        indicador24b();
        indicador25();
        con.Desconectar();
        return obj;
    }

    private void indicador18() {
        int totalcol[]=new int [13];
        int unistmp=0;
        c = 16;
        obj.crearHoja("Ind. 18 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("18. PROGRAMAS CON EVALUACIÓN DIAGNOSTICA", contCol, contCol + 12, 1, 1, "h1");
            obj.combinarCeldas("TOTAL DE PROGRAMAS EDUCATIVOS EVALUADOS", contCol, contCol, 2, 3, "h1");
            contCol += 1;
            obj.combinarCeldas("PROGRAMAS EDUCATIVOS EVALUABLES", contCol, contCol, 2, 3, "h1");
            obj.combinarCeldas("EVALUACÓN DIAGNOSTICA", contCol + 1, contCol + 4, 2, 2, "h1");
            obj.val(contCol + 1, 3, "NIVEL 1", "h2");
            obj.val(contCol + 2, 3, "NIVEL 2", "h2");
            obj.val(contCol + 3, 3, "NIVEL 3", "h2");
            obj.val(contCol + 4, 3, "TOTAL", "h2");
            obj.combinarCeldas("MATRÍCULA INICIAL TOTAL", contCol + 5, contCol + 5, 2, 3, "h1");
            obj.combinarCeldas("MATRÍCULA SIN EVALUACIÓN", contCol + 6, contCol + 6, 2, 3, "h1");
            obj.combinarCeldas("MATRÍCULA CON EVALUACIÓN", contCol + 7, contCol + 7, 2, 3, "h1");
            obj.combinarCeldas("MATRÍCULA EVALUABLE", contCol + 8, contCol + 11, 2, 2, "h1");
            obj.val(contCol + 8, 3, "NIVEL 1", "h2");
            obj.val(contCol + 9, 3, "NIVEL 2", "h2");
            obj.val(contCol + 10, 3, "NIVEL 3", "h2");
            obj.val(contCol + 11, 3, "TOTAL", "h2");
            contCol -= 1;
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT COUNT(DISTINCT(id_pe)) AS 'cantidadProgramas' FROM pe_universidad "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) {
                    obj.val(contCol, unis + 4, rs.getInt("cantidadProgramas"), "datos");
                    totalcol[0]+=rs.getInt("cantidadProgramas");
                } else {
                    obj.val(contCol, unis + 4, "No Concluido", "datos");
                }
                consulta = "SELECT SUM(evaluacion_diagnostica) as 'evaluacion_diagnostica', SUM(val_1) as "
                        .concat("'val_1', SUM(val_2) as 'val_2', SUM(val_3) as 'val_3' FROM pertinenciain18")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol + 1, unis + 4, rs.getInt("evaluacion_diagnostica"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("val_1"), "datos");
                    obj.val(contCol + 3, unis + 4, rs.getInt("val_2"), "datos");
                    obj.val(contCol + 4, unis + 4, rs.getInt("val_3"), "datos");
                    obj.val(contCol + 5, unis + 4, rs.getInt("val_1")+rs.getInt("val_2")+rs.getInt("val_3"), "datos");
                    totalcol[1]+=rs.getInt("evaluacion_diagnostica");
                    totalcol[2]+=rs.getInt("val_1");
                    totalcol[3]+=rs.getInt("val_2");
                    totalcol[4]+=rs.getInt("val_3");
                    totalcol[5]+=rs.getInt("val_1") + rs.getInt("val_2") + rs.getInt("val_3");
                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 1, contCol + 10, unis + 4, unis + 4, "error");
                }
                consulta = "SELECT SUM(matricula_inicial) as 'matricula_inicial', SUM(matricula_sin_eva) as 'sin_evaluacion', "
                        .concat("SUM(matricula_con_eva) as 'con_evaluacion', SUM(val_1) as 'val_1' , SUM(val_2) as 'val_2', ")
                        .concat("SUM(val_3) as 'val_3' FROM pertinenciain18_1 ")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol + 6, unis + 4, rs.getInt("matricula_inicial"), "datos");
                    obj.val(contCol + 7, unis + 4, rs.getInt("sin_evaluacion"), "datos");
                    obj.val(contCol + 8, unis + 4, rs.getInt("con_evaluacion"), "datos");
                    obj.val(contCol + 9, unis + 4, rs.getInt("val_1"), "datos");
                    obj.val(contCol + 10, unis + 4, rs.getInt("val_2"), "datos");
                    obj.val(contCol + 11, unis + 4, rs.getInt("val_3"), "datos");
                    obj.val(contCol + 12, unis + 4, rs.getInt("val_1")+rs.getInt("val_2")+rs.getInt("val_3"), "datos");
                    totalcol[6]+=rs.getInt("matricula_inicial");
                    totalcol[7]+=rs.getInt("sin_evaluacion");
                    totalcol[8]+=rs.getInt("con_evaluacion");
                    totalcol[9]+=rs.getInt("val_1");
                    totalcol[10]+=rs.getInt("val_2");
                    totalcol[11]+=rs.getInt("val_3");
                    totalcol[12]+=rs.getInt("val_1") + rs.getInt("val_2") + rs.getInt("val_3");
                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 1, contCol + 10, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 13; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Pertinencia Indicador 18 " + ex.getMessage());
        }
    }

    private void indicador19() {
        double totalcol[]=new double [6];
        int unistmp=0;
        c = 2 + 6 * niveles.size();
        obj.crearHoja("Ind. 19 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 6250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                totalcol[0]=0;totalcol[1]=0;totalcol[2]=0;totalcol[3]=0;
                obj.combinarCeldas("19. RELACIÓN DE PROGRAMAS EDUCATIVOS ACREDITADOS DEL NIVEL " + niveles.getAbreviatura(nivel), contCol, contCol + 1, 1, 1, "h1");
                obj.val(contCol, 2, "PEA2", "h2");
                obj.val(contCol + 1, 2, "PEA1", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT SUM(matricula_inicial_aten) as 'inicial', SUM(matricula_acreditada) as 'matAcreditada', SUM(acreditados) as 'acreditados'"
                            .concat(" , COUNT(id_pe) as 't_pe' FROM pertinenciain19")
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_pe IN (SELECT id_pe FROM programa_educativo WHERE id_pe IN (")
                            .concat(" SELECT id_pe FROM pe_universidad")
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(") AND id_nivel=").concat(String.valueOf(niveles.getId(nivel))).concat(")");
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        if(rs.getInt("inicial")>0){
                            obj.val(contCol , unis + 3,redondear((double)rs.getDouble("matAcreditada")/rs.getDouble("inicial")*100), "datos2");
                        }else{
                          obj.val(contCol , unis + 3, 0, "datos");
                        }
                        if(rs.getInt("acreditados")>0){
                            obj.val(contCol + 1, unis + 3, redondear((rs.getDouble("acreditados")/rs.getDouble("t_pe"))*100), "datos2");
                        }else{
                            obj.val(contCol + 1, unis + 3, 0, "datos2");
                        }
                        totalcol[0]+=rs.getDouble("inicial");
                        totalcol[1]+=rs.getDouble("matAcreditada");
                        totalcol[2]+=rs.getDouble("acreditados");
                        totalcol[3]+=rs.getDouble("t_pe");
                    } else { //si no hay una respuesta de la consulta
                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 2, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                }
                       if(totalcol[0]>0){
                            obj.val(contCol , unistmp + 3, redondear(totalcol[1]/totalcol[0]*100), "total2");
                       }else{
                           obj.val(contCol , unistmp + 3, 0, "total2");
                       }
                       obj.val(contCol + 1, unistmp + 3, redondear((totalcol[2]/totalcol[3])*100), "total2");
                    
                contCol += 2;
            }
            contCol+=1;
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel, Pertinencia Indicador 19 " + ex.getMessage());
        }
    }

    private void indicador19b() {
        double totalcol[]=new double [4];
        int unistmp=0;
        c = 3 + 3 * niveles.size();
        obj.crearHoja("Ind. 19 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 6250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                 totalcol[0]=0;totalcol[1]=0;totalcol[2]=0;
                obj.combinarCeldas("19. RELACIÓN DE PROGRAMAS EDUCATIVOS ACREDITADOS INTERNACIONALMENTE DEL NIVEL " + niveles.getAbreviatura(nivel), contCol, contCol + 1, 1, 1, "h1");
                obj.val(contCol, 2, "PEAI2", "h2");
                obj.val(contCol + 1, 2, "PEAI1", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    consulta = "SELECT SUM(matricula_inicial_aten) as 'inicial', SUM(matricula_acreditada) as 'matAcreditada', SUM(acreditados) as 'acreditados'"
                            .concat(", COUNT(id_pe) as 't_pe' FROM pertinenciain19_1")
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(" AND id_pe IN (SELECT id_pe FROM programa_educativo WHERE id_pe IN (")
                            .concat(" SELECT id_pe FROM pe_universidad")
                            .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                            .concat(" AND id_periodo=").concat(periodo)
                            .concat(") AND id_nivel=").concat(String.valueOf(niveles.getId(nivel))).concat(")");
                    rs = con.Consultar(consulta);
                    if (rs.next()) { //si hay una respuesta de la consulta
                        if(rs.getDouble("matAcreditada")>0){
                            obj.val(contCol , unis + 3, redondear(rs.getDouble("matAcreditada")/rs.getDouble("inicial")*100), "datos2");
                        }else{
                            obj.val(contCol , unis + 3, 0, "datos2");
                        }
                        if(rs.getDouble("acreditados")>0){
                            obj.val(contCol + 1, unis + 3,redondear(rs.getDouble("acreditados")/rs.getDouble("t_pe")*100) , "datos2");
                        }else{
                            obj.val(contCol + 1, unis + 3,0 , "datos2");
                        }
                        totalcol[0]+=rs.getDouble("inicial");
                        totalcol[1]+=rs.getDouble("matAcreditada");
                        totalcol[2]+=rs.getDouble("acreditados");
                        totalcol[3]+=rs.getDouble("t_pe");
                    } else { //si no hay una respuesta de la consulta
                        obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                }
                 //IMPRIME LOS TOTALES DE COLUMNA
                         obj.val(contCol, unistmp + 3, redondear(totalcol[1]/totalcol[0]*100), "total2");
                         obj.val(contCol + 1, unistmp + 3, redondear(totalcol[2]/totalcol[3]*100), "total2");
                contCol += 2;
            }
            
            
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel general, Pertinencia Indicador 19 b " + ex.getMessage());
        }
    }

    private void indicador19c() {
        int totalcol[]=new int[2];
        int unistmp=0;
        c = 3 + organismos.getNacionales() * 2 * 2;
        obj.crearHoja("Ind. 19 c ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            obj.combinarCeldas("19. RELACIÓN DE PROGRAMAS EDUCATIVOS ACREDITADOS NACIONALMENTE", contCol, contCol + organismos.getNacionales() * 2 - 1, 1, 1, "h1");
            for (int org = 0; org < organismos.size(); org++) {
                totalcol[0]=0;totalcol[1]=0;
                if (!organismos.isExtranjero(org)) {
                    obj.combinarCeldas(organismos.getSiglas(org), contCol, contCol + 1, 2, 2, "h1");
                    obj.val(contCol, 3, "Cantidad", "h2");
                    obj.val(contCol + 1, 3, "Matricula acreditada", "h2");
                    for (int unis = 0; unis < univs.size(); unis++) {
                        consulta = "SELECT COUNT(id_universidad) as 'cantidad' FROM pertinenciain19 WHERE id_organismo="
                                .concat(String.valueOf(organismos.getId(org)))
                                .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            obj.val(contCol, unis + 4, rs.getInt("cantidad"), "datos");
                            totalcol[0]+=rs.getInt("cantidad");
                        } else {
                            obj.val(contCol, unis + 4, 0, "datos");
                        }
                        consulta = "SELECT SUM(matricula_acreditada) as 'cantidad' FROM pertinenciain19 WHERE id_organismo="
                                .concat(String.valueOf(organismos.getId(org)))
                                .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            obj.val(contCol + 1, unis + 4, rs.getInt("cantidad"), "datos");
                            totalcol[1]+=rs.getInt("cantidad");
                        } else {
                            obj.val(contCol + 1, unis + 4, 0, "datos");
                        }
                        unistmp=unis+1;
                    }
                     //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 2; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
                    contCol += 2;
                }
            }

            contCol += 1;

            obj.combinarCeldas("19. RELACIÓN DE PROGRAMAS EDUCATIVOS ACREDITADOS INTERNACIONALMENTE", contCol, contCol + organismos.getExtranjeros() * 2 - 1, 1, 1, "h1");
            for (int org = 0; org < organismos.size(); org++) {
                totalcol[0]=0;totalcol[1]=0;
                if (organismos.isExtranjero(org)) {
                    obj.combinarCeldas(organismos.getSiglas(org), contCol, contCol + 1, 2, 2, "h1");
                    obj.val(contCol, 3, "Cantidad", "h2");
                    obj.val(contCol + 1, 3, "Matricula acreditada", "h2");
                    for (int unis = 0; unis < univs.size(); unis++) {
                        consulta = "SELECT COUNT(id_universidad) as 'cantidad' FROM pertinenciain19_1 WHERE id_organismo="
                                .concat(String.valueOf(organismos.getId(org)))
                                .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            obj.val(contCol, unis + 4, rs.getInt("cantidad"), "datos");
                             totalcol[0]+=rs.getInt("cantidad");
                        } else {
                            obj.val(contCol, unis + 4, 0, "datos");
                        }
                        consulta = "SELECT SUM(matricula_acreditada) as 'cantidad' FROM pertinenciain19_1 WHERE id_organismo="
                                .concat(String.valueOf(organismos.getId(org)))
                                .concat(" AND id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo);
                        rs = con.Consultar(consulta);
                        if (rs.next()) {
                            obj.val(contCol + 1, unis + 4, rs.getInt("cantidad"), "datos");
                            totalcol[1]+=rs.getInt("cantidad");
                        } else {
                            obj.val(contCol + 1, unis + 4, 0, "datos");
                        }
                       unistmp=unis+1; 
                    }
            //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 2; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
                    contCol += 2;
                }
            }

        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Pertinencia Indicador 19 c " + ex.getMessage());
        } finally {
            organismos = null;
        }
    }

    private void indicador20() {
        int totalcol[]= new int [30];
        int unistmp=0;
        c = 32;
        obj.crearHoja("Ind. 20 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            /*1*/
            obj.combinarCeldas("1. Cantidad de Acciones que ha realizado la institución durante el periodo a evaluar.", contCol, contCol + 4, 1, 1, "h1");
            obj.combinarCeldas("CURSOS", contCol, contCol + 1, 2, 2, "h1");
            obj.combinarCeldas("TALLERES", contCol + 2, contCol + 3, 2, 2, "h1");
            obj.combinarCeldas("OTRO", contCol + 4, contCol + 4, 2, 3, "h1");
            obj.val(contCol, 3, "INTERNOS", "h2");
            obj.val(contCol + 1, 3, "EXTERNOS", "h2");
            obj.val(contCol + 2, 3, "INTERNOS", "h2");
            obj.val(contCol + 3, 3, "EXTERNOS", "h2");
            /*2*/
            contCol += 5;
            obj.combinarCeldas("2. Cantidad de  Profesores de Tiempo Completo (PTC) que han participado en las acciones durante el período a evaluar", contCol, contCol + 4, 1, 1, "h1");
            obj.combinarCeldas("CURSOS", contCol, contCol + 1, 2, 2, "h1");
            obj.combinarCeldas("TALLERES", contCol + 2, contCol + 3, 2, 2, "h1");
            obj.combinarCeldas("OTRO", contCol + 4, contCol + 4, 2, 3, "h1");
            obj.val(contCol, 3, "INTERNOS", "h2");
            obj.val(contCol + 1, 3, "EXTERNOS", "h2");
            obj.val(contCol + 2, 3, "INTERNOS", "h2");
            obj.val(contCol + 3, 3, "EXTERNOS", "h2");
            contCol += 5;
            //3
            obj.combinarCeldas("3. Cantidad de Profesores de Asignatura (PA) que han participado en las acciones durante el periodo a evaluar", contCol, contCol + 4, 1, 1, "h1");
            obj.combinarCeldas("CURSOS", contCol, contCol + 1, 2, 2, "h1");
            obj.combinarCeldas("TALLERES", contCol + 2, contCol + 3, 2, 2, "h1");
            obj.combinarCeldas("OTRO", contCol + 4, contCol + 4, 2, 3, "h1");
            obj.val(contCol, 3, "INTERNOS", "h2");
            obj.val(contCol + 1, 3, "EXTERNOS", "h2");
            obj.val(contCol + 2, 3, "INTERNOS", "h2");
            obj.val(contCol + 3, 3, "EXTERNOS", "h2");
            contCol += 5;
            //4
            obj.combinarCeldas("4. Cantidad de material de los Enfoque Centrados en el Aprendizaje", contCol, contCol + 2, 1, 1, "h1");
            obj.combinarCeldas("DIFUSIÓN", contCol, contCol, 2, 3, "h1");
            obj.combinarCeldas("CAP.", contCol + 1, contCol + 1, 2, 3, "h1");
            obj.combinarCeldas("OTRO", contCol + 2, contCol + 2, 2, 3, "h1");
            contCol += 3;
            obj.combinarCeldas("5. Usuarios a quien va dirigido el material", contCol, contCol + 3, 1, 1, "h1");
            obj.combinarCeldas("PTC", contCol, contCol, 2, 3, "h1");
            obj.combinarCeldas("PA", contCol + 1, contCol + 1, 2, 3, "h1");
            obj.combinarCeldas("ALUMNOS", contCol + 2, contCol + 2, 2, 3, "h1");
            obj.combinarCeldas("OTRO", contCol + 3, contCol + 3, 2, 3, "h1");
            contCol += 4;
            obj.combinarCeldas("6. Cantidad de PTC según situación en los enfoques", contCol, contCol + 3, 1, 1, "h1");
            obj.combinarCeldas("Terminaron la capacitacion", contCol, contCol, 2, 3, "h1");
            obj.combinarCeldas("Están en proceso de capacitación", contCol + 1, contCol + 1, 2, 3, "h1");
            obj.combinarCeldas("No tienen capacitación", contCol + 2, contCol + 2, 2, 3, "h1");
            obj.combinarCeldas("PTC que estan aplicando estos enfoques", contCol + 3, contCol + 3, 2, 3, "h1");
            contCol += 4;
            obj.combinarCeldas("7. Cantidad de profesores de asignatura según situaciones en los enfoques", contCol, contCol + 3, 1, 1, "h1");
            obj.combinarCeldas("Terminaron la capacitacion", contCol, contCol, 2, 3, "h1");
            obj.combinarCeldas("Están en proceso de capacitación", contCol + 1, contCol + 1, 2, 3, "h1");
            obj.combinarCeldas("No tienen capacitación", contCol + 2, contCol + 2, 2, 3, "h1");
            obj.combinarCeldas("PTC que estan aplicando estos enfoques", contCol + 3, contCol + 3, 2, 3, "h1");
            contCol = 2;
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT cursos_realizados_i, cursos_realizados_e, talleres_realizados_i, talleres_realizados_e, acciones_otro,"
                        .concat(" ptc_cursos_i, ptc_cursos_e, ptc_talleres_i, ptc_talleres_e, ptc_otros,")
                        .concat(" pa_cursos_i, pa_cursos_e, pa_talleres_i, pa_talleres_e, pa_otros,")
                        .concat(" material_difusion, material_capacitacion, material_otro, material_ptc,")
                        .concat(" material_d_pa, material_d_alumnos, material_d_otro, ptc_terminaron, ptc_proceso,")
                        .concat(" ptc_no_tienen, ptc_aplicando, pa_terminaron, pa_proceso, pa_no_tienen, pa_aplicando")
                        .concat(" FROM pertinenciain20 ")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    //1
                    obj.val(contCol, unis + 4, rs.getInt("cursos_realizados_i"), "datos");
                    obj.val(contCol + 1, unis + 4, rs.getInt("cursos_realizados_e"), "datos");
                    obj.val(contCol + 2, unis + 4, rs.getInt("talleres_realizados_i"), "datos");
                    obj.val(contCol + 3, unis + 4, rs.getInt("talleres_realizados_e"), "datos");
                    obj.val(contCol + 4, unis + 4, rs.getInt("acciones_otro"), "datos");
                    totalcol[0]+=rs.getInt("cursos_realizados_i");
                    totalcol[1]+=rs.getInt("cursos_realizados_e");
                    totalcol[2]+=rs.getInt("talleres_realizados_i");
                    totalcol[3]+=rs.getInt("talleres_realizados_e");
                    totalcol[4]+=rs.getInt("acciones_otro");
                    //2
                    obj.val(contCol + 5, unis + 4, rs.getInt("ptc_cursos_i"), "datos");
                    obj.val(contCol + 6, unis + 4, rs.getInt("ptc_cursos_e"), "datos");
                    obj.val(contCol + 7, unis + 4, rs.getInt("ptc_talleres_i"), "datos");
                    obj.val(contCol + 8, unis + 4, rs.getInt("ptc_talleres_e"), "datos");
                    obj.val(contCol + 9, unis + 4, rs.getInt("ptc_otros"), "datos");
                    totalcol[5]+=rs.getInt("ptc_cursos_i");
                    totalcol[6]+=rs.getInt("ptc_cursos_e");
                    totalcol[7]+=rs.getInt("ptc_talleres_i");
                    totalcol[8]+=rs.getInt("ptc_talleres_e");
                    totalcol[9]+=rs.getInt("ptc_otros");
                    //3
                    obj.val(contCol + 10, unis + 4, rs.getInt("pa_cursos_i"), "datos");
                    obj.val(contCol + 11, unis + 4, rs.getInt("pa_cursos_e"), "datos");
                    obj.val(contCol + 12, unis + 4, rs.getInt("pa_talleres_i"), "datos");
                    obj.val(contCol + 13, unis + 4, rs.getInt("pa_talleres_e"), "datos");
                    obj.val(contCol + 14, unis + 4, rs.getInt("pa_otros"), "datos");
                    totalcol[10]+=rs.getInt("pa_cursos_i");
                    totalcol[11]+=rs.getInt("pa_cursos_e");
                    totalcol[12]+=rs.getInt("pa_talleres_i");
                    totalcol[13]+=rs.getInt("pa_talleres_e");
                    totalcol[14]+=rs.getInt("pa_otros");
                    
                    //
                    obj.val(contCol + 15, unis + 4, rs.getInt("material_difusion"), "datos");
                    obj.val(contCol + 16, unis + 4, rs.getInt("material_capacitacion"), "datos");
                    obj.val(contCol + 17, unis + 4, rs.getInt("material_otro"), "datos");
                     totalcol[15]+=rs.getInt("material_difusion");
                    totalcol[16]+=rs.getInt("material_capacitacion");
                    totalcol[17]+=rs.getInt("material_otro");
                    //4
                    obj.val(contCol + 18, unis + 4, rs.getInt("material_ptc"), "datos");
                    obj.val(contCol + 19, unis + 4, rs.getInt("material_d_pa"), "datos");
                    obj.val(contCol + 20, unis + 4, rs.getInt("material_d_alumnos"), "datos");
                    obj.val(contCol + 21, unis + 4, rs.getInt("material_d_otro"), "datos");
                     totalcol[18]+=rs.getInt("material_ptc");
                    totalcol[19]+=rs.getInt("material_d_pa");
                    totalcol[20]+=rs.getInt("material_d_alumnos");
                    totalcol[21]+=rs.getInt("material_d_otro");
                    //5
                    obj.val(contCol + 22, unis + 4, rs.getInt("ptc_terminaron"), "datos");
                    obj.val(contCol + 23, unis + 4, rs.getInt("ptc_proceso"), "datos");
                    obj.val(contCol + 24, unis + 4, rs.getInt("ptc_no_tienen"), "datos");
                    obj.val(contCol + 25, unis + 4, rs.getInt("ptc_aplicando"), "datos");
                     totalcol[22]+=rs.getInt("ptc_terminaron");
                    totalcol[23]+=rs.getInt("ptc_proceso");
                    totalcol[24]+=rs.getInt("ptc_no_tienen");
                    totalcol[25]+=rs.getInt("ptc_aplicando");
                    //6
                    obj.val(contCol + 26, unis + 4, rs.getInt("pa_terminaron"), "datos");
                    obj.val(contCol + 27, unis + 4, rs.getInt("pa_proceso"), "datos");
                    obj.val(contCol + 28, unis + 4, rs.getInt("pa_no_tienen"), "datos");
                    obj.val(contCol + 29, unis + 4, rs.getInt("pa_aplicando"), "datos");
                     totalcol[26]+=rs.getInt("pa_terminaron");
                    totalcol[27]+=rs.getInt("pa_proceso");
                    totalcol[28]+=rs.getInt("pa_no_tienen");
                    totalcol[29]+=rs.getInt("pa_aplicando");
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 4, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 5, contCol + 9, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 10, contCol + 14, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 15, contCol + 17, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 18, contCol + 21, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 22, contCol + 25, unis + 4, unis + 4, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 26, contCol + 29, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 30; tco++) {
                         obj.val(contCol+tco, unistmp + 4, totalcol[tco], "total");
                    }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Pertinencia Indicador 20 " + ex.getMessage());
        }
    }

    private void indicador21() {
        int totalcol []= new int [7];
        double escala10arr [][] = new double [univs.size()][servicios.size()]; 
        double totalcol7=0, totalcol8=0;
        int unistmp=0;
        double ra, rb, rc, rd, re, rf, rg, totalh;
        double escala5, escala10, totMult, totSum;
        c = (2 + 12 * servicios.size());
        obj.crearHoja("Ind. 21 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(3);
        contCol = 2;
        try {
            //ENCABEZADOS
            for (int serv = 0; serv < servicios.size(); serv++) {
                for (int y = 0; y <= 6; y++) {
                    obj.setAnchoColumna(contCol + y, 4250);
                }
                obj.combinarCeldas("21. ENCUESTA DEL SERVICIO " + servicios.getNombre(serv).toUpperCase(), contCol, contCol + 1, 1, 2, "h1");
                obj.val(contCol, 3, "MS", "h2");
                obj.val(contCol + 1, 3, "TES", "h2");
//                obj.val(contCol + 2, 3, "TES", "h2");
//                obj.val(contCol + 3, 3, "PS", "h2");
//                obj.val(contCol + 4, 3, "NS", "h2");
//                obj.val(contCol + 5, 3, "NA", "h2");
//                obj.val(contCol + 6, 3, "NE", "h2");
//                obj.combinarCeldas("CALIFICACIÓN ESCALA 5", contCol + 7, contCol + 7, 2, 3, "h1");
//                obj.combinarCeldas("CALIFICACIÓN ESCALA 10", contCol + 8, contCol + 8, 2, 3, "h1");
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneServicio(String.valueOf(servicios.getId(serv)), String.valueOf(univs.getId(unis)))) {
                        consulta = "SELECT SUM(r_a) as r_a, SUM(r_b) as r_b, SUM(r_c) as r_c,"
                                .concat(" SUM(r_d) as r_d, SUM(r_e) as r_e, SUM(r_f) as r_f, SUM(r_g) as r_g")
                                .concat(" FROM pertinenciain21 WHERE id_pregunta IN(")
                                .concat(" SELECT id_pregunta FROM encuesta_preguntas WHERE ")
                                .concat(" id_servicio=").concat(String.valueOf(servicios.getId(serv)))
                                .concat(" AND id_encuesta=(")
                                .concat(" SELECT id_encuesta FROM encuestas WHERE indicador=21))")
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
                            totalh= ra + rb + rc + rd + re + rf +rg;
                            obj.val(contCol, unis + 4, redondear(ra/totalh*100), "datos2");
                            obj.val(contCol + 1, unis + 4, redondear((ra+rb)/totalh*100), "datos2");
//                            obj.val(contCol + 2, unis + 4, redondear(ra/totalh*100), "datos2");
//                            obj.val(contCol + 3, unis + 4, redondear((ra+rb)/totalh*100), "datos2");
//                            obj.val(contCol + 4, unis + 4, totalh, "datos2");
//                            obj.val(contCol + 5, unis + 4, rf, "datos");
//                            obj.val(contCol + 6, unis + 4, rg, "datos");
//                            totMult = (ra * 5) + (rb * 4) + (rc * 3) + (rd * 2) + (re * 1);
//                            totSum = ra + rb + rc + rd + re;
//                            if (totSum != 0) {
//                                escala5 = totMult / totSum;
//                            } else {
//                                escala5 = 0;
//                            }
//                            escala10 = escala5 * 2;
//                            obj.val(contCol + 7, unis + 4, escala5, "datos2");
//                            obj.val(contCol + 8, unis + 4, escala10, "datos2");
//                            escala10arr[unis][serv]=escala10;
                            totalcol[0]+=ra;
                            totalcol[1]+=rb;
                            totalcol[2]+=rc;
                            totalcol[3]+=rd;
                            totalcol[4]+=re;
                            totalcol[5]+=rf;
                            totalcol[6]+=rg;
//                            totalcol7+=escala5;
//                            totalcol8+=escala10;
                        } else {
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 1, unis + 4, unis + 4, "error");
                        }
                    } else { //no tiene el servicio
                        obj.combinarCeldas("NO TIENE EL SERVICIO", contCol, contCol + 1, unis + 4, unis + 4, "error");
                    }
                    unistmp=unis+1;
                }
//                 //IMPRIME LOS TOTALES DE COLUMNA
                    double t_totalh=totalcol[0] + totalcol[1] + totalcol[2] + totalcol[3] + totalcol[4] + totalcol[5] + totalcol[6];
                    
//                    obj.val(contCol, unistmp + 4, totalcol[0], "total2");
//                    obj.val(contCol+1, unistmp + 4, totalcol[1], "total2");
                    obj.val(contCol, unistmp + 4, redondear(totalcol[0]/t_totalh*100), "total2");
                    obj.val(contCol + 1, unistmp + 4, redondear((totalcol[0]+totalcol[1])/t_totalh*100), "total2");
//                    obj.val(contCol+4, unistmp + 4, t_totalh, "total2");
                contCol += 2;
            }
//              for (int serv = 0; serv < servicios.size(); serv++) {
//                    obj.combinarCeldas("21. ENCUESTA DEL SERVICIO " + servicios.getNombre(serv).toUpperCase(), contCol , contCol , 2, 3, "h1");
//                  for (int unis = 0; unis < univs.size(); unis++) {
//                      obj.val(contCol, unis + 4, escala10arr[unis][serv], "datos2");
//                  }
//                  contCol+=1;
//              }
//              obj.combinarCeldas("TOTAL", contCol , contCol , 2, 3, "h1");
//                  for (int unis = 0; unis < univs.size(); unis++) {
//                      double tot=0;
//                      for (int serv = 0; serv < servicios.size(); serv++) {
//                          tot+=escala10arr[unis][serv];
//                      }
//                      obj.val(contCol, unis + 4, tot, "datos2");
//                    
//                  }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general Indicador 21 " + ex);
        } finally {
            servicios = null;
        }
    }

    private void indicador22() {
        int totalcol[]=new int [4];
        int unistmp=0;
        c = 2 + 4 * niveles.size();
        obj.crearHoja("Ind. 22 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            for (int nivel = 0; nivel < niveles.size(); nivel++) {
                for (int reinarray = 0; reinarray < 4; reinarray++) {
                    totalcol[reinarray]=0;
                }
                obj.combinarCeldas("22. PROGRAMAS EDUCATIVOS PERTINENTES NIVEL " + niveles.getAbreviatura(nivel), contCol, contCol + 3, 1, 1, "h1");
                obj.val(contCol, 2, "Programas Educativos", "h2");
                obj.val(contCol + 1, 2, "Programas Educativos Pertinentes 2", "h2");
                obj.val(contCol + 2, 2, "Matrícula inicial", "h2");
                obj.val(contCol + 3, 2, "Matrícula Pertinente", "h2");
                for (int unis = 0; unis < univs.size(); unis++) {
                    if (contieneNivel(String.valueOf(niveles.getId(nivel)), String.valueOf(univs.getId(unis)))) {
                        consulta = "SELECT COUNT(id_pe) as 'cantidadProgramas', SUM(pertinente) as 'programasPertinentes', "
                                .concat(" SUM(matricula_ini_pe) as 'matriculaInicial', SUM(matricula_pert) as 'matriculaPertinente'")
                                .concat(" FROM pertinenciain22 ")
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(" AND id_pe IN (SELECT id_pe FROM programa_educativo WHERE id_pe IN (")
                                .concat(" SELECT id_pe FROM pe_universidad")
                                .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND id_periodo=").concat(periodo)
                                .concat(") AND id_nivel=").concat(String.valueOf(niveles.getId(nivel))).concat(")");
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            obj.val(contCol, unis + 3, rs.getInt("cantidadProgramas"), "datos");
                            obj.val(contCol + 1, unis + 3, rs.getInt("programasPertinentes"), "datos");
                            obj.val(contCol + 2, unis + 3, rs.getInt("matriculaInicial"), "datos");
                            obj.val(contCol + 3, unis + 3, rs.getInt("matriculaPertinente"), "datos");
                            totalcol[0]+=rs.getInt("cantidadProgramas");
                            totalcol[1]+=rs.getInt("programasPertinentes");
                            totalcol[2]+=rs.getInt("matriculaInicial");
                            totalcol[3]+=rs.getInt("matriculaPertinente");
                        } else { //si no hay una respuesta de la consulta
                            obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                        }
                    } else {
                        obj.combinarCeldas("NO TIENE EL NIVEL", contCol, contCol + 3, unis + 3, unis + 3, "error");
                    }
                    unistmp=unis+1;
                }
                   //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 4; tco++) {
                         obj.val(contCol+tco, unistmp + 3, totalcol[tco], "total");
                    }
                contCol += 4;
            }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Pertinencia Indicador 22 " + ex.getMessage());
        }
    }

    private void indicador23() {
        int unistmp=0;
        c = 5;
        obj.crearHoja("Ind. 23 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 6250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("23. NIVEL DE ESTUDIOS DE LOS PROFESORES DE TIEMPO COMPLETO", contCol, contCol, 1, 1, "h1");
            obj.val(contCol, 2, "PTCP1", "h2");
            double sumaM0=0; int contM0=0;
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT especialidad_grado, "
                        .concat(" maestria_grado, doctorado, doctorado_grado, (media_superior + media_superior_con + tsu + tsu_con + lic + lic_titulo + especialidad + maestria + especialidad_grado + maestria_grado + doctorado + doctorado_grado) as suma")
                        .concat(" FROM pertinenciain23")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                double total = 0, subtotal=0;
                if (rs.next()) { //si hay una respuesta de la consulta
                    subtotal= rs.getDouble("especialidad_grado")+
                            rs.getDouble("maestria_grado")+rs.getDouble("doctorado")+
                            rs.getDouble("doctorado_grado");
                    total =rs.getDouble("suma");
                    if(subtotal>0){
                        obj.val(contCol , unis + 3, redondear((subtotal/total*100)), "datos2");
                        sumaM0+=redondear((subtotal/total*100));
                        contM0++;
                    }else{
                        obj.val(contCol , unis + 3, 0, "datos2");
                    }
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol , unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            obj.val(contCol, unistmp + 3, redondear(sumaM0/contM0) , "total2");
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Pertinencia Indicador 23 " + ex.getMessage());
        }
    }

    private void indicador23b() {
        int unistmp=0;
        c = 5;
        obj.crearHoja("Ind. 23 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 8250);
        }
        columnasPrincipales(2);
        obj.setAlturaFilas(2, 35);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("23. PERFIL ACADÉMICO DE LOS PROFESORES DE TIEMPO COMPLETO", contCol, contCol , 1, 1, "h1");
//            obj.combinarCeldas("Capacitados en:", contCol, contCol + 1, 2, 2, "h1");
            obj.val(contCol, 2, "PTCP2", "h2");
            double sumptcp2=0;
            int counptcp2=0;
            for (int unis = 0; unis < univs.size(); unis++) {
                double perfilprodep=0, totalptc=0;
                String consulta0="SELECT prof_tc from datos_universidad WHERE id_universidad="+univs.getId(unis)+
                        " and id_periodo = "+periodo+"";
                 rs = con.Consultar(consulta0);
                 if(rs.next()){
                    totalptc=rs.getDouble("prof_tc");
                 }
                
                consulta = "SELECT promep"
                        .concat(" , (cuerpos_formacion + cuerpos_consolidacion + cuerpos_consolidados) as total")
                        .concat(" FROM pertinenciain23")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    perfilprodep=rs.getDouble("promep");
                    if(perfilprodep > 0){
                        obj.val(contCol , unis + 3, redondear((perfilprodep/totalptc*100)), "datos2");
                        sumptcp2 += redondear((perfilprodep/totalptc*100));
                        counptcp2++;
                    }else{
                        obj.val(contCol , unis + 3, 0, "datos2");
                    }
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol , unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            //IMPRIME LOS TOTALES DE COLUMNA
                      obj.val(contCol  , unistmp + 3, redondear(sumptcp2/counptcp2), "total2");
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel, Pertinencia Indicador 23 b " + ex.getMessage());
        }
    }

    private void indicador24() {
        int totalcol[]=new int[13];
        int unistmp=0;
        c = 15;
        obj.crearHoja("Ind. 24 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("24. NIVEL DE ESTUDIOS DE LOS PROFESORES DE ASIGNATURA Y EXPERIENCIA LABORAL EN LA MATERIA", contCol, contCol + 12, 1, 1, "h1");
            obj.val(contCol, 2, "Media superior sin certificado", "h2");
            obj.val(contCol + 1, 2, "Media superior con certificado", "h2");
            obj.val(contCol + 2, 2, "Técnico superior universitario con título", "h2");
            obj.val(contCol + 3, 2, "Técnico superior universitario sin título", "h2");
            obj.val(contCol + 4, 2, "Licenciatura sin título", "h2");
            obj.val(contCol + 5, 2, "Licenciatura título", "h2");
            obj.val(contCol + 6, 2, "Especialidad sin grado", "h2");
            obj.val(contCol + 7, 2, "Maestría sin grado", "h2");
            obj.val(contCol + 8, 2, "Especialidad con grado", "h2");
            obj.val(contCol + 9, 2, "Maestría con grado", "h2");
            obj.val(contCol + 10, 2, "Doctorado sin grado", "h2");
            obj.val(contCol + 11, 2, "Doctorado con grado", "h2");
            obj.val(contCol + 12, 2, "TOTAL", "h2");
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT fila1, fila2, fila3, fila4, fila5, fila6, fila7, fila8, fila9, fila10, fila11, fila12, (fila1 + fila2 + fila3 + fila4 + fila5 + fila6 + fila7 + fila8 + fila9 + fila10 + fila11 + fila12) as total "
                        .concat(" FROM pertinenciain24")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    obj.val(contCol, unis + 3, rs.getInt("fila1"), "datos");
                    obj.val(contCol + 1, unis + 3, rs.getInt("fila2"), "datos");
                    obj.val(contCol + 2, unis + 3, rs.getInt("fila3"), "datos");
                    obj.val(contCol + 3, unis + 3, rs.getInt("fila4"), "datos");
                    obj.val(contCol + 4, unis + 3, rs.getInt("fila5"), "datos");
                    obj.val(contCol + 5, unis + 3, rs.getInt("fila6"), "datos");
                    obj.val(contCol + 6, unis + 3, rs.getInt("fila7"), "datos");
                    obj.val(contCol + 7, unis + 3, rs.getInt("fila8"), "datos");
                    obj.val(contCol + 8, unis + 3, rs.getInt("fila9"), "datos");
                    obj.val(contCol + 9, unis + 3, rs.getInt("fila10"), "datos");
                    obj.val(contCol + 10, unis + 3, rs.getInt("fila11"), "datos");
                    obj.val(contCol + 11, unis + 3, rs.getInt("fila12"), "datos");
                    obj.val(contCol + 12, unis + 3, rs.getInt("total"), "datos");
                    totalcol[0]+=rs.getInt("fila1");
                    totalcol[1]+=rs.getInt("fila2");
                    totalcol[2]+=rs.getInt("fila3");
                    totalcol[3]+=rs.getInt("fila4");
                    totalcol[4]+=rs.getInt("fila5");
                    totalcol[5]+=rs.getInt("fila6");
                    totalcol[6]+=rs.getInt("fila7");
                    totalcol[7]+=rs.getInt("fila8");
                    totalcol[8]+=rs.getInt("fila9");
                    totalcol[9]+=rs.getInt("fila10");
                    totalcol[10]+=rs.getInt("fila11");
                    totalcol[11]+=rs.getInt("fila12");
                    totalcol[12]+=rs.getInt("total");
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 5, unis + 3, unis + 3, "error");
                    obj.combinarCeldas("NO CONCLUIDO", contCol + 6, contCol + 12, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
             //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 13; tco++) {
                         obj.val(contCol+tco, unistmp + 3, totalcol[tco], "total");
                    }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Pertinencia Indicador 24 " + ex.getMessage());
        }
    }

    private void indicador24b() {
        int unistmp=0;
        c = 10;
        obj.crearHoja("Ind. 24 b ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 7250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("24. SITUACIÓN EN EL TRABAJO DE LOS PROFESORES DE ASIGNATURA", contCol, contCol, 1, 1, "h1");
//            obj.combinarCeldas("Situación en el trabajo relacionado con su ejercicio profesional", contCol, contCol + 2, 2, 2, "h2");
//            obj.combinarCeldas("Situación en el trabajo no relacionado con su ejercicio profesional", contCol + 3, contCol + 5, 2, 2, "h2");
            obj.val(contCol, 2, "PAEL1", "h2");
//            obj.val(contCol + 1, 3, "Sin trabajo en los ultimos tres años", "h2");
//            obj.val(contCol + 2, 3, "Sin trabajo más de tres años", "h2");
//            obj.val(contCol + 3, 3, "Con trabajo actual", "h2");
//            obj.val(contCol + 4, 3, "Sin trabajo en los ultimos tres años", "h2");
//            obj.val(contCol + 5, 3, "Sin trabajo más de tres años", "h2");
            double sumpael=0;
            int countpael1=0;
            for (int unis = 0; unis < univs.size(); unis++) {
                double prof_a =0, prof_a_total=0;
                consulta = "SELECT trabajo_actual1, ultimos_3anios1, mas_3anios1, trabajo_actual2, ultimos_3anios2, mas_3anios2 "
                        .concat(" FROM pertinenciain24")
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
                    prof_a=rs.getDouble("trabajo_actual1")+rs.getDouble("ultimos_3anios1")+rs.getDouble("mas_3anios1");
                    prof_a_total=prof_a+rs.getDouble("trabajo_actual2")+rs.getDouble("ultimos_3anios2")+rs.getDouble("mas_3anios2");
//                    obj.val(contCol, unis + 4, rs.getInt("trabajo_actual1"), "datos");
//                    obj.val(contCol + 1, unis + 4, rs.getInt("ultimos_3anios1"), "datos");
//                    obj.val(contCol + 2, unis + 4, rs.getInt("mas_3anios1"), "datos");
//                    obj.val(contCol + 3, unis + 4, rs.getInt("trabajo_actual2"), "datos");
//                    obj.val(contCol + 4, unis + 4, rs.getInt("ultimos_3anios2"), "datos");
//                    obj.val(contCol + 5, unis + 4, rs.getInt("mas_3anios2"), "datos");
                    if(prof_a>0){
                        obj.val(contCol, unis + 3, redondear(prof_a/prof_a_total*100), "datos2");
                        sumpael+= redondear(prof_a/prof_a_total*100);
                        countpael1++;
                    }else{
                        obj.val(contCol, unis + 3, 0, "datos2");
                    }
                    
//                    totalcol[0]+=rs.getInt("trabajo_actual1");
//                    totalcol[1]+=rs.getInt("ultimos_3anios1");
//                    totalcol[2]+=rs.getInt("mas_3anios1");
//                    totalcol[3]+=rs.getInt("trabajo_actual2");
//                    totalcol[4]+=rs.getInt("ultimos_3anios2");
//                    totalcol[5]+=rs.getInt("mas_3anios2");
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol , unis + 3, unis + 3, "error");
//                    obj.combinarCeldas("NO CONCLUIDO", contCol + 3, contCol + 5, unis + 4, unis + 4, "error");
                }
                unistmp=unis+1;
            }
//            //IMPRIME LOS TOTALES DE COLUMNA
//                    for (int tco = 0; tco < 6; tco++) {
                         obj.val(contCol, unistmp + 3, redondear(sumpael/countpael1) , "total2");
//                    }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Excel, Pertinencia Indicador 24 b " + ex.getMessage());
        }
    }

    private void indicador25() {
        int totalcol[]=new int[10];
        int unistmp=0;
        c = 28;
        obj.crearHoja("Ind. 25 ", f, c);
        obj.setAnchoColumna(1, 17500);
        int NoColumnas = c;
        for (int i = 2; i <= NoColumnas; i++) {
            obj.setAnchoColumna(i, 4250);
        }
        columnasPrincipales(2);
        contCol = 2;
        try {
            //ENCABEZADOS
            obj.combinarCeldas("25. CAPACITACIÓN DEL PERSONAL", contCol, contCol + 3, 1, 1, "h1");
//            obj.val(contCol, 2, "Mandos medios y superiores", "h2");
//            obj.val(contCol + 1, 2, "Personal administrativo y secretarial", "h2");
//            obj.val(contCol + 2, 2, "Profesores de tiempo completo", "h2");
//            obj.val(contCol + 3, 2, "Profesores de asignatura", "h2");
//            obj.val(contCol + 4, 2, "TOTAL", "h2");
//            obj.val(contCol + 5, 2, "Mandos medios y superiores con capacitación", "h2");
//            obj.val(contCol + 6, 2, "Personal administrativo y secretarial con capacitación", "h2");
//            obj.val(contCol + 7, 2, "Profesores de tiempo completo con capacitación", "h2");
//            obj.val(contCol + 8, 2, "Profesores de asignatura con capacitación", "h2");
//            obj.val(contCol + 9, 2, "TOTAL", "h2");
            obj.val(contCol , 2, "CP1", "h2");
            obj.val(contCol + 1, 2, "CP2", "h2");
            obj.val(contCol + 2, 2, "CP3", "h2");
            obj.val(contCol + 3, 2, "CP4", "h2");
            double sumacp [] = new double [4];
            int countcp [] = new int [4];
            for (int unis = 0; unis < univs.size(); unis++) {
                consulta = "SELECT superiores, administrativo, superiores_con, administrativo_con, profesores_tc_con, profesores_asignatura_con FROM pertinenciain25 "
                        .concat(" WHERE id_universidad=").concat(String.valueOf(univs.getId(unis)))
                        .concat(" AND id_periodo=").concat(periodo);
                rs = con.Consultar(consulta);
                if (rs.next()) { //si hay una respuesta de la consulta
//                    obj.val(contCol, unis + 3, rs.getInt("superiores"), "datos");
//                    obj.val(contCol + 1, unis + 3, rs.getInt("administrativo"), "datos");
//                    obj.val(contCol + 2, unis + 3, univs.getPtc(unis), "datos");
//                    obj.val(contCol + 3, unis + 3, univs.getPasignatura(unis), "datos");
//                    obj.val(contCol + 4, unis + 3, (rs.getInt("superiores")+rs.getInt("administrativo")+univs.getPtc(unis)+univs.getPasignatura(unis)), "datos");
//                    
//                    obj.val(contCol + 5, unis + 3, rs.getInt("superiores_con"), "datos");
//                    obj.val(contCol + 6, unis + 3, rs.getInt("administrativo_con"), "datos");
//                    obj.val(contCol + 7, unis + 3, rs.getInt("profesores_tc_con"), "datos");
//                    obj.val(contCol + 8, unis + 3, rs.getInt("profesores_asignatura_con"), "datos");
//                    obj.val(contCol + 9, unis + 3, (rs.getInt("superiores_con")+rs.getInt("administrativo_con")+
//                            rs.getInt("profesores_tc_con")+rs.getInt("profesores_asignatura_con")), "datos");
                    if(rs.getDouble("superiores_con")>0 && rs.getDouble("superiores")>0){
                        obj.val(contCol , unis + 3, redondear(rs.getDouble("superiores_con")/rs.getDouble("superiores")*100), "datos2");
                        sumacp[0]+=redondear(rs.getDouble("superiores_con")/rs.getDouble("superiores")*100);
                        countcp[0]++;
                    }else{
                        obj.val(contCol , unis + 3, 0, "datos2");
                    }
                    if(rs.getDouble("administrativo_con")>0){
                        obj.val(contCol + 1, unis + 3, redondear(rs.getDouble("administrativo_con")/rs.getDouble("administrativo")*100), "datos2");
                        sumacp[1]+=redondear(rs.getDouble("administrativo_con")/rs.getDouble("administrativo")*100);
                        countcp[1]++;
                    }else{
                        obj.val(contCol + 1, unis + 3, 0, "datos2");
                    }
                    
                    if(rs.getDouble("profesores_tc_con")>0){
                        obj.val(contCol + 2, unis + 3, redondear(rs.getDouble("profesores_tc_con")/ univs.getPtc(unis)*100), "datos2");
                        sumacp[2]+=redondear(rs.getDouble("profesores_tc_con")/ univs.getPtc(unis)*100);
                        countcp[2]++;
                    }else{
                        obj.val(contCol + 2, unis + 3, 0, "datos2");
                    }
                    
                    if(rs.getDouble("profesores_asignatura_con")>0){
                        obj.val(contCol + 3, unis + 3, redondear(rs.getDouble("profesores_asignatura_con")/univs.getPasignatura(unis) *100), "datos2");
                        sumacp[3]+=redondear(rs.getDouble("profesores_asignatura_con")/univs.getPasignatura(unis) *100);
                        countcp[3]++;
                    }else{
                        obj.val(contCol + 3, unis + 3, 0, "datos2");
                    }
                    
                    
                } else { //si no hay una respuesta de la consulta
                    obj.combinarCeldas("NO CONCLUIDO", contCol, contCol + 3, unis + 3, unis + 3, "error");
                }
                unistmp=unis+1;
            }
            //IMPRIME LOS TOTALES DE COLUMNA
                    for (int tco = 0; tco < 4; tco++) {
                         obj.val(contCol+tco, unistmp + 3, redondear(sumacp[tco]/countcp[tco]), "total2");
                    }
        } catch (Exception ex) {
            System.err.println("ERROR BD: Sabana general, Pertinencia Indicador 25 " + ex.getMessage());
        }
    }
}