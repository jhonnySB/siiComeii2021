package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.persistencia.servicio.ServicioGradoEstudio;

/** @author cerimice **/
public class ControladorGradoEstudio extends GenericController<ServicioGradoEstudio,GradoEstudio,Long>{
    
    private static ControladorGradoEstudio INSTANCE;
    public static ControladorGradoEstudio getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorGradoEstudio();}
        return INSTANCE;
    }
    
    private ControladorGradoEstudio(){
        service = ServicioGradoEstudio.getInstance();
    }

    @Override
    protected boolean validate(GradoEstudio obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        
        return true;
    }

    @Override
    public GradoEstudio save(GradoEstudio obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        GradoEstudio oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        oldObj.setNombre(obj.getNombre());
        return getService().save(obj);
    }
    
}
