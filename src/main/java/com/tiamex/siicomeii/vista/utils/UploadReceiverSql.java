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
import com.vaadin.shared.ui.ContentMode;
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
import java.text.DecimalFormat;
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
public class UploadReceiverSql implements Receiver, SucceededListener, FailedListener, FinishedListener, StartedListener, StreamSource,
        ProgressListener {

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    ByteArrayOutputStream bos;
    Image imagen;
    boolean error = false, newFileReceived = false;
    String mimeType = "", filename = "", pathSql;
    Upload uploader;
    Label infoLbl,errorLbl;
    public final List<String> MIMES_ALLOWED = Arrays.asList("image/jpeg", "image/png", "image/gif");

    public UploadReceiverSql(Upload uploader, Label infoLbl,Label errorLbl) {
        this.uploader = uploader;
        this.infoLbl = infoLbl;
        this.errorLbl = errorLbl;
    }

    public UploadReceiverSql() {
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        this.newFileReceived = true;
        this.mimeType = mimeType;
        this.filename = filename;
        System.out.println("filename: " + filename);
        errorLbl.setValue("");
        uploader.setComponentError(null);
        bos = new ByteArrayOutputStream(10240);
        return bos;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        errorLbl.setValue("");
        infoLbl.setValue("<b>Nombre: </b>" + filename + "<br><b>Tamaño: </b>" + 
                convertToStringRepresentation(event.getLength()));
        System.out.println("l: " + event.getLength());
        File file;
        try {
            file = File.createTempFile(event.getFilename(), ".sql");
            try (FileOutputStream outFile = new FileOutputStream(file)) {
                outFile.write(bos.toByteArray());
                outFile.close();
            }

            pathSql = file.getAbsolutePath();
            /*String fullFileName = event.getFilename();
            StreamResource resource = new StreamResource(this,fullFileName);
            event.getUpload().setButtonCaption("Cargar otra imagen");
            imagen.setCaption("Imagen seleccionada: "+fullFileName);
            imagen.setEnabled(true);
            imagen.setSource(resource); */
        } catch (IOException ex) {
            Logger.getLogger(UploadReceiverSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String convertToStringRepresentation(final long value) {
        final long[] dividers = new long[]{T, G, M, K, 1};
        final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
        String result = null;
        if (value > 1) {
            for (int i = 0; i < dividers.length; i++) {
                final long divider = dividers[i];
                if (value >= divider) {
                    result = format(value, divider, units[i]);
                    break;
                }
            }
        }
        return result;
    }

    private static String format(final long value,
            final long divider,
            final String unit) {
        final double result
                = divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#,##0.#").format(result) + " " + unit;
    }

    public String getPathSql() {
        return pathSql;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFilename() {
        return filename;
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
        errorLbl.setValue("<b><span style=color:black>Cargando archivo..</span></b>");
    }

}
