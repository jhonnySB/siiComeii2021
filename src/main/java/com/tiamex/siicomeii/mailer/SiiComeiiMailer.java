package com.tiamex.siicomeii.mailer;

import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.reportes.base.pdf.ConstanciaAgremiado;
import com.tiamex.siicomeii.utils.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
* @author cerimice
**/
public class SiiComeiiMailer{
    
    private static SiiComeiiMailer INSTANCE;
    public static SiiComeiiMailer getInstance(){
        if(INSTANCE == null)
            INSTANCE = new SiiComeiiMailer();
        return INSTANCE;
    }
    
    public SiiComeiiMailer(){
    }
    
    private String cargarMensaje(String archivo) throws Exception{
        try{
            String data = "";
            data = new String(Files.readAllBytes(Paths.get(archivo)));
            return data; 
        }catch(IOException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public boolean enviarRestaurarPassword(Usuario usuario,String password) throws Exception{
        try{
            String asunto = "SII Comeii | Restaurar contraseña";
            String mensaje = cargarMensaje(Main.getBaseDir()+"/mailer/passwordRecovery.txt");
                mensaje = mensaje.replaceAll(":nombre",usuario.getNombre());
                mensaje = mensaje.replaceAll(":usuario",usuario.getCorreo());
                mensaje = mensaje.replaceAll(":password",password);
                mensaje = mensaje.replaceAll(":uri",Main.getUrlServer());
                
            Mailer mailer = new Mailer();
            //return mailer.sendMail(usuario.getEmail(),"","",asunto,mensaje,null);
            return mailer.sendMail(usuario.getCorreo(),"","",asunto,mensaje,null);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public boolean enviarBienvenida(Usuario usuario,String password) throws Exception{
        try{
            String asunto = "Sii Comeii | Bienvenida ";
            String mensaje = cargarMensaje(Main.getBaseDir()+"/mailer/bienvenidaTemplate.txt");
                mensaje = mensaje.replaceAll(":nombre",usuario.getNombre());
                mensaje = mensaje.replaceAll(":usuario",usuario.getCorreo());
                mensaje = mensaje.replaceAll(":password",password);
                mensaje = mensaje.replaceAll(":uri",Main.getUrlServer());
            
            Mailer mailer = new Mailer();
            //return mailer.sendMail(usuario.getEmail(),"","",asunto,mensaje,null);
            return mailer.sendMail(usuario.getCorreo(),"","",asunto,mensaje,null);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public List<String> enviarConstancias(String correos,long idWebinar) throws Exception{
        try{
            List<String> emailsInvalidos = new ArrayList<>();
            Mailer mailer = new Mailer();
            String asunto = "Sii Comeii | Envio de constancia de webinar 2_3";
            String mensaje = cargarMensaje(Main.getBaseDir()+"/mailer/envioConstancia.txt");
            WebinarRealizado webinar = ControladorWebinarRealizado.getInstance().getById(idWebinar);
            String nombreWebinar = webinar.getNombre();
            ConstanciaAgremiado constancia;
            for(String correo: correos.split(",")){
                System.out.println("'"+correo+"'");
                Agremiado agremiado = ControladorAgremiado.getInstance().getByEmail(correo.trim());
                if(agremiado!=null){
                    System.out.print("Agremiado encontrado");
                    String nombreAgremiado = agremiado.getNombre();
                    mensaje = mensaje.replaceAll(":nombre",nombreAgremiado);
                    mensaje = mensaje.replaceAll(":webinar", nombreWebinar);
                    constancia = new ConstanciaAgremiado(nombreWebinar,nombreAgremiado,webinar.getFecha());
                    String filename = constancia.generarConstancia();
                    System.out.println(mailer.sendMailConstancia(correo, "", "", asunto, mensaje, filename,nombreWebinar,webinar.getFecha().getYear()));
                    //constancia.borrarConstancia(filename);
                }else{
                    emailsInvalidos.add(correo);
                }
            }
            return emailsInvalidos;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
}
