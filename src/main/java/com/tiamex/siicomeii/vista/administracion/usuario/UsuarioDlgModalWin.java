package com.tiamex.siicomeii.vista.administracion.usuario;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.mailer.SiiComeiiMailer;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fred *
 */
public class UsuarioDlgModalWin extends TemplateModalWin {

    private CheckBox activo;
    private TextField correo;
    private TextField nombre;
    private PasswordField password;
    private ComboBox<UsuarioGrupo> usuarioGrupo;
    private PasswordField rePassword;

    public UsuarioDlgModalWin() {
        init();
    }

    public UsuarioDlgModalWin(long id) {
        init();
        loadData(id);
    }

    private void init() {
        
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        activo = new CheckBox();
        Element.cfgComponent(activo, "Activo");
        //activo.setWidth("35%");
        activo.setValue(true); 
        activo.setDescription("Dejar marcado si se desea que el usuario este activo");

        correo = new TextField();
        Element.cfgComponent(correo, "Correo electrónico");
        correo.setPlaceholder("Ingrese su correo (Ej.: ejemplo@gmail.com)");
        correo.setRequiredIndicatorVisible(true);
        correo.setWidth("90%");
        
        rePassword = new PasswordField();
        Element.cfgComponent(rePassword, "Repetir contraseña");
        rePassword.setPlaceholder("Repita su contraseña");
        rePassword.setRequiredIndicatorVisible(true);
        rePassword.addValueChangeListener((HasValue.ValueChangeEvent<String> event) -> {
            showRePass();
        });

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setPlaceholder("Ingrese nombre completo");
        nombre.setRequiredIndicatorVisible(true); 

        password = new PasswordField();
        Element.cfgComponent(password, "Contraseña");
        password.setPlaceholder("Crear contraseña (8~10 caracteres)");
        password.setRequiredIndicatorVisible(true);
        password.setWidth("95%");
        password.addValueChangeListener((HasValue.ValueChangeEvent<String> event) -> {
            showPass();
        });

        usuarioGrupo = new ComboBox<>();
        Element.cfgComponent(usuarioGrupo, "Grupo de usuario");
        usuarioGrupo.setPlaceholder("Seleccionar grupo de usuario");
        usuarioGrupo.setRequiredIndicatorVisible(true);

        ResponsiveRow row2 = contenido.addRow().withAlignment(Alignment.BOTTOM_CENTER);
        row2.addColumn().withDisplayRules(12, 12, 12, 10).withComponent(correo);
        row2.addColumn().withDisplayRules(12, 12, 12, 2).withComponent(activo);
        row2.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(password);
        row2.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(rePassword);
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuarioGrupo);
        

        try {
            usuarioGrupo.setItems(ControladorUsuarioGrupo.getInstance().getAll());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

        contentLayout.addComponent(contenido);

        setCaption("Usuario");
        setWidth("50%");
    }
    
    protected void showPass(){
        password.setDescription(password.getValue());
    }
    
    protected void showRePass(){
        rePassword.setDescription(rePassword.getValue());
    }

    @Override
    protected void loadData(long id) {
        try {
            Usuario obj = ControladorUsuario.getInstance().getById(id);
            this.id = obj.getId();
            activo.setValue(obj.getActivo());
            if(ui.getUsuario().getCorreo().compareTo(obj.getCorreo())==0){
                activo.setEnabled(false);
                activo.setDescription("Cambie a otro usuario para desactivar este usuario");
            }
            correo.setValue(obj.getCorreo());

            nombre.setValue(obj.getNombre());
            password.setValue(obj.getPassword());
            password.setDescription(obj.getPassword());
            rePassword.setValue(obj.getPassword());
            rePassword.setDescription(obj.getPassword());
            usuarioGrupo.setValue(obj.getObjUsuarioGrupo());
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
            if (validarCampos() && usuarioGrupo.getValue() != null) {
                Usuario obj = new Usuario();
                obj.setId(id);
                obj.setCambiarPassword(false);
                obj.setActivo(activo.getValue());
                obj.setCorreo(correo.getValue());
                obj.setNombre(nombre.getValue());
                obj.setPassword(password.getValue());
                obj.setUsuarioGrupo(usuarioGrupo.getValue().getId());
                if(regexName()){
                    Element.makeNotification("Solo se permiten letras en el nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(UI.getCurrent().getPage());
                    nombre.focus();
                }else{
                    if (ContarCaracteres(password.getValue()) >= 8 && ContarCaracteres(password.getValue()) <= 10) {
                        if(rePassword.getValue().compareTo(password.getValue())==0){
                            Usuario usuario = ControladorUsuario.getInstance().getByEmail(correo.getValue());
                            if(id==0){ // nuevo registro (botón agregar)
                                
                                if (usuario != null) { // nuevo registro con entrada de correo duplicada
                                    
                                    Element.makeNotification("Ya existe un usuario con el mismo correo", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                }else{ // nuevo registro con nuevo correo
                                    SiiComeiiMailer mailer = new SiiComeiiMailer();
                                    if (mailer.enviarBienvenida(obj)) {
                                        obj = ControladorUsuario.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getUsuarioDlg().eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }else {
                                        Element.makeNotification("El correo no existe", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }
                                }
                            }else{ // editando un registro
                                
                                if(usuario!=null){ // mismo correo
                                    
                                    if(compareUsers(usuario)){ // el mismo registro
                                        close();
                                    }else if(usuario.getId()!=id){
                                        Element.makeNotification("Ya existe un usuario con el mismo correo", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }else{
                                        obj = ControladorUsuario.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getUsuarioDlg().eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }
                                    
                                }else{
                                    SiiComeiiMailer mailer = new SiiComeiiMailer();
                                    if (mailer.enviarBienvenida(obj)) {
                                        obj = ControladorUsuario.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getUsuarioDlg().eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }else {
                                        Element.makeNotification("El correo no existe", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }
                                }
                            }
                        }else{
                            Element.makeNotification("Las contraseñas no coinciden", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                        }
                    } else {
                        Element.makeNotification("La contraseña no cumple con un minimo de 8 caracteres y un máximo de 10", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(UI.getCurrent().getPage());
                    }
                }
                }else{
                   Element.makeNotification("Faltan campos por completar", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(UI.getCurrent().getPage()); 
                }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    
    }
    
    protected boolean regexName(){
        String regex = "[^A-z|ñ| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherName = pattern.matcher(nombre.getValue()); 
        
        return matcherName.find();
    }
    
    protected boolean compareUsers(Usuario usuario){
        return (usuario.getActivo()==activo.getValue() && usuario.getId()==id
                && usuario.getNombre().compareTo(nombre.getValue())==0 && usuario.getPassword().compareTo(password.getValue())==0
                 && usuario.getUsuarioGrupo()==usuarioGrupo.getValue().getId());
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {
        Binder<Usuario> binder = new Binder<>();
        binder.forField(nombre).asRequired("Campo requerido").bind(Usuario::getNombre, Usuario::setNombre);
        binder.forField(correo).asRequired("Campo requerido").withValidator(new EmailValidator("Ingrese un correo válido")).bind(Usuario::getCorreo, Usuario::setCorreo);
        binder.forField(password).asRequired("Campo requerido").bind(Usuario::getPassword, Usuario::setPassword);
        binder.forField(rePassword).asRequired("Campo requerido").bind(Usuario::getPassword, Usuario::setPassword);
        
        return binder.validate().isOk();
    }

    public int ContarCaracteres(String cadena) {

        int cont = 0;

        for (int i = 0; i < cadena.length(); i++) { // length() pide el tamaño de un String
            cont = cont + 1;
        }
        return cont;
    }

}
