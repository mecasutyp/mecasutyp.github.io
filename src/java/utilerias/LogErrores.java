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
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Danny
 */
public class LogErrores extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    public LogErrores() throws FileNotFoundException, IOException {

        String rutaRelativaWeb = "/files/";
        String rutaAbsolutaEnDisco = getServlet().getServletContext().getRealPath(rutaRelativaWeb);
        ArrayList tt = new ArrayList();

        openFile("s", "s", tt, rutaAbsolutaEnDisco);


    }

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // rutaRelativaWeb = "/files/";



        return mapping.findForward(SUCCESS);

    }

    public void openFile(String universidad, String periodo, ArrayList mensaje, String Ruta) throws FileNotFoundException, IOException {
        System.out.println("Ruta "+Ruta);
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList Hola = new ArrayList();

        try {
            archivo = new File(Ruta + universidad + "_" + periodo + ".txt");
            if (archivo.exists() == true) {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                // Lectura del fichero
                String linea;
                writeFile(universidad, periodo, Hola, mensaje,Ruta);
            } else {
                writeFile(universidad, periodo, Hola, mensaje,Ruta);
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

    public void writeFile(String universidad, String periodo, ArrayList Hola, ArrayList mensaje, String Ruta) {
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            fichero = new FileWriter(Ruta + universidad + "_" + periodo + ".txt", true);
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
}