package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.persistencia.servicio.ServicioUsuario;

/** @author cerimice **/
public class ControladorTutorialSesion extends GenericController<ServicioUsuario,Usuario,Long>{
    
    private static ControladorTutorialSesion INSTANCE;
    public static ControladorTutorialSesion getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorTutorialSesion();}
        return INSTANCE;
    }
    
    private ControladorTutorialSesion(){
        service = ServicioUsuario.getInstance();
    }

    @Override
    protected boolean validate(Usuario obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        
        return true;
    }

    @Override
    public Usuario save(Usuario obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        Usuario oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        //oldObj.setActivo(obj.getActivo());
        //oldObj.setCambiarPassword(obj.getCambiarPassword());
        //oldObj.setNombre(obj.getNombre());
        return getService().save(obj);
    }
    
}