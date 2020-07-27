package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;

/** @author cerimice **/
public class ServicioWebinarRealizado extends GenericDao<WebinarRealizado,Long>{
    
    private static ServicioWebinarRealizado INSTANCE;
    public static ServicioWebinarRealizado getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioWebinarRealizado();
        return INSTANCE;
    }
    
    private ServicioWebinarRealizado(){
    }
    
}
