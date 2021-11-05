package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;

/**@author Bellcross**/
public class Insert_8_Agremiado {

    public static void main(String[] args) throws Exception {

        Agremiado objAgremiado;
        
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("sbjo161021@upemor.edu.mx");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("Profesor-Investigador Universidad Autónoma Chapingo");
        objAgremiado.setNombre("Salazar Brito Jonathan");
        objAgremiado.setPais(1);
        objAgremiado.setSexo('M');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
       
        
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("fredlacruz@outlook.com");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("Profesor-Investigador Universidad Autónoma Chapingo");
        objAgremiado.setNombre("De la Cruz Téllez Fredy");
        objAgremiado.setPais(1);
        objAgremiado.setSexo('M');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("jero172222@upemor.edu.mx");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("Profesor-Investigador Universidad Autónoma Chapingo");
        objAgremiado.setNombre("José Esteban Salazar");
        objAgremiado.setPais(2);
        objAgremiado.setSexo('M');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("cero173333@upemor.edu.mx");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("AMH - Sinaloa");
        objAgremiado.setNombre("Carlos Eduardo");
        objAgremiado.setPais(1);
        objAgremiado.setSexo('M');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
        
    }
    
}
