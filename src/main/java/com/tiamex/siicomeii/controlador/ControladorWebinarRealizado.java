package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.persistencia.servicio.ServicioUsuario;

/** @author cerimice **/
public class ControladorWebinarRealizado extends GenericController<ServicioUsuario,Usuario,Long>{
    
    private static ControladorWebinarRealizado INSTANCE;
    public static ControladorWebinarRealizado getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorWebinarRealizado();}
        return INSTANCE;
    }
    
    private ControladorWebinarRealizado(){
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
