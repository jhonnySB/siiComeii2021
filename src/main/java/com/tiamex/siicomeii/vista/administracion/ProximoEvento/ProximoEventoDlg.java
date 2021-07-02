package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.shared.ui.ContentMode;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoEventoDlg extends TemplateDlg<ProximoEvento>{

    public ProximoEventoDlg() throws Exception{
        init();
    }

    private void init(){
        grid.addColumn(ProximoEvento::getTitulo).setCaption("Título");
        grid.addColumn(ProximoEvento::getDescripcion).setCaption("Descripción");
        grid.addColumn(ProximoEvento::getFechaFrm).setCaption("Fecha"); 
        grid.addColumn(ProximoEvento::getImagen).setCaption("Imagen");
        grid.addColumn(ProximoEvento::getObjUsuario).setCaption("Usuario");
        setCaption("<b>Próximos eventos</b>"); 
        grid.setFrozenColumnCount(2);
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar(){ 
        grid.setItems(ControladorProximoEvento.getInstance().getAll());  
        
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                resBusqueda.setContentMode(ContentMode.HTML);
                String strBusqueda = searchField.getValue();
                Collection<ProximoEvento> eventos = ControladorProximoEvento.getInstance().getByName(strBusqueda);
                int eventoSize = eventos.size();
                if(eventoSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(eventoSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(eventoSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(eventoSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(eventos);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorProximoEvento.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
               @Override
    protected void eventDeleteButtonGrid(ProximoEvento obj) {
        try {
            ui.addWindow(new ProximoEventoModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(ProximoEventoDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new ProximoEventoModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(ProximoEvento obj) {
        ui.addWindow(new ProximoEventoModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(ProximoEvento obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(ProximoEvento obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void eventWebinarsAgremiado(ProximoEvento obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(ProximoEvento obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
