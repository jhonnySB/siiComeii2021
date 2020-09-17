package com.tiamex.siicomeii.controlador;

import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.persistencia.servicio.ServicioPais;

/** @author fred **/
public class ControladorPais extends GenericController<ServicioPais,Pais,Long>{
    
    private static ControladorPais INSTANCE;
    public static ControladorPais getInstance(){
        if (INSTANCE == null){INSTANCE = new ControladorPais();}
        return INSTANCE;
    }
    
    /* DESCRIPCIÓN: Constructor de la clase para inicializar el servicio mediante la instancia de la clase ServicioUsuarioGrupo
       ENTRADAS: No recibe ningún parámetro.
       SALIDAS: No regresa nada.
    */
    private ControladorPais(){
        service = ServicioPais.getInstance();
    }

    /* DESCRIPCIÓN: Función abstracta que sobreescribe a la función de la clase padre GenericController para hacer la validación
                    de los atributos o campos de la entidad "Usuario Grupo" que son: id y nombre.
       ENTRADAS: Recibe como parámetros una instancia de la clase "Usuario Grupo" para validar sus campos.
       SALIDAS: Regresa un booleano "true" todos los campos ingresados son correctos, de lo contrario lanza una excepción que la 
                maneja la clase Exception.
    */ 
    @Override
    protected boolean validate(Pais obj) throws Exception{
        if(obj.getId() < 0){throw new Exception("El ID no es valido");}
        if(obj.getNombre().isEmpty()){throw new Exception("Es necesario proporcionar el nombre");}
        return true;
    }

    /* DESCRIPCIÓN: Función abstracta que sobreescribe a la función de la clase padre GenericController para el guardado de un nuevo
                    registro de "Usuario Grupo" y del editado de un registro existente.
       ENTRADAS: Recibe como parámetros una instancia de la clase "Usuario Grupo".
       SALIDAS: Regresa el registro guardado que llama de la clase GenericDao una vez que se guarda en la base de datos.
    */
    @Override
    public Pais save(Pais obj) throws Exception{
        if(validate(obj)){
            if(obj.getId() == 0){
                obj.setId(getService().generateId());
                return getService().save(obj);
            }
        }
        
        Pais oldObj = getService().getById(obj.getId());
        if(oldObj == null){
            return getService().save(obj);
        }
        
        oldObj.setNombre(obj.getNombre());
        return getService().save(obj);
    }
    
}
