package com.tiamex.siicomeii;

import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
import com.tiamex.siicomeii.persistencia.servicio.ServicioUsuario;
import com.tiamex.siicomeii.persistencia.servicio.ServicioUsuarioGrupo;

/** @author cerimice **/
public class Main{
    public static void main(String[] args) throws Exception{
        System.out.println("Hola Mundo");
        System.out.println("Hola Mundo");
        
        UsuarioGrupo objUsuarioGrupo = new UsuarioGrupo();
            objUsuarioGrupo.setNombre("Grupo 1");
        ControladorUsuarioGrupo.getInstance().save(objUsuarioGrupo);
        
        Usuario objUsuario = ServicioUsuario.getInstance().getById(1l);
        if(objUsuario != null){
            System.out.println(objUsuario.getObjUsuarioGrupo().getNombre());
        }
        
        objUsuarioGrupo = ServicioUsuarioGrupo.getInstance().getById(1l);
        for(Usuario obj:objUsuarioGrupo.getListaUsuarios()){
            System.out.println(obj.getNombre());
        }
        
        System.out.println("Hola Mundo");
        System.out.println("Hola Mundo");
    }
}
