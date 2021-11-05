package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;

/**@author Bellcross**/
public class Insert_4_Usuarios {

    public static void main(String[] args) throws Exception {
        Usuario objUsuario;
        
        objUsuario = new Usuario();
        objUsuario.setActivo(true);
        objUsuario.setCambiarPassword(false);
        objUsuario.setCorreo("cminauro@upemor.edu.mx");
        objUsuario.setNombre("CESAR RICARDO MINAURO");
        objUsuario.setPassword("contra12345");
        objUsuario.setUsuarioGrupo(1);
        ControladorUsuario.getInstance().save(objUsuario);
        
        
        objUsuario = new Usuario();
        objUsuario.setActivo(true);
        objUsuario.setCambiarPassword(false);
        objUsuario.setCorreo("jhon@gmail.com");
        objUsuario.setNombre("JONATHAN SALAZAR BRITO");
        objUsuario.setPassword("secret1Jonh");
        objUsuario.setUsuarioGrupo(2);
        ControladorUsuario.getInstance().save(objUsuario);
        
        
        objUsuario = new Usuario();
        objUsuario.setActivo(true);
        objUsuario.setCambiarPassword(false);
        objUsuario.setCorreo("dtfo170113@upemor.edu.mx");
        objUsuario.setNombre("FREDY DE LA CRUZ");
        objUsuario.setPassword("contra12345");
        objUsuario.setUsuarioGrupo(2);
        ControladorUsuario.getInstance().save(objUsuario);
        
    }
    
}
