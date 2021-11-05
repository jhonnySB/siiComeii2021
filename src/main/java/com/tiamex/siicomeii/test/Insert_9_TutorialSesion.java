package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorTutorialSesion;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;

/**@author Bellcross**/
public class Insert_9_TutorialSesion {

    public static void main(String[] args) throws Exception {
        
        TutorialSesion objTutorialSesion;
        
        
        objTutorialSesion = new TutorialSesion();
        objTutorialSesion.setInstitucion("Instituto Mexicano de Tecnología del Agua");
        objTutorialSesion.setNombre("CESAR RICARDO MINAURO");
        objTutorialSesion.setTutor("Dr. Waldo Ojeda Bustamante (COMEII)");
        objTutorialSesion.setTutorial(1);
        objTutorialSesion.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        objTutorialSesion.setUsuario(1);
        ControladorTutorialSesion.getInstance().save(objTutorialSesion);
        
        
        objTutorialSesion = new TutorialSesion();
        objTutorialSesion.setInstitucion("Consultor independiente");
        objTutorialSesion.setNombre("JONATHAN SALAZAR BRITO");
        objTutorialSesion.setTutor("Dr. Juan Pérez (IRIA)");
        objTutorialSesion.setTutorial(2);
        objTutorialSesion.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        objTutorialSesion.setUsuario(2);
        ControladorTutorialSesion.getInstance().save(objTutorialSesion);
        
        
        objTutorialSesion = new TutorialSesion();
        objTutorialSesion.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objTutorialSesion.setNombre("FREDY DE LA CRUZ TELLEZ");
        objTutorialSesion.setTutor("Dra. María González (AYCMX)");
        objTutorialSesion.setTutorial(3);
        objTutorialSesion.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        objTutorialSesion.setUsuario(1);
        ControladorTutorialSesion.getInstance().save(objTutorialSesion);
    }
    
}
