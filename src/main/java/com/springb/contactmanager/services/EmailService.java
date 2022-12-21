package com.springb.contactmanager.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	public boolean sendEmail(String subject, String message, String to){
        boolean isEmailSendFlag = false;
        System.out.println( "Prepare to send Email" );
//        String message ="Hello Dear, this is Auto generated Mail to check user authenticity";
//        String subject ="CoderArea : Conformation";
//        String to ="onekb.twotb@gmail.com";


        String from ="testmyemailapi3@gmail.com";
        String host ="smtp.gmail.com";
        final Properties properties = System.getProperties();
        System.out.println("properties "+ properties);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//         prop.put("mail.smtp.starttls.enable", "true");
//        SSLSocketFactory
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("use valid Gmail password");
                return new PasswordAuthentication("testmyemailapi3@gmail.com","fnyvwoicqsffjioh");
            }
        });
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
//            mimeMessage.setText(message,"text/html");
            mimeMessage.setContent(message,"text/html");

            Transport.send(mimeMessage);
            System.out.println("--------------mail has been send");
            isEmailSendFlag = true;

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("mail has not been send");
        }

        return isEmailSendFlag;
    }

}
