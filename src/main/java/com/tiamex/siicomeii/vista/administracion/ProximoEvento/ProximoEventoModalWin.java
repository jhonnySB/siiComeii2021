package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoEventoModalWin extends TemplateModalWin implements Upload.Receiver{
    private TextArea descripcion;
    private DateTimeField fecha;
    private TextField imagen;
    private TextField titulo;
    private TextField usuario;

    public ProximoEventoModalWin() {
        init();
        delete.setVisible(false);
    }

    public ProximoEventoModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        descripcion = new TextArea();
        Element.cfgComponent(descripcion, "Descipción");
        descripcion.setRequiredIndicatorVisible(true);

        fecha = new DateTimeField();
        Element.cfgComponent(fecha, "Fecha");
        fecha.setValue(LocalDateTime.now().withHour(11).withMinute(00).plusDays(1));
        fecha.setRangeStart(fecha.getValue().minusDays(1));
        fecha.setRequiredIndicatorVisible(true);

        imagen = new TextField();
        Element.cfgComponent(imagen, "Imagen");
        imagen.setPlaceholder("Ingrese url de imagen");
        imagen.setRequiredIndicatorVisible(true);


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
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(descripcion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(fecha);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(imagen);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(titulo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuario);

        contentLayout.addComponent(contenido);

        setCaption("Próximo evento");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            ProximoEvento obj = ControladorProximoEvento.getInstance().getById(id);            
            this.id = obj.getId();
            descripcion.setValue(obj.getDescripcion());
            fecha.setValue(obj.getFecha());
            imagen.setValue(obj.getImagen());
            titulo.setValue(obj.getTitulo());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorProximoEvento.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                ProximoEvento obj = new ProximoEvento();
                obj.setId(id);
                obj.setDescripcion(descripcion.getValue());
                obj.setFecha(fecha.getValue());
                obj.setImagen(imagen.getValue());
                obj.setTitulo(titulo.getValue());
                obj.setUsuario(ui.getUsuario().getId());

                obj = ControladorProximoEvento.getInstance().save(obj);
                if (obj != null) {
                    Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    ui.getFabricaVista().getProximoEventoDlg().updateDlg();
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
        Binder<ProximoEvento> binder = new Binder<>();
        
        binder.forField(descripcion).asRequired("Campo requerido").bind(ProximoEvento::getDescripcion,ProximoEvento::setDescripcion);
        binder.forField(fecha).asRequired("Campo requerido").bind(ProximoEvento::getFecha,ProximoEvento::setFecha);
        binder.forField(imagen).asRequired("Campo requerido").bind(ProximoEvento::getImagen,ProximoEvento::setImagen);
        binder.forField(titulo).asRequired("Campo requerido").bind(ProximoEvento::getTitulo,ProximoEvento::setTitulo);
        
        return binder.validate().isOk();
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
