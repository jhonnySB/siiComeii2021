package com.tiamex.siicomeii;

import java.io.File;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/** @author cerimice **/

@SpringBootApplication
//@Theme("siiComeiiTheme")
public class Main{
    
    private static String baseDir = "";
    public static String getBaseDir(){
        if (baseDir.isEmpty()){baseDir = "./media";}
        return baseDir;
    }
    
    private static void createDirs(){
        File file;
        file = new File(getBaseDir());
            if(!file.exists()){file.mkdir();}
        
        file = new File(getBaseDir()+"/mailer");
            if(!file.exists()){file.mkdir();}
    }
    
    private static String urlServer = "https://localhost:8443";
    public static String getUrlServer(){return urlServer;}
    public static void setUrlServer(String valor){urlServer = valor;}
    
    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext cfg = SpringApplication.run(Main.class,args);
        createDirs();
    }
}
