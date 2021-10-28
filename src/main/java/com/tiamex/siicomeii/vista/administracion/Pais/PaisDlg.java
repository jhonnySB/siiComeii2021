package com.tiamex.siicomeii.vista.administracion.Pais;

import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlgCatalogos;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class PaisDlg extends TemplateDlgCatalogos<Pais>{

    public PaisDlg() throws Exception{
        init();
    }

    private void init() {
        searchField.setPlaceholder("Buscar país");
        //grid.addColumn(Pais::getId).setCaption("Id");
        grid.addColumn(Pais::getNombre).setCaption("Pais");
        setCaption("<b>Países</b>");
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorPais.getInstance().getAll());
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                //resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<Pais> paises = ControladorPais.getInstance().getByName(strBusqueda);
                int paiseSize = paises.size();
                if(paiseSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;font-family:Lora;"
                            + "letter-spacing: 1px;\">Se encontraron "+Integer.toString(paiseSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(paiseSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                            + "letter-spacing: 1px;\">Se encontró "+Integer.toString(paiseSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:14px;font-family:Lora;"
                             + "letter-spacing: 1px;\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");  
                }
                grid.setItems(paises);
            }else{
                resBusqueda.setValue(null);
                //resBusqueda.setHeight("10px");
                grid.setItems(ControladorPais.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new PaisModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(Pais obj){
        ui.addWindow(new PaisModalWin(obj.getId()));
    }
    
    @Override
    protected void eventDeleteButtonGrid(Pais obj) {
        try {
            ui.addWindow(new PaisModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(PaisDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void eventAsistenciaButton(Pais obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(Pais obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventWebinarsAgremiado(Pais obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(Pais obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}
