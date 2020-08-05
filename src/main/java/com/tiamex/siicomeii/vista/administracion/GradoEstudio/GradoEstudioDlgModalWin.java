package com.tiamex.siicomeii.vista.administracion.GradoEstudio;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;


/** @author fred **/
public class GradoEstudioDlgModalWin extends TemplateModalWin {
    
    private TextField nombre;

    public GradoEstudioDlgModalWin() {
        init();
        delete.setVisible(false);
    }

    public GradoEstudioDlgModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);

        contentLayout.addComponent(contenido);

        setCaption("Grado de estudios");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            GradoEstudio obj = ControladorGradoEstudio.getInstance().getById(id);
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorGradoEstudio.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            GradoEstudio obj = new GradoEstudio();
            obj.setId(id);
            obj.setNombre(nombre.getValue());
            obj = ControladorGradoEstudio.getInstance().save(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getGradoEstudioDlg().updateDlg();
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
