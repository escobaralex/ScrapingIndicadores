package appvaloresdataextractor;

import java.util.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHelper {
    public static void sendMail(){         
        final String username = "aescobar.mabu@gmail.com";
        final String password = "iversonnietszche";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });

        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(username));
                message.setSubject("Reporte AppValores");
                //message.setText("Informe: \n\n " + myLog.msgLog.toString());
                String body = "<div style=\"color:green;\">Reporte</div>";
                body += "<table style=\"border:1px solid #cecece;\" >";
                String[] logs = myLog.msgLog.toString().split("finC");
                for(int i = 0; i < logs.length; i++){
                    body += "<tr style=\"border:1px solid #cecece;\"><td>" + logs[i] + "</td></tr>";    
                }
                body += "</table>";
                message.setContent(body, "text/html; charset=utf-8");
                message.setSentDate(new Date());
                Transport.send(message);

                System.out.println("Email enviado a: " + username);

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
}
