package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Query;

/** @author cerimice **/
public class ServicioUsuario extends GenericDao<Usuario,Long>{
    
    private static ServicioUsuario INSTANCE;
    public static ServicioUsuario getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioUsuario();
        return INSTANCE;
    }
    
    private ServicioUsuario(){
    }
    
    public Usuario getByEmail(String correo){
        try{
            Query query = em.createNamedQuery("Usuario.getByEmail");
                query.setParameter("correo",correo);
            List<Usuario> list = query.getResultList();
            if(list.isEmpty()){return null;}
            return list.get(0);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }
}
