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
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ErrorLevel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
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
        activo.setValue(true);
        activo.setDescription("Dejar marcado si desea que el usuario este activo");

        correo = new TextField();
        correo.setIcon(FontAwesome.USER);
        Element.cfgComponent(correo, "Correo electrónico");
        correo.setPlaceholder("Ingrese su correo (Ej.: ejemplo@gmail.com)");
        correo.setRequiredIndicatorVisible(true);
        correo.setWidth("90%");

        rePassword = new PasswordField();
        rePassword.setIcon(VaadinIcons.LOCK);
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
        password.setSizeUndefined();
        password.setIcon(VaadinIcons.LOCK);
        Element.cfgComponent(password, "Contraseña");
        password.setPlaceholder("Crear contraseña (8~10 caracteres)");
        password.setRequiredIndicatorVisible(true);
        password.setWidthFull();
        password.addValueChangeListener((HasValue.ValueChangeEvent<String> event) -> {
            showPass();
        });

        usuarioGrupo = new ComboBox<>();
        Element.cfgComponent(usuarioGrupo, "Grupo de usuario");
        usuarioGrupo.setPlaceholder("Seleccionar grupo de usuario");
        usuarioGrupo.setRequiredIndicatorVisible(true);
        usuarioGrupo.setTextInputAllowed(false);
        usuarioGrupo.addValueChangeListener((ValueChangeListener) event -> {
            usuarioGrupo.setComponentError(null);
        });

        ResponsiveRow row2 = contenido.addRow().withAlignment(Alignment.BOTTOM_CENTER);
        row2.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row2.addColumn().withDisplayRules(12, 12, 12, 10).withComponent(correo);
        row2.addColumn().withDisplayRules(12, 12, 12, 2).withComponent(activo);
        row2.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(password);
        row2.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(rePassword);
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuarioGrupo);

        try {
            usuarioGrupo.setItems(ControladorUsuarioGrupo.getInstance().getAll());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

        contentLayout.addComponent(contenido);

        setCaption("Usuario");
        setWidth("35%");
    }

    protected void showPass() {
        password.setDescription(password.getValue());
    }

    protected void showRePass() {
        rePassword.setDescription(rePassword.getValue());
    }

    @Override
    protected void loadData(long id) {
        try {
            Usuario obj = ControladorUsuario.getInstance().getById(id);
            this.id = obj.getId();
            activo.setValue(obj.getActivo());
            if (ui.getUsuario().getCorreo().compareTo(obj.getCorreo()) == 0) {
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
                if (regexName()) {
                    Element.buildNotification("Error", "No se permiten números y caracteres especiales en el nombre", "bar error closable").
                            show(Page.getCurrent());
                    nombre.focus();
                } else {
                    if (ContarCaracteres(password.getValue()) >= 8 && ContarCaracteres(password.getValue()) <= 10) {
                        if (rePassword.getValue().compareTo(password.getValue()) == 0) {
                            Usuario usuario = ControladorUsuario.getInstance().getByEmail(correo.getValue());
                            if (id == 0) { // nuevo registro (botón agregar)

                                if (usuario != null) { // nuevo registro con entrada de correo duplicada
                                    Element.buildNotification("Aviso", "Ya existe un registro con el mismo correo", "bar warning closable").
                                            show(Page.getCurrent());
                                } else { // nuevo registro con nuevo correo
                                    SiiComeiiMailer mailer = new SiiComeiiMailer();
                                    if (mailer.enviarBienvenida(obj)) {
                                        obj = ControladorUsuario.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.buildSucessNotification().show(Page.getCurrent());
                                            ui.getFabricaVista().getUsuarioDlg().eventMostrar();
                                            close();
                                        }
                                    } else {
                                        Element.buildNotification("Error", "Verifíque que el correo sea válido y exista", "bar error closable").
                                                show(Page.getCurrent());
                                    }
                                }
                            } else { // editando un registro

                                if (usuario != null) { // mismo correo

                                    if (compareUsers(usuario)) { // el mismo registro
                                        close();
                                    } else if (usuario.getId() != id) {
                                        Element.buildNotification("Aviso", "Ya existe un registro con el mismo correo", "bar warning closable").
                                            show(Page.getCurrent());
                                    } else {
                                        obj = ControladorUsuario.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.buildSucessNotification().show(Page.getCurrent());
                                            ui.getFabricaVista().getUsuarioDlg().eventMostrar();
                                            close();
                                        }
                                    }

                                } else {
                                    SiiComeiiMailer mailer = new SiiComeiiMailer();
                                    if (mailer.enviarBienvenida(obj)) {
                                        obj = ControladorUsuario.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.buildSucessNotification().show(Page.getCurrent());
                                            ui.getFabricaVista().getUsuarioDlg().eventMostrar();
                                            close();
                                        }
                                    } else {
                                        Element.buildNotification("Error", "Verifíque que el correo sea válido y exista", "bar error closable").
                                                show(Page.getCurrent());
                                    }
                                }
                            }
                        } else {
                            Element.buildNotification("Aviso", "Las contraseñas deben coincidir", "bar warning closable").
                                            show(Page.getCurrent());
                            rePassword.focus();
                        }
                    } else {
                        Element.buildNotification("Aviso", "La longitud de la contraseña debe ser entre 8 y 10 caracteres", "bar warning closable").
                                            show(Page.getCurrent());
                    }
                }
                ui.getFabricaVista().usuarioDlg.updateDlg();
            } else {
                if (usuarioGrupo.isEmpty()) {
                    UserError error = new UserError("Este campo es requerido");
                    error.setErrorLevel(ErrorLevel.ERROR);
                    usuarioGrupo.setComponentError(error);
                }
                
                 Element.buildNotification("Aviso", "Faltan campos por completar", "bar warning closable").
                                            show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

    }

    protected boolean regexName() {
        String regex = "[^A-z|ñ|\\p{L}|.| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherName = pattern.matcher(nombre.getValue());

        return matcherName.find();
    }

    protected boolean compareUsers(Usuario usuario) {
        return (usuario.getActivo() == activo.getValue() && usuario.getId() == id
                && usuario.getNombre().compareTo(nombre.getValue()) == 0 && usuario.getPassword().compareTo(password.getValue()) == 0
                && usuario.getUsuarioGrupo() == usuarioGrupo.getValue().getId());
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
