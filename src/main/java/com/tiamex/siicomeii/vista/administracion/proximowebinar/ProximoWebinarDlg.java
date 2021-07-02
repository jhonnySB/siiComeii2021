package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoWebinarDlg extends TemplateDlg<ProximoWebinar>{

    public ProximoWebinarDlg() throws Exception{
        init();
    }

    private void init(){
        grid.addColumn(ProximoWebinar::getTitulo).setCaption("Titulo");
        grid.addColumn(ProximoWebinar::getPonente).setCaption("Ponente");
        grid.addColumn(ProximoWebinar::getInstitucion).setCaption("Institución");
        grid.addColumn(ProximoWebinar::getFechaFrm).setCaption("Fecha");
        grid.addColumn(ProximoWebinar::getImagen).setCaption("Imagen");

        setCaption("<b>Próximos webinars</b>");
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorProximoWebinar.getInstance().getAll());
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<ProximoWebinar> webinars = ControladorProximoWebinar.getInstance().getByTitulo(strBusqueda);
                int proxWebinar = webinars.size();
                if(proxWebinar>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(proxWebinar)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(proxWebinar==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(proxWebinar)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(webinars);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorProximoWebinar.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    @Override
    protected void eventDeleteButtonGrid(ProximoWebinar obj) {
        try {
            ui.addWindow(new ProximoWebinarModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(ProximoWebinarDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new ProximoWebinarModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(ProximoWebinar obj) {
        ui.addWindow(new ProximoWebinarModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(ProximoWebinar obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(ProximoWebinar obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventWebinarsAgremiado(ProximoWebinar obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(ProximoWebinar t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
