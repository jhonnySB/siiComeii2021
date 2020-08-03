package com.tiamex.siicomeii.vista.tutorial;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;

/** @author cerimice **/
public final class TutorialModalWin extends TemplateModalWin{
    
    private TextField nombre;
    private TextField tutor;
    private TextField institucion;
    private ComboBox usuario;
    
    public TutorialModalWin(){
        init();
        delete.setVisible(false);
    }
    
    public TutorialModalWin(long id){
        init();
        loadData(id);
    }
    
    private void init(){
        ResponsiveLayout contenido = new ResponsiveLayout();
            Element.cfgLayoutComponent(contenido);
        
        nombre = new TextField();
            Element.cfgComponent(nombre,"Nombre");
            nombre.setPlaceholder("Ingrese nombre");
        tutor = new TextField();
            Element.cfgComponent(tutor,"Tutor");
            tutor.setPlaceholder("Ingrese tutor");
        institucion = new TextField();
            Element.cfgComponent(institucion,"Institución");
            institucion.setPlaceholder("Ingrese nstitución");
        usuario = new ComboBox();
            Element.cfgComponent(usuario,"Usuario");
            usuario.setPlaceholder("--Seleccionar--");
            
        
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombre);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(tutor);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(institucion);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(usuario);
            
        
        contentLayout.addComponent(contenido);
        setCaption("Tutorial");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id){
        try{
            ProximoWebinar obj = ControladorProximoWebinar.getInstance().getById(id);
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent(){
        try{
            ControladorUsuarioGrupo.getInstance().delete(id);
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent(){
        try{
            UsuarioGrupo obj = new UsuarioGrupo();
                obj.setId(id);
                obj.setNombre(nombre.getValue());
            obj = ControladorUsuarioGrupo.getInstance().save(obj);
            if(obj != null){
                Element.makeNotification("Datos guardados",Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getUsuarioGrupoDlg().updateDlg();
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
