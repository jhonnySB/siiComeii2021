package com.tiamex.siicomeii.vista.administracion.tutorial;

import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.shared.ui.ContentMode;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class TutorialDlg extends TemplateDlg<Tutorial>{

    public static String opt;

    public TutorialDlg() throws Exception{
        init();
    }

    private void init() {
        banBoton = 3;
        grid.addColumn(Tutorial::getNombre).setCaption("Nombre");
        grid.addColumn(Tutorial::getTutor).setCaption("Tutor");
        grid.addColumn(Tutorial::getInstitucion).setCaption("Institución");
        grid.addColumn(Tutorial::getObjUsuario).setCaption("Usuario");

        setCaption("<b>Tutoriales</b>");
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar(){ 
        grid.setItems(ControladorTutorial.getInstance().getAll());  
        
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                resBusqueda.setContentMode(ContentMode.HTML);
                String strBusqueda = searchField.getValue();
                Collection<Tutorial> tutorials = ControladorTutorial.getInstance().getByName(strBusqueda);
                int tutorialSize = tutorials.size();
                if(tutorialSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(tutorialSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(tutorialSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(tutorialSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(tutorials);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorTutorial.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
           @Override
    protected void eventDeleteButtonGrid(Tutorial obj) {
        try {
            ui.addWindow(new TutorialModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(TutorialDlg.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    protected void eventTutorialSesiones(Tutorial obj) {
        try {
            //TutorialSesionDlg obj2 = new TutorialSesionDlg(obj.getId());
            
            //obj2.setTutorial(obj.getId());
            opt = obj.getNombre();
            ui.getFabricaVista().getMainPanel().setContenidoPrincipal(ui.getFabricaVista().getTutorialsesionDlg(obj.getId()));
        } catch (IOException ex) {
            Logger.getLogger(TutorialDlg.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TutorialDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    protected void eventWebinarsAgremiado(Tutorial obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
