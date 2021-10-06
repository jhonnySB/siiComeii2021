package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoAsistentesPDF;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.reportes.webinarRealizadoChart;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Window;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public class WebinarRealizadoDlg extends TemplateDlg<WebinarRealizado> {
    private Button btnCharts;
    public WebinarRealizadoDlg() throws Exception{
        init();
    }

    private void init() {
        btnCharts = new Button(); btnCharts.setIcon(VaadinIcons.BAR_CHART_H);
        btnCharts.setResponsive(true);btnCharts.setDescription("Mostrar estadísticas de webinars realizados");
        btnCharts.addClickListener(event -> {
            try {
                Window wdw = new Window();
                webinarRealizadoChart panelChart;
                wdw.setCaption(VaadinIcons.BAR_CHART.getHtml());
                wdw.setCaptionAsHtml(true);
                wdw.setModal(true);
                wdw.center();
                wdw.setClosable(true);
                wdw.setDraggable(true);
                wdw.setResponsive(true);
                wdw.setResizable(true);
                List<Agremiado> filterList = null;
                if (filterList==null) {
                    panelChart = new webinarRealizadoChart();
                } else {
                    panelChart = new webinarRealizadoChart(filterList);
                }

                wdw.setHeight(panelChart.getHeight() + 41, panelChart.getHeightUnits());
                wdw.setWidth("75%");
                wdw.setContent(panelChart);
                ui.addWindow(wdw);
            } catch (Exception ex) {
                Logger.getLogger(WebinarRealizadoDlg.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        banBoton = 1;
            rowBar.removeAllComponents();
            rowBar.addColumn().withComponent(searchField).setWidth(95.5F,Unit.PERCENTAGE);
            rowBar.addColumn().withComponent(btnCharts);
        
        grid.addColumn(WebinarRealizado::getNombre).setCaption("Nombre").setHidable(false);
        grid.addColumn(WebinarRealizado::getPonente).setCaption("Ponente").setHidable(true).setHidingToggleCaption("Mostrar Ponente");
        grid.addColumn(WebinarRealizado::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución");
        grid.addColumn(WebinarRealizado::getFechaFrm).setCaption("Fecha").setHidable(true).setHidingToggleCaption("Mostrar Fecha");
        grid.addColumn(WebinarRealizado::getPresentacion).setCaption("Presentación").setHidable(true).setHidingToggleCaption("Mostrar Presentación").setHidden(true);
        grid.addColumn(WebinarRealizado::getUrlYoutube).setCaption("URL YOUTUBE").setHidable(true).setHidingToggleCaption("Mostrar URL Youtube").setHidden(true);
        grid.addColumn(webinar->String.valueOf(webinar.getAsistentes())).setCaption("Asistentes").setHidable(true).setHidden(true).setHidingToggleCaption("Mostrar Asistentes");
        
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
