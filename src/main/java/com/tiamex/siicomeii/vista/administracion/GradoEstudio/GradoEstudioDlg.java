package com.tiamex.siicomeii.vista.administracion.GradoEstudio;

import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/** @author cerimice **/
public class GradoEstudioDlg extends TemplateDlg<GradoEstudio>{
    
    public GradoEstudioDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(GradoEstudio::getId).setCaption("Id");
        grid.addColumn(GradoEstudio::getNombre).setCaption("Nombre");
        
        buttonSearchEvent();
    }

    @Override
    protected void buttonSearchEvent(){
        grid.setItems(ControladorGradoEstudio.getInstance().getAll());
    }
    
    @Override
    protected void buttonAddEvent(){
        
    }
    
    @Override
    protected void gridEvent(){
        
    }
    
    @Override
    protected void eventEditButtonGrid(GradoEstudio obj){
        Element.makeNotification(obj.getId()+" | "+obj.getNombre(),Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
    }
}
