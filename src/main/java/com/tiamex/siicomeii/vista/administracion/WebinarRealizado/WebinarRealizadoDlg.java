package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public class WebinarRealizadoDlg extends TemplateDlg<WebinarRealizado> {

    public WebinarRealizadoDlg() {
        init();
    }

    private void init() {
        banBoton = 1;
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
    protected void buttonSearchEvent() {
        try {
            grid.setItems(ControladorWebinarRealizado.getInstance().getAll());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
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
    protected void eventAsistenciaButton(WebinarRealizado obj) {

        if (!ControladorAgremiado.getInstance().getAll().isEmpty()) {
            ui.addWindow(new AsistenciaWebinarModalWin(obj.getId()));
        } else {
            Notification notif = new Notification("AVISO | ", Notification.Type.HUMANIZED_MESSAGE);
            notif.setPosition(Position.TOP_CENTER);
            notif.setDelayMsec(3000);
            notif.setDescription("<b>No hay agremiados registrados</b>");
            notif.setIcon(VaadinIcons.WARNING);
            notif.setHtmlContentAllowed(true);
            notif.show(ui.getPage());
        }
    }
}
