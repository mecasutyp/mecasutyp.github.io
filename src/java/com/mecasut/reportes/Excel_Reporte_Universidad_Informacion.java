package com.mecasut.reportes;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Universidad;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
/*
 * Autor: Salvador Zamora Arias 
 * Fecha: 07/08/2017
 */

public class Excel_Reporte_Universidad_Informacion {

    public Excel_Reporte_Universidad_Informacion() {
    }

    public Excel_Reporte_Universidad crearInformacion(String universidad, String periodo, Excel_Reporte_Universidad obj) throws FileNotFoundException, IOException {
        String consulta = "";
        String consulta2 = "";
        ResultSet rs = null;
        ResultSet rs2 = null;
        ConexionMySQL con = new ConexionMySQL();
        Universidad uni = new Universidad();
        String datos_uni = "datos_uni";
        String datos_uni_sub = "datos_uni_sub";
        String titulo_indicador = "titulo_indicador";
        String titulo_indicador_subr = "titulo_indicador_subr";
        String arial10_bordoble = "arial10_bordoble";
        String arial8_bordoble_text = "arial8_bordoble_text";
        String arial10_leyenda_res = "arial10_leyenda_res";
        String arial10_leyenda2_res = "arial10_leyenda2_res";
        String resultado_base = "resultado_base";
        String resultado_hor = "resultado_hor";
        String no_cuadro = "no_cuadro";
        String titulo_Cua_arial10MayNe = "titulo_Cua_arial10MayNe";
        String titulo_Cua_arial9Ne = "titulo_Cua_arial9Ne";
        String res1decimal = "res1decimal";
        String res1decimallidobleAria10 = "res1decimallidobleAria10";
        String resVerdecimallidobleArial9 = "resVerdecimallidobleArial9";
        String resVerdecimallidobleArial10 = "resVerdecimallidobleArial10";
        String ajustarContenido = "ajustarContenido";
        HSSFSheet informacionS = obj.regresarLibro().createSheet("INFORMACIÓN");
        HSSFRow[] filasInfoS = new HSSFRow[982];
        HSSFCell[][] celdasInfoS = new HSSFCell[14][982];
        for (int i = 0; i <= 13; i++) {
            for (int j = 0; j < 981; j++) {
                filasInfoS[j] = informacionS.createRow(j);
                celdasInfoS[i][j] = filasInfoS[j].createCell(i);
            }
        }
        informacionS.setColumnWidth(0, 1221);
        informacionS.setColumnWidth(1, 6364);
        informacionS.setColumnWidth(2, 5000);
        informacionS.setColumnWidth(3, 5000);
        informacionS.setColumnWidth(4, 5000);
        informacionS.setColumnWidth(5, 5000);
        informacionS.setColumnWidth(6, 6364);
        informacionS.setColumnWidth(7, 5000);
        informacionS.setColumnWidth(8, 5000);
        informacionS.setColumnWidth(9, 5000);
        informacionS.setColumnWidth(10, 5000);
        informacionS.setColumnWidth(11, 5000);
        informacionS.setColumnWidth(12, 5000);
        informacionS.setColumnWidth(13, 5000);
        filasInfoS[10].setHeightInPoints(18);
        informacionS.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, 3, 3, datos_uni, "UNIVERSIDAD TECNOLÓGICA:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, 4, 4, datos_uni, "CLAVE CGUT:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, 5, 5, datos_uni, "RFC:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, 6, 6, datos_uni, "FECHA DE CREACIÓN:", 0, 1, informacionS);
         filasInfoS[3].setHeightInPoints(17);
         filasInfoS[4].setHeightInPoints(17);
         filasInfoS[5].setHeightInPoints(17);
         filasInfoS[6].setHeightInPoints(17);
        try {
            consulta = "Select nombre, apaterno, amaterno, tipo, cargo, telefono, mail from responsables_universidad ru inner join responsables r on ru.id_responsable = r.id_responsable where ru.tipo = 'RC' and ru.id_universidad = ".concat(universidad).concat(" and ru.id_periodo = ").concat(periodo);
            rs = con.Consultar(consulta);
            if (rs.next()) {
                obj.espaciosEstilosVer(celdasInfoS, 2, 7, 3, 3, datos_uni_sub, uni.getNombreUniversidad(universidad), 0, 1, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 2, 7, 4, 4, datos_uni_sub, uni.getCveCgut(universidad), 0, 1, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 2, 7, 5, 5, datos_uni_sub, uni.getRFC(universidad), 0, 1, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 2, 7, 6, 6, datos_uni_sub, uni.getFechaAcreditacion(universidad), 0, 1, informacionS);
            }
        } catch (SQLException e) {
            System.err.println("Reporte por Universidad, Error de consulta de información general :" + e);
        }
        obj.espaciosEstilosVer(celdasInfoS, 0, 12, 10, 10, "titulo_cat", "INFORMACIÓN GENERAL", 0, 1, informacionS);

        int tmpNserv=0, tmpNedifi=0;
//SERVICIOS
        
//  //TITULO DE LA INFORMACIÓN A PRESENTAR
        obj.espaciosEstilosVer(celdasInfoS, 0, 0, 13, 13, "titulo_indicador_subr", "Servicios que ofrece la universidad", 0, 0, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 1, 1, 16, 17, arial8_bordoble_text, "Servicio", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 2, 2, 16, 17, arial8_bordoble_text, "Lo ofrece la universidad", 0, 1, informacionS);
        filasInfoS[16].setHeightInPoints(29);
        filasInfoS[17].setHeightInPoints(29);
//  //DATOS DE LOS SERVICIOS DE LA UNIVERSODAD
        try {
            consulta ="SELECT * FROM encuesta_servicios ";
            rs = con.Consultar(consulta);
            int z=0;
            while (rs.next()) {//CONSULTA DE TODOS LOS SERVICIOS                
                filasInfoS[18 + z].setHeightInPoints(24);
                obj.espaciosEstilosVer(celdasInfoS, 1, 1, 18 + z, 18 + z, titulo_Cua_arial9Ne, rs.getString("nombre"), 0, 0, informacionS);
                consulta2 = "SELECT * FROM servicios_universidad WHERE id_universidad="+universidad+" AND id_periodo="+periodo+" AND id_servicio="+rs.getInt("id_servicio");
                rs2 = con.Consultar(consulta2);
                if(rs2.next()){//TIENE EL SERVICIO
                    obj.espaciosEstilosVer(celdasInfoS, 2, 2, 18 + z, 18 + z, res1decimal, "X", 0, 0, informacionS);
                }else{//NO TIENE EL SERVICIO
                    obj.espaciosEstilosVer(celdasInfoS, 2, 2, 18 + z, 18 + z, res1decimal, "", 0, 0, informacionS);
                }                
                z++;
            }
            tmpNserv=18 + z;
        } catch (SQLException ex) {
            System.err.println("Reporte por Universidad, Error de consulta de datos Servicios :" + ex);
        } finally {
            con.Desconectar();
        }
        

//EDIFICIOS
//  //TITULO DE LA INFORMACIÓN A PRESENTAR
    obj.espaciosEstilosVer(celdasInfoS, 5, 5, 13, 13, "titulo_indicador_subr", "Tipos de edificios con los que cuenta la universidad", 0, 0, informacionS);
    obj.espaciosEstilosVer(celdasInfoS, 5, 5, 16, 17, arial8_bordoble_text, "Tipo de edificio", 0, 1, informacionS);
    obj.espaciosEstilosVer(celdasInfoS, 6, 6, 16, 17, arial8_bordoble_text, "Cuenta con el la universidad", 0, 1, informacionS);
    filasInfoS[16].setHeightInPoints(29);
    filasInfoS[17].setHeightInPoints(29);
//  //DATOS DE LOS SERVICIOS DE LA UNIVERSODAD
    try {
        consulta ="SELECT * FROM edificios WHERE activo=1 ";
        rs = con.Consultar(consulta);
        int z=0;
        while (rs.next()) {//CONSULTA DE TODOS LOS SERVICIOS                
            filasInfoS[18 + z].setHeightInPoints(24);
            obj.espaciosEstilosVer(celdasInfoS, 5, 5, 18 + z, 18 + z, titulo_Cua_arial9Ne, rs.getString("descripcion"), 0, 0, informacionS);
            consulta2 = "SELECT * FROM edificios_universidad WHERE id_universidad="+universidad+" AND id_periodo="+periodo+" AND id_edificio="+rs.getInt("id_edificio");
            rs2 = con.Consultar(consulta2);
            if(rs2.next()){//TIENE EL SERVICIO
                obj.espaciosEstilosVer(celdasInfoS, 6, 6, 18 + z, 18 + z, res1decimal, "X", 0, 0, informacionS);
            }else{//NO TIENE EL SERVICIO
                obj.espaciosEstilosVer(celdasInfoS, 6, 6, 18 + z, 18 + z, res1decimal, "", 0, 0, informacionS);
            }                
            z++;
        }
        tmpNedifi= 18 + z;
    } catch (SQLException ex) {
        System.err.println("Reporte por Universidad, Error de consulta de datos Edificios :" + ex);
    } finally {
        con.Desconectar();
    }
            
    int fila=0;
    if(tmpNserv > tmpNedifi){
        fila= tmpNserv;
    }else{
        fila= tmpNedifi;
    }

//DOMICILIO
    //  //TITULO DE LA INFORMACIÓN A PRESENTAR
    obj.espaciosEstilosVer(celdasInfoS, 0, 0 , fila + 2, fila + 2, "titulo_indicador_subr", "Domicilio de la universidad", 0, 0, informacionS);
    obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 3, fila + 3, datos_uni, "Estado:", 0, 1, informacionS);
    obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 4, fila + 4, datos_uni, "Municipio:", 0, 1, informacionS);
    obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 5, fila + 5, datos_uni, "Colonia:", 0, 1, informacionS);
    obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 6, fila + 6, datos_uni, "Calle, número y C.P.:", 0, 1, informacionS);
//  //DATOS DEL DOMICIO
  try {
      consulta ="SELECT d.*,e.nombre FROM domicilios_universidad d INNER JOIN estados e ON e.id_estado=d.id_estado"
              + " WHERE id_periodo="+periodo+ " AND id_universidad="+universidad;
      rs = con.Consultar(consulta);
      int z=0;
      if (rs.next()) {//CONSULTA DE TODOS LOS SERVICIOS                
            obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 3, fila + 3, datos_uni_sub, rs.getString("e.nombre").toUpperCase(), 0, 1, informacionS);
            obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 4, fila + 4, datos_uni_sub, rs.getString("municipio"), 0, 1, informacionS);
            obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 5, fila + 5, datos_uni_sub, rs.getString("colonia"), 0, 1, informacionS);
            obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 6, fila + 6, datos_uni_sub, rs.getString("calle")+" "+rs.getString("numero")+", "+rs.getString("codigo_postal"), 0, 1, informacionS);
          z++;
      }
      fila = fila + 5;
  } catch (SQLException ex) {
      System.err.println("Reporte por Universidad, Error de consulta de domicilio :" + ex);
  } finally {
      con.Desconectar();
  }  
    
  
  
  fila = fila + 2;
//CONTACTO
    //  //TITULO DE LA INFORMACIÓN A PRESENTAR
//        obj.espaciosEstilosVer(celdasInfoS, 0, 12, fila, fila, "titulo_cat", "DATOS DE CONTACTO", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 0, fila + 2, fila + 2, "titulo_indicador_subr", "Datos del rector", 0, 0, informacionS);
        
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 3, fila + 3, datos_uni, "Nombre:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 4, fila + 4, datos_uni, "Teléfono:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 5, fila + 5, datos_uni, "Correo Electrónico:", 0, 1, informacionS);
//  //DATOS DEL RECTOR
        try {
            consulta ="SELECT * FROM responsables_universidad WHERE id_universidad = "+universidad+" "
                    + "AND id_periodo = "+periodo+" AND tipo='RU'";
            rs = con.Consultar(consulta);
            int z=0;
            if (rs.next()) {//CONSULTA DE DATOS DEL RECTOR              
                consulta2="SELECT * FROM responsables WHERE id_responsable = "+rs.getInt("id_responsable");
                rs2 = con.Consultar(consulta2); 
                if(rs2.next()){
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 3, fila + 3, datos_uni_sub, rs2.getString("nombre")+" "+rs2.getString("apaterno")+" "+rs2.getString("amaterno"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 4, fila + 4, datos_uni_sub, rs2.getString("telefono"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 5, fila + 5, datos_uni_sub, rs2.getString("mail"), 0, 1, informacionS);
                }
                z++;
            }
        } catch (SQLException ex) {
            System.err.println("Reporte por Universidad, Error de consulta de datos del rector :" + ex);
        } finally {
            con.Desconectar();
        }  
        fila = fila + 5;
 
//  //DATOS DEL REsponsable
        obj.espaciosEstilosVer(celdasInfoS, 0, 0, fila + 2, fila + 2, "titulo_indicador_subr", "Datos del Responsable", 0, 0, informacionS);
        
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 3, fila + 3, datos_uni, "Nombre:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 4, fila + 4, datos_uni, "Teléfono:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 5, fila + 5, datos_uni, "Correo Electrónico:", 0, 1, informacionS);
        try {
            consulta ="SELECT * FROM responsables_universidad WHERE id_universidad = "+universidad+" "
                    + "AND id_periodo = "+periodo+" AND tipo='RC'";
            rs = con.Consultar(consulta);
            int z=0;
            if (rs.next()) {//CONSULTA DE DATOS DEL RESPONSABLE              
                consulta2="SELECT * FROM responsables WHERE id_responsable = "+rs.getInt("id_responsable");
                rs2 = con.Consultar(consulta2); 
                if(rs2.next()){
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 3, fila + 3, datos_uni_sub, rs2.getString("nombre")+" "+rs2.getString("apaterno")+" "+rs2.getString("amaterno"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 4, fila + 4, datos_uni_sub, rs2.getString("telefono"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 5, fila + 5, datos_uni_sub, rs2.getString("mail"), 0, 1, informacionS);
                }
                z++;
            }
        } catch (SQLException ex) {
            System.err.println("Reporte por Universidad, Error de consulta de datos del responsable :" + ex);
        } finally {
            con.Desconectar();
        }  
        fila = fila + 5;
        
//  //DATOS DEL CAPTURISTA
        obj.espaciosEstilosVer(celdasInfoS, 0, 0, fila + 2, fila + 2, "titulo_indicador_subr", "Datos del Capturista", 0, 0, informacionS);
        
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 3, fila + 3, datos_uni, "Nombre:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 4, fila + 4, datos_uni, "Teléfono:", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 0, 1, fila + 5, fila + 5, datos_uni, "Correo Electrónico:", 0, 1, informacionS);
        try {
            consulta ="SELECT * FROM responsables_universidad WHERE id_universidad = "+universidad+" "
                    + "AND id_periodo = "+periodo+" AND tipo='CU'";
            rs = con.Consultar(consulta);
            int z=0;
            if (rs.next()) {//CONSULTA DE DATOS DEL CAPTURISTA               
                consulta2="SELECT * FROM responsables WHERE id_responsable = "+rs.getInt("id_responsable");
                rs2 = con.Consultar(consulta2); 
                if(rs2.next()){
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 3, fila + 3, datos_uni_sub, rs2.getString("nombre")+" "+rs2.getString("apaterno")+" "+rs2.getString("amaterno"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 4, fila + 4, datos_uni_sub, rs2.getString("telefono"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 2, 7, fila + 5, fila + 5, datos_uni_sub, rs2.getString("mail"), 0, 1, informacionS);
                }
                z++;
            }
        } catch (SQLException ex) {
            System.err.println("Reporte por Universidad, Error de consulta de datos del capturista :" + ex);
        } finally {
            con.Desconectar();
        }  
        fila = fila + 5;
        
//DATOS ACADEMICOS        
//  //DATOS GENERALES ACADEMICOS
        obj.espaciosEstilosVer(celdasInfoS, 0, 0, fila + 2, fila + 2, "titulo_indicador_subr", "Datos académicos", 0, 0, informacionS);
        
        obj.espaciosEstilosVer(celdasInfoS, 0, 2, fila + 3, fila + 3, datos_uni, "Número de alumnos de nuevo ingreso:", 0, 1, informacionS);        
        obj.espaciosEstilosVer(celdasInfoS, 0, 2, fila + 4, fila + 4, datos_uni, "Matrícula total:", 0, 1, informacionS);        
        obj.espaciosEstilosVer(celdasInfoS, 0, 2, fila + 5, fila + 5, datos_uni, "Número de profesores de tiempo completo:", 0, 1, informacionS);        
        obj.espaciosEstilosVer(celdasInfoS, 0, 2, fila + 6, fila + 6, datos_uni, "Número de profesores de asignatura:", 0, 1, informacionS);        
        try {
            consulta ="SELECT * FROM datos_universidad WHERE id_universidad = "+universidad+" "
                    + "AND id_periodo = "+periodo;
            rs = con.Consultar(consulta);
            int z=0;
            if (rs.next()) {//CONSULTA DE DATOS ACADEMICOS GENERALES               
                    obj.espaciosEstilosVer(celdasInfoS, 3, 7, fila + 3, fila + 3, datos_uni_sub, rs.getString("nuevo_ingreso"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 3, 7, fila + 4, fila + 4, datos_uni_sub, rs.getString("matricula_total"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 3, 7, fila + 5, fila + 5, datos_uni_sub, rs.getString("prof_tc"), 0, 1, informacionS);
                    obj.espaciosEstilosVer(celdasInfoS, 3, 7, fila + 6, fila + 6, datos_uni_sub, rs.getString("prof_asig"), 0, 1, informacionS);
                z++;
            }
        } catch (SQLException ex) {
            System.err.println("Reporte por Universidad, Error de consulta de datos academicos generales :" + ex);
        } finally {
            con.Desconectar();
        }  
        fila = fila + 6;
        fila = fila + 2;
//PROGRAMAS EDUCATIVOS
//  //TITULO DE LA INFORMACIÓN A PRESENTAR
        obj.espaciosEstilosVer(celdasInfoS, 0, 0, fila , fila, "titulo_indicador_subr", "Programas educativos", 0, 0, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 1, 2, fila + 2, fila + 3, titulo_Cua_arial9Ne, "Nivel", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 3, 4, fila + 2, fila + 3, titulo_Cua_arial9Ne, "Programa educativo", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 5, 5, fila + 2, fila + 3, titulo_Cua_arial9Ne, "Año de incorporacion a la Universidad", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 6, 6, fila + 2, fila + 3, titulo_Cua_arial9Ne, "Clasificación", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 7, 7, fila + 2, fila + 3, titulo_Cua_arial9Ne, "Alumnos de nuevo ingreso", 0, 1, informacionS);
        obj.espaciosEstilosVer(celdasInfoS, 8, 8, fila + 2, fila + 3, titulo_Cua_arial9Ne, "Matrícula total", 0, 1, informacionS);
        filasInfoS[fila + 2].setHeightInPoints(18);
        filasInfoS[fila + 3].setHeightInPoints(18);    
//  //DATOS DE LOS PROGRAMAS EDUCATIVOS
        try {
            consulta ="SELECT n.descripcion, pe.nombre_programa, pu.* FROM pe_universidad pu INNER JOIN programa_educativo pe "
                    + "ON pe.id_pe=pu.id_pe INNER JOIN nivel_pe n ON n.id_nivel=pe.id_nivel "
                    + "WHERE id_universidad = "+universidad+" AND id_periodo ="+periodo;
            rs = con.Consultar(consulta);
            int z=0;
            fila = fila +4;
            while (rs.next()) {//CONSULTA DE TODOS LOS SERVICIOS                
                obj.espaciosEstilosVer(celdasInfoS, 1, 2, fila + z, fila + z, res1decimal, rs.getString("descripcion"), 0, 1, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 3, 4, fila + z, fila + z, res1decimal, rs.getString("nombre_programa"), 0, 1, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 5, 5, fila + z, fila + z, res1decimal, rs.getString("anio_incorporacion"), 0, 0, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 6, 6, fila + z, fila + z, res1decimal, rs.getString("clasificacion"), 0, 0, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 7, 7, fila + z, fila + z, res1decimal, rs.getString("nuevo_ingreso"), 0, 0, informacionS);
                obj.espaciosEstilosVer(celdasInfoS, 8, 8, fila + z, fila + z, res1decimal, rs.getString("matricula_total"), 0, 0, informacionS);
                z++;
            }
        } catch (SQLException ex) {
            System.err.println("Reporte por Universidad, Error de consulta de programas educativos :" + ex);
        } finally {
            con.Desconectar();
        }
        
        return obj;
    }
}