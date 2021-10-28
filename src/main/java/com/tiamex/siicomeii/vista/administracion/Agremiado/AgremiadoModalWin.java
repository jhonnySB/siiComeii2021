package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.mailer.SiiComeiiMailer;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ErrorLevel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    }

    public AgremiadoModalWin(long id) {
        init();
        loadData(id);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        //ComboBox<String> select = new ComboBox<>("My Select");
        //select.setItems("Hombre", "Mujer", "Helicoptero", "Coche");
        gradoEstudio = new ComboBox<>();
        Element.cfgComponent(gradoEstudio, "Grado Estudios");
        gradoEstudio.setPlaceholder("Seleccionar grado de estudios");
        gradoEstudio.setRequiredIndicatorVisible(true); gradoEstudio.setWidth("95%");

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
        pais.setRequiredIndicatorVisible(true); pais.setWidth("95%");

        sexo = new ComboBox<>();
        Element.cfgComponent(sexo, "Sexo");
        sexo.setItems("Hombre", "Mujer");
        sexo.setPlaceholder("Seleccione una opción");
        //sexo.setPopupWidth("50%");
        sexo.setEmptySelectionAllowed(false);
        sexo.setRequiredIndicatorVisible(true);
        
        gradoEstudio.setTextInputAllowed(false);
        sexo.setTextInputAllowed(false);
        pais.setTextInputAllowed(false);
        
        gradoEstudio.addValueChangeListener((ValueChangeListener) event->{
            gradoEstudio.setComponentError(null);
        });
        sexo.addValueChangeListener((ValueChangeListener) event->{
            sexo.setComponentError(null);
        });
        pais.addValueChangeListener((ValueChangeListener) event->{
            pais.setComponentError(null);
        });

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(correo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(5, 5, 5, 5).withComponent(gradoEstudio);
        row1.addColumn().withDisplayRules(5, 5, 5, 5).withComponent(pais);
        row1.addColumn().withDisplayRules(2, 2, 2, 2).withComponent(sexo);

        try {
            gradoEstudio.setItems(ControladorGradoEstudio.getInstance().getAll());
            pais.setItems(ControladorPais.getInstance().getAll());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

        contentLayout.addComponent(contenido);

        setCaption("Agremiado");
        setWidth("35%");
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
            sexo.setSelectedItem( obj.getSexo()=='H'?"Hombre":"Mujer" );
            //sexo.setValue(String.valueOf(obj.getSexo()));
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
            if (validarCampos() && gradoEstudio.getValue()!=null && sexo.getValue()!=null && pais.getValue()!=null) {
                Agremiado obj = new Agremiado();
                obj.setId(id);
                obj.setInstitucion(institucion.getValue());
                obj.setNombre(nombre.getValue());
                obj.setCorreo(correo.getValue());
                obj.setPais(pais.getValue().getId());
                obj.setSexo(sexo.getValue().charAt(0)); 
                obj.setGradoEstudios(gradoEstudio.getValue().getId());
                
                if(regexName()){
                    Element.makeNotification("Solo se permiten letras en el nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(UI.getCurrent().getPage());
                    nombre.focus();
                }else{
                    Agremiado agremiado = ControladorAgremiado.getInstance().getByEmail(correo.getValue());
                    if(id==0){ // nuevo registro (botón agregar)
                                
                                if (agremiado != null) { // nuevo registro con entrada de correo duplicada
                                    Element.makeNotification("Ya existe un agremiado con el mismo correo", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                }else{ // nuevo registro con nuevo correo
                                    SiiComeiiMailer mailer = new SiiComeiiMailer();
                                    if (mailer.enviarBienvenida(obj)) {
                                        obj = ControladorAgremiado.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getAgremiadoDlg().eventMostrar();
                                            ui.getFabricaVista().getAgremiadoDlg().updateDateFilters(true, true);
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }else {
                                        Element.makeNotification("El correo no existe", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }
                                }
                            }else{ // editando un registro
                                
                                if(agremiado!=null){ // mismo correo
                                    
                                    if(compareAgremiados(agremiado)){ // el mismo registro
                                        close();
                                    }else if(agremiado.getId()!=id){
                                        Element.makeNotification("Ya existe un agremiado con el mismo correo", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }else{
                                        obj = ControladorAgremiado.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getAgremiadoDlg().eventMostrar();
                                            ui.getFabricaVista().getAgremiadoDlg().updateDateFilters(true, true);
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }
                                    
                                }else{
                                    SiiComeiiMailer mailer = new SiiComeiiMailer();
                                    if (mailer.enviarBienvenida(obj)) {
                                        obj = ControladorAgremiado.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getAgremiadoDlg().eventMostrar();
                                            ui.getFabricaVista().getAgremiadoDlg().updateDateFilters(true, true);
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }else {
                                        Element.makeNotification("El correo no existe", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }
                                }
                            }
                }

            } else {
                validateComboBox();
                Element.makeNotification("Faltan campos por llenar", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    protected void validateComboBox(){
        UserError userError = new UserError("Este campo es requerido");
        userError.setErrorLevel(ErrorLevel.ERROR);
        if (sexo.isEmpty()) {
            sexo.setComponentError(userError);
        }
        if (gradoEstudio.isEmpty()) {
            gradoEstudio.setComponentError(userError); 
        }
        if(pais.isEmpty()){
            pais.setComponentError(userError);
        }
    }
    
    protected boolean compareAgremiados(Agremiado agremiado){
        return (agremiado.getId()==id && agremiado.getCorreo().compareTo(correo.getValue())==0 
                && agremiado.getGradoEstudios()==gradoEstudio.getValue().getId() && agremiado.getInstitucion().compareTo(institucion.getValue())==0
                && agremiado.getNombre().compareTo(nombre.getValue())==0 && agremiado.getPais()==pais.getValue().getId() && 
                agremiado.getSexo()==sexo.getValue().charAt(0));
    }
    
    protected boolean regexName(){
        String regex = "[^A-z|ñ|\\p{L}|.| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherName = pattern.matcher(nombre.getValue()); 
        
        return matcherName.find();
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
