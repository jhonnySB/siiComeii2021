package com.tiamex.siicomeii.vista.administracion.tutorial;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import java.util.logging.Logger;

/** @author fred **/
public final class TutorialModalWin extends TemplateModalWin {

    private TextField institucion;
    private TextField nombre;
    private TextField tutor;
    private TextField usuario;

    public TutorialModalWin() {
        init();
        delete.setVisible(false);
    }

    public TutorialModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        institucion = new TextField();
        Element.cfgComponent(institucion, "Institución");
        institucion.setPlaceholder("Ingrese nstitución");
        institucion.setRequiredIndicatorVisible(true);
        
        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setPlaceholder("Ingrese nombre");
        nombre.setRequiredIndicatorVisible(true);
        
        tutor = new TextField();
        Element.cfgComponent(tutor, "Tutor");
        tutor.setPlaceholder("Ingrese tutor");
        tutor.setRequiredIndicatorVisible(true);
        
        usuario = new TextField();
        Element.cfgComponent(usuario, "Usuario");
        usuario.setPlaceholder("Ingrese usuario");
        usuario.setRequiredIndicatorVisible(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(tutor);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuario);

        contentLayout.addComponent(contenido);
        setCaption("Tutorial");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            Tutorial obj = ControladorTutorial.getInstance().getById(id);
            this.id = obj.getId();
            institucion.setValue(obj.getInstitucion());
            nombre.setValue(obj.getNombre());
            tutor.setValue(obj.getTutor());
            usuario.setValue(Long.toString(obj.getUsuario()));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorTutorial.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                Tutorial obj = new Tutorial();
                obj.setId(id);
                obj.setInstitucion(institucion.getValue());
                obj.setNombre(nombre.getValue());
                obj.setTutor(tutor.getValue());
                obj.setUsuario(Long.parseLong(usuario.getValue()));

                obj = ControladorTutorial.getInstance().save(obj);
                if (obj != null) {
                    Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    ui.getFabricaVista().getTutorialDlg().updateDlg();
                    close();
                }
            } else {
                Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
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
        Binder<Tutorial> binder = new Binder<>();
        
        binder.forField(institucion).asRequired("Campo requerido").bind(Tutorial::getInstitucion,Tutorial::setInstitucion);
        binder.forField(nombre).asRequired("Campo requerido").bind(Tutorial::getNombre,Tutorial::setNombre);
        binder.forField(tutor).asRequired("Campo requerido").bind(Tutorial::getTutor,Tutorial::setTutor);
        //Marca error binder.forField(usuario).asRequired("Campo requerido").bind(Tutorial::getUsuario,Tutorial::setUsuario);
        
        return binder.validate().isOk();
    }
}
