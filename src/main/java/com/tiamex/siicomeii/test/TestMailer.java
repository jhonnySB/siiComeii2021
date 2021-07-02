package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.mailer.SiiComeiiMailer;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import javax.mail.MessagingException;

/** @author cerimice **/
public class TestMailer{
    public static void main(String[] args) throws MessagingException, Exception{
        
        Usuario user = new Usuario();
        user.setActivo(true);
        user.setCambiarPassword(true);
        user.setCorreo("midibi4372@pigicorn.com");
        user.setId(1);
        user.setNombre("a");
        user.setPassword("12345678");
        user.setUsuarioGrupo(1);
        SiiComeiiMailer mailer = new SiiComeiiMailer();
        mailer.enviarBienvenida(user);
        
        //Usuario objUsuario = ControladorUsuario.getInstance().getById(3L);
        //SiiComeiiMailer.getInstance().enviarBienvenida(objUsuario,objUsuario.getPassword());
        
        /*
        objUsuario = ControladorUsuario.getInstance().getById(2L);
        SiiComeiiMailer.getInstance().enviarRestaurarPassword(objUsuario,objUsuario.getPassword());
        
        objUsuario = ControladorUsuario.getInstance().getById(3L);
        SiiComeiiMailer.getInstance().enviarRestaurarPassword(objUsuario,objUsuario.getPassword()); */
    }
}
