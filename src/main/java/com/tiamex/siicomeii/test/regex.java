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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jhon
 */
public class regex {
    public static void main(String[] args){

        List<List<String>> list = new ArrayList<>();
        list.add(Arrays.asList("nombre","1"));
        System.out.println("Lista: "+list);
        System.out.println("Contiene nombre? "+list.contains(Arrays.asList("nombre","1")));
        System.out.println("Contiene 1 ? "+list.contains("1"));
        
        
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
