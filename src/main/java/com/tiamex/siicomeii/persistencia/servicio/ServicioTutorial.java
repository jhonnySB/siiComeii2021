package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;

/** @author cerimice **/
public class ServicioTutorial extends GenericDao<Tutorial,Long>{
    
    private static ServicioTutorial INSTANCE;
    public static ServicioTutorial getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioTutorial();
        return INSTANCE;
    }
    
    private ServicioTutorial(){
    }
    
}
