package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.tiamex.siicomeii.vista.utils.UploadReceiverImg;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.Result;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.ByteArrayInputStream;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fred *
 */
public class ProximoEventoModalWin extends TemplateModalWin implements Upload.Receiver {

    private TextArea descripcion;
    private DateTimeField fecha;
    //private TextField imagen;
    private Upload uploader;
    private Image img;
    private TextField titulo;
    private TextField usuario;
    UploadReceiverImg receiver;
    Label lblError;
    private final ThemeResource picture = new ThemeResource("images/picture.png");
    public ProximoEventoModalWin() {
        init();
    }

    public ProximoEventoModalWin(long id) {
        init();
        loadData(id);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        descripcion = new TextArea();
        Element.cfgComponent(descripcion, "Descipción (máx. 300 caracteres)");
        descripcion.setPlaceholder("Ingrese una descripción del próximo evento");
        descripcion.setRequiredIndicatorVisible(true);
        descripcion.setMaxLength(300);
        descripcion.addValueChangeListener((ValueChangeListener) event->{
            int leftChar = descripcion.getMaxLength()-descripcion.getValue().length();
            descripcion.setCaption("<b>Descipción ("+leftChar+" caracteres restantes)</b>");
        });

        fecha = new DateTimeField("") {
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
        fecha.setPlaceholder("Seleccione o ingresar");
        fecha.setShowISOWeekNumbers(true);
        fecha.setZoneId(ZoneId.of("America/Mexico_City"));
        fecha.setResolution(DateTimeResolution.MINUTE);
        fecha.setLocale(new Locale("es","MX"));
        Element.cfgComponent(fecha, "Fecha y Hora");
        
        lblError = new Label();
        lblError.setResponsive(true);
        lblError.addStyleNames(ValoTheme.LABEL_TINY, ValoTheme.LABEL_NO_MARGIN);
        lblError.setContentMode(ContentMode.HTML);
        img = new Image();
        img.setResponsive(true);
        img.setCaption("Imagen seleccionada: ");img.setCaptionAsHtml(true);
        img.setIcon(VaadinIcons.PICTURE); img.setSource(picture);
        //img.setSource(source);
        img.setWidth(50, Unit.PERCENTAGE);
        uploader = new Upload();
        receiver = new UploadReceiverImg(img, Arrays.asList("image/jpeg","image/png"), uploader, "",lblError);
        uploader.setResponsive(true); uploader.setCaptionAsHtml(true);
        uploader.setIcon(VaadinIcons.UPLOAD);
        uploader.setAcceptMimeTypes("image/*");
        uploader.setImmediateMode(true);
        uploader.setButtonCaption("Cargar imagen");
        uploader.setButtonStyleName("v-button v-button-friendly");
        uploader.setCaption("<b>Imagen </b><span style=color:red>*</span>");
        uploader.setReceiver(receiver); uploader.addSucceededListener(receiver); uploader.addProgressListener(receiver);

        titulo = new TextField();
        Element.cfgComponent(titulo, "Título");
        titulo.setPlaceholder("Ingrese título");
        titulo.setRequiredIndicatorVisible(true);

        usuario = new TextField();
        Element.cfgComponent(usuario, "Usuario");
        usuario.setValue(ui.getUsuario().getNombre());
        //usuario.setEnabled(false);
        usuario.setReadOnly(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row1.addColumn().withDisplayRules(12, 12, 8, 8).withComponent(titulo);
        row1.addColumn().withDisplayRules(12, 12, 4, 4).withComponent(fecha);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(descripcion);
        row1.addColumn().withDisplayRules(12, 12, 6, 6).withComponent(uploader);
        row1.addColumn().withDisplayRules(12, 12, 6, 6).withComponent(img);
        contenido.addRow().withComponents(lblError).withDefaultRules(12, 12, 12, 12);

        contentLayout.addComponent(contenido);

        setCaption("Próximo evento");
        setWidth("35%");
    }

    @Override
    protected final void loadData(long id) {
        try {
            ProximoEvento obj = ControladorProximoEvento.getInstance().getById(id);
            this.id = obj.getId();
            descripcion.setValue(obj.getDescripcion());
            uploader.setButtonCaption("Cargar otra imagen");
            img.setSource(new StreamResource(()-> new ByteArrayInputStream(obj.getImagen()) ,"image_"+obj.getTitulo()));
            titulo.setValue(obj.getTitulo());
            fecha.setValue(obj.getFecha());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                ProximoEvento oldEv = ControladorProximoEvento.getInstance().getById(id);
                ProximoEvento obj = new ProximoEvento();
                obj.setId(id);
                obj.setDescripcion(descripcion.getValue());
                obj.setFecha(fecha.getValue());
                obj.setTitulo(titulo.getValue());
                obj.setUsuario(ui.getUsuario().getId());
                boolean newFile = receiver.newFileReceived();
                if (regexName()) {
                    Element.buildNotification("Error", "No se permiten números y caracteres especiales en el título", "bar error closable").
                            show(Page.getCurrent());
                } else {
                    ProximoEvento evento = (ProximoEvento) ControladorProximoEvento.getInstance().getByTitulos(titulo.getValue());
                    if (id == 0) { // nuevo registro (botón agregar)
                        if(newFile){
                            obj.setImagen(receiver.getContentByte());
                            if (evento != null) { // nuevo registro con entrada de correo duplicada
                                Element.buildNotification("Aviso", "Ya existe un registro con el mismo título", "bar warning closable").
                                            show(Page.getCurrent());
                            } else { // nuevo registro con nuevo correo
                                obj = ControladorProximoEvento.getInstance().save(obj);
                                if (obj != null) {
                                    Element.buildSucessNotification().show(Page.getCurrent());
                                    ui.getFabricaVista().getProximoEventoDlg().eventMostrar();
                                    close();
                                } 
                            }
                        }else{
                            Element.buildNotification("Aviso", "Faltan campos por completar", "bar warning closable").
                                            show(Page.getCurrent());
                            lblError.setValue("<span style=\"color:red\">Seleccione una imagen</span>");
                        }  
                    } else { // editando un registro
                        if(newFile)
                            obj.setImagen(receiver.getContentByte());
                        else
                            obj.setImagen(oldEv.getImagen());
                        if (evento != null) { // mismo reg
                            if (evento.getId() != id) {
                                Element.buildNotification("Aviso", "Ya existe un registro con el mismo título", "bar warning closable").
                                            show(Page.getCurrent());
                            } else {
                                obj = ControladorProximoEvento.getInstance().save(obj);
                                if (obj != null) {
                                    Element.buildSucessNotification().show(Page.getCurrent());
                                    ui.getFabricaVista().getProximoEventoDlg().eventMostrar();
                                    close();
                                }
                            }

                        } else {
                            obj = ControladorProximoEvento.getInstance().save(obj);
                            if (obj != null) {
                                Element.buildSucessNotification().show(Page.getCurrent());
                                ui.getFabricaVista().getProximoEventoDlg().eventMostrar();
                                close();
                            }
                        }
                    }
                    ui.getFabricaVista().getProximoEventoDlg().fechaFinF.setEnabled(true);
                    ui.getFabricaVista().getProximoEventoDlg().fechaInicioF.setEnabled(true);
                }
            } else {
                Element.buildNotification("Aviso", "Faltan campos por completar", "bar warning closable").
                                            show(Page.getCurrent());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    //evento.getImagen().compareTo(imagen.getValue()) == 0
    protected boolean compareEvento(ProximoEvento evento) {
        return (evento.getDescripcion().compareTo(descripcion.getValue()) == 0 && evento.getId() == id
                && evento.getFecha().compareTo(fecha.getValue()) == 0 );
    }
    
    protected boolean regexName() {
        String regex = "[^A-z|ñ|\\p{L}| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherPonente = pattern.matcher(titulo.getValue());

        return matcherPonente.find();
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {
        Binder<ProximoEvento> binder = new Binder<>();
        binder.forField(descripcion).asRequired("Campo requerido").bind(ProximoEvento::getDescripcion, ProximoEvento::setDescripcion);
        binder.forField(fecha).asRequired("Campo requerido").bind(ProximoEvento::getFecha, ProximoEvento::setFecha);
        //binder.forField(imagen).asRequired("Campo requerido").bind(ProximoEvento::getImagen, ProximoEvento::setImagen);
        binder.forField(titulo).asRequired("Campo requerido").bind(ProximoEvento::getTitulo, ProximoEvento::setTitulo);
        return binder.validate().isOk();
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
