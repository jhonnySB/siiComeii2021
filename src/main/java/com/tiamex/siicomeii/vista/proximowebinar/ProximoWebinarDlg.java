package com.tiamex.siicomeii.vista.proximowebinar;

import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
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
        ui.addWindow(new ProximoWebinarModalWin());
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
