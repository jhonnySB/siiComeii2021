package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;

/** @author cerimice **/
public class ServicioTutorialSesion extends GenericDao<TutorialSesion,Long>{
    
    private static ServicioTutorialSesion INSTANCE;
    public static ServicioTutorialSesion getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioTutorialSesion();
        return INSTANCE;
    }
    
    private ServicioTutorialSesion(){
    }
    
}
