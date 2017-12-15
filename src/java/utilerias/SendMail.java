/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
 
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.mail.internet.MimeMessage;
import sun.misc.BASE64Decoder;
/**
 *
 * @author Salvador Zamora Arias
 * 23-01-2017
 */
public class SendMail {
    String Username="mecasutyp@gmail.com";
    String PassWord="Mecasut&p";
    String To="mecasutyup@gmail.com";
    //String To="zamorasalvador17@gmail.com";
    String Subject, Mensaje, filesNameArray, Universidad, Indicador, Extension;
   
    String Protocolo="smtp";
    int Puerto=587;
    String Host="smtp.gmail.com";
    String Auth="true";
    String Starttls="true";
    
    public SendMail( String mensaje, String universidad) {
        Mensaje=mensaje;
        Universidad=universidad;
    }
    public SendMail( String mensaje, String AttachFiles,
            String universidad,String indicador, String extension) {
        Mensaje=mensaje;
        filesNameArray=AttachFiles;
        Universidad=universidad;
        Indicador=indicador;
        Extension=extension;
    }
   

    public String SendMailArchivo() {//ENVIA CORREO CON ARCHIVO ADJUNTO
       Properties props = new Properties();
       props.put("mail.smtp.auth", Auth);
       props.put("mail.smtp.starttls.enable", Starttls);
       props.put("mail.smtp.host", Host);
       props.put("mail.smtp.port", Puerto);
       props.put("mail.transport.protocol", Protocolo);
        
       Session session = Session.getInstance(props, new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(Username, PassWord );}
       });
       Transport transport = null;
       try {
           transport = session.getTransport("smtp");
           Message message = new MimeMessage(session);
           SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy HH:mm:ss", new Locale("es", "ES"));
            String fecha = formato.format(new Date());
           String messageBody = Mensaje+ fecha;
           message.setSentDate(new Date());
           message.setSubject(Universidad+" Indicador "+Indicador);
           Multipart mp = new MimeMultipart();
           BodyPart messageBodyPart = new MimeBodyPart();


           BASE64Decoder decoder = new BASE64Decoder();
           String stringbase64=filesNameArray.replaceAll(" ", "");
           byte[] decodedBytes = decoder.decodeBuffer(stringbase64);

   //SE CREA UN FICHERO TEMPORAL        
           File filetmp = File.createTempFile("mificherotemporal",null);
           BufferedWriter out = new BufferedWriter(new FileWriter(filetmp));
           out.write("Esto es un fichero temporal");
           out.close();
   //SE CREA EL ARCHIVO PDF QUE SE ADJUNTARA        
           File archivoadjunto = new File(filetmp.getParentFile()+"\\"+Universidad+"-"+Indicador+Extension);
           FileOutputStream outer = new FileOutputStream(archivoadjunto);
           outer.write(decodedBytes);
           outer.close();
   //SE ADJUNTA EL SRCHIVO A EL CORREO        
               MimeBodyPart mbp2 = new MimeBodyPart();
               FileDataSource fds = new FileDataSource(archivoadjunto);
               mbp2.setDataHandler(new DataHandler(fds));
               mbp2.setFileName(fds.getName());
               mp.addBodyPart(mbp2);
   //SE COMPLETA EL CORREO Y SE ENVIA            
           messageBodyPart.setText(messageBody);
           mp.addBodyPart(messageBodyPart);
           message.setContent(mp);
          // System.out.println("Conectando..." );
           transport.connect();
           Transport.send(message, InternetAddress.parse(To));
           transport.close();
           System.out.println("Se envio correo " );

   //SE BORRAN LOS ARCHIVOS CREADOS       
           filetmp.delete();
            archivoadjunto.delete();
           return "Si"; 
       }catch(Exception e){
           System.err.println("Exception SendMail "+ e.getMessage());
           return e.getMessage();
       }
   }
    
public String SendMail() {//CORREO SIN ARCHIVO
       Properties props = new Properties();
       props.put("mail.smtp.auth", Auth);
       props.put("mail.smtp.starttls.enable", Starttls);
       props.put("mail.smtp.host", Host);
       props.put("mail.smtp.port", Puerto);
       props.put("mail.transport.protocol", Protocolo);
        
       Session session = Session.getInstance(props, new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(Username, PassWord );}
       });
       Transport transport = null;
       try {
           transport = session.getTransport("smtp");
           Message message = new MimeMessage(session);
           SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy HH:mm:ss", new Locale("es", "ES"));
            String fecha = formato.format(new Date());
           String messageBody = Mensaje+" de la "+Universidad+" \n"+ fecha;
           message.setSentDate(new Date());
           message.setSubject(Universidad);
           
           message.setText(messageBody);
       //    System.out.println("Conectando..." );
           transport.connect();
           Transport.send(message, InternetAddress.parse(To));
           transport.close();
           System.out.println("Se envio correo " );

           return "Si"; 
       }catch(Exception e){
           System.err.println("Exception SendMail "+ e.getMessage());
           return e.getMessage();
       }
   }
    
    
}
