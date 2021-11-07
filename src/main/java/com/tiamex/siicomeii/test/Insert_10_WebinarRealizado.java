package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**@author Bellcross**/
public class Insert_10_WebinarRealizado {

    public static void main(String[] args) throws Exception {
        short a = 2;

        WebinarRealizado obj;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String presentacion = "/pptx/web";
        String youtube = "https://www.youtube.com/watch?v=9lT8jF4PD_Y";
        Random r = new Random();
        
        // 2021
        // Enero
        String str = "2021-01-11 00:00";
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Universidad Nacional Autónoma de México");
        obj.setPonente("M.I Martín Rubén Jiménez Magaña");
        obj.setNombre("Riego por goteo.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-01-02 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Instituto Mexicano de Tecnología del Agua");
        obj.setPonente("M.C. Óscar Pita Díaz");
        obj.setNombre("Riego por Aspersión.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-01-20 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("AMH - Sinaloa");
        obj.setPonente("Dr. José Carlos Douriet Cárdenas");
        obj.setNombre("Riego por Microaspersión.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // MARZO
        dateTime = LocalDateTime.parse("2021-03-01 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        obj.setPonente("Dr. Waldo Ojeda Bustamante");
        obj.setNombre("Los Sistemas de Riego: reglas básicas para su mejor operación");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // JULIO
        dateTime = LocalDateTime.parse("2021-07-03 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. de INIFAP - Campo Experimental “Valle del Fuerte”");
        obj.setPonente("M.C. Ernesto Sifuentes Ibarra");
        obj.setNombre("Uso de sensores de humedad portátiles como apoyo al manejo integral del riego.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-11 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Consultor agrícola Los Mochis, Sinaloa");
        obj.setPonente("Ing. Sergio Fernando Márquez Quiroz");
        obj.setNombre("La agricultura de conservación en zonas de riego: principios y alcances.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-25 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Colegio de Postgraduados Campus Puebla");
        obj.setPonente("Dr. Luis Alberto Villarreal Manzo");
        obj.setNombre("Proyección de disponibilidad y demanda de agua del acuífero del Valle de Tecamachalco, Puebla.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-20 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("ITK Francia");
        obj.setPonente("Dr. Isaac Arturo Ramos Fuentes");
        obj.setNombre("Reúso de aguas residuales para riego: introducción y etapas claves de un proyecto de reúso");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-15 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        obj.setPonente("Dr. Waldo Ojeda Bustamante");
        obj.setNombre("Las Tecnologías Disruptivas en la Agricultura");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // AGOSTO
        dateTime = LocalDateTime.parse("2021-08-15 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("CENID/RASPA-INIFAP");
        obj.setPonente("M.I. Sergio Iván Jiménez Jiménez");
        obj.setNombre("Fotogrametría con drones: conceptos y análisis.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // NOVIEMBRE
        dateTime = LocalDateTime.parse("2021-11-10 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. de INIFAP Campo Experimental “Valle del Fuerte”");
        obj.setPonente("M.C. Ernesto Sifuentes Ibarra");
        obj.setNombre("Manejo del cultivo de papa utilizando grados días: bases y aplicaciones prácticas para fenología, riego, fertilización, plagas y enfermedades");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-11-21 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. de Universidad Autónoma Chapingo");
        obj.setPonente("Dr. Abraham Rojano Aguilar");
        obj.setNombre("Modelos en la Agricultura");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        
        // 2020
        //FEBRERO
        dateTime = LocalDateTime.parse("2020-02-21 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. Depto. Agricultura y Ganadería Universidad de Sonora");
        obj.setPonente("Dr. Fidencio Cruz Bautista y Dr. Julio Cesar Rodríguez");
        obj.setNombre("Evapotranspiración y dinámica de la humedad de un suelo con un cultivo perenne");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // ABRIL
        dateTime = LocalDateTime.parse("2020-04-03 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        obj.setPonente("Dr. Waldo Ojeda Bustamante");
        obj.setNombre("Entendiendo el cambio climático: Conceptos para entender su definición y sus repercusiones");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-03 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Catedrático Instituto Tecnológico Superior de Comalcalco");
        obj.setPonente("M.C. Christian Martinez Sánchez");
        obj.setNombre("Fertilizantes para fertirriego: conceptos y propiedades");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-11 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setNombre("Webinar 15.1 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 1 de 5.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-15 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setNombre("Webinar 15.2 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 2 de 5.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-08 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setNombre("Webinar 15.3 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 3 de 5.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // MAYO
        dateTime = LocalDateTime.parse("2020-05-22 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setNombre("Webinar 15.4 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 4 de 5.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-05-11 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setNombre("Webinar 15.5 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 5 de 5.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // SPETIEMBRE
        dateTime = LocalDateTime.parse("2020-09-02 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Dr. Abraham Rojano Aguilar");
        obj.setNombre("Webinar 17. Análisis integrado de la temperatura, humedad y entalpía en la agrometeorología");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-09-01 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Dr. Abraham Rojano Aguilar");
        obj.setNombre("Webinar 8. Análisis integrado de la temperatura");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-09-19 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("Dr. Jesús Sanchéz Pedraza");
        obj.setNombre("Webinar 18. Ententiendo el riego por aspersión");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // DICIEMBRE
        dateTime = LocalDateTime.parse("2020-12-10 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Ing. Cesar Pérez Gonzáles");
        obj.setNombre("Webinar 18.1 riego por aspersión");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        
        // 2019
        // Junio
        dateTime = LocalDateTime.parse("2019-06-01 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Catedrático Instituto Tecnológico Superior de Comalcalco");
        obj.setPonente("Ing. Mario Pérez Gonzáles");
        obj.setNombre("Webinar 19.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2019-06-10 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Consultor agrícola Los Mochis, Sinaloa");
        obj.setPonente("Ing. Manuel Pérez García");
        obj.setNombre("Webinar 20.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        
            // julio
        dateTime = LocalDateTime.parse("2019-07-10 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Ing. Moises García Castañeda");
        obj.setNombre("Webinar 21.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
            // octubre
        dateTime = LocalDateTime.parse("2019-10-10 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("Dr. Pedro Sánchez");
        obj.setNombre("Webinar 22.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2019-10-10 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("Ing. Alejandro Gonzáles");
        obj.setNombre("Webinar 23.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2019-10-10 00:00", formatter);
        obj = new WebinarRealizado();
        obj.setFecha(dateTime);
        obj.setInstitucion("Colegio de Postgraduados Campus Puebla");
        obj.setPonente("Ing. Jesús Fernando");
        obj.setNombre("Webinar 24.");
        obj.setPresentacion(presentacion);
        obj.setUrlYoutube(youtube);
        obj.setAsistentes((short)r.nextInt(30));
        ControladorWebinarRealizado.getInstance().save(obj);
        

        

        }
}