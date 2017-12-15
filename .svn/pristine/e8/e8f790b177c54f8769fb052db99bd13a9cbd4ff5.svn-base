package com.mecasut.admon;

import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Coleccion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *
 * @author Juan García Barradas, Irvin Joshua LuÃ©vano GarcÃ­a, Cuauhtemoc Medina Muñoz
 */
public class CgutInterfaceAction extends org.apache.struts.action.Action {
//    
    ConexionMySQL conexion = new ConexionMySQL();

    private static final String SUCCESS = "success";
//
//    @Override
//    public ActionForward execute(ActionMapping mapping, ActionForm form,
//            HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//        ArrayList global = new ArrayList();
//        HttpSession sesion = request.getSession(false);
//        CgutInterfaceForm frm = (CgutInterfaceForm) form;
//
//
//        /*----------------------------------------------------------------Al cambiar un combo--------------------------------------------------------------------*/
//        /**
//         * ----------------------------------------------Cambios de estado de
//         * Activo a Inactivo----------------------------------------------------
//         */
//        

//

//        
//      
//
//        
//        if (frm.getCboUni2() >= 0) {
//            ArrayList altareponsables = new ArrayList();
//            String sql = "select responsables.id_responsable, responsables.nombre,  responsables.apaterno, responsables.amaterno from  responsables inner join universidades on  responsables.id_universidad=universidades.id_universidad where universidades.id_universidad =".concat(String.valueOf(frm.getCboUni2()));
//            ResultSet rs = conexion.Consultar(sql);
//            while (rs.next()) {
//                altareponsables.add(new Coleccion(rs.getInt(1), rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)));
//            }
//            frm.setResponsa2(altareponsables);
//            conexion.Desconectar();
//        }
//

//

//

//

//
//        /*
//         * 
//         * 
//         * USUARIOS
//         * 
//         */
//      
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        if (frm.getAsk().equals("bajaCategoria")) {
//            if (frm.getCboCategorias() > 0) {
//                String sql = "select activo from system_categorias where id_categoria = ".concat(String.valueOf(frm.getCboCategorias()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) {
//                        String sql2 = "update system_categorias set activo = 0 where id_categoria = ".concat(String.valueOf(frm.getCboCategorias()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setCategoriaActiva("Activar CategorÃ­a");
//                        return mapping.findForward(SUCCESS);
//                    } else {
//                        String sql3 = "update system_categorias set activo = 1 where id_categoria = ".concat(String.valueOf(frm.getCboCategorias()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setCategoriaActiva("Desactivar CategorÃ­a");
//                        return mapping.findForward(SUCCESS);
//                    }
//                }
//            }
//        }


//        if (frm.getAsk().equals("bajaCausa")) {
//            if (frm.getCboCausas() > 0) {
//                String sql = "select activo from causas_desercion where id_causa = ".concat(String.valueOf(frm.getCboCausas()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) {
//                        String sql2 = "update causas_desercion set activo = 0 where id_causa = ".concat(String.valueOf(frm.getCboCausas()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setCausaActivo("Activar Causa");
//                        return mapping.findForward(SUCCESS);
//                    } else {
//                        String sql3 = "update causas_desercion set activo = 1 where id_causa = ".concat(String.valueOf(frm.getCboCausas()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setCausaActivo("Desactivar Causa");
//                        return mapping.findForward(SUCCESS);
//                    }
//                }
//            }
//        }


//

//        
//        
//        if (frm.getAsk().equals("bajaUsuario")) {
//            if (frm.getCboUsuario() > 0) {
//                String sql = "select activo from usuarios where id_usuario = ".concat(String.valueOf(frm.getCboUsuario()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) { //desactiva usuario
//                        String sql2 = "update usuarios set activo = 0 where id_usuario = ".concat(String.valueOf(frm.getCboUsuario()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setUsuariosActivos("Activar Usuario");
//                    } else { // activar usuario
//                        String sql3 = "update usuarios set activo = 1 where id_usuario = ".concat(String.valueOf(frm.getCboUsuario()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setUsuariosActivos("Desactivar Usuario");
//                    }
//                }
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        

//        
//        /**
//         * ------------------------------------------Guardar Datos nuevos o
//         * modificar el existente
//         * Seleccionado---------------------------------------*
//         */

//        if (frm.getAsk().equals("guardarIndicador")) {
//            if (frm.getCboIndicadores() > 0) {
//                String sql = "update system_indicadores set id_categoria = '".concat(String.valueOf(frm.getCboCategorias2())).concat("', descripcion='").concat(frm.getNombreD()).concat("', archivo_jsp ='").concat(frm.getArJsp()).concat("', archivo_js='").concat(frm.getArjs()).concat("' where id_indicador = '").concat(String.valueOf(frm.getCboIndicadores())).concat("'");
//                conexion.Modificar(sql);
//                conexion.Desconectar();
//                nuevoIndicador(frm);
//                //frm.setIndicadores();
//                return mapping.findForward(SUCCESS);
//            } else {
//                String sql2 = "insert into system_indicadores values(null,'".concat(String.valueOf(frm.getCboCategorias2())).concat("','").concat(sesion.getAttribute("idPeriodo").toString()).concat("','").concat(frm.getNombreD()).concat("','").concat(frm.getArJsp()).concat("','").concat(frm.getArjs()).concat("',1)");
//                conexion.Insertar(sql2);
//                conexion.Desconectar();
//                nuevoIndicador(frm);
//                //frm.setIndicadores();
//                return mapping.findForward(SUCCESS);
//            }
//        }

//        if (frm.getAsk().equals("guardarCategoPreg")) {
//            if (frm.getCboCategoPre() > 0) {
//                String sql = "update encuesta_servicios set nombre = '".concat(frm.getNombreCategoPregu()).concat("', descripcion ='").concat(frm.getNombreCategoDesc()).concat("' where id_servicio ='").concat(String.valueOf(frm.getCboCategoPre())).concat("'");
//                conexion.Modificar(sql);
//                conexion.Desconectar();
//                nuevaCategoPre(frm);
//                frm.setCategoPre();
//                return mapping.findForward(SUCCESS);
//            } else {
//                String sql2 = "insert into encuesta_servicios values(null,'".concat(frm.getNombreCategoPregu()).concat("','").concat(frm.getNombreCategoDesc()).concat("')");
//                conexion.Insertar(sql2);
//                conexion.Desconectar();
//                nuevaCategoPre(frm);
//                frm.setCategoPre();
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        if (frm.getAsk().equals("guardarUsuario")) {
//            String sql;
//            //frm.setPassword(frm.getPassword());
//            if ( frm.getCboUniUsuarios() == 0 ){//usuarios de las cgut
//                if ( frm.getCboUsuario() == -1 ){ //nuevo registro de la cgut
//                    try {
//                        sql = "INSERT INTO usuarios VALUES(null,'".concat(String.valueOf(frm.getCboResponsables2()))
//                        .concat("',0,'").concat(frm.getNombreUsu()).concat("','")
//                        .concat(frm.getPassword()).concat("','CG',1)");
//                        conexion.Insertar(sql);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(CgutInterfaceAction.class.getName()).log(Level.SEVERE, null, ex);
//                        System.out.println("BD ERROR: Insercion fallida de usuario\n " + ex.getMessage());
//                    } finally {
//                        conexion.Desconectar();
//                    }
//                }else{//actualizacion de un registro de la cgut
//                    try {
//                        sql = "UPDATE usuarios SET id_responsable ='".concat(String.valueOf(frm.getCboResponsables2()))
//                        .concat("', user='").concat(frm.getNombreUsu()).concat("', password = '").concat(frm.getPassword())
//                        .concat("' WHERE id_usuario = '").concat(String.valueOf(frm.getCboUsuario())).concat("'");
//                        conexion.Insertar(sql);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(CgutInterfaceAction.class.getName()).log(Level.SEVERE, null, ex);
//                        System.out.println("BD ERROR: Modificacion fallida de usuario \n" + ex.getMessage());
//                    } finally {
//                        conexion.Desconectar();
//                    }
//                }
//            } else { //usuarios de las universidades
//                int rows = 0;
//                try {
//                    sql = "UPDATE usuarios SET user='".concat(frm.getNombreUsu())
//                    .concat("', password='").concat(frm.getPassword())
//                    .concat("' WHERE id_univ=").concat(String.valueOf(frm.getCboUniUsuarios())).concat(" AND tipo='UT'");
//                    rows = conexion.Modificar(sql);
//                    if (rows == 0){
//                        sql = "INSERT INTO usuarios (id_univ, user, password, tipo, activo)".concat(" VALUES(")
//                        .concat(String.valueOf(frm.getCboUniUsuarios())).concat(",'").concat(frm.getNombreUsu()).concat("','")
//                        .concat(frm.getPassword()).concat("', 'UT', 1)");
//                        conexion.Insertar(sql);
//                    }
//                } catch (SQLException ex) {
//                    Logger.getLogger(CgutInterfaceAction.class.getName()).log(Level.SEVERE, null, ex);
//                    System.out.println("BD ERROR: Modificacion o insercion fallida de usuario simple \n" + ex.getMessage());
//                } finally {
//                    conexion.Desconectar();
//                }
//            }
//            frm.setCboUsuario(-1);
//            frm.setCboUniUsuarios(-2);
//            frm.setNombreUsu("");
//            frm.setPassword("");
//            frm.setConfirmacion("");
//        }
//        

//        if (frm.getAsk().equals("guardarEncuesta")) {
//            if (frm.getCboEncuestas() > 0) {
//                String sql = "UPDATE encuestas SET nombre ='".concat(frm.getNombreEncuestas())
//                    .concat("', id_nivel='").concat(String.valueOf(frm.getCboNivelEncuesta()))
//                    .concat("' WHERE id_encuesta='").concat(String.valueOf(frm.getCboEncuestas())).concat("'");
//                try{
//                    conexion.Modificar(sql);
//                }catch (SQLException ex){
//                    Logger.getLogger(CgutInterfaceAction.class.getName()).log(Level.SEVERE, null, ex);
//                    System.out.println("BDERROR: Error en la actualización de encuestas: "+ex.getMessage());
//                }finally{
//                    conexion.Desconectar();
//                }
//                nuevaEncuesta(frm);
//                frm.setEncuestas();
//                return mapping.findForward(SUCCESS);
//            } else {
//                String sql2 = "INSERT INTO encuestas () VALUES(null,'"
//                    .concat(frm.getNombreEncuestas()).concat("','")
//                    .concat(String.valueOf(frm.getCboNivelEncuesta())).concat("','")
//                    .concat(String.valueOf(frm.getCboEncuestaIndicador())).concat("','1')");
//                try{
//                    conexion.Insertar(sql2);
//                }catch (SQLException ex){
//                    Logger.getLogger(CgutInterfaceAction.class.getName()).log(Level.SEVERE, null, ex);
//                    System.out.println("BDERROR: Error en la actualización de encuestas: "+ex.getMessage());
//                }finally{
//                    conexion.Desconectar();
//                }
//                nuevaEncuesta(frm);
//                frm.setEncuestas();
//                return mapping.findForward(SUCCESS);
//            }
//        }



//        
//        
//        if (frm.getAsk().equals("recargarNiveles")) {
//            try {
//                ArrayList niveles = new ArrayList();
//                String consulta = "SELECT id_nivel, nombre FROM nivel_pe WHERE activo=1 AND id_nivel NOT IN (SELECT id_nivel "
//                        + "FROM encuestas WHERE activo=1 AND indicador="
//                        .concat(String.valueOf(frm.getCboEncuestaIndicador())).concat(") AND id_nivel!=-1");
//                ResultSet rs = conexion.Consultar(consulta);
//                while (rs.next()){
//                    niveles.add(new Coleccion(rs.getInt("id_nivel"), rs.getString("nombre")));
//                }
//                frm.setNivelEncuesta(niveles);
//            } catch (SQLException ex) {
//                System.out.println("BDERROR: CgutInterfaceAction : " + ex.getMessage());
//            }
//            return mapping.findForward(SUCCESS);
//        }
//
//        
//        if (frm.getAsk().equals("buscarPregEncuesta")) {
//            if (frm.getCmbPregEnc() > 0) { //si el elemento seleccionado es mayor al inicial
//                if (frm.getCmbPregEnc() == 3){//llenar el combo de categorias
//                    frm.setCategoPre2(llenarDatos("SELECT id_servicio, nombre FROM encuesta_servicios"));
//                    frm.setTxtNombrePreguntas("");
//                }
//                if(frm.getCmbPregEnc() != 3){ //llenar el combo de preguntas segun la encuesta seleccionada
//                    String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
//                            + "WHERE id_encuesta='".concat(String.valueOf(frm.getCmbPregEnc())).concat("'");
//                    frm.setPreguntas(llenarDatos(consulta));
//                    frm.setTxtNombrePreguntas("");
//                }
//            } else { //el elemento seleccionado es el inicial
//                nuevaPregunta(frm); //reiniciar los valores del formulario
//            }
//            return mapping.findForward(SUCCESS);
//        }
//        
//        //--------------------------------------
//        frm.setCategoPre2(llenarDatos("SELECT id_servicio, nombre FROM encuesta_servicios"));
//        if (frm.getAsk().equals("buscaCategoPre2")) {
//            ArrayList resultados = new ArrayList();
//            if (frm.getCboCategoPre2() > 0) {
//                String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
//                        + "WHERE id_servicio=".concat(String.valueOf(frm.getCboCategoPre2()))
//                        .concat(" AND id_encuesta=").concat(String.valueOf(String.valueOf(frm.getCmbPregEnc())));
//                frm.setPreguntas(llenarDatos((consulta)));
//            } else { //si el item seleccionado es el inicial, entonces el llenado de las preguntas es vacio
//                frm.setPreguntas(resultados); //
//            }
//            return mapping.findForward(SUCCESS);
//        }
//        
//        
//        
//        
//        
//        //---------------------------------------
//        if (frm.getCmbPregEnc()==3){
//            String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
//                        + "WHERE id_servicio=".concat(String.valueOf(frm.getCboCategoPre2()))
//                        .concat(" AND id_encuesta=").concat(String.valueOf(String.valueOf(frm.getCmbPregEnc())));
//                frm.setPreguntas(llenarDatos((consulta)));
//        }else{
//            String consulta = "SELECT id_pregunta, pregunta FROM encuesta_preguntas "
//                            + "WHERE id_encuesta='".concat(String.valueOf(frm.getCmbPregEnc())).concat("'");
//                    frm.setPreguntas(llenarDatos(consulta));
//        }
//        if (frm.getAsk().equals("buscarPregPregunta")) {
//            
//            if (frm.getCmbPregPreguntas() > 0) {
//                try{
//                    //Selecciona de la base de datos la pregunta y si estÃ¡ activo
//                    String sql = "SELECT pregunta, activo, nombre_pregunta FROM encuesta_preguntas WHERE id_pregunta="
//                        .concat(String.valueOf(String.valueOf(frm.getCmbPregPreguntas())));
//                    ResultSet rs = conexion.Consultar(sql);
//                    if (rs.next()) {
//                        frm.setTxtNombrePreguntas(rs.getString("pregunta"));//asgina la pregunta al campo
//                        frm.setTxtPalabraClavePreguntas(rs.getString("nombre_pregunta"));
//                        if (rs.getBoolean("activo")) {//cambia el boton (activar/desactivar)
//                            frm.setPreguntasActiva("Desactivar Pregunta");
//                        } else {
//                            frm.setPreguntasActiva("Activar Pregunta");
//                        }
//                    }
//                } catch (SQLException ex) {
//                    Logger.getLogger(CgutInterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
//                } finally {
//                    conexion.Desconectar();
//                    return mapping.findForward(SUCCESS);
//                }
//            } else {
//                frm.setTxtNombrePreguntas("");
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        
//        if (frm.getAsk().equals("bajaPreguntas")) {
//            if (frm.getCmbPregPreguntas()> 0) {
//                String sql = "select activo from  encuesta_preguntas where id_pregunta = ".concat(String.valueOf(frm.getCmbPregPreguntas()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) {
//                        String sql2 = "update encuesta_preguntas set activo = 0 where id_pregunta = ".concat(String.valueOf(frm.getCmbPregPreguntas()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setPreguntasActiva("Activar Pregunta");
//                        return mapping.findForward(SUCCESS);
//                    } else {
//                        String sql3 = "update encuesta_preguntas set activo = 1 where id_pregunta = ".concat(String.valueOf(frm.getCmbPregPreguntas()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setPreguntasActiva("Desactivar Pregunta");
//                        return mapping.findForward(SUCCESS);
//                    }
//                }
//            }
//        }
//        
//        
//        
//        if (frm.getAsk().equals("guardarPregunta")) {
//            try{
//                boolean nuevo = false;
//                boolean conCateg = false;
//                if (frm.getCmbPregPreguntas() == -1){//nuevo registro
//                    nuevo=true;
//                    if (frm.getCmbPregEnc() == 3){ // con categorias
//                        conCateg=true;
//                    }
//                }
//                String consulta = "";
//                if (nuevo){ //nuevo registro
//                    if (conCateg){ //con categorias
//                        consulta = "INSERT INTO encuesta_preguntas (id_encuesta, id_servicio, pregunta, activo, nombre_pregunta) VALUES ('"
//                                .concat(String.valueOf(frm.getCmbPregEnc())).concat("', ")
//                                .concat(String.valueOf(frm.getCboCategoPre2())).concat("', ")
//                                .concat(frm.getTxtNombrePreguntas()).concat("', 1,'")
//                                .concat(frm.getTxtPalabraClavePreguntas()).concat("')");
//
//                    }else{//nuevo sin categorias
//                        consulta = "INSERT INTO encuesta_preguntas (id_encuesta, pregunta, activo, nombre_pregunta) VALUES ('"
//                                .concat(String.valueOf(frm.getCmbPregEnc())).concat("', '")
//                                .concat(frm.getTxtNombrePreguntas()).concat("', 1,'")
//                                .concat(frm.getTxtPalabraClavePreguntas()).concat("')");
//                    }
//                    conexion.Insertar(consulta);
//                    conexion.Desconectar();
//                    nuevaPregunta(frm);
//                }else{//actualizacion
//                    consulta = "UPDATE encuesta_preguntas SET "
//                            .concat("pregunta='").concat(frm.getTxtNombrePreguntas())
//                            .concat("', nombre_pregunta='".concat(String.valueOf(frm.getTxtPalabraClavePreguntas())).concat("' ")
//                            .concat("WHERE id_pregunta='").concat(String.valueOf(frm.getCmbPregPreguntas())))
//                            .concat("'");
//                    System.out.println(consulta);
//                    conexion.Modificar(consulta);
//                    conexion.Desconectar();
//                    nuevaPregunta(frm);
//                }
//                return mapping.findForward(SUCCESS);
//            }catch (SQLException ex) {
//                Logger.getLogger(GlobalAction.class.getName()).log(Level.SEVERE, null, ex);
//                nuevaPregunta(frm);
//            } finally {
//                conexion.Desconectar();
//            }
//        }
//        
//        

//        /**
//         * ------------------------------------Busquedas especificas para llenas
//         * los Datos de las cajas de texto---------------------------------*
//         */
//        if (frm.getAsk().equals("buscaUsuario")) {
//            if (frm.getCboUsuario() > 0) {
//                String sql = "select usuarios.activo, id_usuario, responsables.id_responsable, user, password, "
//                        + "tipo from usuarios inner join responsables on usuarios.id_responsable "
//                        + "= responsables.id_responsable where id_usuario =".concat(String.valueOf(frm.getCboUsuario()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    frm.setCboResponsables2(rs.getInt("id_responsable"));
//                    frm.setNombreUsu(rs.getString("user"));
//                    if (!rs.getString("tipo").equals("CG")) {
//                        if (rs.getBoolean(1)) {
//                            frm.setUsuariosActivos("Desactivar Usuario");
//                        } else {
//                            frm.setUsuariosActivos("Activar Usuario");
//                        }
//                    }
//                }
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            } else {
//                nuevoUsuario(frm);
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        

//        
//        
//        
//        if (frm.getAsk().equals("buscaCategoPre")) {
//            if (frm.getCboCategoPre() > 0) {
//                String sql = "select * from encuesta_servicios where id_servicio =".concat(String.valueOf(frm.getCboCategoPre()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    frm.setNombreCategoPregu(rs.getString(2));
//                    frm.setNombreCategoDesc(rs.getString(3));
//                }
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            } else {
//                nuevaCategoPre(frm);
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        
//        
//        if (frm.getAsk().equals("buscaInidicador")) {
//            if (frm.getCboCategorias2() > 0) {
//                frm.setIndicadores(llenarDatos("select id_indicador, descripcion from system_indicadores inner join system_categorias on system_indicadores.id_categoria=system_categorias.id_categoria where system_categorias.id_categoria =".concat(String.valueOf(frm.getCboCategorias2()))));
//                return mapping.findForward(SUCCESS);
//            } else {
//                nuevoIndicador(frm);
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        
//        
//        frm.setIndicadores(llenarDatos("select id_indicador, descripcion from system_indicadores inner join system_categorias on system_indicadores.id_categoria=system_categorias.id_categoria where system_categorias.id_categoria =".concat(String.valueOf(frm.getCboCategorias2()))));
//        if (frm.getAsk().equals("InidicadorEspecifico")) {
//            if (frm.getCboIndicadores() > 0) {
//                String sql = "select * from system_indicadores where id_indicador =" + String.valueOf(frm.getCboIndicadores());
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    frm.setNombreD(rs.getString(4));
//                    frm.setArJsp(rs.getString(5));
//                    frm.setArjs(rs.getString(6));
//                    if (rs.getBoolean(7)) {
//                        frm.setIndicadorActivo("Desactivar Indicador");
//                    } else {
//                        frm.setIndicadorActivo("Activar Indicador");
//                    }
//                }
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            } else {
//                frm.setNombreD("");
//                frm.setArJsp("");
//                frm.setArjs("");
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        if (frm.getAsk().equals("bajaIndicador")) {
//            if (frm.getCboIndicadores() > 0) {
//                String sql = "select activo from system_indicadores where id_indicador = ".concat(String.valueOf(frm.getCboIndicadores()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) {
//                        String sql2 = "update system_indicadores set activo = 0 where id_indicador = ".concat(String.valueOf(frm.getCboIndicadores()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setIndicadorActivo("Activar Indicador");
//                        return mapping.findForward(SUCCESS);
//                    } else {
//                        String sql3 = "update system_indicadores set activo = 1 where id_indicador = ".concat(String.valueOf(frm.getCboIndicadores()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setIndicadorActivo("Desactivar Indicador");
//                        return mapping.findForward(SUCCESS);
//                    }
//                }
//            }
//        }
//        

//        
//       
//        
//        if (frm.getAsk().equals("bajaResponsables")) {
//            if (frm.getCboResponsables() > 0) {
//                String sql = "select activo from responsables where id_responsable = ".concat(String.valueOf(frm.getCboResponsables()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) {
//                        String sql2 = "update responsables set activo = 0 where id_responsable = ".concat(String.valueOf(frm.getCboResponsables()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setResponsablesActivo("Activar Responsable");
//                    } else {
//                        String sql3 = "update responsables set activo = 1 where id_responsable = ".concat(String.valueOf(frm.getCboResponsables()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setResponsablesActivo("Desactivar Responsable");
//                    }
//                }
//                return mapping.findForward(SUCCESS);
//            }
//        }
//        
//        
//        if (frm.getAsk().equals("bajaCategoriaPre")) {
//            if (frm.getCboCategoPre() > 0) {
//                String sql = "select activo from encuesta_servicios where id_servicio = ".concat(String.valueOf(frm.getCboCategoPre()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) {
//                        String sql2 = "update encuesta_servicios set activo = 0 where id_servicio = ".concat(String.valueOf(frm.getCboCategoPre()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setCategoriaPreActiva("Activar CategorÃ­a");
//                        return mapping.findForward(SUCCESS);
//                    } else {
//                        String sql3 = "update encuesta_servicios set activo = 1 where id_servicio = ".concat(String.valueOf(frm.getCboCategoPre()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setCategoriaPreActiva("Desactivar CategorÃ­a");
//                        return mapping.findForward(SUCCESS);
//                    }
//                }
//            }
//        }
//

//

//

//
//

//
//        if (frm.getAsk().equals("bajaEncuestasPre")) {
//            if (frm.getCboEncuestas() > 0) {
//                String sql = "select activo from encuestas where id_encuesta = ".concat(String.valueOf(frm.getCboEncuestas()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    if (rs.getInt(1) == 1) {
//                        String sql2 = "update encuestas set activo = 0 where id_encuesta = ".concat(String.valueOf(frm.getCboEncuestas()));
//                        conexion.Modificar(sql2);
//                        conexion.Desconectar();
//                        frm.setEncuestasPreActiva("Activar Encuesta");
//                        return mapping.findForward(SUCCESS);
//                    } else {
//                        String sql3 = "update encuestas set activo = 1 where id_encuesta = ".concat(String.valueOf(frm.getCboEncuestas()));
//                        conexion.Modificar(sql3);
//                        conexion.Desconectar();
//                        frm.setEncuestasPreActiva("Desactivar Encuesta");
//                        return mapping.findForward(SUCCESS);
//                    }
//                }
//            }
//        }
//        if (frm.getAsk().equals("buscaEncuestas")) {
//            if (frm.getCboEncuestas() > 0) {
//                String sql = "SELECT id_encuesta, nombre, id_nivel, indicador FROM encuestas WHERE id_encuesta ="
//                        .concat(String.valueOf(frm.getCboEncuestas()));
//                ResultSet rs = conexion.Consultar(sql);
//                if (rs.next()) {
//                    frm.setCboNivelEncuesta(rs.getInt("id_nivel"));
//                    frm.setCboEncuestaIndicador(rs.getInt("indicador"));
//                    frm.setNombreEncuestas(rs.getString(2));
//                }
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            } else {
//                nuevaEncuesta(frm);
//                conexion.Desconectar();
//                return mapping.findForward(SUCCESS);
//            }
//        }
//
//        
//        
//        /**
//         * ----------------------------Utilizados para limpiar los datos de las
//         * cajas de texto si se desea un nuevo registro------------------------*
//         */
//        if (frm.getAsk().equals("nuevaEncuesta")) {
//            nuevaEncuesta(frm);
//            return mapping.findForward(SUCCESS);
//        }
//        if (frm.getAsk().equals("nuevaPregunta")) {
//            nuevaPregunta(frm);
//            return mapping.findForward(SUCCESS);
//        }



//        if (frm.getAsk().equals("nuevoProgramaE")) {
//            nuevoProgramaE(frm);
//            return mapping.findForward(SUCCESS);
//        }

//        if (frm.getAsk().equals("nuevoResponsable")) {
//            frm.setCboUni(0);
//            frm.setCboResponsables(0);
//            frm.setNombreResponsable("");
//            frm.setNombreAPResponsable("");
//            frm.setNombreAMResponsable("");
//            frm.setNombreCargo("");
//            frm.setNombreTelefono("");
//            frm.setNombreMail("");
//            frm.setNombreFechaI("");
//            frm.setNombreFechaR("");
//            //frm.setUniActivo("");
//            return mapping.findForward(SUCCESS);
//        }
//        if (frm.getAsk().equals("nuevoUsuario")) {
//            frm.setCboUni(0);
//            frm.setCboUsuario(-1);
//            frm.setCboResponsables2(-1);
//            frm.setNombreUsu("");
//            frm.setPassword("");
//            frm.setConfirmacion("");
//            frm.setCambioDeResponsable(false);
//            return mapping.findForward(SUCCESS);
//        }

//        if (frm.getAsk().equals("nuevoIndicador")) {
//            nuevoIndicador(frm);
//            return mapping.findForward(SUCCESS);
//        }
//        if (frm.getAsk().equals("nuevaCategoPre")) {
//            nuevaCategoPre(frm);
//            return mapping.findForward(SUCCESS);
//        }
//
//        /*Se realiza al cambiar cualquier objeto dentro del combo de Universidades, en caso de que el id sea mayor a cero se harÃ¡ una consulta
//         a la base de datos, en caso contrario, se limpiarÃ¡n los campÃ³s para el ingreso de un nuevo registro de universidad.
//         */
//        
//
//        /*Se realiza al cambiar cualquier objeto dentro del combo de Ãreas, en caso de que el id sea mayor a cero se harÃ¡ una consulta
//         a la base de datos, en caso contrario, se limpiarÃ¡n los campÃ³s para el ingreso de un nuevo registro de Ã¡rea.
//         */

//
//
//        //Busca dentro de la base de datos las Ãreas activas, para asÃ­, llenar los campos de las Familias.

//
//

//
//        //Busca dentro de la base de datos las Ãreas activas, para asÃ­, llenar el combo de las Familias en el formulario de Programas Educativos.
//        if (frm.getAsk().equals("buscaAreaProg")) {
//            if (frm.getCboAreaProg() > 0) {
//                frm.setFamiliasProg(llenarDatos("Select id_familia,nombre_familia from familia_pe where activo = 1 and id_area=".concat(String.valueOf(frm.getCboAreaProg()))));
//                frm.setCboNivel(-2);
//                frm.setNombrePrograma("");
//                frm.setProgramaActivo("");
//                frm.setVersionPrograma("");
//                frm.setNomenclaturaProg("");
//            } else {
//                nuevoPrograma(frm, false);
//            }
//            return mapping.findForward(SUCCESS);
//        }
//
//        frm.setFamiliasProg(llenarDatos("Select id_familia,nombre_familia from familia_pe where activo = 1 and id_area=".concat(String.valueOf(frm.getCboAreaProg()))));
//        if (frm.getAsk().equals("buscaFamiliaProg")) {
//            if (frm.getCboFamiliaProg() > 0) {
//                frm.setProgramasEducativos(llenarDatos("select id_pe, CONCAT(nombre_programa,' (',version,')') from programa_educativo where activo=1 and id_familia=".concat(String.valueOf(frm.getCboFamiliaProg()))));
//            } else {
//                frm.setCboPE(-1);
//                frm.setNombrePrograma("");
//                frm.setProgramaActivo("");
//                frm.setNomenclaturaProg("");
//                frm.setVersionPrograma("");
//                frm.setCboNivel(-1);
//            }
//            return mapping.findForward(SUCCESS);
//        }
//
//        frm.setProgramasEducativos(llenarDatos("select id_pe, CONCAT(nombre_programa,' (',version,')') from programa_educativo where id_familia=".concat(String.valueOf(frm.getCboFamiliaProg()))));
//        if (frm.getAsk().equals("buscaPrograma")) {
//            if (frm.getCboPE() > 0) {
//                ResultSet rs = conexion.Consultar("select * from programa_educativo pe inner join nivel_pe np on pe.id_nivel=np.id_nivel where pe.id_pe=".concat(String.valueOf(frm.getCboPE())));
//                if (rs.next()) {
//                    frm.setNombrePrograma(rs.getString("nombre_programa"));
//                    frm.setNomenclaturaProg(rs.getString("nomenclatura"));
//                    frm.setCboNivel(rs.getInt("id_nivel"));
//                    frm.setVersionPrograma(rs.getString("version"));
//                    if (rs.getBoolean("activo")) {
//                        frm.setProgramaActivo("Desactivar Programa educativo");
//                    } else {
//                        frm.setProgramaActivo("Activar Programa educativo");
//                    }
//                }
//            } else {
//                frm.setNombrePrograma("");
//                frm.setProgramaActivo("");
//                frm.setNomenclaturaProg("");
//                frm.setVersionPrograma("");
//                frm.setCboNivel(-1);
//            }
//            return mapping.findForward(SUCCESS);
//        }
//
//        /*------------------------------------------------------------------BotÃ³n nuevo--------------------------------------------------------------------*/

//
//        //AcciÃ³n al presionar el botÃ³n "Nuevo" dentro del formulario de Universidades.

//
//        if (frm.getAsk().equals("nuevoPrograma")) {
//            nuevoPrograma(frm, false);
//            return mapping.findForward(SUCCESS);
//        }
//
//        /*----------------------------------------------------------------Al dar de baja----------------------------------------------------------------------*/
//
//
//        
//
//        

//
//        

//        if (frm.getAsk().equals("bajaPrograma")) {
//            if (frm.getCboPE() > 0) {
//                String consulta = "select activo from programa_educativo where id_pe=".concat(String.valueOf(frm.getCboPE()));
//                String update1 = "update programa_educativo set activo=";
//                String update2 = " where id_pe=";
//                if (bajaDatos(consulta, update1, update2, frm.getCboPE()) == 1) {
//                    frm.setProgramaActivo("Activar Programa educativo");
//                } else {
//                    frm.setProgramaActivo("Desactivar Programa educativo");
//                }
//            }
//            return mapping.findForward(SUCCESS);
//        }
//
//        /*----------------------------------------------------------------Al guardar datos--------------------------------------------------------------------*/
//
//        /*Realiza una actualizaciÃ³n de los datos, si el resultado de las filas es igual a 0, se lleva a cabo una inserciÃ³n.
//         * MODIFICACIÃ“N POSTERIOR PARA VALIDAR EL GUARDADO O ACTUALIZADO CON EL COMBO DE "UNIVERSIDAD"
//         */
//        
//

//

//
//        if (frm.getAsk().equals("guardarPrograma")) {
//            String update = "update programa_educativo set nombre_programa='".concat(frm.getNombrePrograma()).concat("', version='".concat(frm.getVersionPrograma()).concat("', nomenclatura='".concat(frm.getNomenclaturaProg())).concat("', id_nivel=".concat(String.valueOf(frm.getCboNivel())).concat(" where id_pe=").concat(String.valueOf(frm.getCboPE()))));
//            String insert = "insert into programa_educativo (nombre_programa,version, nomenclatura,id_familia, id_nivel, activo) values ('".concat(frm.getNombrePrograma()).concat("','").concat(frm.getVersionPrograma()).concat("','").concat(frm.getNomenclaturaProg()).concat("',").concat(String.valueOf(frm.getCboFamiliaProg())).concat(",").concat(String.valueOf(frm.getCboNivel())).concat(",1)");
//            guardarDatos(frm, update, insert);
//            nuevoPrograma(frm, true);
//            frm.setProgramasEducativos(llenarDatos("select id_pe, CONCAT(nombre_programa,' (',version,')') from programa_educativo where id_familia=".concat(String.valueOf(frm.getCboFamiliaProg()))));
//            return mapping.findForward(SUCCESS);
//        }
//        return mapping.findForward(SUCCESS);
//    }
//
//    //MÃ©todo el cual limpia los datos de las universidades para poder realizar una inserciÃ³n.
//    
//

//
//    private int bajaDatos(String consulta, String update1, String update2, int id) {
//        int activo = 0;
//        try {
//            String update = update1;
//            ResultSet rs = conexion.Consultar(consulta);
//            if (rs.next()) {
//                activo = rs.getInt(1);
//            }
//            if (activo == 1) {
//                update = update.concat("0").concat(update2).concat(String.valueOf(id));
//                conexion.Modificar(update);
//            } else {
//                update = update.concat("1").concat(update2).concat(String.valueOf(id));
//                conexion.Modificar(update);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CgutInterfaceAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return activo;
//    }
//
    //

//

//
//    private void nuevoPrograma(CgutInterfaceForm frm, boolean combos) {
//        if (combos == false) {
//            frm.setCboAreaProg(-1);
//            frm.setCboFamiliaProg(-1);
//            frm.setFamiliasProg(new ArrayList());
//        }
//        frm.setCboPE(-1);
//        frm.setProgramasEducativos(new ArrayList());
//        frm.setNombrePrograma("");
//        frm.setProgramaActivo("");
//        frm.setVersionPrograma("");
//        frm.setNomenclaturaProg("");
//        frm.setCboNivel(-2);
//    }
//

//
//    private void nuevaEncuesta(CgutInterfaceForm frm) {
//        frm.setCboEncuestas(-1);
//        frm.setCboNivelEncuesta(-1);
//        frm.setCboEncuestaIndicador(-1);
//        frm.setNombreEncuestas("");
//    }
//
//    private void nuevaPregunta(CgutInterfaceForm frm) {
//        frm.setCmbPregEnc(-1);
//        frm.setCmbPregPreguntas(-1);
//        frm.setTxtNombrePreguntas("");
//        frm.setTxtPalabraClavePreguntas("");
//        frm.setCboCategoPre2(-1);
//    }
//

//

//

//
//    private void nuevoProgramaE(CgutInterfaceForm frm) {
//        frm.setCboNivel2(-1);
//        frm.setNombreNivel("");
//        frm.setNombreDescripcion("");
//        frm.setNombreAbreviatura("");
//    }
//

//

//

//
//    private void nuevoUsuario(CgutInterfaceForm frm) {
//        frm.setCboUni(-2);
//        frm.setUsuario(new ArrayList());
//        frm.setNombreUsu("");
//        frm.setPassword("");
//        frm.setConfirmacion("");
//        frm.setCambioDeResponsable(false);
//    }
//

//
//    private void nuevoIndicador(CgutInterfaceForm frm) {
//        frm.setCboIndicadores(-1);
//        frm.setCboCategorias2(-1);
//        frm.setNombreD("");
//        frm.setArJsp("");
//        frm.setArjs("");
//    }
//
//    private void nuevaCategoPre(CgutInterfaceForm frm) {
//        frm.setCboCategoPre(-1);
//        frm.setNombreCategoPregu("");
//        frm.setNombreCategoDesc("");
//    }
//

}