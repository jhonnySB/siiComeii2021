/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.utils;

import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author jhon
 */
public class UploadReceiverImg implements Receiver, SucceededListener,FailedListener,FinishedListener,StartedListener,StreamSource {
    public File file;
    ByteArrayOutputStream bos;
    Image imagen;
    public final List<String> MIMES_ALLOWED = Arrays.asList("text/plain","text/csv","application/csv",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","application/vnd.ms-excel",
            "application/vnd.ms-excel.sheet.macroEnabled.12");

    
    public UploadReceiverImg(Image imagen){
        this.imagen = imagen;
    }
    
    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        bos = new ByteArrayOutputStream(10240);
        return bos;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {   
        
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        BufferedImage image;
        try {
            OutputStream outputStream;
            FileOutputStream fos = new FileOutputStream(event.getFilename());
            outputStream = fos;
            //image = ImageIO.read(bis);
            //ImageIO.write(image, "png", bos);
            StreamResource resource = new StreamResource(this, "test.png");
            imagen.setSource(resource);
        } catch (IOException ex) {
            Logger.getLogger(UploadReceiverImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Return a stream from the buffer
        //ByteArrayInputStream(imagebuffer.toByteArray());
        
        //FileResource resource = new FileResource(img);
        //imagen.setSource(resource);
    }

    @Override
    public void uploadFailed(Upload.FailedEvent event) {
        
    }

    @Override
    public void uploadFinished(Upload.FinishedEvent event) {
        
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
        System.out.println("MIME: "+event.getMIMEType().trim());
        String mimeType = event.getMIMEType();
        if(!MIMES_ALLOWED.contains(mimeType)){
            event.getUpload().interruptUpload();
            Notification.show("ERROR", "Archivo no soportado", Notification.Type.ERROR_MESSAGE);
        }
        
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(bos.toByteArray());
        
    }
    
}
