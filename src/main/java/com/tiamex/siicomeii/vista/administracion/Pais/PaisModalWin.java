package com.tiamex.siicomeii.vista.administracion.Pais;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public class PaisModalWin extends TemplateModalWin {

    private TextField nombre;

    public PaisModalWin() {
        init();
        delete.setVisible(false);
    }

    public PaisModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setRequiredIndicatorVisible(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);

        contentLayout.addComponent(contenido);

        setCaption("País");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            Pais obj = ControladorPais.getInstance().getById(id);
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorPais.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            Pais obj = new Pais();
            obj.setId(id);

            if (("".equals(nombre.getValue())) || validarCampos()) {
                validarCampos();
                Element.makeNotification("Debe proporcionar un nombre", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            } else {
                obj.setNombre(nombre.getValue());
                obj = ControladorPais.getInstance().save(obj);
                if (obj != null) {
                    Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    ui.getFabricaVista().getPaisDlg().updateDlg();
                    close();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {
        Binder<Pais> binder = new Binder<>();
        
        binder.forField(nombre).asRequired("Campo requerido").bind(Pais::getNombre,Pais::setNombre);
        
        return binder.validate().isOk();
    }
}
