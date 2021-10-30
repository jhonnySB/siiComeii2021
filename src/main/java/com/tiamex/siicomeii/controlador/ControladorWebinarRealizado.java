package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.SingletonPU;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.persistencia.servicio.ServicioWebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import java.util.logging.Logger;
import javax.persistence.Query;

/** @author cerimice **/
public class ControladorWebinarRealizado extends GenericController<ServicioWebinarRealizado,WebinarRealizado,Long>{
    
    private static ControladorWebinarRealizado INSTANCE;
    public static ControladorWebinarRealizado getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorWebinarRealizado();}
        return INSTANCE;
    }
    
    private ControladorWebinarRealizado(){
        service = ServicioWebinarRealizado.getInstance();
    }

    @Override
    protected boolean validate(WebinarRealizado obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        
        return true;
    }

    @Override
    public WebinarRealizado save(WebinarRealizado obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }

        WebinarRealizado oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        oldObj.setPresentacion(obj.getPresentacion());
        oldObj.setUrlYoutube(obj.getUrlYoutube());
        return getService().save(obj);
    }
    
}
