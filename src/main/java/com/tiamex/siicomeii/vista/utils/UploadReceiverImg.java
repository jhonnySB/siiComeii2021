/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.utils;

import com.vaadin.server.ClientConnector.AttachListener;
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
import com.vaadin.ui.Upload.ProgressListener;
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
public class UploadReceiverImg implements Receiver, SucceededListener, FailedListener, FinishedListener, StartedListener, StreamSource,
        ProgressListener{

    public File file;
    ByteArrayOutputStream bos;
    Image imagen;
    boolean error = false,newFileReceived = false;
    public final List<String> MIMES_ALLOWED = Arrays.asList("image/jpeg","image/png","image/gif");

    public UploadReceiverImg(Image imagen) {
        this.imagen = imagen;
    }
    
    public UploadReceiverImg( ) {
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        this.newFileReceived =true;
        bos = new ByteArrayOutputStream(10240);
        return bos;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        //image = ImageIO.read(bis);
        //ImageIO.write(image, "png", bos);
        String fullFileName = event.getFilename();
        StreamResource resource = new StreamResource(this,fullFileName);
        event.getUpload().setButtonCaption("Cargar otra imagen");
        imagen.setCaption("Imagen seleccionada: "+fullFileName);
        imagen.setEnabled(true);
        imagen.setSource(resource);
    }
    
    public boolean newFileReceived(){
        return newFileReceived;
    }
    
    public byte[] getContentByte(){
        return bos.toByteArray();
    }
    
    
    @Override
    public void uploadFailed(Upload.FailedEvent event){
    }

    @Override
    public void uploadFinished(Upload.FinishedEvent event) {

    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {

    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(bos.toByteArray());
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
       
    }

}
