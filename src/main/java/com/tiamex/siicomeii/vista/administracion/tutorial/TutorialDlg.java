package com.tiamex.siicomeii.vista.administracion.tutorial;

import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.administracion.tutorialSesion.TutorialSesionDlg;
import com.tiamex.siicomeii.vista.administracion.tutorialSesion.TutorialSesionModalWin;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class TutorialDlg extends TemplateDlg<Tutorial>{

    public static String opt;

    public TutorialDlg(){
        init();
    }

    private void init(){
        banBoton = 3;
        grid.addColumn(Tutorial::getNombre).setCaption("Nombre");
        grid.addColumn(Tutorial::getTutor).setCaption("Tutor");
        grid.addColumn(Tutorial::getInstitucion).setCaption("Institución");
        grid.addColumn(Tutorial::getObjUsuario).setCaption("Usuario");

        setCaption("<b>Tutoriales</b>");
        buttonSearchEvent();
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorTutorial.getInstance().getByName(searchField.getValue()));
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

    @Override
    protected void eventEditButtonGrid(Tutorial obj) {
        ui.addWindow(new TutorialModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(Tutorial obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(Tutorial obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void eventTutorialSesiones(Tutorial obj) {
        try {
            TutorialSesionDlg obj2 = new TutorialSesionDlg();

            obj2.setTutorial(obj.getId());
            opt = obj.getNombre();
            ui.getFabricaVista().getMainPanel().setContenidoPrincipal(obj2);
        } catch (IOException ex) {
            Logger.getLogger(TutorialDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void eventWebinarsAgremiado(Tutorial obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
