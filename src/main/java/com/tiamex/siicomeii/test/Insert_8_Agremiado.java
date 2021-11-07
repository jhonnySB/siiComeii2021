package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;

/*@author Bellcross*/
public class Insert_8_Agremiado {

    public static void main(String[] args) throws Exception {

        Agremiado objAgremiado;
        
        //1
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("sbjo161021@upemor.edu.mx");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("Universidad Autónoma Chapingo");
        objAgremiado.setNombre("Salazar Brito Jonathan");
        objAgremiado.setPais(1);
        objAgremiado.setSexo('M');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
       
        
        //2
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("fredlacruz@outlook.com");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("Universidad Autónoma Chapingo");
        objAgremiado.setNombre("De la Cruz Téllez Fredy");
        objAgremiado.setPais(1);
        objAgremiado.setSexo('H');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //3
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("jero172222@upemor.edu.mx");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("Universidad Autónoma Chapingo");
        objAgremiado.setNombre("José Esteban Salazar");
        objAgremiado.setPais(2);
        objAgremiado.setSexo('H');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //4
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("cero173333@upemor.edu.mx");
        //created_at
        objAgremiado.setGradoEstudios(4);
        objAgremiado.setInstitucion("AMH - Sinaloa");
        objAgremiado.setNombre("Carlos Eduardo");
        objAgremiado.setPais(2);
        objAgremiado.setSexo('H');
        //updated_at
        //urlicon
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //5
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("MontesilloCedillo@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(5);
        objAgremiado.setInstitucion("Universidad Autónoma Chapingo");
        objAgremiado.setNombre("Dr. José Luis Montesillo Cedillo");
        objAgremiado.setPais(2);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //6
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("LlerenaVillalpando@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(5);
        objAgremiado.setInstitucion("Universidad Autónoma Chapingo");
        objAgremiado.setNombre("M.C. Félix Alberto Llerena Villalpando");
        objAgremiado.setPais(2);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //7
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("GuerreroÁngulo@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(5);
        objAgremiado.setInstitucion("AMH - Sinaloa");
        objAgremiado.setNombre("Dr. José Óscar Guerrero Ángulo");
        objAgremiado.setPais(3);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
       
        //8
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("MartínezBazúa@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(5);
        objAgremiado.setInstitucion("AMH - Sinaloa");
        objAgremiado.setNombre("M.I. Daniel Martínez Bazúa");
        objAgremiado.setPais(3);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //9
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("MunguíaLópez@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(6);
        objAgremiado.setInstitucion("Consultor Independiente y Agroplasticultura");
        objAgremiado.setNombre("Dr. Juan P. Munguía López");
        objAgremiado.setPais(4);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //10
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("VelascoVelasco@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(6);
        objAgremiado.setInstitucion("Consultor Independiente y Agroplasticultura");
        objAgremiado.setNombre("Dr. Israel Velasco Velasco");
        objAgremiado.setPais(4);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //11
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("RamírezRuíz@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(6);
        objAgremiado.setInstitucion("Consultor Independiente y Agroplasticultura");
        objAgremiado.setNombre("M.I. Cándido Ramírez Ruíz");
        objAgremiado.setPais(3);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //12
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("RamosFuentes@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(7);
        objAgremiado.setInstitucion("ITK Francia");
        objAgremiado.setNombre("Dr. Isaac Arturo Ramos Fuentes");
        objAgremiado.setPais(4);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //13
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("VillarrealManzo@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(7);
        objAgremiado.setInstitucion("Colegio de Postgraduados Campus Puebla");
        objAgremiado.setNombre("Dr. Luis Alberto Villarreal Manzo");
        objAgremiado.setPais(1);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
       
        //14
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("MárquezQuiroz@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(7);
        objAgremiado.setInstitucion("Consultor agrícola Los Mochis, Sinaloa");
        objAgremiado.setNombre("Ing. Sergio Fernando Márquez Quiroz");
        objAgremiado.setPais(2);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //15
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("JiménezJiménez@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(7);
        objAgremiado.setInstitucion("CENID/RASPA-INIFAP");
        objAgremiado.setNombre("M.I. Sergio Iván Jiménez Jiménez");
        objAgremiado.setPais(2);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //16
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("PitaDíaz@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(7);
        objAgremiado.setInstitucion("Instituto Mexicano de Tecnología del Agua");
        objAgremiado.setNombre("M.C. Óscar Pita Díaz");
        objAgremiado.setPais(4);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
        
        
        //17
        objAgremiado = new Agremiado();
        objAgremiado.setCorreo("Martinez Sánchez@gmail.com");
        //created_at
        objAgremiado.setGradoEstudios(7);
        objAgremiado.setInstitucion("Catedrático Instituto Tecnológico Superior de Comalcalco");
        objAgremiado.setNombre("M.C. Christian Martinez Sánchez");
        objAgremiado.setPais(4);
        objAgremiado.setSexo('H');
        ControladorAgremiado.getInstance().save(objAgremiado);
    }
    
}