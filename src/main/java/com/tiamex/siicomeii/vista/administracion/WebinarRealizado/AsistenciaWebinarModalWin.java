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
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public final class AsistenciaWebinarModalWin extends TemplateModalWin{

    private TextArea correos;
    private TextField webinarRealizado;
    private Label labelAlert;
    private ResponsiveLayout contenido;
    private CustomLayout alertCorreos;
    private Notification notifG;
    private String globalHtml;
    private final long idWebinar;
    private String idBtnLista;
    private static final String PATTERN_EMAIL
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /*
    public AsistenciaWebinarModalWin() {
        init();
        delete.setVisible(false);
    } */
    public AsistenciaWebinarModalWin(long idWebinar,String idBtn) {
        init();
        //System.out.println(idBtn.substring(0, idBtn.indexOf("_")));
        idBtnLista = idBtn.substring(0, idBtn.indexOf("_"));
        this.idWebinar = idWebinar;
        loadData(idWebinar);
        delete.setVisible(false);
    }

    private void createNotif(String caption, String desc, VaadinIcons icon, Notification.Type type, boolean html) {
        if (notifG != null) {
            notifG.close();
        }
        notifG = new Notification(caption, type);
        notifG.setDescription(desc);
        notifG.setIcon(icon);
        notifG.setHtmlContentAllowed(html);
        notifG.setPosition(Position.TOP_CENTER);
        notifG.setDelayMsec(2500);
        notifG.show(ui.getPage());
    }

    private void setCustomLayout(String fileHtml) {
        if (alertCorreos == null) {
            try {
                alertCorreos = new CustomLayout(new FileInputStream(new File(Main.getBaseDir() + "/mailer/" + fileHtml)));
            } catch (IOException ex) {
                Logger.getLogger(AsistenciaWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (globalHtml.compareTo(fileHtml) != 0) {
                try {
                    contentLayout.removeComponent(alertCorreos);
                    alertCorreos = new CustomLayout(new FileInputStream(new File(Main.getBaseDir() + "/mailer/" + fileHtml)));
                } catch (IOException ex) {
                    Logger.getLogger(AsistenciaWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        globalHtml = fileHtml;
    }

    private void createAlertLabel(String btnName, String labelName, String fileHtml) {
        labelAlert.setValue("La acción se realizó con éxito");
        labelAlert.setCaption("<strong>Constancia(s) enviada(s)</strong> \n");
        setAlertLabel(btnName, labelName, fileHtml);
    }

    private void createAlertLabel(List<String> emailsInvalidos, String btnName, String labelName, String fileHtml) {
        labelAlert.setCaption("<strong>Correos no válidos o no registrados:</strong> \n");
        int cont = emailsInvalidos.size();
        String listaMails = "";
        for (String mail : emailsInvalidos) {
            if (cont > 1) {
                listaMails += mail.trim() + " , ";
            } else {
                listaMails += mail.trim() + " .";
            }
            cont--;
        }
        labelAlert.setValue(listaMails);
        setAlertLabel(btnName, labelName, fileHtml);
    }

    private void setAlertLabel(String btnName, String labelName, String fileHtml) {
        setCustomLayout(fileHtml);
        alertCorreos.setSizeFull();
        labelAlert.setCaptionAsHtml(true);
        labelAlert.setResponsive(true);
        labelAlert.setWidth("100%");
        alertCorreos.setCaption("\n");
        Button btnClose = new Button(VaadinIcons.CLOSE);  // button as a label
        btnClose.setDescription("Cerrar");
        btnClose.addStyleName(ValoTheme.BUTTON_LINK);
        btnClose.addClickListener(event -> {
            btnCerrarAlert();
        });
        alertCorreos.addComponent(labelAlert, labelName);
        alertCorreos.addComponent(btnClose, btnName);
        contentLayout.addComponent(alertCorreos);
    }

    private void init() {
        labelAlert = new Label();
        globalHtml = "";
        contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        correos = new TextArea("Correos");
        correos.setPlaceholder("Escriba los correos separados por coma.");
        Element.cfgComponent(correos);
        correos.setRequiredIndicatorVisible(true);
        correos.focus();
        webinarRealizado = new TextField("Webinar");
        Element.cfgComponent(webinarRealizado);
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
        //System.out.println(correos.getValue());
        String emails = correos.getValue();
        String emailsArray[] = emails.split(",");
        List<String> emailsList;
        emailsList = Arrays.asList(emailsArray);
        List<String> emailsInvalidos;
        if (validarCampos()) {
            try {
                SiiComeiiMailer mailer = new SiiComeiiMailer();
                emailsInvalidos = mailer.enviarConstancias(emails, idWebinar,ui.getUsuario().getId());
                if (!emailsInvalidos.isEmpty()) {
                    if (alertCorreos != null) {
                        alertCorreos.setVisible(true);
                    }
                    if (emailsList.equals(emailsInvalidos)) {
                        createNotif("ERROR | ", "Los correos no son válidos o no estan registrados", VaadinIcons.WARNING,
                                Notification.Type.ERROR_MESSAGE, true);
                        createAlertLabel(emailsInvalidos, "btn_3", "errorMails", "alertCoreosError.html");
                    } else {
                        ui.getFabricaVista().getWebinarRealizadoDlg().updateButtonPdf(idWebinar,idBtnLista.concat("_lista"));
                        createNotif("AVISO | ", "Se encontraron correos no válidos o no registrados", VaadinIcons.WARNING,
                                Notification.Type.WARNING_MESSAGE, true);
                        createAlertLabel(emailsInvalidos, "btn_2", "warningMails", "alertCorreosWarning.html");
                    }
                } else {
                    ui.getFabricaVista().getWebinarRealizadoDlg().updateButtonPdf(idWebinar,idBtnLista.concat("_lista"));
                    createNotif("ÉXITO | ", "Se enviaron las constancias", VaadinIcons.CHECK,
                            Notification.Type.HUMANIZED_MESSAGE, true);
                    createAlertLabel("btn_1", "successMails", "alertCorreosSuccess.html");
                }

            } catch (Exception ex) {
                Logger.getLogger(AsistenciaWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {

        if (correos.isEmpty()) {
            correos.focus();
            createNotif("ERROR | ", "Faltan campos por llenar", VaadinIcons.WARNING, Notification.Type.ERROR_MESSAGE, true);
            return false;
        }
        return true;
    }

    private void btnCerrarAlert() {
        alertCorreos.setVisible(false);
    }

}
