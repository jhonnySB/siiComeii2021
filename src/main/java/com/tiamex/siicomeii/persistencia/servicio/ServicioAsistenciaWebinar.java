package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;

/** @author cerimice **/
public class ServicioAsistenciaWebinar extends GenericDao<AsistenciaWebinar,Long>{
    
    private static ServicioAsistenciaWebinar INSTANCE;
    public static ServicioAsistenciaWebinar getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioAsistenciaWebinar();
        return INSTANCE;
    }
    
    private ServicioAsistenciaWebinar(){
    }
    
}
