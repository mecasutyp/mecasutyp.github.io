/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

/**
 *
 * @author Joshua
 */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

public class MailSender {

    public MailSender() {
    }

    public static boolean send(String from, String password, String to, String cc, String bcc, String asunto, String cuerpo, boolean isHTMLFormat) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.live.com");
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.user", from);

        Session session = Session.getDefaultInstance(props, null);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));

            message.setSubject(asunto);
            
            if (isHTMLFormat) {
                message.setContent(cuerpo.toString(), "text/html");
            } else {
                message.setText(cuerpo.toString());
            }


            Transport t = session.getTransport("smtp");
            t.connect(from, password);
            t.sendMessage(message, message.getAllRecipients());
            System.out.println("Correo Enviado exitosamente!");
            t.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR MailSender  "+e.getMessage());
            return false;
        }
    }
}
