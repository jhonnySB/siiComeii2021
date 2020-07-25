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
    
    public boolean delete(ID id) throws Exception{
        try{
            return service.delete(id);
        }catch (Exception ex){
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
    
    public List<CLASS> getAllSorted(String fields) throws Exception{
        try{
            return service.getAllSorted(fields);
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage());
            throw ex;
        }
    }
}
