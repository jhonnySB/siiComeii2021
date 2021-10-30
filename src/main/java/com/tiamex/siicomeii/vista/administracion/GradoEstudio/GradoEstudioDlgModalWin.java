package com.tiamex.siicomeii.vista.administracion.GradoEstudio;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fred *
 */
public class GradoEstudioDlgModalWin extends TemplateModalWin {

    private TextField nombre;

    public GradoEstudioDlgModalWin() {
        init();
    }

    public GradoEstudioDlgModalWin(long id) {
        init();
        loadData(id);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setPlaceholder("Ingrese grado de estudios");
        nombre.setRequiredIndicatorVisible(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        contentLayout.addComponent(contenido);

        setCaption("Grado de estudios");
        //setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            GradoEstudio obj = ControladorGradoEstudio.getInstance().getById(id);
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

 @Override
    protected void buttonAcceptEvent() {
        try {
            String regex = "[^A-z|ñ|\\p{L}|.| ]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(nombre.getValue());
            GradoEstudio obj = new GradoEstudio();
            obj.setId(id);
            String cadena = nombre.getValue(); 
            GradoEstudio grado = (GradoEstudio)ControladorGradoEstudio.getInstance().getByNames(cadena);
            
            if (!validarCampos()) {
                Element.buildNotification("Error", "Debe proporcionar un nombre", "bar error closable").show(Page.getCurrent());

            } 
            else if (matcher.find()) {
                
                Element.buildNotification("Error", "No se permiten números y caracteres especiales", "bar error closable").show(Page.getCurrent());
            }else if(id==0){
                if(grado!=null){
                    
                    Element.buildNotification("Aviso", "Ya existe un registro con el mismo nombre", "bar warning closable").show(Page.getCurrent());
                }else{
                    obj.setNombre(nombre.getValue());
                    obj = ControladorGradoEstudio.getInstance().save(obj);
                    if (obj != null) {
                        Element.buildSucessNotification().show(Page.getCurrent());
                        ui.getFabricaVista().gradoEstudioDlg.eventMostrar();
                        close();
                    }  
                }
            }else{
                if(grado!=null){
                    if(grado.getId()==id){
                        close();
                    }else{
                        //Element.makeNotification("Ya existe un registro con el mismo nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());        
                        Element.buildNotification("Aviso", "Ya existe un registro con el mismo nombre", "bar warning closable").show(Page.getCurrent());
                    }
                }else{
                    obj.setNombre(nombre.getValue());
                    obj = ControladorGradoEstudio.getInstance().save(obj);
                    if (obj != null) {
                        Element.buildSucessNotification().show(Page.getCurrent());
                        //Element.makeNotification("Se actualizaron los datos con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                        ui.getFabricaVista().gradoEstudioDlg.eventMostrar();
                        close();
                    } 
                }
            }
            ui.getFabricaVista().agremiadoDlg.updateDlg();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
    private boolean validarCampos() {
        Binder<GradoEstudio> binder = new Binder<>();
        binder.forField(nombre).asRequired("Campo requerido").bind(GradoEstudio::getNombre,GradoEstudio::setNombre);
        
        return binder.validate().isOk();
    }
}
