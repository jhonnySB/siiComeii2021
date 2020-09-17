package com.tiamex.siicomeii.vista.administracion.usuario;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;

/** @author fred **/
public class UsuarioDlgModalWin extends TemplateModalWin {
    
    private CheckBox activo;
    private TextField correo;
    private TextField nombre;
    private PasswordField password;
    private ComboBox<UsuarioGrupo> usuarioGrupo;
    
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

        activo = new CheckBox();
        Element.cfgComponent(activo, "Activo/Inactivo");
        activo.setValue(true);
        
        correo = new TextField();
        Element.cfgComponent(correo, "Correo electrónico");
        
        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        
        password = new PasswordField();
        Element.cfgComponent(password, "Contraseña");
        
        usuarioGrupo = new ComboBox<>();
        Element.cfgComponent(usuarioGrupo, "Grupo de usuario");
        
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(activo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(correo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(password);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuarioGrupo);
        
        try{
            usuarioGrupo.setItems(ControladorUsuarioGrupo.getInstance().getAll());
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

        contentLayout.addComponent(contenido);

        setCaption("Usuario");
        setWidth("50%");
    } 
    
    @Override
    protected void loadData(long id) {
        try {
            Usuario obj = ControladorUsuario.getInstance().getById(id);
            this.id = obj.getId();
            activo.setValue(obj.getActivo());
            correo.setValue(obj.getCorreo());
            nombre.setValue(obj.getNombre());
            password.setValue(obj.getPassword());
            usuarioGrupo.setValue(obj.getObjUsuarioGrupo());
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
            obj.setActivo(activo.getValue());
            obj.setCorreo(correo.getValue());
            obj.setNombre(nombre.getValue());
            obj.setPassword(password.getValue());
            obj.setUsuarioGrupo(usuarioGrupo.getValue()==null?0:usuarioGrupo.getValue().getId());

            obj = ControladorUsuario.getInstance().save(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getUsuarioDlg().updateDlg();
                close();
            }
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    @Override
    protected void buttonCancelEvent() {
        close();
    }
            
}
