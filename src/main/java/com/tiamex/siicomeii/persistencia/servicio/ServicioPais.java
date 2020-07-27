package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.Pais;

/** @author cerimice **/
public class ServicioPais extends GenericDao<Pais,Long>{
    
    private static ServicioPais INSTANCE;
    public static ServicioPais getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioPais();
        return INSTANCE;
    }
    
    private ServicioPais(){
    }
    
}
