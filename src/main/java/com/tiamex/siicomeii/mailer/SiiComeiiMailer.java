package com.tiamex.siicomeii.mailer;

import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.reportes.base.pdf.ConstanciaAgremiado;
import com.tiamex.siicomeii.utils.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    
    public boolean enviarBienvenida(Usuario usuario) throws Exception{
        try{
            String asunto = "Sii Comeii | Bienvenida ";
            String mensaje = cargarMensaje(Main.getBaseDir()+"/mailer/bienvenidaTemplate.txt");
                mensaje = mensaje.replaceAll(":nombre",usuario.getNombre());
                mensaje = mensaje.replaceAll(":usuario",usuario.getCorreo());
                mensaje = mensaje.replaceAll(":password",usuario.getPassword());
            
            Mailer mailer = new Mailer();
            return mailer.sendMail(usuario.getCorreo(),"","",asunto,mensaje,null);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    
    public boolean enviarBienvenida(Agremiado agremiado) throws Exception{
        try{
            String asunto = "Sii Comeii | Bienvenida ";
            String mensaje = cargarMensaje(Main.getBaseDir()+"/mailer/bienvenidaTemplateAgremiado.txt");
                mensaje = mensaje.replaceAll(":nombre",agremiado.getNombre());
                mensaje = mensaje.replaceAll(":usuario",agremiado.getCorreo());
            
            Mailer mailer = new Mailer();
            return mailer.sendMail(agremiado.getCorreo(),"","",asunto,mensaje,null);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    
    public List<String> enviarConstancias(String correos,long idWebinar,long idUser) throws Exception{
        List<String> emailsInvalidos = new ArrayList<>();
        try{
            Mailer mailer = new Mailer();
            String asunto = "Sii Comeii | Envio de constancia de webinar";
            String mensaje;
            WebinarRealizado webinar = ControladorWebinarRealizado.getInstance().getById(idWebinar);
            String nombreWebinar = webinar.getNombre();
            ConstanciaAgremiado constancia;
            Agremiado agremiado;
            AsistenciaWebinar asistencia;
            for(String correo: correos.split(",")){
                agremiado = ControladorAgremiado.getInstance().getByEmail(correo.trim());
                if(agremiado!=null){
                    mensaje = cargarMensaje(Main.getBaseDir()+"/mailer/envioConstancia.txt");
                    String nuevoAgremiado = agremiado.getNombre();
                    mensaje = mensaje.replaceAll(":nombre",nuevoAgremiado);
                    mensaje = mensaje.replaceAll(":webinar", nombreWebinar);
                    constancia = new ConstanciaAgremiado(nombreWebinar,nuevoAgremiado,webinar.getFecha(),webinar.getPonente());
                    String filename = constancia.generarConstancia();
                    if(mailer.sendMailConstancia(correo, "", "", asunto, mensaje, filename,nombreWebinar,webinar.getFecha().getYear())){
                        asistencia = new AsistenciaWebinar();
                        asistencia.setAgremiado(agremiado.getId());
                        asistencia.setUsuario(idUser);
                        asistencia.setWebinar(idWebinar);
                        if (ControladorAsistenciaWebinar.getInstance().getByAgremiadoWebinar(idWebinar, agremiado.getId()).isEmpty())
                            ControladorAsistenciaWebinar.getInstance().save(asistencia);
                    }else{
                        emailsInvalidos.add(correo);
                    }
                }
            }
            return emailsInvalidos;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            return emailsInvalidos;
            //throw ex;
        }
    }
}
