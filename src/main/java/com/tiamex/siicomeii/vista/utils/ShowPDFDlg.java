package com.tiamex.siicomeii.vista.utils;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/* @author cerimice */
public class ShowPDFDlg extends Window
   {
    private Embedded file;
    
    public ShowPDFDlg(StreamResource pdf)
       {iniciarComponentes(pdf);}
    
    private void iniciarComponentes(StreamResource pdf)
       {
        pdf.setMIMEType("application/pdf");
        file = new Embedded();
            file.setWidth("100%");
            file.setHeight("100%");
            file.setType(Embedded.TYPE_BROWSER);
            file.setSource(pdf);
        
        VerticalLayout content = new VerticalLayout();
            content.setWidth("100%");
            content.setHeight("100%");
            content.setSpacing(false);
            content.setMargin(false);
            content.addComponent(file);
        
        VerticalLayout main = new VerticalLayout();
            main.setWidth("100%");
            main.setHeight("100%");
            main.addComponent(content);
            main.setComponentAlignment(content, Alignment.TOP_CENTER);
            
        this.setContent(main);
        this.setWidth("80%");
        this.setHeight("70%");
        this.setResizable(true);
        this.setModal(true);
        }
    }
