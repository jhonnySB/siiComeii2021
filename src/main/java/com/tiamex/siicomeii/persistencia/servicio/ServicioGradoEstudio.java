package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;

/** @author cerimice **/
public class ServicioGradoEstudio extends GenericDao<GradoEstudio,Long>{
    
    private static ServicioGradoEstudio INSTANCE;
    public static ServicioGradoEstudio getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioGradoEstudio();
        return INSTANCE;
    }
    
    private ServicioGradoEstudio(){
    }
    
}
