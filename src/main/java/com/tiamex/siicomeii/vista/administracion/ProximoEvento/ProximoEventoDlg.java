package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoEventoDlg extends TemplateDlg<ProximoEvento>{
    
    public ProximoEventoDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(ProximoEvento::getId).setCaption("Id");
        grid.addColumn(ProximoEvento::getDescripcion).setCaption("Descripción");
        grid.addColumn(ProximoEvento::getFecha).setCaption("Fecha");
        grid.addColumn(ProximoEvento::getImagen).setCaption("Imagen");
        grid.addColumn(ProximoEvento::getTitulo).setCaption("Título");
        grid.addColumn(ProximoEvento::getObjUsuario).setCaption("Usuario");
        setCaption("<b>Próximos eventos</b>");
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorProximoEvento.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
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
    
}
