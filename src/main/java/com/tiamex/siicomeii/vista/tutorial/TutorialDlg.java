package com.tiamex.siicomeii.vista.tutorial;

import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author cerimice **/
public class TutorialDlg extends TemplateDlg<Tutorial>{
    
    public TutorialDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(Tutorial::getNombre).setCaption("Nombre");
        grid.addColumn(Tutorial::getTutor).setCaption("Tutor");
        grid.addColumn(Tutorial::getInstitucion).setCaption("Institución");
        grid.addColumn(Tutorial::getUsuario).setCaption("Usuario");
              
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorTutorial.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new TutorialModalWin());
    }

    @Override
    protected void gridEvent() {
    }
    
    protected void eventEditButtonGrid(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void eventEditButtonGrid(ProximoWebinar obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventEditButtonGrid(Tutorial obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
