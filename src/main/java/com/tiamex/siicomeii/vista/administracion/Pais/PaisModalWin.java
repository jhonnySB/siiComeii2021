package com.tiamex.siicomeii.vista.administracion.Pais;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
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
 * @author fred *
 */
public class PaisModalWin extends TemplateModalWin {

    private TextField nombre;

    public PaisModalWin() {
        init();
       
    }

    public PaisModalWin(long id) {
        init();
        loadData(id);
       
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        nombre.setPlaceholder("Ingrese nombre de país");
        nombre.setRequiredIndicatorVisible(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);

        contentLayout.addComponent(contenido);

        setCaption("País");
        //setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            Pais obj = ControladorPais.getInstance().getById(id);
            this.id = obj.getId();
            nombre.setValue(obj.getNombre());

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    /*
    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorPais.getInstance().delete(id);
            
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    } */

    @Override
    protected void buttonAcceptEvent() {
        try {
            String regex = "[^A-z|ñ|\\p{L}| ]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(nombre.getValue());
            Pais obj = new Pais();
            obj.setId(id);
            String cadena = nombre.getValue(); 
            Pais pais = (Pais)ControladorPais.getInstance().getByNames(cadena);
            
            if (!validarCampos()) {
                Element.makeNotification("Debe proporcionar un nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            } 
            else if (matcher.find()) {
                Element.makeNotification("Solo se permiten letras", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                
            }else if(id==0){
                if(pais!=null){
                    Element.makeNotification("Ya existe un registro con el mismo nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());        
                }else{
                    obj.setNombre(nombre.getValue());
                    obj = ControladorPais.getInstance().save(obj);
                    if (obj != null) {
                        Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                        ui.getFabricaVista().paisDlg.eventMostrar();
                        close();
                    }  
                }
            }else{
                if(pais!=null){
                    if(pais.getId()==id){
                        close();
                    }else{
                        Element.makeNotification("Ya existe un registro con el mismo nombre", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());        
                    }
                }else{
                    obj.setNombre(nombre.getValue());
                    obj = ControladorPais.getInstance().save(obj);
                    if (obj != null) {
                        Element.makeNotification("Se actualizaron los datos con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                        ui.getFabricaVista().paisDlg.eventMostrar();
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
        Binder<Pais> binder = new Binder<>();
        
        binder.forField(nombre).asRequired("Campo requerido").bind(Pais::getNombre,Pais::setNombre);
        
        return binder.validate().isOk(); 
    }
}