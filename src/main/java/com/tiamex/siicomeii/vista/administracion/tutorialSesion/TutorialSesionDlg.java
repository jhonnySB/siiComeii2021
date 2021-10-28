/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.administracion.tutorialSesion;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.controlador.ControladorTutorialSesion;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.FabricaVista;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author fred
 */
public class TutorialSesionDlg extends TemplateDlg<TutorialSesion>{
    
    public long tutorialLinked;
    ListDataProvider<TutorialSesion> dataProvider;
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
        tutoriales = new Button(); tutoriales.setResponsive(true); tutoriales.setCaption("Tutoriales");
        tutoriales.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
        tutoriales.addClickListener((ClickListener) listener->{
            FabricaVista fb = ui.getFabricaVista();
            try {
                fb.getMainPanel().setContenidoPrincipal(fb.getTutorialDlg());
            } catch (Exception ex) {
                Logger.getLogger(TutorialSesionDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        rowBar.addComponent(tutoriales);
        searchField.setPlaceholder("Buscar...");
        grid.addColumn(TutorialSesion::getNombre).setCaption("Nombre").setHidable(true).setHidingToggleCaption("Mostrar Nombre");
        grid.addColumn(TutorialSesion::getTutor).setCaption("Tutor").setHidable(true).setHidingToggleCaption("Mostrar Tutor");
        grid.addColumn(TutorialSesion::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución");
        //grid.addColumn(TutorialSesion::getUrlYoutube).setCaption("URL de youtube").setHidable(true).setHidingToggleCaption("Mostrar URL");
        grid.addComponentColumn(this::buildUrlCmpt).setCaption("Enlace de video").setHidable(true).
                setHidingToggleCaption("Mostrar enlace").setWidthUndefined().setResizable(false).clearExpandRatio();
        eventMostrar();
    }
    
    private ResponsiveLayout buildUrlCmpt(TutorialSesion tuto){
        ResponsiveLayout rl = new ResponsiveLayout();
        Link link = new Link();
        link.setResponsive(true); link.addStyleNames(ValoTheme.LINK_SMALL);
        link.setResource(new ExternalResource(tuto.getUrlYoutube()));
        link.setIcon(VaadinIcons.ARROW_FORWARD);
        link.setTargetName("_blank");
        link.setCaption("Video YouTube");
        rl.addRow().withComponents(link);
        return rl;
    }
    
        
    @Override
    protected void eventMostrar() { 
        try {
            setCaption("<span style=\"text-decoration: underline;font-family:inherit;font-size:16px\">Sesiones para el tutorial: </span>"
                    + "<b><span style=\"background-color:#ffc107;padding:3px 6px;color:black;border-radius:5px;font-size:16px;\">"
                    + ControladorTutorial.getInstance().getById(tutorialLinked).getNombre() + "</span></b>");
            dataProvider = DataProvider.ofCollection(ControladorTutorialSesion.getInstance().getAllLinked(tutorialLinked));
            grid.setDataProvider(dataProvider);
        } catch (Exception ex) {
            Logger.getLogger(TutorialSesionDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                String searchTxt = searchField.getValue();
                dataProvider.setFilter(filterAllByString(searchTxt));
                resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans"
                        + "\">No se encontro ninguna coincidencia para la búsqueda '"+searchTxt+"'"+" </span></b>"); 
            }else{
                resBusqueda.setValue(null);
                dataProvider.clearFilters();
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    private SerializablePredicate<TutorialSesion> filterAllByString(String searchTxt) {
        SerializablePredicate<TutorialSesion> predicate;
        predicate = (tuto) -> {
            String name = tuto.getNombre(), tutor = tuto.getTutor(), inst = tuto.getInstitucion();
            Pattern pattern = Pattern.compile(Pattern.quote(searchTxt), Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(name).find() || pattern.matcher(tutor).find() || pattern.matcher(inst).find()) {
                resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                        + "letter-spacing: 1px;\">Se encontraron coincidencias para la búsqueda '" + searchTxt + "'" + " </span></b>");
                return true;
            }
            return false;
        };
        return predicate;
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
