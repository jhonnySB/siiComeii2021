package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.servicio.ServicioProximoWebinar;

/** @author cerimice **/
public class ControladorProximoWebinar extends GenericController<ServicioProximoWebinar,ProximoWebinar,Long>{
    
    private static ControladorProximoWebinar INSTANCE;
    public static ControladorProximoWebinar getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorProximoWebinar();}
        return INSTANCE;
    }
    
    private ControladorProximoWebinar(){
        service = ServicioProximoWebinar.getInstance();
    }

    @Override
    protected boolean validate(ProximoWebinar obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        
        return true;
    }

    @Override
    public ProximoWebinar save(ProximoWebinar obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        ProximoWebinar oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        oldObj.setFecha(obj.getFecha());
        return getService().save(obj);
    }
    
}
