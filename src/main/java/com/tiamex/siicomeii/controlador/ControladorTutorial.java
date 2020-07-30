package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.persistencia.servicio.ServicioTutorial;

/** @author cerimice **/
public class ControladorTutorial extends GenericController<ServicioTutorial,Tutorial,Long>{
    
    private static ControladorTutorial INSTANCE;
    public static ControladorTutorial getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorTutorial();}
        return INSTANCE;
    }
    
    private ControladorTutorial(){
        service = ServicioTutorial.getInstance();
    }

    @Override
    protected boolean validate(Tutorial obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        
        return true;
    }

    @Override
    public Tutorial save(Tutorial obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        Tutorial oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        //oldObj.setActivo(obj.getActivo());
        //oldObj.setCambiarPassword(obj.getCambiarPassword());
        //oldObj.setNombre(obj.getNombre());
        return getService().save(obj);
    }
    
}
