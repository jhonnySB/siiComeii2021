package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;

/**@author Bellcross**/
public class Insert_6_Tutorial {

    public static void main(String[] args) throws Exception {
        
        Tutorial objTutorial;
        
        objTutorial = new Tutorial();
        objTutorial.setInstitucion("COMEII e Hidráulica Fácil");
        objTutorial.setNombre("¿Qué es un sistema de riego?");
        objTutorial.setTutor("Dr. Waldo Ojeda Bustamante (COMEII)");
        objTutorial.setUsuario(1);//1
        ControladorTutorial.getInstance().save(objTutorial);
        
        objTutorial = new Tutorial();
        objTutorial.setInstitucion("Instituto de riego y de agua (IRIA)");
        objTutorial.setNombre("Clasificación de los sistemas de aplicación de riego");
        objTutorial.setTutor("Dr. Juan Pérez (IRIA)");
        objTutorial.setUsuario(2);
        ControladorTutorial.getInstance().save(objTutorial);
        
        objTutorial = new Tutorial();
        objTutorial.setInstitucion("Agua y cultivos CDMX (AYCMX)");
        objTutorial.setNombre("Descripción de los sistemas de aplicación de riego");
        objTutorial.setTutor("Dra. María González (AYCMX)");
        objTutorial.setUsuario(3);
        ControladorTutorial.getInstance().save(objTutorial);
        
    }
    
}
