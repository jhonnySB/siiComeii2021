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
    }

    public ProximoEventoModalWin(long id) {
        init();
        loadData(id);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        descripcion = new TextArea();
        Element.cfgComponent(descripcion, "Descipción");
        descripcion.setPlaceholder("Ingrese una descripción del contenido del próximo evento");
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
            imagen.setValue(obj.getImagen());
            titulo.setValue(obj.getTitulo());
            //fecha.setReadOnly(true);
            //fecha.setValue(obj.getFecha());
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
                
                ProximoEvento evento = (ProximoEvento)ControladorProximoEvento.getInstance().getByTitulos(titulo.getValue());
                
                if(id==0){ // nuevo registro (botón agregar)
                                
                                if (evento != null) { // nuevo registro con entrada de correo duplicada
                                    
                                    Element.makeNotification("Ya existe un evento con el mismo título: '"+evento.getTitulo()+"'", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                }else{ // nuevo registro con nuevo correo
                                    
                                        obj = ControladorProximoEvento.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getProximoEventoDlg().eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                }
                            }else{ // editando un registro
                                
                                if(evento!=null){ // mismo reg
                                    
                                    if(compareEvento(evento)){ // sin cambios
                                        close();
                                    }else if(evento.getId()!=id){
                                        Element.makeNotification("Ya existe un evento con el mismo título: '"+evento.getTitulo()+"'", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }else{
                                        obj = ControladorProximoEvento.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getProximoEventoDlg().eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }
                                    
                                }else{
                                    
                                        obj = ControladorProximoEvento.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getProximoEventoDlg().eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
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
    
    protected boolean compareEvento(ProximoEvento evento){
        return (evento.getDescripcion().compareTo(descripcion.getValue())==0 && evento.getId()==id
                && evento.getFecha().compareTo(fecha.getValue())==0 && evento.getImagen().compareTo(imagen.getValue())==0);
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
