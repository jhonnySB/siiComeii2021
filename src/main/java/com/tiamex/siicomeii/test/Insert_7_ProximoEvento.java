package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Bellcross*
 */
public class Insert_7_ProximoEvento {

    public static void main(String[] args) throws Exception {

        ProximoEvento objProximoEvento;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str = "2020-08-16 00:00";
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        objProximoEvento = new ProximoEvento();
        objProximoEvento.setDescripcion("Los lenguajes de programación en la ingeniería: del aprendizaje a la aplicación");
        objProximoEvento.setFecha(dateTime);//16-Agosto-2020
        objProximoEvento.setImagen(null);
        objProximoEvento.setTitulo("SWebinar 24");
        objProximoEvento.setUsuario(1);//1
        ControladorProximoEvento.getInstance().save(objProximoEvento);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str1 = "2020-08-21 00:00";
        LocalDateTime dateTime1 = LocalDateTime.parse(str1, formatter1);
        objProximoEvento = new ProximoEvento();
        objProximoEvento.setDescripcion("La disponibilidad de agua y energía en un entorno incierto");
        objProximoEvento.setFecha(dateTime1);//21-Agosto-2020
        objProximoEvento.setImagen(null);
        objProximoEvento.setTitulo("Foro de opinión");
        objProximoEvento.setUsuario(2);
        ControladorProximoEvento.getInstance().save(objProximoEvento);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String str2 = "2020-08-23 00:00";
        LocalDateTime dateTime2 = LocalDateTime.parse(str2, formatter2);
        objProximoEvento = new ProximoEvento();
        objProximoEvento.setDescripcion("Los procesos de ensalitramiento de los suelos en los distritos de riego de México");
        objProximoEvento.setFecha(dateTime2);//23-Agosto-2020
        objProximoEvento.setImagen(null);
        objProximoEvento.setTitulo("Webinar 25");
        objProximoEvento.setUsuario(3);
        ControladorProximoEvento.getInstance().save(objProximoEvento);

    }

}
