package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import java.util.logging.Logger;

/** @author cerimice **/
public final class ProximoWebinarModalWin extends TemplateModalWin{
    
    private TextField nombre;
    private TextField titulo;
    private TextField ponente;
    private TextField institucion;
    private DateField fecha;
    private Upload imagen;
    private ComboBox usuario;
    
    public ProximoWebinarModalWin(){
        init();
        delete.setVisible(false);
    }
    
    public ProximoWebinarModalWin(long id){
        init();
        loadData(id);
        delete.setVisible(false);
    }
    
    private void init(){
        ResponsiveLayout contenido = new ResponsiveLayout();
            Element.cfgLayoutComponent(contenido);
        
        nombre = new TextField();
            Element.cfgComponent(nombre,"Nombre");
            nombre.setPlaceholder("Ingrese nombre");
        titulo = new TextField();
            Element.cfgComponent(titulo,"Título");
            titulo.setPlaceholder("Ingrese título");
        ponente = new TextField();
            Element.cfgComponent(ponente,"Ponente");
            ponente.setPlaceholder("Ingrese ponente");
        institucion = new TextField();
            Element.cfgComponent(institucion,"Institución");
            institucion.setPlaceholder("Ingrese nstitución");
        fecha = new DateField();
            Element.cfgComponent(fecha,"Fecha");
            fecha.setPlaceholder("Seleccione fecha");
        imagen = new Upload();
            Element.cfgComponent(imagen,"Seleccionar imagen");
        usuario = new ComboBox();
            Element.cfgComponent(usuario,"Usuario");
            usuario.setPlaceholder("--Seleccionar--");
            
        
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombre);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(titulo);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(ponente);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(institucion);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(fecha);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(usuario);
            row1.addColumn().withDisplayRules(12,12,12,6).withComponent(imagen).setAlignment(ResponsiveColumn.ColumnComponentAlignment.RIGHT);
        
        contentLayout.addComponent(contenido);
        setCaption("Proximo webinar");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id){
        try{
            ProximoWebinar obj = ControladorProximoWebinar.getInstance().getById(id);
            this.id = obj.getId();
            titulo.setValue(obj.getTitulo());
            ponente.setValue(obj.getPonente());
            institucion.setValue(obj.getInstitucion());
            //fecha.setValue(obj.getFecha());
            usuario.setValue(obj.getUsuario());
            //imagen.setValue(obj.getImagen());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent(){
        try{
            ControladorProximoWebinar.getInstance().delete(id);
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent(){
        try{
            ProximoWebinar obj = new ProximoWebinar();
                obj.setId(id);
            obj = ControladorProximoWebinar.getInstance().save(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados",Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getProximoWebinarDlg().updateDlg();
                close();
            }
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonCancelEvent(){
        close();
    }
    
}
