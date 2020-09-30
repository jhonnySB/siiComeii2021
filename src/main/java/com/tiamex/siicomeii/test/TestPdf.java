package com.tiamex.siicomeii.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;

/** @author cerimice **/
public class TestPdf{
    public static void main(String[] args){
        try{ 
            //String k = "<html><body> <b>This</b> is my <i>Project</i> </body></html>";
            String cad = "";
            for(String a:Files.readAllLines(new File("/home/cerimice/Escritorio/ejemplo1.html").toPath())){
                cad += a;
            }
            
            cad = cad.replaceAll(":nombre","Juanito lopez");
            cad = cad.replaceAll(":ponencia","“Uso de Acolchados Plásticos en la Agricultura”");
            
            OutputStream file = new FileOutputStream(new File("/home/cerimice/Escritorio/prueba.pdf"));
            Document document = new Document(PageSize.LETTER.rotate());
                document.setMargins(5,5,5,5);
            PdfWriter.getInstance(document,file);
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);
            htmlWorker.parse(new StringReader(cad));
            document.close();
            file.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    } 
}

/*
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<style type="text/css">
			*{
				font-family: "Noto Sans";
			}
		</style>
	</head>

	<body>
		<h3 align="center">EL COLEGIO MEXICANO DE INGENIEROS EN IRRIGACIÓN</h3>
		<h5 align="center">OTORGA LA PRESENTE</h5>
		<br/>
		<h1 align="center">CONSTANCIA</h1>

		<img src="/media/datos/images/walls/wall013.jpg" width="30%">
		
		<div align="center">
			A: <h3>Fredy de la Cruz</h3> Por su asistencia a la conferencia virtual
		</div>
		<br/><br/>
		<div align="center" style="margin: 50px 200px 50px 200px;">
			“Uso de Acolchados Plásticos en la Agricultura”, impartida por el Dr. Juan P. Munguía López, con una duración de dos horas y 45 minutos, y que fue transmitida por la plataforma digital ZOOM, en el marco del seminario virtual 2020 que organiza nuestro colegio.
		</div>
	</body>
</html>
*/