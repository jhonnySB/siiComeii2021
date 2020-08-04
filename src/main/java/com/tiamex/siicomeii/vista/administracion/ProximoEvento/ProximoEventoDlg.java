package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;

/** @author cerimice **/
public class ProximoEventoDlg extends TemplateDlg<ProximoEvento>{
    
    public ProximoEventoDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(ProximoEvento::getId).setCaption("Id");
        grid.addColumn(ProximoEvento::getDescripcion).setCaption("Descripción");
        grid.addColumn(ProximoEvento::getFecha).setCaption("Fecha");
        grid.addColumn(ProximoEvento::getImagen).setCaption("Imagen");
        grid.addColumn(ProximoEvento::getNombre).setCaption("Nombre");
        grid.addColumn(ProximoEvento::getTitulo).setCaption("Título");
        grid.addColumn(ProximoEvento::getUsuario).setCaption("usuario");
        
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorProximoEvento.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent() {
       ui.addWindow(new ProximoEventoModalWin());
    }

    @Override
    protected void gridEvent() {
    }
    
    @Override
    protected void eventEditButtonGrid(ProximoEvento obj) {
        ui.addWindow(new ProximoEventoModalWin(obj.getId()));
    }
    
}
