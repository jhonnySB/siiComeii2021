/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.reportes.base.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.tiamex.siicomeii.Main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author jhon
 */
public class ConstanciaAgremiado {
    
    private final String nombreWebinar;
    private final String nombreAgremiado;
    private final LocalDateTime fecha;
    private File file;
    
    public ConstanciaAgremiado(String nombreWebinar,String nombreAgremiado,LocalDateTime fecha){
        this.nombreWebinar = nombreWebinar;
        this.nombreAgremiado = nombreAgremiado;
        this.fecha = fecha;
    }
    
    public String generarConstancia(){
        String filename = null;
        try{ 
            String cad = "";
            
            for(String a:Files.readAllLines(new File(Main.getBaseDir()+"/generadorPdf.html").toPath())){
                cad += a;
            }
            
            /*Date fecha = new Date();
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            String StringFecha= DateFor.format(fecha);*/
            
            cad = cad.replaceAll(":nombre",this.nombreAgremiado);
            cad = cad.replaceAll(":ponencia",this.nombreWebinar);
            Locale varRegional=new Locale("es", "MX");
            String fechaFormateada=fecha.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' y",varRegional));
            //cad = cad.replaceAll(":lugarFecha","Cuernavaca, Morelos a "+fecha.getDayOfMonth()+" de "+fechaFormateada);
            cad = cad.replaceAll(":lugarFecha","Cuernavaca, Morelos a "+fechaFormateada);
            cad = cad.replaceAll(":evento","Webinar "+fecha.getYear());
            file = new File(Main.getBaseDir()+"/tempConstancias/constancia_"+nombreAgremiado.
                    toLowerCase()+".pdf");
            filename = file.toString();
            try (OutputStream fileOutput = new FileOutputStream(filename)) {
                Document document = new Document(PageSize.LETTER.rotate());
                document.setMargins(5,5,5,5);
                PdfWriter.getInstance(document,fileOutput);
                document.open();
                
                String dirImagen = Main.getBaseDir() + "/fondoPdf.jpg";
                Image jpg;
                jpg = Image.getInstance(dirImagen);
                //jpg.scaleToFit(792,612);
                jpg.setAbsolutePosition(0,0);
                document.add(jpg);
                HTMLWorker htmlWorker = new HTMLWorker(document);
                htmlWorker.parse(new StringReader(cad));
                document.close();
            }
            
        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
        return filename;
    }
    
    public boolean borrarConstancia(String filename){
        return file.delete();
    }
    
}
