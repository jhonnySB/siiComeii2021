package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public class AgremiadoModalWin extends TemplateModalWin {

    private ComboBox<GradoEstudio> gradoEstudio;
    private TextField institucion;
    private TextField nombre;
    private TextField correo;
    private ComboBox<Pais> pais;
    private ComboBox<String> sexo;

    public AgremiadoModalWin() {
        init();
        delete.setVisible(false);
    }

    public AgremiadoModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        //ComboBox<String> select = new ComboBox<>("My Select");
        //select.setItems("Hombre", "Mujer", "Helicoptero", "Coche");
        gradoEstudio = new ComboBox<>();
        Element.cfgComponent(gradoEstudio, "Grado Estudios");
        gradoEstudio.setRequiredIndicatorVisible(true);

        institucion = new TextField();
        Element.cfgComponent(institucion, "Institución");
        institucion.setRequiredIndicatorVisible(true);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setRequiredIndicatorVisible(true);
        
        correo = new TextField();
        Element.cfgComponent(correo, "Correo");
        correo.setRequiredIndicatorVisible(true);

        pais = new ComboBox<>();
        Element.cfgComponent(pais, "País");
        pais.setRequiredIndicatorVisible(true);

        sexo = new ComboBox<>("Sexo");
        sexo.setItems("Hombre", "Mujer");
        sexo.setRequiredIndicatorVisible(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(gradoEstudio);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(correo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(pais);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(sexo);

        try {
            gradoEstudio.setItems(ControladorGradoEstudio.getInstance().getAll());
            pais.setItems(ControladorPais.getInstance().getAll());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

        contentLayout.addComponent(contenido);

        setCaption("Agremiado");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            Agremiado obj = ControladorAgremiado.getInstance().getById(id);
            this.id = obj.getId();
            gradoEstudio.setValue(obj.getObjGradoEstudio());
            institucion.setValue(obj.getInstitucion());
            nombre.setValue(obj.getNombre());
            correo.setValue(obj.getCorreo());
            pais.setValue(obj.getObjPais());
            sexo.setValue(String.valueOf(obj.getSexo()));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorAgremiado.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                Agremiado obj = new Agremiado();
                obj.setId(id);
                obj.setGradoEstudios(gradoEstudio.getValue() == null ? 0 : gradoEstudio.getValue().getId());
                obj.setInstitucion(institucion.getValue());
                obj.setNombre(nombre.getValue());
                obj.setCorreo(correo.getValue());
                obj.setPais(pais.getValue() == null ? 0 : pais.getValue().getId());
                if (sexo.getValue() != null) {
                    obj.setSexo(sexo.getValue().charAt(0));

                    obj = ControladorAgremiado.getInstance().save(obj);
                    if (obj != null) {
                        Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                        ui.getFabricaVista().getAgremiadoDlg().updateDlg();
                        close();
                    } else {
                        Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                    }
                } else {
                    Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
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
        Binder<Agremiado> binder = new Binder<>();

        //Marca error binder.forField(gradoEstudio).asRequired("Campo requerido").bind(Agremiado::getGradoEstudios,Agremiado::setGradoEstudios);
        binder.forField(institucion).asRequired("Campo requerido").bind(Agremiado::getInstitucion, Agremiado::setInstitucion);
        binder.forField(nombre).asRequired("Campo requerido").bind(Agremiado::getNombre, Agremiado::setNombre);
        binder.forField(correo).asRequired("Campo requerido").bind(Agremiado::getCorreo, Agremiado::setCorreo);
        //Marca error binder.forField(pais).asRequired("Campo requerido").bind(Agremiado::getPais,Agremiado::setPais);
        //Marca error binder.forField(sexo).asRequired("Campo requerido").bind(Agremiado::getSexo,Agremiado::setSexo);
        //binder.bind(sexo, Agremiado::getSexo, Agremiado::setSexo);
        
        return binder.validate().isOk();
    }
}
