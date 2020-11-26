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
        delete.setVisible(false);
    }

    public WebinarRealizadoModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        fecha = new DateTimeField();
        fecha.setRequiredIndicatorVisible(true);
        fecha.setValue(LocalDateTime.now().withHour(11).withMinute(00).plusDays(1));
        fecha.setRangeStart(fecha.getValue().minusDays(1));
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
            fecha.setValue(obj.getFecha());
            institucion.setValue(obj.getInstitucion());
            nombre.setValue(obj.getNombre());
            ponente.setValue(obj.getPonente());
            presentacion.setValue(obj.getPresentacion());
            urlYoutube.setValue(obj.getUrlYoutube());
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
