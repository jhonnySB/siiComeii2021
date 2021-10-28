package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.mailer.SiiComeiiMailer;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.tiamex.siicomeii.vista.utils.UploadReceiver;
import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ErrorLevel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

/**
 * @author fred *
 */
public final class AsistenciaWebinarModalWin extends TemplateModalWin {

    static String mailMatcher = null;
    static int beginIndexStr = 0;
    private TextArea correos;
    private TextField webinarRealizado;
    private Label labelAlert;
    private ResponsiveLayout contenido;
    private CustomLayout alertCorreos;
    private Notification notifG;
    private String globalHtml;
    private final long idWebinar;
    private final String idBtnLista;
    private static final String PATTERN_EMAIL
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String NEW_PATTERN_MAIL
            = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
    private ComboBox<Agremiado> cmboxAgremiados;
    private List<String> listAgremiados;
    private UserError error;
    public final String PATH_ICON_CHECK = Main.getBaseDir() + "/icons/check_3_mod.png";
    public final String PATH_ICON_BLANK = Main.getBaseDir() + "/icons/BLANK_ICON_MOD2.png";
    public final String MIMES_ALLOWED = "text/plain,text/csv,application/csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,"
            + "application/vnd.ms-excel,application/vnd.ms-excel.sheet.macroEnabled.12";

    public AsistenciaWebinarModalWin(long idWebinar, String idBtn) {
        init();
        idBtnLista = idBtn.substring(0, idBtn.indexOf("_"));
        this.idWebinar = idWebinar;
        loadData(idWebinar);
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
        error = new UserError("Revisa que los correos ingresados manualmente sean válidos");
        error.setErrorLevel(ErrorLevel.ERROR);
        listAgremiados = new ArrayList<>();
        //
        Collection<Agremiado> agremiadosData;
        try { //&nbsp;&nbsp;&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            agremiadosData = ControladorAgremiado.getInstance().getAllSorted("nombre");
            cmboxAgremiados = new ComboBox<>("", agremiadosData);
            cmboxAgremiados.setCaptionAsHtml(true);
            cmboxAgremiados.setCaption(
                    "<b>Agregar correos: </b>"
                    + "<span style=\"display:inline-block;color:rgba(255,0,0,0.5);padding:0 0 0 42%;\">"
                    + "*Correos marcados con &#10004; ya se les envio constancia* </span>");
            cmboxAgremiados.setResponsive(true);
        } catch (Exception ex) {
            Logger.getLogger(AsistenciaWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmboxAgremiados.setItemCaptionGenerator(agremiadoBean -> (agremiadoBean.getCorreo()
                + " (" + agremiadoBean.getNombre().toUpperCase(new Locale("es", "MX"))) + ")");
        cmboxAgremiados.setItemIconGenerator(agremiado -> {
            Resource r = null;
            BufferedImage bImage;
            try {
                boolean asistencia = !ControladorAsistenciaWebinar.getInstance().getByAgremiadoWebinar(agremiado.getId(), this.idWebinar).isEmpty();
                if (asistencia) {
                    r = new ThemeResource(PATH_ICON_CHECK);
                    bImage = ImageIO.read(new File(PATH_ICON_CHECK));
                } else {
                    r = new ThemeResource(PATH_ICON_BLANK);
                    bImage = ImageIO.read(new File(PATH_ICON_BLANK));
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos);
                byte[] byteArray = bos.toByteArray();
                StreamResource.StreamSource streamR = () -> new ByteArrayInputStream(byteArray);
                r = new StreamResource(streamR, asistencia ? "check_3_mod.png" : "BLANK_ICON_MOD2.png");
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(AsistenciaWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
            }
            return r;
        });

        cmboxAgremiados.setPlaceholder("Seleccionar correo de agremiado");
        cmboxAgremiados.setEmptySelectionAllowed(false);
        cmboxAgremiados.setSizeFull();
        cmboxAgremiados.setTextInputAllowed(true);
        cmboxAgremiados.setPageLength(5);

        cmboxAgremiados.addValueChangeListener((ValueChangeListener) (HasValue.ValueChangeEvent event) -> {
            Agremiado agremiado = (Agremiado) event.getValue();
            String mailSelected = agremiado.getCorreo();
            Pattern pattern = Pattern.compile(mailSelected);
            if (correos.getValue().isEmpty()) {
                correos.setValue(mailSelected);
            } else {
                if (listAgremiados.contains(mailSelected.trim())) {
                    Matcher matchMail = pattern.matcher(correos.getValue());
                    mailMatcher = mailSelected;
                    if (matchMail.find()) {
                        int length = matchMail.end() - matchMail.start();
                        correos.setSelection(matchMail.start(), length);
                        beginIndexStr = matchMail.start();
                    }
                    createNotif("AVISO | ", "El correo seleccionado ya esta en la lista de correos", VaadinIcons.WARNING,
                            Notification.Type.WARNING_MESSAGE, true);
                } else {
                    //listAgremiados.add(mailSelected);
                    correos.setValue(correos.getValue() + "," + mailSelected);
                    mailMatcher = null;
                }
            }
            /*try {
                cmboxAgremiados.setValue(null);
            } catch (Exception e) {
                /// 
            } */
        });

        labelAlert = new Label();
        globalHtml = "";
        contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        correos = new TextArea("");
        correos.setReadOnly(true);
        correos.setCaptionAsHtml(true);
        correos.setCaption("<b>Correos: </b><span style=\"color:red\">*</span>");
        correos.setPlaceholder("Aquí aparecerán los correos seleccionados");
        Element.cfgComponent(correos);
        correos.setRequiredIndicatorVisible(true);

        correos.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            String[] currentList = correos.getValue().split(",");
            listAgremiados.clear();
            listAgremiados.addAll(Arrays.asList(currentList));
        });

        webinarRealizado = new TextField("<b>Webinar</b>");
        webinarRealizado.setCaptionAsHtml(true);
        Element.cfgComponent(webinarRealizado);
        //webinarRealizado.setReadOnly(true);
        webinarRealizado.setEnabled(false);

        correos.setWidth("97%");
        Button btnClearEntireArea = new Button();
        btnClearEntireArea.addStyleNames(ValoTheme.BUTTON_TINY);
        btnClearEntireArea.addStyleName(ValoTheme.BUTTON_DANGER);
        btnClearEntireArea.setIcon(VaadinIcons.TRASH);
        btnClearEntireArea.setDescription("Borrar todo el texto");
        btnClearEntireArea.setWidth("50%");
        btnClearEntireArea.setCaption("");
        btnClearEntireArea.addClickListener((ClickListener) e -> {
            correos.setValue("");
        });

        MenuBar btnMenuClear = new MenuBar();
        btnMenuClear.addStyleName(ValoTheme.MENUBAR_SMALL);
        MenuItem btnClearTextArea = btnMenuClear.addItem("", VaadinIcons.TRASH, null);
        btnClearTextArea.addItem("Todo el texto", null, c -> {
            correos.setValue("");
        });
        btnClearTextArea.addItem("Correo seleccionado", null, c -> {
            if (mailMatcher != null) {
                String value = correos.getValue();
                if (value.length() > 0) {
                    String[] splitedMails = correos.getValue().split(",");
                    String matcherMail = mailMatcher;
                    if (splitedMails.length > 1) {
                        correos.clear();
                        if (beginIndexStr > 0) {
                            correos.setValue(value.replace("," + matcherMail, ""));
                        } else {
                            correos.setValue(value.replace(matcherMail + ",", ""));
                        }
                    } else {
                        correos.setValue("");
                    }
                }
            } else {
                Notification.show("No hay correo seleccionado", Notification.Type.WARNING_MESSAGE);
            }
        });

        Button btnSelectAll = new Button();
        btnSelectAll.addStyleName(ValoTheme.BUTTON_TINY);
        btnSelectAll.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnSelectAll.setIcon(VaadinIcons.AREA_SELECT);
        btnSelectAll.setDescription("Seleccionar todo");
        btnSelectAll.setWidth("100%");
        btnSelectAll.setCaption("");
        btnSelectAll.addClickListener((ClickListener) e -> {
            correos.setSelection(0, correos.getValue().length());
        });

        UploadReceiver receiver = new UploadReceiver(correos);
        Upload fileUploader = new Upload("", receiver);
        fileUploader.setWidthFull();
        fileUploader.setIcon(VaadinIcons.UPLOAD);
        fileUploader.setCaption("Subir archivo (.txt)");
        fileUploader.setButtonCaption("Subir");
        fileUploader.setButtonCaptionAsHtml(true);
        fileUploader.setImmediateMode(false);
        fileUploader.setResponsive(true);
        fileUploader.addFailedListener(receiver);
        fileUploader.addSucceededListener(receiver);
        fileUploader.addFinishedListener(receiver);
        fileUploader.addStartedListener(receiver);
        fileUploader.setResponsive(true);
        fileUploader.setAcceptMimeTypes("text/plain");

        ResponsiveColumn rC = new ResponsiveColumn();
        rC.setResponsive(true);
        rC.setSizeUndefined();
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();
        lay.setResponsive(true);
        lay.setSpacing(false);
        lay.setMargin(false);
        lay.addComponent(new Label(""));
        lay.setSpacing(true);
        lay.addComponent(btnSelectAll);
        lay.addComponent(btnMenuClear);
        //lay.addComponent(btnClearSelectedMail);
        //lay.addComponent(fileUploader);

        rC.withDisplayRules(1, 1, 1, 1);
        rC.setContent(lay);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(cmboxAgremiados);
        ResponsiveRow row2 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row2.addColumn().withDisplayRules(11, 11, 11, 11).withComponent(correos);
        row2.addColumn(rC);
        //row2.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(fileUploader);
        row2.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(webinarRealizado);

        contentLayout.addComponent(contenido);
        setCaption("Asistencia webinar");
        //setWidth("50%");
    }

    public class ObjectHolder<T> {

        private T obj;

        public ObjectHolder(T obj) {
            this.obj = obj;
        }

        public T get() {
            return obj;
        }

        public void set(T obj) {
            this.obj = obj;
        }
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
    protected void buttonAcceptEvent() {
        String emails = correos.getValue();
        String emailsArray[] = emails.split(",");
        List<String> emailsList;
        emailsList = Arrays.asList(emailsArray);
        List<String> emailsInvalidos;
        List<String> listMail;
        if (validarCampos()) {
            try {
                SiiComeiiMailer mailer = new SiiComeiiMailer();
                emailsInvalidos = mailer.enviarConstancias(emails, idWebinar, ui.getUsuario().getId());
                if (!emailsInvalidos.isEmpty()) {
                    /*if (alertCorreos != null) {
                        alertCorreos.setVisible(true);
                    }
                    if (emailsList.equals(emailsInvalidos)) {
                        createNotif("ERROR | ", "Los correos no son válidos o no estan registrados", VaadinIcons.WARNING,
                                Notification.Type.WARNING_MESSAGE, true);
                        createAlertLabel(emailsInvalidos, "btn_3", "errorMails", "alertCoreosError.html");
                    } else {
                        ui.getFabricaVista().getWebinarRealizadoDlg().updateButtonPdf(idWebinar, idBtnLista.concat("_lista"));
                        createNotif("AVISO | ", "Se encontraron correos no válidos o no registrados", VaadinIcons.WARNING,
                                Notification.Type.WARNING_MESSAGE, true);
                        createAlertLabel(emailsInvalidos, "btn_2", "warningMails", "alertCorreosWarning.html");
                    } */
                } else {
                    ui.getFabricaVista().getWebinarRealizadoDlg().updateButtonPdf(idWebinar, idBtnLista.concat("_lista"));
                    createNotif("ÉXITO | ", "Se enviaron las constancias", VaadinIcons.CHECK,
                            Notification.Type.HUMANIZED_MESSAGE, true);
                    //createAlertLabel("btn_1", "successMails", "alertCorreosSuccess.html");
                }
                listMail = emailsList.stream()
                        .filter(e -> !emailsInvalidos.contains(e))
                        .collect(Collectors.toList());
                System.out.println(listMail.size());
                for (String email : listMail) {
                    Agremiado a = ControladorAgremiado.getInstance().getByEmail(email);
                    ControladorAgremiado.getInstance().updateField("urlIcon", true, a.getId());
                }
                this.close();
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
            createNotif("ERROR | ", "Faltan campos por llenar", VaadinIcons.WARNING, Notification.Type.WARNING_MESSAGE, true);
            UserError emptyError = new UserError("Este campo es requerido");
            emptyError.setErrorLevel(ErrorLevel.ERROR);
            correos.setComponentError(emptyError);
            return false;
        }
        return true;
    }

    private void btnCerrarAlert() {
        alertCorreos.setVisible(false);
    }

}
