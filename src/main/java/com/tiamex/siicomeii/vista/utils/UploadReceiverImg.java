/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.utils;

import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
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
        ProgressListener {

    public File file;
    ByteArrayOutputStream bos;
    Image imagen;
    boolean error = false, newFileReceived = false;
    public List<String> mimes_allowed;
    String ext_allowed;
    Upload uploader;
    Label lblError;

    public UploadReceiverImg(Image imagen, List<String> mimes_allowed, Upload uploader, String ext_allowed) {
        this.imagen = imagen;
        this.mimes_allowed = mimes_allowed;
        this.uploader = uploader;
        this.ext_allowed = ext_allowed;
    }
    
    public UploadReceiverImg(Image imagen, List<String> mimes_allowed, Upload uploader, String ext_allowed,Label lblError) {
        this.imagen = imagen;
        this.mimes_allowed = mimes_allowed;
        this.uploader = uploader;
        this.ext_allowed = ext_allowed;
        this.lblError = lblError;
    }

    public UploadReceiverImg() {
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        lblError.setValue("");
        this.newFileReceived = true;
        if (ext_allowed.isEmpty()) {
            if (!mimes_allowed.contains(mimeType)) {
                Notification not = new Notification("Error", "Solo se permiten archivos JPEG y PNG", Notification.Type.ERROR_MESSAGE);
                //lblError.setValue("<span style=\"color:red\">Archivo no soportado</span>");
                not.show(Page.getCurrent());
                this.newFileReceived = false;
                uploader.interruptUpload();
            }
        }
        bos = new ByteArrayOutputStream(10240);
        return bos;

    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        String fullFileName = event.getFilename();
        StreamResource resource = new StreamResource(this, fullFileName);
        event.getUpload().setButtonCaption("Cargar otra imagen");
        imagen.setCaption("Imagen seleccionada: " + fullFileName);
        imagen.setEnabled(true);
        imagen.setSource(resource);
    }

    public boolean newFileReceived() {
        return newFileReceived;
    }

    public byte[] getContentByte() {
        return bos.toByteArray();
    }

    @Override
    public void uploadFailed(Upload.FailedEvent event) {
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
        if(contentLength>5242880){
            Notification.show("Error", "Tamaño máximo permitido: 5Mb", Notification.Type.WARNING_MESSAGE);
            uploader.interruptUpload();
        }
    }

}
