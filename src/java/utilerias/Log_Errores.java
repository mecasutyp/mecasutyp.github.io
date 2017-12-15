/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Danny
 */
public class Log_Errores {

    public Log_Errores() {
    }

    public void openFile(String universidad, String periodo, ArrayList mensaje) throws FileNotFoundException, IOException {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
 get();

              RutaJar();
        try {
           archivo = new File(universidad + "_" + periodo + ".txt");
            System.out.println("User dir "+System.getProperty("user.dir"));
            if (archivo.exists() == true) {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                // Lectura del fichero
                String linea;
              writeFile(universidad, periodo, mensaje);
            } else {
            writeFile(universidad, periodo, mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void writeFile(String universidad, String periodo, ArrayList mensaje) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
           fichero = new FileWriter(universidad + "_" + periodo + ".txt", true);
            System.out.println("User dir "+System.getProperty("user.dir"));
             get();
              RutaJar();
            pw = new PrintWriter(fichero);
            pw.println("------------------Fecha--------------");
            for (int i = 0; i < mensaje.size(); i++) {
                pw.println(mensaje.get(i));
            }
            pw.println("");
            pw.println("");
      } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public String consigueRuta(String nombreArchivo) {
        String[] propiedades, rutaLimpia;
        propiedades = System.getProperty("java.class.path").split(File.pathSeparator);
        rutaLimpia = propiedades[0].split("\\\\");
        String relativa = rutaLimpia[0] + "//";
        for (int i = 1; i < (rutaLimpia.length - 1); i++) {
            relativa += rutaLimpia[i] + "//";
        }
        System.out.print("nombre archivo "+ nombreArchivo);
        return relativa + "files//" + nombreArchivo;
    }
    private static File WORKING_DIRECTORY;

    public static File get() {
        if (WORKING_DIRECTORY == null) {
            try {
                URL url = Log_Errores.class.getResource("ejemplo.txt");
                System.out.println("URL "+url);
                if (url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    f = f.getParentFile()
                            .getParentFile()
                            .getParentFile();
                    WORKING_DIRECTORY = f;
                } else if (url.getProtocol().equals("jar")) {
                    String expected = "!/util/ejemplo.txt";
                    String s = url.toString();
                    s = s.substring(4);
                    s = s.substring(0, s.length() - expected.length());
                    File f = new File(new URL(s).toURI());
                    f = f.getParentFile();
                    WORKING_DIRECTORY = f;
                }
            } catch (Exception e) {
                WORKING_DIRECTORY = new File(".");
            }
        }
        return WORKING_DIRECTORY;
    }

    public String RutaJar() {

        System.out.print("User dir "+ System.getProperty("user.dir"));//imprime la ruta 
        return System.getProperty("user.dir"); // regresa la ruta
    }
}
