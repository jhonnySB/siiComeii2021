package com.tiamex.siicomeii.persistencia;

import com.tiamex.siicomeii.utils.Utils;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/* @author cerimice */
/* @company tiamex */

public class GenericDao<CLASS,ID extends Serializable> implements GenericDaoInterface<CLASS,ID>{
    
    /* Atributos */
    private final Class<CLASS> persistentClass;
    @Override
    public Class<CLASS> getPersistentClass(){return persistentClass;}
    
    protected EntityManager em;
    public EntityManager getEntityManager(){return this.em;}
    
    /* Constructores */
    public GenericDao(){
        this.persistentClass = (Class<CLASS>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        em = SingletonPU.createEntityManager();
    }
    
    /* Metodos */
    @Override
    public CLASS getById(ID id) throws Exception{
        try{
            CLASS result;
            em = SingletonPU.createEntityManager();
                em.getTransaction().begin();
                result = getEntityManager().find(this.getPersistentClass(),id);
                em.getTransaction().commit();
            return result;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public CLASS save(CLASS entity) throws Exception{
        try{
            CLASS savedEntity;
            em = SingletonPU.createEntityManager();
                em.getTransaction().begin();
                savedEntity = em.merge(entity);
                em.getTransaction().commit();
            return savedEntity;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            throw ex;
        }
    }
    
    @Override
    public boolean delete(ID id) throws Exception{
        try{
            em = SingletonPU.createEntityManager();
                em.getTransaction().begin();
                CLASS entity = em.find(this.getPersistentClass(),id);
                if(entity == null){return false;}
                em.remove(entity);
                em.getTransaction().commit();
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            throw ex;
        }
    }
    
    /* Basic Querys*/
    public long generateId(){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT MAX(t.id) FROM "+this.getPersistentClass().getSimpleName()+" t";
            Query query = em.createQuery(statement);
            long id = (long)query.getSingleResult();
            return id+1;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            return 1;
            //throw ex;
        }
    }
    
    public List<CLASS> getAll(){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t";
            Query query = em.createQuery(statement);
            List<CLASS> list = query.getResultList();
            return list;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public List<CLASS> getAllSorted(String... fields){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t  FROM "+this.getPersistentClass().getSimpleName()+" t ORDER BY ";
                for(String field:fields){statement += "t."+field+",";}
                statement += ";";
                statement = statement.replace(",;","");
            Query query = em.createQuery(statement);
            List<CLASS> lista = query.getResultList();
            return lista;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public List<CLASS> getByName(String nombre){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.nombre LIKE :nombre ORDER BY t.nombre";
            Query query = em.createQuery(statement);
                query.setParameter("nombre",nombre+"%");
            List<CLASS> list = query.getResultList();
            return list;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
   
    public List<CLASS> getByIdWebinar(long idWebinar){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.webinar=:idWebinar";
            Query query = em.createQuery(statement);
                query.setParameter("idWebinar",idWebinar);
            List<CLASS> list = query.getResultList();
            return list;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    
    public List<CLASS> getByAgremiadoWebinar(long idWebinar,long idAgremiado){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.webinar=:idWebinar AND t.agremiado="+idAgremiado;
            Query query = em.createQuery(statement);
                query.setParameter("idWebinar",idWebinar);
                
            List<CLASS> list = query.getResultList();
            return list;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    
    /* Special Native Querys */
    public boolean executeStatementUpdate(String sql) throws Exception{
        try{
            em = SingletonPU.createEntityManager();
            em.getTransaction().begin();
            Query enunciadoSQL = em.createNativeQuery(sql);
                enunciadoSQL.executeUpdate();
            em.getTransaction().commit();
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            throw ex;
        }
    }
    
    public boolean executeStatementUpdate(String... sql) throws Exception{
        try{
            Query enunciadoSQL;
            em = SingletonPU.createEntityManager();
            em.getTransaction().begin();
            for(String query:sql){
                enunciadoSQL = em.createNativeQuery(query);
                enunciadoSQL.executeUpdate();
            }
            em.getTransaction().commit();
            return true;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            throw ex;
        }
    }
}
