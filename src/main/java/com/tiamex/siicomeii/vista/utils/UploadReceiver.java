/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.utils;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jhon
 */
public class UploadReceiver implements Receiver, SucceededListener,FailedListener,FinishedListener,StartedListener {
    public File file;
    ByteArrayOutputStream bos;
    TextArea txtArea;
    public final List<String> MIMES_ALLOWED = Arrays.asList("text/plain","text/csv","application/csv",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","application/vnd.ms-excel",
            "application/vnd.ms-excel.sheet.macroEnabled.12");

    
    public UploadReceiver(TextArea txtArea){
        this.txtArea = txtArea;
    }
    
    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        bos = new ByteArrayOutputStream(10240);
        return bos;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        txtArea.setValue(txtArea.getValue()+bos.toString());
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
    
}
