package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.GenericDao;
import com.tiamex.siicomeii.utils.Utils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/*
@author cerimice
@Company Tiamex
*/

public abstract class GenericController<SERVICE,CLASS,ID extends Serializable>{
    
    protected GenericDao<CLASS,ID> service;
    protected SERVICE getService(){return (SERVICE)service;}
    
    /* Basic Methods*/
    protected abstract boolean validate(CLASS obj) throws Exception;
    public abstract CLASS save(CLASS obj) throws Exception;
    
    public CLASS getById(ID id) throws Exception{
        try{
            return service.getById(id);
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }
        
    public long generarId(){
        return service.generateId();
    }

    
    public boolean delete(ID id) throws Exception{
        try{
            return service.delete(id);
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }
    
    public int delete(long id) throws Exception{
        try{
            return service.delete(id);
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }
    
    public int updateField(String field,boolean value,long id) throws Exception {
        try {
            return service.updateField(field,value,id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            throw ex;
        }
    }
    
    public List<CLASS> getAll(){
        try{
            return service.getAll();
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public List<CLASS> getByName(String nombre){
        try{
            return service.getByName(nombre);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public Object getByNames(String nombre){
        try{ 
            return service.getByNames(nombre);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public List<CLASS> getByTitulo(String titulo){
        try{
            return service.getByTitulo(titulo);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public Object getByTitulos(String titulo){
        try{
            return service.getByTitulos(titulo);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
    
    public List<CLASS> getAllSorted(String fields) throws Exception{
        try{
            return service.getAllSorted(fields);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
}
