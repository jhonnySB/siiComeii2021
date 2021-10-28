package com.tiamex.siicomeii.vista.administracion.tutorial;

import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.ContentMode;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/** @author fred **/
public class TutorialDlg extends TemplateDlg<Tutorial>{

    public static String opt;
    ListDataProvider<Tutorial> dataProvider;
    public TutorialDlg() throws Exception{
        init();
    }

    private void init() {
        banBoton = 3;
        searchField.setPlaceholder("Buscar por nombre,tutor,instituto");
        grid.addColumn(Tutorial::getNombre).setCaption("Nombre").setHidable(false);
        grid.addColumn(Tutorial::getTutor).setCaption("Tutor").setHidable(true).setHidingToggleCaption("Mostrar Tutor");
        grid.addColumn(Tutorial::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución");
        grid.addColumn(Tutorial::getObjUsuario).setCaption("Usuario").setHidable(true).setHidingToggleCaption("Mostrar Usuario").setHidden(true);

        setCaption("<b>Tutoriales</b>");
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar(){ 
        dataProvider = DataProvider.ofCollection(ControladorTutorial.getInstance().getAll());
        grid.setDataProvider(dataProvider);
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                String searchTxt = searchField.getValue();
                dataProvider.setFilter(filterAllByString(searchTxt));
                resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">"
                        + "No se encontro ninguna coincidencia para la búsqueda '"+searchTxt+"'"+" </span></b>"); 
            }else{
                resBusqueda.setValue(null);
                dataProvider.clearFilters();
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    private SerializablePredicate<Tutorial> filterAllByString(String searchTxt) {
        SerializablePredicate<Tutorial> predicate;
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
