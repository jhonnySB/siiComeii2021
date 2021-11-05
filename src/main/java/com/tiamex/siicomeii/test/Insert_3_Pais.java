package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Pais;

/**@author Bellcross**/
public class Insert_3_Pais {

    public static void main(String[] args) throws Exception {
        Pais objInsertPais;
        
        objInsertPais = new Pais();
        objInsertPais.setNombre("México");
        ControladorPais.getInstance().save(objInsertPais);
        
        objInsertPais = new Pais();
        objInsertPais.setNombre("Estados Unidos");
        ControladorPais.getInstance().save(objInsertPais);
        
        
        objInsertPais = new Pais();
        objInsertPais.setNombre("Canadá");
        ControladorPais.getInstance().save(objInsertPais);
        
        objInsertPais = new Pais();
        objInsertPais.setNombre("Brasil");
        ControladorPais.getInstance().save(objInsertPais);
    }
    
}
