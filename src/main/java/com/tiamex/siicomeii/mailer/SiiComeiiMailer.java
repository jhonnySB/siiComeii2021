package com.tiamex.siicomeii.mailer;

import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    
    private SiiComeiiMailer(){
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
}
