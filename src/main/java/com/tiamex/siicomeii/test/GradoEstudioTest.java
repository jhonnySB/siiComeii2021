package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;

/** @author cerimice **/
public class GradoEstudioTest{
    public static void main(String[] args) throws Exception{
        GradoEstudio objGradoEstudio = new GradoEstudio();
            objGradoEstudio.setNombre("Preescolar");
        ControladorGradoEstudio.getInstance().save(objGradoEstudio);
        
        objGradoEstudio = new GradoEstudio();
            objGradoEstudio.setNombre("Primaria");
        ControladorGradoEstudio.getInstance().save(objGradoEstudio);
        
        objGradoEstudio = new GradoEstudio();
            objGradoEstudio.setNombre("Secundaria");
        ControladorGradoEstudio.getInstance().save(objGradoEstudio);
        
        
        objGradoEstudio = new GradoEstudio();
            objGradoEstudio.setNombre("Preparatoria | Bachillerato");
        ControladorGradoEstudio.getInstance().save(objGradoEstudio);
        
        
        objGradoEstudio = new GradoEstudio();
            objGradoEstudio.setNombre("Licenciatura/Ingeniería");
        ControladorGradoEstudio.getInstance().save(objGradoEstudio);
        
        objGradoEstudio = new GradoEstudio();
            objGradoEstudio.setNombre("Maestría");
        ControladorGradoEstudio.getInstance().save(objGradoEstudio);
        
        
        objGradoEstudio = new GradoEstudio();
            objGradoEstudio.setNombre("Doctorado");
        ControladorGradoEstudio.getInstance().save(objGradoEstudio);
    }
}
