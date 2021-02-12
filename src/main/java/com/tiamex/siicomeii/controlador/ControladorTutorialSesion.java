package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;
import com.tiamex.siicomeii.persistencia.servicio.ServicioTutorialSesion;
import java.util.List;

/** @author cerimice **/
public class ControladorTutorialSesion extends GenericController<ServicioTutorialSesion,TutorialSesion,Long>{

    private static ControladorTutorialSesion INSTANCE;
    public static ControladorTutorialSesion getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorTutorialSesion();}
        return INSTANCE;
    }

    private ControladorTutorialSesion(){
        service = ServicioTutorialSesion.getInstance();
    }

    @Override
    protected boolean validate(TutorialSesion obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}

        return true;
    }

    @Override
    public TutorialSesion save(TutorialSesion obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }

        TutorialSesion oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }

        //oldObj.setActivo(obj.getActivo());
        //oldObj.setCambiarPassword(obj.getCambiarPassword());
        //oldObj.setNombre(obj.getNombre());
        return getService().save(obj);
    }

    public List<TutorialSesion> getByTutorialByName(long tutorial, String nombre){return getService().getByTutorialByName(tutorial, nombre);}

}
