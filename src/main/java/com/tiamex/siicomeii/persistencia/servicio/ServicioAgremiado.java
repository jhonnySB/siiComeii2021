package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;

/** @author cerimice **/
public class ServicioAgremiado extends GenericDao<Agremiado,Long>{
    
    private static ServicioAgremiado INSTANCE;
    public static ServicioAgremiado getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioAgremiado();
        return INSTANCE;
    }
    
    private ServicioAgremiado(){
    }
    
}
