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
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fred *
 */
public final class ProximoWebinarModalWin extends TemplateModalWin implements Upload.Receiver{

    private DateTimeField fecha;
    private TextField imagen;
    private TextField institucion;
    private TextField ponente;
    private TextField titulo;
    private TextField usuario;
    private long idProxWeb=0;
    private ResponsiveRow row1;
    private WebinarRealizado webR;

    public ProximoWebinarModalWin() {
        init();
    }

    public ProximoWebinarModalWin(long id) {
        this.idProxWeb = id;
        init();
        loadData(id);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido); 
        //fecha = new DateTimeField();
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
        
        fecha.setDefaultValue(LocalDateTime.now(ZoneId.systemDefault()));
        fecha.setRequiredIndicatorVisible(true);
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        //fecha.setPlaceholder("Seleccione o ingrese la fecha (ej. "+now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+")");
        fecha.setPlaceholder("Seleccionar");
        fecha.setShowISOWeekNumbers(true);
        fecha.setZoneId(ZoneId.of("America/Mexico_City")); fecha.setLocale(new Locale("es","MX"));
        Element.cfgComponent(fecha, "Fecha y Hora");
        
        
        imagen = new TextField();
        Element.cfgComponent(imagen, "Imagen");
        imagen.setPlaceholder("Ingrese URL de la imagen");
        imagen.setRequiredIndicatorVisible(true);

        institucion = new TextField();
        Element.cfgComponent(institucion, "Institución");
        institucion.setPlaceholder("Ingrese  nombre de la institución");
        institucion.setRequiredIndicatorVisible(true);
        
        ponente = new TextField();
        Element.cfgComponent(ponente, "Ponente");
        ponente.setPlaceholder("Ingrese nombre del ponente");
        ponente.setRequiredIndicatorVisible(true);
        
        titulo = new TextField();
        Element.cfgComponent(titulo, "Título");
        titulo.setPlaceholder("Ingrese título");
        titulo.setRequiredIndicatorVisible(true);
        
        usuario = new TextField();
        Element.cfgComponent(usuario, "Usuario");
        usuario.setValue(ui.getUsuario().getNombre());
        //usuario.setEnabled(false);
        usuario.setReadOnly(true);
        
        Image image = new Image("Imagen cargada: "); image.setResponsive(true);
        image.setSizeFull(); 
        UploadReceiverImg receiver = new UploadReceiverImg(image); 
        Upload uploader = new Upload("",receiver); uploader.setResponsive(true);
        uploader.setCaptionAsHtml(true); uploader.setCaption("Imagen <span style=\"color:red\">*</span>");
        uploader.setAcceptMimeTypes("image/*"); 
        uploader.setImmediateMode(false);
        uploader.setButtonCaption("Cargar imagen");
        uploader.addFailedListener(receiver); uploader.addFinishedListener(receiver); uploader.addSucceededListener(receiver);
        
        
        row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        eventCheckBox();
        titulo.setWidth("97%");
        row1.addColumn().withDisplayRules(8, 8, 8, 8).withComponent(titulo);
        row1.addColumn().withDisplayRules(4, 4, 4, 4).withComponent(fecha);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(ponente);
        //row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuario);
        //row1.addColumn().withDisplayRules(6, 6, 6, 6).withComponent(imagen);
        row1.addColumn().withDisplayRules(6, 6, 6, 6).withComponent(uploader);
        row1.addColumn().withDisplayRules(6, 6, 6, 6).withComponent(image);

        contentLayout.addComponent(contenido);
        setCaptionAsHtml(true);
        setWidth("50%");
    }
    
    public void eventCheckBox(){
        if (idProxWeb != 0) {  //editando un registro de prox webinar
            try {
                String dTitulo = ControladorProximoWebinar.getInstance().getById(idProxWeb).getTitulo();
                webR = (WebinarRealizado) ControladorWebinarRealizado.getInstance().getByNames(dTitulo);

                if (webR != null) { // webinar archivado
                    accept.setVisible(false);
                    editBox.setResponsive(true);
                    editBox.setCaption("Editar y actualizar");
                    editBox.setValue(false);
                    editBox.setResponsive(true);
                    editBox.setDescription("Marca esta casilla para poder editar los datos");
                    //editBox.setIcon(VaadinIcons.EDIT); 
                    editBox.addValueChangeListener((HasValue.ValueChangeEvent<Boolean> event) -> {
                        checkBoxValueChanged();
                    });
                    row1.addColumn().withComponent(editBox);

                    fecha.setReadOnly(true);
                    imagen.setReadOnly(true);
                    institucion.setReadOnly(true);
                    ponente.setReadOnly(true);
                    titulo.setReadOnly(true);
                    
                    setCaption("Proximo webinar (Archivado)");
                }else{
                    setCaption("Proximo webinar");
                }

            } catch (Exception ex) {
                Logger.getLogger(ProximoWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            setCaption("<b>Proximo Webinar</b>");
        }
    }
    
    public void checkBoxValueChanged(){
        if(editBox.getValue()==true){
            fecha.setReadOnly(false);
            imagen.setReadOnly(false);
            institucion.setReadOnly(false);
            ponente.setReadOnly(false);
            titulo.setReadOnly(false);
            accept.setVisible(true);
        }else{
            fecha.setReadOnly(true);
            imagen.setReadOnly(true);
            institucion.setReadOnly(true);
            ponente.setReadOnly(true);
            titulo.setReadOnly(true);
            accept.setVisible(false);
        }
    }

    @Override
    protected void loadData(long id) {
        try {
            ProximoWebinar obj = ControladorProximoWebinar.getInstance().getById(id);
            this.id = obj.getId();
            imagen.setValue(obj.getImagen());
            institucion.setValue(obj.getInstitucion());
            ponente.setValue(obj.getPonente());
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
                ProximoWebinar obj = new ProximoWebinar();
                obj.setId(id);
                obj.setFecha(fecha.getValue());
                obj.setImagen(imagen.getValue());
                obj.setInstitucion(institucion.getValue());
                obj.setPonente(ponente.getValue());
                obj.setTitulo(titulo.getValue());
                obj.setUsuario(ui.getUsuario().getId());
                boolean banSave=true;
                if(regexName()){
                    Element.makeNotification("Solo se permiten letras para el nombre del ponente", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                }else{
                    ProximoWebinar proxWebinar = (ProximoWebinar)ControladorProximoWebinar.getInstance().getByTitulos(titulo.getValue());
                    if(id==0){ // nuevo registro (botón agregar)
                                banSave=false;
                                if (proxWebinar != null) { // nuevo registro con entrada de correo duplicada
                                    Element.makeNotification("Ya existe un registro con el mismo título: '"+proxWebinar.getTitulo()+"'", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                }else{ // nuevo registro con nuevo correo
                                        obj = ControladorProximoWebinar.getInstance().save(obj);
                                        if (obj != null) {
                                            
                                            Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getProximoWebinarDlg().eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    
                                }
                            }else{ // editando un registro
                                
                                if(proxWebinar!=null){ // 
                                    
                                    if(compareWebinars(proxWebinar)){ // el mismo registro
                                        banSave=false;
                                        close();
                                    }else if(proxWebinar.getId()!=id){
                                        banSave=false;
                                        Element.makeNotification("Ya existe un webinar con el mismo título", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }else{
                                        obj = ControladorProximoWebinar.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getProximoWebinarDlg().eventMostrar();
                                            //eventCheckBox();
                                            close();
                                        }else{
                                            banSave=false;
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }
                                    
                                }else{
                                        obj = ControladorProximoWebinar.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getProximoWebinarDlg().eventMostrar();
                                            //eventCheckBox();
                                            close();
                                        }else{
                                            banSave=false;
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    
                                }
                                
                            if (banSave == true) {
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
                Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    protected boolean compareWebinars(ProximoWebinar webinar){
        return (webinar.getFecha().compareTo(fecha.getValue())==0 && webinar.getId()==id
                && webinar.getImagen().compareTo(imagen.getValue())==0 && webinar.getInstitucion().compareTo(institucion.getValue())==0
                && webinar.getPonente().compareTo(ponente.getValue())==0);
    }
    
    protected boolean regexName(){
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
        
        binder.forField(fecha).asRequired("Campo requerido").bind(ProximoWebinar::getFecha,ProximoWebinar::setFecha);
        binder.forField(imagen).asRequired("Campo requerido").bind(ProximoWebinar::getImagen,ProximoWebinar::setImagen);
        binder.forField(institucion).asRequired("Campo requerido").bind(ProximoWebinar::getInstitucion,ProximoWebinar::setInstitucion);
        binder.forField(ponente).asRequired("Campo requerido").bind(ProximoWebinar::getPonente,ProximoWebinar::setPonente);
        binder.forField(titulo).asRequired("Campo requerido").bind(ProximoWebinar::getTitulo,ProximoWebinar::setTitulo);
        //Marca error binder.forField(usuario).asRequired("Campo requerido").bind(ProximoWebinar::getUsuario,ProximoWebinar::setUsuario);
        
        return binder.validate().isOk();
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}