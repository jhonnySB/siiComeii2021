package com.tiamex.siicomeii.vista.administracion.Pais;

import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author fred **/
public class PaisDlg extends TemplateDlg<Pais>{

    public PaisDlg(){
        init();
    }

    private void init(){
        grid.addColumn(Pais::getId).setCaption("Id");
        grid.addColumn(Pais::getNombre).setCaption("Pais");
        setCaption("<b>Países</b>");
        buttonSearchEvent();
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorPais.getInstance().getByName(searchField.getValue()));
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
