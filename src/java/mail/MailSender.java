/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import security.AESDecipher;
import security.PasswordGenerator;

/**
 *
 * @author Alex Hurtado
 */
public class MailSender {

    private static final Logger LOG = Logger.getLogger(MailSender.class.getName());
    
	private static final ResourceBundle RB = ResourceBundle.getBundle("mail.mail");
	private static final String SMTP_HOST = RB.getString("SMTP_HOST");
 	private static final String SMTP_PORT = RB.getString("SMTP_PORT");
 	private static final String MAIL_FROM_USER = AESDecipher.decipherText(RB.getString("MAIL_USER"));
 	private static final String MAIL_PASS = AESDecipher.decipherText(RB.getString("MAIL_PASS"));
        private static String generatedPasswd;
        
    //Mail properties
    public static void sendEmail(String mailTo,MailType type) {
        LOG.info("MAIL_FROM_USER: " + MAIL_FROM_USER);
        LOG.info("MAIL_PASS: " + MAIL_PASS);
        String genPassword = null;
        String mailSubject = null;
     	String mailMessage = null;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.ssl.trust", true);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.imap.partialfetch", false);
        properties.put("mail.smtp.ssl.enable", false);

        //Nos autenticamos 
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(MAIL_FROM_USER, MAIL_PASS);
            }
        });
        session.setDebug(true);
        //Mensaje MIME
        Message message = new MimeMessage(session);
        try
        {
        	if(type.equals(MailType.PASS_RESET)) {
        		mailSubject = RB.getString("MAIL_SUBJECT_RESET");
        		genPassword = PasswordGenerator.getPassword(8); 
                        setGeneratedPasswd(genPassword);
        		mailMessage = String.format(RB.getString("MAIL_MESSAGE_RESET"), genPassword);
        	}
        	if(type.equals(MailType.PASS_CHANGE)){
        		mailSubject = RB.getString("MAIL_SUBJECT_CHANGE");
        		mailMessage = RB.getString("MAIL_MESSAGE_CHANGE");
        	}
            message.setFrom(new InternetAddress(MAIL_FROM_USER));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(mailSubject);
            //un mail puede tener varias partes incluyendo un archivo
            MimeMultipart multiPart = new MimeMultipart();
            //parte de un mensaje
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mailMessage, "text/html");
            multiPart.addBodyPart(mimeBodyPart);
            //añadimos las partes al mensaje MIME
            message.setContent(multiPart);
            //enviamos el mensaje
            Transport.send(message);
        } catch (AddressException ex)
        {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex)
        {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getGeneratedPasswd() {
        return generatedPasswd;
    }

    public static void setGeneratedPasswd(String generatedPasswd) {
        MailSender.generatedPasswd = generatedPasswd;
    }
}
