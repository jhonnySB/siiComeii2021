package com.tiamex.siicomeii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/** @author cerimice **/

@SpringBootApplication
public class Main{
    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext cfg = SpringApplication.run(Main.class,args);
        
    }
}
