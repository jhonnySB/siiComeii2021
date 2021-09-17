package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoAsistentesPDF;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Grid.Column;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public class WebinarRealizadoDlg extends TemplateDlg<WebinarRealizado> {

    public WebinarRealizadoDlg() throws Exception{
        init();
    }

    private void init() {
        banBoton = 1;
        int A = 0;
            rowBar.removeAllComponents();
            rowBar.addColumn().withDisplayRules(12, 6, 6, 10).withComponent(searchField);
            rowBar.addColumn().withDisplayRules(12, 3, 3, 2).withComponent(btnSearch);
        
        grid.addColumn(WebinarRealizado::getNombre).setCaption("Nombre").setHidable(false);
        grid.addColumn(WebinarRealizado::getPonente).setCaption("Ponente").setHidable(true).setHidingToggleCaption("Mostrar Ponente");
        grid.addColumn(WebinarRealizado::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución");
        grid.addColumn(WebinarRealizado::getFechaFrm).setCaption("Fecha").setHidable(true).setHidingToggleCaption("Mostrar Fecha");
        grid.addColumn(WebinarRealizado::getPresentacion).setCaption("Presentación").setHidable(true).setHidingToggleCaption("Mostrar Presentación").setHidden(true);
        grid.addColumn(WebinarRealizado::getUrlYoutube).setCaption("URL YOUTUBE").setHidable(true).setHidingToggleCaption("Mostrar URL Youtube").setHidden(true);
        
        setCaption("<b>Webinar Realizado</b>");
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorWebinarRealizado.getInstance().getAll());
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<WebinarRealizado> webinars = ControladorWebinarRealizado.getInstance().getByName(strBusqueda);
                int webinarSize = webinars.size();
                if(webinarSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(webinarSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(webinarSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(webinarSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(webinars);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorWebinarRealizado.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
        @Override
    protected void eventDeleteButtonGrid(WebinarRealizado obj) {
        try {
            ui.addWindow(new WebinarRealizadoModalDelete(obj.getId()));
        }catch (Exception ex) {
            Logger.getLogger(WebinarRealizadoDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new WebinarRealizadoModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(WebinarRealizado obj) {
        ui.addWindow(new WebinarRealizadoModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(WebinarRealizado obj,String idBtn) {
        ui.addWindow(new AsistenciaWebinarModalWin(obj.getId(),idBtn));
    }

    @Override
    protected void eventListaAsistentes(WebinarRealizado obj) {
        try {
            ui.addWindow(new ShowPDFDlg(new StreamResource(new ListadoAsistentesPDF(obj), (Utils.getFormatIdLong() + ".pdf").replace(" ", ""))));
        } catch (Exception ex) {
        }
    }
    
    @Override
    protected void eventWebinarsAgremiado(WebinarRealizado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(WebinarRealizado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
