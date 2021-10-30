package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.tiamex.siicomeii.vista.utils.UploadReceiverImg;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.Result;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fred *
 */
public final class ProximoWebinarModalWin extends TemplateModalWin {

    private DateTimeField fecha;
    private TextField institucion;
    private TextField ponente;
    private TextField titulo;
    private TextField usuario;
    private long idProxWeb = 0;
    private ResponsiveRow row2;
    private WebinarRealizado webR;
    Image image;
    Upload uploader;
    UploadReceiverImg receiver;
    byte[] currentImg;
    long fileLength;
    Label lblError;
    private final ThemeResource picture = new ThemeResource("images/picture.png");
    ResponsiveLayout contenido;
    public ProximoWebinarModalWin() {
        init();
    }

    public ProximoWebinarModalWin(long id) {
        this.idProxWeb = id;
        init();
        loadData(id);
    }

    private void init() {
        contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        fecha = new DateTimeField() {
            @Override
            protected Result<LocalDateTime> handleUnparsableDateString(
                    String dateString) {
                try {
                    LocalDateTime parsedAtServer = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
                    return Result.ok(parsedAtServer);
                } catch (DateTimeParseException e) {
                    return Result.error("Fecha y/u hora no válida");
                }
            }
        };
        fecha.setRangeStart(LocalDateTime.now(ZoneId.of("America/Mexico_City")));
        fecha.setDefaultValue(LocalDateTime.now(ZoneId.systemDefault()));
        fecha.setRequiredIndicatorVisible(true);
        fecha.setPlaceholder("Seleccionar o ingresar");
        fecha.setShowISOWeekNumbers(true);
        fecha.setZoneId(ZoneId.of("America/Mexico_City"));
        fecha.setLocale(Locale.forLanguageTag("es-MX"));
        //Element.cfgComponent(fecha, "Fecha y Hora");
        fecha.setWidthFull();
        fecha.setResponsive(true);
        fecha.setCaptionAsHtml(true);
        fecha.setCaption("<b>Fecha y hora</b>");

        institucion = new TextField();
        institucion.setWidth(100, Unit.PERCENTAGE);
        //Element.cfgComponent(institucion, "Institución");
        institucion.setResponsive(true);
        institucion.setCaptionAsHtml(true);
        institucion.setCaption("<b>Institución</b>");
        institucion.setPlaceholder("Ingrese  nombre de la institución");
        institucion.setRequiredIndicatorVisible(true);

        ponente = new TextField();
        ponente.setWidth(100, Unit.PERCENTAGE);
        //Element.cfgComponent(ponente, "Ponente");
        ponente.setResponsive(true);
        ponente.setCaptionAsHtml(true);
        ponente.setCaption("<b>Ponente</b>");
        ponente.setPlaceholder("Ingrese nombre del ponente");
        ponente.setRequiredIndicatorVisible(true);

        titulo = new TextField();
        //Element.cfgComponent(titulo, "Título");
        titulo.setResponsive(true);
        titulo.setCaptionAsHtml(true);
        titulo.setCaption("<b>Título</b>");
        titulo.setPlaceholder("Ingrese título");
        titulo.setRequiredIndicatorVisible(true);
        titulo.setWidth("100%");

        usuario = new TextField();
        //Element.cfgComponent(usuario, "Usuario");
        usuario.setValue(ui.getUsuario().getNombre());
        //usuario.setEnabled(false);
        usuario.setReadOnly(true);

        image = new Image("Imagen seleccionada: ");
        image.setIcon(VaadinIcons.PICTURE);
        image.setResponsive(true);
        image.setEnabled(true);
        image.setWidth(50, Unit.PERCENTAGE);
        image.setHeight(50, Unit.PERCENTAGE);

        uploader = new Upload();
        lblError = new Label();
        lblError.setResponsive(true);
        lblError.addStyleNames(ValoTheme.LABEL_TINY, ValoTheme.LABEL_NO_MARGIN);
        lblError.setContentMode(ContentMode.HTML);
        receiver = new UploadReceiverImg(image, Arrays.asList("image/jpeg", "image/png"), uploader, "", lblError);
        uploader.setResponsive(true);
        uploader.setIcon(VaadinIcons.UPLOAD);
        uploader.setButtonStyleName("v-button v-button-friendly");
        uploader.setReceiver(receiver);
        uploader.setCaptionAsHtml(true);
        uploader.setCaption("<b>Imagen</b> (Opcional)");
        uploader.setAcceptMimeTypes("image/*");
        uploader.setImmediateMode(true);
        uploader.setButtonCaption("Cargar imagen");
        uploader.addFailedListener(receiver);
        uploader.addFinishedListener(receiver);
        uploader.addSucceededListener(receiver);
        uploader.addProgressListener(receiver);
        image.setSource(picture);
        
        //eventCheckBox();
        row2 = contenido.addRow();
        row2.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row2.addColumn().withDisplayRules(12, 12, 8, 8).withComponent(titulo);
        row2.addColumn().withDisplayRules(12, 12, 4, 4).withComponent(fecha);
        row2.addColumn().withComponent(institucion).withDisplayRules(12, 12, 6, 6);
        row2.addColumn().withComponent(ponente).withDisplayRules(12, 12, 6, 6);
        row2.addColumn().withDisplayRules(12, 12, 6, 6).withComponent(uploader);
        row2.addColumn().withDisplayRules(12, 12, 6, 6).withComponent(image);
        contenido.addRow().withComponents(lblError).withDefaultRules(12, 12, 12, 12);

        contentLayout.addComponent(contenido);
        setCaptionAsHtml(true);
        setWidth("40%");
    }

    public void eventCheckBox() {
        if (idProxWeb != 0) {  //editando un registro de prox webinar
            try {
                String dTitulo = ControladorProximoWebinar.getInstance().getById(idProxWeb).getTitulo();
                webR = (WebinarRealizado) ControladorWebinarRealizado.getInstance().getByNames(dTitulo);

                if (webR != null) { // webinar archivado

                    editBox.setResponsive(true);
                    editBox.setCaption("Editar y actualizar");
                    editBox.setValue(false);
                    editBox.setResponsive(true);
                    editBox.setDescription("Marca esta casilla para poder editar los datos");
                    //editBox.setIcon(VaadinIcons.EDIT); 
                    editBox.addValueChangeListener((HasValue.ValueChangeEvent<Boolean> event) -> {
                        checkBoxValueChanged(event.getValue());
                    });
                    
                    checkBoxValueChanged(false);
                    setCaption("Proximo webinar (Archivado)");
                } else {
                    setCaption("Proximo webinar");
                }

            } catch (Exception ex) {
                Logger.getLogger(ProximoWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            setCaption("<b>Proximo Webinar</b>");
        }
    }

    public void checkBoxValueChanged(boolean value) {
        if (value) {
            fecha.setReadOnly(false);
            institucion.setReadOnly(false);
            ponente.setReadOnly(false);
            titulo.setReadOnly(false);
            uploader.setEnabled(true);
            accept.setVisible(true);
        } else {
            fecha.setReadOnly(true);
            institucion.setReadOnly(true);
            ponente.setReadOnly(true);
            titulo.setReadOnly(true);
            accept.setVisible(false);
            uploader.setEnabled(false);
        }
    }

    @Override
    protected void loadData(long id) {
        try {
            ProximoWebinar obj = ControladorProximoWebinar.getInstance().getById(id);
            String title = obj.getTitulo();
            this.id = obj.getId();
            uploader.setButtonCaption("Cargar otra imagen");
            if (obj.getImagen() != null) {
                image.setSource(new StreamResource(() -> new ByteArrayInputStream(obj.getImagen()), "image_" + title));
            }
            institucion.setValue(obj.getInstitucion());
            ponente.setValue(obj.getPonente());
            titulo.setValue(title);
            fecha.setValue(obj.getFecha());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                ProximoWebinar proxWebinar = (ProximoWebinar) ControladorProximoWebinar.getInstance().getByTitulos(titulo.getValue());
                ProximoWebinar obj = new ProximoWebinar();
                obj.setId(id);
                obj.setFecha(fecha.getValue());
                obj.setInstitucion(institucion.getValue());
                obj.setPonente(ponente.getValue());
                obj.setTitulo(titulo.getValue());
                obj.setUsuario(ui.getUsuario().getId());
                //boolean banSave = true;
                if (regexName()) {
                    Element.buildNotification("Error", "No se permiten números y caracteres especiales en el nombre", "bar error closable").
                            show(Page.getCurrent());
                } else {
                    if (receiver.newFileReceived()) {
                        obj.setImagen(receiver.getContentByte());
                    }
                    if (id == 0) { // nuevo registro (botón agregar)
                        //banSave = false;
                        if (proxWebinar != null) { // nuevo registro con entrada duplicada
                            Element.buildNotification("Aviso", "Ya existe un registro con el mismo título", "bar warning closable").
                                            show(Page.getCurrent());
                            
                        } else { // nuevo registro
                            obj = ControladorProximoWebinar.getInstance().save(obj);
                            if (obj != null) {
                                Element.buildSucessNotification().show(Page.getCurrent());
                                ui.getFabricaVista().getProximoWebinarDlg().eventMostrar();
                                close();
                            }
                        }
                    } else { // editando un registro

                        if (proxWebinar != null) { // 
                            if (obj.getImagen() == null) {
                                obj.setImagen(proxWebinar.getImagen());
                            }
                            if (compareWebinars(proxWebinar)) { // el mismo registro
                                //banSave = false;
                                close();
                            } else if (proxWebinar.getId() != id) {
                                //banSave = false;
                                Element.buildNotification("Aviso", "Ya existe un registro con el mismo título", "bar warning closable").
                                            show(Page.getCurrent());
                            } else {
                                obj = ControladorProximoWebinar.getInstance().save(obj);
                                if (obj != null) {
                                    Element.buildSucessNotification().show(Page.getCurrent());
                                    ui.getFabricaVista().getProximoWebinarDlg().eventMostrar();
                                    //eventCheckBox();
                                    close();
                                }
                            }

                        } else {
                            obj = ControladorProximoWebinar.getInstance().save(obj);
                            if (obj != null) {
                                Element.buildSucessNotification().show(Page.getCurrent());
                                ui.getFabricaVista().getProximoWebinarDlg().eventMostrar();
                                //eventCheckBox();
                                close();
                            }
                        }

                        if (editBox.getValue()) {
                            WebinarRealizado objWebR = new WebinarRealizado();
                            objWebR.setId(webR.getId());
                            objWebR.setFecha(fecha.getValue());
                            objWebR.setInstitucion(institucion.getValue());
                            objWebR.setNombre(titulo.getValue());
                            objWebR.setPonente(ponente.getValue());
                            objWebR.setPresentacion(webR.getPresentacion());
                            objWebR.setUrlYoutube(webR.getUrlYoutube());

                            try {
                                ControladorWebinarRealizado.getInstance().save(objWebR);
                            } catch (Exception e) {
                                Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), e.getMessage());
                            }
                        }
                        ui.getFabricaVista().getWebinarRealizadoDlg().updateDlg();
                    }
                }
            } else {
                Element.buildNotification("Aviso", "Faltan campos por completar", "bar warning closable").
                                            show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected boolean compareWebinars(ProximoWebinar webinar) { //webinar.getImagen().compareTo(imagen.getValue()) == 0
        return (webinar.getFecha().compareTo(fecha.getValue()) == 0 && webinar.getId() == id
                && webinar.getInstitucion().compareTo(institucion.getValue()) == 0
                && webinar.getPonente().compareTo(ponente.getValue()) == 0 && !receiver.newFileReceived());
    }

    protected boolean regexName() {
        String regex = "[^A-z|ñ|\\p{L}|.| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherPonente = pattern.matcher(ponente.getValue());

        return matcherPonente.find();
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {
        Binder<ProximoWebinar> binder = new Binder<>();

        binder.forField(fecha).asRequired("Campo requerido").bind(ProximoWebinar::getFecha, ProximoWebinar::setFecha);
        //binder.forField(imagen).asRequired("Campo requerido").bind(ProximoWebinar::getImagen, ProximoWebinar::setImagen);
        binder.forField(institucion).asRequired("Campo requerido").bind(ProximoWebinar::getInstitucion, ProximoWebinar::setInstitucion);
        binder.forField(ponente).asRequired("Campo requerido").bind(ProximoWebinar::getPonente, ProximoWebinar::setPonente);
        binder.forField(titulo).asRequired("Campo requerido").bind(ProximoWebinar::getTitulo, ProximoWebinar::setTitulo);
        //Marca error binder.forField(usuario).asRequired("Campo requerido").bind(ProximoWebinar::getUsuario,ProximoWebinar::setUsuario);

        //upload
        return binder.validate().isOk();
    }

}
