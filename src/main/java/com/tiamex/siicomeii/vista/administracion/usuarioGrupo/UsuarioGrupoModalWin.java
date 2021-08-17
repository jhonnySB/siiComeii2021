package com.tiamex.siicomeii.vista.administracion.usuarioGrupo;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cerimice *
 */
public final class UsuarioGrupoModalWin extends TemplateModalWin {

    private TextField nombre;

    public UsuarioGrupoModalWin() {
        init();
    }

    public UsuarioGrupoModalWin(long id) {
        init();
        loadData(id);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setPlaceholder("Ingrese grupo de usuario");
        nombre.setRequiredIndicatorVisible(true);
        
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);

        contentLayout.addComponent(contenido);

        setCaption("Usuario grupo");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            UsuarioGrupo obj = ControladorUsuarioGrupo.getInstance().getById(id);
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

   @Override
    protected void buttonAcceptEvent() {
        try {
            String regex = "[^A-z|ñ|\\p{L}| ]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(nombre.getValue());
            UsuarioGrupo obj = new UsuarioGrupo();
            obj.setId(id);
            String cadena = nombre.getValue(); 
            UsuarioGrupo grupoUser = (UsuarioGrupo)ControladorUsuarioGrupo.getInstance().getByNames(cadena);
            
            if (!validarCampos()) {
                Element.makeNotification("Debe proporcionar un nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            } 
            else if (matcher.find()) {
                Element.makeNotification("Solo se permiten letras", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                
            }else if(id==0){
                if(grupoUser!=null){
                    Element.makeNotification("Ya existe un registro con el mismo nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());        
                }else{
                    obj.setNombre(nombre.getValue());
                    obj = ControladorUsuarioGrupo.getInstance().save(obj);
                    if (obj != null) {
                        Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                        ui.getFabricaVista().usuarioGrupoDlg.eventMostrar();
                        close();
                    }  
                }
            }else{
                if(grupoUser!=null){
                    if(grupoUser.getId()==id){
                        close();
                    }else{
                        Element.makeNotification("Ya existe un registro con el mismo nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());        
                    }
                }else{
                    obj.setNombre(nombre.getValue());
                    obj = ControladorUsuarioGrupo.getInstance().save(obj);
                    if (obj != null) {
                        Element.makeNotification("Se actualizaron los datos con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                        ui.getFabricaVista().usuarioGrupoDlg.eventMostrar();
                        close();
                    } 
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
        Binder<UsuarioGrupo> binder = new Binder<>();
        
        binder.forField(nombre).asRequired("Campo requerido").bind(UsuarioGrupo::getNombre,UsuarioGrupo::setNombre);
        
        return binder.validate().isOk();
    }
}
