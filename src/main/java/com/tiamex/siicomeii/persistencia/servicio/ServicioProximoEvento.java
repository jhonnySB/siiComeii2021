package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;

/** @author cerimice **/
public class ServicioProximoEvento extends GenericDao<ProximoEvento,Long>{
    
    private static ServicioProximoEvento INSTANCE;
    public static ServicioProximoEvento getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioProximoEvento();
        return INSTANCE;
    }
    
    private ServicioProximoEvento(){
    }
    
}
