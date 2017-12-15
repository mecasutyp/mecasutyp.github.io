/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mecasut.admon;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.mecasut.conexion.ConexionMySQL;
import com.mecasut.shared.Universidad;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Danny
 */
public class AcusePDF extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String LOGOUT = "logout";

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

        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("usuario") == null) {

            return mapping.findForward(LOGOUT);
        }
        String ask = request.getParameter("ask");

        if (ask.equals("sendInf")) {
            String cveUniversidad, cveEstado = "", estado = "", municipio = "", calle = "", colonia = "",
                    numero = "", cp = "", cvePeriodo, universidad = "", rector = "", contacto = "", captura = "",
                    telUniversidad = "", mailUniversidad = "";
            Universidad uni = new Universidad();
            cveUniversidad = String.valueOf(sesion.getAttribute("idUniversidad"));
            universidad = uni.getNombreUniversidad(cveUniversidad);
            cvePeriodo = sesion.getAttribute("idPeriodo").toString();
            ConexionMySQL conexion = new ConexionMySQL();
            CallableStatement sp = conexion.PrepararSP("sp_ValidarInicio(?, ?, ?, ?, ?, ?, ?, ?)");
            sp.setInt(1, Integer.parseInt(cveUniversidad));
            sp.setInt(2, Integer.parseInt(cvePeriodo));
            sp.registerOutParameter(3, java.sql.Types.INTEGER);
            sp.registerOutParameter(4, java.sql.Types.INTEGER);
            sp.registerOutParameter(5, java.sql.Types.INTEGER);
            sp.registerOutParameter(6, java.sql.Types.INTEGER);
            sp.registerOutParameter(7, java.sql.Types.INTEGER);
            sp.registerOutParameter(8, java.sql.Types.INTEGER);
            // sp.registerOutParameter(9, java.sql.Types.INTEGER);
            sp.execute();
            int servicios = sp.getInt(3);
            int edificios = sp.getInt(4);
            String consulta = "Select * from domicilios_universidad where id_universidad = ".concat(cveUniversidad).concat(" and id_periodo=").concat(cvePeriodo).concat(" and activo = 1");

            ResultSet rs = conexion.Consultar(consulta);
            if (rs.next()) {
                cveEstado = rs.getString("id_estado");
                municipio = rs.getString("municipio");
                colonia = rs.getString("colonia");
                calle = rs.getString("calle");
                numero = rs.getString("numero");
                cp = rs.getString("codigo_postal");
            }
            consulta = "Select nombre from estados where id_estado = ".concat(cveEstado);

            rs = conexion.Consultar(consulta);
            if (rs.next()) {
                estado = rs.getString("nombre");
            }
            consulta = "Select nombre, apaterno, amaterno, tipo, cargo, telefono, mail from responsables_universidad ru inner join responsables r on ru.id_responsable = r.id_responsable where ru.id_universidad = ".concat(cveUniversidad).concat(" and ru.id_periodo = ").concat(String.valueOf(cvePeriodo));
            rs = conexion.Consultar(consulta);
            while (rs.next()) {
                if (rs.getString("tipo").equals("RU")) {
                    rector = rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno"));
                    telUniversidad = rs.getString("telefono");
                    mailUniversidad = rs.getString("mail");
                }
                if (rs.getString("tipo").equals("RC")) {
                    contacto = rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno")).concat("\nCargo: ").concat(rs.getString("cargo"));
                }
                if (rs.getString("tipo").equals("CU")) {
                    captura = rs.getString("nombre").concat(" ").concat(rs.getString("apaterno")).concat(" ").concat(rs.getString("amaterno")).concat("\nCargo: ").concat(rs.getString("cargo"));
                }
            }

            System.out.println("Creando PDF...");
            try {
                //Crear e instanciar el documento
                Document document = new Document(PageSize.LETTER, 35, 10, 40, 50);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
                writer.setPageEvent(new AcusePDF.PieCabecera());
                document.open();

                PdfPCell cell;
                //Tipos de letra
                //BaseFont base = BaseFont.createFont("E:/Documents/NetBeansProjects/SIFUN/New folder/MECASUT/web/WEB-INF/fonts/cambriab.ttf", BaseFont.WINANSI, true);
                com.itextpdf.text.Font FUENTE_TITULOS = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 13f, com.itextpdf.text.Font.BOLD, new BaseColor(79, 129, 189));
                com.itextpdf.text.Font FUENTE_SUBTITULOS = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12f, com.itextpdf.text.Font.ITALIC, new BaseColor(79, 129, 189));

                //Utilerias
                SimpleDateFormat formateador1 = new SimpleDateFormat("yyyy'-'MM'-'dd");
                Date fecha = new Date();
                consulta = "Select fecha_termino from control_universidad where id_periodo = ".concat(String.valueOf(cvePeriodo)).concat(" and id_universidad=").concat(cveUniversidad);
                String copia = "";
                rs = conexion.Consultar(consulta);
                if (rs.next()) {
                    System.err.println("fecha actual" + formateador1.format(fecha));
                    System.err.println("fecha de la base" + rs.getDate(1));
                    if (!String.valueOf(formateador1.format(fecha)).equals(rs.getString(1))) {
                        copia = " copia";

                    }
                }

                Rectangle r = new Rectangle(PageSize.LETTER.getRight(150), PageSize.LETTER.getTop(120));
                float[] widths = {433f}; //anchos de celdas
                consulta = "Select anio, anio+1 as 'anio2' from periodos where id_periodo = ".concat(String.valueOf(cvePeriodo));
                rs = conexion.Consultar(consulta);
                rs.next();
                Paragraph titulo = new Paragraph(universidad, FUENTE_TITULOS);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);
                SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy' a las 'hh:mm");
                titulo = new Paragraph("Información correspondiente al MECASUT "
                        .concat(rs.getString(1))
                        .concat(" - ")
                        .concat(rs.getString(2))
                        .concat(copia)
                        .concat(" generada el ").concat(FechaEnEspanol(formateador.format(fecha))), FUENTE_TITULOS);
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(10);
                document.add(titulo);

                //Servicios
                PdfPTable table = new PdfPTable(1);
                float alto = 22f;
                        cell = new PdfPCell(new Paragraph("Comprobante de término del llenado MECASUT" ));
                        cell.setFixedHeight(alto);
                        cell.setBorder(PdfPCell.NO_BORDER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);    
                document.add(table);

                consulta = "select CONCAT(nombre,' ',apaterno,' ',amaterno) from responsables where id_responsable=".concat(sesion.getAttribute("idResponsable").toString());
                rs = conexion.Consultar(consulta);

                formateador = new SimpleDateFormat("dd'-'MM'-'yyyy' a las 'hh:mm");
                fecha = new Date();
                ByteArrayOutputStream out;
                if (rs.next()) {
                    out = QRCode.from("Fecha de Creación: " + FechaEnEspanol(formateador.format(fecha)) + "\nResponsable: " + rs.getString(1) + " de la " + universidad +" \nAcuse de termino de llenado MECASUT").to(
                            ImageType.PNG).stream();
                } else {
                    out = QRCode.from("Fecha de Creación: " + FechaEnEspanol(formateador.format(fecha)) + "\nEl documento fue creado sin autorización").to(
                            ImageType.PNG).stream();
                }


                Image img2 = Image.getInstance(out.toByteArray());//agrega imagen
                img2.scaleToFit(110, 110);//escala de la imagen
                img2.setAlignment(Image.BOTTOM | Image.RIGHT);//imagen centrada y de fondo.
                document.add(img2);

                document.close();
            
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\"Acuse_EvaluacionFinalizadaUT.pdf\"");
                response.setContentLength(baos.size());
                ServletOutputStream out1 = response.getOutputStream();
                baos.writeTo(out1);

            } catch (DocumentException de) {

                System.err.println(de.getMessage());
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        }


        if (ask.equals("QR")) {

            ByteArrayOutputStream out = QRCode.from(request.getParameter("texto").toString()).to(
                    ImageType.PNG).stream();

            Image img1 = Image.getInstance(out.toByteArray());//agrega imagen
            img1.scaleToFit(680, 680);//escala de la imagen
            img1.setAlignment(Image.MIDDLE | Image.UNDERLYING);//imagen centrada y de fondo.

            response.setContentType("image/png");
            response.setContentLength(out.size());

            OutputStream outStream = response.getOutputStream();

            outStream.write(out.toByteArray());

            outStream.flush();
            outStream.close();
        }
        return mapping.findForward(SUCCESS);
    }

    class PieCabecera extends PdfPageEventHelper {

        public int numeroPagina;
        public Image plantilla;
        public PdfPTable tabla;
        public PdfTemplate tpl;
        public Phrase cabecera;
        Font smallBold = new Font(Font.FontFamily.HELVETICA, 1, Font.BOLD);

        /**
         *
         * @param writer
         * @param documento
         */
        @Override
        public void onStartPage(PdfWriter writer, Document documento) {
            numeroPagina++;
            try {
                if (numeroPagina == 1) {
                    String relativeWebPath = "/images/membreteyP1.jpg";
                    String absoluteDiskPath = getServlet().getServletContext().getRealPath(relativeWebPath);
                    plantilla = Image.getInstance(absoluteDiskPath);//agrega imagen
                    plantilla.scalePercent(47, 48);//scaleToFit(850, 780);//escala de la imagen
                    plantilla.setAlignment(Image.UNDERLYING);//imagen centrada y de fondo.
                    plantilla.setAbsolutePosition(0, 0);
                    documento.add(plantilla);
                    Paragraph parrafo0 = new Paragraph();
                    parrafo0.setSpacingBefore(0);
                    parrafo0.setSpacingAfter(40);
                    documento.add(parrafo0);
                } else {
                    String relativeWebPath = "/images/plantilla.png";
                    String absoluteDiskPath = getServlet().getServletContext().getRealPath(relativeWebPath);
                    plantilla = Image.getInstance(absoluteDiskPath);//agrega imagen
                    plantilla.scalePercent(47, 48);//scaleToFit(850, 780);//escala de la imagen
                    plantilla.setAlignment(Image.UNDERLYING);//imagen centrada y de fondo.
                    plantilla.setAbsolutePosition(0, 0);
                    documento.add(plantilla);
                }
                
            } catch (BadElementException e) {
                System.err.println("Error PDF ACTION: " + e.getStackTrace());
            } catch (IOException e) {
                System.err.println("Error PDF ACTION: " + e.getStackTrace());
            } catch (DocumentException e) {
                System.err.println("Error PDF ACTION: " + e.getStackTrace());
            }
        }

        /**
         *
         * @param writer
         * @param documento
         */
        @Override
        public void onEndPage(PdfWriter writer, Document documento) {
            Rectangle rect = writer.getBoxSize("art");
            //header
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, cabecera, rect.getRight(), rect.getTop(), 0);
            //footer
            com.itextpdf.text.Font FUENTE_SUBTITULOS = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10f, com.itextpdf.text.Font.ITALIC);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Paragraph(String.format("Página %d", numeroPagina), FUENTE_SUBTITULOS), (rect.getRight()), rect.getBottom() - 16, 0);
        }
    }

    private String FechaEnEspanol(String fecha) {
        fecha = fecha.replaceFirst("january", "enero");
        fecha = fecha.replaceFirst("february", "febrero");
        fecha = fecha.replaceFirst("march", "marzo");
        fecha = fecha.replaceFirst("april", "abril");
        fecha = fecha.replaceFirst("may", "mayo");
        fecha = fecha.replaceFirst("june", "junio");
        fecha = fecha.replaceFirst("july", "julio");
        fecha = fecha.replaceFirst("august", "agosto");
        fecha = fecha.replaceFirst("september", "septiembre");
        fecha = fecha.replaceFirst("october", "octubre");
        fecha = fecha.replaceFirst("november", "noviembre");
        fecha = fecha.replaceFirst("december", "diciembre");
        return fecha;
    }
}
