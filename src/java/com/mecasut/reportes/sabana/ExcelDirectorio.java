/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.reportes.sabana;

/**
 *
 *
 * @author Salvador Zamora Arias
 */
public class ExcelDirectorio extends SabanaCategorias {

    int contCol = 0;
    int c = 0;
    int f = 5;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.err.println("Excel Directorio Eliminada :)");
    }

    public ExcelDirectorio(String anio, HojaCalculo obj, String periodo, ListaUniversidades univs, ListaNiveles niveles) {
        super(periodo, obj, univs, niveles, anio);
        this.obj = obj;
        this.f += univs.size();
    }

    public HojaCalculo hacerDirectorio() {
        direcciones();
        directorio();
        con.Desconectar();
        return obj;
    }

    private void directorio() {
        for (int zpersona = 0; zpersona < 3; zpersona++) {
            String cargo="", tipo="";
            if (zpersona==0) {
                cargo="Rector";tipo="RU";
            } if (zpersona==1) {
                cargo="Responsable";tipo="RC";
            } if (zpersona==2) {
                cargo="Capturista";tipo="CU";
            }
             c = 8;
            obj.crearHoja(cargo, f, c);
            obj.setAnchoColumna(1, 17500);
            int NoColumnas = c;
            for (int i = 2; i <= NoColumnas; i++) {
                obj.setAnchoColumna(i, 10000);
            }
            columnasPrincipalessTotal(2);
            obj.setAlturaFilas(2, 57);
            contCol = 2;
            try { 
                    obj.combinarCeldas("Nombre", contCol, contCol, 1, 2, "h1");
                    obj.combinarCeldas("Cargo", contCol + 1, contCol + 1, 1, 2, "h1");
                    obj.combinarCeldas("Lada", contCol + 2, contCol + 2, 1, 2, "h1");
                    obj.combinarCeldas("Teléfono", contCol + 3, contCol + 3, 1, 2, "h1");
                    obj.combinarCeldas("Correo", contCol + 4, contCol + 4, 1, 2, "h1");
                      for (int unis = 0; unis < univs.size(); unis++) {
                        consulta = "SELECT r.nombre, r.apaterno, r.amaterno, r.cargo, r.telefono, r.mail from responsables r inner join responsables_universidad ru on r.id_responsable= ru.id_responsable"
                                .concat(" WHERE r.id_universidad=").concat(String.valueOf(univs.getId(unis)))
                                .concat(" AND (ru.id_periodo=").concat(periodo).concat(" or ru.id_periodo=").concat(String.valueOf(Integer.parseInt(periodo)-1)).concat("  ) AND r.tipoResponsable='"+tipo+"'");
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            obj.val(contCol, unis + 3, rs.getString("r.nombre").concat(" "+rs.getString("r.apaterno")).concat(" "+rs.getString("r.amaterno")));
                            obj.val(contCol + 1, unis + 3, rs.getString("r.cargo"));
                             String[] parts = rs.getString("r.telefono").split("#"); 
                    
                                if(parts.length>1){
                                    obj.val(contCol + 2, unis + 3, parts[0]);
                                    obj.val(contCol + 3, unis + 3, parts[1]);
                                }else{
                                    obj.val(contCol + 2, unis + 3, "");
                                    obj.val(contCol + 3, unis + 3, rs.getString("r.telefono"));
                                }
                               obj.val(contCol + 4, unis + 3, rs.getString("r.mail"));
                           // obj.val(contCol + 2, unis + 3, rs.getString("r.telefono"));
                            
                        } else {
                            obj.val(contCol, unis + 3, "SIN DATOS", "error");
                            obj.val(contCol + 1, unis + 3, "SIN DATOS", "error");
                            obj.val(contCol + 2, unis + 3, "SIN DATOS", "error");
                            obj.val(contCol + 3, unis + 3, "SIN DATOS", "error");
                        }
                    }

            } catch (Exception x) {
                System.err.println("ERROR BD: Reporte directorio rectores " + x.getMessage());
            }
        }     
    }
    
    private void direcciones() {
            c = 3;
            obj.crearHoja("Dirrecciones", f, c);
            obj.setAnchoColumna(1, 17500);
            int NoColumnas = c;
            for (int i = 2; i <= NoColumnas; i++) {
                obj.setAnchoColumna(i, 35000);
            }
            columnasPrincipalessTotal(2);
            obj.setAlturaFilas(2, 57);
          
            contCol = 2;
            try { 
                    obj.combinarCeldas("Dirección\n(estado, municipio, colonia, calle / número y código postal)", contCol, contCol, 1, 2, "h1");
                     for (int unis = 0; unis < univs.size(); unis++) {
                        consulta = "SELECT CONCAT(e.nombre, ', ', du.municipio,', ',du.colonia,', ', du.calle,' ', du.numero,', ', du.codigo_postal)"
                                + " AS direccion  FROM domicilios_universidad du INNER JOIN estados e on e.id_estado=du.id_estado "
                                + "WHERE (du.id_periodo=".concat(periodo).concat(" or du.id_periodo=").concat(String.valueOf(Integer.parseInt(periodo)-1)).concat(" ) AND du.id_universidad=").concat(String.valueOf(univs.getId(unis)));
                        rs = con.Consultar(consulta);
                        if (rs.next()) { //si hay una respuesta de la consulta
                            obj.val(contCol, unis + 3, rs.getString("direccion"));
                        } else {
                            obj.val(contCol, unis + 3, "SIN DATOS", "error");
                        }
                    }
            } catch (Exception x) {
                System.err.println("ERROR BD: Reporte directorio direcciones " + x.getMessage());
            }
    }     
    
}
