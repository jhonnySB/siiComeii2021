/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.administracion.tutorialSesion;

import com.tiamex.siicomeii.controlador.ControladorTutorialSesion;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/**
 *
 * @author fred
 */
public class TutorialSesionDlg extends TemplateDlg<TutorialSesion>{
    
    private long tutorial;
    
    public long getTutorial(){
        return tutorial;
    }
    public void setTutorial(long tutorial){
        this.tutorial = tutorial;
        buttonSearchEvent();
    }
      
    public TutorialSesionDlg(){
        init();
    }

    private void init(){
        grid.addColumn(TutorialSesion::getNombre).setCaption("Nombre");
        grid.addColumn(TutorialSesion::getTutor).setCaption("Tutor");
        grid.addColumn(TutorialSesion::getInstitucion).setCaption("Institución");
        grid.addColumn(TutorialSesion::getUrlYoutube).setCaption("URL de youtube");

        setCaption("<b>Tutoriales</b>");
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorTutorialSesion.getInstance().getByTutorialByName(tutorial, searchField.getValue()));
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        TutorialSesionModalWin venta = new TutorialSesionModalWin();
        venta.setTutorial(tutorial);
        ui.addWindow(venta);
    }

    @Override
    protected void gridEvent() {
    }
    
    @Override
    protected void eventEditButtonGrid(TutorialSesion obj) {
        ui.addWindow(new TutorialSesionModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(TutorialSesion obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(TutorialSesion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventWebinarsAgremiado(TutorialSesion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(TutorialSesion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
