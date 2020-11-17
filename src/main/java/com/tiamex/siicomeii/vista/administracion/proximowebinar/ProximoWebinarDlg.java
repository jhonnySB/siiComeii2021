package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoWebinarDlg extends TemplateDlg<ProximoWebinar>{
    
    public ProximoWebinarDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(ProximoWebinar::getTitulo).setCaption("Titulo");
        grid.addColumn(ProximoWebinar::getImagen).setCaption("Imagen");
        grid.addColumn(ProximoWebinar::getPonente).setCaption("Ponente");
        grid.addColumn(ProximoWebinar::getInstitucion).setCaption("Institución");
        grid.addColumn(ProximoWebinar::getFecha).setCaption("Fecha");
        setCaption("<b>Próximos webinars</b>");
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
    
    @Override
    protected void eventEditButtonGrid(ProximoWebinar obj) {
        ui.addWindow(new ProximoWebinarModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(ProximoWebinar obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
