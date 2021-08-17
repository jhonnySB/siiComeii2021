/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.administracion.tutorialSesion;

import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.controlador.ControladorTutorialSesion;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fred
 */
public class TutorialSesionDlg extends TemplateDlg<TutorialSesion>{
    
    public long tutorialLinked;
    
    public long getTutorial(){
        return tutorialLinked;
    }
    /*
    public void setTutorial(long tutorial){
        this.tutorialLinked = tutorial;
        eventMostrar();
    } */

      
    public TutorialSesionDlg(long tutorialLinked) throws Exception{
        this.tutorialLinked = tutorialLinked;
        init();
    }
    
    public TutorialSesionDlg() throws Exception{
        init();
    }

    private void init() throws Exception{
        banBoton = 4;
        grid.addColumn(TutorialSesion::getNombre).setCaption("Nombre").setHidable(true).setHidingToggleCaption("Mostrar Nombre");
        grid.addColumn(TutorialSesion::getTutor).setCaption("Tutor").setHidable(true).setHidingToggleCaption("Mostrar Tutor");
        grid.addColumn(TutorialSesion::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución");
        grid.addColumn(TutorialSesion::getUrlYoutube).setCaption("URL de youtube").setHidable(true).setHidingToggleCaption("Mostrar URL");
        setCaption("<span style=\"text-decoration: underline;font-family: Source Sans Pro\">Sesiones para el tutorial: </span>"
                + "<b><span style=\"background-color:#ffc107;padding:3px 6px;color:black;border-radius:5px;font-size:16px;\">"
                +ControladorTutorial.getInstance().getById(tutorialLinked).getNombre()+"</span></b>");
        eventMostrar();
    }
    
        
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorTutorialSesion.getInstance().getAllLinked(tutorialLinked));
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<TutorialSesion> tutorials = ControladorTutorialSesion.getInstance().getByTutorialByName(tutorialLinked,strBusqueda);
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
                grid.setItems(ControladorTutorialSesion.getInstance().getAllLinked(tutorialLinked));
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
               @Override
    protected void eventDeleteButtonGrid(TutorialSesion obj) {
        try {
            ui.addWindow(new TutorialSesionModalDelete(obj.getId(),tutorialLinked) );
        } catch (Exception ex) {
            Logger.getLogger(TutorialSesionDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void buttonAddEvent(){
        TutorialSesionModalWin window = new TutorialSesionModalWin(tutorialLinked,true);
        //venta.setTutorial(tutorialLinked); 
        ui.addWindow(window); 
    }

    @Override
    protected void gridEvent() {
    }
    
    @Override
    protected void eventEditButtonGrid(TutorialSesion obj) {
        ui.addWindow(new TutorialSesionModalWin(obj.getId(),false));
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
