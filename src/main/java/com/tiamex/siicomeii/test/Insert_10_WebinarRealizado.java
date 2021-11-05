package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**@author Bellcross**/
public class Insert_10_WebinarRealizado {

    public static void main(String[] args) throws Exception {
        short a = 2;

        WebinarRealizado objWebinarRealizado;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "2020-07-16 00:00";
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime);//16-Junio-2020
        objWebinarRealizado.setInstitucion("Universidad Nacional Autónoma de México");
        objWebinarRealizado.setNombre("Riego por goteo.");
        objWebinarRealizado.setPonente("M.I Martín Rubén Jiménez Magaña");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str1 = "2020-07-21 00:00";
        LocalDateTime dateTime1 = LocalDateTime.parse(str1, formatter1);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime1);//21-Junio-2020
        objWebinarRealizado.setInstitucion("Instituto Mexicano de Tecnología del Agua");
        objWebinarRealizado.setNombre("Riego por Aspersión.");
        objWebinarRealizado.setPonente("M.C. Óscar Pita Díaz");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str2 = "2020-07-23 00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(str2, formatter2);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime2);//23-Junio-2020
        objWebinarRealizado.setInstitucion("AMH - Sinaloa");
        objWebinarRealizado.setNombre("Riego por Microaspersión.");
        objWebinarRealizado.setPonente("Dr. José Carlos Douriet Cárdenas");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str3 = "2020-08-01 00:00";
        LocalDateTime dateTime3 = LocalDateTime.parse(str3, formatter3);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime3);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        objWebinarRealizado.setNombre("Los Sistemas de Riego: reglas básicas para su mejor operación");
        objWebinarRealizado.setPonente("Dr. Waldo Ojeda Bustamante");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str4 = "2020-08-08 00:00";
        LocalDateTime dateTime4 = LocalDateTime.parse(str4, formatter4);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime4);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Investigador de INIFAP - Campo Experimental “Valle del Fuerte” Sinaloa, México.");
        objWebinarRealizado.setNombre("Uso de sensores de humedad portátiles como apoyo al manejo integral del riego.");
        objWebinarRealizado.setPonente("M.C. Ernesto Sifuentes Ibarra");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str5 = "2020-08-15 00:00";
        LocalDateTime dateTime5 = LocalDateTime.parse(str5, formatter5);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime5);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Consultor agrícola Los Mochis, Sinaloa");
        objWebinarRealizado.setNombre("La agricultura de conservación en zonas de riego: principios y alcances.");
        objWebinarRealizado.setPonente("Ing. Sergio Fernando Márquez Quiroz");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter6 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str6 = "2020-09-01 00:00";
        LocalDateTime dateTime6 = LocalDateTime.parse(str6, formatter6);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime6);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Colegio de Postgraduados Campus Puebla");
        objWebinarRealizado.setNombre("Proyección de disponibilidad y demanda de agua del acuífero del Valle de Tecamachalco, Puebla.");
        objWebinarRealizado.setPonente("Dr. Luis Alberto Villarreal Manzo");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter7 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str7 = "2020-09-09 00:00";
        LocalDateTime dateTime7 = LocalDateTime.parse(str7, formatter7);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime7);//23-Junio-2020
        objWebinarRealizado.setInstitucion("ITK Francia");
        objWebinarRealizado.setNombre("Reúso de aguas residuales para riego: introducción y etapas claves de un proyecto de reúso");
        objWebinarRealizado.setPonente("Dr. Isaac Arturo Ramos Fuentes");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
    
        DateTimeFormatter formatter8 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str8 = "2020-09-15 00:00";
        LocalDateTime dateTime8 = LocalDateTime.parse(str8, formatter8);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime8);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        objWebinarRealizado.setNombre("Las Tecnologías Disruptivas en la Agricultura");
        objWebinarRealizado.setPonente("Dr. Waldo Ojeda Bustamante");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter10 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str10 = "2020-10-01 00:00";
        LocalDateTime dateTime10 = LocalDateTime.parse(str10, formatter10);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime10);//23-Junio-2020
        objWebinarRealizado.setInstitucion("CENID/RASPA-INIFAP");
        objWebinarRealizado.setNombre("Fotogrametría con drones: conceptos y análisis.");
        objWebinarRealizado.setPonente("M.I. Sergio Iván Jiménez Jiménez");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter11 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str11 = "2020-10-10 00:00";
        LocalDateTime dateTime11 = LocalDateTime.parse(str11, formatter11);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime11);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Investigador de INIFAP Campo Experimental “Valle del Fuerte” Sinaloa, México.");
        objWebinarRealizado.setNombre("Manejo del cultivo de papa utilizando grados días: bases y aplicaciones prácticas para fenología, riego, fertilización, plagas y enfermedades");
        objWebinarRealizado.setPonente("M.C. Ernesto Sifuentes Ibarra");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str12 = "2020-10-15 00:00";
        LocalDateTime dateTime12 = LocalDateTime.parse(str12, formatter12);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime12);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Profesor-Investigador Universidad Autónoma Chapingo");
        objWebinarRealizado.setNombre("Modelos en la Agricultura");
        objWebinarRealizado.setPonente("Dr. Abraham Rojano Aguilar");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter13 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str13 = "2020-11-01 00:00";
        LocalDateTime dateTime13 = LocalDateTime.parse(str13, formatter13);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime13);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Maestro-Investigador Depto. Agricultura y Ganadería Universidad de Sonora");
        objWebinarRealizado.setNombre("Evapotranspiración y dinámica de la humedad de un suelo con un cultivo perenne");
        objWebinarRealizado.setPonente("Dr. Fidencio Cruz Bautista y Dr. Julio Cesar Rodríguez");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter14 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str14 = "2020-11-10 00:00";
        LocalDateTime dateTime14 = LocalDateTime.parse(str14, formatter14);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime14);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        objWebinarRealizado.setNombre("Entendiendo el cambio climático: Conceptos para entender su definición y sus repercusiones");
        objWebinarRealizado.setPonente("Dr. Waldo Ojeda Bustamante");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter15 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str15 = "2020-11-15 00:00";
        LocalDateTime dateTime15 = LocalDateTime.parse(str15, formatter15);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime15);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Catedrático Instituto Tecnológico Superior de Comalcalco");
        objWebinarRealizado.setNombre("Fertilizantes para fertirriego: conceptos y propiedades");
        objWebinarRealizado.setPonente("M.C. Christian Martinez Sánchez");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        
        DateTimeFormatter formatter16 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str16 = "2020-12-01 00:00";
        LocalDateTime dateTime16 = LocalDateTime.parse(str16, formatter16);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime16);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objWebinarRealizado.setNombre("Webinar 15.1 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 1 de 5.");
        objWebinarRealizado.setPonente("M.I. Daniel Martínez Bazúa");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter17 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str17 = "2020-12-10 00:00";
        LocalDateTime dateTime17 = LocalDateTime.parse(str17, formatter17);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime17);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objWebinarRealizado.setNombre("Webinar 15.2 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 2 de 5.");
        objWebinarRealizado.setPonente("M.I. Daniel Martínez Bazúa");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        
        DateTimeFormatter formatter18 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str18 = "2020-12-15 00:00";
        LocalDateTime dateTime18 = LocalDateTime.parse(str18, formatter18);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime18);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objWebinarRealizado.setNombre("Webinar 15.3 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 3 de 5.");
        objWebinarRealizado.setPonente("M.I. Daniel Martínez Bazúa");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter19 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str19 = "2020-12-01 00:00";
        LocalDateTime dateTime19 = LocalDateTime.parse(str19, formatter19);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime19);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objWebinarRealizado.setNombre("Webinar 15.4 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 4 de 5.");
        objWebinarRealizado.setPonente("M.I. Daniel Martínez Bazúa");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter20 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str20 = "2020-12-10 00:00";
        LocalDateTime dateTime20 = LocalDateTime.parse(str20, formatter20);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime20);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        objWebinarRealizado.setNombre("Webinar 15.5 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 5 de 5.");
        objWebinarRealizado.setPonente("M.I. Daniel Martínez Bazúa");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
        DateTimeFormatter formatter21 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str21 = "2020-12-15 00:00";
        LocalDateTime dateTime21 = LocalDateTime.parse(str21, formatter21);
        objWebinarRealizado = new WebinarRealizado();
        objWebinarRealizado.setAsistentes(a);
        objWebinarRealizado.setFecha(dateTime21);//23-Junio-2020
        objWebinarRealizado.setInstitucion("Profesor-Investigador Universidad Autónoma Chapingo");
        objWebinarRealizado.setNombre("Webinar 17. Análisis integrado de la temperatura, humedad y entalpía en la agrometeorología");
        objWebinarRealizado.setPonente("Dr. Abraham Rojano Aguilar");
        objWebinarRealizado.setPresentacion(null);
        objWebinarRealizado.setUrlYoutube("https://www.youtube.com/watch?v=9lT8jF4PD_Y");
        ControladorWebinarRealizado.getInstance().save(objWebinarRealizado);
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
