package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.administracion.proximowebinar.ProximoWebinarModalWin;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**@author fred **/
public final class WebinarRealizadoModalWin extends TemplateModalWin implements Upload.Receiver{
    
    /*
    private DateTimeField fecha;
    private TextField institucion;
    private TextField nombre;
    private TextField ponente;
    private TextField presentacion;
    private TextField urlYoutube;
    */
    private Label fecha;
    private Label institucion;
    private Label nombre;
    private Label ponente;
    private TextField presentacion;
    private TextField urlYoutube;
    private LocalDateTime dFecha;
    private ComboBox<Agremiado> cmboxAgremiados;
    // 
    private String dInstitucion;
    private String dNombre;
    private String dPonente;

    public WebinarRealizadoModalWin() {
        //init();
    }
    
    public WebinarRealizadoModalWin(WebinarRealizado webR){
        dFecha = webR.getFecha();
        dInstitucion = webR.getInstitucion();
        dNombre = webR.getNombre();
        dPonente = webR.getPonente();
        init();
        loadData(webR.getId());
    }
    
    public WebinarRealizadoModalWin( ProximoWebinar proxWeb){
            dFecha = proxWeb.getFecha();
            dInstitucion = proxWeb.getInstitucion();
            dNombre = proxWeb.getTitulo();
            dPonente = proxWeb.getPonente();
            init();

    }

    public WebinarRealizadoModalWin(long id) {
        //init();
        //loadData(id);
        
    }

    private void init( ) {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        
        cmboxAgremiados = new ComboBox<>();
        cmboxAgremiados.setPlaceholder("Agregar agremiados");
        cmboxAgremiados.setEmptySelectionAllowed(false);
        try {
            cmboxAgremiados.setItems(ControladorAgremiado.getInstance().getAllSorted("nombre"));
        } catch (Exception ex) {
            Logger.getLogger(WebinarRealizadoModalWin.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmboxAgremiados.addValueChangeListener((ValueChangeListener) event->{
            
        });
        
        fecha = new Label();
        fecha.setCaption("Fecha y hora");
        fecha.setIcon(VaadinIcons.CALENDAR_CLOCK);
        fecha.setCaptionAsHtml(true);
        fecha.setValue(dFecha.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' y '('hh:mm'hrs.)'", new Locale("es", "MX"))));
        
        
        institucion = new Label();
        institucion.setCaption("Institución");
        institucion.setIcon(VaadinIcons.ACADEMY_CAP);
        institucion.setCaptionAsHtml(true);
        institucion.setValue(dInstitucion);
        
        nombre = new Label();
        nombre.setCaption("Nombre");
        nombre.setValue(dNombre);
        nombre.setIcon(VaadinIcons.EXCLAMATION_CIRCLE);
        
        ponente = new Label();
        ponente.setCaption("Ponente");
        ponente.setIcon(VaadinIcons.USER);
        ponente.setValue(dPonente);
        
        presentacion = new TextField();
        Element.cfgComponent(presentacion, "URL de presentación");
        presentacion.setPlaceholder("Pega la url de la presentación");
        presentacion.setRequiredIndicatorVisible(true);

        urlYoutube = new TextField();
        Element.cfgComponent(urlYoutube, "URL de Youtube");
        urlYoutube.setPlaceholder("Pega la url de youtube");
        urlYoutube.setRequiredIndicatorVisible(true);
        
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(ponente);
        row1.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(fecha);
        
        row1.setMargin(ResponsiveRow.MarginSize.SMALL, ResponsiveLayout.DisplaySize.XS);
        row1.setVerticalSpacing(true);
        ResponsiveRow row2 = contenido.addRow().withAlignment(Alignment.BOTTOM_CENTER);
        row2.setCaption("");row2.setCaptionAsHtml(true);
        row2.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(presentacion);
        row2.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(urlYoutube);
        
        try {
            
            setCaption((WebinarRealizado) ControladorWebinarRealizado.getInstance().getById(id)!=null ? "Webinar realizado (Archivado)" : "Webinar realizado");
            
        } catch (Exception ex) {
            Logger.getLogger(ProximoWebinarModalWin.class.getName()).log(Level.SEVERE, null, ex);
        }

        contentLayout.addComponent(contenido);
        setWidth("50%");

        
    }

    @Override
    protected void loadData(long id) {
        
        try {
            WebinarRealizado obj = ControladorWebinarRealizado.getInstance().getById(id);
            //this.id = obj.getId();
            //fecha.setValue(obj.getFecha().toString());
            //institucion.setValue(obj.getInstitucion()); 
            //nombre.setValue(obj.getNombre()); 
            //ponente.setValue(obj.getPonente()); 
            this.id = obj.getId();
            presentacion.setValue(obj.getPresentacion()); //presentacion.setReadOnly(true);
            urlYoutube.setValue(obj.getUrlYoutube()); //urlYoutube.setReadOnly(true);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    } 
    
    protected void loadDataWebRealizado(LocalDateTime dFecha,String dInstitucion,String dNombre,String dPonente) {
        try {
            
            fecha.setValue(dFecha.toString());
            institucion.setValue(dInstitucion); 
            nombre.setValue(dNombre); 
            ponente.setValue(dPonente); 
            //presentacion.setValue();
            //urlYoutube.setValue(obj.getUrlYoutube());
            
            
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    

    
    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                WebinarRealizado obj = new WebinarRealizado();
                obj.setId(id);
                obj.setFecha(dFecha);
                obj.setInstitucion(dInstitucion);
                obj.setNombre(dNombre);
                obj.setPonente(dPonente);
                obj.setPresentacion(presentacion.getValue());
                obj.setUrlYoutube(urlYoutube.getValue());

                
                    WebinarRealizado webinar = (WebinarRealizado)ControladorWebinarRealizado.getInstance().getByNames(dNombre);
                    
                    if(webinar!=null){
                        if (compareWebinars(webinar)) {
                            close();
                        }  
                    }
                    
                obj = ControladorWebinarRealizado.getInstance().save(obj);
                if (obj != null) {
                    if (id != 0) {
                        Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    } else {
                        Element.makeNotification("Registro agregado a Webinars Realizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    }
                    ui.getFabricaVista().getWebinarRealizadoDlg().updateDlg();
                    close();
                } else {
                    Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                }
                    
               ui.getFabricaVista().getProximoWebinarDlg().updateDlg();
                 
            } else {
                Element.makeNotification("Faltan campos por llenar", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    protected boolean compareWebinars(WebinarRealizado webinar){
        return (webinar.getUrlYoutube().compareTo(urlYoutube.getValue())==0 && webinar.getPresentacion().compareTo(presentacion.getValue())==0);
    } 
    
    protected boolean regexName(){
        String regex = "[^A-z|ñ|\\p{L}| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherPonente = pattern.matcher(ponente.getValue()); 

        return matcherPonente.find();
    }
    

    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
    private boolean validarCampos() {
        Binder<WebinarRealizado> binder = new Binder<>();

        binder.forField(presentacion).asRequired("Campo requerido").bind(WebinarRealizado::getPresentacion,WebinarRealizado::setPresentacion);
        binder.forField(urlYoutube).asRequired("Campo requerido").bind(WebinarRealizado::getUrlYoutube,WebinarRealizado::setUrlYoutube);

        return binder.validate().isOk();
    }
    /*
    private boolean validarCampos() {
        Binder<WebinarRealizado> binder = new Binder<>();

        binder.forField(fecha).asRequired("Campo requerido").bind(WebinarRealizado::getFecha,WebinarRealizado::setFecha);
        binder.forField(institucion).asRequired("Campo requerido").bind(WebinarRealizado::getInstitucion,WebinarRealizado::setInstitucion);
        binder.forField(nombre).asRequired("Campo requerido").bind(WebinarRealizado::getNombre,WebinarRealizado::setNombre);
        binder.forField(ponente).asRequired("Campo requerido").bind(WebinarRealizado::getPonente,WebinarRealizado::setPonente);
        binder.forField(presentacion).asRequired("Campo requerido").bind(WebinarRealizado::getPresentacion,WebinarRealizado::setPresentacion);
        binder.forField(urlYoutube).asRequired("Campo requerido").bind(WebinarRealizado::getUrlYoutube,WebinarRealizado::setUrlYoutube);

        return binder.validate().isOk();
    }*/
    
    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
