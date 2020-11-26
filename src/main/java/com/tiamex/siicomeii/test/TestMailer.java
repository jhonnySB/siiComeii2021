package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.mailer.SiiComeiiMailer;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import javax.mail.MessagingException;

/** @author cerimice **/
public class TestMailer{
    public static void main(String[] args) throws MessagingException, Exception{
        
        Usuario objUsuario = ControladorUsuario.getInstance().getById(3L);
        //SiiComeiiMailer.getInstance().enviarBienvenida(objUsuario,objUsuario.getPassword());
        
        /*
        objUsuario = ControladorUsuario.getInstance().getById(2L);
        SiiComeiiMailer.getInstance().enviarRestaurarPassword(objUsuario,objUsuario.getPassword());
        
        objUsuario = ControladorUsuario.getInstance().getById(3L);
        SiiComeiiMailer.getInstance().enviarRestaurarPassword(objUsuario,objUsuario.getPassword()); */
    }
}
