/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.test;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.threeten.extra.YearWeek;

/**
 *
 * @author jhon
 */
public class regex {

    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("America/Mexico_City");
        YearWeek currentYearWeek = YearWeek.now(zoneId);
        LocalDate ld = LocalDate.of( 2021 , Month.OCTOBER , 4 ) ;
        YearWeek webinarYearWeek = YearWeek.from(ld);
        currentYearWeek.equals(webinarYearWeek);
        System.out.println(currentYearWeek.equals(webinarYearWeek));
        
        LocalDateTime date = LocalDateTime.now(ZoneId.of("America/Mexico_City"));
        //System.out.println(LocalDateTime.now(ZoneId.of("America/Mexico_City")).getDayOfWeek());
        
        /*
        String some = "some string";
        String other = "some string";
        boolean identical = some == other;
        System.out.println("Identical? "+identical);
        some.equals(other);
        
        Collection<String> bags = new ArrayList<String>();
  
        // Add values in the bags list.
        bags.add("pen");
        bags.add("pencil");
        bags.add("paper");
  
        // Creating another array list
        Collection<String> boxes = new ArrayList<String>();
        //Collection<String> boxes = Arrays.asList("pen","paper","books","rubber");
        // Add values in the boxes list.
        boxes.add("pen");
        boxes.add("paper");
        boxes.add("books");
        boxes.add("rubber");
  
        // Before Applying method print both lists
        System.out.println("Bags Contains :" + bags);
        System.out.println("Boxes Contains :" + boxes);
  
        // Apply retainAll() method to boxes passing bags as parameter
        boxes.retainAll(bags); 
  
        // Displaying both the lists after operation
        System.out.println("\nAfter Applying retainAll()"+
        " method to Boxes\n");
        System.out.println("Bags Contains :" + bags);
        System.out.println("Boxes Contains :" + boxes);
        /*
        HashMap<String, String> map = new HashMap<>();
        map.put("comeii", "");
        map.put("immta", "");
        map.put("cemac", "");
        System.out.println("Map: "+map);
        map.replace("comeii", "4");
        System.out.println("Upd map: "+map);
        System.out.println("Get key value: "+map.get("comeii")); */
 /*
        long l = new Long(-10);
        System.out.println(Integer.toUnsignedLong((int)l) );
        int signedNum = (int)Long.signum(l); 
        System.out.println(signedNum);
        if(signedNum<0){ // fecha antigua
            System.out.println("-");
        }else if(signedNum>0){ // fecha proxima
            System.out.println("+");
        }else{ // fecha actual
            System.out.println("0");
        } 
        //System.out.println(Long.toUnsignedString(-10));
        //System.out.println(Integer.parseInt();
       /*
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       String inputString1 = "18-08-2021";
       LocalDate date1 = LocalDate.parse(inputString1, dtf);
       long days = ChronoUnit.DAYS.between(LocalDate.now(),date1);
       System.out.println("Dias de diferencia: "+days); 
         */
    }
}
