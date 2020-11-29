package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.mailer.SiiComeiiMailer;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
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
    private String backup = " ";
    private int flag = 0;

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
        gradoEstudio.setPlaceholder("Seleccionar grado de estudios");
        gradoEstudio.setRequiredIndicatorVisible(true);

        institucion = new TextField();
        Element.cfgComponent(institucion, "Institución");
        institucion.setPlaceholder("Ingrese nombre de institucion");
        institucion.setRequiredIndicatorVisible(true);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setPlaceholder("Ingrese nombre completo");
        nombre.setRequiredIndicatorVisible(true);

        correo = new TextField();
        Element.cfgComponent(correo, "Correo");
        correo.setPlaceholder("Ingrese correo electrónico");
        correo.setRequiredIndicatorVisible(true);

        pais = new ComboBox<>();
        Element.cfgComponent(pais, "País");
        pais.setPlaceholder("Seleccionar país");
        pais.setRequiredIndicatorVisible(true);

        sexo = new ComboBox<>();
        Element.cfgComponent(sexo, "Sexo");
        sexo.setItems("Hombre", "Mujer");
        sexo.setPlaceholder("Seleccione");
        sexo.setEmptySelectionAllowed(false);
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

            backup = obj.getCorreo();
            flag = 1;

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

    /*
    1-- Comprobar que es un correo valido
    2-- registrar y enviar nuevo correo
    3-- Editar con el mismo correo(sin cambios)
    4-- Editar un registro con otro correo
    5-- Registrar un nuevo usuario con un correo ya registrado
     */
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
                if (gradoEstudio.getValue() != null && sexo.getValue() != null && pais.getValue() != null) {
                    obj.setSexo(sexo.getValue().charAt(0));

                    Agremiado agremiado = ControladorAgremiado.getInstance().getByEmail(correo.getValue());
                    if(agremiado != null && flag == 0){
                        Element.makeNotification("El Correo ingresado ya esta registrado", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                    } else {
                        if(!backup.equals(correo.getValue())){
                            SiiComeiiMailer mailer=new SiiComeiiMailer();
                            if (mailer.enviarBienvenida(obj)) {
                                obj = ControladorAgremiado.getInstance().save(obj);
                                if (obj != null) {
                                    Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                    ui.getFabricaVista().getAgremiadoDlg().updateDlg();
                                    close();
                                } else {
                                    Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                }
                            } else {
                                Element.makeNotification("CORREO NO EXISTE Y NO SE ENVÍA EL CORREO DE BIENVENIDA", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                            }
                        } else {
                            if (flag == 1){
                                obj = ControladorAgremiado.getInstance().save(obj);
                                if (obj != null) {
                                    Element.makeNotification("Datos Modificados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                    ui.getFabricaVista().getAgremiadoDlg().updateDlg();
                                    close();
                                } else {
                                    Element.makeNotification("Faltan campos por llenar", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                }
                            }
                        }
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
        binder.forField(correo).asRequired("Campo requerido").withValidator(new EmailValidator("Ingrese un correo válido")).bind(Agremiado::getCorreo, Agremiado::setCorreo);
        //Marca error binder.forField(pais).asRequired("Campo requerido").bind(Agremiado::getPais,Agremiado::setPais);
        //Marca error binder.forField(sexo).asRequired("Campo requerido").bind(Agremiado::getSexo,Agremiado::setSexo);
        //binder.bind(sexo, Agremiado::getSexo, Agremiado::setSexo);

        return binder.validate().isOk();
    }
}
