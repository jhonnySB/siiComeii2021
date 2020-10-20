package com.tiamex.siicomeii.mailer;

import com.tiamex.siicomeii.utils.Utils;
import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
* @author cerimice
**/
public class Mailer{
    private final String user;
    private final String password;
    private final String host;
    private final int port;
    private final boolean auth;
    private final boolean starttls;
    
    private Properties properties;
    private Session session;
    
    public Mailer(){
        user = "mailer@tiamex.com.mx";
        password = "Mailer.12345";
        host = "mail.tiamex.com.mx";
        port = 587;
        auth = true;
        starttls = true;
        crearConection();
    }
    
    private void crearConection(){
        properties = new Properties();
            properties.put("mail.transport.protocol","smtp");
            properties.put("mail.smtp.host",host);
            properties.put("mail.smtp.port",port);
            properties.put("mail.smtp.auth",auth);
            properties.put("mail.smtp.starttls.enable",starttls);
            properties.put("mail.smtp.ssl.trust",host);
            //properties.put("mail.mime.charset", "UTF-8");
            
        session = Session.getInstance(properties,new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user, password);
            }
        });
    }
    
    public boolean testConnection() throws MessagingException{
        try{
            session.getTransport().connect();
            session.getTransport().close();
            return true;
        }catch (MessagingException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public boolean sendMail(String to,String cc,String bcc,String subject,String message, List<File> files) throws MessagingException{
        
        try{
            Message mensaje = new MimeMessage(this.session);
            for(String direccion:to.replace(" ","").split(";")){
                mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress(direccion));
            }
            
            if(!cc.isEmpty())
                for(String direccion:cc.replace(" ","").split(";")){
                    mensaje.addRecipient(Message.RecipientType.CC,new InternetAddress(direccion));
                }
            
            if(!bcc.isEmpty())
                for(String direccion:bcc.replace(" ","").split(";")){
                    mensaje.addRecipient(Message.RecipientType.BCC,new InternetAddress(direccion));
                }
            
            mensaje.setSubject(subject);
            //mensaje.setHeader("Content-Type","text/html; charset=utf-8");
            mensaje.setFrom(new InternetAddress(user));
            mensaje.setReplyTo(new Address[]{new InternetAddress(user)});

            //BodyPart
            MimeBodyPart messageBodyPart = new MimeBodyPart();
                //messageBodyPart.setContent(message);
                messageBodyPart.setContent(message, "text/html; charset=utf-8");

                // Create a multipar message
                Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                
                if(files != null){
                    for(File archivo : files){
                        messageBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(archivo);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(archivo.getName());
                        multipart.addBodyPart(messageBodyPart);
                    }
                }
            mensaje.setContent(multipart,"text/html; charset=utf-8");
            Transport.send(mensaje);
            return true;
        }catch (MessagingException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }
    
    public boolean sendMail(String from,String to,String response,String cc,String bcc,String subject,String message, List<File> files) throws MessagingException{
        try{
            Message mensaje = new MimeMessage(this.session);
            for(String direccion:to.replace(" ","").split(";")){
                mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress(direccion));
            }
            
            if(!cc.isEmpty())
                for(String direccion:cc.replace(" ","").split(";")){
                    mensaje.addRecipient(Message.RecipientType.CC,new InternetAddress(direccion));
                }
            
            if(!bcc.isEmpty())
                for(String direccion:bcc.replace(" ","").split(";")){
                    mensaje.addRecipient(Message.RecipientType.BCC,new InternetAddress(direccion));
                }
            
            mensaje.setSubject(subject);
            mensaje.setHeader("Content-Type","text/html; charset=UTF-8");
            mensaje.setFrom(new InternetAddress(from));
            mensaje.setReplyTo(new Address[]{new InternetAddress(response)});
            
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message,"text/html; charset=UTF-8");
            
                // Create a multipar message
                Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                
                if(files != null){
                    for(File archivo : files){
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(archivo);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(archivo.getName());
                    multipart.addBodyPart(messageBodyPart);
                    }
                }
            mensaje.setContent(multipart,"text/html; charset=UTF-8");
            
            Transport.send(mensaje);
            return true;
        }catch (MessagingException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }

    /// funcion de prueba para enviar correo con imagenes
    public boolean sendMailImg(String to,String cc,String bcc,String subject,String mensaje, List<File> files) throws MessagingException{
        try{
             // Create a default MimeMessage object.
         Message message = new MimeMessage(this.session);

            for(String direccion:to.replace(" ","").split(";")){
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(direccion));
            }
            
            if(!cc.isEmpty())
                for(String direccion:cc.replace(" ","").split(";")){
                    message.addRecipient(Message.RecipientType.CC,new InternetAddress(direccion));
                }
            
            if(!bcc.isEmpty())
                for(String direccion:bcc.replace(" ","").split(";")){
                    message.addRecipient(Message.RecipientType.BCC,new InternetAddress(direccion));
             }
            
         message.setSubject(subject);
         message.setHeader("Content-Type","text/html; charset=UTF-8");
         message.setFrom(new InternetAddress(user));
         message.setReplyTo(new Address[]{new InternetAddress(user)});

         MimeMultipart multipart = new MimeMultipart("Related");

         BodyPart messageBodyPart = new MimeBodyPart();
         messageBodyPart.setContent(mensaje, "text/html; charset=UTF-8");
         multipart.addBodyPart(messageBodyPart);
 
         message.setContent(multipart,"text/html; charset=UTF-8");

         Transport.send(message);
         
         return true;
        }catch (MessagingException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }
    
    
}