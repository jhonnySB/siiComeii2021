package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author fred **/
public class WebinarRealizadoDlg extends TemplateDlg<WebinarRealizado>{

    public WebinarRealizadoDlg(){
        init();
    }

    private void init(){
        grid.addColumn(WebinarRealizado::getFecha).setCaption("Fecha");
        grid.addColumn(WebinarRealizado::getInstitucion).setCaption("Institución");
        grid.addColumn(WebinarRealizado::getNombre).setCaption("Nombre");
        grid.addColumn(WebinarRealizado::getPonente).setCaption("Ponente");
        grid.addColumn(WebinarRealizado::getPresentacion).setCaption("Presentación");
        grid.addColumn(WebinarRealizado::getUrlYoutube).setCaption("URL YOUTUBE");
        setCaption("<b>Webinar Realizado</b>");
        buttonSearchEvent();
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorWebinarRealizado.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new WebinarRealizadoModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(WebinarRealizado obj) {
        ui.addWindow(new WebinarRealizadoModalWin(obj.getId()));
    }
}
