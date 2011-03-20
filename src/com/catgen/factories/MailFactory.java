package com.catgen.factories;

import java.util.Properties;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.codec.binary.Base64;

import com.catgen.MailObj;
import com.catgen.Utils;
import com.catgen.exception.MailNotSentException;

/**
 * 
 * @author JBS
 *
 */
public class MailFactory{

    private static String HOST = "smtp.gmail.com";
    private static String USER = "c2FsZXMub3BlbmVudHJ5QGdtYWlsLmNvbQ==";
    private static String PASSWORD = "YWRtaW5Ab2U=";
    private static String PORT = "465";

    private static String STARTTLS = "true";
    private static String AUTH = "true";
    private static String DEBUG = "true";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    
    public static synchronized void sendMail(MailObj mailObj) throws MailNotSentException {
        Properties props = new Properties();

        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.user", new String(Base64.decodeBase64(USER.getBytes())));

        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);
        props.put("mail.smtp.debug", DEBUG);

        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");

        try {

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);
            message.setText(mailObj.body);
            //message.setContent("<span style=\"font-family:Verdana\">"+mailObj.body+"</span>", "text/html");
            message.setSubject(mailObj.subject);
            message.setFrom(new InternetAddress(mailObj.from));
            message.setRecipients(RecipientType.TO, Utils.getInternetAddressArrayFromString(mailObj.to));
            message.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, new String(Base64.decodeBase64(USER.getBytes())), new String(Base64.decodeBase64(PASSWORD.getBytes())));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            throw new MailNotSentException("Mail Delivery Failure. FROM:"+mailObj.from+", TO:"+mailObj.to);
        }
    }
}