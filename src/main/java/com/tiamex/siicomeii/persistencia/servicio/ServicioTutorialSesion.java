package com.tiamex.siicomeii.persistencia.servicio;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;
import com.tiamex.siicomeii.utils.Utils;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Query;

/** @author cerimice **/
public class ServicioTutorialSesion extends GenericDao<TutorialSesion,Long>{

    private static ServicioTutorialSesion INSTANCE;
    public static ServicioTutorialSesion getInstance(){
        if(INSTANCE == null)
            INSTANCE = new ServicioTutorialSesion();
        return INSTANCE;
    }

    private ServicioTutorialSesion(){
    }

    public List<TutorialSesion> getByTutorialByName(long tutorial, String nombre){
        try{
            Query query = em.createNamedQuery("TutorialSesion.getByTutorialByName");
            query.setParameter("tutorial", tutorial);
            query.setParameter("nombre", nombre+"%");
            List<TutorialSesion> list = query.getResultList();
            return list;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }

}
