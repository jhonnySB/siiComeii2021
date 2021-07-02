package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**@author fred **/
public class WebinarRealizadoModalWin extends TemplateModalWin implements Upload.Receiver{

    private DateTimeField fecha;
    private TextField institucion;
    private TextField nombre;
    private TextField ponente;
    private TextField presentacion;
    private TextField urlYoutube;

    public WebinarRealizadoModalWin() {
        init();
        
    }

    public WebinarRealizadoModalWin(long id) {
        init();
        loadData(id);
        
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        fecha = new DateTimeField();
        fecha.setRequiredIndicatorVisible(true);
        fecha.setValue(LocalDateTime.now().withHour(11).withMinute(00));
        fecha.setRangeEnd(fecha.getValue());
        Element.cfgComponent(fecha, "Fecha");

        institucion = new TextField();
        Element.cfgComponent(institucion, "Institución");
        institucion.setPlaceholder("Ingrese nstitución");
        institucion.setRequiredIndicatorVisible(true);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setPlaceholder("Ingrese nombre");
        nombre.setRequiredIndicatorVisible(true);

        ponente = new TextField();
        Element.cfgComponent(ponente, "Ponente");
        ponente.setPlaceholder("Ingrese ponente");
        ponente.setRequiredIndicatorVisible(true);

        presentacion = new TextField();
        Element.cfgComponent(presentacion, "URL de presentación");
        presentacion.setPlaceholder("Pega url de la presentación");
        presentacion.setRequiredIndicatorVisible(true);

        urlYoutube = new TextField();
        Element.cfgComponent(urlYoutube, "URL de Youtube");
        urlYoutube.setPlaceholder("Pega url de youtube");
        urlYoutube.setRequiredIndicatorVisible(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(fecha);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(ponente);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(presentacion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(urlYoutube);

        contentLayout.addComponent(contenido);
        setCaption("Webinar realizado");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            WebinarRealizado obj = ControladorWebinarRealizado.getInstance().getById(id);
            this.id = obj.getId();
            institucion.setValue(obj.getInstitucion());
            nombre.setValue(obj.getNombre());
            ponente.setValue(obj.getPonente());
            presentacion.setValue(obj.getPresentacion());
            urlYoutube.setValue(obj.getUrlYoutube());
            fecha.setReadOnly(true);
            fecha.setValue(obj.getFecha());
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
                obj.setFecha(fecha.getValue());
                obj.setInstitucion(institucion.getValue());
                obj.setNombre(nombre.getValue());
                obj.setPonente(ponente.getValue());
                obj.setPresentacion(presentacion.getValue());
                obj.setUrlYoutube(urlYoutube.getValue());

                if(regexName()){
                    Element.makeNotification("Solo se permiten letras para el nombre del ponente", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                }else{
                    WebinarRealizado webinar = (WebinarRealizado)ControladorWebinarRealizado.getInstance().getByNames(nombre.getValue());
                    if(id==0){ // nuevo registro (botón agregar)
                                
                                if (webinar != null) { // nuevo registro con entrada de correo duplicada
                                    Element.makeNotification("Ya existe un registro con el mismo nombre: '"+webinar.getNombre()+"'", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                }else{ // nuevo registro con nuevo correo
                                        obj = ControladorWebinarRealizado.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getWebinarRealizadoDlg().updateDlg();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    
                                }
                            }else{ // editando un registro
                                
                                if(webinar!=null){ // 
                                    
                                    if(compareWebinars(webinar)){ // el mismo registro
                                        close();
                                    }else if(webinar.getId()!=id){
                                        Element.makeNotification("Ya existe un webinar con el mismo título", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }else{
                                        obj = ControladorWebinarRealizado.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getWebinarRealizadoDlg().updateDlg();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }
                                    
                                }else{
                                        obj = ControladorWebinarRealizado.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getWebinarRealizadoDlg().updateDlg();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    
                                }
                            }
                }
            } else {
                Element.makeNotification("Faltan campos por llenar", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    protected boolean compareWebinars(WebinarRealizado webinar){
        return (webinar.getFecha().compareTo(fecha.getValue())==0 && webinar.getId()==id && webinar.getNombre().compareTo(nombre.getValue())==0
                && webinar.getUrlYoutube().compareTo(urlYoutube.getValue())==0 && webinar.getInstitucion().compareTo(institucion.getValue())==0
                && webinar.getPonente().compareTo(ponente.getValue())==0);
    }
    
    protected boolean regexName(){
        String regex = "[^A-z|ñ| ]";
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

        binder.forField(fecha).asRequired("Campo requerido").bind(WebinarRealizado::getFecha,WebinarRealizado::setFecha);
        binder.forField(institucion).asRequired("Campo requerido").bind(WebinarRealizado::getInstitucion,WebinarRealizado::setInstitucion);
        binder.forField(nombre).asRequired("Campo requerido").bind(WebinarRealizado::getNombre,WebinarRealizado::setNombre);
        binder.forField(ponente).asRequired("Campo requerido").bind(WebinarRealizado::getPonente,WebinarRealizado::setPonente);
        binder.forField(presentacion).asRequired("Campo requerido").bind(WebinarRealizado::getPresentacion,WebinarRealizado::setPresentacion);
        binder.forField(urlYoutube).asRequired("Campo requerido").bind(WebinarRealizado::getUrlYoutube,WebinarRealizado::setUrlYoutube);

        return binder.validate().isOk();
    }
    
    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
