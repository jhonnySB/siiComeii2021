package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.mailer.SiiComeiiMailer;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public final class AsistenciaWebinarModalWin extends TemplateModalWin {

    private TextArea correos;
    private TextField webinarRealizado;
    private TextArea correosInvalidos;
    private static final String PATTERN_EMAIL
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /*
    public AsistenciaWebinarModalWin() {
        init();
        delete.setVisible(false);
    } */
    public AsistenciaWebinarModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        correos = new TextArea("Correos");
        correos.setPlaceholder("Escriba los correos separados por coma.");
        Element.cfgComponent(correos);
        correos.setRequiredIndicatorVisible(true);
        correos.focus();

        webinarRealizado = new TextField();
        Element.cfgComponent(webinarRealizado, "Webinar");
        webinarRealizado.setReadOnly(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(correos);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(webinarRealizado);

        contentLayout.addComponent(contenido);
        setCaption("Asistencia webinar");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            WebinarRealizado obj = ControladorWebinarRealizado.getInstance().getById(id);
            this.id = obj.getId();
            webinarRealizado.setValue(obj.getNombre());

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorWebinarRealizado.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    

    @Override
    protected void buttonAcceptEvent() {

        System.out.println(correos.getValue());
        String emails = correos.getValue();
        System.out.println(emails.matches(PATTERN_EMAIL));
        List<String> emailsInvalidos;
        if (validarCampos()) {
            try {
                SiiComeiiMailer mailer = new SiiComeiiMailer();
                emailsInvalidos = mailer.enviarConstancias(emails, this.id);
                System.out.println(emailsInvalidos);
                if(!emailsInvalidos.isEmpty()){
                    Notification notif = new Notification("AVISO | ",Notification.Type.HUMANIZED_MESSAGE);
                    notif.setPosition(Position.TOP_CENTER);
                    notif.setDelayMsec(3000);
                    notif.setDescription("Se encontraron correos no válidos o no registrados");  //agregar un label dimiss abajo de los correos no validos o no encontraos
                    notif.setIcon(VaadinIcons.WARNING);
                    notif.setHtmlContentAllowed(true);
                    notif.show(ui.getPage());
                    //CustomLayout alertCorreos = new CustomLayout(new FileInputStream(new File(Main.getBaseDir()+"/mailer/alertCoreos.html")));
                    ResponsiveLayout alertCorreos = new ResponsiveLayout();
                    ResponsiveRow rowCorreos = alertCorreos.addRow();
                    correosInvalidos = new TextArea();
                    contentLayout.addComponent(alertCorreos);
                }else{
                    Notification notif = new Notification("ÉXITO | ",Notification.Type.HUMANIZED_MESSAGE);
                    notif.setPosition(Position.TOP_CENTER);
                    notif.setDelayMsec(3000);
                    notif.setDescription("Se enviaron las constancias a todos los correos");
                    notif.setIcon(VaadinIcons.CHECK);
                    notif.setHtmlContentAllowed(true);
                    notif.show(ui.getPage());
                }
                
                
                /*if (!email.matches(PATRON_EMAIL)) {
                emailsInvalidos.add(email);
                }
                
                if (emailsAgremiados.size() > 0) {
                    Notification notif = new Notification("ERROR | ",Notification.Type.HUMANIZED_MESSAGE);
                    notif.setPosition(Position.TOP_CENTER);
                    notif.setDelayMsec(3000);
                    int cont=0;
                    notif.setDescription("Correo/s no válidos");
                    notif.setIcon(VaadinIcons.WARNING);
                    notif.setHtmlContentAllowed(true);
                    notif.show(ui.getPage());
                }*/
            } catch (Exception ex) {
                Logger.getLogger(AsistenciaWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        /*
        if (validarCampos()) {
        try {
        //ciclo para cada correo encontrado
        AsistenciaWebinar obj = new AsistenciaWebinar();
        obj.setUsuario(ui.getUsuario().getId());
        obj.setWebinar(id);
        obj = ControladorAsistenciaWebinar.getInstance().save(obj);
        if (obj != null) {
        Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        ui.getFabricaVista().getWebinarRealizadoDlg().updateDlg();
        close();
        }
        } catch (Exception ex) {
        Logger.getLogger(AsistenciaWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        /*
        try {
        if (validarCampos()) {
        WebinarRealizado obj = new WebinarRealizado();
        obj.setId(id);
        obj.setFecha(fecha.getValue());
        obj.setInstitucion(institucion.getValue());
        obj.setNombre(nombre.getValue());
        obj.setPonente(ponente.getValue());
        obj.setPresentacion("xd");
        obj.setUrlYoutube("xd 2");
        obj = ControladorWebinarRealizado.getInstance().save(obj);
        if (obj != null) {
        Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        ui.getFabricaVista().getWebinarRealizadoDlg().updateDlg();
        close();
        }
        } else {
        Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
        }
        } catch (Exception ex) {
        Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
         */
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {

        //TextArea pnotesField = fg.buildAndBind("YOUR_CAPTION", correos, TextArea.class);
        
        if (correos.isEmpty()) {
            correos.focus();
            Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            return false;
        } 
        return true;
    }

}
