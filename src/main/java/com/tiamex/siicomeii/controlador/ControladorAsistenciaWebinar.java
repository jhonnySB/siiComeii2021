package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.servicio.ServicioAsistenciaWebinar;
import java.util.List;

/** @author cerimice **/
public class ControladorAsistenciaWebinar extends GenericController<ServicioAsistenciaWebinar,AsistenciaWebinar,Long>{
    
    private static ControladorAsistenciaWebinar INSTANCE;
    public static ControladorAsistenciaWebinar getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorAsistenciaWebinar();}
        return INSTANCE;
    }
    
    private ControladorAsistenciaWebinar(){
        service = ServicioAsistenciaWebinar.getInstance();
    }

    @Override
    protected boolean validate(AsistenciaWebinar obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        
        return true;
    }

    @Override
    public AsistenciaWebinar save(AsistenciaWebinar obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                obj.setWebinar(obj.getWebinar());
                obj.setUsuario(obj.getUsuario());
                obj.setAgremiado(obj.getAgremiado());
                return getService().save(obj);
            }
        }
        
        AsistenciaWebinar oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }

        return getService().save(obj);
    }

    
    public List<AsistenciaWebinar> getByWebinar(long idWebinar){
        return getService().getByIdWebinar(idWebinar);
    }
    
    public List<AsistenciaWebinar> getByAgremiadoWebinar(long idWebinar,long idAgremiado){
        return getService().getByAgremiadoWebinar(idWebinar,idAgremiado);
    }
    
}
