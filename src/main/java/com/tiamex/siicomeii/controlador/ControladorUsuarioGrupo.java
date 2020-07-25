package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
import com.tiamex.siicomeii.persistencia.servicio.ServicioUsuarioGrupo;

/** @author cerimice **/
public class ControladorUsuarioGrupo extends GenericController<ServicioUsuarioGrupo,UsuarioGrupo,Long>{
    
    private static ControladorUsuarioGrupo INSTANCE;
    public static ControladorUsuarioGrupo getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorUsuarioGrupo();}
        return INSTANCE;
    }
    
    private ControladorUsuarioGrupo(){
        service = ServicioUsuarioGrupo.getInstance();
    }

    @Override
    protected boolean validate(UsuarioGrupo obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        if(obj.getNombre().isEmpty()){throw new Exception("Es necesario proporcionar el nombre");}
        return true;
    }

    @Override
    public UsuarioGrupo save(UsuarioGrupo obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        UsuarioGrupo oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        oldObj.setNombre(obj.getNombre());
        return getService().save(obj);
    }
    
}
