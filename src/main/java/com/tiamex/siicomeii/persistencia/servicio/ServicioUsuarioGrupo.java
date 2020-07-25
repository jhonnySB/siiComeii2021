package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;

/** @author cerimice **/
public class ServicioUsuarioGrupo extends GenericDao<UsuarioGrupo,Long>{
    
    private static ServicioUsuarioGrupo INSTANCE;
    public static ServicioUsuarioGrupo getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioUsuarioGrupo();
        return INSTANCE;
    }
    
    private ServicioUsuarioGrupo(){
    }
    
}
