package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;

/** @author cerimice **/
public class ServicioProximoWebinar extends GenericDao<ProximoWebinar,Long>{
    
    private static ServicioProximoWebinar INSTANCE;
    public static ServicioProximoWebinar getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioProximoWebinar();
        return INSTANCE;
    }
    
    private ServicioProximoWebinar(){
    }
    
}
