package com.tiamex.siicomeii.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.tiamex.siicomeii.Main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import com.itextpdf.text.Image;
import java.text.SimpleDateFormat;
import java.util.Date;

/** @author cerimice **/
public class TestPdf{
    public static void main(String[] args){
        try{ 
            //String k = "<html><body> <b>This</b> is my <i>Project</i> </body></html>";
            String cad = "";
            for(String a:Files.readAllLines(new File(Main.getBaseDir()+"/generadorPdf.html").toPath())){
                cad += a;
            }
            
            Date fecha = new Date();
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            String StringFecha= DateFor.format(fecha);
            
            cad = cad.replaceAll(":nombre","Juanito lopez");
            cad = cad.replaceAll(":ponencia","“Uso de Acolchados Plásticos en la Agricultura”");
            cad = cad.replaceAll(":fecha",StringFecha);
            cad = cad.replaceAll(":evento", "Webinar 2020");
            
            OutputStream file = new FileOutputStream(new File(Main.getBaseDir()+"/prueba.pdf"));
            Document document = new Document(PageSize.LETTER.rotate());
                document.setMargins(5,5,5,5);
            PdfWriter.getInstance(document,file);
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
            file.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    } 
}