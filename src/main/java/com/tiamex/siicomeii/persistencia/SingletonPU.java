package com.tiamex.siicomeii.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** @author tiamex **/
//@WebListener
public class SingletonPU/* implements ServletContextListener*/{
    
    private static EntityManagerFactory emf;
    private static final String PERSISTANCE_UNIT = "siiComeiiPU";
    
    /*@Override
    public void contextInitialized(ServletContextEvent event){
        //emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent event){
        emf.close();
    }*/
    
    public static EntityManager createEntityManager(){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);
            //throw new IllegalStateException("Context is not initialized yet.");
            //System.out.println("Context is not initialized yet.");
        }
        return emf.createEntityManager();
    }
    
    /*@PreDestroy
    public void destruct(){
        emf.close();
    }*/
}
