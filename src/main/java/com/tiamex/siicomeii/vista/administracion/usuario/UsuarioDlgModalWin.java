package com.tiamex.siicomeii.vista.administracion.usuario;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;

/** @author fred **/
public class UsuarioDlgModalWin extends TemplateModalWin {
    
    private TextField activo;
    private TextField cambiarPassword;
    private TextField correo;
    private TextField nombre;
    private PasswordField password;
    private TextField usuarioGrupo;
    
    public UsuarioDlgModalWin() {
        init();
        delete.setVisible(false);
    }
    
    public UsuarioDlgModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }
    
    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        activo = new TextField();
        cambiarPassword = new TextField();
        correo = new TextField();
        nombre = new TextField();
        password = new PasswordField();
        usuarioGrupo = new TextField();

        Element.cfgComponent(activo, "Activo/Inactivo");
        Element.cfgComponent(cambiarPassword, "Cambiar contraseña");
        Element.cfgComponent(correo, "Correo electrónico");
        Element.cfgComponent(nombre, "Nombre");
        Element.cfgComponent(password, "Contraseña");
        Element.cfgComponent(usuarioGrupo, "Grupo de usuario");

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(activo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(cambiarPassword);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(correo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(password);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuarioGrupo);

        contentLayout.addComponent(contenido);

        setCaption("Usuario");
        setWidth("50%");
    } 
    
    @Override
    protected void loadData(long id) {
        try {
            Usuario obj = ControladorUsuario.getInstance().getById(id);
            this.id = obj.getId();
            activo.setValue(String.valueOf(obj.getActivo()));
            cambiarPassword.setValue(String.valueOf(obj.getCambiarPassword()));
            correo.setValue(obj.getCorreo());
            nombre.setValue(obj.getNombre());
            password.setValue(obj.getPassword());
            usuarioGrupo.setValue(Long.toString(obj.getUsuarioGrupo()));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorUsuario.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    @Override
    protected void buttonAcceptEvent() {
        try {
            Usuario obj = new Usuario();
            obj.setId(id);
            obj.setActivo(Boolean.parseBoolean(activo.getValue()));
            obj.setCambiarPassword(Boolean.parseBoolean(cambiarPassword.getValue()));
            obj.setCorreo(correo.getValue());
            obj.setNombre(nombre.getValue());
            obj.setPassword(password.getValue());
            obj.setUsuarioGrupo(Long.parseLong(usuarioGrupo.getValue()));

            obj = ControladorUsuario.getInstance().save(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getUsuarioDlg().updateDlg();
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
