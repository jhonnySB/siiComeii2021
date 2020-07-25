package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;

/** @author cerimice **/
public class ServicioUsuario extends GenericDao<Usuario,Long>{
    
    private static ServicioUsuario INSTANCE;
    public static ServicioUsuario getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioUsuario();
        return INSTANCE;
    }
    
    private ServicioUsuario(){
    }
    
}
