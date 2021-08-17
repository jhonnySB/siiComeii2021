/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.utils;

import com.vaadin.ui.Upload;

/**
 *
 * @author jhon
 */
public class UploadSuccessEvent extends Upload.SucceededEvent{
    
    public UploadSuccessEvent(Upload source, String filename, String mimeType, long length) {
        super(source, filename, mimeType, length);
    }
    
}
