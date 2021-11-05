package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**@author Bellcross*/
public class Insert_5_ProximoWebinar {

    public static void main(String[] args) throws Exception {

        ProximoWebinar objProximoWebinar;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "2020-08-23 00:00";
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime);//23-08-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Universidad Nacional Autónoma de México");
        objProximoWebinar.setPonente("M.I Martín Rubén Jiménez Magaña");
        objProximoWebinar.setTitulo("Riego por goteo.");
        objProximoWebinar.setUsuario(2);//1
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str1 = "2020-06-21 00:00";
        LocalDateTime dateTime1 = LocalDateTime.parse(str1, formatter1);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime1);//21-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Instituto Mexicano de Tecnología del Agua");
        objProximoWebinar.setPonente("M.C. Óscar Pita Díaz");
        objProximoWebinar.setTitulo("Riego por Aspersión.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);

        
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str2 = "2020-06-01 00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(str2, formatter2);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime2);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("AMH - Sinaloa");
        objProximoWebinar.setPonente("Dr. José Carlos Douriet Cárdenas");
        objProximoWebinar.setTitulo("Riego por Microaspersión.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str3 = "2020-08-01 00:00";
        LocalDateTime dateTime3 = LocalDateTime.parse(str3, formatter3);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime3);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        objProximoWebinar.setPonente("Dr. Waldo Ojeda Bustamante");
        objProximoWebinar.setTitulo("Los Sistemas de Riego: reglas básicas para su mejor operación");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str4 = "2020-08-08 00:00";
        LocalDateTime dateTime4 = LocalDateTime.parse(str4, formatter4);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime4);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Investigador de INIFAP - Campo Experimental “Valle del Fuerte” Sinaloa, México.");
        objProximoWebinar.setPonente("M.C. Ernesto Sifuentes Ibarra");
        objProximoWebinar.setTitulo("Uso de sensores de humedad portátiles como apoyo al manejo integral del riego.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
     
        
        DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str5 = "2020-08-15 00:00";
        LocalDateTime dateTime5 = LocalDateTime.parse(str5, formatter5);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime5);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Consultor agrícola Los Mochis, Sinaloa");
        objProximoWebinar.setPonente("Ing. Sergio Fernando Márquez Quiroz");
        objProximoWebinar.setTitulo("La agricultura de conservación en zonas de riego: principios y alcances.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter6 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str6 = "2020-09-01 00:00";
        LocalDateTime dateTime6 = LocalDateTime.parse(str6, formatter6);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime6);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Colegio de Postgraduados Campus Puebla");
        objProximoWebinar.setPonente("Dr. Luis Alberto Villarreal Manzo");
        objProximoWebinar.setTitulo("Proyección de disponibilidad y demanda de agua del acuífero del Valle de Tecamachalco, Puebla.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter7 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str7 = "2020-09-09 00:00";
        LocalDateTime dateTime7 = LocalDateTime.parse(str7, formatter7);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime7);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("ITK Francia");
        objProximoWebinar.setPonente("Dr. Isaac Arturo Ramos Fuentes");
        objProximoWebinar.setTitulo("Reúso de aguas residuales para riego: introducción y etapas claves de un proyecto de reúso");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter8 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str8 = "2020-09-15 00:00";
        LocalDateTime dateTime8 = LocalDateTime.parse(str8, formatter8);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime8);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        objProximoWebinar.setPonente("Dr. Waldo Ojeda Bustamante");
        objProximoWebinar.setTitulo("Las Tecnologías Disruptivas en la Agricultura");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);

        
        DateTimeFormatter formatter10 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str10 = "2020-10-01 00:00";
        LocalDateTime dateTime10 = LocalDateTime.parse(str10, formatter10);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime10);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("CENID/RASPA-INIFAP");
        objProximoWebinar.setPonente("M.I. Sergio Iván Jiménez Jiménez");
        objProximoWebinar.setTitulo("Fotogrametría con drones: conceptos y análisis.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter11 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str11 = "2020-10-10 00:00";
        LocalDateTime dateTime11 = LocalDateTime.parse(str11, formatter11);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime11);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Investigador de INIFAP Campo Experimental “Valle del Fuerte” Sinaloa, México.");
        objProximoWebinar.setPonente("M.C. Ernesto Sifuentes Ibarra");
        objProximoWebinar.setTitulo("Manejo del cultivo de papa utilizando grados días: bases y aplicaciones prácticas para fenología, riego, fertilización, plagas y enfermedades");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str12 = "2020-10-15 00:00";
        LocalDateTime dateTime12 = LocalDateTime.parse(str12, formatter12);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime12);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Profesor-Investigador Universidad Autónoma Chapingo");
        objProximoWebinar.setPonente("Dr. Abraham Rojano Aguilar");
        objProximoWebinar.setTitulo("Modelos en la Agricultura");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter13 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str13 = "2020-11-01 00:00";
        LocalDateTime dateTime13 = LocalDateTime.parse(str13, formatter13);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime13);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Maestro-Investigador Depto. Agricultura y Ganadería Universidad de Sonora");
        objProximoWebinar.setPonente("Dr. Fidencio Cruz Bautista y Dr. Julio Cesar Rodríguez");
        objProximoWebinar.setTitulo("Evapotranspiración y dinámica de la humedad de un suelo con un cultivo perenne");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter14 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str14 = "2020-11-10 00:00";
        LocalDateTime dateTime14 = LocalDateTime.parse(str14, formatter14);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime14);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        objProximoWebinar.setPonente("Dr. Waldo Ojeda Bustamante");
        objProximoWebinar.setTitulo("Entendiendo el cambio climático: Conceptos para entender su definición y sus repercusiones");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter15 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str15 = "2020-11-15 00:00";
        LocalDateTime dateTime15 = LocalDateTime.parse(str15, formatter15);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime15);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Catedrático Instituto Tecnológico Superior de Comalcalco");
        objProximoWebinar.setPonente("M.C. Christian Martinez Sánchez");
        objProximoWebinar.setTitulo("Fertilizantes para fertirriego: conceptos y propiedades");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter16 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str16 = "2020-12-01 00:00";
        LocalDateTime dateTime16 = LocalDateTime.parse(str16, formatter16);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime16);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objProximoWebinar.setPonente("M.I. Daniel Martínez Bazúa");
        objProximoWebinar.setTitulo("Webinar 15.1 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 1 de 5.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter17 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str17 = "2020-12-10 00:00";
        LocalDateTime dateTime17 = LocalDateTime.parse(str17, formatter17);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime17);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objProximoWebinar.setPonente("M.I. Daniel Martínez Bazúa");
        objProximoWebinar.setTitulo("Webinar 15.2 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 2 de 5.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter18 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str18 = "2020-12-15 00:00";
        LocalDateTime dateTime18 = LocalDateTime.parse(str18, formatter18);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime18);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objProximoWebinar.setPonente("M.I. Daniel Martínez Bazúa");
        objProximoWebinar.setTitulo("Webinar 15.3 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 3 de 5.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter19 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str19 = "2020-12-01 00:00";
        LocalDateTime dateTime19 = LocalDateTime.parse(str19, formatter19);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime19);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objProximoWebinar.setPonente("M.I. Daniel Martínez Bazúa");
        objProximoWebinar.setTitulo("Webinar 15.4 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 4 de 5.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter20 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str20 = "2020-12-10 00:00";
        LocalDateTime dateTime20 = LocalDateTime.parse(str20, formatter20);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime20);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objProximoWebinar.setPonente("M.I. Daniel Martínez Bazúa");
        objProximoWebinar.setTitulo("Webinar 15.5 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 5 de 5.");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
        
        
        DateTimeFormatter formatter21 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str21 = "2020-12-15 00:00";
        LocalDateTime dateTime21 = LocalDateTime.parse(str21, formatter21);
        objProximoWebinar = new ProximoWebinar();
        objProximoWebinar.setFecha(dateTime21);//23-Junio-2020
        objProximoWebinar.setImagen(null);
        objProximoWebinar.setInstitucion("Profesor-Investigador Universidad Autónoma Chapingo");
        objProximoWebinar.setPonente("Dr. Abraham Rojano Aguilar");
        objProximoWebinar.setTitulo("Webinar 17. Análisis integrado de la temperatura, humedad y entalpía en la agrometeorología");
        objProximoWebinar.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(objProximoWebinar);
    }

}
