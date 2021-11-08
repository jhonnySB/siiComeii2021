package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**@author Bellcross*/
public class Insert_5_ProximoWebinar {

    public static void main(String[] args) throws Exception {

        ProximoWebinar obj;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        File file = new File(Main.getBaseDir()+"/img_prox_web.png");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        
        // WEBINARS 2021
                // Enero
        String str = "2021-01-11 00:00";
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Universidad Nacional Autónoma de México");
        obj.setPonente("M.I Martín Rubén Jiménez Magaña");
        obj.setTitulo("Riego por goteo.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-01-02 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Instituto Mexicano de Tecnología del Agua");
        obj.setPonente("M.C. Óscar Pita Díaz");
        obj.setTitulo("Riego por Aspersión.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-01-20 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("AMH - Sinaloa");
        obj.setPonente("Dr. José Carlos Douriet Cárdenas");
        obj.setTitulo("Riego por Microaspersión.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // MARZO
        dateTime = LocalDateTime.parse("2021-03-01 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        obj.setPonente("Dr. Waldo Ojeda Bustamante");
        obj.setTitulo("Los Sistemas de Riego: reglas básicas para su mejor operación");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // JULIO
        dateTime = LocalDateTime.parse("2021-07-03 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. de INIFAP - Campo Experimental “Valle del Fuerte”");
        obj.setPonente("M.C. Ernesto Sifuentes Ibarra");
        obj.setTitulo("Uso de sensores de humedad portátiles como apoyo al manejo integral del riego.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-11 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Consultor agrícola Los Mochis, Sinaloa");
        obj.setPonente("Ing. Sergio Fernando Márquez Quiroz");
        obj.setTitulo("La agricultura de conservación en zonas de riego: principios y alcances.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-25 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Colegio de Postgraduados Campus Puebla");
        obj.setPonente("Dr. Luis Alberto Villarreal Manzo");
        obj.setTitulo("Proyección de disponibilidad y demanda de agua del acuífero del Valle de Tecamachalco, Puebla.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-20 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("ITK Francia");
        obj.setPonente("Dr. Isaac Arturo Ramos Fuentes");
        obj.setTitulo("Reúso de aguas residuales para riego: introducción y etapas claves de un proyecto de reúso");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-07-15 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        obj.setPonente("Dr. Waldo Ojeda Bustamante");
        obj.setTitulo("Las Tecnologías Disruptivas en la Agricultura");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // AGOSTO
        dateTime = LocalDateTime.parse("2021-08-15 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("CENID/RASPA-INIFAP");
        obj.setPonente("M.I. Sergio Iván Jiménez Jiménez");
        obj.setTitulo("Fotogrametría con drones: conceptos y análisis.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // NOVIEMBRE
        dateTime = LocalDateTime.parse("2021-11-10 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. de INIFAP Campo Experimental “Valle del Fuerte”");
        obj.setPonente("M.C. Ernesto Sifuentes Ibarra");
        obj.setTitulo("Manejo del cultivo de papa utilizando grados días: bases y aplicaciones prácticas para fenología, riego, fertilización, plagas y enfermedades");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2021-11-21 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. de Universidad Autónoma Chapingo");
        obj.setPonente("Dr. Abraham Rojano Aguilar");
        obj.setTitulo("Modelos en la Agricultura");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        
        // WEBINARS 2020
        
            //FEBRERO
        dateTime = LocalDateTime.parse("2020-02-21 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. Depto. Agricultura y Ganadería Universidad de Sonora");
        obj.setPonente("Dr. Fidencio Cruz Bautista y Dr. Julio Cesar Rodríguez");
        obj.setTitulo("Evapotranspiración y dinámica de la humedad de un suelo con un cultivo perenne");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // ABRIL
        dateTime = LocalDateTime.parse("2020-04-03 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Colegio Mexicano de Ingenieros en Irrigación A.C.");
        obj.setPonente("Dr. Waldo Ojeda Bustamante");
        obj.setTitulo("Entendiendo el cambio climático: Conceptos para entender su definición y sus repercusiones");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-03 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Catedrático Instituto Tecnológico Superior de Comalcalco");
        obj.setPonente("M.C. Christian Martinez Sánchez");
        obj.setTitulo("Fertilizantes para fertirriego: conceptos y propiedades");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-11 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setTitulo("Webinar 15.1 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 1 de 5.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-15 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setTitulo("Webinar 15.2 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 2 de 5.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-04-08 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setTitulo("Webinar 15.3 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 3 de 5.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // MAYO
        dateTime = LocalDateTime.parse("2020-05-22 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setTitulo("Webinar 15.4 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 4 de 5.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-05-11 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("M.I. Daniel Martínez Bazúa");
        obj.setTitulo("Webinar 15.5 Generación de hidroenergía en Infraestructura hidroagrícola. Parte 5 de 5.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // SPETIEMBRE
        dateTime = LocalDateTime.parse("2020-09-02 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Dr. Abraham Rojano Aguilar");
        obj.setTitulo("Webinar 17. Análisis integrado de la temperatura, humedad y entalpía en la agrometeorología");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-09-01 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Dr. Abraham Rojano Aguilar");
        obj.setTitulo("Webinar 8. Análisis integrado de la temperatura");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2020-09-19 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("Dr. Jesús Sanchéz Pedraza");
        obj.setTitulo("Webinar 18. Ententiendo el riego por aspersión");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // DICIEMBRE
        dateTime = LocalDateTime.parse("2020-12-10 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Ing. Cesar Pérez Gonzáles");
        obj.setTitulo("Webinar 18.1 riego por aspersión");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        // WEBINARS 2019
            // Junio
        dateTime = LocalDateTime.parse("2019-06-01 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Catedrático Instituto Tecnológico Superior de Comalcalco");
        obj.setPonente("Ing. Mario Pérez Gonzáles");
        obj.setTitulo("Webinar 19.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2019-06-10 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Consultor agrícola Los Mochis, Sinaloa");
        obj.setPonente("Ing. Manuel Pérez García");
        obj.setTitulo("Webinar 20.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        
            // julio
        dateTime = LocalDateTime.parse("2019-07-15 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Invg. Universidad Autónoma Chapingo");
        obj.setPonente("Ing. Moises García Castañeda");
        obj.setTitulo("Webinar 21.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
            // octubre
        dateTime = LocalDateTime.parse("2019-10-01 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("Dr. Pedro Sánchez");
        obj.setTitulo("Webinar 22.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2019-10-10 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Asociación Mexicana de Hidráulica (AMH)");
        obj.setPonente("Ing. Alejandro Gonzáles");
        obj.setTitulo("Webinar 23.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        dateTime = LocalDateTime.parse("2019-10-20 00:00", formatter);
        obj = new ProximoWebinar();
        obj.setFecha(dateTime);
        obj.setImagen(fileContent);
        obj.setInstitucion("Colegio de Postgraduados Campus Puebla");
        obj.setPonente("Ing. Jesús Fernando");
        obj.setTitulo("Webinar 24.");
        obj.setUsuario(2);
        ControladorProximoWebinar.getInstance().save(obj);
        
        
        
        // ---------------------------
    }

}
