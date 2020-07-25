package com.tiamex.siicomeii.persistencia;

import java.io.Serializable;

/** @author cerimice **/
/** @company tiamex **/

/**
 * @param <CLASS>
 * @param <ID>
 */
public interface GenericDaoInterface <CLASS,ID extends Serializable>{
    Class<CLASS> getPersistentClass();
    CLASS getById(ID id)throws Exception;
    CLASS save(CLASS entity)throws Exception;
    boolean delete(ID id)throws Exception;
}
