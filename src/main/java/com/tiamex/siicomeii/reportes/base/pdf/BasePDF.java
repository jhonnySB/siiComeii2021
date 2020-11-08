package com.tiamex.siicomeii.reportes.base.pdf;

import com.itextpdf.text.Document;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.ElementsPDF;
import com.tiamex.siicomeii.reportes.base.pdf.cfg.Format;
import com.tiamex.siicomeii.utils.Utils;
import com.vaadin.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Logger;

/** @author cerimice **/
/** @company tiamex **/

public abstract class BasePDF  implements StreamResource.StreamSource{
    protected Format format;
    protected Format encabezados;
    protected Format contenido;
    protected final ElementsPDF elementsPDF;
    
    protected ByteArrayOutputStream pdfFile;
    
    protected Document document;
    
    public BasePDF(){
        pdfFile = new ByteArrayOutputStream();
        
        format = new Format();
        elementsPDF = new ElementsPDF();
    }

    @Override
    public InputStream getStream(){
        return new ByteArrayInputStream(pdfFile.toByteArray());
    }
    
    public File getFile() throws Exception{
        try{
            File file;
            try (InputStream input = this.getStream()){
                file = new File(Utils.getTempFolder()+Utils.getFormatId().format(new Date())+".pdf");
                try (OutputStream salida = new FileOutputStream(file)){
                    byte[] buf =new byte[1024];
                    int len;
                    while((len=input.read(buf))>0){salida.write(buf,0,len);}
                }
            }
            return file;
        }catch (IOException ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(),ex.getMessage()); throw ex;
        }
    }
    
    protected abstract void create() throws Exception;
}
