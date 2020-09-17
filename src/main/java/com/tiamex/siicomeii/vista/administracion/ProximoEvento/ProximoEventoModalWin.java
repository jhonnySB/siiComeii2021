package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoEventoModalWin extends TemplateModalWin {
    private TextArea descripcion;
    private DateTimeField fecha;
    private TextField imagen;
    private TextField nombre;
    private TextField titulo;

    public ProximoEventoModalWin() {
        init();
        delete.setVisible(false);
    }

    public ProximoEventoModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        descripcion = new TextArea();
        fecha = new DateTimeField();
            fecha.setRequiredIndicatorVisible(true);
            fecha.setValue(LocalDateTime.now().withHour(11).withMinute(00).plusDays(1));
            fecha.setRangeStart(fecha.getValue().minusDays(1));
        imagen = new TextField();
        nombre = new TextField();
        titulo = new TextField();

        Element.cfgComponent(descripcion, "Descipción");
        Element.cfgComponent(fecha, "Fecha");
        Element.cfgComponent(imagen, "Imagen");
        Element.cfgComponent(nombre, "Nombre");
        Element.cfgComponent(titulo, "Título");

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(descripcion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(fecha);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(imagen);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(titulo);

        contentLayout.addComponent(contenido);

        setCaption("Próximo evento");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            ProximoEvento obj = ControladorProximoEvento.getInstance().getById(id);            

            this.id = obj.getId();
            descripcion.setValue(obj.getDescripcion());
            fecha.setValue(obj.getFecha());
            imagen.setValue(obj.getImagen());
            titulo.setValue(obj.getTitulo());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorProximoEvento.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            ProximoEvento obj = new ProximoEvento();
            obj.setId(id);
            obj.setDescripcion(descripcion.getValue());
            obj.setFecha(fecha.getValue());
            obj.setImagen(imagen.getValue());
            obj.setTitulo(titulo.getValue());
            obj.setUsuario(ui.getUsuario().getId());

            obj = ControladorProximoEvento.getInstance().save(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getProximoEventoDlg().updateDlg();
                close();
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }
}
