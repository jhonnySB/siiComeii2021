package com.tiamex.siicomeii.persistencia;

import com.tiamex.siicomeii.utils.Utils;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
            //return false;
            throw ex;
            
        }
    }
    
    public int delete(long id){
        try{
            em = SingletonPU.createEntityManager();
            em.getTransaction().begin();
            int registroEliminado = em.createQuery("DELETE FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.id="+id).executeUpdate();
            em.getTransaction().commit();
            return registroEliminado;
        }catch(Exception ex){
            //Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            return 0;
            //throw ex;
        }     
    }
    
    public int totalByField(String field,String value){
        try{
            em = SingletonPU.createEntityManager();
            em.getTransaction().begin();
            int registroEliminado = em.createQuery("SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t."+field+"="+"'"+value+"'").executeUpdate();
            em.getTransaction().commit();
            return registroEliminado;
        }catch(Exception ex){
            //Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            return 0;
            //throw ex;
        }     
    }
    
    public int updateField(String field,boolean value,long id){
        try{
            em = SingletonPU.createEntityManager();
            em.getTransaction().begin();
            int registroEliminado = em.createQuery("UPDATE "+this.getPersistentClass().getSimpleName()+" t SET t."+field+" = '"+value+"' WHERE t.id = "+id).executeUpdate();
            em.getTransaction().commit();
            return registroEliminado;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            em.getTransaction().rollback();
            return 0;
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
    
    public List<CLASS> getByInstituto(String i) {
        try {
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM " + this.getPersistentClass().getSimpleName() 
                    + " t WHERE UPPER(t.institucion)=UPPER('"+i+"') ORDER BY t.institucion";
            Query query = em.createQuery(statement);
            List<CLASS> list = query.getResultList();
            return list;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }
    
    public Object getByNames(String nombre){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.nombre="+"'"+nombre+"'";
            Query query = em.createQuery(statement);
                //query.setParameter("nombre","%"+nombre);
            return query.getSingleResult();
        }catch(NoResultException  ex){
            return null;
        }
    }
    
    public List<CLASS> getByTitulo(String titulo){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.titulo LIKE :titulo ORDER BY t.titulo";
            Query query = em.createQuery(statement);
                query.setParameter("titulo",titulo+"%");
            List<CLASS> list = query.getResultList();
            return list;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public Object getByTitulos(String titulo){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.titulo="+"'"+titulo+"'";
            Query query = em.createQuery(statement);
                //query.setParameter("titulo",titulo+"%");
            return query.getSingleResult();
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            return null;
        }
    }
    
    public Object getByNameWebinarRealizado(String name){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.nombre="+"'"+name+"'";
            Query query = em.createQuery(statement);
                //query.setParameter("titulo",titulo+"%");
            return query.getSingleResult();
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            return null;
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
    
    public List<CLASS> getByAsistencia(long idAgremiado){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.agremiado=:idAgremiado";
            Query query = em.createQuery(statement);
                query.setParameter("idAgremiado",idAgremiado);
            List<CLASS> list = query.getResultList();
            return list;
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    
    public List<CLASS> getByAgremiadoWebinarR(long idAgremiado,long idWebinarR){
        try{
            em = SingletonPU.createEntityManager();
            String statement = "SELECT t FROM "+this.getPersistentClass().getSimpleName()+" t WHERE t.webinar=:idWebinarR AND t.agremiado=:idAgremiado";
            Query query = em.createQuery(statement);
            query.setParameter("idWebinarR",idWebinarR);
            query.setParameter("idAgremiado",idAgremiado);
            List<CLASS> list = query.getResultList();
            return list;
        }catch(Exception ex){
            ex.printStackTrace();
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
