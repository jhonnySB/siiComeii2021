package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Query;

/** @author cerimice **/
public class ServicioAgremiado extends GenericDao<Agremiado,Long>{
    
    private static ServicioAgremiado INSTANCE;
    public static ServicioAgremiado getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioAgremiado();
        return INSTANCE;
    }
    
    private ServicioAgremiado(){
    }
    
    public Agremiado getByEmail(String correo){
        try{
            Query query = em.createNamedQuery("Agremiado.getByEmail");
                query.setParameter("correo",correo);
            List<Agremiado> list = query.getResultList();
            if(list.isEmpty()){return null;}
            return list.get(0);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }
    
}
