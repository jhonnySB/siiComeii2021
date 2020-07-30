package com.tiamex.siicomeii.vista.proximowebinar;

import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;

/** @author cerimice **/
public class ProximoWebinarDlg extends TemplateDlg<ProximoWebinar>{
    
    public ProximoWebinarDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(ProximoWebinar::getImagen).setCaption("Imagen");
        grid.addColumn(ProximoWebinar::getNombre).setCaption("Nombre");
        grid.addColumn(ProximoWebinar::getTitulo).setCaption("Titulo");
        grid.addColumn(ProximoWebinar::getPonente).setCaption("Ponente");
        grid.addColumn(ProximoWebinar::getInstitucion).setCaption("Institución");
        grid.addColumn(ProximoWebinar::getFecha).setCaption("Fecha");
              
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorProximoWebinar.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        TemplateModalWin ventana = new TemplateModalWin(){
            @Override
            protected void loadData(long id) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void buttonDeleteEvent() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void buttonAcceptEvent(){
                Element.makeNotification("Datos guardados",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                close();
            }

            @Override
            protected void buttonCancelEvent(){
                close();
            }
        };
        
        ui.addWindow(ventana);
    }

    @Override
    protected void gridEvent() {
    }
    
    protected void eventEditButtonGrid(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventEditButtonGrid(ProximoWebinar obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
