package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;

/**@author bellcross**/
public class Insert_2_GrupoUsuario {

    public static void main(String[] args) throws Exception {
        
        UsuarioGrupo objUsuarioGrupo;
        
        objUsuarioGrupo = new UsuarioGrupo();
        objUsuarioGrupo.setNombre("Administrador");
        ControladorUsuarioGrupo.getInstance().save(objUsuarioGrupo);
        
        objUsuarioGrupo = new UsuarioGrupo();
        objUsuarioGrupo.setNombre("Agremiado");
        ControladorUsuarioGrupo.getInstance().save(objUsuarioGrupo);
        
        objUsuarioGrupo = new UsuarioGrupo();
        objUsuarioGrupo.setNombre("Espectador");
        ControladorUsuarioGrupo.getInstance().save(objUsuarioGrupo);
        
    }
    
}
