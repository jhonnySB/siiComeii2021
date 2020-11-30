package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
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

    public ProximoWebinarModalWin() {
        init();
        delete.setVisible(false);
    }

    public ProximoWebinarModalWin(long id) {
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

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(fecha);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(imagen);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(ponente);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(titulo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuario);

        contentLayout.addComponent(contenido);
        setCaption("Proximo webinar");
        setWidth("50%");
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
            //fecha.setReadOnly(true);
            //fecha.setValue(obj.getFecha());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorProximoWebinar.getInstance().delete(id);
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

                obj = ControladorProximoWebinar.getInstance().save(obj);
                if (obj != null) {
                    Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    ui.getFabricaVista().getProximoWebinarDlg().updateDlg();
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