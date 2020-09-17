package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.persistencia.servicio.ServicioProximoEvento;

/** @author cerimice **/
public class ControladorProximoEvento extends GenericController<ServicioProximoEvento,ProximoEvento,Long>{
    
    private static ControladorProximoEvento INSTANCE;
    public static ControladorProximoEvento getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorProximoEvento();}
        return INSTANCE;
    }
    
    private ControladorProximoEvento(){
        service = ServicioProximoEvento.getInstance();
    }

    @Override
    protected boolean validate(ProximoEvento obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        
        return true;
    }

    @Override
    public ProximoEvento save(ProximoEvento obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        ProximoEvento oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        oldObj.setDescripcion(obj.getDescripcion());
        oldObj.setFecha(obj.getFecha());
        oldObj.setImagen(obj.getImagen());
        oldObj.setTitulo(obj.getTitulo());
        return getService().save(obj);
    }
    
}
