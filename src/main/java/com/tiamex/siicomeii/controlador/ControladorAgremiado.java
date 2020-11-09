package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.servicio.ServicioAgremiado;

/** @author fred **/
public class ControladorAgremiado extends GenericController<ServicioAgremiado,Agremiado,Long>{
    
    private static ControladorAgremiado INSTANCE;
    public static ControladorAgremiado getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorAgremiado();}
        return INSTANCE;
    }
    
    private ControladorAgremiado(){
        service = ServicioAgremiado.getInstance();
    }

    @Override
    protected boolean validate(Agremiado obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        if(obj.getGradoEstudios() <= 0){throw new Exception("El Grado de estudios no es valido");}
        if(obj.getInstitucion().isEmpty()){throw new Exception("Es necesario proporcionar el nombre de la institución");}
        if(obj.getNombre().isEmpty()){throw new Exception("Es necesario proporcionar");}
        if(obj.getPais() <= 0){throw new Exception("Es necesario proporcionar pais");}
        if(obj.getSexo() =='\u0000'){throw new Exception("Es necesario seleccionar sexo");}
        
        return true;
    }

    @Override
    public Agremiado save(Agremiado obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        Agremiado oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        oldObj.setGradoEstudios(obj.getGradoEstudios());
        oldObj.setInstitucion(obj.getInstitucion());
        oldObj.setNombre(obj.getNombre());
        oldObj.setPais(obj.getPais());
        oldObj.setSexo(obj.getSexo());
        
        return getService().save(obj);
    }
    
}
